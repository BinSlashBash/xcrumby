package junit.framework;

@Deprecated
public class Assert
{
  public static void assertEquals(byte paramByte1, byte paramByte2)
  {
    assertEquals(null, paramByte1, paramByte2);
  }
  
  public static void assertEquals(char paramChar1, char paramChar2)
  {
    assertEquals(null, paramChar1, paramChar2);
  }
  
  public static void assertEquals(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    assertEquals(null, paramDouble1, paramDouble2, paramDouble3);
  }
  
  public static void assertEquals(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    assertEquals(null, paramFloat1, paramFloat2, paramFloat3);
  }
  
  public static void assertEquals(int paramInt1, int paramInt2)
  {
    assertEquals(null, paramInt1, paramInt2);
  }
  
  public static void assertEquals(long paramLong1, long paramLong2)
  {
    assertEquals(null, paramLong1, paramLong2);
  }
  
  public static void assertEquals(Object paramObject1, Object paramObject2)
  {
    assertEquals(null, paramObject1, paramObject2);
  }
  
  public static void assertEquals(String paramString, byte paramByte1, byte paramByte2)
  {
    assertEquals(paramString, new Byte(paramByte1), new Byte(paramByte2));
  }
  
  public static void assertEquals(String paramString, char paramChar1, char paramChar2)
  {
    assertEquals(paramString, new Character(paramChar1), new Character(paramChar2));
  }
  
  public static void assertEquals(String paramString, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (Double.compare(paramDouble1, paramDouble2) == 0) {}
    while (Math.abs(paramDouble1 - paramDouble2) <= paramDouble3) {
      return;
    }
    failNotEquals(paramString, new Double(paramDouble1), new Double(paramDouble2));
  }
  
  public static void assertEquals(String paramString, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (Float.compare(paramFloat1, paramFloat2) == 0) {}
    while (Math.abs(paramFloat1 - paramFloat2) <= paramFloat3) {
      return;
    }
    failNotEquals(paramString, new Float(paramFloat1), new Float(paramFloat2));
  }
  
  public static void assertEquals(String paramString, int paramInt1, int paramInt2)
  {
    assertEquals(paramString, new Integer(paramInt1), new Integer(paramInt2));
  }
  
  public static void assertEquals(String paramString, long paramLong1, long paramLong2)
  {
    assertEquals(paramString, new Long(paramLong1), new Long(paramLong2));
  }
  
  public static void assertEquals(String paramString, Object paramObject1, Object paramObject2)
  {
    if ((paramObject1 == null) && (paramObject2 == null)) {}
    while ((paramObject1 != null) && (paramObject1.equals(paramObject2))) {
      return;
    }
    failNotEquals(paramString, paramObject1, paramObject2);
  }
  
  public static void assertEquals(String paramString1, String paramString2)
  {
    assertEquals(null, paramString1, paramString2);
  }
  
  public static void assertEquals(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString2 == null) && (paramString3 == null)) {}
    while ((paramString2 != null) && (paramString2.equals(paramString3))) {
      return;
    }
    if (paramString1 == null) {
      paramString1 = "";
    }
    for (;;)
    {
      throw new ComparisonFailure(paramString1, paramString2, paramString3);
    }
  }
  
  public static void assertEquals(String paramString, short paramShort1, short paramShort2)
  {
    assertEquals(paramString, new Short(paramShort1), new Short(paramShort2));
  }
  
  public static void assertEquals(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    assertEquals(paramString, Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2));
  }
  
  public static void assertEquals(short paramShort1, short paramShort2)
  {
    assertEquals(null, paramShort1, paramShort2);
  }
  
  public static void assertEquals(boolean paramBoolean1, boolean paramBoolean2)
  {
    assertEquals(null, paramBoolean1, paramBoolean2);
  }
  
  public static void assertFalse(String paramString, boolean paramBoolean)
  {
    if (!paramBoolean) {}
    for (paramBoolean = true;; paramBoolean = false)
    {
      assertTrue(paramString, paramBoolean);
      return;
    }
  }
  
  public static void assertFalse(boolean paramBoolean)
  {
    assertFalse(null, paramBoolean);
  }
  
  public static void assertNotNull(Object paramObject)
  {
    assertNotNull(null, paramObject);
  }
  
  public static void assertNotNull(String paramString, Object paramObject)
  {
    if (paramObject != null) {}
    for (boolean bool = true;; bool = false)
    {
      assertTrue(paramString, bool);
      return;
    }
  }
  
  public static void assertNotSame(Object paramObject1, Object paramObject2)
  {
    assertNotSame(null, paramObject1, paramObject2);
  }
  
  public static void assertNotSame(String paramString, Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      failSame(paramString);
    }
  }
  
  public static void assertNull(Object paramObject)
  {
    if (paramObject != null) {
      assertNull("Expected: <null> but was: " + paramObject.toString(), paramObject);
    }
  }
  
  public static void assertNull(String paramString, Object paramObject)
  {
    if (paramObject == null) {}
    for (boolean bool = true;; bool = false)
    {
      assertTrue(paramString, bool);
      return;
    }
  }
  
  public static void assertSame(Object paramObject1, Object paramObject2)
  {
    assertSame(null, paramObject1, paramObject2);
  }
  
  public static void assertSame(String paramString, Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return;
    }
    failNotSame(paramString, paramObject1, paramObject2);
  }
  
  public static void assertTrue(String paramString, boolean paramBoolean)
  {
    if (!paramBoolean) {
      fail(paramString);
    }
  }
  
  public static void assertTrue(boolean paramBoolean)
  {
    assertTrue(null, paramBoolean);
  }
  
  public static void fail()
  {
    fail(null);
  }
  
  public static void fail(String paramString)
  {
    if (paramString == null) {
      throw new AssertionFailedError();
    }
    throw new AssertionFailedError(paramString);
  }
  
  public static void failNotEquals(String paramString, Object paramObject1, Object paramObject2)
  {
    fail(format(paramString, paramObject1, paramObject2));
  }
  
  public static void failNotSame(String paramString, Object paramObject1, Object paramObject2)
  {
    if (paramString != null) {}
    for (paramString = paramString + " ";; paramString = "")
    {
      fail(paramString + "expected same:<" + paramObject1 + "> was not:<" + paramObject2 + ">");
      return;
    }
  }
  
  public static void failSame(String paramString)
  {
    if (paramString != null) {}
    for (paramString = paramString + " ";; paramString = "")
    {
      fail(paramString + "expected not same");
      return;
    }
  }
  
  public static String format(String paramString, Object paramObject1, Object paramObject2)
  {
    String str2 = "";
    String str1 = str2;
    if (paramString != null)
    {
      str1 = str2;
      if (paramString.length() > 0) {
        str1 = paramString + " ";
      }
    }
    return str1 + "expected:<" + paramObject1 + "> but was:<" + paramObject2 + ">";
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/framework/Assert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */