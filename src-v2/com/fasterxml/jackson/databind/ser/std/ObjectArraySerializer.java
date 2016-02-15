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
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
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
import com.fasterxml.jackson.databind.ser.std.ArraySerializerBase;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

@JacksonStdImpl
public class ObjectArraySerializer
extends ArraySerializerBase<Object[]>
implements ContextualSerializer {
    protected PropertySerializerMap _dynamicSerializers;
    protected JsonSerializer<Object> _elementSerializer;
    protected final JavaType _elementType;
    protected final boolean _staticTyping;
    protected final TypeSerializer _valueTypeSerializer;

    public ObjectArraySerializer(JavaType javaType, boolean bl2, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) {
        super(Object[].class, (BeanProperty)null);
        this._elementType = javaType;
        this._staticTyping = bl2;
        this._valueTypeSerializer = typeSerializer;
        this._dynamicSerializers = PropertySerializerMap.emptyMap();
        this._elementSerializer = jsonSerializer;
    }

    public ObjectArraySerializer(ObjectArraySerializer objectArraySerializer, BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer) {
        super(objectArraySerializer, beanProperty);
        this._elementType = objectArraySerializer._elementType;
        this._valueTypeSerializer = typeSerializer;
        this._staticTyping = objectArraySerializer._staticTyping;
        this._dynamicSerializers = objectArraySerializer._dynamicSerializers;
        this._elementSerializer = jsonSerializer;
    }

    public ObjectArraySerializer(ObjectArraySerializer objectArraySerializer, TypeSerializer typeSerializer) {
        super(objectArraySerializer);
        this._elementType = objectArraySerializer._elementType;
        this._valueTypeSerializer = typeSerializer;
        this._staticTyping = objectArraySerializer._staticTyping;
        this._dynamicSerializers = objectArraySerializer._dynamicSerializers;
        this._elementSerializer = objectArraySerializer._elementSerializer;
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

    @Override
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer typeSerializer) {
        return new ObjectArraySerializer(this._elementType, this._staticTyping, typeSerializer, this._elementSerializer);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType jsonSerializer) throws JsonMappingException {
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
     * Enabled aggressive block sorting
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializer;
        void var3_8;
        TypeSerializer typeSerializer;
        void var3_13;
        TypeSerializer typeSerializer2 = typeSerializer = this._valueTypeSerializer;
        if (typeSerializer != null) {
            typeSerializer2 = typeSerializer.forProperty(beanProperty);
        }
        JsonSerializer<Object> jsonSerializer2 = jsonSerializer = null;
        if (beanProperty != null) {
            AnnotatedMember annotatedMember = beanProperty.getMember();
            JsonSerializer<Object> jsonSerializer3 = jsonSerializer;
            if (annotatedMember != null) {
                Object object = serializerProvider.getAnnotationIntrospector().findContentSerializer(annotatedMember);
                JsonSerializer<Object> jsonSerializer4 = jsonSerializer;
                if (object != null) {
                    JsonSerializer<Object> jsonSerializer5 = serializerProvider.serializerInstance(annotatedMember, object);
                }
            }
        }
        jsonSerializer = var3_8;
        if (var3_8 == null) {
            jsonSerializer = this._elementSerializer;
        }
        if ((jsonSerializer = this.findConvertingContentSerializer(serializerProvider, beanProperty, jsonSerializer)) != null) {
            JsonSerializer jsonSerializer6 = serializerProvider.handleSecondaryContextualization(jsonSerializer, beanProperty);
            return this.withResolved(beanProperty, typeSerializer2, var3_13);
        }
        JsonSerializer<Object> jsonSerializer7 = jsonSerializer;
        if (this._elementType == null) return this.withResolved(beanProperty, typeSerializer2, var3_13);
        if (!this._staticTyping) {
            JsonSerializer<Object> jsonSerializer8 = jsonSerializer;
            if (!this.hasContentTypeAnnotation(serializerProvider, beanProperty)) return this.withResolved(beanProperty, typeSerializer2, var3_13);
        }
        JsonSerializer<Object> jsonSerializer9 = serializerProvider.findValueSerializer(this._elementType, beanProperty);
        return this.withResolved(beanProperty, typeSerializer2, var3_13);
    }

    @Override
    public JsonSerializer<?> getContentSerializer() {
        return this._elementSerializer;
    }

    @Override
    public JavaType getContentType() {
        return this._elementType;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public JsonNode getSchema(SerializerProvider object, Type object2) throws JsonMappingException {
        ObjectNode objectNode = this.createSchemaNode("array", true);
        if (object2 == null) return objectNode;
        if (!(object2 = object.constructType((Type)object2)).isArrayType()) return objectNode;
        if ((object2 = ((ArrayType)object2).getContentType().getRawClass()) == Object.class) {
            objectNode.put("items", JsonSchema.getDefaultSchemaNode());
            return objectNode;
        }
        object = (object2 = object.findValueSerializer(object2, this._property)) instanceof SchemaAware ? ((SchemaAware)object2).getSchema((SerializerProvider)object, null) : JsonSchema.getDefaultSchemaNode();
        objectNode.put("items", (JsonNode)object);
        return objectNode;
    }

    @Override
    public boolean hasSingleElement(Object[] arrobject) {
        if (arrobject.length == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty(Object[] arrobject) {
        if (arrobject == null || arrobject.length == 0) {
            return true;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void serializeContents(Object[] var1_1, JsonGenerator var2_5, SerializerProvider var3_6) throws IOException, JsonGenerationException {
        var6_7 = var1_1.length;
        if (var6_7 == 0) {
            return;
        }
        if (this._elementSerializer != null) {
            this.serializeContentsUsing(var1_1, var2_5, var3_6, this._elementSerializer);
            return;
        }
        if (this._valueTypeSerializer != null) {
            this.serializeTypedContents(var1_1, var2_5, var3_6);
            return;
        }
        var5_8 = 0;
        var4_9 = 0;
        var8_10 = null;
        var11_11 = this._dynamicSerializers;
        while (var4_9 < var6_7) {
            block13 : {
                var9_13 = var1_1[var4_9];
                if (var9_13 != null) ** GOTO lbl23
                var8_10 = var9_13;
                var5_8 = var4_9;
                try {
                    var3_6.defaultSerializeNull(var2_5);
                    break block13;
lbl23: // 1 sources:
                    var8_10 = var9_13;
                    var5_8 = var4_9;
                    var12_15 = var9_13.getClass();
                    var8_10 = var9_13;
                    var5_8 = var4_9;
                    var7_12 = var10_14 = var11_11.serializerFor(var12_15);
                    if (var10_14 == null) {
                        var8_10 = var9_13;
                        var5_8 = var4_9;
                        if (this._elementType.hasGenericTypes()) {
                            var8_10 = var9_13;
                            var5_8 = var4_9;
                            var7_12 = this._findAndAddDynamic(var11_11, var3_6.constructSpecializedType(this._elementType, var12_15), var3_6);
                        } else {
                            var8_10 = var9_13;
                            var5_8 = var4_9;
                            var7_12 = this._findAndAddDynamic(var11_11, var12_15, var3_6);
                        }
                    }
                    var8_10 = var9_13;
                    var5_8 = var4_9;
                    var7_12.serialize(var9_13, var2_5, var3_6);
                }
                catch (IOException var1_2) {
                    throw var1_2;
                }
                catch (Exception var1_3) {
                    while (var1_4 instanceof InvocationTargetException && var1_4.getCause() != null) {
                        var1_4 = var1_4.getCause();
                    }
                    if (var1_4 instanceof Error == false) throw JsonMappingException.wrapWithPath(var1_4, var8_10, var5_8);
                    throw (Error)var1_4;
                }
            }
            ++var4_9;
        }
    }

    /*
     * Exception decompiling
     */
    public void serializeContentsUsing(Object[] var1_1, JsonGenerator var2_5, SerializerProvider var3_6, JsonSerializer<Object> var4_7) throws IOException, JsonGenerationException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.lang.IllegalStateException: Backjump on non jumping statement [] lbl14 : TryStatement: try { 1[TRYBLOCK]

        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner$1.call(Cleaner.java:44)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner$1.call(Cleaner.java:22)
        // org.benf.cfr.reader.util.graph.GraphVisitorDFS.process(GraphVisitorDFS.java:68)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner.removeUnreachableCode(Cleaner.java:54)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.RemoveDeterministicJumps.apply(RemoveDeterministicJumps.java:35)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:507)
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void serializeTypedContents(Object[] arrobject, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        int n2 = arrobject.length;
        TypeSerializer typeSerializer = this._valueTypeSerializer;
        int n3 = 0;
        int n4 = 0;
        Object object = null;
        PropertySerializerMap propertySerializerMap = this._dynamicSerializers;
        while (n4 < n2) {
            JsonSerializer<Object> jsonSerializer;
            Object object2;
            block11 : {
                JsonSerializer<Object> jsonSerializer2;
                object2 = arrobject[n4];
                if (object2 == null) {
                    object = object2;
                    n3 = n4;
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                }
                object = object2;
                n3 = n4;
                Class class_ = object2.getClass();
                object = object2;
                n3 = n4;
                jsonSerializer = jsonSerializer2 = propertySerializerMap.serializerFor(class_);
                if (jsonSerializer2 != null) break block11;
                object = object2;
                n3 = n4;
                jsonSerializer = this._findAndAddDynamic(propertySerializerMap, class_, serializerProvider);
            }
            object = object2;
            n3 = n4;
            try {
                jsonSerializer.serializeWithType(object2, jsonGenerator, serializerProvider, typeSerializer);
            }
            catch (IOException var1_2) {
                throw var1_2;
            }
            catch (Exception var1_3) {
                Throwable throwable;
                while (throwable instanceof InvocationTargetException && throwable.getCause() != null) {
                    throwable = throwable.getCause();
                }
                if (!(throwable instanceof Error)) throw JsonMappingException.wrapWithPath(throwable, object, n3);
                throw (Error)throwable;
            }
            ++n4;
        }
    }

    public ObjectArraySerializer withResolved(BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer) {
        if (this._property == beanProperty && jsonSerializer == this._elementSerializer && this._valueTypeSerializer == typeSerializer) {
            return this;
        }
        return new ObjectArraySerializer(this, beanProperty, typeSerializer, jsonSerializer);
    }
}

