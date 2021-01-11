package com.wonsang.madcampweek2.model;

import java.util.List;

public class Blog {
    public String email;
    public Image profile;
    public Image banner;
    public List<Post> posts;
//    public List<Contact> contacts;
    public String description;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public List<Contact> getContacts() {
//        return contacts;
//    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

//    public void setContacts(List<Contact> contacts) {
//        this.contacts = contacts;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
