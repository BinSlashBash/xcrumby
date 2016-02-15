package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.GalleryImageView;

public class GalleryGridDefaultHeader
  extends LinearLayout
  implements GalleryImageView
{
  private TextView description;
  private GalleryImage image;
  private TextView title;
  
  public GalleryGridDefaultHeader(Context paramContext)
  {
    super(paramContext);
  }
  
  public GalleryGridDefaultHeader(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public GalleryGridDefaultHeader(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void initialize(GalleryImage paramGalleryImage)
  {
    this.image = paramGalleryImage;
    paramGalleryImage.addView(this);
    update();
  }
  
  protected void onDetachedFromWindow()
  {
    if (this.image != null) {
      this.image.removeView(this);
    }
    super.onDetachedFromWindow();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.title = ((TextView)findViewById(2131492991));
    this.description = ((TextView)findViewById(2131492992));
  }
  
  public void update()
  {
    if (this.image == null) {}
    do
    {
      return;
      this.title.setText(this.image.getTitle());
      if ((this.image.getTitle() != null) && (!this.image.getTitle().equals(""))) {
        this.title.setVisibility(0);
      }
      this.description.setText(this.image.getDescription());
    } while ((this.image.getDescription() == null) || (this.image.getDescription().equals("")));
    this.description.setVisibility(0);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/GalleryGridDefaultHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */