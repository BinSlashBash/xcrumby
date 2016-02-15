/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.internal.game;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.internal.constants.PlatformType;
import com.google.android.gms.games.internal.game.GameInstance;
import com.google.android.gms.internal.fo;

public final class GameInstanceRef
extends b
implements GameInstance {
    GameInstanceRef(DataHolder dataHolder, int n2) {
        super(dataHolder, n2);
    }

    public String getApplicationId() {
        return this.getString("external_game_id");
    }

    public String getDisplayName() {
        return this.getString("instance_display_name");
    }

    public String getPackageName() {
        return this.getString("package_name");
    }

    public boolean hi() {
        if (this.getInteger("real_time_support") > 0) {
            return true;
        }
        return false;
    }

    public boolean hj() {
        if (this.getInteger("turn_based_support") > 0) {
            return true;
        }
        return false;
    }

    public int hk() {
        return this.getInteger("platform_type");
    }

    public boolean hl() {
        if (this.getInteger("piracy_check") > 0) {
            return true;
        }
        return false;
    }

    public boolean hm() {
        if (this.getInteger("installed") > 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        return fo.e(this).a("ApplicationId", this.getApplicationId()).a("DisplayName", this.getDisplayName()).a("SupportsRealTime", this.hi()).a("SupportsTurnBased", this.hj()).a("PlatformType", PlatformType.bd(this.hk())).a("PackageName", this.getPackageName()).a("PiracyCheckEnabled", this.hl()).a("Installed", this.hm()).toString();
    }
}

