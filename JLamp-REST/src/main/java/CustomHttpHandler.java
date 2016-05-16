import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class CustomHttpHandler implements HttpHandler {

    public final Logger LOG = LoggerFactory.getLogger(CustomHttpHandler.class);
    private CustomKaaClient kaaClient;

    public CustomHttpHandler(CustomKaaClient kaaClient) {
        this.kaaClient = kaaClient;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String context = exchange.getRequestURI().toString();

        switch (context) {
            case "/lamp/turn":
                try {
                    exchange.sendResponseHeaders(200, Constants.code200.length());
                    exchange.getResponseBody().write(Constants.code200.getBytes());
                    kaaClient.sendTurnEvent(Parser.getTurnEvent(readFromInputStream(exchange.getRequestBody())));
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
            case "/lamp/OnI":
                exchange.sendResponseHeaders(200, Constants.code200.length());
                exchange.getResponseBody().write(Constants.code200.getBytes());
                LOG.info("\n\nIM HERE!!!!!!!!!!!!!!!!!\n\n");
                try {
                    LOG.info("\n\nRecieved!!!!!!!!!!!!!!!!!\n\n");
                    String ans = readFromInputStream(exchange.getRequestBody());
                    LOG.info("\n\n"+ans+"\n\n");
                    kaaClient.sendOnIEvent(Parser.getOnIEvent(ans));
                    LOG.info("\n\nFINALLLLLLLLL\n");
                } catch (ParseException e) {
                    LOG.info("\n\nNOT Recieved!!!!!!!!!!!!!!!!!\n\n");
                    exchange.sendResponseHeaders(400, Constants.code400.length());
                    exchange.getResponseBody().write(Constants.code400.getBytes());
                    e.printStackTrace();
                }
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
