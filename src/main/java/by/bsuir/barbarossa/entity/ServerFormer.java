package by.bsuir.barbarossa.entity;

public class ServerFormer {
    public Server formServerInfo(String serverHost, String serverPort) {
        Server server = new Server();
        server.setHost(serverHost);
        server.setPort(Integer.parseInt(serverPort));
        return server;
    }
}
