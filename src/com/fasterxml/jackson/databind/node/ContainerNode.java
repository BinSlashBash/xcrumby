package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class ContainerNode<T extends ContainerNode<T>>
  extends BaseJsonNode
  implements JsonNodeCreator
{
  protected final JsonNodeFactory _nodeFactory;
  
  protected ContainerNode(JsonNodeFactory paramJsonNodeFactory)
  {
    this._nodeFactory = paramJsonNodeFactory;
  }
  
  @Deprecated
  public final POJONode POJONode(Object paramObject)
  {
    return (POJONode)this._nodeFactory.pojoNode(paramObject);
  }
  
  public final ArrayNode arrayNode()
  {
    return this._nodeFactory.arrayNode();
  }
  
  public String asText()
  {
    return "";
  }
  
  public abstract JsonToken asToken();
  
  public final BinaryNode binaryNode(byte[] paramArrayOfByte)
  {
    return this._nodeFactory.binaryNode(paramArrayOfByte);
  }
  
  public final BinaryNode binaryNode(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return this._nodeFactory.binaryNode(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public final BooleanNode booleanNode(boolean paramBoolean)
  {
    return this._nodeFactory.booleanNode(paramBoolean);
  }
  
  public abstract JsonNode get(int paramInt);
  
  public abstract JsonNode get(String paramString);
  
  public final NullNode nullNode()
  {
    return this._nodeFactory.nullNode();
  }
  
  public final NumericNode numberNode(byte paramByte)
  {
    return this._nodeFactory.numberNode(paramByte);
  }
  
  public final NumericNode numberNode(double paramDouble)
  {
    return this._nodeFactory.numberNode(paramDouble);
  }
  
  public final NumericNode numberNode(float paramFloat)
  {
    return this._nodeFactory.numberNode(paramFloat);
  }
  
  public final NumericNode numberNode(int paramInt)
  {
    return this._nodeFactory.numberNode(paramInt);
  }
  
  public final NumericNode numberNode(long paramLong)
  {
    return this._nodeFactory.numberNode(paramLong);
  }
  
  public final NumericNode numberNode(BigDecimal paramBigDecimal)
  {
    return this._nodeFactory.numberNode(paramBigDecimal);
  }
  
  public final NumericNode numberNode(BigInteger paramBigInteger)
  {
    return this._nodeFactory.numberNode(paramBigInteger);
  }
  
  public final NumericNode numberNode(short paramShort)
  {
    return this._nodeFactory.numberNode(paramShort);
  }
  
  public final ValueNode numberNode(Byte paramByte)
  {
    return this._nodeFactory.numberNode(paramByte);
  }
  
  public final ValueNode numberNode(Double paramDouble)
  {
    return this._nodeFactory.numberNode(paramDouble);
  }
  
  public final ValueNode numberNode(Float paramFloat)
  {
    return this._nodeFactory.numberNode(paramFloat);
  }
  
  public final ValueNode numberNode(Integer paramInteger)
  {
    return this._nodeFactory.numberNode(paramInteger);
  }
  
  public final ValueNode numberNode(Long paramLong)
  {
    return this._nodeFactory.numberNode(paramLong);
  }
  
  public final ValueNode numberNode(Short paramShort)
  {
    return this._nodeFactory.numberNode(paramShort);
  }
  
  public final ObjectNode objectNode()
  {
    return this._nodeFactory.objectNode();
  }
  
  public final ValueNode pojoNode(Object paramObject)
  {
    return this._nodeFactory.pojoNode(paramObject);
  }
  
  public abstract T removeAll();
  
  public abstract int size();
  
  public final TextNode textNode(String paramString)
  {
    return this._nodeFactory.textNode(paramString);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/node/ContainerNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */