import ir.ac.aut.ceit.aolab.LampEventFamily;
import ir.ac.aut.ceit.aolab.jlamp.StatusEvent;

import java.io.*;

import static org.apache.avro.util.ByteBufferOutputStream.BUFFER_SIZE;

/**
 * Created by iman on 6/1/16.
 */
public class DefaultLampEventListener implements LampEventFamily.Listener {
    @Override
    public void onEvent(StatusEvent statusEvent, String s) {
        if(statusEvent.getId().equals("true")) {
            try {
                String response = "{\"status\":true}";
                write(response, CustomHttpHandler.httpExchange.getResponseBody());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            try {
                String response= "{\"status\":false}";
                write(response, CustomHttpHandler.httpExchange.getResponseBody());
            } catch (IOException e) {
                e.printStackTrace();
            }

        CustomHttpHandler.httpExchange.close();
    }

    private void write(String response, OutputStream outputStream) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(outputStream)) {

            try (ByteArrayInputStream bis = new ByteArrayInputStream(response.getBytes())) {
                byte [] buffer = new byte [response.length()];
                int count ;
                while ((count = bis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
            }
        }

    }
}
