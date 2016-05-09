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

    public Serial()
    {
        super();
    }

    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);

            if ( commPort instanceof SerialPort)
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.PARITY_EVEN, SerialPort.STOPBITS_1);
                System.out.println(serialPort.getBaudRate());

                printWriter = new PrintWriter(serialPort.getOutputStream());
                bufferedReader = new BufferedReader( new InputStreamReader(serialPort.getInputStream()));
            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    public void write(String string) {
        printWriter.write(string);
        printWriter.flush();
    }

}
