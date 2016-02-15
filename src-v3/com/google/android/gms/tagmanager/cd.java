package com.google.android.gms.tagmanager;

import android.net.Uri;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.commons.codec.binary.Hex;

class cd {
    private static cd YP;
    private volatile String WJ;
    private volatile C0501a YQ;
    private volatile String YR;
    private volatile String YS;

    /* renamed from: com.google.android.gms.tagmanager.cd.a */
    enum C0501a {
        NONE,
        CONTAINER,
        CONTAINER_DEBUG
    }

    cd() {
        clear();
    }

    private String bI(String str) {
        return str.split("&")[0].split("=")[1];
    }

    private String m1399h(Uri uri) {
        return uri.getQuery().replace("&gtm_debug=x", UnsupportedUrlFragment.DISPLAY_NAME);
    }

    static cd kT() {
        cd cdVar;
        synchronized (cd.class) {
            if (YP == null) {
                YP = new cd();
            }
            cdVar = YP;
        }
        return cdVar;
    }

    void clear() {
        this.YQ = C0501a.NONE;
        this.YR = null;
        this.WJ = null;
        this.YS = null;
    }

    synchronized boolean m1400g(Uri uri) {
        boolean z = true;
        synchronized (this) {
            try {
                String decode = URLDecoder.decode(uri.toString(), Hex.DEFAULT_CHARSET_NAME);
                if (decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) {
                    bh.m1386y("Container preview url: " + decode);
                    if (decode.matches(".*?&gtm_debug=x$")) {
                        this.YQ = C0501a.CONTAINER_DEBUG;
                    } else {
                        this.YQ = C0501a.CONTAINER;
                    }
                    this.YS = m1399h(uri);
                    if (this.YQ == C0501a.CONTAINER || this.YQ == C0501a.CONTAINER_DEBUG) {
                        this.YR = "/r?" + this.YS;
                    }
                    this.WJ = bI(this.YS);
                } else {
                    if (!decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                        bh.m1387z("Invalid preview uri: " + decode);
                        z = false;
                    } else if (bI(uri.getQuery()).equals(this.WJ)) {
                        bh.m1386y("Exit preview mode for container: " + this.WJ);
                        this.YQ = C0501a.NONE;
                        this.YR = null;
                    } else {
                        z = false;
                    }
                }
            } catch (UnsupportedEncodingException e) {
                z = false;
            }
        }
        return z;
    }

    String getContainerId() {
        return this.WJ;
    }

    C0501a kU() {
        return this.YQ;
    }

    String kV() {
        return this.YR;
    }
}
