/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package android.support.v4.database;

import android.text.TextUtils;

public class DatabaseUtilsCompat {
    private DatabaseUtilsCompat() {
    }

    public static String[] appendSelectionArgs(String[] arrstring, String[] arrstring2) {
        if (arrstring == null || arrstring.length == 0) {
            return arrstring2;
        }
        String[] arrstring3 = new String[arrstring.length + arrstring2.length];
        System.arraycopy(arrstring, 0, arrstring3, 0, arrstring.length);
        System.arraycopy(arrstring2, 0, arrstring3, arrstring.length, arrstring2.length);
        return arrstring3;
    }

    public static String concatenateWhere(String string2, String string3) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return string3;
        }
        if (TextUtils.isEmpty((CharSequence)string3)) {
            return string2;
        }
        return "(" + string2 + ") AND (" + string3 + ")";
    }
}

