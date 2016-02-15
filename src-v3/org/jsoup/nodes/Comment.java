package org.jsoup.nodes;

import org.jsoup.nodes.Document.OutputSettings;

public class Comment extends Node {
    private static final String COMMENT_KEY = "comment";

    public Comment(String data, String baseUri) {
        super(baseUri);
        this.attributes.put(COMMENT_KEY, data);
    }

    public String nodeName() {
        return "#comment";
    }

    public String getData() {
        return this.attributes.get(COMMENT_KEY);
    }

    void outerHtmlHead(StringBuilder accum, int depth, OutputSettings out) {
        if (out.prettyPrint()) {
            indent(accum, depth, out);
        }
        accum.append("<!--").append(getData()).append("-->");
    }

    void outerHtmlTail(StringBuilder accum, int depth, OutputSettings out) {
    }

    public String toString() {
        return outerHtml();
    }
}
