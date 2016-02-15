package com.google.ads.mediation;

import android.location.Location;
import com.google.ads.AdRequest.Gender;
import java.util.Date;
import java.util.Set;

@Deprecated
public final class MediationAdRequest {
    private final Date f12d;
    private final Gender f13e;
    private final Set<String> f14f;
    private final boolean f15g;
    private final Location f16h;

    public MediationAdRequest(Date birthday, Gender gender, Set<String> keywords, boolean isTesting, Location location) {
        this.f12d = birthday;
        this.f13e = gender;
        this.f14f = keywords;
        this.f15g = isTesting;
        this.f16h = location;
    }

    public Integer getAgeInYears() {
        return null;
    }

    public Date getBirthday() {
        return this.f12d;
    }

    public Gender getGender() {
        return this.f13e;
    }

    public Set<String> getKeywords() {
        return this.f14f;
    }

    public Location getLocation() {
        return this.f16h;
    }

    public boolean isTesting() {
        return this.f15g;
    }
}
