/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class BeanPropertyWriter
extends PropertyWriter
implements BeanProperty {
    public static final Object MARKER_FOR_EMPTY = new Object();
    protected final Method _accessorMethod;
    protected final JavaType _cfgSerializationType;
    protected final Annotations _contextAnnotations;
    protected final JavaType _declaredType;
    protected transient PropertySerializerMap _dynamicSerializers;
    protected final Field _field;
    protected final Class<?>[] _includeInViews;
    protected HashMap<Object, Object> _internalSettings;
    protected final AnnotatedMember _member;
    protected final PropertyMetadata _metadata;
    protected final SerializedString _name;
    protected JavaType _nonTrivialBaseType;
    protected JsonSerializer<Object> _nullSerializer;
    protected JsonSerializer<Object> _serializer;
    protected final boolean _suppressNulls;
    protected final Object _suppressableValue;
    protected TypeSerializer _typeSerializer;
    protected final PropertyName _wrapperName;

    /*
     * Enabled aggressive block sorting
     */
    public BeanPropertyWriter(BeanPropertyDefinition beanPropertyDefinition, AnnotatedMember annotatedMember, Annotations object, JavaType javaType, JsonSerializer<?> jsonSerializer, TypeSerializer typeSerializer, JavaType javaType2, boolean bl2, Object object2) {
        this._member = annotatedMember;
        this._contextAnnotations = object;
        this._name = new SerializedString(beanPropertyDefinition.getName());
        this._wrapperName = beanPropertyDefinition.getWrapperName();
        this._declaredType = javaType;
        this._serializer = jsonSerializer;
        object = jsonSerializer == null ? PropertySerializerMap.emptyMap() : null;
        this._dynamicSerializers = object;
        this._typeSerializer = typeSerializer;
        this._cfgSerializationType = javaType2;
        this._metadata = beanPropertyDefinition.getMetadata();
        if (annotatedMember instanceof AnnotatedField) {
            this._accessorMethod = null;
            this._field = (Field)annotatedMember.getMember();
        } else {
            if (!(annotatedMember instanceof AnnotatedMethod)) {
                throw new IllegalArgumentException("Can not pass member of type " + annotatedMember.getClass().getName());
            }
            this._accessorMethod = (Method)annotatedMember.getMember();
            this._field = null;
        }
        this._suppressNulls = bl2;
        this._suppressableValue = object2;
        this._includeInViews = beanPropertyDefinition.findViews();
        this._nullSerializer = null;
    }

    protected BeanPropertyWriter(BeanPropertyWriter beanPropertyWriter) {
        this(beanPropertyWriter, beanPropertyWriter._name);
    }

    protected BeanPropertyWriter(BeanPropertyWriter beanPropertyWriter, SerializedString serializedString) {
        this._name = serializedString;
        this._wrapperName = beanPropertyWriter._wrapperName;
        this._member = beanPropertyWriter._member;
        this._contextAnnotations = beanPropertyWriter._contextAnnotations;
        this._declaredType = beanPropertyWriter._declaredType;
        this._accessorMethod = beanPropertyWriter._accessorMethod;
        this._field = beanPropertyWriter._field;
        this._serializer = beanPropertyWriter._serializer;
        this._nullSerializer = beanPropertyWriter._nullSerializer;
        if (beanPropertyWriter._internalSettings != null) {
            this._internalSettings = new HashMap<Object, Object>(beanPropertyWriter._internalSettings);
        }
        this._cfgSerializationType = beanPropertyWriter._cfgSerializationType;
        this._dynamicSerializers = beanPropertyWriter._dynamicSerializers;
        this._suppressNulls = beanPropertyWriter._suppressNulls;
        this._suppressableValue = beanPropertyWriter._suppressableValue;
        this._includeInViews = beanPropertyWriter._includeInViews;
        this._typeSerializer = beanPropertyWriter._typeSerializer;
        this._nonTrivialBaseType = beanPropertyWriter._nonTrivialBaseType;
        this._metadata = beanPropertyWriter._metadata;
    }

    protected void _depositSchemaProperty(ObjectNode objectNode, JsonNode jsonNode) {
        objectNode.set(this.getName(), jsonNode);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, Class<?> object, SerializerProvider serializerProvider) throws JsonMappingException {
        object = this._nonTrivialBaseType != null ? propertySerializerMap.findAndAddPrimarySerializer(serializerProvider.constructSpecializedType(this._nonTrivialBaseType, object), serializerProvider, (BeanProperty)this) : propertySerializerMap.findAndAddPrimarySerializer(object, serializerProvider, (BeanProperty)this);
        if (propertySerializerMap != object.map) {
            this._dynamicSerializers = object.map;
        }
        return object.serializer;
    }

    @Deprecated
    protected void _handleSelfReference(Object object, JsonSerializer<?> jsonSerializer) throws JsonMappingException {
        this._handleSelfReference(object, null, null, jsonSerializer);
    }

    protected boolean _handleSelfReference(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, JsonSerializer<?> jsonSerializer) throws JsonMappingException {
        if (serializerProvider.isEnabled(SerializationFeature.FAIL_ON_SELF_REFERENCES) && !jsonSerializer.usesObjectId() && jsonSerializer instanceof BeanSerializerBase) {
            throw new JsonMappingException("Direct self-reference leading to cycle");
        }
        return false;
    }

    public void assignNullSerializer(JsonSerializer<Object> jsonSerializer) {
        if (this._nullSerializer != null && this._nullSerializer != jsonSerializer) {
            throw new IllegalStateException("Can not override null serializer");
        }
        this._nullSerializer = jsonSerializer;
    }

    public void assignSerializer(JsonSerializer<Object> jsonSerializer) {
        if (this._serializer != null && this._serializer != jsonSerializer) {
            throw new IllegalStateException("Can not override serializer");
        }
        this._serializer = jsonSerializer;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void depositSchemaProperty(JsonObjectFormatVisitor jsonObjectFormatVisitor) throws JsonMappingException {
        if (jsonObjectFormatVisitor == null) return;
        if (this.isRequired()) {
            jsonObjectFormatVisitor.property(this);
            return;
        }
        jsonObjectFormatVisitor.optionalProperty(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    @Override
    public void depositSchemaProperty(ObjectNode objectNode, SerializerProvider object) throws JsonMappingException {
        Object object2;
        void var4_5;
        JavaType javaType = this.getSerializationType();
        if (javaType == null) {
            Type type = this.getGenericPropertyType();
        } else {
            Class class_ = javaType.getRawClass();
        }
        Object object3 = object2 = this.getSerializer();
        if (object2 == null) {
            object3 = object2 = this.getRawSerializationType();
            if (object2 == null) {
                object3 = this.getPropertyType();
            }
            object3 = object.findValueSerializer(object3, (BeanProperty)this);
        }
        boolean bl2 = !this.isRequired();
        object = object3 instanceof SchemaAware ? ((SchemaAware)object3).getSchema((SerializerProvider)object, (Type)var4_5, bl2) : JsonSchema.getDefaultSchemaNode();
        this._depositSchemaProperty(objectNode, (JsonNode)object);
    }

    public final Object get(Object object) throws Exception {
        if (this._accessorMethod == null) {
            return this._field.get(object);
        }
        return this._accessorMethod.invoke(object, new Object[0]);
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        return this._member.getAnnotation(class_);
    }

    @Override
    public <A extends Annotation> A getContextAnnotation(Class<A> class_) {
        return this._contextAnnotations.get(class_);
    }

    @Override
    public PropertyName getFullName() {
        return new PropertyName(this._name.getValue());
    }

    public Type getGenericPropertyType() {
        if (this._accessorMethod != null) {
            return this._accessorMethod.getGenericReturnType();
        }
        return this._field.getGenericType();
    }

    public Object getInternalSetting(Object object) {
        if (this._internalSettings == null) {
            return null;
        }
        return this._internalSettings.get(object);
    }

    @Override
    public AnnotatedMember getMember() {
        return this._member;
    }

    @Override
    public PropertyMetadata getMetadata() {
        return this._metadata;
    }

    @Override
    public String getName() {
        return this._name.getValue();
    }

    public Class<?> getPropertyType() {
        if (this._accessorMethod != null) {
            return this._accessorMethod.getReturnType();
        }
        return this._field.getType();
    }

    public Class<?> getRawSerializationType() {
        if (this._cfgSerializationType == null) {
            return null;
        }
        return this._cfgSerializationType.getRawClass();
    }

    public JavaType getSerializationType() {
        return this._cfgSerializationType;
    }

    public SerializableString getSerializedName() {
        return this._name;
    }

    public JsonSerializer<Object> getSerializer() {
        return this._serializer;
    }

    @Override
    public JavaType getType() {
        return this._declaredType;
    }

    public Class<?>[] getViews() {
        return this._includeInViews;
    }

    @Override
    public PropertyName getWrapperName() {
        return this._wrapperName;
    }

    public boolean hasNullSerializer() {
        if (this._nullSerializer != null) {
            return true;
        }
        return false;
    }

    public boolean hasSerializer() {
        if (this._serializer != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isRequired() {
        return this._metadata.isRequired();
    }

    @Deprecated
    protected boolean isRequired(AnnotationIntrospector annotationIntrospector) {
        return this._metadata.isRequired();
    }

    public boolean isUnwrapping() {
        return false;
    }

    public Object removeInternalSetting(Object object) {
        Object object2 = null;
        if (this._internalSettings != null) {
            object2 = object = this._internalSettings.remove(object);
            if (this._internalSettings.size() == 0) {
                this._internalSettings = null;
                object2 = object;
            }
        }
        return object2;
    }

    public BeanPropertyWriter rename(NameTransformer object) {
        if ((object = object.transform(this._name.getValue())).equals(this._name.toString())) {
            return this;
        }
        return new BeanPropertyWriter(this, new SerializedString((String)object));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeAsElement(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        JsonSerializer<Object> jsonSerializer;
        Object object2 = this._accessorMethod == null ? this._field.get(object) : this._accessorMethod.invoke(object, new Object[0]);
        if (object2 == null) {
            if (this._nullSerializer == null) {
                jsonGenerator.writeNull();
                return;
            }
            this._nullSerializer.serialize(null, jsonGenerator, serializerProvider);
            return;
        }
        JsonSerializer<Object> jsonSerializer2 = jsonSerializer = this._serializer;
        if (jsonSerializer == null) {
            Class class_ = object2.getClass();
            PropertySerializerMap propertySerializerMap = this._dynamicSerializers;
            jsonSerializer2 = jsonSerializer = propertySerializerMap.serializerFor(class_);
            if (jsonSerializer == null) {
                jsonSerializer2 = this._findAndAddDynamic(propertySerializerMap, class_, serializerProvider);
            }
        }
        if (this._suppressableValue != null) {
            if (MARKER_FOR_EMPTY == this._suppressableValue) {
                if (jsonSerializer2.isEmpty(object2)) {
                    this.serializeAsPlaceholder(object, jsonGenerator, serializerProvider);
                    return;
                }
            } else if (this._suppressableValue.equals(object2)) {
                this.serializeAsPlaceholder(object, jsonGenerator, serializerProvider);
                return;
            }
        }
        if (object2 == object && this._handleSelfReference(object, jsonGenerator, serializerProvider, jsonSerializer2)) return;
        {
            if (this._typeSerializer == null) {
                jsonSerializer2.serialize(object2, jsonGenerator, serializerProvider);
                return;
            }
        }
        jsonSerializer2.serializeWithType(object2, jsonGenerator, serializerProvider, this._typeSerializer);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void serializeAsField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        JsonSerializer<Object> jsonSerializer;
        Object object2 = this._accessorMethod == null ? this._field.get(object) : this._accessorMethod.invoke(object, new Object[0]);
        if (object2 == null) {
            if (this._nullSerializer == null) return;
            {
                jsonGenerator.writeFieldName(this._name);
                this._nullSerializer.serialize(null, jsonGenerator, serializerProvider);
            }
            return;
        }
        JsonSerializer<Object> jsonSerializer2 = jsonSerializer = this._serializer;
        if (jsonSerializer == null) {
            Class class_ = object2.getClass();
            PropertySerializerMap propertySerializerMap = this._dynamicSerializers;
            jsonSerializer2 = jsonSerializer = propertySerializerMap.serializerFor(class_);
            if (jsonSerializer == null) {
                jsonSerializer2 = this._findAndAddDynamic(propertySerializerMap, class_, serializerProvider);
            }
        }
        if (this._suppressableValue != null) {
            if (MARKER_FOR_EMPTY == this._suppressableValue) {
                if (jsonSerializer2.isEmpty(object2)) return;
            } else if (this._suppressableValue.equals(object2)) {
                return;
            }
        }
        if (object2 == object && this._handleSelfReference(object, jsonGenerator, serializerProvider, jsonSerializer2)) return;
        {
            jsonGenerator.writeFieldName(this._name);
            if (this._typeSerializer == null) {
                jsonSerializer2.serialize(object2, jsonGenerator, serializerProvider);
                return;
            }
        }
        jsonSerializer2.serializeWithType(object2, jsonGenerator, serializerProvider, this._typeSerializer);
    }

    @Override
    public void serializeAsOmittedField(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        if (!jsonGenerator.canOmitFields()) {
            jsonGenerator.writeOmittedField(this._name.getValue());
        }
    }

    @Override
    public void serializeAsPlaceholder(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws Exception {
        if (this._nullSerializer != null) {
            this._nullSerializer.serialize(null, jsonGenerator, serializerProvider);
            return;
        }
        jsonGenerator.writeNull();
    }

    public Object setInternalSetting(Object object, Object object2) {
        if (this._internalSettings == null) {
            this._internalSettings = new HashMap();
        }
        return this._internalSettings.put(object, object2);
    }

    public void setNonTrivialBaseType(JavaType javaType) {
        this._nonTrivialBaseType = javaType;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(40);
        stringBuilder.append("property '").append(this.getName()).append("' (");
        if (this._accessorMethod != null) {
            stringBuilder.append("via method ").append(this._accessorMethod.getDeclaringClass().getName()).append("#").append(this._accessorMethod.getName());
        } else {
            stringBuilder.append("field \"").append(this._field.getDeclaringClass().getName()).append("#").append(this._field.getName());
        }
        if (this._serializer == null) {
            stringBuilder.append(", no static serializer");
        } else {
            stringBuilder.append(", static serializer of type " + this._serializer.getClass().getName());
        }
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public BeanPropertyWriter unwrappingWriter(NameTransformer nameTransformer) {
        return new UnwrappingBeanPropertyWriter(this, nameTransformer);
    }

    public boolean willSuppressNulls() {
        return this._suppressNulls;
    }
}

