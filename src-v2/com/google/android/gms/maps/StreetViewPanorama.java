/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Point
 *  android.os.RemoteException
 */
package com.google.android.gms.maps;

import android.graphics.Point;
import android.os.RemoteException;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.dynamic.e;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.p;
import com.google.android.gms.maps.internal.q;
import com.google.android.gms.maps.internal.r;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

public class StreetViewPanorama {
    private final IStreetViewPanoramaDelegate Sd;

    protected StreetViewPanorama(IStreetViewPanoramaDelegate iStreetViewPanoramaDelegate) {
        this.Sd = fq.f(iStreetViewPanoramaDelegate);
    }

    public void animateTo(StreetViewPanoramaCamera streetViewPanoramaCamera, long l2) {
        try {
            this.Sd.animateTo(streetViewPanoramaCamera, l2);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public StreetViewPanoramaLocation getLocation() {
        try {
            StreetViewPanoramaLocation streetViewPanoramaLocation = this.Sd.getStreetViewPanoramaLocation();
            return streetViewPanoramaLocation;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public StreetViewPanoramaCamera getPanoramaCamera() {
        try {
            StreetViewPanoramaCamera streetViewPanoramaCamera = this.Sd.getPanoramaCamera();
            return streetViewPanoramaCamera;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    IStreetViewPanoramaDelegate ir() {
        return this.Sd;
    }

    public boolean isPanningGesturesEnabled() {
        try {
            boolean bl2 = this.Sd.isPanningGesturesEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isStreetNamesEnabled() {
        try {
            boolean bl2 = this.Sd.isStreetNamesEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isUserNavigationEnabled() {
        try {
            boolean bl2 = this.Sd.isUserNavigationEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public boolean isZoomGesturesEnabled() {
        try {
            boolean bl2 = this.Sd.isZoomGesturesEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public Point orientationToPoint(StreetViewPanoramaOrientation streetViewPanoramaOrientation) {
        try {
            streetViewPanoramaOrientation = (Point)e.d(this.Sd.orientationToPoint(streetViewPanoramaOrientation));
            return streetViewPanoramaOrientation;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public StreetViewPanoramaOrientation pointToOrientation(Point object) {
        try {
            object = this.Sd.pointToOrientation(e.h(object));
            return object;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void setOnStreetViewPanoramaCameraChangeListener(final OnStreetViewPanoramaCameraChangeListener var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Sd.setOnStreetViewPanoramaCameraChangeListener(null);
            return;
lbl5: // 1 sources:
            this.Sd.setOnStreetViewPanoramaCameraChangeListener(new p.a(){

                @Override
                public void onStreetViewPanoramaCameraChange(StreetViewPanoramaCamera streetViewPanoramaCamera) {
                    var1_1.onStreetViewPanoramaCameraChange(streetViewPanoramaCamera);
                }
            });
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void setOnStreetViewPanoramaChangeListener(final OnStreetViewPanoramaChangeListener var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Sd.setOnStreetViewPanoramaChangeListener(null);
            return;
lbl5: // 1 sources:
            this.Sd.setOnStreetViewPanoramaChangeListener(new q.a(){

                @Override
                public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
                    var1_1.onStreetViewPanoramaChange(streetViewPanoramaLocation);
                }
            });
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void setOnStreetViewPanoramaClickListener(final OnStreetViewPanoramaClickListener var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Sd.setOnStreetViewPanoramaClickListener(null);
            return;
lbl5: // 1 sources:
            this.Sd.setOnStreetViewPanoramaClickListener(new r.a(){

                @Override
                public void onStreetViewPanoramaClick(StreetViewPanoramaOrientation streetViewPanoramaOrientation) {
                    var1_1.onStreetViewPanoramaClick(streetViewPanoramaOrientation);
                }
            });
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setPanningGesturesEnabled(boolean bl2) {
        try {
            this.Sd.enablePanning(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setPosition(LatLng latLng) {
        try {
            this.Sd.setPosition(latLng);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setPosition(LatLng latLng, int n2) {
        try {
            this.Sd.setPositionWithRadius(latLng, n2);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setPosition(String string2) {
        try {
            this.Sd.setPositionWithID(string2);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public void setStreetNamesEnabled(boolean bl2) {
        try {
            this.Sd.enableStreetNames(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setUserNavigationEnabled(boolean bl2) {
        try {
            this.Sd.enableUserNavigation(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public void setZoomGesturesEnabled(boolean bl2) {
        try {
            this.Sd.enableZoom(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public static interface OnStreetViewPanoramaCameraChangeListener {
        public void onStreetViewPanoramaCameraChange(StreetViewPanoramaCamera var1);
    }

    public static interface OnStreetViewPanoramaChangeListener {
        public void onStreetViewPanoramaChange(StreetViewPanoramaLocation var1);
    }

    public static interface OnStreetViewPanoramaClickListener {
        public void onStreetViewPanoramaClick(StreetViewPanoramaOrientation var1);
    }

}

