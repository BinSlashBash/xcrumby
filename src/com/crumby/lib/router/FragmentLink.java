package com.crumby.lib.router;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentLink
{
  public static final int INVALID = -1;
  protected String baseUrl;
  protected String displayName;
  private boolean doNotMatchBaseUrl;
  private String faviconUrl;
  private boolean hideBaseUrl;
  private boolean mandatory;
  protected boolean suggestable;
  
  public FragmentLink() {}
  
  public FragmentLink(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    this.displayName = paramString1;
    this.baseUrl = paramString2;
    if (paramString3 == null)
    {
      setFavicon();
      if (paramInt != 1) {
        break label46;
      }
    }
    for (;;)
    {
      this.mandatory = bool;
      return;
      this.faviconUrl = paramString3;
      break;
      label46:
      bool = false;
    }
  }
  
  private int fuzzyMatch(Pattern paramPattern, String paramString)
  {
    paramPattern = paramPattern.matcher(paramString);
    if (paramPattern.find()) {
      return paramPattern.start(1);
    }
    return -1;
  }
  
  public boolean baseUrlIsHidden()
  {
    return this.hideBaseUrl;
  }
  
  public int fuzzyMatchDisplayName(Pattern paramPattern)
  {
    if (this.displayName == null) {
      return -1;
    }
    return fuzzyMatch(paramPattern, this.displayName);
  }
  
  public int fuzzyMatchUrl(Pattern paramPattern)
  {
    return fuzzyMatch(paramPattern, this.baseUrl);
  }
  
  public String[] getAsArray()
  {
    return new String[] { this.displayName, this.baseUrl };
  }
  
  public String getBaseUrl()
  {
    return this.baseUrl;
  }
  
  public String getDisplayName()
  {
    return this.displayName;
  }
  
  public String getFaviconUrl()
  {
    return this.faviconUrl;
  }
  
  public String getRegexUrl()
  {
    return null;
  }
  
  public boolean isMandatory()
  {
    return this.mandatory;
  }
  
  public boolean isSuggestable()
  {
    return this.suggestable;
  }
  
  public void setFavicon()
  {
    for (;;)
    {
      String str3;
      try
      {
        localObject2 = this.baseUrl;
        if ((localObject2 != null) && (!((String)localObject2).contains("crumby://")))
        {
          String str2 = "http://";
          str3 = "/favicon.ico";
          Object localObject1 = localObject2;
          if (((String)localObject2).startsWith("www.")) {
            localObject1 = "http://" + ((String)localObject2).substring("www.".length());
          }
          localObject2 = str2;
          str1 = str3;
          if (localObject1 != null)
          {
            if ((((String)localObject1).contains("derpibooru.org")) || (((String)localObject1).contains("e621.net"))) {
              break label175;
            }
            if (((String)localObject1).contains("sankakucomplex")) {
              break label175;
            }
          }
          else
          {
            this.faviconUrl = ((String)localObject2 + new URL((String)localObject1).getHost() + str1);
            return;
          }
          localObject2 = str2;
          str1 = str3;
          if (!((String)localObject1).contains("gelbooru.com")) {
            continue;
          }
          str1 = "/favicon.png";
          localObject2 = str2;
          continue;
        }
        else
        {
          this.faviconUrl = "https://lh4.ggpht.com/nCBI6wC4Liyg4bKN2qe7UrPEDndmHHRUtomx1fHK2jfxr1ccmkktk6eJ2ThnoDqoD7Y=w300-rw";
          return;
        }
      }
      catch (MalformedURLException localMalformedURLException)
      {
        return;
      }
      label175:
      Object localObject2 = "https://";
      String str1 = str3;
    }
  }
  
  public void setHideBaseUrl(boolean paramBoolean)
  {
    this.hideBaseUrl = paramBoolean;
  }
  
  public void setMandatory(boolean paramBoolean)
  {
    this.mandatory = paramBoolean;
  }
  
  public void setSuggestable(boolean paramBoolean)
  {
    this.suggestable = paramBoolean;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/router/FragmentLink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */