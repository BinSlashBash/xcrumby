/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.RemoteException
 */
package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUiSettingsDelegate
extends IInterface {
    public boolean isCompassEnabled() throws RemoteException;

    public boolean isIndoorLevelPickerEnabled() throws RemoteException;

    public boolean isMyLocationButtonEnabled() throws RemoteException;

    public boolean isRotateGesturesEnabled() throws RemoteException;

    public boolean isScrollGesturesEnabled() throws RemoteException;

    public boolean isTiltGesturesEnabled() throws RemoteException;

    public boolean isZoomControlsEnabled() throws RemoteException;

    public boolean isZoomGesturesEnabled() throws RemoteException;

    public void setAllGesturesEnabled(boolean var1) throws RemoteException;

    public void setCompassEnabled(boolean var1) throws RemoteException;

    public void setIndoorLevelPickerEnabled(boolean var1) throws RemoteException;

    public void setMyLocationButtonEnabled(boolean var1) throws RemoteException;

    public void setRotateGesturesEnabled(boolean var1) throws RemoteException;

    public void setScrollGesturesEnabled(boolean var1) throws RemoteException;

    public void setTiltGesturesEnabled(boolean var1) throws RemoteException;

    public void setZoomControlsEnabled(boolean var1) throws RemoteException;

    public void setZoomGesturesEnabled(boolean var1) throws RemoteException;

    public static abstract class com.google.android.gms.maps.internal.IUiSettingsDelegate$a
    extends Binder
    implements IUiSettingsDelegate {
        public static IUiSettingsDelegate aA(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
            if (iInterface != null && iInterface instanceof IUiSettingsDelegate) {
                return (IUiSettingsDelegate)iInterface;
            }
            return new a(iBinder);
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            boolean bl2 = false;
            boolean bl3 = false;
            boolean bl4 = false;
            boolean bl5 = false;
            boolean bl6 = false;
            boolean bl7 = false;
            boolean bl8 = false;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            int n9 = 0;
            int n10 = 0;
            boolean bl9 = false;
            int n11 = 0;
            boolean bl10 = false;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    if (parcel.readInt() != 0) {
                        bl10 = true;
                    }
                    this.setZoomControlsEnabled(bl10);
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = bl2;
                    if (parcel.readInt() != 0) {
                        bl10 = true;
                    }
                    this.setCompassEnabled(bl10);
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = bl3;
                    if (parcel.readInt() != 0) {
                        bl10 = true;
                    }
                    this.setMyLocationButtonEnabled(bl10);
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = bl4;
                    if (parcel.readInt() != 0) {
                        bl10 = true;
                    }
                    this.setScrollGesturesEnabled(bl10);
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = bl5;
                    if (parcel.readInt() != 0) {
                        bl10 = true;
                    }
                    this.setZoomGesturesEnabled(bl10);
                    parcel2.writeNoException();
                    return true;
                }
                case 6: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = bl6;
                    if (parcel.readInt() != 0) {
                        bl10 = true;
                    }
                    this.setTiltGesturesEnabled(bl10);
                    parcel2.writeNoException();
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = bl7;
                    if (parcel.readInt() != 0) {
                        bl10 = true;
                    }
                    this.setRotateGesturesEnabled(bl10);
                    parcel2.writeNoException();
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = bl8;
                    if (parcel.readInt() != 0) {
                        bl10 = true;
                    }
                    this.setAllGesturesEnabled(bl10);
                    parcel2.writeNoException();
                    return true;
                }
                case 9: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = this.isZoomControlsEnabled();
                    parcel2.writeNoException();
                    n2 = n4;
                    if (bl10) {
                        n2 = 1;
                    }
                    parcel2.writeInt(n2);
                    return true;
                }
                case 10: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = this.isCompassEnabled();
                    parcel2.writeNoException();
                    n2 = n5;
                    if (bl10) {
                        n2 = 1;
                    }
                    parcel2.writeInt(n2);
                    return true;
                }
                case 11: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = this.isMyLocationButtonEnabled();
                    parcel2.writeNoException();
                    n2 = n6;
                    if (bl10) {
                        n2 = 1;
                    }
                    parcel2.writeInt(n2);
                    return true;
                }
                case 12: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = this.isScrollGesturesEnabled();
                    parcel2.writeNoException();
                    n2 = n7;
                    if (bl10) {
                        n2 = 1;
                    }
                    parcel2.writeInt(n2);
                    return true;
                }
                case 13: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = this.isZoomGesturesEnabled();
                    parcel2.writeNoException();
                    n2 = n8;
                    if (bl10) {
                        n2 = 1;
                    }
                    parcel2.writeInt(n2);
                    return true;
                }
                case 14: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = this.isTiltGesturesEnabled();
                    parcel2.writeNoException();
                    n2 = n9;
                    if (bl10) {
                        n2 = 1;
                    }
                    parcel2.writeInt(n2);
                    return true;
                }
                case 15: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = this.isRotateGesturesEnabled();
                    parcel2.writeNoException();
                    n2 = n10;
                    if (bl10) {
                        n2 = 1;
                    }
                    parcel2.writeInt(n2);
                    return true;
                }
                case 16: {
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    bl10 = bl9;
                    if (parcel.readInt() != 0) {
                        bl10 = true;
                    }
                    this.setIndoorLevelPickerEnabled(bl10);
                    parcel2.writeNoException();
                    return true;
                }
                case 17: 
            }
            parcel.enforceInterface("com.google.android.gms.maps.internal.IUiSettingsDelegate");
            bl10 = this.isIndoorLevelPickerEnabled();
            parcel2.writeNoException();
            n2 = n11;
            if (bl10) {
                n2 = 1;
            }
            parcel2.writeInt(n2);
            return true;
        }

        private static class a
        implements IUiSettingsDelegate {
            private IBinder kn;

            a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public IBinder asBinder() {
                return this.kn;
            }

            @Override
            public boolean isCompassEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        this.kn.transact(10, parcel, parcel2, 0);
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
            public boolean isIndoorLevelPickerEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
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
            public boolean isMyLocationButtonEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
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
            public boolean isRotateGesturesEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
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
            public boolean isScrollGesturesEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
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
            public boolean isTiltGesturesEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
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
            public boolean isZoomControlsEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                        this.kn.transact(9, parcel, parcel2, 0);
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
            public boolean isZoomGesturesEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                block2 : {
                    bl2 = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
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
            public void setAllGesturesEnabled(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(8, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setCompassEnabled(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(2, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setIndoorLevelPickerEnabled(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(16, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setMyLocationButtonEnabled(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(3, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setRotateGesturesEnabled(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(7, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setScrollGesturesEnabled(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(4, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setTiltGesturesEnabled(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(6, parcel2, parcel, 0);
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
            public void setZoomControlsEnabled(boolean bl2) throws RemoteException {
                int n2 = 1;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    if (!bl2) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
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
            public void setZoomGesturesEnabled(boolean bl2) throws RemoteException {
                Parcel parcel;
                int n2;
                Parcel parcel2;
                block4 : {
                    n2 = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("com.google.android.gms.maps.internal.IUiSettingsDelegate");
                    if (!bl2) break block4;
                    n2 = 1;
                }
                try {
                    parcel2.writeInt(n2);
                    this.kn.transact(5, parcel2, parcel, 0);
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

