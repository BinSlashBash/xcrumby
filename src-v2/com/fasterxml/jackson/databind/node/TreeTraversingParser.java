/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.NodeCursor;
import com.fasterxml.jackson.databind.node.POJONode;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TreeTraversingParser
extends ParserMinimalBase {
    protected boolean _closed;
    protected JsonToken _nextToken;
    protected NodeCursor _nodeCursor;
    protected ObjectCodec _objectCodec;
    protected boolean _startContainer;

    public TreeTraversingParser(JsonNode jsonNode) {
        this(jsonNode, null);
    }

    public TreeTraversingParser(JsonNode jsonNode, ObjectCodec objectCodec) {
        super(0);
        this._objectCodec = objectCodec;
        if (jsonNode.isArray()) {
            this._nextToken = JsonToken.START_ARRAY;
            this._nodeCursor = new NodeCursor.Array(jsonNode, null);
            return;
        }
        if (jsonNode.isObject()) {
            this._nextToken = JsonToken.START_OBJECT;
            this._nodeCursor = new NodeCursor.Object(jsonNode, null);
            return;
        }
        this._nodeCursor = new NodeCursor.RootValue(jsonNode, null);
    }

    @Override
    protected void _handleEOF() throws JsonParseException {
        this._throwInternal();
    }

    @Override
    public void close() throws IOException {
        if (!this._closed) {
            this._closed = true;
            this._nodeCursor = null;
            this._currToken = null;
        }
    }

    protected JsonNode currentNode() {
        if (this._closed || this._nodeCursor == null) {
            return null;
        }
        return this._nodeCursor.currentNode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JsonNode currentNumericNode() throws JsonParseException {
        Object object = this.currentNode();
        if (object != null && object.isNumber()) return object;
        if (object == null) {
            object = null;
            do {
                throw this._constructError("Current token (" + object + ") not numeric, can not use numeric value accessors");
                break;
            } while (true);
        }
        object = object.asToken();
        throw this._constructError("Current token (" + object + ") not numeric, can not use numeric value accessors");
    }

    @Override
    public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
        return this.currentNumericNode().bigIntegerValue();
    }

    @Override
    public byte[] getBinaryValue(Base64Variant object) throws IOException, JsonParseException {
        object = this.currentNode();
        if (object != null) {
            byte[] arrby = object.binaryValue();
            if (arrby != null) {
                return arrby;
            }
            if (object.isPojo() && (object = ((POJONode)object).getPojo()) instanceof byte[]) {
                return (byte[])object;
            }
        }
        return null;
    }

    @Override
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override
    public JsonLocation getCurrentLocation() {
        return JsonLocation.NA;
    }

    @Override
    public String getCurrentName() {
        if (this._nodeCursor == null) {
            return null;
        }
        return this._nodeCursor.getCurrentName();
    }

    @Override
    public BigDecimal getDecimalValue() throws IOException, JsonParseException {
        return this.currentNumericNode().decimalValue();
    }

    @Override
    public double getDoubleValue() throws IOException, JsonParseException {
        return this.currentNumericNode().doubleValue();
    }

    @Override
    public Object getEmbeddedObject() {
        JsonNode jsonNode;
        if (!this._closed && (jsonNode = this.currentNode()) != null) {
            if (jsonNode.isPojo()) {
                return ((POJONode)jsonNode).getPojo();
            }
            if (jsonNode.isBinary()) {
                return ((BinaryNode)jsonNode).binaryValue();
            }
        }
        return null;
    }

    @Override
    public float getFloatValue() throws IOException, JsonParseException {
        return (float)this.currentNumericNode().doubleValue();
    }

    @Override
    public int getIntValue() throws IOException, JsonParseException {
        return this.currentNumericNode().intValue();
    }

    @Override
    public long getLongValue() throws IOException, JsonParseException {
        return this.currentNumericNode().longValue();
    }

    @Override
    public JsonParser.NumberType getNumberType() throws IOException, JsonParseException {
        JsonNode jsonNode = this.currentNumericNode();
        if (jsonNode == null) {
            return null;
        }
        return jsonNode.numberType();
    }

    @Override
    public Number getNumberValue() throws IOException, JsonParseException {
        return this.currentNumericNode().numberValue();
    }

    @Override
    public JsonStreamContext getParsingContext() {
        return this._nodeCursor;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public String getText() {
        if (this._closed) {
            return null;
        }
        switch (.$SwitchMap$com$fasterxml$jackson$core$JsonToken[this._currToken.ordinal()]) {
            default: {
                break;
            }
            case 1: {
                return this._nodeCursor.getCurrentName();
            }
            case 2: {
                return this.currentNode().textValue();
            }
            case 3: 
            case 4: {
                return String.valueOf(this.currentNode().numberValue());
            }
            case 5: {
                JsonNode jsonNode = this.currentNode();
                if (jsonNode == null || !jsonNode.isBinary()) break;
                return jsonNode.asText();
            }
        }
        if (this._currToken == null) return null;
        return this._currToken.asString();
    }

    @Override
    public char[] getTextCharacters() throws IOException, JsonParseException {
        return this.getText().toCharArray();
    }

    @Override
    public int getTextLength() throws IOException, JsonParseException {
        return this.getText().length();
    }

    @Override
    public int getTextOffset() throws IOException, JsonParseException {
        return 0;
    }

    @Override
    public JsonLocation getTokenLocation() {
        return JsonLocation.NA;
    }

    @Override
    public boolean hasTextCharacters() {
        return false;
    }

    @Override
    public boolean isClosed() {
        return this._closed;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonToken nextToken() throws IOException, JsonParseException {
        if (this._nextToken != null) {
            this._currToken = this._nextToken;
            this._nextToken = null;
            return this._currToken;
        }
        if (this._startContainer) {
            this._startContainer = false;
            if (!this._nodeCursor.currentHasChildren()) {
                JsonToken jsonToken = this._currToken == JsonToken.START_OBJECT ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
                this._currToken = jsonToken;
                return this._currToken;
            }
            this._nodeCursor = this._nodeCursor.iterateChildren();
            this._currToken = this._nodeCursor.nextToken();
            if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
                this._startContainer = true;
            }
            return this._currToken;
        }
        if (this._nodeCursor == null) {
            this._closed = true;
            return null;
        }
        this._currToken = this._nodeCursor.nextToken();
        if (this._currToken == null) {
            this._currToken = this._nodeCursor.endToken();
            this._nodeCursor = this._nodeCursor.getParent();
            return this._currToken;
        }
        if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
            this._startContainer = true;
        }
        return this._currToken;
    }

    @Override
    public void overrideCurrentName(String string2) {
        if (this._nodeCursor != null) {
            this._nodeCursor.overrideCurrentName(string2);
        }
    }

    @Override
    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException, JsonParseException {
        int n2 = 0;
        if ((base64Variant = (Base64Variant)this.getBinaryValue(base64Variant)) != null) {
            outputStream.write((byte[])base64Variant, 0, base64Variant.length);
            n2 = base64Variant.length;
        }
        return n2;
    }

    @Override
    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonParser skipChildren() throws IOException, JsonParseException {
        if (this._currToken == JsonToken.START_OBJECT) {
            this._startContainer = false;
            this._currToken = JsonToken.END_OBJECT;
            return this;
        } else {
            if (this._currToken != JsonToken.START_ARRAY) return this;
            {
                this._startContainer = false;
                this._currToken = JsonToken.END_ARRAY;
                return this;
            }
        }
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

}

