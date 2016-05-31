package ir.ac.aut.ceit.aolab.jlamp.rpi.model;

import ir.ac.aut.ceit.aolab.jlamp.rpi.serial.Serial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by iman on 5/31/16.
 */
public class PIR {
    private String id;
    private static Serial serial = Serial.getSerialInstance();
    private static final char identifier = 'P';
    private static final Logger LOG = LoggerFactory.getLogger(PIR.class);
    private static HashMap<String, PIR> PIRdatabase = new HashMap<>();

    public PIR(String id) {
        this.id = id;
        PIRdatabase.put(id, this);
    }

    public static PIR getbyId(String id) {
        if(PIRdatabase.containsKey(id))
            return PIRdatabase.get(id);
        else
            return new PIR(id);
    }

    public int getStatus(){
        String command = identifier + id + '2' + '\n';
        serial.write(command);
        LOG.info("Sent PIR command " + command);

        serial.readChar(); // Skipping #
        serial.readChar(); // Skipping (
        int lightness = serial.readChar();
        serial.readChar(); // Skipping )

        return lightness;
    }
}
