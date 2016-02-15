package com.google.android.gms.games.internal.constants;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.games.internal.GamesLog;
import org.json.zip.JSONzip;

public final class TurnBasedMatchTurnStatus {
    public static String bd(int i) {
        switch (i) {
            case JSONzip.zipEmptyObject /*0*/:
                return "TURN_STATUS_INVITED";
            case Std.STD_FILE /*1*/:
                return "TURN_STATUS_MY_TURN";
            case Std.STD_URL /*2*/:
                return "TURN_STATUS_THEIR_TURN";
            case Std.STD_URI /*3*/:
                return "TURN_STATUS_COMPLETE";
            default:
                GamesLog.m367h("MatchTurnStatus", "Unknown match turn status: " + i);
                return "TURN_STATUS_UNKNOWN";
        }
    }
}
