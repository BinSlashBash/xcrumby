package in.uncod.android.bypass;

public class Document
{
  Element[] elements;
  
  public Document(Element[] paramArrayOfElement)
  {
    this.elements = paramArrayOfElement;
  }
  
  public Element getElement(int paramInt)
  {
    return this.elements[paramInt];
  }
  
  public int getElementCount()
  {
    return this.elements.length;
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/in/uncod/android/bypass/Document.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */