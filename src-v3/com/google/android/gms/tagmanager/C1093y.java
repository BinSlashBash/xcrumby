package com.google.android.gms.tagmanager;

import android.content.Context;
import java.net.URLEncoder;
import org.apache.commons.codec.binary.Hex;

/* renamed from: com.google.android.gms.tagmanager.y */
class C1093y implements aq {
    private static C1093y XM;
    private static final Object sf;
    private String XN;
    private String XO;
    private ar XP;
    private cf Xa;

    static {
        sf = new Object();
    }

    private C1093y(Context context) {
        this(as.m2450H(context), new cv());
    }

    C1093y(ar arVar, cf cfVar) {
        this.XP = arVar;
        this.Xa = cfVar;
    }

    public static aq m2568F(Context context) {
        aq aqVar;
        synchronized (sf) {
            if (XM == null) {
                XM = new C1093y(context);
            }
            aqVar = XM;
        }
        return aqVar;
    }

    public boolean bz(String str) {
        if (this.Xa.cS()) {
            if (!(this.XN == null || this.XO == null)) {
                try {
                    str = this.XN + "?" + this.XO + "=" + URLEncoder.encode(str, Hex.DEFAULT_CHARSET_NAME);
                    bh.m1386y("Sending wrapped url hit: " + str);
                } catch (Throwable e) {
                    bh.m1382c("Error wrapping URL for testing.", e);
                    return false;
                }
            }
            this.XP.bC(str);
            return true;
        }
        bh.m1387z("Too many urls sent too quickly with the TagManagerSender, rate limiting invoked.");
        return false;
    }
}
