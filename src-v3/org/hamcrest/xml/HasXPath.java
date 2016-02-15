package org.hamcrest.xml;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
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

public class HasXPath extends TypeSafeDiagnosingMatcher<Node> {
    private static final Step<Object, String> NODE_EXISTS;
    public static final NamespaceContext NO_NAMESPACE_CONTEXT;
    private static final IsAnything<String> WITH_ANY_CONTENT;
    private final XPathExpression compiledXPath;
    private final QName evaluationMode;
    private final Matcher<String> valueMatcher;
    private final String xpathString;

    /* renamed from: org.hamcrest.xml.HasXPath.1 */
    static class C12261 implements Step<Object, String> {
        C12261() {
        }

        public Condition<String> apply(Object value, Description mismatch) {
            if (value != null) {
                return Condition.matched(String.valueOf(value), mismatch);
            }
            mismatch.appendText("xpath returned no results.");
            return Condition.notMatched();
        }
    }

    static {
        NO_NAMESPACE_CONTEXT = null;
        WITH_ANY_CONTENT = new IsAnything(UnsupportedUrlFragment.DISPLAY_NAME);
        NODE_EXISTS = nodeExists();
    }

    public HasXPath(String xPathExpression, Matcher<String> valueMatcher) {
        this(xPathExpression, NO_NAMESPACE_CONTEXT, valueMatcher);
    }

    public HasXPath(String xPathExpression, NamespaceContext namespaceContext, Matcher<String> valueMatcher) {
        this(xPathExpression, namespaceContext, valueMatcher, XPathConstants.STRING);
    }

    private HasXPath(String xPathExpression, NamespaceContext namespaceContext, Matcher<String> valueMatcher, QName mode) {
        this.compiledXPath = compiledXPath(xPathExpression, namespaceContext);
        this.xpathString = xPathExpression;
        this.valueMatcher = valueMatcher;
        this.evaluationMode = mode;
    }

    public boolean matchesSafely(Node item, Description mismatch) {
        return evaluated(item, mismatch).and(NODE_EXISTS).matching(this.valueMatcher);
    }

    public void describeTo(Description description) {
        description.appendText("an XML document with XPath ").appendText(this.xpathString);
        if (this.valueMatcher != null) {
            description.appendText(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).appendDescriptionOf(this.valueMatcher);
        }
    }

    private Condition<Object> evaluated(Node item, Description mismatch) {
        try {
            return Condition.matched(this.compiledXPath.evaluate(item, this.evaluationMode), mismatch);
        } catch (XPathExpressionException e) {
            mismatch.appendText(e.getMessage());
            return Condition.notMatched();
        }
    }

    private static Step<Object, String> nodeExists() {
        return new C12261();
    }

    private static XPathExpression compiledXPath(String xPathExpression, NamespaceContext namespaceContext) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            if (namespaceContext != null) {
                xPath.setNamespaceContext(namespaceContext);
            }
            return xPath.compile(xPathExpression);
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException("Invalid XPath : " + xPathExpression, e);
        }
    }

    @Factory
    public static Matcher<Node> hasXPath(String xPath, Matcher<String> valueMatcher) {
        return hasXPath(xPath, NO_NAMESPACE_CONTEXT, valueMatcher);
    }

    @Factory
    public static Matcher<Node> hasXPath(String xPath, NamespaceContext namespaceContext, Matcher<String> valueMatcher) {
        return new HasXPath(xPath, namespaceContext, valueMatcher, XPathConstants.STRING);
    }

    @Factory
    public static Matcher<Node> hasXPath(String xPath) {
        return hasXPath(xPath, NO_NAMESPACE_CONTEXT);
    }

    @Factory
    public static Matcher<Node> hasXPath(String xPath, NamespaceContext namespaceContext) {
        return new HasXPath(xPath, namespaceContext, WITH_ANY_CONTENT, XPathConstants.NODE);
    }
}
