package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
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
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class BeanSerializerBase
  extends StdSerializer<Object>
  implements ContextualSerializer, ResolvableSerializer, JsonFormatVisitable, SchemaAware
{
  protected static final PropertyName NAME_FOR_OBJECT_REF = new PropertyName("#object-ref");
  protected static final BeanPropertyWriter[] NO_PROPS = new BeanPropertyWriter[0];
  protected final AnyGetterWriter _anyGetterWriter;
  protected final BeanPropertyWriter[] _filteredProps;
  protected final ObjectIdWriter _objectIdWriter;
  protected final Object _propertyFilterId;
  protected final BeanPropertyWriter[] _props;
  protected final JsonFormat.Shape _serializationShape;
  protected final AnnotatedMember _typeId;
  
  protected BeanSerializerBase(JavaType paramJavaType, BeanSerializerBuilder paramBeanSerializerBuilder, BeanPropertyWriter[] paramArrayOfBeanPropertyWriter1, BeanPropertyWriter[] paramArrayOfBeanPropertyWriter2)
  {
    super(paramJavaType);
    this._props = paramArrayOfBeanPropertyWriter1;
    this._filteredProps = paramArrayOfBeanPropertyWriter2;
    if (paramBeanSerializerBuilder == null)
    {
      this._typeId = null;
      this._anyGetterWriter = null;
      this._propertyFilterId = null;
      this._objectIdWriter = null;
      this._serializationShape = null;
      return;
    }
    this._typeId = paramBeanSerializerBuilder.getTypeId();
    this._anyGetterWriter = paramBeanSerializerBuilder.getAnyGetter();
    this._propertyFilterId = paramBeanSerializerBuilder.getFilterId();
    this._objectIdWriter = paramBeanSerializerBuilder.getObjectIdWriter();
    paramJavaType = paramBeanSerializerBuilder.getBeanDescription().findExpectedFormat(null);
    if (paramJavaType == null) {}
    for (paramJavaType = (JavaType)localObject;; paramJavaType = paramJavaType.getShape())
    {
      this._serializationShape = paramJavaType;
      return;
    }
  }
  
  protected BeanSerializerBase(BeanSerializerBase paramBeanSerializerBase)
  {
    this(paramBeanSerializerBase, paramBeanSerializerBase._props, paramBeanSerializerBase._filteredProps);
  }
  
  protected BeanSerializerBase(BeanSerializerBase paramBeanSerializerBase, ObjectIdWriter paramObjectIdWriter)
  {
    this(paramBeanSerializerBase, paramObjectIdWriter, paramBeanSerializerBase._propertyFilterId);
  }
  
  protected BeanSerializerBase(BeanSerializerBase paramBeanSerializerBase, ObjectIdWriter paramObjectIdWriter, Object paramObject)
  {
    super(paramBeanSerializerBase._handledType);
    this._props = paramBeanSerializerBase._props;
    this._filteredProps = paramBeanSerializerBase._filteredProps;
    this._typeId = paramBeanSerializerBase._typeId;
    this._anyGetterWriter = paramBeanSerializerBase._anyGetterWriter;
    this._objectIdWriter = paramObjectIdWriter;
    this._propertyFilterId = paramObject;
    this._serializationShape = paramBeanSerializerBase._serializationShape;
  }
  
  protected BeanSerializerBase(BeanSerializerBase paramBeanSerializerBase, NameTransformer paramNameTransformer)
  {
    this(paramBeanSerializerBase, rename(paramBeanSerializerBase._props, paramNameTransformer), rename(paramBeanSerializerBase._filteredProps, paramNameTransformer));
  }
  
  public BeanSerializerBase(BeanSerializerBase paramBeanSerializerBase, BeanPropertyWriter[] paramArrayOfBeanPropertyWriter1, BeanPropertyWriter[] paramArrayOfBeanPropertyWriter2)
  {
    super(paramBeanSerializerBase._handledType);
    this._props = paramArrayOfBeanPropertyWriter1;
    this._filteredProps = paramArrayOfBeanPropertyWriter2;
    this._typeId = paramBeanSerializerBase._typeId;
    this._anyGetterWriter = paramBeanSerializerBase._anyGetterWriter;
    this._objectIdWriter = paramBeanSerializerBase._objectIdWriter;
    this._propertyFilterId = paramBeanSerializerBase._propertyFilterId;
    this._serializationShape = paramBeanSerializerBase._serializationShape;
  }
  
  protected BeanSerializerBase(BeanSerializerBase paramBeanSerializerBase, String[] paramArrayOfString)
  {
    super(paramBeanSerializerBase._handledType);
    HashSet localHashSet = ArrayBuilders.arrayToSet(paramArrayOfString);
    BeanPropertyWriter[] arrayOfBeanPropertyWriter1 = paramBeanSerializerBase._props;
    BeanPropertyWriter[] arrayOfBeanPropertyWriter2 = paramBeanSerializerBase._filteredProps;
    int j = arrayOfBeanPropertyWriter1.length;
    ArrayList localArrayList = new ArrayList(j);
    int i;
    label54:
    BeanPropertyWriter localBeanPropertyWriter;
    if (arrayOfBeanPropertyWriter2 == null)
    {
      paramArrayOfString = null;
      i = 0;
      if (i >= j) {
        break label124;
      }
      localBeanPropertyWriter = arrayOfBeanPropertyWriter1[i];
      if (!localHashSet.contains(localBeanPropertyWriter.getName())) {
        break label99;
      }
    }
    for (;;)
    {
      i += 1;
      break label54;
      paramArrayOfString = new ArrayList(j);
      break;
      label99:
      localArrayList.add(localBeanPropertyWriter);
      if (arrayOfBeanPropertyWriter2 != null) {
        paramArrayOfString.add(arrayOfBeanPropertyWriter2[i]);
      }
    }
    label124:
    this._props = ((BeanPropertyWriter[])localArrayList.toArray(new BeanPropertyWriter[localArrayList.size()]));
    if (paramArrayOfString == null) {}
    for (paramArrayOfString = (String[])localObject;; paramArrayOfString = (BeanPropertyWriter[])paramArrayOfString.toArray(new BeanPropertyWriter[paramArrayOfString.size()]))
    {
      this._filteredProps = paramArrayOfString;
      this._typeId = paramBeanSerializerBase._typeId;
      this._anyGetterWriter = paramBeanSerializerBase._anyGetterWriter;
      this._objectIdWriter = paramBeanSerializerBase._objectIdWriter;
      this._propertyFilterId = paramBeanSerializerBase._propertyFilterId;
      this._serializationShape = paramBeanSerializerBase._serializationShape;
      return;
    }
  }
  
  private final String _customTypeId(Object paramObject)
  {
    paramObject = this._typeId.getValue(paramObject);
    if (paramObject == null) {
      return "";
    }
    if ((paramObject instanceof String)) {
      return (String)paramObject;
    }
    return paramObject.toString();
  }
  
  private static final BeanPropertyWriter[] rename(BeanPropertyWriter[] paramArrayOfBeanPropertyWriter, NameTransformer paramNameTransformer)
  {
    Object localObject;
    if ((paramArrayOfBeanPropertyWriter == null) || (paramArrayOfBeanPropertyWriter.length == 0) || (paramNameTransformer == null) || (paramNameTransformer == NameTransformer.NOP))
    {
      localObject = paramArrayOfBeanPropertyWriter;
      return (BeanPropertyWriter[])localObject;
    }
    int j = paramArrayOfBeanPropertyWriter.length;
    BeanPropertyWriter[] arrayOfBeanPropertyWriter = new BeanPropertyWriter[j];
    int i = 0;
    for (;;)
    {
      localObject = arrayOfBeanPropertyWriter;
      if (i >= j) {
        break;
      }
      localObject = paramArrayOfBeanPropertyWriter[i];
      if (localObject != null) {
        arrayOfBeanPropertyWriter[i] = ((BeanPropertyWriter)localObject).rename(paramNameTransformer);
      }
      i += 1;
    }
  }
  
  protected void _serializeObjectId(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer, WritableObjectId paramWritableObjectId)
    throws IOException, JsonProcessingException, JsonGenerationException
  {
    ObjectIdWriter localObjectIdWriter = this._objectIdWriter;
    String str;
    if (this._typeId == null)
    {
      str = null;
      if (str != null) {
        break label74;
      }
      paramTypeSerializer.writeTypePrefixForObject(paramObject, paramJsonGenerator);
      label28:
      paramWritableObjectId.writeAsField(paramJsonGenerator, paramSerializerProvider, localObjectIdWriter);
      if (this._propertyFilterId == null) {
        break label86;
      }
      serializeFieldsFiltered(paramObject, paramJsonGenerator, paramSerializerProvider);
    }
    for (;;)
    {
      if (str != null) {
        break label96;
      }
      paramTypeSerializer.writeTypeSuffixForObject(paramObject, paramJsonGenerator);
      return;
      str = _customTypeId(paramObject);
      break;
      label74:
      paramTypeSerializer.writeCustomTypePrefixForObject(paramObject, paramJsonGenerator, str);
      break label28;
      label86:
      serializeFields(paramObject, paramJsonGenerator, paramSerializerProvider);
    }
    label96:
    paramTypeSerializer.writeCustomTypeSuffixForObject(paramObject, paramJsonGenerator, str);
  }
  
  protected final void _serializeWithObjectId(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
    throws IOException, JsonGenerationException
  {
    ObjectIdWriter localObjectIdWriter = this._objectIdWriter;
    WritableObjectId localWritableObjectId = paramSerializerProvider.findObjectId(paramObject, localObjectIdWriter.generator);
    if (localWritableObjectId.writeAsId(paramJsonGenerator, paramSerializerProvider, localObjectIdWriter)) {
      return;
    }
    Object localObject = localWritableObjectId.generateId(paramObject);
    if (localObjectIdWriter.alwaysAsId)
    {
      localObjectIdWriter.serializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    _serializeObjectId(paramObject, paramJsonGenerator, paramSerializerProvider, paramTypeSerializer, localWritableObjectId);
  }
  
  protected final void _serializeWithObjectId(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, boolean paramBoolean)
    throws IOException, JsonGenerationException
  {
    ObjectIdWriter localObjectIdWriter = this._objectIdWriter;
    WritableObjectId localWritableObjectId = paramSerializerProvider.findObjectId(paramObject, localObjectIdWriter.generator);
    if (localWritableObjectId.writeAsId(paramJsonGenerator, paramSerializerProvider, localObjectIdWriter)) {}
    for (;;)
    {
      return;
      Object localObject = localWritableObjectId.generateId(paramObject);
      if (localObjectIdWriter.alwaysAsId)
      {
        localObjectIdWriter.serializer.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
        return;
      }
      if (paramBoolean) {
        paramJsonGenerator.writeStartObject();
      }
      localWritableObjectId.writeAsField(paramJsonGenerator, paramSerializerProvider, localObjectIdWriter);
      if (this._propertyFilterId != null) {
        serializeFieldsFiltered(paramObject, paramJsonGenerator, paramSerializerProvider);
      }
      while (paramBoolean)
      {
        paramJsonGenerator.writeEndObject();
        return;
        serializeFields(paramObject, paramJsonGenerator, paramSerializerProvider);
      }
    }
  }
  
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
    throws JsonMappingException
  {
    if (paramJsonFormatVisitorWrapper == null) {}
    for (;;)
    {
      return;
      paramJavaType = paramJsonFormatVisitorWrapper.expectObjectFormat(paramJavaType);
      if (paramJavaType != null)
      {
        int i;
        if (this._propertyFilterId != null)
        {
          PropertyFilter localPropertyFilter = findPropertyFilter(paramJsonFormatVisitorWrapper.getProvider(), this._propertyFilterId, null);
          i = 0;
          while (i < this._props.length)
          {
            localPropertyFilter.depositSchemaProperty(this._props[i], paramJavaType, paramJsonFormatVisitorWrapper.getProvider());
            i += 1;
          }
        }
        else
        {
          i = 0;
          while (i < this._props.length)
          {
            this._props[i].depositSchemaProperty(paramJavaType);
            i += 1;
          }
        }
      }
    }
  }
  
  protected abstract BeanSerializerBase asArraySerializer();
  
  public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    Object localObject5 = this._objectIdWriter;
    Object localObject4 = null;
    Object localObject7 = null;
    AnnotationIntrospector localAnnotationIntrospector = paramSerializerProvider.getAnnotationIntrospector();
    Object localObject2;
    Object localObject3;
    Object localObject6;
    String[] arrayOfString;
    Object localObject1;
    if ((paramBeanProperty == null) || (localAnnotationIntrospector == null))
    {
      localObject2 = null;
      localObject3 = localObject7;
      localObject6 = localObject5;
      if (localObject2 != null)
      {
        arrayOfString = localAnnotationIntrospector.findPropertiesToIgnore((Annotated)localObject2);
        localObject1 = localAnnotationIntrospector.findObjectIdInfo((Annotated)localObject2);
        if (localObject1 != null) {
          break label342;
        }
        localObject1 = localObject5;
        if (localObject5 != null)
        {
          localObject1 = localAnnotationIntrospector.findObjectReferenceInfo((Annotated)localObject2, new ObjectIdInfo(NAME_FOR_OBJECT_REF, null, null, null));
          localObject1 = this._objectIdWriter.withAlwaysAsId(((ObjectIdInfo)localObject1).getAlwaysAsId());
        }
      }
    }
    for (;;)
    {
      localObject5 = localAnnotationIntrospector.findFilterId((Annotated)localObject2);
      localObject4 = arrayOfString;
      localObject3 = localObject7;
      localObject6 = localObject1;
      if (localObject5 != null) {
        if (this._propertyFilterId != null)
        {
          localObject4 = arrayOfString;
          localObject3 = localObject7;
          localObject6 = localObject1;
          if (localObject5.equals(this._propertyFilterId)) {}
        }
        else
        {
          localObject3 = localObject5;
          localObject6 = localObject1;
          localObject4 = arrayOfString;
        }
      }
      localObject5 = this;
      localObject1 = localObject5;
      if (localObject6 != null)
      {
        paramSerializerProvider = ((ObjectIdWriter)localObject6).withSerializer(paramSerializerProvider.findValueSerializer(((ObjectIdWriter)localObject6).idType, paramBeanProperty));
        localObject1 = localObject5;
        if (paramSerializerProvider != this._objectIdWriter) {
          localObject1 = ((BeanSerializerBase)localObject5).withObjectIdWriter(paramSerializerProvider);
        }
      }
      paramBeanProperty = (BeanProperty)localObject1;
      if (localObject4 != null)
      {
        paramBeanProperty = (BeanProperty)localObject1;
        if (localObject4.length != 0) {
          paramBeanProperty = ((BeanSerializerBase)localObject1).withIgnorals((String[])localObject4);
        }
      }
      paramSerializerProvider = paramBeanProperty;
      if (localObject3 != null) {
        paramSerializerProvider = paramBeanProperty.withFilterId(localObject3);
      }
      localObject1 = null;
      paramBeanProperty = (BeanProperty)localObject1;
      if (localObject2 != null)
      {
        localObject2 = localAnnotationIntrospector.findFormat((Annotated)localObject2);
        paramBeanProperty = (BeanProperty)localObject1;
        if (localObject2 != null) {
          paramBeanProperty = ((JsonFormat.Value)localObject2).getShape();
        }
      }
      localObject1 = paramBeanProperty;
      if (paramBeanProperty == null) {
        localObject1 = this._serializationShape;
      }
      paramBeanProperty = paramSerializerProvider;
      if (localObject1 == JsonFormat.Shape.ARRAY) {
        paramBeanProperty = paramSerializerProvider.asArraySerializer();
      }
      return paramBeanProperty;
      localObject2 = paramBeanProperty.getMember();
      break;
      label342:
      localObject1 = localAnnotationIntrospector.findObjectReferenceInfo((Annotated)localObject2, (ObjectIdInfo)localObject1);
      localObject3 = ((ObjectIdInfo)localObject1).getGeneratorType();
      localObject4 = paramSerializerProvider.constructType((Type)localObject3);
      localObject4 = paramSerializerProvider.getTypeFactory().findTypeParameters(localObject4, ObjectIdGenerator.class)[0];
      if (localObject3 == ObjectIdGenerators.PropertyGenerator.class)
      {
        localObject4 = ((ObjectIdInfo)localObject1).getPropertyName().getSimpleName();
        int i = 0;
        int j = this._props.length;
        for (;;)
        {
          if (i == j) {
            throw new IllegalArgumentException("Invalid Object Id definition for " + this._handledType.getName() + ": can not find property with name '" + (String)localObject4 + "'");
          }
          localObject3 = this._props[i];
          if (((String)localObject4).equals(((BeanPropertyWriter)localObject3).getName()))
          {
            if (i > 0)
            {
              System.arraycopy(this._props, 0, this._props, 1, i);
              this._props[0] = localObject3;
              if (this._filteredProps != null)
              {
                localObject4 = this._filteredProps[i];
                System.arraycopy(this._filteredProps, 0, this._filteredProps, 1, i);
                this._filteredProps[0] = localObject4;
              }
            }
            localObject4 = ((BeanPropertyWriter)localObject3).getType();
            localObject3 = new PropertyBasedObjectIdGenerator((ObjectIdInfo)localObject1, (BeanPropertyWriter)localObject3);
            localObject1 = ObjectIdWriter.construct((JavaType)localObject4, (PropertyName)null, (ObjectIdGenerator)localObject3, ((ObjectIdInfo)localObject1).getAlwaysAsId());
            break;
          }
          i += 1;
        }
      }
      localObject3 = paramSerializerProvider.objectIdGeneratorInstance((Annotated)localObject2, (ObjectIdInfo)localObject1);
      localObject1 = ObjectIdWriter.construct((JavaType)localObject4, ((ObjectIdInfo)localObject1).getPropertyName(), (ObjectIdGenerator)localObject3, ((ObjectIdInfo)localObject1).getAlwaysAsId());
    }
  }
  
  protected JsonSerializer<Object> findConvertingSerializer(SerializerProvider paramSerializerProvider, BeanPropertyWriter paramBeanPropertyWriter)
    throws JsonMappingException
  {
    Object localObject = paramSerializerProvider.getAnnotationIntrospector();
    if (localObject != null)
    {
      localObject = ((AnnotationIntrospector)localObject).findSerializationConverter(paramBeanPropertyWriter.getMember());
      if (localObject != null)
      {
        localObject = paramSerializerProvider.converterInstance(paramBeanPropertyWriter.getMember(), localObject);
        JavaType localJavaType = ((Converter)localObject).getOutputType(paramSerializerProvider.getTypeFactory());
        return new StdDelegatingSerializer((Converter)localObject, localJavaType, paramSerializerProvider.findValueSerializer(localJavaType, paramBeanPropertyWriter));
      }
    }
    return null;
  }
  
  @Deprecated
  public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    throws JsonMappingException
  {
    ObjectNode localObjectNode1 = createSchemaNode("object", true);
    paramType = (JsonSerializableSchema)this._handledType.getAnnotation(JsonSerializableSchema.class);
    if (paramType != null)
    {
      paramType = paramType.id();
      if ((paramType != null) && (paramType.length() > 0)) {
        localObjectNode1.put("id", paramType);
      }
    }
    ObjectNode localObjectNode2 = localObjectNode1.objectNode();
    int i;
    label83:
    BeanPropertyWriter localBeanPropertyWriter;
    if (this._propertyFilterId != null)
    {
      paramType = findPropertyFilter(paramSerializerProvider, this._propertyFilterId, null);
      i = 0;
      if (i >= this._props.length) {
        break label138;
      }
      localBeanPropertyWriter = this._props[i];
      if (paramType != null) {
        break label124;
      }
      localBeanPropertyWriter.depositSchemaProperty(localObjectNode2, paramSerializerProvider);
    }
    for (;;)
    {
      i += 1;
      break label83;
      paramType = null;
      break;
      label124:
      paramType.depositSchemaProperty(localBeanPropertyWriter, localObjectNode2, paramSerializerProvider);
    }
    label138:
    localObjectNode1.put("properties", localObjectNode2);
    return localObjectNode1;
  }
  
  public void resolve(SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    int i;
    int j;
    label18:
    BeanPropertyWriter localBeanPropertyWriter;
    Object localObject1;
    Object localObject2;
    if (this._filteredProps == null)
    {
      i = 0;
      j = 0;
      int k = this._props.length;
      if (j >= k) {
        break label303;
      }
      localBeanPropertyWriter = this._props[j];
      if ((!localBeanPropertyWriter.willSuppressNulls()) && (!localBeanPropertyWriter.hasNullSerializer()))
      {
        localObject1 = paramSerializerProvider.findNullValueSerializer(localBeanPropertyWriter);
        if (localObject1 != null)
        {
          localBeanPropertyWriter.assignNullSerializer((JsonSerializer)localObject1);
          if (j < i)
          {
            localObject2 = this._filteredProps[j];
            if (localObject2 != null) {
              ((BeanPropertyWriter)localObject2).assignNullSerializer((JsonSerializer)localObject1);
            }
          }
        }
      }
      if (!localBeanPropertyWriter.hasSerializer()) {
        break label117;
      }
    }
    for (;;)
    {
      j += 1;
      break label18;
      i = this._filteredProps.length;
      break;
      label117:
      localObject2 = findConvertingSerializer(paramSerializerProvider, localBeanPropertyWriter);
      localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = localBeanPropertyWriter.getSerializationType();
        localObject2 = localObject1;
        if (localObject1 == null)
        {
          localObject1 = paramSerializerProvider.constructType(localBeanPropertyWriter.getGenericPropertyType());
          localObject2 = localObject1;
          if (!((JavaType)localObject1).isFinal())
          {
            if ((!((JavaType)localObject1).isContainerType()) && (((JavaType)localObject1).containedTypeCount() <= 0)) {
              continue;
            }
            localBeanPropertyWriter.setNonTrivialBaseType((JavaType)localObject1);
            continue;
          }
        }
        JsonSerializer localJsonSerializer = paramSerializerProvider.findValueSerializer((JavaType)localObject2, localBeanPropertyWriter);
        localObject1 = localJsonSerializer;
        if (((JavaType)localObject2).isContainerType())
        {
          localObject2 = (TypeSerializer)((JavaType)localObject2).getContentType().getTypeHandler();
          localObject1 = localJsonSerializer;
          if (localObject2 != null)
          {
            localObject1 = localJsonSerializer;
            if ((localJsonSerializer instanceof ContainerSerializer)) {
              localObject1 = ((ContainerSerializer)localJsonSerializer).withValueTypeSerializer((TypeSerializer)localObject2);
            }
          }
        }
      }
      localBeanPropertyWriter.assignSerializer((JsonSerializer)localObject1);
      if (j < i)
      {
        localObject2 = this._filteredProps[j];
        if (localObject2 != null) {
          ((BeanPropertyWriter)localObject2).assignSerializer((JsonSerializer)localObject1);
        }
      }
    }
    label303:
    if (this._anyGetterWriter != null) {
      this._anyGetterWriter.resolve(paramSerializerProvider);
    }
  }
  
  public abstract void serialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException;
  
  protected void serializeFields(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    BeanPropertyWriter[] arrayOfBeanPropertyWriter;
    if ((this._filteredProps != null) && (paramSerializerProvider.getActiveView() != null)) {
      arrayOfBeanPropertyWriter = this._filteredProps;
    }
    int k;
    for (;;)
    {
      int j = 0;
      k = 0;
      int i = 0;
      try
      {
        int m = arrayOfBeanPropertyWriter.length;
        for (;;)
        {
          if (i < m)
          {
            BeanPropertyWriter localBeanPropertyWriter = arrayOfBeanPropertyWriter[i];
            if (localBeanPropertyWriter != null)
            {
              j = i;
              k = i;
              localBeanPropertyWriter.serializeAsField(paramObject, paramJsonGenerator, paramSerializerProvider);
            }
            i += 1;
            continue;
            arrayOfBeanPropertyWriter = this._props;
            break;
          }
        }
        j = i;
        k = i;
        if (this._anyGetterWriter != null)
        {
          j = i;
          k = i;
          this._anyGetterWriter.getAndSerialize(paramObject, paramJsonGenerator, paramSerializerProvider);
        }
        return;
      }
      catch (Exception localException)
      {
        if (j == arrayOfBeanPropertyWriter.length) {}
        for (paramJsonGenerator = "[anySetter]";; paramJsonGenerator = arrayOfBeanPropertyWriter[j].getName())
        {
          wrapAndThrow(paramSerializerProvider, localException, paramObject, paramJsonGenerator);
          return;
        }
      }
      catch (StackOverflowError paramJsonGenerator)
      {
        paramSerializerProvider = new JsonMappingException("Infinite recursion (StackOverflowError)", paramJsonGenerator);
        if (k != arrayOfBeanPropertyWriter.length) {}
      }
    }
    for (paramJsonGenerator = "[anySetter]";; paramJsonGenerator = arrayOfBeanPropertyWriter[k].getName())
    {
      paramSerializerProvider.prependPath(new JsonMappingException.Reference(paramObject, paramJsonGenerator));
      throw paramSerializerProvider;
    }
  }
  
  protected void serializeFieldsFiltered(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    BeanPropertyWriter[] arrayOfBeanPropertyWriter;
    PropertyFilter localPropertyFilter;
    if ((this._filteredProps != null) && (paramSerializerProvider.getActiveView() != null))
    {
      arrayOfBeanPropertyWriter = this._filteredProps;
      localPropertyFilter = findPropertyFilter(paramSerializerProvider, this._propertyFilterId, paramObject);
      if (localPropertyFilter != null) {
        break label54;
      }
      serializeFields(paramObject, paramJsonGenerator, paramSerializerProvider);
    }
    for (;;)
    {
      return;
      arrayOfBeanPropertyWriter = this._props;
      break;
      label54:
      int j = 0;
      int k = 0;
      int i = 0;
      try
      {
        int m = arrayOfBeanPropertyWriter.length;
        if (i < m)
        {
          BeanPropertyWriter localBeanPropertyWriter = arrayOfBeanPropertyWriter[i];
          if (localBeanPropertyWriter != null)
          {
            j = i;
            k = i;
            localPropertyFilter.serializeAsField(paramObject, paramJsonGenerator, paramSerializerProvider, localBeanPropertyWriter);
          }
        }
        else
        {
          j = i;
          k = i;
          if (this._anyGetterWriter == null) {
            continue;
          }
          j = i;
          k = i;
          this._anyGetterWriter.getAndFilter(paramObject, paramJsonGenerator, paramSerializerProvider, localPropertyFilter);
          return;
        }
      }
      catch (Exception localException)
      {
        if (j == arrayOfBeanPropertyWriter.length) {}
        for (paramJsonGenerator = "[anySetter]";; paramJsonGenerator = arrayOfBeanPropertyWriter[j].getName())
        {
          wrapAndThrow(paramSerializerProvider, localException, paramObject, paramJsonGenerator);
          return;
        }
      }
      catch (StackOverflowError paramJsonGenerator)
      {
        for (;;)
        {
          paramSerializerProvider = new JsonMappingException("Infinite recursion (StackOverflowError)", paramJsonGenerator);
          if (k == arrayOfBeanPropertyWriter.length) {}
          for (paramJsonGenerator = "[anySetter]";; paramJsonGenerator = arrayOfBeanPropertyWriter[k].getName())
          {
            paramSerializerProvider.prependPath(new JsonMappingException.Reference(paramObject, paramJsonGenerator));
            throw paramSerializerProvider;
          }
          i += 1;
        }
      }
    }
  }
  
  public void serializeWithType(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
    throws IOException, JsonGenerationException
  {
    if (this._objectIdWriter != null)
    {
      _serializeWithObjectId(paramObject, paramJsonGenerator, paramSerializerProvider, paramTypeSerializer);
      return;
    }
    String str;
    if (this._typeId == null)
    {
      str = null;
      if (str != null) {
        break label76;
      }
      paramTypeSerializer.writeTypePrefixForObject(paramObject, paramJsonGenerator);
      label39:
      if (this._propertyFilterId == null) {
        break label88;
      }
      serializeFieldsFiltered(paramObject, paramJsonGenerator, paramSerializerProvider);
    }
    for (;;)
    {
      if (str != null) {
        break label98;
      }
      paramTypeSerializer.writeTypeSuffixForObject(paramObject, paramJsonGenerator);
      return;
      str = _customTypeId(paramObject);
      break;
      label76:
      paramTypeSerializer.writeCustomTypePrefixForObject(paramObject, paramJsonGenerator, str);
      break label39;
      label88:
      serializeFields(paramObject, paramJsonGenerator, paramSerializerProvider);
    }
    label98:
    paramTypeSerializer.writeCustomTypeSuffixForObject(paramObject, paramJsonGenerator, str);
  }
  
  public boolean usesObjectId()
  {
    return this._objectIdWriter != null;
  }
  
  protected abstract BeanSerializerBase withFilterId(Object paramObject);
  
  protected abstract BeanSerializerBase withIgnorals(String[] paramArrayOfString);
  
  public abstract BeanSerializerBase withObjectIdWriter(ObjectIdWriter paramObjectIdWriter);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/std/BeanSerializerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */