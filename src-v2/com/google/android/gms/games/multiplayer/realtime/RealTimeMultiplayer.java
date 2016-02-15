/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.games.multiplayer.realtime;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import java.util.List;

public interface RealTimeMultiplayer {
    public static final int REAL_TIME_MESSAGE_FAILED = -1;

    public void create(GoogleApiClient var1, RoomConfig var2);

    public void declineInvitation(GoogleApiClient var1, String var2);

    public void dismissInvitation(GoogleApiClient var1, String var2);

    public Intent getSelectOpponentsIntent(GoogleApiClient var1, int var2, int var3);

    public Intent getSelectOpponentsIntent(GoogleApiClient var1, int var2, int var3, boolean var4);

    public RealTimeSocket getSocketForParticipant(GoogleApiClient var1, String var2, String var3);

    public Intent getWaitingRoomIntent(GoogleApiClient var1, Room var2, int var3);

    public void join(GoogleApiClient var1, RoomConfig var2);

    public void leave(GoogleApiClient var1, RoomUpdateListener var2, String var3);

    public int sendReliableMessage(GoogleApiClient var1, ReliableMessageSentCallback var2, byte[] var3, String var4, String var5);

    public int sendUnreliableMessage(GoogleApiClient var1, byte[] var2, String var3, String var4);

    public int sendUnreliableMessage(GoogleApiClient var1, byte[] var2, String var3, List<String> var4);

    public int sendUnreliableMessageToOthers(GoogleApiClient var1, byte[] var2, String var3);

    public static interface ReliableMessageSentCallback {
        public void onRealTimeMessageSent(int var1, int var2, String var3);
    }

}

