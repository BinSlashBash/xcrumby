package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.SelectAllOnClickListener;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import java.util.List;

public class SelectAllButton
  extends CustomToggleButton
  implements GalleryConsumer
{
  SelectAllOnClickListener clickListener;
  
  public SelectAllButton(Context paramContext)
  {
    super(paramContext);
  }
  
  public SelectAllButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public SelectAllButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public boolean addImages(List<GalleryImage> paramList)
  {
    return false;
  }
  
  public void finishLoading() {}
  
  public boolean isChecked()
  {
    if (this.clickListener == null) {
      return false;
    }
    return this.clickListener.isChecked();
  }
  
  public void notifyDataSetChanged() {}
  
  public void setChecked(boolean paramBoolean)
  {
    this.clickListener.setChecked(paramBoolean);
  }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    super.setOnClickListener(paramOnClickListener);
    this.clickListener = ((SelectAllOnClickListener)paramOnClickListener);
  }
  
  public void showError(Exception paramException) {}
  
  public void toggle()
  {
    this.clickListener.toggle();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/fragment_options/SelectAllButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */