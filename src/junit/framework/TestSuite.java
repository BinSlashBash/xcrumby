package junit.framework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.junit.internal.MethodSorter;

public class TestSuite
  implements Test
{
  private String fName;
  private Vector<Test> fTests = new Vector(10);
  
  public TestSuite() {}
  
  public TestSuite(Class<?> paramClass)
  {
    addTestsFromTestCase(paramClass);
  }
  
  public TestSuite(Class<? extends TestCase> paramClass, String paramString)
  {
    this(paramClass);
    setName(paramString);
  }
  
  public TestSuite(String paramString)
  {
    setName(paramString);
  }
  
  public TestSuite(Class<?>... paramVarArgs)
  {
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      addTest(testCaseForClass(paramVarArgs[i]));
      i += 1;
    }
  }
  
  public TestSuite(Class<? extends TestCase>[] paramArrayOfClass, String paramString)
  {
    this(paramArrayOfClass);
    setName(paramString);
  }
  
  private void addTestMethod(Method paramMethod, List<String> paramList, Class<?> paramClass)
  {
    String str = paramMethod.getName();
    if (paramList.contains(str)) {}
    do
    {
      return;
      if (isPublicTestMethod(paramMethod)) {
        break;
      }
    } while (!isTestMethod(paramMethod));
    addTest(warning("Test method isn't public: " + paramMethod.getName() + "(" + paramClass.getCanonicalName() + ")"));
    return;
    paramList.add(str);
    addTest(createTest(paramClass, str));
  }
  
  private void addTestsFromTestCase(Class<?> paramClass)
  {
    this.fName = paramClass.getName();
    do
    {
      try
      {
        getTestConstructor(paramClass);
        if (!Modifier.isPublic(paramClass.getModifiers()))
        {
          addTest(warning("Class " + paramClass.getName() + " is not public"));
          return;
        }
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        addTest(warning("Class " + paramClass.getName() + " has no public constructor TestCase(String name) or TestCase()"));
        return;
      }
      Object localObject = paramClass;
      ArrayList localArrayList = new ArrayList();
      while (Test.class.isAssignableFrom((Class)localObject))
      {
        Method[] arrayOfMethod = MethodSorter.getDeclaredMethods((Class)localObject);
        int j = arrayOfMethod.length;
        int i = 0;
        while (i < j)
        {
          addTestMethod(arrayOfMethod[i], localArrayList, paramClass);
          i += 1;
        }
        localObject = ((Class)localObject).getSuperclass();
      }
    } while (this.fTests.size() != 0);
    addTest(warning("No tests found in " + paramClass.getName()));
  }
  
  /* Error */
  public static Test createTest(Class<?> paramClass, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 116	junit/framework/TestSuite:getTestConstructor	(Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   4: astore_2
    //   5: aload_2
    //   6: invokevirtual 168	java/lang/reflect/Constructor:getParameterTypes	()[Ljava/lang/Class;
    //   9: arraylength
    //   10: ifne +68 -> 78
    //   13: aload_2
    //   14: iconst_0
    //   15: anewarray 4	java/lang/Object
    //   18: invokevirtual 172	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   21: astore_2
    //   22: aload_2
    //   23: astore_0
    //   24: aload_2
    //   25: instanceof 174
    //   28: ifeq +13 -> 41
    //   31: aload_2
    //   32: checkcast 174	junit/framework/TestCase
    //   35: aload_1
    //   36: invokevirtual 175	junit/framework/TestCase:setName	(Ljava/lang/String;)V
    //   39: aload_2
    //   40: astore_0
    //   41: aload_0
    //   42: checkcast 6	junit/framework/Test
    //   45: areturn
    //   46: astore_1
    //   47: new 76	java/lang/StringBuilder
    //   50: dup
    //   51: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   54: ldc -128
    //   56: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: aload_0
    //   60: invokevirtual 110	java/lang/Class:getName	()Ljava/lang/String;
    //   63: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: ldc -124
    //   68: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   74: invokestatic 99	junit/framework/TestSuite:warning	(Ljava/lang/String;)Ljunit/framework/Test;
    //   77: areturn
    //   78: aload_2
    //   79: iconst_1
    //   80: anewarray 4	java/lang/Object
    //   83: dup
    //   84: iconst_0
    //   85: aload_1
    //   86: aastore
    //   87: invokevirtual 172	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   90: astore_0
    //   91: goto -50 -> 41
    //   94: astore_0
    //   95: new 76	java/lang/StringBuilder
    //   98: dup
    //   99: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   102: ldc -79
    //   104: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: aload_1
    //   108: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: ldc -77
    //   113: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: aload_0
    //   117: invokestatic 183	junit/framework/TestSuite:exceptionToString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   120: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: ldc 92
    //   125: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   131: invokestatic 99	junit/framework/TestSuite:warning	(Ljava/lang/String;)Ljunit/framework/Test;
    //   134: areturn
    //   135: astore_0
    //   136: new 76	java/lang/StringBuilder
    //   139: dup
    //   140: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   143: ldc -71
    //   145: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   148: aload_1
    //   149: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   152: ldc -77
    //   154: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: aload_0
    //   158: invokevirtual 189	java/lang/reflect/InvocationTargetException:getTargetException	()Ljava/lang/Throwable;
    //   161: invokestatic 183	junit/framework/TestSuite:exceptionToString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   164: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: ldc 92
    //   169: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   172: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   175: invokestatic 99	junit/framework/TestSuite:warning	(Ljava/lang/String;)Ljunit/framework/Test;
    //   178: areturn
    //   179: astore_0
    //   180: new 76	java/lang/StringBuilder
    //   183: dup
    //   184: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   187: ldc -65
    //   189: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: aload_1
    //   193: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   196: ldc -77
    //   198: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   201: aload_0
    //   202: invokestatic 183	junit/framework/TestSuite:exceptionToString	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   205: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: ldc 92
    //   210: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   213: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   216: invokestatic 99	junit/framework/TestSuite:warning	(Ljava/lang/String;)Ljunit/framework/Test;
    //   219: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	220	0	paramClass	Class<?>
    //   0	220	1	paramString	String
    //   4	75	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	5	46	java/lang/NoSuchMethodException
    //   5	22	94	java/lang/InstantiationException
    //   24	39	94	java/lang/InstantiationException
    //   78	91	94	java/lang/InstantiationException
    //   5	22	135	java/lang/reflect/InvocationTargetException
    //   24	39	135	java/lang/reflect/InvocationTargetException
    //   78	91	135	java/lang/reflect/InvocationTargetException
    //   5	22	179	java/lang/IllegalAccessException
    //   24	39	179	java/lang/IllegalAccessException
    //   78	91	179	java/lang/IllegalAccessException
  }
  
  private static String exceptionToString(Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    paramThrowable.printStackTrace(new PrintWriter(localStringWriter));
    return localStringWriter.toString();
  }
  
  public static Constructor<?> getTestConstructor(Class<?> paramClass)
    throws NoSuchMethodException
  {
    try
    {
      Constructor localConstructor = paramClass.getConstructor(new Class[] { String.class });
      return localConstructor;
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}
    return paramClass.getConstructor(new Class[0]);
  }
  
  private boolean isPublicTestMethod(Method paramMethod)
  {
    return (isTestMethod(paramMethod)) && (Modifier.isPublic(paramMethod.getModifiers()));
  }
  
  private boolean isTestMethod(Method paramMethod)
  {
    return (paramMethod.getParameterTypes().length == 0) && (paramMethod.getName().startsWith("test")) && (paramMethod.getReturnType().equals(Void.TYPE));
  }
  
  private Test testCaseForClass(Class<?> paramClass)
  {
    if (TestCase.class.isAssignableFrom(paramClass)) {
      return new TestSuite(paramClass.asSubclass(TestCase.class));
    }
    return warning(paramClass.getCanonicalName() + " does not extend TestCase");
  }
  
  public static Test warning(final String paramString)
  {
    new TestCase("warning")
    {
      protected void runTest()
      {
        fail(paramString);
      }
    };
  }
  
  public void addTest(Test paramTest)
  {
    this.fTests.add(paramTest);
  }
  
  public void addTestSuite(Class<? extends TestCase> paramClass)
  {
    addTest(new TestSuite(paramClass));
  }
  
  public int countTestCases()
  {
    int i = 0;
    Iterator localIterator = this.fTests.iterator();
    while (localIterator.hasNext()) {
      i += ((Test)localIterator.next()).countTestCases();
    }
    return i;
  }
  
  public String getName()
  {
    return this.fName;
  }
  
  public void run(TestResult paramTestResult)
  {
    Iterator localIterator = this.fTests.iterator();
    for (;;)
    {
      Test localTest;
      if (localIterator.hasNext())
      {
        localTest = (Test)localIterator.next();
        if (!paramTestResult.shouldStop()) {}
      }
      else
      {
        return;
      }
      runTest(localTest, paramTestResult);
    }
  }
  
  public void runTest(Test paramTest, TestResult paramTestResult)
  {
    paramTest.run(paramTestResult);
  }
  
  public void setName(String paramString)
  {
    this.fName = paramString;
  }
  
  public Test testAt(int paramInt)
  {
    return (Test)this.fTests.get(paramInt);
  }
  
  public int testCount()
  {
    return this.fTests.size();
  }
  
  public Enumeration<Test> tests()
  {
    return this.fTests.elements();
  }
  
  public String toString()
  {
    if (getName() != null) {
      return getName();
    }
    return super.toString();
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/framework/TestSuite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */