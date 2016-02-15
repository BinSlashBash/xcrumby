/*
 * Decompiled with CFR 0_110.
 */
package android.support.v4.text;

import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextUtilsCompat;
import java.nio.CharBuffer;
import java.util.Locale;

public class TextDirectionHeuristicsCompat {
    public static final TextDirectionHeuristicCompat ANYRTL_LTR;
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_LTR;
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_RTL;
    public static final TextDirectionHeuristicCompat LOCALE;
    public static final TextDirectionHeuristicCompat LTR;
    public static final TextDirectionHeuristicCompat RTL;
    private static final int STATE_FALSE = 1;
    private static final int STATE_TRUE = 0;
    private static final int STATE_UNKNOWN = 2;

    static {
        LTR = new TextDirectionHeuristicInternal(null, false);
        RTL = new TextDirectionHeuristicInternal(null, true);
        FIRSTSTRONG_LTR = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, false);
        FIRSTSTRONG_RTL = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, true);
        ANYRTL_LTR = new TextDirectionHeuristicInternal(AnyStrong.INSTANCE_RTL, false);
        LOCALE = TextDirectionHeuristicLocale.INSTANCE;
    }

    private static int isRtlText(int n2) {
        switch (n2) {
            default: {
                return 2;
            }
            case 0: {
                return 1;
            }
            case 1: 
            case 2: 
        }
        return 0;
    }

    private static int isRtlTextOrFormat(int n2) {
        switch (n2) {
            default: {
                return 2;
            }
            case 0: 
            case 14: 
            case 15: {
                return 1;
            }
            case 1: 
            case 2: 
            case 16: 
            case 17: 
        }
        return 0;
    }

    private static class AnyStrong
    implements TextDirectionAlgorithm {
        public static final AnyStrong INSTANCE_LTR;
        public static final AnyStrong INSTANCE_RTL;
        private final boolean mLookForRtl;

        static {
            INSTANCE_RTL = new AnyStrong(true);
            INSTANCE_LTR = new AnyStrong(false);
        }

        private AnyStrong(boolean bl2) {
            this.mLookForRtl = bl2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int checkRtl(CharSequence charSequence, int n2, int n3) {
            int n4 = 1;
            int n5 = 0;
            block4 : for (int i2 = n2; i2 < n2 + n3; ++i2) {
                switch (TextDirectionHeuristicsCompat.isRtlText(Character.getDirectionality(charSequence.charAt(i2)))) {
                    case 0: {
                        if (this.mLookForRtl) {
                            return 0;
                        }
                        n5 = 1;
                    }
                    default: {
                        continue block4;
                    }
                    case 1: {
                        n5 = n4;
                        if (!this.mLookForRtl) return n5;
                        n5 = 1;
                    }
                }
            }
            if (n5 == 0) {
                return 2;
            }
            n5 = n4;
            if (this.mLookForRtl) return n5;
            return 0;
        }
    }

    private static class FirstStrong
    implements TextDirectionAlgorithm {
        public static final FirstStrong INSTANCE = new FirstStrong();

        private FirstStrong() {
        }

        @Override
        public int checkRtl(CharSequence charSequence, int n2, int n3) {
            int n4 = 2;
            for (int i2 = n2; i2 < n2 + n3 && n4 == 2; ++i2) {
                n4 = TextDirectionHeuristicsCompat.isRtlTextOrFormat(Character.getDirectionality(charSequence.charAt(i2)));
            }
            return n4;
        }
    }

    private static interface TextDirectionAlgorithm {
        public int checkRtl(CharSequence var1, int var2, int var3);
    }

    private static abstract class TextDirectionHeuristicImpl
    implements TextDirectionHeuristicCompat {
        private final TextDirectionAlgorithm mAlgorithm;

        public TextDirectionHeuristicImpl(TextDirectionAlgorithm textDirectionAlgorithm) {
            this.mAlgorithm = textDirectionAlgorithm;
        }

        private boolean doCheck(CharSequence charSequence, int n2, int n3) {
            switch (this.mAlgorithm.checkRtl(charSequence, n2, n3)) {
                default: {
                    return this.defaultIsRtl();
                }
                case 0: {
                    return true;
                }
                case 1: 
            }
            return false;
        }

        protected abstract boolean defaultIsRtl();

        @Override
        public boolean isRtl(CharSequence charSequence, int n2, int n3) {
            if (charSequence == null || n2 < 0 || n3 < 0 || charSequence.length() - n3 < n2) {
                throw new IllegalArgumentException();
            }
            if (this.mAlgorithm == null) {
                return this.defaultIsRtl();
            }
            return this.doCheck(charSequence, n2, n3);
        }

        @Override
        public boolean isRtl(char[] arrc, int n2, int n3) {
            return this.isRtl(CharBuffer.wrap(arrc), n2, n3);
        }
    }

    private static class TextDirectionHeuristicInternal
    extends TextDirectionHeuristicImpl {
        private final boolean mDefaultIsRtl;

        private TextDirectionHeuristicInternal(TextDirectionAlgorithm textDirectionAlgorithm, boolean bl2) {
            super(textDirectionAlgorithm);
            this.mDefaultIsRtl = bl2;
        }

        @Override
        protected boolean defaultIsRtl() {
            return this.mDefaultIsRtl;
        }
    }

    private static class TextDirectionHeuristicLocale
    extends TextDirectionHeuristicImpl {
        public static final TextDirectionHeuristicLocale INSTANCE = new TextDirectionHeuristicLocale();

        public TextDirectionHeuristicLocale() {
            super(null);
        }

        @Override
        protected boolean defaultIsRtl() {
            if (TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1) {
                return true;
            }
            return false;
        }
    }

}

