package com.wonsang.madcampweek2.model;

import android.graphics.Bitmap;

public class Image {
    private String title;
    private String value;

    public Image(String title, String value){
        this.value = value;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public String getValue() {
        return value;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
