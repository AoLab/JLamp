package ir.ac.aut.ceit.aolab.jlamp.rest;

/**
 * Created by iman on 5/20/16.
 */
public class Response {
    private int statusCode = 200;
    private String response = "";

    public Response(int statusCode, String response) {
        this.statusCode = statusCode;
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
