package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.dynamic.C0305c;
import com.google.android.gms.dynamic.C0305c.C0818a;
import com.google.android.gms.dynamic.C0306d;
import com.google.android.gms.dynamic.C0306d.C0820a;
import com.google.android.gms.internal.iz.C0922a;
import com.google.android.gms.internal.ja.C0924a;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;

public interface jc extends IInterface {

    /* renamed from: com.google.android.gms.internal.jc.a */
    public static abstract class C0928a extends Binder implements jc {

        /* renamed from: com.google.android.gms.internal.jc.a.a */
        private static class C0927a implements jc {
            private IBinder kn;

            C0927a(IBinder iBinder) {
                this.kn = iBinder;
            }

            public iz m2314a(C0306d c0306d, C0305c c0305c, WalletFragmentOptions walletFragmentOptions, ja jaVar) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
                    obtain.writeStrongBinder(c0306d != null ? c0306d.asBinder() : null);
                    obtain.writeStrongBinder(c0305c != null ? c0305c.asBinder() : null);
                    if (walletFragmentOptions != null) {
                        obtain.writeInt(1);
                        walletFragmentOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (jaVar != null) {
                        iBinder = jaVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.kn.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    iz aS = C0922a.aS(obtain2.readStrongBinder());
                    return aS;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.kn;
            }
        }

        public static jc aV(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof jc)) ? new C0927a(iBinder) : (jc) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IBinder iBinder = null;
            switch (code) {
                case Std.STD_FILE /*1*/:
                    data.enforceInterface("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
                    iz a = m1101a(C0820a.m1755K(data.readStrongBinder()), C0818a.m1754J(data.readStrongBinder()), data.readInt() != 0 ? (WalletFragmentOptions) WalletFragmentOptions.CREATOR.createFromParcel(data) : null, C0924a.aT(data.readStrongBinder()));
                    reply.writeNoException();
                    if (a != null) {
                        iBinder = a.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.wallet.internal.IWalletDynamiteCreator");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    iz m1101a(C0306d c0306d, C0305c c0305c, WalletFragmentOptions walletFragmentOptions, ja jaVar) throws RemoteException;
}
