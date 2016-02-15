/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.Selector;

public class Element
extends Node {
    private Set<String> classNames;
    private Tag tag;

    public Element(Tag tag, String string2) {
        this(tag, string2, new Attributes());
    }

    public Element(Tag tag, String string2, Attributes attributes) {
        super(string2, attributes);
        Validate.notNull(tag);
        this.tag = tag;
    }

    private static void accumulateParents(Element element, Elements elements) {
        if ((element = element.parent()) != null && !element.tagName().equals("#root")) {
            elements.add(element);
            Element.accumulateParents(element, elements);
        }
    }

    private static void appendNormalisedText(StringBuilder stringBuilder, TextNode object) {
        String string2;
        Object object2 = string2 = object.getWholeText();
        if (!Element.preserveWhitespace(object.parent())) {
            object2 = object = TextNode.normaliseWhitespace(string2);
            if (TextNode.lastCharIsWhitespace(stringBuilder)) {
                object2 = TextNode.stripLeadingWhitespace((String)object);
            }
        }
        stringBuilder.append((String)object2);
    }

    private static void appendWhitespaceIfBr(Element element, StringBuilder stringBuilder) {
        if (element.tag.getName().equals("br") && !TextNode.lastCharIsWhitespace(stringBuilder)) {
            stringBuilder.append(" ");
        }
    }

    private void html(StringBuilder stringBuilder) {
        Iterator iterator = this.childNodes.iterator();
        while (iterator.hasNext()) {
            ((Node)iterator.next()).outerHtml(stringBuilder);
        }
    }

    private static <E extends Element> Integer indexInList(Element element, List<E> list) {
        Validate.notNull(element);
        Validate.notNull(list);
        for (int i2 = 0; i2 < list.size(); ++i2) {
            if (!((Element)list.get(i2)).equals(element)) continue;
            return i2;
        }
        return null;
    }

    private void ownText(StringBuilder stringBuilder) {
        for (Node node : this.childNodes) {
            if (node instanceof TextNode) {
                Element.appendNormalisedText(stringBuilder, (TextNode)node);
                continue;
            }
            if (!(node instanceof Element)) continue;
            Element.appendWhitespaceIfBr((Element)node, stringBuilder);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean preserveWhitespace(Node node) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (node == null) return bl3;
        bl3 = bl2;
        if (!(node instanceof Element)) return bl3;
        node = (Element)node;
        if (node.tag.preserveWhitespace()) return true;
        bl3 = bl2;
        if (node.parent() == null) return bl3;
        bl3 = bl2;
        if (!node.parent().tag.preserveWhitespace()) return bl3;
        return true;
    }

    public Element addClass(String string2) {
        Validate.notNull(string2);
        Set<String> set = this.classNames();
        set.add(string2);
        this.classNames(set);
        return this;
    }

    @Override
    public Element after(String string2) {
        return (Element)super.after(string2);
    }

    @Override
    public Element after(Node node) {
        return (Element)super.after(node);
    }

    public Element append(String object) {
        Validate.notNull(object);
        object = Parser.parseFragment((String)object, this, this.baseUri());
        this.addChildren(object.toArray(new Node[object.size()]));
        return this;
    }

    public Element appendChild(Node node) {
        Validate.notNull(node);
        this.addChildren(node);
        return this;
    }

    public Element appendElement(String object) {
        object = new Element(Tag.valueOf((String)object), this.baseUri());
        this.appendChild((Node)object);
        return object;
    }

    public Element appendText(String string2) {
        this.appendChild(new TextNode(string2, this.baseUri()));
        return this;
    }

    @Override
    public Element attr(String string2, String string3) {
        super.attr(string2, string3);
        return this;
    }

    @Override
    public Element before(String string2) {
        return (Element)super.before(string2);
    }

    @Override
    public Element before(Node node) {
        return (Element)super.before(node);
    }

    public Element child(int n2) {
        return this.children().get(n2);
    }

    public Elements children() {
        ArrayList<Element> arrayList = new ArrayList<Element>();
        for (Node node : this.childNodes) {
            if (!(node instanceof Element)) continue;
            arrayList.add((Element)node);
        }
        return new Elements((List<Element>)arrayList);
    }

    public String className() {
        return this.attr("class");
    }

    public Set<String> classNames() {
        if (this.classNames == null) {
            this.classNames = new LinkedHashSet<String>(Arrays.asList(this.className().split("\\s+")));
        }
        return this.classNames;
    }

    public Element classNames(Set<String> set) {
        Validate.notNull(set);
        this.attributes.put("class", StringUtil.join(set, " "));
        return this;
    }

    @Override
    public Element clone() {
        Element element = (Element)super.clone();
        element.classNames = null;
        return element;
    }

    public String data() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Node node : this.childNodes) {
            if (node instanceof DataNode) {
                stringBuilder.append(((DataNode)node).getWholeData());
                continue;
            }
            if (!(node instanceof Element)) continue;
            stringBuilder.append(((Element)node).data());
        }
        return stringBuilder.toString();
    }

    public List<DataNode> dataNodes() {
        ArrayList<DataNode> arrayList = new ArrayList<DataNode>();
        for (Node node : this.childNodes) {
            if (!(node instanceof DataNode)) continue;
            arrayList.add((DataNode)node);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Map<String, String> dataset() {
        return this.attributes.dataset();
    }

    public Integer elementSiblingIndex() {
        if (this.parent() == null) {
            return 0;
        }
        return Element.indexInList(this, this.parent().children());
    }

    public Element empty() {
        this.childNodes.clear();
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return false;
    }

    public Element firstElementSibling() {
        Elements elements = this.parent().children();
        if (elements.size() > 1) {
            return (Element)elements.get(0);
        }
        return null;
    }

    public Elements getAllElements() {
        return Collector.collect(new Evaluator.AllElements(), this);
    }

    public Element getElementById(String object) {
        Validate.notEmpty((String)object);
        object = Collector.collect(new Evaluator.Id((String)object), this);
        if (object.size() > 0) {
            return object.get(0);
        }
        return null;
    }

    public Elements getElementsByAttribute(String string2) {
        Validate.notEmpty(string2);
        return Collector.collect(new Evaluator.Attribute(string2.trim().toLowerCase()), this);
    }

    public Elements getElementsByAttributeStarting(String string2) {
        Validate.notEmpty(string2);
        return Collector.collect(new Evaluator.AttributeStarting(string2.trim().toLowerCase()), this);
    }

    public Elements getElementsByAttributeValue(String string2, String string3) {
        return Collector.collect(new Evaluator.AttributeWithValue(string2, string3), this);
    }

    public Elements getElementsByAttributeValueContaining(String string2, String string3) {
        return Collector.collect(new Evaluator.AttributeWithValueContaining(string2, string3), this);
    }

    public Elements getElementsByAttributeValueEnding(String string2, String string3) {
        return Collector.collect(new Evaluator.AttributeWithValueEnding(string2, string3), this);
    }

    public Elements getElementsByAttributeValueMatching(String string2, String string3) {
        Pattern pattern;
        try {
            pattern = Pattern.compile(string3);
        }
        catch (PatternSyntaxException var1_2) {
            throw new IllegalArgumentException("Pattern syntax error: " + string3, var1_2);
        }
        return this.getElementsByAttributeValueMatching(string2, pattern);
    }

    public Elements getElementsByAttributeValueMatching(String string2, Pattern pattern) {
        return Collector.collect(new Evaluator.AttributeWithValueMatching(string2, pattern), this);
    }

    public Elements getElementsByAttributeValueNot(String string2, String string3) {
        return Collector.collect(new Evaluator.AttributeWithValueNot(string2, string3), this);
    }

    public Elements getElementsByAttributeValueStarting(String string2, String string3) {
        return Collector.collect(new Evaluator.AttributeWithValueStarting(string2, string3), this);
    }

    public Elements getElementsByClass(String string2) {
        Validate.notEmpty(string2);
        return Collector.collect(new Evaluator.Class(string2), this);
    }

    public Elements getElementsByIndexEquals(int n2) {
        return Collector.collect(new Evaluator.IndexEquals(n2), this);
    }

    public Elements getElementsByIndexGreaterThan(int n2) {
        return Collector.collect(new Evaluator.IndexGreaterThan(n2), this);
    }

    public Elements getElementsByIndexLessThan(int n2) {
        return Collector.collect(new Evaluator.IndexLessThan(n2), this);
    }

    public Elements getElementsByTag(String string2) {
        Validate.notEmpty(string2);
        return Collector.collect(new Evaluator.Tag(string2.toLowerCase().trim()), this);
    }

    public Elements getElementsContainingOwnText(String string2) {
        return Collector.collect(new Evaluator.ContainsOwnText(string2), this);
    }

    public Elements getElementsContainingText(String string2) {
        return Collector.collect(new Evaluator.ContainsText(string2), this);
    }

    public Elements getElementsMatchingOwnText(String string2) {
        Pattern pattern;
        try {
            pattern = Pattern.compile(string2);
        }
        catch (PatternSyntaxException var2_3) {
            throw new IllegalArgumentException("Pattern syntax error: " + string2, var2_3);
        }
        return this.getElementsMatchingOwnText(pattern);
    }

    public Elements getElementsMatchingOwnText(Pattern pattern) {
        return Collector.collect(new Evaluator.MatchesOwn(pattern), this);
    }

    public Elements getElementsMatchingText(String string2) {
        Pattern pattern;
        try {
            pattern = Pattern.compile(string2);
        }
        catch (PatternSyntaxException var2_3) {
            throw new IllegalArgumentException("Pattern syntax error: " + string2, var2_3);
        }
        return this.getElementsMatchingText(pattern);
    }

    public Elements getElementsMatchingText(Pattern pattern) {
        return Collector.collect(new Evaluator.Matches(pattern), this);
    }

    public boolean hasClass(String string2) {
        Iterator<String> iterator = this.classNames().iterator();
        while (iterator.hasNext()) {
            if (!string2.equalsIgnoreCase(iterator.next())) continue;
            return true;
        }
        return false;
    }

    public boolean hasText() {
        for (Node node : this.childNodes) {
            if (!(node instanceof TextNode ? !((TextNode)node).isBlank() : node instanceof Element && ((Element)node).hasText())) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int hashCode() {
        int n2;
        int n3 = super.hashCode();
        if (this.tag != null) {
            n2 = this.tag.hashCode();
            do {
                return n3 * 31 + n2;
                break;
            } while (true);
        }
        n2 = 0;
        return n3 * 31 + n2;
    }

    public String html() {
        StringBuilder stringBuilder = new StringBuilder();
        this.html(stringBuilder);
        return stringBuilder.toString().trim();
    }

    public Element html(String string2) {
        this.empty();
        this.append(string2);
        return this;
    }

    public String id() {
        String string2;
        String string3 = string2 = this.attr("id");
        if (string2 == null) {
            string3 = "";
        }
        return string3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Element insertChildren(int n2, Collection<? extends Node> collection) {
        Validate.notNull(collection, "Children collection to be inserted must not be null.");
        int n3 = this.childNodeSize();
        int n4 = n2;
        if (n2 < 0) {
            n4 = n2 + (n3 + 1);
        }
        boolean bl2 = n4 >= 0 && n4 <= n3;
        Validate.isTrue(bl2, "Insert position out of bounds.");
        collection = new ArrayList<Node>(collection);
        this.addChildren(n4, collection.toArray(new Node[collection.size()]));
        return this;
    }

    public boolean isBlock() {
        return this.tag.isBlock();
    }

    public Element lastElementSibling() {
        Elements elements = this.parent().children();
        if (elements.size() > 1) {
            return (Element)elements.get(elements.size() - 1);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public Element nextElementSibling() {
        if (this.parentNode == null) {
            return null;
        }
        Elements elements = this.parent().children();
        Integer n2 = Element.indexInList(this, elements);
        Validate.notNull(n2);
        if (elements.size() <= n2 + 1) return null;
        return (Element)elements.get(n2 + 1);
    }

    @Override
    public String nodeName() {
        return this.tag.getName();
    }

    @Override
    void outerHtmlHead(StringBuilder stringBuilder, int n2, Document.OutputSettings outputSettings) {
        if (stringBuilder.length() > 0 && outputSettings.prettyPrint() && (this.tag.formatAsBlock() || this.parent() != null && this.parent().tag().formatAsBlock() || outputSettings.outline())) {
            this.indent(stringBuilder, n2, outputSettings);
        }
        stringBuilder.append("<").append(this.tagName());
        this.attributes.html(stringBuilder, outputSettings);
        if (this.childNodes.isEmpty() && this.tag.isSelfClosing()) {
            stringBuilder.append(" />");
            return;
        }
        stringBuilder.append(">");
    }

    @Override
    void outerHtmlTail(StringBuilder stringBuilder, int n2, Document.OutputSettings outputSettings) {
        if (!this.childNodes.isEmpty() || !this.tag.isSelfClosing()) {
            if (outputSettings.prettyPrint() && !this.childNodes.isEmpty() && (this.tag.formatAsBlock() || outputSettings.outline() && (this.childNodes.size() > 1 || this.childNodes.size() == 1 && !(this.childNodes.get(0) instanceof TextNode)))) {
                this.indent(stringBuilder, n2, outputSettings);
            }
            stringBuilder.append("</").append(this.tagName()).append(">");
        }
    }

    public String ownText() {
        StringBuilder stringBuilder = new StringBuilder();
        this.ownText(stringBuilder);
        return stringBuilder.toString().trim();
    }

    @Override
    public final Element parent() {
        return (Element)this.parentNode;
    }

    public Elements parents() {
        Elements elements = new Elements();
        Element.accumulateParents(this, elements);
        return elements;
    }

    public Element prepend(String object) {
        Validate.notNull(object);
        object = Parser.parseFragment((String)object, this, this.baseUri());
        this.addChildren(0, object.toArray(new Node[object.size()]));
        return this;
    }

    public Element prependChild(Node node) {
        Validate.notNull(node);
        this.addChildren(0, node);
        return this;
    }

    public Element prependElement(String object) {
        object = new Element(Tag.valueOf((String)object), this.baseUri());
        this.prependChild((Node)object);
        return object;
    }

    public Element prependText(String string2) {
        this.prependChild(new TextNode(string2, this.baseUri()));
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public Element previousElementSibling() {
        if (this.parentNode == null) {
            return null;
        }
        Elements elements = this.parent().children();
        Integer n2 = Element.indexInList(this, elements);
        Validate.notNull(n2);
        if (n2 <= 0) return null;
        return (Element)elements.get(n2 - 1);
    }

    public Element removeClass(String string2) {
        Validate.notNull(string2);
        Set<String> set = this.classNames();
        set.remove(string2);
        this.classNames(set);
        return this;
    }

    public Elements select(String string2) {
        return Selector.select(string2, this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Elements siblingElements() {
        void var1_2;
        if (this.parentNode == null) {
            Elements elements = new Elements(0);
            return var1_2;
        } else {
            Elements elements = this.parent().children();
            Elements elements2 = new Elements(elements.size() - 1);
            Iterator iterator = elements.iterator();
            do {
                Elements elements3 = elements2;
                if (!iterator.hasNext()) return var1_2;
                Element element = (Element)iterator.next();
                if (element == this) continue;
                elements2.add(element);
            } while (true);
        }
    }

    public Tag tag() {
        return this.tag;
    }

    public String tagName() {
        return this.tag.getName();
    }

    public Element tagName(String string2) {
        Validate.notEmpty(string2, "Tag name must not be empty.");
        this.tag = Tag.valueOf(string2);
        return this;
    }

    public String text() {
        final StringBuilder stringBuilder = new StringBuilder();
        new NodeTraversor(new NodeVisitor(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void head(Node node, int n2) {
                if (node instanceof TextNode) {
                    node = (TextNode)node;
                    Element.appendNormalisedText(stringBuilder, (TextNode)node);
                    return;
                } else {
                    if (!(node instanceof Element)) return;
                    {
                        node = (Element)node;
                        if (stringBuilder.length() <= 0 || !node.isBlock() && !((Element)node).tag.getName().equals("br") || TextNode.lastCharIsWhitespace(stringBuilder)) return;
                        {
                            stringBuilder.append(" ");
                            return;
                        }
                    }
                }
            }

            @Override
            public void tail(Node node, int n2) {
            }
        }).traverse(this);
        return stringBuilder.toString().trim();
    }

    public Element text(String string2) {
        Validate.notNull(string2);
        this.empty();
        this.appendChild(new TextNode(string2, this.baseUri));
        return this;
    }

    public List<TextNode> textNodes() {
        ArrayList<TextNode> arrayList = new ArrayList<TextNode>();
        for (Node node : this.childNodes) {
            if (!(node instanceof TextNode)) continue;
            arrayList.add((TextNode)node);
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Override
    public String toString() {
        return this.outerHtml();
    }

    /*
     * Enabled aggressive block sorting
     */
    public Element toggleClass(String string2) {
        Validate.notNull(string2);
        Set<String> set = this.classNames();
        if (set.contains(string2)) {
            set.remove(string2);
        } else {
            set.add(string2);
        }
        this.classNames(set);
        return this;
    }

    public String val() {
        if (this.tagName().equals("textarea")) {
            return this.text();
        }
        return this.attr("value");
    }

    public Element val(String string2) {
        if (this.tagName().equals("textarea")) {
            this.text(string2);
            return this;
        }
        this.attr("value", string2);
        return this;
    }

    @Override
    public Element wrap(String string2) {
        return (Element)super.wrap(string2);
    }

}

