package saxrssreader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class RssHandler
  extends DefaultHandler
{
  private RssFeed rssFeed;
  private RssItem rssItem;
  private StringBuilder stringBuilder;
  
  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    this.stringBuilder.append(paramArrayOfChar, paramInt1, paramInt2);
  }
  
  public void endElement(String paramString1, String paramString2, String paramString3)
  {
    if ((this.rssFeed == null) || (this.rssItem != null) || (paramString3 != null)) {}
    for (;;)
    {
      try
      {
        if (paramString3.length() > 0)
        {
          paramString1 = "set" + paramString3.substring(0, 1).toUpperCase() + paramString3.substring(1);
          this.rssFeed.getClass().getMethod(paramString1, new Class[] { String.class }).invoke(this.rssFeed, new Object[] { this.stringBuilder.toString() });
        }
        return;
      }
      catch (InvocationTargetException paramString1)
      {
        return;
      }
      catch (IllegalAccessException paramString1)
      {
        return;
      }
      catch (IllegalArgumentException paramString1)
      {
        return;
      }
      catch (NoSuchMethodException paramString1)
      {
        return;
      }
      catch (SecurityException paramString1) {}
      if (this.rssItem != null)
      {
        paramString1 = paramString3;
        try
        {
          if (paramString3.equals("content:encoded")) {
            paramString1 = "content";
          }
          paramString1 = "set" + paramString1.substring(0, 1).toUpperCase() + paramString1.substring(1);
          this.rssItem.getClass().getMethod(paramString1, new Class[] { String.class }).invoke(this.rssItem, new Object[] { this.stringBuilder.toString() });
          return;
        }
        catch (SecurityException paramString1) {}catch (InvocationTargetException paramString1) {}catch (IllegalAccessException paramString1) {}catch (IllegalArgumentException paramString1) {}catch (NoSuchMethodException paramString1) {}
      }
    }
  }
  
  public RssFeed getResult()
  {
    return this.rssFeed;
  }
  
  public void startDocument()
  {
    this.rssFeed = new RssFeed();
  }
  
  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
  {
    this.stringBuilder = new StringBuilder();
    if ((paramString3.equals("item")) && (this.rssFeed != null))
    {
      this.rssItem = new RssItem();
      this.rssItem.setFeed(this.rssFeed);
      this.rssFeed.addRssItem(this.rssItem);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/saxrssreader/RssHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */