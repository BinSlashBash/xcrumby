package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class TreeTraversingParser
  extends ParserMinimalBase
{
  protected boolean _closed;
  protected JsonToken _nextToken;
  protected NodeCursor _nodeCursor;
  protected ObjectCodec _objectCodec;
  protected boolean _startContainer;
  
  public TreeTraversingParser(JsonNode paramJsonNode)
  {
    this(paramJsonNode, null);
  }
  
  public TreeTraversingParser(JsonNode paramJsonNode, ObjectCodec paramObjectCodec)
  {
    super(0);
    this._objectCodec = paramObjectCodec;
    if (paramJsonNode.isArray())
    {
      this._nextToken = JsonToken.START_ARRAY;
      this._nodeCursor = new NodeCursor.Array(paramJsonNode, null);
      return;
    }
    if (paramJsonNode.isObject())
    {
      this._nextToken = JsonToken.START_OBJECT;
      this._nodeCursor = new NodeCursor.Object(paramJsonNode, null);
      return;
    }
    this._nodeCursor = new NodeCursor.RootValue(paramJsonNode, null);
  }
  
  protected void _handleEOF()
    throws JsonParseException
  {
    _throwInternal();
  }
  
  public void close()
    throws IOException
  {
    if (!this._closed)
    {
      this._closed = true;
      this._nodeCursor = null;
      this._currToken = null;
    }
  }
  
  protected JsonNode currentNode()
  {
    if ((this._closed) || (this._nodeCursor == null)) {
      return null;
    }
    return this._nodeCursor.currentNode();
  }
  
  protected JsonNode currentNumericNode()
    throws JsonParseException
  {
    Object localObject = currentNode();
    if ((localObject == null) || (!((JsonNode)localObject).isNumber()))
    {
      if (localObject == null) {}
      for (localObject = null;; localObject = ((JsonNode)localObject).asToken()) {
        throw _constructError("Current token (" + localObject + ") not numeric, can not use numeric value accessors");
      }
    }
    return (JsonNode)localObject;
  }
  
  public BigInteger getBigIntegerValue()
    throws IOException, JsonParseException
  {
    return currentNumericNode().bigIntegerValue();
  }
  
  public byte[] getBinaryValue(Base64Variant paramBase64Variant)
    throws IOException, JsonParseException
  {
    paramBase64Variant = currentNode();
    if (paramBase64Variant != null)
    {
      byte[] arrayOfByte = paramBase64Variant.binaryValue();
      if (arrayOfByte != null) {
        return arrayOfByte;
      }
      if (paramBase64Variant.isPojo())
      {
        paramBase64Variant = ((POJONode)paramBase64Variant).getPojo();
        if ((paramBase64Variant instanceof byte[])) {
          return (byte[])paramBase64Variant;
        }
      }
    }
    return null;
  }
  
  public ObjectCodec getCodec()
  {
    return this._objectCodec;
  }
  
  public JsonLocation getCurrentLocation()
  {
    return JsonLocation.NA;
  }
  
  public String getCurrentName()
  {
    if (this._nodeCursor == null) {
      return null;
    }
    return this._nodeCursor.getCurrentName();
  }
  
  public BigDecimal getDecimalValue()
    throws IOException, JsonParseException
  {
    return currentNumericNode().decimalValue();
  }
  
  public double getDoubleValue()
    throws IOException, JsonParseException
  {
    return currentNumericNode().doubleValue();
  }
  
  public Object getEmbeddedObject()
  {
    if (!this._closed)
    {
      JsonNode localJsonNode = currentNode();
      if (localJsonNode != null)
      {
        if (localJsonNode.isPojo()) {
          return ((POJONode)localJsonNode).getPojo();
        }
        if (localJsonNode.isBinary()) {
          return ((BinaryNode)localJsonNode).binaryValue();
        }
      }
    }
    return null;
  }
  
  public float getFloatValue()
    throws IOException, JsonParseException
  {
    return (float)currentNumericNode().doubleValue();
  }
  
  public int getIntValue()
    throws IOException, JsonParseException
  {
    return currentNumericNode().intValue();
  }
  
  public long getLongValue()
    throws IOException, JsonParseException
  {
    return currentNumericNode().longValue();
  }
  
  public JsonParser.NumberType getNumberType()
    throws IOException, JsonParseException
  {
    JsonNode localJsonNode = currentNumericNode();
    if (localJsonNode == null) {
      return null;
    }
    return localJsonNode.numberType();
  }
  
  public Number getNumberValue()
    throws IOException, JsonParseException
  {
    return currentNumericNode().numberValue();
  }
  
  public JsonStreamContext getParsingContext()
  {
    return this._nodeCursor;
  }
  
  public String getText()
  {
    if (this._closed) {}
    JsonNode localJsonNode;
    do
    {
      while (this._currToken == null)
      {
        return null;
        switch (this._currToken)
        {
        }
      }
      return this._currToken.asString();
      return this._nodeCursor.getCurrentName();
      return currentNode().textValue();
      return String.valueOf(currentNode().numberValue());
      localJsonNode = currentNode();
    } while ((localJsonNode == null) || (!localJsonNode.isBinary()));
    return localJsonNode.asText();
  }
  
  public char[] getTextCharacters()
    throws IOException, JsonParseException
  {
    return getText().toCharArray();
  }
  
  public int getTextLength()
    throws IOException, JsonParseException
  {
    return getText().length();
  }
  
  public int getTextOffset()
    throws IOException, JsonParseException
  {
    return 0;
  }
  
  public JsonLocation getTokenLocation()
  {
    return JsonLocation.NA;
  }
  
  public boolean hasTextCharacters()
  {
    return false;
  }
  
  public boolean isClosed()
  {
    return this._closed;
  }
  
  public JsonToken nextToken()
    throws IOException, JsonParseException
  {
    if (this._nextToken != null)
    {
      this._currToken = this._nextToken;
      this._nextToken = null;
      return this._currToken;
    }
    if (this._startContainer)
    {
      this._startContainer = false;
      if (!this._nodeCursor.currentHasChildren())
      {
        if (this._currToken == JsonToken.START_OBJECT) {}
        for (JsonToken localJsonToken = JsonToken.END_OBJECT;; localJsonToken = JsonToken.END_ARRAY)
        {
          this._currToken = localJsonToken;
          return this._currToken;
        }
      }
      this._nodeCursor = this._nodeCursor.iterateChildren();
      this._currToken = this._nodeCursor.nextToken();
      if ((this._currToken == JsonToken.START_OBJECT) || (this._currToken == JsonToken.START_ARRAY)) {
        this._startContainer = true;
      }
      return this._currToken;
    }
    if (this._nodeCursor == null)
    {
      this._closed = true;
      return null;
    }
    this._currToken = this._nodeCursor.nextToken();
    if (this._currToken != null)
    {
      if ((this._currToken == JsonToken.START_OBJECT) || (this._currToken == JsonToken.START_ARRAY)) {
        this._startContainer = true;
      }
      return this._currToken;
    }
    this._currToken = this._nodeCursor.endToken();
    this._nodeCursor = this._nodeCursor.getParent();
    return this._currToken;
  }
  
  public void overrideCurrentName(String paramString)
  {
    if (this._nodeCursor != null) {
      this._nodeCursor.overrideCurrentName(paramString);
    }
  }
  
  public int readBinaryValue(Base64Variant paramBase64Variant, OutputStream paramOutputStream)
    throws IOException, JsonParseException
  {
    int i = 0;
    paramBase64Variant = getBinaryValue(paramBase64Variant);
    if (paramBase64Variant != null)
    {
      paramOutputStream.write(paramBase64Variant, 0, paramBase64Variant.length);
      i = paramBase64Variant.length;
    }
    return i;
  }
  
  public void setCodec(ObjectCodec paramObjectCodec)
  {
    this._objectCodec = paramObjectCodec;
  }
  
  public JsonParser skipChildren()
    throws IOException, JsonParseException
  {
    if (this._currToken == JsonToken.START_OBJECT)
    {
      this._startContainer = false;
      this._currToken = JsonToken.END_OBJECT;
    }
    while (this._currToken != JsonToken.START_ARRAY) {
      return this;
    }
    this._startContainer = false;
    this._currToken = JsonToken.END_ARRAY;
    return this;
  }
  
  public Version version()
  {
    return PackageVersion.VERSION;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/node/TreeTraversingParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */