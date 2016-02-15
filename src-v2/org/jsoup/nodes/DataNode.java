/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.nodes;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Node;

public class DataNode
extends Node {
    private static final String DATA_KEY = "data";

    public DataNode(String string2, String string3) {
        super(string3);
        this.attributes.put("data", string2);
    }

    public static DataNode createFromEncoded(String string2, String string3) {
        return new DataNode(Entities.unescape(string2), string3);
    }

    public String getWholeData() {
        return this.attributes.get("data");
    }

    @Override
    public String nodeName() {
        return "#data";
    }

    @Override
    void outerHtmlHead(StringBuilder stringBuilder, int n2, Document.OutputSettings outputSettings) {
        stringBuilder.append(this.getWholeData());
    }

    @Override
    void outerHtmlTail(StringBuilder stringBuilder, int n2, Document.OutputSettings outputSettings) {
    }

    public DataNode setWholeData(String string2) {
        this.attributes.put("data", string2);
        return this;
    }

    @Override
    public String toString() {
        return this.outerHtml();
    }
}

