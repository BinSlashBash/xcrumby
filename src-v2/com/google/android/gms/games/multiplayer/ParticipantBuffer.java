/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.multiplayer;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.ParticipantRef;

public final class ParticipantBuffer
extends DataBuffer<Participant> {
    @Override
    public Participant get(int n2) {
        return new ParticipantRef(this.BB, n2);
    }
}

