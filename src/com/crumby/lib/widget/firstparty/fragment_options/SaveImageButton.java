package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.download.ImageDownloadManager;

public class SaveImageButton
  extends ImageButton
  implements View.OnClickListener
{
  private GalleryImage image;
  
  public SaveImageButton(Context paramContext)
  {
    super(paramContext);
  }
  
  public SaveImageButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public SaveImageButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void hide()
  {
    setVisibility(8);
  }
  
  public void initialize(GalleryImage paramGalleryImage)
  {
    this.image = paramGalleryImage;
    setOnClickListener(this);
    render();
  }
  
  public void onClick(View paramView)
  {
    save();
  }
  
  public void render()
  {
    if (this.image.getImageUrl() == null)
    {
      hide();
      return;
    }
    show();
  }
  
  public void save()
  {
    ImageDownloadManager.INSTANCE.downloadOne(this.image);
  }
  
  public void show()
  {
    setVisibility(0);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/fragment_options/SaveImageButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */