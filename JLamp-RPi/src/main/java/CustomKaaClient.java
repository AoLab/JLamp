import ir.ac.aut.ceit.aolab.LampEventFamily;
import ir.ac.aut.ceit.aolab.OnIEvent;
import ir.ac.aut.ceit.aolab.TurnEvent;
import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.event.EventFamilyFactory;
import org.kaaproject.kaa.client.event.FindEventListenersCallback;
import org.kaaproject.kaa.client.event.registration.UserAttachCallback;
import org.kaaproject.kaa.common.endpoint.gen.UserAttachResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

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
        try {
            Serial.getSerialInstance().connect("/dev/ttyACM0");
        } catch (Exception e) {
            e.printStackTrace();
        }

        kaaClient.start();

        EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        LampEventFamily lampEventFamily = eventFamilyFactory.getLampEventFamily();
        kaaClient.attachUser("userExternalId", "userAccessToken", new UserAttachCallback()
        {
            @Override
            public void onAttachResult(UserAttachResponse response) {
                System.out.println("Attach response" + response.getResult());
            }
        });
        List<String> FQNs = new LinkedList<>();
        FQNs.add(TurnEvent.class.getName());
        FQNs.add(OnIEvent.class.getName());

   
        lampEventFamily.addListener(new LampEventFamily.Listener() {
            @Override
            public void onEvent(TurnEvent turnEvent, String s) {

            }

            @Override
            public void onEvent(OnIEvent onIEvent, String s) {
                LOG.info("RECEIIIIIIIIIIIIIEVED!");
                Serial.getSerialInstance().sendLampCommand(onIEvent.getId(),onIEvent.getCommand());
            }

        });

    }

    public static CustomKaaClient getKaaClient() {

        if (instance == null)
            instance = new CustomKaaClient();

        return instance;
    }


}
