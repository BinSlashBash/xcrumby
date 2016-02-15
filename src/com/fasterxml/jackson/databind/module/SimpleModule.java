package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.Module.SetupContext;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SimpleModule
  extends Module
  implements Serializable
{
  private static final long serialVersionUID = -8905749147637667249L;
  protected SimpleAbstractTypeResolver _abstractTypes = null;
  protected BeanDeserializerModifier _deserializerModifier = null;
  protected SimpleDeserializers _deserializers = null;
  protected SimpleKeyDeserializers _keyDeserializers = null;
  protected SimpleSerializers _keySerializers = null;
  protected HashMap<Class<?>, Class<?>> _mixins = null;
  protected final String _name;
  protected PropertyNamingStrategy _namingStrategy = null;
  protected BeanSerializerModifier _serializerModifier = null;
  protected SimpleSerializers _serializers = null;
  protected LinkedHashSet<NamedType> _subtypes = null;
  protected SimpleValueInstantiators _valueInstantiators = null;
  protected final Version _version;
  
  public SimpleModule()
  {
    this._name = ("SimpleModule-" + System.identityHashCode(this));
    this._version = Version.unknownVersion();
  }
  
  public SimpleModule(Version paramVersion)
  {
    this._name = paramVersion.getArtifactId();
    this._version = paramVersion;
  }
  
  public SimpleModule(String paramString)
  {
    this(paramString, Version.unknownVersion());
  }
  
  public SimpleModule(String paramString, Version paramVersion)
  {
    this._name = paramString;
    this._version = paramVersion;
  }
  
  public SimpleModule(String paramString, Version paramVersion, List<JsonSerializer<?>> paramList)
  {
    this(paramString, paramVersion, null, paramList);
  }
  
  public SimpleModule(String paramString, Version paramVersion, Map<Class<?>, JsonDeserializer<?>> paramMap)
  {
    this(paramString, paramVersion, paramMap, null);
  }
  
  public SimpleModule(String paramString, Version paramVersion, Map<Class<?>, JsonDeserializer<?>> paramMap, List<JsonSerializer<?>> paramList)
  {
    this._name = paramString;
    this._version = paramVersion;
    if (paramMap != null) {
      this._deserializers = new SimpleDeserializers(paramMap);
    }
    if (paramList != null) {
      this._serializers = new SimpleSerializers(paramList);
    }
  }
  
  public <T> SimpleModule addAbstractTypeMapping(Class<T> paramClass, Class<? extends T> paramClass1)
  {
    if (this._abstractTypes == null) {
      this._abstractTypes = new SimpleAbstractTypeResolver();
    }
    this._abstractTypes = this._abstractTypes.addMapping(paramClass, paramClass1);
    return this;
  }
  
  public <T> SimpleModule addDeserializer(Class<T> paramClass, JsonDeserializer<? extends T> paramJsonDeserializer)
  {
    if (this._deserializers == null) {
      this._deserializers = new SimpleDeserializers();
    }
    this._deserializers.addDeserializer(paramClass, paramJsonDeserializer);
    return this;
  }
  
  public SimpleModule addKeyDeserializer(Class<?> paramClass, KeyDeserializer paramKeyDeserializer)
  {
    if (this._keyDeserializers == null) {
      this._keyDeserializers = new SimpleKeyDeserializers();
    }
    this._keyDeserializers.addDeserializer(paramClass, paramKeyDeserializer);
    return this;
  }
  
  public <T> SimpleModule addKeySerializer(Class<? extends T> paramClass, JsonSerializer<T> paramJsonSerializer)
  {
    if (this._keySerializers == null) {
      this._keySerializers = new SimpleSerializers();
    }
    this._keySerializers.addSerializer(paramClass, paramJsonSerializer);
    return this;
  }
  
  public SimpleModule addSerializer(JsonSerializer<?> paramJsonSerializer)
  {
    if (this._serializers == null) {
      this._serializers = new SimpleSerializers();
    }
    this._serializers.addSerializer(paramJsonSerializer);
    return this;
  }
  
  public <T> SimpleModule addSerializer(Class<? extends T> paramClass, JsonSerializer<T> paramJsonSerializer)
  {
    if (this._serializers == null) {
      this._serializers = new SimpleSerializers();
    }
    this._serializers.addSerializer(paramClass, paramJsonSerializer);
    return this;
  }
  
  public SimpleModule addValueInstantiator(Class<?> paramClass, ValueInstantiator paramValueInstantiator)
  {
    if (this._valueInstantiators == null) {
      this._valueInstantiators = new SimpleValueInstantiators();
    }
    this._valueInstantiators = this._valueInstantiators.addValueInstantiator(paramClass, paramValueInstantiator);
    return this;
  }
  
  public String getModuleName()
  {
    return this._name;
  }
  
  public SimpleModule registerSubtypes(NamedType... paramVarArgs)
  {
    if (this._subtypes == null) {
      this._subtypes = new LinkedHashSet(Math.max(16, paramVarArgs.length));
    }
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      NamedType localNamedType = paramVarArgs[i];
      this._subtypes.add(localNamedType);
      i += 1;
    }
    return this;
  }
  
  public SimpleModule registerSubtypes(Class<?>... paramVarArgs)
  {
    if (this._subtypes == null) {
      this._subtypes = new LinkedHashSet(Math.max(16, paramVarArgs.length));
    }
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      Class<?> localClass = paramVarArgs[i];
      this._subtypes.add(new NamedType(localClass));
      i += 1;
    }
    return this;
  }
  
  public void setAbstractTypes(SimpleAbstractTypeResolver paramSimpleAbstractTypeResolver)
  {
    this._abstractTypes = paramSimpleAbstractTypeResolver;
  }
  
  public SimpleModule setDeserializerModifier(BeanDeserializerModifier paramBeanDeserializerModifier)
  {
    this._deserializerModifier = paramBeanDeserializerModifier;
    return this;
  }
  
  public void setDeserializers(SimpleDeserializers paramSimpleDeserializers)
  {
    this._deserializers = paramSimpleDeserializers;
  }
  
  public void setKeyDeserializers(SimpleKeyDeserializers paramSimpleKeyDeserializers)
  {
    this._keyDeserializers = paramSimpleKeyDeserializers;
  }
  
  public void setKeySerializers(SimpleSerializers paramSimpleSerializers)
  {
    this._keySerializers = paramSimpleSerializers;
  }
  
  public SimpleModule setMixInAnnotation(Class<?> paramClass1, Class<?> paramClass2)
  {
    if (this._mixins == null) {
      this._mixins = new HashMap();
    }
    this._mixins.put(paramClass1, paramClass2);
    return this;
  }
  
  protected SimpleModule setNamingStrategy(PropertyNamingStrategy paramPropertyNamingStrategy)
  {
    this._namingStrategy = paramPropertyNamingStrategy;
    return this;
  }
  
  public SimpleModule setSerializerModifier(BeanSerializerModifier paramBeanSerializerModifier)
  {
    this._serializerModifier = paramBeanSerializerModifier;
    return this;
  }
  
  public void setSerializers(SimpleSerializers paramSimpleSerializers)
  {
    this._serializers = paramSimpleSerializers;
  }
  
  public void setValueInstantiators(SimpleValueInstantiators paramSimpleValueInstantiators)
  {
    this._valueInstantiators = paramSimpleValueInstantiators;
  }
  
  public void setupModule(Module.SetupContext paramSetupContext)
  {
    if (this._serializers != null) {
      paramSetupContext.addSerializers(this._serializers);
    }
    if (this._deserializers != null) {
      paramSetupContext.addDeserializers(this._deserializers);
    }
    if (this._keySerializers != null) {
      paramSetupContext.addKeySerializers(this._keySerializers);
    }
    if (this._keyDeserializers != null) {
      paramSetupContext.addKeyDeserializers(this._keyDeserializers);
    }
    if (this._abstractTypes != null) {
      paramSetupContext.addAbstractTypeResolver(this._abstractTypes);
    }
    if (this._valueInstantiators != null) {
      paramSetupContext.addValueInstantiators(this._valueInstantiators);
    }
    if (this._deserializerModifier != null) {
      paramSetupContext.addBeanDeserializerModifier(this._deserializerModifier);
    }
    if (this._serializerModifier != null) {
      paramSetupContext.addBeanSerializerModifier(this._serializerModifier);
    }
    if ((this._subtypes != null) && (this._subtypes.size() > 0)) {
      paramSetupContext.registerSubtypes((NamedType[])this._subtypes.toArray(new NamedType[this._subtypes.size()]));
    }
    if (this._namingStrategy != null) {
      paramSetupContext.setNamingStrategy(this._namingStrategy);
    }
    if (this._mixins != null)
    {
      Iterator localIterator = this._mixins.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        paramSetupContext.setMixInAnnotations((Class)localEntry.getKey(), (Class)localEntry.getValue());
      }
    }
  }
  
  public Version version()
  {
    return this._version;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/module/SimpleModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */