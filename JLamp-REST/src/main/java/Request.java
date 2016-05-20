/**
 * Created by iman on 5/20/16.
 */
public interface Request {
    String[] requests = {"/lamp/turn", "lamp/OnI", "lamp/list"};
    Response requestOnSuccess(String requestMethod);
    Response requestOnFailure(String requestMethod);
}
