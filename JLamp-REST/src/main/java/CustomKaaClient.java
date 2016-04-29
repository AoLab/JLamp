import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
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

        kaaClient.addNotificationListener(new CustomNotificationListener());
    }

    public static CustomKaaClient getKaaClient() {

        if (instance == null)
            instance = new CustomKaaClient();

        return instance;
    }

}
