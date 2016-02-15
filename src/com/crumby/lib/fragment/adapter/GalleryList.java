package com.crumby.lib.fragment.adapter;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.DisplayError;

public abstract interface GalleryList
{
  public abstract Bundle getArguments();
  
  public abstract Context getContext();
  
  public abstract GalleryImage getImage();
  
  public abstract AdapterView getList();
  
  public abstract GalleryProducer getProducer();
  
  public abstract boolean getUserVisibleHint();
  
  public abstract void indicateProgressChange(float paramFloat);
  
  public abstract void showError(DisplayError paramDisplayError, String paramString1, String paramString2);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/fragment/adapter/GalleryList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */