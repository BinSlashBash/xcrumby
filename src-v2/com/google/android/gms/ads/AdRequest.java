/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.location.Location
 *  android.os.Bundle
 */
package com.google.android.gms.ads;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.internal.as;
import com.google.android.gms.internal.fq;
import java.util.Date;
import java.util.Set;

public final class AdRequest {
    public static final String DEVICE_ID_EMULATOR = as.DEVICE_ID_EMULATOR;
    public static final int ERROR_CODE_INTERNAL_ERROR = 0;
    public static final int ERROR_CODE_INVALID_REQUEST = 1;
    public static final int ERROR_CODE_NETWORK_ERROR = 2;
    public static final int ERROR_CODE_NO_FILL = 3;
    public static final int GENDER_FEMALE = 2;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_UNKNOWN = 0;
    public static final int MAX_CONTENT_URL_LENGTH = 512;
    private final as kp;

    private AdRequest(Builder builder) {
        this.kp = new as(builder.kq);
    }

    as O() {
        return this.kp;
    }

    public Date getBirthday() {
        return this.kp.getBirthday();
    }

    public String getContentUrl() {
        return this.kp.getContentUrl();
    }

    public int getGender() {
        return this.kp.getGender();
    }

    public Set<String> getKeywords() {
        return this.kp.getKeywords();
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

    public boolean isTestDevice(Context context) {
        return this.kp.isTestDevice(context);
    }

    public static final class Builder {
        private final as.a kq = new as.a();

        public Builder addKeyword(String string2) {
            this.kq.g(string2);
            return this;
        }

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

        public AdRequest build() {
            return new AdRequest(this);
        }

        public Builder setBirthday(Date date) {
            this.kq.a(date);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder setContentUrl(String string2) {
            fq.b(string2, (Object)"Content URL must be non-null.");
            fq.b(string2, (Object)"Content URL must be non-empty.");
            boolean bl2 = string2.length() <= 512;
            fq.a(bl2, "Content URL must not exceed %d in length.  Provided length was %d.", 512, string2.length());
            this.kq.i(string2);
            return this;
        }

        public Builder setGender(int n2) {
            this.kq.d(n2);
            return this;
        }

        public Builder setLocation(Location location) {
            this.kq.a(location);
            return this;
        }

        public Builder tagForChildDirectedTreatment(boolean bl2) {
            this.kq.g(bl2);
            return this;
        }
    }

}

