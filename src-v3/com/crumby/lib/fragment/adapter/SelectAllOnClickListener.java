package com.crumby.lib.fragment.adapter;

import android.view.View.OnClickListener;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import java.util.List;

public abstract class SelectAllOnClickListener implements OnClickListener {
    final List<GalleryImage> images;
    final MultiSelect multiSelect;

    protected SelectAllOnClickListener(List<GalleryImage> images, MultiSelect multiSelect) {
        this.images = images;
        this.multiSelect = multiSelect;
    }

    public boolean isChecked() {
        if (this.images.size() == 0) {
            return false;
        }
        for (GalleryImage image : this.images) {
            if (!image.isUnfilled() && !image.isChecked()) {
                return false;
            }
        }
        return true;
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    public void setChecked(boolean check) {
        for (GalleryImage image : this.images) {
            if (!(image == null || image.isUnfilled())) {
                image.setChecked(check);
            }
        }
        if (check) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_GRID, "selected all", this.images.size() + UnsupportedUrlFragment.DISPLAY_NAME);
            this.multiSelect.add(this.images);
            return;
        }
        this.multiSelect.remove(this.images);
    }
}
