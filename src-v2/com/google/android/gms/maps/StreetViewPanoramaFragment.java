/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.gms.maps;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.dynamic.f;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.t;
import com.google.android.gms.maps.internal.u;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class StreetViewPanoramaFragment
extends Fragment {
    private final b Si;
    private StreetViewPanorama Sj;

    public StreetViewPanoramaFragment() {
        this.Si = new b(this);
    }

    public static StreetViewPanoramaFragment newInstance() {
        return new StreetViewPanoramaFragment();
    }

    public static StreetViewPanoramaFragment newInstance(StreetViewPanoramaOptions streetViewPanoramaOptions) {
        StreetViewPanoramaFragment streetViewPanoramaFragment = new StreetViewPanoramaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("StreetViewPanoramaOptions", (Parcelable)streetViewPanoramaOptions);
        streetViewPanoramaFragment.setArguments(bundle);
        return streetViewPanoramaFragment;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final StreetViewPanorama getStreetViewPanorama() {
        IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate;
        block3 : {
            IStreetViewPanoramaFragmentDelegate iStreetViewPanoramaFragmentDelegate = this.is();
            if (iStreetViewPanoramaFragmentDelegate == null) {
                return null;
            }
            try {
                iStreetViewPanoramaDelegate = iStreetViewPanoramaFragmentDelegate.getStreetViewPanorama();
                if (iStreetViewPanoramaDelegate == null) return null;
                if (this.Sj == null) break block3;
            }
            catch (RemoteException var1_3) {
                throw new RuntimeRemoteException(var1_3);
            }
            if (this.Sj.ir().asBinder() == iStreetViewPanoramaDelegate.asBinder()) return this.Sj;
        }
        this.Sj = new StreetViewPanorama(iStreetViewPanoramaDelegate);
        return this.Sj;
    }

    protected IStreetViewPanoramaFragmentDelegate is() {
        this.Si.ip();
        if (this.Si.fW() == null) {
            return null;
        }
        return ((a)this.Si.fW()).is();
    }

    public void onActivityCreated(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(StreetViewPanoramaFragment.class.getClassLoader());
        }
        super.onActivityCreated(bundle);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.Si.setActivity(activity);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.Si.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.Si.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onDestroy() {
        this.Si.onDestroy();
        super.onDestroy();
    }

    public void onDestroyView() {
        this.Si.onDestroyView();
        super.onDestroyView();
    }

    public void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        this.Si.setActivity(activity);
        attributeSet = new Bundle();
        this.Si.onInflate(activity, (Bundle)attributeSet, bundle);
    }

    public void onLowMemory() {
        this.Si.onLowMemory();
        super.onLowMemory();
    }

    public void onPause() {
        this.Si.onPause();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.Si.onResume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(StreetViewPanoramaFragment.class.getClassLoader());
        }
        super.onSaveInstanceState(bundle);
        this.Si.onSaveInstanceState(bundle);
    }

    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }

    static class a
    implements LifecycleDelegate {
        private final Fragment Hv;
        private final IStreetViewPanoramaFragmentDelegate Sk;

        public a(Fragment fragment, IStreetViewPanoramaFragmentDelegate iStreetViewPanoramaFragmentDelegate) {
            this.Sk = fq.f(iStreetViewPanoramaFragmentDelegate);
            this.Hv = fq.f(fragment);
        }

        public IStreetViewPanoramaFragmentDelegate is() {
            return this.Sk;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void onCreate(Bundle var1_1) {
            var2_3 = var1_1;
            if (var1_1 != null) ** GOTO lbl5
            try {
                var2_3 = new Bundle();
lbl5: // 2 sources:
                if ((var1_1 = this.Hv.getArguments()) != null && var1_1.containsKey("StreetViewPanoramaOptions")) {
                    t.a(var2_3, "StreetViewPanoramaOptions", var1_1.getParcelable("StreetViewPanoramaOptions"));
                }
                this.Sk.onCreate(var2_3);
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater object, ViewGroup viewGroup, Bundle bundle) {
            try {
                object = this.Sk.onCreateView(e.h(object), e.h(viewGroup), bundle);
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
            return (View)e.d((d)object);
        }

        @Override
        public void onDestroy() {
            try {
                this.Sk.onDestroy();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onDestroyView() {
            try {
                this.Sk.onDestroyView();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            try {
                this.Sk.onInflate(e.h(activity), null, bundle2);
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
        }

        @Override
        public void onLowMemory() {
            try {
                this.Sk.onLowMemory();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onPause() {
            try {
                this.Sk.onPause();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onResume() {
            try {
                this.Sk.onResume();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle bundle) {
            try {
                this.Sk.onSaveInstanceState(bundle);
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
        }

        @Override
        public void onStart() {
        }

        @Override
        public void onStop() {
        }
    }

    static class b
    extends com.google.android.gms.dynamic.a<a> {
        private final Fragment Hv;
        protected f<a> RV;
        private Activity nS;

        b(Fragment fragment) {
            this.Hv = fragment;
        }

        private void setActivity(Activity activity) {
            this.nS = activity;
            this.ip();
        }

        @Override
        protected void a(f<a> f2) {
            this.RV = f2;
            this.ip();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public void ip() {
            if (this.nS == null || this.RV == null || this.fW() != null) return;
            try {
                MapsInitializer.initialize((Context)this.nS);
                IStreetViewPanoramaFragmentDelegate iStreetViewPanoramaFragmentDelegate = u.A((Context)this.nS).i(e.h(this.nS));
                this.RV.a(new a(this.Hv, iStreetViewPanoramaFragmentDelegate));
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
            catch (GooglePlayServicesNotAvailableException var1_3) {
                return;
            }
        }
    }

}

