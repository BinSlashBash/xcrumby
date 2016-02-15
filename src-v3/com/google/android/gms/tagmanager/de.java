package com.google.android.gms.tagmanager;

import android.content.Context;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

class de {
    private GoogleAnalytics aaB;
    private Context mContext;
    private Tracker sB;

    /* renamed from: com.google.android.gms.tagmanager.de.a */
    static class C1073a implements Logger {
        C1073a() {
        }

        private static int ci(int i) {
            switch (i) {
                case Std.STD_URL /*2*/:
                    return 0;
                case Std.STD_URI /*3*/:
                case Std.STD_CLASS /*4*/:
                    return 1;
                case Std.STD_JAVA_TYPE /*5*/:
                    return 2;
                default:
                    return 3;
            }
        }

        public void error(Exception exception) {
            bh.m1381b(UnsupportedUrlFragment.DISPLAY_NAME, exception);
        }

        public void error(String message) {
            bh.m1384w(message);
        }

        public int getLogLevel() {
            return C1073a.ci(bh.getLogLevel());
        }

        public void info(String message) {
            bh.m1385x(message);
        }

        public void setLogLevel(int logLevel) {
            bh.m1387z("GA uses GTM logger. Please use TagManager.setLogLevel(int) instead.");
        }

        public void verbose(String message) {
            bh.m1386y(message);
        }

        public void warn(String message) {
            bh.m1387z(message);
        }
    }

    de(Context context) {
        this.mContext = context;
    }

    private synchronized void bV(String str) {
        if (this.aaB == null) {
            this.aaB = GoogleAnalytics.getInstance(this.mContext);
            this.aaB.setLogger(new C1073a());
            this.sB = this.aaB.newTracker(str);
        }
    }

    public Tracker bU(String str) {
        bV(str);
        return this.sB;
    }
}
