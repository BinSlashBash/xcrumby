package com.google.android.gms.games.internal;

import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.ParcelFileDescriptor.AutoCloseOutputStream;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class LibjingleNativeSocket implements RealTimeSocket {
    private static final String TAG;
    private final ParcelFileDescriptor Cj;
    private final InputStream JI;
    private final OutputStream JJ;

    static {
        TAG = LibjingleNativeSocket.class.getSimpleName();
    }

    LibjingleNativeSocket(ParcelFileDescriptor parcelFileDescriptor) {
        this.Cj = parcelFileDescriptor;
        this.JI = new AutoCloseInputStream(parcelFileDescriptor);
        this.JJ = new AutoCloseOutputStream(parcelFileDescriptor);
    }

    public void close() throws IOException {
        this.Cj.close();
    }

    public InputStream getInputStream() throws IOException {
        return this.JI;
    }

    public OutputStream getOutputStream() throws IOException {
        return this.JJ;
    }

    public ParcelFileDescriptor getParcelFileDescriptor() throws IOException {
        return this.Cj;
    }

    public boolean isClosed() {
        try {
            this.JI.available();
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}
