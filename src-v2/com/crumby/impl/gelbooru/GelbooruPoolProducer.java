/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.gelbooru;

import com.crumby.lib.GalleryImage;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;

public class GelbooruPoolProducer {
    public static GalleryImage test1(String string2) {
        Jsoup.parse("http://gelbooru.com/index.php?page=pool&s=list", "", Parser.htmlParser());
        return null;
    }
}

