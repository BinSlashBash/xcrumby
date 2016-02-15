package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public abstract class MapperConfigBase<CFG extends ConfigFeature, T extends MapperConfigBase<CFG, T>>
  extends MapperConfig<T>
  implements Serializable
{
  private static final int DEFAULT_MAPPER_FEATURES = collectFeatureDefaults(MapperFeature.class);
  private static final long serialVersionUID = 6062961959359172474L;
  protected final ContextAttributes _attributes;
  protected final Map<ClassKey, Class<?>> _mixInAnnotations;
  protected final String _rootName;
  protected final SubtypeResolver _subtypeResolver;
  protected final Class<?> _view;
  
  protected MapperConfigBase(BaseSettings paramBaseSettings, SubtypeResolver paramSubtypeResolver, Map<ClassKey, Class<?>> paramMap)
  {
    super(paramBaseSettings, DEFAULT_MAPPER_FEATURES);
    this._mixInAnnotations = paramMap;
    this._subtypeResolver = paramSubtypeResolver;
    this._rootName = null;
    this._view = null;
    this._attributes = ContextAttributes.getEmpty();
  }
  
  protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase)
  {
    super(paramMapperConfigBase);
    this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
    this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
    this._rootName = paramMapperConfigBase._rootName;
    this._view = paramMapperConfigBase._view;
    this._attributes = paramMapperConfigBase._attributes;
  }
  
  protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, int paramInt)
  {
    super(paramMapperConfigBase._base, paramInt);
    this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
    this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
    this._rootName = paramMapperConfigBase._rootName;
    this._view = paramMapperConfigBase._view;
    this._attributes = paramMapperConfigBase._attributes;
  }
  
  protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, BaseSettings paramBaseSettings)
  {
    super(paramBaseSettings, paramMapperConfigBase._mapperFeatures);
    this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
    this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
    this._rootName = paramMapperConfigBase._rootName;
    this._view = paramMapperConfigBase._view;
    this._attributes = paramMapperConfigBase._attributes;
  }
  
  protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, ContextAttributes paramContextAttributes)
  {
    super(paramMapperConfigBase);
    this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
    this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
    this._rootName = paramMapperConfigBase._rootName;
    this._view = paramMapperConfigBase._view;
    this._attributes = paramContextAttributes;
  }
  
  protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, SubtypeResolver paramSubtypeResolver)
  {
    super(paramMapperConfigBase);
    this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
    this._subtypeResolver = paramSubtypeResolver;
    this._rootName = paramMapperConfigBase._rootName;
    this._view = paramMapperConfigBase._view;
    this._attributes = paramMapperConfigBase._attributes;
  }
  
  protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, Class<?> paramClass)
  {
    super(paramMapperConfigBase);
    this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
    this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
    this._rootName = paramMapperConfigBase._rootName;
    this._view = paramClass;
    this._attributes = paramMapperConfigBase._attributes;
  }
  
  protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, String paramString)
  {
    super(paramMapperConfigBase);
    this._mixInAnnotations = paramMapperConfigBase._mixInAnnotations;
    this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
    this._rootName = paramString;
    this._view = paramMapperConfigBase._view;
    this._attributes = paramMapperConfigBase._attributes;
  }
  
  protected MapperConfigBase(MapperConfigBase<CFG, T> paramMapperConfigBase, Map<ClassKey, Class<?>> paramMap)
  {
    super(paramMapperConfigBase);
    this._mixInAnnotations = paramMap;
    this._subtypeResolver = paramMapperConfigBase._subtypeResolver;
    this._rootName = paramMapperConfigBase._rootName;
    this._view = paramMapperConfigBase._view;
    this._attributes = paramMapperConfigBase._attributes;
  }
  
  public final Class<?> findMixInClassFor(Class<?> paramClass)
  {
    if (this._mixInAnnotations == null) {
      return null;
    }
    return (Class)this._mixInAnnotations.get(new ClassKey(paramClass));
  }
  
  public final Class<?> getActiveView()
  {
    return this._view;
  }
  
  public final ContextAttributes getAttributes()
  {
    return this._attributes;
  }
  
  public final String getRootName()
  {
    return this._rootName;
  }
  
  public final SubtypeResolver getSubtypeResolver()
  {
    return this._subtypeResolver;
  }
  
  public final int mixInCount()
  {
    if (this._mixInAnnotations == null) {
      return 0;
    }
    return this._mixInAnnotations.size();
  }
  
  public abstract T with(Base64Variant paramBase64Variant);
  
  public abstract T with(AnnotationIntrospector paramAnnotationIntrospector);
  
  public abstract T with(PropertyNamingStrategy paramPropertyNamingStrategy);
  
  public abstract T with(ContextAttributes paramContextAttributes);
  
  public abstract T with(HandlerInstantiator paramHandlerInstantiator);
  
  public abstract T with(ClassIntrospector paramClassIntrospector);
  
  public abstract T with(VisibilityChecker<?> paramVisibilityChecker);
  
  public abstract T with(SubtypeResolver paramSubtypeResolver);
  
  public abstract T with(TypeResolverBuilder<?> paramTypeResolverBuilder);
  
  public abstract T with(TypeFactory paramTypeFactory);
  
  public abstract T with(DateFormat paramDateFormat);
  
  public abstract T with(Locale paramLocale);
  
  public abstract T with(TimeZone paramTimeZone);
  
  public abstract T withAppendedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector);
  
  public T withAttribute(Object paramObject1, Object paramObject2)
  {
    return with(getAttributes().withSharedAttribute(paramObject1, paramObject2));
  }
  
  public T withAttributes(Map<Object, Object> paramMap)
  {
    return with(getAttributes().withSharedAttributes(paramMap));
  }
  
  public abstract T withInsertedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector);
  
  public abstract T withRootName(String paramString);
  
  public abstract T withView(Class<?> paramClass);
  
  public abstract T withVisibility(PropertyAccessor paramPropertyAccessor, JsonAutoDetect.Visibility paramVisibility);
  
  public T withoutAttribute(Object paramObject)
  {
    return with(getAttributes().withoutSharedAttribute(paramObject));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/cfg/MapperConfigBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */