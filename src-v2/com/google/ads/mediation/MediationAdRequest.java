/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.google.ads.mediation;

import android.location.Location;
import com.google.ads.AdRequest;
import java.util.Date;
import java.util.Set;

@Deprecated
public final class MediationAdRequest {
    private final Date d;
    private final AdRequest.Gender e;
    private final Set<String> f;
    private final boolean g;
    private final Location h;

    public MediationAdRequest(Date date, AdRequest.Gender gender, Set<String> set, boolean bl2, Location location) {
        this.d = date;
        this.e = gender;
        this.f = set;
        this.g = bl2;
        this.h = location;
    }

    public Integer getAgeInYears() {
        return null;
    }

    public Date getBirthday() {
        return this.d;
    }

    public AdRequest.Gender getGender() {
        return this.e;
    }

    public Set<String> getKeywords() {
        return this.f;
    }

    public Location getLocation() {
        return this.h;
    }

    public boolean isTesting() {
        return this.g;
    }
}

