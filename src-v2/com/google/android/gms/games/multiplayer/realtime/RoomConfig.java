/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.android.gms.internal.fq;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public final class RoomConfig {
    private final String IV;
    private final RoomUpdateListener MK;
    private final RoomStatusUpdateListener ML;
    private final RealTimeMessageReceivedListener MM;
    private final String[] MN;
    private final Bundle MO;
    private final boolean MP;
    private final int My;

    private RoomConfig(Builder builder) {
        this.MK = builder.MK;
        this.ML = builder.ML;
        this.MM = builder.MM;
        this.IV = builder.MQ;
        this.My = builder.My;
        this.MO = builder.MO;
        this.MP = builder.MP;
        int n2 = builder.MR.size();
        this.MN = builder.MR.toArray(new String[n2]);
        if (this.MM == null) {
            fq.a(this.MP, "Must either enable sockets OR specify a message listener");
        }
    }

    public static Builder builder(RoomUpdateListener roomUpdateListener) {
        return new Builder(roomUpdateListener);
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

    public String getInvitationId() {
        return this.IV;
    }

    public String[] getInvitedPlayerIds() {
        return this.MN;
    }

    public RealTimeMessageReceivedListener getMessageReceivedListener() {
        return this.MM;
    }

    public RoomStatusUpdateListener getRoomStatusUpdateListener() {
        return this.ML;
    }

    public RoomUpdateListener getRoomUpdateListener() {
        return this.MK;
    }

    public int getVariant() {
        return this.My;
    }

    public boolean isSocketEnabled() {
        return this.MP;
    }

    public static final class Builder {
        final RoomUpdateListener MK;
        RoomStatusUpdateListener ML;
        RealTimeMessageReceivedListener MM;
        Bundle MO;
        boolean MP = false;
        String MQ = null;
        ArrayList<String> MR = new ArrayList();
        int My = -1;

        private Builder(RoomUpdateListener roomUpdateListener) {
            this.MK = fq.b(roomUpdateListener, (Object)"Must provide a RoomUpdateListener");
        }

        public Builder addPlayersToInvite(ArrayList<String> arrayList) {
            fq.f(arrayList);
            this.MR.addAll(arrayList);
            return this;
        }

        public /* varargs */ Builder addPlayersToInvite(String ... arrstring) {
            fq.f(arrstring);
            this.MR.addAll(Arrays.asList(arrstring));
            return this;
        }

        public RoomConfig build() {
            return new RoomConfig(this);
        }

        public Builder setAutoMatchCriteria(Bundle bundle) {
            this.MO = bundle;
            return this;
        }

        public Builder setInvitationIdToAccept(String string2) {
            fq.f(string2);
            this.MQ = string2;
            return this;
        }

        public Builder setMessageReceivedListener(RealTimeMessageReceivedListener realTimeMessageReceivedListener) {
            this.MM = realTimeMessageReceivedListener;
            return this;
        }

        public Builder setRoomStatusUpdateListener(RoomStatusUpdateListener roomStatusUpdateListener) {
            this.ML = roomStatusUpdateListener;
            return this;
        }

        public Builder setSocketCommunicationEnabled(boolean bl2) {
            this.MP = bl2;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder setVariant(int n2) {
            boolean bl2 = n2 == -1 || n2 > 0;
            fq.b(bl2, (Object)"Variant must be a positive integer or Room.ROOM_VARIANT_ANY");
            this.My = n2;
            return this;
        }
    }

}

