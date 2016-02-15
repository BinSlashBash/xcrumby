/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.IntentSender
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.drive.internal;

import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.drive.internal.AddEventListenerRequest;
import com.google.android.gms.drive.internal.AuthorizeAccessRequest;
import com.google.android.gms.drive.internal.CloseContentsAndUpdateMetadataRequest;
import com.google.android.gms.drive.internal.CloseContentsRequest;
import com.google.android.gms.drive.internal.CreateContentsRequest;
import com.google.android.gms.drive.internal.CreateFileIntentSenderRequest;
import com.google.android.gms.drive.internal.CreateFileRequest;
import com.google.android.gms.drive.internal.CreateFolderRequest;
import com.google.android.gms.drive.internal.DisconnectRequest;
import com.google.android.gms.drive.internal.GetMetadataRequest;
import com.google.android.gms.drive.internal.ListParentsRequest;
import com.google.android.gms.drive.internal.OpenContentsRequest;
import com.google.android.gms.drive.internal.OpenFileIntentSenderRequest;
import com.google.android.gms.drive.internal.QueryRequest;
import com.google.android.gms.drive.internal.RemoveEventListenerRequest;
import com.google.android.gms.drive.internal.TrashResourceRequest;
import com.google.android.gms.drive.internal.UpdateMetadataRequest;
import com.google.android.gms.drive.internal.v;
import com.google.android.gms.drive.internal.w;

public interface u
extends IInterface {
    public IntentSender a(CreateFileIntentSenderRequest var1) throws RemoteException;

    public IntentSender a(OpenFileIntentSenderRequest var1) throws RemoteException;

    public void a(AddEventListenerRequest var1, w var2, String var3, v var4) throws RemoteException;

    public void a(AuthorizeAccessRequest var1, v var2) throws RemoteException;

    public void a(CloseContentsAndUpdateMetadataRequest var1, v var2) throws RemoteException;

    public void a(CloseContentsRequest var1, v var2) throws RemoteException;

    public void a(CreateContentsRequest var1, v var2) throws RemoteException;

    public void a(CreateFileRequest var1, v var2) throws RemoteException;

    public void a(CreateFolderRequest var1, v var2) throws RemoteException;

    public void a(DisconnectRequest var1) throws RemoteException;

    public void a(GetMetadataRequest var1, v var2) throws RemoteException;

    public void a(ListParentsRequest var1, v var2) throws RemoteException;

    public void a(OpenContentsRequest var1, v var2) throws RemoteException;

    public void a(QueryRequest var1, v var2) throws RemoteException;

    public void a(RemoveEventListenerRequest var1, w var2, String var3, v var4) throws RemoteException;

    public void a(TrashResourceRequest var1, v var2) throws RemoteException;

    public void a(UpdateMetadataRequest var1, v var2) throws RemoteException;

    public void a(v var1) throws RemoteException;

    public void b(QueryRequest var1, v var2) throws RemoteException;

    public static abstract class com.google.android.gms.drive.internal.u$a
    extends Binder
    implements u {
        public static u G(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.google.android.gms.drive.internal.IDriveService");
            if (iInterface != null && iInterface instanceof u) {
                return (u)iInterface;
            }
            return new a(iBinder);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean onTransact(int n2, Parcel parcel, Parcel parcel2, int n3) throws RemoteException {
            void var5_75;
            Object var6_5 = null;
            Object var7_6 = null;
            Object var8_7 = null;
            Object var9_8 = null;
            Object var10_9 = null;
            Object var11_10 = null;
            Object var12_11 = null;
            Object var13_12 = null;
            Object var14_13 = null;
            Object var15_14 = null;
            Object var16_15 = null;
            Object var17_16 = null;
            Object var18_17 = null;
            Object var19_18 = null;
            Object var20_19 = null;
            Object var21_20 = null;
            Object var22_21 = null;
            Object var5_22 = null;
            switch (n2) {
                default: {
                    return super.onTransact(n2, parcel, parcel2, n3);
                }
                case 1598968902: {
                    parcel2.writeString("com.google.android.gms.drive.internal.IDriveService");
                    return true;
                }
                case 1: {
                    void var5_24;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    if (parcel.readInt() != 0) {
                        GetMetadataRequest getMetadataRequest = (GetMetadataRequest)GetMetadataRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((GetMetadataRequest)var5_24, v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 2: {
                    void var5_27;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_25 = var6_5;
                    if (parcel.readInt() != 0) {
                        QueryRequest queryRequest = (QueryRequest)QueryRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((QueryRequest)var5_27, v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 3: {
                    void var5_30;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_28 = var7_6;
                    if (parcel.readInt() != 0) {
                        UpdateMetadataRequest updateMetadataRequest = (UpdateMetadataRequest)UpdateMetadataRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((UpdateMetadataRequest)var5_30, v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 4: {
                    void var5_33;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_31 = var8_7;
                    if (parcel.readInt() != 0) {
                        CreateContentsRequest createContentsRequest = (CreateContentsRequest)CreateContentsRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((CreateContentsRequest)var5_33, v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 5: {
                    void var5_36;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_34 = var9_8;
                    if (parcel.readInt() != 0) {
                        CreateFileRequest createFileRequest = (CreateFileRequest)CreateFileRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((CreateFileRequest)var5_36, v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 6: {
                    void var5_39;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_37 = var10_9;
                    if (parcel.readInt() != 0) {
                        CreateFolderRequest createFolderRequest = (CreateFolderRequest)CreateFolderRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((CreateFolderRequest)var5_39, v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 7: {
                    void var5_42;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_40 = var11_10;
                    if (parcel.readInt() != 0) {
                        OpenContentsRequest openContentsRequest = (OpenContentsRequest)OpenContentsRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((OpenContentsRequest)var5_42, v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 8: {
                    void var5_45;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_43 = var12_11;
                    if (parcel.readInt() != 0) {
                        CloseContentsRequest closeContentsRequest = (CloseContentsRequest)CloseContentsRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((CloseContentsRequest)var5_45, v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 9: {
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    this.a(v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 10: {
                    void var5_48;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_46 = var13_12;
                    if (parcel.readInt() != 0) {
                        OpenFileIntentSenderRequest openFileIntentSenderRequest = (OpenFileIntentSenderRequest)OpenFileIntentSenderRequest.CREATOR.createFromParcel(parcel);
                    }
                    parcel = this.a((OpenFileIntentSenderRequest)var5_48);
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
                case 11: {
                    void var5_51;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_49 = var14_13;
                    if (parcel.readInt() != 0) {
                        CreateFileIntentSenderRequest createFileIntentSenderRequest = (CreateFileIntentSenderRequest)CreateFileIntentSenderRequest.CREATOR.createFromParcel(parcel);
                    }
                    parcel = this.a((CreateFileIntentSenderRequest)var5_51);
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
                case 12: {
                    void var5_54;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_52 = var15_14;
                    if (parcel.readInt() != 0) {
                        AuthorizeAccessRequest authorizeAccessRequest = (AuthorizeAccessRequest)AuthorizeAccessRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((AuthorizeAccessRequest)var5_54, v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 13: {
                    void var5_57;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_55 = var16_15;
                    if (parcel.readInt() != 0) {
                        ListParentsRequest listParentsRequest = (ListParentsRequest)ListParentsRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((ListParentsRequest)var5_57, v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 14: {
                    void var5_60;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_58 = var17_16;
                    if (parcel.readInt() != 0) {
                        AddEventListenerRequest addEventListenerRequest = (AddEventListenerRequest)AddEventListenerRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((AddEventListenerRequest)var5_60, w.a.I(parcel.readStrongBinder()), parcel.readString(), v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 15: {
                    void var5_63;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_61 = var18_17;
                    if (parcel.readInt() != 0) {
                        RemoveEventListenerRequest removeEventListenerRequest = (RemoveEventListenerRequest)RemoveEventListenerRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((RemoveEventListenerRequest)var5_63, w.a.I(parcel.readStrongBinder()), parcel.readString(), v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 16: {
                    void var5_66;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_64 = var19_18;
                    if (parcel.readInt() != 0) {
                        DisconnectRequest disconnectRequest = (DisconnectRequest)DisconnectRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((DisconnectRequest)var5_66);
                    parcel2.writeNoException();
                    return true;
                }
                case 17: {
                    void var5_69;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_67 = var20_19;
                    if (parcel.readInt() != 0) {
                        TrashResourceRequest trashResourceRequest = (TrashResourceRequest)TrashResourceRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((TrashResourceRequest)var5_69, v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 18: {
                    void var5_72;
                    parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
                    Object var5_70 = var21_20;
                    if (parcel.readInt() != 0) {
                        CloseContentsAndUpdateMetadataRequest closeContentsAndUpdateMetadataRequest = (CloseContentsAndUpdateMetadataRequest)CloseContentsAndUpdateMetadataRequest.CREATOR.createFromParcel(parcel);
                    }
                    this.a((CloseContentsAndUpdateMetadataRequest)var5_72, v.a.H(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                }
                case 19: 
            }
            parcel.enforceInterface("com.google.android.gms.drive.internal.IDriveService");
            Object var5_73 = var22_21;
            if (parcel.readInt() != 0) {
                QueryRequest queryRequest = (QueryRequest)QueryRequest.CREATOR.createFromParcel(parcel);
            }
            this.b((QueryRequest)var5_75, v.a.H(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        private static class a
        implements u {
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
            public IntentSender a(CreateFileIntentSenderRequest createFileIntentSenderRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (createFileIntentSenderRequest != null) {
                        parcel.writeInt(1);
                        createFileIntentSenderRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(11, parcel, parcel2, 0);
                    parcel2.readException();
                    createFileIntentSenderRequest = parcel2.readInt() != 0 ? (IntentSender)IntentSender.CREATOR.createFromParcel(parcel2) : null;
                    return createFileIntentSenderRequest;
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
            public IntentSender a(OpenFileIntentSenderRequest openFileIntentSenderRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (openFileIntentSenderRequest != null) {
                        parcel.writeInt(1);
                        openFileIntentSenderRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.kn.transact(10, parcel, parcel2, 0);
                    parcel2.readException();
                    openFileIntentSenderRequest = parcel2.readInt() != 0 ? (IntentSender)IntentSender.CREATOR.createFromParcel(parcel2) : null;
                    return openFileIntentSenderRequest;
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
            public void a(AddEventListenerRequest addEventListenerRequest, w w2, String string2, v v2) throws RemoteException {
                Object var5_6 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (addEventListenerRequest != null) {
                        parcel.writeInt(1);
                        addEventListenerRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    addEventListenerRequest = w2 != null ? w2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)addEventListenerRequest);
                    parcel.writeString(string2);
                    addEventListenerRequest = var5_6;
                    if (v2 != null) {
                        addEventListenerRequest = v2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)addEventListenerRequest);
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
            public void a(AuthorizeAccessRequest authorizeAccessRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (authorizeAccessRequest != null) {
                        parcel.writeInt(1);
                        authorizeAccessRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    authorizeAccessRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)authorizeAccessRequest);
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
            public void a(CloseContentsAndUpdateMetadataRequest closeContentsAndUpdateMetadataRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (closeContentsAndUpdateMetadataRequest != null) {
                        parcel.writeInt(1);
                        closeContentsAndUpdateMetadataRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    closeContentsAndUpdateMetadataRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)closeContentsAndUpdateMetadataRequest);
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
            public void a(CloseContentsRequest closeContentsRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (closeContentsRequest != null) {
                        parcel.writeInt(1);
                        closeContentsRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    closeContentsRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)closeContentsRequest);
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
            public void a(CreateContentsRequest createContentsRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (createContentsRequest != null) {
                        parcel.writeInt(1);
                        createContentsRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    createContentsRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)createContentsRequest);
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
            public void a(CreateFileRequest createFileRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (createFileRequest != null) {
                        parcel.writeInt(1);
                        createFileRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    createFileRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)createFileRequest);
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
            public void a(CreateFolderRequest createFolderRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (createFolderRequest != null) {
                        parcel.writeInt(1);
                        createFolderRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    createFolderRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)createFolderRequest);
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
            public void a(DisconnectRequest disconnectRequest) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (disconnectRequest != null) {
                        parcel.writeInt(1);
                        disconnectRequest.writeToParcel(parcel, 0);
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
            public void a(GetMetadataRequest getMetadataRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (getMetadataRequest != null) {
                        parcel.writeInt(1);
                        getMetadataRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    getMetadataRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)getMetadataRequest);
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
            public void a(ListParentsRequest listParentsRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (listParentsRequest != null) {
                        parcel.writeInt(1);
                        listParentsRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    listParentsRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)listParentsRequest);
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
            public void a(OpenContentsRequest openContentsRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (openContentsRequest != null) {
                        parcel.writeInt(1);
                        openContentsRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    openContentsRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)openContentsRequest);
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
            public void a(QueryRequest queryRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (queryRequest != null) {
                        parcel.writeInt(1);
                        queryRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    queryRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)queryRequest);
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
            public void a(RemoveEventListenerRequest removeEventListenerRequest, w w2, String string2, v v2) throws RemoteException {
                Object var5_6 = null;
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (removeEventListenerRequest != null) {
                        parcel.writeInt(1);
                        removeEventListenerRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    removeEventListenerRequest = w2 != null ? w2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)removeEventListenerRequest);
                    parcel.writeString(string2);
                    removeEventListenerRequest = var5_6;
                    if (v2 != null) {
                        removeEventListenerRequest = v2.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)removeEventListenerRequest);
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
            public void a(TrashResourceRequest trashResourceRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (trashResourceRequest != null) {
                        parcel.writeInt(1);
                        trashResourceRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    trashResourceRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)trashResourceRequest);
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
            public void a(UpdateMetadataRequest updateMetadataRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (updateMetadataRequest != null) {
                        parcel.writeInt(1);
                        updateMetadataRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    updateMetadataRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)updateMetadataRequest);
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
            public void a(v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    v2 = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)v2);
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
            public void b(QueryRequest queryRequest, v v2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.google.android.gms.drive.internal.IDriveService");
                    if (queryRequest != null) {
                        parcel.writeInt(1);
                        queryRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    queryRequest = v2 != null ? v2.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)queryRequest);
                    this.kn.transact(19, parcel, parcel2, 0);
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

