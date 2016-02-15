package com.google.android.gms.maps.internal;

import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import org.json.zip.JSONzip;

/* renamed from: com.google.android.gms.maps.internal.a */
public final class C0431a {
    public static Boolean m1227a(byte b) {
        switch (b) {
            case JSONzip.zipEmptyObject /*0*/:
                return Boolean.FALSE;
            case Std.STD_FILE /*1*/:
                return Boolean.TRUE;
            default:
                return null;
        }
    }

    public static byte m1228c(Boolean bool) {
        return bool != null ? bool.booleanValue() ? (byte) 1 : (byte) 0 : (byte) -1;
    }
}
