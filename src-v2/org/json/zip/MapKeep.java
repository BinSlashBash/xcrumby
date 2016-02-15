/*
 * Decompiled with CFR 0_110.
 */
package org.json.zip;

import java.util.HashMap;
import org.json.Kim;
import org.json.zip.JSONzip;
import org.json.zip.Keep;
import org.json.zip.PostMortem;

class MapKeep
extends Keep {
    private Object[] list;
    private HashMap map;

    public MapKeep(int n2) {
        super(n2);
        this.list = new Object[this.capacity];
        this.map = new HashMap(this.capacity);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void compact() {
        int n2 = 0;
        for (int i2 = 0; i2 < this.capacity; ++i2) {
            Object object = this.list[i2];
            long l2 = MapKeep.age(this.uses[i2]);
            if (l2 > 0) {
                this.uses[n2] = l2;
                this.list[n2] = object;
                this.map.put(object, new Integer(n2));
                ++n2;
                continue;
            }
            this.map.remove(object);
        }
        if (n2 < this.capacity) {
            this.length = n2;
        } else {
            this.map.clear();
            this.length = 0;
        }
        this.power = 0;
    }

    public int find(Object object) {
        if ((object = this.map.get(object)) instanceof Integer) {
            return (Integer)object;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean postMortem(PostMortem object) {
        MapKeep mapKeep = (MapKeep)object;
        if (this.length != mapKeep.length) {
            JSONzip.log(new StringBuffer().append(this.length).append(" <> ").append(mapKeep.length).toString());
            return false;
        }
        int n2 = 0;
        while (n2 < this.length) {
            boolean bl2;
            if (this.list[n2] instanceof Kim) {
                bl2 = ((Kim)this.list[n2]).equals(mapKeep.list[n2]);
            } else {
                Object object2 = this.list[n2];
                Object object3 = mapKeep.list[n2];
                object = object2;
                if (object2 instanceof Number) {
                    object = object2.toString();
                }
                object2 = object3;
                if (object3 instanceof Number) {
                    object2 = object3.toString();
                }
                bl2 = object.equals(object2);
            }
            if (!bl2) {
                JSONzip.log(new StringBuffer().append("\n[").append(n2).append("]\n ").append(this.list[n2]).append("\n ").append(mapKeep.list[n2]).append("\n ").append(this.uses[n2]).append("\n ").append(mapKeep.uses[n2]).toString());
                return false;
            }
            ++n2;
        }
        return true;
    }

    public void register(Object object) {
        if (this.length >= this.capacity) {
            this.compact();
        }
        this.list[this.length] = object;
        this.map.put(object, new Integer(this.length));
        this.uses[this.length] = 1;
        ++this.length;
    }

    @Override
    public Object value(int n2) {
        return this.list[n2];
    }
}

