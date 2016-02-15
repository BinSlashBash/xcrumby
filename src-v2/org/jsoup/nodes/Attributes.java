/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.nodes;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;

public class Attributes
implements Iterable<Attribute>,
Cloneable {
    protected static final String dataPrefix = "data-";
    private LinkedHashMap<String, Attribute> attributes = null;

    private static String dataKey(String string2) {
        return "data-" + string2;
    }

    public void addAll(Attributes attributes) {
        if (attributes.size() == 0) {
            return;
        }
        if (this.attributes == null) {
            this.attributes = new LinkedHashMap(attributes.size());
        }
        this.attributes.putAll(attributes.attributes);
    }

    public List<Attribute> asList() {
        if (this.attributes == null) {
            return Collections.emptyList();
        }
        ArrayList<Attribute> arrayList = new ArrayList<Attribute>(this.attributes.size());
        Iterator<Map.Entry<String, Attribute>> iterator = this.attributes.entrySet().iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().getValue());
        }
        return Collections.unmodifiableList(arrayList);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Attributes clone() {
        Iterator<Attribute> iterator;
        void var1_2;
        Attributes attributes;
        if (this.attributes == null) {
            Attributes attributes2 = new Attributes();
            return var1_2;
        }
        try {
            attributes = (Attributes)super.clone();
            attributes.attributes = new LinkedHashMap(this.attributes.size());
            iterator = this.iterator();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
        do {
            Attributes attributes2 = attributes;
            if (!iterator.hasNext()) return var1_2;
            Attribute attribute = iterator.next();
            attributes.attributes.put((String)attribute.getKey(), (Attribute)attribute.clone());
        } while (true);
    }

    public Map<String, String> dataset() {
        return new Dataset();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Attributes)) {
            return false;
        }
        object = (Attributes)object;
        if (this.attributes != null) {
            if (this.attributes.equals(object.attributes)) return true;
            return false;
        }
        if (object.attributes == null) return true;
        return false;
    }

    public String get(String object) {
        Validate.notEmpty((String)object);
        if (this.attributes == null) {
            return "";
        }
        if ((object = this.attributes.get(object.toLowerCase())) != null) {
            return object.getValue();
        }
        return "";
    }

    public boolean hasKey(String string2) {
        if (this.attributes != null && this.attributes.containsKey(string2.toLowerCase())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.attributes != null) {
            return this.attributes.hashCode();
        }
        return 0;
    }

    public String html() {
        StringBuilder stringBuilder = new StringBuilder();
        this.html(stringBuilder, new Document("").outputSettings());
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    void html(StringBuilder stringBuilder, Document.OutputSettings outputSettings) {
        if (this.attributes != null) {
            Iterator<Map.Entry<String, Attribute>> iterator = this.attributes.entrySet().iterator();
            while (iterator.hasNext()) {
                Attribute attribute = iterator.next().getValue();
                stringBuilder.append(" ");
                attribute.html(stringBuilder, outputSettings);
            }
        }
    }

    @Override
    public Iterator<Attribute> iterator() {
        return this.asList().iterator();
    }

    public void put(String string2, String string3) {
        this.put(new Attribute(string2, string3));
    }

    public void put(Attribute attribute) {
        Validate.notNull(attribute);
        if (this.attributes == null) {
            this.attributes = new LinkedHashMap(2);
        }
        this.attributes.put((String)attribute.getKey(), attribute);
    }

    public void remove(String string2) {
        Validate.notEmpty(string2);
        if (this.attributes == null) {
            return;
        }
        this.attributes.remove(string2.toLowerCase());
    }

    public int size() {
        if (this.attributes == null) {
            return 0;
        }
        return this.attributes.size();
    }

    public String toString() {
        return this.html();
    }

    private class Dataset
    extends AbstractMap<String, String> {
        private Dataset() {
            if (Attributes.this.attributes == null) {
                Attributes.this.attributes = new LinkedHashMap(2);
            }
        }

        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return new EntrySet();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public String put(String object, String object2) {
            String string2 = Attributes.dataKey((String)object);
            object = Attributes.this.hasKey(string2) ? ((Attribute)Attributes.this.attributes.get(string2)).getValue() : null;
            object2 = new Attribute(string2, (String)object2);
            Attributes.this.attributes.put(string2, object2);
            return object;
        }

        private class DatasetIterator
        implements Iterator<Map.Entry<String, String>> {
            private Attribute attr;
            private Iterator<Attribute> attrIter;

            private DatasetIterator() {
                this.attrIter = Attributes.this.attributes.values().iterator();
            }

            @Override
            public boolean hasNext() {
                while (this.attrIter.hasNext()) {
                    this.attr = this.attrIter.next();
                    if (!this.attr.isDataAttribute()) continue;
                    return true;
                }
                return false;
            }

            @Override
            public Map.Entry<String, String> next() {
                return new Attribute(this.attr.getKey().substring("data-".length()), (String)this.attr.getValue());
            }

            @Override
            public void remove() {
                Attributes.this.attributes.remove(this.attr.getKey());
            }
        }

        private class EntrySet
        extends AbstractSet<Map.Entry<String, String>> {
            private EntrySet() {
            }

            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                return new DatasetIterator();
            }

            @Override
            public int size() {
                int n2 = 0;
                DatasetIterator datasetIterator = new DatasetIterator();
                while (datasetIterator.hasNext()) {
                    ++n2;
                }
                return n2;
            }
        }

    }

}

