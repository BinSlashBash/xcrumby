/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.games.internal.api;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import java.util.List;

public final class RealTimeMultiplayerImpl
implements RealTimeMultiplayer {
    @Override
    public void create(GoogleApiClient googleApiClient, RoomConfig roomConfig) {
        Games.c(googleApiClient).a(roomConfig);
    }

    @Override
    public void declineInvitation(GoogleApiClient googleApiClient, String string2) {
        Games.c(googleApiClient).m(string2, 0);
    }

    @Override
    public void dismissInvitation(GoogleApiClient googleApiClient, String string2) {
        Games.c(googleApiClient).l(string2, 0);
    }

    @Override
    public Intent getSelectOpponentsIntent(GoogleApiClient googleApiClient, int n2, int n3) {
        return Games.c(googleApiClient).b(n2, n3, true);
    }

    @Override
    public Intent getSelectOpponentsIntent(GoogleApiClient googleApiClient, int n2, int n3, boolean bl2) {
        return Games.c(googleApiClient).b(n2, n3, bl2);
    }

    @Override
    public RealTimeSocket getSocketForParticipant(GoogleApiClient googleApiClient, String string2, String string3) {
        return Games.c(googleApiClient).i(string2, string3);
    }

    @Override
    public Intent getWaitingRoomIntent(GoogleApiClient googleApiClient, Room room, int n2) {
        return Games.c(googleApiClient).a(room, n2);
    }

    @Override
    public void join(GoogleApiClient googleApiClient, RoomConfig roomConfig) {
        Games.c(googleApiClient).b(roomConfig);
    }

    @Override
    public void leave(GoogleApiClient googleApiClient, RoomUpdateListener roomUpdateListener, String string2) {
        Games.c(googleApiClient).a(roomUpdateListener, string2);
    }

    @Override
    public int sendReliableMessage(GoogleApiClient googleApiClient, RealTimeMultiplayer.ReliableMessageSentCallback reliableMessageSentCallback, byte[] arrby, String string2, String string3) {
        return Games.c(googleApiClient).a(reliableMessageSentCallback, arrby, string2, string3);
    }

    @Override
    public int sendUnreliableMessage(GoogleApiClient googleApiClient, byte[] arrby, String string2, String string3) {
        return Games.c(googleApiClient).a(arrby, string2, new String[]{string3});
    }

    @Override
    public int sendUnreliableMessage(GoogleApiClient googleApiClient, byte[] arrby, String string2, List<String> arrstring) {
        arrstring = arrstring.toArray(new String[arrstring.size()]);
        return Games.c(googleApiClient).a(arrby, string2, arrstring);
    }

    @Override
    public int sendUnreliableMessageToOthers(GoogleApiClient googleApiClient, byte[] arrby, String string2) {
        return Games.c(googleApiClient).d(arrby, string2);
    }
}

