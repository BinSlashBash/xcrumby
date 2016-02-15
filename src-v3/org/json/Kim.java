package org.json;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import com.google.android.gms.cast.Cast;
import java.util.Arrays;

public class Kim {
    private byte[] bytes;
    private int hashcode;
    public int length;
    private String string;

    public Kim(byte[] bytes, int from, int thru) {
        this.bytes = null;
        this.hashcode = 0;
        this.length = 0;
        this.string = null;
        int sum = 1;
        this.hashcode = 0;
        this.length = thru - from;
        if (this.length > 0) {
            this.bytes = new byte[this.length];
            for (int at = 0; at < this.length; at++) {
                int value = bytes[at + from] & MotionEventCompat.ACTION_MASK;
                sum += value;
                this.hashcode += sum;
                this.bytes[at] = (byte) value;
            }
            this.hashcode += sum << 16;
        }
    }

    public Kim(byte[] bytes, int length) {
        this(bytes, 0, length);
    }

    public Kim(Kim kim, int from, int thru) {
        this(kim.bytes, from, thru);
    }

    public Kim(String string) throws JSONException {
        this.bytes = null;
        this.hashcode = 0;
        this.length = 0;
        this.string = null;
        int stringLength = string.length();
        this.hashcode = 0;
        this.length = 0;
        if (stringLength > 0) {
            int i = 0;
            while (i < stringLength) {
                int c = string.charAt(i);
                if (c <= TransportMediator.KEYCODE_MEDIA_PAUSE) {
                    this.length++;
                } else if (c <= 16383) {
                    this.length += 2;
                } else {
                    if (c >= 55296 && c <= 57343) {
                        i++;
                        int d = string.charAt(i);
                        if (c > 56319 || d < 56320 || d > 57343) {
                            throw new JSONException("Bad UTF16");
                        }
                    }
                    this.length += 3;
                }
                i++;
            }
            this.bytes = new byte[this.length];
            int at = 0;
            int sum = 1;
            i = 0;
            while (i < stringLength) {
                int character = string.charAt(i);
                if (character <= TransportMediator.KEYCODE_MEDIA_PAUSE) {
                    this.bytes[at] = (byte) character;
                    sum += character;
                    this.hashcode += sum;
                    at++;
                } else if (character <= 16383) {
                    b = (character >>> 7) | TransportMediator.FLAG_KEY_MEDIA_NEXT;
                    this.bytes[at] = (byte) b;
                    sum += b;
                    this.hashcode += sum;
                    at++;
                    b = character & TransportMediator.KEYCODE_MEDIA_PAUSE;
                    this.bytes[at] = (byte) b;
                    sum += b;
                    this.hashcode += sum;
                    at++;
                } else {
                    if (character >= 55296 && character <= 56319) {
                        i++;
                        character = (((character & 1023) << 10) | (string.charAt(i) & 1023)) + Cast.MAX_MESSAGE_LENGTH;
                    }
                    b = (character >>> 14) | TransportMediator.FLAG_KEY_MEDIA_NEXT;
                    this.bytes[at] = (byte) b;
                    sum += b;
                    this.hashcode += sum;
                    at++;
                    b = ((character >>> 7) & MotionEventCompat.ACTION_MASK) | TransportMediator.FLAG_KEY_MEDIA_NEXT;
                    this.bytes[at] = (byte) b;
                    sum += b;
                    this.hashcode += sum;
                    at++;
                    b = character & TransportMediator.KEYCODE_MEDIA_PAUSE;
                    this.bytes[at] = (byte) b;
                    sum += b;
                    this.hashcode += sum;
                    at++;
                }
                i++;
            }
            this.hashcode += sum << 16;
        }
    }

    public int characterAt(int at) throws JSONException {
        int c = get(at);
        if ((c & TransportMediator.FLAG_KEY_MEDIA_NEXT) == 0) {
            return c;
        }
        int c1 = get(at + 1);
        int character;
        if ((c1 & TransportMediator.FLAG_KEY_MEDIA_NEXT) == 0) {
            character = ((c & TransportMediator.KEYCODE_MEDIA_PAUSE) << 7) | c1;
            if (character > TransportMediator.KEYCODE_MEDIA_PAUSE) {
                return character;
            }
        }
        int c2 = get(at + 2);
        character = (((c & TransportMediator.KEYCODE_MEDIA_PAUSE) << 14) | ((c1 & TransportMediator.KEYCODE_MEDIA_PAUSE) << 7)) | c2;
        if ((c2 & TransportMediator.FLAG_KEY_MEDIA_NEXT) == 0 && character > 16383 && character <= 1114111) {
            if (character < 55296) {
                return character;
            }
            if (character > 57343) {
                return character;
            }
        }
        throw new JSONException(new StringBuffer().append("Bad character at ").append(at).toString());
    }

    public static int characterSize(int character) throws JSONException {
        if (character < 0 || character > 1114111) {
            throw new JSONException(new StringBuffer().append("Bad character ").append(character).toString());
        } else if (character <= TransportMediator.KEYCODE_MEDIA_PAUSE) {
            return 1;
        } else {
            return character <= 16383 ? 2 : 3;
        }
    }

    public int copy(byte[] bytes, int at) {
        System.arraycopy(this.bytes, 0, bytes, at, this.length);
        return this.length + at;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Kim)) {
            return false;
        }
        Kim that = (Kim) obj;
        if (this == that) {
            return true;
        }
        if (this.hashcode == that.hashcode) {
            return Arrays.equals(this.bytes, that.bytes);
        }
        return false;
    }

    public int get(int at) throws JSONException {
        if (at >= 0 && at <= this.length) {
            return this.bytes[at] & MotionEventCompat.ACTION_MASK;
        }
        throw new JSONException(new StringBuffer().append("Bad character at ").append(at).toString());
    }

    public int hashCode() {
        return this.hashcode;
    }

    public String toString() throws JSONException {
        if (this.string == null) {
            int length = 0;
            char[] chars = new char[this.length];
            int at = 0;
            while (at < this.length) {
                int c = characterAt(at);
                if (c < Cast.MAX_MESSAGE_LENGTH) {
                    chars[length] = (char) c;
                    length++;
                } else {
                    chars[length] = (char) (55296 | ((c - Cast.MAX_MESSAGE_LENGTH) >>> 10));
                    length++;
                    chars[length] = (char) (56320 | (c & 1023));
                    length++;
                }
                at += characterSize(c);
            }
            this.string = new String(chars, 0, length);
        }
        return this.string;
    }
}
