package org.hamcrest.object;

import java.util.EventObject;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class IsEventFrom
  extends TypeSafeDiagnosingMatcher<EventObject>
{
  private final Class<?> eventClass;
  private final Object source;
  
  public IsEventFrom(Class<?> paramClass, Object paramObject)
  {
    this.eventClass = paramClass;
    this.source = paramObject;
  }
  
  @Factory
  public static Matcher<EventObject> eventFrom(Class<? extends EventObject> paramClass, Object paramObject)
  {
    return new IsEventFrom(paramClass, paramObject);
  }
  
  @Factory
  public static Matcher<EventObject> eventFrom(Object paramObject)
  {
    return eventFrom(EventObject.class, paramObject);
  }
  
  private boolean eventHasSameSource(EventObject paramEventObject)
  {
    return paramEventObject.getSource() == this.source;
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("an event of type ").appendText(this.eventClass.getName()).appendText(" from ").appendValue(this.source);
  }
  
  public boolean matchesSafely(EventObject paramEventObject, Description paramDescription)
  {
    if (!this.eventClass.isInstance(paramEventObject))
    {
      paramDescription.appendText("item type was " + paramEventObject.getClass().getName());
      return false;
    }
    if (!eventHasSameSource(paramEventObject))
    {
      paramDescription.appendText("source was ").appendValue(paramEventObject.getSource());
      return false;
    }
    return true;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/object/IsEventFrom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */