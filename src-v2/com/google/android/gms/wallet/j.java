/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.internal.gi;
import com.google.android.gms.internal.jm;
import com.google.android.gms.internal.jo;
import com.google.android.gms.internal.js;
import com.google.android.gms.internal.ju;
import com.google.android.gms.internal.jw;
import com.google.android.gms.internal.jy;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngCreator;
import com.google.android.gms.wallet.LoyaltyWalletObject;
import java.util.ArrayList;

public class j
implements Parcelable.Creator<LoyaltyWalletObject> {
    static void a(LoyaltyWalletObject loyaltyWalletObject, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, loyaltyWalletObject.getVersionCode());
        b.a(parcel, 2, loyaltyWalletObject.eC, false);
        b.a(parcel, 3, loyaltyWalletObject.abz, false);
        b.a(parcel, 4, loyaltyWalletObject.abA, false);
        b.a(parcel, 5, loyaltyWalletObject.abB, false);
        b.a(parcel, 6, loyaltyWalletObject.abC, false);
        b.a(parcel, 7, loyaltyWalletObject.abD, false);
        b.a(parcel, 8, loyaltyWalletObject.abE, false);
        b.a(parcel, 9, loyaltyWalletObject.abF, false);
        b.a(parcel, 10, loyaltyWalletObject.abG, false);
        b.a(parcel, 11, loyaltyWalletObject.abH, false);
        b.c(parcel, 12, loyaltyWalletObject.state);
        b.b(parcel, 13, loyaltyWalletObject.abI, false);
        b.a(parcel, 14, loyaltyWalletObject.abJ, n2, false);
        b.b(parcel, 15, loyaltyWalletObject.abK, false);
        b.a(parcel, 17, loyaltyWalletObject.abM, false);
        b.a(parcel, 16, loyaltyWalletObject.abL, false);
        b.a(parcel, 19, loyaltyWalletObject.abO);
        b.b(parcel, 18, loyaltyWalletObject.abN, false);
        b.b(parcel, 21, loyaltyWalletObject.abQ, false);
        b.b(parcel, 20, loyaltyWalletObject.abP, false);
        b.a(parcel, 23, loyaltyWalletObject.abS, n2, false);
        b.b(parcel, 22, loyaltyWalletObject.abR, false);
        b.F(parcel, n3);
    }

    public LoyaltyWalletObject bf(Parcel parcel) {
        int n2 = a.o(parcel);
        int n3 = 0;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        String string8 = null;
        String string9 = null;
        String string10 = null;
        String string11 = null;
        int n4 = 0;
        ArrayList<jy> arrayList = gi.fs();
        ju ju2 = null;
        ArrayList<LatLng> arrayList2 = gi.fs();
        String string12 = null;
        String string13 = null;
        ArrayList<jm> arrayList3 = gi.fs();
        boolean bl2 = false;
        ArrayList<jw> arrayList4 = gi.fs();
        ArrayList<js> arrayList5 = gi.fs();
        ArrayList<jw> arrayList6 = gi.fs();
        jo jo2 = null;
        block25 : while (parcel.dataPosition() < n2) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    continue block25;
                }
                case 1: {
                    n3 = a.g(parcel, n5);
                    continue block25;
                }
                case 2: {
                    string2 = a.n(parcel, n5);
                    continue block25;
                }
                case 3: {
                    string3 = a.n(parcel, n5);
                    continue block25;
                }
                case 4: {
                    string4 = a.n(parcel, n5);
                    continue block25;
                }
                case 5: {
                    string5 = a.n(parcel, n5);
                    continue block25;
                }
                case 6: {
                    string6 = a.n(parcel, n5);
                    continue block25;
                }
                case 7: {
                    string7 = a.n(parcel, n5);
                    continue block25;
                }
                case 8: {
                    string8 = a.n(parcel, n5);
                    continue block25;
                }
                case 9: {
                    string9 = a.n(parcel, n5);
                    continue block25;
                }
                case 10: {
                    string10 = a.n(parcel, n5);
                    continue block25;
                }
                case 11: {
                    string11 = a.n(parcel, n5);
                    continue block25;
                }
                case 12: {
                    n4 = a.g(parcel, n5);
                    continue block25;
                }
                case 13: {
                    arrayList = a.c(parcel, n5, jy.CREATOR);
                    continue block25;
                }
                case 14: {
                    ju2 = a.a(parcel, n5, ju.CREATOR);
                    continue block25;
                }
                case 15: {
                    arrayList2 = a.c(parcel, n5, LatLng.CREATOR);
                    continue block25;
                }
                case 17: {
                    string13 = a.n(parcel, n5);
                    continue block25;
                }
                case 16: {
                    string12 = a.n(parcel, n5);
                    continue block25;
                }
                case 19: {
                    bl2 = a.c(parcel, n5);
                    continue block25;
                }
                case 18: {
                    arrayList3 = a.c(parcel, n5, jm.CREATOR);
                    continue block25;
                }
                case 21: {
                    arrayList5 = a.c(parcel, n5, js.CREATOR);
                    continue block25;
                }
                case 20: {
                    arrayList4 = a.c(parcel, n5, jw.CREATOR);
                    continue block25;
                }
                case 23: {
                    jo2 = a.a(parcel, n5, jo.CREATOR);
                    continue block25;
                }
                case 22: 
            }
            arrayList6 = a.c(parcel, n5, jw.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new a.a("Overread allowed size end=" + n2, parcel);
        }
        return new LoyaltyWalletObject(n3, string2, string3, string4, string5, string6, string7, string8, string9, string10, string11, n4, arrayList, ju2, arrayList2, string12, string13, arrayList3, bl2, arrayList4, arrayList5, arrayList6, jo2);
    }

    public LoyaltyWalletObject[] cr(int n2) {
        return new LoyaltyWalletObject[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.bf(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.cr(n2);
    }
}

