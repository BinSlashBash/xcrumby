/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.constants.TimeSpan;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fq;
import java.util.HashMap;

public final class ScoreSubmissionData {
    private static final String[] LN = new String[]{"leaderboardId", "playerId", "timeSpan", "hasResult", "rawScore", "formattedScore", "newBest", "scoreTag"};
    private int Ah;
    private String Ie;
    private String LP;
    private HashMap<Integer, Result> Mt;

    /*
     * Enabled aggressive block sorting
     */
    public ScoreSubmissionData(DataHolder dataHolder) {
        this.Ah = dataHolder.getStatusCode();
        this.Mt = new HashMap();
        int n2 = dataHolder.getCount();
        boolean bl2 = n2 == 3;
        fq.z(bl2);
        int n3 = 0;
        while (n3 < n2) {
            int n4 = dataHolder.G(n3);
            if (n3 == 0) {
                this.LP = dataHolder.getString("leaderboardId", n3, n4);
                this.Ie = dataHolder.getString("playerId", n3, n4);
            }
            if (dataHolder.getBoolean("hasResult", n3, n4)) {
                this.a(new Result(dataHolder.getLong("rawScore", n3, n4), dataHolder.getString("formattedScore", n3, n4), dataHolder.getString("scoreTag", n3, n4), dataHolder.getBoolean("newBest", n3, n4)), dataHolder.getInteger("timeSpan", n3, n4));
            }
            ++n3;
        }
    }

    private void a(Result result, int n2) {
        this.Mt.put(n2, result);
    }

    public String getLeaderboardId() {
        return this.LP;
    }

    public String getPlayerId() {
        return this.Ie;
    }

    public Result getScoreResult(int n2) {
        return this.Mt.get(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        fo.a a2 = fo.e(this).a("PlayerId", this.Ie).a("StatusCode", this.Ah);
        int n2 = 0;
        while (n2 < 3) {
            Object object = this.Mt.get(n2);
            a2.a("TimesSpan", TimeSpan.bd(n2));
            object = object == null ? "null" : object.toString();
            a2.a("Result", object);
            ++n2;
        }
        return a2.toString();
    }

    public static final class Result {
        public final String formattedScore;
        public final boolean newBest;
        public final long rawScore;
        public final String scoreTag;

        public Result(long l2, String string2, String string3, boolean bl2) {
            this.rawScore = l2;
            this.formattedScore = string2;
            this.scoreTag = string3;
            this.newBest = bl2;
        }

        public String toString() {
            return fo.e(this).a("RawScore", this.rawScore).a("FormattedScore", this.formattedScore).a("ScoreTag", this.scoreTag).a("NewBest", this.newBest).toString();
        }
    }

}

