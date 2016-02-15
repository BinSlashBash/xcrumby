package com.fasterxml.jackson.databind.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ContainerBuilder {
    private static final int MAX_BUF = 1000;
    private Object[] f8b;
    private List<Object> list;
    private Map<String, Object> map;
    private int start;
    private int tail;

    public ContainerBuilder(int bufSize) {
        this.f8b = new Object[(bufSize & -2)];
    }

    public boolean canReuse() {
        return this.list == null && this.map == null;
    }

    public int bufferLength() {
        return this.f8b.length;
    }

    public int start() {
        if (this.list == null && this.map == null) {
            int prevStart = this.start;
            this.start = this.tail;
            return prevStart;
        }
        throw new IllegalStateException();
    }

    public int startList(Object value) {
        if (this.list == null && this.map == null) {
            int prevStart = this.start;
            this.start = this.tail;
            add(value);
            return prevStart;
        }
        throw new IllegalStateException();
    }

    public int startMap(String key, Object value) {
        if (this.list == null && this.map == null) {
            int prevStart = this.start;
            this.start = this.tail;
            put(key, value);
            return prevStart;
        }
        throw new IllegalStateException();
    }

    public void add(Object value) {
        if (this.list != null) {
            this.list.add(value);
        } else if (this.tail >= this.f8b.length) {
            _expandList(value);
        } else {
            Object[] objArr = this.f8b;
            int i = this.tail;
            this.tail = i + 1;
            objArr[i] = value;
        }
    }

    public void put(String key, Object value) {
        if (this.map != null) {
            this.map.put(key, value);
        } else if (this.tail + 2 > this.f8b.length) {
            _expandMap(key, value);
        } else {
            Object[] objArr = this.f8b;
            int i = this.tail;
            this.tail = i + 1;
            objArr[i] = key;
            objArr = this.f8b;
            i = this.tail;
            this.tail = i + 1;
            objArr[i] = value;
        }
    }

    public List<Object> finishList(int prevStart) {
        List<Object> l = this.list;
        if (l == null) {
            l = _buildList(true);
        } else {
            this.list = null;
        }
        this.start = prevStart;
        return l;
    }

    public Object[] finishArray(int prevStart) {
        Object[] result;
        if (this.list == null) {
            result = Arrays.copyOfRange(this.f8b, this.start, this.tail);
        } else {
            result = this.list.toArray(new Object[(this.tail - this.start)]);
            this.list = null;
        }
        this.start = prevStart;
        return result;
    }

    public <T> Object[] finishArray(int prevStart, Class<T> elemType) {
        int size = this.tail - this.start;
        T[] result = (Object[]) ((Object[]) Array.newInstance(elemType, size));
        if (this.list == null) {
            System.arraycopy(this.f8b, this.start, result, 0, size);
        } else {
            result = this.list.toArray(result);
            this.list = null;
        }
        this.start = prevStart;
        return result;
    }

    public Map<String, Object> finishMap(int prevStart) {
        Map<String, Object> m = this.map;
        if (m == null) {
            m = _buildMap(true);
        } else {
            this.map = null;
        }
        this.start = prevStart;
        return m;
    }

    private void _expandList(Object value) {
        if (this.f8b.length < MAX_BUF) {
            this.f8b = Arrays.copyOf(this.f8b, this.f8b.length << 1);
            Object[] objArr = this.f8b;
            int i = this.tail;
            this.tail = i + 1;
            objArr[i] = value;
            return;
        }
        this.list = _buildList(false);
        this.list.add(value);
    }

    private List<Object> _buildList(boolean isComplete) {
        int currLen = this.tail - this.start;
        if (isComplete) {
            if (currLen < 2) {
                currLen = 2;
            }
        } else if (currLen < 20) {
            currLen = 20;
        } else if (currLen < MAX_BUF) {
            currLen += currLen >> 1;
        } else {
            currLen += currLen >> 2;
        }
        List<Object> l = new ArrayList(currLen);
        for (int i = this.start; i < this.tail; i++) {
            l.add(this.f8b[i]);
        }
        this.tail = this.start;
        return l;
    }

    private void _expandMap(String key, Object value) {
        if (this.f8b.length < MAX_BUF) {
            this.f8b = Arrays.copyOf(this.f8b, this.f8b.length << 1);
            Object[] objArr = this.f8b;
            int i = this.tail;
            this.tail = i + 1;
            objArr[i] = key;
            objArr = this.f8b;
            i = this.tail;
            this.tail = i + 1;
            objArr[i] = value;
            return;
        }
        this.map = _buildMap(false);
        this.map.put(key, value);
    }

    private Map<String, Object> _buildMap(boolean isComplete) {
        int size = (this.tail - this.start) >> 1;
        if (isComplete) {
            if (size <= 3) {
                size = 4;
            } else if (size <= 40) {
                size += size >> 1;
            } else {
                size += (size >> 2) + (size >> 4);
            }
        } else if (size < 10) {
            size = 16;
        } else if (size < MAX_BUF) {
            size += size >> 1;
        } else {
            size += size / 3;
        }
        Map<String, Object> m = new LinkedHashMap(size, 0.8f);
        for (int i = this.start; i < this.tail; i += 2) {
            m.put((String) this.f8b[i], this.f8b[i + 1]);
        }
        this.tail = this.start;
        return m;
    }
}
