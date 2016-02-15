/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 *  android.location.Location
 *  android.os.Bundle
 */
package com.google.android.gms.ads.search;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.internal.as;

public final class SearchAdRequest {
    public static final int BORDER_TYPE_DASHED = 1;
    public static final int BORDER_TYPE_DOTTED = 2;
    public static final int BORDER_TYPE_NONE = 0;
    public static final int BORDER_TYPE_SOLID = 3;
    public static final int CALL_BUTTON_COLOR_DARK = 2;
    public static final int CALL_BUTTON_COLOR_LIGHT = 0;
    public static final int CALL_BUTTON_COLOR_MEDIUM = 1;
    public static final String DEVICE_ID_EMULATOR = as.DEVICE_ID_EMULATOR;
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

    as O() {
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
    public <T extends NetworkExtras> T getNetworkExtras(Class<T> class_) {
        return this.kp.getNetworkExtras(class_);
    }

    public <T extends MediationAdapter> Bundle getNetworkExtrasBundle(Class<T> class_) {
        return this.kp.getNetworkExtrasBundle(class_);
    }

    public String getQuery() {
        return this.se;
    }

    public boolean isTestDevice(Context context) {
        return this.kp.isTestDevice(context);
    }

    public static final class Builder {
        private final as.a kq = new as.a();
        private int rR;
        private int rS;
        private int rT;
        private int rU;
        private int rV;
        private int rW;
        private int rX = 0;
        private int rY;
        private String rZ;
        private int sa;
        private String sb;
        private int sc;
        private int sd;
        private String se;

        public Builder addNetworkExtras(NetworkExtras networkExtras) {
            this.kq.a(networkExtras);
            return this;
        }

        public Builder addNetworkExtrasBundle(Class<? extends MediationAdapter> class_, Bundle bundle) {
            this.kq.a(class_, bundle);
            return this;
        }

        public Builder addTestDevice(String string2) {
            this.kq.h(string2);
            return this;
        }

        public SearchAdRequest build() {
            return new SearchAdRequest(this);
        }

        public Builder setAnchorTextColor(int n2) {
            this.rR = n2;
            return this;
        }

        public Builder setBackgroundColor(int n2) {
            this.rS = n2;
            this.rT = Color.argb((int)0, (int)0, (int)0, (int)0);
            this.rU = Color.argb((int)0, (int)0, (int)0, (int)0);
            return this;
        }

        public Builder setBackgroundGradient(int n2, int n3) {
            this.rS = Color.argb((int)0, (int)0, (int)0, (int)0);
            this.rT = n3;
            this.rU = n2;
            return this;
        }

        public Builder setBorderColor(int n2) {
            this.rV = n2;
            return this;
        }

        public Builder setBorderThickness(int n2) {
            this.rW = n2;
            return this;
        }

        public Builder setBorderType(int n2) {
            this.rX = n2;
            return this;
        }

        public Builder setCallButtonColor(int n2) {
            this.rY = n2;
            return this;
        }

        public Builder setCustomChannels(String string2) {
            this.rZ = string2;
            return this;
        }

        public Builder setDescriptionTextColor(int n2) {
            this.sa = n2;
            return this;
        }

        public Builder setFontFace(String string2) {
            this.sb = string2;
            return this;
        }

        public Builder setHeaderTextColor(int n2) {
            this.sc = n2;
            return this;
        }

        public Builder setHeaderTextSize(int n2) {
            this.sd = n2;
            return this;
        }

        public Builder setLocation(Location location) {
            this.kq.a(location);
            return this;
        }

        public Builder setQuery(String string2) {
            this.se = string2;
            return this;
        }

        public Builder tagForChildDirectedTreatment(boolean bl2) {
            this.kq.g(bl2);
            return this;
        }
    }

}

