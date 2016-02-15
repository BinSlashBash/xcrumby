/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.aj;
import com.google.android.gms.analytics.j;
import com.google.android.gms.analytics.k;
import java.util.Map;

class ai
extends k<aj> {
    public ai(Context context) {
        super(context, new a());
    }

    private static class a
    implements k.a<aj> {
        private final aj wg = new aj();

        @Override
        public void a(String string2, int n2) {
            if ("ga_sessionTimeout".equals(string2)) {
                this.wg.wj = n2;
                return;
            }
            aa.z("int configuration name not recognized:  " + string2);
        }

        @Override
        public void a(String string2, String string3) {
            this.wg.wn.put(string2, string3);
        }

        @Override
        public void b(String string2, String string3) {
            if ("ga_trackingId".equals(string2)) {
                this.wg.wh = string3;
                return;
            }
            if ("ga_sampleFrequency".equals(string2)) {
                try {
                    this.wg.wi = Double.parseDouble(string3);
                    return;
                }
                catch (NumberFormatException var1_2) {
                    aa.w("Error parsing ga_sampleFrequency value: " + string3);
                    return;
                }
            }
            aa.z("string configuration name not recognized:  " + string2);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void c(String object, boolean bl2) {
            int n2 = 1;
            int n3 = 1;
            int n4 = 1;
            if ("ga_autoActivityTracking".equals(object)) {
                object = this.wg;
                if (!bl2) {
                    n4 = 0;
                }
                object.wk = n4;
                return;
            }
            if ("ga_anonymizeIp".equals(object)) {
                object = this.wg;
                n4 = bl2 ? n2 : 0;
                object.wl = n4;
                return;
            }
            if (!"ga_reportUncaughtExceptions".equals(object)) {
                aa.z("bool configuration name not recognized:  " + (String)object);
                return;
            }
            object = this.wg;
            n4 = bl2 ? n3 : 0;
            object.wm = n4;
        }

        @Override
        public /* synthetic */ j cg() {
            return this.di();
        }

        public aj di() {
            return this.wg;
        }
    }

}

