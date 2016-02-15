package com.crumby.impl.gelbooru;

import com.crumby.lib.GalleryImage;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

public class GelbooruPoolProducer
{
  public static GalleryImage test1(String paramString)
  {
    Jsoup.parse("http://gelbooru.com/index.php?page=pool&s=list", "", Parser.htmlParser());
    return null;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/impl/gelbooru/GelbooruPoolProducer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */