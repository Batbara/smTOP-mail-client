package by.bsuir.barbarossa.entity;

import java.io.Serializable;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class User implements Serializable {
    private final static String CREDENTIALS = "\000%s\000%s";
    private String userName;
    private String password;
    private Address eMail;
    private InetAddress localHostName;

    public User() {
    }

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

    public String getEncodedCredentials() {

        return encode(String.format(CREDENTIALS, userName, password));
    }

    public String getEncodedUserName() {
        return encode(userName);
    }

    public String getPassword() {
        return password;
    }

    public String getEncodedPassword() {
        return encode(password);
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

    public String getLocalHostAddress() {
        return localHostName.getHostAddress();
    }

    public void setLocalHostName(InetAddress localHostName) {
        this.localHostName = localHostName;
    }

    private String encode(String strToEncode) {
        //   return DatatypeConverter.printBase64Binary(strToEncode.getBytes());
        return Base64.getEncoder().encodeToString(strToEncode.getBytes(StandardCharsets.UTF_8));
    }
}
