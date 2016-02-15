/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.d;

public interface c
extends IInterface {
    public void b(d var1) throws RemoteException;

    public void c(d var1) throws RemoteException;

    public d fX() throws RemoteException;

    public c fY() throws RemoteException;

    public d fZ() throws RemoteException;

    public c ga() throws RemoteException;

    public Bundle getArguments() throws RemoteException;

    public int getId() throws RemoteException;

    public boolean getRetainInstance() throws RemoteException;

    public String getTag() throws RemoteException;

    public int getTargetRequestCode() throws RemoteException;

    public boolean getUserVisibleHint() throws RemoteException;

    public d getView() throws RemoteException;

    public boolean isAdded() throws RemoteException;

    public boolean isDetached() throws RemoteException;

    public boolean isHidden() throws RemoteException;

    public boolean isInLayout() throws RemoteException;

    public boolean isRemoving() throws RemoteException;

    public boolean isResumed() throws RemoteException;

    public boolean isVisible() throws RemoteException;

    public void setHasOptionsMenu(boolean var1) throws RemoteException;

    public void setMenuVisibility(boolean var1) throws RemoteException;

    public void setRetainInstance(boolean var1) throws RemoteException;

    public void setUserVisibleHint(boolean var1) throws RemoteException;

    public void startActivity(Intent var1) throws RemoteException;

    public void startActivityForResult(Intent var1, int var2) throws RemoteException;

    public static abstract class com.google.android.gms.dynamic.c$a
    extends Binder
    implements c {
        public com.google.android.gms.dynamic.c$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.dynamic.IFragmentWrapper");
        }

        public static c J(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IFragmentWrapper");
            if (iInterface != null && iInterface instanceof c) {
                return (c)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n2, Parcel object, Parcel parcel, int n3) throws RemoteException {
            d d2 = null;
            Object var19_6 = null;
            Object var20_7 = null;
            Object var21_8 = null;
            Object var22_9 = null;
            Object var23_10 = null;
            Object var17_11 = null;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            int n9 = 0;
            int n10 = 0;
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = false;
            boolean bl5 = false;
            int n11 = 0;
            switch (n2) {
                default: {
                    return super.onTransact(n2, (Parcel)object, parcel, n3);
                }
                case 1598968902: {
                    parcel.writeString("com.google.android.gms.dynamic.IFragmentWrapper");
                    return true;
                }
                case 2: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    d2 = this.fX();
                    parcel.writeNoException();
                    object = var17_11;
                    if (d2 != null) {
                        object = d2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 3: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    object = this.getArguments();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 4: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    n2 = this.getId();
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 5: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    c c2 = this.fY();
                    parcel.writeNoException();
                    object = d2;
                    if (c2 != null) {
                        object = c2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 6: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    d d3 = this.fZ();
                    parcel.writeNoException();
                    object = var19_6;
                    if (d3 != null) {
                        object = d3.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 7: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.getRetainInstance();
                    parcel.writeNoException();
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    return true;
                }
                case 8: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    object = this.getTag();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 9: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    c c3 = this.ga();
                    parcel.writeNoException();
                    object = var20_7;
                    if (c3 != null) {
                        object = c3.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 10: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    n2 = this.getTargetRequestCode();
                    parcel.writeNoException();
                    parcel.writeInt(n2);
                    return true;
                }
                case 11: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.getUserVisibleHint();
                    parcel.writeNoException();
                    n2 = n11;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 12: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    d d4 = this.getView();
                    parcel.writeNoException();
                    object = var21_8;
                    if (d4 != null) {
                        object = d4.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                case 13: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isAdded();
                    parcel.writeNoException();
                    n2 = n4;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 14: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isDetached();
                    parcel.writeNoException();
                    n2 = n5;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 15: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isHidden();
                    parcel.writeNoException();
                    n2 = n6;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 16: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isInLayout();
                    parcel.writeNoException();
                    n2 = n7;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 17: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isRemoving();
                    parcel.writeNoException();
                    n2 = n8;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 18: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isResumed();
                    parcel.writeNoException();
                    n2 = n9;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 19: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = this.isVisible();
                    parcel.writeNoException();
                    n2 = n10;
                    if (bl2) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    return true;
                }
                case 20: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.b(d.a.K(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 21: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.setHasOptionsMenu(bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 22: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = bl3;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.setMenuVisibility(bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 23: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = bl4;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.setRetainInstance(bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 24: {
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    bl2 = bl5;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.setUserVisibleHint(bl2);
                    parcel.writeNoException();
                    return true;
                }
                case 25: {
                    void var17_18;
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    Object var17_16 = var22_9;
                    if (object.readInt() != 0) {
                        Intent intent = (Intent)Intent.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.startActivity((Intent)var17_18);
                    parcel.writeNoException();
                    return true;
                }
                case 26: {
                    void var17_21;
                    object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    Object var17_19 = var23_10;
                    if (object.readInt() != 0) {
                        Intent intent = (Intent)Intent.CREATOR.createFromParcel((Parcel)object);
                    }
                    this.startActivityForResult((Intent)var17_21, object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 27: 
            }
            object.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
            this.c(d.a.K(object.readStrongBinder()));
            parcel.writeNoException();
            return true;
        }

        private static class a
        implements c {
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
            public void b(d d2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
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
            public void c(d d2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    d2 = d2 != null ? d2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)d2);
                    this.kn.transact(27, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public d fX() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.kn.transact(2, parcel, parcel2, 0);
                    parcel2.readException();
                    d d2 = d.a.K(parcel2.readStrongBinder());
                    return d2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public c fY() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.kn.transact(5, parcel, parcel2, 0);
                    parcel2.readException();
                    c c2 = com.google.android.gms.dynamic.c$a.J(parcel2.readStrongBinder());
                    return c2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public d fZ() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.kn.transact(6, parcel, parcel2, 0);
                    parcel2.readException();
                    d d2 = d.a.K(parcel2.readStrongBinder());
                    return d2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public c ga() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.kn.transact(9, parcel, parcel2, 0);
                    parcel2.readException();
                    c c2 = com.google.android.gms.dynamic.c$a.J(parcel2.readStrongBinder());
                    return c2;
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
            public Bundle getArguments() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.kn.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    Bundle bundle = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
                    return bundle;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.kn.transact(4, parcel, parcel2, 0);
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
            public boolean getRetainInstance() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.kn.transact(7, parcel, parcel2, 0);
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
            public String getTag() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.kn.transact(8, parcel, parcel2, 0);
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
            public int getTargetRequestCode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.kn.transact(10, parcel, parcel2, 0);
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
            public boolean getUserVisibleHint() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.kn.transact(11, parcel, parcel2, 0);
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
            public d getView() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.kn.transact(12, parcel, parcel2, 0);
                    parcel2.readException();
                    d d2 = d.a.K(parcel2.readStrongBinder());
                    return d2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isAdded() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.kn.transact(13, parcel, parcel2, 0);
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
            public boolean isDetached() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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
            public boolean isHidden() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.kn.transact(15, parcel, parcel2, 0);
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
            public boolean isInLayout() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            @Override
            public boolean isRemoving() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.kn.transact(17, parcel, parcel2, 0);
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
            public boolean isResumed() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.kn.transact(18, parcel, parcel2, 0);
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
                        parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                        this.kn.transact(19, parcel, parcel2, 0);
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
            public void setHasOptionsMenu(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(21, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setMenuVisibility(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(22, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setRetainInstance(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(23, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setUserVisibleHint(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(24, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void startActivity(Intent intent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
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
            public void startActivityForResult(Intent intent, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
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
        }

    }

}

