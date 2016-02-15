/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface s
extends IInterface {
    public Bundle a(String var1, Bundle var2) throws RemoteException;

    public Bundle a(String var1, String var2, Bundle var3) throws RemoteException;

    public static abstract class com.google.android.gms.internal.s$a
    extends Binder
    implements s {
        public static s a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.auth.IAuthManagerService");
            if (iInterface != null && iInterface instanceof s) {
                return (s)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            String string2 = null;
            String string3 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.auth.IAuthManagerService");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.auth.IAuthManagerService");
                    string2 = parcel.readString();
                    String string4 = parcel.readString();
                    if (parcel.readInt() != 0) {
                        string3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    parcel = this.a(string2, string4, (Bundle)string3);
                    parcel2.writeNoException();
                    if (parcel != null) {
                        parcel2.writeInt(1);
                        parcel.writeToParcel(parcel2, 1);
                        do {
                            return true;
                            break;
                        } while (true);
                    }
                    parcel2.writeInt(0);
                    return true;
                }
                case 2: 
            }
            parcel.enforceInterface("com.google.android.auth.IAuthManagerService");
            String string5 = parcel.readString();
            string3 = string2;
            if (parcel.readInt() != 0) {
                string3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
            }
            parcel = this.a(string5, (Bundle)string3);
            parcel2.writeNoException();
            if (parcel != null) {
                parcel2.writeInt(1);
                parcel.writeToParcel(parcel2, 1);
                do {
                    return true;
                    break;
                } while (true);
            }
            parcel2.writeInt(0);
            return true;
        }

        private static class a
        implements s {
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
            public Bundle a(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
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
            public Bundle a(String string2, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    string2 = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }
        }

    }

}

