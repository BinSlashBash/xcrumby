/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class DoubleMetaphone
implements StringEncoder {
    private static final String[] ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER;
    private static final String[] L_R_N_M_B_H_F_V_W_SPACE;
    private static final String[] L_T_K_S_N_M_B_Z;
    private static final String[] SILENT_START;
    private static final String VOWELS = "AEIOUY";
    private int maxCodeLen = 4;

    static {
        SILENT_START = new String[]{"GN", "KN", "PN", "WR", "PS"};
        L_R_N_M_B_H_F_V_W_SPACE = new String[]{"L", "R", "N", "M", "B", "H", "F", "V", "W", " "};
        ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER = new String[]{"ES", "EP", "EB", "EL", "EY", "IB", "IL", "IN", "IE", "EI", "ER"};
        L_T_K_S_N_M_B_Z = new String[]{"L", "T", "K", "S", "N", "M", "B", "Z"};
    }

    /*
     * Enabled aggressive block sorting
     */
    private String cleanInput(String string2) {
        if (string2 == null || (string2 = string2.trim()).length() == 0) {
            return null;
        }
        return string2.toUpperCase(Locale.ENGLISH);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean conditionC0(String string2, int n2) {
        boolean bl2 = false;
        if (DoubleMetaphone.contains(string2, n2, 4, "CHIA")) {
            return true;
        }
        boolean bl3 = bl2;
        if (n2 <= 1) return bl3;
        bl3 = bl2;
        if (this.isVowel(this.charAt(string2, n2 - 2))) return bl3;
        bl3 = bl2;
        if (!DoubleMetaphone.contains(string2, n2 - 1, 3, "ACH")) return bl3;
        char c2 = this.charAt(string2, n2 + 2);
        if (c2 != 'I') {
            if (c2 != 'E') return true;
        }
        bl3 = bl2;
        if (!DoubleMetaphone.contains(string2, n2 - 2, 6, "BACHER", "MACHER")) return bl3;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean conditionCH0(String string2, int n2) {
        if (n2 != 0 || !DoubleMetaphone.contains(string2, n2 + 1, 5, "HARAC", "HARIS") && !DoubleMetaphone.contains(string2, n2 + 1, 3, "HOR", "HYM", "HIA", "HEM") || DoubleMetaphone.contains(string2, 0, 5, "CHORE")) {
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean conditionCH1(String string2, int n2) {
        boolean bl2;
        boolean bl3 = false;
        if (DoubleMetaphone.contains(string2, 0, 4, "VAN ", "VON ")) return true;
        if (DoubleMetaphone.contains(string2, 0, 3, "SCH")) return true;
        if (DoubleMetaphone.contains(string2, n2 - 2, 6, "ORCHES", "ARCHIT", "ORCHID")) return true;
        if (DoubleMetaphone.contains(string2, n2 + 2, 1, "T", "S")) return true;
        if (!DoubleMetaphone.contains(string2, n2 - 1, 1, "A", "O", "U", "E")) {
            bl2 = bl3;
            if (n2 != 0) return bl2;
        }
        if (DoubleMetaphone.contains(string2, n2 + 2, 1, L_R_N_M_B_H_F_V_W_SPACE)) return true;
        bl2 = bl3;
        if (n2 + 1 != string2.length() - 1) return bl2;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean conditionL0(String string2, int n2) {
        if (n2 == string2.length() - 3 && DoubleMetaphone.contains(string2, n2 - 1, 4, "ILLO", "ILLA", "ALLE") || (DoubleMetaphone.contains(string2, string2.length() - 2, 2, "AS", "OS") || DoubleMetaphone.contains(string2, string2.length() - 1, 1, "A", "O")) && DoubleMetaphone.contains(string2, n2 - 1, 4, "ALLE")) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean conditionM0(String string2, int n2) {
        if (this.charAt(string2, n2 + 1) == 'M' || DoubleMetaphone.contains(string2, n2 - 1, 3, "UMB") && (n2 + 1 == string2.length() - 1 || DoubleMetaphone.contains(string2, n2 + 2, 2, "ER"))) {
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static /* varargs */ boolean contains(String string2, int n2, int n3, String ... arrstring) {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (n2 < 0) return bl3;
        bl3 = bl2;
        if (n2 + n3 > string2.length()) return bl3;
        string2 = string2.substring(n2, n2 + n3);
        n3 = arrstring.length;
        n2 = 0;
        do {
            bl3 = bl2;
            if (n2 >= n3) return bl3;
            if (string2.equals(arrstring[n2])) {
                return true;
            }
            ++n2;
        } while (true);
    }

    private int handleAEIOUY(DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if (n2 == 0) {
            doubleMetaphoneResult.append('A');
        }
        return n2 + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int handleC(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if (this.conditionC0(string2, n2)) {
            doubleMetaphoneResult.append('K');
            n2 += 2;
            return n2;
        }
        if (n2 == 0 && DoubleMetaphone.contains(string2, n2, 6, "CAESAR")) {
            doubleMetaphoneResult.append('S');
            return n2 += 2;
        }
        if (DoubleMetaphone.contains(string2, n2, 2, "CH")) {
            return this.handleCH(string2, doubleMetaphoneResult, n2);
        }
        if (DoubleMetaphone.contains(string2, n2, 2, "CZ") && !DoubleMetaphone.contains(string2, n2 - 2, 4, "WICZ")) {
            doubleMetaphoneResult.append('S', 'X');
            return n2 += 2;
        }
        if (DoubleMetaphone.contains(string2, n2 + 1, 3, "CIA")) {
            doubleMetaphoneResult.append('X');
            return n2 += 3;
        }
        if (DoubleMetaphone.contains(string2, n2, 2, "CC")) {
            if (n2 != 1) return this.handleCC(string2, doubleMetaphoneResult, n2);
            if (this.charAt(string2, 0) != 'M') {
                return this.handleCC(string2, doubleMetaphoneResult, n2);
            }
        }
        if (DoubleMetaphone.contains(string2, n2, 2, "CK", "CG", "CQ")) {
            doubleMetaphoneResult.append('K');
            return n2 += 2;
        }
        if (DoubleMetaphone.contains(string2, n2, 2, "CI", "CE", "CY")) {
            if (DoubleMetaphone.contains(string2, n2, 3, "CIO", "CIE", "CIA")) {
                doubleMetaphoneResult.append('S', 'X');
                return n2 += 2;
            } else {
                doubleMetaphoneResult.append('S');
            }
            return n2 += 2;
        }
        doubleMetaphoneResult.append('K');
        if (DoubleMetaphone.contains(string2, n2 + 1, 2, " C", " Q", " G")) {
            return n2 += 3;
        }
        if (!DoubleMetaphone.contains(string2, n2 + 1, 1, "C", "K", "Q") || DoubleMetaphone.contains(string2, n2 + 1, 2, "CE", "CI")) return ++n2;
        return n2 += 2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int handleCC(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if (DoubleMetaphone.contains(string2, n2 + 2, 1, "I", "E", "H") && !DoubleMetaphone.contains(string2, n2 + 2, 2, "HU")) {
            if (n2 == 1 && this.charAt(string2, n2 - 1) == 'A' || DoubleMetaphone.contains(string2, n2 - 1, 5, "UCCEE", "UCCES")) {
                doubleMetaphoneResult.append("KS");
                do {
                    return n2 + 3;
                    break;
                } while (true);
            }
            doubleMetaphoneResult.append('X');
            return n2 + 3;
        }
        doubleMetaphoneResult.append('K');
        return n2 + 2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int handleCH(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if (n2 > 0 && DoubleMetaphone.contains(string2, n2, 4, "CHAE")) {
            doubleMetaphoneResult.append('K', 'X');
            return n2 + 2;
        }
        if (this.conditionCH0(string2, n2)) {
            doubleMetaphoneResult.append('K');
            return n2 + 2;
        }
        if (this.conditionCH1(string2, n2)) {
            doubleMetaphoneResult.append('K');
            return n2 + 2;
        }
        if (n2 > 0) {
            if (DoubleMetaphone.contains(string2, 0, 2, "MC")) {
                doubleMetaphoneResult.append('K');
                do {
                    return n2 + 2;
                    break;
                } while (true);
            }
            doubleMetaphoneResult.append('X', 'K');
            return n2 + 2;
        }
        doubleMetaphoneResult.append('X');
        return n2 + 2;
    }

    private int handleD(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if (DoubleMetaphone.contains(string2, n2, 2, "DG")) {
            if (DoubleMetaphone.contains(string2, n2 + 2, 1, "I", "E", "Y")) {
                doubleMetaphoneResult.append('J');
                return n2 + 3;
            }
            doubleMetaphoneResult.append("TK");
            return n2 + 2;
        }
        if (DoubleMetaphone.contains(string2, n2, 2, "DT", "DD")) {
            doubleMetaphoneResult.append('T');
            return n2 + 2;
        }
        doubleMetaphoneResult.append('T');
        return n2 + 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int handleG(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2, boolean bl2) {
        if (this.charAt(string2, n2 + 1) == 'H') {
            return this.handleGH(string2, doubleMetaphoneResult, n2);
        }
        if (this.charAt(string2, n2 + 1) == 'N') {
            if (n2 == 1 && this.isVowel(this.charAt(string2, 0)) && !bl2) {
                doubleMetaphoneResult.append("KN", "N");
                do {
                    return n2 + 2;
                    break;
                } while (true);
            }
            if (!DoubleMetaphone.contains(string2, n2 + 2, 2, "EY") && this.charAt(string2, n2 + 1) != 'Y' && !bl2) {
                doubleMetaphoneResult.append("N", "KN");
                return n2 + 2;
            }
            doubleMetaphoneResult.append("KN");
            return n2 + 2;
        }
        if (DoubleMetaphone.contains(string2, n2 + 1, 2, "LI") && !bl2) {
            doubleMetaphoneResult.append("KL", "L");
            return n2 + 2;
        }
        if (n2 == 0 && (this.charAt(string2, n2 + 1) == 'Y' || DoubleMetaphone.contains(string2, n2 + 1, 2, ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER))) {
            doubleMetaphoneResult.append('K', 'J');
            return n2 + 2;
        }
        if (!(!DoubleMetaphone.contains(string2, n2 + 1, 2, "ER") && this.charAt(string2, n2 + 1) != 'Y' || DoubleMetaphone.contains(string2, 0, 6, "DANGER", "RANGER", "MANGER") || DoubleMetaphone.contains(string2, n2 - 1, 1, "E", "I") || DoubleMetaphone.contains(string2, n2 - 1, 3, "RGY", "OGY"))) {
            doubleMetaphoneResult.append('K', 'J');
            return n2 + 2;
        }
        if (DoubleMetaphone.contains(string2, n2 + 1, 1, "E", "I", "Y") || DoubleMetaphone.contains(string2, n2 - 1, 4, "AGGI", "OGGI")) {
            if (DoubleMetaphone.contains(string2, 0, 4, "VAN ", "VON ") || DoubleMetaphone.contains(string2, 0, 3, "SCH") || DoubleMetaphone.contains(string2, n2 + 1, 2, "ET")) {
                doubleMetaphoneResult.append('K');
                do {
                    return n2 + 2;
                    break;
                } while (true);
            }
            if (DoubleMetaphone.contains(string2, n2 + 1, 3, "IER")) {
                doubleMetaphoneResult.append('J');
                return n2 + 2;
            }
            doubleMetaphoneResult.append('J', 'K');
            return n2 + 2;
        }
        if (this.charAt(string2, n2 + 1) == 'G') {
            doubleMetaphoneResult.append('K');
            return n2 + 2;
        }
        doubleMetaphoneResult.append('K');
        return n2 + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int handleGH(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if (n2 > 0 && !this.isVowel(this.charAt(string2, n2 - 1))) {
            doubleMetaphoneResult.append('K');
            return n2 + 2;
        }
        if (n2 == 0) {
            if (this.charAt(string2, n2 + 2) == 'I') {
                doubleMetaphoneResult.append('J');
                return n2 + 2;
            }
            doubleMetaphoneResult.append('K');
            return n2 + 2;
        }
        if (n2 > 1) {
            if (DoubleMetaphone.contains(string2, n2 - 2, 1, "B", "H", "D")) return n2 + 2;
        }
        if (n2 > 2) {
            if (DoubleMetaphone.contains(string2, n2 - 3, 1, "B", "H", "D")) return n2 + 2;
        }
        if (n2 > 3 && DoubleMetaphone.contains(string2, n2 - 4, 1, "B", "H")) {
            return n2 + 2;
        }
        if (n2 > 2 && this.charAt(string2, n2 - 1) == 'U' && DoubleMetaphone.contains(string2, n2 - 3, 1, "C", "G", "L", "R", "T")) {
            doubleMetaphoneResult.append('F');
            return n2 + 2;
        }
        if (n2 <= 0) return n2 + 2;
        if (this.charAt(string2, n2 - 1) == 'I') return n2 + 2;
        doubleMetaphoneResult.append('K');
        return n2 + 2;
    }

    private int handleH(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if ((n2 == 0 || this.isVowel(this.charAt(string2, n2 - 1))) && this.isVowel(this.charAt(string2, n2 + 1))) {
            doubleMetaphoneResult.append('H');
            return n2 + 2;
        }
        return n2 + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int handleJ(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2, boolean bl2) {
        if (DoubleMetaphone.contains(string2, n2, 4, "JOSE") || DoubleMetaphone.contains(string2, 0, 4, "SAN ")) {
            if (!(n2 == 0 && this.charAt(string2, n2 + 4) == ' ' || string2.length() == 4 || DoubleMetaphone.contains(string2, 0, 4, "SAN "))) {
                doubleMetaphoneResult.append('J', 'H');
                return n2 + 1;
            }
            doubleMetaphoneResult.append('H');
            return n2 + 1;
        }
        if (n2 == 0 && !DoubleMetaphone.contains(string2, n2, 4, "JOSE")) {
            doubleMetaphoneResult.append('J', 'A');
        } else if (this.isVowel(this.charAt(string2, n2 - 1)) && !bl2 && (this.charAt(string2, n2 + 1) == 'A' || this.charAt(string2, n2 + 1) == 'O')) {
            doubleMetaphoneResult.append('J', 'H');
        } else if (n2 == string2.length() - 1) {
            doubleMetaphoneResult.append('J', ' ');
        } else if (!DoubleMetaphone.contains(string2, n2 + 1, 1, L_T_K_S_N_M_B_Z) && !DoubleMetaphone.contains(string2, n2 - 1, 1, "S", "K", "L")) {
            doubleMetaphoneResult.append('J');
        }
        if (this.charAt(string2, n2 + 1) != 'J') return n2 + 1;
        return n2 + 2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int handleL(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if (this.charAt(string2, n2 + 1) == 'L') {
            if (this.conditionL0(string2, n2)) {
                doubleMetaphoneResult.appendPrimary('L');
                do {
                    return n2 + 2;
                    break;
                } while (true);
            }
            doubleMetaphoneResult.append('L');
            return n2 + 2;
        }
        doubleMetaphoneResult.append('L');
        return n2 + 1;
    }

    private int handleP(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if (this.charAt(string2, n2 + 1) == 'H') {
            doubleMetaphoneResult.append('F');
            return n2 + 2;
        }
        doubleMetaphoneResult.append('P');
        if (DoubleMetaphone.contains(string2, n2 + 1, 1, "P", "B")) {
            return n2 + 2;
        }
        return n2 + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int handleR(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2, boolean bl2) {
        if (n2 == string2.length() - 1 && !bl2 && DoubleMetaphone.contains(string2, n2 - 2, 2, "IE") && !DoubleMetaphone.contains(string2, n2 - 4, 2, "ME", "MA")) {
            doubleMetaphoneResult.appendAlternate('R');
        } else {
            doubleMetaphoneResult.append('R');
        }
        if (this.charAt(string2, n2 + 1) == 'R') {
            return n2 + 2;
        }
        return n2 + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int handleS(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2, boolean bl2) {
        if (DoubleMetaphone.contains(string2, n2 - 1, 3, "ISL", "YSL")) {
            return n2 + 1;
        }
        if (n2 == 0 && DoubleMetaphone.contains(string2, n2, 5, "SUGAR")) {
            doubleMetaphoneResult.append('X', 'S');
            return n2 + 1;
        }
        if (DoubleMetaphone.contains(string2, n2, 2, "SH")) {
            if (DoubleMetaphone.contains(string2, n2 + 1, 4, "HEIM", "HOEK", "HOLM", "HOLZ")) {
                doubleMetaphoneResult.append('S');
                return n2 + 2;
            }
            doubleMetaphoneResult.append('X');
            return n2 + 2;
        }
        if (DoubleMetaphone.contains(string2, n2, 3, "SIO", "SIA") || DoubleMetaphone.contains(string2, n2, 4, "SIAN")) {
            if (bl2) {
                doubleMetaphoneResult.append('S');
                return n2 + 3;
            }
            doubleMetaphoneResult.append('S', 'X');
            return n2 + 3;
        }
        if (n2 == 0 && DoubleMetaphone.contains(string2, n2 + 1, 1, "M", "N", "L", "W") || DoubleMetaphone.contains(string2, n2 + 1, 1, "Z")) {
            doubleMetaphoneResult.append('S', 'X');
            if (!DoubleMetaphone.contains(string2, n2 + 1, 1, "Z")) return n2 + 1;
            return n2 + 2;
        }
        if (DoubleMetaphone.contains(string2, n2, 2, "SC")) {
            return this.handleSC(string2, doubleMetaphoneResult, n2);
        }
        if (n2 == string2.length() - 1 && DoubleMetaphone.contains(string2, n2 - 2, 2, "AI", "OI")) {
            doubleMetaphoneResult.appendAlternate('S');
        } else {
            doubleMetaphoneResult.append('S');
        }
        if (!DoubleMetaphone.contains(string2, n2 + 1, 1, "S", "Z")) return n2 + 1;
        return n2 + 2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int handleSC(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if (this.charAt(string2, n2 + 2) == 'H') {
            if (DoubleMetaphone.contains(string2, n2 + 3, 2, "OO", "ER", "EN", "UY", "ED", "EM")) {
                if (DoubleMetaphone.contains(string2, n2 + 3, 2, "ER", "EN")) {
                    doubleMetaphoneResult.append("X", "SK");
                    do {
                        return n2 + 3;
                        break;
                    } while (true);
                }
                doubleMetaphoneResult.append("SK");
                return n2 + 3;
            }
            if (n2 == 0 && !this.isVowel(this.charAt(string2, 3)) && this.charAt(string2, 3) != 'W') {
                doubleMetaphoneResult.append('X', 'S');
                return n2 + 3;
            }
            doubleMetaphoneResult.append('X');
            return n2 + 3;
        }
        if (DoubleMetaphone.contains(string2, n2 + 2, 1, "I", "E", "Y")) {
            doubleMetaphoneResult.append('S');
            return n2 + 3;
        }
        doubleMetaphoneResult.append("SK");
        return n2 + 3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int handleT(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if (DoubleMetaphone.contains(string2, n2, 4, "TION")) {
            doubleMetaphoneResult.append('X');
            return n2 + 3;
        }
        if (DoubleMetaphone.contains(string2, n2, 3, "TIA", "TCH")) {
            doubleMetaphoneResult.append('X');
            return n2 + 3;
        }
        if (DoubleMetaphone.contains(string2, n2, 2, "TH") || DoubleMetaphone.contains(string2, n2, 3, "TTH")) {
            if (DoubleMetaphone.contains(string2, n2 + 2, 2, "OM", "AM") || DoubleMetaphone.contains(string2, 0, 4, "VAN ", "VON ") || DoubleMetaphone.contains(string2, 0, 3, "SCH")) {
                doubleMetaphoneResult.append('T');
                do {
                    return n2 + 2;
                    break;
                } while (true);
            }
            doubleMetaphoneResult.append('0', 'T');
            return n2 + 2;
        }
        doubleMetaphoneResult.append('T');
        if (!DoubleMetaphone.contains(string2, n2 + 1, 1, "T", "D")) return n2 + 1;
        return n2 + 2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int handleW(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if (DoubleMetaphone.contains(string2, n2, 2, "WR")) {
            doubleMetaphoneResult.append('R');
            return n2 + 2;
        }
        if (n2 == 0 && (this.isVowel(this.charAt(string2, n2 + 1)) || DoubleMetaphone.contains(string2, n2, 2, "WH"))) {
            if (this.isVowel(this.charAt(string2, n2 + 1))) {
                doubleMetaphoneResult.append('A', 'F');
                do {
                    return n2 + 1;
                    break;
                } while (true);
            }
            doubleMetaphoneResult.append('A');
            return n2 + 1;
        }
        if (n2 == string2.length() - 1 && this.isVowel(this.charAt(string2, n2 - 1)) || DoubleMetaphone.contains(string2, n2 - 1, 5, "EWSKI", "EWSKY", "OWSKI", "OWSKY") || DoubleMetaphone.contains(string2, 0, 3, "SCH")) {
            doubleMetaphoneResult.appendAlternate('F');
            return n2 + 1;
        }
        if (!DoubleMetaphone.contains(string2, n2, 4, "WICZ", "WITZ")) return n2 + 1;
        doubleMetaphoneResult.append("TS", "FX");
        return n2 + 4;
    }

    private int handleX(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2) {
        if (n2 == 0) {
            doubleMetaphoneResult.append('S');
            return n2 + 1;
        }
        if (n2 != string2.length() - 1 || !DoubleMetaphone.contains(string2, n2 - 3, 3, "IAU", "EAU") && !DoubleMetaphone.contains(string2, n2 - 2, 2, "AU", "OU")) {
            doubleMetaphoneResult.append("KS");
        }
        if (DoubleMetaphone.contains(string2, n2 + 1, 1, "C", "X")) {
            return n2 + 2;
        }
        return n2 + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int handleZ(String string2, DoubleMetaphoneResult doubleMetaphoneResult, int n2, boolean bl2) {
        if (this.charAt(string2, n2 + 1) == 'H') {
            doubleMetaphoneResult.append('J');
            return n2 + 2;
        }
        if (DoubleMetaphone.contains(string2, n2 + 1, 2, "ZO", "ZI", "ZA") || bl2 && n2 > 0 && this.charAt(string2, n2 - 1) != 'T') {
            doubleMetaphoneResult.append("S", "TS");
        } else {
            doubleMetaphoneResult.append('S');
        }
        if (this.charAt(string2, n2 + 1) == 'Z') {
            return n2 + 2;
        }
        return n2 + 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSilentStart(String string2) {
        boolean bl2 = false;
        String[] arrstring = SILENT_START;
        int n2 = arrstring.length;
        int n3 = 0;
        do {
            boolean bl3 = bl2;
            if (n3 >= n2) return bl3;
            if (string2.startsWith(arrstring[n3])) {
                return true;
            }
            ++n3;
        } while (true);
    }

    private boolean isSlavoGermanic(String string2) {
        if (string2.indexOf(87) > -1 || string2.indexOf(75) > -1 || string2.indexOf("CZ") > -1 || string2.indexOf("WITZ") > -1) {
            return true;
        }
        return false;
    }

    private boolean isVowel(char c2) {
        if ("AEIOUY".indexOf(c2) != -1) {
            return true;
        }
        return false;
    }

    protected char charAt(String string2, int n2) {
        if (n2 < 0 || n2 >= string2.length()) {
            return '\u0000';
        }
        return string2.charAt(n2);
    }

    public String doubleMetaphone(String string2) {
        return this.doubleMetaphone(string2, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String doubleMetaphone(String string2, boolean bl2) {
        if ((string2 = this.cleanInput(string2)) == null) {
            return null;
        }
        boolean bl3 = this.isSlavoGermanic(string2);
        int n2 = this.isSilentStart(string2) ? 1 : 0;
        DoubleMetaphoneResult doubleMetaphoneResult = new DoubleMetaphoneResult(this.getMaxCodeLen());
        block25 : while (!doubleMetaphoneResult.isComplete() && n2 <= string2.length() - 1) {
            switch (string2.charAt(n2)) {
                default: {
                    ++n2;
                    continue block25;
                }
                case 'A': 
                case 'E': 
                case 'I': 
                case 'O': 
                case 'U': 
                case 'Y': {
                    n2 = this.handleAEIOUY(doubleMetaphoneResult, n2);
                    continue block25;
                }
                case 'B': {
                    doubleMetaphoneResult.append('P');
                    if (this.charAt(string2, n2 + 1) == 'B') {
                        n2 += 2;
                        continue block25;
                    }
                    ++n2;
                    continue block25;
                }
                case '\u00c7': {
                    doubleMetaphoneResult.append('S');
                    ++n2;
                    continue block25;
                }
                case 'C': {
                    n2 = this.handleC(string2, doubleMetaphoneResult, n2);
                    continue block25;
                }
                case 'D': {
                    n2 = this.handleD(string2, doubleMetaphoneResult, n2);
                    continue block25;
                }
                case 'F': {
                    doubleMetaphoneResult.append('F');
                    if (this.charAt(string2, n2 + 1) == 'F') {
                        n2 += 2;
                        continue block25;
                    }
                    ++n2;
                    continue block25;
                }
                case 'G': {
                    n2 = this.handleG(string2, doubleMetaphoneResult, n2, bl3);
                    continue block25;
                }
                case 'H': {
                    n2 = this.handleH(string2, doubleMetaphoneResult, n2);
                    continue block25;
                }
                case 'J': {
                    n2 = this.handleJ(string2, doubleMetaphoneResult, n2, bl3);
                    continue block25;
                }
                case 'K': {
                    doubleMetaphoneResult.append('K');
                    if (this.charAt(string2, n2 + 1) == 'K') {
                        n2 += 2;
                        continue block25;
                    }
                    ++n2;
                    continue block25;
                }
                case 'L': {
                    n2 = this.handleL(string2, doubleMetaphoneResult, n2);
                    continue block25;
                }
                case 'M': {
                    doubleMetaphoneResult.append('M');
                    if (this.conditionM0(string2, n2)) {
                        n2 += 2;
                        continue block25;
                    }
                    ++n2;
                    continue block25;
                }
                case 'N': {
                    doubleMetaphoneResult.append('N');
                    if (this.charAt(string2, n2 + 1) == 'N') {
                        n2 += 2;
                        continue block25;
                    }
                    ++n2;
                    continue block25;
                }
                case '\u00d1': {
                    doubleMetaphoneResult.append('N');
                    ++n2;
                    continue block25;
                }
                case 'P': {
                    n2 = this.handleP(string2, doubleMetaphoneResult, n2);
                    continue block25;
                }
                case 'Q': {
                    doubleMetaphoneResult.append('K');
                    if (this.charAt(string2, n2 + 1) == 'Q') {
                        n2 += 2;
                        continue block25;
                    }
                    ++n2;
                    continue block25;
                }
                case 'R': {
                    n2 = this.handleR(string2, doubleMetaphoneResult, n2, bl3);
                    continue block25;
                }
                case 'S': {
                    n2 = this.handleS(string2, doubleMetaphoneResult, n2, bl3);
                    continue block25;
                }
                case 'T': {
                    n2 = this.handleT(string2, doubleMetaphoneResult, n2);
                    continue block25;
                }
                case 'V': {
                    doubleMetaphoneResult.append('F');
                    if (this.charAt(string2, n2 + 1) == 'V') {
                        n2 += 2;
                        continue block25;
                    }
                    ++n2;
                    continue block25;
                }
                case 'W': {
                    n2 = this.handleW(string2, doubleMetaphoneResult, n2);
                    continue block25;
                }
                case 'X': {
                    n2 = this.handleX(string2, doubleMetaphoneResult, n2);
                    continue block25;
                }
                case 'Z': 
            }
            n2 = this.handleZ(string2, doubleMetaphoneResult, n2, bl3);
        }
        if (bl2) {
            return doubleMetaphoneResult.getAlternate();
        }
        return doubleMetaphoneResult.getPrimary();
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("DoubleMetaphone encode parameter is not of type String");
        }
        return this.doubleMetaphone((String)object);
    }

    @Override
    public String encode(String string2) {
        return this.doubleMetaphone(string2);
    }

    public int getMaxCodeLen() {
        return this.maxCodeLen;
    }

    public boolean isDoubleMetaphoneEqual(String string2, String string3) {
        return this.isDoubleMetaphoneEqual(string2, string3, false);
    }

    public boolean isDoubleMetaphoneEqual(String string2, String string3, boolean bl2) {
        return this.doubleMetaphone(string2, bl2).equals(this.doubleMetaphone(string3, bl2));
    }

    public void setMaxCodeLen(int n2) {
        this.maxCodeLen = n2;
    }

    public class DoubleMetaphoneResult {
        private final StringBuilder alternate;
        private final int maxLength;
        private final StringBuilder primary;

        public DoubleMetaphoneResult(int n2) {
            this.primary = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
            this.alternate = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
            this.maxLength = n2;
        }

        public void append(char c2) {
            this.appendPrimary(c2);
            this.appendAlternate(c2);
        }

        public void append(char c2, char c3) {
            this.appendPrimary(c2);
            this.appendAlternate(c3);
        }

        public void append(String string2) {
            this.appendPrimary(string2);
            this.appendAlternate(string2);
        }

        public void append(String string2, String string3) {
            this.appendPrimary(string2);
            this.appendAlternate(string3);
        }

        public void appendAlternate(char c2) {
            if (this.alternate.length() < this.maxLength) {
                this.alternate.append(c2);
            }
        }

        public void appendAlternate(String string2) {
            int n2 = this.maxLength - this.alternate.length();
            if (string2.length() <= n2) {
                this.alternate.append(string2);
                return;
            }
            this.alternate.append(string2.substring(0, n2));
        }

        public void appendPrimary(char c2) {
            if (this.primary.length() < this.maxLength) {
                this.primary.append(c2);
            }
        }

        public void appendPrimary(String string2) {
            int n2 = this.maxLength - this.primary.length();
            if (string2.length() <= n2) {
                this.primary.append(string2);
                return;
            }
            this.primary.append(string2.substring(0, n2));
        }

        public String getAlternate() {
            return this.alternate.toString();
        }

        public String getPrimary() {
            return this.primary.toString();
        }

        public boolean isComplete() {
            if (this.primary.length() >= this.maxLength && this.alternate.length() >= this.maxLength) {
                return true;
            }
            return false;
        }
    }

}

