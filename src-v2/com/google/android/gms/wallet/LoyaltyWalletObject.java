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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.gi;
import com.google.android.gms.internal.jm;
import com.google.android.gms.internal.jo;
import com.google.android.gms.internal.js;
import com.google.android.gms.internal.ju;
import com.google.android.gms.internal.jw;
import com.google.android.gms.internal.jy;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.wallet.j;
import java.util.ArrayList;

public final class LoyaltyWalletObject
implements SafeParcelable {
    public static final Parcelable.Creator<LoyaltyWalletObject> CREATOR = new j();
    String abA;
    String abB;
    String abC;
    String abD;
    String abE;
    String abF;
    String abG;
    String abH;
    ArrayList<jy> abI;
    ju abJ;
    ArrayList<LatLng> abK;
    String abL;
    String abM;
    ArrayList<jm> abN;
    boolean abO;
    ArrayList<jw> abP;
    ArrayList<js> abQ;
    ArrayList<jw> abR;
    jo abS;
    String abz;
    String eC;
    int state;
    private final int xH;

    LoyaltyWalletObject() {
        this.xH = 4;
        this.abI = gi.fs();
        this.abK = gi.fs();
        this.abN = gi.fs();
        this.abP = gi.fs();
        this.abQ = gi.fs();
        this.abR = gi.fs();
    }

    LoyaltyWalletObject(int n2, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, int n3, ArrayList<jy> arrayList, ju ju2, ArrayList<LatLng> arrayList2, String string12, String string13, ArrayList<jm> arrayList3, boolean bl2, ArrayList<jw> arrayList4, ArrayList<js> arrayList5, ArrayList<jw> arrayList6, jo jo2) {
        this.xH = n2;
        this.eC = string2;
        this.abz = string3;
        this.abA = string4;
        this.abB = string5;
        this.abC = string6;
        this.abD = string7;
        this.abE = string8;
        this.abF = string9;
        this.abG = string10;
        this.abH = string11;
        this.state = n3;
        this.abI = arrayList;
        this.abJ = ju2;
        this.abK = arrayList2;
        this.abL = string12;
        this.abM = string13;
        this.abN = arrayList3;
        this.abO = bl2;
        this.abP = arrayList4;
        this.abQ = arrayList5;
        this.abR = arrayList6;
        this.abS = jo2;
    }

    public int describeContents() {
        return 0;
    }

    public String getAccountId() {
        return this.abz;
    }

    public String getAccountName() {
        return this.abC;
    }

    public String getBarcodeAlternateText() {
        return this.abD;
    }

    public String getBarcodeType() {
        return this.abE;
    }

    public String getBarcodeValue() {
        return this.abF;
    }

    public String getId() {
        return this.eC;
    }

    public String getIssuerName() {
        return this.abA;
    }

    public String getProgramName() {
        return this.abB;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        j.a(this, parcel, n2);
    }
}

