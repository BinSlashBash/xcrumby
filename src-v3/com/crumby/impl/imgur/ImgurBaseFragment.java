package com.crumby.impl.imgur;

import android.view.View;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;

public abstract class ImgurBaseFragment extends GalleryGridFragment {
    public void applyGalleryImageChange(View view, GalleryImage image, int position) {
        postUrlChangeToBusWithProducer(image);
    }
}
