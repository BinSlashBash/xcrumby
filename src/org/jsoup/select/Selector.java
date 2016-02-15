package org.jsoup.select;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;

public class Selector
{
  private final Evaluator evaluator;
  private final Element root;
  
  private Selector(String paramString, Element paramElement)
  {
    Validate.notNull(paramString);
    paramString = paramString.trim();
    Validate.notEmpty(paramString);
    Validate.notNull(paramElement);
    this.evaluator = QueryParser.parse(paramString);
    this.root = paramElement;
  }
  
  static Elements filterOut(Collection<Element> paramCollection1, Collection<Element> paramCollection2)
  {
    Elements localElements = new Elements();
    paramCollection1 = paramCollection1.iterator();
    while (paramCollection1.hasNext())
    {
      Element localElement = (Element)paramCollection1.next();
      int j = 0;
      Iterator localIterator = paramCollection2.iterator();
      do
      {
        i = j;
        if (!localIterator.hasNext()) {
          break;
        }
      } while (!localElement.equals((Element)localIterator.next()));
      int i = 1;
      if (i == 0) {
        localElements.add(localElement);
      }
    }
    return localElements;
  }
  
  private Elements select()
  {
    return Collector.collect(this.evaluator, this.root);
  }
  
  public static Elements select(String paramString, Iterable<Element> paramIterable)
  {
    Validate.notEmpty(paramString);
    Validate.notNull(paramIterable);
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      localLinkedHashSet.addAll(select(paramString, (Element)paramIterable.next()));
    }
    return new Elements(localLinkedHashSet);
  }
  
  public static Elements select(String paramString, Element paramElement)
  {
    return new Selector(paramString, paramElement).select();
  }
  
  public static class SelectorParseException
    extends IllegalStateException
  {
    public SelectorParseException(String paramString, Object... paramVarArgs)
    {
      super();
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/select/Selector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */