package com.crumby.impl.derpibooru;

import java.util.ArrayList;

public class DerpibooruAttributes
{
  ArrayList<String> favoritedBy;
  
  public DerpibooruAttributes(ArrayList<String> paramArrayList)
  {
    this.favoritedBy = paramArrayList;
  }
  
  public ArrayList<String> getFavoritedBy()
  {
    return this.favoritedBy;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/derpibooru/DerpibooruAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */