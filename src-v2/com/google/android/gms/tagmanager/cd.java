/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.tagmanager;

import android.net.Uri;
import com.google.android.gms.tagmanager.bh;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

class cd {
    private static cd YP;
    private volatile String WJ;
    private volatile a YQ;
    private volatile String YR;
    private volatile String YS;

    cd() {
        this.clear();
    }

    private String bI(String string2) {
        return string2.split("&")[0].split("=")[1];
    }

    private String h(Uri uri) {
        return uri.getQuery().replace("&gtm_debug=x", "");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static cd kT() {
        synchronized (cd.class) {
            if (YP != null) return YP;
            YP = new cd();
            return YP;
        }
    }

    void clear() {
        this.YQ = a.YT;
        this.YR = null;
        this.WJ = null;
        this.YS = null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    boolean g(Uri var1_1) {
        var2_3 = true;
        // MONITORENTER : this
        try {
            var3_4 = URLDecoder.decode(var1_1.toString(), "UTF-8");
            ** if (!var3_4.matches((String)"^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) goto lbl-1000
        }
        catch (UnsupportedEncodingException var1_2) {
            return false;
        }
lbl-1000: // 1 sources:
        {
            bh.y("Container preview url: " + var3_4);
            this.YQ = var3_4.matches(".*?&gtm_debug=x$") != false ? a.YV : a.YU;
            this.YS = this.h(var1_1);
            if (this.YQ == a.YU || this.YQ == a.YV) {
                this.YR = "/r?" + this.YS;
            }
            this.WJ = this.bI(this.YS);
            // MONITOREXIT : this
            return var2_3;
        }
lbl-1000: // 1 sources:
        {
        }
        if (!var3_4.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
            bh.z("Invalid preview uri: " + var3_4);
            return false;
        }
        if (this.bI(var1_1.getQuery()).equals(this.WJ) == false) return false;
        bh.y("Exit preview mode for container: " + this.WJ);
        this.YQ = a.YT;
        this.YR = null;
        return var2_3;
    }

    String getContainerId() {
        return this.WJ;
    }

    a kU() {
        return this.YQ;
    }

    String kV() {
        return this.YR;
    }

    static enum a {
        YT,
        YU,
        YV;
        

        private a() {
        }
    }

}

