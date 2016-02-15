package org.junit.runners;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Test.None;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.rules.RuleFieldValidator;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.internal.runners.statements.Fail;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.MethodRule;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public class BlockJUnit4ClassRunner
  extends ParentRunner<FrameworkMethod>
{
  public BlockJUnit4ClassRunner(Class<?> paramClass)
    throws InitializationError
  {
    super(paramClass);
  }
  
  private boolean expectsException(Test paramTest)
  {
    return getExpectedException(paramTest) != null;
  }
  
  private Class<? extends Throwable> getExpectedException(Test paramTest)
  {
    if ((paramTest == null) || (paramTest.expected() == Test.None.class)) {
      return null;
    }
    return paramTest.expected();
  }
  
  private List<MethodRule> getMethodRules(Object paramObject)
  {
    return rules(paramObject);
  }
  
  private long getTimeout(Test paramTest)
  {
    if (paramTest == null) {
      return 0L;
    }
    return paramTest.timeout();
  }
  
  private boolean hasOneConstructor()
  {
    return getTestClass().getJavaClass().getConstructors().length == 1;
  }
  
  private void validateMethods(List<Throwable> paramList)
  {
    RuleFieldValidator.RULE_METHOD_VALIDATOR.validate(getTestClass(), paramList);
  }
  
  private Statement withMethodRules(FrameworkMethod paramFrameworkMethod, List<TestRule> paramList, Object paramObject, Statement paramStatement)
  {
    Iterator localIterator = getMethodRules(paramObject).iterator();
    while (localIterator.hasNext())
    {
      MethodRule localMethodRule = (MethodRule)localIterator.next();
      if (!paramList.contains(localMethodRule)) {
        paramStatement = localMethodRule.apply(paramStatement, paramFrameworkMethod, paramObject);
      }
    }
    return paramStatement;
  }
  
  private Statement withRules(FrameworkMethod paramFrameworkMethod, Object paramObject, Statement paramStatement)
  {
    List localList = getTestRules(paramObject);
    return withTestRules(paramFrameworkMethod, localList, withMethodRules(paramFrameworkMethod, localList, paramObject, paramStatement));
  }
  
  private Statement withTestRules(FrameworkMethod paramFrameworkMethod, List<TestRule> paramList, Statement paramStatement)
  {
    if (paramList.isEmpty()) {
      return paramStatement;
    }
    return new RunRules(paramStatement, paramList, describeChild(paramFrameworkMethod));
  }
  
  protected void collectInitializationErrors(List<Throwable> paramList)
  {
    super.collectInitializationErrors(paramList);
    validateNoNonStaticInnerClass(paramList);
    validateConstructor(paramList);
    validateInstanceMethods(paramList);
    validateFields(paramList);
    validateMethods(paramList);
  }
  
  protected List<FrameworkMethod> computeTestMethods()
  {
    return getTestClass().getAnnotatedMethods(Test.class);
  }
  
  protected Object createTest()
    throws Exception
  {
    return getTestClass().getOnlyConstructor().newInstance(new Object[0]);
  }
  
  protected Description describeChild(FrameworkMethod paramFrameworkMethod)
  {
    return Description.createTestDescription(getTestClass().getJavaClass(), testName(paramFrameworkMethod), paramFrameworkMethod.getAnnotations());
  }
  
  protected List<FrameworkMethod> getChildren()
  {
    return computeTestMethods();
  }
  
  protected List<TestRule> getTestRules(Object paramObject)
  {
    List localList = getTestClass().getAnnotatedMethodValues(paramObject, Rule.class, TestRule.class);
    localList.addAll(getTestClass().getAnnotatedFieldValues(paramObject, Rule.class, TestRule.class));
    return localList;
  }
  
  protected Statement methodBlock(FrameworkMethod paramFrameworkMethod)
  {
    try
    {
      Object localObject = new ReflectiveCallable()
      {
        protected Object runReflectiveCall()
          throws Throwable
        {
          return BlockJUnit4ClassRunner.this.createTest();
        }
      }.run();
      return withRules(paramFrameworkMethod, localObject, withAfters(paramFrameworkMethod, localObject, withBefores(paramFrameworkMethod, localObject, withPotentialTimeout(paramFrameworkMethod, localObject, possiblyExpectingExceptions(paramFrameworkMethod, localObject, methodInvoker(paramFrameworkMethod, localObject))))));
    }
    catch (Throwable paramFrameworkMethod) {}
    return new Fail(paramFrameworkMethod);
  }
  
  protected Statement methodInvoker(FrameworkMethod paramFrameworkMethod, Object paramObject)
  {
    return new InvokeMethod(paramFrameworkMethod, paramObject);
  }
  
  @Deprecated
  protected Statement possiblyExpectingExceptions(FrameworkMethod paramFrameworkMethod, Object paramObject, Statement paramStatement)
  {
    paramObject = (Test)paramFrameworkMethod.getAnnotation(Test.class);
    paramFrameworkMethod = paramStatement;
    if (expectsException((Test)paramObject)) {
      paramFrameworkMethod = new ExpectException(paramStatement, getExpectedException((Test)paramObject));
    }
    return paramFrameworkMethod;
  }
  
  protected List<MethodRule> rules(Object paramObject)
  {
    return getTestClass().getAnnotatedFieldValues(paramObject, Rule.class, MethodRule.class);
  }
  
  protected void runChild(FrameworkMethod paramFrameworkMethod, RunNotifier paramRunNotifier)
  {
    Description localDescription = describeChild(paramFrameworkMethod);
    if (paramFrameworkMethod.getAnnotation(Ignore.class) != null)
    {
      paramRunNotifier.fireTestIgnored(localDescription);
      return;
    }
    runLeaf(methodBlock(paramFrameworkMethod), localDescription, paramRunNotifier);
  }
  
  protected String testName(FrameworkMethod paramFrameworkMethod)
  {
    return paramFrameworkMethod.getName();
  }
  
  protected void validateConstructor(List<Throwable> paramList)
  {
    validateOnlyOneConstructor(paramList);
    validateZeroArgConstructor(paramList);
  }
  
  protected void validateFields(List<Throwable> paramList)
  {
    RuleFieldValidator.RULE_VALIDATOR.validate(getTestClass(), paramList);
  }
  
  @Deprecated
  protected void validateInstanceMethods(List<Throwable> paramList)
  {
    validatePublicVoidNoArgMethods(After.class, false, paramList);
    validatePublicVoidNoArgMethods(Before.class, false, paramList);
    validateTestMethods(paramList);
    if (computeTestMethods().size() == 0) {
      paramList.add(new Exception("No runnable methods"));
    }
  }
  
  protected void validateNoNonStaticInnerClass(List<Throwable> paramList)
  {
    if (getTestClass().isANonStaticInnerClass()) {
      paramList.add(new Exception("The inner class " + getTestClass().getName() + " is not static."));
    }
  }
  
  protected void validateOnlyOneConstructor(List<Throwable> paramList)
  {
    if (!hasOneConstructor()) {
      paramList.add(new Exception("Test class should have exactly one public constructor"));
    }
  }
  
  protected void validateTestMethods(List<Throwable> paramList)
  {
    validatePublicVoidNoArgMethods(Test.class, false, paramList);
  }
  
  protected void validateZeroArgConstructor(List<Throwable> paramList)
  {
    if ((!getTestClass().isANonStaticInnerClass()) && (hasOneConstructor()) && (getTestClass().getOnlyConstructor().getParameterTypes().length != 0)) {
      paramList.add(new Exception("Test class should have exactly one public zero-argument constructor"));
    }
  }
  
  @Deprecated
  protected Statement withAfters(FrameworkMethod paramFrameworkMethod, Object paramObject, Statement paramStatement)
  {
    paramFrameworkMethod = getTestClass().getAnnotatedMethods(After.class);
    if (paramFrameworkMethod.isEmpty()) {
      return paramStatement;
    }
    return new RunAfters(paramStatement, paramFrameworkMethod, paramObject);
  }
  
  @Deprecated
  protected Statement withBefores(FrameworkMethod paramFrameworkMethod, Object paramObject, Statement paramStatement)
  {
    paramFrameworkMethod = getTestClass().getAnnotatedMethods(Before.class);
    if (paramFrameworkMethod.isEmpty()) {
      return paramStatement;
    }
    return new RunBefores(paramStatement, paramFrameworkMethod, paramObject);
  }
  
  @Deprecated
  protected Statement withPotentialTimeout(FrameworkMethod paramFrameworkMethod, Object paramObject, Statement paramStatement)
  {
    long l = getTimeout((Test)paramFrameworkMethod.getAnnotation(Test.class));
    paramFrameworkMethod = paramStatement;
    if (l > 0L) {
      paramFrameworkMethod = new FailOnTimeout(paramStatement, l);
    }
    return paramFrameworkMethod;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runners/BlockJUnit4ClassRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */