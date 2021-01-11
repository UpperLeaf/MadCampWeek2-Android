package com.wonsang.madcampweek2.model;

public class Contact {
    private String name;
    private String email;
    private int id;

    public Contact(String name, String email){
        this.name = name;
        this.email = email;
    }

    public Contact(String name, String email, int id){
        this(name, email);
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getId() {
        return id;
    }
}