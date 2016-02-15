package org.jsoup.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;

abstract class CombiningEvaluator
  extends Evaluator
{
  final ArrayList<Evaluator> evaluators = new ArrayList();
  int num = 0;
  
  CombiningEvaluator() {}
  
  CombiningEvaluator(Collection<Evaluator> paramCollection)
  {
    this();
    this.evaluators.addAll(paramCollection);
    updateNumEvaluators();
  }
  
  void replaceRightMostEvaluator(Evaluator paramEvaluator)
  {
    this.evaluators.set(this.num - 1, paramEvaluator);
  }
  
  Evaluator rightMostEvaluator()
  {
    if (this.num > 0) {
      return (Evaluator)this.evaluators.get(this.num - 1);
    }
    return null;
  }
  
  void updateNumEvaluators()
  {
    this.num = this.evaluators.size();
  }
  
  static final class And
    extends CombiningEvaluator
  {
    And(Collection<Evaluator> paramCollection)
    {
      super();
    }
    
    And(Evaluator... paramVarArgs)
    {
      this(Arrays.asList(paramVarArgs));
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      int i = 0;
      while (i < this.num)
      {
        if (!((Evaluator)this.evaluators.get(i)).matches(paramElement1, paramElement2)) {
          return false;
        }
        i += 1;
      }
      return true;
    }
    
    public String toString()
    {
      return StringUtil.join(this.evaluators, " ");
    }
  }
  
  static final class Or
    extends CombiningEvaluator
  {
    Or() {}
    
    Or(Collection<Evaluator> paramCollection)
    {
      if (this.num > 1) {
        this.evaluators.add(new CombiningEvaluator.And(paramCollection));
      }
      for (;;)
      {
        updateNumEvaluators();
        return;
        this.evaluators.addAll(paramCollection);
      }
    }
    
    public void add(Evaluator paramEvaluator)
    {
      this.evaluators.add(paramEvaluator);
      updateNumEvaluators();
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      int i = 0;
      while (i < this.num)
      {
        if (((Evaluator)this.evaluators.get(i)).matches(paramElement1, paramElement2)) {
          return true;
        }
        i += 1;
      }
      return false;
    }
    
    public String toString()
    {
      return String.format(":or%s", new Object[] { this.evaluators });
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/select/CombiningEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */