package de.freerider.model;

import sun.tools.tree.NewArrayExpression;

public class Customer {
    enum Status {
        New,
        InRegistration,
        Active,
        Suspended,
        Deleted
    }
    String id = null;
    String lastName;
    String firstName;
    String contact;
    Status status = Status.New;

    public Customer(String lastName, String firstName, String contact) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.contact = contact;
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
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
