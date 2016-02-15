/*
 * Decompiled with CFR 0_110.
 */
package com.crumby;

public enum AnalyticsCategories {
    OMNIBAR("Omnibar"),
    DOWNLOADS("Downloads"),
    GALLERY_IMAGE("Gallery Image"),
    GALLERY_GRID("Gallery Grid"),
    NAVIGATION("Navigation"),
    MISCELLANEOUS("Miscellaneous"),
    MAIN_MENU("Main Menu"),
    ROUTING("Routing"),
    TUTORIAL("Tutorial"),
    ACCOUNT("Account"),
    SHARING("Sharing"),
    SETTINGS("Settings"),
    UNIVERSAL("Universal");
    
    private String name;

    private AnalyticsCategories(String string3) {
        this.name = string3;
    }

    public String getName() {
        return this.name;
    }
}

