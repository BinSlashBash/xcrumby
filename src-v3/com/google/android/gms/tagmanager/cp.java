package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import com.google.android.gms.internal.C0339c.C1360f;
import com.google.android.gms.internal.it.C1393a;
import com.google.android.gms.internal.ks;
import com.google.android.gms.tagmanager.C1418o.C1087f;
import com.google.android.gms.tagmanager.cq.C0509c;
import com.google.android.gms.tagmanager.cq.C0513g;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;

class cp implements C1087f {
    private final String WJ;
    private bg<C1393a> Zf;
    private final ExecutorService Zm;
    private final Context mContext;

    /* renamed from: com.google.android.gms.tagmanager.cp.1 */
    class C05041 implements Runnable {
        final /* synthetic */ cp Zn;

        C05041(cp cpVar) {
            this.Zn = cpVar;
        }

        public void run() {
            this.Zn.lb();
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.cp.2 */
    class C05052 implements Runnable {
        final /* synthetic */ cp Zn;
        final /* synthetic */ C1393a Zo;

        C05052(cp cpVar, C1393a c1393a) {
            this.Zn = cpVar;
            this.Zo = c1393a;
        }

        public void run() {
            this.Zn.m3215c(this.Zo);
        }
    }

    cp(Context context, String str) {
        this.mContext = context;
        this.WJ = str;
        this.Zm = Executors.newSingleThreadExecutor();
    }

    private C0509c m3211a(ByteArrayOutputStream byteArrayOutputStream) {
        C0509c c0509c = null;
        try {
            c0509c = ba.bG(byteArrayOutputStream.toString(Hex.DEFAULT_CHARSET_NAME));
        } catch (UnsupportedEncodingException e) {
            bh.m1383v("Tried to convert binary resource to string for JSON parsing; not UTF-8 format");
        } catch (JSONException e2) {
            bh.m1387z("Resource is a UTF-8 encoded string but doesn't contain a JSON container");
        }
        return c0509c;
    }

    private C0509c m3212k(byte[] bArr) {
        C0509c c0509c = null;
        try {
            c0509c = cq.m1422b(C1360f.m2952a(bArr));
        } catch (ks e) {
            bh.m1387z("Resource doesn't contain a binary container");
        } catch (C0513g e2) {
            bh.m1387z("Resource doesn't contain a binary container");
        }
        return c0509c;
    }

    public void m3213a(bg<C1393a> bgVar) {
        this.Zf = bgVar;
    }

    public void m3214b(C1393a c1393a) {
        this.Zm.execute(new C05052(this, c1393a));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    boolean m3215c(com.google.android.gms.internal.it.C1393a r5) {
        /*
        r4 = this;
        r0 = 0;
        r1 = r4.lc();
        r2 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x0016 }
        r2.<init>(r1);	 Catch:{ FileNotFoundException -> 0x0016 }
        r3 = com.google.android.gms.internal.kt.m1169d(r5);	 Catch:{ IOException -> 0x0024 }
        r2.write(r3);	 Catch:{ IOException -> 0x0024 }
        r0 = 1;
        r2.close();	 Catch:{ IOException -> 0x001d }
    L_0x0015:
        return r0;
    L_0x0016:
        r1 = move-exception;
        r1 = "Error opening resource file for writing";
        com.google.android.gms.tagmanager.bh.m1384w(r1);
        goto L_0x0015;
    L_0x001d:
        r1 = move-exception;
        r1 = "error closing stream for writing resource to disk";
        com.google.android.gms.tagmanager.bh.m1387z(r1);
        goto L_0x0015;
    L_0x0024:
        r3 = move-exception;
        r3 = "Error writing resource to disk. Removing resource from disk.";
        com.google.android.gms.tagmanager.bh.m1387z(r3);	 Catch:{ all -> 0x0038 }
        r1.delete();	 Catch:{ all -> 0x0038 }
        r2.close();	 Catch:{ IOException -> 0x0031 }
        goto L_0x0015;
    L_0x0031:
        r1 = move-exception;
        r1 = "error closing stream for writing resource to disk";
        com.google.android.gms.tagmanager.bh.m1387z(r1);
        goto L_0x0015;
    L_0x0038:
        r0 = move-exception;
        r2.close();	 Catch:{ IOException -> 0x003d }
    L_0x003c:
        throw r0;
    L_0x003d:
        r1 = move-exception;
        r1 = "error closing stream for writing resource to disk";
        com.google.android.gms.tagmanager.bh.m1387z(r1);
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.cp.c(com.google.android.gms.internal.it$a):boolean");
    }

    public C0509c ca(int i) {
        bh.m1386y("Atttempting to load container from resource ID " + i);
        try {
            InputStream openRawResource = this.mContext.getResources().openRawResource(i);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            cq.m1423b(openRawResource, byteArrayOutputStream);
            C0509c a = m3211a(byteArrayOutputStream);
            return a != null ? a : m3212k(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            bh.m1387z("Error reading default container resource with ID " + i);
            return null;
        } catch (NotFoundException e2) {
            bh.m1387z("No default container resource found.");
            return null;
        }
    }

    public void km() {
        this.Zm.execute(new C05041(this));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void lb() {
        /*
        r3 = this;
        r0 = r3.Zf;
        if (r0 != 0) goto L_0x000c;
    L_0x0004:
        r0 = new java.lang.IllegalStateException;
        r1 = "callback must be set before execute";
        r0.<init>(r1);
        throw r0;
    L_0x000c:
        r0 = r3.Zf;
        r0.kl();
        r0 = "Start loading resource from disk ...";
        com.google.android.gms.tagmanager.bh.m1386y(r0);
        r0 = com.google.android.gms.tagmanager.cd.kT();
        r0 = r0.kU();
        r1 = com.google.android.gms.tagmanager.cd.C0501a.CONTAINER;
        if (r0 == r1) goto L_0x002e;
    L_0x0022:
        r0 = com.google.android.gms.tagmanager.cd.kT();
        r0 = r0.kU();
        r1 = com.google.android.gms.tagmanager.cd.C0501a.CONTAINER_DEBUG;
        if (r0 != r1) goto L_0x0046;
    L_0x002e:
        r0 = r3.WJ;
        r1 = com.google.android.gms.tagmanager.cd.kT();
        r1 = r1.getContainerId();
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x0046;
    L_0x003e:
        r0 = r3.Zf;
        r1 = com.google.android.gms.tagmanager.bg.C0499a.NOT_AVAILABLE;
        r0.m1379a(r1);
    L_0x0045:
        return;
    L_0x0046:
        r1 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x006d }
        r0 = r3.lc();	 Catch:{ FileNotFoundException -> 0x006d }
        r1.<init>(r0);	 Catch:{ FileNotFoundException -> 0x006d }
        r0 = new java.io.ByteArrayOutputStream;	 Catch:{ IOException -> 0x0082 }
        r0.<init>();	 Catch:{ IOException -> 0x0082 }
        com.google.android.gms.tagmanager.cq.m1423b(r1, r0);	 Catch:{ IOException -> 0x0082 }
        r2 = r3.Zf;	 Catch:{ IOException -> 0x0082 }
        r0 = r0.toByteArray();	 Catch:{ IOException -> 0x0082 }
        r0 = com.google.android.gms.internal.it.C1393a.m3115l(r0);	 Catch:{ IOException -> 0x0082 }
        r2.m1380i(r0);	 Catch:{ IOException -> 0x0082 }
        r1.close();	 Catch:{ IOException -> 0x007b }
    L_0x0067:
        r0 = "Load resource from disk finished.";
        com.google.android.gms.tagmanager.bh.m1386y(r0);
        goto L_0x0045;
    L_0x006d:
        r0 = move-exception;
        r0 = "resource not on disk";
        com.google.android.gms.tagmanager.bh.m1383v(r0);
        r0 = r3.Zf;
        r1 = com.google.android.gms.tagmanager.bg.C0499a.NOT_AVAILABLE;
        r0.m1379a(r1);
        goto L_0x0045;
    L_0x007b:
        r0 = move-exception;
        r0 = "error closing stream for reading resource from disk";
        com.google.android.gms.tagmanager.bh.m1387z(r0);
        goto L_0x0067;
    L_0x0082:
        r0 = move-exception;
        r0 = "error reading resource from disk";
        com.google.android.gms.tagmanager.bh.m1387z(r0);	 Catch:{ all -> 0x009a }
        r0 = r3.Zf;	 Catch:{ all -> 0x009a }
        r2 = com.google.android.gms.tagmanager.bg.C0499a.IO_ERROR;	 Catch:{ all -> 0x009a }
        r0.m1379a(r2);	 Catch:{ all -> 0x009a }
        r1.close();	 Catch:{ IOException -> 0x0093 }
        goto L_0x0067;
    L_0x0093:
        r0 = move-exception;
        r0 = "error closing stream for reading resource from disk";
        com.google.android.gms.tagmanager.bh.m1387z(r0);
        goto L_0x0067;
    L_0x009a:
        r0 = move-exception;
        r1.close();	 Catch:{ IOException -> 0x009f }
    L_0x009e:
        throw r0;
    L_0x009f:
        r1 = move-exception;
        r1 = "error closing stream for reading resource from disk";
        com.google.android.gms.tagmanager.bh.m1387z(r1);
        goto L_0x009e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.cp.lb():void");
    }

    File lc() {
        return new File(this.mContext.getDir("google_tagmanager", 0), "resource_" + this.WJ);
    }

    public synchronized void release() {
        this.Zm.shutdown();
    }
}
