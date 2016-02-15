/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.jsoup.Connection;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

public class Jsoup {
    private Jsoup() {
    }

    public static String clean(String object, String string2, Whitelist whitelist) {
        object = Jsoup.parseBodyFragment((String)object, string2);
        return new Cleaner(whitelist).clean((Document)object).body().html();
    }

    public static String clean(String object, String string2, Whitelist whitelist, Document.OutputSettings outputSettings) {
        object = Jsoup.parseBodyFragment((String)object, string2);
        object = new Cleaner(whitelist).clean((Document)object);
        object.outputSettings(outputSettings);
        return object.body().html();
    }

    public static String clean(String string2, Whitelist whitelist) {
        return Jsoup.clean(string2, "", whitelist);
    }

    public static Connection connect(String string2) {
        return HttpConnection.connect(string2);
    }

    public static boolean isValid(String object, Whitelist whitelist) {
        object = Jsoup.parseBodyFragment((String)object, "");
        return new Cleaner(whitelist).isValid((Document)object);
    }

    public static Document parse(File file, String string2) throws IOException {
        return DataUtil.load(file, string2, file.getAbsolutePath());
    }

    public static Document parse(File file, String string2, String string3) throws IOException {
        return DataUtil.load(file, string2, string3);
    }

    public static Document parse(InputStream inputStream, String string2, String string3) throws IOException {
        return DataUtil.load(inputStream, string2, string3);
    }

    public static Document parse(InputStream inputStream, String string2, String string3, Parser parser) throws IOException {
        return DataUtil.load(inputStream, string2, string3, parser);
    }

    public static Document parse(String string2) {
        return Parser.parse(string2, "");
    }

    public static Document parse(String string2, String string3) {
        return Parser.parse(string2, string3);
    }

    public static Document parse(String string2, String string3, Parser parser) {
        return parser.parseInput(string2, string3);
    }

    public static Document parse(URL object, int n2) throws IOException {
        object = HttpConnection.connect((URL)object);
        object.timeout(n2);
        return object.get();
    }

    public static Document parseBodyFragment(String string2) {
        return Parser.parseBodyFragment(string2, "");
    }

    public static Document parseBodyFragment(String string2, String string3) {
        return Parser.parseBodyFragment(string2, string3);
    }
}

