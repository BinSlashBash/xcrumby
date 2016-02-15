/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExternalTypeHandler {
    private final HashMap<String, Integer> _nameToPropertyIndex;
    private final ExtTypedProperty[] _properties;
    private final TokenBuffer[] _tokens;
    private final String[] _typeIds;

    protected ExternalTypeHandler(ExternalTypeHandler externalTypeHandler) {
        this._properties = externalTypeHandler._properties;
        this._nameToPropertyIndex = externalTypeHandler._nameToPropertyIndex;
        int n2 = this._properties.length;
        this._typeIds = new String[n2];
        this._tokens = new TokenBuffer[n2];
    }

    protected ExternalTypeHandler(ExtTypedProperty[] arrextTypedProperty, HashMap<String, Integer> hashMap, String[] arrstring, TokenBuffer[] arrtokenBuffer) {
        this._properties = arrextTypedProperty;
        this._nameToPropertyIndex = hashMap;
        this._typeIds = arrstring;
        this._tokens = arrtokenBuffer;
    }

    protected final Object _deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, int n2, String object) throws IOException, JsonProcessingException {
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser);
        tokenBuffer.writeStartArray();
        tokenBuffer.writeString((String)object);
        object = this._tokens[n2].asParser(jsonParser);
        object.nextToken();
        tokenBuffer.copyCurrentStructure((JsonParser)object);
        tokenBuffer.writeEndArray();
        jsonParser = tokenBuffer.asParser(jsonParser);
        jsonParser.nextToken();
        return this._properties[n2].getProperty().deserialize(jsonParser, deserializationContext);
    }

    protected final void _deserializeAndSet(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, int n2, String object2) throws IOException, JsonProcessingException {
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser);
        tokenBuffer.writeStartArray();
        tokenBuffer.writeString((String)object2);
        object2 = this._tokens[n2].asParser(jsonParser);
        object2.nextToken();
        tokenBuffer.copyCurrentStructure((JsonParser)object2);
        tokenBuffer.writeEndArray();
        jsonParser = tokenBuffer.asParser(jsonParser);
        jsonParser.nextToken();
        this._properties[n2].getProperty().deserializeAndSet(jsonParser, deserializationContext, object);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Object complete(JsonParser object, DeserializationContext deserializationContext, PropertyValueBuffer propertyValueBuffer, PropertyBasedCreator propertyBasedCreator) throws IOException, JsonProcessingException {
        void var3_5;
        int n2;
        void var4_6;
        int n3 = this._properties.length;
        Object[] arrobject = new Object[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            String string2 = this._typeIds[n2];
            if (string2 == null) {
                if (this._tokens[n2] == null) continue;
                if (!this._properties[n2].hasDefaultType()) {
                    throw deserializationContext.mappingException("Missing external type id property '" + this._properties[n2].getTypePropertyName() + "'");
                }
                string2 = this._properties[n2].getDefaultTypeId();
            } else if (this._tokens[n2] == null) {
                object = this._properties[n2].getProperty();
                throw deserializationContext.mappingException("Missing property '" + object.getName() + "' for external type id '" + this._properties[n2].getTypePropertyName());
            }
            arrobject[n2] = this._deserialize((JsonParser)object, deserializationContext, n2, string2);
        }
        for (n2 = 0; n2 < n3; ++n2) {
            object = this._properties[n2].getProperty();
            if (var4_6.findCreatorProperty(object.getName()) == null) continue;
            var3_5.assignParameter(object.getCreatorIndex(), arrobject[n2]);
        }
        object = var4_6.build(deserializationContext, (PropertyValueBuffer)var3_5);
        n2 = 0;
        while (n2 < n3) {
            SettableBeanProperty settableBeanProperty = this._properties[n2].getProperty();
            if (var4_6.findCreatorProperty(settableBeanProperty.getName()) == null) {
                settableBeanProperty.set(object, arrobject[n2]);
            }
            ++n2;
        }
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public Object complete(JsonParser var1_1, DeserializationContext var2_2, Object var3_3) throws IOException, JsonProcessingException {
        var4_4 = 0;
        var5_5 = this._properties.length;
        while (var4_4 < var5_5) {
            var7_7 = this._typeIds[var4_4];
            if (var7_7 != null) ** GOTO lbl24
            var8_8 = this._tokens[var4_4];
            if (var8_8 == null) ** GOTO lbl29
            var9_9 = var8_8.firstToken();
            var6_6 = var7_7;
            if (var9_9 == null) ** GOTO lbl28
            var6_6 = var7_7;
            if (!var9_9.isScalarValue()) ** GOTO lbl28
            var7_7 = var8_8.asParser((JsonParser)var1_1);
            var7_7.nextToken();
            var6_6 = this._properties[var4_4].getProperty();
            var7_7 = TypeDeserializer.deserializeIfNatural((JsonParser)var7_7, var2_2, var6_6.getType());
            if (var7_7 == null) ** GOTO lbl20
            var6_6.set(var3_3, var7_7);
            ** GOTO lbl29
lbl20: // 1 sources:
            if (!this._properties[var4_4].hasDefaultType()) {
                throw var2_2.mappingException("Missing external type id property '" + this._properties[var4_4].getTypePropertyName() + "'");
            }
            var6_6 = this._properties[var4_4].getDefaultTypeId();
            ** GOTO lbl28
lbl24: // 1 sources:
            var6_6 = var7_7;
            if (this._tokens[var4_4] == null) {
                var1_1 = this._properties[var4_4].getProperty();
                throw var2_2.mappingException("Missing property '" + var1_1.getName() + "' for external type id '" + this._properties[var4_4].getTypePropertyName());
            }
lbl28: // 5 sources:
            this._deserializeAndSet((JsonParser)var1_1, var2_2, var3_3, var4_4, (String)var6_6);
lbl29: // 3 sources:
            ++var4_4;
        }
        return var3_3;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean handlePropertyValue(JsonParser jsonParser, DeserializationContext deserializationContext, String object, Object object2) throws IOException, JsonProcessingException {
        boolean bl2;
        Integer n2 = this._nameToPropertyIndex.get(object);
        if (n2 == null) {
            return false;
        }
        int n3 = n2;
        if (this._properties[n3].hasTypePropertyName((String)object)) {
            this._typeIds[n3] = jsonParser.getText();
            jsonParser.skipChildren();
            if (object2 == null) return true;
            if (this._tokens[n3] == null) return true;
            bl2 = true;
        } else {
            object = new TokenBuffer(jsonParser);
            object.copyCurrentStructure(jsonParser);
            this._tokens[n3] = object;
            if (object2 == null) return true;
            if (this._typeIds[n3] == null) return true;
            bl2 = true;
        }
        if (!bl2) return true;
        object = this._typeIds[n3];
        this._typeIds[n3] = null;
        this._deserializeAndSet(jsonParser, deserializationContext, object2, n3, (String)object);
        this._tokens[n3] = null;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean handleTypePropertyValue(JsonParser jsonParser, DeserializationContext deserializationContext, String string2, Object object) throws IOException, JsonProcessingException {
        Integer n2 = this._nameToPropertyIndex.get(string2);
        if (n2 == null) {
            return false;
        }
        int n3 = n2;
        if (!this._properties[n3].hasTypePropertyName(string2)) return false;
        string2 = jsonParser.getText();
        boolean bl2 = object != null && this._tokens[n3] != null;
        if (bl2) {
            this._deserializeAndSet(jsonParser, deserializationContext, object, n3, string2);
            this._tokens[n3] = null;
            return true;
        }
        this._typeIds[n3] = string2;
        return true;
    }

    public ExternalTypeHandler start() {
        return new ExternalTypeHandler(this);
    }

    public static class Builder {
        private final HashMap<String, Integer> _nameToPropertyIndex = new HashMap();
        private final ArrayList<ExtTypedProperty> _properties = new ArrayList();

        public void addExternal(SettableBeanProperty settableBeanProperty, TypeDeserializer typeDeserializer) {
            Integer n2 = this._properties.size();
            this._properties.add(new ExtTypedProperty(settableBeanProperty, typeDeserializer));
            this._nameToPropertyIndex.put(settableBeanProperty.getName(), n2);
            this._nameToPropertyIndex.put(typeDeserializer.getPropertyName(), n2);
        }

        public ExternalTypeHandler build() {
            return new ExternalTypeHandler(this._properties.toArray(new ExtTypedProperty[this._properties.size()]), this._nameToPropertyIndex, null, null);
        }
    }

    private static final class ExtTypedProperty {
        private final SettableBeanProperty _property;
        private final TypeDeserializer _typeDeserializer;
        private final String _typePropertyName;

        public ExtTypedProperty(SettableBeanProperty settableBeanProperty, TypeDeserializer typeDeserializer) {
            this._property = settableBeanProperty;
            this._typeDeserializer = typeDeserializer;
            this._typePropertyName = typeDeserializer.getPropertyName();
        }

        public String getDefaultTypeId() {
            Class class_ = this._typeDeserializer.getDefaultImpl();
            if (class_ == null) {
                return null;
            }
            return this._typeDeserializer.getTypeIdResolver().idFromValueAndType(null, class_);
        }

        public SettableBeanProperty getProperty() {
            return this._property;
        }

        public String getTypePropertyName() {
            return this._typePropertyName;
        }

        public boolean hasDefaultType() {
            if (this._typeDeserializer.getDefaultImpl() != null) {
                return true;
            }
            return false;
        }

        public boolean hasTypePropertyName(String string2) {
            return string2.equals(this._typePropertyName);
        }
    }

}

