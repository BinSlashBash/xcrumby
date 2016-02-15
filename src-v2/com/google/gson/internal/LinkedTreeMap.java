/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap.LinkedTreeMapIterator
 */
package com.google.gson.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class LinkedTreeMap<K, V>
extends AbstractMap<K, V>
implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Comparator<Comparable> NATURAL_ORDER;
    Comparator<? super K> comparator;
    private LinkedTreeMap<K, V> entrySet;
    final Node<K, V> header = new Node();
    private LinkedTreeMap<K, V> keySet;
    int modCount = 0;
    Node<K, V> root;
    int size = 0;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = !LinkedTreeMap.class.desiredAssertionStatus();
        $assertionsDisabled = bl2;
        NATURAL_ORDER = new Comparator<Comparable>(){

            @Override
            public int compare(Comparable comparable, Comparable comparable2) {
                return comparable.compareTo(comparable2);
            }
        };
    }

    public LinkedTreeMap() {
        this(NATURAL_ORDER);
    }

    /*
     * Enabled aggressive block sorting
     */
    public LinkedTreeMap(Comparator<? super K> comparator) {
        void var1_2;
        if (comparator == null) {
            Comparator<Comparable> comparator2 = NATURAL_ORDER;
        }
        this.comparator = var1_2;
    }

    private boolean equal(Object object, Object object2) {
        if (object == object2 || object != null && object.equals(object2)) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void rebalance(Node<K, V> node, boolean bl2) {
        while (node != null) {
            Node node2;
            Node node3 = node.left;
            Node node4 = node.right;
            int n2 = node3 != null ? node3.height : 0;
            int n3 = node4 != null ? node4.height : 0;
            int n4 = n2 - n3;
            if (n4 == -2) {
                node3 = node4.left;
                node2 = node4.right;
                n2 = node2 != null ? node2.height : 0;
                n3 = node3 != null ? node3.height : 0;
                if ((n2 = n3 - n2) == -1 || n2 == 0 && !bl2) {
                    this.rotateLeft(node);
                } else {
                    if (!$assertionsDisabled && n2 != 1) {
                        throw new AssertionError();
                    }
                    this.rotateRight(node4);
                    this.rotateLeft(node);
                }
                if (bl2) {
                    return;
                }
            } else if (n4 == 2) {
                node4 = node3.left;
                node2 = node3.right;
                n2 = node2 != null ? node2.height : 0;
                n3 = node4 != null ? node4.height : 0;
                if ((n2 = n3 - n2) == 1 || n2 == 0 && !bl2) {
                    this.rotateRight(node);
                } else {
                    if (!$assertionsDisabled && n2 != -1) {
                        throw new AssertionError();
                    }
                    this.rotateLeft(node3);
                    this.rotateRight(node);
                }
                if (bl2) return;
            } else if (n4 == 0) {
                node.height = n2 + 1;
                if (bl2) {
                    return;
                }
            } else {
                if (!$assertionsDisabled && n4 != -1 && n4 != 1) {
                    throw new AssertionError();
                }
                node.height = Math.max(n2, n3) + 1;
                if (!bl2) {
                    return;
                }
            }
            node = node.parent;
        }
    }

    private void replaceInParent(Node<K, V> node, Node<K, V> node2) {
        Node node3 = node.parent;
        node.parent = null;
        if (node2 != null) {
            node2.parent = node3;
        }
        if (node3 != null) {
            if (node3.left == node) {
                node3.left = node2;
                return;
            }
            if (!$assertionsDisabled && node3.right != node) {
                throw new AssertionError();
            }
            node3.right = node2;
            return;
        }
        this.root = node2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void rotateLeft(Node<K, V> node) {
        int n2 = 0;
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node3.left;
        Node node5 = node3.right;
        node.right = node4;
        if (node4 != null) {
            node4.parent = node;
        }
        this.replaceInParent(node, node3);
        node3.left = node;
        node.parent = node3;
        int n3 = node2 != null ? node2.height : 0;
        int n4 = node4 != null ? node4.height : 0;
        n4 = node.height = Math.max(n3, n4) + 1;
        n3 = n2;
        if (node5 != null) {
            n3 = node5.height;
        }
        node3.height = Math.max(n4, n3) + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void rotateRight(Node<K, V> node) {
        Node node2;
        int n2 = 0;
        Node node3 = node.left;
        Node node4 = node.right;
        Node node5 = node3.left;
        node.left = node2 = node3.right;
        if (node2 != null) {
            node2.parent = node;
        }
        this.replaceInParent(node, node3);
        node3.right = node;
        node.parent = node3;
        int n3 = node4 != null ? node4.height : 0;
        int n4 = node2 != null ? node2.height : 0;
        n4 = node.height = Math.max(n3, n4) + 1;
        n3 = n2;
        if (node5 != null) {
            n3 = node5.height;
        }
        node3.height = Math.max(n4, n3) + 1;
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }

    @Override
    public void clear() {
        Node<K, V> node;
        this.root = null;
        this.size = 0;
        ++this.modCount;
        node.prev = node = this.header;
        node.next = node;
    }

    @Override
    public boolean containsKey(Object object) {
        if (this.findByObject(object) != null) {
            return true;
        }
        return false;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        LinkedTreeMap<K, V> linkedTreeMap = this.entrySet;
        if (linkedTreeMap != null) {
            return linkedTreeMap;
        }
        this.entrySet = linkedTreeMap = new EntrySet();
        return linkedTreeMap;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    Node<K, V> find(K var1_1, boolean var2_2) {
        block6 : {
            var7_3 = null;
            var8_4 = this.comparator;
            var4_5 = this.root;
            var3_6 = 0;
            var5_7 = var4_5;
            if (var4_5 == null) ** GOTO lbl15
            var6_8 = var8_4 == LinkedTreeMap.NATURAL_ORDER ? (Comparable)var1_1 /* !! */  : null;
            do {
                var3_6 = var6_8 != null ? var6_8.compareTo(var4_5.key) : var8_4.compare(var1_1 /* !! */ , var4_5.key);
                if (var3_6 == 0) {
                    return var4_5;
                }
                var5_7 = var3_6 < 0 ? var4_5.left : var4_5.right;
                if (var5_7 == null) {
                    var5_7 = var4_5;
lbl15: // 2 sources:
                    var4_5 = var7_3;
                    if (var2_2 == false) return var4_5;
                    var4_5 = this.header;
                    if (var5_7 == null) {
                        if (var8_4 != LinkedTreeMap.NATURAL_ORDER || var1_1 /* !! */  instanceof Comparable) break;
                        throw new ClassCastException(var1_1 /* !! */ .getClass().getName() + " is not Comparable");
                    }
                    break block6;
                }
                var4_5 = var5_7;
            } while (true);
            this.root = var1_1 /* !! */  = new Node<Node<K, V>, V>(var5_7, var1_1 /* !! */ , var4_5, var4_5.prev);
            ** GOTO lbl33
        }
        var1_1 /* !! */  = new Node<Node<K, V>, V>(var5_7, var1_1 /* !! */ , var4_5, var4_5.prev);
        if (var3_6 < 0) {
            var5_7.left = var1_1 /* !! */ ;
        } else {
            var5_7.right = var1_1 /* !! */ ;
        }
        this.rebalance(var5_7, true);
lbl33: // 2 sources:
        ++this.size;
        ++this.modCount;
        return var1_1 /* !! */ ;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Node<K, V> findByEntry(Map.Entry<?, ?> entry) {
        Node<K, V> node = this.findByObject(entry.getKey());
        if (node == null || !this.equal(node.value, entry.getValue())) return null;
        return node;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Node<K, V> findByObject(Object object) {
        Node<Object, V> node = null;
        if (object == null) return node;
        try {
            return this.find(object, false);
        }
        catch (ClassCastException classCastException) {
            return null;
        }
    }

    @Override
    public V get(Object node) {
        if ((node = this.findByObject(node)) != null) {
            return node.value;
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        LinkedTreeMap<K, V> linkedTreeMap = this.keySet;
        if (linkedTreeMap != null) {
            return linkedTreeMap;
        }
        this.keySet = linkedTreeMap = new KeySet();
        return linkedTreeMap;
    }

    @Override
    public V put(K object, V v2) {
        if (object == null) {
            throw new NullPointerException("key == null");
        }
        object = this.find(object, true);
        Object v3 = object.value;
        object.value = v2;
        return v3;
    }

    @Override
    public V remove(Object node) {
        if ((node = this.removeInternalByKey(node)) != null) {
            return node.value;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    void removeInternal(Node<K, V> node, boolean bl2) {
        if (bl2) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node.parent;
        if (node2 != null && node3 != null) {
            node2 = node2.height > node3.height ? node2.last() : node3.first();
            this.removeInternal(node2, false);
            int n2 = 0;
            node3 = node.left;
            if (node3 != null) {
                n2 = node3.height;
                node2.left = node3;
                node3.parent = node2;
                node.left = null;
            }
            int n3 = 0;
            node3 = node.right;
            if (node3 != null) {
                n3 = node3.height;
                node2.right = node3;
                node3.parent = node2;
                node.right = null;
            }
            node2.height = Math.max(n2, n3) + 1;
            this.replaceInParent(node, node2);
            return;
        }
        if (node2 != null) {
            this.replaceInParent(node, node2);
            node.left = null;
        } else if (node3 != null) {
            this.replaceInParent(node, node3);
            node.right = null;
        } else {
            this.replaceInParent(node, null);
        }
        this.rebalance(node4, false);
        --this.size;
        ++this.modCount;
    }

    Node<K, V> removeInternalByKey(Object node) {
        if ((node = this.findByObject(node)) != null) {
            this.removeInternal(node, true);
        }
        return node;
    }

    @Override
    public int size() {
        return this.size;
    }

    class EntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override
        public void clear() {
            LinkedTreeMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            if (object instanceof Map.Entry && LinkedTreeMap.this.findByEntry((Map.Entry)object) != null) {
                return true;
            }
            return false;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new com.google.gson.internal.LinkedTreeMap.LinkedTreeMapIterator<Map.Entry<K, V>>(){

                public Map.Entry<K, V> next() {
                    return this.nextNode();
                }
            };
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean remove(Object node) {
            if (!(node instanceof Map.Entry) || (node = LinkedTreeMap.this.findByEntry(node)) == null) {
                return false;
            }
            LinkedTreeMap.this.removeInternal(node, true);
            return true;
        }

        @Override
        public int size() {
            return LinkedTreeMap.this.size;
        }

    }

    class KeySet
    extends AbstractSet<K> {
        KeySet() {
        }

        @Override
        public void clear() {
            LinkedTreeMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return LinkedTreeMap.this.containsKey(object);
        }

        @Override
        public Iterator<K> iterator() {
            return new LinkedTreeMap<K, V>(){

                public K next() {
                    return this.nextNode().key;
                }
            };
        }

        @Override
        public boolean remove(Object object) {
            if (LinkedTreeMap.this.removeInternalByKey(object) != null) {
                return true;
            }
            return false;
        }

        @Override
        public int size() {
            return LinkedTreeMap.this.size;
        }

    }

    private abstract class LinkedTreeMapIterator<T>
    implements Iterator<T> {
        int expectedModCount;
        Node<K, V> lastReturned;
        Node<K, V> next;

        private LinkedTreeMapIterator() {
            this.next = LinkedTreeMap.this.header.next;
            this.lastReturned = null;
            this.expectedModCount = LinkedTreeMap.this.modCount;
        }

        @Override
        public final boolean hasNext() {
            if (this.next != LinkedTreeMap.this.header) {
                return true;
            }
            return false;
        }

        final Node<K, V> nextNode() {
            Node<K, V> node = this.next;
            if (node == LinkedTreeMap.this.header) {
                throw new NoSuchElementException();
            }
            if (LinkedTreeMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            this.next = node.next;
            this.lastReturned = node;
            return node;
        }

        @Override
        public final void remove() {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            LinkedTreeMap.this.removeInternal(this.lastReturned, true);
            this.lastReturned = null;
            this.expectedModCount = LinkedTreeMap.this.modCount;
        }
    }

    static final class Node<K, V>
    implements Map.Entry<K, V> {
        int height;
        final K key;
        Node<K, V> left;
        Node<K, V> next;
        Node<K, V> parent;
        Node<K, V> prev;
        Node<K, V> right;
        V value;

        Node() {
            this.key = null;
            this.prev = this;
            this.next = this;
        }

        Node(Node<K, V> node, K k2, Node<K, V> node2, Node<K, V> node3) {
            this.parent = node;
            this.key = k2;
            this.height = 1;
            this.next = node2;
            this.prev = node3;
            node3.next = this;
            node2.prev = this;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean equals(Object object) {
            boolean bl2;
            boolean bl3 = bl2 = false;
            if (!(object instanceof Map.Entry)) return bl3;
            object = (Map.Entry)object;
            if (this.key == null) {
                bl3 = bl2;
                if (object.getKey() != null) return bl3;
            } else {
                bl3 = bl2;
                if (!this.key.equals(object.getKey())) return bl3;
            }
            if (this.value == null) {
                bl3 = bl2;
                if (object.getValue() != null) return bl3;
                return true;
            } else {
                bl3 = bl2;
                if (!this.value.equals(object.getValue())) return bl3;
            }
            return true;
        }

        public Node<K, V> first() {
            Node<K, V> node = this;
            Node<K, V> node2 = node.left;
            while (node2 != null) {
                node = node2;
                node2 = node.left;
            }
            return node;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int hashCode() {
            int n2 = 0;
            int n3 = this.key == null ? 0 : this.key.hashCode();
            if (this.value == null) {
                return n3 ^ n2;
            }
            n2 = this.value.hashCode();
            return n3 ^ n2;
        }

        public Node<K, V> last() {
            Node<K, V> node = this;
            Node<K, V> node2 = node.right;
            while (node2 != null) {
                node = node2;
                node2 = node.right;
            }
            return node;
        }

        @Override
        public V setValue(V v2) {
            V v3 = this.value;
            this.value = v2;
            return v3;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }
    }

}

