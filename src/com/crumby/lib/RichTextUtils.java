package com.crumby.lib;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.CharacterStyle;

public class RichTextUtils
{
  public static <A extends CharacterStyle, B extends CharacterStyle> Spannable replaceAll(Spanned paramSpanned, Class<A> paramClass, SpanConverter<A, B> paramSpanConverter)
  {
    paramSpanned = new SpannableString(paramSpanned);
    paramClass = (CharacterStyle[])paramSpanned.getSpans(0, paramSpanned.length(), paramClass);
    int j = paramClass.length;
    int i = 0;
    while (i < j)
    {
      Object localObject = paramClass[i];
      int k = paramSpanned.getSpanStart(localObject);
      int m = paramSpanned.getSpanEnd(localObject);
      int n = paramSpanned.getSpanFlags(localObject);
      paramSpanned.removeSpan(localObject);
      paramSpanned.setSpan(paramSpanConverter.convert((CharacterStyle)localObject), k, m, n);
      i += 1;
    }
    return paramSpanned;
  }
  
  public static abstract interface SpanConverter<A extends CharacterStyle, B extends CharacterStyle>
  {
    public abstract B convert(A paramA);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/RichTextUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */