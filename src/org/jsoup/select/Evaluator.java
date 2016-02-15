package org.jsoup.select;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.Tag;

public abstract class Evaluator
{
  public abstract boolean matches(Element paramElement1, Element paramElement2);
  
  public static final class AllElements
    extends Evaluator
  {
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return true;
    }
    
    public String toString()
    {
      return "*";
    }
  }
  
  public static final class Attribute
    extends Evaluator
  {
    private String key;
    
    public Attribute(String paramString)
    {
      this.key = paramString;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return paramElement2.hasAttr(this.key);
    }
    
    public String toString()
    {
      return String.format("[%s]", new Object[] { this.key });
    }
  }
  
  public static abstract class AttributeKeyPair
    extends Evaluator
  {
    String key;
    String value;
    
    public AttributeKeyPair(String paramString1, String paramString2)
    {
      Validate.notEmpty(paramString1);
      Validate.notEmpty(paramString2);
      this.key = paramString1.trim().toLowerCase();
      this.value = paramString2.trim().toLowerCase();
    }
  }
  
  public static final class AttributeStarting
    extends Evaluator
  {
    private String keyPrefix;
    
    public AttributeStarting(String paramString)
    {
      this.keyPrefix = paramString;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      paramElement1 = paramElement2.attributes().asList().iterator();
      while (paramElement1.hasNext()) {
        if (((Attribute)paramElement1.next()).getKey().startsWith(this.keyPrefix)) {
          return true;
        }
      }
      return false;
    }
    
    public String toString()
    {
      return String.format("[^%s]", new Object[] { this.keyPrefix });
    }
  }
  
  public static final class AttributeWithValue
    extends Evaluator.AttributeKeyPair
  {
    public AttributeWithValue(String paramString1, String paramString2)
    {
      super(paramString2);
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return (paramElement2.hasAttr(this.key)) && (this.value.equalsIgnoreCase(paramElement2.attr(this.key)));
    }
    
    public String toString()
    {
      return String.format("[%s=%s]", new Object[] { this.key, this.value });
    }
  }
  
  public static final class AttributeWithValueContaining
    extends Evaluator.AttributeKeyPair
  {
    public AttributeWithValueContaining(String paramString1, String paramString2)
    {
      super(paramString2);
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return (paramElement2.hasAttr(this.key)) && (paramElement2.attr(this.key).toLowerCase().contains(this.value));
    }
    
    public String toString()
    {
      return String.format("[%s*=%s]", new Object[] { this.key, this.value });
    }
  }
  
  public static final class AttributeWithValueEnding
    extends Evaluator.AttributeKeyPair
  {
    public AttributeWithValueEnding(String paramString1, String paramString2)
    {
      super(paramString2);
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return (paramElement2.hasAttr(this.key)) && (paramElement2.attr(this.key).toLowerCase().endsWith(this.value));
    }
    
    public String toString()
    {
      return String.format("[%s$=%s]", new Object[] { this.key, this.value });
    }
  }
  
  public static final class AttributeWithValueMatching
    extends Evaluator
  {
    String key;
    Pattern pattern;
    
    public AttributeWithValueMatching(String paramString, Pattern paramPattern)
    {
      this.key = paramString.trim().toLowerCase();
      this.pattern = paramPattern;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return (paramElement2.hasAttr(this.key)) && (this.pattern.matcher(paramElement2.attr(this.key)).find());
    }
    
    public String toString()
    {
      return String.format("[%s~=%s]", new Object[] { this.key, this.pattern.toString() });
    }
  }
  
  public static final class AttributeWithValueNot
    extends Evaluator.AttributeKeyPair
  {
    public AttributeWithValueNot(String paramString1, String paramString2)
    {
      super(paramString2);
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return !this.value.equalsIgnoreCase(paramElement2.attr(this.key));
    }
    
    public String toString()
    {
      return String.format("[%s!=%s]", new Object[] { this.key, this.value });
    }
  }
  
  public static final class AttributeWithValueStarting
    extends Evaluator.AttributeKeyPair
  {
    public AttributeWithValueStarting(String paramString1, String paramString2)
    {
      super(paramString2);
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return (paramElement2.hasAttr(this.key)) && (paramElement2.attr(this.key).toLowerCase().startsWith(this.value));
    }
    
    public String toString()
    {
      return String.format("[%s^=%s]", new Object[] { this.key, this.value });
    }
  }
  
  public static final class Class
    extends Evaluator
  {
    private String className;
    
    public Class(String paramString)
    {
      this.className = paramString;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return paramElement2.hasClass(this.className);
    }
    
    public String toString()
    {
      return String.format(".%s", new Object[] { this.className });
    }
  }
  
  public static final class ContainsOwnText
    extends Evaluator
  {
    private String searchText;
    
    public ContainsOwnText(String paramString)
    {
      this.searchText = paramString.toLowerCase();
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return paramElement2.ownText().toLowerCase().contains(this.searchText);
    }
    
    public String toString()
    {
      return String.format(":containsOwn(%s", new Object[] { this.searchText });
    }
  }
  
  public static final class ContainsText
    extends Evaluator
  {
    private String searchText;
    
    public ContainsText(String paramString)
    {
      this.searchText = paramString.toLowerCase();
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return paramElement2.text().toLowerCase().contains(this.searchText);
    }
    
    public String toString()
    {
      return String.format(":contains(%s", new Object[] { this.searchText });
    }
  }
  
  public static abstract class CssNthEvaluator
    extends Evaluator
  {
    protected final int a;
    protected final int b;
    
    public CssNthEvaluator(int paramInt)
    {
      this(0, paramInt);
    }
    
    public CssNthEvaluator(int paramInt1, int paramInt2)
    {
      this.a = paramInt1;
      this.b = paramInt2;
    }
    
    protected abstract int calculatePosition(Element paramElement1, Element paramElement2);
    
    protected abstract String getPseudoClass();
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      boolean bool = true;
      Element localElement = paramElement2.parent();
      if ((localElement == null) || ((localElement instanceof Document))) {
        bool = false;
      }
      int i;
      do
      {
        do
        {
          return bool;
          i = calculatePosition(paramElement1, paramElement2);
          if (this.a != 0) {
            break;
          }
        } while (i == this.b);
        return false;
      } while (((i - this.b) * this.a >= 0) && ((i - this.b) % this.a == 0));
      return false;
    }
    
    public String toString()
    {
      if (this.a == 0) {
        return String.format(":%s(%d)", new Object[] { getPseudoClass(), Integer.valueOf(this.b) });
      }
      if (this.b == 0) {
        return String.format(":%s(%dn)", new Object[] { getPseudoClass(), Integer.valueOf(this.a) });
      }
      return String.format(":%s(%dn%+d)", new Object[] { getPseudoClass(), Integer.valueOf(this.a), Integer.valueOf(this.b) });
    }
  }
  
  public static final class Id
    extends Evaluator
  {
    private String id;
    
    public Id(String paramString)
    {
      this.id = paramString;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return this.id.equals(paramElement2.id());
    }
    
    public String toString()
    {
      return String.format("#%s", new Object[] { this.id });
    }
  }
  
  public static final class IndexEquals
    extends Evaluator.IndexEvaluator
  {
    public IndexEquals(int paramInt)
    {
      super();
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return paramElement2.elementSiblingIndex().intValue() == this.index;
    }
    
    public String toString()
    {
      return String.format(":eq(%d)", new Object[] { Integer.valueOf(this.index) });
    }
  }
  
  public static abstract class IndexEvaluator
    extends Evaluator
  {
    int index;
    
    public IndexEvaluator(int paramInt)
    {
      this.index = paramInt;
    }
  }
  
  public static final class IndexGreaterThan
    extends Evaluator.IndexEvaluator
  {
    public IndexGreaterThan(int paramInt)
    {
      super();
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return paramElement2.elementSiblingIndex().intValue() > this.index;
    }
    
    public String toString()
    {
      return String.format(":gt(%d)", new Object[] { Integer.valueOf(this.index) });
    }
  }
  
  public static final class IndexLessThan
    extends Evaluator.IndexEvaluator
  {
    public IndexLessThan(int paramInt)
    {
      super();
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return paramElement2.elementSiblingIndex().intValue() < this.index;
    }
    
    public String toString()
    {
      return String.format(":lt(%d)", new Object[] { Integer.valueOf(this.index) });
    }
  }
  
  public static final class IsEmpty
    extends Evaluator
  {
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      paramElement1 = paramElement2.childNodes();
      int i = 0;
      while (i < paramElement1.size())
      {
        paramElement2 = (Node)paramElement1.get(i);
        if ((!(paramElement2 instanceof Comment)) && (!(paramElement2 instanceof XmlDeclaration)) && (!(paramElement2 instanceof DocumentType))) {
          return false;
        }
        i += 1;
      }
      return true;
    }
    
    public String toString()
    {
      return ":empty";
    }
  }
  
  public static final class IsFirstChild
    extends Evaluator
  {
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      paramElement1 = paramElement2.parent();
      return (paramElement1 != null) && (!(paramElement1 instanceof Document)) && (paramElement2.elementSiblingIndex().intValue() == 0);
    }
    
    public String toString()
    {
      return ":first-child";
    }
  }
  
  public static final class IsFirstOfType
    extends Evaluator.IsNthOfType
  {
    public IsFirstOfType()
    {
      super(1);
    }
    
    public String toString()
    {
      return ":first-of-type";
    }
  }
  
  public static final class IsLastChild
    extends Evaluator
  {
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      paramElement1 = paramElement2.parent();
      return (paramElement1 != null) && (!(paramElement1 instanceof Document)) && (paramElement2.elementSiblingIndex().intValue() == paramElement1.children().size() - 1);
    }
    
    public String toString()
    {
      return ":last-child";
    }
  }
  
  public static final class IsLastOfType
    extends Evaluator.IsNthLastOfType
  {
    public IsLastOfType()
    {
      super(1);
    }
    
    public String toString()
    {
      return ":last-of-type";
    }
  }
  
  public static final class IsNthChild
    extends Evaluator.CssNthEvaluator
  {
    public IsNthChild(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    protected int calculatePosition(Element paramElement1, Element paramElement2)
    {
      return paramElement2.elementSiblingIndex().intValue() + 1;
    }
    
    protected String getPseudoClass()
    {
      return "nth-child";
    }
  }
  
  public static final class IsNthLastChild
    extends Evaluator.CssNthEvaluator
  {
    public IsNthLastChild(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    protected int calculatePosition(Element paramElement1, Element paramElement2)
    {
      return paramElement2.parent().children().size() - paramElement2.elementSiblingIndex().intValue();
    }
    
    protected String getPseudoClass()
    {
      return "nth-last-child";
    }
  }
  
  public static class IsNthLastOfType
    extends Evaluator.CssNthEvaluator
  {
    public IsNthLastOfType(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    protected int calculatePosition(Element paramElement1, Element paramElement2)
    {
      int j = 0;
      paramElement1 = paramElement2.parent().children();
      int i = paramElement2.elementSiblingIndex().intValue();
      while (i < paramElement1.size())
      {
        int k = j;
        if (paramElement1.get(i).tag() == paramElement2.tag()) {
          k = j + 1;
        }
        i += 1;
        j = k;
      }
      return j;
    }
    
    protected String getPseudoClass()
    {
      return "nth-last-of-type";
    }
  }
  
  public static class IsNthOfType
    extends Evaluator.CssNthEvaluator
  {
    public IsNthOfType(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    protected int calculatePosition(Element paramElement1, Element paramElement2)
    {
      int i = 0;
      paramElement1 = paramElement2.parent().children();
      int k = 0;
      for (;;)
      {
        int j = i;
        if (k < paramElement1.size())
        {
          j = i;
          if (paramElement1.get(k).tag() == paramElement2.tag()) {
            j = i + 1;
          }
          if (paramElement1.get(k) != paramElement2) {}
        }
        else
        {
          return j;
        }
        k += 1;
        i = j;
      }
    }
    
    protected String getPseudoClass()
    {
      return "nth-of-type";
    }
  }
  
  public static final class IsOnlyChild
    extends Evaluator
  {
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      paramElement1 = paramElement2.parent();
      return (paramElement1 != null) && (!(paramElement1 instanceof Document)) && (paramElement2.siblingElements().size() == 0);
    }
    
    public String toString()
    {
      return ":only-child";
    }
  }
  
  public static final class IsOnlyOfType
    extends Evaluator
  {
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      boolean bool = true;
      paramElement1 = paramElement2.parent();
      if ((paramElement1 == null) || ((paramElement1 instanceof Document))) {
        bool = false;
      }
      int j;
      do
      {
        return bool;
        j = 0;
        paramElement1 = paramElement1.children();
        int i = 0;
        while (i < paramElement1.size())
        {
          int k = j;
          if (paramElement1.get(i).tag().equals(paramElement2.tag())) {
            k = j + 1;
          }
          i += 1;
          j = k;
        }
      } while (j == 1);
      return false;
    }
    
    public String toString()
    {
      return ":only-of-type";
    }
  }
  
  public static final class IsRoot
    extends Evaluator
  {
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      boolean bool = false;
      if ((paramElement1 instanceof Document)) {
        paramElement1 = paramElement1.child(0);
      }
      for (;;)
      {
        if (paramElement2 == paramElement1) {
          bool = true;
        }
        return bool;
      }
    }
    
    public String toString()
    {
      return ":root";
    }
  }
  
  public static final class Matches
    extends Evaluator
  {
    private Pattern pattern;
    
    public Matches(Pattern paramPattern)
    {
      this.pattern = paramPattern;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return this.pattern.matcher(paramElement2.text()).find();
    }
    
    public String toString()
    {
      return String.format(":matches(%s", new Object[] { this.pattern });
    }
  }
  
  public static final class MatchesOwn
    extends Evaluator
  {
    private Pattern pattern;
    
    public MatchesOwn(Pattern paramPattern)
    {
      this.pattern = paramPattern;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return this.pattern.matcher(paramElement2.ownText()).find();
    }
    
    public String toString()
    {
      return String.format(":matchesOwn(%s", new Object[] { this.pattern });
    }
  }
  
  public static final class Tag
    extends Evaluator
  {
    private String tagName;
    
    public Tag(String paramString)
    {
      this.tagName = paramString;
    }
    
    public boolean matches(Element paramElement1, Element paramElement2)
    {
      return paramElement2.tagName().equals(this.tagName);
    }
    
    public String toString()
    {
      return String.format("%s", new Object[] { this.tagName });
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/select/Evaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */