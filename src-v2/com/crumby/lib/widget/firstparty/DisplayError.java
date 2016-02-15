/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.widget.firstparty;

public class DisplayError {
    private static final String CHECK_CONNECTION = "Check your internet connection.";
    private static final String CHECK_URL = "See if the URL you inputted is correct";
    public static final DisplayError COULD_NOT_AUTHENTICATE_USER;
    public static final DisplayError COULD_NOT_DOWNLOAD_IMAGE;
    public static final DisplayError COULD_NOT_FETCH_IMAGE_METADATA;
    public static final DisplayError EMPTY_GALLERY;
    private static final String FILE_A_REPORT = "Should this url be working? If you think it should be, please let me know by clicking the button below.";
    public static final DisplayError GALLERY_NOT_LOADING;
    public static final DisplayError IMAGE_NOT_LOADING;
    public static final DisplayError IMAGE_NOT_RENDERING;
    private static final String NO_SOLUTIONS;
    private static final String PROTIP = "PROTIP: Check out the forum to see what websites Crumby willl support next! Just click the \"Help\" button in the right side bar.";
    private static final String REFRESH_PAGE = "Try refreshing the page.";
    private static final String TRY_DIRECT_DOWNLOADING = "Try saving this image and then viewing it through another photo viewer.";
    private static final String UNKNOWN = "If the exact error is unknown, you may either not have permissions to access this content or your device does not have enough memory at the moment.";
    public static final DisplayError UNSUPPORTED_URL;
    public static final DisplayError VIEWPAGER_CANNOT_FETCH;
    String[] details;
    private int id;
    String main;
    String reason;
    boolean showBackground;
    boolean showDismiss;

    static {
        NO_SOLUTIONS = null;
        IMAGE_NOT_LOADING = new DisplayError(1, "Cannot load image url", "Check your internet connection.", true, false, "Check your internet connection.", "See if the URL you inputted is correct");
        IMAGE_NOT_RENDERING = new DisplayError(2, "Cannot render image", "Unknown error.", true, true, "See if the URL you inputted is correct", "Try refreshing the page.", "Try saving this image and then viewing it through another photo viewer.");
        GALLERY_NOT_LOADING = new DisplayError(3, "Cannot load gallery url", "", true, true, "Check your internet connection.", "See if the URL you inputted is correct");
        EMPTY_GALLERY = new DisplayError(4, "This gallery is empty.", "The website may have removed them.", true, true, NO_SOLUTIONS);
        UNSUPPORTED_URL = new DisplayError(5, "Url is not supported.", "", false, false, "PROTIP: Check out the forum to see what websites Crumby willl support next! Just click the \"Help\" button in the right side bar.");
        COULD_NOT_DOWNLOAD_IMAGE = new DisplayError(6, "Image could not be downloaded.", "", false, false, "Check your internet connection.", "See if the URL you inputted is correct", "Try refreshing the page.");
        COULD_NOT_FETCH_IMAGE_METADATA = new DisplayError(7, "Metadata not found", "", false, false, NO_SOLUTIONS);
        VIEWPAGER_CANNOT_FETCH = new DisplayError(8, "View pager cannot fetch next images", "", false, false, "Try refreshing the page.", "Check your internet connection.");
        COULD_NOT_AUTHENTICATE_USER = new DisplayError(9, "You need to be logged in to view this page", "", true, false, "Check your internet connection.");
    }

    /* varargs */ DisplayError(int n2, String string2, String string3, boolean bl2, boolean bl3, String ... arrstring) {
        this(n2, string2, string3, arrstring);
        this.showDismiss = bl3;
        this.showBackground = bl2;
    }

    /* varargs */ DisplayError(int n2, String string2, String string3, String ... arrstring) {
        this.id = n2;
        this.main = string2;
        this.reason = string3;
        this.details = arrstring;
        this.showDismiss = true;
        this.showBackground = true;
    }

    public int getErrorCode() {
        return this.id;
    }
}

