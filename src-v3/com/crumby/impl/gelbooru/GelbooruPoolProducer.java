package com.crumby.impl.gelbooru;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

public class GelbooruPoolProducer {
    public static GalleryImage test1(String test) {
        Jsoup.parse("http://gelbooru.com/index.php?page=pool&s=list", UnsupportedUrlFragment.DISPLAY_NAME, Parser.htmlParser());
        return null;
    }
}
