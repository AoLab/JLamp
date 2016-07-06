package ir.ac.aut.ceit.aolab.jlamp.rest.http;

/**
 * Created by iman on 5/21/16.
 */
public class IResponse {
    private String error;
    private String status;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
