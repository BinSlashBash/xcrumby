/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.ParcelFileDescriptor
 */
package com.google.android.gms.games.multiplayer.realtime;

import android.os.ParcelFileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface RealTimeSocket {
    public void close() throws IOException;

    public InputStream getInputStream() throws IOException;

    public OutputStream getOutputStream() throws IOException;

    public ParcelFileDescriptor getParcelFileDescriptor() throws IOException;

    public boolean isClosed();
}

