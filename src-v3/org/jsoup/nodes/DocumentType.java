package org.jsoup.nodes;

import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document.OutputSettings;

public class DocumentType extends Node {
    public DocumentType(String name, String publicId, String systemId, String baseUri) {
        super(baseUri);
        Validate.notEmpty(name);
        attr("name", name);
        attr("publicId", publicId);
        attr("systemId", systemId);
    }

    public String nodeName() {
        return "#doctype";
    }

    void outerHtmlHead(StringBuilder accum, int depth, OutputSettings out) {
        accum.append("<!DOCTYPE ").append(attr("name"));
        if (!StringUtil.isBlank(attr("publicId"))) {
            accum.append(" PUBLIC \"").append(attr("publicId")).append("\"");
        }
        if (!StringUtil.isBlank(attr("systemId"))) {
            accum.append(" \"").append(attr("systemId")).append("\"");
        }
        accum.append('>');
    }

    void outerHtmlTail(StringBuilder accum, int depth, OutputSettings out) {
    }
}
