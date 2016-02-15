package org.hamcrest.collection;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsEqual;

public class IsMapContaining<K, V>
  extends TypeSafeMatcher<Map<? extends K, ? extends V>>
{
  private final Matcher<? super K> keyMatcher;
  private final Matcher<? super V> valueMatcher;
  
  public IsMapContaining(Matcher<? super K> paramMatcher, Matcher<? super V> paramMatcher1)
  {
    this.keyMatcher = paramMatcher;
    this.valueMatcher = paramMatcher1;
  }
  
  @Factory
  public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(K paramK, V paramV)
  {
    return new IsMapContaining(IsEqual.equalTo(paramK), IsEqual.equalTo(paramV));
  }
  
  @Factory
  public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(Matcher<? super K> paramMatcher, Matcher<? super V> paramMatcher1)
  {
    return new IsMapContaining(paramMatcher, paramMatcher1);
  }
  
  @Factory
  public static <K> Matcher<Map<? extends K, ?>> hasKey(K paramK)
  {
    return new IsMapContaining(IsEqual.equalTo(paramK), IsAnything.anything());
  }
  
  @Factory
  public static <K> Matcher<Map<? extends K, ?>> hasKey(Matcher<? super K> paramMatcher)
  {
    return new IsMapContaining(paramMatcher, IsAnything.anything());
  }
  
  @Factory
  public static <V> Matcher<Map<?, ? extends V>> hasValue(V paramV)
  {
    return new IsMapContaining(IsAnything.anything(), IsEqual.equalTo(paramV));
  }
  
  @Factory
  public static <V> Matcher<Map<?, ? extends V>> hasValue(Matcher<? super V> paramMatcher)
  {
    return new IsMapContaining(IsAnything.anything(), paramMatcher);
  }
  
  public void describeMismatchSafely(Map<? extends K, ? extends V> paramMap, Description paramDescription)
  {
    paramDescription.appendText("map was ").appendValueList("[", ", ", "]", paramMap.entrySet());
  }
  
  public void describeTo(Description paramDescription)
  {
    paramDescription.appendText("map containing [").appendDescriptionOf(this.keyMatcher).appendText("->").appendDescriptionOf(this.valueMatcher).appendText("]");
  }
  
  public boolean matchesSafely(Map<? extends K, ? extends V> paramMap)
  {
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if ((this.keyMatcher.matches(localEntry.getKey())) && (this.valueMatcher.matches(localEntry.getValue()))) {
        return true;
      }
    }
    return false;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/collection/IsMapContaining.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */