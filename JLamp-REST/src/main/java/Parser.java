import ir.ac.aut.ceit.aolab.OnIEvent;
import ir.ac.aut.ceit.aolab.TurnEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by iman on 4/27/16.
 */
public class Parser {
    private static final Logger LOG = LoggerFactory.getLogger(Parser.class);
    public static OnIEvent getOnIEvent(String input) {
        LOG.info("Parsing .......");
        JSONParser parser = new JSONParser();

        OnIEvent onIEvent;
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(input);
        } catch (ParseException e) {
            LOG.info("Parse exception");
            onIEvent = null;
            return onIEvent;
        }
        String id = (String) jsonObject.get("id");
        long interval = (long) jsonObject.get("command");
        int intVersion = Math.toIntExact(interval);
        LOG.info("Parsed!!");
        onIEvent = new OnIEvent(id, intVersion);
        LOG.info("Parse success!");
        return onIEvent;
    }

    public static TurnEvent getTurnEvent(String input) {
        LOG.info("Parsing .......");
        JSONParser parser = new JSONParser();

        TurnEvent turnEvent;
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(input);
        } catch (ParseException e) {
            LOG.info("Parse exception");
            turnEvent = null;
            return turnEvent;
        }
        String id;
        Boolean status;
        try {
            id = (String) jsonObject.get("id");
            status = (Boolean) jsonObject.get("status");
            System.out.println(status);
            LOG.info("Parsed!!");
        } catch (Exception exception) {
            LOG.info("Parse exception");
            turnEvent = null;
            return turnEvent;
        }
        turnEvent = new TurnEvent(id, status);
        LOG.info("Parse success!");
        return turnEvent;
    }
}
