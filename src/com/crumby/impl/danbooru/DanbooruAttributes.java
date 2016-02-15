package com.crumby.impl.danbooru;

import com.crumby.lib.ExtraAttributes;

public class DanbooruAttributes
  extends ExtraAttributes
{
  private final String[] artistTags;
  private final String[] characterTags;
  private final String[] copyrightTags;
  
  public DanbooruAttributes(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3)
  {
    this.characterTags = paramArrayOfString1;
    this.artistTags = paramArrayOfString2;
    this.copyrightTags = paramArrayOfString3;
  }
  
  public String[] getArtistTags()
  {
    return this.artistTags;
  }
  
  public String[] getCharacterTags()
  {
    return this.characterTags;
  }
  
  public String[] getCopyrightTags()
  {
    return this.copyrightTags;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/danbooru/DanbooruAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */