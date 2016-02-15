/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.plus.internal;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.fk;
import com.google.android.gms.internal.gg;
import com.google.android.gms.internal.gh;
import com.google.android.gms.plus.internal.b;
import java.util.ArrayList;
import java.util.List;

public interface d
extends IInterface {
    public fk a(b var1, int var2, int var3, int var4, String var5) throws RemoteException;

    public void a(gg var1) throws RemoteException;

    public void a(b var1) throws RemoteException;

    public void a(b var1, int var2, String var3, Uri var4, String var5, String var6) throws RemoteException;

    public void a(b var1, Uri var2, Bundle var3) throws RemoteException;

    public void a(b var1, gg var2) throws RemoteException;

    public void a(b var1, String var2) throws RemoteException;

    public void a(b var1, String var2, String var3) throws RemoteException;

    public void a(b var1, List<String> var2) throws RemoteException;

    public void b(b var1) throws RemoteException;

    public void b(b var1, String var2) throws RemoteException;

    public void c(b var1, String var2) throws RemoteException;

    public void clearDefaultAccount() throws RemoteException;

    public void d(b var1, String var2) throws RemoteException;

    public void e(b var1, String var2) throws RemoteException;

    public String getAccountName() throws RemoteException;

    public String iK() throws RemoteException;

    public boolean iL() throws RemoteException;

    public String iM() throws RemoteException;

    public void removeMoment(String var1) throws RemoteException;

    public static abstract class com.google.android.gms.plus.internal.d$a
    extends Binder
    implements d {
        public static d aQ(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.plus.internal.IPlusService");
            if (iInterface != null && iInterface instanceof d) {
                return (d)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            Uri uri = null;
            Object object2 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.plus.internal.IPlusService");
                    return true;
                }
                case 1: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    this.a(b.a.aO(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    this.a(b.a.aO(object.readStrongBinder()), object.readString(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    this.b(b.a.aO(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    object = object.readInt() != 0 ? gg.CREATOR.x((Parcel)object) : null;
                    this.a((gg)object);
                    parcel.writeNoException();
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    object = this.getAccountName();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    this.clearDefaultAccount();
                    parcel.writeNoException();
                    return true;
                }
                case 8: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    this.a(b.a.aO(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 9: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    b b2 = b.a.aO(object.readStrongBinder());
                    object2 = object.readInt() != 0 ? (Uri)Uri.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.a(b2, (Uri)object2, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 14: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    b b3 = b.a.aO(object.readStrongBinder());
                    n2 = object.readInt();
                    String string2 = object.readString();
                    object2 = object.readInt() != 0 ? (Uri)Uri.CREATOR.createFromParcel((Parcel)object) : null;
                    this.a(b3, n2, string2, (Uri)object2, object.readString(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 16: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    fk fk2 = this.a(b.a.aO(object.readStrongBinder()), object.readInt(), object.readInt(), object.readInt(), object.readString());
                    parcel.writeNoException();
                    object = object2;
                    if (fk2 != null) {
                        object = fk2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 17: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    this.removeMoment(object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 18: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    this.c(b.a.aO(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 19: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    this.b(b.a.aO(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 34: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    this.a(b.a.aO(object.readStrongBinder()), (List)object.createStringArrayList());
                    parcel.writeNoException();
                    return true;
                }
                case 40: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    this.d(b.a.aO(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 41: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    object = this.iK();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 42: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    boolean bl2 = this.iL();
                    parcel.writeNoException();
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    return true;
                }
                case 43: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    object = this.iM();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 44: {
                    object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    this.e(b.a.aO(object.readStrongBinder()), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 45: 
            }
            object.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
            b b4 = b.a.aO(object.readStrongBinder());
            object2 = uri;
            if (object.readInt() != 0) {
                object2 = gg.CREATOR.x((Parcel)object);
            }
            this.a(b4, (gg)object2);
            parcel.writeNoException();
            return true;
        }

        private static class a
        implements d {
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
            public fk a(b object, int n2, int n3, int n4, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_3;
                    void var2_7;
                    void var4_9;
                    void var5_10;
                    void var3_8;
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    if (object != null) {
                        IBinder iBinder = object.asBinder();
                    } else {
                        Object var1_5 = null;
                    }
                    parcel.writeStrongBinder((IBinder)var1_3);
                    parcel.writeInt((int)var2_7);
                    parcel.writeInt((int)var3_8);
                    parcel.writeInt((int)var4_9);
                    parcel.writeString((String)var5_10);
                    this.kn.transact(16, parcel, parcel2, 0);
                    parcel2.readException();
                    fk fk2 = fk.a.A(parcel2.readStrongBinder());
                    return fk2;
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
            public void a(gg gg2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    if (gg2 != null) {
                        parcel.writeInt(1);
                        gg2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void a(b b2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    b2 = b2 != null ? b2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)b2);
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
            public void a(b b2, int n2, String string2, Uri uri, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    b2 = b2 != null ? b2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)b2);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string3);
                    parcel.writeString(string4);
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
            public void a(b b2, Uri uri, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    b2 = b2 != null ? b2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)b2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
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
            public void a(b b2, gg gg2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    b2 = b2 != null ? b2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)b2);
                    if (gg2 != null) {
                        parcel.writeInt(1);
                        gg2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(45, parcel, parcel2, 0);
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
            public void a(b b2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    b2 = b2 != null ? b2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)b2);
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
            public void a(b b2, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    b2 = b2 != null ? b2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)b2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
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
            public void a(b b2, List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    b2 = b2 != null ? b2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)b2);
                    parcel.writeStringList(list);
                    this.kn.transact(34, parcel, parcel2, 0);
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
            public void b(b b2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    b2 = b2 != null ? b2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)b2);
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
            public void b(b b2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    b2 = b2 != null ? b2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)b2);
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
            public void c(b b2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    b2 = b2 != null ? b2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)b2);
                    parcel.writeString(string2);
                    this.kn.transact(18, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void clearDefaultAccount() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
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
            public void d(b b2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    b2 = b2 != null ? b2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)b2);
                    parcel.writeString(string2);
                    this.kn.transact(40, parcel, parcel2, 0);
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
            public void e(b b2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    b2 = b2 != null ? b2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)b2);
                    parcel.writeString(string2);
                    this.kn.transact(44, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getAccountName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.kn.transact(5, parcel, parcel2, 0);
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
            public String iK() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.kn.transact(41, parcel, parcel2, 0);
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
            public boolean iL() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                        this.kn.transact(42, parcel, parcel2, 0);
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
            public String iM() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.kn.transact(43, parcel, parcel2, 0);
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
            public void removeMoment(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    parcel.writeString(string2);
                    this.kn.transact(17, parcel, parcel2, 0);
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

