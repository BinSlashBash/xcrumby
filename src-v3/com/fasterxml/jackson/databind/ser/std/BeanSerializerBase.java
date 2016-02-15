package com.fasterxml.jackson.databind.ser.std;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
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
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class BeanSerializerBase extends StdSerializer<Object> implements ContextualSerializer, ResolvableSerializer, JsonFormatVisitable, SchemaAware {
    protected static final PropertyName NAME_FOR_OBJECT_REF;
    protected static final BeanPropertyWriter[] NO_PROPS;
    protected final AnyGetterWriter _anyGetterWriter;
    protected final BeanPropertyWriter[] _filteredProps;
    protected final ObjectIdWriter _objectIdWriter;
    protected final Object _propertyFilterId;
    protected final BeanPropertyWriter[] _props;
    protected final Shape _serializationShape;
    protected final AnnotatedMember _typeId;

    protected abstract BeanSerializerBase asArraySerializer();

    public abstract void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException;

    protected abstract BeanSerializerBase withFilterId(Object obj);

    protected abstract BeanSerializerBase withIgnorals(String[] strArr);

    public abstract BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter);

    static {
        NAME_FOR_OBJECT_REF = new PropertyName("#object-ref");
        NO_PROPS = new BeanPropertyWriter[0];
    }

    protected BeanSerializerBase(JavaType type, BeanSerializerBuilder builder, BeanPropertyWriter[] properties, BeanPropertyWriter[] filteredProperties) {
        Shape shape = null;
        super(type);
        this._props = properties;
        this._filteredProps = filteredProperties;
        if (builder == null) {
            this._typeId = null;
            this._anyGetterWriter = null;
            this._propertyFilterId = null;
            this._objectIdWriter = null;
            this._serializationShape = null;
            return;
        }
        this._typeId = builder.getTypeId();
        this._anyGetterWriter = builder.getAnyGetter();
        this._propertyFilterId = builder.getFilterId();
        this._objectIdWriter = builder.getObjectIdWriter();
        Value format = builder.getBeanDescription().findExpectedFormat(null);
        if (format != null) {
            shape = format.getShape();
        }
        this._serializationShape = shape;
    }

    public BeanSerializerBase(BeanSerializerBase src, BeanPropertyWriter[] properties, BeanPropertyWriter[] filteredProperties) {
        super(src._handledType);
        this._props = properties;
        this._filteredProps = filteredProperties;
        this._typeId = src._typeId;
        this._anyGetterWriter = src._anyGetterWriter;
        this._objectIdWriter = src._objectIdWriter;
        this._propertyFilterId = src._propertyFilterId;
        this._serializationShape = src._serializationShape;
    }

    protected BeanSerializerBase(BeanSerializerBase src, ObjectIdWriter objectIdWriter) {
        this(src, objectIdWriter, src._propertyFilterId);
    }

    protected BeanSerializerBase(BeanSerializerBase src, ObjectIdWriter objectIdWriter, Object filterId) {
        super(src._handledType);
        this._props = src._props;
        this._filteredProps = src._filteredProps;
        this._typeId = src._typeId;
        this._anyGetterWriter = src._anyGetterWriter;
        this._objectIdWriter = objectIdWriter;
        this._propertyFilterId = filterId;
        this._serializationShape = src._serializationShape;
    }

    protected BeanSerializerBase(BeanSerializerBase src, String[] toIgnore) {
        BeanPropertyWriter[] beanPropertyWriterArr = null;
        super(src._handledType);
        HashSet<String> ignoredSet = ArrayBuilders.arrayToSet(toIgnore);
        BeanPropertyWriter[] propsIn = src._props;
        BeanPropertyWriter[] fpropsIn = src._filteredProps;
        int len = propsIn.length;
        ArrayList<BeanPropertyWriter> propsOut = new ArrayList(len);
        ArrayList<BeanPropertyWriter> fpropsOut = fpropsIn == null ? null : new ArrayList(len);
        for (int i = 0; i < len; i++) {
            BeanPropertyWriter bpw = propsIn[i];
            if (!ignoredSet.contains(bpw.getName())) {
                propsOut.add(bpw);
                if (fpropsIn != null) {
                    fpropsOut.add(fpropsIn[i]);
                }
            }
        }
        this._props = (BeanPropertyWriter[]) propsOut.toArray(new BeanPropertyWriter[propsOut.size()]);
        if (fpropsOut != null) {
            beanPropertyWriterArr = (BeanPropertyWriter[]) fpropsOut.toArray(new BeanPropertyWriter[fpropsOut.size()]);
        }
        this._filteredProps = beanPropertyWriterArr;
        this._typeId = src._typeId;
        this._anyGetterWriter = src._anyGetterWriter;
        this._objectIdWriter = src._objectIdWriter;
        this._propertyFilterId = src._propertyFilterId;
        this._serializationShape = src._serializationShape;
    }

    protected BeanSerializerBase(BeanSerializerBase src) {
        this(src, src._props, src._filteredProps);
    }

    protected BeanSerializerBase(BeanSerializerBase src, NameTransformer unwrapper) {
        this(src, rename(src._props, unwrapper), rename(src._filteredProps, unwrapper));
    }

    private static final BeanPropertyWriter[] rename(BeanPropertyWriter[] props, NameTransformer transformer) {
        if (props == null || props.length == 0 || transformer == null || transformer == NameTransformer.NOP) {
            return props;
        }
        int len = props.length;
        BeanPropertyWriter[] result = new BeanPropertyWriter[len];
        for (int i = 0; i < len; i++) {
            BeanPropertyWriter bpw = props[i];
            if (bpw != null) {
                result[i] = bpw.rename(transformer);
            }
        }
        return result;
    }

    public void resolve(SerializerProvider provider) throws JsonMappingException {
        int filteredCount;
        if (this._filteredProps == null) {
            filteredCount = 0;
        } else {
            filteredCount = this._filteredProps.length;
        }
        int len = this._props.length;
        for (int i = 0; i < len; i++) {
            BeanPropertyWriter w2;
            BeanProperty prop = this._props[i];
            if (!(prop.willSuppressNulls() || prop.hasNullSerializer())) {
                JsonSerializer<Object> nullSer = provider.findNullValueSerializer(prop);
                if (nullSer != null) {
                    prop.assignNullSerializer(nullSer);
                    if (i < filteredCount) {
                        w2 = this._filteredProps[i];
                        if (w2 != null) {
                            w2.assignNullSerializer(nullSer);
                        }
                    }
                }
            }
            if (!prop.hasSerializer()) {
                JsonSerializer<Object> ser = findConvertingSerializer(provider, prop);
                if (ser == null) {
                    JavaType type = prop.getSerializationType();
                    if (type == null) {
                        type = provider.constructType(prop.getGenericPropertyType());
                        if (!type.isFinal()) {
                            if (type.isContainerType() || type.containedTypeCount() > 0) {
                                prop.setNonTrivialBaseType(type);
                            }
                        }
                    }
                    ser = provider.findValueSerializer(type, prop);
                    if (type.isContainerType()) {
                        TypeSerializer typeSer = (TypeSerializer) type.getContentType().getTypeHandler();
                        if (typeSer != null && (ser instanceof ContainerSerializer)) {
                            ser = ((ContainerSerializer) ser).withValueTypeSerializer(typeSer);
                        }
                    }
                }
                prop.assignSerializer(ser);
                if (i < filteredCount) {
                    w2 = this._filteredProps[i];
                    if (w2 != null) {
                        w2.assignSerializer(ser);
                    }
                }
            }
        }
        if (this._anyGetterWriter != null) {
            this._anyGetterWriter.resolve(provider);
        }
    }

    protected JsonSerializer<Object> findConvertingSerializer(SerializerProvider provider, BeanPropertyWriter prop) throws JsonMappingException {
        AnnotationIntrospector intr = provider.getAnnotationIntrospector();
        if (intr != null) {
            Object convDef = intr.findSerializationConverter(prop.getMember());
            if (convDef != null) {
                Converter<Object, Object> conv = provider.converterInstance(prop.getMember(), convDef);
                JavaType delegateType = conv.getOutputType(provider.getTypeFactory());
                return new StdDelegatingSerializer(conv, delegateType, provider.findValueSerializer(delegateType, (BeanProperty) prop));
            }
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.fasterxml.jackson.databind.JsonSerializer<?> createContextual(com.fasterxml.jackson.databind.SerializerProvider r31, com.fasterxml.jackson.databind.BeanProperty r32) throws com.fasterxml.jackson.databind.JsonMappingException {
        /*
        r30 = this;
        r0 = r30;
        r0 = r0._objectIdWriter;
        r19 = r0;
        r13 = 0;
        r17 = 0;
        r15 = r31.getAnnotationIntrospector();
        if (r32 == 0) goto L_0x0011;
    L_0x000f:
        if (r15 != 0) goto L_0x00c3;
    L_0x0011:
        r4 = 0;
    L_0x0012:
        if (r4 == 0) goto L_0x005f;
    L_0x0014:
        r13 = r15.findPropertiesToIgnore(r4);
        r18 = r15.findObjectIdInfo(r4);
        if (r18 != 0) goto L_0x00c9;
    L_0x001e:
        if (r19 == 0) goto L_0x0041;
    L_0x0020:
        r25 = new com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
        r26 = NAME_FOR_OBJECT_REF;
        r27 = 0;
        r28 = 0;
        r29 = 0;
        r25.<init>(r26, r27, r28, r29);
        r0 = r25;
        r18 = r15.findObjectReferenceInfo(r4, r0);
        r0 = r30;
        r0 = r0._objectIdWriter;
        r25 = r0;
        r26 = r18.getAlwaysAsId();
        r19 = r25.withAlwaysAsId(r26);
    L_0x0041:
        r6 = r15.findFilterId(r4);
        if (r6 == 0) goto L_0x005f;
    L_0x0047:
        r0 = r30;
        r0 = r0._propertyFilterId;
        r25 = r0;
        if (r25 == 0) goto L_0x005d;
    L_0x004f:
        r0 = r30;
        r0 = r0._propertyFilterId;
        r25 = r0;
        r0 = r25;
        r25 = r6.equals(r0);
        if (r25 != 0) goto L_0x005f;
    L_0x005d:
        r17 = r6;
    L_0x005f:
        r5 = r30;
        if (r19 == 0) goto L_0x008d;
    L_0x0063:
        r0 = r19;
        r0 = r0.idType;
        r25 = r0;
        r0 = r31;
        r1 = r25;
        r2 = r32;
        r22 = r0.findValueSerializer(r1, r2);
        r0 = r19;
        r1 = r22;
        r19 = r0.withSerializer(r1);
        r0 = r30;
        r0 = r0._objectIdWriter;
        r25 = r0;
        r0 = r19;
        r1 = r25;
        if (r0 == r1) goto L_0x008d;
    L_0x0087:
        r0 = r19;
        r5 = r5.withObjectIdWriter(r0);
    L_0x008d:
        if (r13 == 0) goto L_0x0098;
    L_0x008f:
        r0 = r13.length;
        r25 = r0;
        if (r25 == 0) goto L_0x0098;
    L_0x0094:
        r5 = r5.withIgnorals(r13);
    L_0x0098:
        if (r17 == 0) goto L_0x00a0;
    L_0x009a:
        r0 = r17;
        r5 = r5.withFilterId(r0);
    L_0x00a0:
        r23 = 0;
        if (r4 == 0) goto L_0x00ae;
    L_0x00a4:
        r7 = r15.findFormat(r4);
        if (r7 == 0) goto L_0x00ae;
    L_0x00aa:
        r23 = r7.getShape();
    L_0x00ae:
        if (r23 != 0) goto L_0x00b6;
    L_0x00b0:
        r0 = r30;
        r0 = r0._serializationShape;
        r23 = r0;
    L_0x00b6:
        r25 = com.fasterxml.jackson.annotation.JsonFormat.Shape.ARRAY;
        r0 = r23;
        r1 = r25;
        if (r0 != r1) goto L_0x00c2;
    L_0x00be:
        r5 = r5.asArraySerializer();
    L_0x00c2:
        return r5;
    L_0x00c3:
        r4 = r32.getMember();
        goto L_0x0012;
    L_0x00c9:
        r0 = r18;
        r18 = r15.findObjectReferenceInfo(r4, r0);
        r14 = r18.getGeneratorType();
        r0 = r31;
        r24 = r0.constructType(r14);
        r25 = r31.getTypeFactory();
        r26 = com.fasterxml.jackson.annotation.ObjectIdGenerator.class;
        r0 = r25;
        r1 = r24;
        r2 = r26;
        r25 = r0.findTypeParameters(r1, r2);
        r26 = 0;
        r12 = r25[r26];
        r25 = com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator.class;
        r0 = r25;
        if (r14 != r0) goto L_0x01d8;
    L_0x00f3:
        r25 = r18.getPropertyName();
        r21 = r25.getSimpleName();
        r11 = 0;
        r10 = 0;
        r0 = r30;
        r0 = r0._props;
        r25 = r0;
        r0 = r25;
        r0 = r0.length;
        r16 = r0;
    L_0x0108:
        r0 = r16;
        if (r10 != r0) goto L_0x0143;
    L_0x010c:
        r25 = new java.lang.IllegalArgumentException;
        r26 = new java.lang.StringBuilder;
        r26.<init>();
        r27 = "Invalid Object Id definition for ";
        r26 = r26.append(r27);
        r0 = r30;
        r0 = r0._handledType;
        r27 = r0;
        r27 = r27.getName();
        r26 = r26.append(r27);
        r27 = ": can not find property with name '";
        r26 = r26.append(r27);
        r0 = r26;
        r1 = r21;
        r26 = r0.append(r1);
        r27 = "'";
        r26 = r26.append(r27);
        r26 = r26.toString();
        r25.<init>(r26);
        throw r25;
    L_0x0143:
        r0 = r30;
        r0 = r0._props;
        r25 = r0;
        r20 = r25[r10];
        r25 = r20.getName();
        r0 = r21;
        r1 = r25;
        r25 = r0.equals(r1);
        if (r25 == 0) goto L_0x01d4;
    L_0x0159:
        r11 = r20;
        if (r10 <= 0) goto L_0x01b7;
    L_0x015d:
        r0 = r30;
        r0 = r0._props;
        r25 = r0;
        r26 = 0;
        r0 = r30;
        r0 = r0._props;
        r27 = r0;
        r28 = 1;
        r0 = r25;
        r1 = r26;
        r2 = r27;
        r3 = r28;
        java.lang.System.arraycopy(r0, r1, r2, r3, r10);
        r0 = r30;
        r0 = r0._props;
        r25 = r0;
        r26 = 0;
        r25[r26] = r11;
        r0 = r30;
        r0 = r0._filteredProps;
        r25 = r0;
        if (r25 == 0) goto L_0x01b7;
    L_0x018a:
        r0 = r30;
        r0 = r0._filteredProps;
        r25 = r0;
        r8 = r25[r10];
        r0 = r30;
        r0 = r0._filteredProps;
        r25 = r0;
        r26 = 0;
        r0 = r30;
        r0 = r0._filteredProps;
        r27 = r0;
        r28 = 1;
        r0 = r25;
        r1 = r26;
        r2 = r27;
        r3 = r28;
        java.lang.System.arraycopy(r0, r1, r2, r3, r10);
        r0 = r30;
        r0 = r0._filteredProps;
        r25 = r0;
        r26 = 0;
        r25[r26] = r8;
    L_0x01b7:
        r12 = r11.getType();
        r9 = new com.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
        r0 = r18;
        r9.<init>(r0, r11);
        r25 = 0;
        r25 = (com.fasterxml.jackson.databind.PropertyName) r25;
        r26 = r18.getAlwaysAsId();
        r0 = r25;
        r1 = r26;
        r19 = com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter.construct(r12, r0, r9, r1);
        goto L_0x0041;
    L_0x01d4:
        r10 = r10 + 1;
        goto L_0x0108;
    L_0x01d8:
        r0 = r31;
        r1 = r18;
        r9 = r0.objectIdGeneratorInstance(r4, r1);
        r25 = r18.getPropertyName();
        r26 = r18.getAlwaysAsId();
        r0 = r25;
        r1 = r26;
        r19 = com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter.construct(r12, r0, r9, r1);
        goto L_0x0041;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.createContextual(com.fasterxml.jackson.databind.SerializerProvider, com.fasterxml.jackson.databind.BeanProperty):com.fasterxml.jackson.databind.JsonSerializer<?>");
    }

    public boolean usesObjectId() {
        return this._objectIdWriter != null;
    }

    public void serializeWithType(Object bean, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException, JsonGenerationException {
        if (this._objectIdWriter != null) {
            _serializeWithObjectId(bean, jgen, provider, typeSer);
            return;
        }
        String typeStr = this._typeId == null ? null : _customTypeId(bean);
        if (typeStr == null) {
            typeSer.writeTypePrefixForObject(bean, jgen);
        } else {
            typeSer.writeCustomTypePrefixForObject(bean, jgen, typeStr);
        }
        if (this._propertyFilterId != null) {
            serializeFieldsFiltered(bean, jgen, provider);
        } else {
            serializeFields(bean, jgen, provider);
        }
        if (typeStr == null) {
            typeSer.writeTypeSuffixForObject(bean, jgen);
        } else {
            typeSer.writeCustomTypeSuffixForObject(bean, jgen, typeStr);
        }
    }

    protected final void _serializeWithObjectId(Object bean, JsonGenerator jgen, SerializerProvider provider, boolean startEndObject) throws IOException, JsonGenerationException {
        ObjectIdWriter w = this._objectIdWriter;
        WritableObjectId objectId = provider.findObjectId(bean, w.generator);
        if (!objectId.writeAsId(jgen, provider, w)) {
            Object id = objectId.generateId(bean);
            if (w.alwaysAsId) {
                w.serializer.serialize(id, jgen, provider);
                return;
            }
            if (startEndObject) {
                jgen.writeStartObject();
            }
            objectId.writeAsField(jgen, provider, w);
            if (this._propertyFilterId != null) {
                serializeFieldsFiltered(bean, jgen, provider);
            } else {
                serializeFields(bean, jgen, provider);
            }
            if (startEndObject) {
                jgen.writeEndObject();
            }
        }
    }

    protected final void _serializeWithObjectId(Object bean, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException, JsonGenerationException {
        ObjectIdWriter w = this._objectIdWriter;
        WritableObjectId objectId = provider.findObjectId(bean, w.generator);
        if (!objectId.writeAsId(jgen, provider, w)) {
            Object id = objectId.generateId(bean);
            if (w.alwaysAsId) {
                w.serializer.serialize(id, jgen, provider);
            } else {
                _serializeObjectId(bean, jgen, provider, typeSer, objectId);
            }
        }
    }

    protected void _serializeObjectId(Object bean, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer, WritableObjectId objectId) throws IOException, JsonProcessingException, JsonGenerationException {
        ObjectIdWriter w = this._objectIdWriter;
        String typeStr = this._typeId == null ? null : _customTypeId(bean);
        if (typeStr == null) {
            typeSer.writeTypePrefixForObject(bean, jgen);
        } else {
            typeSer.writeCustomTypePrefixForObject(bean, jgen, typeStr);
        }
        objectId.writeAsField(jgen, provider, w);
        if (this._propertyFilterId != null) {
            serializeFieldsFiltered(bean, jgen, provider);
        } else {
            serializeFields(bean, jgen, provider);
        }
        if (typeStr == null) {
            typeSer.writeTypeSuffixForObject(bean, jgen);
        } else {
            typeSer.writeCustomTypeSuffixForObject(bean, jgen, typeStr);
        }
    }

    private final String _customTypeId(Object bean) {
        Object typeId = this._typeId.getValue(bean);
        if (typeId == null) {
            return UnsupportedUrlFragment.DISPLAY_NAME;
        }
        return typeId instanceof String ? (String) typeId : typeId.toString();
    }

    protected void serializeFields(Object bean, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        BeanPropertyWriter[] props;
        if (this._filteredProps == null || provider.getActiveView() == null) {
            props = this._props;
        } else {
            props = this._filteredProps;
        }
        int i = 0;
        try {
            int len = props.length;
            while (i < len) {
                BeanPropertyWriter prop = props[i];
                if (prop != null) {
                    prop.serializeAsField(bean, jgen, provider);
                }
                i++;
            }
            if (this._anyGetterWriter != null) {
                this._anyGetterWriter.getAndSerialize(bean, jgen, provider);
            }
        } catch (Exception e) {
            wrapAndThrow(provider, (Throwable) e, bean, i == props.length ? "[anySetter]" : props[i].getName());
        } catch (Throwable e2) {
            JsonMappingException mapE = new JsonMappingException("Infinite recursion (StackOverflowError)", e2);
            mapE.prependPath(new Reference(bean, i == props.length ? "[anySetter]" : props[i].getName()));
            throw mapE;
        }
    }

    protected void serializeFieldsFiltered(Object bean, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        BeanPropertyWriter[] props;
        if (this._filteredProps == null || provider.getActiveView() == null) {
            props = this._props;
        } else {
            props = this._filteredProps;
        }
        PropertyFilter filter = findPropertyFilter(provider, this._propertyFilterId, bean);
        if (filter == null) {
            serializeFields(bean, jgen, provider);
            return;
        }
        int i = 0;
        try {
            int len = props.length;
            while (i < len) {
                BeanPropertyWriter prop = props[i];
                if (prop != null) {
                    filter.serializeAsField(bean, jgen, provider, prop);
                }
                i++;
            }
            if (this._anyGetterWriter != null) {
                this._anyGetterWriter.getAndFilter(bean, jgen, provider, filter);
            }
        } catch (Exception e) {
            wrapAndThrow(provider, (Throwable) e, bean, 0 == props.length ? "[anySetter]" : props[0].getName());
        } catch (Throwable e2) {
            JsonMappingException mapE = new JsonMappingException("Infinite recursion (StackOverflowError)", e2);
            mapE.prependPath(new Reference(bean, 0 == props.length ? "[anySetter]" : props[0].getName()));
            throw mapE;
        }
    }

    @Deprecated
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        PropertyFilter filter;
        ObjectNode o = createSchemaNode("object", true);
        JsonSerializableSchema ann = (JsonSerializableSchema) this._handledType.getAnnotation(JsonSerializableSchema.class);
        if (ann != null) {
            String id = ann.id();
            if (id != null && id.length() > 0) {
                o.put("id", id);
            }
        }
        JsonNode propertiesNode = o.objectNode();
        if (this._propertyFilterId != null) {
            filter = findPropertyFilter(provider, this._propertyFilterId, null);
        } else {
            filter = null;
        }
        for (PropertyWriter prop : this._props) {
            if (filter == null) {
                prop.depositSchemaProperty(propertiesNode, provider);
            } else {
                filter.depositSchemaProperty(prop, (ObjectNode) propertiesNode, provider);
            }
        }
        o.put("properties", propertiesNode);
        return o;
    }

    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException {
        if (visitor != null) {
            JsonObjectFormatVisitor objectVisitor = visitor.expectObjectFormat(typeHint);
            if (objectVisitor == null) {
                return;
            }
            if (this._propertyFilterId != null) {
                PropertyFilter filter = findPropertyFilter(visitor.getProvider(), this._propertyFilterId, null);
                for (PropertyWriter depositSchemaProperty : this._props) {
                    filter.depositSchemaProperty(depositSchemaProperty, objectVisitor, visitor.getProvider());
                }
                return;
            }
            for (BeanPropertyWriter depositSchemaProperty2 : this._props) {
                depositSchemaProperty2.depositSchemaProperty(objectVisitor);
            }
        }
    }
}
