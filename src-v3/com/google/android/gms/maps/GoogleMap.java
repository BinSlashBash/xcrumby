package com.google.android.gms.maps;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.dynamic.C0306d;
import com.google.android.gms.dynamic.C1324e;
import com.google.android.gms.internal.fq;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.internal.C0432b.C0977a;
import com.google.android.gms.maps.internal.C0434d.C0981a;
import com.google.android.gms.maps.internal.C0435e.C0983a;
import com.google.android.gms.maps.internal.C0436f.C0985a;
import com.google.android.gms.maps.internal.C0437g.C0987a;
import com.google.android.gms.maps.internal.C0438h;
import com.google.android.gms.maps.internal.C0439i.C0991a;
import com.google.android.gms.maps.internal.C0440j.C0993a;
import com.google.android.gms.maps.internal.C0441k.C0995a;
import com.google.android.gms.maps.internal.C0442l.C0997a;
import com.google.android.gms.maps.internal.C0443m.C0999a;
import com.google.android.gms.maps.internal.C0444n.C1001a;
import com.google.android.gms.maps.internal.C0445o.C1003a;
import com.google.android.gms.maps.internal.C0449s.C1011a;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.ILocationSourceDelegate.C0961a;
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
import com.google.android.gms.maps.model.internal.C0464c;
import com.google.android.gms.maps.model.internal.C0465d;
import com.google.android.gms.maps.model.internal.C0467f;
import com.google.android.gms.maps.model.internal.C0469h;

public final class GoogleMap {
    public static final int MAP_TYPE_HYBRID = 4;
    public static final int MAP_TYPE_NONE = 0;
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    public static final int MAP_TYPE_TERRAIN = 3;
    private final IGoogleMapDelegate Rp;
    private UiSettings Rq;

    public interface CancelableCallback {
        void onCancel();

        void onFinish();
    }

    public interface InfoWindowAdapter {
        View getInfoContents(Marker marker);

        View getInfoWindow(Marker marker);
    }

    public interface OnCameraChangeListener {
        void onCameraChange(CameraPosition cameraPosition);
    }

    public interface OnIndoorStateChangeListener {
        void onIndoorBuildingFocused();

        void onIndoorLevelActivated(IndoorBuilding indoorBuilding);
    }

    public interface OnInfoWindowClickListener {
        void onInfoWindowClick(Marker marker);
    }

    public interface OnMapClickListener {
        void onMapClick(LatLng latLng);
    }

    public interface OnMapLoadedCallback {
        void onMapLoaded();
    }

    public interface OnMapLongClickListener {
        void onMapLongClick(LatLng latLng);
    }

    public interface OnMarkerClickListener {
        boolean onMarkerClick(Marker marker);
    }

    public interface OnMarkerDragListener {
        void onMarkerDrag(Marker marker);

        void onMarkerDragEnd(Marker marker);

        void onMarkerDragStart(Marker marker);
    }

    public interface OnMyLocationButtonClickListener {
        boolean onMyLocationButtonClick();
    }

    @Deprecated
    public interface OnMyLocationChangeListener {
        void onMyLocationChange(Location location);
    }

    public interface SnapshotReadyCallback {
        void onSnapshotReady(Bitmap bitmap);
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.10 */
    class AnonymousClass10 extends C0997a {
        final /* synthetic */ OnMarkerClickListener RD;
        final /* synthetic */ GoogleMap Rs;

        AnonymousClass10(GoogleMap googleMap, OnMarkerClickListener onMarkerClickListener) {
            this.Rs = googleMap;
            this.RD = onMarkerClickListener;
        }

        public boolean m3171a(C0467f c0467f) {
            return this.RD.onMarkerClick(new Marker(c0467f));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.11 */
    class AnonymousClass11 extends C0999a {
        final /* synthetic */ OnMarkerDragListener RE;
        final /* synthetic */ GoogleMap Rs;

        AnonymousClass11(GoogleMap googleMap, OnMarkerDragListener onMarkerDragListener) {
            this.Rs = googleMap;
            this.RE = onMarkerDragListener;
        }

        public void m3172b(C0467f c0467f) {
            this.RE.onMarkerDragStart(new Marker(c0467f));
        }

        public void m3173c(C0467f c0467f) {
            this.RE.onMarkerDragEnd(new Marker(c0467f));
        }

        public void m3174d(C0467f c0467f) {
            this.RE.onMarkerDrag(new Marker(c0467f));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.12 */
    class AnonymousClass12 extends C0987a {
        final /* synthetic */ OnInfoWindowClickListener RF;
        final /* synthetic */ GoogleMap Rs;

        AnonymousClass12(GoogleMap googleMap, OnInfoWindowClickListener onInfoWindowClickListener) {
            this.Rs = googleMap;
            this.RF = onInfoWindowClickListener;
        }

        public void m3175e(C0467f c0467f) {
            this.RF.onInfoWindowClick(new Marker(c0467f));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.13 */
    class AnonymousClass13 extends C0981a {
        final /* synthetic */ InfoWindowAdapter RG;
        final /* synthetic */ GoogleMap Rs;

        AnonymousClass13(GoogleMap googleMap, InfoWindowAdapter infoWindowAdapter) {
            this.Rs = googleMap;
            this.RG = infoWindowAdapter;
        }

        public C0306d m3176f(C0467f c0467f) {
            return C1324e.m2710h(this.RG.getInfoWindow(new Marker(c0467f)));
        }

        public C0306d m3177g(C0467f c0467f) {
            return C1324e.m2710h(this.RG.getInfoContents(new Marker(c0467f)));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.1 */
    class C13971 extends C0985a {
        final /* synthetic */ OnIndoorStateChangeListener Rr;
        final /* synthetic */ GoogleMap Rs;

        C13971(GoogleMap googleMap, OnIndoorStateChangeListener onIndoorStateChangeListener) {
            this.Rs = googleMap;
            this.Rr = onIndoorStateChangeListener;
        }

        public void m3178a(C0465d c0465d) {
            this.Rr.onIndoorLevelActivated(new IndoorBuilding(c0465d));
        }

        public void onIndoorBuildingFocused() {
            this.Rr.onIndoorBuildingFocused();
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.2 */
    class C13982 extends C1003a {
        final /* synthetic */ GoogleMap Rs;
        final /* synthetic */ OnMyLocationChangeListener Rt;

        C13982(GoogleMap googleMap, OnMyLocationChangeListener onMyLocationChangeListener) {
            this.Rs = googleMap;
            this.Rt = onMyLocationChangeListener;
        }

        public void m3179e(C0306d c0306d) {
            this.Rt.onMyLocationChange((Location) C1324e.m2709d(c0306d));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.3 */
    class C13993 extends C1001a {
        final /* synthetic */ GoogleMap Rs;
        final /* synthetic */ OnMyLocationButtonClickListener Ru;

        C13993(GoogleMap googleMap, OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
            this.Rs = googleMap;
            this.Ru = onMyLocationButtonClickListener;
        }

        public boolean onMyLocationButtonClick() throws RemoteException {
            return this.Ru.onMyLocationButtonClick();
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.4 */
    class C14004 extends C0993a {
        final /* synthetic */ GoogleMap Rs;
        final /* synthetic */ OnMapLoadedCallback Rv;

        C14004(GoogleMap googleMap, OnMapLoadedCallback onMapLoadedCallback) {
            this.Rs = googleMap;
            this.Rv = onMapLoadedCallback;
        }

        public void onMapLoaded() throws RemoteException {
            this.Rv.onMapLoaded();
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.5 */
    class C14015 extends C1011a {
        final /* synthetic */ GoogleMap Rs;
        final /* synthetic */ SnapshotReadyCallback Rw;

        C14015(GoogleMap googleMap, SnapshotReadyCallback snapshotReadyCallback) {
            this.Rs = googleMap;
            this.Rw = snapshotReadyCallback;
        }

        public void m3180f(C0306d c0306d) throws RemoteException {
            this.Rw.onSnapshotReady((Bitmap) C1324e.m2709d(c0306d));
        }

        public void onSnapshotReady(Bitmap snapshot) throws RemoteException {
            this.Rw.onSnapshotReady(snapshot);
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.6 */
    class C14026 extends C0961a {
        final /* synthetic */ GoogleMap Rs;
        final /* synthetic */ LocationSource Rx;

        /* renamed from: com.google.android.gms.maps.GoogleMap.6.1 */
        class C09431 implements OnLocationChangedListener {
            final /* synthetic */ C0438h Ry;
            final /* synthetic */ C14026 Rz;

            C09431(C14026 c14026, C0438h c0438h) {
                this.Rz = c14026;
                this.Ry = c0438h;
            }

            public void onLocationChanged(Location location) {
                try {
                    this.Ry.m1239j(C1324e.m2710h(location));
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                }
            }
        }

        C14026(GoogleMap googleMap, LocationSource locationSource) {
            this.Rs = googleMap;
            this.Rx = locationSource;
        }

        public void activate(C0438h listener) {
            this.Rx.activate(new C09431(this, listener));
        }

        public void deactivate() {
            this.Rx.deactivate();
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.7 */
    class C14037 extends C0983a {
        final /* synthetic */ OnCameraChangeListener RA;
        final /* synthetic */ GoogleMap Rs;

        C14037(GoogleMap googleMap, OnCameraChangeListener onCameraChangeListener) {
            this.Rs = googleMap;
            this.RA = onCameraChangeListener;
        }

        public void onCameraChange(CameraPosition position) {
            this.RA.onCameraChange(position);
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.8 */
    class C14048 extends C0991a {
        final /* synthetic */ OnMapClickListener RB;
        final /* synthetic */ GoogleMap Rs;

        C14048(GoogleMap googleMap, OnMapClickListener onMapClickListener) {
            this.Rs = googleMap;
            this.RB = onMapClickListener;
        }

        public void onMapClick(LatLng point) {
            this.RB.onMapClick(point);
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.9 */
    class C14059 extends C0995a {
        final /* synthetic */ OnMapLongClickListener RC;
        final /* synthetic */ GoogleMap Rs;

        C14059(GoogleMap googleMap, OnMapLongClickListener onMapLongClickListener) {
            this.Rs = googleMap;
            this.RC = onMapLongClickListener;
        }

        public void onMapLongClick(LatLng point) {
            this.RC.onMapLongClick(point);
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.a */
    private static final class C1406a extends C0977a {
        private final CancelableCallback RH;

        C1406a(CancelableCallback cancelableCallback) {
            this.RH = cancelableCallback;
        }

        public void onCancel() {
            this.RH.onCancel();
        }

        public void onFinish() {
            this.RH.onFinish();
        }
    }

    protected GoogleMap(IGoogleMapDelegate map) {
        this.Rp = (IGoogleMapDelegate) fq.m985f(map);
    }

    public final Circle addCircle(CircleOptions options) {
        try {
            return new Circle(this.Rp.addCircle(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final GroundOverlay addGroundOverlay(GroundOverlayOptions options) {
        try {
            C0464c addGroundOverlay = this.Rp.addGroundOverlay(options);
            return addGroundOverlay != null ? new GroundOverlay(addGroundOverlay) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Marker addMarker(MarkerOptions options) {
        try {
            C0467f addMarker = this.Rp.addMarker(options);
            return addMarker != null ? new Marker(addMarker) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polygon addPolygon(PolygonOptions options) {
        try {
            return new Polygon(this.Rp.addPolygon(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polyline addPolyline(PolylineOptions options) {
        try {
            return new Polyline(this.Rp.addPolyline(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final TileOverlay addTileOverlay(TileOverlayOptions options) {
        try {
            C0469h addTileOverlay = this.Rp.addTileOverlay(options);
            return addTileOverlay != null ? new TileOverlay(addTileOverlay) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update) {
        try {
            this.Rp.animateCamera(update.id());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update, int durationMs, CancelableCallback callback) {
        try {
            this.Rp.animateCameraWithDurationAndCallback(update.id(), durationMs, callback == null ? null : new C1406a(callback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update, CancelableCallback callback) {
        try {
            this.Rp.animateCameraWithCallback(update.id(), callback == null ? null : new C1406a(callback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void clear() {
        try {
            this.Rp.clear();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final CameraPosition getCameraPosition() {
        try {
            return this.Rp.getCameraPosition();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public IndoorBuilding getFocusedBuilding() {
        try {
            C0465d focusedBuilding = this.Rp.getFocusedBuilding();
            return focusedBuilding != null ? new IndoorBuilding(focusedBuilding) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final int getMapType() {
        try {
            return this.Rp.getMapType();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMaxZoomLevel() {
        try {
            return this.Rp.getMaxZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMinZoomLevel() {
        try {
            return this.Rp.getMinZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public final Location getMyLocation() {
        try {
            return this.Rp.getMyLocation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Projection getProjection() {
        try {
            return new Projection(this.Rp.getProjection());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final UiSettings getUiSettings() {
        try {
            if (this.Rq == null) {
                this.Rq = new UiSettings(this.Rp.getUiSettings());
            }
            return this.Rq;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    IGoogleMapDelegate m1223if() {
        return this.Rp;
    }

    public final boolean isBuildingsEnabled() {
        try {
            return this.Rp.isBuildingsEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isIndoorEnabled() {
        try {
            return this.Rp.isIndoorEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isMyLocationEnabled() {
        try {
            return this.Rp.isMyLocationEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isTrafficEnabled() {
        try {
            return this.Rp.isTrafficEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void moveCamera(CameraUpdate update) {
        try {
            this.Rp.moveCamera(update.id());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setBuildingsEnabled(boolean enabled) {
        try {
            this.Rp.setBuildingsEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean setIndoorEnabled(boolean enabled) {
        try {
            return this.Rp.setIndoorEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setInfoWindowAdapter(InfoWindowAdapter adapter) {
        if (adapter == null) {
            try {
                this.Rp.setInfoWindowAdapter(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.Rp.setInfoWindowAdapter(new AnonymousClass13(this, adapter));
    }

    public final void setLocationSource(LocationSource source) {
        if (source == null) {
            try {
                this.Rp.setLocationSource(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.Rp.setLocationSource(new C14026(this, source));
    }

    public final void setMapType(int type) {
        try {
            this.Rp.setMapType(type);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setMyLocationEnabled(boolean enabled) {
        try {
            this.Rp.setMyLocationEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnCameraChangeListener(OnCameraChangeListener listener) {
        if (listener == null) {
            try {
                this.Rp.setOnCameraChangeListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.Rp.setOnCameraChangeListener(new C14037(this, listener));
    }

    public final void setOnIndoorStateChangeListener(OnIndoorStateChangeListener listener) {
        if (listener == null) {
            try {
                this.Rp.setOnIndoorStateChangeListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.Rp.setOnIndoorStateChangeListener(new C13971(this, listener));
    }

    public final void setOnInfoWindowClickListener(OnInfoWindowClickListener listener) {
        if (listener == null) {
            try {
                this.Rp.setOnInfoWindowClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.Rp.setOnInfoWindowClickListener(new AnonymousClass12(this, listener));
    }

    public final void setOnMapClickListener(OnMapClickListener listener) {
        if (listener == null) {
            try {
                this.Rp.setOnMapClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.Rp.setOnMapClickListener(new C14048(this, listener));
    }

    public void setOnMapLoadedCallback(OnMapLoadedCallback callback) {
        if (callback == null) {
            try {
                this.Rp.setOnMapLoadedCallback(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.Rp.setOnMapLoadedCallback(new C14004(this, callback));
    }

    public final void setOnMapLongClickListener(OnMapLongClickListener listener) {
        if (listener == null) {
            try {
                this.Rp.setOnMapLongClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.Rp.setOnMapLongClickListener(new C14059(this, listener));
    }

    public final void setOnMarkerClickListener(OnMarkerClickListener listener) {
        if (listener == null) {
            try {
                this.Rp.setOnMarkerClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.Rp.setOnMarkerClickListener(new AnonymousClass10(this, listener));
    }

    public final void setOnMarkerDragListener(OnMarkerDragListener listener) {
        if (listener == null) {
            try {
                this.Rp.setOnMarkerDragListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.Rp.setOnMarkerDragListener(new AnonymousClass11(this, listener));
    }

    public final void setOnMyLocationButtonClickListener(OnMyLocationButtonClickListener listener) {
        if (listener == null) {
            try {
                this.Rp.setOnMyLocationButtonClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.Rp.setOnMyLocationButtonClickListener(new C13993(this, listener));
    }

    @Deprecated
    public final void setOnMyLocationChangeListener(OnMyLocationChangeListener listener) {
        if (listener == null) {
            try {
                this.Rp.setOnMyLocationChangeListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.Rp.setOnMyLocationChangeListener(new C13982(this, listener));
    }

    public final void setPadding(int left, int top, int right, int bottom) {
        try {
            this.Rp.setPadding(left, top, right, bottom);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setTrafficEnabled(boolean enabled) {
        try {
            this.Rp.setTrafficEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void snapshot(SnapshotReadyCallback callback) {
        snapshot(callback, null);
    }

    public final void snapshot(SnapshotReadyCallback callback, Bitmap bitmap) {
        try {
            this.Rp.snapshot(new C14015(this, callback), (C1324e) (bitmap != null ? C1324e.m2710h(bitmap) : null));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void stopAnimation() {
        try {
            this.Rp.stopAnimation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
