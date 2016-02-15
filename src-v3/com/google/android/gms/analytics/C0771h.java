package com.google.android.gms.analytics;

import android.content.Context;
import android.support.v4.media.TransportMediator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/* renamed from: com.google.android.gms.analytics.h */
class C0771h implements C0214m {
    private static final Object sf;
    private static C0771h st;
    private final Context mContext;
    private String su;
    private boolean sv;
    private final Object sw;

    /* renamed from: com.google.android.gms.analytics.h.1 */
    class C02091 extends Thread {
        final /* synthetic */ C0771h sx;

        C02091(C0771h c0771h, String str) {
            this.sx = c0771h;
            super(str);
        }

        public void run() {
            synchronized (this.sx.sw) {
                this.sx.su = this.sx.cf();
                this.sx.sv = true;
                this.sx.sw.notifyAll();
            }
        }
    }

    static {
        sf = new Object();
    }

    protected C0771h(Context context) {
        this.sv = false;
        this.sw = new Object();
        this.mContext = context;
        ce();
    }

    private boolean m1588D(String str) {
        try {
            aa.m34y("Storing clientId.");
            FileOutputStream openFileOutput = this.mContext.openFileOutput("gaClientId", 0);
            openFileOutput.write(str.getBytes());
            openFileOutput.close();
            return true;
        } catch (FileNotFoundException e) {
            aa.m32w("Error creating clientId file.");
            return false;
        } catch (IOException e2) {
            aa.m32w("Error writing to clientId file.");
            return false;
        }
    }

    public static C0771h cb() {
        C0771h c0771h;
        synchronized (sf) {
            c0771h = st;
        }
        return c0771h;
    }

    private String cc() {
        if (!this.sv) {
            synchronized (this.sw) {
                if (!this.sv) {
                    aa.m34y("Waiting for clientId to load");
                    do {
                        try {
                            this.sw.wait();
                        } catch (InterruptedException e) {
                            aa.m32w("Exception while waiting for clientId: " + e);
                        }
                    } while (!this.sv);
                }
            }
        }
        aa.m34y("Loaded clientId");
        return this.su;
    }

    private void ce() {
        new C02091(this, "client_id_fetcher").start();
    }

    public static void m1592n(Context context) {
        synchronized (sf) {
            if (st == null) {
                st = new C0771h(context);
            }
        }
    }

    public boolean m1593C(String str) {
        return "&cid".equals(str);
    }

    protected String cd() {
        String toLowerCase = UUID.randomUUID().toString().toLowerCase();
        try {
            return !m1588D(toLowerCase) ? "0" : toLowerCase;
        } catch (Exception e) {
            return null;
        }
    }

    String cf() {
        String str = null;
        try {
            FileInputStream openFileInput = this.mContext.openFileInput("gaClientId");
            byte[] bArr = new byte[TransportMediator.FLAG_KEY_MEDIA_NEXT];
            int read = openFileInput.read(bArr, 0, TransportMediator.FLAG_KEY_MEDIA_NEXT);
            if (openFileInput.available() > 0) {
                aa.m32w("clientId file seems corrupted, deleting it.");
                openFileInput.close();
                this.mContext.deleteFile("gaClientId");
            } else if (read <= 0) {
                aa.m32w("clientId file seems empty, deleting it.");
                openFileInput.close();
                this.mContext.deleteFile("gaClientId");
            } else {
                String str2 = new String(bArr, 0, read);
                try {
                    openFileInput.close();
                    str = str2;
                } catch (FileNotFoundException e) {
                    str = str2;
                } catch (IOException e2) {
                    str = str2;
                    aa.m32w("Error reading clientId file, deleting it.");
                    this.mContext.deleteFile("gaClientId");
                }
            }
        } catch (FileNotFoundException e3) {
        } catch (IOException e4) {
            aa.m32w("Error reading clientId file, deleting it.");
            this.mContext.deleteFile("gaClientId");
        }
        return str == null ? cd() : str;
    }

    public String getValue(String field) {
        return "&cid".equals(field) ? cc() : null;
    }
}
