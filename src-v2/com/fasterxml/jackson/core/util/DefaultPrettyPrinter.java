/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.util.Instantiatable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public class DefaultPrettyPrinter
implements PrettyPrinter,
Instantiatable<DefaultPrettyPrinter>,
Serializable {
    public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(" ");
    private static final long serialVersionUID = -5512586643324525213L;
    protected Indenter _arrayIndenter;
    protected transient int _nesting;
    protected Indenter _objectIndenter;
    protected final SerializableString _rootSeparator;
    protected boolean _spacesInObjectEntries;

    public DefaultPrettyPrinter() {
        this(DEFAULT_ROOT_VALUE_SEPARATOR);
    }

    public DefaultPrettyPrinter(SerializableString serializableString) {
        this._arrayIndenter = FixedSpaceIndenter.instance;
        this._objectIndenter = Lf2SpacesIndenter.instance;
        this._spacesInObjectEntries = true;
        this._nesting = 0;
        this._rootSeparator = serializableString;
    }

    public DefaultPrettyPrinter(DefaultPrettyPrinter defaultPrettyPrinter) {
        this(defaultPrettyPrinter, defaultPrettyPrinter._rootSeparator);
    }

    public DefaultPrettyPrinter(DefaultPrettyPrinter defaultPrettyPrinter, SerializableString serializableString) {
        this._arrayIndenter = FixedSpaceIndenter.instance;
        this._objectIndenter = Lf2SpacesIndenter.instance;
        this._spacesInObjectEntries = true;
        this._nesting = 0;
        this._arrayIndenter = defaultPrettyPrinter._arrayIndenter;
        this._objectIndenter = defaultPrettyPrinter._objectIndenter;
        this._spacesInObjectEntries = defaultPrettyPrinter._spacesInObjectEntries;
        this._nesting = defaultPrettyPrinter._nesting;
        this._rootSeparator = serializableString;
    }

    /*
     * Enabled aggressive block sorting
     */
    public DefaultPrettyPrinter(String object) {
        object = object == null ? null : new SerializedString((String)object);
        this((SerializableString)object);
    }

    protected DefaultPrettyPrinter _withSpaces(boolean bl2) {
        if (this._spacesInObjectEntries == bl2) {
            return this;
        }
        DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter(this);
        defaultPrettyPrinter._spacesInObjectEntries = bl2;
        return defaultPrettyPrinter;
    }

    @Override
    public void beforeArrayValues(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override
    public void beforeObjectEntries(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override
    public DefaultPrettyPrinter createInstance() {
        return new DefaultPrettyPrinter(this);
    }

    public void indentArraysWith(Indenter indenter) {
        Indenter indenter2 = indenter;
        if (indenter == null) {
            indenter2 = NopIndenter.instance;
        }
        this._arrayIndenter = indenter2;
    }

    public void indentObjectsWith(Indenter indenter) {
        Indenter indenter2 = indenter;
        if (indenter == null) {
            indenter2 = NopIndenter.instance;
        }
        this._objectIndenter = indenter2;
    }

    @Deprecated
    public void spacesInObjectEntries(boolean bl2) {
        this._spacesInObjectEntries = bl2;
    }

    public DefaultPrettyPrinter withArrayIndenter(Indenter object) {
        Indenter indenter = object;
        if (object == null) {
            indenter = NopIndenter.instance;
        }
        if (this._arrayIndenter == indenter) {
            return this;
        }
        object = new DefaultPrettyPrinter(this);
        object._arrayIndenter = indenter;
        return object;
    }

    public DefaultPrettyPrinter withObjectIndenter(Indenter object) {
        Indenter indenter = object;
        if (object == null) {
            indenter = NopIndenter.instance;
        }
        if (this._objectIndenter == indenter) {
            return this;
        }
        object = new DefaultPrettyPrinter(this);
        object._objectIndenter = indenter;
        return object;
    }

    public DefaultPrettyPrinter withRootSeparator(SerializableString serializableString) {
        if (this._rootSeparator == serializableString || serializableString != null && serializableString.equals(this._rootSeparator)) {
            return this;
        }
        return new DefaultPrettyPrinter(this, serializableString);
    }

    public DefaultPrettyPrinter withSpacesInObjectEntries() {
        return this._withSpaces(true);
    }

    public DefaultPrettyPrinter withoutSpacesInObjectEntries() {
        return this._withSpaces(false);
    }

    @Override
    public void writeArrayValueSeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(',');
        this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeEndArray(JsonGenerator jsonGenerator, int n2) throws IOException, JsonGenerationException {
        if (!this._arrayIndenter.isInline()) {
            --this._nesting;
        }
        if (n2 > 0) {
            this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
        } else {
            jsonGenerator.writeRaw(' ');
        }
        jsonGenerator.writeRaw(']');
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeEndObject(JsonGenerator jsonGenerator, int n2) throws IOException, JsonGenerationException {
        if (!this._objectIndenter.isInline()) {
            --this._nesting;
        }
        if (n2 > 0) {
            this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
        } else {
            jsonGenerator.writeRaw(' ');
        }
        jsonGenerator.writeRaw('}');
    }

    @Override
    public void writeObjectEntrySeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(',');
        this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override
    public void writeObjectFieldValueSeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        if (this._spacesInObjectEntries) {
            jsonGenerator.writeRaw(" : ");
            return;
        }
        jsonGenerator.writeRaw(':');
    }

    @Override
    public void writeRootValueSeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        if (this._rootSeparator != null) {
            jsonGenerator.writeRaw(this._rootSeparator);
        }
    }

    @Override
    public void writeStartArray(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        if (!this._arrayIndenter.isInline()) {
            ++this._nesting;
        }
        jsonGenerator.writeRaw('[');
    }

    @Override
    public void writeStartObject(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw('{');
        if (!this._objectIndenter.isInline()) {
            ++this._nesting;
        }
    }

    public static class FixedSpaceIndenter
    extends NopIndenter {
        public static final FixedSpaceIndenter instance = new FixedSpaceIndenter();

        @Override
        public boolean isInline() {
            return true;
        }

        @Override
        public void writeIndentation(JsonGenerator jsonGenerator, int n2) throws IOException, JsonGenerationException {
            jsonGenerator.writeRaw(' ');
        }
    }

    public static interface Indenter {
        public boolean isInline();

        public void writeIndentation(JsonGenerator var1, int var2) throws IOException, JsonGenerationException;
    }

    public static class Lf2SpacesIndenter
    extends NopIndenter {
        static final char[] SPACES;
        static final int SPACE_COUNT = 64;
        private static final String SYS_LF;
        public static final Lf2SpacesIndenter instance;
        protected final String _lf;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static {
            String string2;
            String string3;
            string3 = null;
            try {
                string3 = string2 = System.getProperty("line.separator");
            }
            catch (Throwable var1_2) {}
            string2 = string3;
            if (string3 == null) {
                string2 = "\n";
            }
            SYS_LF = string2;
            SPACES = new char[64];
            Arrays.fill(SPACES, ' ');
            instance = new Lf2SpacesIndenter();
        }

        public Lf2SpacesIndenter() {
            this(SYS_LF);
        }

        public Lf2SpacesIndenter(String string2) {
            this._lf = string2;
        }

        @Override
        public boolean isInline() {
            return false;
        }

        public Lf2SpacesIndenter withLinefeed(String string2) {
            if (string2.equals(this._lf)) {
                return this;
            }
            return new Lf2SpacesIndenter(string2);
        }

        @Override
        public void writeIndentation(JsonGenerator jsonGenerator, int n2) throws IOException, JsonGenerationException {
            jsonGenerator.writeRaw(this._lf);
            if (n2 > 0) {
                n2 += n2;
                while (n2 > 64) {
                    jsonGenerator.writeRaw(SPACES, 0, 64);
                    n2 -= SPACES.length;
                }
                jsonGenerator.writeRaw(SPACES, 0, n2);
            }
        }
    }

    public static class NopIndenter
    implements Indenter,
    Serializable {
        public static final NopIndenter instance = new NopIndenter();

        @Override
        public boolean isInline() {
            return true;
        }

        @Override
        public void writeIndentation(JsonGenerator jsonGenerator, int n2) throws IOException, JsonGenerationException {
        }
    }

}

