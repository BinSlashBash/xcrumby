package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Hex;

/* renamed from: com.google.android.gms.internal.j */
public abstract class C1394j extends C0920i {
    private static Method jR;
    private static Method jS;
    private static Method jT;
    private static Method jU;
    private static Method jV;
    private static Method jW;
    private static String jX;
    private static C0415p jY;
    static boolean jZ;
    private static long startTime;

    /* renamed from: com.google.android.gms.internal.j.a */
    static class C0406a extends Exception {
        public C0406a(Throwable th) {
            super(th);
        }
    }

    static {
        startTime = 0;
        jZ = false;
    }

    protected C1394j(Context context, C0412n c0412n, C0413o c0413o) {
        super(context, c0412n, c0413o);
    }

    static String m3120a(Context context, C0412n c0412n) throws C0406a {
        if (jT == null) {
            throw new C0406a();
        }
        try {
            ByteBuffer byteBuffer = (ByteBuffer) jT.invoke(null, new Object[]{context});
            if (byteBuffer != null) {
                return c0412n.m1184a(byteBuffer.array(), true);
            }
            throw new C0406a();
        } catch (Throwable e) {
            throw new C0406a(e);
        } catch (Throwable e2) {
            throw new C0406a(e2);
        }
    }

    static ArrayList<Long> m3121a(MotionEvent motionEvent, DisplayMetrics displayMetrics) throws C0406a {
        if (jU == null || motionEvent == null) {
            throw new C0406a();
        }
        try {
            return (ArrayList) jU.invoke(null, new Object[]{motionEvent, displayMetrics});
        } catch (Throwable e) {
            throw new C0406a(e);
        } catch (Throwable e2) {
            throw new C0406a(e2);
        }
    }

    protected static synchronized void m3122a(String str, Context context, C0412n c0412n) {
        synchronized (C1394j.class) {
            if (!jZ) {
                try {
                    jY = new C0415p(c0412n, null);
                    jX = str;
                    C1394j.m3126e(context);
                    startTime = C1394j.m3128w().longValue();
                    jZ = true;
                } catch (C0406a e) {
                } catch (UnsupportedOperationException e2) {
                }
            }
        }
    }

    static String m3123b(Context context, C0412n c0412n) throws C0406a {
        if (jW == null) {
            throw new C0406a();
        }
        try {
            ByteBuffer byteBuffer = (ByteBuffer) jW.invoke(null, new Object[]{context});
            if (byteBuffer != null) {
                return c0412n.m1184a(byteBuffer.array(), true);
            }
            throw new C0406a();
        } catch (Throwable e) {
            throw new C0406a(e);
        } catch (Throwable e2) {
            throw new C0406a(e2);
        }
    }

    private static String m3124b(byte[] bArr, String str) throws C0406a {
        try {
            return new String(jY.m1191c(bArr, str), Hex.DEFAULT_CHARSET_NAME);
        } catch (Throwable e) {
            throw new C0406a(e);
        } catch (Throwable e2) {
            throw new C0406a(e2);
        }
    }

    static String m3125d(Context context) throws C0406a {
        if (jV == null) {
            throw new C0406a();
        }
        try {
            String str = (String) jV.invoke(null, new Object[]{context});
            if (str != null) {
                return str;
            }
            throw new C0406a();
        } catch (Throwable e) {
            throw new C0406a(e);
        } catch (Throwable e2) {
            throw new C0406a(e2);
        }
    }

    private static void m3126e(Context context) throws C0406a {
        try {
            byte[] b = jY.m1190b(C0416r.getKey());
            byte[] c = jY.m1191c(b, C0416r.m1192A());
            File cacheDir = context.getCacheDir();
            if (cacheDir == null) {
                cacheDir = context.getDir("dex", 0);
                if (cacheDir == null) {
                    throw new C0406a();
                }
            }
            File createTempFile = File.createTempFile("ads", ".jar", cacheDir);
            FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
            fileOutputStream.write(c, 0, c.length);
            fileOutputStream.close();
            DexClassLoader dexClassLoader = new DexClassLoader(createTempFile.getAbsolutePath(), cacheDir.getAbsolutePath(), null, context.getClassLoader());
            Class loadClass = dexClassLoader.loadClass(C1394j.m3124b(b, C0416r.m1193B()));
            Class loadClass2 = dexClassLoader.loadClass(C1394j.m3124b(b, C0416r.m1199H()));
            Class loadClass3 = dexClassLoader.loadClass(C1394j.m3124b(b, C0416r.m1197F()));
            Class loadClass4 = dexClassLoader.loadClass(C1394j.m3124b(b, C0416r.m1203L()));
            Class loadClass5 = dexClassLoader.loadClass(C1394j.m3124b(b, C0416r.m1195D()));
            Class loadClass6 = dexClassLoader.loadClass(C1394j.m3124b(b, C0416r.m1201J()));
            jR = loadClass.getMethod(C1394j.m3124b(b, C0416r.m1194C()), new Class[0]);
            jS = loadClass2.getMethod(C1394j.m3124b(b, C0416r.m1200I()), new Class[0]);
            jT = loadClass3.getMethod(C1394j.m3124b(b, C0416r.m1198G()), new Class[]{Context.class});
            jU = loadClass4.getMethod(C1394j.m3124b(b, C0416r.m1204M()), new Class[]{MotionEvent.class, DisplayMetrics.class});
            jV = loadClass5.getMethod(C1394j.m3124b(b, C0416r.m1196E()), new Class[]{Context.class});
            jW = loadClass6.getMethod(C1394j.m3124b(b, C0416r.m1202K()), new Class[]{Context.class});
            String name = createTempFile.getName();
            createTempFile.delete();
            new File(cacheDir, name.replace(".jar", ".dex")).delete();
        } catch (Throwable e) {
            throw new C0406a(e);
        } catch (Throwable e2) {
            throw new C0406a(e2);
        } catch (Throwable e22) {
            throw new C0406a(e22);
        } catch (Throwable e222) {
            throw new C0406a(e222);
        } catch (Throwable e2222) {
            throw new C0406a(e2222);
        } catch (Throwable e22222) {
            throw new C0406a(e22222);
        }
    }

    static String m3127v() throws C0406a {
        if (jX != null) {
            return jX;
        }
        throw new C0406a();
    }

    static Long m3128w() throws C0406a {
        if (jR == null) {
            throw new C0406a();
        }
        try {
            return (Long) jR.invoke(null, new Object[0]);
        } catch (Throwable e) {
            throw new C0406a(e);
        } catch (Throwable e2) {
            throw new C0406a(e2);
        }
    }

    static String m3129x() throws C0406a {
        if (jS == null) {
            throw new C0406a();
        }
        try {
            return (String) jS.invoke(null, new Object[0]);
        } catch (Throwable e) {
            throw new C0406a(e);
        } catch (Throwable e2) {
            throw new C0406a(e2);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void m3130b(android.content.Context r4) {
        /*
        r3 = this;
        r0 = 1;
        r1 = com.google.android.gms.internal.C1394j.m3129x();	 Catch:{ a -> 0x002f, IOException -> 0x0027 }
        r3.m2299a(r0, r1);	 Catch:{ a -> 0x002f, IOException -> 0x0027 }
    L_0x0008:
        r0 = 2;
        r1 = com.google.android.gms.internal.C1394j.m3127v();	 Catch:{ a -> 0x002d, IOException -> 0x0027 }
        r3.m2299a(r0, r1);	 Catch:{ a -> 0x002d, IOException -> 0x0027 }
    L_0x0010:
        r0 = 25;
        r1 = com.google.android.gms.internal.C1394j.m3128w();	 Catch:{ a -> 0x002b, IOException -> 0x0027 }
        r1 = r1.longValue();	 Catch:{ a -> 0x002b, IOException -> 0x0027 }
        r3.m2298a(r0, r1);	 Catch:{ a -> 0x002b, IOException -> 0x0027 }
    L_0x001d:
        r0 = 24;
        r1 = com.google.android.gms.internal.C1394j.m3125d(r4);	 Catch:{ a -> 0x0029, IOException -> 0x0027 }
        r3.m2299a(r0, r1);	 Catch:{ a -> 0x0029, IOException -> 0x0027 }
    L_0x0026:
        return;
    L_0x0027:
        r0 = move-exception;
        goto L_0x0026;
    L_0x0029:
        r0 = move-exception;
        goto L_0x0026;
    L_0x002b:
        r0 = move-exception;
        goto L_0x001d;
    L_0x002d:
        r0 = move-exception;
        goto L_0x0010;
    L_0x002f:
        r0 = move-exception;
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.j.b(android.content.Context):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void m3131c(android.content.Context r7) {
        /*
        r6 = this;
        r0 = 2;
        r1 = com.google.android.gms.internal.C1394j.m3127v();	 Catch:{ a -> 0x0097, IOException -> 0x008a }
        r6.m2299a(r0, r1);	 Catch:{ a -> 0x0097, IOException -> 0x008a }
    L_0x0008:
        r0 = 1;
        r1 = com.google.android.gms.internal.C1394j.m3129x();	 Catch:{ a -> 0x0094, IOException -> 0x008a }
        r6.m2299a(r0, r1);	 Catch:{ a -> 0x0094, IOException -> 0x008a }
    L_0x0010:
        r0 = com.google.android.gms.internal.C1394j.m3128w();	 Catch:{ a -> 0x0092, IOException -> 0x008a }
        r0 = r0.longValue();	 Catch:{ a -> 0x0092, IOException -> 0x008a }
        r2 = 25;
        r6.m2298a(r2, r0);	 Catch:{ a -> 0x0092, IOException -> 0x008a }
        r2 = startTime;	 Catch:{ a -> 0x0092, IOException -> 0x008a }
        r4 = 0;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 == 0) goto L_0x0034;
    L_0x0025:
        r2 = 17;
        r3 = startTime;	 Catch:{ a -> 0x0092, IOException -> 0x008a }
        r0 = r0 - r3;
        r6.m2298a(r2, r0);	 Catch:{ a -> 0x0092, IOException -> 0x008a }
        r0 = 23;
        r1 = startTime;	 Catch:{ a -> 0x0092, IOException -> 0x008a }
        r6.m2298a(r0, r1);	 Catch:{ a -> 0x0092, IOException -> 0x008a }
    L_0x0034:
        r0 = r6.jN;	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r1 = r6.jO;	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r1 = com.google.android.gms.internal.C1394j.m3121a(r0, r1);	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r2 = 14;
        r0 = 0;
        r0 = r1.get(r0);	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r0 = (java.lang.Long) r0;	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r3 = r0.longValue();	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r6.m2298a(r2, r3);	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r2 = 15;
        r0 = 1;
        r0 = r1.get(r0);	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r0 = (java.lang.Long) r0;	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r3 = r0.longValue();	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r6.m2298a(r2, r3);	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r0 = r1.size();	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r2 = 3;
        if (r0 < r2) goto L_0x0073;
    L_0x0063:
        r2 = 16;
        r0 = 2;
        r0 = r1.get(r0);	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r0 = (java.lang.Long) r0;	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r0 = r0.longValue();	 Catch:{ a -> 0x0090, IOException -> 0x008a }
        r6.m2298a(r2, r0);	 Catch:{ a -> 0x0090, IOException -> 0x008a }
    L_0x0073:
        r0 = 27;
        r1 = r6.jP;	 Catch:{ a -> 0x008e, IOException -> 0x008a }
        r1 = com.google.android.gms.internal.C1394j.m3120a(r7, r1);	 Catch:{ a -> 0x008e, IOException -> 0x008a }
        r6.m2299a(r0, r1);	 Catch:{ a -> 0x008e, IOException -> 0x008a }
    L_0x007e:
        r0 = 29;
        r1 = r6.jP;	 Catch:{ a -> 0x008c, IOException -> 0x008a }
        r1 = com.google.android.gms.internal.C1394j.m3123b(r7, r1);	 Catch:{ a -> 0x008c, IOException -> 0x008a }
        r6.m2299a(r0, r1);	 Catch:{ a -> 0x008c, IOException -> 0x008a }
    L_0x0089:
        return;
    L_0x008a:
        r0 = move-exception;
        goto L_0x0089;
    L_0x008c:
        r0 = move-exception;
        goto L_0x0089;
    L_0x008e:
        r0 = move-exception;
        goto L_0x007e;
    L_0x0090:
        r0 = move-exception;
        goto L_0x0073;
    L_0x0092:
        r0 = move-exception;
        goto L_0x0034;
    L_0x0094:
        r0 = move-exception;
        goto L_0x0010;
    L_0x0097:
        r0 = move-exception;
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.j.c(android.content.Context):void");
    }
}
