package org.junit.runners;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.internal.runners.rules.RuleFieldValidator;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerScheduler;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public abstract class ParentRunner<T>
  extends Runner
  implements Filterable, Sortable
{
  private List<T> fFilteredChildren = null;
  private RunnerScheduler fScheduler = new RunnerScheduler()
  {
    public void finished() {}
    
    public void schedule(Runnable paramAnonymousRunnable)
    {
      paramAnonymousRunnable.run();
    }
  };
  private Sorter fSorter = Sorter.NULL;
  private final TestClass fTestClass;
  
  protected ParentRunner(Class<?> paramClass)
    throws InitializationError
  {
    this.fTestClass = new TestClass(paramClass);
    validate();
  }
  
  private Comparator<? super T> comparator()
  {
    new Comparator()
    {
      public int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        return ParentRunner.this.fSorter.compare(ParentRunner.this.describeChild(paramAnonymousT1), ParentRunner.this.describeChild(paramAnonymousT2));
      }
    };
  }
  
  private List<T> getFilteredChildren()
  {
    if (this.fFilteredChildren == null) {
      this.fFilteredChildren = new ArrayList(getChildren());
    }
    return this.fFilteredChildren;
  }
  
  private void runChildren(final RunNotifier paramRunNotifier)
  {
    Iterator localIterator = getFilteredChildren().iterator();
    while (localIterator.hasNext())
    {
      final Object localObject = localIterator.next();
      this.fScheduler.schedule(new Runnable()
      {
        public void run()
        {
          ParentRunner.this.runChild(localObject, paramRunNotifier);
        }
      });
    }
    this.fScheduler.finished();
  }
  
  private boolean shouldRun(Filter paramFilter, T paramT)
  {
    return paramFilter.shouldRun(describeChild(paramT));
  }
  
  private void sortChild(T paramT)
  {
    this.fSorter.apply(paramT);
  }
  
  private void validate()
    throws InitializationError
  {
    ArrayList localArrayList = new ArrayList();
    collectInitializationErrors(localArrayList);
    if (!localArrayList.isEmpty()) {
      throw new InitializationError(localArrayList);
    }
  }
  
  private void validateClassRules(List<Throwable> paramList)
  {
    RuleFieldValidator.CLASS_RULE_VALIDATOR.validate(getTestClass(), paramList);
    RuleFieldValidator.CLASS_RULE_METHOD_VALIDATOR.validate(getTestClass(), paramList);
  }
  
  private Statement withClassRules(Statement paramStatement)
  {
    List localList = classRules();
    if (localList.isEmpty()) {
      return paramStatement;
    }
    return new RunRules(paramStatement, localList, getDescription());
  }
  
  protected Statement childrenInvoker(final RunNotifier paramRunNotifier)
  {
    new Statement()
    {
      public void evaluate()
      {
        ParentRunner.this.runChildren(paramRunNotifier);
      }
    };
  }
  
  protected Statement classBlock(RunNotifier paramRunNotifier)
  {
    return withClassRules(withAfterClasses(withBeforeClasses(childrenInvoker(paramRunNotifier))));
  }
  
  protected List<TestRule> classRules()
  {
    List localList = this.fTestClass.getAnnotatedMethodValues(null, ClassRule.class, TestRule.class);
    localList.addAll(this.fTestClass.getAnnotatedFieldValues(null, ClassRule.class, TestRule.class));
    return localList;
  }
  
  protected void collectInitializationErrors(List<Throwable> paramList)
  {
    validatePublicVoidNoArgMethods(BeforeClass.class, true, paramList);
    validatePublicVoidNoArgMethods(AfterClass.class, true, paramList);
    validateClassRules(paramList);
  }
  
  protected abstract Description describeChild(T paramT);
  
  public void filter(Filter paramFilter)
    throws NoTestsRemainException
  {
    Iterator localIterator = getFilteredChildren().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if (shouldRun(paramFilter, localObject)) {
        try
        {
          paramFilter.apply(localObject);
        }
        catch (NoTestsRemainException localNoTestsRemainException)
        {
          localIterator.remove();
        }
      } else {
        localIterator.remove();
      }
    }
    if (getFilteredChildren().isEmpty()) {
      throw new NoTestsRemainException();
    }
  }
  
  protected abstract List<T> getChildren();
  
  public Description getDescription()
  {
    Description localDescription = Description.createSuiteDescription(getName(), getRunnerAnnotations());
    Iterator localIterator = getFilteredChildren().iterator();
    while (localIterator.hasNext()) {
      localDescription.addChild(describeChild(localIterator.next()));
    }
    return localDescription;
  }
  
  protected String getName()
  {
    return this.fTestClass.getName();
  }
  
  protected Annotation[] getRunnerAnnotations()
  {
    return this.fTestClass.getAnnotations();
  }
  
  public final TestClass getTestClass()
  {
    return this.fTestClass;
  }
  
  public void run(RunNotifier paramRunNotifier)
  {
    EachTestNotifier localEachTestNotifier = new EachTestNotifier(paramRunNotifier, getDescription());
    try
    {
      classBlock(paramRunNotifier).evaluate();
      return;
    }
    catch (AssumptionViolatedException paramRunNotifier)
    {
      localEachTestNotifier.fireTestIgnored();
      return;
    }
    catch (StoppedByUserException paramRunNotifier)
    {
      throw paramRunNotifier;
    }
    catch (Throwable paramRunNotifier)
    {
      localEachTestNotifier.addFailure(paramRunNotifier);
    }
  }
  
  protected abstract void runChild(T paramT, RunNotifier paramRunNotifier);
  
  protected final void runLeaf(Statement paramStatement, Description paramDescription, RunNotifier paramRunNotifier)
  {
    paramDescription = new EachTestNotifier(paramRunNotifier, paramDescription);
    paramDescription.fireTestStarted();
    try
    {
      paramStatement.evaluate();
      return;
    }
    catch (AssumptionViolatedException paramStatement)
    {
      paramDescription.addFailedAssumption(paramStatement);
      return;
    }
    catch (Throwable paramStatement)
    {
      paramDescription.addFailure(paramStatement);
      return;
    }
    finally
    {
      paramDescription.fireTestFinished();
    }
  }
  
  public void setScheduler(RunnerScheduler paramRunnerScheduler)
  {
    this.fScheduler = paramRunnerScheduler;
  }
  
  public void sort(Sorter paramSorter)
  {
    this.fSorter = paramSorter;
    paramSorter = getFilteredChildren().iterator();
    while (paramSorter.hasNext()) {
      sortChild(paramSorter.next());
    }
    Collections.sort(getFilteredChildren(), comparator());
  }
  
  protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> paramClass, boolean paramBoolean, List<Throwable> paramList)
  {
    paramClass = getTestClass().getAnnotatedMethods(paramClass).iterator();
    while (paramClass.hasNext()) {
      ((FrameworkMethod)paramClass.next()).validatePublicVoidNoArg(paramBoolean, paramList);
    }
  }
  
  protected Statement withAfterClasses(Statement paramStatement)
  {
    List localList = this.fTestClass.getAnnotatedMethods(AfterClass.class);
    if (localList.isEmpty()) {
      return paramStatement;
    }
    return new RunAfters(paramStatement, localList, null);
  }
  
  protected Statement withBeforeClasses(Statement paramStatement)
  {
    List localList = this.fTestClass.getAnnotatedMethods(BeforeClass.class);
    if (localList.isEmpty()) {
      return paramStatement;
    }
    return new RunBefores(paramStatement, localList, null);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runners/ParentRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */