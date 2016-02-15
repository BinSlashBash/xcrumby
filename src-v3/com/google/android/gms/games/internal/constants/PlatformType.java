package com.google.android.gms.games.internal.constants;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import org.json.zip.JSONzip;

public final class PlatformType {
    private PlatformType() {
    }

    public static String bd(int i) {
        switch (i) {
            case JSONzip.zipEmptyObject /*0*/:
                return "ANDROID";
            case Std.STD_FILE /*1*/:
                return "IOS";
            case Std.STD_URL /*2*/:
                return "WEB_APP";
            default:
                throw new IllegalArgumentException("Unknown platform type: " + i);
        }
    }
}
