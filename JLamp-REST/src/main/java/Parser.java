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
        JSONParser parser = new JSONParser();

        OnIEvent onIEvent;
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
            onIEvent = null;
            return onIEvent;
        }
        String id = (String) jsonObject.get("id");
        long interval = (long) jsonObject.get("command");
        int intVersion = Math.toIntExact(interval);
        onIEvent = new OnIEvent(id, intVersion);
        return onIEvent;
    }

    public static TurnEvent getTurnEvent(String input) {
        JSONParser parser = new JSONParser();

        TurnEvent turnEvent;
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(input);
        } catch (ParseException e) {
            turnEvent = null;
            return turnEvent;
        }
        String id = (String) jsonObject.get("id");
        Boolean status = (Boolean) jsonObject.get("status");
        turnEvent = new TurnEvent(id, status);
        return turnEvent;
    }
}
