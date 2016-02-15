/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.examples;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ListLinks {
    /*
     * Enabled aggressive block sorting
     */
    public static void main(String[] iterator) throws IOException {
        boolean bl2 = iterator.length == 1;
        Validate.isTrue(bl2, "usage: supply url to fetch");
        iterator = iterator[0];
        ListLinks.print("Fetching %s...", iterator);
        Object object = Jsoup.connect(iterator).get();
        iterator = object.select("a[href]");
        Object object2 = object.select("[src]");
        object = object.select("link[href]");
        ListLinks.print("\nMedia: (%d)", object2.size());
        object2 = object2.iterator();
        while (object2.hasNext()) {
            Element element = (Element)object2.next();
            if (element.tagName().equals("img")) {
                ListLinks.print(" * %s: <%s> %sx%s (%s)", element.tagName(), element.attr("abs:src"), element.attr("width"), element.attr("height"), ListLinks.trim(element.attr("alt"), 20));
                continue;
            }
            ListLinks.print(" * %s: <%s>", element.tagName(), element.attr("abs:src"));
        }
        ListLinks.print("\nImports: (%d)", object.size());
        object = object.iterator();
        while (object.hasNext()) {
            object2 = (Element)object.next();
            ListLinks.print(" * %s <%s> (%s)", object2.tagName(), object2.attr("abs:href"), object2.attr("rel"));
        }
        ListLinks.print("\nLinks: (%d)", iterator.size());
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            ListLinks.print(" * a: <%s>  (%s)", object.attr("abs:href"), ListLinks.trim(object.text(), 35));
        }
        return;
    }

    private static /* varargs */ void print(String string2, Object ... arrobject) {
        System.out.println(String.format(string2, arrobject));
    }

    private static String trim(String string2, int n2) {
        String string3 = string2;
        if (string2.length() > n2) {
            string3 = string2.substring(0, n2 - 1) + ".";
        }
        return string3;
    }
}

