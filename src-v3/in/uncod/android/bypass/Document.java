package in.uncod.android.bypass;

public class Document {
    Element[] elements;

    public Document(Element[] elements) {
        this.elements = elements;
    }

    public int getElementCount() {
        return this.elements.length;
    }

    public Element getElement(int pos) {
        return this.elements[pos];
    }
}
