/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;

public class ef
implements Parcelable {
    @Deprecated
    public static final Parcelable.Creator<ef> CREATOR = new Parcelable.Creator<ef>(){

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.i(parcel);
        }

        @Deprecated
        public ef i(Parcel parcel) {
            return new ef(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.u(n2);
        }

        @Deprecated
        public ef[] u(int n2) {
            return new ef[n2];
        }
    };
    private String mValue;
    private String wp;
    private String wq;

    @Deprecated
    public ef() {
    }

    @Deprecated
    ef(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public ef(String string2, String string3, String string4) {
        this.wp = string2;
        this.wq = string3;
        this.mValue = string4;
    }

    @Deprecated
    private void readFromParcel(Parcel parcel) {
        this.wp = parcel.readString();
        this.wq = parcel.readString();
        this.mValue = parcel.readString();
    }

    @Deprecated
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.wp;
    }

    public String getValue() {
        return this.mValue;
    }

    @Deprecated
    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeString(this.wp);
        parcel.writeString(this.wq);
        parcel.writeString(this.mValue);
    }

}

