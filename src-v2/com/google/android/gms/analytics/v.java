/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.j;
import com.google.android.gms.analytics.k;
import com.google.android.gms.analytics.w;

class v
extends k<w> {
    public v(Context context) {
        super(context, new a());
    }

    private static class a
    implements k.a<w> {
        private final w uU = new w();

        @Override
        public void a(String string2, int n2) {
            if ("ga_dispatchPeriod".equals(string2)) {
                this.uU.uW = n2;
                return;
            }
            aa.z("int configuration name not recognized:  " + string2);
        }

        @Override
        public void a(String string2, String string3) {
        }

        @Override
        public void b(String string2, String string3) {
            if ("ga_appName".equals(string2)) {
                this.uU.so = string3;
                return;
            }
            if ("ga_appVersion".equals(string2)) {
                this.uU.sp = string3;
                return;
            }
            if ("ga_logLevel".equals(string2)) {
                this.uU.uV = string3;
                return;
            }
            aa.z("string configuration name not recognized:  " + string2);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void c(String object, boolean bl2) {
            if (!"ga_dryRun".equals(object)) {
                aa.z("bool configuration name not recognized:  " + (String)object);
                return;
            }
            object = this.uU;
            int n2 = bl2 ? 1 : 0;
            object.uX = n2;
        }

        public w cB() {
            return this.uU;
        }

        @Override
        public /* synthetic */ j cg() {
            return this.cB();
        }
    }

}

