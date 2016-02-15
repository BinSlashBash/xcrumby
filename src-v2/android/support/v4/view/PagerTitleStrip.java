/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.widget.TextView
 */
package android.support.v4.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStripIcs;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import java.lang.ref.WeakReference;

public class PagerTitleStrip
extends ViewGroup
implements ViewPager.Decor {
    private static final int[] ATTRS = new int[]{16842804, 16842901, 16842904, 16842927};
    private static final PagerTitleStripImpl IMPL;
    private static final float SIDE_ALPHA = 0.6f;
    private static final String TAG = "PagerTitleStrip";
    private static final int[] TEXT_ATTRS;
    private static final int TEXT_SPACING = 16;
    TextView mCurrText;
    private int mGravity;
    private int mLastKnownCurrentPage = -1;
    private float mLastKnownPositionOffset = -1.0f;
    TextView mNextText;
    private int mNonPrimaryAlpha;
    private final PageListener mPageListener;
    ViewPager mPager;
    TextView mPrevText;
    private int mScaledTextSpacing;
    int mTextColor;
    private boolean mUpdatingPositions;
    private boolean mUpdatingText;
    private WeakReference<PagerAdapter> mWatchingAdapter;

    static {
        TEXT_ATTRS = new int[]{16843660};
        IMPL = Build.VERSION.SDK_INT >= 14 ? new PagerTitleStripImplIcs() : new PagerTitleStripImplBase();
    }

    public PagerTitleStrip(Context context) {
        this(context, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public PagerTitleStrip(Context var1_1, AttributeSet var2_2) {
        super(var1_1, var2_2);
        this.mPageListener = new PageListener();
        this.mPrevText = var6_3 = new TextView(var1_1);
        this.addView((View)var6_3);
        this.mCurrText = var6_3 = new TextView(var1_1);
        this.addView((View)var6_3);
        this.mNextText = var6_3 = new TextView(var1_1);
        this.addView((View)var6_3);
        var2_2 = var1_1.obtainStyledAttributes(var2_2, PagerTitleStrip.ATTRS);
        var3_4 = var2_2.getResourceId(0, 0);
        if (var3_4 != 0) {
            this.mPrevText.setTextAppearance(var1_1, var3_4);
            this.mCurrText.setTextAppearance(var1_1, var3_4);
            this.mNextText.setTextAppearance(var1_1, var3_4);
        }
        if ((var4_5 = var2_2.getDimensionPixelSize(1, 0)) != 0) {
            this.setTextSize(0, var4_5);
        }
        if (var2_2.hasValue(2)) {
            var4_5 = var2_2.getColor(2, 0);
            this.mPrevText.setTextColor(var4_5);
            this.mCurrText.setTextColor(var4_5);
            this.mNextText.setTextColor(var4_5);
        }
        this.mGravity = var2_2.getInteger(3, 80);
        var2_2.recycle();
        this.mTextColor = this.mCurrText.getTextColors().getDefaultColor();
        this.setNonPrimaryAlpha(0.6f);
        this.mPrevText.setEllipsize(TextUtils.TruncateAt.END);
        this.mCurrText.setEllipsize(TextUtils.TruncateAt.END);
        this.mNextText.setEllipsize(TextUtils.TruncateAt.END);
        var5_6 = false;
        if (var3_4 == 0) ** GOTO lbl-1000
        var2_2 = var1_1.obtainStyledAttributes(var3_4, PagerTitleStrip.TEXT_ATTRS);
        var5_6 = var2_2.getBoolean(0, false);
        var2_2.recycle();
        if (var5_6) {
            PagerTitleStrip.setSingleLineAllCaps(this.mPrevText);
            PagerTitleStrip.setSingleLineAllCaps(this.mCurrText);
            PagerTitleStrip.setSingleLineAllCaps(this.mNextText);
        } else lbl-1000: // 2 sources:
        {
            this.mPrevText.setSingleLine();
            this.mCurrText.setSingleLine();
            this.mNextText.setSingleLine();
        }
        this.mScaledTextSpacing = (int)(16.0f * var1_1.getResources().getDisplayMetrics().density);
    }

    private static void setSingleLineAllCaps(TextView textView) {
        IMPL.setSingleLineAllCaps(textView);
    }

    int getMinHeight() {
        int n2 = 0;
        Drawable drawable2 = this.getBackground();
        if (drawable2 != null) {
            n2 = drawable2.getIntrinsicHeight();
        }
        return n2;
    }

    public int getTextSpacing() {
        return this.mScaledTextSpacing;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object object = this.getParent();
        if (!(object instanceof ViewPager)) {
            throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
        }
        object = (ViewPager)((Object)object);
        PagerAdapter pagerAdapter = object.getAdapter();
        object.setInternalPageChangeListener(this.mPageListener);
        object.setOnAdapterChangeListener(this.mPageListener);
        this.mPager = object;
        object = this.mWatchingAdapter != null ? this.mWatchingAdapter.get() : null;
        this.updateAdapter((PagerAdapter)object, pagerAdapter);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mPager != null) {
            this.updateAdapter(this.mPager.getAdapter(), null);
            this.mPager.setInternalPageChangeListener(null);
            this.mPager.setOnAdapterChangeListener(null);
            this.mPager = null;
        }
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        float f2 = 0.0f;
        if (this.mPager != null) {
            if (this.mLastKnownPositionOffset >= 0.0f) {
                f2 = this.mLastKnownPositionOffset;
            }
            this.updateTextPositions(this.mLastKnownCurrentPage, f2, true);
        }
    }

    protected void onMeasure(int n2, int n3) {
        int n4 = View.MeasureSpec.getMode((int)n2);
        int n5 = View.MeasureSpec.getMode((int)n3);
        n2 = View.MeasureSpec.getSize((int)n2);
        n3 = View.MeasureSpec.getSize((int)n3);
        if (n4 != 1073741824) {
            throw new IllegalStateException("Must measure with an exact width");
        }
        n4 = this.getMinHeight();
        int n6 = this.getPaddingTop() + this.getPaddingBottom();
        int n7 = View.MeasureSpec.makeMeasureSpec((int)((int)((float)n2 * 0.8f)), (int)Integer.MIN_VALUE);
        int n8 = View.MeasureSpec.makeMeasureSpec((int)(n3 - n6), (int)Integer.MIN_VALUE);
        this.mPrevText.measure(n7, n8);
        this.mCurrText.measure(n7, n8);
        this.mNextText.measure(n7, n8);
        if (n5 == 1073741824) {
            this.setMeasuredDimension(n2, n3);
            return;
        }
        this.setMeasuredDimension(n2, Math.max(n4, this.mCurrText.getMeasuredHeight() + n6));
    }

    public void requestLayout() {
        if (!this.mUpdatingText) {
            super.requestLayout();
        }
    }

    public void setGravity(int n2) {
        this.mGravity = n2;
        this.requestLayout();
    }

    public void setNonPrimaryAlpha(float f2) {
        this.mNonPrimaryAlpha = (int)(255.0f * f2) & 255;
        int n2 = this.mNonPrimaryAlpha << 24 | this.mTextColor & 16777215;
        this.mPrevText.setTextColor(n2);
        this.mNextText.setTextColor(n2);
    }

    public void setTextColor(int n2) {
        this.mTextColor = n2;
        this.mCurrText.setTextColor(n2);
        n2 = this.mNonPrimaryAlpha << 24 | this.mTextColor & 16777215;
        this.mPrevText.setTextColor(n2);
        this.mNextText.setTextColor(n2);
    }

    public void setTextSize(int n2, float f2) {
        this.mPrevText.setTextSize(n2, f2);
        this.mCurrText.setTextSize(n2, f2);
        this.mNextText.setTextSize(n2, f2);
    }

    public void setTextSpacing(int n2) {
        this.mScaledTextSpacing = n2;
        this.requestLayout();
    }

    void updateAdapter(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
        if (pagerAdapter != null) {
            pagerAdapter.unregisterDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = null;
        }
        if (pagerAdapter2 != null) {
            pagerAdapter2.registerDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = new WeakReference<PagerAdapter>(pagerAdapter2);
        }
        if (this.mPager != null) {
            this.mLastKnownCurrentPage = -1;
            this.mLastKnownPositionOffset = -1.0f;
            this.updateText(this.mPager.getCurrentItem(), pagerAdapter2);
            this.requestLayout();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateText(int n2, PagerAdapter pagerAdapter) {
        TextView textView;
        int n3 = pagerAdapter != null ? pagerAdapter.getCount() : 0;
        this.mUpdatingText = true;
        Object object = textView = null;
        if (n2 >= 1) {
            object = textView;
            if (pagerAdapter != null) {
                object = pagerAdapter.getPageTitle(n2 - 1);
            }
        }
        this.mPrevText.setText((CharSequence)object);
        textView = this.mCurrText;
        object = pagerAdapter != null && n2 < n3 ? pagerAdapter.getPageTitle(n2) : null;
        textView.setText((CharSequence)object);
        object = textView = null;
        if (n2 + 1 < n3) {
            object = textView;
            if (pagerAdapter != null) {
                object = pagerAdapter.getPageTitle(n2 + 1);
            }
        }
        this.mNextText.setText((CharSequence)object);
        int n4 = this.getWidth();
        int n5 = this.getPaddingLeft();
        int n6 = this.getPaddingRight();
        n3 = this.getHeight();
        int n7 = this.getPaddingTop();
        int n8 = this.getPaddingBottom();
        n4 = View.MeasureSpec.makeMeasureSpec((int)((int)((float)(n4 - n5 - n6) * 0.8f)), (int)Integer.MIN_VALUE);
        n3 = View.MeasureSpec.makeMeasureSpec((int)(n3 - n7 - n8), (int)Integer.MIN_VALUE);
        this.mPrevText.measure(n4, n3);
        this.mCurrText.measure(n4, n3);
        this.mNextText.measure(n4, n3);
        this.mLastKnownCurrentPage = n2;
        if (!this.mUpdatingPositions) {
            this.updateTextPositions(n2, this.mLastKnownPositionOffset, false);
        }
        this.mUpdatingText = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateTextPositions(int n2, float f2, boolean bl2) {
        float f3;
        if (n2 != this.mLastKnownCurrentPage) {
            this.updateText(n2, this.mPager.getAdapter());
        } else if (!bl2 && f2 == this.mLastKnownPositionOffset) {
            return;
        }
        this.mUpdatingPositions = true;
        int n3 = this.mPrevText.getMeasuredWidth();
        int n4 = this.mCurrText.getMeasuredWidth();
        int n5 = this.mNextText.getMeasuredWidth();
        int n6 = n4 / 2;
        int n7 = this.getWidth();
        int n8 = this.getHeight();
        int n9 = this.getPaddingLeft();
        int n10 = this.getPaddingRight();
        n2 = this.getPaddingTop();
        int n11 = this.getPaddingBottom();
        int n12 = n10 + n6;
        float f4 = f3 = f2 + 0.5f;
        if (f3 > 1.0f) {
            f4 = f3 - 1.0f;
        }
        n6 = n7 - n12 - (int)((float)(n7 - (n9 + n6) - n12) * f4) - n4 / 2;
        n4 = n6 + n4;
        int n13 = this.mPrevText.getBaseline();
        int n14 = this.mCurrText.getBaseline();
        n12 = this.mNextText.getBaseline();
        int n15 = Math.max(Math.max(n13, n14), n12);
        n13 = n15 - n13;
        n14 = n15 - n14;
        n12 = n15 - n12;
        n15 = this.mPrevText.getMeasuredHeight();
        int n16 = this.mCurrText.getMeasuredHeight();
        int n17 = this.mNextText.getMeasuredHeight();
        n15 = Math.max(Math.max(n13 + n15, n14 + n16), n12 + n17);
        switch (this.mGravity & 112) {
            default: {
                n11 = n2 + n13;
                n8 = n2 + n14;
                n2 += n12;
                break;
            }
            case 16: {
                n2 = (n8 - n2 - n11 - n15) / 2;
                n11 = n2 + n13;
                n8 = n2 + n14;
                n2 += n12;
                break;
            }
            case 80: {
                n2 = n8 - n11 - n15;
                n11 = n2 + n13;
                n8 = n2 + n14;
                n2 += n12;
            }
        }
        this.mCurrText.layout(n6, n8, n4, this.mCurrText.getMeasuredHeight() + n8);
        n8 = Math.min(n9, n6 - this.mScaledTextSpacing - n3);
        this.mPrevText.layout(n8, n11, n8 + n3, this.mPrevText.getMeasuredHeight() + n11);
        n8 = Math.max(n7 - n10 - n5, this.mScaledTextSpacing + n4);
        this.mNextText.layout(n8, n2, n8 + n5, this.mNextText.getMeasuredHeight() + n2);
        this.mLastKnownPositionOffset = f2;
        this.mUpdatingPositions = false;
    }

    private class PageListener
    extends DataSetObserver
    implements ViewPager.OnPageChangeListener,
    ViewPager.OnAdapterChangeListener {
        private int mScrollState;

        private PageListener() {
        }

        @Override
        public void onAdapterChanged(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            PagerTitleStrip.this.updateAdapter(pagerAdapter, pagerAdapter2);
        }

        public void onChanged() {
            float f2 = 0.0f;
            PagerTitleStrip.this.updateText(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
            if (PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f) {
                f2 = PagerTitleStrip.this.mLastKnownPositionOffset;
            }
            PagerTitleStrip.this.updateTextPositions(PagerTitleStrip.this.mPager.getCurrentItem(), f2, true);
        }

        @Override
        public void onPageScrollStateChanged(int n2) {
            this.mScrollState = n2;
        }

        @Override
        public void onPageScrolled(int n2, float f2, int n3) {
            n3 = n2;
            if (f2 > 0.5f) {
                n3 = n2 + 1;
            }
            PagerTitleStrip.this.updateTextPositions(n3, f2, false);
        }

        @Override
        public void onPageSelected(int n2) {
            float f2 = 0.0f;
            if (this.mScrollState == 0) {
                PagerTitleStrip.this.updateText(PagerTitleStrip.this.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
                if (PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f) {
                    f2 = PagerTitleStrip.this.mLastKnownPositionOffset;
                }
                PagerTitleStrip.this.updateTextPositions(PagerTitleStrip.this.mPager.getCurrentItem(), f2, true);
            }
        }
    }

    static interface PagerTitleStripImpl {
        public void setSingleLineAllCaps(TextView var1);
    }

    static class PagerTitleStripImplBase
    implements PagerTitleStripImpl {
        PagerTitleStripImplBase() {
        }

        @Override
        public void setSingleLineAllCaps(TextView textView) {
            textView.setSingleLine();
        }
    }

    static class PagerTitleStripImplIcs
    implements PagerTitleStripImpl {
        PagerTitleStripImplIcs() {
        }

        @Override
        public void setSingleLineAllCaps(TextView textView) {
            PagerTitleStripIcs.setSingleLineAllCaps(textView);
        }
    }

}

