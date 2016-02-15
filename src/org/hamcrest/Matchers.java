package org.hamcrest;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import org.hamcrest.beans.HasProperty;
import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.hamcrest.collection.IsArray;
import org.hamcrest.collection.IsArrayContaining;
import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.hamcrest.collection.IsArrayContainingInOrder;
import org.hamcrest.collection.IsArrayWithSize;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.collection.IsIn;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.collection.IsIterableWithSize;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.CombinableMatcher;
import org.hamcrest.core.CombinableMatcher.CombinableBothMatcher;
import org.hamcrest.core.CombinableMatcher.CombinableEitherMatcher;
import org.hamcrest.core.DescribedAs;
import org.hamcrest.core.Every;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsCollectionContaining;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringEndsWith;
import org.hamcrest.core.StringStartsWith;
import org.hamcrest.number.BigDecimalCloseTo;
import org.hamcrest.number.IsCloseTo;
import org.hamcrest.number.OrderingComparison;
import org.hamcrest.object.HasToString;
import org.hamcrest.object.IsCompatibleType;
import org.hamcrest.object.IsEventFrom;
import org.hamcrest.text.IsEmptyString;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.hamcrest.text.IsEqualIgnoringWhiteSpace;
import org.hamcrest.text.StringContainsInOrder;
import org.hamcrest.xml.HasXPath;
import org.w3c.dom.Node;

public class Matchers
{
  public static <T> Matcher<T> allOf(Iterable<Matcher<? super T>> paramIterable)
  {
    return AllOf.allOf(paramIterable);
  }
  
  public static <T> Matcher<T> allOf(Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2)
  {
    return AllOf.allOf(paramMatcher1, paramMatcher2);
  }
  
  public static <T> Matcher<T> allOf(Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2, Matcher<? super T> paramMatcher3)
  {
    return AllOf.allOf(paramMatcher1, paramMatcher2, paramMatcher3);
  }
  
  public static <T> Matcher<T> allOf(Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2, Matcher<? super T> paramMatcher3, Matcher<? super T> paramMatcher4)
  {
    return AllOf.allOf(paramMatcher1, paramMatcher2, paramMatcher3, paramMatcher4);
  }
  
  public static <T> Matcher<T> allOf(Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2, Matcher<? super T> paramMatcher3, Matcher<? super T> paramMatcher4, Matcher<? super T> paramMatcher5)
  {
    return AllOf.allOf(paramMatcher1, paramMatcher2, paramMatcher3, paramMatcher4, paramMatcher5);
  }
  
  public static <T> Matcher<T> allOf(Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2, Matcher<? super T> paramMatcher3, Matcher<? super T> paramMatcher4, Matcher<? super T> paramMatcher5, Matcher<? super T> paramMatcher6)
  {
    return AllOf.allOf(paramMatcher1, paramMatcher2, paramMatcher3, paramMatcher4, paramMatcher5, paramMatcher6);
  }
  
  public static <T> Matcher<T> allOf(Matcher<? super T>... paramVarArgs)
  {
    return AllOf.allOf(paramVarArgs);
  }
  
  public static <T> Matcher<T> any(Class<T> paramClass)
  {
    return IsInstanceOf.any(paramClass);
  }
  
  public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> paramIterable)
  {
    return AnyOf.anyOf(paramIterable);
  }
  
  public static <T> AnyOf<T> anyOf(Matcher<T> paramMatcher, Matcher<? super T> paramMatcher1)
  {
    return AnyOf.anyOf(paramMatcher, paramMatcher1);
  }
  
  public static <T> AnyOf<T> anyOf(Matcher<T> paramMatcher, Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2)
  {
    return AnyOf.anyOf(paramMatcher, paramMatcher1, paramMatcher2);
  }
  
  public static <T> AnyOf<T> anyOf(Matcher<T> paramMatcher, Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2, Matcher<? super T> paramMatcher3)
  {
    return AnyOf.anyOf(paramMatcher, paramMatcher1, paramMatcher2, paramMatcher3);
  }
  
  public static <T> AnyOf<T> anyOf(Matcher<T> paramMatcher, Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2, Matcher<? super T> paramMatcher3, Matcher<? super T> paramMatcher4)
  {
    return AnyOf.anyOf(paramMatcher, paramMatcher1, paramMatcher2, paramMatcher3, paramMatcher4);
  }
  
  public static <T> AnyOf<T> anyOf(Matcher<T> paramMatcher, Matcher<? super T> paramMatcher1, Matcher<? super T> paramMatcher2, Matcher<? super T> paramMatcher3, Matcher<? super T> paramMatcher4, Matcher<? super T> paramMatcher5)
  {
    return AnyOf.anyOf(paramMatcher, paramMatcher1, paramMatcher2, paramMatcher3, paramMatcher4, paramMatcher5);
  }
  
  public static <T> AnyOf<T> anyOf(Matcher<? super T>... paramVarArgs)
  {
    return AnyOf.anyOf(paramVarArgs);
  }
  
  public static Matcher<Object> anything()
  {
    return IsAnything.anything();
  }
  
  public static Matcher<Object> anything(String paramString)
  {
    return IsAnything.anything(paramString);
  }
  
  public static <T> IsArray<T> array(Matcher<? super T>... paramVarArgs)
  {
    return IsArray.array(paramVarArgs);
  }
  
  public static <E> Matcher<E[]> arrayContaining(List<Matcher<? super E>> paramList)
  {
    return IsArrayContainingInOrder.arrayContaining(paramList);
  }
  
  public static <E> Matcher<E[]> arrayContaining(E... paramVarArgs)
  {
    return IsArrayContainingInOrder.arrayContaining(paramVarArgs);
  }
  
  public static <E> Matcher<E[]> arrayContaining(Matcher<? super E>... paramVarArgs)
  {
    return IsArrayContainingInOrder.arrayContaining(paramVarArgs);
  }
  
  public static <E> Matcher<E[]> arrayContainingInAnyOrder(Collection<Matcher<? super E>> paramCollection)
  {
    return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(paramCollection);
  }
  
  public static <E> Matcher<E[]> arrayContainingInAnyOrder(E... paramVarArgs)
  {
    return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(paramVarArgs);
  }
  
  public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E>... paramVarArgs)
  {
    return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(paramVarArgs);
  }
  
  public static <E> Matcher<E[]> arrayWithSize(int paramInt)
  {
    return IsArrayWithSize.arrayWithSize(paramInt);
  }
  
  public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> paramMatcher)
  {
    return IsArrayWithSize.arrayWithSize(paramMatcher);
  }
  
  public static <LHS> CombinableMatcher.CombinableBothMatcher<LHS> both(Matcher<? super LHS> paramMatcher)
  {
    return CombinableMatcher.both(paramMatcher);
  }
  
  public static Matcher<Double> closeTo(double paramDouble1, double paramDouble2)
  {
    return IsCloseTo.closeTo(paramDouble1, paramDouble2);
  }
  
  public static Matcher<BigDecimal> closeTo(BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2)
  {
    return BigDecimalCloseTo.closeTo(paramBigDecimal1, paramBigDecimal2);
  }
  
  public static <T extends Comparable<T>> Matcher<T> comparesEqualTo(T paramT)
  {
    return OrderingComparison.comparesEqualTo(paramT);
  }
  
  public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> paramList)
  {
    return IsIterableContainingInOrder.contains(paramList);
  }
  
  public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> paramMatcher)
  {
    return IsIterableContainingInOrder.contains(paramMatcher);
  }
  
  public static <E> Matcher<Iterable<? extends E>> contains(E... paramVarArgs)
  {
    return IsIterableContainingInOrder.contains(paramVarArgs);
  }
  
  public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... paramVarArgs)
  {
    return IsIterableContainingInOrder.contains(paramVarArgs);
  }
  
  public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> paramCollection)
  {
    return IsIterableContainingInAnyOrder.containsInAnyOrder(paramCollection);
  }
  
  public static <E> Matcher<Iterable<? extends E>> containsInAnyOrder(Matcher<? super E> paramMatcher)
  {
    return IsIterableContainingInAnyOrder.containsInAnyOrder(paramMatcher);
  }
  
  public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... paramVarArgs)
  {
    return IsIterableContainingInAnyOrder.containsInAnyOrder(paramVarArgs);
  }
  
  public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... paramVarArgs)
  {
    return IsIterableContainingInAnyOrder.containsInAnyOrder(paramVarArgs);
  }
  
  public static Matcher<String> containsString(String paramString)
  {
    return StringContains.containsString(paramString);
  }
  
  public static <T> Matcher<T> describedAs(String paramString, Matcher<T> paramMatcher, Object... paramVarArgs)
  {
    return DescribedAs.describedAs(paramString, paramMatcher, paramVarArgs);
  }
  
  public static <LHS> CombinableMatcher.CombinableEitherMatcher<LHS> either(Matcher<? super LHS> paramMatcher)
  {
    return CombinableMatcher.either(paramMatcher);
  }
  
  public static <E> Matcher<Collection<? extends E>> empty()
  {
    return IsEmptyCollection.empty();
  }
  
  public static <E> Matcher<E[]> emptyArray()
  {
    return IsArrayWithSize.emptyArray();
  }
  
  public static <E> Matcher<Collection<E>> emptyCollectionOf(Class<E> paramClass)
  {
    return IsEmptyCollection.emptyCollectionOf(paramClass);
  }
  
  public static <E> Matcher<Iterable<? extends E>> emptyIterable()
  {
    return IsEmptyIterable.emptyIterable();
  }
  
  public static <E> Matcher<Iterable<E>> emptyIterableOf(Class<E> paramClass)
  {
    return IsEmptyIterable.emptyIterableOf(paramClass);
  }
  
  public static Matcher<String> endsWith(String paramString)
  {
    return StringEndsWith.endsWith(paramString);
  }
  
  public static <T> Matcher<T> equalTo(T paramT)
  {
    return IsEqual.equalTo(paramT);
  }
  
  public static Matcher<String> equalToIgnoringCase(String paramString)
  {
    return IsEqualIgnoringCase.equalToIgnoringCase(paramString);
  }
  
  public static Matcher<String> equalToIgnoringWhiteSpace(String paramString)
  {
    return IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(paramString);
  }
  
  public static Matcher<EventObject> eventFrom(Class<? extends EventObject> paramClass, Object paramObject)
  {
    return IsEventFrom.eventFrom(paramClass, paramObject);
  }
  
  public static Matcher<EventObject> eventFrom(Object paramObject)
  {
    return IsEventFrom.eventFrom(paramObject);
  }
  
  public static <U> Matcher<Iterable<U>> everyItem(Matcher<U> paramMatcher)
  {
    return Every.everyItem(paramMatcher);
  }
  
  public static <T extends Comparable<T>> Matcher<T> greaterThan(T paramT)
  {
    return OrderingComparison.greaterThan(paramT);
  }
  
  public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T paramT)
  {
    return OrderingComparison.greaterThanOrEqualTo(paramT);
  }
  
  public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(K paramK, V paramV)
  {
    return IsMapContaining.hasEntry(paramK, paramV);
  }
  
  public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(Matcher<? super K> paramMatcher, Matcher<? super V> paramMatcher1)
  {
    return IsMapContaining.hasEntry(paramMatcher, paramMatcher1);
  }
  
  public static <T> Matcher<Iterable<? super T>> hasItem(T paramT)
  {
    return IsCollectionContaining.hasItem(paramT);
  }
  
  public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> paramMatcher)
  {
    return IsCollectionContaining.hasItem(paramMatcher);
  }
  
  public static <T> Matcher<T[]> hasItemInArray(T paramT)
  {
    return IsArrayContaining.hasItemInArray(paramT);
  }
  
  public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> paramMatcher)
  {
    return IsArrayContaining.hasItemInArray(paramMatcher);
  }
  
  public static <T> Matcher<Iterable<T>> hasItems(T... paramVarArgs)
  {
    return IsCollectionContaining.hasItems(paramVarArgs);
  }
  
  public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... paramVarArgs)
  {
    return IsCollectionContaining.hasItems(paramVarArgs);
  }
  
  public static <K> Matcher<Map<? extends K, ?>> hasKey(K paramK)
  {
    return IsMapContaining.hasKey(paramK);
  }
  
  public static <K> Matcher<Map<? extends K, ?>> hasKey(Matcher<? super K> paramMatcher)
  {
    return IsMapContaining.hasKey(paramMatcher);
  }
  
  public static <T> Matcher<T> hasProperty(String paramString)
  {
    return HasProperty.hasProperty(paramString);
  }
  
  public static <T> Matcher<T> hasProperty(String paramString, Matcher<?> paramMatcher)
  {
    return HasPropertyWithValue.hasProperty(paramString, paramMatcher);
  }
  
  public static <E> Matcher<Collection<? extends E>> hasSize(int paramInt)
  {
    return IsCollectionWithSize.hasSize(paramInt);
  }
  
  public static <E> Matcher<Collection<? extends E>> hasSize(Matcher<? super Integer> paramMatcher)
  {
    return IsCollectionWithSize.hasSize(paramMatcher);
  }
  
  public static <T> Matcher<T> hasToString(String paramString)
  {
    return HasToString.hasToString(paramString);
  }
  
  public static <T> Matcher<T> hasToString(Matcher<? super String> paramMatcher)
  {
    return HasToString.hasToString(paramMatcher);
  }
  
  public static <V> Matcher<Map<?, ? extends V>> hasValue(V paramV)
  {
    return IsMapContaining.hasValue(paramV);
  }
  
  public static <V> Matcher<Map<?, ? extends V>> hasValue(Matcher<? super V> paramMatcher)
  {
    return IsMapContaining.hasValue(paramMatcher);
  }
  
  public static Matcher<Node> hasXPath(String paramString)
  {
    return HasXPath.hasXPath(paramString);
  }
  
  public static Matcher<Node> hasXPath(String paramString, NamespaceContext paramNamespaceContext)
  {
    return HasXPath.hasXPath(paramString, paramNamespaceContext);
  }
  
  public static Matcher<Node> hasXPath(String paramString, NamespaceContext paramNamespaceContext, Matcher<String> paramMatcher)
  {
    return HasXPath.hasXPath(paramString, paramNamespaceContext, paramMatcher);
  }
  
  public static Matcher<Node> hasXPath(String paramString, Matcher<String> paramMatcher)
  {
    return HasXPath.hasXPath(paramString, paramMatcher);
  }
  
  public static <T> Matcher<T> instanceOf(Class<?> paramClass)
  {
    return IsInstanceOf.instanceOf(paramClass);
  }
  
  public static <T> Matcher<T> is(Class<T> paramClass)
  {
    return Is.is(paramClass);
  }
  
  public static <T> Matcher<T> is(T paramT)
  {
    return Is.is(paramT);
  }
  
  public static <T> Matcher<T> is(Matcher<T> paramMatcher)
  {
    return Is.is(paramMatcher);
  }
  
  public static <T> Matcher<T> isA(Class<T> paramClass)
  {
    return Is.isA(paramClass);
  }
  
  public static Matcher<String> isEmptyOrNullString()
  {
    return IsEmptyString.isEmptyOrNullString();
  }
  
  public static Matcher<String> isEmptyString()
  {
    return IsEmptyString.isEmptyString();
  }
  
  public static <T> Matcher<T> isIn(Collection<T> paramCollection)
  {
    return IsIn.isIn(paramCollection);
  }
  
  public static <T> Matcher<T> isIn(T[] paramArrayOfT)
  {
    return IsIn.isIn(paramArrayOfT);
  }
  
  public static <T> Matcher<T> isOneOf(T... paramVarArgs)
  {
    return IsIn.isOneOf(paramVarArgs);
  }
  
  public static <E> Matcher<Iterable<E>> iterableWithSize(int paramInt)
  {
    return IsIterableWithSize.iterableWithSize(paramInt);
  }
  
  public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> paramMatcher)
  {
    return IsIterableWithSize.iterableWithSize(paramMatcher);
  }
  
  public static <T extends Comparable<T>> Matcher<T> lessThan(T paramT)
  {
    return OrderingComparison.lessThan(paramT);
  }
  
  public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T paramT)
  {
    return OrderingComparison.lessThanOrEqualTo(paramT);
  }
  
  public static <T> Matcher<T> not(T paramT)
  {
    return IsNot.not(paramT);
  }
  
  public static <T> Matcher<T> not(Matcher<T> paramMatcher)
  {
    return IsNot.not(paramMatcher);
  }
  
  public static Matcher<Object> notNullValue()
  {
    return IsNull.notNullValue();
  }
  
  public static <T> Matcher<T> notNullValue(Class<T> paramClass)
  {
    return IsNull.notNullValue(paramClass);
  }
  
  public static Matcher<Object> nullValue()
  {
    return IsNull.nullValue();
  }
  
  public static <T> Matcher<T> nullValue(Class<T> paramClass)
  {
    return IsNull.nullValue(paramClass);
  }
  
  public static <T> Matcher<T> sameInstance(T paramT)
  {
    return IsSame.sameInstance(paramT);
  }
  
  public static <T> Matcher<T> samePropertyValuesAs(T paramT)
  {
    return SamePropertyValuesAs.samePropertyValuesAs(paramT);
  }
  
  public static Matcher<String> startsWith(String paramString)
  {
    return StringStartsWith.startsWith(paramString);
  }
  
  public static Matcher<String> stringContainsInOrder(Iterable<String> paramIterable)
  {
    return StringContainsInOrder.stringContainsInOrder(paramIterable);
  }
  
  public static <T> Matcher<T> theInstance(T paramT)
  {
    return IsSame.theInstance(paramT);
  }
  
  public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> paramClass)
  {
    return IsCompatibleType.typeCompatibleWith(paramClass);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/hamcrest/Matchers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */