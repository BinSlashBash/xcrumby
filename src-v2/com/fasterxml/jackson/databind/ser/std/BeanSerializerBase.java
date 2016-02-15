/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.AnyGetterWriter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class BeanSerializerBase
extends StdSerializer<Object>
implements ContextualSerializer,
ResolvableSerializer,
JsonFormatVisitable,
SchemaAware {
    protected static final PropertyName NAME_FOR_OBJECT_REF = new PropertyName("#object-ref");
    protected static final BeanPropertyWriter[] NO_PROPS = new BeanPropertyWriter[0];
    protected final AnyGetterWriter _anyGetterWriter;
    protected final BeanPropertyWriter[] _filteredProps;
    protected final ObjectIdWriter _objectIdWriter;
    protected final Object _propertyFilterId;
    protected final BeanPropertyWriter[] _props;
    protected final JsonFormat.Shape _serializationShape;
    protected final AnnotatedMember _typeId;

    /*
     * Enabled aggressive block sorting
     */
    protected BeanSerializerBase(JavaType object, BeanSerializerBuilder beanSerializerBuilder, BeanPropertyWriter[] arrbeanPropertyWriter, BeanPropertyWriter[] arrbeanPropertyWriter2) {
        Object var5_5 = null;
        super((JavaType)object);
        this._props = arrbeanPropertyWriter;
        this._filteredProps = arrbeanPropertyWriter2;
        if (beanSerializerBuilder == null) {
            this._typeId = null;
            this._anyGetterWriter = null;
            this._propertyFilterId = null;
            this._objectIdWriter = null;
            this._serializationShape = null;
            return;
        }
        this._typeId = beanSerializerBuilder.getTypeId();
        this._anyGetterWriter = beanSerializerBuilder.getAnyGetter();
        this._propertyFilterId = beanSerializerBuilder.getFilterId();
        this._objectIdWriter = beanSerializerBuilder.getObjectIdWriter();
        object = beanSerializerBuilder.getBeanDescription().findExpectedFormat(null);
        object = object == null ? var5_5 : object.getShape();
        this._serializationShape = object;
    }

    protected BeanSerializerBase(BeanSerializerBase beanSerializerBase) {
        this(beanSerializerBase, beanSerializerBase._props, beanSerializerBase._filteredProps);
    }

    protected BeanSerializerBase(BeanSerializerBase beanSerializerBase, ObjectIdWriter objectIdWriter) {
        this(beanSerializerBase, objectIdWriter, beanSerializerBase._propertyFilterId);
    }

    protected BeanSerializerBase(BeanSerializerBase beanSerializerBase, ObjectIdWriter objectIdWriter, Object object) {
        super(beanSerializerBase._handledType);
        this._props = beanSerializerBase._props;
        this._filteredProps = beanSerializerBase._filteredProps;
        this._typeId = beanSerializerBase._typeId;
        this._anyGetterWriter = beanSerializerBase._anyGetterWriter;
        this._objectIdWriter = objectIdWriter;
        this._propertyFilterId = object;
        this._serializationShape = beanSerializerBase._serializationShape;
    }

    protected BeanSerializerBase(BeanSerializerBase beanSerializerBase, NameTransformer nameTransformer) {
        this(beanSerializerBase, BeanSerializerBase.rename(beanSerializerBase._props, nameTransformer), BeanSerializerBase.rename(beanSerializerBase._filteredProps, nameTransformer));
    }

    public BeanSerializerBase(BeanSerializerBase beanSerializerBase, BeanPropertyWriter[] arrbeanPropertyWriter, BeanPropertyWriter[] arrbeanPropertyWriter2) {
        super(beanSerializerBase._handledType);
        this._props = arrbeanPropertyWriter;
        this._filteredProps = arrbeanPropertyWriter2;
        this._typeId = beanSerializerBase._typeId;
        this._anyGetterWriter = beanSerializerBase._anyGetterWriter;
        this._objectIdWriter = beanSerializerBase._objectIdWriter;
        this._propertyFilterId = beanSerializerBase._propertyFilterId;
        this._serializationShape = beanSerializerBase._serializationShape;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected BeanSerializerBase(BeanSerializerBase beanSerializerBase, String[] arrbeanPropertyWriter) {
        Object var5_3 = null;
        super(beanSerializerBase._handledType);
        HashSet<BeanPropertyWriter> hashSet = ArrayBuilders.arrayToSet(arrbeanPropertyWriter);
        BeanPropertyWriter[] arrbeanPropertyWriter2 = beanSerializerBase._props;
        BeanPropertyWriter[] arrbeanPropertyWriter3 = beanSerializerBase._filteredProps;
        int n2 = arrbeanPropertyWriter2.length;
        ArrayList<BeanPropertyWriter> arrayList = new ArrayList<BeanPropertyWriter>(n2);
        arrbeanPropertyWriter = arrbeanPropertyWriter3 == null ? null : new ArrayList(n2);
        for (int i2 = 0; i2 < n2; ++i2) {
            BeanPropertyWriter beanPropertyWriter = arrbeanPropertyWriter2[i2];
            if (hashSet.contains(beanPropertyWriter.getName())) continue;
            arrayList.add(beanPropertyWriter);
            if (arrbeanPropertyWriter3 == null) continue;
            arrbeanPropertyWriter.add(arrbeanPropertyWriter3[i2]);
        }
        this._props = arrayList.toArray(new BeanPropertyWriter[arrayList.size()]);
        arrbeanPropertyWriter = arrbeanPropertyWriter == null ? var5_3 : arrbeanPropertyWriter.toArray(new BeanPropertyWriter[arrbeanPropertyWriter.size()]);
        this._filteredProps = arrbeanPropertyWriter;
        this._typeId = beanSerializerBase._typeId;
        this._anyGetterWriter = beanSerializerBase._anyGetterWriter;
        this._objectIdWriter = beanSerializerBase._objectIdWriter;
        this._propertyFilterId = beanSerializerBase._propertyFilterId;
        this._serializationShape = beanSerializerBase._serializationShape;
    }

    private final String _customTypeId(Object object) {
        if ((object = this._typeId.getValue(object)) == null) {
            return "";
        }
        if (object instanceof String) {
            return (String)object;
        }
        return object.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static final BeanPropertyWriter[] rename(BeanPropertyWriter[] arrbeanPropertyWriter, NameTransformer nameTransformer) {
        if (arrbeanPropertyWriter == null) return arrbeanPropertyWriter;
        if (arrbeanPropertyWriter.length == 0) return arrbeanPropertyWriter;
        if (nameTransformer == null) return arrbeanPropertyWriter;
        if (nameTransformer == NameTransformer.NOP) {
            return arrbeanPropertyWriter;
        }
        int n2 = arrbeanPropertyWriter.length;
        BeanPropertyWriter[] arrbeanPropertyWriter2 = new BeanPropertyWriter[n2];
        int n3 = 0;
        do {
            Object object = arrbeanPropertyWriter2;
            if (n3 >= n2) return object;
            object = arrbeanPropertyWriter[n3];
            if (object != null) {
                arrbeanPropertyWriter2[n3] = object.rename(nameTransformer);
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _serializeObjectId(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer, WritableObjectId writableObjectId) throws IOException, JsonProcessingException, JsonGenerationException {
        ObjectIdWriter objectIdWriter = this._objectIdWriter;
        String string2 = this._typeId == null ? null : this._customTypeId(object);
        if (string2 == null) {
            typeSerializer.writeTypePrefixForObject(object, jsonGenerator);
        } else {
            typeSerializer.writeCustomTypePrefixForObject(object, jsonGenerator, string2);
        }
        writableObjectId.writeAsField(jsonGenerator, serializerProvider, objectIdWriter);
        if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(object, jsonGenerator, serializerProvider);
        } else {
            this.serializeFields(object, jsonGenerator, serializerProvider);
        }
        if (string2 == null) {
            typeSerializer.writeTypeSuffixForObject(object, jsonGenerator);
            return;
        }
        typeSerializer.writeCustomTypeSuffixForObject(object, jsonGenerator, string2);
    }

    protected final void _serializeWithObjectId(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        ObjectIdWriter objectIdWriter = this._objectIdWriter;
        WritableObjectId writableObjectId = serializerProvider.findObjectId(object, objectIdWriter.generator);
        if (writableObjectId.writeAsId(jsonGenerator, serializerProvider, objectIdWriter)) {
            return;
        }
        Object object2 = writableObjectId.generateId(object);
        if (objectIdWriter.alwaysAsId) {
            objectIdWriter.serializer.serialize(object2, jsonGenerator, serializerProvider);
            return;
        }
        this._serializeObjectId(object, jsonGenerator, serializerProvider, typeSerializer, writableObjectId);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected final void _serializeWithObjectId(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, boolean bl2) throws IOException, JsonGenerationException {
        ObjectIdWriter objectIdWriter = this._objectIdWriter;
        WritableObjectId writableObjectId = serializerProvider.findObjectId(object, objectIdWriter.generator);
        if (writableObjectId.writeAsId(jsonGenerator, serializerProvider, objectIdWriter)) {
            return;
        }
        Object object2 = writableObjectId.generateId(object);
        if (objectIdWriter.alwaysAsId) {
            objectIdWriter.serializer.serialize(object2, jsonGenerator, serializerProvider);
            return;
        }
        if (bl2) {
            jsonGenerator.writeStartObject();
        }
        writableObjectId.writeAsField(jsonGenerator, serializerProvider, objectIdWriter);
        if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(object, jsonGenerator, serializerProvider);
        } else {
            this.serializeFields(object, jsonGenerator, serializerProvider);
        }
        if (!bl2) return;
        jsonGenerator.writeEndObject();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType object) throws JsonMappingException {
        if (jsonFormatVisitorWrapper == null || (object = jsonFormatVisitorWrapper.expectObjectFormat((JavaType)object)) == null) {
            return;
        }
        if (this._propertyFilterId != null) {
            PropertyFilter propertyFilter = this.findPropertyFilter(jsonFormatVisitorWrapper.getProvider(), this._propertyFilterId, null);
            int n2 = 0;
            while (n2 < this._props.length) {
                propertyFilter.depositSchemaProperty((PropertyWriter)this._props[n2], (JsonObjectFormatVisitor)object, jsonFormatVisitorWrapper.getProvider());
                ++n2;
            }
            return;
        }
        int n3 = 0;
        while (n3 < this._props.length) {
            this._props[n3].depositSchemaProperty((JsonObjectFormatVisitor)object);
            ++n3;
        }
    }

    protected abstract BeanSerializerBase asArraySerializer();

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider var1_1, BeanProperty var2_2) throws JsonMappingException {
        var9_3 = this._objectIdWriter;
        var8_4 = null;
        var11_5 = null;
        var13_6 = var1_1.getAnnotationIntrospector();
        var6_7 = var2_2 == null || var13_6 == null ? null : var2_2.getMember();
        var7_8 = var11_5;
        var10_9 = var9_3;
        if (var6_7 == null) ** GOTO lbl38
        var12_10 = var13_6.findPropertiesToIgnore((Annotated)var6_7);
        var5_11 = var13_6.findObjectIdInfo((Annotated)var6_7);
        if (var5_11 != null) ** GOTO lbl17
        var5_11 = var9_3;
        if (var9_3 != null) {
            var5_11 = var13_6.findObjectReferenceInfo((Annotated)var6_7, new ObjectIdInfo(BeanSerializerBase.NAME_FOR_OBJECT_REF, null, null, null));
            var5_11 = this._objectIdWriter.withAlwaysAsId(var5_11.getAlwaysAsId());
        }
        ** GOTO lbl24
lbl17: // 1 sources:
        var5_11 = var13_6.findObjectReferenceInfo((Annotated)var6_7, (ObjectIdInfo)var5_11);
        var7_8 = var5_11.getGeneratorType();
        var8_4 = var1_1.constructType((Type)var7_8);
        var8_4 = var1_1.getTypeFactory().findTypeParameters((JavaType)var8_4, ObjectIdGenerator.class)[0];
        if (var7_8 == ObjectIdGenerators.PropertyGenerator.class) ** GOTO lbl64
        var7_8 = var1_1.objectIdGeneratorInstance((Annotated)var6_7, (ObjectIdInfo)var5_11);
        var5_11 = ObjectIdWriter.construct((JavaType)var8_4, var5_11.getPropertyName(), var7_8, var5_11.getAlwaysAsId());
lbl24: // 3 sources:
        do {
            var9_3 = var13_6.findFilterId((Annotated)var6_7);
            var8_4 = var12_10;
            var7_8 = var11_5;
            var10_9 = var5_11;
            if (var9_3 == null) ** GOTO lbl38
            if (this._propertyFilterId == null) ** GOTO lbl-1000
            var8_4 = var12_10;
            var7_8 = var11_5;
            var10_9 = var5_11;
            if (!var9_3.equals(this._propertyFilterId)) lbl-1000: // 2 sources:
            {
                var7_8 = var9_3;
                var10_9 = var5_11;
                var8_4 = var12_10;
            }
lbl38: // 5 sources:
            var5_11 = var9_3 = this;
            if (var10_9 != null) {
                var1_1 = var10_9.withSerializer(var1_1.findValueSerializer(var10_9.idType, (BeanProperty)var2_2));
                var5_11 = var9_3;
                if (var1_1 != this._objectIdWriter) {
                    var5_11 = var9_3.withObjectIdWriter((ObjectIdWriter)var1_1);
                }
            }
            var2_2 = var5_11;
            if (var8_4 != null) {
                var2_2 = var5_11;
                if (var8_4.length != 0) {
                    var2_2 = var5_11.withIgnorals((String[])var8_4);
                }
            }
            var1_1 = var2_2;
            if (var7_8 != null) {
                var1_1 = var2_2.withFilterId(var7_8);
            }
            var2_2 = var5_11 = null;
            if (var6_7 != null) {
                var6_7 = var13_6.findFormat((Annotated)var6_7);
                var2_2 = var5_11;
                if (var6_7 != null) {
                    var2_2 = var6_7.getShape();
                }
            }
            var5_11 = var2_2;
            if (var2_2 == null) {
                var5_11 = this._serializationShape;
            }
            var2_2 = var1_1;
            if (var5_11 != JsonFormat.Shape.ARRAY) return var2_2;
            return var1_1.asArraySerializer();
            break;
        } while (true);
lbl64: // 1 sources:
        var8_4 = var5_11.getPropertyName().getSimpleName();
        var3_12 = 0;
        var4_13 = this._props.length;
        do {
            if (var3_12 == var4_13) {
                throw new IllegalArgumentException("Invalid Object Id definition for " + this._handledType.getName() + ": can not find property with name '" + (String)var8_4 + "'");
            }
            var7_8 = this._props[var3_12];
            if (var8_4.equals(var7_8.getName())) {
                if (var3_12 > 0) {
                    System.arraycopy(this._props, 0, this._props, 1, var3_12);
                    this._props[0] = var7_8;
                    if (this._filteredProps != null) {
                        var8_4 = this._filteredProps[var3_12];
                        System.arraycopy(this._filteredProps, 0, this._filteredProps, 1, var3_12);
                        this._filteredProps[0] = var8_4;
                    }
                }
                var8_4 = var7_8.getType();
                var7_8 = new PropertyBasedObjectIdGenerator((ObjectIdInfo)var5_11, (BeanPropertyWriter)var7_8);
                var5_11 = ObjectIdWriter.construct((JavaType)var8_4, (PropertyName)null, var7_8, var5_11.getAlwaysAsId());
                ** continue;
            }
            ++var3_12;
        } while (true);
    }

    protected JsonSerializer<Object> findConvertingSerializer(SerializerProvider serializerProvider, BeanPropertyWriter beanPropertyWriter) throws JsonMappingException {
        Object object = serializerProvider.getAnnotationIntrospector();
        if (object != null && (object = object.findSerializationConverter(beanPropertyWriter.getMember())) != null) {
            object = serializerProvider.converterInstance(beanPropertyWriter.getMember(), object);
            JavaType javaType = object.getOutputType(serializerProvider.getTypeFactory());
            return new StdDelegatingSerializer(object, javaType, serializerProvider.findValueSerializer(javaType, (BeanProperty)beanPropertyWriter));
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    @Override
    public JsonNode getSchema(SerializerProvider serializerProvider, Type object) throws JsonMappingException {
        ObjectNode objectNode = this.createSchemaNode("object", true);
        object = (JsonSerializableSchema)this._handledType.getAnnotation(JsonSerializableSchema.class);
        if (object != null && (object = object.id()) != null && object.length() > 0) {
            objectNode.put("id", (String)object);
        }
        ObjectNode objectNode2 = objectNode.objectNode();
        object = this._propertyFilterId != null ? this.findPropertyFilter(serializerProvider, this._propertyFilterId, null) : null;
        int n2 = 0;
        do {
            if (n2 >= this._props.length) {
                objectNode.put("properties", objectNode2);
                return objectNode;
            }
            BeanPropertyWriter beanPropertyWriter = this._props[n2];
            if (object == null) {
                beanPropertyWriter.depositSchemaProperty(objectNode2, serializerProvider);
            } else {
                object.depositSchemaProperty((PropertyWriter)beanPropertyWriter, objectNode2, serializerProvider);
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void resolve(SerializerProvider serializerProvider) throws JsonMappingException {
        int n2 = this._filteredProps == null ? 0 : this._filteredProps.length;
        int n3 = this._props.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            Object object;
            JsonSerializer<Object> jsonSerializer;
            BeanPropertyWriter beanPropertyWriter = this._props[i2];
            if (!beanPropertyWriter.willSuppressNulls() && !beanPropertyWriter.hasNullSerializer() && (jsonSerializer = serializerProvider.findNullValueSerializer(beanPropertyWriter)) != null) {
                beanPropertyWriter.assignNullSerializer(jsonSerializer);
                if (i2 < n2 && (object = this._filteredProps[i2]) != null) {
                    object.assignNullSerializer(jsonSerializer);
                }
            }
            if (beanPropertyWriter.hasSerializer()) continue;
            jsonSerializer = object = this.findConvertingSerializer(serializerProvider, beanPropertyWriter);
            if (object == null) {
                JsonSerializer<Object> jsonSerializer2;
                object = jsonSerializer = beanPropertyWriter.getSerializationType();
                if (jsonSerializer == null) {
                    object = jsonSerializer = serializerProvider.constructType(beanPropertyWriter.getGenericPropertyType());
                    if (!jsonSerializer.isFinal()) {
                        if (!jsonSerializer.isContainerType() && jsonSerializer.containedTypeCount() <= 0) continue;
                        beanPropertyWriter.setNonTrivialBaseType((JavaType)((Object)jsonSerializer));
                        continue;
                    }
                }
                jsonSerializer = jsonSerializer2 = serializerProvider.findValueSerializer((JavaType)object, (BeanProperty)beanPropertyWriter);
                if (object.isContainerType()) {
                    object = (TypeSerializer)object.getContentType().getTypeHandler();
                    jsonSerializer = jsonSerializer2;
                    if (object != null) {
                        jsonSerializer = jsonSerializer2;
                        if (jsonSerializer2 instanceof ContainerSerializer) {
                            jsonSerializer = ((ContainerSerializer)jsonSerializer2).withValueTypeSerializer((TypeSerializer)object);
                        }
                    }
                }
            }
            beanPropertyWriter.assignSerializer(jsonSerializer);
            if (i2 >= n2 || (object = this._filteredProps[i2]) == null) continue;
            object.assignSerializer(jsonSerializer);
        }
        if (this._anyGetterWriter != null) {
            this._anyGetterWriter.resolve(serializerProvider);
        }
    }

    @Override
    public abstract void serialize(Object var1, JsonGenerator var2, SerializerProvider var3) throws IOException, JsonGenerationException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void serializeFields(Object object, JsonGenerator object2, SerializerProvider object3) throws IOException, JsonGenerationException {
        int n2;
        BeanPropertyWriter[] arrbeanPropertyWriter = this._filteredProps != null && object3.getActiveView() != null ? this._filteredProps : this._props;
        int n3 = 0;
        int n4 = 0;
        int n5 = arrbeanPropertyWriter.length;
        for (n2 = 0; n2 < n5; ++n2) {
            BeanPropertyWriter beanPropertyWriter = arrbeanPropertyWriter[n2];
            if (beanPropertyWriter == null) continue;
            n3 = n2;
            n4 = n2;
            beanPropertyWriter.serializeAsField(object, (JsonGenerator)object2, (SerializerProvider)object3);
        }
        n3 = n2;
        n4 = n2;
        try {
            if (this._anyGetterWriter != null) {
                n3 = n2;
                n4 = n2;
                this._anyGetterWriter.getAndSerialize(object, (JsonGenerator)object2, (SerializerProvider)object3);
            }
            return;
        }
        catch (Exception var9_12) {
            object2 = n3 == arrbeanPropertyWriter.length ? "[anySetter]" : arrbeanPropertyWriter[n3].getName();
            this.wrapAndThrow((SerializerProvider)object3, (Throwable)var9_12, object, (String)object2);
            return;
        }
        catch (StackOverflowError var2_3) {
            object3 = new JsonMappingException("Infinite recursion (StackOverflowError)", var2_3);
            String string2 = n4 == arrbeanPropertyWriter.length ? "[anySetter]" : arrbeanPropertyWriter[n4].getName();
            object3.prependPath(new JsonMappingException.Reference(object, string2));
            throw object3;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void serializeFieldsFiltered(Object var1_1, JsonGenerator var2_2, SerializerProvider var3_5) throws IOException, JsonGenerationException {
        if (this._filteredProps == null || var3_5.getActiveView() == null) ** GOTO lbl8
        var8_6 = this._filteredProps;
lbl3: // 2 sources:
        while ((var9_7 = this.findPropertyFilter((SerializerProvider)var3_5, this._propertyFilterId, var1_1)) == null) {
            this.serializeFields(var1_1, (JsonGenerator)var2_2, (SerializerProvider)var3_5);
            do {
                return;
                break;
            } while (true);
        }
        ** GOTO lbl10
lbl8: // 1 sources:
        var8_6 = this._props;
        ** GOTO lbl3
lbl10: // 1 sources:
        var5_9 = 0;
        var6_10 = 0;
        var4_11 = 0;
        var7_12 = var8_6.length;
        do {
            if (var4_11 < var7_12) {
                var10_13 = var8_6[var4_11];
                if (var10_13 != null) {
                    var5_9 = var4_11;
                    var6_10 = var4_11;
                    var9_7.serializeAsField(var1_1, (JsonGenerator)var2_2, (SerializerProvider)var3_5, var10_13);
                }
            } else {
                var5_9 = var4_11;
                var6_10 = var4_11;
                if (this._anyGetterWriter == null) ** continue;
                var5_9 = var4_11;
                var6_10 = var4_11;
                try {
                    this._anyGetterWriter.getAndFilter(var1_1, (JsonGenerator)var2_2, (SerializerProvider)var3_5, var9_7);
                    return;
                }
                catch (Exception var9_8) {
                    if (var5_9 == var8_6.length) {
                        var2_2 = "[anySetter]";
lbl36: // 2 sources:
                        do {
                            this.wrapAndThrow((SerializerProvider)var3_5, (Throwable)var9_8, var1_1, (String)var2_2);
                            return;
                            break;
                        } while (true);
                    }
                    var2_2 = var8_6[var5_9].getName();
                    ** continue;
                }
                catch (StackOverflowError var2_3) {
                    var3_5 = new JsonMappingException("Infinite recursion (StackOverflowError)", var2_3);
                    if (var6_10 == var8_6.length) {
                        var2_4 = "[anySetter]";
lbl45: // 2 sources:
                        do {
                            var3_5.prependPath(new JsonMappingException.Reference(var1_1, var2_4));
                            throw var3_5;
                            break;
                        } while (true);
                    }
                    var2_4 = var8_6[var6_10].getName();
                    ** continue;
                }
            }
            ++var4_11;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeWithType(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        if (this._objectIdWriter != null) {
            this._serializeWithObjectId(object, jsonGenerator, serializerProvider, typeSerializer);
            return;
        }
        String string2 = this._typeId == null ? null : this._customTypeId(object);
        if (string2 == null) {
            typeSerializer.writeTypePrefixForObject(object, jsonGenerator);
        } else {
            typeSerializer.writeCustomTypePrefixForObject(object, jsonGenerator, string2);
        }
        if (this._propertyFilterId != null) {
            this.serializeFieldsFiltered(object, jsonGenerator, serializerProvider);
        } else {
            this.serializeFields(object, jsonGenerator, serializerProvider);
        }
        if (string2 == null) {
            typeSerializer.writeTypeSuffixForObject(object, jsonGenerator);
            return;
        }
        typeSerializer.writeCustomTypeSuffixForObject(object, jsonGenerator, string2);
    }

    @Override
    public boolean usesObjectId() {
        if (this._objectIdWriter != null) {
            return true;
        }
        return false;
    }

    protected abstract BeanSerializerBase withFilterId(Object var1);

    protected abstract BeanSerializerBase withIgnorals(String[] var1);

    public abstract BeanSerializerBase withObjectIdWriter(ObjectIdWriter var1);
}

