/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.multiplayer.realtime;

import com.google.android.gms.games.multiplayer.realtime.Room;
import java.util.List;

public interface RoomStatusUpdateListener {
    public void onConnectedToRoom(Room var1);

    public void onDisconnectedFromRoom(Room var1);

    public void onP2PConnected(String var1);

    public void onP2PDisconnected(String var1);

    public void onPeerDeclined(Room var1, List<String> var2);

    public void onPeerInvitedToRoom(Room var1, List<String> var2);

    public void onPeerJoined(Room var1, List<String> var2);

    public void onPeerLeft(Room var1, List<String> var2);

    public void onPeersConnected(Room var1, List<String> var2);

    public void onPeersDisconnected(Room var1, List<String> var2);

    public void onRoomAutoMatching(Room var1);

    public void onRoomConnecting(Room var1);
}

