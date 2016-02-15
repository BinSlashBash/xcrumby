package com.crumby.lib.download;

import java.util.ArrayList;

public interface ImageDownloadListener {
    void terminate();

    void update(ImageDownload imageDownload);

    void update(ArrayList<ImageDownload> arrayList);
}
