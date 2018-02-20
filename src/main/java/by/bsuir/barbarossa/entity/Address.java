package by.bsuir.barbarossa.entity;

import java.io.Serializable;

public class Address implements Serializable {
    private String mailAddress;

    public Address() {
    }

    public Address(String mailAddress){
        this.mailAddress = mailAddress;
    }


    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

}
