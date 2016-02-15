package com.google.android.gms.ads.search;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.internal.as;
import com.google.android.gms.internal.as.C0324a;

public final class SearchAdRequest {
    public static final int BORDER_TYPE_DASHED = 1;
    public static final int BORDER_TYPE_DOTTED = 2;
    public static final int BORDER_TYPE_NONE = 0;
    public static final int BORDER_TYPE_SOLID = 3;
    public static final int CALL_BUTTON_COLOR_DARK = 2;
    public static final int CALL_BUTTON_COLOR_LIGHT = 0;
    public static final int CALL_BUTTON_COLOR_MEDIUM = 1;
    public static final String DEVICE_ID_EMULATOR;
    public static final int ERROR_CODE_INTERNAL_ERROR = 0;
    public static final int ERROR_CODE_INVALID_REQUEST = 1;
    public static final int ERROR_CODE_NETWORK_ERROR = 2;
    public static final int ERROR_CODE_NO_FILL = 3;
    private final as kp;
    private final int rR;
    private final int rS;
    private final int rT;
    private final int rU;
    private final int rV;
    private final int rW;
    private final int rX;
    private final int rY;
    private final String rZ;
    private final int sa;
    private final String sb;
    private final int sc;
    private final int sd;
    private final String se;

    public static final class Builder {
        private final C0324a kq;
        private int rR;
        private int rS;
        private int rT;
        private int rU;
        private int rV;
        private int rW;
        private int rX;
        private int rY;
        private String rZ;
        private int sa;
        private String sb;
        private int sc;
        private int sd;
        private String se;

        public Builder() {
            this.kq = new C0324a();
            this.rX = SearchAdRequest.ERROR_CODE_INTERNAL_ERROR;
        }

        public Builder addNetworkExtras(NetworkExtras networkExtras) {
            this.kq.m639a(networkExtras);
            return this;
        }

        public Builder addNetworkExtrasBundle(Class<? extends MediationAdapter> adapterClass, Bundle networkExtras) {
            this.kq.m640a(adapterClass, networkExtras);
            return this;
        }

        public Builder addTestDevice(String deviceId) {
            this.kq.m646h(deviceId);
            return this;
        }

        public SearchAdRequest build() {
            return new SearchAdRequest();
        }

        public Builder setAnchorTextColor(int anchorTextColor) {
            this.rR = anchorTextColor;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.rS = backgroundColor;
            this.rT = Color.argb(SearchAdRequest.ERROR_CODE_INTERNAL_ERROR, SearchAdRequest.ERROR_CODE_INTERNAL_ERROR, SearchAdRequest.ERROR_CODE_INTERNAL_ERROR, SearchAdRequest.ERROR_CODE_INTERNAL_ERROR);
            this.rU = Color.argb(SearchAdRequest.ERROR_CODE_INTERNAL_ERROR, SearchAdRequest.ERROR_CODE_INTERNAL_ERROR, SearchAdRequest.ERROR_CODE_INTERNAL_ERROR, SearchAdRequest.ERROR_CODE_INTERNAL_ERROR);
            return this;
        }

        public Builder setBackgroundGradient(int top, int bottom) {
            this.rS = Color.argb(SearchAdRequest.ERROR_CODE_INTERNAL_ERROR, SearchAdRequest.ERROR_CODE_INTERNAL_ERROR, SearchAdRequest.ERROR_CODE_INTERNAL_ERROR, SearchAdRequest.ERROR_CODE_INTERNAL_ERROR);
            this.rT = bottom;
            this.rU = top;
            return this;
        }

        public Builder setBorderColor(int borderColor) {
            this.rV = borderColor;
            return this;
        }

        public Builder setBorderThickness(int borderThickness) {
            this.rW = borderThickness;
            return this;
        }

        public Builder setBorderType(int borderType) {
            this.rX = borderType;
            return this;
        }

        public Builder setCallButtonColor(int callButtonColor) {
            this.rY = callButtonColor;
            return this;
        }

        public Builder setCustomChannels(String channelIds) {
            this.rZ = channelIds;
            return this;
        }

        public Builder setDescriptionTextColor(int descriptionTextColor) {
            this.sa = descriptionTextColor;
            return this;
        }

        public Builder setFontFace(String fontFace) {
            this.sb = fontFace;
            return this;
        }

        public Builder setHeaderTextColor(int headerTextColor) {
            this.sc = headerTextColor;
            return this;
        }

        public Builder setHeaderTextSize(int headerTextSize) {
            this.sd = headerTextSize;
            return this;
        }

        public Builder setLocation(Location location) {
            this.kq.m638a(location);
            return this;
        }

        public Builder setQuery(String query) {
            this.se = query;
            return this;
        }

        public Builder tagForChildDirectedTreatment(boolean tagForChildDirectedTreatment) {
            this.kq.m645g(tagForChildDirectedTreatment);
            return this;
        }
    }

    static {
        DEVICE_ID_EMULATOR = as.DEVICE_ID_EMULATOR;
    }

    private SearchAdRequest(Builder builder) {
        this.rR = builder.rR;
        this.rS = builder.rS;
        this.rT = builder.rT;
        this.rU = builder.rU;
        this.rV = builder.rV;
        this.rW = builder.rW;
        this.rX = builder.rX;
        this.rY = builder.rY;
        this.rZ = builder.rZ;
        this.sa = builder.sa;
        this.sb = builder.sb;
        this.sc = builder.sc;
        this.sd = builder.sd;
        this.se = builder.se;
        this.kp = new as(builder.kq, this);
    }

    as m25O() {
        return this.kp;
    }

    public int getAnchorTextColor() {
        return this.rR;
    }

    public int getBackgroundColor() {
        return this.rS;
    }

    public int getBackgroundGradientBottom() {
        return this.rT;
    }

    public int getBackgroundGradientTop() {
        return this.rU;
    }

    public int getBorderColor() {
        return this.rV;
    }

    public int getBorderThickness() {
        return this.rW;
    }

    public int getBorderType() {
        return this.rX;
    }

    public int getCallButtonColor() {
        return this.rY;
    }

    public String getCustomChannels() {
        return this.rZ;
    }

    public int getDescriptionTextColor() {
        return this.sa;
    }

    public String getFontFace() {
        return this.sb;
    }

    public int getHeaderTextColor() {
        return this.sc;
    }

    public int getHeaderTextSize() {
        return this.sd;
    }

    public Location getLocation() {
        return this.kp.getLocation();
    }

    @Deprecated
    public <T extends NetworkExtras> T getNetworkExtras(Class<T> networkExtrasClass) {
        return this.kp.getNetworkExtras(networkExtrasClass);
    }

    public <T extends MediationAdapter> Bundle getNetworkExtrasBundle(Class<T> adapterClass) {
        return this.kp.getNetworkExtrasBundle(adapterClass);
    }

    public String getQuery() {
        return this.se;
    }

    public boolean isTestDevice(Context context) {
        return this.kp.isTestDevice(context);
    }
}
