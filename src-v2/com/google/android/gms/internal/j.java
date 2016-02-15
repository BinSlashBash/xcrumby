/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.DisplayMetrics
 *  android.view.MotionEvent
 *  dalvik.system.DexClassLoader
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.google.android.gms.internal.i;
import com.google.android.gms.internal.n;
import com.google.android.gms.internal.o;
import com.google.android.gms.internal.p;
import com.google.android.gms.internal.r;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;

public abstract class j
extends i {
    private static Method jR;
    private static Method jS;
    private static Method jT;
    private static Method jU;
    private static Method jV;
    private static Method jW;
    private static String jX;
    private static p jY;
    static boolean jZ;
    private static long startTime;

    static {
        startTime = 0;
        jZ = false;
    }

    protected j(Context context, n n2, o o2) {
        super(context, n2, o2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static String a(Context object, n n2) throws a {
        if (jT == null) {
            throw new a();
        }
        try {
            object = (ByteBuffer)jT.invoke(null, object);
            if (object != null) return n2.a(object.array(), true);
            throw new a();
        }
        catch (IllegalAccessException var0_1) {
            throw new a(var0_1);
        }
        catch (InvocationTargetException var0_2) {
            throw new a(var0_2);
        }
    }

    static ArrayList<Long> a(MotionEvent object, DisplayMetrics displayMetrics) throws a {
        if (jU == null || object == null) {
            throw new a();
        }
        try {
            object = (ArrayList)jU.invoke(null, new Object[]{object, displayMetrics});
            return object;
        }
        catch (IllegalAccessException var0_1) {
            throw new a(var0_1);
        }
        catch (InvocationTargetException var0_2) {
            throw new a(var0_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static void a(String string2, Context context, n n2) {
        synchronized (j.class) {
            boolean bl2 = jZ;
            if (!bl2) {
                try {
                    void var2_4;
                    void var1_3;
                    jY = new p((n)var2_4, null);
                    jX = string2;
                    j.e((Context)var1_3);
                    startTime = j.w();
                    jZ = true;
                }
                catch (UnsupportedOperationException var0_1) {
                }
                catch (a var0_2) {}
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static String b(Context object, n n2) throws a {
        if (jW == null) {
            throw new a();
        }
        try {
            object = (ByteBuffer)jW.invoke(null, object);
            if (object != null) return n2.a(object.array(), true);
            throw new a();
        }
        catch (IllegalAccessException var0_1) {
            throw new a(var0_1);
        }
        catch (InvocationTargetException var0_2) {
            throw new a(var0_2);
        }
    }

    private static String b(byte[] object, String string2) throws a {
        try {
            object = new String(jY.c((byte[])object, string2), "UTF-8");
            return object;
        }
        catch (p.a var0_1) {
            throw new a(var0_1);
        }
        catch (UnsupportedEncodingException var0_2) {
            throw new a(var0_2);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static String d(Context object) throws a {
        if (jV == null) {
            throw new a();
        }
        object = (String)jV.invoke(null, object);
        if (object != null) return object;
        try {
            throw new a();
        }
        catch (IllegalAccessException var0_1) {
            throw new a(var0_1);
        }
        catch (InvocationTargetException var0_2) {
            throw new a(var0_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void e(Context object) throws a {
        try {
            File file;
            byte[] arrby = jY.b(r.getKey());
            Object object2 = jY.c(arrby, r.A());
            File file2 = file = object.getCacheDir();
            if (file == null) {
                file2 = file = object.getDir("dex", 0);
                if (file == null) {
                    throw new a();
                }
            }
            file = File.createTempFile("ads", ".jar", file2);
            Object object3 = new FileOutputStream(file);
            object3.write((byte[])object2, 0, object2.length);
            object3.close();
            Object object4 = new DexClassLoader(file.getAbsolutePath(), file2.getAbsolutePath(), null, object.getClassLoader());
            object = object4.loadClass(j.b(arrby, r.B()));
            object2 = object4.loadClass(j.b(arrby, r.H()));
            object3 = object4.loadClass(j.b(arrby, r.F()));
            Class class_ = object4.loadClass(j.b(arrby, r.L()));
            Class class_2 = object4.loadClass(j.b(arrby, r.D()));
            object4 = object4.loadClass(j.b(arrby, r.J()));
            jR = object.getMethod(j.b(arrby, r.C()), new Class[0]);
            jS = object2.getMethod(j.b(arrby, r.I()), new Class[0]);
            jT = object3.getMethod(j.b(arrby, r.G()), Context.class);
            jU = class_.getMethod(j.b(arrby, r.M()), MotionEvent.class, DisplayMetrics.class);
            jV = class_2.getMethod(j.b(arrby, r.E()), Context.class);
            jW = object4.getMethod(j.b(arrby, r.K()), Context.class);
            object = file.getName();
            file.delete();
            new File(file2, object.replace(".jar", ".dex")).delete();
            return;
        }
        catch (FileNotFoundException var0_1) {
            throw new a(var0_1);
        }
        catch (IOException var0_2) {
            throw new a(var0_2);
        }
        catch (ClassNotFoundException var0_3) {
            throw new a(var0_3);
        }
        catch (p.a var0_4) {
            throw new a(var0_4);
        }
        catch (NoSuchMethodException var0_5) {
            throw new a(var0_5);
        }
        catch (NullPointerException var0_6) {
            throw new a(var0_6);
        }
    }

    static String v() throws a {
        if (jX == null) {
            throw new a();
        }
        return jX;
    }

    static Long w() throws a {
        if (jR == null) {
            throw new a();
        }
        try {
            Long l2 = (Long)jR.invoke(null, new Object[0]);
            return l2;
        }
        catch (IllegalAccessException var0_1) {
            throw new a(var0_1);
        }
        catch (InvocationTargetException var0_2) {
            throw new a(var0_2);
        }
    }

    static String x() throws a {
        if (jS == null) {
            throw new a();
        }
        try {
            String string2 = (String)jS.invoke(null, new Object[0]);
            return string2;
        }
        catch (IllegalAccessException var0_1) {
            throw new a(var0_1);
        }
        catch (InvocationTargetException var0_2) {
            throw new a(var0_2);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected void b(Context context) {
        block10 : {
            try {
                this.a(1, j.x());
                break block10;
            }
            catch (IOException var1_2) {
                return;
            }
            catch (a a2) {}
        }
        try {
            this.a(2, j.v());
        }
        catch (a var2_5) {}
        try {
            this.a(25, j.w());
        }
        catch (a var2_4) {}
        try {
            this.a(24, j.d(context));
            return;
        }
        catch (a var1_3) {
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected void c(Context context) {
        block16 : {
            try {
                this.a(2, j.v());
                break block16;
            }
            catch (IOException var1_2) {
                return;
            }
            catch (a a2) {}
        }
        try {
            this.a(1, j.x());
        }
        catch (a var4_9) {}
        try {
            long l2 = j.w();
            this.a(25, l2);
            if (startTime != 0) {
                this.a(17, l2 - startTime);
                this.a(23, startTime);
            }
        }
        catch (a var4_8) {}
        try {
            ArrayList<Long> arrayList = j.a(this.jN, this.jO);
            this.a(14, arrayList.get(0));
            this.a(15, arrayList.get(1));
            if (arrayList.size() >= 3) {
                this.a(16, arrayList.get(2));
            }
        }
        catch (a var4_7) {}
        try {
            this.a(27, j.a(context, this.jP));
        }
        catch (a var4_6) {}
        try {
            this.a(29, j.b(context, this.jP));
            return;
        }
        catch (a var1_3) {
            return;
        }
    }

    static class a
    extends Exception {
        public a() {
        }

        public a(Throwable throwable) {
            super(throwable);
        }
    }

}

