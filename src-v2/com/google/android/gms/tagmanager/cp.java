/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.internal.c;
import com.google.android.gms.internal.it;
import com.google.android.gms.internal.ks;
import com.google.android.gms.internal.kt;
import com.google.android.gms.tagmanager.ba;
import com.google.android.gms.tagmanager.bg;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.cd;
import com.google.android.gms.tagmanager.cq;
import com.google.android.gms.tagmanager.o;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

class cp
implements o.f {
    private final String WJ;
    private bg<it.a> Zf;
    private final ExecutorService Zm;
    private final Context mContext;

    cp(Context context, String string2) {
        this.mContext = context;
        this.WJ = string2;
        this.Zm = Executors.newSingleThreadExecutor();
    }

    private cq.c a(ByteArrayOutputStream object) {
        try {
            object = ba.bG(object.toString("UTF-8"));
            return object;
        }
        catch (UnsupportedEncodingException var1_2) {
            bh.v("Tried to convert binary resource to string for JSON parsing; not UTF-8 format");
            return null;
        }
        catch (JSONException var1_3) {
            bh.z("Resource is a UTF-8 encoded string but doesn't contain a JSON container");
            return null;
        }
    }

    private cq.c k(byte[] object) {
        try {
            object = cq.b(c.f.a((byte[])object));
            return object;
        }
        catch (ks var1_2) {
            bh.z("Resource doesn't contain a binary container");
            return null;
        }
        catch (cq.g var1_3) {
            bh.z("Resource doesn't contain a binary container");
            return null;
        }
    }

    @Override
    public void a(bg<it.a> bg2) {
        this.Zf = bg2;
    }

    @Override
    public void b(final it.a a2) {
        this.Zm.execute(new Runnable(){

            @Override
            public void run() {
                cp.this.c(a2);
            }
        });
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    boolean c(it.a a2) {
        FileOutputStream fileOutputStream;
        File file = this.lc();
        try {
            fileOutputStream = new FileOutputStream(file);
        }
        catch (FileNotFoundException var1_2) {
            bh.w("Error opening resource file for writing");
            return false;
        }
        fileOutputStream.write(kt.d(a2));
        try {
            fileOutputStream.close();
            return true;
        }
        catch (IOException var1_3) {
            bh.z("error closing stream for writing resource to disk");
            return true;
        }
        catch (IOException iOException) {
            try {
                bh.z("Error writing resource to disk. Removing resource from disk.");
                file.delete();
            }
            catch (Throwable var1_6) {
                try {
                    fileOutputStream.close();
                }
                catch (IOException var2_9) {
                    bh.z("error closing stream for writing resource to disk");
                    throw var1_6;
                }
                throw var1_6;
            }
            try {
                fileOutputStream.close();
                return false;
            }
            catch (IOException var1_5) {
                bh.z("error closing stream for writing resource to disk");
                return false;
            }
        }
    }

    @Override
    public cq.c ca(int n2) {
        Object object;
        block4 : {
            bh.y("Atttempting to load container from resource ID " + n2);
            Object object2 = this.mContext.getResources().openRawResource(n2);
            object = new ByteArrayOutputStream();
            cq.b((InputStream)object2, (OutputStream)object);
            object2 = this.a((ByteArrayOutputStream)object);
            if (object2 == null) break block4;
            return object2;
        }
        try {
            object = this.k(object.toByteArray());
            return object;
        }
        catch (IOException var2_4) {
            bh.z("Error reading default container resource with ID " + n2);
            return null;
        }
        catch (Resources.NotFoundException var2_5) {
            bh.z("No default container resource found.");
            return null;
        }
    }

    @Override
    public void km() {
        this.Zm.execute(new Runnable(){

            @Override
            public void run() {
                cp.this.lb();
            }
        });
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void lb() {
        if (this.Zf == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        this.Zf.kl();
        bh.y("Start loading resource from disk ...");
        if ((cd.kT().kU() == cd.a.YU || cd.kT().kU() == cd.a.YV) && this.WJ.equals(cd.kT().getContainerId())) {
            this.Zf.a(bg.a.Yy);
            return;
        }
        try {
            var1_1 = new FileInputStream(this.lc());
        }
        catch (FileNotFoundException var1_2) {
            bh.v("resource not on disk");
            this.Zf.a(bg.a.Yy);
            return;
        }
        var2_6 = new ByteArrayOutputStream();
        cq.b(var1_1, var2_6);
        this.Zf.i(it.a.l(var2_6.toByteArray()));
        try {
            var1_1.close();
        }
        catch (IOException var1_3) {
            bh.z("error closing stream for reading resource from disk");
        }
        ** GOTO lbl40
        catch (IOException var2_7) {
            try {
                bh.z("error reading resource from disk");
                this.Zf.a(bg.a.Yz);
            }
            catch (Throwable var2_8) {
                try {
                    var1_1.close();
                }
                catch (IOException var1_5) {
                    bh.z("error closing stream for reading resource from disk");
                    throw var2_8;
                }
                throw var2_8;
            }
            try {
                var1_1.close();
            }
            catch (IOException var1_4) {
                bh.z("error closing stream for reading resource from disk");
            }
lbl40: // 4 sources:
            bh.y("Load resource from disk finished.");
            return;
        }
    }

    File lc() {
        String string2 = "resource_" + this.WJ;
        return new File(this.mContext.getDir("google_tagmanager", 0), string2);
    }

    @Override
    public void release() {
        synchronized (this) {
            this.Zm.shutdown();
            return;
        }
    }

}

