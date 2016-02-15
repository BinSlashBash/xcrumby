/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

import java.util.Iterator;
import java.util.List;
import org.jsoup.helper.DescendableLinkedList;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.Tag;
import org.jsoup.parser.Token;
import org.jsoup.parser.Tokeniser;
import org.jsoup.parser.TokeniserState;
import org.jsoup.parser.TreeBuilder;

enum HtmlTreeBuilderState {
    Initial{

        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return true;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype()) {
                token = token.asDoctype();
                DocumentType documentType = new DocumentType(token.getName(), token.getPublicIdentifier(), token.getSystemIdentifier(), htmlTreeBuilder.getBaseUri());
                htmlTreeBuilder.getDocument().appendChild(documentType);
                if (token.isForceQuirks()) {
                    htmlTreeBuilder.getDocument().quirksMode(Document.QuirksMode.quirks);
                }
                htmlTreeBuilder.transition(BeforeHtml);
                return true;
            }
            htmlTreeBuilder.transition(BeforeHtml);
            return htmlTreeBuilder.process(token);
        }
    }
    ,
    BeforeHtml{

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.insert("html");
            htmlTreeBuilder.transition(BeforeHead);
            return htmlTreeBuilder.process(token);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                do {
                    return true;
                    break;
                } while (true);
            }
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return true;
            }
            if (token.isStartTag() && token.asStartTag().name().equals("html")) {
                htmlTreeBuilder.insert(token.asStartTag());
                htmlTreeBuilder.transition(BeforeHead);
                return true;
            }
            if (token.isEndTag() && StringUtil.in(token.asEndTag().name(), "head", "body", "html", "br")) {
                return this.anythingElse(token, htmlTreeBuilder);
            }
            if (!token.isEndTag()) return this.anythingElse(token, htmlTreeBuilder);
            htmlTreeBuilder.error(this);
            return false;
        }
    }
    ,
    BeforeHead{

        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return true;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            if (token.isStartTag() && token.asStartTag().name().equals("html")) {
                return InBody.process(token, htmlTreeBuilder);
            }
            if (token.isStartTag() && token.asStartTag().name().equals("head")) {
                htmlTreeBuilder.setHeadElement(htmlTreeBuilder.insert(token.asStartTag()));
                htmlTreeBuilder.transition(InHead);
                return true;
            }
            if (token.isEndTag() && StringUtil.in(token.asEndTag().name(), "head", "body", "html", "br")) {
                htmlTreeBuilder.process(new Token.StartTag("head"));
                return htmlTreeBuilder.process(token);
            }
            if (token.isEndTag()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            htmlTreeBuilder.process(new Token.StartTag("head"));
            return htmlTreeBuilder.process(token);
        }
    }
    ,
    InHead{

        private boolean anythingElse(Token token, TreeBuilder treeBuilder) {
            treeBuilder.process(new Token.EndTag("head"));
            return treeBuilder.process(token);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        boolean process(Token object, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace((Token)object)) {
                htmlTreeBuilder.insert(object.asCharacter());
                return true;
            }
            switch (.$SwitchMap$org$jsoup$parser$Token$TokenType[object.type.ordinal()]) {
                default: {
                    return this.anythingElse((Token)object, htmlTreeBuilder);
                }
                case 1: {
                    htmlTreeBuilder.insert(object.asComment());
                    return true;
                }
                case 2: {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                case 3: {
                    Token.StartTag startTag = object.asStartTag();
                    String string2 = startTag.name();
                    if (string2.equals("html")) {
                        return InBody.process((Token)object, htmlTreeBuilder);
                    }
                    if (StringUtil.in(string2, "base", "basefont", "bgsound", "command", "link")) {
                        object = htmlTreeBuilder.insertEmpty(startTag);
                        if (!string2.equals("base") || !object.hasAttr("href")) return true;
                        {
                            htmlTreeBuilder.maybeSetBaseUri((Element)object);
                            return true;
                        }
                    }
                    if (string2.equals("meta")) {
                        htmlTreeBuilder.insertEmpty(startTag);
                        return true;
                    }
                    if (string2.equals("title")) {
                        HtmlTreeBuilderState.handleRcData(startTag, htmlTreeBuilder);
                        return true;
                    }
                    if (StringUtil.in(string2, "noframes", "style")) {
                        HtmlTreeBuilderState.handleRawtext(startTag, htmlTreeBuilder);
                        return true;
                    }
                    if (string2.equals("noscript")) {
                        htmlTreeBuilder.insert(startTag);
                        htmlTreeBuilder.transition(InHeadNoscript);
                        return true;
                    }
                    if (string2.equals("script")) {
                        htmlTreeBuilder.tokeniser.transition(TokeniserState.ScriptData);
                        htmlTreeBuilder.markInsertionMode();
                        htmlTreeBuilder.transition(Text);
                        htmlTreeBuilder.insert(startTag);
                        return true;
                    }
                    if (!string2.equals("head")) return this.anythingElse((Token)object, htmlTreeBuilder);
                    {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                }
                case 4: 
            }
            String string3 = object.asEndTag().name();
            if (string3.equals("head")) {
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(AfterHead);
                return true;
            }
            if (StringUtil.in(string3, "body", "html", "br")) {
                return this.anythingElse((Token)object, htmlTreeBuilder);
            }
            htmlTreeBuilder.error(this);
            return false;
        }
    }
    ,
    InHeadNoscript{

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.error(this);
            htmlTreeBuilder.process(new Token.EndTag("noscript"));
            return htmlTreeBuilder.process(token);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                do {
                    return true;
                    break;
                } while (true);
            }
            if (token.isStartTag() && token.asStartTag().name().equals("html")) {
                return htmlTreeBuilder.process(token, InBody);
            }
            if (token.isEndTag() && token.asEndTag().name().equals("noscript")) {
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(InHead);
                return true;
            }
            if (HtmlTreeBuilderState.isWhitespace(token) || token.isComment() || token.isStartTag() && StringUtil.in(token.asStartTag().name(), "basefont", "bgsound", "link", "meta", "noframes", "style")) {
                return htmlTreeBuilder.process(token, InHead);
            }
            if (token.isEndTag() && token.asEndTag().name().equals("br")) {
                return this.anythingElse(token, htmlTreeBuilder);
            }
            if ((!token.isStartTag() || !StringUtil.in(token.asStartTag().name(), "head", "noscript")) && !token.isEndTag()) return this.anythingElse(token, htmlTreeBuilder);
            htmlTreeBuilder.error(this);
            return false;
        }
    }
    ,
    AfterHead{

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.process(new Token.StartTag("body"));
            htmlTreeBuilder.framesetOk(true);
            return htmlTreeBuilder.process(token);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                do {
                    return true;
                    break;
                } while (true);
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return true;
            }
            if (token.isStartTag()) {
                Object object = token.asStartTag();
                String string2 = object.name();
                if (string2.equals("html")) {
                    return htmlTreeBuilder.process(token, InBody);
                }
                if (string2.equals("body")) {
                    htmlTreeBuilder.insert((Token.StartTag)object);
                    htmlTreeBuilder.framesetOk(false);
                    htmlTreeBuilder.transition(InBody);
                    return true;
                }
                if (string2.equals("frameset")) {
                    htmlTreeBuilder.insert((Token.StartTag)object);
                    htmlTreeBuilder.transition(InFrameset);
                    return true;
                }
                if (StringUtil.in(string2, "base", "basefont", "bgsound", "link", "meta", "noframes", "script", "style", "title")) {
                    htmlTreeBuilder.error(this);
                    object = htmlTreeBuilder.getHeadElement();
                    htmlTreeBuilder.push((Element)object);
                    htmlTreeBuilder.process(token, InHead);
                    htmlTreeBuilder.removeFromStack((Element)object);
                    return true;
                }
                if (string2.equals("head")) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                this.anythingElse(token, htmlTreeBuilder);
                return true;
            }
            if (token.isEndTag()) {
                if (StringUtil.in(token.asEndTag().name(), "body", "html")) {
                    this.anythingElse(token, htmlTreeBuilder);
                    return true;
                }
                htmlTreeBuilder.error(this);
                return false;
            }
            this.anythingElse(token, htmlTreeBuilder);
            return true;
        }
    }
    ,
    InBody{

        boolean anyOtherEndTag(Token object, HtmlTreeBuilder htmlTreeBuilder) {
            Element element;
            object = object.asEndTag().name();
            Iterator<Element> iterator = htmlTreeBuilder.getStack().descendingIterator();
            do {
                if (iterator.hasNext()) {
                    element = iterator.next();
                    if (!element.nodeName().equals(object)) continue;
                    htmlTreeBuilder.generateImpliedEndTags((String)object);
                    if (!object.equals(htmlTreeBuilder.currentElement().nodeName())) {
                        htmlTreeBuilder.error(this);
                    }
                    htmlTreeBuilder.popStackToClose((String)object);
                }
                return true;
            } while (!htmlTreeBuilder.isSpecial(element));
            htmlTreeBuilder.error(this);
            return false;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        boolean process(Token var1_1, HtmlTreeBuilder var2_2) {
            switch (.$SwitchMap$org$jsoup$parser$Token$TokenType[var1_1.type.ordinal()]) {
                case 5: {
                    var1_1 = var1_1.asCharacter();
                    if (var1_1.getData().equals(HtmlTreeBuilderState.access$400())) {
                        var2_2.error((HtmlTreeBuilderState)this);
                        return false;
                    }
                    if (HtmlTreeBuilderState.access$100((Token)var1_1)) {
                        var2_2.reconstructFormattingElements();
                        var2_2.insert((Token.Character)var1_1);
                        break;
                    }
                    var2_2.reconstructFormattingElements();
                    var2_2.insert((Token.Character)var1_1);
                    var2_2.framesetOk(false);
                    break;
                }
                case 1: {
                    var2_2.insert(var1_1.asComment());
                    break;
                }
                case 2: {
                    var2_2.error((HtmlTreeBuilderState)this);
                    return false;
                }
                case 3: {
                    var8_3 = var1_1.asStartTag();
                    var9_5 = var8_3.name();
                    if (var9_5.equals("html")) {
                        var2_2.error((HtmlTreeBuilderState)this);
                        var1_1 = var2_2.getStack().getFirst();
                        var2_2 = var8_3.getAttributes().iterator();
                        while (var2_2.hasNext() != false) {
                            var8_3 = var2_2.next();
                            if (var1_1.hasAttr((String)var8_3.getKey())) continue;
                            var1_1.attributes().put((Attribute)var8_3);
                        }
                        return true;
                    }
                    if (StringUtil.in((String)var9_5, Constants.access$500())) {
                        return var2_2.process((Token)var1_1, .InHead);
                    }
                    if (var9_5.equals("body")) {
                        var2_2.error((HtmlTreeBuilderState)this);
                        var1_1 = var2_2.getStack();
                        if (var1_1.size() == 1) return false;
                        if (var1_1.size() > 2 && !((Element)var1_1.get(1)).nodeName().equals("body")) {
                            return false;
                        }
                        var2_2.framesetOk(false);
                        var1_1 = (Element)var1_1.get(1);
                        var2_2 = var8_3.getAttributes().iterator();
                        while (var2_2.hasNext() != false) {
                            var8_3 = var2_2.next();
                            if (var1_1.hasAttr((String)var8_3.getKey())) continue;
                            var1_1.attributes().put((Attribute)var8_3);
                        }
                        return true;
                    }
                    if (var9_5.equals("frameset")) {
                        var2_2.error((HtmlTreeBuilderState)this);
                        var1_1 = var2_2.getStack();
                        if (var1_1.size() == 1) return false;
                        if (var1_1.size() > 2 && !((Element)var1_1.get(1)).nodeName().equals("body")) {
                            return false;
                        }
                        if (!var2_2.framesetOk()) {
                            return false;
                        }
                        var9_5 = (Element)var1_1.get(1);
                        if (var9_5.parent() != null) {
                            var9_5.remove();
                        }
                        while (var1_1.size() > 1) {
                            var1_1.removeLast();
                        }
                        var2_2.insert((Token.StartTag)var8_3);
                        var2_2.transition(.InFrameset);
                        break;
                    }
                    if (StringUtil.in((String)var9_5, Constants.access$600())) {
                        if (var2_2.inButtonScope("p")) {
                            var2_2.process((Token)new Token.EndTag("p"));
                        }
                        var2_2.insert((Token.StartTag)var8_3);
                        break;
                    }
                    if (StringUtil.in((String)var9_5, Constants.access$700())) {
                        if (var2_2.inButtonScope("p")) {
                            var2_2.process((Token)new Token.EndTag("p"));
                        }
                        if (StringUtil.in(var2_2.currentElement().nodeName(), Constants.access$700())) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            var2_2.pop();
                        }
                        var2_2.insert((Token.StartTag)var8_3);
                        break;
                    }
                    if (StringUtil.in((String)var9_5, Constants.access$800())) {
                        if (var2_2.inButtonScope("p")) {
                            var2_2.process((Token)new Token.EndTag("p"));
                        }
                        var2_2.insert((Token.StartTag)var8_3);
                        var2_2.framesetOk(false);
                        break;
                    }
                    if (var9_5.equals("form")) {
                        if (var2_2.getFormElement() != null) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            return false;
                        }
                        if (var2_2.inButtonScope("p")) {
                            var2_2.process((Token)new Token.EndTag("p"));
                        }
                        var2_2.insertForm((Token.StartTag)var8_3, true);
                        break;
                    }
                    if (!var9_5.equals("li")) ** GOTO lbl99
                    var2_2.framesetOk(false);
                    var1_1 = var2_2.getStack();
                    ** GOTO lbl353
lbl99: // 1 sources:
                    if (!StringUtil.in((String)var9_5, Constants.access$1000())) ** GOTO lbl103
                    var2_2.framesetOk(false);
                    var1_1 = var2_2.getStack();
                    ** GOTO lbl364
lbl103: // 1 sources:
                    if (var9_5.equals("plaintext")) {
                        if (var2_2.inButtonScope("p")) {
                            var2_2.process((Token)new Token.EndTag("p"));
                        }
                        var2_2.insert((Token.StartTag)var8_3);
                        var2_2.tokeniser.transition(TokeniserState.PLAINTEXT);
                        break;
                    }
                    if (var9_5.equals("button")) {
                        if (var2_2.inButtonScope("button")) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            var2_2.process((Token)new Token.EndTag("button"));
                            var2_2.process((Token)var8_3);
                            break;
                        }
                        var2_2.reconstructFormattingElements();
                        var2_2.insert((Token.StartTag)var8_3);
                        var2_2.framesetOk(false);
                        break;
                    }
                    if (var9_5.equals("a")) {
                        if (var2_2.getActiveFormattingElement("a") != null) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            var2_2.process((Token)new Token.EndTag("a"));
                            var1_1 = var2_2.getFromStack("a");
                            if (var1_1 != null) {
                                var2_2.removeFromActiveFormattingElements(var1_1);
                                var2_2.removeFromStack(var1_1);
                            }
                        }
                        var2_2.reconstructFormattingElements();
                        var2_2.pushActiveFormattingElements(var2_2.insert((Token.StartTag)var8_3));
                        break;
                    }
                    if (StringUtil.in((String)var9_5, Constants.access$1100())) {
                        var2_2.reconstructFormattingElements();
                        var2_2.pushActiveFormattingElements(var2_2.insert((Token.StartTag)var8_3));
                        break;
                    }
                    if (var9_5.equals("nobr")) {
                        var2_2.reconstructFormattingElements();
                        if (var2_2.inScope("nobr")) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            var2_2.process((Token)new Token.EndTag("nobr"));
                            var2_2.reconstructFormattingElements();
                        }
                        var2_2.pushActiveFormattingElements(var2_2.insert((Token.StartTag)var8_3));
                        break;
                    }
                    if (StringUtil.in((String)var9_5, Constants.access$1200())) {
                        var2_2.reconstructFormattingElements();
                        var2_2.insert((Token.StartTag)var8_3);
                        var2_2.insertMarkerToFormattingElements();
                        var2_2.framesetOk(false);
                        break;
                    }
                    if (var9_5.equals("table")) {
                        if (var2_2.getDocument().quirksMode() != Document.QuirksMode.quirks && var2_2.inButtonScope("p")) {
                            var2_2.process((Token)new Token.EndTag("p"));
                        }
                        var2_2.insert((Token.StartTag)var8_3);
                        var2_2.framesetOk(false);
                        var2_2.transition(.InTable);
                        break;
                    }
                    if (StringUtil.in((String)var9_5, Constants.access$1300())) {
                        var2_2.reconstructFormattingElements();
                        var2_2.insertEmpty((Token.StartTag)var8_3);
                        var2_2.framesetOk(false);
                        break;
                    }
                    if (var9_5.equals("input")) {
                        var2_2.reconstructFormattingElements();
                        if (var2_2.insertEmpty((Token.StartTag)var8_3).attr("type").equalsIgnoreCase("hidden") != false) return true;
                        var2_2.framesetOk(false);
                        break;
                    }
                    if (StringUtil.in((String)var9_5, Constants.access$1400())) {
                        var2_2.insertEmpty((Token.StartTag)var8_3);
                        break;
                    }
                    if (var9_5.equals("hr")) {
                        if (var2_2.inButtonScope("p")) {
                            var2_2.process((Token)new Token.EndTag("p"));
                        }
                        var2_2.insertEmpty((Token.StartTag)var8_3);
                        var2_2.framesetOk(false);
                        break;
                    }
                    if (var9_5.equals("image")) {
                        var8_3.name("img");
                        return var2_2.process((Token)var8_3);
                    }
                    if (var9_5.equals("isindex")) {
                        var2_2.error((HtmlTreeBuilderState)this);
                        if (var2_2.getFormElement() != null) {
                            return false;
                        }
                        var2_2.tokeniser.acknowledgeSelfClosingFlag();
                        var2_2.process((Token)new Token.StartTag("form"));
                        if (var8_3.attributes.hasKey("action")) {
                            var2_2.getFormElement().attr("action", var8_3.attributes.get("action"));
                        }
                        var2_2.process((Token)new Token.StartTag("hr"));
                        var2_2.process((Token)new Token.StartTag("label"));
                        var1_1 = var8_3.attributes.hasKey("prompt") != false ? var8_3.attributes.get("prompt") : "This is a searchable index. Enter search keywords: ";
                        var2_2.process((Token)new Token.Character((String)var1_1));
                        var1_1 = new Attributes();
                        for (Object var9_5 : var8_3.attributes) {
                            if (StringUtil.in((String)var9_5.getKey(), Constants.access$1500())) continue;
                            var1_1.put((Attribute)var9_5);
                        }
                        var1_1.put("name", "isindex");
                        var2_2.process((Token)new Token.StartTag("input", (Attributes)var1_1));
                        var2_2.process((Token)new Token.EndTag("label"));
                        var2_2.process((Token)new Token.StartTag("hr"));
                        var2_2.process((Token)new Token.EndTag("form"));
                        break;
                    }
                    if (var9_5.equals("textarea")) {
                        var2_2.insert((Token.StartTag)var8_3);
                        var2_2.tokeniser.transition(TokeniserState.Rcdata);
                        var2_2.markInsertionMode();
                        var2_2.framesetOk(false);
                        var2_2.transition(.Text);
                        break;
                    }
                    if (var9_5.equals("xmp")) {
                        if (var2_2.inButtonScope("p")) {
                            var2_2.process((Token)new Token.EndTag("p"));
                        }
                        var2_2.reconstructFormattingElements();
                        var2_2.framesetOk(false);
                        HtmlTreeBuilderState.access$300((Token.StartTag)var8_3, var2_2);
                        break;
                    }
                    if (var9_5.equals("iframe")) {
                        var2_2.framesetOk(false);
                        HtmlTreeBuilderState.access$300((Token.StartTag)var8_3, var2_2);
                        break;
                    }
                    if (var9_5.equals("noembed")) {
                        HtmlTreeBuilderState.access$300((Token.StartTag)var8_3, var2_2);
                        break;
                    }
                    if (var9_5.equals("select")) {
                        var2_2.reconstructFormattingElements();
                        var2_2.insert((Token.StartTag)var8_3);
                        var2_2.framesetOk(false);
                        var1_1 = var2_2.state();
                        if (var1_1.equals((Object).InTable) || var1_1.equals((Object).InCaption) || var1_1.equals((Object).InTableBody) || var1_1.equals((Object).InRow) || var1_1.equals((Object).InCell)) {
                            var2_2.transition(.InSelectInTable);
                            break;
                        }
                        var2_2.transition(.InSelect);
                        break;
                    }
                    if (StringUtil.in((String)var9_5, Constants.access$1600())) {
                        if (var2_2.currentElement().nodeName().equals("option")) {
                            var2_2.process((Token)new Token.EndTag("option"));
                        }
                        var2_2.reconstructFormattingElements();
                        var2_2.insert((Token.StartTag)var8_3);
                        break;
                    }
                    if (StringUtil.in((String)var9_5, Constants.access$1700())) {
                        if (var2_2.inScope("ruby") == false) return true;
                        var2_2.generateImpliedEndTags();
                        if (!var2_2.currentElement().nodeName().equals("ruby")) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            var2_2.popStackToBefore("ruby");
                        }
                        var2_2.insert((Token.StartTag)var8_3);
                        break;
                    }
                    if (var9_5.equals("math")) {
                        var2_2.reconstructFormattingElements();
                        var2_2.insert((Token.StartTag)var8_3);
                        var2_2.tokeniser.acknowledgeSelfClosingFlag();
                        break;
                    }
                    if (var9_5.equals("svg")) {
                        var2_2.reconstructFormattingElements();
                        var2_2.insert((Token.StartTag)var8_3);
                        var2_2.tokeniser.acknowledgeSelfClosingFlag();
                        break;
                    }
                    if (StringUtil.in((String)var9_5, Constants.access$1800())) {
                        var2_2.error((HtmlTreeBuilderState)this);
                        return false;
                    }
                    var2_2.reconstructFormattingElements();
                    var2_2.insert((Token.StartTag)var8_3);
                    break;
                }
                case 4: {
                    var8_4 = var1_1.asEndTag();
                    var13_10 = var8_4.name();
                    if (var13_10.equals("body")) {
                        if (!var2_2.inScope("body")) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            return false;
                        }
                        var2_2.transition(.AfterBody);
                        return true;
                    }
                    if (var13_10.equals("html")) {
                        if (var2_2.process((Token)new Token.EndTag("body")) == false) return true;
                        return var2_2.process((Token)var8_4);
                    }
                    if (StringUtil.in(var13_10, Constants.access$1900())) {
                        if (!var2_2.inScope(var13_10)) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            return false;
                        }
                        var2_2.generateImpliedEndTags();
                        if (!var2_2.currentElement().nodeName().equals(var13_10)) {
                            var2_2.error((HtmlTreeBuilderState)this);
                        }
                        var2_2.popStackToClose(var13_10);
                        return true;
                    }
                    if (var13_10.equals("form")) {
                        var1_1 = var2_2.getFormElement();
                        var2_2.setFormElement(null);
                        if (var1_1 == null || !var2_2.inScope(var13_10)) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            return false;
                        }
                        var2_2.generateImpliedEndTags();
                        if (!var2_2.currentElement().nodeName().equals(var13_10)) {
                            var2_2.error((HtmlTreeBuilderState)this);
                        }
                        var2_2.removeFromStack((Element)var1_1);
                        return true;
                    }
                    if (var13_10.equals("p")) {
                        if (!var2_2.inButtonScope(var13_10)) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            var2_2.process((Token)new Token.StartTag(var13_10));
                            return var2_2.process((Token)var8_4);
                        }
                        var2_2.generateImpliedEndTags(var13_10);
                        if (!var2_2.currentElement().nodeName().equals(var13_10)) {
                            var2_2.error((HtmlTreeBuilderState)this);
                        }
                        var2_2.popStackToClose(var13_10);
                        return true;
                    }
                    if (var13_10.equals("li")) {
                        if (!var2_2.inListItemScope(var13_10)) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            return false;
                        }
                        var2_2.generateImpliedEndTags(var13_10);
                        if (!var2_2.currentElement().nodeName().equals(var13_10)) {
                            var2_2.error((HtmlTreeBuilderState)this);
                        }
                        var2_2.popStackToClose(var13_10);
                        return true;
                    }
                    if (StringUtil.in(var13_10, Constants.access$1000())) {
                        if (!var2_2.inScope(var13_10)) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            return false;
                        }
                        var2_2.generateImpliedEndTags(var13_10);
                        if (!var2_2.currentElement().nodeName().equals(var13_10)) {
                            var2_2.error((HtmlTreeBuilderState)this);
                        }
                        var2_2.popStackToClose(var13_10);
                        return true;
                    }
                    if (StringUtil.in(var13_10, Constants.access$700())) {
                        if (!var2_2.inScope(Constants.access$700())) {
                            var2_2.error((HtmlTreeBuilderState)this);
                            return false;
                        }
                        var2_2.generateImpliedEndTags(var13_10);
                        if (!var2_2.currentElement().nodeName().equals(var13_10)) {
                            var2_2.error((HtmlTreeBuilderState)this);
                        }
                        var2_2.popStackToClose(Constants.access$700());
                        return true;
                    }
                    if (var13_10.equals("sarcasm")) {
                        return this.anyOtherEndTag((Token)var1_1, (HtmlTreeBuilder)var2_2);
                    }
                    if (!StringUtil.in(var13_10, Constants.access$2000())) ** GOTO lbl335
                    var3_9 = 0;
                    ** GOTO lbl375
lbl335: // 1 sources:
                    if (!StringUtil.in(var13_10, Constants.access$1200())) {
                        if (var13_10.equals("br") == false) return this.anyOtherEndTag((Token)var1_1, (HtmlTreeBuilder)var2_2);
                        var2_2.error((HtmlTreeBuilderState)this);
                        var2_2.process((Token)new Token.StartTag("br"));
                        return false;
                    }
                    if (var2_2.inScope("name") != false) return true;
                    if (!var2_2.inScope(var13_10)) {
                        var2_2.error((HtmlTreeBuilderState)this);
                        return false;
                    }
                    var2_2.generateImpliedEndTags();
                    if (!var2_2.currentElement().nodeName().equals(var13_10)) {
                        var2_2.error((HtmlTreeBuilderState)this);
                    }
                    var2_2.popStackToClose(var13_10);
                    var2_2.clearFormattingElementsToLastMarker();
                    return true;
                }
            }
lbl350: // 35 sources:
            do {
                ** default:
lbl352: // 1 sources:
                return true;
                break;
            } while (true);
lbl353: // 2 sources:
            for (var3_7 = var1_1.size() - 1; var3_7 > 0; --var3_7) {
                var9_5 = (Element)var1_1.get(var3_7);
                if (var9_5.nodeName().equals("li")) {
                    var2_2.process((Token)new Token.EndTag("li"));
                    break;
                }
                if (var2_2.isSpecial((Element)var9_5) && !StringUtil.in(var9_5.nodeName(), Constants.access$900())) break;
            }
            if (var2_2.inButtonScope("p")) {
                var2_2.process((Token)new Token.EndTag("p"));
            }
            var2_2.insert((Token.StartTag)var8_3);
            ** GOTO lbl350
lbl364: // 2 sources:
            for (var3_8 = var1_1.size() - 1; var3_8 > 0; --var3_8) {
                var9_5 = (Element)var1_1.get(var3_8);
                if (StringUtil.in(var9_5.nodeName(), Constants.access$1000())) {
                    var2_2.process((Token)new Token.EndTag(var9_5.nodeName()));
                    break;
                }
                if (var2_2.isSpecial((Element)var9_5) && !StringUtil.in(var9_5.nodeName(), Constants.access$900())) break;
            }
            if (var2_2.inButtonScope("p")) {
                var2_2.process((Token)new Token.EndTag("p"));
            }
            var2_2.insert((Token.StartTag)var8_3);
            ** while (true)
lbl375: // 2 sources:
            while (var3_9 < 8) {
                var14_18 = var2_2.getActiveFormattingElement(var13_10);
                if (var14_18 == null) {
                    return this.anyOtherEndTag((Token)var1_1, (HtmlTreeBuilder)var2_2);
                }
                if (!var2_2.onStack(var14_18)) {
                    var2_2.error((HtmlTreeBuilderState)this);
                    var2_2.removeFromActiveFormattingElements(var14_18);
                    return true;
                }
                if (!var2_2.inScope(var14_18.nodeName())) {
                    var2_2.error((HtmlTreeBuilderState)this);
                    return false;
                }
                if (var2_2.currentElement() != var14_18) {
                    var2_2.error((HtmlTreeBuilderState)this);
                }
                var9_6 = null;
                var11_16 = null;
                var5_12 = 0;
                var12_17 = var2_2.getStack();
                var7_14 = var12_17.size();
                var4_11 = 0;
                do {
                    var8_4 = var9_6;
                    if (var4_11 >= var7_14) break;
                    var8_4 = var9_6;
                    if (var4_11 >= 64) break;
                    var10_15 = var12_17.get(var4_11);
                    if (var10_15 == var14_18) {
                        var8_4 = (Element)var12_17.get(var4_11 - 1);
                        var6_13 = 1;
                    } else {
                        var8_4 = var11_16;
                        var6_13 = var5_12;
                        if (var5_12 != 0) {
                            var8_4 = var11_16;
                            var6_13 = var5_12;
                            if (var2_2.isSpecial((Element)var10_15)) {
                                var8_4 = var10_15;
                                break;
                            }
                        }
                    }
                    ++var4_11;
                    var11_16 = var8_4;
                    var5_12 = var6_13;
                } while (true);
                if (var8_4 == null) {
                    var2_2.popStackToClose(var14_18.nodeName());
                    var2_2.removeFromActiveFormattingElements(var14_18);
                    return true;
                }
                var9_6 = var8_4;
                var12_17 = var8_4;
                for (var4_11 = 0; var4_11 < 3; ++var4_11) {
                    var10_15 = var9_6;
                    if (var2_2.onStack((Element)var9_6)) {
                        var10_15 = var2_2.aboveOnStack((Element)var9_6);
                    }
                    if (!var2_2.isInActiveFormattingElements((Element)var10_15)) {
                        var2_2.removeFromStack((Element)var10_15);
                        var9_6 = var10_15;
                        continue;
                    }
                    if (var10_15 == var14_18) break;
                    var9_6 = new Element(Tag.valueOf(var10_15.nodeName()), var2_2.getBaseUri());
                    var2_2.replaceActiveFormattingElement((Element)var10_15, (Element)var9_6);
                    var2_2.replaceOnStack((Element)var10_15, (Element)var9_6);
                    if (var12_17 == var8_4) {
                        // empty if block
                    }
                    if (var12_17.parent() != null) {
                        var12_17.remove();
                    }
                    var9_6.appendChild((Node)var12_17);
                    var12_17 = var9_6;
                }
                if (StringUtil.in(var11_16.nodeName(), Constants.access$2100())) {
                    if (var12_17.parent() != null) {
                        var12_17.remove();
                    }
                    var2_2.insertInFosterParent((Node)var12_17);
                } else {
                    if (var12_17.parent() != null) {
                        var12_17.remove();
                    }
                    var11_16.appendChild((Node)var12_17);
                }
                var9_6 = new Element(var14_18.tag(), var2_2.getBaseUri());
                var9_6.attributes().addAll(var14_18.attributes());
                var10_15 = var8_4.childNodes().toArray(new Node[var8_4.childNodeSize()]);
                var5_12 = var10_15.length;
                for (var4_11 = 0; var4_11 < var5_12; ++var4_11) {
                    var9_6.appendChild(var10_15[var4_11]);
                }
                var8_4.appendChild((Node)var9_6);
                var2_2.removeFromActiveFormattingElements(var14_18);
                var2_2.removeFromStack(var14_18);
                var2_2.insertOnStackAfter((Element)var8_4, (Element)var9_6);
                ++var3_9;
            }
            return true;
        }
    }
    ,
    Text{

        /*
         * Enabled aggressive block sorting
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isCharacter()) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            }
            if (token.isEOF()) {
                htmlTreeBuilder.error(this);
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(htmlTreeBuilder.originalState());
                return htmlTreeBuilder.process(token);
            }
            if (!token.isEndTag()) return true;
            htmlTreeBuilder.pop();
            htmlTreeBuilder.transition(htmlTreeBuilder.originalState());
            return true;
        }
    }
    ,
    InTable{

        boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.error(this);
            if (StringUtil.in(htmlTreeBuilder.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                htmlTreeBuilder.setFosterInserts(true);
                boolean bl2 = htmlTreeBuilder.process(token, InBody);
                htmlTreeBuilder.setFosterInserts(false);
                return bl2;
            }
            return htmlTreeBuilder.process(token, InBody);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            boolean bl2 = true;
            if (token.isCharacter()) {
                htmlTreeBuilder.newPendingTableCharacters();
                htmlTreeBuilder.markInsertionMode();
                htmlTreeBuilder.transition(InTableText);
                return htmlTreeBuilder.process(token);
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            if (token.isStartTag()) {
                Token.StartTag startTag = token.asStartTag();
                String string2 = startTag.name();
                if (string2.equals("caption")) {
                    htmlTreeBuilder.clearStackToTableContext();
                    htmlTreeBuilder.insertMarkerToFormattingElements();
                    htmlTreeBuilder.insert(startTag);
                    htmlTreeBuilder.transition(InCaption);
                    return true;
                }
                if (string2.equals("colgroup")) {
                    htmlTreeBuilder.clearStackToTableContext();
                    htmlTreeBuilder.insert(startTag);
                    htmlTreeBuilder.transition(InColumnGroup);
                    return true;
                }
                if (string2.equals("col")) {
                    htmlTreeBuilder.process(new Token.StartTag("colgroup"));
                    return htmlTreeBuilder.process(token);
                }
                if (StringUtil.in(string2, "tbody", "tfoot", "thead")) {
                    htmlTreeBuilder.clearStackToTableContext();
                    htmlTreeBuilder.insert(startTag);
                    htmlTreeBuilder.transition(InTableBody);
                    return true;
                }
                if (StringUtil.in(string2, "td", "th", "tr")) {
                    htmlTreeBuilder.process(new Token.StartTag("tbody"));
                    return htmlTreeBuilder.process(token);
                }
                if (string2.equals("table")) {
                    htmlTreeBuilder.error(this);
                    if (!htmlTreeBuilder.process(new Token.EndTag("table"))) return bl2;
                    return htmlTreeBuilder.process(token);
                }
                if (StringUtil.in(string2, "style", "script")) {
                    return htmlTreeBuilder.process(token, InHead);
                }
                if (string2.equals("input")) {
                    if (!startTag.attributes.get("type").equalsIgnoreCase("hidden")) {
                        return this.anythingElse(token, htmlTreeBuilder);
                    }
                    htmlTreeBuilder.insertEmpty(startTag);
                    return true;
                }
                if (!string2.equals("form")) return this.anythingElse(token, htmlTreeBuilder);
                htmlTreeBuilder.error(this);
                if (htmlTreeBuilder.getFormElement() != null) {
                    return false;
                }
                htmlTreeBuilder.insertForm(startTag, false);
                return true;
            }
            if (token.isEndTag()) {
                String string3 = token.asEndTag().name();
                if (string3.equals("table")) {
                    if (!htmlTreeBuilder.inTableScope(string3)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.popStackToClose("table");
                    htmlTreeBuilder.resetInsertionMode();
                    return true;
                }
                if (!StringUtil.in(string3, "body", "caption", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr")) return this.anythingElse(token, htmlTreeBuilder);
                htmlTreeBuilder.error(this);
                return false;
            }
            if (!token.isEOF()) return this.anythingElse(token, htmlTreeBuilder);
            if (!htmlTreeBuilder.currentElement().nodeName().equals("html")) return bl2;
            htmlTreeBuilder.error(this);
            return true;
        }
    }
    ,
    InTableText{

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        boolean process(Token var1_1, HtmlTreeBuilder var2_2) {
            switch (.$SwitchMap$org$jsoup$parser$Token$TokenType[var1_1.type.ordinal()]) {
                default: {
                    if (var2_2.getPendingTableCharacters().size() > 0) {
                        var3_3 = var2_2.getPendingTableCharacters().iterator();
                        break;
                    }
                    ** GOTO lbl28
                }
                case 5: {
                    var1_1 = var1_1.asCharacter();
                    if (var1_1.getData().equals(HtmlTreeBuilderState.access$400())) {
                        var2_2.error(this);
                        return false;
                    }
                    var2_2.getPendingTableCharacters().add((Token.Character)var1_1);
                    return true;
                }
            }
            while (var3_3.hasNext()) {
                var4_4 = var3_3.next();
                if (!HtmlTreeBuilderState.access$100(var4_4)) {
                    var2_2.error(this);
                    if (StringUtil.in(var2_2.currentElement().nodeName(), new String[]{"table", "tbody", "tfoot", "thead", "tr"})) {
                        var2_2.setFosterInserts(true);
                        var2_2.process(var4_4, .InBody);
                        var2_2.setFosterInserts(false);
                        continue;
                    }
                    var2_2.process(var4_4, .InBody);
                    continue;
                }
                var2_2.insert(var4_4);
            }
            var2_2.newPendingTableCharacters();
lbl28: // 2 sources:
            var2_2.transition(var2_2.originalState());
            return var2_2.process(var1_1);
        }
    }
    ,
    InCaption{

        /*
         * Enabled aggressive block sorting
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isEndTag() && token.asEndTag().name().equals("caption")) {
                if (!htmlTreeBuilder.inTableScope(token.asEndTag().name())) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.generateImpliedEndTags();
                if (!htmlTreeBuilder.currentElement().nodeName().equals("caption")) {
                    htmlTreeBuilder.error(this);
                }
                htmlTreeBuilder.popStackToClose("caption");
                htmlTreeBuilder.clearFormattingElementsToLastMarker();
                htmlTreeBuilder.transition(InTable);
                return true;
            }
            if (token.isStartTag() && StringUtil.in(token.asStartTag().name(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr") || token.isEndTag() && token.asEndTag().name().equals("table")) {
                htmlTreeBuilder.error(this);
                if (!htmlTreeBuilder.process(new Token.EndTag("caption"))) return true;
                return htmlTreeBuilder.process(token);
            }
            if (!token.isEndTag() || !StringUtil.in(token.asEndTag().name(), "body", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr")) return htmlTreeBuilder.process(token, InBody);
            {
                htmlTreeBuilder.error(this);
                return false;
            }
        }
    }
    ,
    InColumnGroup{

        private boolean anythingElse(Token token, TreeBuilder treeBuilder) {
            if (treeBuilder.process(new Token.EndTag("colgroup"))) {
                return treeBuilder.process(token);
            }
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            } else {
                switch (.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()]) {
                    default: {
                        return this.anythingElse(token, htmlTreeBuilder);
                    }
                    case 1: {
                        htmlTreeBuilder.insert(token.asComment());
                        return true;
                    }
                    case 2: {
                        htmlTreeBuilder.error(this);
                        return true;
                    }
                    case 3: {
                        Token.StartTag startTag = token.asStartTag();
                        String string2 = startTag.name();
                        if (string2.equals("html")) {
                            return htmlTreeBuilder.process(token, InBody);
                        }
                        if (!string2.equals("col")) return this.anythingElse(token, htmlTreeBuilder);
                        {
                            htmlTreeBuilder.insertEmpty(startTag);
                            return true;
                        }
                    }
                    case 4: {
                        if (!token.asEndTag().name().equals("colgroup")) {
                            return this.anythingElse(token, htmlTreeBuilder);
                        }
                        if (htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                            htmlTreeBuilder.error(this);
                            return false;
                        }
                        htmlTreeBuilder.pop();
                        htmlTreeBuilder.transition(InTable);
                        return true;
                    }
                    case 6: 
                }
                if (htmlTreeBuilder.currentElement().nodeName().equals("html")) return true;
                return this.anythingElse(token, htmlTreeBuilder);
            }
        }
    }
    ,
    InTableBody{

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return htmlTreeBuilder.process(token, InTable);
        }

        private boolean exitTableBody(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (!(htmlTreeBuilder.inTableScope("tbody") || htmlTreeBuilder.inTableScope("thead") || htmlTreeBuilder.inScope("tfoot"))) {
                htmlTreeBuilder.error(this);
                return false;
            }
            htmlTreeBuilder.clearStackToTableBodyContext();
            htmlTreeBuilder.process(new Token.EndTag(htmlTreeBuilder.currentElement().nodeName()));
            return htmlTreeBuilder.process(token);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            switch (.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()]) {
                default: {
                    return this.anythingElse(token, htmlTreeBuilder);
                }
                case 3: {
                    Token.StartTag startTag = token.asStartTag();
                    String string2 = startTag.name();
                    if (string2.equals("tr")) {
                        htmlTreeBuilder.clearStackToTableBodyContext();
                        htmlTreeBuilder.insert(startTag);
                        htmlTreeBuilder.transition(InRow);
                        do {
                            return true;
                            break;
                        } while (true);
                    }
                    if (StringUtil.in(string2, "th", "td")) {
                        htmlTreeBuilder.error(this);
                        htmlTreeBuilder.process(new Token.StartTag("tr"));
                        return htmlTreeBuilder.process(startTag);
                    }
                    if (!StringUtil.in(string2, "caption", "col", "colgroup", "tbody", "tfoot", "thead")) return this.anythingElse(token, htmlTreeBuilder);
                    return this.exitTableBody(token, htmlTreeBuilder);
                }
                case 4: 
            }
            String string3 = token.asEndTag().name();
            if (StringUtil.in(string3, "tbody", "tfoot", "thead")) {
                if (!htmlTreeBuilder.inTableScope(string3)) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.clearStackToTableBodyContext();
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(InTable);
                return true;
            }
            if (string3.equals("table")) {
                return this.exitTableBody(token, htmlTreeBuilder);
            }
            if (!StringUtil.in(string3, "body", "caption", "col", "colgroup", "html", "td", "th", "tr")) return this.anythingElse(token, htmlTreeBuilder);
            htmlTreeBuilder.error(this);
            return false;
        }
    }
    ,
    InRow{

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return htmlTreeBuilder.process(token, InTable);
        }

        private boolean handleMissingTr(Token token, TreeBuilder treeBuilder) {
            if (treeBuilder.process(new Token.EndTag("tr"))) {
                return treeBuilder.process(token);
            }
            return false;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isStartTag()) {
                Token.StartTag startTag = token.asStartTag();
                String string2 = startTag.name();
                if (StringUtil.in(string2, "th", "td")) {
                    htmlTreeBuilder.clearStackToTableRowContext();
                    htmlTreeBuilder.insert(startTag);
                    htmlTreeBuilder.transition(InCell);
                    htmlTreeBuilder.insertMarkerToFormattingElements();
                    do {
                        return true;
                        break;
                    } while (true);
                }
                if (!StringUtil.in(string2, "caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr")) return this.anythingElse(token, htmlTreeBuilder);
                return this.handleMissingTr(token, htmlTreeBuilder);
            }
            if (!token.isEndTag()) return this.anythingElse(token, htmlTreeBuilder);
            String string3 = token.asEndTag().name();
            if (string3.equals("tr")) {
                if (!htmlTreeBuilder.inTableScope(string3)) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.clearStackToTableRowContext();
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(InTableBody);
                return true;
            }
            if (string3.equals("table")) {
                return this.handleMissingTr(token, htmlTreeBuilder);
            }
            if (StringUtil.in(string3, "tbody", "tfoot", "thead")) {
                if (!htmlTreeBuilder.inTableScope(string3)) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.process(new Token.EndTag("tr"));
                return htmlTreeBuilder.process(token);
            }
            if (!StringUtil.in(string3, "body", "caption", "col", "colgroup", "html", "td", "th")) return this.anythingElse(token, htmlTreeBuilder);
            htmlTreeBuilder.error(this);
            return false;
        }
    }
    ,
    InCell{

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return htmlTreeBuilder.process(token, InBody);
        }

        private void closeCell(HtmlTreeBuilder htmlTreeBuilder) {
            if (htmlTreeBuilder.inTableScope("td")) {
                htmlTreeBuilder.process(new Token.EndTag("td"));
                return;
            }
            htmlTreeBuilder.process(new Token.EndTag("th"));
        }

        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isEndTag()) {
                String string2 = token.asEndTag().name();
                if (StringUtil.in(string2, "td", "th")) {
                    if (!htmlTreeBuilder.inTableScope(string2)) {
                        htmlTreeBuilder.error(this);
                        htmlTreeBuilder.transition(InRow);
                        return false;
                    }
                    htmlTreeBuilder.generateImpliedEndTags();
                    if (!htmlTreeBuilder.currentElement().nodeName().equals(string2)) {
                        htmlTreeBuilder.error(this);
                    }
                    htmlTreeBuilder.popStackToClose(string2);
                    htmlTreeBuilder.clearFormattingElementsToLastMarker();
                    htmlTreeBuilder.transition(InRow);
                    return true;
                }
                if (StringUtil.in(string2, "body", "caption", "col", "colgroup", "html")) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                if (StringUtil.in(string2, "table", "tbody", "tfoot", "thead", "tr")) {
                    if (!htmlTreeBuilder.inTableScope(string2)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    this.closeCell(htmlTreeBuilder);
                    return htmlTreeBuilder.process(token);
                }
                return this.anythingElse(token, htmlTreeBuilder);
            }
            if (token.isStartTag() && StringUtil.in(token.asStartTag().name(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                if (!htmlTreeBuilder.inTableScope("td") && !htmlTreeBuilder.inTableScope("th")) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                this.closeCell(htmlTreeBuilder);
                return htmlTreeBuilder.process(token);
            }
            return this.anythingElse(token, htmlTreeBuilder);
        }
    }
    ,
    InSelect{

        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.error(this);
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            boolean bl2 = false;
            switch (.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()]) {
                default: {
                    return this.anythingElse(token, htmlTreeBuilder);
                }
                case 5: {
                    token = token.asCharacter();
                    if (token.getData().equals(nullString)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.insert((Token.Character)token);
                    return true;
                }
                case 1: {
                    htmlTreeBuilder.insert(token.asComment());
                    return true;
                }
                case 2: {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                case 3: {
                    Token.StartTag startTag = token.asStartTag();
                    String string2 = startTag.name();
                    if (string2.equals("html")) {
                        return htmlTreeBuilder.process(startTag, InBody);
                    }
                    if (string2.equals("option")) {
                        htmlTreeBuilder.process(new Token.EndTag("option"));
                        htmlTreeBuilder.insert(startTag);
                        return true;
                    }
                    if (string2.equals("optgroup")) {
                        if (htmlTreeBuilder.currentElement().nodeName().equals("option")) {
                            htmlTreeBuilder.process(new Token.EndTag("option"));
                        } else if (htmlTreeBuilder.currentElement().nodeName().equals("optgroup")) {
                            htmlTreeBuilder.process(new Token.EndTag("optgroup"));
                        }
                        htmlTreeBuilder.insert(startTag);
                        return true;
                    }
                    if (string2.equals("select")) {
                        htmlTreeBuilder.error(this);
                        return htmlTreeBuilder.process(new Token.EndTag("select"));
                    }
                    if (StringUtil.in(string2, "input", "keygen", "textarea")) {
                        htmlTreeBuilder.error(this);
                        if (!htmlTreeBuilder.inSelectScope("select")) return bl2;
                        htmlTreeBuilder.process(new Token.EndTag("select"));
                        return htmlTreeBuilder.process(startTag);
                    }
                    if (!string2.equals("script")) return this.anythingElse(token, htmlTreeBuilder);
                    return htmlTreeBuilder.process(token, InHead);
                }
                case 4: {
                    String string3 = token.asEndTag().name();
                    if (string3.equals("optgroup")) {
                        if (htmlTreeBuilder.currentElement().nodeName().equals("option") && htmlTreeBuilder.aboveOnStack(htmlTreeBuilder.currentElement()) != null && htmlTreeBuilder.aboveOnStack(htmlTreeBuilder.currentElement()).nodeName().equals("optgroup")) {
                            htmlTreeBuilder.process(new Token.EndTag("option"));
                        }
                        if (htmlTreeBuilder.currentElement().nodeName().equals("optgroup")) {
                            htmlTreeBuilder.pop();
                            return true;
                        }
                        htmlTreeBuilder.error(this);
                        return true;
                    }
                    if (string3.equals("option")) {
                        if (htmlTreeBuilder.currentElement().nodeName().equals("option")) {
                            htmlTreeBuilder.pop();
                            return true;
                        }
                        htmlTreeBuilder.error(this);
                        return true;
                    }
                    if (!string3.equals("select")) return this.anythingElse(token, htmlTreeBuilder);
                    if (!htmlTreeBuilder.inSelectScope(string3)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.popStackToClose(string3);
                    htmlTreeBuilder.resetInsertionMode();
                    return true;
                }
                case 6: 
            }
            if (htmlTreeBuilder.currentElement().nodeName().equals("html")) return true;
            htmlTreeBuilder.error(this);
            return true;
        }
    }
    ,
    InSelectInTable{

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            boolean bl2 = false;
            if (token.isStartTag() && StringUtil.in(token.asStartTag().name(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                htmlTreeBuilder.error(this);
                htmlTreeBuilder.process(new Token.EndTag("select"));
                return htmlTreeBuilder.process(token);
            }
            if (!token.isEndTag()) return htmlTreeBuilder.process(token, InSelect);
            if (!StringUtil.in(token.asEndTag().name(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) return htmlTreeBuilder.process(token, InSelect);
            htmlTreeBuilder.error(this);
            if (!htmlTreeBuilder.inTableScope(token.asEndTag().name())) return bl2;
            htmlTreeBuilder.process(new Token.EndTag("select"));
            return htmlTreeBuilder.process(token);
        }
    }
    ,
    AfterBody{

        /*
         * Enabled aggressive block sorting
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return htmlTreeBuilder.process(token, InBody);
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            if (token.isStartTag() && token.asStartTag().name().equals("html")) {
                return htmlTreeBuilder.process(token, InBody);
            }
            if (token.isEndTag() && token.asEndTag().name().equals("html")) {
                if (htmlTreeBuilder.isFragmentParsing()) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.transition(AfterAfterBody);
                return true;
            }
            if (token.isEOF()) return true;
            htmlTreeBuilder.error(this);
            htmlTreeBuilder.transition(InBody);
            return htmlTreeBuilder.process(token);
        }
    }
    ,
    InFrameset{

        /*
         * Enabled aggressive block sorting
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            if (token.isStartTag()) {
                String string2 = (token = token.asStartTag()).name();
                if (string2.equals("html")) {
                    return htmlTreeBuilder.process(token, InBody);
                }
                if (string2.equals("frameset")) {
                    htmlTreeBuilder.insert((Token.StartTag)token);
                    return true;
                }
                if (string2.equals("frame")) {
                    htmlTreeBuilder.insertEmpty((Token.StartTag)token);
                    return true;
                }
                if (string2.equals("noframes")) {
                    return htmlTreeBuilder.process(token, InHead);
                }
                htmlTreeBuilder.error(this);
                return false;
            }
            if (token.isEndTag() && token.asEndTag().name().equals("frameset")) {
                if (htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.pop();
                if (htmlTreeBuilder.isFragmentParsing()) return true;
                if (htmlTreeBuilder.currentElement().nodeName().equals("frameset")) return true;
                htmlTreeBuilder.transition(AfterFrameset);
                return true;
            }
            if (token.isEOF()) {
                if (htmlTreeBuilder.currentElement().nodeName().equals("html")) return true;
                htmlTreeBuilder.error(this);
                return true;
            }
            htmlTreeBuilder.error(this);
            return false;
        }
    }
    ,
    AfterFrameset{

        /*
         * Enabled aggressive block sorting
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            if (token.isStartTag() && token.asStartTag().name().equals("html")) {
                return htmlTreeBuilder.process(token, InBody);
            }
            if (token.isEndTag() && token.asEndTag().name().equals("html")) {
                htmlTreeBuilder.transition(AfterAfterFrameset);
                return true;
            }
            if (token.isStartTag() && token.asStartTag().name().equals("noframes")) {
                return htmlTreeBuilder.process(token, InHead);
            }
            if (token.isEOF()) return true;
            htmlTreeBuilder.error(this);
            return false;
        }
    }
    ,
    AfterAfterBody{

        /*
         * Enabled aggressive block sorting
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            } else {
                if (token.isDoctype() || HtmlTreeBuilderState.isWhitespace(token) || token.isStartTag() && token.asStartTag().name().equals("html")) {
                    return htmlTreeBuilder.process(token, InBody);
                }
                if (token.isEOF()) return true;
                {
                    htmlTreeBuilder.error(this);
                    htmlTreeBuilder.transition(InBody);
                    return htmlTreeBuilder.process(token);
                }
            }
        }
    }
    ,
    AfterAfterFrameset{

        /*
         * Enabled aggressive block sorting
         */
        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype() || HtmlTreeBuilderState.isWhitespace(token) || token.isStartTag() && token.asStartTag().name().equals("html")) {
                return htmlTreeBuilder.process(token, InBody);
            }
            if (token.isEOF()) return true;
            {
                if (token.isStartTag() && token.asStartTag().name().equals("noframes")) {
                    return htmlTreeBuilder.process(token, InHead);
                }
            }
            htmlTreeBuilder.error(this);
            return false;
        }
    }
    ,
    ForeignContent{

        @Override
        boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return true;
        }
    };
    
    private static String nullString;

    static {
        nullString = String.valueOf('\u0000');
    }

    private HtmlTreeBuilderState() {
    }

    private static void handleRawtext(Token.StartTag startTag, HtmlTreeBuilder htmlTreeBuilder) {
        htmlTreeBuilder.insert(startTag);
        htmlTreeBuilder.tokeniser.transition(TokeniserState.Rawtext);
        htmlTreeBuilder.markInsertionMode();
        htmlTreeBuilder.transition(Text);
    }

    private static void handleRcData(Token.StartTag startTag, HtmlTreeBuilder htmlTreeBuilder) {
        htmlTreeBuilder.insert(startTag);
        htmlTreeBuilder.tokeniser.transition(TokeniserState.Rcdata);
        htmlTreeBuilder.markInsertionMode();
        htmlTreeBuilder.transition(Text);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean isWhitespace(Token object) {
        if (!object.isCharacter()) return false;
        object = object.asCharacter().getData();
        int n2 = 0;
        while (n2 < object.length()) {
            if (!StringUtil.isWhitespace(object.charAt(n2))) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    abstract boolean process(Token var1, HtmlTreeBuilder var2);

    private static final class Constants {
        private static final String[] DdDt;
        private static final String[] Formatters;
        private static final String[] Headings;
        private static final String[] InBodyEndAdoptionFormatters;
        private static final String[] InBodyEndClosers;
        private static final String[] InBodyEndTableFosters;
        private static final String[] InBodyStartApplets;
        private static final String[] InBodyStartDrop;
        private static final String[] InBodyStartEmptyFormatters;
        private static final String[] InBodyStartInputAttribs;
        private static final String[] InBodyStartLiBreakers;
        private static final String[] InBodyStartMedia;
        private static final String[] InBodyStartOptions;
        private static final String[] InBodyStartPClosers;
        private static final String[] InBodyStartPreListing;
        private static final String[] InBodyStartRuby;
        private static final String[] InBodyStartToHead;

        static {
            InBodyStartToHead = new String[]{"base", "basefont", "bgsound", "command", "link", "meta", "noframes", "script", "style", "title"};
            InBodyStartPClosers = new String[]{"address", "article", "aside", "blockquote", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "menu", "nav", "ol", "p", "section", "summary", "ul"};
            Headings = new String[]{"h1", "h2", "h3", "h4", "h5", "h6"};
            InBodyStartPreListing = new String[]{"pre", "listing"};
            InBodyStartLiBreakers = new String[]{"address", "div", "p"};
            DdDt = new String[]{"dd", "dt"};
            Formatters = new String[]{"b", "big", "code", "em", "font", "i", "s", "small", "strike", "strong", "tt", "u"};
            InBodyStartApplets = new String[]{"applet", "marquee", "object"};
            InBodyStartEmptyFormatters = new String[]{"area", "br", "embed", "img", "keygen", "wbr"};
            InBodyStartMedia = new String[]{"param", "source", "track"};
            InBodyStartInputAttribs = new String[]{"name", "action", "prompt"};
            InBodyStartOptions = new String[]{"optgroup", "option"};
            InBodyStartRuby = new String[]{"rp", "rt"};
            InBodyStartDrop = new String[]{"caption", "col", "colgroup", "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr"};
            InBodyEndClosers = new String[]{"address", "article", "aside", "blockquote", "button", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "listing", "menu", "nav", "ol", "pre", "section", "summary", "ul"};
            InBodyEndAdoptionFormatters = new String[]{"a", "b", "big", "code", "em", "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u"};
            InBodyEndTableFosters = new String[]{"table", "tbody", "tfoot", "thead", "tr"};
        }

        private Constants() {
        }

        static /* synthetic */ String[] access$1000() {
            return DdDt;
        }

        static /* synthetic */ String[] access$1100() {
            return Formatters;
        }

        static /* synthetic */ String[] access$1200() {
            return InBodyStartApplets;
        }

        static /* synthetic */ String[] access$1300() {
            return InBodyStartEmptyFormatters;
        }

        static /* synthetic */ String[] access$1400() {
            return InBodyStartMedia;
        }

        static /* synthetic */ String[] access$1500() {
            return InBodyStartInputAttribs;
        }

        static /* synthetic */ String[] access$1600() {
            return InBodyStartOptions;
        }

        static /* synthetic */ String[] access$1700() {
            return InBodyStartRuby;
        }

        static /* synthetic */ String[] access$1800() {
            return InBodyStartDrop;
        }

        static /* synthetic */ String[] access$1900() {
            return InBodyEndClosers;
        }

        static /* synthetic */ String[] access$2000() {
            return InBodyEndAdoptionFormatters;
        }

        static /* synthetic */ String[] access$2100() {
            return InBodyEndTableFosters;
        }

        static /* synthetic */ String[] access$500() {
            return InBodyStartToHead;
        }

        static /* synthetic */ String[] access$600() {
            return InBodyStartPClosers;
        }

        static /* synthetic */ String[] access$700() {
            return Headings;
        }

        static /* synthetic */ String[] access$800() {
            return InBodyStartPreListing;
        }

        static /* synthetic */ String[] access$900() {
            return InBodyStartLiBreakers;
        }
    }

}

