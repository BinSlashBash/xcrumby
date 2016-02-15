package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.mediation.admob.AdMobExtras;
import com.google.android.gms.ads.search.SearchAdRequest;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class as {
    public static final String DEVICE_ID_EMULATOR;
    private final Date f52d;
    private final Set<String> f53f;
    private final Location f54h;
    private final String lY;
    private final int lZ;
    private final boolean ma;
    private final Map<Class<? extends MediationAdapter>, Bundle> mb;
    private final Map<Class<? extends NetworkExtras>, NetworkExtras> mc;
    private final String md;
    private final SearchAdRequest me;
    private final int mf;
    private final Set<String> mg;

    /* renamed from: com.google.android.gms.internal.as.a */
    public static final class C0324a {
        private Date f50d;
        private Location f51h;
        private String lY;
        private int lZ;
        private boolean ma;
        private String md;
        private int mf;
        private final HashSet<String> mh;
        private final HashMap<Class<? extends MediationAdapter>, Bundle> mi;
        private final HashMap<Class<? extends NetworkExtras>, NetworkExtras> mj;
        private final HashSet<String> mk;

        public C0324a() {
            this.mh = new HashSet();
            this.mi = new HashMap();
            this.mj = new HashMap();
            this.mk = new HashSet();
            this.lZ = -1;
            this.ma = false;
            this.mf = -1;
        }

        public void m638a(Location location) {
            this.f51h = location;
        }

        @Deprecated
        public void m639a(NetworkExtras networkExtras) {
            if (networkExtras instanceof AdMobExtras) {
                m640a(AdMobAdapter.class, ((AdMobExtras) networkExtras).getExtras());
            } else {
                this.mj.put(networkExtras.getClass(), networkExtras);
            }
        }

        public void m640a(Class<? extends MediationAdapter> cls, Bundle bundle) {
            this.mi.put(cls, bundle);
        }

        public void m641a(Date date) {
            this.f50d = date;
        }

        public void m642d(int i) {
            this.lZ = i;
        }

        public void m643f(boolean z) {
            this.ma = z;
        }

        public void m644g(String str) {
            this.mh.add(str);
        }

        public void m645g(boolean z) {
            this.mf = z ? 1 : 0;
        }

        public void m646h(String str) {
            this.mk.add(str);
        }

        public void m647i(String str) {
            this.lY = str;
        }

        public void m648j(String str) {
            this.md = str;
        }
    }

    static {
        DEVICE_ID_EMULATOR = dv.m814u("emulator");
    }

    public as(C0324a c0324a) {
        this(c0324a, null);
    }

    public as(C0324a c0324a, SearchAdRequest searchAdRequest) {
        this.f52d = c0324a.f50d;
        this.lY = c0324a.lY;
        this.lZ = c0324a.lZ;
        this.f53f = Collections.unmodifiableSet(c0324a.mh);
        this.f54h = c0324a.f51h;
        this.ma = c0324a.ma;
        this.mb = Collections.unmodifiableMap(c0324a.mi);
        this.mc = Collections.unmodifiableMap(c0324a.mj);
        this.md = c0324a.md;
        this.me = searchAdRequest;
        this.mf = c0324a.mf;
        this.mg = Collections.unmodifiableSet(c0324a.mk);
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
        return this.f52d;
    }

    public String getContentUrl() {
        return this.lY;
    }

    public int getGender() {
        return this.lZ;
    }

    public Set<String> getKeywords() {
        return this.f53f;
    }

    public Location getLocation() {
        return this.f54h;
    }

    public boolean getManualImpressionsEnabled() {
        return this.ma;
    }

    @Deprecated
    public <T extends NetworkExtras> T getNetworkExtras(Class<T> networkExtrasClass) {
        return (NetworkExtras) this.mc.get(networkExtrasClass);
    }

    public Bundle getNetworkExtrasBundle(Class<? extends MediationAdapter> adapterClass) {
        return (Bundle) this.mb.get(adapterClass);
    }

    public String getPublisherProvidedId() {
        return this.md;
    }

    public boolean isTestDevice(Context context) {
        return this.mg.contains(dv.m813l(context));
    }
}
