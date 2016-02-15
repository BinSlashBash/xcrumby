/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

import org.jsoup.helper.DescendableLinkedList;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.ParseErrorList;
import org.jsoup.parser.Token;
import org.jsoup.parser.Tokeniser;

abstract class TreeBuilder {
    protected String baseUri;
    protected Token currentToken;
    protected Document doc;
    protected ParseErrorList errors;
    CharacterReader reader;
    protected DescendableLinkedList<Element> stack;
    Tokeniser tokeniser;

    TreeBuilder() {
    }

    protected Element currentElement() {
        return this.stack.getLast();
    }

    protected void initialiseParse(String string2, String string3, ParseErrorList parseErrorList) {
        Validate.notNull(string2, "String input must not be null");
        Validate.notNull(string3, "BaseURI must not be null");
        this.doc = new Document(string3);
        this.reader = new CharacterReader(string2);
        this.errors = parseErrorList;
        this.tokeniser = new Tokeniser(this.reader, parseErrorList);
        this.stack = new DescendableLinkedList();
        this.baseUri = string3;
    }

    Document parse(String string2, String string3) {
        return this.parse(string2, string3, ParseErrorList.noTracking());
    }

    Document parse(String string2, String string3, ParseErrorList parseErrorList) {
        this.initialiseParse(string2, string3, parseErrorList);
        this.runParser();
        return this.doc;
    }

    protected abstract boolean process(Token var1);

    protected void runParser() {
        do {
            Token token = this.tokeniser.read();
            this.process(token);
        } while (token.type != Token.TokenType.EOF);
    }
}

