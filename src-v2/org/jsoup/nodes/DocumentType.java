/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.nodes;

import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

public class DocumentType
extends Node {
    public DocumentType(String string2, String string3, String string4, String string5) {
        super(string5);
        Validate.notEmpty(string2);
        this.attr("name", string2);
        this.attr("publicId", string3);
        this.attr("systemId", string4);
    }

    @Override
    public String nodeName() {
        return "#doctype";
    }

    @Override
    void outerHtmlHead(StringBuilder stringBuilder, int n2, Document.OutputSettings outputSettings) {
        stringBuilder.append("<!DOCTYPE ").append(this.attr("name"));
        if (!StringUtil.isBlank(this.attr("publicId"))) {
            stringBuilder.append(" PUBLIC \"").append(this.attr("publicId")).append("\"");
        }
        if (!StringUtil.isBlank(this.attr("systemId"))) {
            stringBuilder.append(" \"").append(this.attr("systemId")).append("\"");
        }
        stringBuilder.append('>');
    }

    @Override
    void outerHtmlTail(StringBuilder stringBuilder, int n2, Document.OutputSettings outputSettings) {
    }
}

