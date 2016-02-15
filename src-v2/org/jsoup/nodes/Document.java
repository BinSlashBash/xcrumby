/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.nodes;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class Document
extends Element {
    private String location;
    private OutputSettings outputSettings = new OutputSettings();
    private QuirksMode quirksMode = QuirksMode.noQuirks;

    public Document(String string2) {
        super(Tag.valueOf("#root"), string2);
        this.location = string2;
    }

    public static Document createShell(String object) {
        Validate.notNull(object);
        object = new Document((String)object);
        Element element = object.appendElement("html");
        element.appendElement("head");
        element.appendElement("body");
        return object;
    }

    private Element findFirstElementByTagName(String string2, Node object) {
        if (object.nodeName().equals(string2)) {
            return (Element)object;
        }
        object = object.childNodes.iterator();
        while (object.hasNext()) {
            Element element = this.findFirstElementByTagName(string2, (Node)object.next());
            if (element == null) continue;
            return element;
        }
        return null;
    }

    private void normaliseStructure(String object, Element element) {
        Object object2 = this.getElementsByTag((String)object);
        object = object2.first();
        if (object2.size() > 1) {
            ArrayList<Node> arrayList = new ArrayList<Node>();
            for (int i2 = 1; i2 < object2.size(); ++i2) {
                Object object3 = object2.get(i2);
                Iterator<Node> iterator = object3.childNodes.iterator();
                while (iterator.hasNext()) {
                    arrayList.add(iterator.next());
                }
                object3.remove();
            }
            object2 = arrayList.iterator();
            while (object2.hasNext()) {
                object.appendChild((Node)object2.next());
            }
        }
        if (!object.parent().equals(element)) {
            element.appendChild((Node)object);
        }
    }

    private void normaliseTextNodes(Element element) {
        ArrayList<Node> arrayList = new ArrayList<Node>();
        for (Node node : element.childNodes) {
            if (!(node instanceof TextNode) || (node = (TextNode)node).isBlank()) continue;
            arrayList.add(node);
        }
        for (int i2 = arrayList.size() - 1; i2 >= 0; --i2) {
            Node node2 = (Node)arrayList.get(i2);
            element.removeChild(node2);
            this.body().prependChild(new TextNode(" ", ""));
            this.body().prependChild(node2);
        }
    }

    public Element body() {
        return this.findFirstElementByTagName("body", this);
    }

    @Override
    public Document clone() {
        Document document = (Document)super.clone();
        document.outputSettings = this.outputSettings.clone();
        return document;
    }

    public Element createElement(String string2) {
        return new Element(Tag.valueOf(string2), this.baseUri());
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    public Element head() {
        return this.findFirstElementByTagName("head", this);
    }

    public String location() {
        return this.location;
    }

    @Override
    public String nodeName() {
        return "#document";
    }

    public Document normalise() {
        Element element;
        Element element2 = element = this.findFirstElementByTagName("html", this);
        if (element == null) {
            element2 = this.appendElement("html");
        }
        if (this.head() == null) {
            element2.prependElement("head");
        }
        if (this.body() == null) {
            element2.appendElement("body");
        }
        this.normaliseTextNodes(this.head());
        this.normaliseTextNodes(element2);
        this.normaliseTextNodes(this);
        this.normaliseStructure("head", element2);
        this.normaliseStructure("body", element2);
        return this;
    }

    @Override
    public String outerHtml() {
        return super.html();
    }

    public OutputSettings outputSettings() {
        return this.outputSettings;
    }

    public Document outputSettings(OutputSettings outputSettings) {
        Validate.notNull(outputSettings);
        this.outputSettings = outputSettings;
        return this;
    }

    public QuirksMode quirksMode() {
        return this.quirksMode;
    }

    public Document quirksMode(QuirksMode quirksMode) {
        this.quirksMode = quirksMode;
        return this;
    }

    @Override
    public Element text(String string2) {
        this.body().text(string2);
        return this;
    }

    public String title() {
        Element element = this.getElementsByTag("title").first();
        if (element != null) {
            return StringUtil.normaliseWhitespace(element.text()).trim();
        }
        return "";
    }

    public void title(String string2) {
        Validate.notNull(string2);
        Element element = this.getElementsByTag("title").first();
        if (element == null) {
            this.head().appendElement("title").text(string2);
            return;
        }
        element.text(string2);
    }

    public static class OutputSettings
    implements Cloneable {
        private Charset charset = Charset.forName("UTF-8");
        private CharsetEncoder charsetEncoder = this.charset.newEncoder();
        private Entities.EscapeMode escapeMode = Entities.EscapeMode.base;
        private int indentAmount = 1;
        private boolean outline = false;
        private boolean prettyPrint = true;

        public Charset charset() {
            return this.charset;
        }

        public OutputSettings charset(String string2) {
            this.charset(Charset.forName(string2));
            return this;
        }

        public OutputSettings charset(Charset charset) {
            this.charset = charset;
            this.charsetEncoder = charset.newEncoder();
            return this;
        }

        public OutputSettings clone() {
            OutputSettings outputSettings;
            try {
                outputSettings = (OutputSettings)super.clone();
                outputSettings.charset(this.charset.name());
            }
            catch (CloneNotSupportedException var1_2) {
                throw new RuntimeException(var1_2);
            }
            outputSettings.escapeMode = Entities.EscapeMode.valueOf(this.escapeMode.name());
            return outputSettings;
        }

        CharsetEncoder encoder() {
            return this.charsetEncoder;
        }

        public OutputSettings escapeMode(Entities.EscapeMode escapeMode) {
            this.escapeMode = escapeMode;
            return this;
        }

        public Entities.EscapeMode escapeMode() {
            return this.escapeMode;
        }

        public int indentAmount() {
            return this.indentAmount;
        }

        /*
         * Enabled aggressive block sorting
         */
        public OutputSettings indentAmount(int n2) {
            boolean bl2 = n2 >= 0;
            Validate.isTrue(bl2);
            this.indentAmount = n2;
            return this;
        }

        public OutputSettings outline(boolean bl2) {
            this.outline = bl2;
            return this;
        }

        public boolean outline() {
            return this.outline;
        }

        public OutputSettings prettyPrint(boolean bl2) {
            this.prettyPrint = bl2;
            return this;
        }

        public boolean prettyPrint() {
            return this.prettyPrint;
        }
    }

    public static enum QuirksMode {
        noQuirks,
        quirks,
        limitedQuirks;
        

        private QuirksMode() {
        }
    }

}

