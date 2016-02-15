package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.util.Iterator;
import java.util.Map.Entry;

public class UnwrappingBeanPropertyWriter
  extends BeanPropertyWriter
{
  protected final NameTransformer _nameTransformer;
  
  public UnwrappingBeanPropertyWriter(BeanPropertyWriter paramBeanPropertyWriter, NameTransformer paramNameTransformer)
  {
    super(paramBeanPropertyWriter);
    this._nameTransformer = paramNameTransformer;
  }
  
  private UnwrappingBeanPropertyWriter(UnwrappingBeanPropertyWriter paramUnwrappingBeanPropertyWriter, NameTransformer paramNameTransformer, SerializedString paramSerializedString)
  {
    super(paramUnwrappingBeanPropertyWriter, paramSerializedString);
    this._nameTransformer = paramNameTransformer;
  }
  
  protected void _depositSchemaProperty(ObjectNode paramObjectNode, JsonNode paramJsonNode)
  {
    paramJsonNode = paramJsonNode.get("properties");
    if (paramJsonNode != null)
    {
      Iterator localIterator = paramJsonNode.fields();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        String str = (String)localEntry.getKey();
        paramJsonNode = str;
        if (this._nameTransformer != null) {
          paramJsonNode = this._nameTransformer.transform(str);
        }
        paramObjectNode.set(paramJsonNode, (JsonNode)localEntry.getValue());
      }
    }
  }
  
  protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap paramPropertySerializerMap, Class<?> paramClass, SerializerProvider paramSerializerProvider)
    throws JsonMappingException
  {
    if (this._nonTrivialBaseType != null) {}
    for (paramPropertySerializerMap = paramSerializerProvider.findValueSerializer(paramSerializerProvider.constructSpecializedType(this._nonTrivialBaseType, paramClass), this);; paramPropertySerializerMap = paramSerializerProvider.findValueSerializer(paramClass, this))
    {
      NameTransformer localNameTransformer = this._nameTransformer;
      paramSerializerProvider = localNameTransformer;
      if (paramPropertySerializerMap.isUnwrappingSerializer()) {
        paramSerializerProvider = NameTransformer.chainedTransformer(localNameTransformer, ((UnwrappingBeanSerializer)paramPropertySerializerMap)._nameTransformer);
      }
      paramPropertySerializerMap = paramPropertySerializerMap.unwrappingSerializer(paramSerializerProvider);
      this._dynamicSerializers = this._dynamicSerializers.newWith(paramClass, paramPropertySerializerMap);
      return paramPropertySerializerMap;
    }
  }
  
  public void assignSerializer(JsonSerializer<Object> paramJsonSerializer)
  {
    super.assignSerializer(paramJsonSerializer);
    if (this._serializer != null)
    {
      NameTransformer localNameTransformer = this._nameTransformer;
      paramJsonSerializer = localNameTransformer;
      if (this._serializer.isUnwrappingSerializer()) {
        paramJsonSerializer = NameTransformer.chainedTransformer(localNameTransformer, ((UnwrappingBeanSerializer)this._serializer)._nameTransformer);
      }
      this._serializer = this._serializer.unwrappingSerializer(paramJsonSerializer);
    }
  }
  
  public boolean isUnwrapping()
  {
    return true;
  }
  
  public UnwrappingBeanPropertyWriter rename(NameTransformer paramNameTransformer)
  {
    String str = paramNameTransformer.transform(this._name.getValue());
    return new UnwrappingBeanPropertyWriter(this, NameTransformer.chainedTransformer(paramNameTransformer, this._nameTransformer), new SerializedString(str));
  }
  
  public void serializeAsField(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws Exception
  {
    Object localObject = get(paramObject);
    if (localObject == null) {}
    JsonSerializer localJsonSerializer1;
    label148:
    do
    {
      return;
      while ((localObject == paramObject) && (_handleSelfReference(paramObject, paramJsonGenerator, paramSerializerProvider, localJsonSerializer1))) {
        do
        {
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
            break;
          }
          if (MARKER_FOR_EMPTY != this._suppressableValue) {
            break label148;
          }
        } while (localJsonSerializer1.isEmpty(localObject));
      }
      if (!localJsonSerializer1.isUnwrappingSerializer()) {
        paramJsonGenerator.writeFieldName(this._name);
      }
      if (this._typeSerializer != null) {
        break;
      }
      localJsonSerializer1.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
      return;
    } while (!this._suppressableValue.equals(localObject));
    return;
    localJsonSerializer1.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, this._typeSerializer);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/impl/UnwrappingBeanPropertyWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */