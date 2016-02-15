package org.json.zip;

import org.json.JSONException;

public class Huff
  implements None, PostMortem
{
  private final int domain;
  private final Symbol[] symbols;
  private Symbol table;
  private boolean upToDate = false;
  private int width;
  
  public Huff(int paramInt)
  {
    this.domain = paramInt;
    int j = paramInt * 2 - 1;
    this.symbols = new Symbol[j];
    int i = 0;
    while (i < paramInt)
    {
      this.symbols[i] = new Symbol(i);
      i += 1;
    }
    while (paramInt < j)
    {
      this.symbols[paramInt] = new Symbol(-1);
      paramInt += 1;
    }
  }
  
  private boolean postMortem(int paramInt)
  {
    boolean bool = true;
    int[] arrayOfInt = new int[this.domain];
    Object localObject = this.symbols[paramInt];
    if (((Symbol)localObject).integer != paramInt) {}
    int i;
    label31:
    Symbol localSymbol;
    do
    {
      return false;
      i = 0;
      localSymbol = ((Symbol)localObject).back;
      if (localSymbol != null) {
        break;
      }
    } while (localObject != this.table);
    this.width = 0;
    localObject = this.table;
    label63:
    if (((Symbol)localObject).integer == -1)
    {
      i -= 1;
      if (arrayOfInt[i] != 0) {}
      for (localObject = ((Symbol)localObject).one;; localObject = ((Symbol)localObject).zero)
      {
        break label63;
        if (localSymbol.zero == localObject) {
          arrayOfInt[i] = 0;
        }
        for (;;)
        {
          i += 1;
          localObject = localSymbol;
          break label31;
          if (localSymbol.one != localObject) {
            break;
          }
          arrayOfInt[i] = 1;
        }
      }
    }
    if ((((Symbol)localObject).integer == paramInt) && (i == 0)) {}
    for (;;)
    {
      return bool;
      bool = false;
    }
  }
  
  private void write(Symbol paramSymbol, BitWriter paramBitWriter)
    throws JSONException
  {
    try
    {
      Symbol localSymbol = paramSymbol.back;
      if (localSymbol != null)
      {
        this.width += 1;
        write(localSymbol, paramBitWriter);
        if (localSymbol.zero == paramSymbol)
        {
          paramBitWriter.zero();
          return;
        }
        paramBitWriter.one();
        return;
      }
    }
    catch (Throwable paramSymbol)
    {
      throw new JSONException(paramSymbol);
    }
  }
  
  public void generate()
  {
    Object localObject1;
    Object localObject2;
    int i;
    Object localObject4;
    Object localObject3;
    if (!this.upToDate)
    {
      localObject1 = this.symbols[0];
      localObject2 = localObject1;
      this.table = null;
      ((Symbol)localObject1).next = null;
      i = 1;
      while (i < this.domain)
      {
        localObject4 = this.symbols[i];
        if (((Symbol)localObject4).weight < ((Symbol)localObject1).weight)
        {
          ((Symbol)localObject4).next = ((Symbol)localObject1);
          localObject1 = localObject4;
          i += 1;
        }
        else
        {
          localObject3 = localObject2;
          if (((Symbol)localObject4).weight < ((Symbol)localObject2).weight) {}
          for (localObject3 = localObject1;; localObject3 = localObject2)
          {
            localObject2 = ((Symbol)localObject3).next;
            if ((localObject2 == null) || (((Symbol)localObject4).weight < ((Symbol)localObject2).weight))
            {
              ((Symbol)localObject4).next = ((Symbol)localObject2);
              ((Symbol)localObject3).next = ((Symbol)localObject4);
              localObject2 = localObject4;
              break;
            }
          }
        }
      }
      i = this.domain;
      localObject2 = localObject1;
    }
    for (;;)
    {
      localObject4 = localObject1;
      Symbol localSymbol = ((Symbol)localObject4).next;
      localObject3 = localSymbol.next;
      localObject1 = this.symbols[i];
      i += 1;
      ((Symbol)localObject4).weight += localSymbol.weight;
      ((Symbol)localObject1).zero = ((Symbol)localObject4);
      ((Symbol)localObject1).one = localSymbol;
      ((Symbol)localObject1).back = null;
      ((Symbol)localObject4).back = ((Symbol)localObject1);
      localSymbol.back = ((Symbol)localObject1);
      if (localObject3 == null)
      {
        this.table = ((Symbol)localObject1);
        this.upToDate = true;
        return;
      }
      if (((Symbol)localObject1).weight < ((Symbol)localObject3).weight)
      {
        ((Symbol)localObject1).next = ((Symbol)localObject3);
        localObject2 = localObject1;
      }
      else
      {
        do
        {
          localObject2 = localObject4;
          localObject4 = ((Symbol)localObject2).next;
        } while ((localObject4 != null) && (((Symbol)localObject1).weight >= ((Symbol)localObject4).weight));
        ((Symbol)localObject1).next = ((Symbol)localObject4);
        ((Symbol)localObject2).next = ((Symbol)localObject1);
        localObject2 = localObject1;
        localObject1 = localObject3;
      }
    }
  }
  
  public boolean postMortem(PostMortem paramPostMortem)
  {
    int i = 0;
    while (i < this.domain)
    {
      if (!postMortem(i))
      {
        JSONzip.log("\nBad huff ");
        JSONzip.logchar(i, i);
        return false;
      }
      i += 1;
    }
    return this.table.postMortem(((Huff)paramPostMortem).table);
  }
  
  public int read(BitReader paramBitReader)
    throws JSONException
  {
    for (;;)
    {
      try
      {
        this.width = 0;
        Symbol localSymbol = this.table;
        if (localSymbol.integer == -1)
        {
          this.width += 1;
          if (paramBitReader.bit()) {
            localSymbol = localSymbol.one;
          } else {
            localSymbol = localSymbol.zero;
          }
        }
        else
        {
          tick(localSymbol.integer);
          int i = localSymbol.integer;
          return i;
        }
      }
      catch (Throwable paramBitReader)
      {
        throw new JSONException(paramBitReader);
      }
    }
  }
  
  public void tick(int paramInt)
  {
    Symbol localSymbol = this.symbols[paramInt];
    localSymbol.weight += 1L;
    this.upToDate = false;
  }
  
  public void tick(int paramInt1, int paramInt2)
  {
    while (paramInt1 <= paramInt2)
    {
      tick(paramInt1);
      paramInt1 += 1;
    }
  }
  
  public void write(int paramInt, BitWriter paramBitWriter)
    throws JSONException
  {
    this.width = 0;
    write(this.symbols[paramInt], paramBitWriter);
    tick(paramInt);
  }
  
  private static class Symbol
    implements PostMortem
  {
    public Symbol back;
    public final int integer;
    public Symbol next;
    public Symbol one;
    public long weight;
    public Symbol zero;
    
    public Symbol(int paramInt)
    {
      this.integer = paramInt;
      this.weight = 0L;
      this.next = null;
      this.back = null;
      this.one = null;
      this.zero = null;
    }
    
    public boolean postMortem(PostMortem paramPostMortem)
    {
      int j = 1;
      boolean bool = true;
      paramPostMortem = (Symbol)paramPostMortem;
      if ((this.integer != paramPostMortem.integer) || (this.weight != paramPostMortem.weight)) {}
      int i;
      label44:
      label51:
      Symbol localSymbol1;
      Symbol localSymbol2;
      label80:
      do
      {
        do
        {
          return false;
          if (this.back == null) {
            break;
          }
          i = 1;
          if (paramPostMortem.back == null) {
            break label100;
          }
          if (i != j) {
            break label103;
          }
          localSymbol1 = this.zero;
          localSymbol2 = this.one;
          if (localSymbol1 != null) {
            break label105;
          }
        } while (paramPostMortem.zero != null);
        if (localSymbol2 != null) {
          break label119;
        }
      } while (paramPostMortem.one != null);
      for (;;)
      {
        return bool;
        i = 0;
        break label44;
        label100:
        j = 0;
        break label51;
        label103:
        break;
        label105:
        bool = localSymbol1.postMortem(paramPostMortem.zero);
        break label80;
        label119:
        bool = localSymbol2.postMortem(paramPostMortem.one);
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/zip/Huff.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */