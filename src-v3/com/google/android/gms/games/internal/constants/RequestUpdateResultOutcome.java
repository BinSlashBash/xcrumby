package com.google.android.gms.games.internal.constants;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import org.json.zip.JSONzip;

public final class RequestUpdateResultOutcome {
    private RequestUpdateResultOutcome() {
    }

    public static boolean isValid(int outcome) {
        switch (outcome) {
            case JSONzip.zipEmptyObject /*0*/:
            case Std.STD_FILE /*1*/:
            case Std.STD_URL /*2*/:
            case Std.STD_URI /*3*/:
                return true;
            default:
                return false;
        }
    }
}
