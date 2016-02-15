/*
 * Decompiled with CFR 0_110.
 */
package saxrssreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import saxrssreader.RssFeed;
import saxrssreader.RssHandler;

public class RssReader {
    public static RssFeed read(InputStream object) throws SAXException, IOException {
        try {
            XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            RssHandler rssHandler = new RssHandler();
            object = new InputSource((InputStream)object);
            xMLReader.setContentHandler(rssHandler);
            xMLReader.parse((InputSource)object);
            object = rssHandler.getResult();
            return object;
        }
        catch (ParserConfigurationException var0_1) {
            throw new SAXException();
        }
    }

    public static RssFeed read(URL uRL) throws SAXException, IOException {
        return RssReader.read(uRL.openStream());
    }
}

