package org.junit.experimental.theories.internal;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.PotentialAssignment.CouldNotGenerateValueException;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class AllMembersSupplier
  extends ParameterSupplier
{
  private final TestClass fClass;
  
  public AllMembersSupplier(TestClass paramTestClass)
  {
    this.fClass = paramTestClass;
  }
  
  private void addArrayValues(String paramString, List<PotentialAssignment> paramList, Object paramObject)
  {
    int i = 0;
    while (i < Array.getLength(paramObject))
    {
      paramList.add(PotentialAssignment.forValue(paramString + "[" + i + "]", Array.get(paramObject, i)));
      i += 1;
    }
  }
  
  private void addFields(ParameterSignature paramParameterSignature, List<PotentialAssignment> paramList)
  {
    Field[] arrayOfField = this.fClass.getJavaClass().getFields();
    int j = arrayOfField.length;
    int i = 0;
    for (;;)
    {
      Field localField;
      Class localClass;
      if (i < j)
      {
        localField = arrayOfField[i];
        if (Modifier.isStatic(localField.getModifiers()))
        {
          localClass = localField.getType();
          if ((!paramParameterSignature.canAcceptArrayType(localClass)) || (localField.getAnnotation(DataPoints.class) == null)) {
            break label91;
          }
        }
      }
      try
      {
        addArrayValues(localField.getName(), paramList, getStaticFieldValue(localField));
        for (;;)
        {
          i += 1;
          break;
          label91:
          if ((paramParameterSignature.canAcceptType(localClass)) && (localField.getAnnotation(DataPoint.class) != null)) {
            paramList.add(PotentialAssignment.forValue(localField.getName(), getStaticFieldValue(localField)));
          }
        }
        return;
      }
      catch (Throwable localThrowable)
      {
        for (;;) {}
      }
    }
  }
  
  private void addMultiPointArrayValues(ParameterSignature paramParameterSignature, String paramString, List<PotentialAssignment> paramList, Object paramObject)
    throws Throwable
  {
    int i = 0;
    for (;;)
    {
      if ((i >= Array.getLength(paramObject)) || (!isCorrectlyTyped(paramParameterSignature, Array.get(paramObject, i).getClass()))) {
        return;
      }
      paramList.add(PotentialAssignment.forValue(paramString + "[" + i + "]", Array.get(paramObject, i)));
      i += 1;
    }
  }
  
  private void addMultiPointMethods(ParameterSignature paramParameterSignature, List<PotentialAssignment> paramList)
  {
    Iterator localIterator = this.fClass.getAnnotatedMethods(DataPoints.class).iterator();
    while (localIterator.hasNext())
    {
      FrameworkMethod localFrameworkMethod = (FrameworkMethod)localIterator.next();
      try
      {
        addMultiPointArrayValues(paramParameterSignature, localFrameworkMethod.getName(), paramList, localFrameworkMethod.invokeExplosively(null, new Object[0]));
      }
      catch (Throwable localThrowable) {}
    }
  }
  
  private void addSinglePointMethods(ParameterSignature paramParameterSignature, List<PotentialAssignment> paramList)
  {
    Iterator localIterator = this.fClass.getAnnotatedMethods(DataPoint.class).iterator();
    while (localIterator.hasNext())
    {
      FrameworkMethod localFrameworkMethod = (FrameworkMethod)localIterator.next();
      if (isCorrectlyTyped(paramParameterSignature, localFrameworkMethod.getType())) {
        paramList.add(new MethodParameterValue(localFrameworkMethod, null));
      }
    }
  }
  
  private Object getStaticFieldValue(Field paramField)
  {
    try
    {
      paramField = paramField.get(null);
      return paramField;
    }
    catch (IllegalArgumentException paramField)
    {
      throw new RuntimeException("unexpected: field from getClass doesn't exist on object");
    }
    catch (IllegalAccessException paramField)
    {
      throw new RuntimeException("unexpected: getFields returned an inaccessible field");
    }
  }
  
  private boolean isCorrectlyTyped(ParameterSignature paramParameterSignature, Class<?> paramClass)
  {
    return paramParameterSignature.canAcceptType(paramClass);
  }
  
  public List<PotentialAssignment> getValueSources(ParameterSignature paramParameterSignature)
  {
    ArrayList localArrayList = new ArrayList();
    addFields(paramParameterSignature, localArrayList);
    addSinglePointMethods(paramParameterSignature, localArrayList);
    addMultiPointMethods(paramParameterSignature, localArrayList);
    return localArrayList;
  }
  
  static class MethodParameterValue
    extends PotentialAssignment
  {
    private final FrameworkMethod fMethod;
    
    private MethodParameterValue(FrameworkMethod paramFrameworkMethod)
    {
      this.fMethod = paramFrameworkMethod;
    }
    
    public String getDescription()
      throws PotentialAssignment.CouldNotGenerateValueException
    {
      return this.fMethod.getName();
    }
    
    public Object getValue()
      throws PotentialAssignment.CouldNotGenerateValueException
    {
      try
      {
        Object localObject = this.fMethod.invokeExplosively(null, new Object[0]);
        return localObject;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw new RuntimeException("unexpected: argument length is checked");
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new RuntimeException("unexpected: getMethods returned an inaccessible method");
      }
      catch (Throwable localThrowable)
      {
        throw new PotentialAssignment.CouldNotGenerateValueException();
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/theories/internal/AllMembersSupplier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */