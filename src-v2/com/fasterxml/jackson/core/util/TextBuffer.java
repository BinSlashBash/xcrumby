/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.util.BufferRecycler;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public final class TextBuffer {
    static final int MAX_SEGMENT_LEN = 262144;
    static final int MIN_SEGMENT_LEN = 1000;
    static final char[] NO_CHARS = new char[0];
    private final BufferRecycler _allocator;
    private char[] _currentSegment;
    private int _currentSize;
    private boolean _hasSegments = false;
    private char[] _inputBuffer;
    private int _inputLen;
    private int _inputStart;
    private char[] _resultArray;
    private String _resultString;
    private int _segmentSize;
    private ArrayList<char[]> _segments;

    public TextBuffer(BufferRecycler bufferRecycler) {
        this._allocator = bufferRecycler;
    }

    private char[] buf(int n2) {
        if (this._allocator != null) {
            return this._allocator.allocCharBuffer(2, n2);
        }
        return new char[Math.max(n2, 1000)];
    }

    private char[] carr(int n2) {
        return new char[n2];
    }

    private void clearSegments() {
        this._hasSegments = false;
        this._segments.clear();
        this._segmentSize = 0;
        this._currentSize = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void expand(int n2) {
        if (this._segments == null) {
            this._segments = new ArrayList();
        }
        char[] arrc = this._currentSegment;
        this._hasSegments = true;
        this._segments.add(arrc);
        this._segmentSize += arrc.length;
        this._currentSize = 0;
        n2 = arrc.length;
        int n3 = n2 + (n2 >> 1);
        if (n3 < 1000) {
            n2 = 1000;
        } else {
            n2 = n3;
            if (n3 > 262144) {
                n2 = 262144;
            }
        }
        this._currentSegment = this.carr(n2);
    }

    private char[] resultArray() {
        if (this._resultString != null) {
            return this._resultString.toCharArray();
        }
        if (this._inputStart >= 0) {
            int n2 = this._inputLen;
            if (n2 < 1) {
                return NO_CHARS;
            }
            int n3 = this._inputStart;
            if (n3 == 0) {
                return Arrays.copyOf(this._inputBuffer, n2);
            }
            return Arrays.copyOfRange(this._inputBuffer, n3, n3 + n2);
        }
        int n4 = this.size();
        if (n4 < 1) {
            return NO_CHARS;
        }
        int n5 = 0;
        int n6 = 0;
        char[] arrc = this.carr(n4);
        if (this._segments != null) {
            n4 = 0;
            int n7 = this._segments.size();
            do {
                n5 = n6;
                if (n4 >= n7) break;
                char[] arrc2 = this._segments.get(n4);
                n5 = arrc2.length;
                System.arraycopy(arrc2, 0, arrc, n6, n5);
                n6 += n5;
                ++n4;
            } while (true);
        }
        System.arraycopy(this._currentSegment, 0, arrc, n5, this._currentSize);
        return arrc;
    }

    private void unshare(int n2) {
        int n3 = this._inputLen;
        this._inputLen = 0;
        char[] arrc = this._inputBuffer;
        this._inputBuffer = null;
        int n4 = this._inputStart;
        this._inputStart = -1;
        n2 = n3 + n2;
        if (this._currentSegment == null || n2 > this._currentSegment.length) {
            this._currentSegment = this.buf(n2);
        }
        if (n3 > 0) {
            System.arraycopy(arrc, n4, this._currentSegment, 0, n3);
        }
        this._segmentSize = 0;
        this._currentSize = n3;
    }

    public void append(char c2) {
        char[] arrc;
        if (this._inputStart >= 0) {
            this.unshare(16);
        }
        this._resultString = null;
        this._resultArray = null;
        char[] arrc2 = arrc = this._currentSegment;
        if (this._currentSize >= arrc.length) {
            this.expand(1);
            arrc2 = this._currentSegment;
        }
        int n2 = this._currentSize;
        this._currentSize = n2 + 1;
        arrc2[n2] = c2;
    }

    public void append(String string2, int n2, int n3) {
        if (this._inputStart >= 0) {
            this.unshare(n3);
        }
        this._resultString = null;
        this._resultArray = null;
        char[] arrc = this._currentSegment;
        int n4 = arrc.length - this._currentSize;
        if (n4 >= n3) {
            string2.getChars(n2, n2 + n3, arrc, this._currentSize);
            this._currentSize += n3;
            return;
        }
        int n5 = n2;
        int n6 = n3;
        if (n4 > 0) {
            string2.getChars(n2, n2 + n4, arrc, this._currentSize);
            n6 = n3 - n4;
            n5 = n2 + n4;
        }
        do {
            this.expand(n6);
            n2 = Math.min(this._currentSegment.length, n6);
            string2.getChars(n5, n5 + n2, this._currentSegment, 0);
            this._currentSize += n2;
            n5 += n2;
            n6 = n2 = n6 - n2;
        } while (n2 > 0);
    }

    public void append(char[] arrc, int n2, int n3) {
        if (this._inputStart >= 0) {
            this.unshare(n3);
        }
        this._resultString = null;
        this._resultArray = null;
        char[] arrc2 = this._currentSegment;
        int n4 = arrc2.length - this._currentSize;
        if (n4 >= n3) {
            System.arraycopy(arrc, n2, arrc2, this._currentSize, n3);
            this._currentSize += n3;
            return;
        }
        int n5 = n2;
        int n6 = n3;
        if (n4 > 0) {
            System.arraycopy(arrc, n2, arrc2, this._currentSize, n4);
            n5 = n2 + n4;
            n6 = n3 - n4;
        }
        do {
            this.expand(n6);
            n2 = Math.min(this._currentSegment.length, n6);
            System.arraycopy(arrc, n5, this._currentSegment, 0, n2);
            this._currentSize += n2;
            n5 += n2;
            n6 = n2 = n6 - n2;
        } while (n2 > 0);
    }

    public char[] contentsAsArray() {
        char[] arrc;
        char[] arrc2 = arrc = this._resultArray;
        if (arrc == null) {
            this._resultArray = arrc2 = this.resultArray();
        }
        return arrc2;
    }

    public BigDecimal contentsAsDecimal() throws NumberFormatException {
        if (this._resultArray != null) {
            return NumberInput.parseBigDecimal(this._resultArray);
        }
        if (this._inputStart >= 0 && this._inputBuffer != null) {
            return NumberInput.parseBigDecimal(this._inputBuffer, this._inputStart, this._inputLen);
        }
        if (this._segmentSize == 0 && this._currentSegment != null) {
            return NumberInput.parseBigDecimal(this._currentSegment, 0, this._currentSize);
        }
        return NumberInput.parseBigDecimal(this.contentsAsArray());
    }

    public double contentsAsDouble() throws NumberFormatException {
        return NumberInput.parseDouble(this.contentsAsString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public String contentsAsString() {
        if (this._resultString != null) return this._resultString;
        if (this._resultArray != null) {
            this._resultString = new String(this._resultArray);
            return this._resultString;
        }
        if (this._inputStart >= 0) {
            if (this._inputLen < 1) {
                this._resultString = "";
                return "";
            }
            this._resultString = new String(this._inputBuffer, this._inputStart, this._inputLen);
            return this._resultString;
        }
        int n2 = this._segmentSize;
        int n3 = this._currentSize;
        if (n2 == 0) {
            String string2 = n3 == 0 ? "" : new String(this._currentSegment, 0, n3);
            this._resultString = string2;
            return this._resultString;
        }
        StringBuilder stringBuilder = new StringBuilder(n2 + n3);
        if (this._segments != null) {
            n3 = this._segments.size();
            for (n2 = 0; n2 < n3; ++n2) {
                char[] arrc = this._segments.get(n2);
                stringBuilder.append(arrc, 0, arrc.length);
            }
        }
        stringBuilder.append(this._currentSegment, 0, this._currentSize);
        this._resultString = stringBuilder.toString();
        return this._resultString;
    }

    public char[] emptyAndGetCurrentSegment() {
        char[] arrc;
        this._inputStart = -1;
        this._currentSize = 0;
        this._inputLen = 0;
        this._inputBuffer = null;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        char[] arrc2 = arrc = this._currentSegment;
        if (arrc == null) {
            this._currentSegment = arrc2 = this.buf(0);
        }
        return arrc2;
    }

    public void ensureNotShared() {
        if (this._inputStart >= 0) {
            this.unshare(16);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public char[] expandCurrentSegment() {
        char[] arrc = this._currentSegment;
        int n2 = arrc.length;
        n2 = n2 == 262144 ? 262145 : Math.min(262144, (n2 >> 1) + n2);
        this._currentSegment = arrc = Arrays.copyOf(arrc, n2);
        return arrc;
    }

    public char[] expandCurrentSegment(int n2) {
        char[] arrc = this._currentSegment;
        if (arrc.length >= n2) {
            return arrc;
        }
        this._currentSegment = arrc = Arrays.copyOf(arrc, n2);
        return arrc;
    }

    /*
     * Enabled aggressive block sorting
     */
    public char[] finishCurrentSegment() {
        char[] arrc;
        if (this._segments == null) {
            this._segments = new ArrayList();
        }
        this._hasSegments = true;
        this._segments.add(this._currentSegment);
        int n2 = this._currentSegment.length;
        this._segmentSize += n2;
        this._currentSize = 0;
        int n3 = n2 + (n2 >> 1);
        if (n3 < 1000) {
            n2 = 1000;
        } else {
            n2 = n3;
            if (n3 > 262144) {
                n2 = 262144;
            }
        }
        this._currentSegment = arrc = this.carr(n2);
        return arrc;
    }

    /*
     * Enabled aggressive block sorting
     */
    public char[] getCurrentSegment() {
        if (this._inputStart >= 0) {
            this.unshare(1);
            return this._currentSegment;
        }
        char[] arrc = this._currentSegment;
        if (arrc == null) {
            this._currentSegment = this.buf(0);
            return this._currentSegment;
        }
        if (this._currentSize < arrc.length) return this._currentSegment;
        this.expand(1);
        return this._currentSegment;
    }

    public int getCurrentSegmentSize() {
        return this._currentSize;
    }

    public char[] getTextBuffer() {
        if (this._inputStart >= 0) {
            return this._inputBuffer;
        }
        if (this._resultArray != null) {
            return this._resultArray;
        }
        if (this._resultString != null) {
            char[] arrc;
            this._resultArray = arrc = this._resultString.toCharArray();
            return arrc;
        }
        if (!this._hasSegments) {
            return this._currentSegment;
        }
        return this.contentsAsArray();
    }

    public int getTextOffset() {
        if (this._inputStart >= 0) {
            return this._inputStart;
        }
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean hasTextAsCharacters() {
        if (this._inputStart >= 0 || this._resultArray != null || this._resultString == null) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void releaseBuffers() {
        if (this._allocator == null) {
            this.resetWithEmpty();
            return;
        } else {
            if (this._currentSegment == null) return;
            {
                this.resetWithEmpty();
                char[] arrc = this._currentSegment;
                this._currentSegment = null;
                this._allocator.releaseCharBuffer(2, arrc);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void resetWithCopy(char[] arrc, int n2, int n3) {
        this._inputBuffer = null;
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        } else if (this._currentSegment == null) {
            this._currentSegment = this.buf(n3);
        }
        this._segmentSize = 0;
        this._currentSize = 0;
        this.append(arrc, n2, n3);
    }

    public void resetWithEmpty() {
        this._inputStart = -1;
        this._currentSize = 0;
        this._inputLen = 0;
        this._inputBuffer = null;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
    }

    public void resetWithShared(char[] arrc, int n2, int n3) {
        this._resultString = null;
        this._resultArray = null;
        this._inputBuffer = arrc;
        this._inputStart = n2;
        this._inputLen = n3;
        if (this._hasSegments) {
            this.clearSegments();
        }
    }

    public void resetWithString(String string2) {
        this._inputBuffer = null;
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = string2;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        this._currentSize = 0;
    }

    public void setCurrentLength(int n2) {
        this._currentSize = n2;
    }

    public int size() {
        if (this._inputStart >= 0) {
            return this._inputLen;
        }
        if (this._resultArray != null) {
            return this._resultArray.length;
        }
        if (this._resultString != null) {
            return this._resultString.length();
        }
        return this._segmentSize + this._currentSize;
    }

    public String toString() {
        return this.contentsAsString();
    }
}

