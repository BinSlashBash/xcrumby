package com.crumby.impl.derpibooru;

import android.content.Context;
import android.util.AttributeSet;
import com.crumby.lib.widget.firstparty.form.FormCheckBox;

public class DerpibooruNsfwCheckBox
  extends FormCheckBox
{
  public DerpibooruNsfwCheckBox(Context paramContext)
  {
    super(paramContext);
  }
  
  public DerpibooruNsfwCheckBox(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public DerpibooruNsfwCheckBox(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (DerpibooruProducer.nsfwMode)
    {
      setFieldValue("1");
      return;
    }
    setFieldValue("0");
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    setFieldValue("0");
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/DerpibooruNsfwCheckBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */