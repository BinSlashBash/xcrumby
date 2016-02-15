/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.util.EnumValues;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@JacksonStdImpl
public class EnumMapSerializer
extends ContainerSerializer<EnumMap<? extends Enum<?>, ?>>
implements ContextualSerializer {
    protected final EnumValues _keyEnums;
    protected final BeanProperty _property;
    protected final boolean _staticTyping;
    protected final JsonSerializer<Object> _valueSerializer;
    protected final JavaType _valueType;
    protected final TypeSerializer _valueTypeSerializer;

    /*
     * Exception decompiling
     */
    public EnumMapSerializer(JavaType var1_1, boolean var2_2, EnumValues var3_3, TypeSerializer var4_4, JsonSerializer<Object> var5_5) {
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

    public EnumMapSerializer(EnumMapSerializer enumMapSerializer, BeanProperty beanProperty, JsonSerializer<?> jsonSerializer) {
        super(enumMapSerializer);
        this._property = beanProperty;
        this._staticTyping = enumMapSerializer._staticTyping;
        this._valueType = enumMapSerializer._valueType;
        this._keyEnums = enumMapSerializer._keyEnums;
        this._valueTypeSerializer = enumMapSerializer._valueTypeSerializer;
        this._valueSerializer = jsonSerializer;
    }

    public EnumMapSerializer _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new EnumMapSerializer(this._valueType, this._staticTyping, this._keyEnums, typeSerializer, this._valueSerializer);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType jsonSerializer) throws JsonMappingException {
        JsonObjectFormatVisitor jsonObjectFormatVisitor;
        if (jsonFormatVisitorWrapper != null && (jsonObjectFormatVisitor = jsonFormatVisitorWrapper.expectObjectFormat((JavaType)((Object)jsonSerializer))) != null) {
            Object object;
            Object object2;
            JsonSerializer<Object> jsonSerializer2 = jsonSerializer.containedType(1);
            Object object3 = object = this._valueSerializer;
            if (object == null) {
                object3 = object;
                if (jsonSerializer2 != null) {
                    object3 = jsonFormatVisitorWrapper.getProvider().findValueSerializer((JavaType)((Object)jsonSerializer2), this._property);
                }
            }
            object = jsonSerializer2;
            if (jsonSerializer2 == null) {
                object = jsonFormatVisitorWrapper.getProvider().constructType(Object.class);
            }
            jsonSerializer2 = object2 = this._keyEnums;
            if (object2 == null) {
                jsonSerializer2 = jsonSerializer.containedType(0);
                if (jsonSerializer2 == null) {
                    throw new IllegalStateException("Can not resolve Enum type of EnumMap: " + jsonSerializer);
                }
                jsonSerializer2 = jsonFormatVisitorWrapper.getProvider().findValueSerializer((JavaType)((Object)jsonSerializer2), this._property);
                if (!(jsonSerializer2 instanceof EnumSerializer)) {
                    throw new IllegalStateException("Can not resolve Enum type of EnumMap: " + jsonSerializer);
                }
                jsonSerializer2 = ((EnumSerializer)jsonSerializer2).getEnumValues();
            }
            for (Map.Entry entry : jsonSerializer2.internalMap().entrySet()) {
                object2 = entry.getValue().getValue();
                jsonSerializer = object3;
                if (object3 == null) {
                    jsonSerializer = jsonFormatVisitorWrapper.getProvider().findValueSerializer(entry.getKey().getClass(), this._property);
                }
                jsonObjectFormatVisitor.property((String)object2, jsonSerializer, (JavaType)object);
                object3 = jsonSerializer;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider object, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        void var3_11;
        JsonSerializer<Object> jsonSerializer2;
        void var3_8;
        JsonSerializer<Object> jsonSerializer3 = jsonSerializer2 = null;
        if (beanProperty != null) {
            AnnotatedMember annotatedMember = beanProperty.getMember();
            JsonSerializer<Object> jsonSerializer4 = jsonSerializer2;
            if (annotatedMember != null) {
                Object object2 = object.getAnnotationIntrospector().findContentSerializer(annotatedMember);
                JsonSerializer<Object> jsonSerializer5 = jsonSerializer2;
                if (object2 != null) {
                    JsonSerializer<Object> jsonSerializer6 = object.serializerInstance(annotatedMember, object2);
                }
            }
        }
        jsonSerializer2 = var3_8;
        if (var3_8 == null) {
            jsonSerializer2 = this._valueSerializer;
        }
        if ((jsonSerializer = this.findConvertingContentSerializer((SerializerProvider)object, beanProperty, jsonSerializer2)) == null) {
            if (this._staticTyping) {
                return this.withValueSerializer(beanProperty, object.findValueSerializer(this._valueType, beanProperty));
            }
        } else {
            JsonSerializer jsonSerializer7 = object.handleSecondaryContextualization(jsonSerializer, beanProperty);
        }
        object = this;
        if (var3_11 == this._valueSerializer) return object;
        return this.withValueSerializer(beanProperty, var3_11);
    }

    @Override
    public JsonSerializer<?> getContentSerializer() {
        return this._valueSerializer;
    }

    @Override
    public JavaType getContentType() {
        return this._valueType;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type jsonSerializer) throws JsonMappingException {
        Object object;
        ObjectNode objectNode = this.createSchemaNode("object", true);
        if (jsonSerializer instanceof ParameterizedType && (object = ((ParameterizedType)((Object)jsonSerializer)).getActualTypeArguments()).length == 2) {
            jsonSerializer = serializerProvider.constructType(object[0]);
            object = serializerProvider.constructType(object[1]);
            ObjectNode objectNode2 = JsonNodeFactory.instance.objectNode();
            for (Enum enum_ : (Enum[])jsonSerializer.getRawClass().getEnumConstants()) {
                jsonSerializer = serializerProvider.findValueSerializer(object.getRawClass(), this._property);
                jsonSerializer = jsonSerializer instanceof SchemaAware ? ((SchemaAware)((Object)jsonSerializer)).getSchema(serializerProvider, null) : JsonSchema.getDefaultSchemaNode();
                objectNode2.put(serializerProvider.getConfig().getAnnotationIntrospector().findEnumValue(enum_), (JsonNode)((Object)jsonSerializer));
            }
            objectNode.put("properties", objectNode2);
        }
        return objectNode;
    }

    @Override
    public boolean hasSingleElement(EnumMap<? extends Enum<?>, ?> enumMap) {
        if (enumMap.size() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty(EnumMap<? extends Enum<?>, ?> enumMap) {
        if (enumMap == null || enumMap.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public void serialize(EnumMap<? extends Enum<?>, ?> enumMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartObject();
        if (!enumMap.isEmpty()) {
            this.serializeContents(enumMap, jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndObject();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void serializeContents(EnumMap<? extends Enum<?>, ?> var1_1, JsonGenerator var2_2, SerializerProvider var3_3) throws IOException, JsonGenerationException {
        if (this._valueSerializer != null) {
            this.serializeContentsUsing(var1_1, var2_2, var3_3, this._valueSerializer);
            return;
        }
        var5_4 = null;
        var7_5 = null;
        var8_6 = this._keyEnums;
        var4_8 = var3_3.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES) == false;
        var10_9 = this._valueTypeSerializer;
        var11_10 = var1_1.entrySet().iterator();
        while (var11_10.hasNext() != false) {
            var12_13 = var11_10.next();
            var13_14 = var12_13.getValue();
            if (var4_8 && var13_14 == null) continue;
            var9_12 = var12_13.getKey();
            var6_11 = var8_6;
            if (var8_6 == null) {
                var6_11 = ((EnumSerializer)((StdSerializer)var3_3.findValueSerializer(var9_12.getDeclaringClass(), this._property))).getEnumValues();
            }
            var2_2.writeFieldName(var6_11.serializedValueFor(var9_12));
            if (var13_14 == null) {
                var3_3.defaultSerializeNull(var2_2);
                var8_6 = var6_11;
                continue;
            }
            var9_12 = var13_14.getClass();
            if (var9_12 == var7_5) {
                var8_6 = var5_4;
            } else {
                var5_4 = var8_6 = var3_3.findValueSerializer(var9_12, this._property);
                var7_5 = var9_12;
            }
            if (var10_9 != null) ** GOTO lbl34
            try {
                var8_6.serialize(var13_14, var2_2, var3_3);
                var8_6 = var6_11;
lbl34: // 1 sources:
                var8_6.serializeWithType(var13_14, var2_2, var3_3, var10_9);
                var8_6 = var6_11;
                continue;
            }
            catch (Exception var8_7) {
                this.wrapAndThrow(var3_3, (Throwable)var8_7, var1_1, var12_13.getKey().name());
                var8_6 = var6_11;
                continue;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void serializeContentsUsing(EnumMap<? extends Enum<?>, ?> var1_1, JsonGenerator var2_2, SerializerProvider var3_3, JsonSerializer<Object> var4_4) throws IOException, JsonGenerationException {
        var7_5 = this._keyEnums;
        var5_7 = var3_3.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES) == false;
        var8_8 = this._valueTypeSerializer;
        var9_9 = var1_1.entrySet().iterator();
        while (var9_9.hasNext() != false) {
            var10_11 = var9_9.next();
            var11_12 = var10_11.getValue();
            if (var5_7 && var11_12 == null) continue;
            var12_13 = var10_11.getKey();
            var6_10 = var7_5;
            if (var7_5 == null) {
                var6_10 = ((EnumSerializer)((StdSerializer)var3_3.findValueSerializer(var12_13.getDeclaringClass(), this._property))).getEnumValues();
            }
            var2_2.writeFieldName(var6_10.serializedValueFor(var12_13));
            if (var11_12 == null) {
                var3_3.defaultSerializeNull(var2_2);
                var7_5 = var6_10;
                continue;
            }
            if (var8_8 != null) ** GOTO lbl23
            try {
                var4_4.serialize(var11_12, var2_2, var3_3);
                var7_5 = var6_10;
lbl23: // 1 sources:
                var4_4.serializeWithType(var11_12, var2_2, var3_3, var8_8);
                var7_5 = var6_10;
                continue;
            }
            catch (Exception var7_6) {
                this.wrapAndThrow(var3_3, (Throwable)var7_6, var1_1, var10_11.getKey().name());
                var7_5 = var6_10;
                continue;
            }
        }
    }

    @Override
    public void serializeWithType(EnumMap<? extends Enum<?>, ?> enumMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        typeSerializer.writeTypePrefixForObject(enumMap, jsonGenerator);
        if (!enumMap.isEmpty()) {
            this.serializeContents(enumMap, jsonGenerator, serializerProvider);
        }
        typeSerializer.writeTypeSuffixForObject(enumMap, jsonGenerator);
    }

    public EnumMapSerializer withValueSerializer(BeanProperty beanProperty, JsonSerializer<?> jsonSerializer) {
        if (this._property == beanProperty && jsonSerializer == this._valueSerializer) {
            return this;
        }
        return new EnumMapSerializer(this, beanProperty, jsonSerializer);
    }
}

