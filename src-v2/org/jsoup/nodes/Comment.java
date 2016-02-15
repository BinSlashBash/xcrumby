/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.nodes;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

public class Comment
extends Node {
    private static final String COMMENT_KEY = "comment";

    public Comment(String string2, String string3) {
        super(string3);
        this.attributes.put("comment", string2);
    }

    public String getData() {
        return this.attributes.get("comment");
    }

    @Override
    public String nodeName() {
        return "#comment";
    }

    @Override
    void outerHtmlHead(StringBuilder stringBuilder, int n2, Document.OutputSettings outputSettings) {
        if (outputSettings.prettyPrint()) {
            this.indent(stringBuilder, n2, outputSettings);
        }
        stringBuilder.append("<!--").append(this.getData()).append("-->");
    }

    @Override
    void outerHtmlTail(StringBuilder stringBuilder, int n2, Document.OutputSettings outputSettings) {
    }

    @Override
    public String toString() {
        return this.outerHtml();
    }
}

