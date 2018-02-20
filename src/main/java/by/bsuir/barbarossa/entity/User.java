package by.bsuir.barbarossa.entity;

import java.io.Serializable;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class User implements Serializable{
    private String userName;
    private String password;
    private Address eMail;
    private InetAddress localHostName;

    public User(){}

    public User(String userName, String password, InetAddress localHostName) {
        this.userName = userName;
        this.password = password;
        this.localHostName = localHostName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailAddress() {
        return eMail.getMailAddress();
    }

    public void seteMail(Address eMail) {
        this.eMail = eMail;
    }

    public String getLocalHostName() {
        return localHostName.getHostAddress();
    }

    public void setLocalHostName(InetAddress localHostName) {
        this.localHostName = localHostName;
    }
}
