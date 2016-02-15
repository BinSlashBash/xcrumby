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
import com.google.android.gms.dynamic.C0304a;
import com.google.android.gms.dynamic.C0307f;
import com.google.android.gms.dynamic.C1324e;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.internal.C0451u;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class MapView extends FrameLayout {
    private GoogleMap RT;
    private final C0947b RW;

    /* renamed from: com.google.android.gms.maps.MapView.a */
    static class C0946a implements LifecycleDelegate {
        private final ViewGroup RX;
        private final IMapViewDelegate RY;
        private View RZ;

        public C0946a(ViewGroup viewGroup, IMapViewDelegate iMapViewDelegate) {
            this.RY = (IMapViewDelegate) fq.m985f(iMapViewDelegate);
            this.RX = (ViewGroup) fq.m985f(viewGroup);
        }

        public IMapViewDelegate iq() {
            return this.RY;
        }

        public void onCreate(Bundle savedInstanceState) {
            try {
                this.RY.onCreate(savedInstanceState);
                this.RZ = (View) C1324e.m2709d(this.RY.getView());
                this.RX.removeAllViews();
                this.RX.addView(this.RZ);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            throw new UnsupportedOperationException("onCreateView not allowed on MapViewDelegate");
        }

        public void onDestroy() {
            try {
                this.RY.onDestroy();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onDestroyView() {
            throw new UnsupportedOperationException("onDestroyView not allowed on MapViewDelegate");
        }

        public void onInflate(Activity activity, Bundle attrs, Bundle savedInstanceState) {
            throw new UnsupportedOperationException("onInflate not allowed on MapViewDelegate");
        }

        public void onLowMemory() {
            try {
                this.RY.onLowMemory();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onPause() {
            try {
                this.RY.onPause();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onResume() {
            try {
                this.RY.onResume();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onSaveInstanceState(Bundle outState) {
            try {
                this.RY.onSaveInstanceState(outState);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public void onStart() {
        }

        public void onStop() {
        }
    }

    /* renamed from: com.google.android.gms.maps.MapView.b */
    static class C0947b extends C0304a<C0946a> {
        protected C0307f<C0946a> RV;
        private final ViewGroup Sa;
        private final GoogleMapOptions Sb;
        private final Context mContext;

        C0947b(ViewGroup viewGroup, Context context, GoogleMapOptions googleMapOptions) {
            this.Sa = viewGroup;
            this.mContext = context;
            this.Sb = googleMapOptions;
        }

        protected void m2342a(C0307f<C0946a> c0307f) {
            this.RV = c0307f;
            ip();
        }

        public void ip() {
            if (this.RV != null && fW() == null) {
                try {
                    this.RV.m357a(new C0946a(this.Sa, C0451u.m1247A(this.mContext).m1229a(C1324e.m2710h(this.mContext), this.Sb)));
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException e2) {
                }
            }
        }
    }

    public MapView(Context context) {
        super(context);
        this.RW = new C0947b(this, context, null);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.RW = new C0947b(this, context, GoogleMapOptions.createFromAttributes(context, attrs));
    }

    public MapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.RW = new C0947b(this, context, GoogleMapOptions.createFromAttributes(context, attrs));
    }

    public MapView(Context context, GoogleMapOptions options) {
        super(context);
        this.RW = new C0947b(this, context, options);
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
            this.RT = new GoogleMap(((C0946a) this.RW.fW()).iq().getMap());
            return this.RT;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void onCreate(Bundle savedInstanceState) {
        this.RW.onCreate(savedInstanceState);
        if (this.RW.fW() == null) {
            C0947b c0947b = this.RW;
            C0304a.m352b((FrameLayout) this);
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

    public final void onSaveInstanceState(Bundle outState) {
        this.RW.onSaveInstanceState(outState);
    }
}
