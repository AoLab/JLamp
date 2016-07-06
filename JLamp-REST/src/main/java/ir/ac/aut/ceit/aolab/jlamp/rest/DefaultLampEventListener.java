package ir.ac.aut.ceit.aolab.jlamp.rest;

import ir.ac.aut.ceit.aolab.LampEventFamily;
import ir.ac.aut.ceit.aolab.jlamp.StatusEvent;

import java.io.IOException;

/**
 * Created by iman on 6/1/16.
 */
public class DefaultLampEventListener implements LampEventFamily.Listener {
    @Override
    public void onEvent(StatusEvent statusEvent, String s) {
        if (statusEvent.getId().equals("true")) {
            try {
                String response = "{\"status\":true}";
                CustomHttpHandler.httpExchange.sendResponseHeaders(200, response.length());
                CustomHttpHandler.httpExchange.getResponseBody().write(response.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            try {
                String response = "{\"status\":false}";
                CustomHttpHandler.httpExchange.sendResponseHeaders(200, response.length());
                CustomHttpHandler.httpExchange.getResponseBody().write(response.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

        CustomHttpHandler.httpExchange.close();
    }
}
