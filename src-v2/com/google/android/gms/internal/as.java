/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.location.Location
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.mediation.admob.AdMobExtras;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.internal.dv;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class as {
    public static final String DEVICE_ID_EMULATOR = dv.u("emulator");
    private final Date d;
    private final Set<String> f;
    private final Location h;
    private final String lY;
    private final int lZ;
    private final boolean ma;
    private final Map<Class<? extends MediationAdapter>, Bundle> mb;
    private final Map<Class<? extends NetworkExtras>, NetworkExtras> mc;
    private final String md;
    private final SearchAdRequest me;
    private final int mf;
    private final Set<String> mg;

    public as(a a2) {
        this(a2, null);
    }

    public as(a a2, SearchAdRequest searchAdRequest) {
        this.d = a2.d;
        this.lY = a2.lY;
        this.lZ = a2.lZ;
        this.f = Collections.unmodifiableSet(a2.mh);
        this.h = a2.h;
        this.ma = a2.ma;
        this.mb = Collections.unmodifiableMap(a2.mi);
        this.mc = Collections.unmodifiableMap(a2.mj);
        this.md = a2.md;
        this.me = searchAdRequest;
        this.mf = a2.mf;
        this.mg = Collections.unmodifiableSet(a2.mk);
    }

    public SearchAdRequest aB() {
        return this.me;
    }

    public Map<Class<? extends NetworkExtras>, NetworkExtras> aC() {
        return this.mc;
    }

    public Map<Class<? extends MediationAdapter>, Bundle> aD() {
        return this.mb;
    }

    public int aE() {
        return this.mf;
    }

    public Date getBirthday() {
        return this.d;
    }

    public String getContentUrl() {
        return this.lY;
    }

    public int getGender() {
        return this.lZ;
    }

    public Set<String> getKeywords() {
        return this.f;
    }

    public Location getLocation() {
        return this.h;
    }

    public boolean getManualImpressionsEnabled() {
        return this.ma;
    }

    @Deprecated
    public <T extends NetworkExtras> T getNetworkExtras(Class<T> class_) {
        return (T)this.mc.get(class_);
    }

    public Bundle getNetworkExtrasBundle(Class<? extends MediationAdapter> class_) {
        return this.mb.get(class_);
    }

    public String getPublisherProvidedId() {
        return this.md;
    }

    public boolean isTestDevice(Context context) {
        return this.mg.contains(dv.l(context));
    }

    public static final class a {
        private Date d;
        private Location h;
        private String lY;
        private int lZ = -1;
        private boolean ma = false;
        private String md;
        private int mf = -1;
        private final HashSet<String> mh = new HashSet();
        private final HashMap<Class<? extends MediationAdapter>, Bundle> mi = new HashMap();
        private final HashMap<Class<? extends NetworkExtras>, NetworkExtras> mj = new HashMap();
        private final HashSet<String> mk = new HashSet();

        public void a(Location location) {
            this.h = location;
        }

        @Deprecated
        public void a(NetworkExtras networkExtras) {
            if (networkExtras instanceof AdMobExtras) {
                this.a(AdMobAdapter.class, ((AdMobExtras)networkExtras).getExtras());
                return;
            }
            this.mj.put(networkExtras.getClass(), networkExtras);
        }

        public void a(Class<? extends MediationAdapter> class_, Bundle bundle) {
            this.mi.put(class_, bundle);
        }

        public void a(Date date) {
            this.d = date;
        }

        public void d(int n2) {
            this.lZ = n2;
        }

        public void f(boolean bl2) {
            this.ma = bl2;
        }

        public void g(String string2) {
            this.mh.add(string2);
        }

        /*
         * Enabled aggressive block sorting
         */
        public void g(boolean bl2) {
            int n2 = bl2 ? 1 : 0;
            this.mf = n2;
        }

        public void h(String string2) {
            this.mk.add(string2);
        }

        public void i(String string2) {
            this.lY = string2;
        }

        public void j(String string2) {
            this.md = string2;
        }
    }

}

