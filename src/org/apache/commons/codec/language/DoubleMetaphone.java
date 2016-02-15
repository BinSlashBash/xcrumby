package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class DoubleMetaphone
  implements StringEncoder
{
  private static final String[] ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER = { "ES", "EP", "EB", "EL", "EY", "IB", "IL", "IN", "IE", "EI", "ER" };
  private static final String[] L_R_N_M_B_H_F_V_W_SPACE;
  private static final String[] L_T_K_S_N_M_B_Z = { "L", "T", "K", "S", "N", "M", "B", "Z" };
  private static final String[] SILENT_START = { "GN", "KN", "PN", "WR", "PS" };
  private static final String VOWELS = "AEIOUY";
  private int maxCodeLen = 4;
  
  static
  {
    L_R_N_M_B_H_F_V_W_SPACE = new String[] { "L", "R", "N", "M", "B", "H", "F", "V", "W", " " };
  }
  
  private String cleanInput(String paramString)
  {
    if (paramString == null) {}
    do
    {
      return null;
      paramString = paramString.trim();
    } while (paramString.length() == 0);
    return paramString.toUpperCase(Locale.ENGLISH);
  }
  
  private boolean conditionC0(String paramString, int paramInt)
  {
    boolean bool2 = false;
    boolean bool1;
    if (contains(paramString, paramInt, 4, new String[] { "CHIA" })) {
      bool1 = true;
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return bool1;
            bool1 = bool2;
          } while (paramInt <= 1);
          bool1 = bool2;
        } while (isVowel(charAt(paramString, paramInt - 2)));
        bool1 = bool2;
      } while (!contains(paramString, paramInt - 1, 3, new String[] { "ACH" }));
      int i = charAt(paramString, paramInt + 2);
      if ((i != 73) && (i != 69)) {
        break;
      }
      bool1 = bool2;
    } while (!contains(paramString, paramInt - 2, 6, new String[] { "BACHER", "MACHER" }));
    return true;
  }
  
  private boolean conditionCH0(String paramString, int paramInt)
  {
    if (paramInt != 0) {}
    do
    {
      do
      {
        return false;
        if (contains(paramString, paramInt + 1, 5, new String[] { "HARAC", "HARIS" })) {
          break;
        }
      } while (!contains(paramString, paramInt + 1, 3, new String[] { "HOR", "HYM", "HIA", "HEM" }));
    } while (contains(paramString, 0, 5, new String[] { "CHORE" }));
    return true;
  }
  
  private boolean conditionCH1(String paramString, int paramInt)
  {
    boolean bool2 = false;
    if (!contains(paramString, 0, 4, new String[] { "VAN ", "VON " })) {
      if (!contains(paramString, 0, 3, new String[] { "SCH" })) {
        if (!contains(paramString, paramInt - 2, 6, new String[] { "ORCHES", "ARCHIT", "ORCHID" })) {
          if (!contains(paramString, paramInt + 2, 1, new String[] { "T", "S" }))
          {
            if (!contains(paramString, paramInt - 1, 1, new String[] { "A", "O", "U", "E" }))
            {
              bool1 = bool2;
              if (paramInt != 0) {
                return bool1;
              }
            }
            if (!contains(paramString, paramInt + 2, 1, L_R_N_M_B_H_F_V_W_SPACE))
            {
              bool1 = bool2;
              if (paramInt + 1 != paramString.length() - 1) {
                return bool1;
              }
            }
          }
        }
      }
    }
    boolean bool1 = true;
    return bool1;
  }
  
  private boolean conditionL0(String paramString, int paramInt)
  {
    if (paramInt == paramString.length() - 3) {
      if (!contains(paramString, paramInt - 1, 4, new String[] { "ILLO", "ILLA", "ALLE" })) {}
    }
    do
    {
      return true;
      if (!contains(paramString, paramString.length() - 2, 2, new String[] { "AS", "OS" })) {
        if (!contains(paramString, paramString.length() - 1, 1, new String[] { "A", "O" })) {
          break;
        }
      }
    } while (contains(paramString, paramInt - 1, 4, new String[] { "ALLE" }));
    return false;
  }
  
  private boolean conditionM0(String paramString, int paramInt)
  {
    if (charAt(paramString, paramInt + 1) == 'M') {}
    do
    {
      do
      {
        return true;
        if (!contains(paramString, paramInt - 1, 3, new String[] { "UMB" })) {
          break;
        }
      } while (paramInt + 1 == paramString.length() - 1);
    } while (contains(paramString, paramInt + 2, 2, new String[] { "ER" }));
    return false;
  }
  
  protected static boolean contains(String paramString, int paramInt1, int paramInt2, String... paramVarArgs)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramInt1 >= 0)
    {
      bool1 = bool2;
      if (paramInt1 + paramInt2 <= paramString.length())
      {
        paramString = paramString.substring(paramInt1, paramInt1 + paramInt2);
        paramInt2 = paramVarArgs.length;
        paramInt1 = 0;
      }
    }
    for (;;)
    {
      bool1 = bool2;
      if (paramInt1 < paramInt2)
      {
        if (paramString.equals(paramVarArgs[paramInt1])) {
          bool1 = true;
        }
      }
      else {
        return bool1;
      }
      paramInt1 += 1;
    }
  }
  
  private int handleAEIOUY(DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if (paramInt == 0) {
      paramDoubleMetaphoneResult.append('A');
    }
    return paramInt + 1;
  }
  
  private int handleC(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if (conditionC0(paramString, paramInt))
    {
      paramDoubleMetaphoneResult.append('K');
      paramInt += 2;
    }
    for (;;)
    {
      return paramInt;
      if (paramInt == 0) {
        if (contains(paramString, paramInt, 6, new String[] { "CAESAR" }))
        {
          paramDoubleMetaphoneResult.append('S');
          paramInt += 2;
          continue;
        }
      }
      if (contains(paramString, paramInt, 2, new String[] { "CH" }))
      {
        paramInt = handleCH(paramString, paramDoubleMetaphoneResult, paramInt);
      }
      else
      {
        if (contains(paramString, paramInt, 2, new String[] { "CZ" })) {
          if (!contains(paramString, paramInt - 2, 4, new String[] { "WICZ" }))
          {
            paramDoubleMetaphoneResult.append('S', 'X');
            paramInt += 2;
            continue;
          }
        }
        if (contains(paramString, paramInt + 1, 3, new String[] { "CIA" }))
        {
          paramDoubleMetaphoneResult.append('X');
          paramInt += 3;
        }
        else
        {
          if ((contains(paramString, paramInt, 2, new String[] { "CC" })) && ((paramInt != 1) || (charAt(paramString, 0) != 'M'))) {
            return handleCC(paramString, paramDoubleMetaphoneResult, paramInt);
          }
          if (contains(paramString, paramInt, 2, new String[] { "CK", "CG", "CQ" }))
          {
            paramDoubleMetaphoneResult.append('K');
            paramInt += 2;
          }
          else
          {
            if (contains(paramString, paramInt, 2, new String[] { "CI", "CE", "CY" }))
            {
              if (contains(paramString, paramInt, 3, new String[] { "CIO", "CIE", "CIA" })) {
                paramDoubleMetaphoneResult.append('S', 'X');
              }
              for (;;)
              {
                paramInt += 2;
                break;
                paramDoubleMetaphoneResult.append('S');
              }
            }
            paramDoubleMetaphoneResult.append('K');
            if (contains(paramString, paramInt + 1, 2, new String[] { " C", " Q", " G" }))
            {
              paramInt += 3;
            }
            else
            {
              if (contains(paramString, paramInt + 1, 1, new String[] { "C", "K", "Q" })) {
                if (!contains(paramString, paramInt + 1, 2, new String[] { "CE", "CI" }))
                {
                  paramInt += 2;
                  continue;
                }
              }
              paramInt += 1;
            }
          }
        }
      }
    }
  }
  
  private int handleCC(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if (contains(paramString, paramInt + 2, 1, new String[] { "I", "E", "H" })) {
      if (!contains(paramString, paramInt + 2, 2, new String[] { "HU" }))
      {
        if ((paramInt != 1) || (charAt(paramString, paramInt - 1) != 'A'))
        {
          if (!contains(paramString, paramInt - 1, 5, new String[] { "UCCEE", "UCCES" })) {}
        }
        else {
          paramDoubleMetaphoneResult.append("KS");
        }
        for (;;)
        {
          return paramInt + 3;
          paramDoubleMetaphoneResult.append('X');
        }
      }
    }
    paramDoubleMetaphoneResult.append('K');
    return paramInt + 2;
  }
  
  private int handleCH(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if (paramInt > 0) {
      if (contains(paramString, paramInt, 4, new String[] { "CHAE" }))
      {
        paramDoubleMetaphoneResult.append('K', 'X');
        return paramInt + 2;
      }
    }
    if (conditionCH0(paramString, paramInt))
    {
      paramDoubleMetaphoneResult.append('K');
      return paramInt + 2;
    }
    if (conditionCH1(paramString, paramInt))
    {
      paramDoubleMetaphoneResult.append('K');
      return paramInt + 2;
    }
    if (paramInt > 0) {
      if (contains(paramString, 0, 2, new String[] { "MC" })) {
        paramDoubleMetaphoneResult.append('K');
      }
    }
    for (;;)
    {
      return paramInt + 2;
      paramDoubleMetaphoneResult.append('X', 'K');
      continue;
      paramDoubleMetaphoneResult.append('X');
    }
  }
  
  private int handleD(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if (contains(paramString, paramInt, 2, new String[] { "DG" }))
    {
      if (contains(paramString, paramInt + 2, 1, new String[] { "I", "E", "Y" }))
      {
        paramDoubleMetaphoneResult.append('J');
        return paramInt + 3;
      }
      paramDoubleMetaphoneResult.append("TK");
      return paramInt + 2;
    }
    if (contains(paramString, paramInt, 2, new String[] { "DT", "DD" }))
    {
      paramDoubleMetaphoneResult.append('T');
      return paramInt + 2;
    }
    paramDoubleMetaphoneResult.append('T');
    return paramInt + 1;
  }
  
  private int handleG(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt, boolean paramBoolean)
  {
    if (charAt(paramString, paramInt + 1) == 'H') {
      return handleGH(paramString, paramDoubleMetaphoneResult, paramInt);
    }
    if (charAt(paramString, paramInt + 1) == 'N')
    {
      if ((paramInt == 1) && (isVowel(charAt(paramString, 0))) && (!paramBoolean)) {
        paramDoubleMetaphoneResult.append("KN", "N");
      }
      for (;;)
      {
        return paramInt + 2;
        if ((!contains(paramString, paramInt + 2, 2, new String[] { "EY" })) && (charAt(paramString, paramInt + 1) != 'Y') && (!paramBoolean)) {
          paramDoubleMetaphoneResult.append("N", "KN");
        } else {
          paramDoubleMetaphoneResult.append("KN");
        }
      }
    }
    if ((contains(paramString, paramInt + 1, 2, new String[] { "LI" })) && (!paramBoolean))
    {
      paramDoubleMetaphoneResult.append("KL", "L");
      return paramInt + 2;
    }
    if ((paramInt == 0) && ((charAt(paramString, paramInt + 1) == 'Y') || (contains(paramString, paramInt + 1, 2, ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER))))
    {
      paramDoubleMetaphoneResult.append('K', 'J');
      return paramInt + 2;
    }
    if ((contains(paramString, paramInt + 1, 2, new String[] { "ER" })) || (charAt(paramString, paramInt + 1) == 'Y')) {
      if (!contains(paramString, 0, 6, new String[] { "DANGER", "RANGER", "MANGER" })) {
        if (!contains(paramString, paramInt - 1, 1, new String[] { "E", "I" })) {
          if (!contains(paramString, paramInt - 1, 3, new String[] { "RGY", "OGY" }))
          {
            paramDoubleMetaphoneResult.append('K', 'J');
            return paramInt + 2;
          }
        }
      }
    }
    if (!contains(paramString, paramInt + 1, 1, new String[] { "E", "I", "Y" }))
    {
      if (!contains(paramString, paramInt - 1, 4, new String[] { "AGGI", "OGGI" })) {}
    }
    else
    {
      if (!contains(paramString, 0, 4, new String[] { "VAN ", "VON " })) {
        if (!contains(paramString, 0, 3, new String[] { "SCH" })) {
          if (!contains(paramString, paramInt + 1, 2, new String[] { "ET" })) {
            break label470;
          }
        }
      }
      paramDoubleMetaphoneResult.append('K');
      for (;;)
      {
        return paramInt + 2;
        label470:
        if (contains(paramString, paramInt + 1, 3, new String[] { "IER" })) {
          paramDoubleMetaphoneResult.append('J');
        } else {
          paramDoubleMetaphoneResult.append('J', 'K');
        }
      }
    }
    if (charAt(paramString, paramInt + 1) == 'G')
    {
      paramDoubleMetaphoneResult.append('K');
      return paramInt + 2;
    }
    paramDoubleMetaphoneResult.append('K');
    return paramInt + 1;
  }
  
  private int handleGH(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if ((paramInt > 0) && (!isVowel(charAt(paramString, paramInt - 1))))
    {
      paramDoubleMetaphoneResult.append('K');
      return paramInt + 2;
    }
    if (paramInt == 0)
    {
      if (charAt(paramString, paramInt + 2) == 'I') {
        paramDoubleMetaphoneResult.append('J');
      }
      for (;;)
      {
        return paramInt + 2;
        paramDoubleMetaphoneResult.append('K');
      }
    }
    if (paramInt > 1)
    {
      if (contains(paramString, paramInt - 2, 1, new String[] { "B", "H", "D" })) {}
    }
    else if (paramInt > 2)
    {
      if (contains(paramString, paramInt - 3, 1, new String[] { "B", "H", "D" })) {}
    }
    else
    {
      if (paramInt <= 3) {
        break label171;
      }
      if (!contains(paramString, paramInt - 4, 1, new String[] { "B", "H" })) {
        break label171;
      }
    }
    return paramInt + 2;
    label171:
    if ((paramInt > 2) && (charAt(paramString, paramInt - 1) == 'U')) {
      if (contains(paramString, paramInt - 3, 1, new String[] { "C", "G", "L", "R", "T" })) {
        paramDoubleMetaphoneResult.append('F');
      }
    }
    for (;;)
    {
      return paramInt + 2;
      if ((paramInt > 0) && (charAt(paramString, paramInt - 1) != 'I')) {
        paramDoubleMetaphoneResult.append('K');
      }
    }
  }
  
  private int handleH(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if (((paramInt == 0) || (isVowel(charAt(paramString, paramInt - 1)))) && (isVowel(charAt(paramString, paramInt + 1))))
    {
      paramDoubleMetaphoneResult.append('H');
      return paramInt + 2;
    }
    return paramInt + 1;
  }
  
  private int handleJ(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt, boolean paramBoolean)
  {
    if (!contains(paramString, paramInt, 4, new String[] { "JOSE" }))
    {
      if (!contains(paramString, 0, 4, new String[] { "SAN " })) {}
    }
    else
    {
      if (((paramInt != 0) || (charAt(paramString, paramInt + 4) != ' ')) && (paramString.length() != 4))
      {
        if (!contains(paramString, 0, 4, new String[] { "SAN " })) {}
      }
      else {
        paramDoubleMetaphoneResult.append('H');
      }
      for (;;)
      {
        return paramInt + 1;
        paramDoubleMetaphoneResult.append('J', 'H');
      }
    }
    if (paramInt == 0) {
      if (!contains(paramString, paramInt, 4, new String[] { "JOSE" })) {
        paramDoubleMetaphoneResult.append('J', 'A');
      }
    }
    while (charAt(paramString, paramInt + 1) == 'J')
    {
      return paramInt + 2;
      if ((isVowel(charAt(paramString, paramInt - 1))) && (!paramBoolean) && ((charAt(paramString, paramInt + 1) == 'A') || (charAt(paramString, paramInt + 1) == 'O'))) {
        paramDoubleMetaphoneResult.append('J', 'H');
      } else if (paramInt == paramString.length() - 1) {
        paramDoubleMetaphoneResult.append('J', ' ');
      } else if (!contains(paramString, paramInt + 1, 1, L_T_K_S_N_M_B_Z)) {
        if (!contains(paramString, paramInt - 1, 1, new String[] { "S", "K", "L" })) {
          paramDoubleMetaphoneResult.append('J');
        }
      }
    }
    return paramInt + 1;
  }
  
  private int handleL(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if (charAt(paramString, paramInt + 1) == 'L')
    {
      if (conditionL0(paramString, paramInt)) {
        paramDoubleMetaphoneResult.appendPrimary('L');
      }
      for (;;)
      {
        return paramInt + 2;
        paramDoubleMetaphoneResult.append('L');
      }
    }
    paramDoubleMetaphoneResult.append('L');
    return paramInt + 1;
  }
  
  private int handleP(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if (charAt(paramString, paramInt + 1) == 'H')
    {
      paramDoubleMetaphoneResult.append('F');
      return paramInt + 2;
    }
    paramDoubleMetaphoneResult.append('P');
    if (contains(paramString, paramInt + 1, 1, new String[] { "P", "B" })) {
      return paramInt + 2;
    }
    return paramInt + 1;
  }
  
  private int handleR(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt, boolean paramBoolean)
  {
    if ((paramInt == paramString.length() - 1) && (!paramBoolean)) {
      if (contains(paramString, paramInt - 2, 2, new String[] { "IE" })) {
        if (!contains(paramString, paramInt - 4, 2, new String[] { "ME", "MA" })) {
          paramDoubleMetaphoneResult.appendAlternate('R');
        }
      }
    }
    while (charAt(paramString, paramInt + 1) == 'R')
    {
      return paramInt + 2;
      paramDoubleMetaphoneResult.append('R');
    }
    return paramInt + 1;
  }
  
  private int handleS(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt, boolean paramBoolean)
  {
    if (contains(paramString, paramInt - 1, 3, new String[] { "ISL", "YSL" })) {
      return paramInt + 1;
    }
    if (paramInt == 0) {
      if (contains(paramString, paramInt, 5, new String[] { "SUGAR" }))
      {
        paramDoubleMetaphoneResult.append('X', 'S');
        return paramInt + 1;
      }
    }
    if (contains(paramString, paramInt, 2, new String[] { "SH" }))
    {
      if (contains(paramString, paramInt + 1, 4, new String[] { "HEIM", "HOEK", "HOLM", "HOLZ" })) {
        paramDoubleMetaphoneResult.append('S');
      }
      for (;;)
      {
        return paramInt + 2;
        paramDoubleMetaphoneResult.append('X');
      }
    }
    if (!contains(paramString, paramInt, 3, new String[] { "SIO", "SIA" }))
    {
      if (!contains(paramString, paramInt, 4, new String[] { "SIAN" })) {}
    }
    else
    {
      if (paramBoolean) {
        paramDoubleMetaphoneResult.append('S');
      }
      for (;;)
      {
        return paramInt + 3;
        paramDoubleMetaphoneResult.append('S', 'X');
      }
    }
    if (paramInt == 0)
    {
      if (contains(paramString, paramInt + 1, 1, new String[] { "M", "N", "L", "W" })) {}
    }
    else {
      if (!contains(paramString, paramInt + 1, 1, new String[] { "Z" })) {
        break label308;
      }
    }
    paramDoubleMetaphoneResult.append('S', 'X');
    if (contains(paramString, paramInt + 1, 1, new String[] { "Z" })) {
      return paramInt + 2;
    }
    return paramInt + 1;
    label308:
    if (contains(paramString, paramInt, 2, new String[] { "SC" })) {
      return handleSC(paramString, paramDoubleMetaphoneResult, paramInt);
    }
    if (paramInt == paramString.length() - 1) {
      if (contains(paramString, paramInt - 2, 2, new String[] { "AI", "OI" })) {
        paramDoubleMetaphoneResult.appendAlternate('S');
      }
    }
    while (contains(paramString, paramInt + 1, 1, new String[] { "S", "Z" }))
    {
      return paramInt + 2;
      paramDoubleMetaphoneResult.append('S');
    }
    return paramInt + 1;
  }
  
  private int handleSC(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if (charAt(paramString, paramInt + 2) == 'H') {
      if (contains(paramString, paramInt + 3, 2, new String[] { "OO", "ER", "EN", "UY", "ED", "EM" })) {
        if (contains(paramString, paramInt + 3, 2, new String[] { "ER", "EN" })) {
          paramDoubleMetaphoneResult.append("X", "SK");
        }
      }
    }
    for (;;)
    {
      return paramInt + 3;
      paramDoubleMetaphoneResult.append("SK");
      continue;
      if ((paramInt == 0) && (!isVowel(charAt(paramString, 3))) && (charAt(paramString, 3) != 'W'))
      {
        paramDoubleMetaphoneResult.append('X', 'S');
      }
      else
      {
        paramDoubleMetaphoneResult.append('X');
        continue;
        if (contains(paramString, paramInt + 2, 1, new String[] { "I", "E", "Y" })) {
          paramDoubleMetaphoneResult.append('S');
        } else {
          paramDoubleMetaphoneResult.append("SK");
        }
      }
    }
  }
  
  private int handleT(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if (contains(paramString, paramInt, 4, new String[] { "TION" }))
    {
      paramDoubleMetaphoneResult.append('X');
      return paramInt + 3;
    }
    if (contains(paramString, paramInt, 3, new String[] { "TIA", "TCH" }))
    {
      paramDoubleMetaphoneResult.append('X');
      return paramInt + 3;
    }
    if (!contains(paramString, paramInt, 2, new String[] { "TH" }))
    {
      if (!contains(paramString, paramInt, 3, new String[] { "TTH" })) {}
    }
    else
    {
      if (!contains(paramString, paramInt + 2, 2, new String[] { "OM", "AM" })) {
        if (!contains(paramString, 0, 4, new String[] { "VAN ", "VON " })) {
          if (!contains(paramString, 0, 3, new String[] { "SCH" })) {
            break label180;
          }
        }
      }
      paramDoubleMetaphoneResult.append('T');
      for (;;)
      {
        return paramInt + 2;
        label180:
        paramDoubleMetaphoneResult.append('0', 'T');
      }
    }
    paramDoubleMetaphoneResult.append('T');
    if (contains(paramString, paramInt + 1, 1, new String[] { "T", "D" })) {
      return paramInt + 2;
    }
    return paramInt + 1;
  }
  
  private int handleW(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if (contains(paramString, paramInt, 2, new String[] { "WR" }))
    {
      paramDoubleMetaphoneResult.append('R');
      return paramInt + 2;
    }
    if (paramInt == 0) {
      if (!isVowel(charAt(paramString, paramInt + 1)))
      {
        if (!contains(paramString, paramInt, 2, new String[] { "WH" })) {}
      }
      else
      {
        if (isVowel(charAt(paramString, paramInt + 1))) {
          paramDoubleMetaphoneResult.append('A', 'F');
        }
        for (;;)
        {
          return paramInt + 1;
          paramDoubleMetaphoneResult.append('A');
        }
      }
    }
    if ((paramInt != paramString.length() - 1) || (!isVowel(charAt(paramString, paramInt - 1)))) {
      if (!contains(paramString, paramInt - 1, 5, new String[] { "EWSKI", "EWSKY", "OWSKI", "OWSKY" })) {
        if (!contains(paramString, 0, 3, new String[] { "SCH" })) {
          break label194;
        }
      }
    }
    paramDoubleMetaphoneResult.appendAlternate('F');
    return paramInt + 1;
    label194:
    if (contains(paramString, paramInt, 4, new String[] { "WICZ", "WITZ" }))
    {
      paramDoubleMetaphoneResult.append("TS", "FX");
      return paramInt + 4;
    }
    return paramInt + 1;
  }
  
  private int handleX(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt)
  {
    if (paramInt == 0)
    {
      paramDoubleMetaphoneResult.append('S');
      return paramInt + 1;
    }
    if (paramInt == paramString.length() - 1)
    {
      if (!contains(paramString, paramInt - 3, 3, new String[] { "IAU", "EAU" })) {
        if (contains(paramString, paramInt - 2, 2, new String[] { "AU", "OU" })) {}
      }
    }
    else {
      paramDoubleMetaphoneResult.append("KS");
    }
    if (contains(paramString, paramInt + 1, 1, new String[] { "C", "X" })) {
      return paramInt + 2;
    }
    return paramInt + 1;
  }
  
  private int handleZ(String paramString, DoubleMetaphoneResult paramDoubleMetaphoneResult, int paramInt, boolean paramBoolean)
  {
    if (charAt(paramString, paramInt + 1) == 'H')
    {
      paramDoubleMetaphoneResult.append('J');
      return paramInt + 2;
    }
    if ((contains(paramString, paramInt + 1, 2, new String[] { "ZO", "ZI", "ZA" })) || ((paramBoolean) && (paramInt > 0) && (charAt(paramString, paramInt - 1) != 'T'))) {
      paramDoubleMetaphoneResult.append("S", "TS");
    }
    while (charAt(paramString, paramInt + 1) == 'Z')
    {
      return paramInt + 2;
      paramDoubleMetaphoneResult.append('S');
    }
    return paramInt + 1;
  }
  
  private boolean isSilentStart(String paramString)
  {
    boolean bool2 = false;
    String[] arrayOfString = SILENT_START;
    int j = arrayOfString.length;
    int i = 0;
    for (;;)
    {
      boolean bool1 = bool2;
      if (i < j)
      {
        if (paramString.startsWith(arrayOfString[i])) {
          bool1 = true;
        }
      }
      else {
        return bool1;
      }
      i += 1;
    }
  }
  
  private boolean isSlavoGermanic(String paramString)
  {
    return (paramString.indexOf('W') > -1) || (paramString.indexOf('K') > -1) || (paramString.indexOf("CZ") > -1) || (paramString.indexOf("WITZ") > -1);
  }
  
  private boolean isVowel(char paramChar)
  {
    return "AEIOUY".indexOf(paramChar) != -1;
  }
  
  protected char charAt(String paramString, int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= paramString.length())) {
      return '\000';
    }
    return paramString.charAt(paramInt);
  }
  
  public String doubleMetaphone(String paramString)
  {
    return doubleMetaphone(paramString, false);
  }
  
  public String doubleMetaphone(String paramString, boolean paramBoolean)
  {
    paramString = cleanInput(paramString);
    if (paramString == null) {
      return null;
    }
    boolean bool = isSlavoGermanic(paramString);
    int i;
    DoubleMetaphoneResult localDoubleMetaphoneResult;
    if (isSilentStart(paramString))
    {
      i = 1;
      localDoubleMetaphoneResult = new DoubleMetaphoneResult(getMaxCodeLen());
    }
    for (;;)
    {
      if ((localDoubleMetaphoneResult.isComplete()) || (i > paramString.length() - 1)) {
        break label751;
      }
      switch (paramString.charAt(i))
      {
      default: 
        i += 1;
        continue;
        i = 0;
        break;
      case 'A': 
      case 'E': 
      case 'I': 
      case 'O': 
      case 'U': 
      case 'Y': 
        i = handleAEIOUY(localDoubleMetaphoneResult, i);
        break;
      case 'B': 
        localDoubleMetaphoneResult.append('P');
        if (charAt(paramString, i + 1) == 'B') {
          i += 2;
        }
        for (;;)
        {
          break;
          i += 1;
        }
      case 'Ç': 
        localDoubleMetaphoneResult.append('S');
        i += 1;
        break;
      case 'C': 
        i = handleC(paramString, localDoubleMetaphoneResult, i);
        break;
      case 'D': 
        i = handleD(paramString, localDoubleMetaphoneResult, i);
        break;
      case 'F': 
        localDoubleMetaphoneResult.append('F');
        if (charAt(paramString, i + 1) == 'F') {
          i += 2;
        }
        for (;;)
        {
          break;
          i += 1;
        }
      case 'G': 
        i = handleG(paramString, localDoubleMetaphoneResult, i, bool);
        break;
      case 'H': 
        i = handleH(paramString, localDoubleMetaphoneResult, i);
        break;
      case 'J': 
        i = handleJ(paramString, localDoubleMetaphoneResult, i, bool);
        break;
      case 'K': 
        localDoubleMetaphoneResult.append('K');
        if (charAt(paramString, i + 1) == 'K') {
          i += 2;
        }
        for (;;)
        {
          break;
          i += 1;
        }
      case 'L': 
        i = handleL(paramString, localDoubleMetaphoneResult, i);
        break;
      case 'M': 
        localDoubleMetaphoneResult.append('M');
        if (conditionM0(paramString, i)) {
          i += 2;
        }
        for (;;)
        {
          break;
          i += 1;
        }
      case 'N': 
        localDoubleMetaphoneResult.append('N');
        if (charAt(paramString, i + 1) == 'N') {
          i += 2;
        }
        for (;;)
        {
          break;
          i += 1;
        }
      case 'Ñ': 
        localDoubleMetaphoneResult.append('N');
        i += 1;
        break;
      case 'P': 
        i = handleP(paramString, localDoubleMetaphoneResult, i);
        break;
      case 'Q': 
        localDoubleMetaphoneResult.append('K');
        if (charAt(paramString, i + 1) == 'Q') {
          i += 2;
        }
        for (;;)
        {
          break;
          i += 1;
        }
      case 'R': 
        i = handleR(paramString, localDoubleMetaphoneResult, i, bool);
        break;
      case 'S': 
        i = handleS(paramString, localDoubleMetaphoneResult, i, bool);
        break;
      case 'T': 
        i = handleT(paramString, localDoubleMetaphoneResult, i);
        break;
      case 'V': 
        localDoubleMetaphoneResult.append('F');
        if (charAt(paramString, i + 1) == 'V') {
          i += 2;
        }
        for (;;)
        {
          break;
          i += 1;
        }
      case 'W': 
        i = handleW(paramString, localDoubleMetaphoneResult, i);
        break;
      case 'X': 
        i = handleX(paramString, localDoubleMetaphoneResult, i);
        break;
      case 'Z': 
        i = handleZ(paramString, localDoubleMetaphoneResult, i, bool);
      }
    }
    label751:
    if (paramBoolean) {
      return localDoubleMetaphoneResult.getAlternate();
    }
    return localDoubleMetaphoneResult.getPrimary();
  }
  
  public Object encode(Object paramObject)
    throws EncoderException
  {
    if (!(paramObject instanceof String)) {
      throw new EncoderException("DoubleMetaphone encode parameter is not of type String");
    }
    return doubleMetaphone((String)paramObject);
  }
  
  public String encode(String paramString)
  {
    return doubleMetaphone(paramString);
  }
  
  public int getMaxCodeLen()
  {
    return this.maxCodeLen;
  }
  
  public boolean isDoubleMetaphoneEqual(String paramString1, String paramString2)
  {
    return isDoubleMetaphoneEqual(paramString1, paramString2, false);
  }
  
  public boolean isDoubleMetaphoneEqual(String paramString1, String paramString2, boolean paramBoolean)
  {
    return doubleMetaphone(paramString1, paramBoolean).equals(doubleMetaphone(paramString2, paramBoolean));
  }
  
  public void setMaxCodeLen(int paramInt)
  {
    this.maxCodeLen = paramInt;
  }
  
  public class DoubleMetaphoneResult
  {
    private final StringBuilder alternate = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
    private final int maxLength;
    private final StringBuilder primary = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
    
    public DoubleMetaphoneResult(int paramInt)
    {
      this.maxLength = paramInt;
    }
    
    public void append(char paramChar)
    {
      appendPrimary(paramChar);
      appendAlternate(paramChar);
    }
    
    public void append(char paramChar1, char paramChar2)
    {
      appendPrimary(paramChar1);
      appendAlternate(paramChar2);
    }
    
    public void append(String paramString)
    {
      appendPrimary(paramString);
      appendAlternate(paramString);
    }
    
    public void append(String paramString1, String paramString2)
    {
      appendPrimary(paramString1);
      appendAlternate(paramString2);
    }
    
    public void appendAlternate(char paramChar)
    {
      if (this.alternate.length() < this.maxLength) {
        this.alternate.append(paramChar);
      }
    }
    
    public void appendAlternate(String paramString)
    {
      int i = this.maxLength - this.alternate.length();
      if (paramString.length() <= i)
      {
        this.alternate.append(paramString);
        return;
      }
      this.alternate.append(paramString.substring(0, i));
    }
    
    public void appendPrimary(char paramChar)
    {
      if (this.primary.length() < this.maxLength) {
        this.primary.append(paramChar);
      }
    }
    
    public void appendPrimary(String paramString)
    {
      int i = this.maxLength - this.primary.length();
      if (paramString.length() <= i)
      {
        this.primary.append(paramString);
        return;
      }
      this.primary.append(paramString.substring(0, i));
    }
    
    public String getAlternate()
    {
      return this.alternate.toString();
    }
    
    public String getPrimary()
    {
      return this.primary.toString();
    }
    
    public boolean isComplete()
    {
      return (this.primary.length() >= this.maxLength) && (this.alternate.length() >= this.maxLength);
    }
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/language/DoubleMetaphone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */