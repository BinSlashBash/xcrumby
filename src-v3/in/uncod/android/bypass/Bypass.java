package in.uncod.android.bypass;

import android.text.TextUtils;
import com.crumby.GalleryViewer;

public class Bypass {
    private static final float[] HEADER_SIZES;

    private native Document processMarkdown(String str);

    static {
        System.loadLibrary("bypass");
        HEADER_SIZES = new float[]{1.5f, 1.4f, 1.3f, 1.2f, 1.1f, GalleryViewer.PROGRESS_COMPLETED};
    }

    public CharSequence markdownToSpannable(String markdown) {
        Document document = processMarkdown(markdown);
        CharSequence[] spans = new CharSequence[document.getElementCount()];
        for (int i = 0; i < document.getElementCount(); i++) {
            spans[i] = recurseElement(document.getElement(i));
        }
        return TextUtils.concat(spans);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.CharSequence recurseElement(in.uncod.android.bypass.Element r23) {
        /*
        r22 = this;
        r18 = r23.size();
        r0 = r18;
        r15 = new java.lang.CharSequence[r0];
        r8 = 0;
    L_0x0009:
        r18 = r23.size();
        r0 = r18;
        if (r8 >= r0) goto L_0x0026;
    L_0x0011:
        r0 = r23;
        r0 = r0.children;
        r18 = r0;
        r18 = r18[r8];
        r0 = r22;
        r1 = r18;
        r18 = r0.recurseElement(r1);
        r15[r8] = r18;
        r8 = r8 + 1;
        goto L_0x0009;
    L_0x0026:
        r7 = android.text.TextUtils.concat(r15);
        r6 = new android.text.SpannableStringBuilder;
        r6.<init>();
        r16 = r23.getText();
        r18 = r23.size();
        if (r18 != 0) goto L_0x0057;
    L_0x0039:
        r18 = r23.getParent();
        r18 = r18.getType();
        r19 = in.uncod.android.bypass.Element.Type.BLOCK_CODE;
        r0 = r18;
        r1 = r19;
        if (r0 == r1) goto L_0x0057;
    L_0x0049:
        r18 = 10;
        r19 = 32;
        r0 = r16;
        r1 = r18;
        r2 = r19;
        r16 = r0.replace(r1, r2);
    L_0x0057:
        r18 = r23.getParent();
        if (r18 == 0) goto L_0x0080;
    L_0x005d:
        r18 = r23.getParent();
        r18 = r18.getType();
        r19 = in.uncod.android.bypass.Element.Type.LIST_ITEM;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x0080;
    L_0x006d:
        r18 = r23.getType();
        r19 = in.uncod.android.bypass.Element.Type.LIST;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x0080;
    L_0x0079:
        r18 = "\n";
        r0 = r18;
        r6.append(r0);
    L_0x0080:
        r18 = r23.getType();
        r19 = in.uncod.android.bypass.Element.Type.LIST_ITEM;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x0093;
    L_0x008c:
        r18 = "\u2022";
        r0 = r18;
        r6.append(r0);
    L_0x0093:
        r0 = r16;
        r6.append(r0);
        r6.append(r7);
        r18 = r23.getType();
        r19 = in.uncod.android.bypass.Element.Type.LIST;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x00fe;
    L_0x00a7:
        r18 = r23.getParent();
        if (r18 == 0) goto L_0x00fe;
    L_0x00ad:
        r18 = r23.getType();
        r19 = in.uncod.android.bypass.Element.Type.HEADER;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x013b;
    L_0x00b9:
        r18 = "level";
        r0 = r23;
        r1 = r18;
        r11 = r0.getAttribute(r1);
        r10 = java.lang.Integer.parseInt(r11);
        r18 = new android.text.style.RelativeSizeSpan;
        r19 = HEADER_SIZES;
        r19 = r19[r10];
        r18.<init>(r19);
        r19 = 0;
        r20 = r6.length();
        r21 = 33;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r3 = r21;
        r6.setSpan(r0, r1, r2, r3);
        r18 = new android.text.style.StyleSpan;
        r19 = 1;
        r18.<init>(r19);
        r19 = 0;
        r20 = r6.length();
        r21 = 33;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r3 = r21;
        r6.setSpan(r0, r1, r2, r3);
    L_0x00fd:
        return r6;
    L_0x00fe:
        r18 = r23.getType();
        r19 = in.uncod.android.bypass.Element.Type.LIST_ITEM;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x012c;
    L_0x010a:
        r18 = r23.size();
        if (r18 <= 0) goto L_0x0124;
    L_0x0110:
        r0 = r23;
        r0 = r0.children;
        r18 = r0;
        r19 = r23.size();
        r19 = r19 + -1;
        r18 = r18[r19];
        r18 = r18.isBlockElement();
        if (r18 != 0) goto L_0x00ad;
    L_0x0124:
        r18 = "\n";
        r0 = r18;
        r6.append(r0);
        goto L_0x00ad;
    L_0x012c:
        r18 = r23.isBlockElement();
        if (r18 == 0) goto L_0x00ad;
    L_0x0132:
        r18 = "\n\n";
        r0 = r18;
        r6.append(r0);
        goto L_0x00ad;
    L_0x013b:
        r18 = r23.getType();
        r19 = in.uncod.android.bypass.Element.Type.LIST_ITEM;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x016c;
    L_0x0147:
        r18 = r23.getParent();
        r18 = r18.getParent();
        if (r18 == 0) goto L_0x016c;
    L_0x0151:
        r14 = new android.text.style.LeadingMarginSpan$Standard;
        r18 = 20;
        r0 = r18;
        r14.<init>(r0);
        r18 = 0;
        r19 = r6.length();
        r20 = 33;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r6.setSpan(r14, r0, r1, r2);
        goto L_0x00fd;
    L_0x016c:
        r18 = r23.getType();
        r19 = in.uncod.android.bypass.Element.Type.EMPHASIS;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x0194;
    L_0x0178:
        r9 = new android.text.style.StyleSpan;
        r18 = 2;
        r0 = r18;
        r9.<init>(r0);
        r18 = 0;
        r19 = r6.length();
        r20 = 33;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r6.setSpan(r9, r0, r1, r2);
        goto L_0x00fd;
    L_0x0194:
        r18 = r23.getType();
        r19 = in.uncod.android.bypass.Element.Type.DOUBLE_EMPHASIS;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x01bc;
    L_0x01a0:
        r4 = new android.text.style.StyleSpan;
        r18 = 1;
        r0 = r18;
        r4.<init>(r0);
        r18 = 0;
        r19 = r6.length();
        r20 = 33;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r6.setSpan(r4, r0, r1, r2);
        goto L_0x00fd;
    L_0x01bc:
        r18 = r23.getType();
        r19 = in.uncod.android.bypass.Element.Type.TRIPLE_EMPHASIS;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x01e4;
    L_0x01c8:
        r5 = new android.text.style.StyleSpan;
        r18 = 3;
        r0 = r18;
        r5.<init>(r0);
        r18 = 0;
        r19 = r6.length();
        r20 = 33;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r6.setSpan(r5, r0, r1, r2);
        goto L_0x00fd;
    L_0x01e4:
        r18 = r23.getType();
        r19 = in.uncod.android.bypass.Element.Type.CODE_SPAN;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x020c;
    L_0x01f0:
        r12 = new android.text.style.TypefaceSpan;
        r18 = "monospace";
        r0 = r18;
        r12.<init>(r0);
        r18 = 0;
        r19 = r6.length();
        r20 = 33;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r6.setSpan(r12, r0, r1, r2);
        goto L_0x00fd;
    L_0x020c:
        r18 = r23.getType();
        r19 = in.uncod.android.bypass.Element.Type.LINK;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x023c;
    L_0x0218:
        r17 = new android.text.style.URLSpan;
        r18 = "link";
        r0 = r23;
        r1 = r18;
        r18 = r0.getAttribute(r1);
        r17.<init>(r18);
        r18 = 0;
        r19 = r6.length();
        r20 = 33;
        r0 = r17;
        r1 = r18;
        r2 = r19;
        r3 = r20;
        r6.setSpan(r0, r1, r2, r3);
        goto L_0x00fd;
    L_0x023c:
        r18 = r23.getType();
        r19 = in.uncod.android.bypass.Element.Type.BLOCK_QUOTE;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x00fd;
    L_0x0248:
        r13 = new android.text.style.QuoteSpan;
        r13.<init>();
        r18 = 0;
        r19 = r6.length();
        r20 = 33;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r6.setSpan(r13, r0, r1, r2);
        r9 = new android.text.style.StyleSpan;
        r18 = 2;
        r0 = r18;
        r9.<init>(r0);
        r18 = 0;
        r19 = r6.length();
        r20 = 33;
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r6.setSpan(r9, r0, r1, r2);
        goto L_0x00fd;
        */
        throw new UnsupportedOperationException("Method not decompiled: in.uncod.android.bypass.Bypass.recurseElement(in.uncod.android.bypass.Element):java.lang.CharSequence");
    }
}
