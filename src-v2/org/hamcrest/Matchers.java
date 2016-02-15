/*
 * Decompiled with CFR 0_110.
 */
package org.hamcrest;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import org.hamcrest.Matcher;
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

public class Matchers {
    public static <T> Matcher<T> allOf(Iterable<Matcher<? super T>> iterable) {
        return AllOf.allOf(iterable);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2) {
        return AllOf.allOf(matcher, matcher2);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3) {
        return AllOf.allOf(matcher, matcher2, matcher3);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4) {
        return AllOf.allOf(matcher, matcher2, matcher3, matcher4);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4, Matcher<? super T> matcher5) {
        return AllOf.allOf(matcher, matcher2, matcher3, matcher4, matcher5);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4, Matcher<? super T> matcher5, Matcher<? super T> matcher6) {
        return AllOf.allOf(matcher, matcher2, matcher3, matcher4, matcher5, matcher6);
    }

    public static /* varargs */ <T> Matcher<T> allOf(Matcher<? super T> ... arrmatcher) {
        return AllOf.allOf(arrmatcher);
    }

    public static <T> Matcher<T> any(Class<T> class_) {
        return IsInstanceOf.any(class_);
    }

    public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> iterable) {
        return AnyOf.anyOf(iterable);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2) {
        return AnyOf.anyOf(matcher, matcher2);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3) {
        return AnyOf.anyOf(matcher, matcher2, matcher3);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4) {
        return AnyOf.anyOf(matcher, matcher2, matcher3, matcher4);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4, Matcher<? super T> matcher5) {
        return AnyOf.anyOf(matcher, matcher2, matcher3, matcher4, matcher5);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> matcher, Matcher<? super T> matcher2, Matcher<? super T> matcher3, Matcher<? super T> matcher4, Matcher<? super T> matcher5, Matcher<? super T> matcher6) {
        return AnyOf.anyOf(matcher, matcher2, matcher3, matcher4, matcher5, matcher6);
    }

    public static /* varargs */ <T> AnyOf<T> anyOf(Matcher<? super T> ... arrmatcher) {
        return AnyOf.anyOf(arrmatcher);
    }

    public static Matcher<Object> anything() {
        return IsAnything.anything();
    }

    public static Matcher<Object> anything(String string2) {
        return IsAnything.anything(string2);
    }

    public static /* varargs */ <T> IsArray<T> array(Matcher<? super T> ... arrmatcher) {
        return IsArray.array(arrmatcher);
    }

    public static <E> Matcher<E[]> arrayContaining(List<Matcher<? super E>> list) {
        return IsArrayContainingInOrder.arrayContaining(list);
    }

    public static /* varargs */ <E> Matcher<E[]> arrayContaining(E ... arrE) {
        return IsArrayContainingInOrder.arrayContaining(arrE);
    }

    public static /* varargs */ <E> Matcher<E[]> arrayContaining(Matcher<? super E> ... arrmatcher) {
        return IsArrayContainingInOrder.arrayContaining(arrmatcher);
    }

    public static <E> Matcher<E[]> arrayContainingInAnyOrder(Collection<Matcher<? super E>> collection) {
        return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(collection);
    }

    public static /* varargs */ <E> Matcher<E[]> arrayContainingInAnyOrder(E ... arrE) {
        return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(arrE);
    }

    public static /* varargs */ <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E> ... arrmatcher) {
        return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(arrmatcher);
    }

    public static <E> Matcher<E[]> arrayWithSize(int n2) {
        return IsArrayWithSize.arrayWithSize(n2);
    }

    public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> matcher) {
        return IsArrayWithSize.arrayWithSize(matcher);
    }

    public static <LHS> CombinableMatcher.CombinableBothMatcher<LHS> both(Matcher<? super LHS> matcher) {
        return CombinableMatcher.both(matcher);
    }

    public static Matcher<Double> closeTo(double d2, double d3) {
        return IsCloseTo.closeTo(d2, d3);
    }

    public static Matcher<BigDecimal> closeTo(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
        return BigDecimalCloseTo.closeTo(bigDecimal, bigDecimal2);
    }

    public static <T extends Comparable<T>> Matcher<T> comparesEqualTo(T t2) {
        return OrderingComparison.comparesEqualTo(t2);
    }

    public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> list) {
        return IsIterableContainingInOrder.contains(list);
    }

    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> matcher) {
        return IsIterableContainingInOrder.contains(matcher);
    }

    public static /* varargs */ <E> Matcher<Iterable<? extends E>> contains(E ... arrE) {
        return IsIterableContainingInOrder.contains(arrE);
    }

    public static /* varargs */ <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> ... arrmatcher) {
        return IsIterableContainingInOrder.contains(arrmatcher);
    }

    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> collection) {
        return IsIterableContainingInAnyOrder.containsInAnyOrder(collection);
    }

    public static <E> Matcher<Iterable<? extends E>> containsInAnyOrder(Matcher<? super E> matcher) {
        return IsIterableContainingInAnyOrder.containsInAnyOrder(matcher);
    }

    public static /* varargs */ <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T ... arrT) {
        return IsIterableContainingInAnyOrder.containsInAnyOrder(arrT);
    }

    public static /* varargs */ <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T> ... arrmatcher) {
        return IsIterableContainingInAnyOrder.containsInAnyOrder(arrmatcher);
    }

    public static Matcher<String> containsString(String string2) {
        return StringContains.containsString(string2);
    }

    public static /* varargs */ <T> Matcher<T> describedAs(String string2, Matcher<T> matcher, Object ... arrobject) {
        return DescribedAs.describedAs(string2, matcher, arrobject);
    }

    public static <LHS> CombinableMatcher.CombinableEitherMatcher<LHS> either(Matcher<? super LHS> matcher) {
        return CombinableMatcher.either(matcher);
    }

    public static <E> Matcher<Collection<? extends E>> empty() {
        return IsEmptyCollection.empty();
    }

    public static <E> Matcher<E[]> emptyArray() {
        return IsArrayWithSize.emptyArray();
    }

    public static <E> Matcher<Collection<E>> emptyCollectionOf(Class<E> class_) {
        return IsEmptyCollection.emptyCollectionOf(class_);
    }

    public static <E> Matcher<Iterable<? extends E>> emptyIterable() {
        return IsEmptyIterable.emptyIterable();
    }

    public static <E> Matcher<Iterable<E>> emptyIterableOf(Class<E> class_) {
        return IsEmptyIterable.emptyIterableOf(class_);
    }

    public static Matcher<String> endsWith(String string2) {
        return StringEndsWith.endsWith(string2);
    }

    public static <T> Matcher<T> equalTo(T t2) {
        return IsEqual.equalTo(t2);
    }

    public static Matcher<String> equalToIgnoringCase(String string2) {
        return IsEqualIgnoringCase.equalToIgnoringCase(string2);
    }

    public static Matcher<String> equalToIgnoringWhiteSpace(String string2) {
        return IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(string2);
    }

    public static Matcher<EventObject> eventFrom(Class<? extends EventObject> class_, Object object) {
        return IsEventFrom.eventFrom(class_, object);
    }

    public static Matcher<EventObject> eventFrom(Object object) {
        return IsEventFrom.eventFrom(object);
    }

    public static <U> Matcher<Iterable<U>> everyItem(Matcher<U> matcher) {
        return Every.everyItem(matcher);
    }

    public static <T extends Comparable<T>> Matcher<T> greaterThan(T t2) {
        return OrderingComparison.greaterThan(t2);
    }

    public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T t2) {
        return OrderingComparison.greaterThanOrEqualTo(t2);
    }

    public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(K k2, V v2) {
        return IsMapContaining.hasEntry(k2, v2);
    }

    public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(Matcher<? super K> matcher, Matcher<? super V> matcher2) {
        return IsMapContaining.hasEntry(matcher, matcher2);
    }

    public static <T> Matcher<Iterable<? super T>> hasItem(T t2) {
        return IsCollectionContaining.hasItem(t2);
    }

    public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> matcher) {
        return IsCollectionContaining.hasItem(matcher);
    }

    public static <T> Matcher<T[]> hasItemInArray(T t2) {
        return IsArrayContaining.hasItemInArray(t2);
    }

    public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> matcher) {
        return IsArrayContaining.hasItemInArray(matcher);
    }

    public static /* varargs */ <T> Matcher<Iterable<T>> hasItems(T ... arrT) {
        return IsCollectionContaining.hasItems(arrT);
    }

    public static /* varargs */ <T> Matcher<Iterable<T>> hasItems(Matcher<? super T> ... arrmatcher) {
        return IsCollectionContaining.hasItems(arrmatcher);
    }

    public static <K> Matcher<Map<? extends K, ?>> hasKey(K k2) {
        return IsMapContaining.hasKey(k2);
    }

    public static <K> Matcher<Map<? extends K, ?>> hasKey(Matcher<? super K> matcher) {
        return IsMapContaining.hasKey(matcher);
    }

    public static <T> Matcher<T> hasProperty(String string2) {
        return HasProperty.hasProperty(string2);
    }

    public static <T> Matcher<T> hasProperty(String string2, Matcher<?> matcher) {
        return HasPropertyWithValue.hasProperty(string2, matcher);
    }

    public static <E> Matcher<Collection<? extends E>> hasSize(int n2) {
        return IsCollectionWithSize.hasSize(n2);
    }

    public static <E> Matcher<Collection<? extends E>> hasSize(Matcher<? super Integer> matcher) {
        return IsCollectionWithSize.hasSize(matcher);
    }

    public static <T> Matcher<T> hasToString(String string2) {
        return HasToString.hasToString(string2);
    }

    public static <T> Matcher<T> hasToString(Matcher<? super String> matcher) {
        return HasToString.hasToString(matcher);
    }

    public static <V> Matcher<Map<?, ? extends V>> hasValue(V v2) {
        return IsMapContaining.hasValue(v2);
    }

    public static <V> Matcher<Map<?, ? extends V>> hasValue(Matcher<? super V> matcher) {
        return IsMapContaining.hasValue(matcher);
    }

    public static Matcher<Node> hasXPath(String string2) {
        return HasXPath.hasXPath(string2);
    }

    public static Matcher<Node> hasXPath(String string2, NamespaceContext namespaceContext) {
        return HasXPath.hasXPath(string2, namespaceContext);
    }

    public static Matcher<Node> hasXPath(String string2, NamespaceContext namespaceContext, Matcher<String> matcher) {
        return HasXPath.hasXPath(string2, namespaceContext, matcher);
    }

    public static Matcher<Node> hasXPath(String string2, Matcher<String> matcher) {
        return HasXPath.hasXPath(string2, matcher);
    }

    public static <T> Matcher<T> instanceOf(Class<?> class_) {
        return IsInstanceOf.instanceOf(class_);
    }

    public static <T> Matcher<T> is(Class<T> class_) {
        return Is.is(class_);
    }

    public static <T> Matcher<T> is(T t2) {
        return Is.is(t2);
    }

    public static <T> Matcher<T> is(Matcher<T> matcher) {
        return Is.is(matcher);
    }

    public static <T> Matcher<T> isA(Class<T> class_) {
        return Is.isA(class_);
    }

    public static Matcher<String> isEmptyOrNullString() {
        return IsEmptyString.isEmptyOrNullString();
    }

    public static Matcher<String> isEmptyString() {
        return IsEmptyString.isEmptyString();
    }

    public static <T> Matcher<T> isIn(Collection<T> collection) {
        return IsIn.isIn(collection);
    }

    public static <T> Matcher<T> isIn(T[] arrT) {
        return IsIn.isIn(arrT);
    }

    public static /* varargs */ <T> Matcher<T> isOneOf(T ... arrT) {
        return IsIn.isOneOf(arrT);
    }

    public static <E> Matcher<Iterable<E>> iterableWithSize(int n2) {
        return IsIterableWithSize.iterableWithSize(n2);
    }

    public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> matcher) {
        return IsIterableWithSize.iterableWithSize(matcher);
    }

    public static <T extends Comparable<T>> Matcher<T> lessThan(T t2) {
        return OrderingComparison.lessThan(t2);
    }

    public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T t2) {
        return OrderingComparison.lessThanOrEqualTo(t2);
    }

    public static <T> Matcher<T> not(T t2) {
        return IsNot.not(t2);
    }

    public static <T> Matcher<T> not(Matcher<T> matcher) {
        return IsNot.not(matcher);
    }

    public static Matcher<Object> notNullValue() {
        return IsNull.notNullValue();
    }

    public static <T> Matcher<T> notNullValue(Class<T> class_) {
        return IsNull.notNullValue(class_);
    }

    public static Matcher<Object> nullValue() {
        return IsNull.nullValue();
    }

    public static <T> Matcher<T> nullValue(Class<T> class_) {
        return IsNull.nullValue(class_);
    }

    public static <T> Matcher<T> sameInstance(T t2) {
        return IsSame.sameInstance(t2);
    }

    public static <T> Matcher<T> samePropertyValuesAs(T t2) {
        return SamePropertyValuesAs.samePropertyValuesAs(t2);
    }

    public static Matcher<String> startsWith(String string2) {
        return StringStartsWith.startsWith(string2);
    }

    public static Matcher<String> stringContainsInOrder(Iterable<String> iterable) {
        return StringContainsInOrder.stringContainsInOrder(iterable);
    }

    public static <T> Matcher<T> theInstance(T t2) {
        return IsSame.theInstance(t2);
    }

    public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> class_) {
        return IsCompatibleType.typeCompatibleWith(class_);
    }
}

