package com.crumby.impl.idolchan;

import android.net.Uri;
import com.crumby.GalleryViewer;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.universal.UniversalProducer;

public class SankakuChanProducer
  extends UniversalProducer
{
  protected String getBaseUrl()
  {
    return "https://idol.sankakucomplex.com";
  }
  
  public String getHostUrl()
  {
    Object localObject2 = getQueryParameter(Uri.parse(super.getHostUrl()), super.getHostUrl(), "tags");
    String str = getBaseUrl() + "?tags=";
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = localObject2;
      if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(SankakuChanFragment.class)) {
        localObject1 = "rating:safe";
      }
    }
    localObject2 = str;
    if (localObject1 != null)
    {
      localObject2 = str;
      if (!((String)localObject1).equals("")) {
        localObject2 = str + Uri.encode((String)localObject1);
      }
    }
    return (String)localObject2 + Uri.encode(GalleryViewer.getBlacklist());
  }
  
  protected String getRegexForMatchingId()
  {
    return SankakuChanFragment.REGEX_URL;
  }
  
  protected String getScriptName()
  {
    return SankakuChanFragment.class.getSimpleName();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/idolchan/SankakuChanProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */