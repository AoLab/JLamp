import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.*;

/**
 * Created by iman on 5/9/16.
 */
public class Serial {

    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private static Serial serialInstance;

    private Serial() {
        super();
        serialInstance = this;
    }

    public static Serial getSerialInstance() {
        if (serialInstance == null)
            new Serial();
        return serialInstance;
    }


    void connect(String portName) throws Exception {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.PARITY_EVEN, SerialPort.FLOWCONTROL_NONE);

                printWriter = new PrintWriter(serialPort.getOutputStream());
                bufferedReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

                bufferedReader.readLine();

                Serial.getSerialInstance().write("L112\n");

                System.out.println(Serial.getSerialInstance().readFromInput());
                System.out.println(Serial.getSerialInstance().readFromInput());
                System.out.println(Serial.getSerialInstance().readFromInput());
                System.out.println(Serial.getSerialInstance().readFromInput());

//                serialPort.notifyOnDataAvailable(true);
//                serialPort.addEventListener(new SerialEventListener(bufferedReader));

            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }



    public int readStatus() {
        readFromInput();
        readFromInput();
        return 2;
    }

    public char readFromInput() {
        try {
            return (char) bufferedReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void write(String string) {
        printWriter.write(string);
        printWriter.flush();
    }

    public void close() throws IOException {
        printWriter.close();
        bufferedReader.close();
    }

}
