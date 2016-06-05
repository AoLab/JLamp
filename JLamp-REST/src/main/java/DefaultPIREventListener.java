import ir.ac.aut.ceit.aolab.jlamp.PIREventClassFamily;
import ir.ac.aut.ceit.aolab.jlamp.pir.StatusEvent;

import java.io.IOException;

/**
 * Created by iman on 6/5/16.
 */
public class DefaultPIREventListener implements PIREventClassFamily.Listener{
    @Override
    public void onEvent(StatusEvent statusEvent, String s) {
        try {
            CustomHttpHandler.httpExchange.sendResponseHeaders(200, statusEvent.getId().length());
            CustomHttpHandler.httpExchange.getResponseBody().write(statusEvent.getId().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        CustomHttpHandler.httpExchange.close();
    }
}
