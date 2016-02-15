package com.crumby.impl.derpibooru;

import java.util.ArrayList;

public class DerpibooruAttributes {
    ArrayList<String> favoritedBy;

    public DerpibooruAttributes(ArrayList<String> favoritedBy) {
        this.favoritedBy = favoritedBy;
    }

    public ArrayList<String> getFavoritedBy() {
        return this.favoritedBy;
    }
}
