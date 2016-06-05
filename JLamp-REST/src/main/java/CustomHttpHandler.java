import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ir.ac.aut.ceit.aolab.jlamp.OnIEvent;
import ir.ac.aut.ceit.aolab.jlamp.StatusEvent;
import ir.ac.aut.ceit.aolab.jlamp.TurnEvent;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class CustomHttpHandler implements HttpHandler {

    private final Logger LOG = LoggerFactory.getLogger(CustomHttpHandler.class);
    private CustomKaaClient kaaClient;
    private Request request;
    public static HttpExchange httpExchange;

    public CustomHttpHandler(CustomKaaClient kaaClient, Request request) {
        this.kaaClient = kaaClient;
        this.request = request;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        httpExchange = exchange;
        String context = exchange.getRequestURI().toString();

        // Interface implementation based:
        // Response response = request.requestOnSuccess(context);
        // exchange.sendResponseHeaders(response.getStatusCode(), response.getResponse().length());
        // exchange.getResponseBody().write(response.getResponse().getBytes());
        switch (context) {
            case "/lamp/turn":
                LOG.info("Received /lamp/turn");
                TurnEvent turnEvent = Parser.getTurnEvent(readFromInputStream(exchange.getRequestBody()));
                if(turnEvent == null) {
                    LOG.info("Malformatted json");
                    exchange.sendResponseHeaders(400, Constants.code400.length());
                    exchange.getResponseBody().write(Constants.code400.getBytes());
                    break;
                }
                exchange.sendResponseHeaders(200, Constants.code200.length());
                exchange.getResponseBody().write(Constants.code200.getBytes());
                kaaClient.sendTurnEvent(turnEvent);
                LOG.info("Successfuly sent an event");
                exchange.getResponseBody().close();
                break;
            case "/lamp/list":
                exchange.sendResponseHeaders(501, Constants.code501.length());
                exchange.getResponseBody().write(Constants.code501.getBytes());
                exchange.getResponseBody().close();
                break;
            case "/lamp/OnI":
                String ans = readFromInputStream(exchange.getRequestBody());
                OnIEvent onIEvent = null;
                onIEvent = Parser.getOnIEvent(ans);
                if(onIEvent == null) {
                    LOG.info("Malformatted json");
                    exchange.sendResponseHeaders(400, Constants.code400.length());
                    exchange.getResponseBody().write(Constants.code400.getBytes());
                    break;
                }
                exchange.sendResponseHeaders(200, Constants.code200.length());
                exchange.getResponseBody().write(Constants.code200.getBytes());
                kaaClient.sendOnIEvent(onIEvent);
                LOG.info("Successfuly sent an event");
                exchange.getResponseBody().close();
                break;
            case "/lamp/status":
                LOG.info("Received /lamp/status");
                ans = readFromInputStream(exchange.getRequestBody());
                StatusEvent statusEvent = null;
                statusEvent = Parser.getStatusEvent(ans);
                if(statusEvent == null) {
                    LOG.info("Malformatted json");
                    exchange.sendResponseHeaders(400, Constants.code400.length());
                    exchange.getResponseBody().write(Constants.code400.getBytes());
                    break;
                }
                kaaClient.sendStatusEvent(statusEvent);
                LOG.info("Successfuly sent an event");
                break;
            case "/lamp/pir":
                ans = readFromInputStream(exchange.getRequestBody());
                ir.ac.aut.ceit.aolab.jlamp.pir.StatusEvent pirStatusEvent = null;
                pirStatusEvent = Parser.getPIRStatusEvent(ans);
                if(pirStatusEvent == null) {
                    LOG.info("Malformatted json");
                    exchange.sendResponseHeaders(400, Constants.code400.length());
                    exchange.getResponseBody().write(Constants.code400.getBytes());
                    break;
                }
                kaaClient.sendPIRStatusEvent(pirStatusEvent);
                LOG.info("Successfuly sent an event");
                break;
            default:
                exchange.sendResponseHeaders(404, Constants.code404.length());
                exchange.getResponseBody().write(Constants.code404.getBytes());
                exchange.getResponseBody().close();
        }

    }

    private String readFromInputStream(InputStream inputStream) {
        String ans = "";
        String read;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((read = br.readLine()) != null) {
                ans += read;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOG.info("Received " + ans + " body string.");
        return ans;
    }

}
