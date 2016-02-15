package org.junit.runner;

import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import junit.framework.Test;
import junit.runner.Version;
import org.junit.internal.JUnitSystem;
import org.junit.internal.RealSystem;
import org.junit.internal.TextListener;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

public class JUnitCore
{
  private final RunNotifier fNotifier = new RunNotifier();
  
  static Computer defaultComputer()
  {
    return new Computer();
  }
  
  public static void main(String... paramVarArgs)
  {
    runMainAndExit(new RealSystem(), paramVarArgs);
  }
  
  public static Result runClasses(Computer paramComputer, Class<?>... paramVarArgs)
  {
    return new JUnitCore().run(paramComputer, paramVarArgs);
  }
  
  public static Result runClasses(Class<?>... paramVarArgs)
  {
    return new JUnitCore().run(defaultComputer(), paramVarArgs);
  }
  
  private Result runMain(JUnitSystem paramJUnitSystem, String... paramVarArgs)
  {
    paramJUnitSystem.out().println("JUnit version " + Version.id());
    ArrayList localArrayList = new ArrayList();
    Object localObject = new ArrayList();
    int j = paramVarArgs.length;
    int i = 0;
    for (;;)
    {
      if (i < j)
      {
        String str = paramVarArgs[i];
        try
        {
          localArrayList.add(Class.forName(str));
          i += 1;
        }
        catch (ClassNotFoundException localClassNotFoundException)
        {
          for (;;)
          {
            paramJUnitSystem.out().println("Could not find class: " + str);
            ((List)localObject).add(new Failure(Description.createSuiteDescription(str, new Annotation[0]), localClassNotFoundException));
          }
        }
      }
    }
    addListener(new TextListener(paramJUnitSystem));
    paramJUnitSystem = run((Class[])localArrayList.toArray(new Class[0]));
    paramVarArgs = ((List)localObject).iterator();
    while (paramVarArgs.hasNext())
    {
      localObject = (Failure)paramVarArgs.next();
      paramJUnitSystem.getFailures().add(localObject);
    }
    return paramJUnitSystem;
  }
  
  private static void runMainAndExit(JUnitSystem paramJUnitSystem, String... paramVarArgs)
  {
    if (new JUnitCore().runMain(paramJUnitSystem, paramVarArgs).wasSuccessful()) {}
    for (int i = 0;; i = 1)
    {
      System.exit(i);
      return;
    }
  }
  
  public void addListener(RunListener paramRunListener)
  {
    this.fNotifier.addListener(paramRunListener);
  }
  
  public String getVersion()
  {
    return Version.id();
  }
  
  public void removeListener(RunListener paramRunListener)
  {
    this.fNotifier.removeListener(paramRunListener);
  }
  
  public Result run(Test paramTest)
  {
    return run(new JUnit38ClassRunner(paramTest));
  }
  
  public Result run(Computer paramComputer, Class<?>... paramVarArgs)
  {
    return run(Request.classes(paramComputer, paramVarArgs));
  }
  
  public Result run(Request paramRequest)
  {
    return run(paramRequest.getRunner());
  }
  
  public Result run(Runner paramRunner)
  {
    Result localResult = new Result();
    RunListener localRunListener = localResult.createListener();
    this.fNotifier.addFirstListener(localRunListener);
    try
    {
      this.fNotifier.fireTestRunStarted(paramRunner.getDescription());
      paramRunner.run(this.fNotifier);
      this.fNotifier.fireTestRunFinished(localResult);
      return localResult;
    }
    finally
    {
      removeListener(localRunListener);
    }
  }
  
  public Result run(Class<?>... paramVarArgs)
  {
    return run(Request.classes(defaultComputer(), paramVarArgs));
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runner/JUnitCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */