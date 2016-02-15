package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.LinkedNode;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class DeserializationConfig
  extends MapperConfigBase<DeserializationFeature, DeserializationConfig>
  implements Serializable
{
  private static final long serialVersionUID = -4227480407273773599L;
  protected final int _deserFeatures;
  protected final JsonNodeFactory _nodeFactory;
  protected final LinkedNode<DeserializationProblemHandler> _problemHandlers;
  
  private DeserializationConfig(DeserializationConfig paramDeserializationConfig, int paramInt1, int paramInt2)
  {
    super(paramDeserializationConfig, paramInt1);
    this._deserFeatures = paramInt2;
    this._nodeFactory = paramDeserializationConfig._nodeFactory;
    this._problemHandlers = paramDeserializationConfig._problemHandlers;
  }
  
  private DeserializationConfig(DeserializationConfig paramDeserializationConfig, BaseSettings paramBaseSettings)
  {
    super(paramDeserializationConfig, paramBaseSettings);
    this._deserFeatures = paramDeserializationConfig._deserFeatures;
    this._nodeFactory = paramDeserializationConfig._nodeFactory;
    this._problemHandlers = paramDeserializationConfig._problemHandlers;
  }
  
  protected DeserializationConfig(DeserializationConfig paramDeserializationConfig, ContextAttributes paramContextAttributes)
  {
    super(paramDeserializationConfig, paramContextAttributes);
    this._deserFeatures = paramDeserializationConfig._deserFeatures;
    this._problemHandlers = paramDeserializationConfig._problemHandlers;
    this._nodeFactory = paramDeserializationConfig._nodeFactory;
  }
  
  private DeserializationConfig(DeserializationConfig paramDeserializationConfig, SubtypeResolver paramSubtypeResolver)
  {
    super(paramDeserializationConfig, paramSubtypeResolver);
    this._deserFeatures = paramDeserializationConfig._deserFeatures;
    this._nodeFactory = paramDeserializationConfig._nodeFactory;
    this._problemHandlers = paramDeserializationConfig._problemHandlers;
  }
  
  private DeserializationConfig(DeserializationConfig paramDeserializationConfig, JsonNodeFactory paramJsonNodeFactory)
  {
    super(paramDeserializationConfig);
    this._deserFeatures = paramDeserializationConfig._deserFeatures;
    this._problemHandlers = paramDeserializationConfig._problemHandlers;
    this._nodeFactory = paramJsonNodeFactory;
  }
  
  private DeserializationConfig(DeserializationConfig paramDeserializationConfig, LinkedNode<DeserializationProblemHandler> paramLinkedNode)
  {
    super(paramDeserializationConfig);
    this._deserFeatures = paramDeserializationConfig._deserFeatures;
    this._problemHandlers = paramLinkedNode;
    this._nodeFactory = paramDeserializationConfig._nodeFactory;
  }
  
  private DeserializationConfig(DeserializationConfig paramDeserializationConfig, Class<?> paramClass)
  {
    super(paramDeserializationConfig, paramClass);
    this._deserFeatures = paramDeserializationConfig._deserFeatures;
    this._problemHandlers = paramDeserializationConfig._problemHandlers;
    this._nodeFactory = paramDeserializationConfig._nodeFactory;
  }
  
  private DeserializationConfig(DeserializationConfig paramDeserializationConfig, String paramString)
  {
    super(paramDeserializationConfig, paramString);
    this._deserFeatures = paramDeserializationConfig._deserFeatures;
    this._problemHandlers = paramDeserializationConfig._problemHandlers;
    this._nodeFactory = paramDeserializationConfig._nodeFactory;
  }
  
  protected DeserializationConfig(DeserializationConfig paramDeserializationConfig, Map<ClassKey, Class<?>> paramMap)
  {
    super(paramDeserializationConfig, paramMap);
    this._deserFeatures = paramDeserializationConfig._deserFeatures;
    this._problemHandlers = paramDeserializationConfig._problemHandlers;
    this._nodeFactory = paramDeserializationConfig._nodeFactory;
  }
  
  public DeserializationConfig(BaseSettings paramBaseSettings, SubtypeResolver paramSubtypeResolver, Map<ClassKey, Class<?>> paramMap)
  {
    super(paramBaseSettings, paramSubtypeResolver, paramMap);
    this._deserFeatures = collectFeatureDefaults(DeserializationFeature.class);
    this._nodeFactory = JsonNodeFactory.instance;
    this._problemHandlers = null;
  }
  
  private final DeserializationConfig _withBase(BaseSettings paramBaseSettings)
  {
    if (this._base == paramBaseSettings) {
      return this;
    }
    return new DeserializationConfig(this, paramBaseSettings);
  }
  
  public TypeDeserializer findTypeDeserializer(JavaType paramJavaType)
    throws JsonMappingException
  {
    Object localObject2 = introspectClassAnnotations(paramJavaType.getRawClass()).getClassInfo();
    Object localObject1 = getAnnotationIntrospector().findTypeResolver(this, (AnnotatedClass)localObject2, paramJavaType);
    Collection localCollection = null;
    if (localObject1 == null)
    {
      localObject2 = getDefaultTyper(paramJavaType);
      localObject1 = localObject2;
      if (localObject2 == null) {
        return null;
      }
    }
    else
    {
      localCollection = getSubtypeResolver().collectAndResolveSubtypes((AnnotatedClass)localObject2, this, getAnnotationIntrospector());
    }
    return ((TypeResolverBuilder)localObject1).buildTypeDeserializer(this, paramJavaType, localCollection);
  }
  
  public AnnotationIntrospector getAnnotationIntrospector()
  {
    if (isEnabled(MapperFeature.USE_ANNOTATIONS)) {
      return super.getAnnotationIntrospector();
    }
    return NopAnnotationIntrospector.instance;
  }
  
  protected BaseSettings getBaseSettings()
  {
    return this._base;
  }
  
  public VisibilityChecker<?> getDefaultVisibilityChecker()
  {
    Object localObject2 = super.getDefaultVisibilityChecker();
    Object localObject1 = localObject2;
    if (!isEnabled(MapperFeature.AUTO_DETECT_SETTERS)) {
      localObject1 = ((VisibilityChecker)localObject2).withSetterVisibility(JsonAutoDetect.Visibility.NONE);
    }
    localObject2 = localObject1;
    if (!isEnabled(MapperFeature.AUTO_DETECT_CREATORS)) {
      localObject2 = ((VisibilityChecker)localObject1).withCreatorVisibility(JsonAutoDetect.Visibility.NONE);
    }
    localObject1 = localObject2;
    if (!isEnabled(MapperFeature.AUTO_DETECT_FIELDS)) {
      localObject1 = ((VisibilityChecker)localObject2).withFieldVisibility(JsonAutoDetect.Visibility.NONE);
    }
    return (VisibilityChecker<?>)localObject1;
  }
  
  public final int getDeserializationFeatures()
  {
    return this._deserFeatures;
  }
  
  public final JsonNodeFactory getNodeFactory()
  {
    return this._nodeFactory;
  }
  
  public LinkedNode<DeserializationProblemHandler> getProblemHandlers()
  {
    return this._problemHandlers;
  }
  
  public final boolean hasDeserializationFeatures(int paramInt)
  {
    return (this._deserFeatures & paramInt) == paramInt;
  }
  
  public <T extends BeanDescription> T introspect(JavaType paramJavaType)
  {
    return getClassIntrospector().forDeserialization(this, paramJavaType, this);
  }
  
  public BeanDescription introspectClassAnnotations(JavaType paramJavaType)
  {
    return getClassIntrospector().forClassAnnotations(this, paramJavaType, this);
  }
  
  public BeanDescription introspectDirectClassAnnotations(JavaType paramJavaType)
  {
    return getClassIntrospector().forDirectClassAnnotations(this, paramJavaType, this);
  }
  
  public <T extends BeanDescription> T introspectForBuilder(JavaType paramJavaType)
  {
    return getClassIntrospector().forDeserializationWithBuilder(this, paramJavaType, this);
  }
  
  public <T extends BeanDescription> T introspectForCreation(JavaType paramJavaType)
  {
    return getClassIntrospector().forCreation(this, paramJavaType, this);
  }
  
  public final boolean isEnabled(DeserializationFeature paramDeserializationFeature)
  {
    return (this._deserFeatures & paramDeserializationFeature.getMask()) != 0;
  }
  
  public boolean useRootWrapping()
  {
    if (this._rootName != null) {
      return this._rootName.length() > 0;
    }
    return isEnabled(DeserializationFeature.UNWRAP_ROOT_VALUE);
  }
  
  public DeserializationConfig with(Base64Variant paramBase64Variant)
  {
    return _withBase(this._base.with(paramBase64Variant));
  }
  
  public DeserializationConfig with(AnnotationIntrospector paramAnnotationIntrospector)
  {
    return _withBase(this._base.withAnnotationIntrospector(paramAnnotationIntrospector));
  }
  
  public DeserializationConfig with(DeserializationFeature paramDeserializationFeature)
  {
    int i = this._deserFeatures | paramDeserializationFeature.getMask();
    if (i == this._deserFeatures) {
      return this;
    }
    return new DeserializationConfig(this, this._mapperFeatures, i);
  }
  
  public DeserializationConfig with(DeserializationFeature paramDeserializationFeature, DeserializationFeature... paramVarArgs)
  {
    int j = this._deserFeatures | paramDeserializationFeature.getMask();
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      j |= paramVarArgs[i].getMask();
      i += 1;
    }
    if (j == this._deserFeatures) {
      return this;
    }
    return new DeserializationConfig(this, this._mapperFeatures, j);
  }
  
  public DeserializationConfig with(MapperFeature paramMapperFeature, boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int i = this._mapperFeatures | paramMapperFeature.getMask(); i == this._mapperFeatures; i = this._mapperFeatures & (paramMapperFeature.getMask() ^ 0xFFFFFFFF)) {
      return this;
    }
    return new DeserializationConfig(this, i, this._deserFeatures);
  }
  
  public DeserializationConfig with(PropertyNamingStrategy paramPropertyNamingStrategy)
  {
    return _withBase(this._base.withPropertyNamingStrategy(paramPropertyNamingStrategy));
  }
  
  public DeserializationConfig with(ContextAttributes paramContextAttributes)
  {
    if (paramContextAttributes == this._attributes) {
      return this;
    }
    return new DeserializationConfig(this, paramContextAttributes);
  }
  
  public DeserializationConfig with(HandlerInstantiator paramHandlerInstantiator)
  {
    return _withBase(this._base.withHandlerInstantiator(paramHandlerInstantiator));
  }
  
  public DeserializationConfig with(ClassIntrospector paramClassIntrospector)
  {
    return _withBase(this._base.withClassIntrospector(paramClassIntrospector));
  }
  
  public DeserializationConfig with(VisibilityChecker<?> paramVisibilityChecker)
  {
    return _withBase(this._base.withVisibilityChecker(paramVisibilityChecker));
  }
  
  public DeserializationConfig with(SubtypeResolver paramSubtypeResolver)
  {
    if (this._subtypeResolver == paramSubtypeResolver) {
      return this;
    }
    return new DeserializationConfig(this, paramSubtypeResolver);
  }
  
  public DeserializationConfig with(TypeResolverBuilder<?> paramTypeResolverBuilder)
  {
    return _withBase(this._base.withTypeResolverBuilder(paramTypeResolverBuilder));
  }
  
  public DeserializationConfig with(JsonNodeFactory paramJsonNodeFactory)
  {
    if (this._nodeFactory == paramJsonNodeFactory) {
      return this;
    }
    return new DeserializationConfig(this, paramJsonNodeFactory);
  }
  
  public DeserializationConfig with(TypeFactory paramTypeFactory)
  {
    return _withBase(this._base.withTypeFactory(paramTypeFactory));
  }
  
  public DeserializationConfig with(DateFormat paramDateFormat)
  {
    return _withBase(this._base.withDateFormat(paramDateFormat));
  }
  
  public DeserializationConfig with(Locale paramLocale)
  {
    return _withBase(this._base.with(paramLocale));
  }
  
  public DeserializationConfig with(TimeZone paramTimeZone)
  {
    return _withBase(this._base.with(paramTimeZone));
  }
  
  public DeserializationConfig with(MapperFeature... paramVarArgs)
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
    return new DeserializationConfig(this, j, this._deserFeatures);
  }
  
  public DeserializationConfig withAppendedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector)
  {
    return _withBase(this._base.withAppendedAnnotationIntrospector(paramAnnotationIntrospector));
  }
  
  public DeserializationConfig withFeatures(DeserializationFeature... paramVarArgs)
  {
    int j = this._deserFeatures;
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      j |= paramVarArgs[i].getMask();
      i += 1;
    }
    if (j == this._deserFeatures) {
      return this;
    }
    return new DeserializationConfig(this, this._mapperFeatures, j);
  }
  
  public DeserializationConfig withHandler(DeserializationProblemHandler paramDeserializationProblemHandler)
  {
    if (LinkedNode.contains(this._problemHandlers, paramDeserializationProblemHandler)) {
      return this;
    }
    return new DeserializationConfig(this, new LinkedNode(paramDeserializationProblemHandler, this._problemHandlers));
  }
  
  public DeserializationConfig withInsertedAnnotationIntrospector(AnnotationIntrospector paramAnnotationIntrospector)
  {
    return _withBase(this._base.withInsertedAnnotationIntrospector(paramAnnotationIntrospector));
  }
  
  public DeserializationConfig withNoProblemHandlers()
  {
    if (this._problemHandlers == null) {
      return this;
    }
    return new DeserializationConfig(this, (LinkedNode)null);
  }
  
  public DeserializationConfig withRootName(String paramString)
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
    return new DeserializationConfig(this, paramString);
  }
  
  public DeserializationConfig withView(Class<?> paramClass)
  {
    if (this._view == paramClass) {
      return this;
    }
    return new DeserializationConfig(this, paramClass);
  }
  
  public DeserializationConfig withVisibility(PropertyAccessor paramPropertyAccessor, JsonAutoDetect.Visibility paramVisibility)
  {
    return _withBase(this._base.withVisibility(paramPropertyAccessor, paramVisibility));
  }
  
  public DeserializationConfig without(DeserializationFeature paramDeserializationFeature)
  {
    int i = this._deserFeatures & (paramDeserializationFeature.getMask() ^ 0xFFFFFFFF);
    if (i == this._deserFeatures) {
      return this;
    }
    return new DeserializationConfig(this, this._mapperFeatures, i);
  }
  
  public DeserializationConfig without(DeserializationFeature paramDeserializationFeature, DeserializationFeature... paramVarArgs)
  {
    int j = this._deserFeatures & (paramDeserializationFeature.getMask() ^ 0xFFFFFFFF);
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      j &= (paramVarArgs[i].getMask() ^ 0xFFFFFFFF);
      i += 1;
    }
    if (j == this._deserFeatures) {
      return this;
    }
    return new DeserializationConfig(this, this._mapperFeatures, j);
  }
  
  public DeserializationConfig without(MapperFeature... paramVarArgs)
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
    return new DeserializationConfig(this, j, this._deserFeatures);
  }
  
  public DeserializationConfig withoutFeatures(DeserializationFeature... paramVarArgs)
  {
    int j = this._deserFeatures;
    int k = paramVarArgs.length;
    int i = 0;
    while (i < k)
    {
      j &= (paramVarArgs[i].getMask() ^ 0xFFFFFFFF);
      i += 1;
    }
    if (j == this._deserFeatures) {
      return this;
    }
    return new DeserializationConfig(this, this._mapperFeatures, j);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/DeserializationConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */