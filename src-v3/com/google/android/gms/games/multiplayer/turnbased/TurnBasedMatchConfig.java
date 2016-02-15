package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;

public final class TurnBasedMatchConfig {
    private final String[] MN;
    private final Bundle MO;
    private final int MZ;
    private final int My;

    public static final class Builder {
        Bundle MO;
        ArrayList<String> MR;
        int MZ;
        int My;

        private Builder() {
            this.My = -1;
            this.MR = new ArrayList();
            this.MO = null;
            this.MZ = 2;
        }

        public Builder addInvitedPlayer(String playerId) {
            fq.m985f(playerId);
            this.MR.add(playerId);
            return this;
        }

        public Builder addInvitedPlayers(ArrayList<String> playerIds) {
            fq.m985f(playerIds);
            this.MR.addAll(playerIds);
            return this;
        }

        public TurnBasedMatchConfig build() {
            return new TurnBasedMatchConfig();
        }

        public Builder setAutoMatchCriteria(Bundle autoMatchCriteria) {
            this.MO = autoMatchCriteria;
            return this;
        }

        public Builder setMinPlayers(int minPlayers) {
            this.MZ = minPlayers;
            return this;
        }

        public Builder setVariant(int variant) {
            boolean z = variant == -1 || variant > 0;
            fq.m984b(z, (Object) "Variant must be a positive integer or TurnBasedMatch.MATCH_VARIANT_ANY");
            this.My = variant;
            return this;
        }
    }

    private TurnBasedMatchConfig(Builder builder) {
        this.My = builder.My;
        this.MZ = builder.MZ;
        this.MO = builder.MO;
        this.MN = (String[]) builder.MR.toArray(new String[builder.MR.size()]);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Bundle createAutoMatchCriteria(int minAutoMatchPlayers, int maxAutoMatchPlayers, long exclusiveBitMask) {
        Bundle bundle = new Bundle();
        bundle.putInt(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, minAutoMatchPlayers);
        bundle.putInt(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, maxAutoMatchPlayers);
        bundle.putLong(Multiplayer.EXTRA_EXCLUSIVE_BIT_MASK, exclusiveBitMask);
        return bundle;
    }

    public Bundle getAutoMatchCriteria() {
        return this.MO;
    }

    public String[] getInvitedPlayerIds() {
        return this.MN;
    }

    public int getMinPlayers() {
        return this.MZ;
    }

    public int getVariant() {
        return this.My;
    }
}
