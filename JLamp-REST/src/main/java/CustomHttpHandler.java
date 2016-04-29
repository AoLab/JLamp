import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;


public class CustomHttpHandler implements HttpHandler {

    public final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String context = exchange.getRequestURI().toString();

        switch (context) {
            case "/lamp/turn":
                try {
                    Parser.getInterval(context);
                    exchange.sendResponseHeaders(200, Constants.code200.length());
                    exchange.getResponseBody().write(Constants.code200.getBytes());
                } catch (ParseException e) {
                    exchange.sendResponseHeaders(400, Constants.code400.length());
                    exchange.getResponseBody().write(Constants.code400.getBytes());
                    e.printStackTrace();
                }
                break;
            case "/lamp/list":
                exchange.sendResponseHeaders(501, Constants.code501.length());
                exchange.getResponseBody().write(Constants.code501.getBytes());
                break;
            default:
                exchange.sendResponseHeaders(404, Constants.code404.length());
                exchange.getResponseBody().write(Constants.code404.getBytes());
        }

        exchange.getResponseBody().close();
    }

    public int getInterval(String query) {
        return Integer.valueOf(query.split("=")[1]);
    }

    public void sendToAll(int timeInterval) {
        //   KaaClient kaaClient = Kaa.newClient(new DesktopKaaPlatformContext(), new SimpleKaaClientStateListener() {

        //       @Override
        //       public void onStarted() {
        //           LOG.info("Kaa SDK client started!");
        //       }
//        });

        // Registering listener for topic updates
        //   kaaClient.addTopicListListener(new NotificationTopicListListener() {
//            @Override
//            public void onListUpdated(List<Topic> topicList) {
//                LOG.info("Topic list updated!");
//                for (Topic topic : topicList) {
//                    LOG.info("Received topic with id {} and name {}", topic.getId(), topic.getName());
//                }
//            }
//        });

        // Registering listener for notifications
        //  kaaClient.addNotificationListener(new NotificationListener() {

        //      @Override
        //      public void onNotification(long topicId, onLampInterval notification) {
        //          LOG.info("Received notification {} for topic with id {}", notification, topicId);
        //      }
        //  });
        //  // Starts Kaa SDK client
        //  kaaClient.start();

        //  kaaClient.attachUser("userExternalId", "userAccessToken", new UserAttachCallback() {
        //      @Override
        //      public void onAttachResult(UserAttachResponse response) {
        //          System.out.println("Attach response" + response.getResult());
        //      }
        //  });

        //  EventFamilyFactory eventFamilyFactory = kaaClient.getEventFamilyFactory();
        //  lamp_event_family lamp = eventFamilyFactory.getlamp_event_family();
        //  List<String> FQNs = new LinkedList<>();
        //  FQNs.add(lamp_event_family.class.getName());

        //  kaaClient.findEventListeners(FQNs, new FindEventListenersCallback() {
        //      @Override
        //      public void onEventListenersReceived(List<String> eventListeners) {
        //          System.out.println("I recieved an event!!!");
        //      }

        //      @Override
        //           public void onRequestFailed() {
        // Some code
        //         }
        // });

        // lamp.sendEventToAll(new turn(true));
    }

}
