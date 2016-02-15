/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.ParcelFileDescriptor
 *  android.os.ParcelFileDescriptor$AutoCloseInputStream
 *  android.os.ParcelFileDescriptor$AutoCloseOutputStream
 */
package com.google.android.gms.games.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.games.multiplayer.realtime.RealTimeSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class LibjingleNativeSocket
implements RealTimeSocket {
    private static final String TAG = LibjingleNativeSocket.class.getSimpleName();
    private final ParcelFileDescriptor Cj;
    private final InputStream JI;
    private final OutputStream JJ;

    LibjingleNativeSocket(ParcelFileDescriptor parcelFileDescriptor) {
        this.Cj = parcelFileDescriptor;
        this.JI = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
        this.JJ = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
    }

    @Override
    public void close() throws IOException {
        this.Cj.close();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.JI;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.JJ;
    }

    @Override
    public ParcelFileDescriptor getParcelFileDescriptor() throws IOException {
        return this.Cj;
    }

    @Override
    public boolean isClosed() {
        try {
            this.JI.available();
            return false;
        }
        catch (IOException var1_1) {
            return true;
        }
    }
}

