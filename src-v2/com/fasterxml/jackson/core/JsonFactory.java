/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.InputDecorator;
import com.fasterxml.jackson.core.io.OutputDecorator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.io.UTF8Writer;
import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.core.json.ReaderBasedJsonParser;
import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
import com.fasterxml.jackson.core.sym.BytesToNameCanonicalizer;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import java.io.CharArrayReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.net.URL;

public class JsonFactory
implements Versioned,
Serializable {
    protected static final int DEFAULT_FACTORY_FEATURE_FLAGS = Feature.collectDefaults();
    protected static final int DEFAULT_GENERATOR_FEATURE_FLAGS;
    protected static final int DEFAULT_PARSER_FEATURE_FLAGS;
    private static final SerializableString DEFAULT_ROOT_VALUE_SEPARATOR;
    public static final String FORMAT_NAME_JSON = "JSON";
    protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef;
    private static final long serialVersionUID = 3306684576057132431L;
    protected CharacterEscapes _characterEscapes;
    protected int _factoryFeatures = DEFAULT_FACTORY_FEATURE_FLAGS;
    protected int _generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
    protected InputDecorator _inputDecorator;
    protected ObjectCodec _objectCodec;
    protected OutputDecorator _outputDecorator;
    protected int _parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
    protected final transient BytesToNameCanonicalizer _rootByteSymbols = BytesToNameCanonicalizer.createRoot();
    protected final transient CharsToNameCanonicalizer _rootCharSymbols = CharsToNameCanonicalizer.createRoot();
    protected SerializableString _rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;

    static {
        DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
        DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
        DEFAULT_ROOT_VALUE_SEPARATOR = DefaultPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
        _recyclerRef = new ThreadLocal();
    }

    public JsonFactory() {
        this(null);
    }

    protected JsonFactory(JsonFactory jsonFactory, ObjectCodec objectCodec) {
        this._objectCodec = null;
        this._factoryFeatures = jsonFactory._factoryFeatures;
        this._parserFeatures = jsonFactory._parserFeatures;
        this._generatorFeatures = jsonFactory._generatorFeatures;
        this._characterEscapes = jsonFactory._characterEscapes;
        this._inputDecorator = jsonFactory._inputDecorator;
        this._outputDecorator = jsonFactory._outputDecorator;
        this._rootValueSeparator = jsonFactory._rootValueSeparator;
    }

    public JsonFactory(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }

    protected void _checkInvalidCopy(Class<?> class_) {
        if (this.getClass() != class_) {
            throw new IllegalStateException("Failed copy(): " + this.getClass().getName() + " (version: " + this.version() + ") does not override copy(); it has to");
        }
    }

    protected IOContext _createContext(Object object, boolean bl2) {
        return new IOContext(this._getBufferRecycler(), object, bl2);
    }

    protected JsonGenerator _createGenerator(Writer closeable, IOContext object) throws IOException {
        closeable = new WriterBasedJsonGenerator((IOContext)object, this._generatorFeatures, this._objectCodec, (Writer)closeable);
        if (this._characterEscapes != null) {
            closeable.setCharacterEscapes(this._characterEscapes);
        }
        if ((object = this._rootValueSeparator) != DEFAULT_ROOT_VALUE_SEPARATOR) {
            closeable.setRootValueSeparator((SerializableString)object);
        }
        return closeable;
    }

    protected JsonParser _createParser(InputStream inputStream, IOContext iOContext) throws IOException {
        return new ByteSourceJsonBootstrapper(iOContext, inputStream).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols, this._factoryFeatures);
    }

    protected JsonParser _createParser(Reader reader, IOContext iOContext) throws IOException {
        return new ReaderBasedJsonParser(iOContext, this._parserFeatures, reader, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures));
    }

    protected JsonParser _createParser(byte[] arrby, int n2, int n3, IOContext iOContext) throws IOException {
        return new ByteSourceJsonBootstrapper(iOContext, arrby, n2, n3).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols, this._factoryFeatures);
    }

    protected JsonParser _createParser(char[] arrc, int n2, int n3, IOContext iOContext, boolean bl2) throws IOException {
        return new ReaderBasedJsonParser(iOContext, this._parserFeatures, null, this._objectCodec, this._rootCharSymbols.makeChild(this._factoryFeatures), arrc, n2, n2 + n3, bl2);
    }

    protected JsonGenerator _createUTF8Generator(OutputStream closeable, IOContext object) throws IOException {
        closeable = new UTF8JsonGenerator((IOContext)object, this._generatorFeatures, this._objectCodec, (OutputStream)closeable);
        if (this._characterEscapes != null) {
            closeable.setCharacterEscapes(this._characterEscapes);
        }
        if ((object = this._rootValueSeparator) != DEFAULT_ROOT_VALUE_SEPARATOR) {
            closeable.setRootValueSeparator((SerializableString)object);
        }
        return closeable;
    }

    protected Writer _createWriter(OutputStream outputStream, JsonEncoding jsonEncoding, IOContext iOContext) throws IOException {
        if (jsonEncoding == JsonEncoding.UTF8) {
            return new UTF8Writer(iOContext, outputStream);
        }
        return new OutputStreamWriter(outputStream, jsonEncoding.getJavaName());
    }

    protected final InputStream _decorate(InputStream inputStream, IOContext object) throws IOException {
        if (this._inputDecorator != null && (object = this._inputDecorator.decorate((IOContext)object, inputStream)) != null) {
            return object;
        }
        return inputStream;
    }

    protected final OutputStream _decorate(OutputStream outputStream, IOContext object) throws IOException {
        if (this._outputDecorator != null && (object = this._outputDecorator.decorate((IOContext)object, outputStream)) != null) {
            return object;
        }
        return outputStream;
    }

    protected final Reader _decorate(Reader reader, IOContext object) throws IOException {
        if (this._inputDecorator != null && (object = this._inputDecorator.decorate((IOContext)object, reader)) != null) {
            return object;
        }
        return reader;
    }

    protected final Writer _decorate(Writer writer, IOContext object) throws IOException {
        if (this._outputDecorator != null && (object = this._outputDecorator.decorate((IOContext)object, writer)) != null) {
            return object;
        }
        return writer;
    }

    /*
     * Enabled aggressive block sorting
     */
    public BufferRecycler _getBufferRecycler() {
        SoftReference<BufferRecycler> softReference = _recyclerRef.get();
        softReference = softReference == null ? null : softReference.get();
        Object object = softReference;
        if (softReference == null) {
            object = new BufferRecycler();
            _recyclerRef.set(new SoftReference<Object>(object));
        }
        return object;
    }

    protected InputStream _optimizedStreamFromURL(URL uRL) throws IOException {
        String string2;
        if ("file".equals(uRL.getProtocol()) && ((string2 = uRL.getHost()) == null || string2.length() == 0) && uRL.getPath().indexOf(37) < 0) {
            return new FileInputStream(uRL.getPath());
        }
        return uRL.openStream();
    }

    public boolean canHandleBinaryNatively() {
        return false;
    }

    public boolean canUseCharArrays() {
        return true;
    }

    public boolean canUseSchema(FormatSchema formatSchema) {
        String string2 = this.getFormatName();
        if (string2 != null && string2.equals(formatSchema.getSchemaType())) {
            return true;
        }
        return false;
    }

    public final JsonFactory configure(Feature feature, boolean bl2) {
        if (bl2) {
            return this.enable(feature);
        }
        return this.disable(feature);
    }

    public final JsonFactory configure(JsonGenerator.Feature feature, boolean bl2) {
        if (bl2) {
            return this.enable(feature);
        }
        return this.disable(feature);
    }

    public final JsonFactory configure(JsonParser.Feature feature, boolean bl2) {
        if (bl2) {
            return this.enable(feature);
        }
        return this.disable(feature);
    }

    public JsonFactory copy() {
        this._checkInvalidCopy(JsonFactory.class);
        return new JsonFactory(this, null);
    }

    public JsonGenerator createGenerator(File object, JsonEncoding jsonEncoding) throws IOException {
        object = new FileOutputStream((File)object);
        IOContext iOContext = this._createContext(object, true);
        iOContext.setEncoding(jsonEncoding);
        if (jsonEncoding == JsonEncoding.UTF8) {
            return this._createUTF8Generator(this._decorate((OutputStream)object, iOContext), iOContext);
        }
        return this._createGenerator(this._decorate(this._createWriter((OutputStream)object, jsonEncoding, iOContext), iOContext), iOContext);
    }

    public JsonGenerator createGenerator(OutputStream outputStream) throws IOException {
        return this.createGenerator(outputStream, JsonEncoding.UTF8);
    }

    public JsonGenerator createGenerator(OutputStream outputStream, JsonEncoding jsonEncoding) throws IOException {
        IOContext iOContext = this._createContext(outputStream, false);
        iOContext.setEncoding(jsonEncoding);
        if (jsonEncoding == JsonEncoding.UTF8) {
            return this._createUTF8Generator(this._decorate(outputStream, iOContext), iOContext);
        }
        return this._createGenerator(this._decorate(this._createWriter(outputStream, jsonEncoding, iOContext), iOContext), iOContext);
    }

    public JsonGenerator createGenerator(Writer writer) throws IOException {
        IOContext iOContext = this._createContext(writer, false);
        return this._createGenerator(this._decorate(writer, iOContext), iOContext);
    }

    @Deprecated
    public JsonGenerator createJsonGenerator(File file, JsonEncoding jsonEncoding) throws IOException {
        return this.createGenerator(file, jsonEncoding);
    }

    @Deprecated
    public JsonGenerator createJsonGenerator(OutputStream outputStream) throws IOException {
        return this.createGenerator(outputStream, JsonEncoding.UTF8);
    }

    @Deprecated
    public JsonGenerator createJsonGenerator(OutputStream outputStream, JsonEncoding jsonEncoding) throws IOException {
        return this.createGenerator(outputStream, jsonEncoding);
    }

    @Deprecated
    public JsonGenerator createJsonGenerator(Writer writer) throws IOException {
        return this.createGenerator(writer);
    }

    @Deprecated
    public JsonParser createJsonParser(File file) throws IOException, JsonParseException {
        return this.createParser(file);
    }

    @Deprecated
    public JsonParser createJsonParser(InputStream inputStream) throws IOException, JsonParseException {
        return this.createParser(inputStream);
    }

    @Deprecated
    public JsonParser createJsonParser(Reader reader) throws IOException, JsonParseException {
        return this.createParser(reader);
    }

    @Deprecated
    public JsonParser createJsonParser(String string2) throws IOException, JsonParseException {
        return this.createParser(string2);
    }

    @Deprecated
    public JsonParser createJsonParser(URL uRL) throws IOException, JsonParseException {
        return this.createParser(uRL);
    }

    @Deprecated
    public JsonParser createJsonParser(byte[] arrby) throws IOException, JsonParseException {
        return this.createParser(arrby);
    }

    @Deprecated
    public JsonParser createJsonParser(byte[] arrby, int n2, int n3) throws IOException, JsonParseException {
        return this.createParser(arrby, n2, n3);
    }

    public JsonParser createParser(File file) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext(file, true);
        return this._createParser(this._decorate(new FileInputStream(file), iOContext), iOContext);
    }

    public JsonParser createParser(InputStream inputStream) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext(inputStream, false);
        return this._createParser(this._decorate(inputStream, iOContext), iOContext);
    }

    public JsonParser createParser(Reader reader) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext(reader, false);
        return this._createParser(this._decorate(reader, iOContext), iOContext);
    }

    public JsonParser createParser(String string2) throws IOException, JsonParseException {
        int n2 = string2.length();
        if (this._inputDecorator != null || n2 > 32768 || !this.canUseCharArrays()) {
            return this.createParser(new StringReader(string2));
        }
        IOContext iOContext = this._createContext(string2, true);
        char[] arrc = iOContext.allocTokenBuffer(n2);
        string2.getChars(0, n2, arrc, 0);
        return this._createParser(arrc, 0, n2, iOContext, true);
    }

    public JsonParser createParser(URL uRL) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext(uRL, true);
        return this._createParser(this._decorate(this._optimizedStreamFromURL(uRL), iOContext), iOContext);
    }

    public JsonParser createParser(byte[] arrby) throws IOException, JsonParseException {
        InputStream inputStream;
        IOContext iOContext = this._createContext(arrby, true);
        if (this._inputDecorator != null && (inputStream = this._inputDecorator.decorate(iOContext, arrby, 0, arrby.length)) != null) {
            return this._createParser(inputStream, iOContext);
        }
        return this._createParser(arrby, 0, arrby.length, iOContext);
    }

    public JsonParser createParser(byte[] arrby, int n2, int n3) throws IOException, JsonParseException {
        InputStream inputStream;
        IOContext iOContext = this._createContext(arrby, true);
        if (this._inputDecorator != null && (inputStream = this._inputDecorator.decorate(iOContext, arrby, n2, n3)) != null) {
            return this._createParser(inputStream, iOContext);
        }
        return this._createParser(arrby, n2, n3, iOContext);
    }

    public JsonParser createParser(char[] arrc) throws IOException {
        return this.createParser(arrc, 0, arrc.length);
    }

    public JsonParser createParser(char[] arrc, int n2, int n3) throws IOException {
        if (this._inputDecorator != null) {
            return this.createParser(new CharArrayReader(arrc, n2, n3));
        }
        return this._createParser(arrc, n2, n3, this._createContext(arrc, true), false);
    }

    public JsonFactory disable(Feature feature) {
        this._factoryFeatures &= ~ feature.getMask();
        return this;
    }

    public JsonFactory disable(JsonGenerator.Feature feature) {
        this._generatorFeatures &= ~ feature.getMask();
        return this;
    }

    public JsonFactory disable(JsonParser.Feature feature) {
        this._parserFeatures &= ~ feature.getMask();
        return this;
    }

    public JsonFactory enable(Feature feature) {
        this._factoryFeatures |= feature.getMask();
        return this;
    }

    public JsonFactory enable(JsonGenerator.Feature feature) {
        this._generatorFeatures |= feature.getMask();
        return this;
    }

    public JsonFactory enable(JsonParser.Feature feature) {
        this._parserFeatures |= feature.getMask();
        return this;
    }

    public CharacterEscapes getCharacterEscapes() {
        return this._characterEscapes;
    }

    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    public String getFormatName() {
        if (this.getClass() == JsonFactory.class) {
            return "JSON";
        }
        return null;
    }

    public InputDecorator getInputDecorator() {
        return this._inputDecorator;
    }

    public OutputDecorator getOutputDecorator() {
        return this._outputDecorator;
    }

    public String getRootValueSeparator() {
        if (this._rootValueSeparator == null) {
            return null;
        }
        return this._rootValueSeparator.getValue();
    }

    public MatchStrength hasFormat(InputAccessor inputAccessor) throws IOException {
        if (this.getClass() == JsonFactory.class) {
            return this.hasJSONFormat(inputAccessor);
        }
        return null;
    }

    protected MatchStrength hasJSONFormat(InputAccessor inputAccessor) throws IOException {
        return ByteSourceJsonBootstrapper.hasJSONFormat(inputAccessor);
    }

    public final boolean isEnabled(Feature feature) {
        if ((this._factoryFeatures & feature.getMask()) != 0) {
            return true;
        }
        return false;
    }

    public final boolean isEnabled(JsonGenerator.Feature feature) {
        if ((this._generatorFeatures & feature.getMask()) != 0) {
            return true;
        }
        return false;
    }

    public final boolean isEnabled(JsonParser.Feature feature) {
        if ((this._parserFeatures & feature.getMask()) != 0) {
            return true;
        }
        return false;
    }

    protected Object readResolve() {
        return new JsonFactory(this, this._objectCodec);
    }

    public boolean requiresCustomCodec() {
        return false;
    }

    public boolean requiresPropertyOrdering() {
        return false;
    }

    public JsonFactory setCharacterEscapes(CharacterEscapes characterEscapes) {
        this._characterEscapes = characterEscapes;
        return this;
    }

    public JsonFactory setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
        return this;
    }

    public JsonFactory setInputDecorator(InputDecorator inputDecorator) {
        this._inputDecorator = inputDecorator;
        return this;
    }

    public JsonFactory setOutputDecorator(OutputDecorator outputDecorator) {
        this._outputDecorator = outputDecorator;
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonFactory setRootValueSeparator(String object) {
        object = object == null ? null : new SerializedString((String)object);
        this._rootValueSeparator = object;
        return this;
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    public static enum Feature {
        INTERN_FIELD_NAMES(true),
        CANONICALIZE_FIELD_NAMES(true),
        FAIL_ON_SYMBOL_HASH_OVERFLOW(true);
        
        private final boolean _defaultState;

        private Feature(boolean bl2) {
            this._defaultState = bl2;
        }

        public static int collectDefaults() {
            int n2 = 0;
            for (Feature feature : Feature.values()) {
                int n3 = n2;
                if (feature.enabledByDefault()) {
                    n3 = n2 | feature.getMask();
                }
                n2 = n3;
            }
            return n2;
        }

        public boolean enabledByDefault() {
            return this._defaultState;
        }

        public boolean enabledIn(int n2) {
            if ((this.getMask() & n2) != 0) {
                return true;
            }
            return false;
        }

        public int getMask() {
            return 1 << this.ordinal();
        }
    }

}

