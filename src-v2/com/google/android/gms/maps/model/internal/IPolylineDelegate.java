/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngCreator;
import java.util.ArrayList;
import java.util.List;

public interface IPolylineDelegate
extends IInterface {
    public boolean equalsRemote(IPolylineDelegate var1) throws RemoteException;

    public int getColor() throws RemoteException;

    public String getId() throws RemoteException;

    public List<LatLng> getPoints() throws RemoteException;

    public float getWidth() throws RemoteException;

    public float getZIndex() throws RemoteException;

    public int hashCodeRemote() throws RemoteException;

    public boolean isGeodesic() throws RemoteException;

    public boolean isVisible() throws RemoteException;

    public void remove() throws RemoteException;

    public void setColor(int var1) throws RemoteException;

    public void setGeodesic(boolean var1) throws RemoteException;

    public void setPoints(List<LatLng> var1) throws RemoteException;

    public void setVisible(boolean var1) throws RemoteException;

    public void setWidth(float var1) throws RemoteException;

    public void setZIndex(float var1) throws RemoteException;

    public static abstract class com.google.android.gms.maps.model.internal.IPolylineDelegate$a
    extends Binder
    implements IPolylineDelegate {
        public static IPolylineDelegate aI(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
            if (iInterface != null && iInterface instanceof IPolylineDelegate) {
                return (IPolylineDelegate)iInterface;
            }
            return new a(iBinder);
        }

        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            int n4 = 0;
            boolean bl2 = false;
            int n5 = 0;
            int n6 = 0;
            boolean bl3 = false;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.remove();
                    parcel.writeNoException();
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    object = this.getId();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.setPoints((List)object.createTypedArrayList((Parcelable.Creator)LatLng.CREATOR));
                    parcel.writeNoException();
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    object = this.getPoints();
                    parcel.writeNoException();
                    parcel.writeTypedList((List)object);
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.setWidth(object.readFloat());
                    parcel.writeNoException();
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    float f2 = this.getWidth();
                    parcel.writeNoException();
                    parcel.writeFloat(f2);
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.setColor(object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 8: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    n2 = this.getColor();
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 9: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.setZIndex(object.readFloat());
                    parcel.writeNoException();
                    return true;
                }
                case 10: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    float f3 = this.getZIndex();
                    parcel.writeNoException();
                    parcel.writeFloat(f3);
                    return true;
                }
                case 11: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    if (object.readInt() != 0) {
                        bl3 = true;
                    }
                    this.setVisible(bl3);
                    parcel.writeNoException();
                    return true;
                }
                case 12: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    bl3 = this.isVisible();
                    parcel.writeNoException();
                    n2 = n4;
                    if (bl3) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 13: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    bl3 = bl2;
                    if (object.readInt() != 0) {
                        bl3 = true;
                    }
                    this.setGeodesic(bl3);
                    parcel.writeNoException();
                    return true;
                }
                case 14: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    bl3 = this.isGeodesic();
                    parcel.writeNoException();
                    n2 = n5;
                    if (bl3) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 15: {
                    object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    bl3 = this.equalsRemote(com.google.android.gms.maps.model.internal.IPolylineDelegate$a.aI(object.readStrongBinder()));
                    parcel.writeNoException();
                    n2 = n6;
                    if (bl3) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 16: 
            }
            object.enforceInterface("com.google.android.gms.maps.model.internal.IPolylineDelegate");
            n2 = this.hashCodeRemote();
            parcel.writeNoException();
            parcel.writeInt(n2);
            return true;
        }

        private static class a
        implements IPolylineDelegate {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public IBinder asBinder() {
                return this.kn;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean equalsRemote(IPolylineDelegate iPolylineDelegate) throws RemoteException {
                boolean bl2 = false;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    iPolylineDelegate = iPolylineDelegate != null ? iPolylineDelegate.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iPolylineDelegate);
                    this.kn.transact(15, parcel, parcel2, 0);
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

            @Override
            public int getColor() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.kn.transact(8, parcel, parcel2, 0);
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
            public String getId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
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

            @Override
            public List<LatLng> getPoints() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.kn.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    ArrayList arrayList = parcel2.createTypedArrayList((Parcelable.Creator)LatLng.CREATOR);
                    return arrayList;
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
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.kn.transact(6, parcel, parcel2, 0);
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
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.kn.transact(10, parcel, parcel2, 0);
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
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    this.kn.transact(16, parcel, parcel2, 0);
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
            public boolean isGeodesic() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        this.kn.transact(14, parcel, parcel2, 0);
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
            public boolean isVisible() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                        this.kn.transact(12, parcel, parcel2, 0);
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
            public void remove() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
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
            public void setColor(int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    parcel.writeInt(n2);
                    this.kn.transact(7, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setGeodesic(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(13, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setPoints(List<LatLng> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    parcel.writeTypedList(list);
                    this.kn.transact(3, parcel, parcel2, 0);
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
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(11, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setWidth(float f2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
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

            @Override
            public void setZIndex(float f2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.model.internal.IPolylineDelegate");
                    parcel.writeFloat(f2);
                    this.kn.transact(9, parcel, parcel2, 0);
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

