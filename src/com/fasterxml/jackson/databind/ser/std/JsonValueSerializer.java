package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

@JacksonStdImpl
public class JsonValueSerializer
  extends StdSerializer<Object>
  implements ContextualSerializer, JsonFormatVisitable, SchemaAware
{
  protected final Method _accessorMethod;
  protected final boolean _forceTypeInformation;
  protected final BeanProperty _property;
  protected final JsonSerializer<Object> _valueSerializer;
  
  public JsonValueSerializer(JsonValueSerializer paramJsonValueSerializer, BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer, boolean paramBoolean)
  {
    super(_notNullClass(paramJsonValueSerializer.handledType()));
    this._accessorMethod = paramJsonValueSerializer._accessorMethod;
    this._valueSerializer = paramJsonSerializer;
    this._property = paramBeanProperty;
    this._forceTypeInformation = paramBoolean;
  }
  
  public JsonValueSerializer(Method paramMethod, JsonSerializer<Object> paramJsonSerializer)
  {
    super(Object.class);
    this._accessorMethod = paramMethod;
    this._valueSerializer = paramJsonSerializer;
    this._property = null;
    this._forceTypeInformation = true;
  }
  
  private static final Class<Object> _notNullClass(Class<?> paramClass)
  {
    Object localObject = paramClass;
    if (paramClass == null) {
      localObject = Object.class;
    }
    return (Class<Object>)localObject;
  }
  
  public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
    throws JsonMappingException
  {
    Object localObject1 = this._valueSerializer;
    Object localObject2 = localObject1;
    if (localObject1 == null)
    {
      localObject1 = paramJavaType;
      if (paramJavaType == null)
      {
        if (this._property != null) {
          paramJavaType = this._property.getType();
        }
        localObject1 = paramJavaType;
        if (paramJavaType == null) {
          localObject1 = paramJsonFormatVisitorWrapper.getProvider().constructType(this._accessorMethod.getReturnType());
        }
      }
      paramJavaType = paramJsonFormatVisitorWrapper.getProvider().findTypedValueSerializer((JavaType)localObject1, false, this._property);
      localObject2 = paramJavaType;
      if (paramJavaType == null)
      {
        paramJsonFormatVisitorWrapper.expectAnyFormat((JavaType)localObject1);
        return;
      }
    }
    ((JsonSerializer)localObject2).acceptJsonFormatVisitor(paramJsonFormatVisitorWrapper, null);
  }
  
  public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
    throws JsonMappingException
  {
    Object localObject = this._valueSerializer;
    if (localObject == null)
    {
      if (!paramSerializerProvider.isEnabled(MapperFeature.USE_STATIC_TYPING))
      {
        localObject = this;
        if (!Modifier.isFinal(this._accessorMethod.getReturnType().getModifiers())) {}
      }
      else
      {
        localObject = paramSerializerProvider.constructType(this._accessorMethod.getGenericReturnType());
        paramSerializerProvider = paramSerializerProvider.findPrimaryPropertySerializer((JavaType)localObject, this._property);
        localObject = withResolved(paramBeanProperty, paramSerializerProvider, isNaturalTypeWithStdHandling(((JavaType)localObject).getRawClass(), paramSerializerProvider));
      }
      return (JsonSerializer<?>)localObject;
    }
    return withResolved(paramBeanProperty, paramSerializerProvider.handlePrimaryContextualization((JsonSerializer)localObject, paramBeanProperty), this._forceTypeInformation);
  }
  
  public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    throws JsonMappingException
  {
    if ((this._valueSerializer instanceof SchemaAware)) {
      return ((SchemaAware)this._valueSerializer).getSchema(paramSerializerProvider, null);
    }
    return JsonSchema.getDefaultSchemaNode();
  }
  
  protected boolean isNaturalTypeWithStdHandling(Class<?> paramClass, JsonSerializer<?> paramJsonSerializer)
  {
    if (paramClass.isPrimitive())
    {
      if ((paramClass == Integer.TYPE) || (paramClass == Boolean.TYPE) || (paramClass == Double.TYPE)) {}
    }
    else {
      while ((paramClass != String.class) && (paramClass != Integer.class) && (paramClass != Boolean.class) && (paramClass != Double.class)) {
        return false;
      }
    }
    return isDefaultSerializer(paramJsonSerializer);
  }
  
  public void serialize(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
    throws IOException, JsonGenerationException
  {
    try
    {
      Object localObject = this._accessorMethod.invoke(paramObject, new Object[0]);
      if (localObject == null)
      {
        paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
        return;
      }
      JsonSerializer localJsonSerializer2 = this._valueSerializer;
      JsonSerializer localJsonSerializer1 = localJsonSerializer2;
      if (localJsonSerializer2 == null) {
        localJsonSerializer1 = paramSerializerProvider.findTypedValueSerializer(localObject.getClass(), true, this._property);
      }
      localJsonSerializer1.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
      return;
    }
    catch (IOException paramObject)
    {
      throw ((Throwable)paramObject);
    }
    catch (Exception paramJsonGenerator)
    {
      while (((paramJsonGenerator instanceof InvocationTargetException)) && (paramJsonGenerator.getCause() != null)) {
        paramJsonGenerator = paramJsonGenerator.getCause();
      }
      if ((paramJsonGenerator instanceof Error)) {
        throw ((Error)paramJsonGenerator);
      }
      throw JsonMappingException.wrapWithPath(paramJsonGenerator, paramObject, this._accessorMethod.getName() + "()");
    }
  }
  
  public void serializeWithType(Object paramObject, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
    throws IOException, JsonProcessingException
  {
    try
    {
      localObject = this._accessorMethod.invoke(paramObject, new Object[0]);
      if (localObject == null)
      {
        paramSerializerProvider.defaultSerializeNull(paramJsonGenerator);
        return;
      }
      localJsonSerializer2 = this._valueSerializer;
      if (localJsonSerializer2 != null) {
        break label66;
      }
      localJsonSerializer1 = paramSerializerProvider.findValueSerializer(localObject.getClass(), this._property);
    }
    catch (IOException paramObject)
    {
      Object localObject;
      JsonSerializer localJsonSerializer2;
      do
      {
        throw ((Throwable)paramObject);
        JsonSerializer localJsonSerializer1 = localJsonSerializer2;
      } while (!this._forceTypeInformation);
      paramTypeSerializer.writeTypePrefixForScalar(paramObject, paramJsonGenerator);
      localJsonSerializer2.serialize(localObject, paramJsonGenerator, paramSerializerProvider);
      paramTypeSerializer.writeTypeSuffixForScalar(paramObject, paramJsonGenerator);
      return;
    }
    catch (Exception paramJsonGenerator)
    {
      label66:
      while (((paramJsonGenerator instanceof InvocationTargetException)) && (paramJsonGenerator.getCause() != null)) {
        paramJsonGenerator = paramJsonGenerator.getCause();
      }
      if (!(paramJsonGenerator instanceof Error)) {
        break label136;
      }
      throw ((Error)paramJsonGenerator);
      label136:
      throw JsonMappingException.wrapWithPath(paramJsonGenerator, paramObject, this._accessorMethod.getName() + "()");
    }
    localJsonSerializer1.serializeWithType(localObject, paramJsonGenerator, paramSerializerProvider, paramTypeSerializer);
  }
  
  public String toString()
  {
    return "(@JsonValue serializer for method " + this._accessorMethod.getDeclaringClass() + "#" + this._accessorMethod.getName() + ")";
  }
  
  public JsonValueSerializer withResolved(BeanProperty paramBeanProperty, JsonSerializer<?> paramJsonSerializer, boolean paramBoolean)
  {
    if ((this._property == paramBeanProperty) && (this._valueSerializer == paramJsonSerializer) && (paramBoolean == this._forceTypeInformation)) {
      return this;
    }
    return new JsonValueSerializer(this, paramBeanProperty, paramJsonSerializer, paramBoolean);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/std/JsonValueSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */