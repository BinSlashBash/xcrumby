package com.google.android.gms.games.multiplayer.realtime;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.fq;

public final class RealTimeMessage implements Parcelable {
    public static final Creator<RealTimeMessage> CREATOR;
    public static final int RELIABLE = 1;
    public static final int UNRELIABLE = 0;
    private final String MH;
    private final byte[] MI;
    private final int MJ;

    /* renamed from: com.google.android.gms.games.multiplayer.realtime.RealTimeMessage.1 */
    static class C03131 implements Creator<RealTimeMessage> {
        C03131() {
        }

        public RealTimeMessage aw(Parcel parcel) {
            return new RealTimeMessage(null);
        }

        public RealTimeMessage[] bp(int i) {
            return new RealTimeMessage[i];
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return aw(x0);
        }

        public /* synthetic */ Object[] newArray(int x0) {
            return bp(x0);
        }
    }

    static {
        CREATOR = new C03131();
    }

    private RealTimeMessage(Parcel parcel) {
        this(parcel.readString(), parcel.createByteArray(), parcel.readInt());
    }

    public RealTimeMessage(String senderParticipantId, byte[] messageData, int isReliable) {
        this.MH = (String) fq.m985f(senderParticipantId);
        this.MI = (byte[]) ((byte[]) fq.m985f(messageData)).clone();
        this.MJ = isReliable;
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
        return this.MJ == RELIABLE;
    }

    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(this.MH);
        parcel.writeByteArray(this.MI);
        parcel.writeInt(this.MJ);
    }
}
