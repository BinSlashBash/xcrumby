package com.google.android.gms.internal;

import com.google.android.gms.ads.mediation.MediationAdRequest;
import java.util.Date;
import java.util.Set;

public final class bt implements MediationAdRequest {
    private final Date f66d;
    private final Set<String> f67f;
    private final boolean f68g;
    private final int lZ;
    private final int nD;

    public bt(Date date, int i, Set<String> set, boolean z, int i2) {
        this.f66d = date;
        this.lZ = i;
        this.f67f = set;
        this.f68g = z;
        this.nD = i2;
    }

    public Date getBirthday() {
        return this.f66d;
    }

    public int getGender() {
        return this.lZ;
    }

    public Set<String> getKeywords() {
        return this.f67f;
    }

    public boolean isTesting() {
        return this.f68g;
    }

    public int taggedForChildDirectedTreatment() {
        return this.nD;
    }
}
