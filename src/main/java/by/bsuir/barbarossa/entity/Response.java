package by.bsuir.barbarossa.entity;

import java.io.Serializable;

public class Response implements Serializable{
    private String response;
    public Response(){}
    public Response(String response){
        this.response = response;
    }
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
