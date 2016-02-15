/*
 * Decompiled with CFR 0_110.
 */
package org.json.zip;

import org.json.Kim;
import org.json.zip.JSONzip;
import org.json.zip.Keep;
import org.json.zip.PostMortem;

class TrieKeep
extends Keep {
    private int[] froms;
    private Kim[] kims;
    private Node root;
    private int[] thrus;

    public TrieKeep(int n2) {
        super(n2);
        this.froms = new int[this.capacity];
        this.thrus = new int[this.capacity];
        this.kims = new Kim[this.capacity];
        this.root = new Node(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Kim kim(int n2) {
        Kim kim;
        Kim kim2 = this.kims[n2];
        int n3 = this.froms[n2];
        int n4 = this.thrus[n2];
        if (n3 == 0) {
            kim = kim2;
            if (n4 == kim2.length) return kim;
        }
        kim = new Kim(kim2, n3, n4);
        this.froms[n2] = 0;
        this.thrus[n2] = kim.length;
        this.kims[n2] = kim;
        return kim;
    }

    public int length(int n2) {
        return this.thrus[n2] - this.froms[n2];
    }

    public int match(Kim kim, int n2, int n3) {
        Node node = this.root;
        int n4 = -1;
        int n5 = n2;
        while (n5 < n3 && (node = node.get(kim.get(n5))) != null) {
            if (Node.access$000(node) != -1) {
                n4 = Node.access$000(node);
            }
            ++n2;
            ++n5;
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean postMortem(PostMortem postMortem) {
        boolean bl2 = true;
        postMortem = (TrieKeep)postMortem;
        if (this.length != postMortem.length) {
            JSONzip.log(new StringBuffer().append("\nLength ").append(this.length).append(" <> ").append(postMortem.length).toString());
            return false;
        } else {
            if (this.capacity != postMortem.capacity) {
                JSONzip.log(new StringBuffer().append("\nCapacity ").append(this.capacity).append(" <> ").append(postMortem.capacity).toString());
                return false;
            }
            for (int i2 = 0; i2 < this.length; ++i2) {
                Kim kim;
                Kim kim2 = this.kim(i2);
                if (kim2.equals(kim = postMortem.kim(i2))) continue;
                JSONzip.log(new StringBuffer().append("\n[").append(i2).append("] ").append(kim2).append(" <> ").append(kim).toString());
                bl2 = false;
            }
            if (!bl2 || !this.root.postMortem(postMortem.root)) return false;
            return true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void registerMany(Kim kim) {
        int n2;
        int n3 = kim.length;
        int n4 = n2 = this.capacity - this.length;
        if (n2 > 40) {
            n4 = 40;
        }
        n2 = 0;
        int n5 = n4;
        n4 = n2;
        while (n4 < n3 - 2) {
            int n6;
            n2 = n6 = n3 - n4;
            if (n6 > 10) {
                n2 = 10;
            }
            Node node = this.root;
            for (int i2 = n4; i2 < n2 + n4; ++i2) {
                node = node.vet(kim.get(i2));
                n6 = n5;
                if (Node.access$000(node) == -1) {
                    n6 = n5--;
                    if (i2 - n4 >= 2) {
                        Node.access$002(node, this.length);
                        this.uses[this.length] = 1;
                        this.kims[this.length] = kim;
                        this.froms[this.length] = n4;
                        this.thrus[this.length] = i2 + 1;
                        ++this.length;
                        n6 = n5;
                        if (n5 <= 0) {
                            return;
                        }
                    }
                }
                n5 = n6;
            }
            ++n4;
        }
    }

    public int registerOne(Kim kim, int n2, int n3) {
        int n4;
        int n5 = n4 = -1;
        if (this.length < this.capacity) {
            Node node = this.root;
            for (n5 = n2; n5 < n3; ++n5) {
                node = node.vet(kim.get(n5));
            }
            n5 = n4;
            if (Node.access$000(node) == -1) {
                n5 = this.length++;
                Node.access$002(node, n5);
                this.uses[n5] = 1;
                this.kims[n5] = kim;
                this.froms[n5] = n2;
                this.thrus[n5] = n3;
            }
        }
        return n5;
    }

    public void registerOne(Kim kim) {
        int n2 = this.registerOne(kim, 0, kim.length);
        if (n2 != -1) {
            this.kims[n2] = kim;
        }
    }

    public void reserve() {
        if (this.capacity - this.length < 40) {
            int n2;
            int n3 = 0;
            this.root = new Node(this);
            for (n2 = 0; n2 < this.capacity; ++n2) {
                int n4 = n3;
                if (this.uses[n2] > 1) {
                    Kim kim = this.kims[n2];
                    int n5 = this.thrus[n2];
                    Node node = this.root;
                    for (n4 = this.froms[n2]; n4 < n5; ++n4) {
                        node = node.vet(kim.get(n4));
                    }
                    Node.access$002(node, n3);
                    this.uses[n3] = TrieKeep.age(this.uses[n2]);
                    this.froms[n3] = this.froms[n2];
                    this.thrus[n3] = n5;
                    this.kims[n3] = kim;
                    n4 = n3 + 1;
                }
                n3 = n4;
            }
            n2 = n3;
            if (this.capacity - n3 < 40) {
                this.power = 0;
                this.root = new Node(this);
                n2 = 0;
            }
            this.length = n2;
            while (n2 < this.capacity) {
                this.uses[n2] = 0;
                this.kims[n2] = null;
                this.froms[n2] = 0;
                this.thrus[n2] = 0;
                ++n2;
            }
        }
    }

    @Override
    public Object value(int n2) {
        return this.kim(n2);
    }

    class Node
    implements PostMortem {
        private int integer;
        private Node[] next;
        private final TrieKeep this$0;

        public Node(TrieKeep trieKeep) {
            this.this$0 = trieKeep;
            this.integer = -1;
            this.next = null;
        }

        static int access$000(Node node) {
            return node.integer;
        }

        static int access$002(Node node, int n2) {
            node.integer = n2;
            return n2;
        }

        public Node get(byte by2) {
            return this.get(by2 & 255);
        }

        public Node get(int n2) {
            if (this.next == null) {
                return null;
            }
            return this.next[n2];
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public boolean postMortem(PostMortem postMortem) {
            if ((postMortem = (Node)postMortem) == null) {
                JSONzip.log("\nMisalign");
                return false;
            }
            if (this.integer != postMortem.integer) {
                JSONzip.log(new StringBuffer().append("\nInteger ").append(this.integer).append(" <> ").append(postMortem.integer).toString());
                return false;
            }
            if (this.next == null) {
                if (postMortem.next == null) {
                    return true;
                }
                JSONzip.log(new StringBuffer().append("\nNext is null ").append(this.integer).toString());
                return false;
            }
            int n2 = 0;
            while (n2 < 256) {
                Node node = this.next[n2];
                if (node != null) {
                    if (!node.postMortem(postMortem.next[n2])) return false;
                } else if (postMortem.next[n2] != null) {
                    JSONzip.log(new StringBuffer().append("\nMisalign ").append(n2).toString());
                    return false;
                }
                ++n2;
            }
            return true;
        }

        public void set(byte by2, Node node) {
            this.set(by2 & 255, node);
        }

        public void set(int n2, Node node) {
            if (this.next == null) {
                this.next = new Node[256];
            }
            this.next[n2] = node;
        }

        public Node vet(byte by2) {
            return this.vet(by2 & 255);
        }

        public Node vet(int n2) {
            Node node;
            Node node2 = node = this.get(n2);
            if (node == null) {
                node2 = new Node(this.this$0);
                this.set(n2, node2);
            }
            return node2;
        }
    }

}

