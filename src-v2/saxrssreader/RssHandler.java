/*
 * Decompiled with CFR 0_110.
 */
package saxrssreader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import saxrssreader.RssFeed;
import saxrssreader.RssItem;

public class RssHandler
extends DefaultHandler {
    private RssFeed rssFeed;
    private RssItem rssItem;
    private StringBuilder stringBuilder;

    @Override
    public void characters(char[] arrc, int n2, int n3) {
        this.stringBuilder.append(arrc, n2, n3);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void endElement(String string2, String string3, String string4) {
        if (this.rssFeed != null && this.rssItem == null) {
            if (string4 == null) return;
            if (string4.length() <= 0) return;
            string2 = "set" + string4.substring(0, 1).toUpperCase() + string4.substring(1);
            this.rssFeed.getClass().getMethod(string2, String.class).invoke(this.rssFeed, this.stringBuilder.toString());
            return;
        }
        if (this.rssItem == null) return;
        string2 = string4;
        try {
            if (string4.equals("content:encoded")) {
                string2 = "content";
            }
            string2 = "set" + string2.substring(0, 1).toUpperCase() + string2.substring(1);
            this.rssItem.getClass().getMethod(string2, String.class).invoke(this.rssItem, this.stringBuilder.toString());
            return;
        }
        catch (SecurityException var1_2) {
            return;
        }
        catch (InvocationTargetException var1_3) {
            return;
        }
        catch (IllegalAccessException var1_4) {
            return;
        }
        catch (IllegalArgumentException var1_5) {
            return;
        }
        catch (NoSuchMethodException var1_6) {
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return;
        }
        catch (SecurityException securityException) {
            return;
        }
    }

    public RssFeed getResult() {
        return this.rssFeed;
    }

    @Override
    public void startDocument() {
        this.rssFeed = new RssFeed();
    }

    @Override
    public void startElement(String string2, String string3, String string4, Attributes attributes) {
        this.stringBuilder = new StringBuilder();
        if (string4.equals("item") && this.rssFeed != null) {
            this.rssItem = new RssItem();
            this.rssItem.setFeed(this.rssFeed);
            this.rssFeed.addRssItem(this.rssItem);
        }
    }
}

