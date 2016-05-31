package ir.ac.aut.ceit.aolab.jlamp.rpi.serial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by iman on 5/9/16.
 */
public class Serial {

	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	private static Serial serialInstance;
	private static final Logger LOG = LoggerFactory.getLogger(Serial.class);


	private Serial() {
		super();
		serialInstance = this;
	}

	public static Serial getSerialInstance() {
		if (serialInstance == null)
			new Serial();
		return serialInstance;
	}


	public void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		LOG.info("Found the " + portName + " port.");
		if (portIdentifier.isCurrentlyOwned()) {
			LOG.error("Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);
			LOG.info("Opened port " + portName);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.PARITY_EVEN, SerialPort.FLOWCONTROL_NONE);

				printWriter = new PrintWriter(serialPort.getOutputStream());
				bufferedReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

                // For skipping AnA string
                skipInput();

			} else {
				LOG.error("Only serial ports are handled by this example.");
			}
		}
	}

	public char readChar() {
		try {
			return (char) bufferedReader.read();
		} catch (IOException e) {
			LOG.error("Failed to read a char.");
		}
		return 0;
	}

    public String readLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            LOG.error("Failed to read a line.");
        }
        return null;

    }

    public int readInt() {
        try {
            return  Integer.parseInt(bufferedReader.readLine());
        } catch (IOException e) {
            LOG.error("Failed to read a line.");
        }
        return -1;
    }

	public void write(String string) {
		printWriter.write(string);
        LOG.info("Wrote string " + string + " to serial");
		printWriter.flush();
	}

	public void close() throws IOException {
		printWriter.close();
		bufferedReader.close();
        LOG.info("Serial link was closed");
	}

    public void skipInput() {
        readLine();
        LOG.info("Successfully skipped one line");
    }

}
