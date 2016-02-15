/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.widget.AdapterView
 */
package com.crumby.lib.fragment.adapter;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.DisplayError;

public interface GalleryList {
    public Bundle getArguments();

    public Context getContext();

    public GalleryImage getImage();

    public AdapterView getList();

    public GalleryProducer getProducer();

    public boolean getUserVisibleHint();

    public void indicateProgressChange(float var1);

    public void showError(DisplayError var1, String var2, String var3);
}

