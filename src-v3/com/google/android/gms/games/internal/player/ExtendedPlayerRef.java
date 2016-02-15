package com.google.android.gms.games.internal.player;

import android.net.Uri;
import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;

public final class ExtendedPlayerRef extends C0251b implements ExtendedPlayer {
    private final PlayerRef LL;

    ExtendedPlayerRef(DataHolder holder, int dataRow) {
        super(holder, dataRow);
        this.LL = new PlayerRef(holder, dataRow);
    }

    public /* synthetic */ Object freeze() {
        return hy();
    }

    public Player getPlayer() {
        return this.LL;
    }

    public String hu() {
        return getString("most_recent_external_game_id");
    }

    public long hv() {
        return getLong("most_recent_activity_timestamp");
    }

    public Uri hw() {
        return ah("game_icon_image_uri");
    }

    public ExtendedPlayer hy() {
        return new ExtendedPlayerEntity(this);
    }
}
