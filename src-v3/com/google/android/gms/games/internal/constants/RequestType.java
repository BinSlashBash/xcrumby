package com.google.android.gms.games.internal.constants;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.games.internal.GamesLog;

public final class RequestType {
    private RequestType() {
    }

    public static String bd(int i) {
        switch (i) {
            case Std.STD_FILE /*1*/:
                return "GIFT";
            case Std.STD_URL /*2*/:
                return "WISH";
            default:
                GamesLog.m367h("RequestType", "Unknown request type: " + i);
                return "UNKNOWN_TYPE";
        }
    }
}
