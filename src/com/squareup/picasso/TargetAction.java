package com.squareup.picasso;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

final class TargetAction
  extends Action<Target>
{
  TargetAction(Picasso paramPicasso, Target paramTarget, Request paramRequest, boolean paramBoolean, int paramInt, Drawable paramDrawable, String paramString)
  {
    super(paramPicasso, paramTarget, paramRequest, paramBoolean, false, paramInt, paramDrawable, paramString);
  }
  
  void complete(Bitmap paramBitmap, Picasso.LoadedFrom paramLoadedFrom)
  {
    if (paramBitmap == null) {
      throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", new Object[] { this }));
    }
    Target localTarget = (Target)getTarget();
    if (localTarget != null)
    {
      localTarget.onBitmapLoaded(paramBitmap, paramLoadedFrom);
      if (paramBitmap.isRecycled()) {
        throw new IllegalStateException("Target callback must not recycle bitmap!");
      }
    }
  }
  
  void error(Exception paramException)
  {
    paramException = (Target)getTarget();
    if (paramException != null)
    {
      if (this.errorResId != 0) {
        paramException.onBitmapFailed(this.picasso.context.getResources().getDrawable(this.errorResId));
      }
    }
    else {
      return;
    }
    paramException.onBitmapFailed(this.errorDrawable);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/squareup/picasso/TargetAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */