/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class DataUtil {
    private static final int bufferSize = 131072;
    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");
    static final String defaultCharset = "UTF-8";

    private DataUtil() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static String getCharsetFromContentType(String object) {
        if (object == null) {
            return null;
        }
        if (!(object = charsetPattern.matcher((CharSequence)object)).find()) return null;
        String string2 = object.group(1).trim().replace("charset=", "");
        if (string2.isEmpty()) {
            return null;
        }
        object = string2;
        try {
            if (Charset.isSupported(string2)) return object;
            object = string2.toUpperCase(Locale.ENGLISH);
            boolean bl2 = Charset.isSupported((String)object);
            if (bl2) return object;
        }
        catch (IllegalCharsetNameException var0_1) {
            return null;
        }
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static Document load(File object, String object2, String string2) throws IOException {
        Object var3_6 = null;
        try {
            object = new FileInputStream((File)object);
        }
        catch (Throwable var1_2) {
            void var1_3;
            object = var3_6;
            if (object == null) throw var1_3;
            object.close();
            throw var1_3;
        }
        object2 = DataUtil.parseByteData(DataUtil.readToByteBuffer((InputStream)object), (String)object2, string2, Parser.htmlParser());
        if (object == null) return object2;
        {
            catch (Throwable throwable) {}
        }
        object.close();
        return object2;
    }

    public static Document load(InputStream inputStream, String string2, String string3) throws IOException {
        return DataUtil.parseByteData(DataUtil.readToByteBuffer(inputStream), string2, string3, Parser.htmlParser());
    }

    public static Document load(InputStream inputStream, String string2, String string3, Parser parser) throws IOException {
        return DataUtil.parseByteData(DataUtil.readToByteBuffer(inputStream), string2, string3, parser);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Document parseByteData(ByteBuffer object, String string2, String string3, Parser parser) {
        String string4;
        String string5;
        Document document = null;
        if (string2 == null) {
            String string6 = Charset.forName("UTF-8").decode((ByteBuffer)object).toString();
            Document document2 = parser.parseInput(string6, string3);
            Element element = document2.select("meta[http-equiv=content-type], meta[charset]").first();
            document = document2;
            string4 = string6;
            string5 = string2;
            if (element != null) {
                String string7;
                if (element.hasAttr("http-equiv")) {
                    string7 = string4 = DataUtil.getCharsetFromContentType(element.attr("content"));
                    if (string4 == null) {
                        string7 = string4;
                        if (element.hasAttr("charset")) {
                            string7 = string4;
                            try {
                                if (Charset.isSupported(element.attr("charset"))) {
                                    string7 = element.attr("charset");
                                }
                            }
                            catch (IllegalCharsetNameException var4_9) {
                                string7 = null;
                            }
                        }
                    }
                } else {
                    string7 = element.attr("charset");
                }
                document = document2;
                string4 = string6;
                string5 = string2;
                if (string7 != null) {
                    document = document2;
                    string4 = string6;
                    string5 = string2;
                    if (string7.length() != 0) {
                        document = document2;
                        string4 = string6;
                        string5 = string2;
                        if (!string7.equals("UTF-8")) {
                            string5 = string2 = string7.trim().replaceAll("[\"']", "");
                            object.rewind();
                            string4 = Charset.forName(string2).decode((ByteBuffer)object).toString();
                            document = null;
                        }
                    }
                }
            }
        } else {
            Validate.notEmpty(string2, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
            string4 = Charset.forName(string2).decode((ByteBuffer)object).toString();
            string5 = string2;
        }
        object = document;
        if (document == null) {
            object = string4;
            if (string4.length() > 0) {
                object = string4;
                if (string4.charAt(0) == '\ufeff') {
                    object = string4.substring(1);
                }
            }
            object = parser.parseInput((String)object, string3);
            object.outputSettings().charset(string5);
        }
        return object;
    }

    static ByteBuffer readToByteBuffer(InputStream inputStream) throws IOException {
        return DataUtil.readToByteBuffer(inputStream, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    static ByteBuffer readToByteBuffer(InputStream inputStream, int n2) throws IOException {
        boolean bl2 = n2 >= 0;
        Validate.isTrue(bl2, "maxSize must be 0 (unlimited) or larger");
        boolean bl3 = n2 > 0;
        byte[] arrby = new byte[131072];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(131072);
        int n3;
        while ((n3 = inputStream.read(arrby)) != -1) {
            int n4 = n2;
            if (bl3) {
                if (n3 > n2) {
                    byteArrayOutputStream.write(arrby, 0, n2);
                    return ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
                }
                n4 = n2 - n3;
            }
            byteArrayOutputStream.write(arrby, 0, n3);
            n2 = n4;
        }
        return ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
    }
}

