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
package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.ef;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface eg
extends IInterface {
    public void a(Map var1, long var2, String var4, List<ef> var5) throws RemoteException;

    public void bR() throws RemoteException;

    public String getVersion() throws RemoteException;

    public static abstract class com.google.android.gms.internal.eg$a
    extends Binder
    implements eg {
        public static eg t(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.analytics.internal.IAnalyticsService");
            if (iInterface != null && iInterface instanceof eg) {
                return (eg)iInterface;
            }
            return new a(iBinder);
        }

        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.analytics.internal.IAnalyticsService");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.analytics.internal.IAnalyticsService");
                    this.a((Map)object.readHashMap(this.getClass().getClassLoader()), object.readLong(), object.readString(), (List)object.createTypedArrayList(ef.CREATOR));
                    parcel.writeNoException();
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.analytics.internal.IAnalyticsService");
                    this.bR();
                    parcel.writeNoException();
                    return true;
                }
                case 3: 
            }
            object.enforceInterface("com.google.android.gms.analytics.internal.IAnalyticsService");
            object = this.getVersion();
            parcel.writeNoException();
            parcel.writeString((String)object);
            return true;
        }

        private static class a
        implements eg {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            @Override
            public void a(Map map, long l2, String string2, List<ef> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.analytics.internal.IAnalyticsService");
                    parcel.writeMap(map);
                    parcel.writeLong(l2);
                    parcel.writeString(string2);
                    parcel.writeTypedList(list);
                    this.kn.transact(1, parcel, parcel2, 0);
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
            public void bR() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.analytics.internal.IAnalyticsService");
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getVersion() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.analytics.internal.IAnalyticsService");
                    this.kn.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    String string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

