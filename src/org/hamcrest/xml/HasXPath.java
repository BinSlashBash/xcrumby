package org.hamcrest.xml;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.hamcrest.Condition;
import org.hamcrest.Condition.Step;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsAnything;
import org.w3c.dom.Node;

public class HasXPath
  extends TypeSafeDiagnosingMatcher<Node>
{
  private static final Condition.Step<Object, String> NODE_EXISTS = nodeExists();
  public static final NamespaceContext NO_NAMESPACE_CONTEXT = null;
  private static final IsAnything<String> WITH_ANY_CONTENT = new IsAnything("");
  private final XPathExpression compiledXPath;
  private final QName evaluationMode;
  private final Matcher<String> valueMatcher;
  private final String xpathString;
  
  public HasXPath(String paramString, NamespaceContext paramNamespaceContext, Matcher<String> paramMatcher)
  {
    this(paramString, paramNamespaceContext, paramMatcher, XPathConstants.STRING);
  }
  
  private HasXPath(String paramString, NamespaceContext paramNamespaceContext, Matcher<String> paramMatcher, QName paramQName)
  {
    this.compiledXPath = compiledXPath(paramString, paramNamespaceContext);
    this.xpathString = paramString;
    this.valueMatcher = paramMatcher;
    this.evaluationMode = paramQName;
  }
  
  public HasXPath(String paramString, Matcher<String> paramMatcher)
  {
    this(paramString, NO_NAMESPACE_CONTEXT, paramMatcher);
  }
  
  private static XPathExpression compiledXPath(String paramString, NamespaceContext paramNamespaceContext)
  {
    try
    {
      XPath localXPath = XPathFactory.newInstance().newXPath();
      if (paramNamespaceContext != null) {
        localXPath.setNamespaceContext(paramNamespaceContext);
      }
      paramNamespaceContext = localXPath.compile(paramString);
      return paramNamespaceContext;
    }
    catch (XPathExpressionException paramNamespaceContext)
    {
      throw new IllegalArgumentException("Invalid XPath : " + paramString, paramNamespaceContext);
    }
  }
  
  private Condition<Object> evaluated(Node paramNode, Description paramDescription)
  {
    try
    {
      paramNode = Condition.matched(this.compiledXPath.evaluate(paramNode, this.evaluationMode), paramDescription);
      return paramNode;
    }
    catch (XPathExpressionException paramNode)
    {
      paramDescription.appendText(paramNode.getMessage());
    }
    return Condition.notMatched();
  }
  
  @Factory
  public static Matcher<Node> hasXPath(String paramString)
  {
    return hasXPath(paramString, NO_NAMESPACE_CONTEXT);
  }
  
  @Factory
  public static Matcher<Node> hasXPath(String paramString, NamespaceContext paramNamespaceContext)
  {
    return new HasXPath(paramString, paramNamespaceContext, WITH_ANY_CONTENT, XPathConstants.NODE);
  }
  
  @Factory
  public static Matcher<Node> hasXPath(String paramString, NamespaceContext paramNamespaceContext, Matcher<String> paramMatcher)
  {
    return new HasXPath(paramString, paramNamespaceContext, paramMatcher, XPathConstants.STRING);
  }
  
  @Factory
  public static Matcher<Node> hasXPath(String paramString, Matcher<String> paramMatcher)
  {
    return hasXPath(paramString, NO_NAMESPACE_CONTEXT, paramMatcher);
  }
  
  private static Condition.Step<Object, String> nodeExists()
  {
    new Condition.Step()
    {
      public Condition<String> apply(Object paramAnonymousObject, Description paramAnonymousDescription)
      {
        if (paramAnonymousObject == null)
        {
          paramAnonymousDescription.appendText("xpath returned no results.");
          return Condition.notMatched();
        }
        return Condition.matched(String.valueOf(paramAnonymousObject), paramAnonymousDescription);
      }
    };
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("an XML document with XPath ").appendText(this.xpathString);
    if (this.valueMatcher != null) {
      paramDescription.appendText(" ").appendDescriptionOf(this.valueMatcher);
    }
  }
  
  public boolean matchesSafely(Node paramNode, Description paramDescription)
  {
    return evaluated(paramNode, paramDescription).and(NODE_EXISTS).matching(this.valueMatcher);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/xml/HasXPath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */