/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.internal;

import com.google.android.gms.ads.mediation.MediationAdRequest;
import java.util.Date;
import java.util.Set;

public final class bt
implements MediationAdRequest {
    private final Date d;
    private final Set<String> f;
    private final boolean g;
    private final int lZ;
    private final int nD;

    public bt(Date date, int n2, Set<String> set, boolean bl2, int n3) {
        this.d = date;
        this.lZ = n2;
        this.f = set;
        this.g = bl2;
        this.nD = n3;
    }

    @Override
    public Date getBirthday() {
        return this.d;
    }

    @Override
    public int getGender() {
        return this.lZ;
    }

    @Override
    public Set<String> getKeywords() {
        return this.f;
    }

    @Override
    public boolean isTesting() {
        return this.g;
    }

    @Override
    public int taggedForChildDirectedTreatment() {
        return this.nD;
    }
}

