package saxrssreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class RssReader {
    public static RssFeed read(URL url) throws SAXException, IOException {
        return read(url.openStream());
    }

    public static RssFeed read(InputStream stream) throws SAXException, IOException {
        try {
            XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            RssHandler handler = new RssHandler();
            InputSource input = new InputSource(stream);
            reader.setContentHandler(handler);
            reader.parse(input);
            return handler.getResult();
        } catch (ParserConfigurationException e) {
            throw new SAXException();
        }
    }
}
