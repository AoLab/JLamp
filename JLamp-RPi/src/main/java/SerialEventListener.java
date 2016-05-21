import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by root on 5/21/16.
 */
public class SerialEventListener implements SerialPortEventListener {
    private BufferedReader bufferedReader;

    public SerialEventListener(BufferedReader in) {
        this.bufferedReader = in;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {

        int data;
        try {
            int len = 0;
            while ((data = bufferedReader.read()) > -1) {
                System.out.println(data);
                len++;
                if (len == 2) {
                    System.out.println("READ SUCCESS!");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
