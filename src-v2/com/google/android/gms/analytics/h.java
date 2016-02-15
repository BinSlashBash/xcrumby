/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.aa;
import com.google.android.gms.analytics.m;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

class h
implements m {
    private static final Object sf = new Object();
    private static h st;
    private final Context mContext;
    private String su;
    private boolean sv = false;
    private final Object sw = new Object();

    protected h(Context context) {
        this.mContext = context;
        this.ce();
    }

    private boolean D(String string2) {
        try {
            aa.y("Storing clientId.");
            FileOutputStream fileOutputStream = this.mContext.openFileOutput("gaClientId", 0);
            fileOutputStream.write(string2.getBytes());
            fileOutputStream.close();
            return true;
        }
        catch (FileNotFoundException var1_2) {
            aa.w("Error creating clientId file.");
            return false;
        }
        catch (IOException var1_3) {
            aa.w("Error writing to clientId file.");
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static h cb() {
        Object object = sf;
        synchronized (object) {
            return st;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String cc() {
        if (!this.sv) {
            Object object = this.sw;
            synchronized (object) {
                if (!this.sv) {
                    aa.y("Waiting for clientId to load");
                    do {
                        try {
                            this.sw.wait();
                            continue;
                        }
                        catch (InterruptedException var2_2) {
                            aa.w("Exception while waiting for clientId: " + var2_2);
                        }
                    } while (!this.sv);
                }
            }
        }
        aa.y("Loaded clientId");
        return this.su;
    }

    private void ce() {
        new Thread("client_id_fetcher"){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object = h.this.sw;
                synchronized (object) {
                    h.this.su = h.this.cf();
                    h.this.sv = true;
                    h.this.sw.notifyAll();
                    return;
                }
            }
        }.start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void n(Context context) {
        Object object = sf;
        synchronized (object) {
            if (st == null) {
                st = new h(context);
            }
            return;
        }
    }

    public boolean C(String string2) {
        return "&cid".equals(string2);
    }

    protected String cd() {
        String string2;
        String string3 = string2 = UUID.randomUUID().toString().toLowerCase();
        try {
            if (!this.D(string2)) {
                string3 = "0";
            }
            return string3;
        }
        catch (Exception var1_3) {
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    String cf() {
        block9 : {
            var4_1 = null;
            var3_2 = null;
            var5_5 = this.mContext.openFileInput("gaClientId");
            var2_6 = new byte[128];
            var1_9 = var5_5.read((byte[])var2_6, 0, 128);
            if (var5_5.available() > 0) {
                aa.w("clientId file seems corrupted, deleting it.");
                var5_5.close();
                this.mContext.deleteFile("gaClientId");
                var2_6 = var3_2;
                break block9;
            }
            if (var1_9 <= 0) {
                aa.w("clientId file seems empty, deleting it.");
                var5_5.close();
                this.mContext.deleteFile("gaClientId");
                var2_6 = var3_2;
                break block9;
            }
            var2_6 = new String((byte[])var2_6, 0, var1_9);
            var5_5.close();
            break block9;
            catch (IOException var2_7) {
                var2_6 = var4_1;
                ** GOTO lbl27
                catch (IOException var3_3) {}
lbl27: // 2 sources:
                aa.w("Error reading clientId file, deleting it.");
                this.mContext.deleteFile("gaClientId");
            }
            catch (FileNotFoundException var3_4) {
                break block9;
            }
            catch (FileNotFoundException var2_8) {
                var2_6 = var3_2;
            }
        }
        var3_2 = var2_6;
        if (var2_6 != null) return var3_2;
        return this.cd();
    }

    @Override
    public String getValue(String string2) {
        if ("&cid".equals(string2)) {
            return this.cc();
        }
        return null;
    }

}

