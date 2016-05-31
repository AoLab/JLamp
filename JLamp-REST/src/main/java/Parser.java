import ir.ac.aut.ceit.aolab.jlamp.TurnEvent;
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
            if(id == null || status == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException exception) {
            LOG.info("Parse exception");
            turnEvent = null;
            return turnEvent;
        }
        turnEvent = new TurnEvent(id, status);
        LOG.info("Parse success!");
        return turnEvent;
    }
}
