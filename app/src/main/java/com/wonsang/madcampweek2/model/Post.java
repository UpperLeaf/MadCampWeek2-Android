package com.wonsang.madcampweek2.model;

public class Post {
    public int id;
    public String title;
    public String content;

    public Post(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Post(String title, String content, int id){
        this(title, content);
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}