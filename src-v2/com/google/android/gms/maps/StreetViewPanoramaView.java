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
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.internal.u;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class StreetViewPanoramaView
extends FrameLayout {
    private StreetViewPanorama Sj;
    private final a Ss;

    public StreetViewPanoramaView(Context context) {
        super(context);
        this.Ss = new a((ViewGroup)this, context, null);
    }

    public StreetViewPanoramaView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.Ss = new a((ViewGroup)this, context, null);
    }

    public StreetViewPanoramaView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.Ss = new a((ViewGroup)this, context, null);
    }

    public StreetViewPanoramaView(Context context, StreetViewPanoramaOptions streetViewPanoramaOptions) {
        super(context);
        this.Ss = new a((ViewGroup)this, context, streetViewPanoramaOptions);
    }

    public final StreetViewPanorama getStreetViewPanorama() {
        if (this.Sj != null) {
            return this.Sj;
        }
        this.Ss.ip();
        if (this.Ss.fW() == null) {
            return null;
        }
        try {
            this.Sj = new StreetViewPanorama(((b)this.Ss.fW()).iw().getStreetViewPanorama());
            return this.Sj;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public final void onCreate(Bundle object) {
        this.Ss.onCreate((Bundle)object);
        if (this.Ss.fW() == null) {
            object = this.Ss;
            a.b(this);
        }
    }

    public final void onDestroy() {
        this.Ss.onDestroy();
    }

    public final void onLowMemory() {
        this.Ss.onLowMemory();
    }

    public final void onPause() {
        this.Ss.onPause();
    }

    public final void onResume() {
        this.Ss.onResume();
    }

    public final void onSaveInstanceState(Bundle bundle) {
        this.Ss.onSaveInstanceState(bundle);
    }

    static class a
    extends com.google.android.gms.dynamic.a<b> {
        protected f<b> RV;
        private final ViewGroup Sa;
        private final StreetViewPanoramaOptions St;
        private final Context mContext;

        a(ViewGroup viewGroup, Context context, StreetViewPanoramaOptions streetViewPanoramaOptions) {
            this.Sa = viewGroup;
            this.mContext = context;
            this.St = streetViewPanoramaOptions;
        }

        @Override
        protected void a(f<b> f2) {
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
                IStreetViewPanoramaViewDelegate iStreetViewPanoramaViewDelegate = u.A(this.mContext).a(e.h(this.mContext), this.St);
                this.RV.a(new b(this.Sa, iStreetViewPanoramaViewDelegate));
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

    static class b
    implements LifecycleDelegate {
        private final ViewGroup RX;
        private final IStreetViewPanoramaViewDelegate Su;
        private View Sv;

        public b(ViewGroup viewGroup, IStreetViewPanoramaViewDelegate iStreetViewPanoramaViewDelegate) {
            this.Su = fq.f(iStreetViewPanoramaViewDelegate);
            this.RX = fq.f(viewGroup);
        }

        public IStreetViewPanoramaViewDelegate iw() {
            return this.Su;
        }

        @Override
        public void onCreate(Bundle bundle) {
            try {
                this.Su.onCreate(bundle);
                this.Sv = (View)e.d(this.Su.getView());
                this.RX.removeAllViews();
                this.RX.addView(this.Sv);
                return;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            throw new UnsupportedOperationException("onCreateView not allowed on StreetViewPanoramaViewDelegate");
        }

        @Override
        public void onDestroy() {
            try {
                this.Su.onDestroy();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onDestroyView() {
            throw new UnsupportedOperationException("onDestroyView not allowed on StreetViewPanoramaViewDelegate");
        }

        @Override
        public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            throw new UnsupportedOperationException("onInflate not allowed on StreetViewPanoramaViewDelegate");
        }

        @Override
        public void onLowMemory() {
            try {
                this.Su.onLowMemory();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onPause() {
            try {
                this.Su.onPause();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onResume() {
            try {
                this.Su.onResume();
                return;
            }
            catch (RemoteException var1_1) {
                throw new RuntimeRemoteException(var1_1);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle bundle) {
            try {
                this.Su.onSaveInstanceState(bundle);
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

}

