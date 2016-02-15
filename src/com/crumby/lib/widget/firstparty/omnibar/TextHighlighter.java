package com.crumby.lib.widget.firstparty.omnibar;

public class TextHighlighter
{
  public static void highlight(HighlightText paramHighlightText, String paramString1, String paramString2)
  {
    paramString1 = paramString1.trim().split(" ");
    int i = 0;
    int j = 0;
    paramString2 = paramString2.toLowerCase();
    while (i < paramString1.length)
    {
      paramString1[i] = paramString1[i].toLowerCase();
      j = paramString2.indexOf(paramString1[i], j);
      paramHighlightText.set(j, paramString1[i].length() + j);
      j += paramString1[i].length();
      i += 1;
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/com/crumby/lib/widget/firstparty/omnibar/TextHighlighter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */