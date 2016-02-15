package org.jsoup.helper;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

public class DataUtil {
    private static final int bufferSize = 131072;
    private static final Pattern charsetPattern;
    static final String defaultCharset = "UTF-8";

    static {
        charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");
    }

    private DataUtil() {
    }

    public static Document load(File in, String charsetName, String baseUri) throws IOException {
        Throwable th;
        FileInputStream inStream = null;
        try {
            FileInputStream inStream2 = new FileInputStream(in);
            try {
                Document parseByteData = parseByteData(readToByteBuffer(inStream2), charsetName, baseUri, Parser.htmlParser());
                if (inStream2 != null) {
                    inStream2.close();
                }
                return parseByteData;
            } catch (Throwable th2) {
                th = th2;
                inStream = inStream2;
                if (inStream != null) {
                    inStream.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (inStream != null) {
                inStream.close();
            }
            throw th;
        }
    }

    public static Document load(InputStream in, String charsetName, String baseUri) throws IOException {
        return parseByteData(readToByteBuffer(in), charsetName, baseUri, Parser.htmlParser());
    }

    public static Document load(InputStream in, String charsetName, String baseUri, Parser parser) throws IOException {
        return parseByteData(readToByteBuffer(in), charsetName, baseUri, parser);
    }

    static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
        String docData;
        Document doc = null;
        if (charsetName == null) {
            docData = Charset.forName(defaultCharset).decode(byteData).toString();
            doc = parser.parseInput(docData, baseUri);
            Element meta = doc.select("meta[http-equiv=content-type], meta[charset]").first();
            if (meta != null) {
                String foundCharset;
                if (meta.hasAttr("http-equiv")) {
                    foundCharset = getCharsetFromContentType(meta.attr("content"));
                    if (foundCharset == null && meta.hasAttr("charset")) {
                        try {
                            if (Charset.isSupported(meta.attr("charset"))) {
                                foundCharset = meta.attr("charset");
                            }
                        } catch (IllegalCharsetNameException e) {
                            foundCharset = null;
                        }
                    }
                } else {
                    foundCharset = meta.attr("charset");
                }
                if (!(foundCharset == null || foundCharset.length() == 0 || foundCharset.equals(defaultCharset))) {
                    foundCharset = foundCharset.trim().replaceAll("[\"']", UnsupportedUrlFragment.DISPLAY_NAME);
                    charsetName = foundCharset;
                    byteData.rewind();
                    docData = Charset.forName(foundCharset).decode(byteData).toString();
                    doc = null;
                }
            }
        } else {
            Validate.notEmpty(charsetName, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
            docData = Charset.forName(charsetName).decode(byteData).toString();
        }
        if (doc != null) {
            return doc;
        }
        if (docData.length() > 0 && docData.charAt(0) == '\ufeff') {
            docData = docData.substring(1);
        }
        doc = parser.parseInput(docData, baseUri);
        doc.outputSettings().charset(charsetName);
        return doc;
    }

    static ByteBuffer readToByteBuffer(InputStream inStream, int maxSize) throws IOException {
        boolean z;
        boolean capped;
        if (maxSize >= 0) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "maxSize must be 0 (unlimited) or larger");
        if (maxSize > 0) {
            capped = true;
        } else {
            capped = false;
        }
        byte[] buffer = new byte[bufferSize];
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(bufferSize);
        int remaining = maxSize;
        while (true) {
            int read = inStream.read(buffer);
            if (read == -1) {
                break;
            }
            if (capped) {
                if (read > remaining) {
                    break;
                }
                remaining -= read;
            }
            outStream.write(buffer, 0, read);
        }
        outStream.write(buffer, 0, remaining);
        return ByteBuffer.wrap(outStream.toByteArray());
    }

    static ByteBuffer readToByteBuffer(InputStream inStream) throws IOException {
        return readToByteBuffer(inStream, 0);
    }

    static String getCharsetFromContentType(String contentType) {
        if (contentType == null) {
            return null;
        }
        Matcher m = charsetPattern.matcher(contentType);
        if (m.find()) {
            String charset = m.group(1).trim().replace("charset=", UnsupportedUrlFragment.DISPLAY_NAME);
            if (charset.isEmpty()) {
                return null;
            }
            try {
                if (Charset.isSupported(charset)) {
                    return charset;
                }
                charset = charset.toUpperCase(Locale.ENGLISH);
                if (Charset.isSupported(charset)) {
                    return charset;
                }
            } catch (IllegalCharsetNameException e) {
                return null;
            }
        }
        return null;
    }
}
