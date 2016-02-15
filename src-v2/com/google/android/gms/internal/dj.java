/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.internal.di;
import com.google.android.gms.internal.dk;
import com.google.android.gms.internal.dl;
import com.google.android.gms.internal.dm;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class dj {
    private static final dj qJ = new dj();
    public static final String qK = dj.qJ.qL;
    private final Object li = new Object();
    public final String qL = dj.br();
    private final dk qM = new dk(this.qL);
    private BigInteger qN = BigInteger.ONE;
    private final HashSet<di> qO = new HashSet();
    private final HashMap<String, dm> qP = new HashMap();

    private dj() {
    }

    public static Bundle a(dl dl2, String string2) {
        return qJ.b(dl2, string2);
    }

    public static void b(HashSet<di> hashSet) {
        qJ.c(hashSet);
    }

    public static dj bq() {
        return qJ;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String br() {
        Object object = UUID.randomUUID();
        byte[] arrby = BigInteger.valueOf(object.getLeastSignificantBits()).toByteArray();
        byte[] arrby2 = BigInteger.valueOf(object.getMostSignificantBits()).toByteArray();
        object = new BigInteger(1, arrby).toString();
        int n2 = 0;
        while (n2 < 2) {
            try {
                Object object2 = MessageDigest.getInstance("MD5");
                object2.update(arrby);
                object2.update(arrby2);
                byte[] arrby3 = new byte[8];
                System.arraycopy(object2.digest(), 0, arrby3, 0, 8);
                object = object2 = new BigInteger(1, arrby3).toString();
            }
            catch (NoSuchAlgorithmException var2_5) {}
            ++n2;
        }
        return object;
    }

    public static String bs() {
        return qJ.bt();
    }

    public static dk bu() {
        return qJ.bv();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(di di2) {
        Object object = this.li;
        synchronized (object) {
            this.qO.add(di2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(String string2, dm dm2) {
        Object object = this.li;
        synchronized (object) {
            this.qP.put(string2, dm2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Bundle b(dl dl2, String object) {
        Object object2 = this.li;
        synchronized (object2) {
            Bundle bundle = new Bundle();
            bundle.putBundle("app", this.qM.q((String)object));
            object = new Bundle();
            for (String string2 : this.qP.keySet()) {
                object.putBundle(string2, this.qP.get(string2).toBundle());
            }
            bundle.putBundle("slots", (Bundle)object);
            object = new ArrayList();
            Iterator iterator = this.qO.iterator();
            do {
                if (!iterator.hasNext()) {
                    bundle.putParcelableArrayList("ads", (ArrayList)object);
                    dl2.a(this.qO);
                    this.qO.clear();
                    return bundle;
                }
                object.add(((di)iterator.next()).toBundle());
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String bt() {
        Object object = this.li;
        synchronized (object) {
            String string2 = this.qN.toString();
            this.qN = this.qN.add(BigInteger.ONE);
            return string2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public dk bv() {
        Object object = this.li;
        synchronized (object) {
            return this.qM;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void c(HashSet<di> hashSet) {
        Object object = this.li;
        synchronized (object) {
            this.qO.addAll(hashSet);
            return;
        }
    }
}

