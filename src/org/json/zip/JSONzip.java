package org.json.zip;

import java.io.PrintStream;

public abstract class JSONzip
  implements None, PostMortem
{
  public static final byte[] bcd = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 46, 45, 43, 69 };
  public static final int end = 256;
  public static final int endOfNumber = bcd.length;
  public static final long int14 = 16384L;
  public static final long int4 = 16L;
  public static final long int7 = 128L;
  public static final int maxSubstringLength = 10;
  public static final int minSubstringLength = 3;
  public static final boolean probe = false;
  public static final int substringLimit = 40;
  public static final int[] twos = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536 };
  public static final int zipArrayString = 6;
  public static final int zipArrayValue = 7;
  public static final int zipEmptyArray = 1;
  public static final int zipEmptyObject = 0;
  public static final int zipFalse = 3;
  public static final int zipNull = 4;
  public static final int zipObject = 5;
  public static final int zipTrue = 2;
  protected final Huff namehuff = new Huff(257);
  protected final MapKeep namekeep = new MapKeep(9);
  protected final MapKeep stringkeep = new MapKeep(11);
  protected final Huff substringhuff = new Huff(257);
  protected final TrieKeep substringkeep = new TrieKeep(12);
  protected final MapKeep values = new MapKeep(10);
  
  protected JSONzip()
  {
    this.namehuff.tick(32, 125);
    this.namehuff.tick(97, 122);
    this.namehuff.tick(256);
    this.namehuff.tick(256);
    this.substringhuff.tick(32, 125);
    this.substringhuff.tick(97, 122);
    this.substringhuff.tick(256);
    this.substringhuff.tick(256);
  }
  
  static void log()
  {
    log("\n");
  }
  
  static void log(int paramInt)
  {
    log(paramInt + " ");
  }
  
  static void log(int paramInt1, int paramInt2)
  {
    log(paramInt1 + ":" + paramInt2 + " ");
  }
  
  static void log(String paramString)
  {
    System.out.print(paramString);
  }
  
  static void logchar(int paramInt1, int paramInt2)
  {
    if ((paramInt1 > 32) && (paramInt1 <= 125))
    {
      log("'" + (char)paramInt1 + "':" + paramInt2 + " ");
      return;
    }
    log(paramInt1, paramInt2);
  }
  
  protected void begin()
  {
    this.namehuff.generate();
    this.substringhuff.generate();
  }
  
  public boolean postMortem(PostMortem paramPostMortem)
  {
    paramPostMortem = (JSONzip)paramPostMortem;
    return (this.namehuff.postMortem(paramPostMortem.namehuff)) && (this.namekeep.postMortem(paramPostMortem.namekeep)) && (this.stringkeep.postMortem(paramPostMortem.stringkeep)) && (this.substringhuff.postMortem(paramPostMortem.substringhuff)) && (this.substringkeep.postMortem(paramPostMortem.substringkeep)) && (this.values.postMortem(paramPostMortem.values));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/json/zip/JSONzip.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */