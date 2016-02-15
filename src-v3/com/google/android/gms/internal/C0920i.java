package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.crumby.GalleryViewer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.json.zip.JSONzip;

/* renamed from: com.google.android.gms.internal.i */
public abstract class C0920i implements C0401h {
    protected MotionEvent jN;
    protected DisplayMetrics jO;
    protected C0412n jP;
    private C0413o jQ;

    protected C0920i(Context context, C0412n c0412n, C0413o c0413o) {
        this.jP = c0412n;
        this.jQ = c0413o;
        try {
            this.jO = context.getResources().getDisplayMetrics();
        } catch (UnsupportedOperationException e) {
            this.jO = new DisplayMetrics();
            this.jO.density = GalleryViewer.PROGRESS_COMPLETED;
        }
    }

    private String m2291a(Context context, String str, boolean z) {
        try {
            byte[] u;
            synchronized (this) {
                m2292t();
                if (z) {
                    m2303c(context);
                } else {
                    m2302b(context);
                }
                u = m2293u();
            }
            return u.length == 0 ? Integer.toString(5) : m2296a(u, str);
        } catch (NoSuchAlgorithmException e) {
            return Integer.toString(7);
        } catch (UnsupportedEncodingException e2) {
            return Integer.toString(7);
        } catch (IOException e3) {
            return Integer.toString(3);
        }
    }

    private void m2292t() {
        this.jQ.reset();
    }

    private byte[] m2293u() throws IOException {
        return this.jQ.m1188z();
    }

    public String m2294a(Context context) {
        return m2291a(context, null, false);
    }

    public String m2295a(Context context, String str) {
        return m2291a(context, str, true);
    }

    String m2296a(byte[] bArr, String str) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {
        byte[] bArr2;
        if (bArr.length > 239) {
            m2292t();
            m2298a(20, 1);
            bArr = m2293u();
        }
        if (bArr.length < 239) {
            bArr2 = new byte[(239 - bArr.length)];
            new SecureRandom().nextBytes(bArr2);
            bArr2 = ByteBuffer.allocate(240).put((byte) bArr.length).put(bArr).put(bArr2).array();
        } else {
            bArr2 = ByteBuffer.allocate(240).put((byte) bArr.length).put(bArr).array();
        }
        MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
        instance.update(bArr2);
        bArr2 = ByteBuffer.allocate(JSONzip.end).put(instance.digest()).put(bArr2).array();
        byte[] bArr3 = new byte[JSONzip.end];
        new C0388f().m918a(bArr2, bArr3);
        if (str != null && str.length() > 0) {
            m2301a(str, bArr3);
        }
        return this.jP.m1184a(bArr3, true);
    }

    public void m2297a(int i, int i2, int i3) {
        if (this.jN != null) {
            this.jN.recycle();
        }
        this.jN = MotionEvent.obtain(0, (long) i3, 1, ((float) i) * this.jO.density, ((float) i2) * this.jO.density, 0.0f, 0.0f, 0, 0.0f, 0.0f, 0, 0);
    }

    protected void m2298a(int i, long j) throws IOException {
        this.jQ.m1186b(i, j);
    }

    protected void m2299a(int i, String str) throws IOException {
        this.jQ.m1187b(i, str);
    }

    public void m2300a(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (this.jN != null) {
                this.jN.recycle();
            }
            this.jN = MotionEvent.obtain(motionEvent);
        }
    }

    void m2301a(String str, byte[] bArr) throws UnsupportedEncodingException {
        if (str.length() > 32) {
            str = str.substring(0, 32);
        }
        new km(str.getBytes(Hex.DEFAULT_CHARSET_NAME)).m1122m(bArr);
    }

    protected abstract void m2302b(Context context);

    protected abstract void m2303c(Context context);
}
