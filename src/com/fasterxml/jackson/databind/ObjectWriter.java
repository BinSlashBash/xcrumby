package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Instantiatable;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

public class ObjectWriter
  implements Versioned, Serializable
{
  protected static final PrettyPrinter NULL_PRETTY_PRINTER = new MinimalPrettyPrinter();
  private static final long serialVersionUID = -7040667122552707164L;
  protected final boolean _cfgBigDecimalAsPlain;
  protected final CharacterEscapes _characterEscapes;
  protected final SerializationConfig _config;
  protected final JsonFactory _generatorFactory;
  protected final PrettyPrinter _prettyPrinter;
  protected final JsonSerializer<Object> _rootSerializer;
  protected final JavaType _rootType;
  protected final FormatSchema _schema;
  protected final SerializerFactory _serializerFactory;
  protected final DefaultSerializerProvider _serializerProvider;
  
  protected ObjectWriter(ObjectMapper paramObjectMapper, SerializationConfig paramSerializationConfig)
  {
    this._config = paramSerializationConfig;
    this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
    this._serializerProvider = paramObjectMapper._serializerProvider;
    this._serializerFactory = paramObjectMapper._serializerFactory;
    this._generatorFactory = paramObjectMapper._jsonFactory;
    this._rootType = null;
    this._rootSerializer = null;
    this._prettyPrinter = null;
    this._schema = null;
    this._characterEscapes = null;
  }
  
  protected ObjectWriter(ObjectMapper paramObjectMapper, SerializationConfig paramSerializationConfig, FormatSchema paramFormatSchema)
  {
    this._config = paramSerializationConfig;
    this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
    this._serializerProvider = paramObjectMapper._serializerProvider;
    this._serializerFactory = paramObjectMapper._serializerFactory;
    this._generatorFactory = paramObjectMapper._jsonFactory;
    this._rootType = null;
    this._rootSerializer = null;
    this._prettyPrinter = null;
    this._schema = paramFormatSchema;
    this._characterEscapes = null;
  }
  
  protected ObjectWriter(ObjectMapper paramObjectMapper, SerializationConfig paramSerializationConfig, JavaType paramJavaType, PrettyPrinter paramPrettyPrinter)
  {
    this._config = paramSerializationConfig;
    this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
    this._serializerProvider = paramObjectMapper._serializerProvider;
    this._serializerFactory = paramObjectMapper._serializerFactory;
    this._generatorFactory = paramObjectMapper._jsonFactory;
    this._prettyPrinter = paramPrettyPrinter;
    this._schema = null;
    this._characterEscapes = null;
    if ((paramJavaType == null) || (paramJavaType.hasRawClass(Object.class)))
    {
      this._rootType = null;
      this._rootSerializer = null;
      return;
    }
    this._rootType = paramJavaType.withStaticTyping();
    this._rootSerializer = _prefetchRootSerializer(paramSerializationConfig, this._rootType);
  }
  
  protected ObjectWriter(ObjectWriter paramObjectWriter, JsonFactory paramJsonFactory)
  {
    this._config = paramObjectWriter._config.with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, paramJsonFactory.requiresPropertyOrdering());
    this._cfgBigDecimalAsPlain = paramObjectWriter._cfgBigDecimalAsPlain;
    this._serializerProvider = paramObjectWriter._serializerProvider;
    this._serializerFactory = paramObjectWriter._serializerFactory;
    this._generatorFactory = paramObjectWriter._generatorFactory;
    this._schema = paramObjectWriter._schema;
    this._characterEscapes = paramObjectWriter._characterEscapes;
    this._rootType = paramObjectWriter._rootType;
    this._rootSerializer = paramObjectWriter._rootSerializer;
    this._prettyPrinter = paramObjectWriter._prettyPrinter;
  }
  
  protected ObjectWriter(ObjectWriter paramObjectWriter, SerializationConfig paramSerializationConfig)
  {
    this._config = paramSerializationConfig;
    this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
    this._serializerProvider = paramObjectWriter._serializerProvider;
    this._serializerFactory = paramObjectWriter._serializerFactory;
    this._generatorFactory = paramObjectWriter._generatorFactory;
    this._schema = paramObjectWriter._schema;
    this._characterEscapes = paramObjectWriter._characterEscapes;
    this._rootType = paramObjectWriter._rootType;
    this._rootSerializer = paramObjectWriter._rootSerializer;
    this._prettyPrinter = paramObjectWriter._prettyPrinter;
  }
  
  protected ObjectWriter(ObjectWriter paramObjectWriter, SerializationConfig paramSerializationConfig, JavaType paramJavaType, JsonSerializer<Object> paramJsonSerializer, PrettyPrinter paramPrettyPrinter, FormatSchema paramFormatSchema, CharacterEscapes paramCharacterEscapes)
  {
    this._config = paramSerializationConfig;
    this._cfgBigDecimalAsPlain = this._config.isEnabled(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
    this._serializerProvider = paramObjectWriter._serializerProvider;
    this._serializerFactory = paramObjectWriter._serializerFactory;
    this._generatorFactory = paramObjectWriter._generatorFactory;
    this._rootType = paramJavaType;
    this._rootSerializer = paramJsonSerializer;
    this._prettyPrinter = paramPrettyPrinter;
    this._schema = paramFormatSchema;
    this._characterEscapes = paramCharacterEscapes;
  }
  
  private void _configureJsonGenerator(JsonGenerator paramJsonGenerator)
  {
    PrettyPrinter localPrettyPrinter2;
    if (this._prettyPrinter != null)
    {
      localPrettyPrinter2 = this._prettyPrinter;
      if (localPrettyPrinter2 == NULL_PRETTY_PRINTER) {
        paramJsonGenerator.setPrettyPrinter(null);
      }
    }
    for (;;)
    {
      if (this._characterEscapes != null) {
        paramJsonGenerator.setCharacterEscapes(this._characterEscapes);
      }
      if (this._schema != null) {
        paramJsonGenerator.setSchema(this._schema);
      }
      if (this._cfgBigDecimalAsPlain) {
        paramJsonGenerator.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
      }
      return;
      PrettyPrinter localPrettyPrinter1 = localPrettyPrinter2;
      if ((localPrettyPrinter2 instanceof Instantiatable)) {
        localPrettyPrinter1 = (PrettyPrinter)((Instantiatable)localPrettyPrinter2).createInstance();
      }
      paramJsonGenerator.setPrettyPrinter(localPrettyPrinter1);
      continue;
      if (this._config.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
        paramJsonGenerator.useDefaultPrettyPrinter();
      }
    }
  }
  
  /* Error */
  private final void _writeCloseable(JsonGenerator paramJsonGenerator, Object paramObject, SerializationConfig paramSerializationConfig)
    throws IOException, JsonGenerationException, JsonMappingException
  {
    // Byte code:
    //   0: aload_2
    //   1: checkcast 172	java/io/Closeable
    //   4: astore 6
    //   6: aload 6
    //   8: astore 4
    //   10: aload_1
    //   11: astore 5
    //   13: aload_0
    //   14: getfield 77	com/fasterxml/jackson/databind/ObjectWriter:_rootType	Lcom/fasterxml/jackson/databind/JavaType;
    //   17: ifnonnull +75 -> 92
    //   20: aload 6
    //   22: astore 4
    //   24: aload_1
    //   25: astore 5
    //   27: aload_0
    //   28: aload_3
    //   29: invokevirtual 175	com/fasterxml/jackson/databind/ObjectWriter:_serializerProvider	(Lcom/fasterxml/jackson/databind/SerializationConfig;)Lcom/fasterxml/jackson/databind/ser/DefaultSerializerProvider;
    //   32: aload_1
    //   33: aload_2
    //   34: invokevirtual 181	com/fasterxml/jackson/databind/ser/DefaultSerializerProvider:serializeValue	(Lcom/fasterxml/jackson/core/JsonGenerator;Ljava/lang/Object;)V
    //   37: aconst_null
    //   38: astore_2
    //   39: aload 6
    //   41: astore 4
    //   43: aload_2
    //   44: astore 5
    //   46: aload_1
    //   47: invokevirtual 184	com/fasterxml/jackson/core/JsonGenerator:close	()V
    //   50: aconst_null
    //   51: astore 4
    //   53: aload_2
    //   54: astore 5
    //   56: aload 6
    //   58: invokeinterface 185 1 0
    //   63: iconst_0
    //   64: ifeq +15 -> 79
    //   67: getstatic 188	com/fasterxml/jackson/core/JsonGenerator$Feature:AUTO_CLOSE_JSON_CONTENT	Lcom/fasterxml/jackson/core/JsonGenerator$Feature;
    //   70: astore_1
    //   71: new 190	java/lang/NullPointerException
    //   74: dup
    //   75: invokespecial 191	java/lang/NullPointerException:<init>	()V
    //   78: athrow
    //   79: iconst_0
    //   80: ifeq +11 -> 91
    //   83: new 190	java/lang/NullPointerException
    //   86: dup
    //   87: invokespecial 191	java/lang/NullPointerException:<init>	()V
    //   90: athrow
    //   91: return
    //   92: aload 6
    //   94: astore 4
    //   96: aload_1
    //   97: astore 5
    //   99: aload_0
    //   100: aload_3
    //   101: invokevirtual 175	com/fasterxml/jackson/databind/ObjectWriter:_serializerProvider	(Lcom/fasterxml/jackson/databind/SerializationConfig;)Lcom/fasterxml/jackson/databind/ser/DefaultSerializerProvider;
    //   104: aload_1
    //   105: aload_2
    //   106: aload_0
    //   107: getfield 77	com/fasterxml/jackson/databind/ObjectWriter:_rootType	Lcom/fasterxml/jackson/databind/JavaType;
    //   110: aload_0
    //   111: getfield 79	com/fasterxml/jackson/databind/ObjectWriter:_rootSerializer	Lcom/fasterxml/jackson/databind/JsonSerializer;
    //   114: invokevirtual 194	com/fasterxml/jackson/databind/ser/DefaultSerializerProvider:serializeValue	(Lcom/fasterxml/jackson/core/JsonGenerator;Ljava/lang/Object;Lcom/fasterxml/jackson/databind/JavaType;Lcom/fasterxml/jackson/databind/JsonSerializer;)V
    //   117: goto -80 -> 37
    //   120: astore_1
    //   121: aload 5
    //   123: ifnull +17 -> 140
    //   126: aload 5
    //   128: getstatic 188	com/fasterxml/jackson/core/JsonGenerator$Feature:AUTO_CLOSE_JSON_CONTENT	Lcom/fasterxml/jackson/core/JsonGenerator$Feature;
    //   131: invokevirtual 197	com/fasterxml/jackson/core/JsonGenerator:disable	(Lcom/fasterxml/jackson/core/JsonGenerator$Feature;)Lcom/fasterxml/jackson/core/JsonGenerator;
    //   134: pop
    //   135: aload 5
    //   137: invokevirtual 184	com/fasterxml/jackson/core/JsonGenerator:close	()V
    //   140: aload 4
    //   142: ifnull +10 -> 152
    //   145: aload 4
    //   147: invokeinterface 185 1 0
    //   152: aload_1
    //   153: athrow
    //   154: astore_1
    //   155: return
    //   156: astore_2
    //   157: goto -17 -> 140
    //   160: astore_2
    //   161: goto -9 -> 152
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	164	0	this	ObjectWriter
    //   0	164	1	paramJsonGenerator	JsonGenerator
    //   0	164	2	paramObject	Object
    //   0	164	3	paramSerializationConfig	SerializationConfig
    //   8	138	4	localCloseable1	Closeable
    //   11	125	5	localObject	Object
    //   4	89	6	localCloseable2	Closeable
    // Exception table:
    //   from	to	target	type
    //   13	20	120	finally
    //   27	37	120	finally
    //   46	50	120	finally
    //   56	63	120	finally
    //   99	117	120	finally
    //   83	91	154	java/io/IOException
    //   135	140	156	java/io/IOException
    //   145	152	160	java/io/IOException
  }
  
  /* Error */
  private final void _writeCloseableValue(JsonGenerator paramJsonGenerator, Object paramObject, SerializationConfig paramSerializationConfig)
    throws IOException, JsonGenerationException, JsonMappingException
  {
    // Byte code:
    //   0: aload_2
    //   1: checkcast 172	java/io/Closeable
    //   4: astore 5
    //   6: aload 5
    //   8: astore 4
    //   10: aload_0
    //   11: getfield 77	com/fasterxml/jackson/databind/ObjectWriter:_rootType	Lcom/fasterxml/jackson/databind/JavaType;
    //   14: ifnonnull +65 -> 79
    //   17: aload 5
    //   19: astore 4
    //   21: aload_0
    //   22: aload_3
    //   23: invokevirtual 175	com/fasterxml/jackson/databind/ObjectWriter:_serializerProvider	(Lcom/fasterxml/jackson/databind/SerializationConfig;)Lcom/fasterxml/jackson/databind/ser/DefaultSerializerProvider;
    //   26: aload_1
    //   27: aload_2
    //   28: invokevirtual 181	com/fasterxml/jackson/databind/ser/DefaultSerializerProvider:serializeValue	(Lcom/fasterxml/jackson/core/JsonGenerator;Ljava/lang/Object;)V
    //   31: aload 5
    //   33: astore 4
    //   35: aload_0
    //   36: getfield 48	com/fasterxml/jackson/databind/ObjectWriter:_config	Lcom/fasterxml/jackson/databind/SerializationConfig;
    //   39: getstatic 202	com/fasterxml/jackson/databind/SerializationFeature:FLUSH_AFTER_WRITE_VALUE	Lcom/fasterxml/jackson/databind/SerializationFeature;
    //   42: invokevirtual 60	com/fasterxml/jackson/databind/SerializationConfig:isEnabled	(Lcom/fasterxml/jackson/databind/SerializationFeature;)Z
    //   45: ifeq +11 -> 56
    //   48: aload 5
    //   50: astore 4
    //   52: aload_1
    //   53: invokevirtual 205	com/fasterxml/jackson/core/JsonGenerator:flush	()V
    //   56: aconst_null
    //   57: astore 4
    //   59: aload 5
    //   61: invokeinterface 185 1 0
    //   66: iconst_0
    //   67: ifeq +11 -> 78
    //   70: new 190	java/lang/NullPointerException
    //   73: dup
    //   74: invokespecial 191	java/lang/NullPointerException:<init>	()V
    //   77: athrow
    //   78: return
    //   79: aload 5
    //   81: astore 4
    //   83: aload_0
    //   84: aload_3
    //   85: invokevirtual 175	com/fasterxml/jackson/databind/ObjectWriter:_serializerProvider	(Lcom/fasterxml/jackson/databind/SerializationConfig;)Lcom/fasterxml/jackson/databind/ser/DefaultSerializerProvider;
    //   88: aload_1
    //   89: aload_2
    //   90: aload_0
    //   91: getfield 77	com/fasterxml/jackson/databind/ObjectWriter:_rootType	Lcom/fasterxml/jackson/databind/JavaType;
    //   94: aload_0
    //   95: getfield 79	com/fasterxml/jackson/databind/ObjectWriter:_rootSerializer	Lcom/fasterxml/jackson/databind/JsonSerializer;
    //   98: invokevirtual 194	com/fasterxml/jackson/databind/ser/DefaultSerializerProvider:serializeValue	(Lcom/fasterxml/jackson/core/JsonGenerator;Ljava/lang/Object;Lcom/fasterxml/jackson/databind/JavaType;Lcom/fasterxml/jackson/databind/JsonSerializer;)V
    //   101: goto -70 -> 31
    //   104: astore_1
    //   105: aload 4
    //   107: ifnull +10 -> 117
    //   110: aload 4
    //   112: invokeinterface 185 1 0
    //   117: aload_1
    //   118: athrow
    //   119: astore_1
    //   120: return
    //   121: astore_2
    //   122: goto -5 -> 117
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	125	0	this	ObjectWriter
    //   0	125	1	paramJsonGenerator	JsonGenerator
    //   0	125	2	paramObject	Object
    //   0	125	3	paramSerializationConfig	SerializationConfig
    //   8	103	4	localCloseable1	Closeable
    //   4	76	5	localCloseable2	Closeable
    // Exception table:
    //   from	to	target	type
    //   10	17	104	finally
    //   21	31	104	finally
    //   35	48	104	finally
    //   52	56	104	finally
    //   59	66	104	finally
    //   83	101	104	finally
    //   70	78	119	java/io/IOException
    //   110	117	121	java/io/IOException
  }
  
  protected final void _configAndWriteValue(JsonGenerator paramJsonGenerator, Object paramObject)
    throws IOException
  {
    _configureJsonGenerator(paramJsonGenerator);
    if ((this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE)) && ((paramObject instanceof Closeable))) {
      _writeCloseable(paramJsonGenerator, paramObject, this._config);
    }
    for (;;)
    {
      return;
      int j = 0;
      int i = j;
      try
      {
        if (this._rootType == null)
        {
          i = j;
          _serializerProvider(this._config).serializeValue(paramJsonGenerator, paramObject);
        }
        for (;;)
        {
          i = 1;
          paramJsonGenerator.close();
          if (1 != 0) {
            break;
          }
          paramJsonGenerator.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
          try
          {
            paramJsonGenerator.close();
            return;
          }
          catch (IOException paramJsonGenerator)
          {
            return;
          }
          i = j;
          _serializerProvider(this._config).serializeValue(paramJsonGenerator, paramObject, this._rootType, this._rootSerializer);
        }
        try
        {
          paramJsonGenerator.close();
          throw ((Throwable)paramObject);
        }
        catch (IOException paramJsonGenerator)
        {
          for (;;) {}
        }
      }
      finally
      {
        if (i == 0) {
          paramJsonGenerator.disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
        }
      }
    }
  }
  
  protected JsonSerializer<Object> _prefetchRootSerializer(SerializationConfig paramSerializationConfig, JavaType paramJavaType)
  {
    if ((paramJavaType == null) || (!this._config.isEnabled(SerializationFeature.EAGER_SERIALIZER_FETCH))) {
      return null;
    }
    try
    {
      paramSerializationConfig = _serializerProvider(paramSerializationConfig).findTypedValueSerializer(paramJavaType, true, null);
      return paramSerializationConfig;
    }
    catch (JsonProcessingException paramSerializationConfig) {}
    return null;
  }
  
  protected DefaultSerializerProvider _serializerProvider(SerializationConfig paramSerializationConfig)
  {
    return this._serializerProvider.createInstance(paramSerializationConfig, this._serializerFactory);
  }
  
  protected void _verifySchemaType(FormatSchema paramFormatSchema)
  {
    if ((paramFormatSchema != null) && (!this._generatorFactory.canUseSchema(paramFormatSchema))) {
      throw new IllegalArgumentException("Can not use FormatSchema of type " + paramFormatSchema.getClass().getName() + " for format " + this._generatorFactory.getFormatName());
    }
  }
  
  public void acceptJsonFormatVisitor(JavaType paramJavaType, JsonFormatVisitorWrapper paramJsonFormatVisitorWrapper)
    throws JsonMappingException
  {
    if (paramJavaType == null) {
      throw new IllegalArgumentException("type must be provided");
    }
    _serializerProvider(this._config).acceptJsonFormatVisitor(paramJavaType, paramJsonFormatVisitorWrapper);
  }
  
  public boolean canSerialize(Class<?> paramClass)
  {
    return _serializerProvider(this._config).hasSerializerFor(paramClass, null);
  }
  
  public boolean canSerialize(Class<?> paramClass, AtomicReference<Throwable> paramAtomicReference)
  {
    return _serializerProvider(this._config).hasSerializerFor(paramClass, paramAtomicReference);
  }
  
  public ContextAttributes getAttributes()
  {
    return this._config.getAttributes();
  }
  
  public SerializationConfig getConfig()
  {
    return this._config;
  }
  
  public JsonFactory getFactory()
  {
    return this._generatorFactory;
  }
  
  @Deprecated
  public JsonFactory getJsonFactory()
  {
    return this._generatorFactory;
  }
  
  public TypeFactory getTypeFactory()
  {
    return this._config.getTypeFactory();
  }
  
  public boolean hasPrefetchedSerializer()
  {
    return this._rootSerializer != null;
  }
  
  public boolean isEnabled(JsonParser.Feature paramFeature)
  {
    return this._generatorFactory.isEnabled(paramFeature);
  }
  
  public boolean isEnabled(MapperFeature paramMapperFeature)
  {
    return this._config.isEnabled(paramMapperFeature);
  }
  
  public boolean isEnabled(SerializationFeature paramSerializationFeature)
  {
    return this._config.isEnabled(paramSerializationFeature);
  }
  
  public Version version()
  {
    return PackageVersion.VERSION;
  }
  
  public ObjectWriter with(Base64Variant paramBase64Variant)
  {
    paramBase64Variant = this._config.with(paramBase64Variant);
    if (paramBase64Variant == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramBase64Variant);
  }
  
  public ObjectWriter with(JsonFactory paramJsonFactory)
  {
    if (paramJsonFactory == this._generatorFactory) {
      return this;
    }
    return new ObjectWriter(this, paramJsonFactory);
  }
  
  public ObjectWriter with(PrettyPrinter paramPrettyPrinter)
  {
    if (paramPrettyPrinter == this._prettyPrinter) {
      return this;
    }
    PrettyPrinter localPrettyPrinter = paramPrettyPrinter;
    if (paramPrettyPrinter == null) {
      localPrettyPrinter = NULL_PRETTY_PRINTER;
    }
    return new ObjectWriter(this, this._config, this._rootType, this._rootSerializer, localPrettyPrinter, this._schema, this._characterEscapes);
  }
  
  public ObjectWriter with(CharacterEscapes paramCharacterEscapes)
  {
    if (this._characterEscapes == paramCharacterEscapes) {
      return this;
    }
    return new ObjectWriter(this, this._config, this._rootType, this._rootSerializer, this._prettyPrinter, this._schema, paramCharacterEscapes);
  }
  
  public ObjectWriter with(SerializationFeature paramSerializationFeature)
  {
    paramSerializationFeature = this._config.with(paramSerializationFeature);
    if (paramSerializationFeature == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramSerializationFeature);
  }
  
  public ObjectWriter with(SerializationFeature paramSerializationFeature, SerializationFeature... paramVarArgs)
  {
    paramSerializationFeature = this._config.with(paramSerializationFeature, paramVarArgs);
    if (paramSerializationFeature == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramSerializationFeature);
  }
  
  public ObjectWriter with(ContextAttributes paramContextAttributes)
  {
    paramContextAttributes = this._config.with(paramContextAttributes);
    if (paramContextAttributes == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramContextAttributes);
  }
  
  public ObjectWriter with(FilterProvider paramFilterProvider)
  {
    if (paramFilterProvider == this._config.getFilterProvider()) {
      return this;
    }
    return new ObjectWriter(this, this._config.withFilters(paramFilterProvider));
  }
  
  public ObjectWriter with(DateFormat paramDateFormat)
  {
    paramDateFormat = this._config.with(paramDateFormat);
    if (paramDateFormat == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramDateFormat);
  }
  
  public ObjectWriter with(Locale paramLocale)
  {
    paramLocale = this._config.with(paramLocale);
    if (paramLocale == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramLocale);
  }
  
  public ObjectWriter with(TimeZone paramTimeZone)
  {
    paramTimeZone = this._config.with(paramTimeZone);
    if (paramTimeZone == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramTimeZone);
  }
  
  public ObjectWriter withAttribute(Object paramObject1, Object paramObject2)
  {
    paramObject1 = (SerializationConfig)this._config.withAttribute(paramObject1, paramObject2);
    if (paramObject1 == this._config) {
      return this;
    }
    return new ObjectWriter(this, (SerializationConfig)paramObject1);
  }
  
  public ObjectWriter withAttributes(Map<Object, Object> paramMap)
  {
    paramMap = (SerializationConfig)this._config.withAttributes(paramMap);
    if (paramMap == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramMap);
  }
  
  public ObjectWriter withDefaultPrettyPrinter()
  {
    return with(new DefaultPrettyPrinter());
  }
  
  public ObjectWriter withFeatures(SerializationFeature... paramVarArgs)
  {
    paramVarArgs = this._config.withFeatures(paramVarArgs);
    if (paramVarArgs == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramVarArgs);
  }
  
  public ObjectWriter withRootName(String paramString)
  {
    paramString = this._config.withRootName(paramString);
    if (paramString == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramString);
  }
  
  public ObjectWriter withSchema(FormatSchema paramFormatSchema)
  {
    if (this._schema == paramFormatSchema) {
      return this;
    }
    _verifySchemaType(paramFormatSchema);
    return new ObjectWriter(this, this._config, this._rootType, this._rootSerializer, this._prettyPrinter, paramFormatSchema, this._characterEscapes);
  }
  
  public ObjectWriter withType(TypeReference<?> paramTypeReference)
  {
    return withType(this._config.getTypeFactory().constructType(paramTypeReference.getType()));
  }
  
  public ObjectWriter withType(JavaType paramJavaType)
  {
    JavaType localJavaType;
    if ((paramJavaType == null) || (paramJavaType.hasRawClass(Object.class))) {
      localJavaType = null;
    }
    for (paramJavaType = null;; paramJavaType = _prefetchRootSerializer(this._config, localJavaType))
    {
      return new ObjectWriter(this, this._config, localJavaType, paramJavaType, this._prettyPrinter, this._schema, this._characterEscapes);
      localJavaType = paramJavaType.withStaticTyping();
    }
  }
  
  public ObjectWriter withType(Class<?> paramClass)
  {
    if (paramClass == Object.class) {
      return withType((JavaType)null);
    }
    return withType(this._config.constructType(paramClass));
  }
  
  public ObjectWriter withView(Class<?> paramClass)
  {
    paramClass = this._config.withView(paramClass);
    if (paramClass == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramClass);
  }
  
  public ObjectWriter without(SerializationFeature paramSerializationFeature)
  {
    paramSerializationFeature = this._config.without(paramSerializationFeature);
    if (paramSerializationFeature == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramSerializationFeature);
  }
  
  public ObjectWriter without(SerializationFeature paramSerializationFeature, SerializationFeature... paramVarArgs)
  {
    paramSerializationFeature = this._config.without(paramSerializationFeature, paramVarArgs);
    if (paramSerializationFeature == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramSerializationFeature);
  }
  
  public ObjectWriter withoutAttribute(Object paramObject)
  {
    paramObject = (SerializationConfig)this._config.withoutAttribute(paramObject);
    if (paramObject == this._config) {
      return this;
    }
    return new ObjectWriter(this, (SerializationConfig)paramObject);
  }
  
  public ObjectWriter withoutFeatures(SerializationFeature... paramVarArgs)
  {
    paramVarArgs = this._config.withoutFeatures(paramVarArgs);
    if (paramVarArgs == this._config) {
      return this;
    }
    return new ObjectWriter(this, paramVarArgs);
  }
  
  public void writeValue(JsonGenerator paramJsonGenerator, Object paramObject)
    throws IOException, JsonGenerationException, JsonMappingException
  {
    _configureJsonGenerator(paramJsonGenerator);
    if ((this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE)) && ((paramObject instanceof Closeable))) {
      _writeCloseableValue(paramJsonGenerator, paramObject, this._config);
    }
    for (;;)
    {
      return;
      if (this._rootType == null) {
        _serializerProvider(this._config).serializeValue(paramJsonGenerator, paramObject);
      }
      while (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE))
      {
        paramJsonGenerator.flush();
        return;
        _serializerProvider(this._config).serializeValue(paramJsonGenerator, paramObject, this._rootType, this._rootSerializer);
      }
    }
  }
  
  public void writeValue(File paramFile, Object paramObject)
    throws IOException, JsonGenerationException, JsonMappingException
  {
    _configAndWriteValue(this._generatorFactory.createGenerator(paramFile, JsonEncoding.UTF8), paramObject);
  }
  
  public void writeValue(OutputStream paramOutputStream, Object paramObject)
    throws IOException, JsonGenerationException, JsonMappingException
  {
    _configAndWriteValue(this._generatorFactory.createGenerator(paramOutputStream, JsonEncoding.UTF8), paramObject);
  }
  
  public void writeValue(Writer paramWriter, Object paramObject)
    throws IOException, JsonGenerationException, JsonMappingException
  {
    _configAndWriteValue(this._generatorFactory.createGenerator(paramWriter), paramObject);
  }
  
  public byte[] writeValueAsBytes(Object paramObject)
    throws JsonProcessingException
  {
    ByteArrayBuilder localByteArrayBuilder = new ByteArrayBuilder(this._generatorFactory._getBufferRecycler());
    try
    {
      _configAndWriteValue(this._generatorFactory.createGenerator(localByteArrayBuilder, JsonEncoding.UTF8), paramObject);
      paramObject = localByteArrayBuilder.toByteArray();
      localByteArrayBuilder.release();
      return (byte[])paramObject;
    }
    catch (JsonProcessingException paramObject)
    {
      throw ((Throwable)paramObject);
    }
    catch (IOException paramObject)
    {
      throw JsonMappingException.fromUnexpectedIOE((IOException)paramObject);
    }
  }
  
  public String writeValueAsString(Object paramObject)
    throws JsonProcessingException
  {
    SegmentedStringWriter localSegmentedStringWriter = new SegmentedStringWriter(this._generatorFactory._getBufferRecycler());
    try
    {
      _configAndWriteValue(this._generatorFactory.createGenerator(localSegmentedStringWriter), paramObject);
      return localSegmentedStringWriter.getAndClear();
    }
    catch (JsonProcessingException paramObject)
    {
      throw ((Throwable)paramObject);
    }
    catch (IOException paramObject)
    {
      throw JsonMappingException.fromUnexpectedIOE((IOException)paramObject);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ObjectWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */