/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.select;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.QueryParser;

public class Selector {
    private final Evaluator evaluator;
    private final Element root;

    private Selector(String string2, Element element) {
        Validate.notNull(string2);
        string2 = string2.trim();
        Validate.notEmpty(string2);
        Validate.notNull(element);
        this.evaluator = QueryParser.parse(string2);
        this.root = element;
    }

    static Elements filterOut(Collection<Element> object, Collection<Element> collection) {
        Elements elements = new Elements();
        object = object.iterator();
        while (object.hasNext()) {
            Element element;
            boolean bl2;
            block2 : {
                element = (Element)object.next();
                boolean bl3 = false;
                Iterator<Element> iterator = collection.iterator();
                do {
                    bl2 = bl3;
                    if (!iterator.hasNext()) break block2;
                } while (!element.equals(iterator.next()));
                bl2 = true;
            }
            if (bl2) continue;
            elements.add(element);
        }
        return elements;
    }

    private Elements select() {
        return Collector.collect(this.evaluator, this.root);
    }

    public static Elements select(String string2, Iterable<Element> object) {
        Validate.notEmpty(string2);
        Validate.notNull(object);
        LinkedHashSet<Element> linkedHashSet = new LinkedHashSet<Element>();
        object = object.iterator();
        while (object.hasNext()) {
            linkedHashSet.addAll(Selector.select(string2, (Element)object.next()));
        }
        return new Elements(linkedHashSet);
    }

    public static Elements select(String string2, Element element) {
        return new Selector(string2, element).select();
    }

    public static class SelectorParseException
    extends IllegalStateException {
        public /* varargs */ SelectorParseException(String string2, Object ... arrobject) {
            super(String.format(string2, arrobject));
        }
    }

}

