package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MappingIterator<T>
  implements Iterator<T>, Closeable
{
  protected static final MappingIterator<?> EMPTY_ITERATOR = new MappingIterator(null, null, null, null, false, null);
  protected final boolean _closeParser;
  protected final DeserializationContext _context;
  protected final JsonDeserializer<T> _deserializer;
  protected boolean _hasNextChecked;
  protected JsonParser _parser;
  protected final JavaType _type;
  protected final T _updatedValue;
  
  @Deprecated
  protected MappingIterator(JavaType paramJavaType, JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JsonDeserializer<?> paramJsonDeserializer)
  {
    this(paramJavaType, paramJsonParser, paramDeserializationContext, paramJsonDeserializer, true, null);
  }
  
  protected MappingIterator(JavaType paramJavaType, JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, JsonDeserializer<?> paramJsonDeserializer, boolean paramBoolean, Object paramObject)
  {
    this._type = paramJavaType;
    this._parser = paramJsonParser;
    this._context = paramDeserializationContext;
    this._deserializer = paramJsonDeserializer;
    this._closeParser = paramBoolean;
    if (paramObject == null) {}
    for (this._updatedValue = null;; this._updatedValue = paramObject)
    {
      if ((paramBoolean) && (paramJsonParser != null) && (paramJsonParser.getCurrentToken() == JsonToken.START_ARRAY)) {
        paramJsonParser.clearCurrentToken();
      }
      return;
    }
  }
  
  protected static <T> MappingIterator<T> emptyIterator()
  {
    return EMPTY_ITERATOR;
  }
  
  public void close()
    throws IOException
  {
    if (this._parser != null) {
      this._parser.close();
    }
  }
  
  public JsonLocation getCurrentLocation()
  {
    return this._parser.getCurrentLocation();
  }
  
  public JsonParser getParser()
  {
    return this._parser;
  }
  
  public FormatSchema getParserSchema()
  {
    return this._parser.getSchema();
  }
  
  public boolean hasNext()
  {
    try
    {
      boolean bool = hasNextValue();
      return bool;
    }
    catch (JsonMappingException localJsonMappingException)
    {
      throw new RuntimeJsonMappingException(localJsonMappingException.getMessage(), localJsonMappingException);
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException.getMessage(), localIOException);
    }
  }
  
  public boolean hasNextValue()
    throws IOException
  {
    if (this._parser == null) {}
    Object localObject;
    do
    {
      return false;
      if (this._hasNextChecked) {
        break;
      }
      localObject = this._parser.getCurrentToken();
      this._hasNextChecked = true;
      if (localObject != null) {
        break;
      }
      localObject = this._parser.nextToken();
      if ((localObject != null) && (localObject != JsonToken.END_ARRAY)) {
        break;
      }
      localObject = this._parser;
      this._parser = null;
    } while (!this._closeParser);
    ((JsonParser)localObject).close();
    return false;
    return true;
  }
  
  public T next()
  {
    try
    {
      Object localObject = nextValue();
      return (T)localObject;
    }
    catch (JsonMappingException localJsonMappingException)
    {
      throw new RuntimeJsonMappingException(localJsonMappingException.getMessage(), localJsonMappingException);
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException.getMessage(), localIOException);
    }
  }
  
  public T nextValue()
    throws IOException
  {
    if ((!this._hasNextChecked) && (!hasNextValue())) {
      throw new NoSuchElementException();
    }
    if (this._parser == null) {
      throw new NoSuchElementException();
    }
    this._hasNextChecked = false;
    if (this._updatedValue == null) {}
    for (Object localObject = this._deserializer.deserialize(this._parser, this._context);; localObject = this._updatedValue)
    {
      this._parser.clearCurrentToken();
      return (T)localObject;
      this._deserializer.deserialize(this._parser, this._context, this._updatedValue);
    }
  }
  
  public List<T> readAll()
    throws IOException
  {
    return readAll(new ArrayList());
  }
  
  public List<T> readAll(List<T> paramList)
    throws IOException
  {
    while (hasNextValue()) {
      paramList.add(nextValue());
    }
    return paramList;
  }
  
  public void remove()
  {
    throw new UnsupportedOperationException();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/MappingIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */