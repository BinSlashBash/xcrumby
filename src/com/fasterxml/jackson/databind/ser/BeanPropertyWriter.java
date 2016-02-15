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
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap.SerializerAndMapResult;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;

public class BeanPropertyWriter
  extends PropertyWriter
  implements BeanProperty
{
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
  
  public BeanPropertyWriter(BeanPropertyDefinition paramBeanPropertyDefinition, AnnotatedMember paramAnnotatedMember, Annotations paramAnnotations, JavaType paramJavaType1, JsonSerializer<?> paramJsonSerializer, TypeSerializer paramTypeSerializer, JavaType paramJavaType2, boolean paramBoolean, Object paramObject)
  {
    this._member = paramAnnotatedMember;
    this._contextAnnotations = paramAnnotations;
    this._name = new SerializedString(paramBeanPropertyDefinition.getName());
    this._wrapperName = paramBeanPropertyDefinition.getWrapperName();
    this._declaredType = paramJavaType1;
    this._serializer = paramJsonSerializer;
    if (paramJsonSerializer == null)
    {
      paramAnnotations = PropertySerializerMap.emptyMap();
      this._dynamicSerializers = paramAnnotations;
      this._typeSerializer = paramTypeSerializer;
      this._cfgSerializationType = paramJavaType2;
      this._metadata = paramBeanPropertyDefinition.getMetadata();
      if (!(paramAnnotatedMember instanceof AnnotatedField)) {
        break label137;
      }
      this._accessorMethod = null;
    }
    for (this._field = ((Field)paramAnnotatedMember.getMember());; this._field = null)
    {
      this._suppressNulls = paramBoolean;
      this._suppressableValue = paramObject;
      this._includeInViews = paramBeanPropertyDefinition.findViews();
      this._nullSerializer = null;
      return;
      paramAnnotations = null;
      break;
      label137:
      if (!(paramAnnotatedMember instanceof AnnotatedMethod)) {
        break label163;
      }
      this._accessorMethod = ((Method)paramAnnotatedMember.getMember());
    }
    label163:
    throw new IllegalArgumentException("Can not pass member of type " + paramAnnotatedMember.getClass().getName());
  }
  
  protected BeanPropertyWriter(BeanPropertyWriter paramBeanPropertyWriter)
  {
    this(paramBeanPropertyWriter, paramBeanPropertyWriter._name);
  }
  
  protected BeanPropertyWriter(BeanPropertyWriter paramBeanPropertyWriter, SerializedString paramSerializedString)
  {
    this._name = paramSerializedString;
    this._wrapperName = paramBeanPropertyWriter._wrapperName;
    this._member = paramBeanPropertyWriter._member;
    this._contextAnnotations = paramBeanPropertyWriter._contextAnnotations;
    this._declaredType = paramBeanPropertyWriter._declaredType;
    this._accessorMethod = paramBeanPropertyWriter._accessorMethod;
    this._field = paramBeanPropertyWriter._field;
    this._serializer = paramBeanPropertyWriter._serializer;
    this._nullSerializer = paramBeanPropertyWriter._nullSerializer;
    if (paramBeanPropertyWriter._internalSettings != null) {
      this._internalSettings = new HashMap(paramBeanPropertyWriter._internalSettings);
    }
    this._cfgSerializationType = paramBeanPropertyWriter._cfgSerializationType;
    this._dynamicSerializers = paramBeanPropertyWriter._dynamicSerializers;
    this._suppressNulls = paramBeanPropertyWriter._suppressNulls;
    this._suppressableValue = paramBeanPropertyWriter._suppressableValue;
    this._includeInViews = paramBeanPropertyWriter._includeInViews;
    this._typeSerializer = paramBeanPropertyWriter._typeSerializer;
    this._nonTrivialBaseType = paramBeanPropertyWriter._nonTrivialBaseType;
    this._metadata = paramBeanPropertyWriter._metadata;
  }
  
  protected void _depositSchemaProperty(ObjectNode paramObjectNode, JsonNode paramJsonNode)
  {
    paramObjectNode.set(getName(), paramJsonNode);
  }
  
  protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, Class<?> paramClass, SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    if (this._nonTrivialBaseType != null) {}
    for (paramClass = paramPropertySerializerMap.findAndAddPrimarySerializer(paramSerializerProvider.constructSpecializedType(this._nonTrivialBaseType, paramClass), paramSerializerProvider, this);; paramClass = paramPropertySerializerMap.findAndAddPrimarySerializer(paramClass, paramSerializerProvider, this))
    {
      if (paramPropertySerializerMap != paramClass.map) {
        this._dynamicSerializers = paramClass.map;
      }
      return paramClass.serializer;
    }
  }
  
  @Deprecated
  protected void _handleSelfReference(Object paramObject, JsonSerializer<?> paramJsonSerializer)
    throws JsonMappingException
  {
    _handleSelfReference(paramObject, null, null, paramJsonSerializer);
  }
  
  protected boolean _handleSelfReference(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, JsonSerializer<?> paramJsonSerializer)
    throws JsonMappingException
  {
    if ((paramSerializerProvider.isEnabled(SerializationFeature.FAIL_ON_SELF_REFERENCES)) && (!paramJsonSerializer.usesObjectId()) && ((paramJsonSerializer instanceof BeanSerializerBase))) {
      throw new JsonMappingException("Direct self-reference leading to cycle");
    }
    return false;
  }
  
  public void assignNullSerializer(JsonSerializer<Object> paramJsonSerializer)
  {
    if ((this._nullSerializer != null) && (this._nullSerializer != paramJsonSerializer)) {
      throw new IllegalStateException("Can not override null serializer");
    }
    this._nullSerializer = paramJsonSerializer;
  }
  
  public void assignSerializer(JsonSerializer<Object> paramJsonSerializer)
  {
    if ((this._serializer != null) && (this._serializer != paramJsonSerializer)) {
      throw new IllegalStateException("Can not override serializer");
    }
    this._serializer = paramJsonSerializer;
  }
  
  public void depositSchemaProperty(JsonObjectFormatVisitor paramJsonObjectFormatVisitor)
    throws JsonMappingException
  {
    if (paramJsonObjectFormatVisitor != null)
    {
      if (isRequired()) {
        paramJsonObjectFormatVisitor.property(this);
      }
    }
    else {
      return;
    }
    paramJsonObjectFormatVisitor.optionalProperty(this);
  }
  
  @Deprecated
  public void depositSchemaProperty(ObjectNode paramObjectNode, SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    Object localObject1 = getSerializationType();
    Object localObject2;
    boolean bool;
    if (localObject1 == null)
    {
      localObject1 = getGenericPropertyType();
      Object localObject3 = getSerializer();
      localObject2 = localObject3;
      if (localObject3 == null)
      {
        localObject3 = getRawSerializationType();
        localObject2 = localObject3;
        if (localObject3 == null) {
          localObject2 = getPropertyType();
        }
        localObject2 = paramSerializerProvider.findValueSerializer((Class)localObject2, this);
      }
      if (isRequired()) {
        break label111;
      }
      bool = true;
      label71:
      if (!(localObject2 instanceof SchemaAware)) {
        break label116;
      }
    }
    label111:
    label116:
    for (paramSerializerProvider = ((SchemaAware)localObject2).getSchema(paramSerializerProvider, (Type)localObject1, bool);; paramSerializerProvider = JsonSchema.getDefaultSchemaNode())
    {
      _depositSchemaProperty(paramObjectNode, paramSerializerProvider);
      return;
      localObject1 = ((JavaType)localObject1).getRawClass();
      break;
      bool = false;
      break label71;
    }
  }
  
  public final Object get(Object paramObject)
    throws Exception
  {
    if (this._accessorMethod == null) {
      return this._field.get(paramObject);
    }
    return this._accessorMethod.invoke(paramObject, new Object[0]);
  }
  
  public <A extends Annotation> A getAnnotation(Class<A> paramClass)
  {
    return this._member.getAnnotation(paramClass);
  }
  
  public <A extends Annotation> A getContextAnnotation(Class<A> paramClass)
  {
    return this._contextAnnotations.get(paramClass);
  }
  
  public PropertyName getFullName()
  {
    return new PropertyName(this._name.getValue());
  }
  
  public Type getGenericPropertyType()
  {
    if (this._accessorMethod != null) {
      return this._accessorMethod.getGenericReturnType();
    }
    return this._field.getGenericType();
  }
  
  public Object getInternalSetting(Object paramObject)
  {
    if (this._internalSettings == null) {
      return null;
    }
    return this._internalSettings.get(paramObject);
  }
  
  public AnnotatedMember getMember()
  {
    return this._member;
  }
  
  public PropertyMetadata getMetadata()
  {
    return this._metadata;
  }
  
  public String getName()
  {
    return this._name.getValue();
  }
  
  public Class<?> getPropertyType()
  {
    if (this._accessorMethod != null) {
      return this._accessorMethod.getReturnType();
    }
    return this._field.getType();
  }
  
  public Class<?> getRawSerializationType()
  {
    if (this._cfgSerializationType == null) {
      return null;
    }
    return this._cfgSerializationType.getRawClass();
  }
  
  public JavaType getSerializationType()
  {
    return this._cfgSerializationType;
  }
  
  public SerializableString getSerializedName()
  {
    return this._name;
  }
  
  public JsonSerializer<Object> getSerializer()
  {
    return this._serializer;
  }
  
  public JavaType getType()
  {
    return this._declaredType;
  }
  
  public Class<?>[] getViews()
  {
    return this._includeInViews;
  }
  
  public PropertyName getWrapperName()
  {
    return this._wrapperName;
  }
  
  public boolean hasNullSerializer()
  {
    return this._nullSerializer != null;
  }
  
  public boolean hasSerializer()
  {
    return this._serializer != null;
  }
  
  public boolean isRequired()
  {
    return this._metadata.isRequired();
  }
  
  @Deprecated
  protected boolean isRequired(AnnotationIntrospector paramAnnotationIntrospector)
  {
    return this._metadata.isRequired();
  }
  
  public boolean isUnwrapping()
  {
    return false;
  }
  
  public Object removeInternalSetting(Object paramObject)
  {
    Object localObject = null;
    if (this._internalSettings != null)
    {
      paramObject = this._internalSettings.remove(paramObject);
      localObject = paramObject;
      if (this._internalSettings.size() == 0)
      {
        this._internalSettings = null;
        localObject = paramObject;
      }
    }
    return localObject;
  }
  
  public BeanPropertyWriter rename(NameTransformer paramNameTransformer)
  {
    paramNameTransformer = paramNameTransformer.transform(this._name.getValue());
    if (paramNameTransformer.equals(this._name.toString())) {
      return this;
    }
    return new BeanPropertyWriter(this, new SerializedString(paramNameTransformer));
  }
  
  public void serializeAsElement(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws Exception
  {
    Object localObject;
    if (this._accessorMethod == null)
    {
      localObject = this._field.get(paramObject);
      if (localObject != null) {
        break label62;
      }
      if (this._nullSerializer == null) {
        break label57;
      }
      this._nullSerializer.serialize(null, paramJsonGenerator, paramSerializerProvider);
    }
    label57:
    label62:
    JsonSerializer localJsonSerializer1;
    do
    {
      return;
      localObject = this._accessorMethod.invoke(paramObject, new Object[0]);
      break;
      paramJsonGenerator.writeNull();
      return;
      JsonSerializer localJsonSerializer2 = this._serializer;
      localJsonSerializer1 = localJsonSerializer2;
      if (localJsonSerializer2 == null)
      {
        Class localClass = localObject.getClass();
        PropertySerializerMap localPropertySerializerMap = this._dynamicSerializers;
        localJsonSerializer2 = localPropertySerializerMap.serializerFor(localClass);
        localJsonSerializer1 = localJsonSerializer2;
        if (localJsonSerializer2 == null) {
          localJsonSerializer1 = _findAndAddDynamic(localPropertySerializerMap, localClass, paramSerializerProvider);
        }
      }
      if (this._suppressableValue != null) {
        if (MARKER_FOR_EMPTY == this._suppressableValue)
        {
          if (localJsonSerializer1.isEmpty(localObject)) {
            serializeAsPlaceholder(paramObject, paramJsonGenerator, paramSerializerProvider);
          }
        }
        else if (this._suppressableValue.equals(localObject))
        {
          serializeAsPlaceholder(paramObject, paramJsonGenerator, paramSerializerProvider);
          return;
        }
      }
    } while ((localObject == paramObject) && (_handleSelfReference(paramObject, paramJsonGenerator, paramSerializerProvider, localJsonSerializer1)));
    if (this._typeSerializer == null)
    {
      localJsonSerializer1.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    localJsonSerializer1.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, this._typeSerializer);
  }
  
  public void serializeAsField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws Exception
  {
    Object localObject;
    if (this._accessorMethod == null)
    {
      localObject = this._field.get(paramObject);
      if (localObject != null) {
        break label65;
      }
      if (this._nullSerializer != null)
      {
        paramJsonGenerator.writeFieldName(this._name);
        this._nullSerializer.serialize(null, paramJsonGenerator, paramSerializerProvider);
      }
    }
    label65:
    JsonSerializer localJsonSerializer1;
    label149:
    label192:
    do
    {
      return;
      while ((localObject == paramObject) && (_handleSelfReference(paramObject, paramJsonGenerator, paramSerializerProvider, localJsonSerializer1))) {
        do
        {
          localObject = this._accessorMethod.invoke(paramObject, new Object[0]);
          break;
          JsonSerializer localJsonSerializer2 = this._serializer;
          localJsonSerializer1 = localJsonSerializer2;
          if (localJsonSerializer2 == null)
          {
            Class localClass = localObject.getClass();
            PropertySerializerMap localPropertySerializerMap = this._dynamicSerializers;
            localJsonSerializer2 = localPropertySerializerMap.serializerFor(localClass);
            localJsonSerializer1 = localJsonSerializer2;
            if (localJsonSerializer2 == null) {
              localJsonSerializer1 = _findAndAddDynamic(localPropertySerializerMap, localClass, paramSerializerProvider);
            }
          }
          if (this._suppressableValue == null) {
            break label149;
          }
          if (MARKER_FOR_EMPTY != this._suppressableValue) {
            break label192;
          }
        } while (localJsonSerializer1.isEmpty(localObject));
      }
      paramJsonGenerator.writeFieldName(this._name);
      if (this._typeSerializer != null) {
        break label205;
      }
      localJsonSerializer1.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
      return;
    } while (!this._suppressableValue.equals(localObject));
    return;
    label205:
    localJsonSerializer1.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, this._typeSerializer);
  }
  
  public void serializeAsOmittedField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws Exception
  {
    if (!paramJsonGenerator.canOmitFields()) {
      paramJsonGenerator.writeOmittedField(this._name.getValue());
    }
  }
  
  public void serializeAsPlaceholder(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws Exception
  {
    if (this._nullSerializer != null)
    {
      this._nullSerializer.serialize(null, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    paramJsonGenerator.writeNull();
  }
  
  public Object setInternalSetting(Object paramObject1, Object paramObject2)
  {
    if (this._internalSettings == null) {
      this._internalSettings = new HashMap();
    }
    return this._internalSettings.put(paramObject1, paramObject2);
  }
  
  public void setNonTrivialBaseType(JavaType paramJavaType)
  {
    this._nonTrivialBaseType = paramJavaType;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(40);
    localStringBuilder.append("property '").append(getName()).append("' (");
    if (this._accessorMethod != null)
    {
      localStringBuilder.append("via method ").append(this._accessorMethod.getDeclaringClass().getName()).append("#").append(this._accessorMethod.getName());
      if (this._serializer != null) {
        break label142;
      }
      localStringBuilder.append(", no static serializer");
    }
    for (;;)
    {
      localStringBuilder.append(')');
      return localStringBuilder.toString();
      localStringBuilder.append("field \"").append(this._field.getDeclaringClass().getName()).append("#").append(this._field.getName());
      break;
      label142:
      localStringBuilder.append(", static serializer of type " + this._serializer.getClass().getName());
    }
  }
  
  public BeanPropertyWriter unwrappingWriter(NameTransformer paramNameTransformer)
  {
    return new UnwrappingBeanPropertyWriter(this, paramNameTransformer);
  }
  
  public boolean willSuppressNulls()
  {
    return this._suppressNulls;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/BeanPropertyWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */