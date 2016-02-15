/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.games.internal.player;

import android.net.Uri;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerEntity;
import com.google.android.gms.games.internal.player.ExtendedPlayer;
import com.google.android.gms.internal.fo;

public final class ExtendedPlayerEntity
implements ExtendedPlayer {
    private final PlayerEntity LH;
    private final String LI;
    private final long LJ;
    private final Uri LK;

    /*
     * Enabled aggressive block sorting
     */
    public ExtendedPlayerEntity(ExtendedPlayer extendedPlayer) {
        Player player = extendedPlayer.getPlayer();
        player = player == null ? null : new PlayerEntity(player);
        this.LH = player;
        this.LI = extendedPlayer.hu();
        this.LJ = extendedPlayer.hv();
        this.LK = extendedPlayer.hw();
    }

    static int a(ExtendedPlayer extendedPlayer) {
        return fo.hashCode(new Object[]{extendedPlayer.getPlayer(), extendedPlayer.hu(), extendedPlayer.hv(), extendedPlayer.hw()});
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean a(ExtendedPlayer extendedPlayer, Object object) {
        boolean bl2 = true;
        if (!(object instanceof ExtendedPlayer)) {
            return false;
        }
        boolean bl3 = bl2;
        if (extendedPlayer == object) return bl3;
        if (!fo.equal((object = (ExtendedPlayer)object).getPlayer(), extendedPlayer.getPlayer())) return false;
        if (!fo.equal(object.hu(), extendedPlayer.hu())) return false;
        if (!fo.equal(object.hv(), extendedPlayer.hv())) return false;
        bl3 = bl2;
        if (fo.equal((Object)object.hw(), (Object)extendedPlayer.hw())) return bl3;
        return false;
    }

    static String b(ExtendedPlayer extendedPlayer) {
        return fo.e(extendedPlayer).a("Player", extendedPlayer.getPlayer()).a("MostRecentGameId", extendedPlayer.hu()).a("MostRecentActivityTimestamp", extendedPlayer.hv()).a("MostRecentGameIconImageUri", (Object)extendedPlayer.hw()).toString();
    }

    public boolean equals(Object object) {
        return ExtendedPlayerEntity.a(this, object);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.hy();
    }

    @Override
    public /* synthetic */ Player getPlayer() {
        return this.hx();
    }

    public int hashCode() {
        return ExtendedPlayerEntity.a(this);
    }

    @Override
    public String hu() {
        return this.LI;
    }

    @Override
    public long hv() {
        return this.LJ;
    }

    @Override
    public Uri hw() {
        return this.LK;
    }

    public PlayerEntity hx() {
        return this.LH;
    }

    public ExtendedPlayer hy() {
        return this;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return ExtendedPlayerEntity.b(this);
    }
}

