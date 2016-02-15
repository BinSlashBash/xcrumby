/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib;

import java.util.ArrayList;

public class ImageComment {
    private ArrayList<ImageComment> children;
    private String markdown;
    private String message;
    private String username;

    public ImageComment() {
    }

    public ImageComment(String string2, String string3, ArrayList<ImageComment> arrayList) {
        this.username = string2;
        this.message = string3;
        this.children = arrayList;
    }

    public String getMessage() {
        return this.message;
    }

    public String getUsername() {
        return this.username;
    }

    public void setChildren(ArrayList<ImageComment> arrayList) {
        this.children = arrayList;
    }

    public void setMessage(String string2) {
        this.message = string2;
    }

    public void setUsername(String string2) {
        this.username = string2;
    }
}

