/**
 * Created by iman on 5/21/16.
 */
public class IResponse implements Request {
    @Override
    public Response requestOnSuccess(String requestMethod) {
        CustomKaaClient kaaClient = CustomKaaClient.getKaaClient();
        return null;
    }

    @Override
    public Response requestOnFailure(String requestMethod) {
        return null;
    }
}
