/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AsArraySerializerBase<T>
extends ContainerSerializer<T>
implements ContextualSerializer {
    protected PropertySerializerMap _dynamicSerializers;
    protected final JsonSerializer<Object> _elementSerializer;
    protected final JavaType _elementType;
    protected final BeanProperty _property;
    protected final boolean _staticTyping;
    protected final TypeSerializer _valueTypeSerializer;

    protected AsArraySerializerBase(AsArraySerializerBase<?> asArraySerializerBase, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer) {
        super(asArraySerializerBase);
        this._elementType = asArraySerializerBase._elementType;
        this._staticTyping = asArraySerializerBase._staticTyping;
        this._valueTypeSerializer = typeSerializer;
        this._property = beanProperty;
        this._elementSerializer = jsonSerializer;
        this._dynamicSerializers = asArraySerializerBase._dynamicSerializers;
    }

    /*
     * Exception decompiling
     */
    protected AsArraySerializerBase(Class<?> var1_1, JavaType var2_2, boolean var3_3, TypeSerializer var4_4, BeanProperty var5_5, JsonSerializer<Object> var6_6) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:412)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:219)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:619)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:45)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:669)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
        // org.benf.cfr.reader.Main.doJar(Main.java:128)
        // org.benf.cfr.reader.Main.main(Main.java:178)
        throw new IllegalStateException("Decompilation failed");
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, JavaType object, SerializerProvider serializerProvider) throws JsonMappingException {
        object = propertySerializerMap.findAndAddSecondarySerializer((JavaType)object, serializerProvider, this._property);
        if (propertySerializerMap != object.map) {
            this._dynamicSerializers = object.map;
        }
        return object.serializer;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, Class<?> object, SerializerProvider serializerProvider) throws JsonMappingException {
        object = propertySerializerMap.findAndAddSecondarySerializer(object, serializerProvider, this._property);
        if (propertySerializerMap != object.map) {
            this._dynamicSerializers = object.map;
        }
        return object.serializer;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType jsonSerializer) throws JsonMappingException {
        if (jsonFormatVisitorWrapper == null) {
            return;
        }
        JsonArrayFormatVisitor jsonArrayFormatVisitor = jsonFormatVisitorWrapper.expectArrayFormat((JavaType)((Object)jsonSerializer));
        if (jsonArrayFormatVisitor != null) {
            JsonSerializer<Object> jsonSerializer2;
            JavaType javaType = jsonFormatVisitorWrapper.getProvider().getTypeFactory().moreSpecificType(this._elementType, (JavaType)jsonSerializer.getContentType());
            if (javaType == null) {
                throw new JsonMappingException("Could not resolve type");
            }
            jsonSerializer = jsonSerializer2 = this._elementSerializer;
            if (jsonSerializer2 == null) {
                jsonSerializer = jsonFormatVisitorWrapper.getProvider().findValueSerializer(javaType, this._property);
            }
            jsonArrayFormatVisitor.itemsFormat(jsonSerializer, javaType);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider var1_1, BeanProperty var2_2) throws JsonMappingException {
        var4_15 = var3_3 = this._valueTypeSerializer;
        if (var3_3 != null) {
            var4_15 = var3_3.forProperty(var2_2);
        }
        var3_4 = var5_16 = null;
        if (var2_2 != null) {
            var6_17 = var2_2.getMember();
            var3_5 = var5_16;
            if (var6_17 != null) {
                var7_18 = var1_1.getAnnotationIntrospector().findContentSerializer(var6_17);
                var3_6 = var5_16;
                if (var7_18 != null) {
                    var3_7 = var1_1.serializerInstance((Annotated)var6_17, var7_18);
                }
            }
        }
        var5_16 = var3_8;
        if (var3_8 == null) {
            var5_16 = this._elementSerializer;
        }
        if ((var5_16 = this.findConvertingContentSerializer((SerializerProvider)var1_1, var2_2, var5_16)) != null) ** GOTO lbl24
        var3_9 = var5_16;
        if (this._elementType == null) ** GOTO lbl25
        if (this._staticTyping && this._elementType.getRawClass() != Object.class) ** GOTO lbl-1000
        var3_10 = var5_16;
        if (this.hasContentTypeAnnotation((SerializerProvider)var1_1, var2_2)) lbl-1000: // 2 sources:
        {
            var3_12 = var1_1.findValueSerializer(this._elementType, var2_2);
        }
        ** GOTO lbl25
lbl24: // 1 sources:
        var3_14 = var1_1.handleSecondaryContextualization(var5_16, var2_2);
lbl25: // 3 sources:
        if (var3_13 != this._elementSerializer) return this.withResolved(var2_2, var4_15, var3_13);
        if (var2_2 != this._property) return this.withResolved(var2_2, var4_15, var3_13);
        var1_1 = this;
        if (this._valueTypeSerializer == var4_15) return var1_1;
        return this.withResolved(var2_2, var4_15, var3_13);
    }

    @Override
    public JsonSerializer<?> getContentSerializer() {
        return this._elementSerializer;
    }

    @Override
    public JavaType getContentType() {
        return this._elementType;
    }

    @Override
    public JsonNode getSchema(SerializerProvider object, Type object2) throws JsonMappingException {
        Object object3;
        ObjectNode objectNode = this.createSchemaNode("array", true);
        Object object4 = null;
        if (object2 != null) {
            object4 = object3 = object.constructType((Type)object2).getContentType();
            if (object3 == null) {
                object4 = object3;
                if (object2 instanceof ParameterizedType) {
                    object2 = ((ParameterizedType)object2).getActualTypeArguments();
                    object4 = object3;
                    if (object2.length == 1) {
                        object4 = object.constructType((Type)object2[0]);
                    }
                }
            }
        }
        object3 = object4;
        if (object4 == null) {
            object3 = object4;
            if (this._elementType != null) {
                object3 = this._elementType;
            }
        }
        if (object3 != null) {
            object2 = object4 = null;
            if (object3.getRawClass() != Object.class) {
                object3 = object.findValueSerializer((JavaType)object3, this._property);
                object2 = object4;
                if (object3 instanceof SchemaAware) {
                    object2 = ((SchemaAware)object3).getSchema((SerializerProvider)object, null);
                }
            }
            object = object2;
            if (object2 == null) {
                object = JsonSchema.getDefaultSchemaNode();
            }
            objectNode.put("items", (JsonNode)object);
        }
        return objectNode;
    }

    @Override
    public final void serialize(T t2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        if (serializerProvider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) && this.hasSingleElement(t2)) {
            this.serializeContents(t2, jsonGenerator, serializerProvider);
            return;
        }
        jsonGenerator.writeStartArray();
        this.serializeContents(t2, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndArray();
    }

    protected abstract void serializeContents(T var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonGenerationException;

    @Override
    public void serializeWithType(T t2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForArray(t2, jsonGenerator);
        this.serializeContents(t2, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffixForArray(t2, jsonGenerator);
    }

    public abstract AsArraySerializerBase<T> withResolved(BeanProperty var1, TypeSerializer var2, JsonSerializer<?> var3);
}

