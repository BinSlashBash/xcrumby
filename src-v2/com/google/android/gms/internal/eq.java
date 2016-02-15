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
import com.google.android.gms.cast.ApplicationMetadata;

public interface eq
extends IInterface {
    public void A(int var1) throws RemoteException;

    public void B(int var1) throws RemoteException;

    public void C(int var1) throws RemoteException;

    public void a(ApplicationMetadata var1, String var2, String var3, boolean var4) throws RemoteException;

    public void a(String var1, long var2) throws RemoteException;

    public void a(String var1, long var2, int var4) throws RemoteException;

    public void b(String var1, double var2, boolean var4) throws RemoteException;

    public void b(String var1, byte[] var2) throws RemoteException;

    public void d(String var1, String var2) throws RemoteException;

    public void onApplicationDisconnected(int var1) throws RemoteException;

    public void z(int var1) throws RemoteException;

    public static abstract class a
    extends Binder
    implements eq {
        public a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.cast.internal.ICastDeviceControllerListener");
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel object, int n3) throws RemoteException {
            boolean bl2 = false;
            boolean bl3 = false;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, (Parcel)object, n3);
                }
                case 1598968902: {
                    object.writeString("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    this.z(parcel.readInt());
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    object = parcel.readInt() != 0 ? (ApplicationMetadata)ApplicationMetadata.CREATOR.createFromParcel(parcel) : null;
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    if (parcel.readInt() != 0) {
                        bl3 = true;
                    }
                    this.a((ApplicationMetadata)object, string2, string3, bl3);
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    this.A(parcel.readInt());
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    object = parcel.readString();
                    double d2 = parcel.readDouble();
                    bl3 = bl2;
                    if (parcel.readInt() != 0) {
                        bl3 = true;
                    }
                    this.b((String)object, d2, bl3);
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    this.d(parcel.readString(), parcel.readString());
                    return true;
                }
                case 6: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    this.b(parcel.readString(), parcel.createByteArray());
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    this.C(parcel.readInt());
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    this.B(parcel.readInt());
                    return true;
                }
                case 9: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    this.onApplicationDisconnected(parcel.readInt());
                    return true;
                }
                case 10: {
                    parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
                    this.a(parcel.readString(), parcel.readLong(), parcel.readInt());
                    return true;
                }
                case 11: 
            }
            parcel.enforceInterface("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
            this.a(parcel.readString(), parcel.readLong());
            return true;
        }
    }

}

