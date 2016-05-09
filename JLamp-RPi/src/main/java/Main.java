import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.event.registration.UserAttachCallback;
import org.kaaproject.kaa.client.notification.NotificationTopicListListener;
import org.kaaproject.kaa.common.endpoint.gen.Topic;
import org.kaaproject.kaa.common.endpoint.gen.UserAttachResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.List;



public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // new Main().launch();
        Serial serial = new Serial();
        try {
            serial.connect("/dev/tnt0");
            serial.write("Salam");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void launch() {
        // Create client for Kaa SDK
        KaaClient kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(),
                new SimpleKaaClientStateListener() {
                    @Override
                    public void onStarted() {
                        LOG.info("Kaa SDK client started!");
                    }
                });

        // Registering listener for topic updates
        kaaClient.addTopicListListener(new NotificationTopicListListener() {
            @Override
            public void onListUpdated(List<Topic> topicList) {
                LOG.info("Topic list updated!");
                for (Topic topic : topicList) {
                    LOG.info("Received topic with id {} and name {}", topic.getId(), topic.getName());
                }
            }
        });

        // Registering listener for notifications
        //kaaClient.addNotificationListener();

        // Starts Kaa SDK client
        kaaClient.start();

        kaaClient.attachUser("userExternalId", "userAccessToken", new UserAttachCallback() {
            @Override
            public void onAttachResult(UserAttachResponse response) {
                System.out.println("Attach response" + response.getResult());
            }
        });

    }
}
