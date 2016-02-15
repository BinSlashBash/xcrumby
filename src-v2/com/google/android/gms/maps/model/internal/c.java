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
package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.d;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBoundsCreator;
import com.google.android.gms.maps.model.LatLngCreator;

public interface c
extends IInterface {
    public void a(float var1, float var2) throws RemoteException;

    public boolean a(c var1) throws RemoteException;

    public float getBearing() throws RemoteException;

    public LatLngBounds getBounds() throws RemoteException;

    public float getHeight() throws RemoteException;

    public String getId() throws RemoteException;

    public LatLng getPosition() throws RemoteException;

    public float getTransparency() throws RemoteException;

    public float getWidth() throws RemoteException;

    public float getZIndex() throws RemoteException;

    public int hashCodeRemote() throws RemoteException;

    public boolean isVisible() throws RemoteException;

    public void k(d var1) throws RemoteException;

    public void remove() throws RemoteException;

    public void setBearing(float var1) throws RemoteException;

    public void setDimensions(float var1) throws RemoteException;

    public void setPosition(LatLng var1) throws RemoteException;

    public void setPositionFromBounds(LatLngBounds var1) throws RemoteException;

    public void setTransparency(float var1) throws RemoteException;

    public void setVisible(boolean var1) throws RemoteException;

    public void setZIndex(float var1) throws RemoteException;

    public static abstract class com.google.android.gms.maps.model.internal.c$a
    extends Binder
    implements c {
        public static c aD(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
            if (iInterface != null && iInterface instanceof c) {
                return (c)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            Object var10_5 = null;
            Object var9_6 = null;
            int n4 = 0;
            int n5 = 0;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.remove();
                    parcel.writeNoException();
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    object = this.getId();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 3: {
                    void var9_8;
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    if (object.readInt() != 0) {
                        LatLng latLng = LatLng.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.setPosition((LatLng)var9_8);
                    parcel.writeNoException();
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    object = this.getPosition();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.setDimensions(object.readFloat());
                    parcel.writeNoException();
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.a(object.readFloat(), object.readFloat());
                    parcel.writeNoException();
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    float f2 = this.getWidth();
                    parcel.writeNoException();
                    parcel.writeFloat(f2);
                    return true;
                }
                case 8: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    float f3 = this.getHeight();
                    parcel.writeNoException();
                    parcel.writeFloat(f3);
                    return true;
                }
                case 9: {
                    void var9_11;
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    Object var9_9 = var10_5;
                    if (object.readInt() != 0) {
                        LatLngBounds latLngBounds = LatLngBounds.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.setPositionFromBounds((LatLngBounds)var9_11);
                    parcel.writeNoException();
                    return true;
                }
                case 10: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    object = this.getBounds();
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
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.setBearing(object.readFloat());
                    parcel.writeNoException();
                    return true;
                }
                case 12: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    float f4 = this.getBearing();
                    parcel.writeNoException();
                    parcel.writeFloat(f4);
                    return true;
                }
                case 13: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.setZIndex(object.readFloat());
                    parcel.writeNoException();
                    return true;
                }
                case 14: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    float f5 = this.getZIndex();
                    parcel.writeNoException();
                    parcel.writeFloat(f5);
                    return true;
                }
                case 15: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    boolean bl2 = object.readInt() != 0;
                    this.setVisible(bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 16: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    boolean bl3 = this.isVisible();
                    parcel.writeNoException();
                    n2 = n5;
                    if (bl3) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 17: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.setTransparency(object.readFloat());
                    parcel.writeNoException();
                    return true;
                }
                case 18: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    float f6 = this.getTransparency();
                    parcel.writeNoException();
                    parcel.writeFloat(f6);
                    return true;
                }
                case 19: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    boolean bl4 = this.a(com.google.android.gms.maps.model.internal.c$a.aD(object.readStrongBinder()));
                    parcel.writeNoException();
                    n2 = n4;
                    if (bl4) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 20: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    n2 = this.hashCodeRemote();
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 21: 
            }
            object.enforceInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
            this.k(d.a.K(object.readStrongBinder()));
            parcel.writeNoException();
            return true;
        }

        private static class a
        implements c {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            @Override
            public void a(float f2, float f3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    parcel.writeFloat(f2);
                    parcel.writeFloat(f3);
                    this.kn.transact(6, parcel, parcel2, 0);
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
            public boolean a(c c2) throws RemoteException {
                boolean bl2 = false;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    c2 = c2 != null ? c2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)c2);
                    this.kn.transact(19, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    if (n2 != 0) {
                        bl2 = true;
                    }
                    return bl2;
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
            public float getBearing() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kn.transact(12, parcel, parcel2, 0);
                    parcel2.readException();
                    float f2 = parcel2.readFloat();
                    return f2;
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
            public LatLngBounds getBounds() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kn.transact(10, parcel, parcel2, 0);
                    parcel2.readException();
                    LatLngBounds latLngBounds = parcel2.readInt() != 0 ? LatLngBounds.CREATOR.createFromParcel(parcel2) : null;
                    return latLngBounds;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public float getHeight() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kn.transact(8, parcel, parcel2, 0);
                    parcel2.readException();
                    float f2 = parcel2.readFloat();
                    return f2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    String string2 = parcel2.readString();
                    return string2;
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
            public LatLng getPosition() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kn.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    LatLng latLng = parcel2.readInt() != 0 ? LatLng.CREATOR.createFromParcel(parcel2) : null;
                    return latLng;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public float getTransparency() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kn.transact(18, parcel, parcel2, 0);
                    parcel2.readException();
                    float f2 = parcel2.readFloat();
                    return f2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public float getWidth() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kn.transact(7, parcel, parcel2, 0);
                    parcel2.readException();
                    float f2 = parcel2.readFloat();
                    return f2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public float getZIndex() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kn.transact(14, parcel, parcel2, 0);
                    parcel2.readException();
                    float f2 = parcel2.readFloat();
                    return f2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int hashCodeRemote() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kn.transact(20, parcel, parcel2, 0);
                    parcel2.readException();
                    int n2 = parcel2.readInt();
                    return n2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isVisible() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                        this.kn.transact(16, parcel, parcel2, 0);
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
            public void k(d d2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    this.kn.transact(21, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void remove() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setBearing(float f2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    parcel.writeFloat(f2);
                    this.kn.transact(11, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setDimensions(float f2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    parcel.writeFloat(f2);
                    this.kn.transact(5, parcel, parcel2, 0);
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
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    if (latLng != null) {
                        parcel.writeInt(1);
                        latLng.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(3, parcel, parcel2, 0);
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
            public void setPositionFromBounds(LatLngBounds latLngBounds) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    if (latLngBounds != null) {
                        parcel.writeInt(1);
                        latLngBounds.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(9, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setTransparency(float f2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    parcel.writeFloat(f2);
                    this.kn.transact(17, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setVisible(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(15, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setZIndex(float f2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    parcel.writeFloat(f2);
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

