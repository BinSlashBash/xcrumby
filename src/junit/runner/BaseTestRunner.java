package junit.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.NumberFormat;
import java.util.Properties;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;
import junit.framework.TestSuite;

public abstract class BaseTestRunner
  implements TestListener
{
  public static final String SUITE_METHODNAME = "suite";
  private static Properties fPreferences;
  static boolean fgFilterStack = true;
  static int fgMaxMessageLength = getPreference("maxmessage", fgMaxMessageLength);
  boolean fLoading = true;
  
  static boolean filterLine(String paramString)
  {
    String[] arrayOfString = new String[8];
    arrayOfString[0] = "junit.framework.TestCase";
    arrayOfString[1] = "junit.framework.TestResult";
    arrayOfString[2] = "junit.framework.TestSuite";
    arrayOfString[3] = "junit.framework.Assert.";
    arrayOfString[4] = "junit.swingui.TestRunner";
    arrayOfString[5] = "junit.awtui.TestRunner";
    arrayOfString[6] = "junit.textui.TestRunner";
    arrayOfString[7] = "java.lang.reflect.Method.invoke(";
    int i = 0;
    while (i < arrayOfString.length)
    {
      if (paramString.indexOf(arrayOfString[i]) > 0) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public static String getFilteredTrace(String paramString)
  {
    if (showStackRaw()) {
      return paramString;
    }
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    BufferedReader localBufferedReader = new BufferedReader(new StringReader(paramString));
    try
    {
      for (;;)
      {
        String str = localBufferedReader.readLine();
        if (str == null) {
          break;
        }
        if (!filterLine(str)) {
          localPrintWriter.println(str);
        }
      }
      return localException.toString();
    }
    catch (Exception localException)
    {
      return paramString;
    }
  }
  
  public static String getFilteredTrace(Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    paramThrowable.printStackTrace(new PrintWriter(localStringWriter));
    return getFilteredTrace(localStringWriter.getBuffer().toString());
  }
  
  public static int getPreference(String paramString, int paramInt)
  {
    paramString = getPreference(paramString);
    if (paramString == null) {
      return paramInt;
    }
    try
    {
      int i = Integer.parseInt(paramString);
      paramInt = i;
    }
    catch (NumberFormatException paramString)
    {
      for (;;) {}
    }
    return paramInt;
  }
  
  public static String getPreference(String paramString)
  {
    return getPreferences().getProperty(paramString);
  }
  
  protected static Properties getPreferences()
  {
    if (fPreferences == null)
    {
      fPreferences = new Properties();
      fPreferences.put("loading", "true");
      fPreferences.put("filterstack", "true");
      readPreferences();
    }
    return fPreferences;
  }
  
  private static File getPreferencesFile()
  {
    return new File(System.getProperty("user.home"), "junit.properties");
  }
  
  /* Error */
  private static void readPreferences()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_0
    //   2: new 165	java/io/FileInputStream
    //   5: dup
    //   6: invokestatic 167	junit/runner/BaseTestRunner:getPreferencesFile	()Ljava/io/File;
    //   9: invokespecial 170	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   12: astore_1
    //   13: new 128	java/util/Properties
    //   16: dup
    //   17: invokestatic 126	junit/runner/BaseTestRunner:getPreferences	()Ljava/util/Properties;
    //   20: invokespecial 173	java/util/Properties:<init>	(Ljava/util/Properties;)V
    //   23: invokestatic 176	junit/runner/BaseTestRunner:setPreferences	(Ljava/util/Properties;)V
    //   26: invokestatic 126	junit/runner/BaseTestRunner:getPreferences	()Ljava/util/Properties;
    //   29: aload_1
    //   30: invokevirtual 180	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   33: return
    //   34: astore_1
    //   35: aload_0
    //   36: ifnull -3 -> 33
    //   39: aload_0
    //   40: invokevirtual 185	java/io/InputStream:close	()V
    //   43: return
    //   44: astore_0
    //   45: return
    //   46: astore_0
    //   47: aload_1
    //   48: astore_0
    //   49: goto -14 -> 35
    // Local variable table:
    //   start	length	slot	name	signature
    //   1	39	0	localObject1	Object
    //   44	1	0	localIOException1	IOException
    //   46	1	0	localIOException2	IOException
    //   48	1	0	localObject2	Object
    //   12	18	1	localFileInputStream	java.io.FileInputStream
    //   34	14	1	localIOException3	IOException
    // Exception table:
    //   from	to	target	type
    //   2	13	34	java/io/IOException
    //   39	43	44	java/io/IOException
    //   13	33	46	java/io/IOException
  }
  
  public static void savePreferences()
    throws IOException
  {
    FileOutputStream localFileOutputStream = new FileOutputStream(getPreferencesFile());
    try
    {
      getPreferences().store(localFileOutputStream, "");
      return;
    }
    finally
    {
      localFileOutputStream.close();
    }
  }
  
  public static void setPreference(String paramString1, String paramString2)
  {
    getPreferences().put(paramString1, paramString2);
  }
  
  protected static void setPreferences(Properties paramProperties)
  {
    fPreferences = paramProperties;
  }
  
  protected static boolean showStackRaw()
  {
    return (!getPreference("filterstack").equals("true")) || (!fgFilterStack);
  }
  
  public static String truncate(String paramString)
  {
    String str = paramString;
    if (fgMaxMessageLength != -1)
    {
      str = paramString;
      if (paramString.length() > fgMaxMessageLength) {
        str = paramString.substring(0, fgMaxMessageLength) + "...";
      }
    }
    return str;
  }
  
  public void addError(Test paramTest, Throwable paramThrowable)
  {
    try
    {
      testFailed(1, paramTest, paramThrowable);
      return;
    }
    finally
    {
      paramTest = finally;
      throw paramTest;
    }
  }
  
  public void addFailure(Test paramTest, AssertionFailedError paramAssertionFailedError)
  {
    try
    {
      testFailed(2, paramTest, paramAssertionFailedError);
      return;
    }
    finally
    {
      paramTest = finally;
      throw paramTest;
    }
  }
  
  protected void clearStatus() {}
  
  public String elapsedTimeAsString(long paramLong)
  {
    return NumberFormat.getInstance().format(paramLong / 1000.0D);
  }
  
  public void endTest(Test paramTest)
  {
    try
    {
      testEnded(paramTest.toString());
      return;
    }
    finally
    {
      paramTest = finally;
      throw paramTest;
    }
  }
  
  public String extractClassName(String paramString)
  {
    String str = paramString;
    if (paramString.startsWith("Default package for")) {
      str = paramString.substring(paramString.lastIndexOf(".") + 1);
    }
    return str;
  }
  
  public Test getTest(String paramString)
  {
    if (paramString.length() <= 0)
    {
      clearStatus();
      paramString = null;
    }
    for (;;)
    {
      return paramString;
      try
      {
        Class localClass = loadSuiteClass(paramString);
        try
        {
          String str;
          Object localObject = (Test)paramString.invoke(null, (Object[])new Class[0]);
          paramString = (String)localObject;
          if (localObject != null)
          {
            clearStatus();
            return (Test)localObject;
          }
        }
        catch (InvocationTargetException paramString)
        {
          runFailed("Failed to invoke suite():" + paramString.getTargetException().toString());
          return null;
        }
        catch (IllegalAccessException paramString)
        {
          runFailed("Failed to invoke suite():" + paramString.toString());
        }
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        try
        {
          paramString = localClass.getMethod("suite", new Class[0]);
          if (Modifier.isStatic(paramString.getModifiers())) {
            break label141;
          }
          runFailed("Suite() method must be static");
          return null;
        }
        catch (Exception paramString)
        {
          clearStatus();
          return new TestSuite((Class)localObject);
        }
        localClassNotFoundException = localClassNotFoundException;
        str = localClassNotFoundException.getMessage();
        localObject = str;
        if (str == null) {
          localObject = paramString;
        }
        runFailed("Class not found \"" + (String)localObject + "\"");
        return null;
      }
      catch (Exception paramString)
      {
        runFailed("Error: " + paramString.toString());
        return null;
      }
    }
    label141:
    return null;
  }
  
  protected Class<?> loadSuiteClass(String paramString)
    throws ClassNotFoundException
  {
    return Class.forName(paramString);
  }
  
  protected String processArguments(String[] paramArrayOfString)
  {
    String str = null;
    int i = 0;
    if (i < paramArrayOfString.length)
    {
      if (paramArrayOfString[i].equals("-noloading")) {
        setLoading(false);
      }
      for (;;)
      {
        i += 1;
        break;
        if (paramArrayOfString[i].equals("-nofilterstack"))
        {
          fgFilterStack = false;
        }
        else
        {
          if (paramArrayOfString[i].equals("-c"))
          {
            if (paramArrayOfString.length > i + 1) {
              str = extractClassName(paramArrayOfString[(i + 1)]);
            }
            for (;;)
            {
              i += 1;
              break;
              System.out.println("Missing Test class name");
            }
          }
          str = paramArrayOfString[i];
        }
      }
    }
    return str;
  }
  
  protected abstract void runFailed(String paramString);
  
  public void setLoading(boolean paramBoolean)
  {
    this.fLoading = paramBoolean;
  }
  
  public void startTest(Test paramTest)
  {
    try
    {
      testStarted(paramTest.toString());
      return;
    }
    finally
    {
      paramTest = finally;
      throw paramTest;
    }
  }
  
  public abstract void testEnded(String paramString);
  
  public abstract void testFailed(int paramInt, Test paramTest, Throwable paramThrowable);
  
  public abstract void testStarted(String paramString);
  
  protected boolean useReloadingTestSuiteLoader()
  {
    return (getPreference("loading").equals("true")) && (this.fLoading);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/runner/BaseTestRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */