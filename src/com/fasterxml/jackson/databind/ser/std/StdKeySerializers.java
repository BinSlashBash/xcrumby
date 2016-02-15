package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class StdKeySerializers
{
  protected static final JsonSerializer<Object> DEFAULT_KEY_SERIALIZER = new StdKeySerializer();
  protected static final JsonSerializer<Object> DEFAULT_STRING_SERIALIZER = new StringKeySerializer();
  
  public static JsonSerializer<Object> getStdKeySerializer(JavaType paramJavaType)
  {
    if (paramJavaType == null) {
      return DEFAULT_KEY_SERIALIZER;
    }
    paramJavaType = paramJavaType.getRawClass();
    if (paramJavaType == String.class) {
      return DEFAULT_STRING_SERIALIZER;
    }
    if ((paramJavaType == Object.class) || (paramJavaType.isPrimitive()) || (Number.class.isAssignableFrom(paramJavaType))) {
      return DEFAULT_KEY_SERIALIZER;
    }
    if (Date.class.isAssignableFrom(paramJavaType)) {
      return DateKeySerializer.instance;
    }
    if (Calendar.class.isAssignableFrom(paramJavaType)) {
      return CalendarKeySerializer.instance;
    }
    return DEFAULT_KEY_SERIALIZER;
  }
  
  public static class CalendarKeySerializer
    extends StdSerializer<Calendar>
  {
    protected static final JsonSerializer<?> instance = new CalendarKeySerializer();
    
    public CalendarKeySerializer()
    {
      super();
    }
    
    public void serialize(Calendar paramCalendar, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      paramSerializerProvider.defaultSerializeDateKey(paramCalendar.getTimeInMillis(), paramJsonGenerator);
    }
  }
  
  public static class DateKeySerializer
    extends StdSerializer<Date>
  {
    protected static final JsonSerializer<?> instance = new DateKeySerializer();
    
    public DateKeySerializer()
    {
      super();
    }
    
    public void serialize(Date paramDate, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      paramSerializerProvider.defaultSerializeDateKey(paramDate, paramJsonGenerator);
    }
  }
  
  public static class StringKeySerializer
    extends StdSerializer<String>
  {
    public StringKeySerializer()
    {
      super();
    }
    
    public void serialize(String paramString, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      paramJsonGenerator.writeFieldName(paramString);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/std/StdKeySerializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */