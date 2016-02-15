/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.multiplayer.realtime;

import com.google.android.gms.games.multiplayer.realtime.Room;

public interface RoomUpdateListener {
    public void onJoinedRoom(int var1, Room var2);

    public void onLeftRoom(int var1, String var2);

    public void onRoomConnected(int var1, Room var2);

    public void onRoomCreated(int var1, Room var2);
}

