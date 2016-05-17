import ir.ac.aut.ceit.aolab.LampEventFamily;
import ir.ac.aut.ceit.aolab.OnIEvent;
import ir.ac.aut.ceit.aolab.TurnEvent;
import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.event.EventFamilyFactory;
import org.kaaproject.kaa.client.event.registration.UserAttachCallback;
import org.kaaproject.kaa.common.endpoint.gen.UserAttachResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iman on 4/29/16.
 */
public class CustomKaaClient {

    private static CustomKaaClient instance;
    private final Logger LOG = LoggerFactory.getLogger(CustomKaaClient.class);
    private KaaClient kaaClient;

    private CustomKaaClient() {

        kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener() {

            @Override
            public void onStarted() {
                super.onStarted();
                LOG.info("Kaa Client Started!");
            }

        });

//        kaaClient.findEventListeners(FQNs, new FindEventListenersCallback() {
//            @Override
//            public void onEventListenersReceived(List<String> eventListeners) {
//                System.out.println("Events:");
//                System.out.println(eventListeners);
//            }
//        @Override
//        public void onRequestFailed() {
        // Some code
//        }
//        });
//        kaaClient.addNotificationListener(new CustomNotificationListener());
        kaaClient.start();
        kaaClient.attachUser("userExternalId", "userAccessToken", new UserAttachCallback() {
            @Override
            public void onAttachResult(UserAttachResponse response) {
                System.out.println("Attach response" + response.getResult());
            }
        });


    }

    public static CustomKaaClient getKaaClient() {

        if (instance == null)
            instance = new CustomKaaClient();

        return instance;
    }

    public void sendOnIEvent(OnIEvent onIEvent) {
        EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        LampEventFamily lampEventFamily = eventFamilyFactory.getLampEventFamily();
        lampEventFamily.sendEventToAll(onIEvent);
        LOG.info("\n\nI'm in END\n\n");
    }

    public void sendTurnEvent(TurnEvent turnEvent) {
        EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        LampEventFamily lampEventFamily = eventFamilyFactory.getLampEventFamily();
        lampEventFamily.sendEventToAll(turnEvent);
    }


}
