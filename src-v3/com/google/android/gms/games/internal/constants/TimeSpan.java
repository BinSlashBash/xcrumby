package com.google.android.gms.games.internal.constants;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import org.json.zip.JSONzip;

public final class TimeSpan {
    private TimeSpan() {
        throw new AssertionError("Uninstantiable");
    }

    public static String bd(int i) {
        switch (i) {
            case JSONzip.zipEmptyObject /*0*/:
                return "DAILY";
            case Std.STD_FILE /*1*/:
                return "WEEKLY";
            case Std.STD_URL /*2*/:
                return "ALL_TIME";
            default:
                throw new IllegalArgumentException("Unknown time span " + i);
        }
    }
}
