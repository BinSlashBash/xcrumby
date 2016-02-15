package org.jsoup.select;

import java.util.Iterator;
import org.jsoup.nodes.Element;

abstract class StructuralEvaluator
  extends Evaluator
{
  Evaluator evaluator;
  
  static class Has
    extends StructuralEvaluator
  {
    public Has(Evaluator paramEvaluator)
    {
      this.evaluator = paramEvaluator;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      Iterator localIterator = paramElement2.getAllElements().iterator();
      while (localIterator.hasNext())
      {
        Element localElement = (Element)localIterator.next();
        if ((localElement != paramElement2) && (this.evaluator.matches(paramElement1, localElement))) {
          return true;
        }
      }
      return false;
    }
    
    public String toString()
    {
      return String.format(":has(%s)", new Object[] { this.evaluator });
    }
  }
  
  static class ImmediateParent
    extends StructuralEvaluator
  {
    public ImmediateParent(Evaluator paramEvaluator)
    {
      this.evaluator = paramEvaluator;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      if (paramElement1 == paramElement2) {}
      do
      {
        return false;
        paramElement2 = paramElement2.parent();
      } while ((paramElement2 == null) || (!this.evaluator.matches(paramElement1, paramElement2)));
      return true;
    }
    
    public String toString()
    {
      return String.format(":ImmediateParent%s", new Object[] { this.evaluator });
    }
  }
  
  static class ImmediatePreviousSibling
    extends StructuralEvaluator
  {
    public ImmediatePreviousSibling(Evaluator paramEvaluator)
    {
      this.evaluator = paramEvaluator;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      if (paramElement1 == paramElement2) {}
      do
      {
        return false;
        paramElement2 = paramElement2.previousElementSibling();
      } while ((paramElement2 == null) || (!this.evaluator.matches(paramElement1, paramElement2)));
      return true;
    }
    
    public String toString()
    {
      return String.format(":prev%s", new Object[] { this.evaluator });
    }
  }
  
  static class Not
    extends StructuralEvaluator
  {
    public Not(Evaluator paramEvaluator)
    {
      this.evaluator = paramEvaluator;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return !this.evaluator.matches(paramElement1, paramElement2);
    }
    
    public String toString()
    {
      return String.format(":not%s", new Object[] { this.evaluator });
    }
  }
  
  static class Parent
    extends StructuralEvaluator
  {
    public Parent(Evaluator paramEvaluator)
    {
      this.evaluator = paramEvaluator;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      if (paramElement1 == paramElement2) {}
      for (;;)
      {
        return false;
        for (paramElement2 = paramElement2.parent(); paramElement2 != paramElement1; paramElement2 = paramElement2.parent()) {
          if (this.evaluator.matches(paramElement1, paramElement2)) {
            return true;
          }
        }
      }
    }
    
    public String toString()
    {
      return String.format(":parent%s", new Object[] { this.evaluator });
    }
  }
  
  static class PreviousSibling
    extends StructuralEvaluator
  {
    public PreviousSibling(Evaluator paramEvaluator)
    {
      this.evaluator = paramEvaluator;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      if (paramElement1 == paramElement2) {}
      for (;;)
      {
        return false;
        for (paramElement2 = paramElement2.previousElementSibling(); paramElement2 != null; paramElement2 = paramElement2.previousElementSibling()) {
          if (this.evaluator.matches(paramElement1, paramElement2)) {
            return true;
          }
        }
      }
    }
    
    public String toString()
    {
      return String.format(":prev*%s", new Object[] { this.evaluator });
    }
  }
  
  static class Root
    extends Evaluator
  {
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return paramElement1 == paramElement2;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/select/StructuralEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */