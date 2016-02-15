package com.fasterxml.jackson.core.sym;

import com.crumby.impl.crumby.UnsupportedUrlFragment;

public final class Name1 extends Name {
    private static final Name1 EMPTY;
    private final int f60q;

    static {
        EMPTY = new Name1(UnsupportedUrlFragment.DISPLAY_NAME, 0, 0);
    }

    Name1(String name, int hash, int quad) {
        super(name, hash);
        this.f60q = quad;
    }

    public static Name1 getEmptyName() {
        return EMPTY;
    }

    public boolean equals(int quad) {
        return quad == this.f60q;
    }

    public boolean equals(int quad1, int quad2) {
        return quad1 == this.f60q && quad2 == 0;
    }

    public boolean equals(int[] quads, int qlen) {
        return qlen == 1 && quads[0] == this.f60q;
    }
}
