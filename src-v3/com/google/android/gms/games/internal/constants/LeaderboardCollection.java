package com.google.android.gms.games.internal.constants;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import org.json.zip.JSONzip;

public final class LeaderboardCollection {
    private LeaderboardCollection() {
    }

    public static String bd(int i) {
        switch (i) {
            case JSONzip.zipEmptyObject /*0*/:
                return "PUBLIC";
            case Std.STD_FILE /*1*/:
                return "SOCIAL";
            default:
                throw new IllegalArgumentException("Unknown leaderboard collection: " + i);
        }
    }
}
