import ir.ac.aut.ceit.aolab.OnIEvent;
import ir.ac.aut.ceit.aolab.TurnEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by iman on 4/27/16.
 */
public class Parser {
    public static OnIEvent getOnIEvent(String input) throws ParseException {
        JSONParser parser = new JSONParser();

        OnIEvent onIEvent;
        JSONObject jsonObject = (JSONObject) parser.parse(input);
        String id = (String) jsonObject.get("id");
        System.out.println(id);
        long interval = (long) jsonObject.get("command");
        int intVersion = Math.toIntExact(interval);
        System.out.println(interval);
        onIEvent = new OnIEvent(id, intVersion);
        return onIEvent;
    }

    public static TurnEvent getTurnEvent(String input) throws ParseException {
        JSONParser parser = new JSONParser();

        TurnEvent turnEvent;
        JSONObject jsonObject = (JSONObject) parser.parse(input);
        String id = (String) jsonObject.get("id");
        Boolean status = (Boolean) jsonObject.get("status");
        turnEvent = new TurnEvent(id, status);
        return turnEvent;
    }
}
