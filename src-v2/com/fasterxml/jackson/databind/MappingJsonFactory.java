/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class MappingJsonFactory
extends JsonFactory {
    private static final long serialVersionUID = -6744103724013275513L;

    public MappingJsonFactory() {
        this(null);
    }

    public MappingJsonFactory(ObjectMapper objectMapper) {
        super(objectMapper);
        if (objectMapper == null) {
            this.setCodec(new ObjectMapper(this));
        }
    }

    @Override
    public JsonFactory copy() {
        this._checkInvalidCopy(MappingJsonFactory.class);
        return new MappingJsonFactory(null);
    }

    @Override
    public final ObjectMapper getCodec() {
        return (ObjectMapper)this._objectCodec;
    }

    @Override
    public String getFormatName() {
        return "JSON";
    }

    @Override
    public MatchStrength hasFormat(InputAccessor inputAccessor) throws IOException {
        if (this.getClass() == MappingJsonFactory.class) {
            return this.hasJSONFormat(inputAccessor);
        }
        return null;
    }
}

