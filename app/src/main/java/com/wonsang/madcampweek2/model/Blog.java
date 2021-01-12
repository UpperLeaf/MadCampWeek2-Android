package com.wonsang.madcampweek2.model;

import java.util.List;

public class Blog {
    public String title;
    public String profileImageUrl;
    public String bannerImage;
    public List<Post> posts;
    public String description;

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public List<Post> getPosts() {
        return posts;
    }
    public String getBannerImage() {
        return bannerImage;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
