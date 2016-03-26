import java.util.List;

import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.notification.NotificationListener;
import org.kaaproject.kaa.client.notification.NotificationTopicListListener;
import org.kaaproject.kaa.common.endpoint.gen.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ir.ac.aut.ceit.aolab.lamp.Lamp;
 
public class Main {
 
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
 
    public static void main(String[] args) {
        new Main().launch();
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
        kaaClient.addNotificationListener(new NotificationListener() {

			@Override
			public void onNotification(String topicId, Lamp notification) {
				LOG.info("Received notification {} for topic with id {}", notification, topicId);
				// TODO Auto-generated method stub
				
			}
        });
 
        // Starts Kaa SDK client
        kaaClient.start();
    }
}