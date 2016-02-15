package com.crumby.impl;

import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;

public class ProducerFocus
{
  int lastImageFocus;
  GalleryProducer producer;
  
  public ProducerFocus(GalleryProducer paramGalleryProducer, int paramInt)
  {
    this.producer = paramGalleryProducer;
    this.lastImageFocus = paramInt;
  }
  
  public ProducerFocus(String paramString)
  {
    this.producer = FragmentRouter.INSTANCE.getGalleryFragmentInstance(paramString).getProducer();
    this.lastImageFocus = -1;
  }
  
  public GalleryProducer getProducer()
  {
    return this.producer;
  }
  
  public boolean reselect()
  {
    if ((this.lastImageFocus != this.producer.getCurrentImageFocus()) && (this.producer.getCurrentImageFocus() != -1)) {}
    for (boolean bool = true;; bool = false)
    {
      this.lastImageFocus = this.producer.getCurrentImageFocus();
      return bool;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/ProducerFocus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */