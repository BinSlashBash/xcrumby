package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.internal.C0339c.C1364j;
import com.google.android.gms.tagmanager.bg.C0499a;
import com.google.android.gms.tagmanager.cd.C0501a;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

class cn implements Runnable {
    private final String WJ;
    private volatile String Xg;
    private final bm Zd;
    private final String Ze;
    private bg<C1364j> Zf;
    private volatile C0529r Zg;
    private volatile String Zh;
    private final Context mContext;

    cn(Context context, String str, bm bmVar, C0529r c0529r) {
        this.mContext = context;
        this.Zd = bmVar;
        this.WJ = str;
        this.Zg = c0529r;
        this.Ze = "/r?id=" + str;
        this.Xg = this.Ze;
        this.Zh = null;
    }

    public cn(Context context, String str, C0529r c0529r) {
        this(context, str, new bm(), c0529r);
    }

    private boolean kW() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        bh.m1386y("...no network connectivity");
        return false;
    }

    private void kX() {
        if (kW()) {
            bh.m1386y("Start loading resource from network ...");
            String kY = kY();
            bl kH = this.Zd.kH();
            try {
                InputStream bD = kH.bD(kY);
                try {
                    OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    cq.m1423b(bD, byteArrayOutputStream);
                    C1364j b = C1364j.m2975b(byteArrayOutputStream.toByteArray());
                    bh.m1386y("Successfully loaded supplemented resource: " + b);
                    if (b.fK == null && b.fJ.length == 0) {
                        bh.m1386y("No change for container: " + this.WJ);
                    }
                    this.Zf.m1380i(b);
                    bh.m1386y("Load resource from network finished.");
                } catch (Throwable e) {
                    bh.m1382c("Error when parsing downloaded resources from url: " + kY + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e.getMessage(), e);
                    this.Zf.m1379a(C0499a.SERVER_ERROR);
                    kH.close();
                }
            } catch (FileNotFoundException e2) {
                bh.m1387z("No data is retrieved from the given url: " + kY + ". Make sure container_id: " + this.WJ + " is correct.");
                this.Zf.m1379a(C0499a.SERVER_ERROR);
            } catch (Throwable e3) {
                bh.m1382c("Error when loading resources from url: " + kY + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + e3.getMessage(), e3);
                this.Zf.m1379a(C0499a.IO_ERROR);
            } finally {
                kH.close();
            }
        } else {
            this.Zf.m1379a(C0499a.NOT_AVAILABLE);
        }
    }

    void m1405a(bg<C1364j> bgVar) {
        this.Zf = bgVar;
    }

    void bJ(String str) {
        bh.m1383v("Setting previous container version: " + str);
        this.Zh = str;
    }

    void bu(String str) {
        if (str == null) {
            this.Xg = this.Ze;
            return;
        }
        bh.m1383v("Setting CTFE URL path: " + str);
        this.Xg = str;
    }

    String kY() {
        String str = this.Zg.kn() + this.Xg + "&v=a65833898";
        if (!(this.Zh == null || this.Zh.trim().equals(UnsupportedUrlFragment.DISPLAY_NAME))) {
            str = str + "&pv=" + this.Zh;
        }
        return cd.kT().kU().equals(C0501a.CONTAINER_DEBUG) ? str + "&gtm_debug=x" : str;
    }

    public void run() {
        if (this.Zf == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        this.Zf.kl();
        kX();
    }
}
