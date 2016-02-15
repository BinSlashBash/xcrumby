package org.jsoup.select;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public class Collector
{
  public static Elements collect(Evaluator paramEvaluator, Element paramElement)
  {
    Elements localElements = new Elements();
    new NodeTraversor(new Accumulator(paramElement, localElements, paramEvaluator)).traverse(paramElement);
    return localElements;
  }
  
  private static class Accumulator
    implements NodeVisitor
  {
    private final Elements elements;
    private final Evaluator eval;
    private final Element root;
    
    Accumulator(Element paramElement, Elements paramElements, Evaluator paramEvaluator)
    {
      this.root = paramElement;
      this.elements = paramElements;
      this.eval = paramEvaluator;
    }
    
    public void head(Node paramNode, int paramInt)
    {
      if ((paramNode instanceof Element))
      {
        paramNode = (Element)paramNode;
        if (this.eval.matches(this.root, paramNode)) {
          this.elements.add(paramNode);
        }
      }
    }
    
    public void tail(Node paramNode, int paramInt) {}
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/select/Collector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */