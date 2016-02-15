package org.junit.runners;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public class Parameterized
  extends Suite
{
  private static final List<Runner> NO_RUNNERS = ;
  private final ArrayList<Runner> runners = new ArrayList();
  
  public Parameterized(Class<?> paramClass)
    throws Throwable
  {
    super(paramClass, NO_RUNNERS);
    paramClass = (Parameters)getParametersMethod().getAnnotation(Parameters.class);
    createRunnersForParameters(allParameters(), paramClass.name());
  }
  
  private Iterable<Object[]> allParameters()
    throws Throwable
  {
    Object localObject = getParametersMethod().invokeExplosively(null, new Object[0]);
    if ((localObject instanceof Iterable)) {
      return (Iterable)localObject;
    }
    throw parametersMethodReturnedWrongType();
  }
  
  private void createRunnersForParameters(Iterable<Object[]> paramIterable, String paramString)
    throws InitializationError, Exception
  {
    int i = 0;
    try
    {
      paramIterable = paramIterable.iterator();
      while (paramIterable.hasNext())
      {
        Object localObject = (Object[])paramIterable.next();
        String str = nameFor(paramString, i, (Object[])localObject);
        localObject = new TestClassRunnerForParameters(getTestClass().getJavaClass(), (Object[])localObject, str);
        this.runners.add(localObject);
        i += 1;
      }
      return;
    }
    catch (ClassCastException paramIterable)
    {
      throw parametersMethodReturnedWrongType();
    }
  }
  
  private boolean fieldsAreAnnotated()
  {
    return !getAnnotatedFieldsByParameter().isEmpty();
  }
  
  private List<FrameworkField> getAnnotatedFieldsByParameter()
  {
    return getTestClass().getAnnotatedFields(Parameter.class);
  }
  
  private FrameworkMethod getParametersMethod()
    throws Exception
  {
    Iterator localIterator = getTestClass().getAnnotatedMethods(Parameters.class).iterator();
    while (localIterator.hasNext())
    {
      FrameworkMethod localFrameworkMethod = (FrameworkMethod)localIterator.next();
      if ((localFrameworkMethod.isStatic()) && (localFrameworkMethod.isPublic())) {
        return localFrameworkMethod;
      }
    }
    throw new Exception("No public static parameters method on class " + getTestClass().getName());
  }
  
  private String nameFor(String paramString, int paramInt, Object[] paramArrayOfObject)
  {
    paramString = MessageFormat.format(paramString.replaceAll("\\{index\\}", Integer.toString(paramInt)), paramArrayOfObject);
    return "[" + paramString + "]";
  }
  
  private Exception parametersMethodReturnedWrongType()
    throws Exception
  {
    return new Exception(MessageFormat.format("{0}.{1}() must return an Iterable of arrays.", new Object[] { getTestClass().getName(), getParametersMethod().getName() }));
  }
  
  protected List<Runner> getChildren()
  {
    return this.runners;
  }
  
  @Retention(RetentionPolicy.RUNTIME)
  @Target({java.lang.annotation.ElementType.FIELD})
  public static @interface Parameter
  {
    int value() default 0;
  }
  
  @Retention(RetentionPolicy.RUNTIME)
  @Target({java.lang.annotation.ElementType.METHOD})
  public static @interface Parameters
  {
    String name() default "{index}";
  }
  
  private class TestClassRunnerForParameters
    extends BlockJUnit4ClassRunner
  {
    private final String fName;
    private final Object[] fParameters;
    
    TestClassRunnerForParameters(Object[] paramArrayOfObject, String paramString)
      throws InitializationError
    {
      super();
      this.fParameters = paramString;
      String str;
      this.fName = str;
    }
    
    private Object createTestUsingConstructorInjection()
      throws Exception
    {
      return getTestClass().getOnlyConstructor().newInstance(this.fParameters);
    }
    
    private Object createTestUsingFieldInjection()
      throws Exception
    {
      Object localObject2 = Parameterized.this.getAnnotatedFieldsByParameter();
      if (((List)localObject2).size() != this.fParameters.length) {
        throw new Exception("Wrong number of parameters and @Parameter fields. @Parameter fields counted: " + ((List)localObject2).size() + ", available parameters: " + this.fParameters.length + ".");
      }
      Object localObject1 = getTestClass().getJavaClass().newInstance();
      Iterator localIterator = ((List)localObject2).iterator();
      while (localIterator.hasNext())
      {
        localObject2 = ((FrameworkField)localIterator.next()).getField();
        int i = ((Parameterized.Parameter)((Field)localObject2).getAnnotation(Parameterized.Parameter.class)).value();
        try
        {
          ((Field)localObject2).set(localObject1, this.fParameters[i]);
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          throw new Exception(getTestClass().getName() + ": Trying to set " + ((Field)localObject2).getName() + " with the value " + this.fParameters[i] + " that is not the right type (" + this.fParameters[i].getClass().getSimpleName() + " instead of " + ((Field)localObject2).getType().getSimpleName() + ").", localIllegalArgumentException);
        }
      }
      return localIllegalArgumentException;
    }
    
    protected Statement classBlock(RunNotifier paramRunNotifier)
    {
      return childrenInvoker(paramRunNotifier);
    }
    
    public Object createTest()
      throws Exception
    {
      if (Parameterized.this.fieldsAreAnnotated()) {
        return createTestUsingFieldInjection();
      }
      return createTestUsingConstructorInjection();
    }
    
    protected String getName()
    {
      return this.fName;
    }
    
    protected Annotation[] getRunnerAnnotations()
    {
      return new Annotation[0];
    }
    
    protected String testName(FrameworkMethod paramFrameworkMethod)
    {
      return paramFrameworkMethod.getName() + getName();
    }
    
    protected void validateConstructor(List<Throwable> paramList)
    {
      validateOnlyOneConstructor(paramList);
      if (Parameterized.this.fieldsAreAnnotated()) {
        validateZeroArgConstructor(paramList);
      }
    }
    
    protected void validateFields(List<Throwable> paramList)
    {
      super.validateFields(paramList);
      if (Parameterized.this.fieldsAreAnnotated())
      {
        List localList = Parameterized.this.getAnnotatedFieldsByParameter();
        int[] arrayOfInt = new int[localList.size()];
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
        {
          i = ((Parameterized.Parameter)((FrameworkField)localIterator.next()).getField().getAnnotation(Parameterized.Parameter.class)).value();
          if ((i < 0) || (i > localList.size() - 1)) {
            paramList.add(new Exception("Invalid @Parameter value: " + i + ". @Parameter fields counted: " + localList.size() + ". Please use an index between 0 and " + (localList.size() - 1) + "."));
          } else {
            arrayOfInt[i] += 1;
          }
        }
        int i = 0;
        if (i < arrayOfInt.length)
        {
          int j = arrayOfInt[i];
          if (j == 0) {
            paramList.add(new Exception("@Parameter(" + i + ") is never used."));
          }
          for (;;)
          {
            i += 1;
            break;
            if (j > 1) {
              paramList.add(new Exception("@Parameter(" + i + ") is used more than once (" + j + ")."));
            }
          }
        }
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runners/Parameterized.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */