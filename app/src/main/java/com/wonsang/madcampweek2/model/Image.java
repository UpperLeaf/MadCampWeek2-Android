package com.wonsang.madcampweek2.model;

import android.graphics.Bitmap;

public class Image {
    private String title;
    private byte[] value;

    public Image(String title, byte[] value){
        this.value = value;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public byte[] getValue() {
        return value;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setValue(byte[] value) {
        this.value = value;
    }
}
