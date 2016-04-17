import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.kaaproject.kaa.client.DesktopKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.event.EventFamilyFactory;
import org.kaaproject.kaa.client.event.FindEventListenersCallback;
import org.kaaproject.kaa.client.event.registration.UserAttachCallback;
import org.kaaproject.kaa.client.notification.NotificationListener;
import org.kaaproject.kaa.client.notification.NotificationTopicListListener;
import org.kaaproject.kaa.common.endpoint.gen.Topic;
import org.kaaproject.kaa.common.endpoint.gen.UserAttachResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ir.ac.aut.aolab.lamp.Onl.lamp_event_family;
import ir.ac.aut.ceit.iot.lamp.turn;
import ir.ac.aut.ceit.iot.lamp.onlampinteraval.onLampInterval;


public class CustomHttpHandler implements HttpHandler{
	
	public final Logger LOG = LoggerFactory.getLogger(Main.class);

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		String method = exchange.getRequestMethod();
		exchange.getRequestBody();
		System.out.println(method);
		
		switch(method) {
		case "list":
			break;
		case "turn":
			break;
		case "":
			break;
		}

		OutputStream outputStream = exchange.getResponseBody();
//		outputStream.write(response.getBytes());
		outputStream.close();

//		sendToAll(getInterval(query));
	}

	public int getInterval(String query) {
		return Integer.valueOf(query.split("=")[1]);
	}

	public void sendToAll(int timeInterval) {
		KaaClient kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener() {

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
			public void onNotification(long topicId, onLampInterval notification) {
				LOG.info("Received notification {} for topic with id {}", notification, topicId);
			}
		});
		// Starts Kaa SDK client
		kaaClient.start();

		kaaClient.attachUser("userExternalId", "userAccessToken", new UserAttachCallback() {
			@Override
			public void onAttachResult(UserAttachResponse response) {
				System.out.println("Attach response" + response.getResult());
			}
		});

		EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
		lamp_event_family lamp =  eventFamilyFactory.getlamp_event_family();
		List<String> FQNs = new LinkedList<>();
		FQNs.add(lamp_event_family.class.getName());

		kaaClient.findEventListeners(FQNs, new FindEventListenersCallback() {
			@Override
			public void onEventListenersReceived(List<String> eventListeners) {
				System.out.println("I recieved an event!!!");
			}

			@Override
			public void onRequestFailed() {
				// Some code
			}
		});
		
		lamp.sendEventToAll(new turn(true));
	}

}
