/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.DupDetector;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;
import java.io.InputStream;

public abstract class GeneratorBase
extends JsonGenerator {
    protected boolean _cfgNumbersAsStrings;
    protected boolean _closed;
    protected int _features;
    protected ObjectCodec _objectCodec;
    protected JsonWriteContext _writeContext;

    /*
     * Enabled aggressive block sorting
     */
    protected GeneratorBase(int n2, ObjectCodec objectCodec) {
        this._features = n2;
        DupDetector dupDetector = JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION.enabledIn(n2) ? DupDetector.rootDetector(this) : null;
        this._writeContext = JsonWriteContext.createRootContext(dupDetector);
        this._objectCodec = objectCodec;
        this._cfgNumbersAsStrings = JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS.enabledIn(n2);
    }

    protected abstract void _releaseBuffers();

    protected abstract void _verifyValueWrite(String var1) throws IOException;

    @Override
    public void close() throws IOException {
        this._closed = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonGenerator disable(JsonGenerator.Feature feature) {
        this._features &= ~ feature.getMask();
        if (feature == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
            this._cfgNumbersAsStrings = false;
            return this;
        } else {
            if (feature != JsonGenerator.Feature.ESCAPE_NON_ASCII) return this;
            {
                this.setHighestNonEscapedChar(0);
                return this;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonGenerator enable(JsonGenerator.Feature feature) {
        this._features |= feature.getMask();
        if (feature == JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS) {
            this._cfgNumbersAsStrings = true;
            return this;
        } else {
            if (feature != JsonGenerator.Feature.ESCAPE_NON_ASCII) return this;
            {
                this.setHighestNonEscapedChar(127);
                return this;
            }
        }
    }

    @Override
    public abstract void flush() throws IOException;

    @Override
    public final ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override
    public int getFeatureMask() {
        return this._features;
    }

    @Override
    public final JsonWriteContext getOutputContext() {
        return this._writeContext;
    }

    @Override
    public boolean isClosed() {
        return this._closed;
    }

    @Override
    public final boolean isEnabled(JsonGenerator.Feature feature) {
        if ((this._features & feature.getMask()) != 0) {
            return true;
        }
        return false;
    }

    @Override
    public JsonGenerator setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
        return this;
    }

    @Override
    public JsonGenerator setFeatureMask(int n2) {
        this._features = n2;
        return this;
    }

    @Override
    public JsonGenerator useDefaultPrettyPrinter() {
        if (this.getPrettyPrinter() != null) {
            return this;
        }
        return this.setPrettyPrinter(new DefaultPrettyPrinter());
    }

    @Override
    public Version version() {
        return VersionUtil.versionFor(this.getClass());
    }

    @Override
    public int writeBinary(Base64Variant base64Variant, InputStream inputStream, int n2) throws IOException {
        this._reportUnsupportedOperation();
        return 0;
    }

    @Override
    public void writeFieldName(SerializableString serializableString) throws IOException {
        this.writeFieldName(serializableString.getValue());
    }

    @Override
    public void writeObject(Object object) throws IOException {
        if (object == null) {
            this.writeNull();
            return;
        }
        if (this._objectCodec != null) {
            this._objectCodec.writeValue(this, object);
            return;
        }
        this._writeSimpleObject(object);
    }

    @Override
    public void writeRawValue(String string2) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(string2);
    }

    @Override
    public void writeRawValue(String string2, int n2, int n3) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(string2, n2, n3);
    }

    @Override
    public void writeRawValue(char[] arrc, int n2, int n3) throws IOException {
        this._verifyValueWrite("write raw value");
        this.writeRaw(arrc, n2, n3);
    }

    @Override
    public void writeString(SerializableString serializableString) throws IOException {
        this.writeString(serializableString.getValue());
    }

    @Override
    public void writeTree(TreeNode treeNode) throws IOException {
        if (treeNode == null) {
            this.writeNull();
            return;
        }
        if (this._objectCodec == null) {
            throw new IllegalStateException("No ObjectCodec defined");
        }
        this._objectCodec.writeValue(this, treeNode);
    }
}

