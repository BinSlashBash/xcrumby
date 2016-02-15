package com.google.android.gms.games.internal.constants;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import org.json.zip.JSONzip;

public final class MatchResult {
    public static boolean isValid(int result) {
        switch (result) {
            case JSONzip.zipEmptyObject /*0*/:
            case Std.STD_FILE /*1*/:
            case Std.STD_URL /*2*/:
            case Std.STD_URI /*3*/:
            case Std.STD_CLASS /*4*/:
            case Std.STD_JAVA_TYPE /*5*/:
                return true;
            default:
                return false;
        }
    }
}
