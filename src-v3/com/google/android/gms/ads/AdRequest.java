package com.google.android.gms.ads;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.internal.as;
import com.google.android.gms.internal.as.C0324a;
import com.google.android.gms.internal.fq;
import java.util.Date;
import java.util.Set;

public final class AdRequest {
    public static final String DEVICE_ID_EMULATOR;
    public static final int ERROR_CODE_INTERNAL_ERROR = 0;
    public static final int ERROR_CODE_INVALID_REQUEST = 1;
    public static final int ERROR_CODE_NETWORK_ERROR = 2;
    public static final int ERROR_CODE_NO_FILL = 3;
    public static final int GENDER_FEMALE = 2;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_UNKNOWN = 0;
    public static final int MAX_CONTENT_URL_LENGTH = 512;
    private final as kp;

    public static final class Builder {
        private final C0324a kq;

        public Builder() {
            this.kq = new C0324a();
        }

        public Builder addKeyword(String keyword) {
            this.kq.m644g(keyword);
            return this;
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

        public AdRequest build() {
            return new AdRequest();
        }

        public Builder setBirthday(Date birthday) {
            this.kq.m641a(birthday);
            return this;
        }

        public Builder setContentUrl(String contentUrl) {
            fq.m982b((Object) contentUrl, (Object) "Content URL must be non-null.");
            fq.m983b(contentUrl, (Object) "Content URL must be non-empty.");
            boolean z = contentUrl.length() <= AdRequest.MAX_CONTENT_URL_LENGTH;
            Object[] objArr = new Object[AdRequest.GENDER_FEMALE];
            objArr[AdRequest.GENDER_UNKNOWN] = Integer.valueOf(AdRequest.MAX_CONTENT_URL_LENGTH);
            objArr[AdRequest.GENDER_MALE] = Integer.valueOf(contentUrl.length());
            fq.m981a(z, "Content URL must not exceed %d in length.  Provided length was %d.", objArr);
            this.kq.m647i(contentUrl);
            return this;
        }

        public Builder setGender(int gender) {
            this.kq.m642d(gender);
            return this;
        }

        public Builder setLocation(Location location) {
            this.kq.m638a(location);
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

    private AdRequest(Builder builder) {
        this.kp = new as(builder.kq);
    }

    as m4O() {
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
    public <T extends NetworkExtras> T getNetworkExtras(Class<T> networkExtrasClass) {
        return this.kp.getNetworkExtras(networkExtrasClass);
    }

    public <T extends MediationAdapter> Bundle getNetworkExtrasBundle(Class<T> adapterClass) {
        return this.kp.getNetworkExtrasBundle(adapterClass);
    }

    public boolean isTestDevice(Context context) {
        return this.kp.isTestDevice(context);
    }
}
