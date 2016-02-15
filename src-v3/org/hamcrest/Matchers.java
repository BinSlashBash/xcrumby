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

public class Matchers {
    public static <T> Matcher<T> allOf(Iterable<Matcher<? super T>> matchers) {
        return AllOf.allOf((Iterable) matchers);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T>... matchers) {
        return AllOf.allOf((Matcher[]) matchers);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second) {
        return AllOf.allOf(first, second);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third) {
        return AllOf.allOf(first, second, third);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth) {
        return AllOf.allOf(first, second, third, fourth);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth) {
        return AllOf.allOf(first, second, third, fourth, fifth);
    }

    public static <T> Matcher<T> allOf(Matcher<? super T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth, Matcher<? super T> sixth) {
        return AllOf.allOf(first, second, third, fourth, fifth, sixth);
    }

    public static <T> AnyOf<T> anyOf(Iterable<Matcher<? super T>> matchers) {
        return AnyOf.anyOf((Iterable) matchers);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third) {
        return AnyOf.anyOf(first, second, third);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth) {
        return AnyOf.anyOf(first, second, third, fourth);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth) {
        return AnyOf.anyOf(first, second, third, fourth, fifth);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second, Matcher<? super T> third, Matcher<? super T> fourth, Matcher<? super T> fifth, Matcher<? super T> sixth) {
        return AnyOf.anyOf(first, second, third, fourth, fifth, sixth);
    }

    public static <T> AnyOf<T> anyOf(Matcher<T> first, Matcher<? super T> second) {
        return AnyOf.anyOf(first, second);
    }

    public static <T> AnyOf<T> anyOf(Matcher<? super T>... matchers) {
        return AnyOf.anyOf((Matcher[]) matchers);
    }

    public static <LHS> CombinableBothMatcher<LHS> both(Matcher<? super LHS> matcher) {
        return CombinableMatcher.both(matcher);
    }

    public static <LHS> CombinableEitherMatcher<LHS> either(Matcher<? super LHS> matcher) {
        return CombinableMatcher.either(matcher);
    }

    public static <T> Matcher<T> describedAs(String description, Matcher<T> matcher, Object... values) {
        return DescribedAs.describedAs(description, matcher, values);
    }

    public static <U> Matcher<Iterable<U>> everyItem(Matcher<U> itemMatcher) {
        return Every.everyItem(itemMatcher);
    }

    public static <T> Matcher<T> is(T value) {
        return Is.is((Object) value);
    }

    public static <T> Matcher<T> is(Matcher<T> matcher) {
        return Is.is((Matcher) matcher);
    }

    public static <T> Matcher<T> is(Class<T> type) {
        return Is.is((Class) type);
    }

    public static <T> Matcher<T> isA(Class<T> type) {
        return Is.isA(type);
    }

    public static Matcher<Object> anything() {
        return IsAnything.anything();
    }

    public static Matcher<Object> anything(String description) {
        return IsAnything.anything(description);
    }

    public static <T> Matcher<Iterable<? super T>> hasItem(T item) {
        return IsCollectionContaining.hasItem((Object) item);
    }

    public static <T> Matcher<Iterable<? super T>> hasItem(Matcher<? super T> itemMatcher) {
        return IsCollectionContaining.hasItem((Matcher) itemMatcher);
    }

    public static <T> Matcher<Iterable<T>> hasItems(T... items) {
        return IsCollectionContaining.hasItems((Object[]) items);
    }

    public static <T> Matcher<Iterable<T>> hasItems(Matcher<? super T>... itemMatchers) {
        return IsCollectionContaining.hasItems((Matcher[]) itemMatchers);
    }

    public static <T> Matcher<T> equalTo(T operand) {
        return IsEqual.equalTo(operand);
    }

    public static <T> Matcher<T> any(Class<T> type) {
        return IsInstanceOf.any(type);
    }

    public static <T> Matcher<T> instanceOf(Class<?> type) {
        return IsInstanceOf.instanceOf(type);
    }

    public static <T> Matcher<T> not(Matcher<T> matcher) {
        return IsNot.not((Matcher) matcher);
    }

    public static <T> Matcher<T> not(T value) {
        return IsNot.not((Object) value);
    }

    public static Matcher<Object> nullValue() {
        return IsNull.nullValue();
    }

    public static <T> Matcher<T> nullValue(Class<T> type) {
        return IsNull.nullValue(type);
    }

    public static Matcher<Object> notNullValue() {
        return IsNull.notNullValue();
    }

    public static <T> Matcher<T> notNullValue(Class<T> type) {
        return IsNull.notNullValue(type);
    }

    public static <T> Matcher<T> sameInstance(T target) {
        return IsSame.sameInstance(target);
    }

    public static <T> Matcher<T> theInstance(T target) {
        return IsSame.theInstance(target);
    }

    public static Matcher<String> containsString(String substring) {
        return StringContains.containsString(substring);
    }

    public static Matcher<String> startsWith(String prefix) {
        return StringStartsWith.startsWith(prefix);
    }

    public static Matcher<String> endsWith(String suffix) {
        return StringEndsWith.endsWith(suffix);
    }

    public static <T> IsArray<T> array(Matcher<? super T>... elementMatchers) {
        return IsArray.array(elementMatchers);
    }

    public static <T> Matcher<T[]> hasItemInArray(T element) {
        return IsArrayContaining.hasItemInArray((Object) element);
    }

    public static <T> Matcher<T[]> hasItemInArray(Matcher<? super T> elementMatcher) {
        return IsArrayContaining.hasItemInArray((Matcher) elementMatcher);
    }

    public static <E> Matcher<E[]> arrayContaining(List<Matcher<? super E>> itemMatchers) {
        return IsArrayContainingInOrder.arrayContaining((List) itemMatchers);
    }

    public static <E> Matcher<E[]> arrayContaining(E... items) {
        return IsArrayContainingInOrder.arrayContaining((Object[]) items);
    }

    public static <E> Matcher<E[]> arrayContaining(Matcher<? super E>... itemMatchers) {
        return IsArrayContainingInOrder.arrayContaining((Matcher[]) itemMatchers);
    }

    public static <E> Matcher<E[]> arrayContainingInAnyOrder(E... items) {
        return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder((Object[]) items);
    }

    public static <E> Matcher<E[]> arrayContainingInAnyOrder(Matcher<? super E>... itemMatchers) {
        return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder((Matcher[]) itemMatchers);
    }

    public static <E> Matcher<E[]> arrayContainingInAnyOrder(Collection<Matcher<? super E>> itemMatchers) {
        return IsArrayContainingInAnyOrder.arrayContainingInAnyOrder((Collection) itemMatchers);
    }

    public static <E> Matcher<E[]> arrayWithSize(Matcher<? super Integer> sizeMatcher) {
        return IsArrayWithSize.arrayWithSize((Matcher) sizeMatcher);
    }

    public static <E> Matcher<E[]> arrayWithSize(int size) {
        return IsArrayWithSize.arrayWithSize(size);
    }

    public static <E> Matcher<E[]> emptyArray() {
        return IsArrayWithSize.emptyArray();
    }

    public static <E> Matcher<Collection<? extends E>> hasSize(Matcher<? super Integer> sizeMatcher) {
        return IsCollectionWithSize.hasSize((Matcher) sizeMatcher);
    }

    public static <E> Matcher<Collection<? extends E>> hasSize(int size) {
        return IsCollectionWithSize.hasSize(size);
    }

    public static <E> Matcher<Collection<? extends E>> empty() {
        return IsEmptyCollection.empty();
    }

    public static <E> Matcher<Collection<E>> emptyCollectionOf(Class<E> type) {
        return IsEmptyCollection.emptyCollectionOf(type);
    }

    public static <E> Matcher<Iterable<? extends E>> emptyIterable() {
        return IsEmptyIterable.emptyIterable();
    }

    public static <E> Matcher<Iterable<E>> emptyIterableOf(Class<E> type) {
        return IsEmptyIterable.emptyIterableOf(type);
    }

    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E>... itemMatchers) {
        return IsIterableContainingInOrder.contains((Matcher[]) itemMatchers);
    }

    public static <E> Matcher<Iterable<? extends E>> contains(E... items) {
        return IsIterableContainingInOrder.contains((Object[]) items);
    }

    public static <E> Matcher<Iterable<? extends E>> contains(Matcher<? super E> itemMatcher) {
        return IsIterableContainingInOrder.contains((Matcher) itemMatcher);
    }

    public static <E> Matcher<Iterable<? extends E>> contains(List<Matcher<? super E>> itemMatchers) {
        return IsIterableContainingInOrder.contains((List) itemMatchers);
    }

    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(T... items) {
        return IsIterableContainingInAnyOrder.containsInAnyOrder((Object[]) items);
    }

    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<Matcher<? super T>> itemMatchers) {
        return IsIterableContainingInAnyOrder.containsInAnyOrder((Collection) itemMatchers);
    }

    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Matcher<? super T>... itemMatchers) {
        return IsIterableContainingInAnyOrder.containsInAnyOrder((Matcher[]) itemMatchers);
    }

    public static <E> Matcher<Iterable<? extends E>> containsInAnyOrder(Matcher<? super E> itemMatcher) {
        return IsIterableContainingInAnyOrder.containsInAnyOrder((Matcher) itemMatcher);
    }

    public static <E> Matcher<Iterable<E>> iterableWithSize(Matcher<? super Integer> sizeMatcher) {
        return IsIterableWithSize.iterableWithSize((Matcher) sizeMatcher);
    }

    public static <E> Matcher<Iterable<E>> iterableWithSize(int size) {
        return IsIterableWithSize.iterableWithSize(size);
    }

    public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(K key, V value) {
        return IsMapContaining.hasEntry((Object) key, (Object) value);
    }

    public static <K, V> Matcher<Map<? extends K, ? extends V>> hasEntry(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        return IsMapContaining.hasEntry((Matcher) keyMatcher, (Matcher) valueMatcher);
    }

    public static <K> Matcher<Map<? extends K, ?>> hasKey(Matcher<? super K> keyMatcher) {
        return IsMapContaining.hasKey((Matcher) keyMatcher);
    }

    public static <K> Matcher<Map<? extends K, ?>> hasKey(K key) {
        return IsMapContaining.hasKey((Object) key);
    }

    public static <V> Matcher<Map<?, ? extends V>> hasValue(V value) {
        return IsMapContaining.hasValue((Object) value);
    }

    public static <V> Matcher<Map<?, ? extends V>> hasValue(Matcher<? super V> valueMatcher) {
        return IsMapContaining.hasValue((Matcher) valueMatcher);
    }

    public static <T> Matcher<T> isIn(Collection<T> collection) {
        return IsIn.isIn((Collection) collection);
    }

    public static <T> Matcher<T> isIn(T[] param1) {
        return IsIn.isIn((Object[]) param1);
    }

    public static <T> Matcher<T> isOneOf(T... elements) {
        return IsIn.isOneOf(elements);
    }

    public static Matcher<Double> closeTo(double operand, double error) {
        return IsCloseTo.closeTo(operand, error);
    }

    public static Matcher<BigDecimal> closeTo(BigDecimal operand, BigDecimal error) {
        return BigDecimalCloseTo.closeTo(operand, error);
    }

    public static <T extends Comparable<T>> Matcher<T> comparesEqualTo(T value) {
        return OrderingComparison.comparesEqualTo(value);
    }

    public static <T extends Comparable<T>> Matcher<T> greaterThan(T value) {
        return OrderingComparison.greaterThan(value);
    }

    public static <T extends Comparable<T>> Matcher<T> greaterThanOrEqualTo(T value) {
        return OrderingComparison.greaterThanOrEqualTo(value);
    }

    public static <T extends Comparable<T>> Matcher<T> lessThan(T value) {
        return OrderingComparison.lessThan(value);
    }

    public static <T extends Comparable<T>> Matcher<T> lessThanOrEqualTo(T value) {
        return OrderingComparison.lessThanOrEqualTo(value);
    }

    public static Matcher<String> equalToIgnoringCase(String expectedString) {
        return IsEqualIgnoringCase.equalToIgnoringCase(expectedString);
    }

    public static Matcher<String> equalToIgnoringWhiteSpace(String expectedString) {
        return IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(expectedString);
    }

    public static Matcher<String> isEmptyString() {
        return IsEmptyString.isEmptyString();
    }

    public static Matcher<String> isEmptyOrNullString() {
        return IsEmptyString.isEmptyOrNullString();
    }

    public static Matcher<String> stringContainsInOrder(Iterable<String> substrings) {
        return StringContainsInOrder.stringContainsInOrder(substrings);
    }

    public static <T> Matcher<T> hasToString(Matcher<? super String> toStringMatcher) {
        return HasToString.hasToString((Matcher) toStringMatcher);
    }

    public static <T> Matcher<T> hasToString(String expectedToString) {
        return HasToString.hasToString(expectedToString);
    }

    public static <T> Matcher<Class<?>> typeCompatibleWith(Class<T> baseType) {
        return IsCompatibleType.typeCompatibleWith(baseType);
    }

    public static Matcher<EventObject> eventFrom(Class<? extends EventObject> eventClass, Object source) {
        return IsEventFrom.eventFrom(eventClass, source);
    }

    public static Matcher<EventObject> eventFrom(Object source) {
        return IsEventFrom.eventFrom(source);
    }

    public static <T> Matcher<T> hasProperty(String propertyName) {
        return HasProperty.hasProperty(propertyName);
    }

    public static <T> Matcher<T> hasProperty(String propertyName, Matcher<?> valueMatcher) {
        return HasPropertyWithValue.hasProperty(propertyName, valueMatcher);
    }

    public static <T> Matcher<T> samePropertyValuesAs(T expectedBean) {
        return SamePropertyValuesAs.samePropertyValuesAs(expectedBean);
    }

    public static Matcher<Node> hasXPath(String xPath, NamespaceContext namespaceContext) {
        return HasXPath.hasXPath(xPath, namespaceContext);
    }

    public static Matcher<Node> hasXPath(String xPath) {
        return HasXPath.hasXPath(xPath);
    }

    public static Matcher<Node> hasXPath(String xPath, NamespaceContext namespaceContext, Matcher<String> valueMatcher) {
        return HasXPath.hasXPath(xPath, namespaceContext, valueMatcher);
    }

    public static Matcher<Node> hasXPath(String xPath, Matcher<String> valueMatcher) {
        return HasXPath.hasXPath(xPath, (Matcher) valueMatcher);
    }
}
