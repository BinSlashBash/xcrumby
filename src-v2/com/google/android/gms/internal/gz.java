/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
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
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface gz
extends IInterface {
    public void onAddGeofencesResult(int var1, String[] var2) throws RemoteException;

    public void onRemoveGeofencesByPendingIntentResult(int var1, PendingIntent var2) throws RemoteException;

    public void onRemoveGeofencesByRequestIdsResult(int var1, String[] var2) throws RemoteException;

    public static abstract class com.google.android.gms.internal.gz$a
    extends Binder
    implements gz {
        public com.google.android.gms.internal.gz$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.location.internal.IGeofencerCallbacks");
        }

        public static gz V(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IGeofencerCallbacks");
            if (iInterface != null && iInterface instanceof gz) {
                return (gz)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    this.onAddGeofencesResult(parcel.readInt(), parcel.createStringArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    this.onRemoveGeofencesByRequestIdsResult(parcel.readInt(), parcel.createStringArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 3: 
            }
            parcel.enforceInterface("com.google.android.gms.location.internal.IGeofencerCallbacks");
            n2 = parcel.readInt();
            parcel = parcel.readInt() != 0 ? (PendingIntent)PendingIntent.CREATOR.createFromParcel(parcel) : null;
            this.onRemoveGeofencesByPendingIntentResult(n2, (PendingIntent)parcel);
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements gz {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public void onAddGeofencesResult(int n2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    parcel.writeInt(n2);
                    parcel.writeStringArray(arrstring);
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
            public void onRemoveGeofencesByPendingIntentResult(int n2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    parcel.writeInt(n2);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
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

            @Override
            public void onRemoveGeofencesByRequestIdsResult(int n2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.location.internal.IGeofencerCallbacks");
                    parcel.writeInt(n2);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(2, parcel, parcel2, 0);
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

