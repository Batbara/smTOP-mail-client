package by.bsuir.barbarossa.entity;

import java.io.Serializable;

public class Envelope implements Serializable {
    private User sender;
    private Address recipientAddresses;

    public Envelope() {
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Address getRecipientAddresses() {
        return recipientAddresses;
    }

    public void setRecipientAddresses(Address recipientAddresses) {
        this.recipientAddresses = recipientAddresses;
    }
}
