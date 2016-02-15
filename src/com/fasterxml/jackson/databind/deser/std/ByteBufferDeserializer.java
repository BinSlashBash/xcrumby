package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.util.ByteBufferBackedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ByteBufferDeserializer
  extends StdScalarDeserializer<ByteBuffer>
{
  private static final long serialVersionUID = 1L;
  
  protected ByteBufferDeserializer()
  {
    super(ByteBuffer.class);
  }
  
  public ByteBuffer deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
    throws IOException
  {
    return ByteBuffer.wrap(paramJsonParser.getBinaryValue());
  }
  
  public ByteBuffer deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext, ByteBuffer paramByteBuffer)
    throws IOException
  {
    ByteBufferBackedOutputStream localByteBufferBackedOutputStream = new ByteBufferBackedOutputStream(paramByteBuffer);
    paramJsonParser.readBinaryValue(paramDeserializationContext.getBase64Variant(), localByteBufferBackedOutputStream);
    localByteBufferBackedOutputStream.close();
    return paramByteBuffer;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/deser/std/ByteBufferDeserializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */