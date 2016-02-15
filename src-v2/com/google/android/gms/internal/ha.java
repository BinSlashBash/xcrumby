/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.location.Location
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.gz;
import com.google.android.gms.internal.hd;
import com.google.android.gms.internal.he;
import com.google.android.gms.internal.hg;
import com.google.android.gms.internal.hh;
import com.google.android.gms.internal.hi;
import com.google.android.gms.internal.hj;
import com.google.android.gms.internal.hk;
import com.google.android.gms.internal.hl;
import com.google.android.gms.internal.ho;
import com.google.android.gms.internal.hq;
import com.google.android.gms.internal.hs;
import com.google.android.gms.internal.ht;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationRequestCreator;
import com.google.android.gms.location.a;
import com.google.android.gms.location.b;
import com.google.android.gms.location.c;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBoundsCreator;
import com.google.android.gms.maps.model.LatLngCreator;
import java.util.ArrayList;
import java.util.List;

public interface ha
extends IInterface {
    public void a(long var1, boolean var3, PendingIntent var4) throws RemoteException;

    public void a(PendingIntent var1) throws RemoteException;

    public void a(PendingIntent var1, gz var2, String var3) throws RemoteException;

    public void a(Location var1, int var2) throws RemoteException;

    public void a(gz var1, String var2) throws RemoteException;

    public void a(hg var1, hs var2, hq var3) throws RemoteException;

    public void a(hi var1, hs var2) throws RemoteException;

    public void a(hk var1, hs var2, PendingIntent var3) throws RemoteException;

    public void a(ho var1, hs var2, hq var3) throws RemoteException;

    public void a(hs var1, PendingIntent var2) throws RemoteException;

    public void a(LocationRequest var1, PendingIntent var2) throws RemoteException;

    public void a(LocationRequest var1, com.google.android.gms.location.a var2) throws RemoteException;

    public void a(LocationRequest var1, com.google.android.gms.location.a var2, String var3) throws RemoteException;

    public void a(com.google.android.gms.location.a var1) throws RemoteException;

    public void a(LatLng var1, hg var2, hs var3, hq var4) throws RemoteException;

    public void a(LatLngBounds var1, int var2, hg var3, hs var4, hq var5) throws RemoteException;

    public void a(LatLngBounds var1, int var2, String var3, hg var4, hs var5, hq var6) throws RemoteException;

    public void a(String var1, hs var2, hq var3) throws RemoteException;

    public void a(String var1, LatLngBounds var2, hg var3, hs var4, hq var5) throws RemoteException;

    public void a(List<hd> var1, PendingIntent var2, gz var3, String var4) throws RemoteException;

    public void a(String[] var1, gz var2, String var3) throws RemoteException;

    public Location aW(String var1) throws RemoteException;

    public b aX(String var1) throws RemoteException;

    public void b(String var1, hs var2, hq var3) throws RemoteException;

    public Location hP() throws RemoteException;

    public void removeActivityUpdates(PendingIntent var1) throws RemoteException;

    public void setMockLocation(Location var1) throws RemoteException;

    public void setMockMode(boolean var1) throws RemoteException;

    public static abstract class com.google.android.gms.internal.ha$a
    extends Binder
    implements ha {
        public static ha W(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
            if (iInterface != null && iInterface instanceof ha) {
                return (ha)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel object2, int n3) throws RemoteException {
            boolean bl2 = false;
            Object object3 = null;
            Object var13_7 = null;
            String string2 = null;
            Object object4 = null;
            Object var14_10 = null;
            Object var12_11 = null;
            Object var16_12 = null;
            Object var8_13 = null;
            Object var15_64 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, (Parcel)object2, n3);
                }
                case 1598968902: {
                    object2.writeString("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    return true;
                }
                case 1: {
                    void var8_15;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    object4 = object.createTypedArrayList((Parcelable.Creator)hd.CREATOR);
                    if (object.readInt() != 0) {
                        PendingIntent pendingIntent = (PendingIntent)PendingIntent.CREATOR.createFromParcel((Parcel)object);
                    } else {
                        Object var8_16 = null;
                    }
                    this.a((List)object4, (PendingIntent)var8_15, gz.a.V(object.readStrongBinder()), object.readString());
                    object2.writeNoException();
                    return true;
                }
                case 2: {
                    void var8_18;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (object.readInt() != 0) {
                        PendingIntent pendingIntent = (PendingIntent)PendingIntent.CREATOR.createFromParcel((Parcel)object);
                    } else {
                        Object var8_19 = null;
                    }
                    this.a((PendingIntent)var8_18, gz.a.V(object.readStrongBinder()), object.readString());
                    object2.writeNoException();
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    this.a(object.createStringArray(), gz.a.V(object.readStrongBinder()), object.readString());
                    object2.writeNoException();
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    this.a(gz.a.V(object.readStrongBinder()), object.readString());
                    object2.writeNoException();
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    long l2 = object.readLong();
                    bl2 = object.readInt() != 0;
                    object = object.readInt() != 0 ? (PendingIntent)PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                    this.a(l2, bl2, (PendingIntent)object);
                    object2.writeNoException();
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    object = object.readInt() != 0 ? (PendingIntent)PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                    this.removeActivityUpdates((PendingIntent)object);
                    object2.writeNoException();
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    object = this.hP();
                    object2.writeNoException();
                    if (object != null) {
                        object2.writeInt(1);
                        object.writeToParcel((Parcel)object2, 1);
                        return true;
                    }
                    object2.writeInt(0);
                    return true;
                }
                case 8: {
                    void var8_22;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    Object var8_20 = var15_64;
                    if (object.readInt() != 0) {
                        LocationRequest locationRequest = LocationRequest.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.a((LocationRequest)var8_22, a.a.U(object.readStrongBinder()));
                    object2.writeNoException();
                    return true;
                }
                case 9: {
                    void var8_24;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (object.readInt() != 0) {
                        LocationRequest locationRequest = LocationRequest.CREATOR.createFromParcel((Parcel)object);
                    } else {
                        Object var8_25 = null;
                    }
                    object = object.readInt() != 0 ? (PendingIntent)PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                    this.a((LocationRequest)var8_24, (PendingIntent)object);
                    object2.writeNoException();
                    return true;
                }
                case 10: {
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    this.a(a.a.U(object.readStrongBinder()));
                    object2.writeNoException();
                    return true;
                }
                case 11: {
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    object = object.readInt() != 0 ? (PendingIntent)PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                    this.a((PendingIntent)object);
                    object2.writeNoException();
                    return true;
                }
                case 12: {
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.setMockMode(bl2);
                    object2.writeNoException();
                    return true;
                }
                case 13: {
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    object = object.readInt() != 0 ? (Location)Location.CREATOR.createFromParcel((Parcel)object) : null;
                    this.setMockLocation((Location)object);
                    object2.writeNoException();
                    return true;
                }
                case 14: {
                    void var8_27;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (object.readInt() != 0) {
                        LatLngBounds latLngBounds = LatLngBounds.CREATOR.createFromParcel((Parcel)object);
                    } else {
                        Object var8_28 = null;
                    }
                    n2 = object.readInt();
                    object4 = object.readInt() != 0 ? hg.CREATOR.aD((Parcel)object) : null;
                    object3 = object.readInt() != 0 ? hs.CREATOR.aI((Parcel)object) : null;
                    this.a((LatLngBounds)var8_27, n2, (hg)object4, (hs)object3, hq.a.Y(object.readStrongBinder()));
                    object2.writeNoException();
                    return true;
                }
                case 47: {
                    void var8_30;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (object.readInt() != 0) {
                        LatLngBounds latLngBounds = LatLngBounds.CREATOR.createFromParcel((Parcel)object);
                    } else {
                        Object var8_31 = null;
                    }
                    n2 = object.readInt();
                    string2 = object.readString();
                    object4 = object.readInt() != 0 ? hg.CREATOR.aD((Parcel)object) : null;
                    if (object.readInt() != 0) {
                        object3 = hs.CREATOR.aI((Parcel)object);
                    }
                    this.a((LatLngBounds)var8_30, n2, string2, (hg)object4, (hs)object3, hq.a.Y(object.readStrongBinder()));
                    object2.writeNoException();
                    return true;
                }
                case 15: {
                    void var8_34;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    object4 = object.readString();
                    Object var8_32 = var13_7;
                    if (object.readInt() != 0) {
                        hs hs2 = hs.CREATOR.aI((Parcel)object);
                    }
                    this.a((String)object4, (hs)var8_34, hq.a.Y(object.readStrongBinder()));
                    object2.writeNoException();
                    return true;
                }
                case 16: {
                    void var8_36;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (object.readInt() != 0) {
                        LatLng latLng = LatLng.CREATOR.createFromParcel((Parcel)object);
                    } else {
                        Object var8_37 = null;
                    }
                    object4 = object.readInt() != 0 ? hg.CREATOR.aD((Parcel)object) : null;
                    object3 = string2;
                    if (object.readInt() != 0) {
                        object3 = hs.CREATOR.aI((Parcel)object);
                    }
                    this.a((LatLng)var8_36, (hg)object4, (hs)object3, hq.a.Y(object.readStrongBinder()));
                    object2.writeNoException();
                    return true;
                }
                case 17: {
                    void var8_39;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (object.readInt() != 0) {
                        hg hg2 = hg.CREATOR.aD((Parcel)object);
                    } else {
                        Object var8_40 = null;
                    }
                    if (object.readInt() != 0) {
                        object4 = hs.CREATOR.aI((Parcel)object);
                    }
                    this.a((hg)var8_39, (hs)object4, hq.a.Y(object.readStrongBinder()));
                    object2.writeNoException();
                    return true;
                }
                case 42: {
                    void var8_43;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    object4 = object.readString();
                    Object var8_41 = var14_10;
                    if (object.readInt() != 0) {
                        hs hs3 = hs.CREATOR.aI((Parcel)object);
                    }
                    this.b((String)object4, (hs)var8_43, hq.a.Y(object.readStrongBinder()));
                    object2.writeNoException();
                    return true;
                }
                case 18: {
                    void var8_45;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (object.readInt() != 0) {
                        hk hk2 = hk.CREATOR.aF((Parcel)object);
                    } else {
                        Object var8_46 = null;
                    }
                    object4 = object.readInt() != 0 ? hs.CREATOR.aI((Parcel)object) : null;
                    object = object.readInt() != 0 ? (PendingIntent)PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                    this.a((hk)var8_45, (hs)object4, (PendingIntent)object);
                    object2.writeNoException();
                    return true;
                }
                case 19: {
                    void var8_48;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (object.readInt() != 0) {
                        hs hs4 = hs.CREATOR.aI((Parcel)object);
                    } else {
                        Object var8_49 = null;
                    }
                    object = object.readInt() != 0 ? (PendingIntent)PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                    this.a((hs)var8_48, (PendingIntent)object);
                    object2.writeNoException();
                    return true;
                }
                case 45: {
                    void var8_51;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    string2 = object.readString();
                    if (object.readInt() != 0) {
                        LatLngBounds latLngBounds = LatLngBounds.CREATOR.createFromParcel((Parcel)object);
                    } else {
                        Object var8_52 = null;
                    }
                    object4 = object.readInt() != 0 ? hg.CREATOR.aD((Parcel)object) : null;
                    object3 = object.readInt() != 0 ? hs.CREATOR.aI((Parcel)object) : null;
                    this.a(string2, (LatLngBounds)var8_51, (hg)object4, (hs)object3, hq.a.Y(object.readStrongBinder()));
                    object2.writeNoException();
                    return true;
                }
                case 46: {
                    void var8_54;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (object.readInt() != 0) {
                        ho ho2 = (ho)ho.CREATOR.createFromParcel((Parcel)object);
                    } else {
                        Object var8_55 = null;
                    }
                    object4 = var12_11;
                    if (object.readInt() != 0) {
                        object4 = hs.CREATOR.aI((Parcel)object);
                    }
                    this.a((ho)var8_54, (hs)object4, hq.a.Y(object.readStrongBinder()));
                    object2.writeNoException();
                    return true;
                }
                case 20: {
                    void var8_58;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    Object var8_56 = var16_12;
                    if (object.readInt() != 0) {
                        LocationRequest locationRequest = LocationRequest.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.a((LocationRequest)var8_58, a.a.U(object.readStrongBinder()), object.readString());
                    object2.writeNoException();
                    return true;
                }
                case 21: {
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    object = this.aW(object.readString());
                    object2.writeNoException();
                    if (object != null) {
                        object2.writeInt(1);
                        object.writeToParcel((Parcel)object2, 1);
                        return true;
                    }
                    object2.writeInt(0);
                    return true;
                }
                case 25: {
                    void var8_60;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    object2 = object.readInt() != 0 ? hi.CREATOR.aE((Parcel)object) : null;
                    if (object.readInt() != 0) {
                        hs hs5 = hs.CREATOR.aI((Parcel)object);
                    }
                    this.a((hi)object2, (hs)var8_60);
                    return true;
                }
                case 26: {
                    void var8_62;
                    object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (object.readInt() != 0) {
                        Location location = (Location)Location.CREATOR.createFromParcel((Parcel)object);
                    } else {
                        Object var8_63 = null;
                    }
                    this.a((Location)var8_62, object.readInt());
                    object2.writeNoException();
                    return true;
                }
                case 34: 
            }
            object.enforceInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
            object = this.aX(object.readString());
            object2.writeNoException();
            if (object != null) {
                object2.writeInt(1);
                object.writeToParcel((Parcel)object2, 1);
                return true;
            }
            object2.writeInt(0);
            return true;
        }

        private static class a
        implements ha {
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
            public void a(long l2, boolean bl2, PendingIntent pendingIntent) throws RemoteException {
                int n2 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    parcel.writeLong(l2);
                    if (!bl2) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void a(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void a(PendingIntent pendingIntent, gz gz2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    pendingIntent = gz2 != null ? gz2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)pendingIntent);
                    parcel.writeString(string2);
                    this.kn.transact(2, parcel, parcel2, 0);
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
            public void a(Location location, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (location != null) {
                        parcel.writeInt(1);
                        location.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    this.kn.transact(26, parcel, parcel2, 0);
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
            public void a(gz gz2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    gz2 = gz2 != null ? gz2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)gz2);
                    parcel.writeString(string2);
                    this.kn.transact(4, parcel, parcel2, 0);
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
            public void a(hg hg2, hs hs2, hq hq2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (hg2 != null) {
                        parcel.writeInt(1);
                        hg2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (hs2 != null) {
                        parcel.writeInt(1);
                        hs2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    hg2 = hq2 != null ? hq2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)hg2);
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
            public void a(hi hi2, hs hs2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (hi2 != null) {
                        parcel.writeInt(1);
                        hi2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (hs2 != null) {
                        parcel.writeInt(1);
                        hs2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(25, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(hk hk2, hs hs2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (hk2 != null) {
                        parcel.writeInt(1);
                        hk2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (hs2 != null) {
                        parcel.writeInt(1);
                        hs2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(18, parcel, parcel2, 0);
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
            public void a(ho ho2, hs hs2, hq hq2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (ho2 != null) {
                        parcel.writeInt(1);
                        ho2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (hs2 != null) {
                        parcel.writeInt(1);
                        hs2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    ho2 = hq2 != null ? hq2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)ho2);
                    this.kn.transact(46, parcel, parcel2, 0);
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
            public void a(hs hs2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (hs2 != null) {
                        parcel.writeInt(1);
                        hs2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(19, parcel, parcel2, 0);
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
            public void a(LocationRequest locationRequest, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequest != null) {
                        parcel.writeInt(1);
                        locationRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(LocationRequest locationRequest, com.google.android.gms.location.a a2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequest != null) {
                        parcel.writeInt(1);
                        locationRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    locationRequest = a2 != null ? a2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)locationRequest);
                    this.kn.transact(8, parcel, parcel2, 0);
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
            public void a(LocationRequest locationRequest, com.google.android.gms.location.a a2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (locationRequest != null) {
                        parcel.writeInt(1);
                        locationRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    locationRequest = a2 != null ? a2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)locationRequest);
                    parcel.writeString(string2);
                    this.kn.transact(20, parcel, parcel2, 0);
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
            public void a(com.google.android.gms.location.a a2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    a2 = a2 != null ? a2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)a2);
                    this.kn.transact(10, parcel, parcel2, 0);
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
            public void a(LatLng latLng, hg hg2, hs hs2, hq hq2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block9 : {
                    block8 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        try {
                            parcel2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                            if (latLng != null) {
                                parcel2.writeInt(1);
                                latLng.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (hg2 != null) {
                                parcel2.writeInt(1);
                                hg2.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (hs2 == null) break block8;
                            parcel2.writeInt(1);
                            hs2.writeToParcel(parcel2, 0);
                            break block9;
                        }
                        catch (Throwable var1_2) {
                            parcel.recycle();
                            parcel2.recycle();
                            throw var1_2;
                        }
                    }
                    parcel2.writeInt(0);
                }
                latLng = hq2 != null ? hq2.asBinder() : null;
                parcel2.writeStrongBinder((IBinder)latLng);
                this.kn.transact(16, parcel2, parcel, 0);
                parcel.readException();
                parcel.recycle();
                parcel2.recycle();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(LatLngBounds latLngBounds, int n2, hg hg2, hs hs2, hq hq2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block9 : {
                    block8 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        try {
                            parcel2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                            if (latLngBounds != null) {
                                parcel2.writeInt(1);
                                latLngBounds.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            parcel2.writeInt(n2);
                            if (hg2 != null) {
                                parcel2.writeInt(1);
                                hg2.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (hs2 == null) break block8;
                            parcel2.writeInt(1);
                            hs2.writeToParcel(parcel2, 0);
                            break block9;
                        }
                        catch (Throwable var1_2) {
                            parcel.recycle();
                            parcel2.recycle();
                            throw var1_2;
                        }
                    }
                    parcel2.writeInt(0);
                }
                latLngBounds = hq2 != null ? hq2.asBinder() : null;
                parcel2.writeStrongBinder((IBinder)latLngBounds);
                this.kn.transact(14, parcel2, parcel, 0);
                parcel.readException();
                parcel.recycle();
                parcel2.recycle();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(LatLngBounds latLngBounds, int n2, String string2, hg hg2, hs hs2, hq hq2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block9 : {
                    block8 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        try {
                            parcel2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                            if (latLngBounds != null) {
                                parcel2.writeInt(1);
                                latLngBounds.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            parcel2.writeInt(n2);
                            parcel2.writeString(string2);
                            if (hg2 != null) {
                                parcel2.writeInt(1);
                                hg2.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (hs2 == null) break block8;
                            parcel2.writeInt(1);
                            hs2.writeToParcel(parcel2, 0);
                            break block9;
                        }
                        catch (Throwable var1_2) {
                            parcel.recycle();
                            parcel2.recycle();
                            throw var1_2;
                        }
                    }
                    parcel2.writeInt(0);
                }
                latLngBounds = hq2 != null ? hq2.asBinder() : null;
                parcel2.writeStrongBinder((IBinder)latLngBounds);
                this.kn.transact(47, parcel2, parcel, 0);
                parcel.readException();
                parcel.recycle();
                parcel2.recycle();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(String string2, hs hs2, hq hq2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    parcel.writeString(string2);
                    if (hs2 != null) {
                        parcel.writeInt(1);
                        hs2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    string2 = hq2 != null ? hq2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)string2);
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
            public void a(String string2, LatLngBounds latLngBounds, hg hg2, hs hs2, hq hq2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block9 : {
                    block8 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        try {
                            parcel2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                            parcel2.writeString(string2);
                            if (latLngBounds != null) {
                                parcel2.writeInt(1);
                                latLngBounds.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (hg2 != null) {
                                parcel2.writeInt(1);
                                hg2.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (hs2 == null) break block8;
                            parcel2.writeInt(1);
                            hs2.writeToParcel(parcel2, 0);
                            break block9;
                        }
                        catch (Throwable var1_2) {
                            parcel.recycle();
                            parcel2.recycle();
                            throw var1_2;
                        }
                    }
                    parcel2.writeInt(0);
                }
                string2 = hq2 != null ? hq2.asBinder() : null;
                parcel2.writeStrongBinder((IBinder)string2);
                this.kn.transact(45, parcel2, parcel, 0);
                parcel.readException();
                parcel.recycle();
                parcel2.recycle();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(List<hd> iBinder, PendingIntent pendingIntent, gz gz2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    parcel.writeTypedList((List)iBinder);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    iBinder = gz2 != null ? gz2.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
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
            public void a(String[] iBinder, gz gz2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    parcel.writeStringArray((String[])iBinder);
                    iBinder = gz2 != null ? gz2.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
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
            public Location aW(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    parcel.writeString(string2);
                    this.kn.transact(21, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readInt() != 0 ? (Location)Location.CREATOR.createFromParcel(parcel2) : null;
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
            public b aX(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    parcel.writeString((String)object);
                    this.kn.transact(34, parcel, parcel2, 0);
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? b.CREATOR.aB(parcel2) : null;
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
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
            public void b(String string2, hs hs2, hq hq2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    parcel.writeString(string2);
                    if (hs2 != null) {
                        parcel.writeInt(1);
                        hs2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    string2 = hq2 != null ? hq2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)string2);
                    this.kn.transact(42, parcel, parcel2, 0);
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
            public Location hP() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    this.kn.transact(7, parcel, parcel2, 0);
                    parcel2.readException();
                    Location location = parcel2.readInt() != 0 ? (Location)Location.CREATOR.createFromParcel(parcel2) : null;
                    return location;
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
            public void removeActivityUpdates(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void setMockLocation(Location location) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (location != null) {
                        parcel.writeInt(1);
                        location.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(13, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setMockMode(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.location.internal.IGoogleLocationManagerService");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(12, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }
        }

    }

}

