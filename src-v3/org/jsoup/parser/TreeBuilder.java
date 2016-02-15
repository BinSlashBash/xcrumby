package org.jsoup.parser;

import org.jsoup.helper.DescendableLinkedList;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

abstract class TreeBuilder {
    protected String baseUri;
    protected Token currentToken;
    protected Document doc;
    protected ParseErrorList errors;
    CharacterReader reader;
    protected DescendableLinkedList<Element> stack;
    Tokeniser tokeniser;

    protected abstract boolean process(Token token);

    TreeBuilder() {
    }

    protected void initialiseParse(String input, String baseUri, ParseErrorList errors) {
        Validate.notNull(input, "String input must not be null");
        Validate.notNull(baseUri, "BaseURI must not be null");
        this.doc = new Document(baseUri);
        this.reader = new CharacterReader(input);
        this.errors = errors;
        this.tokeniser = new Tokeniser(this.reader, errors);
        this.stack = new DescendableLinkedList();
        this.baseUri = baseUri;
    }

    Document parse(String input, String baseUri) {
        return parse(input, baseUri, ParseErrorList.noTracking());
    }

    Document parse(String input, String baseUri, ParseErrorList errors) {
        initialiseParse(input, baseUri, errors);
        runParser();
        return this.doc;
    }

    protected void runParser() {
        Token token;
        do {
            token = this.tokeniser.read();
            process(token);
        } while (token.type != TokenType.EOF);
    }

    protected Element currentElement() {
        return (Element) this.stack.getLast();
    }
}
