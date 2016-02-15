/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.RemoteException
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 */
package com.google.android.gms.maps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.dynamic.f;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.u;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class MapView
extends FrameLayout {
    private GoogleMap RT;
    private final b RW;

    public MapView(Context context) {
        super(context);
        this.RW = new b((ViewGroup)this, context, null);
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.RW = new b((ViewGroup)this, context, GoogleMapOptions.createFromAttributes(context, attributeSet));
    }

    public MapView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.RW = new b((ViewGroup)this, context, GoogleMapOptions.createFromAttributes(context, attributeSet));
    }

    public MapView(Context context, GoogleMapOptions googleMapOptions) {
        super(context);
        this.RW = new b((ViewGroup)this, context, googleMapOptions);
    }

    public final GoogleMap getMap() {
        if (this.RT != null) {
            return this.RT;
        }
        this.RW.ip();
        if (this.RW.fW() == null) {
            return null;
        }
        try {
            this.RT = new GoogleMap(((a)this.RW.fW()).iq().getMap());
            return this.RT;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public final void onCreate(Bundle object) {
        this.RW.onCreate((Bundle)object);
        if (this.RW.fW() == null) {
            object = this.RW;
            b.b(this);
        }
    }

    public final void onDestroy() {
        this.RW.onDestroy();
    }

    public final void onLowMemory() {
        this.RW.onLowMemory();
    }

    public final void onPause() {
        this.RW.onPause();
    }

    public final void onResume() {
        this.RW.onResume();
    }

    public final void onSaveInstanceState(Bundle bundle) {
        this.RW.onSaveInstanceState(bundle);
    }

    static class a
    implements LifecycleDelegate {
        private final ViewGroup RX;
        private final IMapViewDelegate RY;
        private View RZ;

        public a(ViewGroup viewGroup, IMapViewDelegate iMapViewDelegate) {
            this.RY = fq.f(iMapViewDelegate);
            this.RX = fq.f(viewGroup);
        }

        public IMapViewDelegate iq() {
            return this.RY;
        }

        @Override
        public void onCreate(Bundle bundle) {
            try {
                this.RY.onCreate(bundle);
                this.RZ = (View)e.d(this.RY.getView());
                this.RX.removeAllViews();
                this.RX.addView(this.RZ);
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            throw new UnsupportedOperationException("onCreateView not allowed on MapViewDelegate");
        }

        @Override
        public void onDestroy() {
            try {
                this.RY.onDestroy();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onDestroyView() {
            throw new UnsupportedOperationException("onDestroyView not allowed on MapViewDelegate");
        }

        @Override
        public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            throw new UnsupportedOperationException("onInflate not allowed on MapViewDelegate");
        }

        @Override
        public void onLowMemory() {
            try {
                this.RY.onLowMemory();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onPause() {
            try {
                this.RY.onPause();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onResume() {
            try {
                this.RY.onResume();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle bundle) {
            try {
                this.RY.onSaveInstanceState(bundle);
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
        protected f<a> RV;
        private final ViewGroup Sa;
        private final GoogleMapOptions Sb;
        private final Context mContext;

        b(ViewGroup viewGroup, Context context, GoogleMapOptions googleMapOptions) {
            this.Sa = viewGroup;
            this.mContext = context;
            this.Sb = googleMapOptions;
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
            if (this.RV == null || this.fW() != null) return;
            try {
                IMapViewDelegate iMapViewDelegate = u.A(this.mContext).a(e.h(this.mContext), this.Sb);
                this.RV.a(new a(this.Sa, iMapViewDelegate));
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

