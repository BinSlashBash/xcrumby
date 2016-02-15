package com.fasterxml.jackson.databind.util;

public abstract class NameTransformer
{
  public static final NameTransformer NOP = new NameTransformer()
  {
    public String reverse(String paramAnonymousString)
    {
      return paramAnonymousString;
    }
    
    public String transform(String paramAnonymousString)
    {
      return paramAnonymousString;
    }
  };
  
  public static NameTransformer chainedTransformer(NameTransformer paramNameTransformer1, NameTransformer paramNameTransformer2)
  {
    return new Chained(paramNameTransformer1, paramNameTransformer2);
  }
  
  public static NameTransformer simpleTransformer(String paramString1, final String paramString2)
  {
    int j = 1;
    int i;
    if ((paramString1 != null) && (paramString1.length() > 0))
    {
      i = 1;
      if ((paramString2 == null) || (paramString2.length() <= 0)) {
        break label49;
      }
    }
    for (;;)
    {
      if (i != 0)
      {
        if (j != 0)
        {
          new NameTransformer()
          {
            public String reverse(String paramAnonymousString)
            {
              if (paramAnonymousString.startsWith(this.val$prefix))
              {
                paramAnonymousString = paramAnonymousString.substring(this.val$prefix.length());
                if (paramAnonymousString.endsWith(paramString2)) {
                  return paramAnonymousString.substring(0, paramAnonymousString.length() - paramString2.length());
                }
              }
              return null;
            }
            
            public String toString()
            {
              return "[PreAndSuffixTransformer('" + this.val$prefix + "','" + paramString2 + "')]";
            }
            
            public String transform(String paramAnonymousString)
            {
              return this.val$prefix + paramAnonymousString + paramString2;
            }
          };
          i = 0;
          break;
          label49:
          j = 0;
          continue;
        }
        new NameTransformer()
        {
          public String reverse(String paramAnonymousString)
          {
            if (paramAnonymousString.startsWith(this.val$prefix)) {
              return paramAnonymousString.substring(this.val$prefix.length());
            }
            return null;
          }
          
          public String toString()
          {
            return "[PrefixTransformer('" + this.val$prefix + "')]";
          }
          
          public String transform(String paramAnonymousString)
          {
            return this.val$prefix + paramAnonymousString;
          }
        };
      }
    }
    if (j != 0) {
      new NameTransformer()
      {
        public String reverse(String paramAnonymousString)
        {
          if (paramAnonymousString.endsWith(this.val$suffix)) {
            return paramAnonymousString.substring(0, paramAnonymousString.length() - this.val$suffix.length());
          }
          return null;
        }
        
        public String toString()
        {
          return "[SuffixTransformer('" + this.val$suffix + "')]";
        }
        
        public String transform(String paramAnonymousString)
        {
          return paramAnonymousString + this.val$suffix;
        }
      };
    }
    return NOP;
  }
  
  public abstract String reverse(String paramString);
  
  public abstract String transform(String paramString);
  
  public static class Chained
    extends NameTransformer
  {
    protected final NameTransformer _t1;
    protected final NameTransformer _t2;
    
    public Chained(NameTransformer paramNameTransformer1, NameTransformer paramNameTransformer2)
    {
      this._t1 = paramNameTransformer1;
      this._t2 = paramNameTransformer2;
    }
    
    public String reverse(String paramString)
    {
      String str = this._t1.reverse(paramString);
      paramString = str;
      if (str != null) {
        paramString = this._t2.reverse(str);
      }
      return paramString;
    }
    
    public String toString()
    {
      return "[ChainedTransformer(" + this._t1 + ", " + this._t2 + ")]";
    }
    
    public String transform(String paramString)
    {
      return this._t1.transform(this._t2.transform(paramString));
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/fasterxml/jackson/databind/util/NameTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */