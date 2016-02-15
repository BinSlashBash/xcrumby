package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

public class CoreXMLSerializers
  extends Serializers.Base
{
  public JsonSerializer<?> findSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription)
  {
    paramSerializationConfig = paramJavaType.getRawClass();
    if ((Duration.class.isAssignableFrom(paramSerializationConfig)) || (QName.class.isAssignableFrom(paramSerializationConfig))) {
      return ToStringSerializer.instance;
    }
    if (XMLGregorianCalendar.class.isAssignableFrom(paramSerializationConfig)) {
      return new XMLGregorianCalendarSerializer();
    }
    return null;
  }
  
  public static class XMLGregorianCalendarSerializer
    extends StdSerializer<XMLGregorianCalendar>
  {
    public XMLGregorianCalendarSerializer()
    {
      super();
    }
    
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper, JavaType paramJavaType)
      throws JsonMappingException
    {
      CalendarSerializer.instance.acceptJsonFormatVisitor(paramJsonFormatVisitorWrapper, null);
    }
    
    public JsonNode getSchema(SerializerProvider paramSerializerProvider, Type paramType)
      throws JsonMappingException
    {
      return CalendarSerializer.instance.getSchema(paramSerializerProvider, paramType);
    }
    
    public void serialize(XMLGregorianCalendar paramXMLGregorianCalendar, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
      throws IOException, JsonGenerationException
    {
      CalendarSerializer.instance.serialize(paramXMLGregorianCalendar.toGregorianCalendar(), paramJsonGenerator, paramSerializerProvider);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ext/CoreXMLSerializers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */