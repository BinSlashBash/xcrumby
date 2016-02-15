/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonGeneratorDelegate
extends JsonGenerator {
    protected JsonGenerator delegate;
    protected boolean delegateCopyMethods;

    public JsonGeneratorDelegate(JsonGenerator jsonGenerator) {
        this(jsonGenerator, true);
    }

    public JsonGeneratorDelegate(JsonGenerator jsonGenerator, boolean bl2) {
        this.delegate = jsonGenerator;
        this.delegateCopyMethods = bl2;
    }

    @Override
    public boolean canOmitFields() {
        return this.delegate.canOmitFields();
    }

    @Override
    public boolean canUseSchema(FormatSchema formatSchema) {
        return this.delegate.canUseSchema(formatSchema);
    }

    @Override
    public boolean canWriteBinaryNatively() {
        return this.delegate.canWriteBinaryNatively();
    }

    @Override
    public boolean canWriteObjectId() {
        return this.delegate.canWriteObjectId();
    }

    @Override
    public boolean canWriteTypeId() {
        return this.delegate.canWriteTypeId();
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }

    @Override
    public void copyCurrentEvent(JsonParser jsonParser) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.copyCurrentEvent(jsonParser);
            return;
        }
        super.copyCurrentEvent(jsonParser);
    }

    @Override
    public void copyCurrentStructure(JsonParser jsonParser) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.copyCurrentStructure(jsonParser);
            return;
        }
        super.copyCurrentStructure(jsonParser);
    }

    @Override
    public JsonGenerator disable(JsonGenerator.Feature feature) {
        this.delegate.disable(feature);
        return this;
    }

    @Override
    public JsonGenerator enable(JsonGenerator.Feature feature) {
        this.delegate.enable(feature);
        return this;
    }

    @Override
    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override
    public CharacterEscapes getCharacterEscapes() {
        return this.delegate.getCharacterEscapes();
    }

    @Override
    public ObjectCodec getCodec() {
        return this.delegate.getCodec();
    }

    public JsonGenerator getDelegate() {
        return this.delegate;
    }

    @Override
    public int getFeatureMask() {
        return this.delegate.getFeatureMask();
    }

    @Override
    public int getHighestEscapedChar() {
        return this.delegate.getHighestEscapedChar();
    }

    @Override
    public JsonStreamContext getOutputContext() {
        return this.delegate.getOutputContext();
    }

    @Override
    public Object getOutputTarget() {
        return this.delegate.getOutputTarget();
    }

    @Override
    public PrettyPrinter getPrettyPrinter() {
        return this.delegate.getPrettyPrinter();
    }

    @Override
    public FormatSchema getSchema() {
        return this.delegate.getSchema();
    }

    @Override
    public boolean isClosed() {
        return this.delegate.isClosed();
    }

    @Override
    public boolean isEnabled(JsonGenerator.Feature feature) {
        return this.delegate.isEnabled(feature);
    }

    @Override
    public JsonGenerator setCharacterEscapes(CharacterEscapes characterEscapes) {
        this.delegate.setCharacterEscapes(characterEscapes);
        return this;
    }

    @Override
    public JsonGenerator setCodec(ObjectCodec objectCodec) {
        this.delegate.setCodec(objectCodec);
        return this;
    }

    @Override
    public JsonGenerator setFeatureMask(int n2) {
        this.delegate.setFeatureMask(n2);
        return this;
    }

    @Override
    public JsonGenerator setHighestNonEscapedChar(int n2) {
        this.delegate.setHighestNonEscapedChar(n2);
        return this;
    }

    @Override
    public JsonGenerator setPrettyPrinter(PrettyPrinter prettyPrinter) {
        this.delegate.setPrettyPrinter(prettyPrinter);
        return this;
    }

    @Override
    public JsonGenerator setRootValueSeparator(SerializableString serializableString) {
        this.delegate.setRootValueSeparator(serializableString);
        return this;
    }

    @Override
    public void setSchema(FormatSchema formatSchema) {
        this.delegate.setSchema(formatSchema);
    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        this.delegate.useDefaultPrettyPrinter();
        return this;
    }

    @Override
    public Version version() {
        return this.delegate.version();
    }

    @Override
    public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int n2) throws IOException {
        return this.delegate.writeBinary(base64Variant, inputStream, n2);
    }

    @Override
    public void writeBinary(Base64Variant base64Variant, byte[] arrby, int n2, int n3) throws IOException {
        this.delegate.writeBinary(base64Variant, arrby, n2, n3);
    }

    @Override
    public void writeBoolean(boolean bl2) throws IOException {
        this.delegate.writeBoolean(bl2);
    }

    @Override
    public void writeEndArray() throws IOException {
        this.delegate.writeEndArray();
    }

    @Override
    public void writeEndObject() throws IOException {
        this.delegate.writeEndObject();
    }

    @Override
    public void writeFieldName(SerializableString serializableString) throws IOException {
        this.delegate.writeFieldName(serializableString);
    }

    @Override
    public void writeFieldName(String string2) throws IOException {
        this.delegate.writeFieldName(string2);
    }

    @Override
    public void writeNull() throws IOException {
        this.delegate.writeNull();
    }

    @Override
    public void writeNumber(double d2) throws IOException {
        this.delegate.writeNumber(d2);
    }

    @Override
    public void writeNumber(float f2) throws IOException {
        this.delegate.writeNumber(f2);
    }

    @Override
    public void writeNumber(int n2) throws IOException {
        this.delegate.writeNumber(n2);
    }

    @Override
    public void writeNumber(long l2) throws IOException {
        this.delegate.writeNumber(l2);
    }

    @Override
    public void writeNumber(String string2) throws IOException, UnsupportedOperationException {
        this.delegate.writeNumber(string2);
    }

    @Override
    public void writeNumber(BigDecimal bigDecimal) throws IOException {
        this.delegate.writeNumber(bigDecimal);
    }

    @Override
    public void writeNumber(BigInteger bigInteger) throws IOException {
        this.delegate.writeNumber(bigInteger);
    }

    @Override
    public void writeNumber(short s2) throws IOException {
        this.delegate.writeNumber(s2);
    }

    @Override
    public void writeObject(Object object) throws IOException, JsonProcessingException {
        if (this.delegateCopyMethods) {
            this.delegate.writeObject(object);
            return;
        }
        if (object == null) {
            this.writeNull();
            return;
        }
        if (this.getCodec() != null) {
            this.getCodec().writeValue(this, object);
            return;
        }
        this._writeSimpleObject(object);
    }

    @Override
    public void writeObjectId(Object object) throws IOException {
        this.delegate.writeObjectId(object);
    }

    @Override
    public void writeObjectRef(Object object) throws IOException {
        this.delegate.writeObjectRef(object);
    }

    @Override
    public void writeOmittedField(String string2) throws IOException {
        this.delegate.writeOmittedField(string2);
    }

    @Override
    public void writeRaw(char c2) throws IOException {
        this.delegate.writeRaw(c2);
    }

    @Override
    public void writeRaw(SerializableString serializableString) throws IOException {
        this.delegate.writeRaw(serializableString);
    }

    @Override
    public void writeRaw(String string2) throws IOException {
        this.delegate.writeRaw(string2);
    }

    @Override
    public void writeRaw(String string2, int n2, int n3) throws IOException {
        this.delegate.writeRaw(string2, n2, n3);
    }

    @Override
    public void writeRaw(char[] arrc, int n2, int n3) throws IOException {
        this.delegate.writeRaw(arrc, n2, n3);
    }

    @Override
    public void writeRawUTF8String(byte[] arrby, int n2, int n3) throws IOException {
        this.delegate.writeRawUTF8String(arrby, n2, n3);
    }

    @Override
    public void writeRawValue(String string2) throws IOException {
        this.delegate.writeRawValue(string2);
    }

    @Override
    public void writeRawValue(String string2, int n2, int n3) throws IOException {
        this.delegate.writeRawValue(string2, n2, n3);
    }

    @Override
    public void writeRawValue(char[] arrc, int n2, int n3) throws IOException {
        this.delegate.writeRawValue(arrc, n2, n3);
    }

    @Override
    public void writeStartArray() throws IOException {
        this.delegate.writeStartArray();
    }

    @Override
    public void writeStartArray(int n2) throws IOException {
        this.delegate.writeStartArray(n2);
    }

    @Override
    public void writeStartObject() throws IOException {
        this.delegate.writeStartObject();
    }

    @Override
    public void writeString(SerializableString serializableString) throws IOException {
        this.delegate.writeString(serializableString);
    }

    @Override
    public void writeString(String string2) throws IOException {
        this.delegate.writeString(string2);
    }

    @Override
    public void writeString(char[] arrc, int n2, int n3) throws IOException {
        this.delegate.writeString(arrc, n2, n3);
    }

    @Override
    public void writeTree(TreeNode treeNode) throws IOException {
        if (this.delegateCopyMethods) {
            this.delegate.writeTree(treeNode);
            return;
        }
        if (treeNode == null) {
            this.writeNull();
            return;
        }
        if (this.getCodec() == null) {
            throw new IllegalStateException("No ObjectCodec defined");
        }
        this.getCodec().writeValue(this, treeNode);
    }

    @Override
    public void writeTypeId(Object object) throws IOException {
        this.delegate.writeTypeId(object);
    }

    @Override
    public void writeUTF8String(byte[] arrby, int n2, int n3) throws IOException {
        this.delegate.writeUTF8String(arrby, n2, n3);
    }
}

