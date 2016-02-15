/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.crumby.lib.fragment.adapter;

import android.view.View;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import java.util.Collection;
import java.util.List;

public abstract class SelectAllOnClickListener
implements View.OnClickListener {
    final List<GalleryImage> images;
    final MultiSelect multiSelect;

    protected SelectAllOnClickListener(List<GalleryImage> list, MultiSelect multiSelect) {
        this.images = list;
        this.multiSelect = multiSelect;
    }

    public boolean isChecked() {
        if (this.images.size() == 0) {
            return false;
        }
        for (GalleryImage galleryImage : this.images) {
            if (galleryImage.isUnfilled() || galleryImage.isChecked()) continue;
            return false;
        }
        return true;
    }

    public void setChecked(boolean bl2) {
        for (GalleryImage galleryImage : this.images) {
            if (galleryImage == null || galleryImage.isUnfilled()) continue;
            galleryImage.setChecked(bl2);
        }
        if (bl2) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_GRID, "selected all", "" + this.images.size() + "");
            this.multiSelect.add(this.images);
            return;
        }
        this.multiSelect.remove(this.images);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void toggle() {
        boolean bl2 = !this.isChecked();
        this.setChecked(bl2);
    }
}

