package com.crumby.lib.widget.firstparty;

import com.crumby.impl.crumby.UnsupportedUrlFragment;

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
        IMAGE_NOT_LOADING = new DisplayError(1, "Cannot load image url", CHECK_CONNECTION, true, false, CHECK_CONNECTION, CHECK_URL);
        IMAGE_NOT_RENDERING = new DisplayError(2, "Cannot render image", "Unknown error.", true, true, CHECK_URL, REFRESH_PAGE, TRY_DIRECT_DOWNLOADING);
        int i = 3;
        GALLERY_NOT_LOADING = new DisplayError(i, "Cannot load gallery url", UnsupportedUrlFragment.DISPLAY_NAME, true, true, CHECK_CONNECTION, CHECK_URL);
        EMPTY_GALLERY = new DisplayError(4, "This gallery is empty.", "The website may have removed them.", true, true, NO_SOLUTIONS);
        UNSUPPORTED_URL = new DisplayError(5, "Url is not supported.", UnsupportedUrlFragment.DISPLAY_NAME, false, false, PROTIP);
        COULD_NOT_DOWNLOAD_IMAGE = new DisplayError(6, "Image could not be downloaded.", UnsupportedUrlFragment.DISPLAY_NAME, false, false, CHECK_CONNECTION, CHECK_URL, REFRESH_PAGE);
        COULD_NOT_FETCH_IMAGE_METADATA = new DisplayError(7, "Metadata not found", UnsupportedUrlFragment.DISPLAY_NAME, false, false, NO_SOLUTIONS);
        int i2 = 8;
        VIEWPAGER_CANNOT_FETCH = new DisplayError(i2, "View pager cannot fetch next images", UnsupportedUrlFragment.DISPLAY_NAME, false, false, REFRESH_PAGE, CHECK_CONNECTION);
        COULD_NOT_AUTHENTICATE_USER = new DisplayError(9, "You need to be logged in to view this page", UnsupportedUrlFragment.DISPLAY_NAME, true, false, CHECK_CONNECTION);
    }

    DisplayError(int id, String main, String reason, boolean showBackground, boolean showDismiss, String... details) {
        this(id, main, reason, details);
        this.showDismiss = showDismiss;
        this.showBackground = showBackground;
    }

    DisplayError(int id, String main, String reason, String... details) {
        this.id = id;
        this.main = main;
        this.reason = reason;
        this.details = details;
        this.showDismiss = true;
        this.showBackground = true;
    }

    public int getErrorCode() {
        return this.id;
    }
}
