package org.junit.runner;

import java.util.Comparator;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.internal.requests.ClassRequest;
import org.junit.internal.requests.FilterRequest;
import org.junit.internal.requests.SortingRequest;
import org.junit.internal.runners.ErrorReportingRunner;
import org.junit.runner.manipulation.Filter;
import org.junit.runners.model.InitializationError;

public abstract class Request
{
  public static Request aClass(Class<?> paramClass)
  {
    return new ClassRequest(paramClass);
  }
  
  public static Request classWithoutSuiteMethod(Class<?> paramClass)
  {
    return new ClassRequest(paramClass, false);
  }
  
  public static Request classes(Computer paramComputer, Class<?>... paramVarArgs)
  {
    try
    {
      paramComputer = runner(paramComputer.getSuite(new AllDefaultPossibilitiesBuilder(true), paramVarArgs));
      return paramComputer;
    }
    catch (InitializationError paramComputer)
    {
      throw new RuntimeException("Bug in saff's brain: Suite constructor, called as above, should always complete");
    }
  }
  
  public static Request classes(Class<?>... paramVarArgs)
  {
    return classes(JUnitCore.defaultComputer(), paramVarArgs);
  }
  
  @Deprecated
  public static Request errorReport(Class<?> paramClass, Throwable paramThrowable)
  {
    return runner(new ErrorReportingRunner(paramClass, paramThrowable));
  }
  
  public static Request method(Class<?> paramClass, String paramString)
  {
    paramString = Description.createTestDescription(paramClass, paramString);
    return aClass(paramClass).filterWith(paramString);
  }
  
  public static Request runner(Runner paramRunner)
  {
    new Request()
    {
      public Runner getRunner()
      {
        return this.val$runner;
      }
    };
  }
  
  public Request filterWith(Description paramDescription)
  {
    return filterWith(Filter.matchMethodDescription(paramDescription));
  }
  
  public Request filterWith(Filter paramFilter)
  {
    return new FilterRequest(this, paramFilter);
  }
  
  public abstract Runner getRunner();
  
  public Request sortWith(Comparator<Description> paramComparator)
  {
    return new SortingRequest(this, paramComparator);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/junit/runner/Request.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */