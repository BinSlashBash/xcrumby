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
import com.google.android.gms.internal.fl;

public interface fm
extends IInterface {
    public void a(fl var1, int var2) throws RemoteException;

    public void a(fl var1, int var2, String var3) throws RemoteException;

    public void a(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void a(fl var1, int var2, String var3, IBinder var4, Bundle var5) throws RemoteException;

    public void a(fl var1, int var2, String var3, String var4, String[] var5) throws RemoteException;

    public void a(fl var1, int var2, String var3, String var4, String[] var5, String var6, Bundle var7) throws RemoteException;

    public void a(fl var1, int var2, String var3, String var4, String[] var5, String var6, IBinder var7, String var8, Bundle var9) throws RemoteException;

    public void a(fl var1, int var2, String var3, String[] var4, String var5, Bundle var6) throws RemoteException;

    public void b(fl var1, int var2, String var3) throws RemoteException;

    public void b(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void b(fl var1, int var2, String var3, String var4, String[] var5) throws RemoteException;

    public void c(fl var1, int var2, String var3) throws RemoteException;

    public void c(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void d(fl var1, int var2, String var3) throws RemoteException;

    public void d(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void e(fl var1, int var2, String var3) throws RemoteException;

    public void e(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void f(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void g(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void h(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void i(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void j(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void k(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void l(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void m(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void n(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void o(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public void p(fl var1, int var2, String var3, Bundle var4) throws RemoteException;

    public static abstract class com.google.android.gms.internal.fm$a
    extends Binder
    implements fm {
        public static fm C(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
            if (iInterface != null && iInterface instanceof fm) {
                return (fm)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            Object object = null;
            String[] arrstring = null;
            Object object2 = null;
            String string2 = null;
            IBinder iBinder = null;
            String string3 = null;
            Object var12_11 = null;
            Object var13_12 = null;
            Object var14_13 = null;
            Object var15_14 = null;
            Object var16_15 = null;
            Object var17_16 = null;
            Object var18_17 = null;
            Object var19_18 = null;
            Object var20_19 = null;
            Object object3 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.common.internal.IGmsServiceBroker");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object3 = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    object = parcel.readString();
                    arrstring = parcel.readString();
                    object2 = parcel.createStringArray();
                    string2 = parcel.readString();
                    parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a((fl)object3, n2, (String)object, (String)arrstring, (String[])object2, string2, (Bundle)parcel);
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.a((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    this.a(fl.a.B(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    this.a(fl.a.B(parcel.readStrongBinder()), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    arrstring = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    object2 = parcel.readString();
                    object3 = object;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.b((fl)arrstring, n2, (String)object2, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 6: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    object2 = parcel.readString();
                    object3 = arrstring;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.c((fl)object, n2, (String)object2, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = object2;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.d((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = string2;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.e((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 9: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object3 = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    object = parcel.readString();
                    arrstring = parcel.readString();
                    object2 = parcel.createStringArray();
                    string2 = parcel.readString();
                    iBinder = parcel.readStrongBinder();
                    string3 = parcel.readString();
                    parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a((fl)object3, n2, (String)object, (String)arrstring, (String[])object2, string2, iBinder, string3, (Bundle)parcel);
                    parcel2.writeNoException();
                    return true;
                }
                case 10: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    this.a(fl.a.B(parcel.readStrongBinder()), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.createStringArray());
                    parcel2.writeNoException();
                    return true;
                }
                case 11: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = iBinder;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.f((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 12: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = string3;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.g((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 13: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = var12_11;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.h((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 14: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = var13_12;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.i((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 15: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = var14_13;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.j((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 16: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = var15_14;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.k((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 17: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = var16_15;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.l((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 18: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = var17_16;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.m((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 19: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object3 = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    object = parcel.readString();
                    arrstring = parcel.readStrongBinder();
                    parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a((fl)object3, n2, (String)object, (IBinder)arrstring, (Bundle)parcel);
                    parcel2.writeNoException();
                    return true;
                }
                case 20: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object3 = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    object = parcel.readString();
                    arrstring = parcel.createStringArray();
                    object2 = parcel.readString();
                    parcel = parcel.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel) : null;
                    this.a((fl)object3, n2, (String)object, arrstring, (String)object2, (Bundle)parcel);
                    parcel2.writeNoException();
                    return true;
                }
                case 21: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    this.b(fl.a.B(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 22: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    this.c(fl.a.B(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 23: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = var18_17;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.n((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 24: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    this.d(fl.a.B(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 25: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = var19_18;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.o((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 26: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    this.e(fl.a.B(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                }
                case 27: {
                    parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    object = fl.a.B(parcel.readStrongBinder());
                    n2 = parcel.readInt();
                    arrstring = parcel.readString();
                    object3 = var20_19;
                    if (parcel.readInt() != 0) {
                        object3 = (Bundle)Bundle.CREATOR.createFromParcel(parcel);
                    }
                    this.p((fl)object, n2, (String)arrstring, (Bundle)object3);
                    parcel2.writeNoException();
                    return true;
                }
                case 28: 
            }
            parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
            this.b(fl.a.B(parcel.readStrongBinder()), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.createStringArray());
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements fm {
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
            public void a(fl fl2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
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
            public void a(fl fl2, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
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
            public void a(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void a(fl fl2, int n2, String string2, IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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
            public void a(fl fl2, int n2, String string2, String string3, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeStringArray(arrstring);
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
            public void a(fl fl2, int n2, String string2, String string3, String[] arrstring, String string4, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeStringArray(arrstring);
                    parcel.writeString(string4);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void a(fl fl2, int n2, String string2, String string3, String[] arrstring, String string4, IBinder iBinder, String string5, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeStringArray(arrstring);
                    parcel.writeString(string4);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string5);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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
            public void a(fl fl2, int n2, String string2, String[] arrstring, String string3, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    parcel.writeString(string3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(20, parcel, parcel2, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void b(fl fl2, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    this.kn.transact(21, parcel, parcel2, 0);
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
            public void b(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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
            public void b(fl fl2, int n2, String string2, String string3, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeStringArray(arrstring);
                    this.kn.transact(28, parcel, parcel2, 0);
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
            public void c(fl fl2, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    this.kn.transact(22, parcel, parcel2, 0);
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
            public void c(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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
            public void d(fl fl2, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    this.kn.transact(24, parcel, parcel2, 0);
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
            public void d(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(7, parcel, parcel2, 0);
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
            public void e(fl fl2, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
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
            public void e(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void f(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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
            public void g(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void h(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void i(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(14, parcel, parcel2, 0);
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
            public void j(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void k(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void l(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void m(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
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
            public void n(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(23, parcel, parcel2, 0);
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
            public void o(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(25, parcel, parcel2, 0);
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
            public void p(fl fl2, int n2, String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    fl2 = fl2 != null ? fl2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)fl2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(27, parcel, parcel2, 0);
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

