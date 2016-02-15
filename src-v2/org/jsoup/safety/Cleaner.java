/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.safety;

import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Tag;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class Cleaner {
    private Whitelist whitelist;

    public Cleaner(Whitelist whitelist) {
        Validate.notNull(whitelist);
        this.whitelist = whitelist;
    }

    private int copySafeNodes(Element element, Element object) {
        object = new CleaningVisitor(element, (Element)object);
        new NodeTraversor((NodeVisitor)object).traverse(element);
        return ((CleaningVisitor)object).numDiscarded;
    }

    private ElementMeta createSafeElement(Element element) {
        String string2 = element.tagName();
        Attributes attributes = new Attributes();
        Element element2 = new Element(Tag.valueOf(string2), element.baseUri(), attributes);
        int n2 = 0;
        for (Attribute attribute : element.attributes()) {
            if (this.whitelist.isSafeAttribute(string2, element, attribute)) {
                attributes.put(attribute);
                continue;
            }
            ++n2;
        }
        attributes.addAll(this.whitelist.getEnforcedAttributes(string2));
        return new ElementMeta(element2, n2);
    }

    public Document clean(Document document) {
        Validate.notNull(document);
        Document document2 = Document.createShell(document.baseUri());
        if (document.body() != null) {
            this.copySafeNodes(document.body(), document2.body());
        }
        return document2;
    }

    public boolean isValid(Document document) {
        Validate.notNull(document);
        Document document2 = Document.createShell(document.baseUri());
        if (this.copySafeNodes(document.body(), document2.body()) == 0) {
            return true;
        }
        return false;
    }

    private final class CleaningVisitor
    implements NodeVisitor {
        private Element destination;
        private int numDiscarded;
        private final Element root;

        private CleaningVisitor(Element element, Element element2) {
            this.numDiscarded = 0;
            this.root = element;
            this.destination = element2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void head(Node object, int n2) {
            if (object instanceof Element) {
                Element element = (Element)object;
                if (Cleaner.this.whitelist.isSafeTag(element.tagName())) {
                    object = Cleaner.this.createSafeElement(element);
                    element = object.el;
                    this.destination.appendChild(element);
                    this.numDiscarded += object.numAttribsDiscarded;
                    this.destination = element;
                    return;
                } else {
                    if (object == this.root) return;
                    {
                        ++this.numDiscarded;
                        return;
                    }
                }
            }
            if (object instanceof TextNode) {
                object = new TextNode(((TextNode)object).getWholeText(), object.baseUri());
                this.destination.appendChild((Node)object);
                return;
            }
            ++this.numDiscarded;
        }

        @Override
        public void tail(Node node, int n2) {
            if (node instanceof Element && Cleaner.this.whitelist.isSafeTag(node.nodeName())) {
                this.destination = this.destination.parent();
            }
        }
    }

    private static class ElementMeta {
        Element el;
        int numAttribsDiscarded;

        ElementMeta(Element element, int n2) {
            this.el = element;
            this.numAttribsDiscarded = n2;
        }
    }

}

