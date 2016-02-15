/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.Selector;

public class Elements
implements List<Element>,
Cloneable {
    private List<Element> contents;

    public Elements() {
        this.contents = new ArrayList<Element>();
    }

    public Elements(int n2) {
        this.contents = new ArrayList<Element>(n2);
    }

    public Elements(Collection<Element> collection) {
        this.contents = new ArrayList<Element>(collection);
    }

    public Elements(List<Element> list) {
        this.contents = list;
    }

    public /* varargs */ Elements(Element ... arrelement) {
        this(Arrays.asList(arrelement));
    }

    @Override
    public void add(int n2, Element element) {
        this.contents.add(n2, element);
    }

    @Override
    public boolean add(Element element) {
        return this.contents.add(element);
    }

    @Override
    public boolean addAll(int n2, Collection<? extends Element> collection) {
        return this.contents.addAll(n2, collection);
    }

    @Override
    public boolean addAll(Collection<? extends Element> collection) {
        return this.contents.addAll(collection);
    }

    public Elements addClass(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().addClass(string2);
        }
        return this;
    }

    public Elements after(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().after(string2);
        }
        return this;
    }

    public Elements append(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().append(string2);
        }
        return this;
    }

    public String attr(String string2) {
        for (Element element : this.contents) {
            if (!element.hasAttr(string2)) continue;
            return element.attr(string2);
        }
        return "";
    }

    public Elements attr(String string2, String string3) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().attr(string2, string3);
        }
        return this;
    }

    public Elements before(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().before(string2);
        }
        return this;
    }

    @Override
    public void clear() {
        this.contents.clear();
    }

    public Elements clone() {
        Elements elements;
        Iterator<Element> iterator;
        ArrayList<Element> arrayList;
        try {
            elements = (Elements)super.clone();
            elements.contents = arrayList = new ArrayList<Element>();
            iterator = this.contents.iterator();
        }
        catch (CloneNotSupportedException var1_2) {
            throw new RuntimeException(var1_2);
        }
        while (iterator.hasNext()) {
            arrayList.add((Element)iterator.next().clone());
        }
        return elements;
    }

    @Override
    public boolean contains(Object object) {
        return this.contents.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.contents.containsAll(collection);
    }

    public Elements empty() {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().empty();
        }
        return this;
    }

    public Elements eq(int n2) {
        if (this.contents.size() > n2) {
            return new Elements(new Element[]{this.get(n2)});
        }
        return new Elements();
    }

    @Override
    public boolean equals(Object object) {
        return this.contents.equals(object);
    }

    public Element first() {
        if (this.contents.isEmpty()) {
            return null;
        }
        return this.contents.get(0);
    }

    public List<FormElement> forms() {
        ArrayList<FormElement> arrayList = new ArrayList<FormElement>();
        for (Element element : this.contents) {
            if (!(element instanceof FormElement)) continue;
            arrayList.add((FormElement)element);
        }
        return arrayList;
    }

    @Override
    public Element get(int n2) {
        return this.contents.get(n2);
    }

    public boolean hasAttr(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().hasAttr(string2)) continue;
            return true;
        }
        return false;
    }

    public boolean hasClass(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().hasClass(string2)) continue;
            return true;
        }
        return false;
    }

    public boolean hasText() {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().hasText()) continue;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.contents.hashCode();
    }

    public String html() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Element element : this.contents) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(element.html());
        }
        return stringBuilder.toString();
    }

    public Elements html(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().html(string2);
        }
        return this;
    }

    @Override
    public int indexOf(Object object) {
        return this.contents.indexOf(object);
    }

    public boolean is(String string2) {
        if (!this.select(string2).isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.contents.isEmpty();
    }

    @Override
    public Iterator<Element> iterator() {
        return this.contents.iterator();
    }

    public Element last() {
        if (this.contents.isEmpty()) {
            return null;
        }
        return this.contents.get(this.contents.size() - 1);
    }

    @Override
    public int lastIndexOf(Object object) {
        return this.contents.lastIndexOf(object);
    }

    @Override
    public ListIterator<Element> listIterator() {
        return this.contents.listIterator();
    }

    @Override
    public ListIterator<Element> listIterator(int n2) {
        return this.contents.listIterator(n2);
    }

    public Elements not(String string2) {
        return Selector.filterOut(this, Selector.select(string2, this));
    }

    public String outerHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Element element : this.contents) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(element.outerHtml());
        }
        return stringBuilder.toString();
    }

    public Elements parents() {
        LinkedHashSet<Element> linkedHashSet = new LinkedHashSet<Element>();
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            linkedHashSet.addAll(iterator.next().parents());
        }
        return new Elements(linkedHashSet);
    }

    public Elements prepend(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().prepend(string2);
        }
        return this;
    }

    @Override
    public Element remove(int n2) {
        return this.contents.remove(n2);
    }

    public Elements remove() {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().remove();
        }
        return this;
    }

    @Override
    public boolean remove(Object object) {
        return this.contents.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.contents.removeAll(collection);
    }

    public Elements removeAttr(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().removeAttr(string2);
        }
        return this;
    }

    public Elements removeClass(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().removeClass(string2);
        }
        return this;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.contents.retainAll(collection);
    }

    public Elements select(String string2) {
        return Selector.select(string2, this);
    }

    @Override
    public Element set(int n2, Element element) {
        return this.contents.set(n2, element);
    }

    @Override
    public int size() {
        return this.contents.size();
    }

    @Override
    public List<Element> subList(int n2, int n3) {
        return this.contents.subList(n2, n3);
    }

    public Elements tagName(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().tagName(string2);
        }
        return this;
    }

    public String text() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Element element : this.contents) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(element.text());
        }
        return stringBuilder.toString();
    }

    @Override
    public Object[] toArray() {
        return this.contents.toArray();
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        return this.contents.toArray(arrT);
    }

    public String toString() {
        return this.outerHtml();
    }

    public Elements toggleClass(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().toggleClass(string2);
        }
        return this;
    }

    public Elements traverse(NodeVisitor object) {
        Validate.notNull(object);
        object = new NodeTraversor((NodeVisitor)object);
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            object.traverse(iterator.next());
        }
        return this;
    }

    public Elements unwrap() {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().unwrap();
        }
        return this;
    }

    public String val() {
        if (this.size() > 0) {
            return this.first().val();
        }
        return "";
    }

    public Elements val(String string2) {
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().val(string2);
        }
        return this;
    }

    public Elements wrap(String string2) {
        Validate.notEmpty(string2);
        Iterator<Element> iterator = this.contents.iterator();
        while (iterator.hasNext()) {
            iterator.next().wrap(string2);
        }
        return this;
    }
}

