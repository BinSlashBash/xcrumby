package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class StdArraySerializers
{
  protected static final HashMap<String, JsonSerializer<?>> _arraySerializers = new HashMap();
  
  static
  {
    _arraySerializers.put(boolean[].class.getName(), new BooleanArraySerializer());
    _arraySerializers.put(byte[].class.getName(), new ByteArraySerializer());
    _arraySerializers.put(char[].class.getName(), new CharArraySerializer());
    _arraySerializers.put(short[].class.getName(), new ShortArraySerializer());
    _arraySerializers.put(int[].class.getName(), new IntArraySerializer());
    _arraySerializers.put(long[].class.getName(), new LongArraySerializer());
    _arraySerializers.put(float[].class.getName(), new FloatArraySerializer());
    _arraySerializers.put(double[].class.getName(), new DoubleArraySerializer());
  }
  
  public static JsonSerializer<?> findStandardImpl(Class<?> paramClass)
  {
    return (JsonSerializer)_arraySerializers.get(paramClass.getName());
  }
  
  @JacksonStdImpl
  public static final class BooleanArraySerializer
    extends ArraySerializerBase<boolean[]>
  {
    private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Boolean.class);
    
    public BooleanArraySerializer()
    {
      super(null);
    }
    
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
    {
      return this;
    }
    
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
      throws JsonMappingException
    {
      if (paramJsonFormatVisitorWrapper != null)
      {
        paramJsonFormatVisitorWrapper = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
        if (paramJsonFormatVisitorWrapper != null) {
          paramJsonFormatVisitorWrapper.itemsFormat(JsonFormatTypes.BOOLEAN);
        }
      }
    }
    
    public JsonSerializer<?> getContentSerializer()
    {
      return null;
    }
    
    public JavaType getContentType()
    {
      return VALUE_TYPE;
    }
    
    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      paramSerializerProvider = createSchemaNode("array", true);
      paramSerializerProvider.set("items", createSchemaNode("boolean"));
      return paramSerializerProvider;
    }
    
    public boolean hasSingleElement(boolean[] paramArrayOfBoolean)
    {
      return paramArrayOfBoolean.length == 1;
    }
    
    public boolean isEmpty(boolean[] paramArrayOfBoolean)
    {
      return (paramArrayOfBoolean == null) || (paramArrayOfBoolean.length == 0);
    }
    
    public void serializeContents(boolean[] paramArrayOfBoolean, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      int i = 0;
      int j = paramArrayOfBoolean.length;
      while (i < j)
      {
        paramJsonGenerator.writeBoolean(paramArrayOfBoolean[i]);
        i += 1;
      }
    }
  }
  
  @JacksonStdImpl
  public static final class ByteArraySerializer
    extends StdSerializer<byte[]>
  {
    public ByteArraySerializer()
    {
      super();
    }
    
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
      throws JsonMappingException
    {
      if (paramJsonFormatVisitorWrapper != null)
      {
        paramJsonFormatVisitorWrapper = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
        if (paramJsonFormatVisitorWrapper != null) {
          paramJsonFormatVisitorWrapper.itemsFormat(JsonFormatTypes.STRING);
        }
      }
    }
    
    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode("array", true).set("items", createSchemaNode("string"));
    }
    
    public boolean isEmpty(byte[] paramArrayOfByte)
    {
      return (paramArrayOfByte == null) || (paramArrayOfByte.length == 0);
    }
    
    public void serialize(byte[] paramArrayOfByte, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      paramJsonGenerator.writeBinary(paramSerializerProvider.getConfig().getBase64Variant(), paramArrayOfByte, 0, paramArrayOfByte.length);
    }
    
    public void serializeWithType(byte[] paramArrayOfByte, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
      throws IOException, JsonGenerationException
    {
      paramTypeSerializer.writeTypePrefixForScalar(paramArrayOfByte, paramJsonGenerator);
      paramJsonGenerator.writeBinary(paramSerializerProvider.getConfig().getBase64Variant(), paramArrayOfByte, 0, paramArrayOfByte.length);
      paramTypeSerializer.writeTypeSuffixForScalar(paramArrayOfByte, paramJsonGenerator);
    }
  }
  
  @JacksonStdImpl
  public static final class CharArraySerializer
    extends StdSerializer<char[]>
  {
    public CharArraySerializer()
    {
      super();
    }
    
    private final void _writeArrayContents(JsonGenerator paramJsonGenerator, char[] paramArrayOfChar)
      throws IOException, JsonGenerationException
    {
      int i = 0;
      int j = paramArrayOfChar.length;
      while (i < j)
      {
        paramJsonGenerator.writeString(paramArrayOfChar, i, 1);
        i += 1;
      }
    }
    
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
      throws JsonMappingException
    {
      if (paramJsonFormatVisitorWrapper != null)
      {
        paramJsonFormatVisitorWrapper = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
        if (paramJsonFormatVisitorWrapper != null) {
          paramJsonFormatVisitorWrapper.itemsFormat(JsonFormatTypes.STRING);
        }
      }
    }
    
    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      paramSerializerProvider = createSchemaNode("array", true);
      paramType = createSchemaNode("string");
      paramType.put("type", "string");
      return paramSerializerProvider.set("items", paramType);
    }
    
    public boolean isEmpty(char[] paramArrayOfChar)
    {
      return (paramArrayOfChar == null) || (paramArrayOfChar.length == 0);
    }
    
    public void serialize(char[] paramArrayOfChar, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      if (paramSerializerProvider.isEnabled(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS))
      {
        paramJsonGenerator.writeStartArray();
        _writeArrayContents(paramJsonGenerator, paramArrayOfChar);
        paramJsonGenerator.writeEndArray();
        return;
      }
      paramJsonGenerator.writeString(paramArrayOfChar, 0, paramArrayOfChar.length);
    }
    
    public void serializeWithType(char[] paramArrayOfChar, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
      throws IOException, JsonGenerationException
    {
      if (paramSerializerProvider.isEnabled(SerializationFeature.WRITE_CHAR_ARRAYS_AS_JSON_ARRAYS))
      {
        paramTypeSerializer.writeTypePrefixForArray(paramArrayOfChar, paramJsonGenerator);
        _writeArrayContents(paramJsonGenerator, paramArrayOfChar);
        paramTypeSerializer.writeTypeSuffixForArray(paramArrayOfChar, paramJsonGenerator);
        return;
      }
      paramTypeSerializer.writeTypePrefixForScalar(paramArrayOfChar, paramJsonGenerator);
      paramJsonGenerator.writeString(paramArrayOfChar, 0, paramArrayOfChar.length);
      paramTypeSerializer.writeTypeSuffixForScalar(paramArrayOfChar, paramJsonGenerator);
    }
  }
  
  @JacksonStdImpl
  public static final class DoubleArraySerializer
    extends ArraySerializerBase<double[]>
  {
    private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Double.TYPE);
    
    public DoubleArraySerializer()
    {
      super(null);
    }
    
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
    {
      return this;
    }
    
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
      throws JsonMappingException
    {
      if (paramJsonFormatVisitorWrapper != null)
      {
        paramJsonFormatVisitorWrapper = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
        if (paramJsonFormatVisitorWrapper != null) {
          paramJsonFormatVisitorWrapper.itemsFormat(JsonFormatTypes.NUMBER);
        }
      }
    }
    
    public JsonSerializer<?> getContentSerializer()
    {
      return null;
    }
    
    public JavaType getContentType()
    {
      return VALUE_TYPE;
    }
    
    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode("array", true).set("items", createSchemaNode("number"));
    }
    
    public boolean hasSingleElement(double[] paramArrayOfDouble)
    {
      return paramArrayOfDouble.length == 1;
    }
    
    public boolean isEmpty(double[] paramArrayOfDouble)
    {
      return (paramArrayOfDouble == null) || (paramArrayOfDouble.length == 0);
    }
    
    public void serializeContents(double[] paramArrayOfDouble, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      int i = 0;
      int j = paramArrayOfDouble.length;
      while (i < j)
      {
        paramJsonGenerator.writeNumber(paramArrayOfDouble[i]);
        i += 1;
      }
    }
  }
  
  @JacksonStdImpl
  public static final class FloatArraySerializer
    extends StdArraySerializers.TypedPrimitiveArraySerializer<float[]>
  {
    private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Float.TYPE);
    
    public FloatArraySerializer()
    {
      super();
    }
    
    public FloatArraySerializer(FloatArraySerializer paramFloatArraySerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer)
    {
      super(paramBeanProperty, paramTypeSerializer);
    }
    
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
    {
      return new FloatArraySerializer(this, this._property, paramTypeSerializer);
    }
    
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
      throws JsonMappingException
    {
      if (paramJsonFormatVisitorWrapper != null)
      {
        paramJsonFormatVisitorWrapper = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
        if (paramJsonFormatVisitorWrapper != null) {
          paramJsonFormatVisitorWrapper.itemsFormat(JsonFormatTypes.NUMBER);
        }
      }
    }
    
    public JsonSerializer<?> getContentSerializer()
    {
      return null;
    }
    
    public JavaType getContentType()
    {
      return VALUE_TYPE;
    }
    
    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode("array", true).set("items", createSchemaNode("number"));
    }
    
    public boolean hasSingleElement(float[] paramArrayOfFloat)
    {
      return paramArrayOfFloat.length == 1;
    }
    
    public boolean isEmpty(float[] paramArrayOfFloat)
    {
      return (paramArrayOfFloat == null) || (paramArrayOfFloat.length == 0);
    }
    
    public void serializeContents(float[] paramArrayOfFloat, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      if (this._valueTypeSerializer != null)
      {
        i = 0;
        j = paramArrayOfFloat.length;
        while (i < j)
        {
          this._valueTypeSerializer.writeTypePrefixForScalar(null, paramJsonGenerator, Float.TYPE);
          paramJsonGenerator.writeNumber(paramArrayOfFloat[i]);
          this._valueTypeSerializer.writeTypeSuffixForScalar(null, paramJsonGenerator);
          i += 1;
        }
      }
      int i = 0;
      int j = paramArrayOfFloat.length;
      while (i < j)
      {
        paramJsonGenerator.writeNumber(paramArrayOfFloat[i]);
        i += 1;
      }
    }
  }
  
  @JacksonStdImpl
  public static final class IntArraySerializer
    extends ArraySerializerBase<int[]>
  {
    private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Integer.TYPE);
    
    public IntArraySerializer()
    {
      super(null);
    }
    
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
    {
      return this;
    }
    
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
      throws JsonMappingException
    {
      if (paramJsonFormatVisitorWrapper != null)
      {
        paramJsonFormatVisitorWrapper = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
        if (paramJsonFormatVisitorWrapper != null) {
          paramJsonFormatVisitorWrapper.itemsFormat(JsonFormatTypes.INTEGER);
        }
      }
    }
    
    public JsonSerializer<?> getContentSerializer()
    {
      return null;
    }
    
    public JavaType getContentType()
    {
      return VALUE_TYPE;
    }
    
    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode("array", true).set("items", createSchemaNode("integer"));
    }
    
    public boolean hasSingleElement(int[] paramArrayOfInt)
    {
      return paramArrayOfInt.length == 1;
    }
    
    public boolean isEmpty(int[] paramArrayOfInt)
    {
      return (paramArrayOfInt == null) || (paramArrayOfInt.length == 0);
    }
    
    public void serializeContents(int[] paramArrayOfInt, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      int i = 0;
      int j = paramArrayOfInt.length;
      while (i < j)
      {
        paramJsonGenerator.writeNumber(paramArrayOfInt[i]);
        i += 1;
      }
    }
  }
  
  @JacksonStdImpl
  public static final class LongArraySerializer
    extends StdArraySerializers.TypedPrimitiveArraySerializer<long[]>
  {
    private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Long.TYPE);
    
    public LongArraySerializer()
    {
      super();
    }
    
    public LongArraySerializer(LongArraySerializer paramLongArraySerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer)
    {
      super(paramBeanProperty, paramTypeSerializer);
    }
    
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
    {
      return new LongArraySerializer(this, this._property, paramTypeSerializer);
    }
    
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
      throws JsonMappingException
    {
      if (paramJsonFormatVisitorWrapper != null)
      {
        paramJsonFormatVisitorWrapper = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
        if (paramJsonFormatVisitorWrapper != null) {
          paramJsonFormatVisitorWrapper.itemsFormat(JsonFormatTypes.NUMBER);
        }
      }
    }
    
    public JsonSerializer<?> getContentSerializer()
    {
      return null;
    }
    
    public JavaType getContentType()
    {
      return VALUE_TYPE;
    }
    
    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode("array", true).set("items", createSchemaNode("number", true));
    }
    
    public boolean hasSingleElement(long[] paramArrayOfLong)
    {
      return paramArrayOfLong.length == 1;
    }
    
    public boolean isEmpty(long[] paramArrayOfLong)
    {
      return (paramArrayOfLong == null) || (paramArrayOfLong.length == 0);
    }
    
    public void serializeContents(long[] paramArrayOfLong, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      if (this._valueTypeSerializer != null)
      {
        i = 0;
        j = paramArrayOfLong.length;
        while (i < j)
        {
          this._valueTypeSerializer.writeTypePrefixForScalar(null, paramJsonGenerator, Long.TYPE);
          paramJsonGenerator.writeNumber(paramArrayOfLong[i]);
          this._valueTypeSerializer.writeTypeSuffixForScalar(null, paramJsonGenerator);
          i += 1;
        }
      }
      int i = 0;
      int j = paramArrayOfLong.length;
      while (i < j)
      {
        paramJsonGenerator.writeNumber(paramArrayOfLong[i]);
        i += 1;
      }
    }
  }
  
  @JacksonStdImpl
  public static final class ShortArraySerializer
    extends StdArraySerializers.TypedPrimitiveArraySerializer<short[]>
  {
    private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(Short.TYPE);
    
    public ShortArraySerializer()
    {
      super();
    }
    
    public ShortArraySerializer(ShortArraySerializer paramShortArraySerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer)
    {
      super(paramBeanProperty, paramTypeSerializer);
    }
    
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer paramTypeSerializer)
    {
      return new ShortArraySerializer(this, this._property, paramTypeSerializer);
    }
    
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
      throws JsonMappingException
    {
      if (paramJsonFormatVisitorWrapper != null)
      {
        paramJsonFormatVisitorWrapper = paramJsonFormatVisitorWrapper.expectArrayFormat(paramJavaType);
        if (paramJsonFormatVisitorWrapper != null) {
          paramJsonFormatVisitorWrapper.itemsFormat(JsonFormatTypes.INTEGER);
        }
      }
    }
    
    public JsonSerializer<?> getContentSerializer()
    {
      return null;
    }
    
    public JavaType getContentType()
    {
      return VALUE_TYPE;
    }
    
    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode("array", true).set("items", createSchemaNode("integer"));
    }
    
    public boolean hasSingleElement(short[] paramArrayOfShort)
    {
      return paramArrayOfShort.length == 1;
    }
    
    public boolean isEmpty(short[] paramArrayOfShort)
    {
      return (paramArrayOfShort == null) || (paramArrayOfShort.length == 0);
    }
    
    public void serializeContents(short[] paramArrayOfShort, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      if (this._valueTypeSerializer != null)
      {
        i = 0;
        j = paramArrayOfShort.length;
        while (i < j)
        {
          this._valueTypeSerializer.writeTypePrefixForScalar(null, paramJsonGenerator, Short.TYPE);
          paramJsonGenerator.writeNumber(paramArrayOfShort[i]);
          this._valueTypeSerializer.writeTypeSuffixForScalar(null, paramJsonGenerator);
          i += 1;
        }
      }
      int i = 0;
      int j = paramArrayOfShort.length;
      while (i < j)
      {
        paramJsonGenerator.writeNumber(paramArrayOfShort[i]);
        i += 1;
      }
    }
  }
  
  protected static abstract class TypedPrimitiveArraySerializer<T>
    extends ArraySerializerBase<T>
  {
    protected final TypeSerializer _valueTypeSerializer;
    
    protected TypedPrimitiveArraySerializer(TypedPrimitiveArraySerializer<T> paramTypedPrimitiveArraySerializer, BeanProperty paramBeanProperty, TypeSerializer paramTypeSerializer)
    {
      super(paramBeanProperty);
      this._valueTypeSerializer = paramTypeSerializer;
    }
    
    protected TypedPrimitiveArraySerializer(Class<T> paramClass)
    {
      super();
      this._valueTypeSerializer = null;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/std/StdArraySerializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */