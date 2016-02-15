/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.database.CharArrayBuffer
 *  android.net.Uri
 */
package com.google.android.gms.games.achievement;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.b;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayerRef;
import com.google.android.gms.games.achievement.Achievement;
import com.google.android.gms.internal.fb;
import com.google.android.gms.internal.fo;

public final class AchievementRef
extends b
implements Achievement {
    AchievementRef(DataHolder dataHolder, int n2) {
        super(dataHolder, n2);
    }

    @Override
    public String getAchievementId() {
        return this.getString("external_achievement_id");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int getCurrentSteps() {
        boolean bl2 = true;
        if (this.getType() != 1) {
            bl2 = false;
        }
        fb.x(bl2);
        return this.getInteger("current_steps");
    }

    @Override
    public String getDescription() {
        return this.getString("description");
    }

    @Override
    public void getDescription(CharArrayBuffer charArrayBuffer) {
        this.a("description", charArrayBuffer);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public String getFormattedCurrentSteps() {
        boolean bl2 = true;
        if (this.getType() != 1) {
            bl2 = false;
        }
        fb.x(bl2);
        return this.getString("formatted_current_steps");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void getFormattedCurrentSteps(CharArrayBuffer charArrayBuffer) {
        boolean bl2 = true;
        if (this.getType() != 1) {
            bl2 = false;
        }
        fb.x(bl2);
        this.a("formatted_current_steps", charArrayBuffer);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public String getFormattedTotalSteps() {
        boolean bl2 = true;
        if (this.getType() != 1) {
            bl2 = false;
        }
        fb.x(bl2);
        return this.getString("formatted_total_steps");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void getFormattedTotalSteps(CharArrayBuffer charArrayBuffer) {
        boolean bl2 = true;
        if (this.getType() != 1) {
            bl2 = false;
        }
        fb.x(bl2);
        this.a("formatted_total_steps", charArrayBuffer);
    }

    @Override
    public long getLastUpdatedTimestamp() {
        return this.getLong("last_updated_timestamp");
    }

    @Override
    public String getName() {
        return this.getString("name");
    }

    @Override
    public void getName(CharArrayBuffer charArrayBuffer) {
        this.a("name", charArrayBuffer);
    }

    @Override
    public Player getPlayer() {
        return new PlayerRef(this.BB, this.BD);
    }

    @Override
    public Uri getRevealedImageUri() {
        return this.ah("revealed_icon_image_uri");
    }

    @Override
    public String getRevealedImageUrl() {
        return this.getString("revealed_icon_image_url");
    }

    @Override
    public int getState() {
        return this.getInteger("state");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int getTotalSteps() {
        boolean bl2 = true;
        if (this.getType() != 1) {
            bl2 = false;
        }
        fb.x(bl2);
        return this.getInteger("total_steps");
    }

    @Override
    public int getType() {
        return this.getInteger("type");
    }

    @Override
    public Uri getUnlockedImageUri() {
        return this.ah("unlocked_icon_image_uri");
    }

    @Override
    public String getUnlockedImageUrl() {
        return this.getString("unlocked_icon_image_url");
    }

    public String toString() {
        fo.a a2 = fo.e(this).a("AchievementId", this.getAchievementId()).a("Type", this.getType()).a("Name", this.getName()).a("Description", this.getDescription()).a("UnlockedImageUri", (Object)this.getUnlockedImageUri()).a("UnlockedImageUrl", this.getUnlockedImageUrl()).a("RevealedImageUri", (Object)this.getRevealedImageUri()).a("RevealedImageUrl", this.getRevealedImageUrl()).a("Player", this.getPlayer()).a("LastUpdatedTimeStamp", this.getLastUpdatedTimestamp());
        if (this.getType() == 1) {
            a2.a("CurrentSteps", this.getCurrentSteps());
            a2.a("TotalSteps", this.getTotalSteps());
        }
        return a2.toString();
    }
}

