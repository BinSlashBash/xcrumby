package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.internal.constants.TimeSpan;
import com.google.android.gms.internal.fo;
import com.google.android.gms.internal.fo.C0398a;
import com.google.android.gms.internal.fq;
import java.util.HashMap;

public final class ScoreSubmissionData {
    private static final String[] LN;
    private int Ah;
    private String Ie;
    private String LP;
    private HashMap<Integer, Result> Mt;

    public static final class Result {
        public final String formattedScore;
        public final boolean newBest;
        public final long rawScore;
        public final String scoreTag;

        public Result(long rawScore, String formattedScore, String scoreTag, boolean newBest) {
            this.rawScore = rawScore;
            this.formattedScore = formattedScore;
            this.scoreTag = scoreTag;
            this.newBest = newBest;
        }

        public String toString() {
            return fo.m976e(this).m975a("RawScore", Long.valueOf(this.rawScore)).m975a("FormattedScore", this.formattedScore).m975a("ScoreTag", this.scoreTag).m975a("NewBest", Boolean.valueOf(this.newBest)).toString();
        }
    }

    static {
        LN = new String[]{"leaderboardId", "playerId", "timeSpan", "hasResult", "rawScore", "formattedScore", "newBest", "scoreTag"};
    }

    public ScoreSubmissionData(DataHolder dataHolder) {
        this.Ah = dataHolder.getStatusCode();
        this.Mt = new HashMap();
        int count = dataHolder.getCount();
        fq.m987z(count == 3);
        for (int i = 0; i < count; i++) {
            int G = dataHolder.m1687G(i);
            if (i == 0) {
                this.LP = dataHolder.getString("leaderboardId", i, G);
                this.Ie = dataHolder.getString("playerId", i, G);
            }
            if (dataHolder.getBoolean("hasResult", i, G)) {
                m574a(new Result(dataHolder.getLong("rawScore", i, G), dataHolder.getString("formattedScore", i, G), dataHolder.getString("scoreTag", i, G), dataHolder.getBoolean("newBest", i, G)), dataHolder.getInteger("timeSpan", i, G));
            }
        }
    }

    private void m574a(Result result, int i) {
        this.Mt.put(Integer.valueOf(i), result);
    }

    public String getLeaderboardId() {
        return this.LP;
    }

    public String getPlayerId() {
        return this.Ie;
    }

    public Result getScoreResult(int timeSpan) {
        return (Result) this.Mt.get(Integer.valueOf(timeSpan));
    }

    public String toString() {
        C0398a a = fo.m976e(this).m975a("PlayerId", this.Ie).m975a("StatusCode", Integer.valueOf(this.Ah));
        for (int i = 0; i < 3; i++) {
            Result result = (Result) this.Mt.get(Integer.valueOf(i));
            a.m975a("TimesSpan", TimeSpan.bd(i));
            a.m975a("Result", result == null ? "null" : result.toString());
        }
        return a.toString();
    }
}
