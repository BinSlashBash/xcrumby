package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectReader
  extends ObjectCodec
  implements Versioned, Serializable
{
  private static final JavaType JSON_NODE_TYPE = SimpleType.constructUnsafe(JsonNode.class);
  private static final long serialVersionUID = -4251443320039569153L;
  protected final DeserializationConfig _config;
  protected final DefaultDeserializationContext _context;
  protected final DataFormatReaders _dataFormatReaders;
  protected final InjectableValues _injectableValues;
  protected final JsonFactory _parserFactory;
  protected final JsonDeserializer<Object> _rootDeserializer;
  protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _rootDeserializers;
  protected final RootNameLookup _rootNames;
  protected final FormatSchema _schema;
  protected final boolean _unwrapRoot;
  protected final Object _valueToUpdate;
  protected final JavaType _valueType;
  
  protected ObjectReader(ObjectMapper paramObjectMapper, DeserializationConfig paramDeserializationConfig)
  {
    this(paramObjectMapper, paramDeserializationConfig, null, null, null, null);
  }
  
  protected ObjectReader(ObjectMapper paramObjectMapper, DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, Object paramObject, FormatSchema paramFormatSchema, InjectableValues paramInjectableValues)
  {
    this._config = paramDeserializationConfig;
    this._context = paramObjectMapper._deserializationContext;
    this._rootDeserializers = paramObjectMapper._rootDeserializers;
    this._parserFactory = paramObjectMapper._jsonFactory;
    this._rootNames = paramObjectMapper._rootNames;
    this._valueType = paramJavaType;
    this._valueToUpdate = paramObject;
    if ((paramObject != null) && (paramJavaType.isArrayType())) {
      throw new IllegalArgumentException("Can not update an array value");
    }
    this._schema = paramFormatSchema;
    this._injectableValues = paramInjectableValues;
    this._unwrapRoot = paramDeserializationConfig.useRootWrapping();
    this._rootDeserializer = _prefetchRootDeserializer(paramDeserializationConfig, paramJavaType);
    this._dataFormatReaders = null;
  }
  
  protected ObjectReader(ObjectReader paramObjectReader, JsonFactory paramJsonFactory)
  {
    this._config = paramObjectReader._config.with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, paramJsonFactory.requiresPropertyOrdering());
    this._context = paramObjectReader._context;
    this._rootDeserializers = paramObjectReader._rootDeserializers;
    this._parserFactory = paramJsonFactory;
    this._rootNames = paramObjectReader._rootNames;
    this._valueType = paramObjectReader._valueType;
    this._rootDeserializer = paramObjectReader._rootDeserializer;
    this._valueToUpdate = paramObjectReader._valueToUpdate;
    this._schema = paramObjectReader._schema;
    this._injectableValues = paramObjectReader._injectableValues;
    this._unwrapRoot = paramObjectReader._unwrapRoot;
    this._dataFormatReaders = paramObjectReader._dataFormatReaders;
  }
  
  protected ObjectReader(ObjectReader paramObjectReader, DeserializationConfig paramDeserializationConfig)
  {
    this._config = paramDeserializationConfig;
    this._context = paramObjectReader._context;
    this._rootDeserializers = paramObjectReader._rootDeserializers;
    this._parserFactory = paramObjectReader._parserFactory;
    this._rootNames = paramObjectReader._rootNames;
    this._valueType = paramObjectReader._valueType;
    this._rootDeserializer = paramObjectReader._rootDeserializer;
    this._valueToUpdate = paramObjectReader._valueToUpdate;
    this._schema = paramObjectReader._schema;
    this._injectableValues = paramObjectReader._injectableValues;
    this._unwrapRoot = paramDeserializationConfig.useRootWrapping();
    this._dataFormatReaders = paramObjectReader._dataFormatReaders;
  }
  
  protected ObjectReader(ObjectReader paramObjectReader, DeserializationConfig paramDeserializationConfig, JavaType paramJavaType, JsonDeserializer<Object> paramJsonDeserializer, Object paramObject, FormatSchema paramFormatSchema, InjectableValues paramInjectableValues, DataFormatReaders paramDataFormatReaders)
  {
    this._config = paramDeserializationConfig;
    this._context = paramObjectReader._context;
    this._rootDeserializers = paramObjectReader._rootDeserializers;
    this._parserFactory = paramObjectReader._parserFactory;
    this._rootNames = paramObjectReader._rootNames;
    this._valueType = paramJavaType;
    this._rootDeserializer = paramJsonDeserializer;
    this._valueToUpdate = paramObject;
    if ((paramObject != null) && (paramJavaType.isArrayType())) {
      throw new IllegalArgumentException("Can not update an array value");
    }
    this._schema = paramFormatSchema;
    this._injectableValues = paramInjectableValues;
    this._unwrapRoot = paramDeserializationConfig.useRootWrapping();
    this._dataFormatReaders = paramDataFormatReaders;
  }
  
  protected static JsonToken _initForReading(JsonParser paramJsonParser)
    throws IOException, JsonParseException, JsonMappingException
  {
    JsonToken localJsonToken2 = paramJsonParser.getCurrentToken();
    JsonToken localJsonToken1 = localJsonToken2;
    if (localJsonToken2 == null)
    {
      localJsonToken2 = paramJsonParser.nextToken();
      localJsonToken1 = localJsonToken2;
      if (localJsonToken2 == null) {
        throw JsonMappingException.from(paramJsonParser, "No content to map due to end-of-input");
      }
    }
    return localJsonToken1;
  }
  
  protected Object _bind(JsonParser paramJsonParser, Object paramObject)
    throws IOException, JsonParseException, JsonMappingException
  {
    Object localObject = _initForReading(paramJsonParser);
    if (localObject == JsonToken.VALUE_NULL) {
      if (paramObject == null) {
        paramObject = _findRootDeserializer(createDeserializationContext(paramJsonParser, this._config), this._valueType).getNullValue();
      }
    }
    for (;;)
    {
      paramJsonParser.clearCurrentToken();
      return paramObject;
      continue;
      if ((localObject == JsonToken.END_ARRAY) || (localObject != JsonToken.END_OBJECT))
      {
        localObject = createDeserializationContext(paramJsonParser, this._config);
        JsonDeserializer localJsonDeserializer = _findRootDeserializer((DeserializationContext)localObject, this._valueType);
        if (this._unwrapRoot) {
          paramObject = _unwrapAndDeserialize(paramJsonParser, (DeserializationContext)localObject, this._valueType, localJsonDeserializer);
        } else if (paramObject == null) {
          paramObject = localJsonDeserializer.deserialize(paramJsonParser, (DeserializationContext)localObject);
        } else {
          localJsonDeserializer.deserialize(paramJsonParser, (DeserializationContext)localObject, paramObject);
        }
      }
    }
  }
  
  /* Error */
  protected Object _bindAndClose(JsonParser paramJsonParser, Object paramObject)
    throws IOException, JsonParseException, JsonMappingException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 98	com/fasterxml/jackson/databind/ObjectReader:_schema	Lcom/fasterxml/jackson/core/FormatSchema;
    //   4: ifnull +11 -> 15
    //   7: aload_1
    //   8: aload_0
    //   9: getfield 98	com/fasterxml/jackson/databind/ObjectReader:_schema	Lcom/fasterxml/jackson/core/FormatSchema;
    //   12: invokevirtual 208	com/fasterxml/jackson/core/JsonParser:setSchema	(Lcom/fasterxml/jackson/core/FormatSchema;)V
    //   15: aload_1
    //   16: invokestatic 163	com/fasterxml/jackson/databind/ObjectReader:_initForReading	(Lcom/fasterxml/jackson/core/JsonParser;)Lcom/fasterxml/jackson/core/JsonToken;
    //   19: astore_3
    //   20: aload_3
    //   21: getstatic 169	com/fasterxml/jackson/core/JsonToken:VALUE_NULL	Lcom/fasterxml/jackson/core/JsonToken;
    //   24: if_acmpne +37 -> 61
    //   27: aload_2
    //   28: ifnonnull +30 -> 58
    //   31: aload_0
    //   32: aload_0
    //   33: aload_1
    //   34: aload_0
    //   35: getfield 61	com/fasterxml/jackson/databind/ObjectReader:_config	Lcom/fasterxml/jackson/databind/DeserializationConfig;
    //   38: invokevirtual 173	com/fasterxml/jackson/databind/ObjectReader:createDeserializationContext	(Lcom/fasterxml/jackson/core/JsonParser;Lcom/fasterxml/jackson/databind/DeserializationConfig;)Lcom/fasterxml/jackson/databind/deser/DefaultDeserializationContext;
    //   41: aload_0
    //   42: getfield 81	com/fasterxml/jackson/databind/ObjectReader:_valueType	Lcom/fasterxml/jackson/databind/JavaType;
    //   45: invokevirtual 177	com/fasterxml/jackson/databind/ObjectReader:_findRootDeserializer	(Lcom/fasterxml/jackson/databind/DeserializationContext;Lcom/fasterxml/jackson/databind/JavaType;)Lcom/fasterxml/jackson/databind/JsonDeserializer;
    //   48: invokevirtual 183	com/fasterxml/jackson/databind/JsonDeserializer:getNullValue	()Ljava/lang/Object;
    //   51: astore_2
    //   52: aload_1
    //   53: invokevirtual 211	com/fasterxml/jackson/core/JsonParser:close	()V
    //   56: aload_2
    //   57: areturn
    //   58: goto -6 -> 52
    //   61: aload_3
    //   62: getstatic 189	com/fasterxml/jackson/core/JsonToken:END_ARRAY	Lcom/fasterxml/jackson/core/JsonToken;
    //   65: if_acmpeq +98 -> 163
    //   68: aload_3
    //   69: getstatic 192	com/fasterxml/jackson/core/JsonToken:END_OBJECT	Lcom/fasterxml/jackson/core/JsonToken;
    //   72: if_acmpne +6 -> 78
    //   75: goto +88 -> 163
    //   78: aload_0
    //   79: aload_1
    //   80: aload_0
    //   81: getfield 61	com/fasterxml/jackson/databind/ObjectReader:_config	Lcom/fasterxml/jackson/databind/DeserializationConfig;
    //   84: invokevirtual 173	com/fasterxml/jackson/databind/ObjectReader:createDeserializationContext	(Lcom/fasterxml/jackson/core/JsonParser;Lcom/fasterxml/jackson/databind/DeserializationConfig;)Lcom/fasterxml/jackson/databind/deser/DefaultDeserializationContext;
    //   87: astore_3
    //   88: aload_0
    //   89: aload_3
    //   90: aload_0
    //   91: getfield 81	com/fasterxml/jackson/databind/ObjectReader:_valueType	Lcom/fasterxml/jackson/databind/JavaType;
    //   94: invokevirtual 177	com/fasterxml/jackson/databind/ObjectReader:_findRootDeserializer	(Lcom/fasterxml/jackson/databind/DeserializationContext;Lcom/fasterxml/jackson/databind/JavaType;)Lcom/fasterxml/jackson/databind/JsonDeserializer;
    //   97: astore 4
    //   99: aload_0
    //   100: getfield 107	com/fasterxml/jackson/databind/ObjectReader:_unwrapRoot	Z
    //   103: ifeq +19 -> 122
    //   106: aload_0
    //   107: aload_1
    //   108: aload_3
    //   109: aload_0
    //   110: getfield 81	com/fasterxml/jackson/databind/ObjectReader:_valueType	Lcom/fasterxml/jackson/databind/JavaType;
    //   113: aload 4
    //   115: invokevirtual 196	com/fasterxml/jackson/databind/ObjectReader:_unwrapAndDeserialize	(Lcom/fasterxml/jackson/core/JsonParser;Lcom/fasterxml/jackson/databind/DeserializationContext;Lcom/fasterxml/jackson/databind/JavaType;Lcom/fasterxml/jackson/databind/JsonDeserializer;)Ljava/lang/Object;
    //   118: astore_2
    //   119: goto -67 -> 52
    //   122: aload_2
    //   123: ifnonnull +14 -> 137
    //   126: aload 4
    //   128: aload_1
    //   129: aload_3
    //   130: invokevirtual 200	com/fasterxml/jackson/databind/JsonDeserializer:deserialize	(Lcom/fasterxml/jackson/core/JsonParser;Lcom/fasterxml/jackson/databind/DeserializationContext;)Ljava/lang/Object;
    //   133: astore_2
    //   134: goto -82 -> 52
    //   137: aload 4
    //   139: aload_1
    //   140: aload_3
    //   141: aload_2
    //   142: invokevirtual 203	com/fasterxml/jackson/databind/JsonDeserializer:deserialize	(Lcom/fasterxml/jackson/core/JsonParser;Lcom/fasterxml/jackson/databind/DeserializationContext;Ljava/lang/Object;)Ljava/lang/Object;
    //   145: pop
    //   146: goto -94 -> 52
    //   149: astore_2
    //   150: aload_1
    //   151: invokevirtual 211	com/fasterxml/jackson/core/JsonParser:close	()V
    //   154: aload_2
    //   155: athrow
    //   156: astore_1
    //   157: aload_2
    //   158: areturn
    //   159: astore_1
    //   160: goto -6 -> 154
    //   163: goto -111 -> 52
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	166	0	this	ObjectReader
    //   0	166	1	paramJsonParser	JsonParser
    //   0	166	2	paramObject	Object
    //   19	122	3	localObject	Object
    //   97	41	4	localJsonDeserializer	JsonDeserializer
    // Exception table:
    //   from	to	target	type
    //   15	27	149	finally
    //   31	52	149	finally
    //   61	75	149	finally
    //   78	119	149	finally
    //   126	134	149	finally
    //   137	146	149	finally
    //   52	56	156	java/io/IOException
    //   150	154	159	java/io/IOException
  }
  
  /* Error */
  protected JsonNode _bindAndCloseAsTree(JsonParser paramJsonParser)
    throws IOException, JsonParseException, JsonMappingException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 98	com/fasterxml/jackson/databind/ObjectReader:_schema	Lcom/fasterxml/jackson/core/FormatSchema;
    //   4: ifnull +11 -> 15
    //   7: aload_1
    //   8: aload_0
    //   9: getfield 98	com/fasterxml/jackson/databind/ObjectReader:_schema	Lcom/fasterxml/jackson/core/FormatSchema;
    //   12: invokevirtual 208	com/fasterxml/jackson/core/JsonParser:setSchema	(Lcom/fasterxml/jackson/core/FormatSchema;)V
    //   15: aload_0
    //   16: aload_1
    //   17: invokevirtual 216	com/fasterxml/jackson/databind/ObjectReader:_bindAsTree	(Lcom/fasterxml/jackson/core/JsonParser;)Lcom/fasterxml/jackson/databind/JsonNode;
    //   20: astore_2
    //   21: aload_1
    //   22: invokevirtual 211	com/fasterxml/jackson/core/JsonParser:close	()V
    //   25: aload_2
    //   26: areturn
    //   27: astore_2
    //   28: aload_1
    //   29: invokevirtual 211	com/fasterxml/jackson/core/JsonParser:close	()V
    //   32: aload_2
    //   33: athrow
    //   34: astore_1
    //   35: aload_2
    //   36: areturn
    //   37: astore_1
    //   38: goto -6 -> 32
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	41	0	this	ObjectReader
    //   0	41	1	paramJsonParser	JsonParser
    //   20	6	2	localJsonNode1	JsonNode
    //   27	9	2	localJsonNode2	JsonNode
    // Exception table:
    //   from	to	target	type
    //   15	21	27	finally
    //   21	25	34	java/io/IOException
    //   28	32	37	java/io/IOException
  }
  
  protected <T> MappingIterator<T> _bindAndReadValues(JsonParser paramJsonParser, Object paramObject)
    throws IOException, JsonProcessingException
  {
    if (this._schema != null) {
      paramJsonParser.setSchema(this._schema);
    }
    paramJsonParser.nextToken();
    paramObject = createDeserializationContext(paramJsonParser, this._config);
    return new MappingIterator(this._valueType, paramJsonParser, (DeserializationContext)paramObject, _findRootDeserializer((DeserializationContext)paramObject, this._valueType), true, this._valueToUpdate);
  }
  
  protected JsonNode _bindAsTree(JsonParser paramJsonParser)
    throws IOException, JsonParseException, JsonMappingException
  {
    Object localObject = _initForReading(paramJsonParser);
    if ((localObject == JsonToken.VALUE_NULL) || (localObject == JsonToken.END_ARRAY) || (localObject == JsonToken.END_OBJECT)) {
      localObject = NullNode.instance;
    }
    for (;;)
    {
      paramJsonParser.clearCurrentToken();
      return (JsonNode)localObject;
      localObject = createDeserializationContext(paramJsonParser, this._config);
      JsonDeserializer localJsonDeserializer = _findRootDeserializer((DeserializationContext)localObject, JSON_NODE_TYPE);
      if (this._unwrapRoot) {
        localObject = (JsonNode)_unwrapAndDeserialize(paramJsonParser, (DeserializationContext)localObject, JSON_NODE_TYPE, localJsonDeserializer);
      } else {
        localObject = (JsonNode)localJsonDeserializer.deserialize(paramJsonParser, (DeserializationContext)localObject);
      }
    }
  }
  
  protected Object _detectBindAndClose(DataFormatReaders.Match paramMatch, boolean paramBoolean)
    throws IOException
  {
    if (!paramMatch.hasMatch()) {
      _reportUnkownFormat(this._dataFormatReaders, paramMatch);
    }
    JsonParser localJsonParser = paramMatch.createParserWithMatch();
    if (paramBoolean) {
      localJsonParser.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
    }
    return paramMatch.getReader()._bindAndClose(localJsonParser, this._valueToUpdate);
  }
  
  protected Object _detectBindAndClose(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    paramArrayOfByte = this._dataFormatReaders.findFormat(paramArrayOfByte, paramInt1, paramInt2);
    if (!paramArrayOfByte.hasMatch()) {
      _reportUnkownFormat(this._dataFormatReaders, paramArrayOfByte);
    }
    JsonParser localJsonParser = paramArrayOfByte.createParserWithMatch();
    return paramArrayOfByte.getReader()._bindAndClose(localJsonParser, this._valueToUpdate);
  }
  
  protected JsonNode _detectBindAndCloseAsTree(InputStream paramInputStream)
    throws IOException
  {
    paramInputStream = this._dataFormatReaders.findFormat(paramInputStream);
    if (!paramInputStream.hasMatch()) {
      _reportUnkownFormat(this._dataFormatReaders, paramInputStream);
    }
    JsonParser localJsonParser = paramInputStream.createParserWithMatch();
    localJsonParser.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
    return paramInputStream.getReader()._bindAndCloseAsTree(localJsonParser);
  }
  
  protected <T> MappingIterator<T> _detectBindAndReadValues(DataFormatReaders.Match paramMatch, boolean paramBoolean)
    throws IOException, JsonProcessingException
  {
    if (!paramMatch.hasMatch()) {
      _reportUnkownFormat(this._dataFormatReaders, paramMatch);
    }
    JsonParser localJsonParser = paramMatch.createParserWithMatch();
    if (paramBoolean) {
      localJsonParser.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
    }
    return paramMatch.getReader()._bindAndReadValues(localJsonParser, this._valueToUpdate);
  }
  
  protected JsonDeserializer<Object> _findRootDeserializer(DeserializationContext paramDeserializationContext, JavaType paramJavaType)
    throws JsonMappingException
  {
    Object localObject;
    if (this._rootDeserializer != null) {
      localObject = this._rootDeserializer;
    }
    JsonDeserializer localJsonDeserializer;
    do
    {
      return (JsonDeserializer<Object>)localObject;
      if (paramJavaType == null) {
        throw new JsonMappingException("No value type configured for ObjectReader");
      }
      localJsonDeserializer = (JsonDeserializer)this._rootDeserializers.get(paramJavaType);
      localObject = localJsonDeserializer;
    } while (localJsonDeserializer != null);
    paramDeserializationContext = paramDeserializationContext.findRootValueDeserializer(paramJavaType);
    if (paramDeserializationContext == null) {
      throw new JsonMappingException("Can not find a deserializer for type " + paramJavaType);
    }
    this._rootDeserializers.put(paramJavaType, paramDeserializationContext);
    return paramDeserializationContext;
  }
  
  protected InputStream _inputStream(File paramFile)
    throws IOException
  {
    return new FileInputStream(paramFile);
  }
  
  protected InputStream _inputStream(URL paramURL)
    throws IOException
  {
    return paramURL.openStream();
  }
  
  protected JsonDeserializer<Object> _prefetchRootDeserializer(DeserializationConfig paramDeserializationConfig, JavaType paramJavaType)
  {
    JsonDeserializer localJsonDeserializer = null;
    paramDeserializationConfig = localJsonDeserializer;
    if (paramJavaType != null)
    {
      if (this._config.isEnabled(DeserializationFeature.EAGER_DESERIALIZER_FETCH)) {
        break label25;
      }
      paramDeserializationConfig = localJsonDeserializer;
    }
    for (;;)
    {
      return paramDeserializationConfig;
      label25:
      localJsonDeserializer = (JsonDeserializer)this._rootDeserializers.get(paramJavaType);
      paramDeserializationConfig = localJsonDeserializer;
      if (localJsonDeserializer == null)
      {
        paramDeserializationConfig = localJsonDeserializer;
        try
        {
          localJsonDeserializer = createDeserializationContext(null, this._config).findRootValueDeserializer(paramJavaType);
          paramDeserializationConfig = localJsonDeserializer;
          if (localJsonDeserializer != null)
          {
            paramDeserializationConfig = localJsonDeserializer;
            this._rootDeserializers.put(paramJavaType, localJsonDeserializer);
            return localJsonDeserializer;
          }
        }
        catch (JsonProcessingException paramJavaType) {}
      }
    }
    return paramDeserializationConfig;
  }
  
  protected void _reportUndetectableSource(Object paramObject)
    throws JsonProcessingException
  {
    throw new JsonParseException("Can not use source of type " + paramObject.getClass().getName() + " with format auto-detection: must be byte- not char-based", JsonLocation.NA);
  }
  
  protected void _reportUnkownFormat(DataFormatReaders paramDataFormatReaders, DataFormatReaders.Match paramMatch)
    throws JsonProcessingException
  {
    throw new JsonParseException("Can not detect format from input, does not look like any of detectable formats " + paramDataFormatReaders.toString(), JsonLocation.NA);
  }
  
  protected Object _unwrapAndDeserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JavaType paramJavaType, JsonDeserializer<Object> paramJsonDeserializer)
    throws IOException, JsonParseException, JsonMappingException
  {
    String str2 = this._config.getRootName();
    String str1 = str2;
    if (str2 == null) {
      str1 = this._rootNames.findRootName(paramJavaType, this._config).getSimpleName();
    }
    if (paramJsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
      throw JsonMappingException.from(paramJsonParser, "Current token not START_OBJECT (needed to unwrap root name '" + str1 + "'), but " + paramJsonParser.getCurrentToken());
    }
    if (paramJsonParser.nextToken() != JsonToken.FIELD_NAME) {
      throw JsonMappingException.from(paramJsonParser, "Current token not FIELD_NAME (to contain expected root name '" + str1 + "'), but " + paramJsonParser.getCurrentToken());
    }
    str2 = paramJsonParser.getCurrentName();
    if (!str1.equals(str2)) {
      throw JsonMappingException.from(paramJsonParser, "Root name '" + str2 + "' does not match expected ('" + str1 + "') for type " + paramJavaType);
    }
    paramJsonParser.nextToken();
    if (this._valueToUpdate == null) {}
    for (paramDeserializationContext = paramJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext); paramJsonParser.nextToken() != JsonToken.END_OBJECT; paramDeserializationContext = this._valueToUpdate)
    {
      throw JsonMappingException.from(paramJsonParser, "Current token not END_OBJECT (to match wrapper object with root name '" + str1 + "'), but " + paramJsonParser.getCurrentToken());
      paramJsonDeserializer.deserialize(paramJsonParser, paramDeserializationContext, this._valueToUpdate);
    }
    return paramDeserializationContext;
  }
  
  protected void _verifySchemaType(FormatSchema paramFormatSchema)
  {
    if ((paramFormatSchema != null) && (!this._parserFactory.canUseSchema(paramFormatSchema))) {
      throw new IllegalArgumentException("Can not use FormatSchema of type " + paramFormatSchema.getClass().getName() + " for format " + this._parserFactory.getFormatName());
    }
  }
  
  protected ObjectReader _with(DeserializationConfig paramDeserializationConfig)
  {
    if (paramDeserializationConfig == this._config) {
      return this;
    }
    if (this._dataFormatReaders != null) {
      return new ObjectReader(this, paramDeserializationConfig).withFormatDetection(this._dataFormatReaders.with(paramDeserializationConfig));
    }
    return new ObjectReader(this, paramDeserializationConfig);
  }
  
  public JsonNode createArrayNode()
  {
    return this._config.getNodeFactory().arrayNode();
  }
  
  protected DefaultDeserializationContext createDeserializationContext(JsonParser paramJsonParser, DeserializationConfig paramDeserializationConfig)
  {
    return this._context.createInstance(paramDeserializationConfig, paramJsonParser, this._injectableValues);
  }
  
  public JsonNode createObjectNode()
  {
    return this._config.getNodeFactory().objectNode();
  }
  
  public ContextAttributes getAttributes()
  {
    return this._config.getAttributes();
  }
  
  public DeserializationConfig getConfig()
  {
    return this._config;
  }
  
  public JsonFactory getFactory()
  {
    return this._parserFactory;
  }
  
  @Deprecated
  public JsonFactory getJsonFactory()
  {
    return this._parserFactory;
  }
  
  public TypeFactory getTypeFactory()
  {
    return this._config.getTypeFactory();
  }
  
  public boolean isEnabled(JsonParser.Feature paramFeature)
  {
    return this._parserFactory.isEnabled(paramFeature);
  }
  
  public boolean isEnabled(DeserializationFeature paramDeserializationFeature)
  {
    return this._config.isEnabled(paramDeserializationFeature);
  }
  
  public boolean isEnabled(MapperFeature paramMapperFeature)
  {
    return this._config.isEnabled(paramMapperFeature);
  }
  
  public <T extends TreeNode> T readTree(JsonParser paramJsonParser)
    throws IOException, JsonProcessingException
  {
    return _bindAsTree(paramJsonParser);
  }
  
  public JsonNode readTree(InputStream paramInputStream)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      return _detectBindAndCloseAsTree(paramInputStream);
    }
    return _bindAndCloseAsTree(this._parserFactory.createParser(paramInputStream));
  }
  
  public JsonNode readTree(Reader paramReader)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      _reportUndetectableSource(paramReader);
    }
    return _bindAndCloseAsTree(this._parserFactory.createParser(paramReader));
  }
  
  public JsonNode readTree(String paramString)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      _reportUndetectableSource(paramString);
    }
    return _bindAndCloseAsTree(this._parserFactory.createParser(paramString));
  }
  
  public <T> T readValue(JsonParser paramJsonParser)
    throws IOException, JsonProcessingException
  {
    return (T)_bind(paramJsonParser, this._valueToUpdate);
  }
  
  public <T> T readValue(JsonParser paramJsonParser, ResolvedType paramResolvedType)
    throws IOException, JsonProcessingException
  {
    return (T)withType((JavaType)paramResolvedType).readValue(paramJsonParser);
  }
  
  public <T> T readValue(JsonParser paramJsonParser, TypeReference<?> paramTypeReference)
    throws IOException, JsonProcessingException
  {
    return (T)withType(paramTypeReference).readValue(paramJsonParser);
  }
  
  public <T> T readValue(JsonParser paramJsonParser, JavaType paramJavaType)
    throws IOException, JsonProcessingException
  {
    return (T)withType(paramJavaType).readValue(paramJsonParser);
  }
  
  public <T> T readValue(JsonParser paramJsonParser, Class<T> paramClass)
    throws IOException, JsonProcessingException
  {
    return (T)withType(paramClass).readValue(paramJsonParser);
  }
  
  public <T> T readValue(JsonNode paramJsonNode)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      _reportUndetectableSource(paramJsonNode);
    }
    return (T)_bindAndClose(treeAsTokens(paramJsonNode), this._valueToUpdate);
  }
  
  public <T> T readValue(File paramFile)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      return (T)_detectBindAndClose(this._dataFormatReaders.findFormat(_inputStream(paramFile)), true);
    }
    return (T)_bindAndClose(this._parserFactory.createParser(paramFile), this._valueToUpdate);
  }
  
  public <T> T readValue(InputStream paramInputStream)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      return (T)_detectBindAndClose(this._dataFormatReaders.findFormat(paramInputStream), false);
    }
    return (T)_bindAndClose(this._parserFactory.createParser(paramInputStream), this._valueToUpdate);
  }
  
  public <T> T readValue(Reader paramReader)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      _reportUndetectableSource(paramReader);
    }
    return (T)_bindAndClose(this._parserFactory.createParser(paramReader), this._valueToUpdate);
  }
  
  public <T> T readValue(String paramString)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      _reportUndetectableSource(paramString);
    }
    return (T)_bindAndClose(this._parserFactory.createParser(paramString), this._valueToUpdate);
  }
  
  public <T> T readValue(URL paramURL)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      return (T)_detectBindAndClose(this._dataFormatReaders.findFormat(_inputStream(paramURL)), true);
    }
    return (T)_bindAndClose(this._parserFactory.createParser(paramURL), this._valueToUpdate);
  }
  
  public <T> T readValue(byte[] paramArrayOfByte)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      return (T)_detectBindAndClose(paramArrayOfByte, 0, paramArrayOfByte.length);
    }
    return (T)_bindAndClose(this._parserFactory.createParser(paramArrayOfByte), this._valueToUpdate);
  }
  
  public <T> T readValue(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      return (T)_detectBindAndClose(paramArrayOfByte, paramInt1, paramInt2);
    }
    return (T)_bindAndClose(this._parserFactory.createParser(paramArrayOfByte, paramInt1, paramInt2), this._valueToUpdate);
  }
  
  public <T> MappingIterator<T> readValues(JsonParser paramJsonParser)
    throws IOException, JsonProcessingException
  {
    DefaultDeserializationContext localDefaultDeserializationContext = createDeserializationContext(paramJsonParser, this._config);
    return new MappingIterator(this._valueType, paramJsonParser, localDefaultDeserializationContext, _findRootDeserializer(localDefaultDeserializationContext, this._valueType), false, this._valueToUpdate);
  }
  
  public <T> MappingIterator<T> readValues(File paramFile)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      return _detectBindAndReadValues(this._dataFormatReaders.findFormat(_inputStream(paramFile)), false);
    }
    return _bindAndReadValues(this._parserFactory.createParser(paramFile), this._valueToUpdate);
  }
  
  public <T> MappingIterator<T> readValues(InputStream paramInputStream)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      return _detectBindAndReadValues(this._dataFormatReaders.findFormat(paramInputStream), false);
    }
    return _bindAndReadValues(this._parserFactory.createParser(paramInputStream), this._valueToUpdate);
  }
  
  public <T> MappingIterator<T> readValues(Reader paramReader)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      _reportUndetectableSource(paramReader);
    }
    paramReader = this._parserFactory.createParser(paramReader);
    if (this._schema != null) {
      paramReader.setSchema(this._schema);
    }
    paramReader.nextToken();
    DefaultDeserializationContext localDefaultDeserializationContext = createDeserializationContext(paramReader, this._config);
    return new MappingIterator(this._valueType, paramReader, localDefaultDeserializationContext, _findRootDeserializer(localDefaultDeserializationContext, this._valueType), true, this._valueToUpdate);
  }
  
  public <T> MappingIterator<T> readValues(String paramString)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      _reportUndetectableSource(paramString);
    }
    paramString = this._parserFactory.createParser(paramString);
    if (this._schema != null) {
      paramString.setSchema(this._schema);
    }
    paramString.nextToken();
    DefaultDeserializationContext localDefaultDeserializationContext = createDeserializationContext(paramString, this._config);
    return new MappingIterator(this._valueType, paramString, localDefaultDeserializationContext, _findRootDeserializer(localDefaultDeserializationContext, this._valueType), true, this._valueToUpdate);
  }
  
  public <T> MappingIterator<T> readValues(URL paramURL)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      return _detectBindAndReadValues(this._dataFormatReaders.findFormat(_inputStream(paramURL)), true);
    }
    return _bindAndReadValues(this._parserFactory.createParser(paramURL), this._valueToUpdate);
  }
  
  public final <T> MappingIterator<T> readValues(byte[] paramArrayOfByte)
    throws IOException, JsonProcessingException
  {
    return readValues(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public <T> MappingIterator<T> readValues(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException, JsonProcessingException
  {
    if (this._dataFormatReaders != null) {
      return _detectBindAndReadValues(this._dataFormatReaders.findFormat(paramArrayOfByte, paramInt1, paramInt2), false);
    }
    return _bindAndReadValues(this._parserFactory.createParser(paramArrayOfByte), this._valueToUpdate);
  }
  
  public <T> Iterator<T> readValues(JsonParser paramJsonParser, ResolvedType paramResolvedType)
    throws IOException, JsonProcessingException
  {
    return readValues(paramJsonParser, (JavaType)paramResolvedType);
  }
  
  public <T> Iterator<T> readValues(JsonParser paramJsonParser, TypeReference<?> paramTypeReference)
    throws IOException, JsonProcessingException
  {
    return withType(paramTypeReference).readValues(paramJsonParser);
  }
  
  public <T> Iterator<T> readValues(JsonParser paramJsonParser, JavaType paramJavaType)
    throws IOException, JsonProcessingException
  {
    return withType(paramJavaType).readValues(paramJsonParser);
  }
  
  public <T> Iterator<T> readValues(JsonParser paramJsonParser, Class<T> paramClass)
    throws IOException, JsonProcessingException
  {
    return withType(paramClass).readValues(paramJsonParser);
  }
  
  public JsonParser treeAsTokens(TreeNode paramTreeNode)
  {
    return new TreeTraversingParser((JsonNode)paramTreeNode, this);
  }
  
  public <T> T treeToValue(TreeNode paramTreeNode, Class<T> paramClass)
    throws JsonProcessingException
  {
    try
    {
      paramTreeNode = readValue(treeAsTokens(paramTreeNode), paramClass);
      return paramTreeNode;
    }
    catch (JsonProcessingException paramTreeNode)
    {
      throw paramTreeNode;
    }
    catch (IOException paramTreeNode)
    {
      throw new IllegalArgumentException(paramTreeNode.getMessage(), paramTreeNode);
    }
  }
  
  public Version version()
  {
    return PackageVersion.VERSION;
  }
  
  public ObjectReader with(Base64Variant paramBase64Variant)
  {
    return _with(this._config.with(paramBase64Variant));
  }
  
  public ObjectReader with(FormatSchema paramFormatSchema)
  {
    if (this._schema == paramFormatSchema) {
      return this;
    }
    _verifySchemaType(paramFormatSchema);
    return new ObjectReader(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, paramFormatSchema, this._injectableValues, this._dataFormatReaders);
  }
  
  public ObjectReader with(JsonFactory paramJsonFactory)
  {
    if (paramJsonFactory == this._parserFactory) {
      return this;
    }
    ObjectReader localObjectReader = new ObjectReader(this, paramJsonFactory);
    if (paramJsonFactory.getCodec() == null) {
      paramJsonFactory.setCodec(localObjectReader);
    }
    return localObjectReader;
  }
  
  public ObjectReader with(DeserializationConfig paramDeserializationConfig)
  {
    return _with(paramDeserializationConfig);
  }
  
  public ObjectReader with(DeserializationFeature paramDeserializationFeature)
  {
    return _with(this._config.with(paramDeserializationFeature));
  }
  
  public ObjectReader with(DeserializationFeature paramDeserializationFeature, DeserializationFeature... paramVarArgs)
  {
    return _with(this._config.with(paramDeserializationFeature, paramVarArgs));
  }
  
  public ObjectReader with(InjectableValues paramInjectableValues)
  {
    if (this._injectableValues == paramInjectableValues) {
      return this;
    }
    return new ObjectReader(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, this._schema, paramInjectableValues, this._dataFormatReaders);
  }
  
  public ObjectReader with(ContextAttributes paramContextAttributes)
  {
    paramContextAttributes = this._config.with(paramContextAttributes);
    if (paramContextAttributes == this._config) {
      return this;
    }
    return new ObjectReader(this, paramContextAttributes);
  }
  
  public ObjectReader with(JsonNodeFactory paramJsonNodeFactory)
  {
    return _with(this._config.with(paramJsonNodeFactory));
  }
  
  public ObjectReader with(Locale paramLocale)
  {
    return _with(this._config.with(paramLocale));
  }
  
  public ObjectReader with(TimeZone paramTimeZone)
  {
    return _with(this._config.with(paramTimeZone));
  }
  
  public ObjectReader withAttribute(Object paramObject1, Object paramObject2)
  {
    paramObject1 = (DeserializationConfig)this._config.withAttribute(paramObject1, paramObject2);
    if (paramObject1 == this._config) {
      return this;
    }
    return new ObjectReader(this, (DeserializationConfig)paramObject1);
  }
  
  public ObjectReader withAttributes(Map<Object, Object> paramMap)
  {
    paramMap = (DeserializationConfig)this._config.withAttributes(paramMap);
    if (paramMap == this._config) {
      return this;
    }
    return new ObjectReader(this, paramMap);
  }
  
  public ObjectReader withFeatures(DeserializationFeature... paramVarArgs)
  {
    return _with(this._config.withFeatures(paramVarArgs));
  }
  
  public ObjectReader withFormatDetection(DataFormatReaders paramDataFormatReaders)
  {
    return new ObjectReader(this, this._config, this._valueType, this._rootDeserializer, this._valueToUpdate, this._schema, this._injectableValues, paramDataFormatReaders);
  }
  
  public ObjectReader withFormatDetection(ObjectReader... paramVarArgs)
  {
    return withFormatDetection(new DataFormatReaders(paramVarArgs));
  }
  
  public ObjectReader withHandler(DeserializationProblemHandler paramDeserializationProblemHandler)
  {
    return _with(this._config.withHandler(paramDeserializationProblemHandler));
  }
  
  public ObjectReader withRootName(String paramString)
  {
    return _with(this._config.withRootName(paramString));
  }
  
  public ObjectReader withType(TypeReference<?> paramTypeReference)
  {
    return withType(this._config.getTypeFactory().constructType(paramTypeReference.getType()));
  }
  
  public ObjectReader withType(JavaType paramJavaType)
  {
    if ((paramJavaType != null) && (paramJavaType.equals(this._valueType))) {
      return this;
    }
    JsonDeserializer localJsonDeserializer = _prefetchRootDeserializer(this._config, paramJavaType);
    DataFormatReaders localDataFormatReaders2 = this._dataFormatReaders;
    DataFormatReaders localDataFormatReaders1 = localDataFormatReaders2;
    if (localDataFormatReaders2 != null) {
      localDataFormatReaders1 = localDataFormatReaders2.withType(paramJavaType);
    }
    return new ObjectReader(this, this._config, paramJavaType, localJsonDeserializer, this._valueToUpdate, this._schema, this._injectableValues, localDataFormatReaders1);
  }
  
  public ObjectReader withType(Class<?> paramClass)
  {
    return withType(this._config.constructType(paramClass));
  }
  
  public ObjectReader withType(Type paramType)
  {
    return withType(this._config.getTypeFactory().constructType(paramType));
  }
  
  public ObjectReader withValueToUpdate(Object paramObject)
  {
    if (paramObject == this._valueToUpdate) {
      return this;
    }
    if (paramObject == null) {
      throw new IllegalArgumentException("cat not update null value");
    }
    if (this._valueType == null) {}
    for (JavaType localJavaType = this._config.constructType(paramObject.getClass());; localJavaType = this._valueType) {
      return new ObjectReader(this, this._config, localJavaType, this._rootDeserializer, paramObject, this._schema, this._injectableValues, this._dataFormatReaders);
    }
  }
  
  public ObjectReader withView(Class<?> paramClass)
  {
    return _with(this._config.withView(paramClass));
  }
  
  public ObjectReader without(DeserializationFeature paramDeserializationFeature)
  {
    return _with(this._config.without(paramDeserializationFeature));
  }
  
  public ObjectReader without(DeserializationFeature paramDeserializationFeature, DeserializationFeature... paramVarArgs)
  {
    return _with(this._config.without(paramDeserializationFeature, paramVarArgs));
  }
  
  public ObjectReader withoutAttribute(Object paramObject)
  {
    paramObject = (DeserializationConfig)this._config.withoutAttribute(paramObject);
    if (paramObject == this._config) {
      return this;
    }
    return new ObjectReader(this, (DeserializationConfig)paramObject);
  }
  
  public ObjectReader withoutFeatures(DeserializationFeature... paramVarArgs)
  {
    return _with(this._config.withoutFeatures(paramVarArgs));
  }
  
  public void writeTree(JsonGenerator paramJsonGenerator, TreeNode paramTreeNode)
  {
    throw new UnsupportedOperationException();
  }
  
  public void writeValue(JsonGenerator paramJsonGenerator, Object paramObject)
    throws IOException, JsonProcessingException
  {
    throw new UnsupportedOperationException("Not implemented for ObjectReader");
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ObjectReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */