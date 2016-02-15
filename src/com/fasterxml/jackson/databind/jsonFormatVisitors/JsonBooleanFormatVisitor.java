package com.fasterxml.jackson.databind.jsonFormatVisitors;

public abstract interface JsonBooleanFormatVisitor
  extends JsonValueFormatVisitor
{
  public static class Base
    extends JsonValueFormatVisitor.Base
    implements JsonBooleanFormatVisitor
  {}
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/jsonFormatVisitors/JsonBooleanFormatVisitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */