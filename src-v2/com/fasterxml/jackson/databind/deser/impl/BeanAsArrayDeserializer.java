/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.HashSet;

public class BeanAsArrayDeserializer
extends BeanDeserializerBase {
    private static final long serialVersionUID = 1;
    protected final BeanDeserializerBase _delegate;
    protected final SettableBeanProperty[] _orderedProperties;

    public BeanAsArrayDeserializer(BeanDeserializerBase beanDeserializerBase, SettableBeanProperty[] arrsettableBeanProperty) {
        super(beanDeserializerBase);
        this._delegate = beanDeserializerBase;
        this._orderedProperties = arrsettableBeanProperty;
    }

    protected Object _deserializeFromNonArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        throw deserializationContext.mappingException("Can not deserialize a POJO (of type " + this._beanType.getRawClass().getName() + ") from non-Array representation (token: " + (Object)((Object)jsonParser.getCurrentToken()) + "): type/property designed to be serialized as JSON Array");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Object _deserializeNonVanilla(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Object object;
        if (this._nonStandardCreation) {
            return this._deserializeWithCreator(jsonParser, deserializationContext);
        }
        Object object2 = this._valueInstantiator.createUsingDefault(deserializationContext);
        if (this._injectables != null) {
            this.injectValues(deserializationContext, object2);
        }
        Class class_ = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        SettableBeanProperty[] arrsettableBeanProperty = this._orderedProperties;
        int n2 = 0;
        int n3 = arrsettableBeanProperty.length;
        do {
            object = object2;
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) return object;
            if (n2 == n3) {
                if (this._ignoreAllUnknown) break;
                throw deserializationContext.mappingException("Unexpected JSON values; expected at most " + n3 + " properties (in JSON Array)");
            }
            object = arrsettableBeanProperty[n2];
            ++n2;
            if (object != null && (class_ == null || object.visibleInView(class_))) {
                try {
                    object.deserializeAndSet(jsonParser, deserializationContext, object2);
                }
                catch (Exception var9_9) {
                    this.wrapAndThrow((Throwable)var9_9, object2, object.getName(), deserializationContext);
                }
                continue;
            }
            jsonParser.skipChildren();
        } while (true);
        do {
            object = object2;
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) return object;
            jsonParser.skipChildren();
        } while (true);
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
        var8_4 = this._propertyBasedCreator;
        var9_5 = var8_4.startBuilding((JsonParser)var1_1, var2_3, this._objectIdReader);
        var10_6 = this._orderedProperties;
        var4_7 = var10_6.length;
        var3_8 = 0;
        var5_9 = null;
        while (var1_1.nextToken() != JsonToken.END_ARRAY) {
            var7_13 = var3_8 < var4_7 ? var10_6[var3_8] : null;
            if (var7_13 == null) {
                var1_1.skipChildren();
                var6_10 = var5_9;
            } else if (var5_9 != null) {
                try {
                    var7_13.deserializeAndSet((JsonParser)var1_1, var2_3, var5_9);
                    var6_10 = var5_9;
                }
                catch (Exception var6_11) {
                    this.wrapAndThrow((Throwable)var6_11, var5_9, var7_13.getName(), var2_3);
                    var6_10 = var5_9;
                }
            } else {
                var11_14 = var7_13.getName();
                var12_15 = var8_4.findCreatorProperty(var11_14);
                if (var12_15 != null) {
                    var7_13 = var12_15.deserialize((JsonParser)var1_1, var2_3);
                    var6_10 = var5_9;
                    if (var9_5.assignParameter(var12_15.getCreatorIndex(), var7_13)) {
                        try {
                            var6_10 = var5_9 = (var6_10 = var8_4.build(var2_3, var9_5));
                            if (var5_9.getClass() == this._beanType.getRawClass()) ** GOTO lbl40
                        }
                        catch (Exception var6_12) {
                            this.wrapAndThrow((Throwable)var6_12, this._beanType.getRawClass(), var11_14, var2_3);
                            var6_10 = var5_9;
                        }
                        throw var2_3.mappingException("Can not support implicit polymorphic deserialization for POJOs-as-Arrays style: nominal type " + this._beanType.getRawClass().getName() + ", actual type " + var5_9.getClass().getName());
                    }
                } else {
                    var6_10 = var5_9;
                    if (!var9_5.readIdProperty(var11_14)) {
                        var9_5.bufferProperty((SettableBeanProperty)var7_13, var7_13.deserialize((JsonParser)var1_1, var2_3));
                        var6_10 = var5_9;
                    }
                }
            }
lbl40: // 8 sources:
            ++var3_8;
            var5_9 = var6_10;
        }
        var1_1 = var5_9;
        if (var5_9 != null) return var1_1;
        try {
            return var8_4.build(var2_3, var9_5);
        }
        catch (Exception var1_2) {
            this.wrapInstantiationProblem(var1_2, var2_3);
            return null;
        }
    }

    protected Object _deserializeWithCreator(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (this._propertyBasedCreator != null) {
            return this._deserializeUsingPropertyBased(jsonParser, deserializationContext);
        }
        if (this._beanType.isAbstract()) {
            throw JsonMappingException.from(jsonParser, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
        }
        throw JsonMappingException.from(jsonParser, "No suitable constructor found for type " + this._beanType + ": can not instantiate from JSON object (need to add/enable type information?)");
    }

    @Override
    protected BeanDeserializerBase asArrayDeserializer() {
        return this;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Object object;
        if (jsonParser.getCurrentToken() != JsonToken.START_ARRAY) {
            return this._deserializeFromNonArray(jsonParser, deserializationContext);
        }
        if (!this._vanillaProcessing) {
            return this._deserializeNonVanilla(jsonParser, deserializationContext);
        }
        Object object2 = this._valueInstantiator.createUsingDefault(deserializationContext);
        SettableBeanProperty[] arrsettableBeanProperty = this._orderedProperties;
        int n2 = 0;
        int n3 = arrsettableBeanProperty.length;
        do {
            object = object2;
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) return object;
            if (n2 == n3) {
                if (this._ignoreAllUnknown) break;
                throw deserializationContext.mappingException("Unexpected JSON values; expected at most " + n3 + " properties (in JSON Array)");
            }
            object = arrsettableBeanProperty[n2];
            if (object != null) {
                try {
                    object.deserializeAndSet(jsonParser, deserializationContext, object2);
                }
                catch (Exception var8_8) {
                    this.wrapAndThrow((Throwable)var8_8, object2, object.getName(), deserializationContext);
                }
            } else {
                jsonParser.skipChildren();
            }
            ++n2;
        } while (true);
        do {
            object = object2;
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) return object;
            jsonParser.skipChildren();
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Object object) throws IOException, JsonProcessingException {
        if (this._injectables != null) {
            this.injectValues(deserializationContext, object);
        }
        SettableBeanProperty[] arrsettableBeanProperty = this._orderedProperties;
        int n2 = 0;
        int n3 = arrsettableBeanProperty.length;
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            if (n2 == n3) {
                if (!this._ignoreAllUnknown) {
                    throw deserializationContext.mappingException("Unexpected JSON values; expected at most " + n3 + " properties (in JSON Array)");
                }
            } else {
                SettableBeanProperty settableBeanProperty = arrsettableBeanProperty[n2];
                if (settableBeanProperty != null) {
                    try {
                        settableBeanProperty.deserializeAndSet(jsonParser, deserializationContext, object);
                    }
                    catch (Exception var8_8) {
                        this.wrapAndThrow((Throwable)var8_8, object, settableBeanProperty.getName(), deserializationContext);
                    }
                } else {
                    jsonParser.skipChildren();
                }
                ++n2;
                continue;
            }
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                jsonParser.skipChildren();
            }
            break block2;
        }
        return object;
    }

    @Override
    public Object deserializeFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this._deserializeFromNonArray(jsonParser, deserializationContext);
    }

    @Override
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer nameTransformer) {
        return this._delegate.unwrappingDeserializer(nameTransformer);
    }

    @Override
    public BeanAsArrayDeserializer withIgnorableProperties(HashSet<String> hashSet) {
        return new BeanAsArrayDeserializer(this._delegate.withIgnorableProperties(hashSet), this._orderedProperties);
    }

    @Override
    public BeanAsArrayDeserializer withObjectIdReader(ObjectIdReader objectIdReader) {
        return new BeanAsArrayDeserializer(this._delegate.withObjectIdReader(objectIdReader), this._orderedProperties);
    }
}

