/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.maps.internal.p;
import com.google.android.gms.maps.internal.q;
import com.google.android.gms.maps.internal.r;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngCreator;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaCameraCreator;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaLocationCreator;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientationCreator;

public interface IStreetViewPanoramaDelegate
extends IInterface {
    public void animateTo(StreetViewPanoramaCamera var1, long var2) throws RemoteException;

    public void enablePanning(boolean var1) throws RemoteException;

    public void enableStreetNames(boolean var1) throws RemoteException;

    public void enableUserNavigation(boolean var1) throws RemoteException;

    public void enableZoom(boolean var1) throws RemoteException;

    public StreetViewPanoramaCamera getPanoramaCamera() throws RemoteException;

    public StreetViewPanoramaLocation getStreetViewPanoramaLocation() throws RemoteException;

    public boolean isPanningGesturesEnabled() throws RemoteException;

    public boolean isStreetNamesEnabled() throws RemoteException;

    public boolean isUserNavigationEnabled() throws RemoteException;

    public boolean isZoomGesturesEnabled() throws RemoteException;

    public d orientationToPoint(StreetViewPanoramaOrientation var1) throws RemoteException;

    public StreetViewPanoramaOrientation pointToOrientation(d var1) throws RemoteException;

    public void setOnStreetViewPanoramaCameraChangeListener(p var1) throws RemoteException;

    public void setOnStreetViewPanoramaChangeListener(q var1) throws RemoteException;

    public void setOnStreetViewPanoramaClickListener(r var1) throws RemoteException;

    public void setPosition(LatLng var1) throws RemoteException;

    public void setPositionWithID(String var1) throws RemoteException;

    public void setPositionWithRadius(LatLng var1, int var2) throws RemoteException;

    public static abstract class com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate$a
    extends Binder
    implements IStreetViewPanoramaDelegate {
        public static IStreetViewPanoramaDelegate ax(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
            if (iInterface != null && iInterface instanceof IStreetViewPanoramaDelegate) {
                return (IStreetViewPanoramaDelegate)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            Object var15_5 = null;
            Object var13_6 = null;
            d d2 = null;
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = false;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            boolean bl5 = false;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (object.readInt() != 0) {
                        bl5 = true;
                    }
                    this.enableZoom(bl5);
                    parcel.writeNoException();
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    bl5 = bl2;
                    if (object.readInt() != 0) {
                        bl5 = true;
                    }
                    this.enablePanning(bl5);
                    parcel.writeNoException();
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    bl5 = bl3;
                    if (object.readInt() != 0) {
                        bl5 = true;
                    }
                    this.enableUserNavigation(bl5);
                    parcel.writeNoException();
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    bl5 = bl4;
                    if (object.readInt() != 0) {
                        bl5 = true;
                    }
                    this.enableStreetNames(bl5);
                    parcel.writeNoException();
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    bl5 = this.isZoomGesturesEnabled();
                    parcel.writeNoException();
                    n2 = n4;
                    if (bl5) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    bl5 = this.isPanningGesturesEnabled();
                    parcel.writeNoException();
                    n2 = n5;
                    if (bl5) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    bl5 = this.isUserNavigationEnabled();
                    parcel.writeNoException();
                    n2 = n6;
                    if (bl5) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 8: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    bl5 = this.isStreetNamesEnabled();
                    parcel.writeNoException();
                    n2 = n7;
                    if (bl5) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 9: {
                    void var13_8;
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (object.readInt() != 0) {
                        StreetViewPanoramaCamera streetViewPanoramaCamera = StreetViewPanoramaCamera.CREATOR.createFromParcel((Parcel)object);
                    } else {
                        Object var13_9 = null;
                    }
                    this.animateTo((StreetViewPanoramaCamera)var13_8, object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 10: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    object = this.getPanoramaCamera();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 11: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.setPositionWithID(object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 12: {
                    void var13_12;
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    d d3 = d2;
                    if (object.readInt() != 0) {
                        LatLng latLng = LatLng.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.setPosition((LatLng)var13_12);
                    parcel.writeNoException();
                    return true;
                }
                case 13: {
                    void var13_15;
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    Object var13_13 = var15_5;
                    if (object.readInt() != 0) {
                        LatLng latLng = LatLng.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.setPositionWithRadius((LatLng)var13_15, object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 14: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    object = this.getStreetViewPanoramaLocation();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 15: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.setOnStreetViewPanoramaChangeListener(q.a.at(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 16: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.setOnStreetViewPanoramaCameraChangeListener(p.a.as(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 17: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.setOnStreetViewPanoramaClickListener(r.a.au(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 18: {
                    object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    object = this.pointToOrientation(d.a.K(object.readStrongBinder()));
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 19: 
            }
            object.enforceInterface("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
            object = object.readInt() != 0 ? StreetViewPanoramaOrientation.CREATOR.createFromParcel((Parcel)object) : null;
            d2 = this.orientationToPoint((StreetViewPanoramaOrientation)object);
            parcel.writeNoException();
            object = var13_6;
            if (d2 != null) {
                object = d2.asBinder();
            }
            parcel.writeStrongBinder((IBinder)object);
            return true;
        }

        private static class a
        implements IStreetViewPanoramaDelegate {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void animateTo(StreetViewPanoramaCamera streetViewPanoramaCamera, long l2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (streetViewPanoramaCamera != null) {
                        parcel.writeInt(1);
                        streetViewPanoramaCamera.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l2);
                    this.kn.transact(9, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public void enablePanning(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(2, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void enableStreetNames(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(4, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void enableUserNavigation(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(3, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void enableZoom(boolean bl2) throws RemoteException {
                int n2 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (!bl2) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public StreetViewPanoramaCamera getPanoramaCamera() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.kn.transact(10, parcel, parcel2, 0);
                    parcel2.readException();
                    StreetViewPanoramaCamera streetViewPanoramaCamera = parcel2.readInt() != 0 ? StreetViewPanoramaCamera.CREATOR.createFromParcel(parcel2) : null;
                    return streetViewPanoramaCamera;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public StreetViewPanoramaLocation getStreetViewPanoramaLocation() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    this.kn.transact(14, parcel, parcel2, 0);
                    parcel2.readException();
                    StreetViewPanoramaLocation streetViewPanoramaLocation = parcel2.readInt() != 0 ? StreetViewPanoramaLocation.CREATOR.createFromParcel(parcel2) : null;
                    return streetViewPanoramaLocation;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isPanningGesturesEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                        this.kn.transact(6, parcel, parcel2, 0);
                        parcel2.readException();
                        int n2 = parcel2.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable var5_5) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw var5_5;
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                return bl2;
            }

            @Override
            public boolean isStreetNamesEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                        this.kn.transact(8, parcel, parcel2, 0);
                        parcel2.readException();
                        int n2 = parcel2.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable var5_5) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw var5_5;
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                return bl2;
            }

            @Override
            public boolean isUserNavigationEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                        this.kn.transact(7, parcel, parcel2, 0);
                        parcel2.readException();
                        int n2 = parcel2.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable var5_5) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw var5_5;
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                return bl2;
            }

            @Override
            public boolean isZoomGesturesEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                        this.kn.transact(5, parcel, parcel2, 0);
                        parcel2.readException();
                        int n2 = parcel2.readInt();
                        if (n2 == 0) break block2;
                        bl2 = true;
                    }
                    catch (Throwable var5_5) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw var5_5;
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                return bl2;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public d orientationToPoint(StreetViewPanoramaOrientation object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(19, parcel, parcel2, 0);
                    parcel2.readException();
                    object = d.a.K(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public StreetViewPanoramaOrientation pointToOrientation(d object) throws RemoteException {
                Object var2_3 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    object = object != null ? object.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)object);
                    this.kn.transact(18, parcel, parcel2, 0);
                    parcel2.readException();
                    object = var2_3;
                    if (parcel2.readInt() != 0) {
                        object = StreetViewPanoramaOrientation.CREATOR.createFromParcel(parcel2);
                    }
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setOnStreetViewPanoramaCameraChangeListener(p p2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    p2 = p2 != null ? p2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)p2);
                    this.kn.transact(16, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setOnStreetViewPanoramaChangeListener(q q2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    q2 = q2 != null ? q2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)q2);
                    this.kn.transact(15, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setOnStreetViewPanoramaClickListener(r r2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    r2 = r2 != null ? r2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)r2);
                    this.kn.transact(17, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setPosition(LatLng latLng) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (latLng != null) {
                        parcel.writeInt(1);
                        latLng.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(12, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setPositionWithID(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    parcel.writeString(string2);
                    this.kn.transact(11, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setPositionWithRadius(LatLng latLng, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate");
                    if (latLng != null) {
                        parcel.writeInt(1);
                        latLng.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(13, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

