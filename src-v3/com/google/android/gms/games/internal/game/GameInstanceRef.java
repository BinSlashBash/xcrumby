package com.google.android.gms.games.internal.game;

import com.google.android.gms.common.data.C0251b;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.constants.PlatformType;
import com.google.android.gms.internal.fo;

public final class GameInstanceRef extends C0251b implements GameInstance {
    GameInstanceRef(DataHolder holder, int dataRow) {
        super(holder, dataRow);
    }

    public String getApplicationId() {
        return getString("external_game_id");
    }

    public String getDisplayName() {
        return getString("instance_display_name");
    }

    public String getPackageName() {
        return getString("package_name");
    }

    public boolean hi() {
        return getInteger("real_time_support") > 0;
    }

    public boolean hj() {
        return getInteger("turn_based_support") > 0;
    }

    public int hk() {
        return getInteger("platform_type");
    }

    public boolean hl() {
        return getInteger("piracy_check") > 0;
    }

    public boolean hm() {
        return getInteger("installed") > 0;
    }

    public String toString() {
        return fo.m976e(this).m975a("ApplicationId", getApplicationId()).m975a("DisplayName", getDisplayName()).m975a("SupportsRealTime", Boolean.valueOf(hi())).m975a("SupportsTurnBased", Boolean.valueOf(hj())).m975a("PlatformType", PlatformType.bd(hk())).m975a("PackageName", getPackageName()).m975a("PiracyCheckEnabled", Boolean.valueOf(hl())).m975a("Installed", Boolean.valueOf(hm())).toString();
    }
}
