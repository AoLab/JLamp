import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ir.ac.aut.ceit.aolab.OnIEvent;
import ir.ac.aut.ceit.aolab.TurnEvent;
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

    public CustomHttpHandler(CustomKaaClient kaaClient, Request request) {
        this.kaaClient = kaaClient;
        this.request = request;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String context = exchange.getRequestURI().toString();

        // Interface implementation based:
        // Response response = request.requestOnSuccess(context);
        // exchange.sendResponseHeaders(response.getStatusCode(), response.getResponse().length());
        // exchange.getResponseBody().write(response.getResponse().getBytes());
        switch (context) {
            case "/lamp/turn":
                TurnEvent turnEvent = Parser.getTurnEvent(readFromInputStream(exchange.getRequestBody()));
                if(turnEvent == null) {
                    exchange.sendResponseHeaders(400, Constants.code400.length());
                    exchange.getResponseBody().write(Constants.code400.getBytes());
                    break;
                }
                exchange.sendResponseHeaders(200, Constants.code200.length());
                exchange.getResponseBody().write(Constants.code200.getBytes());
                kaaClient.sendTurnEvent(turnEvent);
                break;
            case "/lamp/list":
                exchange.sendResponseHeaders(501, Constants.code501.length());
                exchange.getResponseBody().write(Constants.code501.getBytes());
                break;
            case "/lamp/OnI":
                String ans = readFromInputStream(exchange.getRequestBody());
                OnIEvent onIEvent = null;
                onIEvent = Parser.getOnIEvent(ans);
                if(onIEvent == null) {
                    exchange.sendResponseHeaders(400, Constants.code400.length());
                    exchange.getResponseBody().write(Constants.code400.getBytes());
                }
                exchange.sendResponseHeaders(200, Constants.code200.length());
                exchange.getResponseBody().write(Constants.code200.getBytes());
                kaaClient.sendOnIEvent(Parser.getOnIEvent(ans));
                break;
            default:
                exchange.sendResponseHeaders(404, Constants.code404.length());
                exchange.getResponseBody().write(Constants.code404.getBytes());
        }

        exchange.getResponseBody().close();
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
        return ans;
    }

}
