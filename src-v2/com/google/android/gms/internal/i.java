/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.view.MotionEvent
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.android.gms.internal.f;
import com.google.android.gms.internal.h;
import com.google.android.gms.internal.km;
import com.google.android.gms.internal.n;
import com.google.android.gms.internal.o;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class i
implements h {
    protected MotionEvent jN;
    protected DisplayMetrics jO;
    protected n jP;
    private o jQ;

    protected i(Context context, n n2, o o2) {
        this.jP = n2;
        this.jQ = o2;
        try {
            this.jO = context.getResources().getDisplayMetrics();
            return;
        }
        catch (UnsupportedOperationException var1_2) {
            this.jO = new DisplayMetrics();
            this.jO.density = 1.0f;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private String a(Context object, String string2, boolean bl2) {
        block12 : {
            this.t();
            if (bl2) {
                this.c((Context)object);
            } else {
                this.b((Context)object);
            }
            object = this.u();
            // MONITOREXIT : this
            if (object.length != 0) break block12;
            return Integer.toString(5);
        }
        try {
            return this.a((byte[])object, string2);
        }
        catch (NoSuchAlgorithmException var1_2) {
            return Integer.toString(7);
        }
        catch (UnsupportedEncodingException var1_3) {
            return Integer.toString(7);
        }
        catch (IOException var1_4) {
            return Integer.toString(3);
        }
    }

    private void t() {
        this.jQ.reset();
    }

    private byte[] u() throws IOException {
        return this.jQ.z();
    }

    @Override
    public String a(Context context) {
        return this.a(context, null, false);
    }

    @Override
    public String a(Context context, String string2) {
        return this.a(context, string2, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    String a(byte[] arrby, String string2) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
        Object object = arrby;
        if (arrby.length > 239) {
            this.t();
            this.a(20, 1);
            object = this.u();
        }
        if (object.length < 239) {
            arrby = new byte[239 - object.length];
            new SecureRandom().nextBytes(arrby);
            arrby = ByteBuffer.allocate(240).put((byte)object.length).put((byte[])object).put(arrby).array();
        } else {
            arrby = ByteBuffer.allocate(240).put((byte)object.length).put((byte[])object).array();
        }
        object = MessageDigest.getInstance("MD5");
        object.update(arrby);
        object = object.digest();
        arrby = ByteBuffer.allocate(256).put((byte[])object).put(arrby).array();
        object = new byte[256];
        new f().a(arrby, (byte[])object);
        if (string2 != null && string2.length() > 0) {
            this.a(string2, (byte[])object);
        }
        return this.jP.a((byte[])object, true);
    }

    @Override
    public void a(int n2, int n3, int n4) {
        if (this.jN != null) {
            this.jN.recycle();
        }
        this.jN = MotionEvent.obtain((long)0, (long)n4, (int)1, (float)((float)n2 * this.jO.density), (float)((float)n3 * this.jO.density), (float)0.0f, (float)0.0f, (int)0, (float)0.0f, (float)0.0f, (int)0, (int)0);
    }

    protected void a(int n2, long l2) throws IOException {
        this.jQ.b(n2, l2);
    }

    protected void a(int n2, String string2) throws IOException {
        this.jQ.b(n2, string2);
    }

    @Override
    public void a(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (this.jN != null) {
                this.jN.recycle();
            }
            this.jN = MotionEvent.obtain((MotionEvent)motionEvent);
        }
    }

    void a(String string2, byte[] arrby) throws UnsupportedEncodingException {
        String string3 = string2;
        if (string2.length() > 32) {
            string3 = string2.substring(0, 32);
        }
        new km(string3.getBytes("UTF-8")).m(arrby);
    }

    protected abstract void b(Context var1);

    protected abstract void c(Context var1);
}

