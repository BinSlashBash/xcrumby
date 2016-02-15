/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.BeanAsArrayDeserializer;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.deser.impl.UnwrappedPropertyHandler;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;

public class BeanDeserializer
extends BeanDeserializerBase
implements Serializable {
    private static final long serialVersionUID = 1;

    protected BeanDeserializer(BeanDeserializerBase beanDeserializerBase) {
        super(beanDeserializerBase, beanDeserializerBase._ignoreAllUnknown);
    }

    public BeanDeserializer(BeanDeserializerBase beanDeserializerBase, ObjectIdReader objectIdReader) {
        super(beanDeserializerBase, objectIdReader);
    }

    protected BeanDeserializer(BeanDeserializerBase beanDeserializerBase, NameTransformer nameTransformer) {
        super(beanDeserializerBase, nameTransformer);
    }

    public BeanDeserializer(BeanDeserializerBase beanDeserializerBase, HashSet<String> hashSet) {
        super(beanDeserializerBase, hashSet);
    }

    protected BeanDeserializer(BeanDeserializerBase beanDeserializerBase, boolean bl2) {
        super(beanDeserializerBase, bl2);
    }

    public BeanDeserializer(BeanDeserializerBuilder beanDeserializerBuilder, BeanDescription beanDescription, BeanPropertyMap beanPropertyMap, Map<String, SettableBeanProperty> map, HashSet<String> hashSet, boolean bl2, boolean bl3) {
        super(beanDeserializerBuilder, beanDescription, beanPropertyMap, map, hashSet, bl2, bl3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final Object vanillaDeserialize(JsonParser jsonParser, DeserializationContext deserializationContext, JsonToken object) throws IOException, JsonProcessingException {
        Object object2 = this._valueInstantiator.createUsingDefault(deserializationContext);
        while (object == JsonToken.FIELD_NAME) {
            object = jsonParser.getCurrentName();
            jsonParser.nextToken();
            SettableBeanProperty settableBeanProperty = this._beanProperties.find((String)object);
            if (settableBeanProperty != null) {
                try {
                    settableBeanProperty.deserializeAndSet(jsonParser, deserializationContext, object2);
                }
                catch (Exception var5_6) {
                    this.wrapAndThrow((Throwable)var5_6, object2, (String)object, deserializationContext);
                }
            } else {
                this.handleUnknownVanilla(jsonParser, deserializationContext, object2, (String)object);
            }
            object = jsonParser.nextToken();
        }
        return object2;
    }

    protected final Object _deserializeOther(JsonParser jsonParser, DeserializationContext deserializationContext, JsonToken jsonToken) throws IOException, JsonProcessingException {
        if (jsonToken == null) {
            return this._missingToken(jsonParser, deserializationContext);
        }
        switch (.$SwitchMap$com$fasterxml$jackson$core$JsonToken[jsonToken.ordinal()]) {
            default: {
                throw deserializationContext.mappingException(this.handledType());
            }
            case 1: {
                return this.deserializeFromString(jsonParser, deserializationContext);
            }
            case 2: {
                return this.deserializeFromNumber(jsonParser, deserializationContext);
            }
            case 3: {
                return this.deserializeFromDouble(jsonParser, deserializationContext);
            }
            case 4: {
                return jsonParser.getEmbeddedObject();
            }
            case 5: 
            case 6: {
                return this.deserializeFromBoolean(jsonParser, deserializationContext);
            }
            case 7: {
                return this.deserializeFromArray(jsonParser, deserializationContext);
            }
            case 8: 
            case 9: 
        }
        if (this._vanillaProcessing) {
            return this.vanillaDeserialize(jsonParser, deserializationContext, jsonToken);
        }
        if (this._objectIdReader != null) {
            return this.deserializeWithObjectId(jsonParser, deserializationContext);
        }
        return this.deserializeFromObject(jsonParser, deserializationContext);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected Object _deserializeUsingPropertyBased(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Object object2;
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer propertyValueBuffer = propertyBasedCreator.startBuilding((JsonParser)object, deserializationContext, this._objectIdReader);
        Object object3 = null;
        Object object4 = object.getCurrentToken();
        while (object4 == JsonToken.FIELD_NAME) {
            object4 = object.getCurrentName();
            object.nextToken();
            SettableBeanProperty settableBeanProperty = propertyBasedCreator.findCreatorProperty((String)object4);
            if (settableBeanProperty != null) {
                Object object5 = settableBeanProperty.deserialize((JsonParser)object, deserializationContext);
                object2 = object3;
                if (propertyValueBuffer.assignParameter(settableBeanProperty.getCreatorIndex(), object5)) {
                    object.nextToken();
                    try {
                        object2 = propertyBasedCreator.build(deserializationContext, propertyValueBuffer);
                    }
                    catch (Exception var4_9) {
                        this.wrapAndThrow((Throwable)var4_9, this._beanType.getRawClass(), (String)object4, deserializationContext);
                        object2 = null;
                    }
                    if (object2.getClass() != this._beanType.getRawClass()) {
                        return this.handlePolymorphic((JsonParser)object, deserializationContext, object2, (TokenBuffer)object3);
                    }
                    object4 = object2;
                    if (object3 == null) return this.deserialize((JsonParser)object, deserializationContext, object4);
                    object4 = this.handleUnknownProperties(deserializationContext, object2, (TokenBuffer)object3);
                    return this.deserialize((JsonParser)object, deserializationContext, object4);
                }
            } else if (propertyValueBuffer.readIdProperty((String)object4)) {
                object2 = object3;
            } else {
                object2 = this._beanProperties.find((String)object4);
                if (object2 != null) {
                    propertyValueBuffer.bufferProperty((SettableBeanProperty)object2, object2.deserialize((JsonParser)object, deserializationContext));
                    object2 = object3;
                } else if (this._ignorableProps != null && this._ignorableProps.contains(object4)) {
                    this.handleIgnoredProperty((JsonParser)object, deserializationContext, this.handledType(), (String)object4);
                    object2 = object3;
                } else if (this._anySetter != null) {
                    propertyValueBuffer.bufferAnyProperty(this._anySetter, (String)object4, this._anySetter.deserialize((JsonParser)object, deserializationContext));
                    object2 = object3;
                } else {
                    object2 = object3;
                    if (object3 == null) {
                        object2 = new TokenBuffer((JsonParser)object);
                    }
                    object2.writeFieldName((String)object4);
                    object2.copyCurrentStructure((JsonParser)object);
                }
            }
            object4 = object.nextToken();
            object3 = object2;
        }
        try {
            object = propertyBasedCreator.build(deserializationContext, propertyValueBuffer);
        }
        catch (Exception var1_2) {
            this.wrapInstantiationProblem(var1_2, deserializationContext);
            object = null;
        }
        object2 = object;
        if (object3 == null) return object2;
        if (object.getClass() == this._beanType.getRawClass()) return this.handleUnknownProperties(deserializationContext, object, (TokenBuffer)object3);
        return this.handlePolymorphic(null, deserializationContext, object, (TokenBuffer)object3);
    }

    protected Object _missingToken(JsonParser jsonParser, DeserializationContext deserializationContext) throws JsonProcessingException {
        throw deserializationContext.endOfInputException(this.handledType());
    }

    @Override
    protected BeanDeserializerBase asArrayDeserializer() {
        return new BeanAsArrayDeserializer(this, this._beanProperties.getPropertiesInInsertionOrder());
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            if (this._vanillaProcessing) {
                return this.vanillaDeserialize(jsonParser, deserializationContext, jsonParser.nextToken());
            }
            jsonParser.nextToken();
            if (this._objectIdReader != null) {
                return this.deserializeWithObjectId(jsonParser, deserializationContext);
            }
            return this.deserializeFromObject(jsonParser, deserializationContext);
        }
        return this._deserializeOther(jsonParser, deserializationContext, jsonToken);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        Object object2;
        if (this._injectables != null) {
            this.injectValues(deserializationContext, object);
        }
        if (this._unwrappedPropertyHandler != null) {
            return this.deserializeWithUnwrapped(jsonParser, deserializationContext, object);
        }
        if (this._externalTypeIdHandler != null) {
            return this.deserializeWithExternalTypeId(jsonParser, deserializationContext, object);
        }
        Object object3 = object2 = jsonParser.getCurrentToken();
        if (object2 == JsonToken.START_OBJECT) {
            object3 = jsonParser.nextToken();
        }
        object2 = object3;
        if (this._needViewProcesing) {
            Class class_ = deserializationContext.getActiveView();
            object2 = object3;
            if (class_ != null) {
                return this.deserializeWithView(jsonParser, deserializationContext, object, class_);
            }
        }
        do {
            object3 = object;
            if (object2 != JsonToken.FIELD_NAME) return object3;
            object3 = jsonParser.getCurrentName();
            jsonParser.nextToken();
            object2 = this._beanProperties.find((String)object3);
            if (object2 != null) {
                try {
                    object2.deserializeAndSet(jsonParser, deserializationContext, object);
                }
                catch (Exception var5_6) {
                    this.wrapAndThrow((Throwable)var5_6, object, (String)object3, deserializationContext);
                }
            } else {
                this.handleUnknownVanilla(jsonParser, deserializationContext, object, (String)object3);
            }
            object2 = jsonParser.nextToken();
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object deserializeFromObject(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Object object2;
        if (this._nonStandardCreation) {
            if (this._unwrappedPropertyHandler != null) {
                return this.deserializeWithUnwrapped((JsonParser)object, deserializationContext);
            }
            if (this._externalTypeIdHandler != null) {
                return this.deserializeWithExternalTypeId((JsonParser)object, deserializationContext);
            }
            object2 = object = this.deserializeFromObjectUsingNonDefault((JsonParser)object, deserializationContext);
            if (this._injectables == null) return object2;
            this.injectValues(deserializationContext, object);
            return object;
        }
        Object object3 = this._valueInstantiator.createUsingDefault(deserializationContext);
        if (object.canReadObjectId() && (object2 = object.getObjectId()) != null) {
            this._handleTypedObjectId((JsonParser)object, deserializationContext, object3, object2);
        }
        if (this._injectables != null) {
            this.injectValues(deserializationContext, object3);
        }
        if (this._needViewProcesing && (object2 = deserializationContext.getActiveView()) != null) {
            return this.deserializeWithView((JsonParser)object, deserializationContext, object3, object2);
        }
        Object object4 = object.getCurrentToken();
        do {
            object2 = object3;
            if (object4 != JsonToken.FIELD_NAME) return object2;
            object2 = object.getCurrentName();
            object.nextToken();
            object4 = this._beanProperties.find((String)object2);
            if (object4 != null) {
                try {
                    object4.deserializeAndSet((JsonParser)object, deserializationContext, object3);
                }
                catch (Exception var4_6) {
                    this.wrapAndThrow((Throwable)var4_6, object3, (String)object2, deserializationContext);
                }
            } else {
                this.handleUnknownVanilla((JsonParser)object, deserializationContext, object3, (String)object2);
            }
            object4 = object.nextToken();
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected Object deserializeUsingPropertyBasedWithExternalTypeId(JsonParser object, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ExternalTypeHandler externalTypeHandler = this._externalTypeIdHandler.start();
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer propertyValueBuffer = propertyBasedCreator.startBuilding((JsonParser)object, deserializationContext, this._objectIdReader);
        TokenBuffer tokenBuffer = new TokenBuffer((JsonParser)object);
        tokenBuffer.writeStartObject();
        Object object2 = object.getCurrentToken();
        while (object2 == JsonToken.FIELD_NAME) {
            String string2 = object.getCurrentName();
            object.nextToken();
            object2 = propertyBasedCreator.findCreatorProperty(string2);
            if (object2 != null) {
                if (!externalTypeHandler.handlePropertyValue((JsonParser)object, deserializationContext, string2, propertyValueBuffer)) {
                    Object object3 = object2.deserialize((JsonParser)object, deserializationContext);
                    if (propertyValueBuffer.assignParameter(object2.getCreatorIndex(), object3)) {
                        object2 = object.nextToken();
                        try {
                            object3 = propertyBasedCreator.build(deserializationContext, propertyValueBuffer);
                        }
                        catch (Exception var3_9) {
                            this.wrapAndThrow((Throwable)var3_9, this._beanType.getRawClass(), string2, deserializationContext);
                        }
                        do {
                            if (object2 != JsonToken.FIELD_NAME) {
                                if (object3.getClass() == this._beanType.getRawClass()) return externalTypeHandler.complete((JsonParser)object, deserializationContext, object3);
                                throw deserializationContext.mappingException("Can not create polymorphic instances with unwrapped values");
                            }
                            object.nextToken();
                            tokenBuffer.copyCurrentStructure((JsonParser)object);
                            object2 = object.nextToken();
                        } while (true);
                    }
                }
            } else if (!propertyValueBuffer.readIdProperty(string2)) {
                object2 = this._beanProperties.find(string2);
                if (object2 != null) {
                    propertyValueBuffer.bufferProperty((SettableBeanProperty)object2, object2.deserialize((JsonParser)object, deserializationContext));
                } else if (!externalTypeHandler.handlePropertyValue((JsonParser)object, deserializationContext, string2, null)) {
                    if (this._ignorableProps != null && this._ignorableProps.contains(string2)) {
                        this.handleIgnoredProperty((JsonParser)object, deserializationContext, this.handledType(), string2);
                    } else if (this._anySetter != null) {
                        propertyValueBuffer.bufferAnyProperty(this._anySetter, string2, this._anySetter.deserialize((JsonParser)object, deserializationContext));
                    }
                }
            }
            object2 = object.nextToken();
        }
        try {
            return externalTypeHandler.complete((JsonParser)object, deserializationContext, propertyValueBuffer, propertyBasedCreator);
        }
        catch (Exception var1_2) {
            this.wrapInstantiationProblem(var1_2, deserializationContext);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected Object deserializeUsingPropertyBasedWithUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer propertyValueBuffer = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser);
        tokenBuffer.writeStartObject();
        Object object = jsonParser.getCurrentToken();
        while (object == JsonToken.FIELD_NAME) {
            String string2 = jsonParser.getCurrentName();
            jsonParser.nextToken();
            object = propertyBasedCreator.findCreatorProperty(string2);
            if (object != null) {
                Object object2 = object.deserialize(jsonParser, deserializationContext);
                if (propertyValueBuffer.assignParameter(object.getCreatorIndex(), object2)) {
                    object = jsonParser.nextToken();
                    try {
                        object2 = propertyBasedCreator.build(deserializationContext, propertyValueBuffer);
                    }
                    catch (Exception var3_8) {
                        this.wrapAndThrow((Throwable)var3_8, this._beanType.getRawClass(), string2, deserializationContext);
                    }
                    do {
                        if (object != JsonToken.FIELD_NAME) {
                            tokenBuffer.writeEndObject();
                            if (object2.getClass() == this._beanType.getRawClass()) return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, object2, tokenBuffer);
                            tokenBuffer.close();
                            throw deserializationContext.mappingException("Can not create polymorphic instances with unwrapped values");
                        }
                        jsonParser.nextToken();
                        tokenBuffer.copyCurrentStructure(jsonParser);
                        object = jsonParser.nextToken();
                    } while (true);
                }
            } else if (!propertyValueBuffer.readIdProperty(string2)) {
                object = this._beanProperties.find(string2);
                if (object != null) {
                    propertyValueBuffer.bufferProperty((SettableBeanProperty)object, object.deserialize(jsonParser, deserializationContext));
                } else if (this._ignorableProps != null && this._ignorableProps.contains(string2)) {
                    this.handleIgnoredProperty(jsonParser, deserializationContext, this.handledType(), string2);
                } else {
                    tokenBuffer.writeFieldName(string2);
                    tokenBuffer.copyCurrentStructure(jsonParser);
                    if (this._anySetter != null) {
                        propertyValueBuffer.bufferAnyProperty(this._anySetter, string2, this._anySetter.deserialize(jsonParser, deserializationContext));
                    }
                }
            }
            object = jsonParser.nextToken();
        }
        try {
            object = propertyBasedCreator.build(deserializationContext, propertyValueBuffer);
        }
        catch (Exception var1_2) {
            this.wrapInstantiationProblem(var1_2, deserializationContext);
            return null;
        }
        return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, object, tokenBuffer);
    }

    protected Object deserializeWithExternalTypeId(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._propertyBasedCreator != null) {
            return this.deserializeUsingPropertyBasedWithExternalTypeId(jsonParser, deserializationContext);
        }
        return this.deserializeWithExternalTypeId(jsonParser, deserializationContext, this._valueInstantiator.createUsingDefault(deserializationContext));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object deserializeWithExternalTypeId(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        Class class_ = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        ExternalTypeHandler externalTypeHandler = this._externalTypeIdHandler.start();
        Object object2 = jsonParser.getCurrentToken();
        while (object2 == JsonToken.FIELD_NAME) {
            object2 = jsonParser.getCurrentName();
            jsonParser.nextToken();
            SettableBeanProperty settableBeanProperty = this._beanProperties.find((String)object2);
            if (settableBeanProperty != null) {
                if (jsonParser.getCurrentToken().isScalarValue()) {
                    externalTypeHandler.handleTypePropertyValue(jsonParser, deserializationContext, (String)object2, object);
                }
                if (class_ != null && !settableBeanProperty.visibleInView(class_)) {
                    jsonParser.skipChildren();
                } else {
                    try {
                        settableBeanProperty.deserializeAndSet(jsonParser, deserializationContext, object);
                    }
                    catch (Exception var7_8) {
                        this.wrapAndThrow((Throwable)var7_8, object, (String)object2, deserializationContext);
                    }
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(object2)) {
                this.handleIgnoredProperty(jsonParser, deserializationContext, object, (String)object2);
            } else if (!externalTypeHandler.handlePropertyValue(jsonParser, deserializationContext, (String)object2, object)) {
                if (this._anySetter != null) {
                    try {
                        this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, (String)object2);
                    }
                    catch (Exception var7_9) {
                        this.wrapAndThrow((Throwable)var7_9, object, (String)object2, deserializationContext);
                    }
                } else {
                    this.handleUnknownProperty(jsonParser, deserializationContext, object, (String)object2);
                }
            }
            object2 = jsonParser.nextToken();
        }
        return externalTypeHandler.complete(jsonParser, deserializationContext, object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object deserializeWithUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (this._propertyBasedCreator != null) {
            return this.deserializeUsingPropertyBasedWithUnwrapped(jsonParser, deserializationContext);
        }
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser);
        tokenBuffer.writeStartObject();
        Object object = this._valueInstantiator.createUsingDefault(deserializationContext);
        if (this._injectables != null) {
            this.injectValues(deserializationContext, object);
        }
        Class class_ = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        do {
            if (jsonParser.getCurrentToken() == JsonToken.END_OBJECT) {
                tokenBuffer.writeEndObject();
                this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, object, tokenBuffer);
                return object;
            }
            String string2 = jsonParser.getCurrentName();
            jsonParser.nextToken();
            SettableBeanProperty settableBeanProperty = this._beanProperties.find(string2);
            if (settableBeanProperty != null) {
                if (class_ != null && !settableBeanProperty.visibleInView(class_)) {
                    jsonParser.skipChildren();
                } else {
                    try {
                        settableBeanProperty.deserializeAndSet(jsonParser, deserializationContext, object);
                    }
                    catch (Exception var7_8) {
                        this.wrapAndThrow((Throwable)var7_8, object, string2, deserializationContext);
                    }
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(string2)) {
                this.handleIgnoredProperty(jsonParser, deserializationContext, object, string2);
            } else {
                tokenBuffer.writeFieldName(string2);
                tokenBuffer.copyCurrentStructure(jsonParser);
                if (this._anySetter != null) {
                    try {
                        this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, string2);
                    }
                    catch (Exception var7_9) {
                        this.wrapAndThrow((Throwable)var7_9, object, string2, deserializationContext);
                    }
                }
            }
            jsonParser.nextToken();
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object deserializeWithUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        Class class_;
        Object object2 = class_ = jsonParser.getCurrentToken();
        if (class_ == JsonToken.START_OBJECT) {
            object2 = jsonParser.nextToken();
        }
        TokenBuffer tokenBuffer = new TokenBuffer(jsonParser);
        tokenBuffer.writeStartObject();
        class_ = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        do {
            if (object2 != JsonToken.FIELD_NAME) {
                tokenBuffer.writeEndObject();
                this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, object, tokenBuffer);
                return object;
            }
            object2 = jsonParser.getCurrentName();
            SettableBeanProperty settableBeanProperty = this._beanProperties.find((String)object2);
            jsonParser.nextToken();
            if (settableBeanProperty != null) {
                if (class_ != null && !settableBeanProperty.visibleInView(class_)) {
                    jsonParser.skipChildren();
                } else {
                    try {
                        settableBeanProperty.deserializeAndSet(jsonParser, deserializationContext, object);
                    }
                    catch (Exception var7_8) {
                        this.wrapAndThrow((Throwable)var7_8, object, (String)object2, deserializationContext);
                    }
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(object2)) {
                this.handleIgnoredProperty(jsonParser, deserializationContext, object, (String)object2);
            } else {
                tokenBuffer.writeFieldName((String)object2);
                tokenBuffer.copyCurrentStructure(jsonParser);
                if (this._anySetter != null) {
                    this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, (String)object2);
                }
            }
            object2 = jsonParser.nextToken();
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final Object deserializeWithView(JsonParser jsonParser, DeserializationContext deserializationContext, Object object, Class<?> class_) throws IOException, JsonProcessingException {
        Object object2 = jsonParser.getCurrentToken();
        while (object2 == JsonToken.FIELD_NAME) {
            object2 = jsonParser.getCurrentName();
            jsonParser.nextToken();
            SettableBeanProperty settableBeanProperty = this._beanProperties.find((String)object2);
            if (settableBeanProperty != null) {
                if (!settableBeanProperty.visibleInView(class_)) {
                    jsonParser.skipChildren();
                } else {
                    try {
                        settableBeanProperty.deserializeAndSet(jsonParser, deserializationContext, object);
                    }
                    catch (Exception var6_7) {
                        this.wrapAndThrow((Throwable)var6_7, object, (String)object2, deserializationContext);
                    }
                }
            } else {
                this.handleUnknownVanilla(jsonParser, deserializationContext, object, (String)object2);
            }
            object2 = jsonParser.nextToken();
        }
        return object;
    }

    @Override
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer nameTransformer) {
        if (this.getClass() != BeanDeserializer.class) {
            return this;
        }
        return new BeanDeserializer((BeanDeserializerBase)this, nameTransformer);
    }

    @Override
    public BeanDeserializer withIgnorableProperties(HashSet<String> hashSet) {
        return new BeanDeserializer((BeanDeserializerBase)this, hashSet);
    }

    @Override
    public BeanDeserializer withObjectIdReader(ObjectIdReader objectIdReader) {
        return new BeanDeserializer((BeanDeserializerBase)this, objectIdReader);
    }

}

