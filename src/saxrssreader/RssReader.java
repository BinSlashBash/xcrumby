package saxrssreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class RssReader
{
  public static RssFeed read(InputStream paramInputStream)
    throws SAXException, IOException
  {
    try
    {
      XMLReader localXMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      RssHandler localRssHandler = new RssHandler();
      paramInputStream = new InputSource(paramInputStream);
      localXMLReader.setContentHandler(localRssHandler);
      localXMLReader.parse(paramInputStream);
      paramInputStream = localRssHandler.getResult();
      return paramInputStream;
    }
    catch (ParserConfigurationException paramInputStream)
    {
      throw new SAXException();
    }
  }
  
  public static RssFeed read(URL paramURL)
    throws SAXException, IOException
  {
    return read(paramURL.openStream());
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/saxrssreader/RssReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */