package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class SerializationConfig
  extends MapperConfigBase<SerializationFeature, SerializationConfig>
  implements Serializable
{
  private static final long serialVersionUID = -1278867172535832879L;
  protected final FilterProvider _filterProvider;
  protected final int _serFeatures;
  protected JsonInclude.Include _serializationInclusion = null;
  
  private SerializationConfig(SerializationConfig paramSerializationConfig, int paramInt1, int paramInt2)
  {
    super(paramSerializationConfig, paramInt1);
    this._serFeatures = paramInt2;
    this._serializationInclusion = paramSerializationConfig._serializationInclusion;
    this._filterProvider = paramSerializationConfig._filterProvider;
  }
  
  private SerializationConfig(SerializationConfig paramSerializationConfig, JsonInclude.Include paramInclude)
  {
    super(paramSerializationConfig);
    this._serFeatures = paramSerializationConfig._serFeatures;
    this._serializationInclusion = paramInclude;
    this._filterProvider = paramSerializationConfig._filterProvider;
  }
  
  private SerializationConfig(SerializationConfig paramSerializationConfig, BaseSettings paramBaseSettings)
  {
    super(paramSerializationConfig, paramBaseSettings);
    this._serFeatures = paramSerializationConfig._serFeatures;
    this._serializationInclusion = paramSerializationConfig._serializationInclusion;
    this._filterProvider = paramSerializationConfig._filterProvider;
  }
  
  protected SerializationConfig(SerializationConfig paramSerializationConfig, ContextAttributes paramContextAttributes)
  {
    super(paramSerializationConfig, paramContextAttributes);
    this._serFeatures = paramSerializationConfig._serFeatures;
    this._serializationInclusion = paramSerializationConfig._serializationInclusion;
    this._filterProvider = paramSerializationConfig._filterProvider;
  }
  
  private SerializationConfig(SerializationConfig paramSerializationConfig, SubtypeResolver paramSubtypeResolver)
  {
    super(paramSerializationConfig, paramSubtypeResolver);
    this._serFeatures = paramSerializationConfig._serFeatures;
    this._serializationInclusion = paramSerializationConfig._serializationInclusion;
    this._filterProvider = paramSerializationConfig._filterProvider;
  }
  
  private SerializationConfig(SerializationConfig paramSerializationConfig, FilterProvider paramFilterProvider)
  {
    super(paramSerializationConfig);
    this._serFeatures = paramSerializationConfig._serFeatures;
    this._serializationInclusion = paramSerializationConfig._serializationInclusion;
    this._filterProvider = paramFilterProvider;
  }
  
  private SerializationConfig(SerializationConfig paramSerializationConfig, Class<?> paramClass)
  {
    super(paramSerializationConfig, paramClass);
    this._serFeatures = paramSerializationConfig._serFeatures;
    this._serializationInclusion = paramSerializationConfig._serializationInclusion;
    this._filterProvider = paramSerializationConfig._filterProvider;
  }
  
  private SerializationConfig(SerializationConfig paramSerializationConfig, String paramString)
  {
    super(paramSerializationConfig, paramString);
    this._serFeatures = paramSerializationConfig._serFeatures;
    this._serializationInclusion = paramSerializationConfig._serializationInclusion;
    this._filterProvider = paramSerializationConfig._filterProvider;
  }
  
  protected SerializationConfig(SerializationConfig paramSerializationConfig, Map<ClassKey, Class<?>> paramMap)
  {
    super(paramSerializationConfig, paramMap);
    this._serFeatures = paramSerializationConfig._serFeatures;
    this._serializationInclusion = paramSerializationConfig._serializationInclusion;
    this._filterProvider = paramSerializationConfig._filterProvider;
  }
  
  public SerializationConfig(BaseSettings paramBaseSettings, SubtypeResolver paramSubtypeResolver, Map<ClassKey, Class<?>> paramMap)
  {
    super(paramBaseSettings, paramSubtypeResolver, paramMap);
    this._serFeatures = collectFeatureDefaults(SerializationFeature.class);
    this._filterProvider = null;
  }
  
  private final SerializationConfig _withBase(BaseSettings paramBaseSettings)
  {
    if (this._base == paramBaseSettings) {
      return this;
    }
    return new SerializationConfig(this, paramBaseSettings);
  }
  
  public AnnotationIntrospector getAnnotationIntrospector()
  {
    if (isEnabled(MapperFeature.USE_ANNOTATIONS)) {
      return super.getAnnotationIntrospector();
    }
    return AnnotationIntrospector.nopInstance();
  }
  
  public VisibilityChecker<?> getDefaultVisibilityChecker()
  {
    Object localObject2 = super.getDefaultVisibilityChecker();
    Object localObject1 = localObject2;
    if (!isEnabled(MapperFeature.AUTO_DETECT_GETTERS)) {
      localObject1 = ((VisibilityChecker)localObject2).withGetterVisibility(JsonAutoDetect.Visibility.NONE);
    }
    localObject2 = localObject1;
    if (!isEnabled(MapperFeature.AUTO_DETECT_IS_GETTERS)) {
      localObject2 = ((VisibilityChecker)localObject1).withIsGetterVisibility(JsonAutoDetect.Visibility.NONE);
    }
    localObject1 = localObject2;
    if (!isEnabled(MapperFeature.AUTO_DETECT_FIELDS)) {
      localObject1 = ((VisibilityChecker)localObject2).withFieldVisibility(JsonAutoDetect.Visibility.NONE);
    }
    return (VisibilityChecker<?>)localObject1;
  }
  
  public FilterProvider getFilterProvider()
  {
    return this._filterProvider;
  }
  
  public final int getSerializationFeatures()
  {
    return this._serFeatures;
  }
  
  public JsonInclude.Include getSerializationInclusion()
  {
    if (this._serializationInclusion != null) {
      return this._serializationInclusion;
    }
    return JsonInclude.Include.ALWAYS;
  }
  
  public final boolean hasSerializationFeatures(int paramInt)
  {
    return (this._serFeatures & paramInt) == paramInt;
  }
  
  public <T extends BeanDescription> T introspect(JavaType paramJavaType)
  {
    return getClassIntrospector().forSerialization(this, paramJavaType, this);
  }
  
  public BeanDescription introspectClassAnnotations(JavaType paramJavaType)
  {
    return getClassIntrospector().forClassAnnotations(this, paramJavaType, this);
  }
  
  public BeanDescription introspectDirectClassAnnotations(JavaType paramJavaType)
  {
    return getClassIntrospector().forDirectClassAnnotations(this, paramJavaType, this);
  }
  
  public final boolean isEnabled(SerializationFeature paramSerializationFeature)
  {
    return (this._serFeatures & paramSerializationFeature.getMask()) != 0;
  }
  
  public String toString()
  {
    return "[SerializationConfig: flags=0x" + Integer.toHexString(this._serFeatures) + "]";
  }
  
  public boolean useRootWrapping()
  {
    if (this._rootName != null) {
      return this._rootName.length() > 0;
    }
    return isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
  }
  
  public SerializationConfig with(Base64Variant paramBase64Variant)
  {
    return _withBase(this._base.with(paramBase64Variant));
  }
  
  public SerializationConfig with(AnnotationIntrospector paramAnnotationIntrospector)
  {
    return _withBase(this._base.withAnnotationIntrospector(paramAnnotationIntrospector));
  }
  
  public SerializationConfig with(MapperFeature paramMapperFeature, boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int i = this._mapperFeatures | paramMapperFeature.getMask(); i == this._mapperFeatures; i = this._mapperFeatures & (paramMapperFeature.getMask() ^ 0xFFFFFFFF)) {
      return this;
    }
    return new SerializationConfig(this, i, this._serFeatures);
  }
  
  public SerializationConfig with(PropertyNamingStrategy paramPropertyNamingStrategy)
  {
    return _withBase(this._base.withPropertyNamingStrategy(paramPropertyNamingStrategy));
  }
  
  public SerializationConfig with(SerializationFeature paramSerializationFeature)
  {
    int i = this._serFeatures | paramSerializationFeature.getMask();
    if (i == this._serFeatures) {
      return this;
    }
    return new SerializationConfig(this, this._mapperFeatures, i);
  }
  
  public SerializationConfig with(SerializationFeature paramSerializationFeature, SerializationFeature... paramVarArgs)
  {
    int j = this._serFeatures | paramSerializationFeature.getMask();
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      j |= paramVarArgs[i].getMask();
      i += 1;
    }
    if (j == this._serFeatures) {
      return this;
    }
    return new SerializationConfig(this, this._mapperFeatures, j);
  }
  
  public SerializationConfig with(ContextAttributes paramContextAttributes)
  {
    if (paramContextAttributes == this._attributes) {
      return this;
    }
    return new SerializationConfig(this, paramContextAttributes);
  }
  
  public SerializationConfig with(HandlerInstantiator paramHandlerInstantiator)
  {
    return _withBase(this._base.withHandlerInstantiator(paramHandlerInstantiator));
  }
  
  public SerializationConfig with(ClassIntrospector paramClassIntrospector)
  {
    return _withBase(this._base.withClassIntrospector(paramClassIntrospector));
  }
  
  public SerializationConfig with(VisibilityChecker<?> paramVisibilityChecker)
  {
    return _withBase(this._base.withVisibilityChecker(paramVisibilityChecker));
  }
  
  public SerializationConfig with(SubtypeResolver paramSubtypeResolver)
  {
    if (paramSubtypeResolver == this._subtypeResolver) {
      return this;
    }
    return new SerializationConfig(this, paramSubtypeResolver);
  }
  
  public SerializationConfig with(TypeResolverBuilder<?> paramTypeResolverBuilder)
  {
    return _withBase(this._base.withTypeResolverBuilder(paramTypeResolverBuilder));
  }
  
  public SerializationConfig with(TypeFactory paramTypeFactory)
  {
    return _withBase(this._base.withTypeFactory(paramTypeFactory));
  }
  
  public SerializationConfig with(DateFormat paramDateFormat)
  {
    SerializationConfig localSerializationConfig = new SerializationConfig(this, this._base.withDateFormat(paramDateFormat));
    if (paramDateFormat == null) {
      return localSerializationConfig.with(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    return localSerializationConfig.without(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }
  
  public SerializationConfig with(Locale paramLocale)
  {
    return _withBase(this._base.with(paramLocale));
  }
  
  public SerializationConfig with(TimeZone paramTimeZone)
  {
    return _withBase(this._base.with(paramTimeZone));
  }
  
  public SerializationConfig with(MapperFeature... paramVarArgs)
  {
    int j = this._mapperFeatures;
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      j |= paramVarArgs[i].getMask();
      i += 1;
    }
    if (j == this._mapperFeatures) {
      return this;
    }
    return new SerializationConfig(this, j, this._serFeatures);
  }
  
  public SerializationConfig withAppendedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector)
  {
    return _withBase(this._base.withAppendedAnnotationIntrospector(paramAnnotationIntrospector));
  }
  
  public SerializationConfig withFeatures(SerializationFeature... paramVarArgs)
  {
    int j = this._serFeatures;
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      j |= paramVarArgs[i].getMask();
      i += 1;
    }
    if (j == this._serFeatures) {
      return this;
    }
    return new SerializationConfig(this, this._mapperFeatures, j);
  }
  
  public SerializationConfig withFilters(FilterProvider paramFilterProvider)
  {
    if (paramFilterProvider == this._filterProvider) {
      return this;
    }
    return new SerializationConfig(this, paramFilterProvider);
  }
  
  public SerializationConfig withInsertedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector)
  {
    return _withBase(this._base.withInsertedAnnotationIntrospector(paramAnnotationIntrospector));
  }
  
  public SerializationConfig withRootName(String paramString)
  {
    if (paramString == null)
    {
      if (this._rootName != null) {}
    }
    else {
      while (paramString.equals(this._rootName)) {
        return this;
      }
    }
    return new SerializationConfig(this, paramString);
  }
  
  public SerializationConfig withSerializationInclusion(JsonInclude.Include paramInclude)
  {
    if (this._serializationInclusion == paramInclude) {
      return this;
    }
    return new SerializationConfig(this, paramInclude);
  }
  
  public SerializationConfig withView(Class<?> paramClass)
  {
    if (this._view == paramClass) {
      return this;
    }
    return new SerializationConfig(this, paramClass);
  }
  
  public SerializationConfig withVisibility(PropertyAccessor paramPropertyAccessor, JsonAutoDetect.Visibility paramVisibility)
  {
    return _withBase(this._base.withVisibility(paramPropertyAccessor, paramVisibility));
  }
  
  public SerializationConfig without(SerializationFeature paramSerializationFeature)
  {
    int i = this._serFeatures & (paramSerializationFeature.getMask() ^ 0xFFFFFFFF);
    if (i == this._serFeatures) {
      return this;
    }
    return new SerializationConfig(this, this._mapperFeatures, i);
  }
  
  public SerializationConfig without(SerializationFeature paramSerializationFeature, SerializationFeature... paramVarArgs)
  {
    int j = this._serFeatures & (paramSerializationFeature.getMask() ^ 0xFFFFFFFF);
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      j &= (paramVarArgs[i].getMask() ^ 0xFFFFFFFF);
      i += 1;
    }
    if (j == this._serFeatures) {
      return this;
    }
    return new SerializationConfig(this, this._mapperFeatures, j);
  }
  
  public SerializationConfig without(MapperFeature... paramVarArgs)
  {
    int j = this._mapperFeatures;
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      j &= (paramVarArgs[i].getMask() ^ 0xFFFFFFFF);
      i += 1;
    }
    if (j == this._mapperFeatures) {
      return this;
    }
    return new SerializationConfig(this, j, this._serFeatures);
  }
  
  public SerializationConfig withoutFeatures(SerializationFeature... paramVarArgs)
  {
    int j = this._serFeatures;
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      j &= (paramVarArgs[i].getMask() ^ 0xFFFFFFFF);
      i += 1;
    }
    if (j == this._serFeatures) {
      return this;
    }
    return new SerializationConfig(this, this._mapperFeatures, j);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/SerializationConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */