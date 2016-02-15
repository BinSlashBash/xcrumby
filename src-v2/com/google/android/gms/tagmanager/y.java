/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.tagmanager.aq;
import com.google.android.gms.tagmanager.ar;
import com.google.android.gms.tagmanager.as;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.cf;
import com.google.android.gms.tagmanager.cv;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class y
implements aq {
    private static y XM;
    private static final Object sf;
    private String XN;
    private String XO;
    private ar XP;
    private cf Xa;

    static {
        sf = new Object();
    }

    private y(Context context) {
        this(as.H(context), new cv());
    }

    y(ar ar2, cf cf2) {
        this.XP = ar2;
        this.Xa = cf2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static aq F(Context object) {
        Object object2 = sf;
        synchronized (object2) {
            if (XM != null) return XM;
            XM = new y((Context)object);
            return XM;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean bz(String string2) {
        if (!this.Xa.cS()) {
            bh.z("Too many urls sent too quickly with the TagManagerSender, rate limiting invoked.");
            return false;
        }
        String string3 = string2;
        if (this.XN != null) {
            string3 = string2;
            if (this.XO != null) {
                string3 = this.XN + "?" + this.XO + "=" + URLEncoder.encode(string2, "UTF-8");
                bh.y("Sending wrapped url hit: " + string3);
            }
        }
        this.XP.bC(string3);
        return true;
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            bh.c("Error wrapping URL for testing.", unsupportedEncodingException);
            return false;
        }
    }
}

