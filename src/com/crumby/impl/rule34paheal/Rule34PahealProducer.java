package com.crumby.impl.rule34paheal;

import com.crumby.lib.universal.UniversalProducer;

class Rule34PahealProducer
  extends UniversalProducer
{
  protected String getBaseUrl()
  {
    return "http://rule34.paheal.net";
  }
  
  public String getHostUrl()
  {
    return Rule34PahealFragment.getDisplayUrl(super.getHostUrl());
  }
  
  protected String getRegexForMatchingId()
  {
    return null;
  }
  
  protected String getScriptName()
  {
    return Rule34PahealFragment.class.getSimpleName();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/rule34paheal/Rule34PahealProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */