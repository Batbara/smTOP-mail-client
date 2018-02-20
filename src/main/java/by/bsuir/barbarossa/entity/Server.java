package by.bsuir.barbarossa.entity;

import java.io.Serializable;

public class Server implements Serializable{
    private String host;
    private int port;
    public Server(){}

    public Server(String host, int port){
        this.host = host;
        this.port = port;
    }
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
