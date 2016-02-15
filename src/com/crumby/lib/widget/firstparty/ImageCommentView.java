package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.lib.ImageComment;

public class ImageCommentView
  extends LinearLayout
{
  public ImageCommentView(Context paramContext)
  {
    super(paramContext);
  }
  
  public ImageCommentView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ImageCommentView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void fillWith(ImageComment paramImageComment)
  {
    ((TextView)findViewById(2131492921)).setText(paramImageComment.getUsername());
    ((TextView)findViewById(2131492922)).setText(paramImageComment.getMessage());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/ImageCommentView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */