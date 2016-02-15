/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.ads.mediation;

import java.util.Date;
import java.util.Set;

public interface MediationAdRequest {
    public static final int TAG_FOR_CHILD_DIRECTED_TREATMENT_FALSE = 0;
    public static final int TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE = 1;
    public static final int TAG_FOR_CHILD_DIRECTED_TREATMENT_UNSPECIFIED = -1;

    public Date getBirthday();

    public int getGender();

    public Set<String> getKeywords();

    public boolean isTesting();

    public int taggedForChildDirectedTreatment();
}

