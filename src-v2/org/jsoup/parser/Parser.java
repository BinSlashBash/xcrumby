/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.ParseError;
import org.jsoup.parser.ParseErrorList;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TreeBuilder;
import org.jsoup.parser.XmlTreeBuilder;

public class Parser {
    private static final int DEFAULT_MAX_ERRORS = 0;
    private ParseErrorList errors;
    private int maxErrors = 0;
    private TreeBuilder treeBuilder;

    public Parser(TreeBuilder treeBuilder) {
        this.treeBuilder = treeBuilder;
    }

    public static Parser htmlParser() {
        return new Parser(new HtmlTreeBuilder());
    }

    public static Document parse(String string2, String string3) {
        return new HtmlTreeBuilder().parse(string2, string3, ParseErrorList.noTracking());
    }

    public static Document parseBodyFragment(String arrnode, String string2) {
        Document document = Document.createShell(string2);
        Element element = document.body();
        arrnode = Parser.parseFragment((String)arrnode, element, string2);
        arrnode = arrnode.toArray(new Node[arrnode.size()]);
        int n2 = arrnode.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            element.appendChild(arrnode[i2]);
        }
        return document;
    }

    public static Document parseBodyFragmentRelaxed(String string2, String string3) {
        return Parser.parse(string2, string3);
    }

    public static List<Node> parseFragment(String string2, Element element, String string3) {
        return new HtmlTreeBuilder().parseFragment(string2, element, string3, ParseErrorList.noTracking());
    }

    public static List<Node> parseXmlFragment(String string2, String string3) {
        return new XmlTreeBuilder().parseFragment(string2, string3, ParseErrorList.noTracking());
    }

    public static String unescapeEntities(String string2, boolean bl2) {
        return new Tokeniser(new CharacterReader(string2), ParseErrorList.noTracking()).unescapeEntities(bl2);
    }

    public static Parser xmlParser() {
        return new Parser(new XmlTreeBuilder());
    }

    public List<ParseError> getErrors() {
        return this.errors;
    }

    public TreeBuilder getTreeBuilder() {
        return this.treeBuilder;
    }

    public boolean isTrackErrors() {
        if (this.maxErrors > 0) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Document parseInput(String string2, String string3) {
        ParseErrorList parseErrorList = this.isTrackErrors() ? ParseErrorList.tracking(this.maxErrors) : ParseErrorList.noTracking();
        this.errors = parseErrorList;
        return this.treeBuilder.parse(string2, string3, this.errors);
    }

    public Parser setTrackErrors(int n2) {
        this.maxErrors = n2;
        return this;
    }

    public Parser setTreeBuilder(TreeBuilder treeBuilder) {
        this.treeBuilder = treeBuilder;
        return this;
    }
}

