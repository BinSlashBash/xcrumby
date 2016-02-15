package org.junit.experimental.theories;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.experimental.theories.internal.Assignments;
import org.junit.experimental.theories.internal.ParameterizedAssertionError;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public class Theories
  extends BlockJUnit4ClassRunner
{
  public Theories(Class<?> paramClass)
    throws InitializationError
  {
    super(paramClass);
  }
  
  private void validateDataPointFields(List<Throwable> paramList)
  {
    Field[] arrayOfField = getTestClass().getJavaClass().getDeclaredFields();
    int j = arrayOfField.length;
    int i = 0;
    if (i < j)
    {
      Field localField = arrayOfField[i];
      if (localField.getAnnotation(DataPoint.class) == null) {}
      for (;;)
      {
        i += 1;
        break;
        if (!Modifier.isStatic(localField.getModifiers())) {
          paramList.add(new Error("DataPoint field " + localField.getName() + " must be static"));
        }
        if (!Modifier.isPublic(localField.getModifiers())) {
          paramList.add(new Error("DataPoint field " + localField.getName() + " must be public"));
        }
      }
    }
  }
  
  protected void collectInitializationErrors(List<Throwable> paramList)
  {
    super.collectInitializationErrors(paramList);
    validateDataPointFields(paramList);
  }
  
  protected List<FrameworkMethod> computeTestMethods()
  {
    List localList1 = super.computeTestMethods();
    List localList2 = getTestClass().getAnnotatedMethods(Theory.class);
    localList1.removeAll(localList2);
    localList1.addAll(localList2);
    return localList1;
  }
  
  public Statement methodBlock(FrameworkMethod paramFrameworkMethod)
  {
    return new TheoryAnchor(paramFrameworkMethod, getTestClass());
  }
  
  protected void validateConstructor(List<Throwable> paramList)
  {
    validateOnlyOneConstructor(paramList);
  }
  
  protected void validateTestMethods(List<Throwable> paramList)
  {
    Iterator localIterator = computeTestMethods().iterator();
    while (localIterator.hasNext())
    {
      FrameworkMethod localFrameworkMethod = (FrameworkMethod)localIterator.next();
      if (localFrameworkMethod.getAnnotation(Theory.class) != null) {
        localFrameworkMethod.validatePublicVoid(false, paramList);
      } else {
        localFrameworkMethod.validatePublicVoidNoArg(false, paramList);
      }
    }
  }
  
  public static class TheoryAnchor
    extends Statement
  {
    private List<AssumptionViolatedException> fInvalidParameters = new ArrayList();
    private TestClass fTestClass;
    private FrameworkMethod fTestMethod;
    private int successes = 0;
    
    public TheoryAnchor(FrameworkMethod paramFrameworkMethod, TestClass paramTestClass)
    {
      this.fTestMethod = paramFrameworkMethod;
      this.fTestClass = paramTestClass;
    }
    
    private TestClass getTestClass()
    {
      return this.fTestClass;
    }
    
    private Statement methodCompletesWithParameters(final FrameworkMethod paramFrameworkMethod, final Assignments paramAssignments, final Object paramObject)
    {
      new Statement()
      {
        public void evaluate()
          throws Throwable
        {
          try
          {
            Object[] arrayOfObject = paramAssignments.getMethodArguments(Theories.TheoryAnchor.this.nullsOk());
            paramFrameworkMethod.invokeExplosively(paramObject, arrayOfObject);
            return;
          }
          catch (PotentialAssignment.CouldNotGenerateValueException localCouldNotGenerateValueException) {}
        }
      };
    }
    
    private boolean nullsOk()
    {
      Theory localTheory = (Theory)this.fTestMethod.getMethod().getAnnotation(Theory.class);
      if (localTheory == null) {
        return false;
      }
      return localTheory.nullsAccepted();
    }
    
    public void evaluate()
      throws Throwable
    {
      runWithAssignment(Assignments.allUnassigned(this.fTestMethod.getMethod(), getTestClass()));
      if (this.successes == 0) {
        Assert.fail("Never found parameters that satisfied method assumptions.  Violated assumptions: " + this.fInvalidParameters);
      }
    }
    
    protected void handleAssumptionViolation(AssumptionViolatedException paramAssumptionViolatedException)
    {
      this.fInvalidParameters.add(paramAssumptionViolatedException);
    }
    
    protected void handleDataPointSuccess()
    {
      this.successes += 1;
    }
    
    protected void reportParameterizedError(Throwable paramThrowable, Object... paramVarArgs)
      throws Throwable
    {
      if (paramVarArgs.length == 0) {
        throw paramThrowable;
      }
      throw new ParameterizedAssertionError(paramThrowable, this.fTestMethod.getName(), paramVarArgs);
    }
    
    protected void runWithAssignment(Assignments paramAssignments)
      throws Throwable
    {
      if (!paramAssignments.isComplete())
      {
        runWithIncompleteAssignment(paramAssignments);
        return;
      }
      runWithCompleteAssignment(paramAssignments);
    }
    
    protected void runWithCompleteAssignment(final Assignments paramAssignments)
      throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, Throwable
    {
      new BlockJUnit4ClassRunner(getTestClass().getJavaClass())
      {
        protected void collectInitializationErrors(List<Throwable> paramAnonymousList) {}
        
        public Object createTest()
          throws Exception
        {
          return getTestClass().getOnlyConstructor().newInstance(paramAssignments.getConstructorArguments(Theories.TheoryAnchor.this.nullsOk()));
        }
        
        public Statement methodBlock(FrameworkMethod paramAnonymousFrameworkMethod)
        {
          new Statement()
          {
            public void evaluate()
              throws Throwable
            {
              try
              {
                this.val$statement.evaluate();
                Theories.TheoryAnchor.this.handleDataPointSuccess();
                return;
              }
              catch (AssumptionViolatedException localAssumptionViolatedException)
              {
                Theories.TheoryAnchor.this.handleAssumptionViolation(localAssumptionViolatedException);
                return;
              }
              catch (Throwable localThrowable)
              {
                Theories.TheoryAnchor.this.reportParameterizedError(localThrowable, Theories.TheoryAnchor.1.this.val$complete.getArgumentStrings(Theories.TheoryAnchor.this.nullsOk()));
              }
            }
          };
        }
        
        protected Statement methodInvoker(FrameworkMethod paramAnonymousFrameworkMethod, Object paramAnonymousObject)
        {
          return Theories.TheoryAnchor.this.methodCompletesWithParameters(paramAnonymousFrameworkMethod, paramAssignments, paramAnonymousObject);
        }
      }.methodBlock(this.fTestMethod).evaluate();
    }
    
    protected void runWithIncompleteAssignment(Assignments paramAssignments)
      throws InstantiationException, IllegalAccessException, Throwable
    {
      Iterator localIterator = paramAssignments.potentialsForNextUnassigned().iterator();
      while (localIterator.hasNext()) {
        runWithAssignment(paramAssignments.assignNext((PotentialAssignment)localIterator.next()));
      }
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/experimental/theories/Theories.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */