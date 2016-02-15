/*
 * Decompiled with CFR 0_110.
 */
package in.uncod.android.bypass;

import in.uncod.android.bypass.Element;

public class Document {
    Element[] elements;

    public Document(Element[] arrelement) {
        this.elements = arrelement;
    }

    public Element getElement(int n2) {
        return this.elements[n2];
    }

    public int getElementCount() {
        return this.elements.length;
    }
}

