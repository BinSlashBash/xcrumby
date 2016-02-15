package org.junit.internal.runners;

import java.lang.annotation.Annotation;
import junit.extensions.TestDecorator;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class JUnit38ClassRunner
  extends Runner
  implements Filterable, Sortable
{
  private Test fTest;
  
  public JUnit38ClassRunner(Class<?> paramClass)
  {
    this(new TestSuite(paramClass.asSubclass(TestCase.class)));
  }
  
  public JUnit38ClassRunner(Test paramTest)
  {
    setTest(paramTest);
  }
  
  private static String createSuiteDescription(TestSuite paramTestSuite)
  {
    int i = paramTestSuite.countTestCases();
    if (i == 0) {}
    for (paramTestSuite = "";; paramTestSuite = String.format(" [example: %s]", new Object[] { paramTestSuite.testAt(0) })) {
      return String.format("TestSuite with %s tests%s", new Object[] { Integer.valueOf(i), paramTestSuite });
    }
  }
  
  private Test getTest()
  {
    return this.fTest;
  }
  
  private static Description makeDescription(Test paramTest)
  {
    if ((paramTest instanceof TestCase))
    {
      paramTest = (TestCase)paramTest;
      paramTest = Description.createTestDescription(paramTest.getClass(), paramTest.getName());
      return paramTest;
    }
    if ((paramTest instanceof TestSuite))
    {
      TestSuite localTestSuite = (TestSuite)paramTest;
      if (localTestSuite.getName() == null) {}
      for (paramTest = createSuiteDescription(localTestSuite);; paramTest = localTestSuite.getName())
      {
        Description localDescription = Description.createSuiteDescription(paramTest, new Annotation[0]);
        int j = localTestSuite.testCount();
        int i = 0;
        for (;;)
        {
          paramTest = localDescription;
          if (i >= j) {
            break;
          }
          localDescription.addChild(makeDescription(localTestSuite.testAt(i)));
          i += 1;
        }
      }
    }
    if ((paramTest instanceof Describable)) {
      return ((Describable)paramTest).getDescription();
    }
    if ((paramTest instanceof TestDecorator)) {
      return makeDescription(((TestDecorator)paramTest).getTest());
    }
    return Description.createSuiteDescription(paramTest.getClass());
  }
  
  private void setTest(Test paramTest)
  {
    this.fTest = paramTest;
  }
  
  public TestListener createAdaptingListener(RunNotifier paramRunNotifier)
  {
    return new OldTestClassAdaptingListener(paramRunNotifier, null);
  }
  
  public void filter(Filter paramFilter)
    throws NoTestsRemainException
  {
    if ((getTest() instanceof Filterable)) {
      ((Filterable)getTest()).filter(paramFilter);
    }
    while (!(getTest() instanceof TestSuite)) {
      return;
    }
    TestSuite localTestSuite1 = (TestSuite)getTest();
    TestSuite localTestSuite2 = new TestSuite(localTestSuite1.getName());
    int j = localTestSuite1.testCount();
    int i = 0;
    while (i < j)
    {
      Test localTest = localTestSuite1.testAt(i);
      if (paramFilter.shouldRun(makeDescription(localTest))) {
        localTestSuite2.addTest(localTest);
      }
      i += 1;
    }
    setTest(localTestSuite2);
  }
  
  public Description getDescription()
  {
    return makeDescription(getTest());
  }
  
  public void run(RunNotifier paramRunNotifier)
  {
    TestResult localTestResult = new TestResult();
    localTestResult.addListener(createAdaptingListener(paramRunNotifier));
    getTest().run(localTestResult);
  }
  
  public void sort(Sorter paramSorter)
  {
    if ((getTest() instanceof Sortable)) {
      ((Sortable)getTest()).sort(paramSorter);
    }
  }
  
  private final class OldTestClassAdaptingListener
    implements TestListener
  {
    private final RunNotifier fNotifier;
    
    private OldTestClassAdaptingListener(RunNotifier paramRunNotifier)
    {
      this.fNotifier = paramRunNotifier;
    }
    
    private Description asDescription(Test paramTest)
    {
      if ((paramTest instanceof Describable)) {
        return ((Describable)paramTest).getDescription();
      }
      return Description.createTestDescription(getEffectiveClass(paramTest), getName(paramTest));
    }
    
    private Class<? extends Test> getEffectiveClass(Test paramTest)
    {
      return paramTest.getClass();
    }
    
    private String getName(Test paramTest)
    {
      if ((paramTest instanceof TestCase)) {
        return ((TestCase)paramTest).getName();
      }
      return paramTest.toString();
    }
    
    public void addError(Test paramTest, Throwable paramThrowable)
    {
      paramTest = new Failure(asDescription(paramTest), paramThrowable);
      this.fNotifier.fireTestFailure(paramTest);
    }
    
    public void addFailure(Test paramTest, AssertionFailedError paramAssertionFailedError)
    {
      addError(paramTest, paramAssertionFailedError);
    }
    
    public void endTest(Test paramTest)
    {
      this.fNotifier.fireTestFinished(asDescription(paramTest));
    }
    
    public void startTest(Test paramTest)
    {
      this.fNotifier.fireTestStarted(asDescription(paramTest));
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/internal/runners/JUnit38ClassRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */