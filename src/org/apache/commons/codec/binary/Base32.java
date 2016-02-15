package org.apache.commons.codec.binary;

public class Base32
  extends BaseNCodec
{
  private static final int BITS_PER_ENCODED_BYTE = 5;
  private static final int BYTES_PER_ENCODED_BLOCK = 8;
  private static final int BYTES_PER_UNENCODED_BLOCK = 5;
  private static final byte[] CHUNK_SEPARATOR = { 13, 10 };
  private static final byte[] DECODE_TABLE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };
  private static final byte[] ENCODE_TABLE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 50, 51, 52, 53, 54, 55 };
  private static final byte[] HEX_DECODE_TABLE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32 };
  private static final byte[] HEX_ENCODE_TABLE = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86 };
  private static final int MASK_5BITS = 31;
  private final int decodeSize;
  private final byte[] decodeTable;
  private final int encodeSize;
  private final byte[] encodeTable;
  private final byte[] lineSeparator;
  
  public Base32()
  {
    this(false);
  }
  
  public Base32(int paramInt)
  {
    this(paramInt, CHUNK_SEPARATOR);
  }
  
  public Base32(int paramInt, byte[] paramArrayOfByte)
  {
    this(paramInt, paramArrayOfByte, false);
  }
  
  public Base32(int paramInt, byte[] paramArrayOfByte, boolean paramBoolean) {}
  
  public Base32(boolean paramBoolean)
  {
    this(0, null, paramBoolean);
  }
  
  void decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, BaseNCodec.Context paramContext)
  {
    if (paramContext.eof) {}
    label889:
    for (;;)
    {
      return;
      if (paramInt2 < 0) {
        paramContext.eof = true;
      }
      int i = 0;
      int j;
      if (i < paramInt2)
      {
        j = paramArrayOfByte[paramInt1];
        if (j == 61) {
          paramContext.eof = true;
        }
      }
      for (;;)
      {
        if ((!paramContext.eof) || (paramContext.modulus < 2)) {
          break label889;
        }
        paramArrayOfByte = ensureBufferSize(this.decodeSize, paramContext);
        switch (paramContext.modulus)
        {
        default: 
          throw new IllegalStateException("Impossible modulus " + paramContext.modulus);
          byte[] arrayOfByte = ensureBufferSize(this.decodeSize, paramContext);
          if ((j >= 0) && (j < this.decodeTable.length))
          {
            j = this.decodeTable[j];
            if (j >= 0)
            {
              paramContext.modulus = ((paramContext.modulus + 1) % 8);
              paramContext.lbitWorkArea = ((paramContext.lbitWorkArea << 5) + j);
              if (paramContext.modulus == 0)
              {
                j = paramContext.pos;
                paramContext.pos = (j + 1);
                arrayOfByte[j] = ((byte)(int)(paramContext.lbitWorkArea >> 32 & 0xFF));
                j = paramContext.pos;
                paramContext.pos = (j + 1);
                arrayOfByte[j] = ((byte)(int)(paramContext.lbitWorkArea >> 24 & 0xFF));
                j = paramContext.pos;
                paramContext.pos = (j + 1);
                arrayOfByte[j] = ((byte)(int)(paramContext.lbitWorkArea >> 16 & 0xFF));
                j = paramContext.pos;
                paramContext.pos = (j + 1);
                arrayOfByte[j] = ((byte)(int)(paramContext.lbitWorkArea >> 8 & 0xFF));
                j = paramContext.pos;
                paramContext.pos = (j + 1);
                arrayOfByte[j] = ((byte)(int)(paramContext.lbitWorkArea & 0xFF));
              }
            }
          }
          i += 1;
          paramInt1 += 1;
          break;
        case 2: 
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea >> 2 & 0xFF));
          return;
        case 3: 
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea >> 7 & 0xFF));
          return;
        case 4: 
          paramContext.lbitWorkArea >>= 4;
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea >> 8 & 0xFF));
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea & 0xFF));
          return;
        case 5: 
          paramContext.lbitWorkArea >>= 1;
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea >> 16 & 0xFF));
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea >> 8 & 0xFF));
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea & 0xFF));
          return;
        case 6: 
          paramContext.lbitWorkArea >>= 6;
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea >> 16 & 0xFF));
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea >> 8 & 0xFF));
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea & 0xFF));
          return;
        case 7: 
          paramContext.lbitWorkArea >>= 3;
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea >> 24 & 0xFF));
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea >> 16 & 0xFF));
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea >> 8 & 0xFF));
          paramInt1 = paramContext.pos;
          paramContext.pos = (paramInt1 + 1);
          paramArrayOfByte[paramInt1] = ((byte)(int)(paramContext.lbitWorkArea & 0xFF));
          return;
        }
      }
    }
  }
  
  void encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, BaseNCodec.Context paramContext)
  {
    if (paramContext.eof) {}
    do
    {
      return;
      if (paramInt2 >= 0) {
        break;
      }
      paramContext.eof = true;
    } while ((paramContext.modulus == 0) && (this.lineLength == 0));
    paramArrayOfByte = ensureBufferSize(this.encodeSize, paramContext);
    paramInt1 = paramContext.pos;
    switch (paramContext.modulus)
    {
    default: 
      throw new IllegalStateException("Impossible modulus " + paramContext.modulus);
    case 1: 
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 3) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea << 2) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
    }
    for (;;)
    {
      paramContext.currentLinePos += paramContext.pos - paramInt1;
      if ((this.lineLength <= 0) || (paramContext.currentLinePos <= 0)) {
        break;
      }
      System.arraycopy(this.lineSeparator, 0, paramArrayOfByte, paramContext.pos, this.lineSeparator.length);
      paramContext.pos += this.lineSeparator.length;
      return;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 11) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 6) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 1) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea << 4) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
      continue;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 19) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 14) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 9) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 4) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea << 1) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
      continue;
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 27) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 22) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 17) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 12) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 7) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 2) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = this.encodeTable[((int)(paramContext.lbitWorkArea << 3) & 0x1F)];
      paramInt2 = paramContext.pos;
      paramContext.pos = (paramInt2 + 1);
      paramArrayOfByte[paramInt2] = 61;
    }
    int i = 0;
    while (i < paramInt2)
    {
      byte[] arrayOfByte = ensureBufferSize(this.encodeSize, paramContext);
      paramContext.modulus = ((paramContext.modulus + 1) % 5);
      int k = paramArrayOfByte[paramInt1];
      int j = k;
      if (k < 0) {
        j = k + 256;
      }
      paramContext.lbitWorkArea = ((paramContext.lbitWorkArea << 8) + j);
      if (paramContext.modulus == 0)
      {
        j = paramContext.pos;
        paramContext.pos = (j + 1);
        arrayOfByte[j] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 35) & 0x1F)];
        j = paramContext.pos;
        paramContext.pos = (j + 1);
        arrayOfByte[j] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 30) & 0x1F)];
        j = paramContext.pos;
        paramContext.pos = (j + 1);
        arrayOfByte[j] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 25) & 0x1F)];
        j = paramContext.pos;
        paramContext.pos = (j + 1);
        arrayOfByte[j] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 20) & 0x1F)];
        j = paramContext.pos;
        paramContext.pos = (j + 1);
        arrayOfByte[j] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 15) & 0x1F)];
        j = paramContext.pos;
        paramContext.pos = (j + 1);
        arrayOfByte[j] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 10) & 0x1F)];
        j = paramContext.pos;
        paramContext.pos = (j + 1);
        arrayOfByte[j] = this.encodeTable[((int)(paramContext.lbitWorkArea >> 5) & 0x1F)];
        j = paramContext.pos;
        paramContext.pos = (j + 1);
        arrayOfByte[j] = this.encodeTable[((int)paramContext.lbitWorkArea & 0x1F)];
        paramContext.currentLinePos += 8;
        if ((this.lineLength > 0) && (this.lineLength <= paramContext.currentLinePos))
        {
          System.arraycopy(this.lineSeparator, 0, arrayOfByte, paramContext.pos, this.lineSeparator.length);
          paramContext.pos += this.lineSeparator.length;
          paramContext.currentLinePos = 0;
        }
      }
      i += 1;
      paramInt1 += 1;
    }
  }
  
  public boolean isInAlphabet(byte paramByte)
  {
    return (paramByte >= 0) && (paramByte < this.decodeTable.length) && (this.decodeTable[paramByte] != -1);
  }
}


/* Location:              /home/dev/Downloads/apk/dex2jar-2.0/crumby-dex2jar.jar!/org/apache/commons/codec/binary/Base32.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */