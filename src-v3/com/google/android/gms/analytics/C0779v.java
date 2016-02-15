package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.C0213k.C0212a;

/* renamed from: com.google.android.gms.analytics.v */
class C0779v extends C0213k<C0780w> {

    /* renamed from: com.google.android.gms.analytics.v.a */
    private static class C0778a implements C0212a<C0780w> {
        private final C0780w uU;

        public C0778a() {
            this.uU = new C0780w();
        }

        public void m1636a(String str, int i) {
            if ("ga_dispatchPeriod".equals(str)) {
                this.uU.uW = i;
            } else {
                aa.m35z("int configuration name not recognized:  " + str);
            }
        }

        public void m1637a(String str, String str2) {
        }

        public void m1638b(String str, String str2) {
            if ("ga_appName".equals(str)) {
                this.uU.so = str2;
            } else if ("ga_appVersion".equals(str)) {
                this.uU.sp = str2;
            } else if ("ga_logLevel".equals(str)) {
                this.uU.uV = str2;
            } else {
                aa.m35z("string configuration name not recognized:  " + str);
            }
        }

        public void m1639c(String str, boolean z) {
            if ("ga_dryRun".equals(str)) {
                this.uU.uX = z ? 1 : 0;
                return;
            }
            aa.m35z("bool configuration name not recognized:  " + str);
        }

        public C0780w cB() {
            return this.uU;
        }

        public /* synthetic */ C0211j cg() {
            return cB();
        }
    }

    public C0779v(Context context) {
        super(context, new C0778a());
    }
}
