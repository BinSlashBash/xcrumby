/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.games.achievement;

import android.content.Intent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.games.achievement.AchievementBuffer;

public interface Achievements {
    public Intent getAchievementsIntent(GoogleApiClient var1);

    public void increment(GoogleApiClient var1, String var2, int var3);

    public PendingResult<UpdateAchievementResult> incrementImmediate(GoogleApiClient var1, String var2, int var3);

    public PendingResult<LoadAchievementsResult> load(GoogleApiClient var1, boolean var2);

    public void reveal(GoogleApiClient var1, String var2);

    public PendingResult<UpdateAchievementResult> revealImmediate(GoogleApiClient var1, String var2);

    public void setSteps(GoogleApiClient var1, String var2, int var3);

    public PendingResult<UpdateAchievementResult> setStepsImmediate(GoogleApiClient var1, String var2, int var3);

    public void unlock(GoogleApiClient var1, String var2);

    public PendingResult<UpdateAchievementResult> unlockImmediate(GoogleApiClient var1, String var2);

    public static interface LoadAchievementsResult
    extends Releasable,
    Result {
        public AchievementBuffer getAchievements();
    }

    public static interface UpdateAchievementResult
    extends Result {
        public String getAchievementId();
    }

}

