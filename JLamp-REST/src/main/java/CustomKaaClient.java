import ir.ac.aut.ceit.iot.TimeInterval;
import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.notification.NotificationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iman on 4/29/16.
 */
public class CustomKaaClient {

    private static CustomKaaClient instance;
    private KaaClient kaaClient;
    private final Logger LOG = LoggerFactory.getLogger(CustomKaaClient.class);

    private CustomKaaClient() {
        kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener() {

            @Override
            public void onStarted() {
                super.onStarted();
                LOG.info("Kaa Client Started!");
            }

        });

        kaaClient.addNotificationListener(new CustomNotificationListener());
    }

    public static CustomKaaClient getKaaClient() {

        if (instance == null)
            instance = new CustomKaaClient();

        return instance;
    }
}
