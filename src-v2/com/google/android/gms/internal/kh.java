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
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.DataHolderCreator;
import com.google.android.gms.internal.ki;
import com.google.android.gms.internal.kk;

public interface kh
extends IInterface {
    public void M(DataHolder var1) throws RemoteException;

    public void a(ki var1) throws RemoteException;

    public void a(kk var1) throws RemoteException;

    public void b(kk var1) throws RemoteException;

    public static abstract class a
    extends Binder
    implements kh {
        public a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.wearable.internal.IWearableListener");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel object, int n3) throws RemoteException {
            Object var6_5 = null;
            Object var7_6 = null;
            Object var8_7 = null;
            Object var5_8 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, (Parcel)object, n3);
                }
                case 1598968902: {
                    object.writeString("com.google.android.gms.wearable.internal.IWearableListener");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    object = var5_8;
                    if (parcel.readInt() != 0) {
                        object = DataHolder.CREATOR.createFromParcel(parcel);
                    }
                    this.M((DataHolder)object);
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    object = var6_5;
                    if (parcel.readInt() != 0) {
                        object = (ki)ki.CREATOR.createFromParcel(parcel);
                    }
                    this.a((ki)object);
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
                    object = var7_6;
                    if (parcel.readInt() != 0) {
                        object = (kk)kk.CREATOR.createFromParcel(parcel);
                    }
                    this.a((kk)object);
                    return true;
                }
                case 4: 
            }
            parcel.enforceInterface("com.google.android.gms.wearable.internal.IWearableListener");
            object = var8_7;
            if (parcel.readInt() != 0) {
                object = (kk)kk.CREATOR.createFromParcel(parcel);
            }
            this.b((kk)object);
            return true;
        }
    }

}

