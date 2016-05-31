import ir.ac.aut.ceit.aolab.LampEventFamily;
import ir.ac.aut.ceit.aolab.jlamp.StatusEvent;

import java.io.IOException;

/**
 * Created by iman on 6/1/16.
 */
public class DefaultLampEventListener implements LampEventFamily.Listener {
    @Override
    public void onEvent(StatusEvent statusEvent, String s) {
        if(statusEvent.getId().equals("true"))
            try {
                CustomHttpHandler.httpExchange.getResponseBody().write("{\"status\":true}".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        else
            try {
                CustomHttpHandler.httpExchange.getResponseBody().write("{\"status\":false}".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

        CustomHttpHandler.httpExchange.close();
    }
}
