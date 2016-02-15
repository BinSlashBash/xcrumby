/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.bh;
import com.google.android.gms.tagmanager.cd;
import com.google.android.gms.tagmanager.d;
import com.google.android.gms.tagmanager.n;
import com.google.android.gms.tagmanager.o;
import com.google.android.gms.tagmanager.r;
import com.google.android.gms.tagmanager.v;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TagManager {
    private static TagManager aay;
    private final DataLayer WK;
    private final r Zg;
    private final a aaw;
    private final ConcurrentMap<n, Boolean> aax;
    private final Context mContext;

    TagManager(Context context, a a2, DataLayer dataLayer) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.mContext = context.getApplicationContext();
        this.aaw = a2;
        this.aax = new ConcurrentHashMap<n, Boolean>();
        this.WK = dataLayer;
        this.WK.a(new DataLayer.b(){

            @Override
            public void y(Map<String, Object> object) {
                if ((object = object.get("event")) != null) {
                    TagManager.this.bT(object.toString());
                }
            }
        });
        this.WK.a(new d(this.mContext));
        this.Zg = new r();
    }

    private void bT(String string2) {
        Iterator<n> iterator = this.aax.keySet().iterator();
        while (iterator.hasNext()) {
            iterator.next().bp(string2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static TagManager getInstance(Context object) {
        synchronized (TagManager.class) {
            if (aay != null) return aay;
            if (object == null) {
                bh.w("TagManager.getInstance requires non-null context.");
                throw new NullPointerException();
            }
            aay = new TagManager((Context)object, new a(){

                @Override
                public o a(Context context, TagManager tagManager, Looper looper, String string2, int n2, r r2) {
                    return new o(context, tagManager, looper, string2, n2, r2);
                }
            }, new DataLayer(new v((Context)object)));
            return aay;
        }
    }

    void a(n n2) {
        this.aax.put(n2, true);
    }

    boolean b(n n2) {
        if (this.aax.remove(n2) != null) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean g(Uri object) {
        synchronized (this) {
            Object object2 = cd.kT();
            if (!object2.g((Uri)object)) return false;
            object = object2.getContainerId();
            int n2 = .aaA[object2.kU().ordinal()];
            switch (n2) {
                case 1: {
                    object2 = this.aax.keySet().iterator();
                    while (object2.hasNext()) {
                        n n3 = (n)object2.next();
                        if (!n3.getContainerId().equals(object)) continue;
                        n3.br(null);
                        n3.refresh();
                    }
                    return true;
                }
                default: {
                    return true;
                }
                case 2: 
                case 3: 
            }
            Iterator<n> iterator = this.aax.keySet().iterator();
            while (iterator.hasNext()) {
                n n4 = iterator.next();
                if (n4.getContainerId().equals(object)) {
                    n4.br(object2.kV());
                    n4.refresh();
                    continue;
                }
                if (n4.ke() == null) continue;
                n4.br(null);
                n4.refresh();
            }
            return true;
        }
    }

    public DataLayer getDataLayer() {
        return this.WK;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String object, int n2) {
        object = this.aaw.a(this.mContext, this, null, (String)object, n2, this.Zg);
        object.kh();
        return object;
    }

    public PendingResult<ContainerHolder> loadContainerDefaultOnly(String object, int n2, Handler handler) {
        object = this.aaw.a(this.mContext, this, handler.getLooper(), (String)object, n2, this.Zg);
        object.kh();
        return object;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String object, int n2) {
        object = this.aaw.a(this.mContext, this, null, (String)object, n2, this.Zg);
        object.kj();
        return object;
    }

    public PendingResult<ContainerHolder> loadContainerPreferFresh(String object, int n2, Handler handler) {
        object = this.aaw.a(this.mContext, this, handler.getLooper(), (String)object, n2, this.Zg);
        object.kj();
        return object;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String object, int n2) {
        object = this.aaw.a(this.mContext, this, null, (String)object, n2, this.Zg);
        object.ki();
        return object;
    }

    public PendingResult<ContainerHolder> loadContainerPreferNonDefault(String object, int n2, Handler handler) {
        object = this.aaw.a(this.mContext, this, handler.getLooper(), (String)object, n2, this.Zg);
        object.ki();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setVerboseLoggingEnabled(boolean bl2) {
        int n2 = bl2 ? 2 : 5;
        bh.setLogLevel(n2);
    }

    static interface a {
        public o a(Context var1, TagManager var2, Looper var3, String var4, int var5, r var6);
    }

}

