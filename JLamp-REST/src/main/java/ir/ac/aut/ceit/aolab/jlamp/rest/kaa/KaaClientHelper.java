/*
 * In The Name Of God
 * ======================================
 * [] Project Name : JLamp 
 * 
 * [] Package Name : ir.ac.aut.ceit.aolab.jlamp.rest.kaa
 *
 * [] Creation Date : 05-07-2016
 *
 * [] Created By : Parham Alvani (parham.alvani@gmail.com)
 * =======================================
*/

package ir.ac.aut.ceit.aolab.jlamp.rest.kaa;

import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.event.EventFamilyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ir.ac.aut.ceit.aolab.LampEventFamily;
import ir.ac.aut.ceit.aolab.jlamp.OnIEvent;
import ir.ac.aut.ceit.aolab.jlamp.PIREventClassFamily;
import ir.ac.aut.ceit.aolab.jlamp.TurnEvent;
import ir.ac.aut.ceit.aolab.jlamp.rest.DefaultLampEventListener;
import ir.ac.aut.ceit.aolab.jlamp.rest.DefaultPIREventListener;

public class KaaClientHelper {
    private static final Logger LOG = LoggerFactory.getLogger(KaaClientHelper.class);
    private static KaaClient kaaClient;

    public static void initiate() {
        if (kaaClient != null)
            return;

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

        new Thread(() -> {
            kaaClient.start();
        }).start();

        kaaClient.attachUser("userExternalId", "userAccessToken",
                response -> System.out.println("Attach response" + response.getResult()));


    }

    @Deprecated
    public static void sendOnIEvent(OnIEvent onIEvent) {
        EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        LampEventFamily lampEventFamily = eventFamilyFactory.getLampEventFamily();
        lampEventFamily.sendEventToAll(onIEvent);
    }

    public static void sendTurnEvent(TurnEvent turnEvent) {
        EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        LampEventFamily lampEventFamily = eventFamilyFactory.getLampEventFamily();
        lampEventFamily.sendEventToAll(turnEvent);
    }
}
