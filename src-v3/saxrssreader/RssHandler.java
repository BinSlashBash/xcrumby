package saxrssreader;

import java.lang.reflect.InvocationTargetException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class RssHandler extends DefaultHandler {
    private RssFeed rssFeed;
    private RssItem rssItem;
    private StringBuilder stringBuilder;

    public void startDocument() {
        this.rssFeed = new RssFeed();
    }

    public RssFeed getResult() {
        return this.rssFeed;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        this.stringBuilder = new StringBuilder();
        if (qName.equals("item") && this.rssFeed != null) {
            this.rssItem = new RssItem();
            this.rssItem.setFeed(this.rssFeed);
            this.rssFeed.addRssItem(this.rssItem);
        }
    }

    public void characters(char[] ch, int start, int length) {
        this.stringBuilder.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) {
        String methodName;
        if (this.rssFeed == null || this.rssItem != null) {
            if (this.rssItem != null) {
                try {
                    if (qName.equals("content:encoded")) {
                        qName = "content";
                    }
                    methodName = "set" + qName.substring(0, 1).toUpperCase() + qName.substring(1);
                    this.rssItem.getClass().getMethod(methodName, new Class[]{String.class}).invoke(this.rssItem, new Object[]{this.stringBuilder.toString()});
                } catch (SecurityException e) {
                } catch (NoSuchMethodException e2) {
                } catch (IllegalArgumentException e3) {
                } catch (IllegalAccessException e4) {
                } catch (InvocationTargetException e5) {
                }
            }
        } else if (qName != null) {
            try {
                if (qName.length() > 0) {
                    methodName = "set" + qName.substring(0, 1).toUpperCase() + qName.substring(1);
                    this.rssFeed.getClass().getMethod(methodName, new Class[]{String.class}).invoke(this.rssFeed, new Object[]{this.stringBuilder.toString()});
                }
            } catch (SecurityException e6) {
            } catch (NoSuchMethodException e7) {
            } catch (IllegalArgumentException e8) {
            } catch (IllegalAccessException e9) {
            } catch (InvocationTargetException e10) {
            }
        }
    }
}
