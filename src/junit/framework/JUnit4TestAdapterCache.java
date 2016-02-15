package junit.framework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

public class JUnit4TestAdapterCache
  extends HashMap<Description, Test>
{
  private static final JUnit4TestAdapterCache fInstance = new JUnit4TestAdapterCache();
  private static final long serialVersionUID = 1L;
  
  public static JUnit4TestAdapterCache getDefault()
  {
    return fInstance;
  }
  
  public Test asTest(Description paramDescription)
  {
    if (paramDescription.isSuite()) {
      return createTest(paramDescription);
    }
    if (!containsKey(paramDescription)) {
      put(paramDescription, createTest(paramDescription));
    }
    return (Test)get(paramDescription);
  }
  
  public List<Test> asTestList(Description paramDescription)
  {
    if (paramDescription.isTest())
    {
      paramDescription = Arrays.asList(new Test[] { asTest(paramDescription) });
      return paramDescription;
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramDescription.getChildren().iterator();
    for (;;)
    {
      paramDescription = localArrayList;
      if (!localIterator.hasNext()) {
        break;
      }
      localArrayList.add(asTest((Description)localIterator.next()));
    }
  }
  
  Test createTest(Description paramDescription)
  {
    if (paramDescription.isTest())
    {
      paramDescription = new JUnit4TestCaseFacade(paramDescription);
      return paramDescription;
    }
    TestSuite localTestSuite = new TestSuite(paramDescription.getDisplayName());
    Iterator localIterator = paramDescription.getChildren().iterator();
    for (;;)
    {
      paramDescription = localTestSuite;
      if (!localIterator.hasNext()) {
        break;
      }
      localTestSuite.addTest(asTest((Description)localIterator.next()));
    }
  }
  
  public RunNotifier getNotifier(final TestResult paramTestResult, JUnit4TestAdapter paramJUnit4TestAdapter)
  {
    paramJUnit4TestAdapter = new RunNotifier();
    paramJUnit4TestAdapter.addListener(new RunListener()
    {
      public void testFailure(Failure paramAnonymousFailure)
        throws Exception
      {
        paramTestResult.addError(JUnit4TestAdapterCache.this.asTest(paramAnonymousFailure.getDescription()), paramAnonymousFailure.getException());
      }
      
      public void testFinished(Description paramAnonymousDescription)
        throws Exception
      {
        paramTestResult.endTest(JUnit4TestAdapterCache.this.asTest(paramAnonymousDescription));
      }
      
      public void testStarted(Description paramAnonymousDescription)
        throws Exception
      {
        paramTestResult.startTest(JUnit4TestAdapterCache.this.asTest(paramAnonymousDescription));
      }
    });
    return paramJUnit4TestAdapter;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/junit/framework/JUnit4TestAdapterCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */