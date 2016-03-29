import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

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
import com.sun.net.httpserver.HttpServer;

import ir.ac.aut.ceit.aolab.jlamp.time_interval;
import ir.ac.aut.ceit.aolab.lamp.Lamp;
import ir.ac.aut.ceit.aolab.timeintervalevent.TimeIntervalEvent;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 80), 30);

			server.createContext("/lamp/Onl", new CustomHttpHandler());
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static class CustomHttpHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {

			String query = exchange.getRequestURI().getQuery();

			int interval = getInterval(query);
			String response = "Your requested time interval was sent to kaa: interval is " + interval + " second(s)";
			exchange.sendResponseHeaders(200, response.length());

			OutputStream outputStream = exchange.getResponseBody();
			outputStream.write(response.getBytes());
			outputStream.close();
			
			sendToAll(getInterval(query));
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
				public void onNotification(String topicId, Lamp notification) {
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
			TimeIntervalEvent timeIntervalEvent = eventFamilyFactory.getTimeIntervalEvent();
			List<String> FQNs = new LinkedList<>();
			FQNs.add(time_interval.class.getName());

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
			
			timeIntervalEvent.sendEventToAll(new time_interval(timeInterval));
		}
	}

}
