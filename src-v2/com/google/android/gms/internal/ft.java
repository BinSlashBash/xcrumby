/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.TypedValue
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

public class ft {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String a(String string2, String string3, Context context, AttributeSet object, boolean bl2, boolean bl3, String string4) {
        string2 = object == null ? null : object.getAttributeValue(string2, string3);
        object = string2;
        if (string2 != null) {
            object = string2;
            if (string2.startsWith("@string/")) {
                object = string2;
                if (bl2) {
                    String string5 = string2.substring("@string/".length());
                    String string6 = context.getPackageName();
                    object = new TypedValue();
                    try {
                        context.getResources().getValue(string6 + ":string/" + string5, (TypedValue)object, true);
                    }
                    catch (Resources.NotFoundException var2_3) {
                        Log.w((String)string4, (String)("Could not find resource for " + string3 + ": " + string2));
                    }
                    if (object.string != null) {
                        object = object.string.toString();
                    } else {
                        Log.w((String)string4, (String)("Resource " + string3 + " was not a string: " + object));
                        object = string2;
                    }
                }
            }
        }
        if (bl3 && object == null) {
            Log.w((String)string4, (String)("Required XML attribute \"" + string3 + "\" missing"));
        }
        return object;
    }
}

