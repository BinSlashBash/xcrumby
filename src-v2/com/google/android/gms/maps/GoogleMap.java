/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.location.Location
 *  android.os.RemoteException
 *  android.view.View
 */
package com.google.android.gms.maps;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.ILocationSourceDelegate;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.internal.IUiSettingsDelegate;
import com.google.android.gms.maps.internal.b;
import com.google.android.gms.maps.internal.d;
import com.google.android.gms.maps.internal.e;
import com.google.android.gms.maps.internal.f;
import com.google.android.gms.maps.internal.g;
import com.google.android.gms.maps.internal.h;
import com.google.android.gms.maps.internal.i;
import com.google.android.gms.maps.internal.j;
import com.google.android.gms.maps.internal.k;
import com.google.android.gms.maps.internal.l;
import com.google.android.gms.maps.internal.m;
import com.google.android.gms.maps.internal.n;
import com.google.android.gms.maps.internal.o;
import com.google.android.gms.maps.internal.s;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.internal.IPolylineDelegate;
import com.google.android.gms.maps.model.internal.b;
import com.google.android.gms.maps.model.internal.c;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class GoogleMap {
    public static final int MAP_TYPE_HYBRID = 4;
    public static final int MAP_TYPE_NONE = 0;
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    public static final int MAP_TYPE_TERRAIN = 3;
    private final IGoogleMapDelegate Rp;
    private UiSettings Rq;

    protected GoogleMap(IGoogleMapDelegate iGoogleMapDelegate) {
        this.Rp = fq.f(iGoogleMapDelegate);
    }

    public final Circle addCircle(CircleOptions object) {
        try {
            object = new Circle(this.Rp.addCircle((CircleOptions)object));
            return object;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public final GroundOverlay addGroundOverlay(GroundOverlayOptions object) {
        block3 : {
            try {
                object = this.Rp.addGroundOverlay((GroundOverlayOptions)object);
                if (object == null) break block3;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
            object = new GroundOverlay((c)object);
            return object;
        }
        return null;
    }

    public final Marker addMarker(MarkerOptions object) {
        block3 : {
            try {
                object = this.Rp.addMarker((MarkerOptions)object);
                if (object == null) break block3;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
            object = new Marker((com.google.android.gms.maps.model.internal.f)object);
            return object;
        }
        return null;
    }

    public final Polygon addPolygon(PolygonOptions object) {
        try {
            object = new Polygon(this.Rp.addPolygon((PolygonOptions)object));
            return object;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public final Polyline addPolyline(PolylineOptions object) {
        try {
            object = new Polyline(this.Rp.addPolyline((PolylineOptions)object));
            return object;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public final TileOverlay addTileOverlay(TileOverlayOptions object) {
        block3 : {
            try {
                object = this.Rp.addTileOverlay((TileOverlayOptions)object);
                if (object == null) break block3;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
            object = new TileOverlay((com.google.android.gms.maps.model.internal.h)object);
            return object;
        }
        return null;
    }

    public final void animateCamera(CameraUpdate cameraUpdate) {
        try {
            this.Rp.animateCamera(cameraUpdate.id());
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void animateCamera(CameraUpdate object, int n2, CancelableCallback cancelableCallback) {
        try {
            IGoogleMapDelegate iGoogleMapDelegate = this.Rp;
            com.google.android.gms.dynamic.d d2 = object.id();
            object = cancelableCallback == null ? null : new a(cancelableCallback);
            iGoogleMapDelegate.animateCameraWithDurationAndCallback(d2, n2, (com.google.android.gms.maps.internal.b)object);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void animateCamera(CameraUpdate object, CancelableCallback cancelableCallback) {
        try {
            IGoogleMapDelegate iGoogleMapDelegate = this.Rp;
            com.google.android.gms.dynamic.d d2 = object.id();
            object = cancelableCallback == null ? null : new a(cancelableCallback);
            iGoogleMapDelegate.animateCameraWithCallback(d2, (com.google.android.gms.maps.internal.b)object);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public final void clear() {
        try {
            this.Rp.clear();
            return;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public final CameraPosition getCameraPosition() {
        try {
            CameraPosition cameraPosition = this.Rp.getCameraPosition();
            return cameraPosition;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public IndoorBuilding getFocusedBuilding() {
        block3 : {
            Object object;
            try {
                object = this.Rp.getFocusedBuilding();
                if (object == null) break block3;
            }
            catch (RemoteException var1_2) {
                throw new RuntimeRemoteException(var1_2);
            }
            object = new IndoorBuilding((com.google.android.gms.maps.model.internal.d)object);
            return object;
        }
        return null;
    }

    public final int getMapType() {
        try {
            int n2 = this.Rp.getMapType();
            return n2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public final float getMaxZoomLevel() {
        try {
            float f2 = this.Rp.getMaxZoomLevel();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public final float getMinZoomLevel() {
        try {
            float f2 = this.Rp.getMinZoomLevel();
            return f2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    @Deprecated
    public final Location getMyLocation() {
        try {
            Location location = this.Rp.getMyLocation();
            return location;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public final Projection getProjection() {
        try {
            Projection projection = new Projection(this.Rp.getProjection());
            return projection;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public final UiSettings getUiSettings() {
        try {
            if (this.Rq == null) {
                this.Rq = new UiSettings(this.Rp.getUiSettings());
            }
            UiSettings uiSettings = this.Rq;
            return uiSettings;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    IGoogleMapDelegate if() {
        return this.Rp;
    }

    public final boolean isBuildingsEnabled() {
        try {
            boolean bl2 = this.Rp.isBuildingsEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public final boolean isIndoorEnabled() {
        try {
            boolean bl2 = this.Rp.isIndoorEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public final boolean isMyLocationEnabled() {
        try {
            boolean bl2 = this.Rp.isMyLocationEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public final boolean isTrafficEnabled() {
        try {
            boolean bl2 = this.Rp.isTrafficEnabled();
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public final void moveCamera(CameraUpdate cameraUpdate) {
        try {
            this.Rp.moveCamera(cameraUpdate.id());
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public final void setBuildingsEnabled(boolean bl2) {
        try {
            this.Rp.setBuildingsEnabled(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public final boolean setIndoorEnabled(boolean bl2) {
        try {
            bl2 = this.Rp.setIndoorEnabled(bl2);
            return bl2;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void setInfoWindowAdapter(final InfoWindowAdapter var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Rp.setInfoWindowAdapter(null);
            return;
lbl5: // 1 sources:
            this.Rp.setInfoWindowAdapter(new d.a(){

                @Override
                public com.google.android.gms.dynamic.d f(com.google.android.gms.maps.model.internal.f f2) {
                    return com.google.android.gms.dynamic.e.h(var1_1.getInfoWindow(new Marker(f2)));
                }

                @Override
                public com.google.android.gms.dynamic.d g(com.google.android.gms.maps.model.internal.f f2) {
                    return com.google.android.gms.dynamic.e.h(var1_1.getInfoContents(new Marker(f2)));
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
    public final void setLocationSource(final LocationSource var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Rp.setLocationSource(null);
            return;
lbl5: // 1 sources:
            this.Rp.setLocationSource(new ILocationSourceDelegate.a(){

                @Override
                public void activate(final h h2) {
                    var1_1.activate(new LocationSource.OnLocationChangedListener(){

                        @Override
                        public void onLocationChanged(Location location) {
                            try {
                                h2.j(com.google.android.gms.dynamic.e.h(location));
                                return;
                            }
                            catch (RemoteException var1_2) {
                                throw new RuntimeRemoteException(var1_2);
                            }
                        }
                    });
                }

                @Override
                public void deactivate() {
                    var1_1.deactivate();
                }

            });
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public final void setMapType(int n2) {
        try {
            this.Rp.setMapType(n2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public final void setMyLocationEnabled(boolean bl2) {
        try {
            this.Rp.setMyLocationEnabled(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void setOnCameraChangeListener(final OnCameraChangeListener var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Rp.setOnCameraChangeListener(null);
            return;
lbl5: // 1 sources:
            this.Rp.setOnCameraChangeListener(new e.a(){

                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    var1_1.onCameraChange(cameraPosition);
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
    public final void setOnIndoorStateChangeListener(final OnIndoorStateChangeListener var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Rp.setOnIndoorStateChangeListener(null);
            return;
lbl5: // 1 sources:
            this.Rp.setOnIndoorStateChangeListener(new f.a(){

                @Override
                public void a(com.google.android.gms.maps.model.internal.d d2) {
                    var1_1.onIndoorLevelActivated(new IndoorBuilding(d2));
                }

                @Override
                public void onIndoorBuildingFocused() {
                    var1_1.onIndoorBuildingFocused();
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
    public final void setOnInfoWindowClickListener(final OnInfoWindowClickListener var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Rp.setOnInfoWindowClickListener(null);
            return;
lbl5: // 1 sources:
            this.Rp.setOnInfoWindowClickListener(new g.a(){

                @Override
                public void e(com.google.android.gms.maps.model.internal.f f2) {
                    var1_1.onInfoWindowClick(new Marker(f2));
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
    public final void setOnMapClickListener(final OnMapClickListener var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Rp.setOnMapClickListener(null);
            return;
lbl5: // 1 sources:
            this.Rp.setOnMapClickListener(new i.a(){

                @Override
                public void onMapClick(LatLng latLng) {
                    var1_1.onMapClick(latLng);
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
    public void setOnMapLoadedCallback(final OnMapLoadedCallback var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Rp.setOnMapLoadedCallback(null);
            return;
lbl5: // 1 sources:
            this.Rp.setOnMapLoadedCallback(new j.a(){

                @Override
                public void onMapLoaded() throws RemoteException {
                    var1_1.onMapLoaded();
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
    public final void setOnMapLongClickListener(final OnMapLongClickListener var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Rp.setOnMapLongClickListener(null);
            return;
lbl5: // 1 sources:
            this.Rp.setOnMapLongClickListener(new k.a(){

                @Override
                public void onMapLongClick(LatLng latLng) {
                    var1_1.onMapLongClick(latLng);
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
    public final void setOnMarkerClickListener(final OnMarkerClickListener var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Rp.setOnMarkerClickListener(null);
            return;
lbl5: // 1 sources:
            this.Rp.setOnMarkerClickListener(new l.a(){

                @Override
                public boolean a(com.google.android.gms.maps.model.internal.f f2) {
                    return var1_1.onMarkerClick(new Marker(f2));
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
    public final void setOnMarkerDragListener(final OnMarkerDragListener var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Rp.setOnMarkerDragListener(null);
            return;
lbl5: // 1 sources:
            this.Rp.setOnMarkerDragListener(new m.a(){

                @Override
                public void b(com.google.android.gms.maps.model.internal.f f2) {
                    var1_1.onMarkerDragStart(new Marker(f2));
                }

                @Override
                public void c(com.google.android.gms.maps.model.internal.f f2) {
                    var1_1.onMarkerDragEnd(new Marker(f2));
                }

                @Override
                public void d(com.google.android.gms.maps.model.internal.f f2) {
                    var1_1.onMarkerDrag(new Marker(f2));
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
    public final void setOnMyLocationButtonClickListener(final OnMyLocationButtonClickListener var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Rp.setOnMyLocationButtonClickListener(null);
            return;
lbl5: // 1 sources:
            this.Rp.setOnMyLocationButtonClickListener(new n.a(){

                @Override
                public boolean onMyLocationButtonClick() throws RemoteException {
                    return var1_1.onMyLocationButtonClick();
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
    @Deprecated
    public final void setOnMyLocationChangeListener(final OnMyLocationChangeListener var1_1) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.Rp.setOnMyLocationChangeListener(null);
            return;
lbl5: // 1 sources:
            this.Rp.setOnMyLocationChangeListener(new o.a(){

                @Override
                public void e(com.google.android.gms.dynamic.d d2) {
                    var1_1.onMyLocationChange((Location)com.google.android.gms.dynamic.e.d(d2));
                }
            });
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public final void setPadding(int n2, int n3, int n4, int n5) {
        try {
            this.Rp.setPadding(n2, n3, n4, n5);
            return;
        }
        catch (RemoteException var5_5) {
            throw new RuntimeRemoteException(var5_5);
        }
    }

    public final void setTrafficEnabled(boolean bl2) {
        try {
            this.Rp.setTrafficEnabled(bl2);
            return;
        }
        catch (RemoteException var2_2) {
            throw new RuntimeRemoteException(var2_2);
        }
    }

    public final void snapshot(SnapshotReadyCallback snapshotReadyCallback) {
        this.snapshot(snapshotReadyCallback, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void snapshot(final SnapshotReadyCallback snapshotReadyCallback, Bitmap object) {
        object = object != null ? com.google.android.gms.dynamic.e.h(object) : null;
        object = (com.google.android.gms.dynamic.e)object;
        try {
            this.Rp.snapshot(new s.a(){

                @Override
                public void f(com.google.android.gms.dynamic.d d2) throws RemoteException {
                    snapshotReadyCallback.onSnapshotReady((Bitmap)com.google.android.gms.dynamic.e.d(d2));
                }

                @Override
                public void onSnapshotReady(Bitmap bitmap) throws RemoteException {
                    snapshotReadyCallback.onSnapshotReady(bitmap);
                }
            }, (com.google.android.gms.dynamic.d)object);
            return;
        }
        catch (RemoteException var1_2) {
            throw new RuntimeRemoteException(var1_2);
        }
    }

    public final void stopAnimation() {
        try {
            this.Rp.stopAnimation();
            return;
        }
        catch (RemoteException var1_1) {
            throw new RuntimeRemoteException(var1_1);
        }
    }

    public static interface CancelableCallback {
        public void onCancel();

        public void onFinish();
    }

    public static interface InfoWindowAdapter {
        public View getInfoContents(Marker var1);

        public View getInfoWindow(Marker var1);
    }

    public static interface OnCameraChangeListener {
        public void onCameraChange(CameraPosition var1);
    }

    public static interface OnIndoorStateChangeListener {
        public void onIndoorBuildingFocused();

        public void onIndoorLevelActivated(IndoorBuilding var1);
    }

    public static interface OnInfoWindowClickListener {
        public void onInfoWindowClick(Marker var1);
    }

    public static interface OnMapClickListener {
        public void onMapClick(LatLng var1);
    }

    public static interface OnMapLoadedCallback {
        public void onMapLoaded();
    }

    public static interface OnMapLongClickListener {
        public void onMapLongClick(LatLng var1);
    }

    public static interface OnMarkerClickListener {
        public boolean onMarkerClick(Marker var1);
    }

    public static interface OnMarkerDragListener {
        public void onMarkerDrag(Marker var1);

        public void onMarkerDragEnd(Marker var1);

        public void onMarkerDragStart(Marker var1);
    }

    public static interface OnMyLocationButtonClickListener {
        public boolean onMyLocationButtonClick();
    }

    @Deprecated
    public static interface OnMyLocationChangeListener {
        public void onMyLocationChange(Location var1);
    }

    public static interface SnapshotReadyCallback {
        public void onSnapshotReady(Bitmap var1);
    }

    private static final class a
    extends b.a {
        private final CancelableCallback RH;

        a(CancelableCallback cancelableCallback) {
            this.RH = cancelableCallback;
        }

        @Override
        public void onCancel() {
            this.RH.onCancel();
        }

        @Override
        public void onFinish() {
            this.RH.onFinish();
        }
    }

}

