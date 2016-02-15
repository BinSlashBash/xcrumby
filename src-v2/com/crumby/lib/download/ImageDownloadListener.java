/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.download;

import com.crumby.lib.download.ImageDownload;
import java.util.ArrayList;

public interface ImageDownloadListener {
    public void terminate();

    public void update(ImageDownload var1);

    public void update(ArrayList<ImageDownload> var1);
}

