package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.ser.Serializers;
import java.io.Serializable;

public class OptionalHandlerFactory
  implements Serializable
{
  private static final String CLASS_NAME_DOM_DOCUMENT = "org.w3c.dom.Node";
  private static final String CLASS_NAME_DOM_NODE = "org.w3c.dom.Node";
  private static final String DESERIALIZERS_FOR_JAVAX_XML = "com.fasterxml.jackson.databind.ext.CoreXMLDeserializers";
  private static final String DESERIALIZER_FOR_DOM_DOCUMENT = "com.fasterxml.jackson.databind.ext.DOMDeserializer$DocumentDeserializer";
  private static final String DESERIALIZER_FOR_DOM_NODE = "com.fasterxml.jackson.databind.ext.DOMDeserializer$NodeDeserializer";
  private static final String PACKAGE_PREFIX_JAVAX_XML = "javax.xml.";
  private static final String SERIALIZERS_FOR_JAVAX_XML = "com.fasterxml.jackson.databind.ext.CoreXMLSerializers";
  private static final String SERIALIZER_FOR_DOM_NODE = "com.fasterxml.jackson.databind.ext.DOMSerializer";
  public static final OptionalHandlerFactory instance = new OptionalHandlerFactory();
  private static final long serialVersionUID = 1L;
  
  private boolean doesImplement(Class<?> paramClass, String paramString)
  {
    while (paramClass != null)
    {
      if (paramClass.getName().equals(paramString)) {}
      while (hasInterface(paramClass, paramString)) {
        return true;
      }
      paramClass = paramClass.getSuperclass();
    }
    return false;
  }
  
  private boolean hasInterface(Class<?> paramClass, String paramString)
  {
    paramClass = paramClass.getInterfaces();
    int j = paramClass.length;
    int i = 0;
    while (i < j)
    {
      if (paramClass[i].getName().equals(paramString)) {
        return true;
      }
      i += 1;
    }
    j = paramClass.length;
    i = 0;
    for (;;)
    {
      if (i >= j) {
        break label69;
      }
      if (hasInterface(paramClass[i], paramString)) {
        break;
      }
      i += 1;
    }
    label69:
    return false;
  }
  
  private boolean hasInterfaceStartingWith(Class<?> paramClass, String paramString)
  {
    paramClass = paramClass.getInterfaces();
    int j = paramClass.length;
    int i = 0;
    while (i < j)
    {
      if (paramClass[i].getName().startsWith(paramString)) {
        return true;
      }
      i += 1;
    }
    j = paramClass.length;
    i = 0;
    for (;;)
    {
      if (i >= j) {
        break label69;
      }
      if (hasInterfaceStartingWith(paramClass[i], paramString)) {
        break;
      }
      i += 1;
    }
    label69:
    return false;
  }
  
  private boolean hasSupertypeStartingWith(Class<?> paramClass, String paramString)
  {
    for (Class localClass = paramClass.getSuperclass(); localClass != null; localClass = localClass.getSuperclass()) {
      if (localClass.getName().startsWith(paramString)) {
        return true;
      }
    }
    for (;;)
    {
      if (paramClass == null) {
        break label51;
      }
      if (hasInterfaceStartingWith(paramClass, paramString)) {
        break;
      }
      paramClass = paramClass.getSuperclass();
    }
    label51:
    return false;
  }
  
  private Object instantiate(String paramString)
  {
    try
    {
      paramString = Class.forName(paramString).newInstance();
      return paramString;
    }
    catch (Exception paramString)
    {
      return null;
    }
    catch (LinkageError paramString)
    {
      for (;;) {}
    }
  }
  
  public JsonDeserializer<?> findDeserializer(JavaType paramJavaType, DeserializationConfig paramDeserializationConfig, BeanDescription paramBeanDescription)
    throws JsonMappingException
  {
    Object localObject = paramJavaType.getRawClass();
    if ((((Class)localObject).getName().startsWith("javax.xml.")) || (hasSupertypeStartingWith((Class)localObject, "javax.xml.")))
    {
      localObject = instantiate("com.fasterxml.jackson.databind.ext.CoreXMLDeserializers");
      if (localObject != null) {}
    }
    else
    {
      do
      {
        return null;
        if (doesImplement((Class)localObject, "org.w3c.dom.Node")) {
          return (JsonDeserializer)instantiate("com.fasterxml.jackson.databind.ext.DOMDeserializer$DocumentDeserializer");
        }
      } while (!doesImplement((Class)localObject, "org.w3c.dom.Node"));
      return (JsonDeserializer)instantiate("com.fasterxml.jackson.databind.ext.DOMDeserializer$NodeDeserializer");
    }
    return ((Deserializers)localObject).findBeanDeserializer(paramJavaType, paramDeserializationConfig, paramBeanDescription);
  }
  
  public JsonSerializer<?> findSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType, BeanDescription paramBeanDescription)
  {
    Object localObject = paramJavaType.getRawClass();
    if ((((Class)localObject).getName().startsWith("javax.xml.")) || (hasSupertypeStartingWith((Class)localObject, "javax.xml.")))
    {
      localObject = instantiate("com.fasterxml.jackson.databind.ext.CoreXMLSerializers");
      if (localObject != null) {}
    }
    else
    {
      while (!doesImplement((Class)localObject, "org.w3c.dom.Node")) {
        return null;
      }
      return (JsonSerializer)instantiate("com.fasterxml.jackson.databind.ext.DOMSerializer");
    }
    return ((Serializers)localObject).findSerializer(paramSerializationConfig, paramJavaType, paramBeanDescription);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ext/OptionalHandlerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */