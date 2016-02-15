/*
 * Decompiled with CFR 0_110.
 */
package oauth.signpost.http;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import oauth.signpost.OAuth;

public class HttpParameters
implements Map<String, SortedSet<String>>,
Serializable {
    private TreeMap<String, SortedSet<String>> wrappedMap = new TreeMap();

    @Override
    public void clear() {
        this.wrappedMap.clear();
    }

    @Override
    public boolean containsKey(Object object) {
        return this.wrappedMap.containsKey(object);
    }

    @Override
    public boolean containsValue(Object object) {
        Iterator<SortedSet<String>> iterator = this.wrappedMap.values().iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().contains(object)) continue;
            return true;
        }
        return false;
    }

    @Override
    public Set<Map.Entry<String, SortedSet<String>>> entrySet() {
        return this.wrappedMap.entrySet();
    }

    @Override
    public SortedSet<String> get(Object object) {
        return this.wrappedMap.get(object);
    }

    public String getAsHeaderElement(String string2) {
        String string3 = this.getFirst(string2);
        if (string3 == null) {
            return null;
        }
        return string2 + "=\"" + string3 + "\"";
    }

    public String getAsQueryString(Object object) {
        return this.getAsQueryString(object, true);
    }

    public String getAsQueryString(Object iterator, boolean bl2) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator iterator2 = iterator;
        if (bl2) {
            iterator2 = OAuth.percentEncode((String)((Object)iterator));
        }
        if ((iterator = (Set)this.wrappedMap.get(iterator2)) == null) {
            return iterator2 + "=";
        }
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator2 + "=" + (String)iterator.next());
            if (!iterator.hasNext()) continue;
            stringBuilder.append("&");
        }
        return stringBuilder.toString();
    }

    public String getFirst(Object object) {
        return this.getFirst(object, false);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getFirst(Object object, boolean bl2) {
        String string2;
        if ((object = this.wrappedMap.get(object)) == null) return null;
        if (object.isEmpty()) {
            return null;
        }
        object = string2 = object.first();
        if (!bl2) return object;
        return OAuth.percentDecode(string2);
    }

    public HttpParameters getOAuthParameters() {
        HttpParameters httpParameters = new HttpParameters();
        for (Map.Entry<String, SortedSet<String>> entry : this.entrySet()) {
            String string2 = entry.getKey();
            if (!string2.startsWith("oauth_") && !string2.startsWith("x_oauth_")) continue;
            httpParameters.put(string2, entry.getValue());
        }
        return httpParameters;
    }

    @Override
    public boolean isEmpty() {
        return this.wrappedMap.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return this.wrappedMap.keySet();
    }

    @Override
    public String put(String string2, String string3) {
        return this.put(string2, string3, false);
    }

    public String put(String object, String string2, boolean bl2) {
        String string3 = object;
        if (bl2) {
            string3 = OAuth.percentEncode((String)object);
        }
        Object object2 = object = this.wrappedMap.get(string3);
        if (object == null) {
            object2 = new TreeSet<Object>();
            this.wrappedMap.put(string3, (SortedSet<String>)object2);
        }
        object = string2;
        if (string2 != null) {
            object = string2;
            if (bl2) {
                object = OAuth.percentEncode(string2);
            }
            object2.add((Object)object);
        }
        return object;
    }

    @Override
    public SortedSet<String> put(String string2, SortedSet<String> sortedSet) {
        return this.wrappedMap.put(string2, sortedSet);
    }

    public SortedSet<String> put(String string2, SortedSet<String> object, boolean bl2) {
        if (bl2) {
            this.remove(string2);
            object = object.iterator();
            while (object.hasNext()) {
                this.put(string2, (String)object.next(), true);
            }
            return this.get(string2);
        }
        return this.wrappedMap.put(string2, (SortedSet<String>)object);
    }

    @Override
    public void putAll(Map<? extends String, ? extends SortedSet<String>> map) {
        this.wrappedMap.putAll(map);
    }

    public void putAll(Map<? extends String, ? extends SortedSet<String>> map, boolean bl2) {
        if (bl2) {
            for (String string2 : map.keySet()) {
                this.put(string2, map.get(string2), true);
            }
        } else {
            this.wrappedMap.putAll(map);
        }
    }

    public void putAll(String[] arrstring, boolean bl2) {
        for (int i2 = 0; i2 < arrstring.length - 1; i2 += 2) {
            this.put(arrstring[i2], arrstring[i2 + 1], bl2);
        }
    }

    public void putMap(Map<String, List<String>> map) {
        for (String string2 : map.keySet()) {
            TreeSet<String> treeSet;
            TreeSet<String> treeSet2 = treeSet = this.get(string2);
            if (treeSet == null) {
                treeSet2 = new TreeSet<String>();
                this.put(string2, treeSet2);
            }
            treeSet2.addAll((Collection)map.get(string2));
        }
    }

    public String putNull(String string2, String string3) {
        return this.put(string2, string3);
    }

    @Override
    public SortedSet<String> remove(Object object) {
        return this.wrappedMap.remove(object);
    }

    @Override
    public int size() {
        int n2 = 0;
        for (String string2 : this.wrappedMap.keySet()) {
            n2 += this.wrappedMap.get(string2).size();
        }
        return n2;
    }

    @Override
    public Collection<SortedSet<String>> values() {
        return this.wrappedMap.values();
    }
}

