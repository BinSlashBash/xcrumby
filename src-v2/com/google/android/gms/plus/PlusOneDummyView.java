/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.graphics.Point
 *  android.graphics.drawable.Drawable
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.Button
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package com.google.android.gms.plus;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class PlusOneDummyView
extends FrameLayout {
    public static final String TAG = "PlusOneDummyView";

    public PlusOneDummyView(Context context, int n2) {
        super(context);
        context = new Button(context);
        context.setEnabled(false);
        context.setBackgroundDrawable(this.iJ().getDrawable(n2));
        Point point = this.bL(n2);
        this.addView((View)context, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(point.x, point.y, 17));
    }

    /*
     * Enabled aggressive block sorting
     */
    private Point bL(int n2) {
        int n3 = 24;
        int n4 = 20;
        Point point = new Point();
        switch (n2) {
            default: {
                n2 = 38;
                n4 = 24;
                break;
            }
            case 1: {
                n2 = 32;
                break;
            }
            case 0: {
                n4 = 14;
                n2 = n3;
                break;
            }
            case 2: {
                n2 = 50;
            }
        }
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float f2 = TypedValue.applyDimension((int)1, (float)n2, (DisplayMetrics)displayMetrics);
        float f3 = TypedValue.applyDimension((int)1, (float)n4, (DisplayMetrics)displayMetrics);
        point.x = (int)((double)f2 + 0.5);
        point.y = (int)((double)f3 + 0.5);
        return point;
    }

    private d iJ() {
        d d2;
        d d3 = d2 = new b(this.getContext());
        if (!d2.isValid()) {
            d3 = new c(this.getContext());
        }
        d2 = d3;
        if (!d3.isValid()) {
            d2 = new a(this.getContext());
        }
        return d2;
    }

    private static class a
    implements d {
        private Context mContext;

        private a(Context context) {
            this.mContext = context;
        }

        @Override
        public Drawable getDrawable(int n2) {
            return this.mContext.getResources().getDrawable(17301508);
        }

        @Override
        public boolean isValid() {
            return true;
        }
    }

    private static class b
    implements d {
        private Context mContext;

        private b(Context context) {
            this.mContext = context;
        }

        /*
         * Exception decompiling
         */
        @Override
        public Drawable getDrawable(int var1_1) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:366)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:422)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:220)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:165)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:91)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:354)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:751)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:664)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:747)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:683)
            // org.benf.cfr.reader.Main.doJar(Main.java:128)
            // org.benf.cfr.reader.Main.main(Main.java:178)
            throw new IllegalStateException("Decompilation failed");
        }

        @Override
        public boolean isValid() {
            try {
                this.mContext.createPackageContext("com.google.android.gms", 4).getResources();
                return true;
            }
            catch (PackageManager.NameNotFoundException var1_1) {
                return false;
            }
        }
    }

    private static class c
    implements d {
        private Context mContext;

        private c(Context context) {
            this.mContext = context;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public Drawable getDrawable(int n2) {
            String string2;
            switch (n2) {
                default: {
                    string2 = "ic_plusone_standard_off_client";
                    break;
                }
                case 0: {
                    string2 = "ic_plusone_small_off_client";
                    break;
                }
                case 1: {
                    string2 = "ic_plusone_medium_off_client";
                    break;
                }
                case 2: {
                    string2 = "ic_plusone_tall_off_client";
                }
            }
            n2 = this.mContext.getResources().getIdentifier(string2, "drawable", this.mContext.getPackageName());
            return this.mContext.getResources().getDrawable(n2);
        }

        @Override
        public boolean isValid() {
            int n2 = this.mContext.getResources().getIdentifier("ic_plusone_small_off_client", "drawable", this.mContext.getPackageName());
            int n3 = this.mContext.getResources().getIdentifier("ic_plusone_medium_off_client", "drawable", this.mContext.getPackageName());
            int n4 = this.mContext.getResources().getIdentifier("ic_plusone_tall_off_client", "drawable", this.mContext.getPackageName());
            int n5 = this.mContext.getResources().getIdentifier("ic_plusone_standard_off_client", "drawable", this.mContext.getPackageName());
            if (n2 != 0 && n3 != 0 && n4 != 0 && n5 != 0) {
                return true;
            }
            return false;
        }
    }

    private static interface d {
        public Drawable getDrawable(int var1);

        public boolean isValid();
    }

}

