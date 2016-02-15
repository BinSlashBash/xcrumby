package com.crumby.lib;

import java.util.ArrayList;

public class ImageComment {
    private ArrayList<ImageComment> children;
    private String markdown;
    private String message;
    private String username;

    public ImageComment(String username, String message, ArrayList<ImageComment> children) {
        this.username = username;
        this.message = message;
        this.children = children;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChildren(ArrayList<ImageComment> children) {
        this.children = children;
    }
}
