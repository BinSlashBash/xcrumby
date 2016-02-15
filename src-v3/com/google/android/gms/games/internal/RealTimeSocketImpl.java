package com.google.android.gms.games.internal;

import android.net.LocalSocket;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

final class RealTimeSocketImpl implements RealTimeSocket {
    private ParcelFileDescriptor Cj;
    private final LocalSocket JP;
    private final String Jg;

    RealTimeSocketImpl(LocalSocket localSocket, String participantId) {
        this.JP = localSocket;
        this.Jg = participantId;
    }

    public void close() throws IOException {
        this.JP.close();
    }

    public InputStream getInputStream() throws IOException {
        return this.JP.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.JP.getOutputStream();
    }

    public ParcelFileDescriptor getParcelFileDescriptor() throws IOException {
        if (this.Cj == null && !isClosed()) {
            Parcel obtain = Parcel.obtain();
            obtain.writeFileDescriptor(this.JP.getFileDescriptor());
            obtain.setDataPosition(0);
            this.Cj = obtain.readFileDescriptor();
        }
        return this.Cj;
    }

    public boolean isClosed() {
        return (this.JP.isConnected() || this.JP.isBound()) ? false : true;
    }
}
