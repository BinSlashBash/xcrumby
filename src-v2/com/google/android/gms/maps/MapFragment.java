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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.t;
import com.google.android.gms.maps.internal.u;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class MapFragment
extends Fragment {
    private final b RS;
    private GoogleMap RT;

    public MapFragment() {
        this.RS = new b(this);
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    public static MapFragment newInstance(GoogleMapOptions googleMapOptions) {
        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("MapOptions", (Parcelable)googleMapOptions);
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final GoogleMap getMap() {
        IGoogleMapDelegate iGoogleMapDelegate;
        block3 : {
            IMapFragmentDelegate iMapFragmentDelegate = this.io();
            if (iMapFragmentDelegate == null) {
                return null;
            }
            try {
                iGoogleMapDelegate = iMapFragmentDelegate.getMap();
                if (iGoogleMapDelegate == null) return null;
                if (this.RT == null) break block3;
            }
            catch (RemoteException var1_3) {
                throw new RuntimeRemoteException(var1_3);
            }
            if (this.RT.if().asBinder() == iGoogleMapDelegate.asBinder()) return this.RT;
        }
        this.RT = new GoogleMap(iGoogleMapDelegate);
        return this.RT;
    }

    protected IMapFragmentDelegate io() {
        this.RS.ip();
        if (this.RS.fW() == null) {
            return null;
        }
        return ((a)this.RS.fW()).io();
    }

    public void onActivityCreated(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(MapFragment.class.getClassLoader());
        }
        super.onActivityCreated(bundle);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.RS.setActivity(activity);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.RS.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.RS.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onDestroy() {
        this.RS.onDestroy();
        super.onDestroy();
    }

    public void onDestroyView() {
        this.RS.onDestroyView();
        super.onDestroyView();
    }

    public void onInflate(Activity activity, AttributeSet object, Bundle bundle) {
        super.onInflate(activity, (AttributeSet)object, bundle);
        this.RS.setActivity(activity);
        object = GoogleMapOptions.createFromAttributes((Context)activity, (AttributeSet)object);
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("MapOptions", (Parcelable)object);
        this.RS.onInflate(activity, bundle2, bundle);
    }

    public void onLowMemory() {
        this.RS.onLowMemory();
        super.onLowMemory();
    }

    public void onPause() {
        this.RS.onPause();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.RS.onResume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(MapFragment.class.getClassLoader());
        }
        super.onSaveInstanceState(bundle);
        this.RS.onSaveInstanceState(bundle);
    }

    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
    }

    static class a
    implements LifecycleDelegate {
        private final Fragment Hv;
        private final IMapFragmentDelegate RU;

        public a(Fragment fragment, IMapFragmentDelegate iMapFragmentDelegate) {
            this.RU = fq.f(iMapFragmentDelegate);
            this.Hv = fq.f(fragment);
        }

        public IMapFragmentDelegate io() {
            return this.RU;
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
                if ((var1_1 = this.Hv.getArguments()) != null && var1_1.containsKey("MapOptions")) {
                    t.a(var2_3, "MapOptions", var1_1.getParcelable("MapOptions"));
                }
                this.RU.onCreate(var2_3);
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater object, ViewGroup viewGroup, Bundle bundle) {
            try {
                object = this.RU.onCreateView(e.h(object), e.h(viewGroup), bundle);
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
            return (View)e.d((d)object);
        }

        @Override
        public void onDestroy() {
            try {
                this.RU.onDestroy();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onDestroyView() {
            try {
                this.RU.onDestroyView();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onInflate(Activity activity, Bundle object, Bundle bundle) {
            object = (GoogleMapOptions)object.getParcelable("MapOptions");
            try {
                this.RU.onInflate(e.h(activity), (GoogleMapOptions)object, bundle);
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
        }

        @Override
        public void onLowMemory() {
            try {
                this.RU.onLowMemory();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onPause() {
            try {
                this.RU.onPause();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onResume() {
            try {
                this.RU.onResume();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle bundle) {
            try {
                this.RU.onSaveInstanceState(bundle);
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
                IMapFragmentDelegate iMapFragmentDelegate = u.A((Context)this.nS).h(e.h(this.nS));
                this.RV.a(new a(this.Hv, iMapFragmentDelegate));
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

