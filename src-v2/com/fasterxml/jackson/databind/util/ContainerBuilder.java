/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ContainerBuilder {
    private static final int MAX_BUF = 1000;
    private Object[] b;
    private List<Object> list;
    private Map<String, Object> map;
    private int start;
    private int tail;

    public ContainerBuilder(int n2) {
        this.b = new Object[n2 & -2];
    }

    /*
     * Enabled aggressive block sorting
     */
    private List<Object> _buildList(boolean bl2) {
        int n2;
        int n3 = this.tail - this.start;
        if (bl2) {
            n2 = n3;
            if (n3 < 2) {
                n2 = 2;
            }
        } else {
            n2 = n3 < 20 ? 20 : (n3 < 1000 ? n3 + (n3 >> 1) : n3 + (n3 >> 2));
        }
        ArrayList<Object> arrayList = new ArrayList<Object>(n2);
        n2 = this.start;
        do {
            if (n2 >= this.tail) {
                this.tail = this.start;
                return arrayList;
            }
            arrayList.add(this.b[n2]);
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Map<String, Object> _buildMap(boolean bl2) {
        int n2 = this.tail - this.start >> 1;
        n2 = bl2 ? (n2 <= 3 ? 4 : (n2 <= 40 ? (n2 += n2 >> 1) : (n2 += (n2 >> 2) + (n2 >> 4)))) : (n2 < 10 ? 16 : (n2 < 1000 ? (n2 += n2 >> 1) : (n2 += n2 / 3)));
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>(n2, 0.8f);
        n2 = this.start;
        do {
            if (n2 >= this.tail) {
                this.tail = this.start;
                return linkedHashMap;
            }
            linkedHashMap.put((String)this.b[n2], this.b[n2 + 1]);
            n2 += 2;
        } while (true);
    }

    private void _expandList(Object object) {
        if (this.b.length < 1000) {
            Object[] arrobject = this.b = Arrays.copyOf(this.b, this.b.length << 1);
            int n2 = this.tail;
            this.tail = n2 + 1;
            arrobject[n2] = object;
            return;
        }
        this.list = this._buildList(false);
        this.list.add(object);
    }

    private void _expandMap(String arrobject, Object object) {
        if (this.b.length < 1000) {
            Object[] arrobject2 = this.b = Arrays.copyOf(this.b, this.b.length << 1);
            int n2 = this.tail;
            this.tail = n2 + 1;
            arrobject2[n2] = arrobject;
            arrobject = this.b;
            n2 = this.tail;
            this.tail = n2 + 1;
            arrobject[n2] = object;
            return;
        }
        this.map = this._buildMap(false);
        this.map.put((String)arrobject, object);
    }

    public void add(Object object) {
        if (this.list != null) {
            this.list.add(object);
            return;
        }
        if (this.tail >= this.b.length) {
            this._expandList(object);
            return;
        }
        Object[] arrobject = this.b;
        int n2 = this.tail;
        this.tail = n2 + 1;
        arrobject[n2] = object;
    }

    public int bufferLength() {
        return this.b.length;
    }

    public boolean canReuse() {
        if (this.list == null && this.map == null) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Object[] finishArray(int n2) {
        Object[] arrobject;
        if (this.list == null) {
            arrobject = Arrays.copyOfRange(this.b, this.start, this.tail);
        } else {
            arrobject = this.list.toArray(new Object[this.tail - this.start]);
            this.list = null;
        }
        this.start = n2;
        return arrobject;
    }

    /*
     * Enabled aggressive block sorting
     */
    public <T> Object[] finishArray(int n2, Class<T> arrobject) {
        int n3 = this.tail - this.start;
        arrobject = (Object[])Array.newInstance(arrobject, n3);
        if (this.list == null) {
            System.arraycopy(this.b, this.start, arrobject, 0, n3);
        } else {
            arrobject = this.list.toArray(arrobject);
            this.list = null;
        }
        this.start = n2;
        return arrobject;
    }

    /*
     * Enabled aggressive block sorting
     */
    public List<Object> finishList(int n2) {
        List<Object> list = this.list;
        if (list == null) {
            list = this._buildList(true);
        } else {
            this.list = null;
        }
        this.start = n2;
        return list;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Map<String, Object> finishMap(int n2) {
        Map<String, Object> map = this.map;
        if (map == null) {
            map = this._buildMap(true);
        } else {
            this.map = null;
        }
        this.start = n2;
        return map;
    }

    public void put(String arrobject, Object object) {
        if (this.map != null) {
            this.map.put((String)arrobject, object);
            return;
        }
        if (this.tail + 2 > this.b.length) {
            this._expandMap((String)arrobject, object);
            return;
        }
        Object[] arrobject2 = this.b;
        int n2 = this.tail;
        this.tail = n2 + 1;
        arrobject2[n2] = arrobject;
        arrobject = this.b;
        n2 = this.tail;
        this.tail = n2 + 1;
        arrobject[n2] = object;
    }

    public int start() {
        if (this.list != null || this.map != null) {
            throw new IllegalStateException();
        }
        int n2 = this.start;
        this.start = this.tail;
        return n2;
    }

    public int startList(Object object) {
        if (this.list != null || this.map != null) {
            throw new IllegalStateException();
        }
        int n2 = this.start;
        this.start = this.tail;
        this.add(object);
        return n2;
    }

    public int startMap(String string2, Object object) {
        if (this.list != null || this.map != null) {
            throw new IllegalStateException();
        }
        int n2 = this.start;
        this.start = this.tail;
        this.put(string2, object);
        return n2;
    }
}

