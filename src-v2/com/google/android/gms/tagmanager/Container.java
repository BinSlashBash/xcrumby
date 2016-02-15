/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.c;
import com.google.android.gms.internal.d;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.ag;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.bq;
import com.google.android.gms.tagmanager.by;
import com.google.android.gms.tagmanager.cd;
import com.google.android.gms.tagmanager.cq;
import com.google.android.gms.tagmanager.cs;
import com.google.android.gms.tagmanager.dh;
import com.google.android.gms.tagmanager.s;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container {
    private final String WJ;
    private final DataLayer WK;
    private cs WL;
    private Map<String, FunctionCallMacroCallback> WM = new HashMap<String, FunctionCallMacroCallback>();
    private Map<String, FunctionCallTagCallback> WN = new HashMap<String, FunctionCallTagCallback>();
    private volatile long WO;
    private volatile String WP = "";
    private final Context mContext;

    Container(Context context, DataLayer dataLayer, String string2, long l2, c.j j2) {
        this.mContext = context;
        this.WK = dataLayer;
        this.WJ = string2;
        this.WO = l2;
        this.a(j2.fK);
        if (j2.fJ != null) {
            this.a(j2.fJ);
        }
    }

    Container(Context context, DataLayer dataLayer, String string2, long l2, cq.c c2) {
        this.mContext = context;
        this.WK = dataLayer;
        this.WJ = string2;
        this.WO = l2;
        this.a(c2);
    }

    private void a(c.f f2) {
        if (f2 == null) {
            throw new NullPointerException();
        }
        try {
            cq.c c2 = cq.b(f2);
            this.a(c2);
            return;
        }
        catch (cq.g var2_3) {
            bh.w("Not loading resource: " + f2 + " because it is invalid: " + var2_3.toString());
            return;
        }
    }

    private void a(cq.c c2) {
        this.WP = c2.getVersion();
        ag ag2 = this.bq(this.WP);
        this.a(new cs(this.mContext, c2, this.WK, new a(), new b(), ag2));
    }

    private void a(cs cs2) {
        synchronized (this) {
            this.WL = cs2;
            return;
        }
    }

    private void a(c.i[] arri) {
        ArrayList<c.i> arrayList = new ArrayList<c.i>();
        int n2 = arri.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(arri[i2]);
        }
        this.kd().e(arrayList);
    }

    private cs kd() {
        synchronized (this) {
            cs cs2 = this.WL;
            return cs2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    FunctionCallMacroCallback bn(String object) {
        Map<String, FunctionCallMacroCallback> map = this.WM;
        synchronized (map) {
            return this.WM.get(object);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    FunctionCallTagCallback bo(String object) {
        Map<String, FunctionCallTagCallback> map = this.WN;
        synchronized (map) {
            return this.WN.get(object);
        }
    }

    void bp(String string2) {
        this.kd().bp(string2);
    }

    ag bq(String string2) {
        if (cd.kT().kU().equals((Object)cd.a.YV)) {
            // empty if block
        }
        return new bq();
    }

    public boolean getBoolean(String string2) {
        cs cs2 = this.kd();
        if (cs2 == null) {
            bh.w("getBoolean called for closed container.");
            return dh.lQ();
        }
        try {
            boolean bl2 = dh.n(cs2.bR(string2).getObject());
            return bl2;
        }
        catch (Exception var1_2) {
            bh.w("Calling getBoolean() threw an exception: " + var1_2.getMessage() + " Returning default value.");
            return dh.lQ();
        }
    }

    public String getContainerId() {
        return this.WJ;
    }

    public double getDouble(String string2) {
        cs cs2 = this.kd();
        if (cs2 == null) {
            bh.w("getDouble called for closed container.");
            return dh.lP();
        }
        try {
            double d2 = dh.m(cs2.bR(string2).getObject());
            return d2;
        }
        catch (Exception var1_2) {
            bh.w("Calling getDouble() threw an exception: " + var1_2.getMessage() + " Returning default value.");
            return dh.lP();
        }
    }

    public long getLastRefreshTime() {
        return this.WO;
    }

    public long getLong(String string2) {
        cs cs2 = this.kd();
        if (cs2 == null) {
            bh.w("getLong called for closed container.");
            return dh.lO();
        }
        try {
            long l2 = dh.l(cs2.bR(string2).getObject());
            return l2;
        }
        catch (Exception var1_2) {
            bh.w("Calling getLong() threw an exception: " + var1_2.getMessage() + " Returning default value.");
            return dh.lO();
        }
    }

    public String getString(String string2) {
        cs cs2 = this.kd();
        if (cs2 == null) {
            bh.w("getString called for closed container.");
            return dh.lS();
        }
        try {
            string2 = dh.j(cs2.bR(string2).getObject());
            return string2;
        }
        catch (Exception var1_2) {
            bh.w("Calling getString() threw an exception: " + var1_2.getMessage() + " Returning default value.");
            return dh.lS();
        }
    }

    public boolean isDefault() {
        if (this.getLastRefreshTime() == 0) {
            return true;
        }
        return false;
    }

    String kc() {
        return this.WP;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerFunctionCallMacroCallback(String string2, FunctionCallMacroCallback functionCallMacroCallback) {
        if (functionCallMacroCallback == null) {
            throw new NullPointerException("Macro handler must be non-null");
        }
        Map<String, FunctionCallMacroCallback> map = this.WM;
        synchronized (map) {
            this.WM.put(string2, functionCallMacroCallback);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerFunctionCallTagCallback(String string2, FunctionCallTagCallback functionCallTagCallback) {
        if (functionCallTagCallback == null) {
            throw new NullPointerException("Tag callback must be non-null");
        }
        Map<String, FunctionCallTagCallback> map = this.WN;
        synchronized (map) {
            this.WN.put(string2, functionCallTagCallback);
            return;
        }
    }

    void release() {
        this.WL = null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterFunctionCallMacroCallback(String string2) {
        Map<String, FunctionCallMacroCallback> map = this.WM;
        synchronized (map) {
            this.WM.remove(string2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterFunctionCallTagCallback(String string2) {
        Map<String, FunctionCallTagCallback> map = this.WN;
        synchronized (map) {
            this.WN.remove(string2);
            return;
        }
    }

    public static interface FunctionCallMacroCallback {
        public Object getValue(String var1, Map<String, Object> var2);
    }

    public static interface FunctionCallTagCallback {
        public void execute(String var1, Map<String, Object> var2);
    }

    private class a
    implements s.a {
        private a() {
        }

        @Override
        public Object b(String string2, Map<String, Object> map) {
            FunctionCallMacroCallback functionCallMacroCallback = Container.this.bn(string2);
            if (functionCallMacroCallback == null) {
                return null;
            }
            return functionCallMacroCallback.getValue(string2, map);
        }
    }

    private class b
    implements s.a {
        private b() {
        }

        @Override
        public Object b(String string2, Map<String, Object> map) {
            FunctionCallTagCallback functionCallTagCallback = Container.this.bo(string2);
            if (functionCallTagCallback != null) {
                functionCallTagCallback.execute(string2, map);
            }
            return dh.lS();
        }
    }

}

