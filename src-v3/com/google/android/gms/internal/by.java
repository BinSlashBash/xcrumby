package com.google.android.gms.internal;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdRequest.Gender;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.C0194a;
import java.util.Date;
import java.util.HashSet;

public final class by {

    /* renamed from: com.google.android.gms.internal.by.1 */
    static /* synthetic */ class C03381 {
        static final /* synthetic */ int[] nL;
        static final /* synthetic */ int[] nM;

        static {
            nM = new int[ErrorCode.values().length];
            try {
                nM[ErrorCode.INTERNAL_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                nM[ErrorCode.INVALID_REQUEST.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                nM[ErrorCode.NETWORK_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                nM[ErrorCode.NO_FILL.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            nL = new int[Gender.values().length];
            try {
                nL[Gender.FEMALE.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                nL[Gender.MALE.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                nL[Gender.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static int m674a(ErrorCode errorCode) {
        switch (C03381.nM[errorCode.ordinal()]) {
            case Std.STD_URL /*2*/:
                return 1;
            case Std.STD_URI /*3*/:
                return 2;
            case Std.STD_CLASS /*4*/:
                return 3;
            default:
                return 0;
        }
    }

    public static AdSize m675b(ak akVar) {
        int i = 0;
        AdSize[] adSizeArr = new AdSize[]{AdSize.SMART_BANNER, AdSize.BANNER, AdSize.IAB_MRECT, AdSize.IAB_BANNER, AdSize.IAB_LEADERBOARD, AdSize.IAB_WIDE_SKYSCRAPER};
        while (i < adSizeArr.length) {
            if (adSizeArr[i].getWidth() == akVar.width && adSizeArr[i].getHeight() == akVar.height) {
                return adSizeArr[i];
            }
            i++;
        }
        return new AdSize(C0194a.m5a(akVar.width, akVar.height, akVar.lS));
    }

    public static MediationAdRequest m676e(ah ahVar) {
        return new MediationAdRequest(new Date(ahVar.lH), m677g(ahVar.lI), ahVar.lJ != null ? new HashSet(ahVar.lJ) : null, ahVar.lK, ahVar.lP);
    }

    public static Gender m677g(int i) {
        switch (i) {
            case Std.STD_FILE /*1*/:
                return Gender.MALE;
            case Std.STD_URL /*2*/:
                return Gender.FEMALE;
            default:
                return Gender.UNKNOWN;
        }
    }
}
