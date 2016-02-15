package okio;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import com.google.android.gms.games.request.GameRequest;
import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Hex;

final class Util {
    public static final Charset UTF_8;

    static {
        UTF_8 = Charset.forName(Hex.DEFAULT_CHARSET_NAME);
    }

    private Util() {
    }

    public static void checkOffsetAndCount(long size, long offset, long byteCount) {
        if ((offset | byteCount) < 0 || offset > size || size - offset < byteCount) {
            throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", new Object[]{Long.valueOf(size), Long.valueOf(offset), Long.valueOf(byteCount)}));
        }
    }

    public static short reverseBytesShort(short s) {
        int i = s & GameRequest.TYPE_ALL;
        return (short) (((MotionEventCompat.ACTION_POINTER_INDEX_MASK & i) >>> 8) | ((i & MotionEventCompat.ACTION_MASK) << 8));
    }

    public static int reverseBytesInt(int i) {
        return ((((ViewCompat.MEASURED_STATE_MASK & i) >>> 24) | ((16711680 & i) >>> 8)) | ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & i) << 8)) | ((i & MotionEventCompat.ACTION_MASK) << 24);
    }

    public static long reverseBytesLong(long v) {
        return ((((((((-72057594037927936L & v) >>> 56) | ((71776119061217280L & v) >>> 40)) | ((280375465082880L & v) >>> 24)) | ((1095216660480L & v) >>> 8)) | ((4278190080L & v) << 8)) | ((16711680 & v) << 24)) | ((65280 & v) << 40)) | ((255 & v) << 56);
    }

    public static void sneakyRethrow(Throwable t) {
        sneakyThrow2(t);
    }

    private static <T extends Throwable> void sneakyThrow2(Throwable t) throws Throwable {
        throw t;
    }
}
