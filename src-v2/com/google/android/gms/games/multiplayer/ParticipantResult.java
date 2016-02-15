/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.games.internal.constants.MatchResult;
import com.google.android.gms.games.multiplayer.ParticipantResultCreator;
import com.google.android.gms.internal.fq;

public final class ParticipantResult
implements SafeParcelable {
    public static final ParticipantResultCreator CREATOR = new ParticipantResultCreator();
    public static final int MATCH_RESULT_DISAGREED = 5;
    public static final int MATCH_RESULT_DISCONNECT = 4;
    public static final int MATCH_RESULT_LOSS = 1;
    public static final int MATCH_RESULT_NONE = 3;
    public static final int MATCH_RESULT_TIE = 2;
    public static final int MATCH_RESULT_UNINITIALIZED = -1;
    public static final int MATCH_RESULT_WIN = 0;
    public static final int PLACING_UNINITIALIZED = -1;
    private final String Jg;
    private final int MF;
    private final int MG;
    private final int xH;

    public ParticipantResult(int n2, String string2, int n3, int n4) {
        this.xH = n2;
        this.Jg = fq.f(string2);
        fq.x(MatchResult.isValid(n3));
        this.MF = n3;
        this.MG = n4;
    }

    public ParticipantResult(String string2, int n2, int n3) {
        this(1, string2, n2, n3);
    }

    public int describeContents() {
        return 0;
    }

    public String getParticipantId() {
        return this.Jg;
    }

    public int getPlacing() {
        return this.MG;
    }

    public int getResult() {
        return this.MF;
    }

    public int getVersionCode() {
        return this.xH;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        ParticipantResultCreator.a(this, parcel, n2);
    }
}

