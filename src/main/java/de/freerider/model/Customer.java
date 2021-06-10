package de.freerider.model;

import org.springframework.stereotype.Component;

public class Customer {
    public enum Status {
        New,
        InRegistration,
        Active,
        Suspended,
        Deleted
    }
    private String id = null;
    private String lastName = "";
    private String firstName = "";
    private String contact = "";
    private Status status = Status.New;

    public Customer(String lastName, String firstName, String contact) {
        setLastName(lastName);
        setFirstName(firstName);
        setContact(contact);
    }

    public String getId() {
        return this.id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getContact() {
        return this.contact;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setId(String id) {
        if(this.id == null) {
            this.id = id;
        }
        else if(id == null) {
            this.id = null;
        }
    }

    public void setLastName(String lastName) {
        if(lastName == null) {
            this.lastName = "";
        }
        else {
            this.lastName = lastName;
        }
    }

    public void setFirstName(String firstName) {
        if(firstName == null) {
            this.firstName = "";
        }
        else {
            this.firstName = firstName;
        }
    }

    public void setContact(String contact) {
        if(contact == null) {
            this.contact = "";
        }
        else {
            this.contact = contact;
        }
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
