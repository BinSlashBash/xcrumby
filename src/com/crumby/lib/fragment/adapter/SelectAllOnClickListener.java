package com.crumby.lib.fragment.adapter;

import android.view.View.OnClickListener;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import java.util.Iterator;
import java.util.List;

public abstract class SelectAllOnClickListener
  implements View.OnClickListener
{
  final List<GalleryImage> images;
  final MultiSelect multiSelect;
  
  protected SelectAllOnClickListener(List<GalleryImage> paramList, MultiSelect paramMultiSelect)
  {
    this.images = paramList;
    this.multiSelect = paramMultiSelect;
  }
  
  public boolean isChecked()
  {
    if (this.images.size() == 0) {
      return false;
    }
    Iterator localIterator = this.images.iterator();
    while (localIterator.hasNext())
    {
      GalleryImage localGalleryImage = (GalleryImage)localIterator.next();
      if ((!localGalleryImage.isUnfilled()) && (!localGalleryImage.isChecked())) {
        return false;
      }
    }
    return true;
  }
  
  public void setChecked(boolean paramBoolean)
  {
    Iterator localIterator = this.images.iterator();
    while (localIterator.hasNext())
    {
      GalleryImage localGalleryImage = (GalleryImage)localIterator.next();
      if ((localGalleryImage != null) && (!localGalleryImage.isUnfilled())) {
        localGalleryImage.setChecked(paramBoolean);
      }
    }
    if (paramBoolean)
    {
      Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_GRID, "selected all", this.images.size() + "");
      this.multiSelect.add(this.images);
      return;
    }
    this.multiSelect.remove(this.images);
  }
  
  public void toggle()
  {
    if (!isChecked()) {}
    for (boolean bool = true;; bool = false)
    {
      setChecked(bool);
      return;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/adapter/SelectAllOnClickListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */