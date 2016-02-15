package org.junit.experimental.theories.internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.PotentialAssignment.CouldNotGenerateValueException;
import org.junit.runners.model.TestClass;

public class Assignments
{
  private List<PotentialAssignment> fAssigned;
  private final TestClass fClass;
  private final List<ParameterSignature> fUnassigned;
  
  private Assignments(List<PotentialAssignment> paramList, List<ParameterSignature> paramList1, TestClass paramTestClass)
  {
    this.fUnassigned = paramList1;
    this.fAssigned = paramList;
    this.fClass = paramTestClass;
  }
  
  public static Assignments allUnassigned(Method paramMethod, TestClass paramTestClass)
    throws Exception
  {
    List localList = ParameterSignature.signatures(paramTestClass.getOnlyConstructor());
    localList.addAll(ParameterSignature.signatures(paramMethod));
    return new Assignments(new ArrayList(), localList, paramTestClass);
  }
  
  private int getConstructorParameterCount()
  {
    return ParameterSignature.signatures(this.fClass.getOnlyConstructor()).size();
  }
  
  public Assignments assignNext(PotentialAssignment paramPotentialAssignment)
  {
    ArrayList localArrayList = new ArrayList(this.fAssigned);
    localArrayList.add(paramPotentialAssignment);
    return new Assignments(localArrayList, this.fUnassigned.subList(1, this.fUnassigned.size()), this.fClass);
  }
  
  public Object[] getActualValues(int paramInt1, int paramInt2, boolean paramBoolean)
    throws PotentialAssignment.CouldNotGenerateValueException
  {
    Object[] arrayOfObject = new Object[paramInt2 - paramInt1];
    int i = paramInt1;
    while (i < paramInt2)
    {
      Object localObject = ((PotentialAssignment)this.fAssigned.get(i)).getValue();
      if ((localObject == null) && (!paramBoolean)) {
        throw new PotentialAssignment.CouldNotGenerateValueException();
      }
      arrayOfObject[(i - paramInt1)] = localObject;
      i += 1;
    }
    return arrayOfObject;
  }
  
  public Object[] getAllArguments(boolean paramBoolean)
    throws PotentialAssignment.CouldNotGenerateValueException
  {
    return getActualValues(0, this.fAssigned.size(), paramBoolean);
  }
  
  public ParameterSupplier getAnnotatedSupplier(ParameterSignature paramParameterSignature)
    throws InstantiationException, IllegalAccessException
  {
    paramParameterSignature = (ParametersSuppliedBy)paramParameterSignature.findDeepAnnotation(ParametersSuppliedBy.class);
    if (paramParameterSignature == null) {
      return null;
    }
    return (ParameterSupplier)paramParameterSignature.value().newInstance();
  }
  
  public Object[] getArgumentStrings(boolean paramBoolean)
    throws PotentialAssignment.CouldNotGenerateValueException
  {
    Object[] arrayOfObject = new Object[this.fAssigned.size()];
    int i = 0;
    while (i < arrayOfObject.length)
    {
      arrayOfObject[i] = ((PotentialAssignment)this.fAssigned.get(i)).getDescription();
      i += 1;
    }
    return arrayOfObject;
  }
  
  public Object[] getConstructorArguments(boolean paramBoolean)
    throws PotentialAssignment.CouldNotGenerateValueException
  {
    return getActualValues(0, getConstructorParameterCount(), paramBoolean);
  }
  
  public Object[] getMethodArguments(boolean paramBoolean)
    throws PotentialAssignment.CouldNotGenerateValueException
  {
    return getActualValues(getConstructorParameterCount(), this.fAssigned.size(), paramBoolean);
  }
  
  public ParameterSupplier getSupplier(ParameterSignature paramParameterSignature)
    throws InstantiationException, IllegalAccessException
  {
    paramParameterSignature = getAnnotatedSupplier(paramParameterSignature);
    if (paramParameterSignature != null) {
      return paramParameterSignature;
    }
    return new AllMembersSupplier(this.fClass);
  }
  
  public boolean isComplete()
  {
    return this.fUnassigned.size() == 0;
  }
  
  public ParameterSignature nextUnassigned()
  {
    return (ParameterSignature)this.fUnassigned.get(0);
  }
  
  public List<PotentialAssignment> potentialsForNextUnassigned()
    throws InstantiationException, IllegalAccessException
  {
    ParameterSignature localParameterSignature = nextUnassigned();
    return getSupplier(localParameterSignature).getValueSources(localParameterSignature);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/theories/internal/Assignments.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */