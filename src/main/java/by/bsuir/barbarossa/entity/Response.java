package by.bsuir.barbarossa.entity;

import java.io.Serializable;

public class Response implements Serializable {
    private String response;
    private String clientRequest;
    private String serverResponse;

    public Response() {
    }

    public Response(String response) {
        this.response = response;
    }

    public Response(String clientRequest, String serverResponse) {
        this.clientRequest = clientRequest;
        this.serverResponse = serverResponse;
    }

    public String getResponse() {
        return response;
    }

    public String getMessage() {

        return (response != null) ? response : formClientMessage() + formServerMessage();
    }

    public void setResponse(String response) {
        this.response = response;
    }

    private String formClientMessage() {
        return "CLIENT: " + clientRequest;
    }

    private String formServerMessage() {
        return "SERVER: " + serverResponse + "\n";
    }
}
