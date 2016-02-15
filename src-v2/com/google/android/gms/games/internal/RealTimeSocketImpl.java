/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.LocalSocket
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 */
package com.google.android.gms.games.internal;

import android.net.LocalSocket;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

final class RealTimeSocketImpl
implements RealTimeSocket {
    private ParcelFileDescriptor Cj;
    private final LocalSocket JP;
    private final String Jg;

    RealTimeSocketImpl(LocalSocket localSocket, String string2) {
        this.JP = localSocket;
        this.Jg = string2;
    }

    @Override
    public void close() throws IOException {
        this.JP.close();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.JP.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.JP.getOutputStream();
    }

    @Override
    public ParcelFileDescriptor getParcelFileDescriptor() throws IOException {
        if (this.Cj == null && !this.isClosed()) {
            Parcel parcel = Parcel.obtain();
            parcel.writeFileDescriptor(this.JP.getFileDescriptor());
            parcel.setDataPosition(0);
            this.Cj = parcel.readFileDescriptor();
        }
        return this.Cj;
    }

    @Override
    public boolean isClosed() {
        if (!this.JP.isConnected() && !this.JP.isBound()) {
            return true;
        }
        return false;
    }
}

