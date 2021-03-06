package org.jsoup.parser;

import java.util.ArrayList;

class ParseErrorList
  extends ArrayList<ParseError>
{
  private static final int INITIAL_CAPACITY = 16;
  private final int maxSize;
  
  ParseErrorList(int paramInt1, int paramInt2)
  {
    super(paramInt1);
    this.maxSize = paramInt2;
  }
  
  static ParseErrorList noTracking()
  {
    return new ParseErrorList(0, 0);
  }
  
  static ParseErrorList tracking(int paramInt)
  {
    return new ParseErrorList(16, paramInt);
  }
  
  boolean canAddError()
  {
    return size() < this.maxSize;
  }
  
  int getMaxSize()
  {
    return this.maxSize;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/jsoup/parser/ParseErrorList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */