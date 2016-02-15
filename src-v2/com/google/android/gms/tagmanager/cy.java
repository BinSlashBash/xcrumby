/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

class cy {
    static void a(Context context, String string2, String string3, String string4) {
        context = context.getSharedPreferences(string2, 0).edit();
        context.putString(string3, string4);
        cy.a((SharedPreferences.Editor)context);
    }

    static void a(final SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT >= 9) {
            editor.apply();
            return;
        }
        new Thread(new Runnable(){

            @Override
            public void run() {
                editor.commit();
            }
        }).start();
    }

}

