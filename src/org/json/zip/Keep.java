package org.json.zip;

abstract class Keep
  implements None, PostMortem
{
  protected int capacity = JSONzip.twos[paramInt];
  protected int length = 0;
  protected int power = 0;
  protected long[] uses = new long[this.capacity];
  
  public Keep(int paramInt) {}
  
  public static long age(long paramLong)
  {
    if (paramLong >= 32L) {
      return 16L;
    }
    return paramLong / 2L;
  }
  
  public int bitsize()
  {
    while (JSONzip.twos[this.power] < this.length) {
      this.power += 1;
    }
    return this.power;
  }
  
  public void tick(int paramInt)
  {
    long[] arrayOfLong = this.uses;
    arrayOfLong[paramInt] += 1L;
  }
  
  public abstract Object value(int paramInt);
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/zip/Keep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */