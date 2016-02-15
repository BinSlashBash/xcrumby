package com.crumby.lib.widget.firstparty.multiselect;

import com.crumby.lib.GalleryImage;
import java.util.Collection;

public abstract interface MultiSelect
{
  public abstract void add(GalleryImage paramGalleryImage);
  
  public abstract void add(Collection<GalleryImage> paramCollection);
  
  public abstract boolean isOpen();
  
  public abstract void remove(Collection<GalleryImage> paramCollection);
  
  public abstract boolean remove(GalleryImage paramGalleryImage);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/multiselect/MultiSelect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */