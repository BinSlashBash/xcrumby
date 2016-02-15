package com.google.android.gms.tagmanager;

import android.content.Context;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.google.android.gms.internal.C0339c.C1360f;
import com.google.android.gms.internal.C0339c.C1363i;
import com.google.android.gms.internal.C0339c.C1364j;
import com.google.android.gms.internal.C0355d.C1367a;
import com.google.android.gms.tagmanager.C1089s.C0530a;
import com.google.android.gms.tagmanager.cd.C0501a;
import com.google.android.gms.tagmanager.cq.C0509c;
import com.google.android.gms.tagmanager.cq.C0513g;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container {
    private final String WJ;
    private final DataLayer WK;
    private cs WL;
    private Map<String, FunctionCallMacroCallback> WM;
    private Map<String, FunctionCallTagCallback> WN;
    private volatile long WO;
    private volatile String WP;
    private final Context mContext;

    public interface FunctionCallMacroCallback {
        Object getValue(String str, Map<String, Object> map);
    }

    public interface FunctionCallTagCallback {
        void execute(String str, Map<String, Object> map);
    }

    /* renamed from: com.google.android.gms.tagmanager.Container.a */
    private class C1054a implements C0530a {
        final /* synthetic */ Container WQ;

        private C1054a(Container container) {
            this.WQ = container;
        }

        public Object m2437b(String str, Map<String, Object> map) {
            FunctionCallMacroCallback bn = this.WQ.bn(str);
            return bn == null ? null : bn.getValue(str, map);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.Container.b */
    private class C1055b implements C0530a {
        final /* synthetic */ Container WQ;

        private C1055b(Container container) {
            this.WQ = container;
        }

        public Object m2438b(String str, Map<String, Object> map) {
            FunctionCallTagCallback bo = this.WQ.bo(str);
            if (bo != null) {
                bo.execute(str, map);
            }
            return dh.lS();
        }
    }

    Container(Context context, DataLayer dataLayer, String containerId, long lastRefreshTime, C1364j resource) {
        this.WM = new HashMap();
        this.WN = new HashMap();
        this.WP = UnsupportedUrlFragment.DISPLAY_NAME;
        this.mContext = context;
        this.WK = dataLayer;
        this.WJ = containerId;
        this.WO = lastRefreshTime;
        m1327a(resource.fK);
        if (resource.fJ != null) {
            m1330a(resource.fJ);
        }
    }

    Container(Context context, DataLayer dataLayer, String containerId, long lastRefreshTime, C0509c resource) {
        this.WM = new HashMap();
        this.WN = new HashMap();
        this.WP = UnsupportedUrlFragment.DISPLAY_NAME;
        this.mContext = context;
        this.WK = dataLayer;
        this.WJ = containerId;
        this.WO = lastRefreshTime;
        m1328a(resource);
    }

    private void m1327a(C1360f c1360f) {
        if (c1360f == null) {
            throw new NullPointerException();
        }
        try {
            m1328a(cq.m1422b(c1360f));
        } catch (C0513g e) {
            bh.m1384w("Not loading resource: " + c1360f + " because it is invalid: " + e.toString());
        }
    }

    private void m1328a(C0509c c0509c) {
        this.WP = c0509c.getVersion();
        C0509c c0509c2 = c0509c;
        m1329a(new cs(this.mContext, c0509c2, this.WK, new C1054a(), new C1055b(), bq(this.WP)));
    }

    private synchronized void m1329a(cs csVar) {
        this.WL = csVar;
    }

    private void m1330a(C1363i[] c1363iArr) {
        List arrayList = new ArrayList();
        for (Object add : c1363iArr) {
            arrayList.add(add);
        }
        kd().m1450e(arrayList);
    }

    private synchronized cs kd() {
        return this.WL;
    }

    FunctionCallMacroCallback bn(String str) {
        FunctionCallMacroCallback functionCallMacroCallback;
        synchronized (this.WM) {
            functionCallMacroCallback = (FunctionCallMacroCallback) this.WM.get(str);
        }
        return functionCallMacroCallback;
    }

    FunctionCallTagCallback bo(String str) {
        FunctionCallTagCallback functionCallTagCallback;
        synchronized (this.WN) {
            functionCallTagCallback = (FunctionCallTagCallback) this.WN.get(str);
        }
        return functionCallTagCallback;
    }

    void bp(String str) {
        kd().bp(str);
    }

    ag bq(String str) {
        if (cd.kT().kU().equals(C0501a.CONTAINER_DEBUG)) {
        }
        return new bq();
    }

    public boolean getBoolean(String key) {
        cs kd = kd();
        if (kd == null) {
            bh.m1384w("getBoolean called for closed container.");
            return dh.lQ().booleanValue();
        }
        try {
            return dh.m1466n((C1367a) kd.bR(key).getObject()).booleanValue();
        } catch (Exception e) {
            bh.m1384w("Calling getBoolean() threw an exception: " + e.getMessage() + " Returning default value.");
            return dh.lQ().booleanValue();
        }
    }

    public String getContainerId() {
        return this.WJ;
    }

    public double getDouble(String key) {
        cs kd = kd();
        if (kd == null) {
            bh.m1384w("getDouble called for closed container.");
            return dh.lP().doubleValue();
        }
        try {
            return dh.m1463m((C1367a) kd.bR(key).getObject()).doubleValue();
        } catch (Exception e) {
            bh.m1384w("Calling getDouble() threw an exception: " + e.getMessage() + " Returning default value.");
            return dh.lP().doubleValue();
        }
    }

    public long getLastRefreshTime() {
        return this.WO;
    }

    public long getLong(String key) {
        cs kd = kd();
        if (kd == null) {
            bh.m1384w("getLong called for closed container.");
            return dh.lO().longValue();
        }
        try {
            return dh.m1462l((C1367a) kd.bR(key).getObject()).longValue();
        } catch (Exception e) {
            bh.m1384w("Calling getLong() threw an exception: " + e.getMessage() + " Returning default value.");
            return dh.lO().longValue();
        }
    }

    public String getString(String key) {
        cs kd = kd();
        if (kd == null) {
            bh.m1384w("getString called for closed container.");
            return dh.lS();
        }
        try {
            return dh.m1460j((C1367a) kd.bR(key).getObject());
        } catch (Exception e) {
            bh.m1384w("Calling getString() threw an exception: " + e.getMessage() + " Returning default value.");
            return dh.lS();
        }
    }

    public boolean isDefault() {
        return getLastRefreshTime() == 0;
    }

    String kc() {
        return this.WP;
    }

    public void registerFunctionCallMacroCallback(String customMacroName, FunctionCallMacroCallback customMacroCallback) {
        if (customMacroCallback == null) {
            throw new NullPointerException("Macro handler must be non-null");
        }
        synchronized (this.WM) {
            this.WM.put(customMacroName, customMacroCallback);
        }
    }

    public void registerFunctionCallTagCallback(String customTagName, FunctionCallTagCallback customTagCallback) {
        if (customTagCallback == null) {
            throw new NullPointerException("Tag callback must be non-null");
        }
        synchronized (this.WN) {
            this.WN.put(customTagName, customTagCallback);
        }
    }

    void release() {
        this.WL = null;
    }

    public void unregisterFunctionCallMacroCallback(String customMacroName) {
        synchronized (this.WM) {
            this.WM.remove(customMacroName);
        }
    }

    public void unregisterFunctionCallTagCallback(String customTagName) {
        synchronized (this.WN) {
            this.WN.remove(customTagName);
        }
    }
}
