package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.fq;

public final class NotifyTransactionStatusRequest implements SafeParcelable {
    public static final Creator<NotifyTransactionStatusRequest> CREATOR;
    String abh;
    String ach;
    int status;
    final int xH;

    public final class Builder {
        final /* synthetic */ NotifyTransactionStatusRequest aci;

        private Builder(NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
            this.aci = notifyTransactionStatusRequest;
        }

        public NotifyTransactionStatusRequest build() {
            boolean z = true;
            fq.m984b(!TextUtils.isEmpty(this.aci.abh), (Object) "googleTransactionId is required");
            if (this.aci.status < 1 || this.aci.status > 8) {
                z = false;
            }
            fq.m984b(z, (Object) "status is an unrecognized value");
            return this.aci;
        }

        public Builder setDetailedReason(String detailedReason) {
            this.aci.ach = detailedReason;
            return this;
        }

        public Builder setGoogleTransactionId(String googleTransactionId) {
            this.aci.abh = googleTransactionId;
            return this;
        }

        public Builder setStatus(int status) {
            this.aci.status = status;
            return this;
        }
    }

    public interface Status {
        public static final int SUCCESS = 1;

        public interface Error {
            public static final int AVS_DECLINE = 7;
            public static final int BAD_CARD = 4;
            public static final int BAD_CVC = 3;
            public static final int DECLINED = 5;
            public static final int FRAUD_DECLINE = 8;
            public static final int OTHER = 6;
            public static final int UNKNOWN = 2;
        }
    }

    static {
        CREATOR = new C0560m();
    }

    NotifyTransactionStatusRequest() {
        this.xH = 1;
    }

    NotifyTransactionStatusRequest(int versionCode, String googleTransactionId, int status, String detailedReason) {
        this.xH = versionCode;
        this.abh = googleTransactionId;
        this.status = status;
        this.ach = detailedReason;
    }

    public static Builder newBuilder() {
        NotifyTransactionStatusRequest notifyTransactionStatusRequest = new NotifyTransactionStatusRequest();
        notifyTransactionStatusRequest.getClass();
        return new Builder(null);
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

    public void writeToParcel(Parcel out, int flags) {
        C0560m.m1520a(this, out, flags);
    }
}
