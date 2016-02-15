/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.google.android.gms.internal;

import android.location.Location;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.a;
import com.google.android.gms.internal.ah;
import com.google.android.gms.internal.ak;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class by {
    public static int a(AdRequest.ErrorCode errorCode) {
        switch (.nM[errorCode.ordinal()]) {
            default: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 2;
            }
            case 4: 
        }
        return 3;
    }

    public static AdSize b(ak ak2) {
        AdSize[] arradSize = new AdSize[]{AdSize.SMART_BANNER, AdSize.BANNER, AdSize.IAB_MRECT, AdSize.IAB_BANNER, AdSize.IAB_LEADERBOARD, AdSize.IAB_WIDE_SKYSCRAPER};
        for (int i2 = 0; i2 < arradSize.length; ++i2) {
            if (arradSize[i2].getWidth() != ak2.width || arradSize[i2].getHeight() != ak2.height) continue;
            return arradSize[i2];
        }
        return new AdSize(a.a(ak2.width, ak2.height, ak2.lS));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static MediationAdRequest e(ah ah2) {
        HashSet<String> hashSet;
        if (ah2.lJ != null) {
            hashSet = new HashSet<String>(ah2.lJ);
            do {
                return new MediationAdRequest(new Date(ah2.lH), by.g(ah2.lI), hashSet, ah2.lK, ah2.lP);
                break;
            } while (true);
        }
        hashSet = null;
        return new MediationAdRequest(new Date(ah2.lH), by.g(ah2.lI), hashSet, ah2.lK, ah2.lP);
    }

    public static AdRequest.Gender g(int n2) {
        switch (n2) {
            default: {
                return AdRequest.Gender.UNKNOWN;
            }
            case 2: {
                return AdRequest.Gender.FEMALE;
            }
            case 1: 
        }
        return AdRequest.Gender.MALE;
    }

}

