package android.support.v4.net;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import org.json.zip.JSONzip;

class ConnectivityManagerCompatHoneycombMR2 {
    ConnectivityManagerCompatHoneycombMR2() {
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return true;
        }
        switch (info.getType()) {
            case JSONzip.zipEmptyObject /*0*/:
            case Std.STD_URL /*2*/:
            case Std.STD_URI /*3*/:
            case Std.STD_CLASS /*4*/:
            case Std.STD_JAVA_TYPE /*5*/:
            case Std.STD_CURRENCY /*6*/:
                return true;
            case Std.STD_FILE /*1*/:
            case Std.STD_PATTERN /*7*/:
            case Std.STD_CHARSET /*9*/:
                return false;
            default:
                return true;
        }
    }
}
