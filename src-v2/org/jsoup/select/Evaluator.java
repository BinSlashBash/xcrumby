/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.select;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.select.Elements;

public abstract class Evaluator {
    protected Evaluator() {
    }

    public abstract boolean matches(Element var1, Element var2);

    public static final class AllElements
    extends Evaluator {
        @Override
        public boolean matches(Element element, Element element2) {
            return true;
        }

        public String toString() {
            return "*";
        }
    }

    public static final class Attribute
    extends Evaluator {
        private String key;

        public Attribute(String string2) {
            this.key = string2;
        }

        @Override
        public boolean matches(Element element, Element element2) {
            return element2.hasAttr(this.key);
        }

        public String toString() {
            return String.format("[%s]", this.key);
        }
    }

    public static abstract class AttributeKeyPair
    extends Evaluator {
        String key;
        String value;

        public AttributeKeyPair(String string2, String string3) {
            Validate.notEmpty(string2);
            Validate.notEmpty(string3);
            this.key = string2.trim().toLowerCase();
            this.value = string3.trim().toLowerCase();
        }
    }

    public static final class AttributeStarting
    extends Evaluator {
        private String keyPrefix;

        public AttributeStarting(String string2) {
            this.keyPrefix = string2;
        }

        @Override
        public boolean matches(Element object, Element element) {
            object = element.attributes().asList().iterator();
            while (object.hasNext()) {
                if (!((org.jsoup.nodes.Attribute)object.next()).getKey().startsWith(this.keyPrefix)) continue;
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format("[^%s]", this.keyPrefix);
        }
    }

    public static final class AttributeWithValue
    extends AttributeKeyPair {
        public AttributeWithValue(String string2, String string3) {
            super(string2, string3);
        }

        @Override
        public boolean matches(Element element, Element element2) {
            if (element2.hasAttr(this.key) && this.value.equalsIgnoreCase(element2.attr(this.key))) {
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format("[%s=%s]", this.key, this.value);
        }
    }

    public static final class AttributeWithValueContaining
    extends AttributeKeyPair {
        public AttributeWithValueContaining(String string2, String string3) {
            super(string2, string3);
        }

        @Override
        public boolean matches(Element element, Element element2) {
            if (element2.hasAttr(this.key) && element2.attr(this.key).toLowerCase().contains(this.value)) {
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format("[%s*=%s]", this.key, this.value);
        }
    }

    public static final class AttributeWithValueEnding
    extends AttributeKeyPair {
        public AttributeWithValueEnding(String string2, String string3) {
            super(string2, string3);
        }

        @Override
        public boolean matches(Element element, Element element2) {
            if (element2.hasAttr(this.key) && element2.attr(this.key).toLowerCase().endsWith(this.value)) {
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format("[%s$=%s]", this.key, this.value);
        }
    }

    public static final class AttributeWithValueMatching
    extends Evaluator {
        String key;
        Pattern pattern;

        public AttributeWithValueMatching(String string2, Pattern pattern) {
            this.key = string2.trim().toLowerCase();
            this.pattern = pattern;
        }

        @Override
        public boolean matches(Element element, Element element2) {
            if (element2.hasAttr(this.key) && this.pattern.matcher(element2.attr(this.key)).find()) {
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format("[%s~=%s]", this.key, this.pattern.toString());
        }
    }

    public static final class AttributeWithValueNot
    extends AttributeKeyPair {
        public AttributeWithValueNot(String string2, String string3) {
            super(string2, string3);
        }

        @Override
        public boolean matches(Element element, Element element2) {
            if (!this.value.equalsIgnoreCase(element2.attr(this.key))) {
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format("[%s!=%s]", this.key, this.value);
        }
    }

    public static final class AttributeWithValueStarting
    extends AttributeKeyPair {
        public AttributeWithValueStarting(String string2, String string3) {
            super(string2, string3);
        }

        @Override
        public boolean matches(Element element, Element element2) {
            if (element2.hasAttr(this.key) && element2.attr(this.key).toLowerCase().startsWith(this.value)) {
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format("[%s^=%s]", this.key, this.value);
        }
    }

    public static final class Class
    extends Evaluator {
        private String className;

        public Class(String string2) {
            this.className = string2;
        }

        @Override
        public boolean matches(Element element, Element element2) {
            return element2.hasClass(this.className);
        }

        public String toString() {
            return String.format(".%s", this.className);
        }
    }

    public static final class ContainsOwnText
    extends Evaluator {
        private String searchText;

        public ContainsOwnText(String string2) {
            this.searchText = string2.toLowerCase();
        }

        @Override
        public boolean matches(Element element, Element element2) {
            return element2.ownText().toLowerCase().contains(this.searchText);
        }

        public String toString() {
            return String.format(":containsOwn(%s", this.searchText);
        }
    }

    public static final class ContainsText
    extends Evaluator {
        private String searchText;

        public ContainsText(String string2) {
            this.searchText = string2.toLowerCase();
        }

        @Override
        public boolean matches(Element element, Element element2) {
            return element2.text().toLowerCase().contains(this.searchText);
        }

        public String toString() {
            return String.format(":contains(%s", this.searchText);
        }
    }

    public static abstract class CssNthEvaluator
    extends Evaluator {
        protected final int a;
        protected final int b;

        public CssNthEvaluator(int n2) {
            this(0, n2);
        }

        public CssNthEvaluator(int n2, int n3) {
            this.a = n2;
            this.b = n3;
        }

        protected abstract int calculatePosition(Element var1, Element var2);

        protected abstract String getPseudoClass();

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean matches(Element element, Element element2) {
            boolean bl2 = true;
            Element element3 = element2.parent();
            if (element3 == null) return false;
            if (element3 instanceof Document) {
                return false;
            }
            int n2 = this.calculatePosition(element, element2);
            if (this.a == 0) {
                if (n2 == this.b) return bl2;
                return false;
            }
            if ((n2 - this.b) * this.a < 0) return false;
            if ((n2 - this.b) % this.a == 0) return bl2;
            return false;
        }

        public String toString() {
            if (this.a == 0) {
                return String.format(":%s(%d)", this.getPseudoClass(), this.b);
            }
            if (this.b == 0) {
                return String.format(":%s(%dn)", this.getPseudoClass(), this.a);
            }
            return String.format(":%s(%dn%+d)", this.getPseudoClass(), this.a, this.b);
        }
    }

    public static final class Id
    extends Evaluator {
        private String id;

        public Id(String string2) {
            this.id = string2;
        }

        @Override
        public boolean matches(Element element, Element element2) {
            return this.id.equals(element2.id());
        }

        public String toString() {
            return String.format("#%s", this.id);
        }
    }

    public static final class IndexEquals
    extends IndexEvaluator {
        public IndexEquals(int n2) {
            super(n2);
        }

        @Override
        public boolean matches(Element element, Element element2) {
            if (element2.elementSiblingIndex() == this.index) {
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format(":eq(%d)", this.index);
        }
    }

    public static abstract class IndexEvaluator
    extends Evaluator {
        int index;

        public IndexEvaluator(int n2) {
            this.index = n2;
        }
    }

    public static final class IndexGreaterThan
    extends IndexEvaluator {
        public IndexGreaterThan(int n2) {
            super(n2);
        }

        @Override
        public boolean matches(Element element, Element element2) {
            if (element2.elementSiblingIndex() > this.index) {
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format(":gt(%d)", this.index);
        }
    }

    public static final class IndexLessThan
    extends IndexEvaluator {
        public IndexLessThan(int n2) {
            super(n2);
        }

        @Override
        public boolean matches(Element element, Element element2) {
            if (element2.elementSiblingIndex() < this.index) {
                return true;
            }
            return false;
        }

        public String toString() {
            return String.format(":lt(%d)", this.index);
        }
    }

    public static final class IsEmpty
    extends Evaluator {
        @Override
        public boolean matches(Element object, Element node) {
            object = node.childNodes();
            for (int i2 = 0; i2 < object.size(); ++i2) {
                node = (Node)object.get(i2);
                if (node instanceof Comment || node instanceof XmlDeclaration || node instanceof DocumentType) continue;
                return false;
            }
            return true;
        }

        public String toString() {
            return ":empty";
        }
    }

    public static final class IsFirstChild
    extends Evaluator {
        @Override
        public boolean matches(Element element, Element element2) {
            element = element2.parent();
            if (element != null && !(element instanceof Document) && element2.elementSiblingIndex() == 0) {
                return true;
            }
            return false;
        }

        public String toString() {
            return ":first-child";
        }
    }

    public static final class IsFirstOfType
    extends IsNthOfType {
        public IsFirstOfType() {
            super(0, 1);
        }

        @Override
        public String toString() {
            return ":first-of-type";
        }
    }

    public static final class IsLastChild
    extends Evaluator {
        @Override
        public boolean matches(Element element, Element element2) {
            element = element2.parent();
            if (element != null && !(element instanceof Document) && element2.elementSiblingIndex() == element.children().size() - 1) {
                return true;
            }
            return false;
        }

        public String toString() {
            return ":last-child";
        }
    }

    public static final class IsLastOfType
    extends IsNthLastOfType {
        public IsLastOfType() {
            super(0, 1);
        }

        @Override
        public String toString() {
            return ":last-of-type";
        }
    }

    public static final class IsNthChild
    extends CssNthEvaluator {
        public IsNthChild(int n2, int n3) {
            super(n2, n3);
        }

        @Override
        protected int calculatePosition(Element element, Element element2) {
            return element2.elementSiblingIndex() + 1;
        }

        @Override
        protected String getPseudoClass() {
            return "nth-child";
        }
    }

    public static final class IsNthLastChild
    extends CssNthEvaluator {
        public IsNthLastChild(int n2, int n3) {
            super(n2, n3);
        }

        @Override
        protected int calculatePosition(Element element, Element element2) {
            return element2.parent().children().size() - element2.elementSiblingIndex();
        }

        @Override
        protected String getPseudoClass() {
            return "nth-last-child";
        }
    }

    public static class IsNthLastOfType
    extends CssNthEvaluator {
        public IsNthLastOfType(int n2, int n3) {
            super(n2, n3);
        }

        @Override
        protected int calculatePosition(Element cloneable, Element element) {
            int n2 = 0;
            cloneable = element.parent().children();
            for (int i2 = element.elementSiblingIndex().intValue(); i2 < cloneable.size(); ++i2) {
                int n3 = n2;
                if (cloneable.get(i2).tag() == element.tag()) {
                    n3 = n2 + 1;
                }
                n2 = n3;
            }
            return n2;
        }

        @Override
        protected String getPseudoClass() {
            return "nth-last-of-type";
        }
    }

    public static class IsNthOfType
    extends CssNthEvaluator {
        public IsNthOfType(int n2, int n3) {
            super(n2, n3);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        protected int calculatePosition(Element cloneable, Element element) {
            void var2_3;
            int n2 = 0;
            Elements elements = var2_3.parent().children();
            int n3 = 0;
            do {
                int n4 = n2;
                if (n3 >= elements.size()) return n4;
                n4 = n2;
                if (elements.get(n3).tag() == var2_3.tag()) {
                    n4 = n2 + 1;
                }
                if (elements.get(n3) == var2_3) {
                    return n4;
                }
                ++n3;
                n2 = n4;
            } while (true);
        }

        @Override
        protected String getPseudoClass() {
            return "nth-of-type";
        }
    }

    public static final class IsOnlyChild
    extends Evaluator {
        @Override
        public boolean matches(Element element, Element element2) {
            element = element2.parent();
            if (element != null && !(element instanceof Document) && element2.siblingElements().size() == 0) {
                return true;
            }
            return false;
        }

        public String toString() {
            return ":only-child";
        }
    }

    public static final class IsOnlyOfType
    extends Evaluator {
        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean matches(Element cloneable, Element element) {
            void var2_4;
            boolean bl2 = true;
            Element element2 = var2_4.parent();
            if (element2 == null) return false;
            if (element2 instanceof Document) {
                return false;
            }
            int n2 = 0;
            Elements elements = element2.children();
            for (int i2 = 0; i2 < elements.size(); ++i2) {
                int n3 = n2;
                if (elements.get(i2).tag().equals(var2_4.tag())) {
                    n3 = n2 + 1;
                }
                n2 = n3;
            }
            if (n2 == true) return bl2;
            return false;
        }

        public String toString() {
            return ":only-of-type";
        }
    }

    public static final class IsRoot
    extends Evaluator {
        @Override
        public boolean matches(Element element, Element element2) {
            boolean bl2 = false;
            if (element instanceof Document) {
                element = element.child(0);
            }
            if (element2 == element) {
                bl2 = true;
            }
            return bl2;
        }

        public String toString() {
            return ":root";
        }
    }

    public static final class Matches
    extends Evaluator {
        private Pattern pattern;

        public Matches(Pattern pattern) {
            this.pattern = pattern;
        }

        @Override
        public boolean matches(Element element, Element element2) {
            return this.pattern.matcher(element2.text()).find();
        }

        public String toString() {
            return String.format(":matches(%s", this.pattern);
        }
    }

    public static final class MatchesOwn
    extends Evaluator {
        private Pattern pattern;

        public MatchesOwn(Pattern pattern) {
            this.pattern = pattern;
        }

        @Override
        public boolean matches(Element element, Element element2) {
            return this.pattern.matcher(element2.ownText()).find();
        }

        public String toString() {
            return String.format(":matchesOwn(%s", this.pattern);
        }
    }

    public static final class Tag
    extends Evaluator {
        private String tagName;

        public Tag(String string2) {
            this.tagName = string2;
        }

        @Override
        public boolean matches(Element element, Element element2) {
            return element2.tagName().equals(this.tagName);
        }

        public String toString() {
            return String.format("%s", this.tagName);
        }
    }

}

