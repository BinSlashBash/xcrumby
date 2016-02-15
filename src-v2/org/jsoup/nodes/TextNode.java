/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.nodes;

import java.util.List;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;

public class TextNode
extends Node {
    private static final String TEXT_KEY = "text";
    String text;

    public TextNode(String string2, String string3) {
        this.baseUri = string3;
        this.text = string2;
    }

    public static TextNode createFromEncoded(String string2, String string3) {
        return new TextNode(Entities.unescape(string2), string3);
    }

    private void ensureAttributes() {
        if (this.attributes == null) {
            this.attributes = new Attributes();
            this.attributes.put("text", this.text);
        }
    }

    static boolean lastCharIsWhitespace(StringBuilder stringBuilder) {
        if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ' ') {
            return true;
        }
        return false;
    }

    static String normaliseWhitespace(String string2) {
        return StringUtil.normaliseWhitespace(string2);
    }

    static String stripLeadingWhitespace(String string2) {
        return string2.replaceFirst("^\\s+", "");
    }

    @Override
    public String absUrl(String string2) {
        this.ensureAttributes();
        return super.absUrl(string2);
    }

    @Override
    public String attr(String string2) {
        this.ensureAttributes();
        return super.attr(string2);
    }

    @Override
    public Node attr(String string2, String string3) {
        this.ensureAttributes();
        return super.attr(string2, string3);
    }

    @Override
    public Attributes attributes() {
        this.ensureAttributes();
        return super.attributes();
    }

    public String getWholeText() {
        if (this.attributes == null) {
            return this.text;
        }
        return this.attributes.get("text");
    }

    @Override
    public boolean hasAttr(String string2) {
        this.ensureAttributes();
        return super.hasAttr(string2);
    }

    public boolean isBlank() {
        return StringUtil.isBlank(this.getWholeText());
    }

    @Override
    public String nodeName() {
        return "#text";
    }

    @Override
    void outerHtmlHead(StringBuilder stringBuilder, int n2, Document.OutputSettings outputSettings) {
        String string2;
        String string3 = string2 = Entities.escape(this.getWholeText(), outputSettings);
        if (outputSettings.prettyPrint()) {
            string3 = string2;
            if (this.parent() instanceof Element) {
                string3 = string2;
                if (!Element.preserveWhitespace((Element)this.parent())) {
                    string3 = TextNode.normaliseWhitespace(string2);
                }
            }
        }
        if (outputSettings.prettyPrint() && (this.siblingIndex() == 0 && this.parentNode instanceof Element && ((Element)this.parentNode).tag().formatAsBlock() && !this.isBlank() || outputSettings.outline() && this.siblingNodes().size() > 0 && !this.isBlank())) {
            this.indent(stringBuilder, n2, outputSettings);
        }
        stringBuilder.append(string3);
    }

    @Override
    void outerHtmlTail(StringBuilder stringBuilder, int n2, Document.OutputSettings outputSettings) {
    }

    @Override
    public Node removeAttr(String string2) {
        this.ensureAttributes();
        return super.removeAttr(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public TextNode splitText(int n2) {
        boolean bl2 = n2 >= 0;
        Validate.isTrue(bl2, "Split offset must be not be negative");
        bl2 = n2 < this.text.length();
        Validate.isTrue(bl2, "Split offset must not be greater than current text length");
        Object object = this.getWholeText().substring(0, n2);
        String string2 = this.getWholeText().substring(n2);
        this.text((String)object);
        object = new TextNode(string2, this.baseUri());
        if (this.parent() != null) {
            this.parent().addChildren(this.siblingIndex() + 1, new Node[]{object});
        }
        return object;
    }

    public String text() {
        return TextNode.normaliseWhitespace(this.getWholeText());
    }

    public TextNode text(String string2) {
        this.text = string2;
        if (this.attributes != null) {
            this.attributes.put("text", string2);
        }
        return this;
    }

    @Override
    public String toString() {
        return this.outerHtml();
    }
}

