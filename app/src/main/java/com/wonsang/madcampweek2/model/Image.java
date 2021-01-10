package com.wonsang.madcampweek2.model;

import android.graphics.Bitmap;

public class Image {
    private int id;
    private String title;
    private byte[] value;

    public Image(String title, byte[] value, int id){
        this.value = value;
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public byte[] getValue() {
        return value;
    }
    public int getId() {
        return id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setValue(byte[] value) {
        this.value = value;
    }

}
