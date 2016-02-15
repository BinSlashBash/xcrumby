/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.games.multiplayer.realtime;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.internal.fq;

public final class RealTimeMessage
implements Parcelable {
    public static final Parcelable.Creator<RealTimeMessage> CREATOR = new Parcelable.Creator<RealTimeMessage>(){

        public RealTimeMessage aw(Parcel parcel) {
            return new RealTimeMessage(parcel);
        }

        public RealTimeMessage[] bp(int n2) {
            return new RealTimeMessage[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.aw(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.bp(n2);
        }
    };
    public static final int RELIABLE = 1;
    public static final int UNRELIABLE = 0;
    private final String MH;
    private final byte[] MI;
    private final int MJ;

    private RealTimeMessage(Parcel parcel) {
        this(parcel.readString(), parcel.createByteArray(), parcel.readInt());
    }

    public RealTimeMessage(String string2, byte[] arrby, int n2) {
        this.MH = fq.f(string2);
        this.MI = (byte[])fq.f(arrby).clone();
        this.MJ = n2;
    }

    public int describeContents() {
        return 0;
    }

    public byte[] getMessageData() {
        return this.MI;
    }

    public String getSenderParticipantId() {
        return this.MH;
    }

    public boolean isReliable() {
        if (this.MJ == 1) {
            return true;
        }
        return false;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeString(this.MH);
        parcel.writeByteArray(this.MI);
        parcel.writeInt(this.MJ);
    }

}

