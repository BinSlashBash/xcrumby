/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

import java.util.Iterator;
import java.util.List;
import org.jsoup.helper.DescendableLinkedList;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.ParseErrorList;
import org.jsoup.parser.Tag;
import org.jsoup.parser.Token;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TreeBuilder;

public class XmlTreeBuilder
extends TreeBuilder {
    private void insertNode(Node node) {
        this.currentElement().appendChild(node);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void popStackToClose(Token.EndTag object) {
        String string2 = object.name();
        Iterator iterator = null;
        Iterator iterator2 = this.stack.descendingIterator();
        do {
            object = iterator;
        } while (iterator2.hasNext() && !(object = (Element)iterator2.next()).nodeName().equals(string2));
        if (object != null) {
            iterator = this.stack.descendingIterator();
            while (iterator.hasNext()) {
                if ((Element)iterator.next() == object) {
                    iterator.remove();
                    return;
                }
                iterator.remove();
            }
        }
    }

    @Override
    protected void initialiseParse(String string2, String string3, ParseErrorList parseErrorList) {
        super.initialiseParse(string2, string3, parseErrorList);
        this.stack.add(this.doc);
    }

    Element insert(Token.StartTag startTag) {
        Tag tag = Tag.valueOf(startTag.name());
        Element element = new Element(tag, this.baseUri, startTag.attributes);
        this.insertNode(element);
        if (startTag.isSelfClosing()) {
            this.tokeniser.acknowledgeSelfClosingFlag();
            if (!tag.isKnownTag()) {
                tag.setSelfClosing();
            }
            return element;
        }
        this.stack.add(element);
        return element;
    }

    void insert(Token.Character character) {
        this.insertNode(new TextNode(character.getData(), this.baseUri));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    void insert(Token.Comment var1_1) {
        var3_4 = var2_3 = (var4_2 = new Comment(var1_1.getData(), this.baseUri));
        if (!var1_1.bogus) ** GOTO lbl10
        var1_1 = var4_2.getData();
        var3_5 = var2_3;
        if (var1_1.length() <= 1) ** GOTO lbl10
        if (var1_1.startsWith("!")) ** GOTO lbl-1000
        var3_6 = var2_3;
        if (var1_1.startsWith("?")) lbl-1000: // 2 sources:
        {
            var3_8 = new XmlDeclaration(var1_1.substring(1), var4_2.baseUri(), var1_1.startsWith("!"));
        }
lbl10: // 5 sources:
        this.insertNode((Node)var3_9);
    }

    void insert(Token.Doctype doctype) {
        this.insertNode(new DocumentType(doctype.getName(), doctype.getPublicIdentifier(), doctype.getSystemIdentifier(), this.baseUri));
    }

    List<Node> parseFragment(String string2, String string3, ParseErrorList parseErrorList) {
        this.initialiseParse(string2, string3, parseErrorList);
        this.runParser();
        return this.doc.childNodes();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected boolean process(Token var1_1) {
        switch (.$SwitchMap$org$jsoup$parser$Token$TokenType[var1_1.type.ordinal()]) {
            default: {
                Validate.fail("Unexpected token type: " + (Object)var1_1.type);
                ** GOTO lbl16
            }
            case 1: {
                this.insert(var1_1.asStartTag());
                ** GOTO lbl16
            }
            case 2: {
                this.popStackToClose(var1_1.asEndTag());
                ** GOTO lbl16
            }
            case 3: {
                this.insert(var1_1.asComment());
                ** GOTO lbl16
            }
            case 4: {
                this.insert(var1_1.asCharacter());
            }
lbl16: // 6 sources:
            case 6: {
                return true;
            }
            case 5: 
        }
        this.insert(var1_1.asDoctype());
        return true;
    }

}

