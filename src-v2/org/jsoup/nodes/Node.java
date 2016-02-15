/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.nodes;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public abstract class Node
implements Cloneable {
    Attributes attributes;
    String baseUri;
    List<Node> childNodes;
    Node parentNode;
    int siblingIndex;

    protected Node() {
        this.childNodes = Collections.emptyList();
        this.attributes = null;
    }

    protected Node(String string2) {
        this(string2, new Attributes());
    }

    protected Node(String string2, Attributes attributes) {
        Validate.notNull(string2);
        Validate.notNull(attributes);
        this.childNodes = new ArrayList<Node>(4);
        this.baseUri = string2.trim();
        this.attributes = attributes;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addSiblingHtml(int n2, String list) {
        Validate.notNull(list);
        Validate.notNull(this.parentNode);
        Element element = this.parent() instanceof Element ? (Element)this.parent() : null;
        list = Parser.parseFragment(list, element, this.baseUri());
        this.parentNode.addChildren(n2, list.toArray(new Node[list.size()]));
    }

    private Element getDeepChild(Element element) {
        Elements elements = element.children();
        if (elements.size() > 0) {
            element = this.getDeepChild((Element)elements.get(0));
        }
        return element;
    }

    private Document.OutputSettings getOutputSettings() {
        if (this.ownerDocument() != null) {
            return this.ownerDocument().outputSettings();
        }
        return new Document("").outputSettings();
    }

    private void reindexChildren() {
        for (int i2 = 0; i2 < this.childNodes.size(); ++i2) {
            this.childNodes.get(i2).setSiblingIndex(i2);
        }
    }

    private void reparentChild(Node node) {
        if (node.parentNode != null) {
            node.parentNode.removeChild(node);
        }
        node.setParentNode(this);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String absUrl(String string2) {
        Validate.notEmpty(string2);
        String string3 = this.attr(string2);
        if (!this.hasAttr(string2)) {
            return "";
        }
        URL uRL = new URL(this.baseUri);
        string2 = string3;
        {
            catch (MalformedURLException malformedURLException) {
                return new URL(string3).toExternalForm();
            }
        }
        try {
            if (!string3.startsWith("?")) return new URL(uRL, string2).toExternalForm();
            string2 = uRL.getPath() + string3;
            return new URL(uRL, string2).toExternalForm();
        }
        catch (MalformedURLException malformedURLException) {
            return "";
        }
    }

    protected /* varargs */ void addChildren(int n2, Node ... arrnode) {
        Validate.noNullElements(arrnode);
        for (int i2 = arrnode.length - 1; i2 >= 0; --i2) {
            Node node = arrnode[i2];
            this.reparentChild(node);
            this.childNodes.add(n2, node);
        }
        this.reindexChildren();
    }

    protected /* varargs */ void addChildren(Node ... arrnode) {
        for (Node node : arrnode) {
            this.reparentChild(node);
            this.childNodes.add(node);
            node.setSiblingIndex(this.childNodes.size() - 1);
        }
    }

    public Node after(String string2) {
        this.addSiblingHtml(this.siblingIndex() + 1, string2);
        return this;
    }

    public Node after(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.addChildren(this.siblingIndex() + 1, node);
        return this;
    }

    public String attr(String string2) {
        Validate.notNull(string2);
        if (this.attributes.hasKey(string2)) {
            return this.attributes.get(string2);
        }
        if (string2.toLowerCase().startsWith("abs:")) {
            return this.absUrl(string2.substring("abs:".length()));
        }
        return "";
    }

    public Node attr(String string2, String string3) {
        this.attributes.put(string2, string3);
        return this;
    }

    public Attributes attributes() {
        return this.attributes;
    }

    public String baseUri() {
        return this.baseUri;
    }

    public Node before(String string2) {
        this.addSiblingHtml(this.siblingIndex(), string2);
        return this;
    }

    public Node before(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.addChildren(this.siblingIndex(), node);
        return this;
    }

    public Node childNode(int n2) {
        return this.childNodes.get(n2);
    }

    public final int childNodeSize() {
        return this.childNodes.size();
    }

    public List<Node> childNodes() {
        return Collections.unmodifiableList(this.childNodes);
    }

    protected Node[] childNodesAsArray() {
        return this.childNodes.toArray(new Node[this.childNodeSize()]);
    }

    public List<Node> childNodesCopy() {
        ArrayList<Node> arrayList = new ArrayList<Node>(this.childNodes.size());
        Iterator<Node> iterator = this.childNodes.iterator();
        while (iterator.hasNext()) {
            arrayList.add((Node)iterator.next().clone());
        }
        return arrayList;
    }

    public Node clone() {
        Node node = this.doClone(null);
        LinkedList<Node> linkedList = new LinkedList<Node>();
        linkedList.add(node);
        while (!linkedList.isEmpty()) {
            Node node2 = (Node)linkedList.remove();
            for (int i2 = 0; i2 < node2.childNodes.size(); ++i2) {
                Node node3 = node2.childNodes.get(i2).doClone(node2);
                node2.childNodes.set(i2, node3);
                linkedList.add(node3);
            }
        }
        return node;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Node doClone(Node object) {
        int n2;
        Node node;
        try {
            node = (Node)super.clone();
            node.parentNode = object;
            n2 = object == null ? 0 : this.siblingIndex;
        }
        catch (CloneNotSupportedException var1_2) {
            throw new RuntimeException(var1_2);
        }
        node.siblingIndex = n2;
        object = this.attributes != null ? this.attributes.clone() : null;
        node.attributes = object;
        node.baseUri = this.baseUri;
        node.childNodes = new ArrayList<Node>(this.childNodes.size());
        object = this.childNodes.iterator();
        do {
            if (!object.hasNext()) {
                return node;
            }
            Node node2 = object.next();
            node.childNodes.add(node2);
        } while (true);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return false;
    }

    public boolean hasAttr(String string2) {
        String string3;
        Validate.notNull(string2);
        if (string2.startsWith("abs:") && this.attributes.hasKey(string3 = string2.substring("abs:".length())) && !this.absUrl(string3).equals("")) {
            return true;
        }
        return this.attributes.hasKey(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n2 = 0;
        int n3 = this.parentNode != null ? this.parentNode.hashCode() : 0;
        if (this.attributes != null) {
            n2 = this.attributes.hashCode();
        }
        return n3 * 31 + n2;
    }

    protected void indent(StringBuilder stringBuilder, int n2, Document.OutputSettings outputSettings) {
        stringBuilder.append("\n").append(StringUtil.padding(outputSettings.indentAmount() * n2));
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public Node nextSibling() {
        if (this.parentNode == null) {
            return null;
        }
        List<Node> list = this.parentNode.childNodes;
        Integer n2 = this.siblingIndex();
        Validate.notNull(n2);
        if (list.size() <= n2 + 1) return null;
        return list.get(n2 + 1);
    }

    public abstract String nodeName();

    public String outerHtml() {
        StringBuilder stringBuilder = new StringBuilder(128);
        this.outerHtml(stringBuilder);
        return stringBuilder.toString();
    }

    protected void outerHtml(StringBuilder stringBuilder) {
        new NodeTraversor(new OuterHtmlVisitor(stringBuilder, this.getOutputSettings())).traverse(this);
    }

    abstract void outerHtmlHead(StringBuilder var1, int var2, Document.OutputSettings var3);

    abstract void outerHtmlTail(StringBuilder var1, int var2, Document.OutputSettings var3);

    public Document ownerDocument() {
        if (this instanceof Document) {
            return (Document)this;
        }
        if (this.parentNode == null) {
            return null;
        }
        return this.parentNode.ownerDocument();
    }

    public Node parent() {
        return this.parentNode;
    }

    public final Node parentNode() {
        return this.parentNode;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public Node previousSibling() {
        if (this.parentNode == null) {
            return null;
        }
        List<Node> list = this.parentNode.childNodes;
        Integer n2 = this.siblingIndex();
        Validate.notNull(n2);
        if (n2 <= 0) return null;
        return list.get(n2 - 1);
    }

    public void remove() {
        Validate.notNull(this.parentNode);
        this.parentNode.removeChild(this);
    }

    public Node removeAttr(String string2) {
        Validate.notNull(string2);
        this.attributes.remove(string2);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void removeChild(Node node) {
        boolean bl2 = node.parentNode == this;
        Validate.isTrue(bl2);
        int n2 = node.siblingIndex();
        this.childNodes.remove(n2);
        this.reindexChildren();
        node.parentNode = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void replaceChild(Node node, Node node2) {
        boolean bl2 = node.parentNode == this;
        Validate.isTrue(bl2);
        Validate.notNull(node2);
        if (node2.parentNode != null) {
            node2.parentNode.removeChild(node2);
        }
        Integer n2 = node.siblingIndex();
        this.childNodes.set(n2, node2);
        node2.parentNode = this;
        node2.setSiblingIndex(n2);
        node.parentNode = null;
    }

    public void replaceWith(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.replaceChild(this, node);
    }

    public void setBaseUri(final String string2) {
        Validate.notNull(string2);
        this.traverse(new NodeVisitor(){

            @Override
            public void head(Node node, int n2) {
                node.baseUri = string2;
            }

            @Override
            public void tail(Node node, int n2) {
            }
        });
    }

    protected void setParentNode(Node node) {
        if (this.parentNode != null) {
            this.parentNode.removeChild(this);
        }
        this.parentNode = node;
    }

    protected void setSiblingIndex(int n2) {
        this.siblingIndex = n2;
    }

    public int siblingIndex() {
        return this.siblingIndex;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<Node> siblingNodes() {
        if (this.parentNode == null) {
            return Collections.emptyList();
        }
        Object object = this.parentNode.childNodes;
        ArrayList<Node> arrayList = new ArrayList<Node>(object.size() - 1);
        Iterator<Node> iterator = object.iterator();
        do {
            object = arrayList;
            if (!iterator.hasNext()) return object;
            object = iterator.next();
            if (object == this) continue;
            arrayList.add((Node)object);
        } while (true);
    }

    public String toString() {
        return this.outerHtml();
    }

    public Node traverse(NodeVisitor nodeVisitor) {
        Validate.notNull(nodeVisitor);
        new NodeTraversor(nodeVisitor).traverse(this);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Node unwrap() {
        Validate.notNull(this.parentNode);
        int n2 = this.siblingIndex;
        Node node = this.childNodes.size() > 0 ? this.childNodes.get(0) : null;
        this.parentNode.addChildren(n2, this.childNodesAsArray());
        this.remove();
        return node;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Node wrap(String object) {
        Validate.notEmpty((String)object);
        Object object2 = this.parent() instanceof Element ? (Element)this.parent() : null;
        object2 = Parser.parseFragment((String)object, (Element)object2, this.baseUri());
        object = (Node)object2.get(0);
        if (object == null) return null;
        if (!(object instanceof Element)) {
            return null;
        }
        Element element = (Element)object;
        object = this.getDeepChild(element);
        this.parentNode.replaceChild(this, element);
        object.addChildren(this);
        object = this;
        if (object2.size() <= 0) return object;
        int n2 = 0;
        do {
            object = this;
            if (n2 >= object2.size()) return object;
            object = (Node)object2.get(n2);
            object.parentNode.removeChild((Node)object);
            element.appendChild((Node)object);
            ++n2;
        } while (true);
    }

    private static class OuterHtmlVisitor
    implements NodeVisitor {
        private StringBuilder accum;
        private Document.OutputSettings out;

        OuterHtmlVisitor(StringBuilder stringBuilder, Document.OutputSettings outputSettings) {
            this.accum = stringBuilder;
            this.out = outputSettings;
        }

        @Override
        public void head(Node node, int n2) {
            node.outerHtmlHead(this.accum, n2, this.out);
        }

        @Override
        public void tail(Node node, int n2) {
            if (!node.nodeName().equals("#text")) {
                node.outerHtmlTail(this.accum, n2, this.out);
            }
        }
    }

}

