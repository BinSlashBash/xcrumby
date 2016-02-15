package org.jsoup.safety;

import java.util.Iterator;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class Cleaner
{
  private Whitelist whitelist;
  
  public Cleaner(Whitelist paramWhitelist)
  {
    Validate.notNull(paramWhitelist);
    this.whitelist = paramWhitelist;
  }
  
  private int copySafeNodes(Element paramElement1, Element paramElement2)
  {
    paramElement2 = new CleaningVisitor(paramElement1, paramElement2, null);
    new NodeTraversor(paramElement2).traverse(paramElement1);
    return paramElement2.numDiscarded;
  }
  
  private ElementMeta createSafeElement(Element paramElement)
  {
    String str = paramElement.tagName();
    Attributes localAttributes = new Attributes();
    Element localElement = new Element(Tag.valueOf(str), paramElement.baseUri(), localAttributes);
    int i = 0;
    Iterator localIterator = paramElement.attributes().iterator();
    while (localIterator.hasNext())
    {
      Attribute localAttribute = (Attribute)localIterator.next();
      if (this.whitelist.isSafeAttribute(str, paramElement, localAttribute)) {
        localAttributes.put(localAttribute);
      } else {
        i += 1;
      }
    }
    localAttributes.addAll(this.whitelist.getEnforcedAttributes(str));
    return new ElementMeta(localElement, i);
  }
  
  public Document clean(Document paramDocument)
  {
    Validate.notNull(paramDocument);
    Document localDocument = Document.createShell(paramDocument.baseUri());
    if (paramDocument.body() != null) {
      copySafeNodes(paramDocument.body(), localDocument.body());
    }
    return localDocument;
  }
  
  public boolean isValid(Document paramDocument)
  {
    Validate.notNull(paramDocument);
    Document localDocument = Document.createShell(paramDocument.baseUri());
    return copySafeNodes(paramDocument.body(), localDocument.body()) == 0;
  }
  
  private final class CleaningVisitor
    implements NodeVisitor
  {
    private Element destination;
    private int numDiscarded = 0;
    private final Element root;
    
    private CleaningVisitor(Element paramElement1, Element paramElement2)
    {
      this.root = paramElement1;
      this.destination = paramElement2;
    }
    
    public void head(Node paramNode, int paramInt)
    {
      if ((paramNode instanceof Element))
      {
        Element localElement = (Element)paramNode;
        if (Cleaner.this.whitelist.isSafeTag(localElement.tagName()))
        {
          paramNode = Cleaner.this.createSafeElement(localElement);
          localElement = paramNode.el;
          this.destination.appendChild(localElement);
          this.numDiscarded += paramNode.numAttribsDiscarded;
          this.destination = localElement;
        }
        while (paramNode == this.root) {
          return;
        }
        this.numDiscarded += 1;
        return;
      }
      if ((paramNode instanceof TextNode))
      {
        paramNode = new TextNode(((TextNode)paramNode).getWholeText(), paramNode.baseUri());
        this.destination.appendChild(paramNode);
        return;
      }
      this.numDiscarded += 1;
    }
    
    public void tail(Node paramNode, int paramInt)
    {
      if (((paramNode instanceof Element)) && (Cleaner.this.whitelist.isSafeTag(paramNode.nodeName()))) {
        this.destination = this.destination.parent();
      }
    }
  }
  
  private static class ElementMeta
  {
    Element el;
    int numAttribsDiscarded;
    
    ElementMeta(Element paramElement, int paramInt)
    {
      this.el = paramElement;
      this.numAttribsDiscarded = paramInt;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/safety/Cleaner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */