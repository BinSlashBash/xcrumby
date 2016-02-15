/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.games.multiplayer.turnbased;

import android.os.Bundle;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.Collection;

public final class TurnBasedMatchConfig {
    private final String[] MN;
    private final Bundle MO;
    private final int MZ;
    private final int My;

    private TurnBasedMatchConfig(Builder builder) {
        this.My = builder.My;
        this.MZ = builder.MZ;
        this.MO = builder.MO;
        int n2 = builder.MR.size();
        this.MN = builder.MR.toArray(new String[n2]);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Bundle createAutoMatchCriteria(int n2, int n3, long l2) {
        Bundle bundle = new Bundle();
        bundle.putInt("min_automatch_players", n2);
        bundle.putInt("max_automatch_players", n3);
        bundle.putLong("exclusive_bit_mask", l2);
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

    public static final class Builder {
        Bundle MO = null;
        ArrayList<String> MR = new ArrayList();
        int MZ = 2;
        int My = -1;

        private Builder() {
        }

        public Builder addInvitedPlayer(String string2) {
            fq.f(string2);
            this.MR.add(string2);
            return this;
        }

        public Builder addInvitedPlayers(ArrayList<String> arrayList) {
            fq.f(arrayList);
            this.MR.addAll(arrayList);
            return this;
        }

        public TurnBasedMatchConfig build() {
            return new TurnBasedMatchConfig(this);
        }

        public Builder setAutoMatchCriteria(Bundle bundle) {
            this.MO = bundle;
            return this;
        }

        public Builder setMinPlayers(int n2) {
            this.MZ = n2;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder setVariant(int n2) {
            boolean bl2 = n2 == -1 || n2 > 0;
            fq.b(bl2, (Object)"Variant must be a positive integer or TurnBasedMatch.MATCH_VARIANT_ANY");
            this.My = n2;
            return this;
        }
    }

}

