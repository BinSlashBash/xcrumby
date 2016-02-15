/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.widget.firstparty.multiselect;

import com.crumby.lib.GalleryImage;
import java.util.Collection;

public interface MultiSelect {
    public void add(GalleryImage var1);

    public void add(Collection<GalleryImage> var1);

    public boolean isOpen();

    public void remove(Collection<GalleryImage> var1);

    public boolean remove(GalleryImage var1);
}

