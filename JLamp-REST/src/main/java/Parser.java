import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by iman on 4/27/16.
 */
public class Parser {
    public static int getInterval(String timeInterval) throws ParseException{
        JSONParser parser = new JSONParser();
        JSONObject time;
        time = (JSONObject) parser.parse(timeInterval);

        return (int) time.get("interval");
    }
}
