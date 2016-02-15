/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.helper.DescendableLinkedList;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.HtmlTreeBuilderState;
import org.jsoup.parser.ParseError;
import org.jsoup.parser.ParseErrorList;
import org.jsoup.parser.Tag;
import org.jsoup.parser.Token;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;
import org.jsoup.parser.TreeBuilder;
import org.jsoup.select.Elements;

class HtmlTreeBuilder
extends TreeBuilder {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final String[] TagSearchButton;
    private static final String[] TagSearchEndTags;
    private static final String[] TagSearchList;
    private static final String[] TagSearchSelectScope;
    private static final String[] TagSearchSpecial;
    private static final String[] TagSearchTableScope;
    private static final String[] TagsScriptStyle;
    public static final String[] TagsSearchInScope;
    private boolean baseUriSetFromDoc = false;
    private Element contextElement;
    private FormElement formElement;
    private DescendableLinkedList<Element> formattingElements = new DescendableLinkedList();
    private boolean fosterInserts = false;
    private boolean fragmentParsing = false;
    private boolean framesetOk = true;
    private Element headElement;
    private HtmlTreeBuilderState originalState;
    private List<Token.Character> pendingTableCharacters = new ArrayList<Token.Character>();
    private HtmlTreeBuilderState state;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = !HtmlTreeBuilder.class.desiredAssertionStatus();
        $assertionsDisabled = bl2;
        TagsScriptStyle = new String[]{"script", "style"};
        TagsSearchInScope = new String[]{"applet", "caption", "html", "table", "td", "th", "marquee", "object"};
        TagSearchList = new String[]{"ol", "ul"};
        TagSearchButton = new String[]{"button"};
        TagSearchTableScope = new String[]{"html", "table"};
        TagSearchSelectScope = new String[]{"optgroup", "option"};
        TagSearchEndTags = new String[]{"dd", "dt", "li", "option", "optgroup", "p", "rp", "rt"};
        TagSearchSpecial = new String[]{"address", "applet", "area", "article", "aside", "base", "basefont", "bgsound", "blockquote", "body", "br", "button", "caption", "center", "col", "colgroup", "command", "dd", "details", "dir", "div", "dl", "dt", "embed", "fieldset", "figcaption", "figure", "footer", "form", "frame", "frameset", "h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hgroup", "hr", "html", "iframe", "img", "input", "isindex", "li", "link", "listing", "marquee", "menu", "meta", "nav", "noembed", "noframes", "noscript", "object", "ol", "p", "param", "plaintext", "pre", "script", "section", "select", "style", "summary", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "title", "tr", "ul", "wbr", "xmp"};
    }

    HtmlTreeBuilder() {
    }

    private /* varargs */ void clearStackToContext(String ... arrstring) {
        Iterator iterator = this.stack.descendingIterator();
        Element element;
        while (iterator.hasNext() && !StringUtil.in((element = (Element)iterator.next()).nodeName(), arrstring) && !element.nodeName().equals("html")) {
            iterator.remove();
        }
        return;
    }

    private boolean inSpecificScope(String string2, String[] arrstring, String[] arrstring2) {
        return this.inSpecificScope(new String[]{string2}, arrstring, arrstring2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean inSpecificScope(String[] arrstring, String[] arrstring2, String[] arrstring3) {
        boolean bl2 = false;
        Iterator iterator = this.stack.descendingIterator();
        while (iterator.hasNext()) {
            String string2 = ((Element)iterator.next()).nodeName();
            if (StringUtil.in(string2, arrstring)) {
                return true;
            }
            boolean bl3 = bl2;
            if (StringUtil.in(string2, arrstring2)) return bl3;
            if (arrstring3 == null || !StringUtil.in(string2, arrstring3)) continue;
            return false;
        }
        Validate.fail("Should not be reachable");
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void insertNode(Node node) {
        if (this.stack.size() == 0) {
            this.doc.appendChild(node);
        } else if (this.isFosterInserts()) {
            this.insertInFosterParent(node);
        } else {
            this.currentElement().appendChild(node);
        }
        if (node instanceof Element && ((Element)node).tag().isFormListed() && this.formElement != null) {
            this.formElement.addElement((Element)node);
        }
    }

    private boolean isElementInQueue(DescendableLinkedList<Element> object, Element element) {
        object = object.descendingIterator();
        while (object.hasNext()) {
            if ((Element)object.next() != element) continue;
            return true;
        }
        return false;
    }

    private boolean isSameFormattingElement(Element element, Element element2) {
        if (element.nodeName().equals(element2.nodeName()) && element.attributes().equals(element2.attributes())) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void replaceInQueue(LinkedList<Element> linkedList, Element element, Element element2) {
        int n2 = linkedList.lastIndexOf(element);
        boolean bl2 = n2 != -1;
        Validate.isTrue(bl2);
        linkedList.remove(n2);
        linkedList.add(n2, element2);
    }

    Element aboveOnStack(Element element) {
        if (!$assertionsDisabled && !this.onStack(element)) {
            throw new AssertionError();
        }
        Iterator iterator = this.stack.descendingIterator();
        while (iterator.hasNext()) {
            if ((Element)iterator.next() != element) continue;
            return (Element)iterator.next();
        }
        return null;
    }

    void clearFormattingElementsToLastMarker() {
        while (!this.formattingElements.isEmpty()) {
            Element element = this.formattingElements.peekLast();
            this.formattingElements.removeLast();
            if (element != null) continue;
        }
    }

    void clearStackToTableBodyContext() {
        this.clearStackToContext("tbody", "tfoot", "thead");
    }

    void clearStackToTableContext() {
        this.clearStackToContext("table");
    }

    void clearStackToTableRowContext() {
        this.clearStackToContext("tr");
    }

    void error(HtmlTreeBuilderState htmlTreeBuilderState) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Unexpected token [%s] when in state [%s]", new Object[]{this.currentToken.tokenType(), htmlTreeBuilderState}));
        }
    }

    void framesetOk(boolean bl2) {
        this.framesetOk = bl2;
    }

    boolean framesetOk() {
        return this.framesetOk;
    }

    void generateImpliedEndTags() {
        this.generateImpliedEndTags(null);
    }

    void generateImpliedEndTags(String string2) {
        while (string2 != null && !this.currentElement().nodeName().equals(string2) && StringUtil.in(this.currentElement().nodeName(), TagSearchEndTags)) {
            this.pop();
        }
    }

    Element getActiveFormattingElement(String string2) {
        Element element;
        Iterator<Element> iterator = this.formattingElements.descendingIterator();
        do {
            if (iterator.hasNext() && (element = iterator.next()) != null) continue;
            return null;
        } while (!element.nodeName().equals(string2));
        return element;
    }

    String getBaseUri() {
        return this.baseUri;
    }

    Document getDocument() {
        return this.doc;
    }

    FormElement getFormElement() {
        return this.formElement;
    }

    Element getFromStack(String string2) {
        Iterator iterator = this.stack.descendingIterator();
        while (iterator.hasNext()) {
            Element element = (Element)iterator.next();
            if (!element.nodeName().equals(string2)) continue;
            return element;
        }
        return null;
    }

    Element getHeadElement() {
        return this.headElement;
    }

    List<Token.Character> getPendingTableCharacters() {
        return this.pendingTableCharacters;
    }

    DescendableLinkedList<Element> getStack() {
        return this.stack;
    }

    boolean inButtonScope(String string2) {
        return this.inScope(string2, TagSearchButton);
    }

    boolean inListItemScope(String string2) {
        return this.inScope(string2, TagSearchList);
    }

    boolean inScope(String string2) {
        return this.inScope(string2, null);
    }

    boolean inScope(String string2, String[] arrstring) {
        return this.inSpecificScope(string2, TagsSearchInScope, arrstring);
    }

    boolean inScope(String[] arrstring) {
        return this.inSpecificScope(arrstring, TagsSearchInScope, null);
    }

    boolean inSelectScope(String string2) {
        Iterator iterator = this.stack.descendingIterator();
        while (iterator.hasNext()) {
            String string3 = ((Element)iterator.next()).nodeName();
            if (string3.equals(string2)) {
                return true;
            }
            if (StringUtil.in(string3, TagSearchSelectScope)) continue;
            return false;
        }
        Validate.fail("Should not be reachable");
        return false;
    }

    boolean inTableScope(String string2) {
        return this.inSpecificScope(string2, TagSearchTableScope, null);
    }

    Element insert(String object) {
        object = new Element(Tag.valueOf((String)object), this.baseUri);
        this.insert((Element)object);
        return object;
    }

    Element insert(Token.StartTag object) {
        if (object.isSelfClosing()) {
            object = this.insertEmpty((Token.StartTag)object);
            this.stack.add(object);
            this.tokeniser.transition(TokeniserState.Data);
            this.tokeniser.emit(new Token.EndTag(object.tagName()));
            return object;
        }
        object = new Element(Tag.valueOf(object.name()), this.baseUri, object.attributes);
        this.insert((Element)object);
        return object;
    }

    void insert(Element element) {
        this.insertNode(element);
        this.stack.add(element);
    }

    /*
     * Enabled aggressive block sorting
     */
    void insert(Token.Character object) {
        object = StringUtil.in(this.currentElement().tagName(), TagsScriptStyle) ? new DataNode(object.getData(), this.baseUri) : new TextNode(object.getData(), this.baseUri);
        this.currentElement().appendChild((Node)object);
    }

    void insert(Token.Comment comment) {
        this.insertNode(new Comment(comment.getData(), this.baseUri));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Element insertEmpty(Token.StartTag startTag) {
        Tag tag = Tag.valueOf(startTag.name());
        Element element = new Element(tag, this.baseUri, startTag.attributes);
        this.insertNode(element);
        if (!startTag.isSelfClosing()) return element;
        if (tag.isKnownTag()) {
            if (!tag.isSelfClosing()) return element;
            this.tokeniser.acknowledgeSelfClosingFlag();
            return element;
        }
        tag.setSelfClosing();
        this.tokeniser.acknowledgeSelfClosingFlag();
        return element;
    }

    FormElement insertForm(Token.StartTag object, boolean bl2) {
        object = new FormElement(Tag.valueOf(object.name()), this.baseUri, object.attributes);
        this.setFormElement((FormElement)object);
        this.insertNode((Node)object);
        if (bl2) {
            this.stack.add(object);
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    void insertInFosterParent(Node node) {
        Element element;
        Element element2 = this.getFromStack("table");
        boolean bl2 = false;
        if (element2 != null) {
            if (element2.parent() != null) {
                element = element2.parent();
                bl2 = true;
            } else {
                element = this.aboveOnStack(element2);
            }
        } else {
            element = (Element)this.stack.get(0);
        }
        if (bl2) {
            Validate.notNull(element2);
            element2.before(node);
            return;
        }
        element.appendChild(node);
    }

    void insertMarkerToFormattingElements() {
        this.formattingElements.add(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    void insertOnStackAfter(Element element, Element element2) {
        int n2 = this.stack.lastIndexOf(element);
        boolean bl2 = n2 != -1;
        Validate.isTrue(bl2);
        this.stack.add(n2 + 1, element2);
    }

    boolean isFosterInserts() {
        return this.fosterInserts;
    }

    boolean isFragmentParsing() {
        return this.fragmentParsing;
    }

    boolean isInActiveFormattingElements(Element element) {
        return this.isElementInQueue(this.formattingElements, element);
    }

    boolean isSpecial(Element element) {
        return StringUtil.in(element.nodeName(), TagSearchSpecial);
    }

    void markInsertionMode() {
        this.originalState = this.state;
    }

    /*
     * Enabled aggressive block sorting
     */
    void maybeSetBaseUri(Element object) {
        if (this.baseUriSetFromDoc || (object = object.absUrl("href")).length() == 0) {
            return;
        }
        this.baseUri = object;
        this.baseUriSetFromDoc = true;
        this.doc.setBaseUri((String)object);
    }

    void newPendingTableCharacters() {
        this.pendingTableCharacters = new ArrayList<Token.Character>();
    }

    boolean onStack(Element element) {
        return this.isElementInQueue(this.stack, element);
    }

    HtmlTreeBuilderState originalState() {
        return this.originalState;
    }

    @Override
    Document parse(String string2, String string3, ParseErrorList parseErrorList) {
        this.state = HtmlTreeBuilderState.Initial;
        return super.parse(string2, string3, parseErrorList);
    }

    /*
     * Enabled aggressive block sorting
     */
    List<Node> parseFragment(String object, Element element, String object2, ParseErrorList iterator) {
        block14 : {
            this.state = HtmlTreeBuilderState.Initial;
            this.initialiseParse((String)object, (String)object2, (ParseErrorList)((Object)iterator));
            this.contextElement = element;
            this.fragmentParsing = true;
            object = null;
            if (element != null) {
                if (element.ownerDocument() != null) {
                    this.doc.quirksMode(element.ownerDocument().quirksMode());
                }
                if (StringUtil.in((String)(object = element.tagName()), "title", "textarea")) {
                    this.tokeniser.transition(TokeniserState.Rcdata);
                } else if (StringUtil.in((String)object, "iframe", "noembed", "noframes", "style", "xmp")) {
                    this.tokeniser.transition(TokeniserState.Rawtext);
                } else if (object.equals("script")) {
                    this.tokeniser.transition(TokeniserState.ScriptData);
                } else if (object.equals("noscript")) {
                    this.tokeniser.transition(TokeniserState.Data);
                } else if (object.equals("plaintext")) {
                    this.tokeniser.transition(TokeniserState.Data);
                } else {
                    this.tokeniser.transition(TokeniserState.Data);
                }
                object2 = new Element(Tag.valueOf("html"), (String)object2);
                this.doc.appendChild((Node)object2);
                this.stack.push(object2);
                this.resetInsertionMode();
                object = element.parents();
                object.add(0, element);
                iterator = object.iterator();
                do {
                    object = object2;
                    if (!iterator.hasNext()) break block14;
                } while (!((object = iterator.next()) instanceof FormElement));
                this.formElement = (FormElement)object;
                object = object2;
            }
        }
        this.runParser();
        if (element != null) {
            return object.childNodes();
        }
        return this.doc.childNodes();
    }

    Element pop() {
        if (((Element)this.stack.peekLast()).nodeName().equals("td") && !this.state.name().equals("InCell")) {
            Validate.isFalse(true, "pop td not in cell");
        }
        if (((Element)this.stack.peekLast()).nodeName().equals("html")) {
            Validate.isFalse(true, "popping html!");
        }
        return (Element)this.stack.pollLast();
    }

    void popStackToBefore(String string2) {
        Iterator iterator = this.stack.descendingIterator();
        while (iterator.hasNext() && !((Element)iterator.next()).nodeName().equals(string2)) {
            iterator.remove();
        }
        return;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void popStackToClose(String string2) {
        Iterator iterator = this.stack.descendingIterator();
        while (iterator.hasNext()) {
            if (((Element)iterator.next()).nodeName().equals(string2)) {
                iterator.remove();
                return;
            }
            iterator.remove();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    /* varargs */ void popStackToClose(String ... arrstring) {
        Iterator iterator = this.stack.descendingIterator();
        while (iterator.hasNext()) {
            if (StringUtil.in(((Element)iterator.next()).nodeName(), arrstring)) {
                iterator.remove();
                return;
            }
            iterator.remove();
        }
    }

    @Override
    protected boolean process(Token token) {
        this.currentToken = token;
        return this.state.process(token, this);
    }

    boolean process(Token token, HtmlTreeBuilderState htmlTreeBuilderState) {
        this.currentToken = token;
        return htmlTreeBuilderState.process(token, this);
    }

    void push(Element element) {
        this.stack.add(element);
    }

    /*
     * Enabled aggressive block sorting
     */
    void pushActiveFormattingElements(Element element) {
        Element element2;
        int n2 = 0;
        Iterator<Element> iterator = this.formattingElements.descendingIterator();
        while (iterator.hasNext() && (element2 = iterator.next()) != null) {
            int n3 = n2;
            if (this.isSameFormattingElement(element, element2)) {
                n3 = n2 + 1;
            }
            n2 = n3;
            if (n3 != 3) continue;
            iterator.remove();
            break;
        }
        this.formattingElements.add(element);
    }

    /*
     * Enabled aggressive block sorting
     */
    void reconstructFormattingElements() {
        int n2;
        int n3;
        boolean bl2;
        Element element;
        Element element2;
        int n4;
        block5 : {
            n4 = this.formattingElements.size();
            if (n4 == 0 || this.formattingElements.getLast() == null || this.onStack(this.formattingElements.getLast())) {
                return;
            }
            element2 = this.formattingElements.getLast();
            n3 = n4 - 1;
            boolean bl3 = false;
            do {
                if (n3 == 0) {
                    bl2 = true;
                    break block5;
                }
                element2 = this.formattingElements;
                n2 = n3 - 1;
                element2 = element = (Element)element2.get(n2);
                n3 = n2;
                bl2 = bl3;
                if (element == null) break block5;
                element2 = element;
                n3 = n2;
            } while (!this.onStack(element));
            element2 = element;
            n3 = n2;
            bl2 = bl3;
        }
        do {
            n2 = n3;
            if (!bl2) {
                element2 = this.formattingElements;
                n2 = n3 + 1;
                element2 = (Element)element2.get(n2);
            }
            Validate.notNull(element2);
            bl2 = false;
            element = this.insert(element2.nodeName());
            element.attributes().addAll(element2.attributes());
            this.formattingElements.add(n2, element);
            this.formattingElements.remove(n2 + 1);
            n3 = n2;
        } while (n2 != n4 - 1);
    }

    void removeFromActiveFormattingElements(Element element) {
        Iterator<Element> iterator = this.formattingElements.descendingIterator();
        while (iterator.hasNext()) {
            if (iterator.next() != element) continue;
            iterator.remove();
            break;
        }
    }

    boolean removeFromStack(Element element) {
        Iterator iterator = this.stack.descendingIterator();
        while (iterator.hasNext()) {
            if ((Element)iterator.next() != element) continue;
            iterator.remove();
            return true;
        }
        return false;
    }

    void replaceActiveFormattingElement(Element element, Element element2) {
        this.replaceInQueue(this.formattingElements, element, element2);
    }

    void replaceOnStack(Element element, Element element2) {
        this.replaceInQueue(this.stack, element, element2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void resetInsertionMode() {
        boolean bl2;
        boolean bl3 = false;
        Iterator iterator = this.stack.descendingIterator();
        do {
            if (!iterator.hasNext()) return;
            Object object = (Element)iterator.next();
            bl2 = bl3;
            if (!iterator.hasNext()) {
                bl2 = true;
                object = this.contextElement;
            }
            if ("select".equals(object = object.nodeName())) {
                this.transition(HtmlTreeBuilderState.InSelect);
                return;
            }
            if ("td".equals(object) || "td".equals(object) && !bl2) {
                this.transition(HtmlTreeBuilderState.InCell);
                return;
            }
            if ("tr".equals(object)) {
                this.transition(HtmlTreeBuilderState.InRow);
                return;
            }
            if ("tbody".equals(object) || "thead".equals(object) || "tfoot".equals(object)) {
                this.transition(HtmlTreeBuilderState.InTableBody);
                return;
            }
            if ("caption".equals(object)) {
                this.transition(HtmlTreeBuilderState.InCaption);
                return;
            }
            if ("colgroup".equals(object)) {
                this.transition(HtmlTreeBuilderState.InColumnGroup);
                return;
            }
            if ("table".equals(object)) {
                this.transition(HtmlTreeBuilderState.InTable);
                return;
            }
            if ("head".equals(object)) {
                this.transition(HtmlTreeBuilderState.InBody);
                return;
            }
            if ("body".equals(object)) {
                this.transition(HtmlTreeBuilderState.InBody);
                return;
            }
            if ("frameset".equals(object)) {
                this.transition(HtmlTreeBuilderState.InFrameset);
                return;
            }
            if ("html".equals(object)) {
                this.transition(HtmlTreeBuilderState.BeforeHead);
                return;
            }
            bl3 = bl2;
        } while (!bl2);
        this.transition(HtmlTreeBuilderState.InBody);
    }

    void setFormElement(FormElement formElement) {
        this.formElement = formElement;
    }

    void setFosterInserts(boolean bl2) {
        this.fosterInserts = bl2;
    }

    void setHeadElement(Element element) {
        this.headElement = element;
    }

    void setPendingTableCharacters(List<Token.Character> list) {
        this.pendingTableCharacters = list;
    }

    HtmlTreeBuilderState state() {
        return this.state;
    }

    public String toString() {
        return "TreeBuilder{currentToken=" + this.currentToken + ", state=" + (Object)((Object)this.state) + ", currentElement=" + this.currentElement() + '}';
    }

    void transition(HtmlTreeBuilderState htmlTreeBuilderState) {
        this.state = htmlTreeBuilderState;
    }
}

