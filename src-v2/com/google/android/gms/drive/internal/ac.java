/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;
import com.google.android.gms.common.internal.safeparcel.b;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ConflictEvent;
import com.google.android.gms.drive.internal.OnEventResponse;

public class ac
implements Parcelable.Creator<OnEventResponse> {
    static void a(OnEventResponse onEventResponse, Parcel parcel, int n2) {
        int n3 = b.p(parcel);
        b.c(parcel, 1, onEventResponse.xH);
        b.c(parcel, 2, onEventResponse.ES);
        b.a(parcel, 3, onEventResponse.FH, n2, false);
        b.a(parcel, 4, onEventResponse.FI, n2, false);
        b.F(parcel, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public OnEventResponse Q(Parcel parcel) {
        ConflictEvent conflictEvent = null;
        int n2 = 0;
        int n3 = a.o(parcel);
        ChangeEvent changeEvent = null;
        int n4 = 0;
        while (parcel.dataPosition() < n3) {
            int n5 = a.n(parcel);
            switch (a.R(n5)) {
                default: {
                    a.b(parcel, n5);
                    n5 = n2;
                    n2 = n4;
                    n4 = n5;
                    break;
                }
                case 1: {
                    n5 = a.g(parcel, n5);
                    n4 = n2;
                    n2 = n5;
                    break;
                }
                case 2: {
                    n5 = a.g(parcel, n5);
                    n2 = n4;
                    n4 = n5;
                    break;
                }
                case 3: {
                    changeEvent = a.a(parcel, n5, ChangeEvent.CREATOR);
                    n5 = n4;
                    n4 = n2;
                    n2 = n5;
                    break;
                }
                case 4: {
                    conflictEvent = a.a(parcel, n5, ConflictEvent.CREATOR);
                    n5 = n4;
                    n4 = n2;
                    n2 = n5;
                }
            }
            n5 = n2;
            n2 = n4;
            n4 = n5;
        }
        if (parcel.dataPosition() != n3) {
            throw new a.a("Overread allowed size end=" + n3, parcel);
        }
        return new OnEventResponse(n4, n2, changeEvent, conflictEvent);
    }

    public OnEventResponse[] au(int n2) {
        return new OnEventResponse[n2];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.Q(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.au(n2);
    }
}

