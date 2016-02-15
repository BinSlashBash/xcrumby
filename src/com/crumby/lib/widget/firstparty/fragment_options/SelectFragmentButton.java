package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.ImageWrapper;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;

public class SelectFragmentButton
  extends CustomToggleButton
  implements View.OnClickListener
{
  GalleryImage image;
  private MultiSelect multiSelect;
  
  public SelectFragmentButton(Context paramContext)
  {
    super(paramContext);
    setOnClickListener(this);
  }
  
  public SelectFragmentButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setOnClickListener(this);
  }
  
  public SelectFragmentButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setOnClickListener(this);
  }
  
  public void initialize(GalleryImage paramGalleryImage, MultiSelect paramMultiSelect)
  {
    this.image = paramGalleryImage;
    setTag(new ImageWrapper(paramGalleryImage));
    this.multiSelect = paramMultiSelect;
    setVisibility(0);
  }
  
  public boolean isChecked()
  {
    if (this.image == null) {
      return false;
    }
    return this.image.isChecked();
  }
  
  public void onClick(View paramView)
  {
    toggle();
  }
  
  public void setChecked(boolean paramBoolean)
  {
    this.image.setChecked(paramBoolean);
    if (this.image.isChecked())
    {
      this.multiSelect.add(this.image);
      return;
    }
    this.multiSelect.remove(this.image);
  }
  
  public void toggle()
  {
    if (!this.image.isChecked()) {}
    for (boolean bool = true;; bool = false)
    {
      setChecked(bool);
      return;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/fragment_options/SelectFragmentButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */