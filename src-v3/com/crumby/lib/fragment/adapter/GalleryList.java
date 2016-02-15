package com.crumby.lib.fragment.adapter;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.DisplayError;

public interface GalleryList {
    Bundle getArguments();

    Context getContext();

    GalleryImage getImage();

    AdapterView getList();

    GalleryProducer getProducer();

    boolean getUserVisibleHint();

    void indicateProgressChange(float f);

    void showError(DisplayError displayError, String str, String str2);
}
