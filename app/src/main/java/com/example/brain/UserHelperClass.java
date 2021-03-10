package com.example.brain;

public class UserHelperClass {
    String email, username, contact, password;

    public UserHelperClass() {

    }

    public UserHelperClass(String email, String username, String contact, String password) {
        this.email = email;
        this.username = username;
        this.contact = contact;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
