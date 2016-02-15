/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.nodes;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

public class XmlDeclaration
extends Node {
    private static final String DECL_KEY = "declaration";
    private final boolean isProcessingInstruction;

    public XmlDeclaration(String string2, String string3, boolean bl2) {
        super(string3);
        this.attributes.put("declaration", string2);
        this.isProcessingInstruction = bl2;
    }

    public String getWholeDeclaration() {
        return this.attributes.get("declaration");
    }

    @Override
    public String nodeName() {
        return "#declaration";
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void outerHtmlHead(StringBuilder stringBuilder, int n2, Document.OutputSettings object) {
        void var1_3;
        StringBuilder stringBuilder2 = stringBuilder.append("<");
        if (this.isProcessingInstruction) {
            String string2 = "!";
        } else {
            String string3 = "?";
        }
        stringBuilder2.append((String)var1_3).append(this.getWholeDeclaration()).append(">");
    }

    @Override
    void outerHtmlTail(StringBuilder stringBuilder, int n2, Document.OutputSettings outputSettings) {
    }

    @Override
    public String toString() {
        return this.outerHtml();
    }
}

