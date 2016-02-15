/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fq;
import com.google.android.gms.wallet.m;

public final class NotifyTransactionStatusRequest
implements SafeParcelable {
    public static final Parcelable.Creator<NotifyTransactionStatusRequest> CREATOR = new m();
    String abh;
    String ach;
    int status;
    final int xH;

    NotifyTransactionStatusRequest() {
        this.xH = 1;
    }

    NotifyTransactionStatusRequest(int n2, String string2, int n3, String string3) {
        this.xH = n2;
        this.abh = string2;
        this.status = n3;
        this.ach = string3;
    }

    public static Builder newBuilder() {
        NotifyTransactionStatusRequest notifyTransactionStatusRequest = new NotifyTransactionStatusRequest();
        notifyTransactionStatusRequest.getClass();
        return notifyTransactionStatusRequest.new Builder();
    }

    public int describeContents() {
        return 0;
    }

    public String getDetailedReason() {
        return this.ach;
    }

    public String getGoogleTransactionId() {
        return this.abh;
    }

    public int getStatus() {
        return this.status;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        m.a(this, parcel, n2);
    }

    public final class Builder {
        private Builder() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public NotifyTransactionStatusRequest build() {
            boolean bl2 = true;
            boolean bl3 = !TextUtils.isEmpty((CharSequence)NotifyTransactionStatusRequest.this.abh);
            fq.b(bl3, (Object)"googleTransactionId is required");
            bl3 = NotifyTransactionStatusRequest.this.status >= 1 && NotifyTransactionStatusRequest.this.status <= 8 ? bl2 : false;
            fq.b(bl3, (Object)"status is an unrecognized value");
            return NotifyTransactionStatusRequest.this;
        }

        public Builder setDetailedReason(String string2) {
            NotifyTransactionStatusRequest.this.ach = string2;
            return this;
        }

        public Builder setGoogleTransactionId(String string2) {
            NotifyTransactionStatusRequest.this.abh = string2;
            return this;
        }

        public Builder setStatus(int n2) {
            NotifyTransactionStatusRequest.this.status = n2;
            return this;
        }
    }

    public static interface Status {
        public static final int SUCCESS = 1;

        public static interface Error {
            public static final int AVS_DECLINE = 7;
            public static final int BAD_CARD = 4;
            public static final int BAD_CVC = 3;
            public static final int DECLINED = 5;
            public static final int FRAUD_DECLINE = 8;
            public static final int OTHER = 6;
            public static final int UNKNOWN = 2;
        }

    }

}

