/*
 * Decompiled with CFR 0_110.
 */
package com.google.android.gms.maps.model;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public abstract class UrlTileProvider
implements TileProvider {
    private final int kr;
    private final int ks;

    public UrlTileProvider(int n2, int n3) {
        this.kr = n2;
        this.ks = n3;
    }

    private static long a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] arrby = new byte[4096];
        long l2 = 0;
        int n2;
        while ((n2 = inputStream.read(arrby)) != -1) {
            outputStream.write(arrby, 0, n2);
            l2 += (long)n2;
        }
        return l2;
    }

    private static byte[] a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        UrlTileProvider.a(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public final Tile getTile(int n2, int n3, int n4) {
        Object object = this.getTileUrl(n2, n3, n4);
        if (object == null) {
            return NO_TILE;
        }
        try {
            object = new Tile(this.kr, this.ks, UrlTileProvider.a(object.openStream()));
            return object;
        }
        catch (IOException var4_5) {
            return null;
        }
    }

    public abstract URL getTileUrl(int var1, int var2, int var3);
}

