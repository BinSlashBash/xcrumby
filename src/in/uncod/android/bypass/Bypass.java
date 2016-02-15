package in.uncod.android.bypass;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan.Standard;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;

public class Bypass
{
  private static final float[] HEADER_SIZES = { 1.5F, 1.4F, 1.3F, 1.2F, 1.1F, 1.0F };
  
  static
  {
    System.loadLibrary("bypass");
  }
  
  private native Document processMarkdown(String paramString);
  
  private CharSequence recurseElement(Element paramElement)
  {
    Object localObject = new CharSequence[paramElement.size()];
    int i = 0;
    while (i < paramElement.size())
    {
      localObject[i] = recurseElement(paramElement.children[i]);
      i += 1;
    }
    CharSequence localCharSequence = TextUtils.concat((CharSequence[])localObject);
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
    String str = paramElement.getText();
    localObject = str;
    if (paramElement.size() == 0)
    {
      localObject = str;
      if (paramElement.getParent().getType() != Element.Type.BLOCK_CODE) {
        localObject = str.replace('\n', ' ');
      }
    }
    if ((paramElement.getParent() != null) && (paramElement.getParent().getType() == Element.Type.LIST_ITEM) && (paramElement.getType() == Element.Type.LIST)) {
      localSpannableStringBuilder.append("\n");
    }
    if (paramElement.getType() == Element.Type.LIST_ITEM) {
      localSpannableStringBuilder.append("•");
    }
    localSpannableStringBuilder.append((CharSequence)localObject);
    localSpannableStringBuilder.append(localCharSequence);
    if ((paramElement.getType() == Element.Type.LIST) && (paramElement.getParent() != null))
    {
      if (paramElement.getType() != Element.Type.HEADER) {
        break label315;
      }
      i = Integer.parseInt(paramElement.getAttribute("level"));
      localSpannableStringBuilder.setSpan(new RelativeSizeSpan(HEADER_SIZES[i]), 0, localSpannableStringBuilder.length(), 33);
      localSpannableStringBuilder.setSpan(new StyleSpan(1), 0, localSpannableStringBuilder.length(), 33);
    }
    label315:
    do
    {
      return localSpannableStringBuilder;
      if (paramElement.getType() == Element.Type.LIST_ITEM)
      {
        if ((paramElement.size() > 0) && (paramElement.children[(paramElement.size() - 1)].isBlockElement())) {
          break;
        }
        localSpannableStringBuilder.append("\n");
        break;
      }
      if (!paramElement.isBlockElement()) {
        break;
      }
      localSpannableStringBuilder.append("\n\n");
      break;
      if ((paramElement.getType() == Element.Type.LIST_ITEM) && (paramElement.getParent().getParent() != null))
      {
        localSpannableStringBuilder.setSpan(new LeadingMarginSpan.Standard(20), 0, localSpannableStringBuilder.length(), 33);
        return localSpannableStringBuilder;
      }
      if (paramElement.getType() == Element.Type.EMPHASIS)
      {
        localSpannableStringBuilder.setSpan(new StyleSpan(2), 0, localSpannableStringBuilder.length(), 33);
        return localSpannableStringBuilder;
      }
      if (paramElement.getType() == Element.Type.DOUBLE_EMPHASIS)
      {
        localSpannableStringBuilder.setSpan(new StyleSpan(1), 0, localSpannableStringBuilder.length(), 33);
        return localSpannableStringBuilder;
      }
      if (paramElement.getType() == Element.Type.TRIPLE_EMPHASIS)
      {
        localSpannableStringBuilder.setSpan(new StyleSpan(3), 0, localSpannableStringBuilder.length(), 33);
        return localSpannableStringBuilder;
      }
      if (paramElement.getType() == Element.Type.CODE_SPAN)
      {
        localSpannableStringBuilder.setSpan(new TypefaceSpan("monospace"), 0, localSpannableStringBuilder.length(), 33);
        return localSpannableStringBuilder;
      }
      if (paramElement.getType() == Element.Type.LINK)
      {
        localSpannableStringBuilder.setSpan(new URLSpan(paramElement.getAttribute("link")), 0, localSpannableStringBuilder.length(), 33);
        return localSpannableStringBuilder;
      }
    } while (paramElement.getType() != Element.Type.BLOCK_QUOTE);
    localSpannableStringBuilder.setSpan(new QuoteSpan(), 0, localSpannableStringBuilder.length(), 33);
    localSpannableStringBuilder.setSpan(new StyleSpan(2), 0, localSpannableStringBuilder.length(), 33);
    return localSpannableStringBuilder;
  }
  
  public CharSequence markdownToSpannable(String paramString)
  {
    paramString = processMarkdown(paramString);
    CharSequence[] arrayOfCharSequence = new CharSequence[paramString.getElementCount()];
    int i = 0;
    while (i < paramString.getElementCount())
    {
      arrayOfCharSequence[i] = recurseElement(paramString.getElement(i));
      i += 1;
    }
    return TextUtils.concat(arrayOfCharSequence);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/in/uncod/android/bypass/Bypass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */