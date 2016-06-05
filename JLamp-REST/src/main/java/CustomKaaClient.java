import ir.ac.aut.ceit.aolab.LampEventFamily;
import ir.ac.aut.ceit.aolab.jlamp.OnIEvent;
import ir.ac.aut.ceit.aolab.jlamp.PIREventClassFamily;
import ir.ac.aut.ceit.aolab.jlamp.StatusEvent;
import ir.ac.aut.ceit.aolab.jlamp.TurnEvent;
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

        EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        LampEventFamily lampEventFamily = eventFamilyFactory.getLampEventFamily();
        lampEventFamily.addListener(new DefaultLampEventListener());
        PIREventClassFamily pirEventClassFamily = eventFamilyFactory.getPIREventClassFamily();
        pirEventClassFamily.addListener(new DefaultPIREventListener());

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
    }

    public void sendTurnEvent(TurnEvent turnEvent) {
        EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        LampEventFamily lampEventFamily = eventFamilyFactory.getLampEventFamily();
        lampEventFamily.sendEventToAll(turnEvent);
    }

    public void sendStatusEvent(StatusEvent statusEvent) {
        EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        LampEventFamily lampEventFamily = eventFamilyFactory.getLampEventFamily();
        lampEventFamily.sendEventToAll(statusEvent);

    }


    public void sendPIRStatusEvent(ir.ac.aut.ceit.aolab.jlamp.pir.StatusEvent pirStatusEvent) {
        EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        PIREventClassFamily pirEventFamily = eventFamilyFactory.getPIREventClassFamily();
        pirEventFamily.sendEventToAll(pirStatusEvent);
    }
}
