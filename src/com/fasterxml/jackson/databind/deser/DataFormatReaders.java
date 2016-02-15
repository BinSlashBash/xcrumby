package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.format.InputAccessor.Std;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.MergedStream;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class DataFormatReaders
{
  public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
  protected final int _maxInputLookahead;
  protected final MatchStrength _minimalMatch;
  protected final MatchStrength _optimalMatch;
  protected final ObjectReader[] _readers;
  
  public DataFormatReaders(Collection<ObjectReader> paramCollection)
  {
    this((ObjectReader[])paramCollection.toArray(new ObjectReader[paramCollection.size()]));
  }
  
  public DataFormatReaders(ObjectReader... paramVarArgs)
  {
    this(paramVarArgs, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
  }
  
  private DataFormatReaders(ObjectReader[] paramArrayOfObjectReader, MatchStrength paramMatchStrength1, MatchStrength paramMatchStrength2, int paramInt)
  {
    this._readers = paramArrayOfObjectReader;
    this._optimalMatch = paramMatchStrength1;
    this._minimalMatch = paramMatchStrength2;
    this._maxInputLookahead = paramInt;
  }
  
  private Match _findFormat(AccessorForReader paramAccessorForReader)
    throws IOException
  {
    Object localObject2 = null;
    Object localObject1 = null;
    ObjectReader[] arrayOfObjectReader = this._readers;
    int j = arrayOfObjectReader.length;
    int i = 0;
    Object localObject4 = localObject2;
    Object localObject3 = localObject1;
    if (i < j)
    {
      ObjectReader localObjectReader = arrayOfObjectReader[i];
      paramAccessorForReader.reset();
      MatchStrength localMatchStrength = localObjectReader.getFactory().hasFormat(paramAccessorForReader);
      localObject3 = localObject2;
      localObject4 = localObject1;
      if (localMatchStrength != null)
      {
        if (localMatchStrength.ordinal() >= this._minimalMatch.ordinal()) {
          break label103;
        }
        localObject4 = localObject1;
        localObject3 = localObject2;
      }
      label103:
      label129:
      do
      {
        do
        {
          i += 1;
          localObject2 = localObject3;
          localObject1 = localObject4;
          break;
          if (localObject2 == null) {
            break label129;
          }
          localObject3 = localObject2;
          localObject4 = localObject1;
        } while (((MatchStrength)localObject1).ordinal() >= localMatchStrength.ordinal());
        localObject1 = localObjectReader;
        localObject2 = localMatchStrength;
        localObject3 = localObject1;
        localObject4 = localObject2;
      } while (localMatchStrength.ordinal() < this._optimalMatch.ordinal());
      localObject3 = localObject2;
      localObject4 = localObject1;
    }
    return paramAccessorForReader.createMatcher((ObjectReader)localObject4, (MatchStrength)localObject3);
  }
  
  public Match findFormat(InputStream paramInputStream)
    throws IOException
  {
    return _findFormat(new AccessorForReader(paramInputStream, new byte[this._maxInputLookahead]));
  }
  
  public Match findFormat(byte[] paramArrayOfByte)
    throws IOException
  {
    return _findFormat(new AccessorForReader(paramArrayOfByte));
  }
  
  public Match findFormat(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    return _findFormat(new AccessorForReader(paramArrayOfByte, paramInt1, paramInt2));
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('[');
    int j = this._readers.length;
    if (j > 0)
    {
      localStringBuilder.append(this._readers[0].getFactory().getFormatName());
      int i = 1;
      while (i < j)
      {
        localStringBuilder.append(", ");
        localStringBuilder.append(this._readers[i].getFactory().getFormatName());
        i += 1;
      }
    }
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
  
  public DataFormatReaders with(DeserializationConfig paramDeserializationConfig)
  {
    int j = this._readers.length;
    ObjectReader[] arrayOfObjectReader = new ObjectReader[j];
    int i = 0;
    while (i < j)
    {
      arrayOfObjectReader[i] = this._readers[i].with(paramDeserializationConfig);
      i += 1;
    }
    return new DataFormatReaders(arrayOfObjectReader, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
  }
  
  public DataFormatReaders with(ObjectReader[] paramArrayOfObjectReader)
  {
    return new DataFormatReaders(paramArrayOfObjectReader, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
  }
  
  public DataFormatReaders withMaxInputLookahead(int paramInt)
  {
    if (paramInt == this._maxInputLookahead) {
      return this;
    }
    return new DataFormatReaders(this._readers, this._optimalMatch, this._minimalMatch, paramInt);
  }
  
  public DataFormatReaders withMinimalMatch(MatchStrength paramMatchStrength)
  {
    if (paramMatchStrength == this._minimalMatch) {
      return this;
    }
    return new DataFormatReaders(this._readers, this._optimalMatch, paramMatchStrength, this._maxInputLookahead);
  }
  
  public DataFormatReaders withOptimalMatch(MatchStrength paramMatchStrength)
  {
    if (paramMatchStrength == this._optimalMatch) {
      return this;
    }
    return new DataFormatReaders(this._readers, paramMatchStrength, this._minimalMatch, this._maxInputLookahead);
  }
  
  public DataFormatReaders withType(JavaType paramJavaType)
  {
    int j = this._readers.length;
    ObjectReader[] arrayOfObjectReader = new ObjectReader[j];
    int i = 0;
    while (i < j)
    {
      arrayOfObjectReader[i] = this._readers[i].withType(paramJavaType);
      i += 1;
    }
    return new DataFormatReaders(arrayOfObjectReader, this._optimalMatch, this._minimalMatch, this._maxInputLookahead);
  }
  
  protected class AccessorForReader
    extends InputAccessor.Std
  {
    public AccessorForReader(InputStream paramInputStream, byte[] paramArrayOfByte)
    {
      super(paramArrayOfByte);
    }
    
    public AccessorForReader(byte[] paramArrayOfByte)
    {
      super();
    }
    
    public AccessorForReader(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      super(paramInt1, paramInt2);
    }
    
    public DataFormatReaders.Match createMatcher(ObjectReader paramObjectReader, MatchStrength paramMatchStrength)
    {
      return new DataFormatReaders.Match(this._in, this._buffer, this._bufferedStart, this._bufferedEnd - this._bufferedStart, paramObjectReader, paramMatchStrength);
    }
  }
  
  public static class Match
  {
    protected final byte[] _bufferedData;
    protected final int _bufferedLength;
    protected final int _bufferedStart;
    protected final ObjectReader _match;
    protected final MatchStrength _matchStrength;
    protected final InputStream _originalStream;
    
    protected Match(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2, ObjectReader paramObjectReader, MatchStrength paramMatchStrength)
    {
      this._originalStream = paramInputStream;
      this._bufferedData = paramArrayOfByte;
      this._bufferedStart = paramInt1;
      this._bufferedLength = paramInt2;
      this._match = paramObjectReader;
      this._matchStrength = paramMatchStrength;
    }
    
    public JsonParser createParserWithMatch()
      throws IOException
    {
      if (this._match == null) {
        return null;
      }
      JsonFactory localJsonFactory = this._match.getFactory();
      if (this._originalStream == null) {
        return localJsonFactory.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength);
      }
      return localJsonFactory.createParser(getDataStream());
    }
    
    public InputStream getDataStream()
    {
      if (this._originalStream == null) {
        return new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength);
      }
      return new MergedStream(null, this._originalStream, this._bufferedData, this._bufferedStart, this._bufferedLength);
    }
    
    public MatchStrength getMatchStrength()
    {
      if (this._matchStrength == null) {
        return MatchStrength.INCONCLUSIVE;
      }
      return this._matchStrength;
    }
    
    public String getMatchedFormatName()
    {
      return this._match.getFactory().getFormatName();
    }
    
    public ObjectReader getReader()
    {
      return this._match;
    }
    
    public boolean hasMatch()
    {
      return this._match != null;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/DataFormatReaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */