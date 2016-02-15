package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public final class WritableObjectId
{
  public final ObjectIdGenerator<?> generator;
  public Object id;
  protected boolean idWritten = false;
  
  public WritableObjectId(ObjectIdGenerator<?> paramObjectIdGenerator)
  {
    this.generator = paramObjectIdGenerator;
  }
  
  public Object generateId(Object paramObject)
  {
    paramObject = this.generator.generateId(paramObject);
    this.id = paramObject;
    return paramObject;
  }
  
  public void writeAsField(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, ObjectIdWriter paramObjectIdWriter)
    throws IOException, JsonGenerationException
  {
    this.idWritten = true;
    if (paramJsonGenerator.canWriteObjectId()) {
      paramJsonGenerator.writeObjectId(String.valueOf(this.id));
    }
    SerializableString localSerializableString;
    do
    {
      return;
      localSerializableString = paramObjectIdWriter.propertyName;
    } while (localSerializableString == null);
    paramJsonGenerator.writeFieldName(localSerializableString);
    paramObjectIdWriter.serializer.serialize(this.id, paramJsonGenerator, paramSerializerProvider);
  }
  
  public boolean writeAsId(JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider, ObjectIdWriter paramObjectIdWriter)
    throws IOException, JsonGenerationException
  {
    if ((this.id != null) && ((this.idWritten) || (paramObjectIdWriter.alwaysAsId)))
    {
      if (paramJsonGenerator.canWriteObjectId()) {
        paramJsonGenerator.writeObjectRef(String.valueOf(this.id));
      }
      for (;;)
      {
        return true;
        paramObjectIdWriter.serializer.serialize(this.id, paramJsonGenerator, paramSerializerProvider);
      }
    }
    return false;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/ser/impl/WritableObjectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */