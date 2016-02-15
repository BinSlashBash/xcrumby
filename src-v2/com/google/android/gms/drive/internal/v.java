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
package com.google.android.gms.drive.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.StatusCreator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.internal.OnContentsResponse;
import com.google.android.gms.drive.internal.OnDownloadProgressResponse;
import com.google.android.gms.drive.internal.OnDriveIdResponse;
import com.google.android.gms.drive.internal.OnListEntriesResponse;
import com.google.android.gms.drive.internal.OnListParentsResponse;
import com.google.android.gms.drive.internal.OnMetadataResponse;
import com.google.android.gms.drive.internal.OnSyncMoreResponse;

public interface v
extends IInterface {
    public void a(OnContentsResponse var1) throws RemoteException;

    public void a(OnDownloadProgressResponse var1) throws RemoteException;

    public void a(OnDriveIdResponse var1) throws RemoteException;

    public void a(OnListEntriesResponse var1) throws RemoteException;

    public void a(OnListParentsResponse var1) throws RemoteException;

    public void a(OnMetadataResponse var1) throws RemoteException;

    public void a(OnSyncMoreResponse var1) throws RemoteException;

    public void m(Status var1) throws RemoteException;

    public void onSuccess() throws RemoteException;

    public static abstract class com.google.android.gms.drive.internal.v$a
    extends Binder
    implements v {
        public com.google.android.gms.drive.internal.v$a() {
            this.attachInterface((IInterface)this, "com.google.android.gms.drive.internal.IDriveServiceCallbacks");
        }

        public static v H(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
            if (iInterface != null && iInterface instanceof v) {
                return (v)iInterface;
            }
            return new a(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            OnDownloadProgressResponse onDownloadProgressResponse = null;
            OnDownloadProgressResponse onDownloadProgressResponse2 = null;
            OnDownloadProgressResponse onDownloadProgressResponse3 = null;
            OnDownloadProgressResponse onDownloadProgressResponse4 = null;
            OnDownloadProgressResponse onDownloadProgressResponse5 = null;
            OnDownloadProgressResponse onDownloadProgressResponse6 = null;
            OnDownloadProgressResponse onDownloadProgressResponse7 = null;
            SafeParcelable safeParcelable = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    return true;
                }
                case 1: {
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (parcel.readInt() != 0) {
                        safeParcelable = (OnDownloadProgressResponse)OnDownloadProgressResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a(safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    safeParcelable = onDownloadProgressResponse;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (OnListEntriesResponse)OnListEntriesResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((OnListEntriesResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    safeParcelable = onDownloadProgressResponse2;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (OnDriveIdResponse)OnDriveIdResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((OnDriveIdResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    safeParcelable = onDownloadProgressResponse3;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (OnMetadataResponse)OnMetadataResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((OnMetadataResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    safeParcelable = onDownloadProgressResponse4;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (OnContentsResponse)OnContentsResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((OnContentsResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 6: {
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    safeParcelable = onDownloadProgressResponse5;
                    if (parcel.readInt() != 0) {
                        safeParcelable = Status.CREATOR.createFromParcel(parcel);
                    }
                    this.m((Status)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 7: {
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    this.onSuccess();
                    parcel2.writeNoException();
                    return true;
                }
                case 8: {
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    safeParcelable = onDownloadProgressResponse6;
                    if (parcel.readInt() != 0) {
                        safeParcelable = (OnListParentsResponse)OnListParentsResponse.CREATOR.createFromParcel(parcel);
                    }
                    this.a((OnListParentsResponse)safeParcelable);
                    parcel2.writeNoException();
                    return true;
                }
                case 9: 
            }
            parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
            safeParcelable = onDownloadProgressResponse7;
            if (parcel.readInt() != 0) {
                safeParcelable = (OnSyncMoreResponse)OnSyncMoreResponse.CREATOR.createFromParcel(parcel);
            }
            this.a((OnSyncMoreResponse)safeParcelable);
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements v {
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
            public void a(OnContentsResponse onContentsResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (onContentsResponse != null) {
                        parcel.writeInt(1);
                        onContentsResponse.writeToParcel(parcel, 0);
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
            public void a(OnDownloadProgressResponse onDownloadProgressResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (onDownloadProgressResponse != null) {
                        parcel.writeInt(1);
                        onDownloadProgressResponse.writeToParcel(parcel, 0);
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
            public void a(OnDriveIdResponse onDriveIdResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (onDriveIdResponse != null) {
                        parcel.writeInt(1);
                        onDriveIdResponse.writeToParcel(parcel, 0);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void a(OnListEntriesResponse onListEntriesResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (onListEntriesResponse != null) {
                        parcel.writeInt(1);
                        onListEntriesResponse.writeToParcel(parcel, 0);
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
            public void a(OnListParentsResponse onListParentsResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (onListParentsResponse != null) {
                        parcel.writeInt(1);
                        onListParentsResponse.writeToParcel(parcel, 0);
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
            public void a(OnMetadataResponse onMetadataResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (onMetadataResponse != null) {
                        parcel.writeInt(1);
                        onMetadataResponse.writeToParcel(parcel, 0);
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
            public void a(OnSyncMoreResponse onSyncMoreResponse) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (onSyncMoreResponse != null) {
                        parcel.writeInt(1);
                        onSyncMoreResponse.writeToParcel(parcel, 0);
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

            public IBinder asBinder() {
                return this.kn;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void m(Status status) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    if (status != null) {
                        parcel.writeInt(1);
                        status.writeToParcel(parcel, 0);
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

            @Override
            public void onSuccess() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveServiceCallbacks");
                    this.kn.transact(7, parcel, parcel2, 0);
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

