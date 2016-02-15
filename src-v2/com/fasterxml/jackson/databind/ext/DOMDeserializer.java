/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import java.io.Reader;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public abstract class DOMDeserializer<T>
extends FromStringDeserializer<T> {
    private static final DocumentBuilderFactory _parserFactory = DocumentBuilderFactory.newInstance();
    private static final long serialVersionUID = 1;

    static {
        _parserFactory.setNamespaceAware(true);
    }

    protected DOMDeserializer(Class<T> class_) {
        super(class_);
    }

    @Override
    public abstract T _deserialize(String var1, DeserializationContext var2);

    protected final Document parse(String object) throws IllegalArgumentException {
        try {
            object = _parserFactory.newDocumentBuilder().parse(new InputSource(new StringReader((String)object)));
            return object;
        }
        catch (Exception var1_2) {
            throw new IllegalArgumentException("Failed to parse JSON String as XML: " + var1_2.getMessage(), var1_2);
        }
    }

    public static class DocumentDeserializer
    extends DOMDeserializer<Document> {
        private static final long serialVersionUID = 1;

        public DocumentDeserializer() {
            super(Document.class);
        }

        @Override
        public Document _deserialize(String string2, DeserializationContext deserializationContext) throws IllegalArgumentException {
            return this.parse(string2);
        }
    }

    public static class NodeDeserializer
    extends DOMDeserializer<Node> {
        private static final long serialVersionUID = 1;

        public NodeDeserializer() {
            super(Node.class);
        }

        @Override
        public Node _deserialize(String string2, DeserializationContext deserializationContext) throws IllegalArgumentException {
            return this.parse(string2);
        }
    }

}

