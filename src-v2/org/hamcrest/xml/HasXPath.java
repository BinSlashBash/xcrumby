/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest.xml;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.hamcrest.Condition;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsAnything;
import org.w3c.dom.Node;

public class HasXPath
extends TypeSafeDiagnosingMatcher<Node> {
    private static final Condition.Step<Object, String> NODE_EXISTS;
    public static final NamespaceContext NO_NAMESPACE_CONTEXT;
    private static final IsAnything<String> WITH_ANY_CONTENT;
    private final XPathExpression compiledXPath;
    private final QName evaluationMode;
    private final Matcher<String> valueMatcher;
    private final String xpathString;

    static {
        NO_NAMESPACE_CONTEXT = null;
        WITH_ANY_CONTENT = new IsAnything("");
        NODE_EXISTS = HasXPath.nodeExists();
    }

    public HasXPath(String string2, NamespaceContext namespaceContext, Matcher<String> matcher) {
        this(string2, namespaceContext, matcher, XPathConstants.STRING);
    }

    private HasXPath(String string2, NamespaceContext namespaceContext, Matcher<String> matcher, QName qName) {
        this.compiledXPath = HasXPath.compiledXPath(string2, namespaceContext);
        this.xpathString = string2;
        this.valueMatcher = matcher;
        this.evaluationMode = qName;
    }

    public HasXPath(String string2, Matcher<String> matcher) {
        this(string2, NO_NAMESPACE_CONTEXT, matcher);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static XPathExpression compiledXPath(String string2, NamespaceContext object) {
        XPath xPath;
        try {
            xPath = XPathFactory.newInstance().newXPath();
            if (object == null) return xPath.compile(string2);
        }
        catch (XPathExpressionException var1_2) {
            throw new IllegalArgumentException("Invalid XPath : " + string2, var1_2);
        }
        xPath.setNamespaceContext((NamespaceContext)object);
        return xPath.compile(string2);
    }

    private Condition<Object> evaluated(Node object, Description description) {
        try {
            object = Condition.matched(this.compiledXPath.evaluate(object, this.evaluationMode), description);
            return object;
        }
        catch (XPathExpressionException var1_2) {
            description.appendText(var1_2.getMessage());
            return Condition.notMatched();
        }
    }

    @Factory
    public static Matcher<Node> hasXPath(String string2) {
        return HasXPath.hasXPath(string2, NO_NAMESPACE_CONTEXT);
    }

    @Factory
    public static Matcher<Node> hasXPath(String string2, NamespaceContext namespaceContext) {
        return new HasXPath(string2, namespaceContext, WITH_ANY_CONTENT, XPathConstants.NODE);
    }

    @Factory
    public static Matcher<Node> hasXPath(String string2, NamespaceContext namespaceContext, Matcher<String> matcher) {
        return new HasXPath(string2, namespaceContext, matcher, XPathConstants.STRING);
    }

    @Factory
    public static Matcher<Node> hasXPath(String string2, Matcher<String> matcher) {
        return HasXPath.hasXPath(string2, NO_NAMESPACE_CONTEXT, matcher);
    }

    private static Condition.Step<Object, String> nodeExists() {
        return new Condition.Step<Object, String>(){

            @Override
            public Condition<String> apply(Object object, Description description) {
                if (object == null) {
                    description.appendText("xpath returned no results.");
                    return Condition.notMatched();
                }
                return Condition.matched(String.valueOf(object), description);
            }
        };
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("an XML document with XPath ").appendText(this.xpathString);
        if (this.valueMatcher != null) {
            description.appendText(" ").appendDescriptionOf(this.valueMatcher);
        }
    }

    @Override
    public boolean matchesSafely(Node node, Description description) {
        return this.evaluated(node, description).and(NODE_EXISTS).matching(this.valueMatcher);
    }

}

