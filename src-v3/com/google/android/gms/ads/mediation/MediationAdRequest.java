package com.google.android.gms.ads.mediation;

import java.util.Date;
import java.util.Set;

public interface MediationAdRequest {
    public static final int TAG_FOR_CHILD_DIRECTED_TREATMENT_FALSE = 0;
    public static final int TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE = 1;
    public static final int TAG_FOR_CHILD_DIRECTED_TREATMENT_UNSPECIFIED = -1;

    Date getBirthday();

    int getGender();

    Set<String> getKeywords();

    boolean isTesting();

    int taggedForChildDirectedTreatment();
}
