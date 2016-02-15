/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.multiplayer;

import com.google.android.gms.games.Player;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;

public final class ParticipantUtils {
    private ParticipantUtils() {
    }

    public static boolean aV(String string2) {
        fq.b(string2, (Object)"Participant ID must not be null");
        return string2.startsWith("p_");
    }

    public static String getParticipantId(ArrayList<Participant> arrayList, String string2) {
        int n2 = arrayList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            Participant participant = arrayList.get(i2);
            Player player = participant.getPlayer();
            if (player == null || !player.getPlayerId().equals(string2)) continue;
            return participant.getParticipantId();
        }
        return null;
    }
}

