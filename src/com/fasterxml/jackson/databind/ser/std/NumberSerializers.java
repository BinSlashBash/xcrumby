package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class NumberSerializers
{
  public static void addAll(Map<String, JsonSerializer<?>> paramMap)
  {
    IntegerSerializer localIntegerSerializer = new IntegerSerializer();
    paramMap.put(Integer.class.getName(), localIntegerSerializer);
    paramMap.put(Integer.TYPE.getName(), localIntegerSerializer);
    paramMap.put(Long.class.getName(), LongSerializer.instance);
    paramMap.put(Long.TYPE.getName(), LongSerializer.instance);
    paramMap.put(Byte.class.getName(), IntLikeSerializer.instance);
    paramMap.put(Byte.TYPE.getName(), IntLikeSerializer.instance);
    paramMap.put(Short.class.getName(), ShortSerializer.instance);
    paramMap.put(Short.TYPE.getName(), ShortSerializer.instance);
    paramMap.put(Float.class.getName(), FloatSerializer.instance);
    paramMap.put(Float.TYPE.getName(), FloatSerializer.instance);
    paramMap.put(Double.class.getName(), DoubleSerializer.instance);
    paramMap.put(Double.TYPE.getName(), DoubleSerializer.instance);
  }
  
  protected static abstract class Base<T>
    extends StdScalarSerializer<T>
    implements ContextualSerializer
  {
    protected final JsonParser.NumberType _numberType;
    protected final String _schemaType;
    
    protected Base(Class<T> paramClass, JsonParser.NumberType paramNumberType, String paramString)
    {
      super();
      this._numberType = paramNumberType;
      this._schemaType = paramString;
    }
    
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
      throws JsonMappingException
    {
      paramJsonFormatVisitorWrapper = paramJsonFormatVisitorWrapper.expectIntegerFormat(paramJavaType);
      if (paramJsonFormatVisitorWrapper != null) {
        paramJsonFormatVisitorWrapper.numberType(this._numberType);
      }
    }
    
    public JsonSerializer<?> createContextual(SerializerProvider paramSerializerProvider, BeanProperty paramBeanProperty)
      throws JsonMappingException
    {
      if (paramBeanProperty != null)
      {
        paramSerializerProvider = paramSerializerProvider.getAnnotationIntrospector().findFormat(paramBeanProperty.getMember());
        if (paramSerializerProvider == null) {}
      }
      switch (NumberSerializers.1.$SwitchMap$com$fasterxml$jackson$annotation$JsonFormat$Shape[paramSerializerProvider.getShape().ordinal()])
      {
      default: 
        return this;
      }
      return ToStringSerializer.instance;
    }
    
    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
    {
      return createSchemaNode(this._schemaType, true);
    }
  }
  
  @JacksonStdImpl
  public static final class DoubleSerializer
    extends NumberSerializers.Base<Double>
  {
    static final DoubleSerializer instance = new DoubleSerializer();
    
    public DoubleSerializer()
    {
      super(JsonParser.NumberType.DOUBLE, "number");
    }
    
    public void serialize(Double paramDouble, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException
    {
      paramJsonGenerator.writeNumber(paramDouble.doubleValue());
    }
    
    public void serializeWithType(Double paramDouble, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
      throws IOException
    {
      serialize(paramDouble, paramJsonGenerator, paramSerializerProvider);
    }
  }
  
  @JacksonStdImpl
  public static final class FloatSerializer
    extends NumberSerializers.Base<Float>
  {
    static final FloatSerializer instance = new FloatSerializer();
    
    public FloatSerializer()
    {
      super(JsonParser.NumberType.FLOAT, "number");
    }
    
    public void serialize(Float paramFloat, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException
    {
      paramJsonGenerator.writeNumber(paramFloat.floatValue());
    }
  }
  
  @JacksonStdImpl
  public static final class IntLikeSerializer
    extends NumberSerializers.Base<Number>
  {
    static final IntLikeSerializer instance = new IntLikeSerializer();
    
    public IntLikeSerializer()
    {
      super(JsonParser.NumberType.INT, "integer");
    }
    
    public void serialize(Number paramNumber, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException
    {
      paramJsonGenerator.writeNumber(paramNumber.intValue());
    }
  }
  
  @JacksonStdImpl
  public static final class IntegerSerializer
    extends NumberSerializers.Base<Integer>
  {
    public IntegerSerializer()
    {
      super(JsonParser.NumberType.INT, "integer");
    }
    
    public void serialize(Integer paramInteger, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException
    {
      paramJsonGenerator.writeNumber(paramInteger.intValue());
    }
    
    public void serializeWithType(Integer paramInteger, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, TypeSerializer paramTypeSerializer)
      throws IOException
    {
      serialize(paramInteger, paramJsonGenerator, paramSerializerProvider);
    }
  }
  
  @JacksonStdImpl
  public static final class LongSerializer
    extends NumberSerializers.Base<Long>
  {
    static final LongSerializer instance = new LongSerializer();
    
    public LongSerializer()
    {
      super(JsonParser.NumberType.LONG, "number");
    }
    
    public void serialize(Long paramLong, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException
    {
      paramJsonGenerator.writeNumber(paramLong.longValue());
    }
  }
  
  @JacksonStdImpl
  public static final class ShortSerializer
    extends NumberSerializers.Base<Short>
  {
    static final ShortSerializer instance = new ShortSerializer();
    
    public ShortSerializer()
    {
      super(JsonParser.NumberType.INT, "number");
    }
    
    public void serialize(Short paramShort, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException
    {
      paramJsonGenerator.writeNumber(paramShort.shortValue());
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/std/NumberSerializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */