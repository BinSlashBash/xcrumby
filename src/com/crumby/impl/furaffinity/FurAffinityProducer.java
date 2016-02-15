package com.crumby.impl.furaffinity;

import com.crumby.lib.universal.UniversalProducer;

public class FurAffinityProducer
  extends UniversalProducer
{
  protected String getBaseUrl()
  {
    return "http://www.furaffinity.net";
  }
  
  protected String getRegexForMatchingId()
  {
    return null;
  }
  
  public String getScriptName()
  {
    return FurAffinityFragment.class.getSimpleName();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/furaffinity/FurAffinityProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */