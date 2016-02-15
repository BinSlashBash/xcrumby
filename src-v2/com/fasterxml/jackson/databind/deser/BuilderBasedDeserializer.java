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
import com.fasterxml.jackson.databind.deser.impl.BeanAsArrayBuilderDeserializer;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.deser.impl.UnwrappedPropertyHandler;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.HashSet;
import java.util.Map;

public class BuilderBasedDeserializer
extends BeanDeserializerBase {
    private static final long serialVersionUID = 1;
    protected final AnnotatedMethod _buildMethod;

    public BuilderBasedDeserializer(BeanDeserializerBuilder beanDeserializerBuilder, BeanDescription beanDescription, BeanPropertyMap beanPropertyMap, Map<String, SettableBeanProperty> map, HashSet<String> hashSet, boolean bl2, boolean bl3) {
        super(beanDeserializerBuilder, beanDescription, beanPropertyMap, map, hashSet, bl2, bl3);
        this._buildMethod = beanDeserializerBuilder.getBuildMethod();
        if (this._objectIdReader != null) {
            throw new IllegalArgumentException("Can not use Object Id with Builder-based deserialization (type " + beanDescription.getType() + ")");
        }
    }

    protected BuilderBasedDeserializer(BuilderBasedDeserializer builderBasedDeserializer) {
        this(builderBasedDeserializer, builderBasedDeserializer._ignoreAllUnknown);
    }

    public BuilderBasedDeserializer(BuilderBasedDeserializer builderBasedDeserializer, ObjectIdReader objectIdReader) {
        super((BeanDeserializerBase)builderBasedDeserializer, objectIdReader);
        this._buildMethod = builderBasedDeserializer._buildMethod;
    }

    protected BuilderBasedDeserializer(BuilderBasedDeserializer builderBasedDeserializer, NameTransformer nameTransformer) {
        super((BeanDeserializerBase)builderBasedDeserializer, nameTransformer);
        this._buildMethod = builderBasedDeserializer._buildMethod;
    }

    public BuilderBasedDeserializer(BuilderBasedDeserializer builderBasedDeserializer, HashSet<String> hashSet) {
        super((BeanDeserializerBase)builderBasedDeserializer, hashSet);
        this._buildMethod = builderBasedDeserializer._buildMethod;
    }

    protected BuilderBasedDeserializer(BuilderBasedDeserializer builderBasedDeserializer, boolean bl2) {
        super((BeanDeserializerBase)builderBasedDeserializer, bl2);
        this._buildMethod = builderBasedDeserializer._buildMethod;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final Object vanillaDeserialize(JsonParser jsonParser, DeserializationContext deserializationContext, JsonToken object) throws IOException, JsonProcessingException {
        object = this._valueInstantiator.createUsingDefault(deserializationContext);
        while (jsonParser.getCurrentToken() != JsonToken.END_OBJECT) {
            String string2 = jsonParser.getCurrentName();
            jsonParser.nextToken();
            Object object2 = this._beanProperties.find(string2);
            if (object2 != null) {
                try {
                    object = object2 = object2.deserializeSetAndReturn(jsonParser, deserializationContext, object);
                }
                catch (Exception var4_5) {
                    this.wrapAndThrow((Throwable)var4_5, object, string2, deserializationContext);
                }
            } else {
                this.handleUnknownVanilla(jsonParser, deserializationContext, object, string2);
            }
            jsonParser.nextToken();
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final Object _deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        Object object2;
        JsonToken jsonToken;
        if (this._injectables != null) {
            this.injectValues(deserializationContext, object);
        }
        if (this._unwrappedPropertyHandler != null) {
            return this.deserializeWithUnwrapped(jsonParser, deserializationContext, object);
        }
        if (this._externalTypeIdHandler != null) {
            return this.deserializeWithExternalTypeId(jsonParser, deserializationContext, object);
        }
        if (this._needViewProcesing && (object2 = deserializationContext.getActiveView()) != null) {
            return this.deserializeWithView(jsonParser, deserializationContext, object, object2);
        }
        object2 = jsonToken = jsonParser.getCurrentToken();
        Object object3 = object;
        if (jsonToken == JsonToken.START_OBJECT) {
            object2 = jsonParser.nextToken();
            object3 = object;
        }
        while (object2 == JsonToken.FIELD_NAME) {
            object2 = jsonParser.getCurrentName();
            jsonParser.nextToken();
            object = this._beanProperties.find((String)object2);
            if (object != null) {
                try {
                    object3 = object = object.deserializeSetAndReturn(jsonParser, deserializationContext, object3);
                }
                catch (Exception var3_4) {
                    this.wrapAndThrow((Throwable)var3_4, object3, (String)object2, deserializationContext);
                }
            } else {
                this.handleUnknownVanilla(jsonParser, deserializationContext, this.handledType(), (String)object2);
            }
            object2 = jsonParser.nextToken();
        }
        return object3;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected final Object _deserializeUsingPropertyBased(JsonParser var1_1, DeserializationContext var2_3) throws IOException, JsonProcessingException {
        var6_4 = this._propertyBasedCreator;
        var7_5 = var6_4.startBuilding((JsonParser)var1_1, var2_3, this._objectIdReader);
        var3_6 = null;
        var5_7 = var1_1.getCurrentToken();
        while (var5_7 == JsonToken.FIELD_NAME) {
            block17 : {
                var5_7 = var1_1.getCurrentName();
                var1_1.nextToken();
                var8_10 = var6_4.findCreatorProperty((String)var5_7);
                if (var8_10 != null) {
                    var9_11 = var8_10.deserialize((JsonParser)var1_1, var2_3);
                    var4_8 = var3_6;
                    if (var7_5.assignParameter(var8_10.getCreatorIndex(), var9_11)) {
                        var1_1.nextToken();
                        try {
                            var4_8 = var6_4.build(var2_3, var7_5);
                            ** if (var4_8.getClass() == this._beanType.getRawClass()) goto lbl-1000
                        }
                        catch (Exception var4_9) {
                            this.wrapAndThrow((Throwable)var4_9, this._beanType.getRawClass(), (String)var5_7, var2_3);
                            var4_8 = var3_6;
                            break block17;
                        }
lbl17: // 1 sources:
                        return this.handlePolymorphic((JsonParser)var1_1, var2_3, var4_8, (TokenBuffer)var3_6);
lbl-1000: // 1 sources:
                        {
                        }
                        var5_7 = var4_8;
                        if (var3_6 == null) return this._deserialize((JsonParser)var1_1, var2_3, var5_7);
                        var5_7 = this.handleUnknownProperties(var2_3, var4_8, (TokenBuffer)var3_6);
                        return this._deserialize((JsonParser)var1_1, var2_3, var5_7);
                    }
                } else {
                    var4_8 = var3_6;
                    if (!var7_5.readIdProperty((String)var5_7)) {
                        var4_8 = this._beanProperties.find((String)var5_7);
                        if (var4_8 != null) {
                            var7_5.bufferProperty((SettableBeanProperty)var4_8, var4_8.deserialize((JsonParser)var1_1, var2_3));
                            var4_8 = var3_6;
                        } else if (this._ignorableProps != null && this._ignorableProps.contains(var5_7)) {
                            this.handleIgnoredProperty((JsonParser)var1_1, var2_3, this.handledType(), (String)var5_7);
                            var4_8 = var3_6;
                        } else if (this._anySetter != null) {
                            var7_5.bufferAnyProperty(this._anySetter, (String)var5_7, this._anySetter.deserialize((JsonParser)var1_1, var2_3));
                            var4_8 = var3_6;
                        } else {
                            var4_8 = var3_6;
                            if (var3_6 == null) {
                                var4_8 = new TokenBuffer((JsonParser)var1_1);
                            }
                            var4_8.writeFieldName((String)var5_7);
                            var4_8.copyCurrentStructure((JsonParser)var1_1);
                        }
                    }
                }
            }
            var5_7 = var1_1.nextToken();
            var3_6 = var4_8;
        }
        try {
            var1_1 = var4_8 = var6_4.build(var2_3, var7_5);
            if (var3_6 == null) return var1_1;
            if (var4_8.getClass() == this._beanType.getRawClass()) return this.handleUnknownProperties(var2_3, var4_8, (TokenBuffer)var3_6);
        }
        catch (Exception var1_2) {
            this.wrapInstantiationProblem(var1_2, var2_3);
            return null;
        }
        return this.handlePolymorphic(null, var2_3, var4_8, (TokenBuffer)var3_6);
    }

    @Override
    protected BeanAsArrayBuilderDeserializer asArrayDeserializer() {
        return new BeanAsArrayBuilderDeserializer(this, this._beanProperties.getPropertiesInInsertionOrder(), this._buildMethod);
    }

    @Override
    public final Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonToken jsonToken = jsonParser.getCurrentToken();
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonToken = jsonParser.nextToken();
            if (this._vanillaProcessing) {
                return this.finishBuild(deserializationContext, this.vanillaDeserialize(jsonParser, deserializationContext, jsonToken));
            }
            return this.finishBuild(deserializationContext, this.deserializeFromObject(jsonParser, deserializationContext));
        }
        switch (.$SwitchMap$com$fasterxml$jackson$core$JsonToken[jsonToken.ordinal()]) {
            default: {
                throw deserializationContext.mappingException(this.handledType());
            }
            case 1: {
                return this.finishBuild(deserializationContext, this.deserializeFromString(jsonParser, deserializationContext));
            }
            case 2: {
                return this.finishBuild(deserializationContext, this.deserializeFromNumber(jsonParser, deserializationContext));
            }
            case 3: {
                return this.finishBuild(deserializationContext, this.deserializeFromDouble(jsonParser, deserializationContext));
            }
            case 4: {
                return jsonParser.getEmbeddedObject();
            }
            case 5: 
            case 6: {
                return this.finishBuild(deserializationContext, this.deserializeFromBoolean(jsonParser, deserializationContext));
            }
            case 7: {
                return this.finishBuild(deserializationContext, this.deserializeFromArray(jsonParser, deserializationContext));
            }
            case 8: 
            case 9: 
        }
        return this.finishBuild(deserializationContext, this.deserializeFromObject(jsonParser, deserializationContext));
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        return this.finishBuild(deserializationContext, this._deserialize(jsonParser, deserializationContext, object));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object deserializeFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Object object;
        if (this._nonStandardCreation) {
            if (this._unwrappedPropertyHandler != null) {
                return this.deserializeWithUnwrapped(jsonParser, deserializationContext);
            }
            if (this._externalTypeIdHandler == null) return this.deserializeFromObjectUsingNonDefault(jsonParser, deserializationContext);
            return this.deserializeWithExternalTypeId(jsonParser, deserializationContext);
        }
        Object object2 = this._valueInstantiator.createUsingDefault(deserializationContext);
        if (this._injectables != null) {
            this.injectValues(deserializationContext, object2);
        }
        Object object3 = object2;
        if (this._needViewProcesing) {
            object = deserializationContext.getActiveView();
            object3 = object2;
            if (object != null) {
                return this.deserializeWithView(jsonParser, deserializationContext, object2, object);
            }
        }
        do {
            object2 = object3;
            if (jsonParser.getCurrentToken() == JsonToken.END_OBJECT) return object2;
            object = jsonParser.getCurrentName();
            jsonParser.nextToken();
            object2 = this._beanProperties.find((String)object);
            if (object2 != null) {
                try {
                    object3 = object2 = object2.deserializeSetAndReturn(jsonParser, deserializationContext, object3);
                }
                catch (Exception var4_4) {
                    this.wrapAndThrow((Throwable)var4_4, object3, (String)object, deserializationContext);
                }
            } else {
                this.handleUnknownVanilla(jsonParser, deserializationContext, object3, (String)object);
            }
            jsonParser.nextToken();
        } while (true);
    }

    protected Object deserializeUsingPropertyBasedWithExternalTypeId(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        throw new IllegalStateException("Deserialization with Builder, External type id, @JsonCreator not yet implemented");
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
        while (jsonParser.getCurrentToken() != JsonToken.END_OBJECT) {
            String string2 = jsonParser.getCurrentName();
            jsonParser.nextToken();
            Object object2 = this._beanProperties.find(string2);
            if (object2 != null) {
                if (class_ != null && !object2.visibleInView(class_)) {
                    jsonParser.skipChildren();
                    object2 = object;
                } else {
                    try {
                        object2 = object2.deserializeSetAndReturn(jsonParser, deserializationContext, object);
                    }
                    catch (Exception var5_7) {
                        this.wrapAndThrow((Throwable)var5_7, object, string2, deserializationContext);
                        object2 = object;
                    }
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(string2)) {
                this.handleIgnoredProperty(jsonParser, deserializationContext, object, string2);
                object2 = object;
            } else {
                object2 = object;
                if (!externalTypeHandler.handlePropertyValue(jsonParser, deserializationContext, string2, object)) {
                    if (this._anySetter != null) {
                        try {
                            this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, string2);
                            object2 = object;
                        }
                        catch (Exception var5_8) {
                            this.wrapAndThrow((Throwable)var5_8, object, string2, deserializationContext);
                            object2 = object;
                        }
                    } else {
                        this.handleUnknownProperty(jsonParser, deserializationContext, object, string2);
                        object2 = object;
                    }
                }
            }
            jsonParser.nextToken();
            object = object2;
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
            Object object2 = this._beanProperties.find(string2);
            if (object2 != null) {
                if (class_ != null && !object2.visibleInView(class_)) {
                    jsonParser.skipChildren();
                    object2 = object;
                } else {
                    try {
                        object2 = object2.deserializeSetAndReturn(jsonParser, deserializationContext, object);
                    }
                    catch (Exception var5_7) {
                        this.wrapAndThrow((Throwable)var5_7, object, string2, deserializationContext);
                        object2 = object;
                    }
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(string2)) {
                this.handleIgnoredProperty(jsonParser, deserializationContext, object, string2);
                object2 = object;
            } else {
                tokenBuffer.writeFieldName(string2);
                tokenBuffer.copyCurrentStructure(jsonParser);
                object2 = object;
                if (this._anySetter != null) {
                    try {
                        this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, string2);
                        object2 = object;
                    }
                    catch (Exception var5_8) {
                        this.wrapAndThrow((Throwable)var5_8, object, string2, deserializationContext);
                        object2 = object;
                    }
                }
            }
            jsonParser.nextToken();
            object = object2;
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
            Object object3 = this._beanProperties.find((String)object2);
            jsonParser.nextToken();
            if (object3 != null) {
                if (class_ != null && !object3.visibleInView(class_)) {
                    jsonParser.skipChildren();
                    object3 = object;
                } else {
                    try {
                        object3 = object3.deserializeSetAndReturn(jsonParser, deserializationContext, object);
                    }
                    catch (Exception var6_8) {
                        this.wrapAndThrow((Throwable)var6_8, object, (String)object2, deserializationContext);
                        object3 = object;
                    }
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(object2)) {
                this.handleIgnoredProperty(jsonParser, deserializationContext, object, (String)object2);
                object3 = object;
            } else {
                tokenBuffer.writeFieldName((String)object2);
                tokenBuffer.copyCurrentStructure(jsonParser);
                object3 = object;
                if (this._anySetter != null) {
                    this._anySetter.deserializeAndSet(jsonParser, deserializationContext, object, (String)object2);
                    object3 = object;
                }
            }
            object2 = jsonParser.nextToken();
            object = object3;
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
            String string2 = jsonParser.getCurrentName();
            jsonParser.nextToken();
            object2 = this._beanProperties.find(string2);
            if (object2 != null) {
                if (!object2.visibleInView(class_)) {
                    jsonParser.skipChildren();
                } else {
                    try {
                        object = object2 = object2.deserializeSetAndReturn(jsonParser, deserializationContext, object);
                    }
                    catch (Exception var5_6) {
                        this.wrapAndThrow((Throwable)var5_6, object, string2, deserializationContext);
                    }
                }
            } else {
                this.handleUnknownVanilla(jsonParser, deserializationContext, object, string2);
            }
            object2 = jsonParser.nextToken();
        }
        return object;
    }

    protected final Object finishBuild(DeserializationContext deserializationContext, Object object) throws IOException {
        try {
            object = this._buildMethod.getMember().invoke(object, new Object[0]);
            return object;
        }
        catch (Exception var2_3) {
            this.wrapInstantiationProblem(var2_3, deserializationContext);
            return null;
        }
    }

    @Override
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer nameTransformer) {
        return new BuilderBasedDeserializer(this, nameTransformer);
    }

    @Override
    public BuilderBasedDeserializer withIgnorableProperties(HashSet<String> hashSet) {
        return new BuilderBasedDeserializer(this, hashSet);
    }

    @Override
    public BuilderBasedDeserializer withObjectIdReader(ObjectIdReader objectIdReader) {
        return new BuilderBasedDeserializer(this, objectIdReader);
    }

}

