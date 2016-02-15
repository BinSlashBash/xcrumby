package org.jsoup.nodes;

import org.jsoup.nodes.Document.OutputSettings;

public class XmlDeclaration extends Node {
    private static final String DECL_KEY = "declaration";
    private final boolean isProcessingInstruction;

    public XmlDeclaration(String data, String baseUri, boolean isProcessingInstruction) {
        super(baseUri);
        this.attributes.put(DECL_KEY, data);
        this.isProcessingInstruction = isProcessingInstruction;
    }

    public String nodeName() {
        return "#declaration";
    }

    public String getWholeDeclaration() {
        return this.attributes.get(DECL_KEY);
    }

    void outerHtmlHead(StringBuilder accum, int depth, OutputSettings out) {
        accum.append("<").append(this.isProcessingInstruction ? "!" : "?").append(getWholeDeclaration()).append(">");
    }

    void outerHtmlTail(StringBuilder accum, int depth, OutputSettings out) {
    }

    public String toString() {
        return outerHtml();
    }
}
