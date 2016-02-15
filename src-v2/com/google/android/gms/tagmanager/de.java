/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.tagmanager.bh;

class de {
    private GoogleAnalytics aaB;
    private Context mContext;
    private Tracker sB;

    de(Context context) {
        this.mContext = context;
    }

    private void bV(String string2) {
        synchronized (this) {
            if (this.aaB == null) {
                this.aaB = GoogleAnalytics.getInstance(this.mContext);
                this.aaB.setLogger(new a());
                this.sB = this.aaB.newTracker(string2);
            }
            return;
        }
    }

    public Tracker bU(String string2) {
        this.bV(string2);
        return this.sB;
    }

    static class a
    implements Logger {
        a() {
        }

        private static int ci(int n2) {
            switch (n2) {
                default: {
                    return 3;
                }
                case 5: {
                    return 2;
                }
                case 3: 
                case 4: {
                    return 1;
                }
                case 2: 
            }
            return 0;
        }

        @Override
        public void error(Exception exception) {
            bh.b("", exception);
        }

        @Override
        public void error(String string2) {
            bh.w(string2);
        }

        @Override
        public int getLogLevel() {
            return a.ci(bh.getLogLevel());
        }

        @Override
        public void info(String string2) {
            bh.x(string2);
        }

        @Override
        public void setLogLevel(int n2) {
            bh.z("GA uses GTM logger. Please use TagManager.setLogLevel(int) instead.");
        }

        @Override
        public void verbose(String string2) {
            bh.y(string2);
        }

        @Override
        public void warn(String string2) {
            bh.z(string2);
        }
    }

}

