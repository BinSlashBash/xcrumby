package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.C0213k.C0212a;

class ai extends C0213k<aj> {

    /* renamed from: com.google.android.gms.analytics.ai.a */
    private static class C0768a implements C0212a<aj> {
        private final aj wg;

        public C0768a() {
            this.wg = new aj();
        }

        public void m1572a(String str, int i) {
            if ("ga_sessionTimeout".equals(str)) {
                this.wg.wj = i;
            } else {
                aa.m35z("int configuration name not recognized:  " + str);
            }
        }

        public void m1573a(String str, String str2) {
            this.wg.wn.put(str, str2);
        }

        public void m1574b(String str, String str2) {
            if ("ga_trackingId".equals(str)) {
                this.wg.wh = str2;
            } else if ("ga_sampleFrequency".equals(str)) {
                try {
                    this.wg.wi = Double.parseDouble(str2);
                } catch (NumberFormatException e) {
                    aa.m32w("Error parsing ga_sampleFrequency value: " + str2);
                }
            } else {
                aa.m35z("string configuration name not recognized:  " + str);
            }
        }

        public void m1575c(String str, boolean z) {
            int i = 1;
            aj ajVar;
            if ("ga_autoActivityTracking".equals(str)) {
                ajVar = this.wg;
                if (!z) {
                    i = 0;
                }
                ajVar.wk = i;
            } else if ("ga_anonymizeIp".equals(str)) {
                ajVar = this.wg;
                if (!z) {
                    i = 0;
                }
                ajVar.wl = i;
            } else if ("ga_reportUncaughtExceptions".equals(str)) {
                ajVar = this.wg;
                if (!z) {
                    i = 0;
                }
                ajVar.wm = i;
            } else {
                aa.m35z("bool configuration name not recognized:  " + str);
            }
        }

        public /* synthetic */ C0211j cg() {
            return di();
        }

        public aj di() {
            return this.wg;
        }
    }

    public ai(Context context) {
        super(context, new C0768a());
    }
}
