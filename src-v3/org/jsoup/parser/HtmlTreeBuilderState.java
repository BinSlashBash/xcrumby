package org.jsoup.parser;

import com.crumby.impl.danbooru.DanbooruImageFragment;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer.Std;
import com.google.android.gms.plus.PlusShare;
import java.util.Iterator;
import java.util.LinkedList;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document.QuirksMode;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

enum HtmlTreeBuilderState {
    Initial {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                return true;
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
                return true;
            } else if (t.isDoctype()) {
                Doctype d = t.asDoctype();
                tb.getDocument().appendChild(new DocumentType(d.getName(), d.getPublicIdentifier(), d.getSystemIdentifier(), tb.getBaseUri()));
                if (d.isForceQuirks()) {
                    tb.getDocument().quirksMode(QuirksMode.quirks);
                }
                tb.transition(BeforeHtml);
                return true;
            } else {
                tb.transition(BeforeHtml);
                return tb.process(t);
            }
        }
    },
    BeforeHtml {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isDoctype()) {
                tb.error(this);
                return false;
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (HtmlTreeBuilderState.isWhitespace(t)) {
                return true;
            } else {
                if (t.isStartTag() && t.asStartTag().name().equals("html")) {
                    tb.insert(t.asStartTag());
                    tb.transition(BeforeHead);
                } else {
                    if (t.isEndTag()) {
                        if (StringUtil.in(t.asEndTag().name(), "head", "body", "html", "br")) {
                            return anythingElse(t, tb);
                        }
                    }
                    if (!t.isEndTag()) {
                        return anythingElse(t, tb);
                    }
                    tb.error(this);
                    return false;
                }
            }
            return true;
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.insert("html");
            tb.transition(BeforeHead);
            return tb.process(t);
        }
    },
    BeforeHead {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                return true;
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
                return true;
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag() && t.asStartTag().name().equals("html")) {
                return InBody.process(t, tb);
            } else {
                if (t.isStartTag() && t.asStartTag().name().equals("head")) {
                    tb.setHeadElement(tb.insert(t.asStartTag()));
                    tb.transition(InHead);
                    return true;
                }
                if (t.isEndTag()) {
                    if (StringUtil.in(t.asEndTag().name(), "head", "body", "html", "br")) {
                        tb.process(new StartTag("head"));
                        return tb.process(t);
                    }
                }
                if (t.isEndTag()) {
                    tb.error(this);
                    return false;
                }
                tb.process(new StartTag("head"));
                return tb.process(t);
            }
        }
    },
    InHead {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
                return true;
            }
            String name;
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[t.type.ordinal()]) {
                case Std.STD_FILE /*1*/:
                    tb.insert(t.asComment());
                    return true;
                case Std.STD_URL /*2*/:
                    tb.error(this);
                    return false;
                case Std.STD_URI /*3*/:
                    StartTag start = t.asStartTag();
                    name = start.name();
                    if (name.equals("html")) {
                        return InBody.process(t, tb);
                    }
                    if (StringUtil.in(name, "base", "basefont", "bgsound", "command", "link")) {
                        Element el = tb.insertEmpty(start);
                        if (!name.equals("base") || !el.hasAttr("href")) {
                            return true;
                        }
                        tb.maybeSetBaseUri(el);
                        return true;
                    } else if (name.equals("meta")) {
                        tb.insertEmpty(start);
                        return true;
                    } else if (name.equals(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE)) {
                        HtmlTreeBuilderState.handleRcData(start, tb);
                        return true;
                    } else {
                        if (StringUtil.in(name, "noframes", "style")) {
                            HtmlTreeBuilderState.handleRawtext(start, tb);
                            return true;
                        } else if (name.equals("noscript")) {
                            tb.insert(start);
                            tb.transition(InHeadNoscript);
                            return true;
                        } else if (name.equals("script")) {
                            tb.tokeniser.transition(TokeniserState.ScriptData);
                            tb.markInsertionMode();
                            tb.transition(Text);
                            tb.insert(start);
                            return true;
                        } else if (!name.equals("head")) {
                            return anythingElse(t, tb);
                        } else {
                            tb.error(this);
                            return false;
                        }
                    }
                case Std.STD_CLASS /*4*/:
                    name = t.asEndTag().name();
                    if (name.equals("head")) {
                        tb.pop();
                        tb.transition(AfterHead);
                        return true;
                    }
                    if (StringUtil.in(name, "body", "html", "br")) {
                        return anythingElse(t, tb);
                    }
                    tb.error(this);
                    return false;
                default:
                    return anythingElse(t, tb);
            }
        }

        private boolean anythingElse(Token t, TreeBuilder tb) {
            tb.process(new EndTag("head"));
            return tb.process(t);
        }
    },
    InHeadNoscript {
        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        boolean process(org.jsoup.parser.Token r8, org.jsoup.parser.HtmlTreeBuilder r9) {
            /*
            r7 = this;
            r6 = 2;
            r1 = 1;
            r0 = 0;
            r2 = r8.isDoctype();
            if (r2 == 0) goto L_0x000e;
        L_0x0009:
            r9.error(r7);
        L_0x000c:
            r0 = r1;
        L_0x000d:
            return r0;
        L_0x000e:
            r2 = r8.isStartTag();
            if (r2 == 0) goto L_0x002b;
        L_0x0014:
            r2 = r8.asStartTag();
            r2 = r2.name();
            r3 = "html";
            r2 = r2.equals(r3);
            if (r2 == 0) goto L_0x002b;
        L_0x0024:
            r0 = InBody;
            r0 = r9.process(r8, r0);
            goto L_0x000d;
        L_0x002b:
            r2 = r8.isEndTag();
            if (r2 == 0) goto L_0x004a;
        L_0x0031:
            r2 = r8.asEndTag();
            r2 = r2.name();
            r3 = "noscript";
            r2 = r2.equals(r3);
            if (r2 == 0) goto L_0x004a;
        L_0x0041:
            r9.pop();
            r0 = InHead;
            r9.transition(r0);
            goto L_0x000c;
        L_0x004a:
            r2 = org.jsoup.parser.HtmlTreeBuilderState.isWhitespace(r8);
            if (r2 != 0) goto L_0x0088;
        L_0x0050:
            r2 = r8.isComment();
            if (r2 != 0) goto L_0x0088;
        L_0x0056:
            r2 = r8.isStartTag();
            if (r2 == 0) goto L_0x0090;
        L_0x005c:
            r2 = r8.asStartTag();
            r2 = r2.name();
            r3 = 6;
            r3 = new java.lang.String[r3];
            r4 = "basefont";
            r3[r0] = r4;
            r4 = "bgsound";
            r3[r1] = r4;
            r4 = "link";
            r3[r6] = r4;
            r4 = 3;
            r5 = "meta";
            r3[r4] = r5;
            r4 = 4;
            r5 = "noframes";
            r3[r4] = r5;
            r4 = 5;
            r5 = "style";
            r3[r4] = r5;
            r2 = org.jsoup.helper.StringUtil.in(r2, r3);
            if (r2 == 0) goto L_0x0090;
        L_0x0088:
            r0 = InHead;
            r0 = r9.process(r8, r0);
            goto L_0x000d;
        L_0x0090:
            r2 = r8.isEndTag();
            if (r2 == 0) goto L_0x00ac;
        L_0x0096:
            r2 = r8.asEndTag();
            r2 = r2.name();
            r3 = "br";
            r2 = r2.equals(r3);
            if (r2 == 0) goto L_0x00ac;
        L_0x00a6:
            r0 = r7.anythingElse(r8, r9);
            goto L_0x000d;
        L_0x00ac:
            r2 = r8.isStartTag();
            if (r2 == 0) goto L_0x00ca;
        L_0x00b2:
            r2 = r8.asStartTag();
            r2 = r2.name();
            r3 = new java.lang.String[r6];
            r4 = "head";
            r3[r0] = r4;
            r4 = "noscript";
            r3[r1] = r4;
            r1 = org.jsoup.helper.StringUtil.in(r2, r3);
            if (r1 != 0) goto L_0x00d0;
        L_0x00ca:
            r1 = r8.isEndTag();
            if (r1 == 0) goto L_0x00d5;
        L_0x00d0:
            r9.error(r7);
            goto L_0x000d;
        L_0x00d5:
            r0 = r7.anythingElse(r8, r9);
            goto L_0x000d;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.HtmlTreeBuilderState.5.process(org.jsoup.parser.Token, org.jsoup.parser.HtmlTreeBuilder):boolean");
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.error(this);
            tb.process(new EndTag("noscript"));
            return tb.process(t);
        }
    },
    AfterHead {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
            } else if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype()) {
                tb.error(this);
            } else if (t.isStartTag()) {
                StartTag startTag = t.asStartTag();
                String name = startTag.name();
                if (name.equals("html")) {
                    return tb.process(t, InBody);
                }
                if (name.equals("body")) {
                    tb.insert(startTag);
                    tb.framesetOk(false);
                    tb.transition(InBody);
                } else if (name.equals("frameset")) {
                    tb.insert(startTag);
                    tb.transition(InFrameset);
                } else {
                    if (StringUtil.in(name, "base", "basefont", "bgsound", "link", "meta", "noframes", "script", "style", PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE)) {
                        tb.error(this);
                        Element head = tb.getHeadElement();
                        tb.push(head);
                        tb.process(t, InHead);
                        tb.removeFromStack(head);
                    } else if (name.equals("head")) {
                        tb.error(this);
                        return false;
                    } else {
                        anythingElse(t, tb);
                    }
                }
            } else if (t.isEndTag()) {
                if (StringUtil.in(t.asEndTag().name(), "body", "html")) {
                    anythingElse(t, tb);
                } else {
                    tb.error(this);
                    return false;
                }
            } else {
                anythingElse(t, tb);
            }
            return true;
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.process(new StartTag("body"));
            tb.framesetOk(true);
            return tb.process(t);
        }
    },
    InBody {
        boolean process(Token t, HtmlTreeBuilder tb) {
            String name;
            LinkedList<Element> stack;
            int i;
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[t.type.ordinal()]) {
                case Std.STD_FILE /*1*/:
                    tb.insert(t.asComment());
                    break;
                case Std.STD_URL /*2*/:
                    tb.error(this);
                    return false;
                case Std.STD_URI /*3*/:
                    Token startTag = t.asStartTag();
                    name = startTag.name();
                    Iterator i$;
                    Attribute attribute;
                    if (!name.equals("html")) {
                        if (!StringUtil.in(name, Constants.InBodyStartToHead)) {
                            if (!name.equals("body")) {
                                if (!name.equals("frameset")) {
                                    if (!StringUtil.in(name, Constants.InBodyStartPClosers)) {
                                        if (!StringUtil.in(name, Constants.Headings)) {
                                            if (!StringUtil.in(name, Constants.InBodyStartPreListing)) {
                                                if (name.equals("form")) {
                                                    if (tb.getFormElement() == null) {
                                                        if (tb.inButtonScope("p")) {
                                                            tb.process(new EndTag("p"));
                                                        }
                                                        tb.insertForm(startTag, true);
                                                        break;
                                                    }
                                                    tb.error(this);
                                                    return false;
                                                }
                                                Element el;
                                                if (!name.equals("li")) {
                                                    if (!StringUtil.in(name, Constants.DdDt)) {
                                                        if (!name.equals("plaintext")) {
                                                            if (!name.equals("button")) {
                                                                if (!name.equals("a")) {
                                                                    if (!StringUtil.in(name, Constants.Formatters)) {
                                                                        if (!name.equals("nobr")) {
                                                                            if (!StringUtil.in(name, Constants.InBodyStartApplets)) {
                                                                                if (!name.equals("table")) {
                                                                                    if (!StringUtil.in(name, Constants.InBodyStartEmptyFormatters)) {
                                                                                        if (!name.equals("input")) {
                                                                                            if (!StringUtil.in(name, Constants.InBodyStartMedia)) {
                                                                                                if (!name.equals("hr")) {
                                                                                                    if (!name.equals("image")) {
                                                                                                        if (!name.equals("isindex")) {
                                                                                                            if (!name.equals("textarea")) {
                                                                                                                if (!name.equals("xmp")) {
                                                                                                                    if (!name.equals("iframe")) {
                                                                                                                        if (!name.equals("noembed")) {
                                                                                                                            if (!name.equals("select")) {
                                                                                                                                if (!StringUtil.in(name, Constants.InBodyStartOptions)) {
                                                                                                                                    if (!StringUtil.in(name, Constants.InBodyStartRuby)) {
                                                                                                                                        if (!name.equals("math")) {
                                                                                                                                            if (!name.equals("svg")) {
                                                                                                                                                if (!StringUtil.in(name, Constants.InBodyStartDrop)) {
                                                                                                                                                    tb.reconstructFormattingElements();
                                                                                                                                                    tb.insert((StartTag) startTag);
                                                                                                                                                    break;
                                                                                                                                                }
                                                                                                                                                tb.error(this);
                                                                                                                                                return false;
                                                                                                                                            }
                                                                                                                                            tb.reconstructFormattingElements();
                                                                                                                                            tb.insert((StartTag) startTag);
                                                                                                                                            tb.tokeniser.acknowledgeSelfClosingFlag();
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        tb.reconstructFormattingElements();
                                                                                                                                        tb.insert((StartTag) startTag);
                                                                                                                                        tb.tokeniser.acknowledgeSelfClosingFlag();
                                                                                                                                        break;
                                                                                                                                    }
                                                                                                                                    if (tb.inScope("ruby")) {
                                                                                                                                        tb.generateImpliedEndTags();
                                                                                                                                        if (!tb.currentElement().nodeName().equals("ruby")) {
                                                                                                                                            tb.error(this);
                                                                                                                                            tb.popStackToBefore("ruby");
                                                                                                                                        }
                                                                                                                                        tb.insert((StartTag) startTag);
                                                                                                                                        break;
                                                                                                                                    }
                                                                                                                                }
                                                                                                                                if (tb.currentElement().nodeName().equals("option")) {
                                                                                                                                    tb.process(new EndTag("option"));
                                                                                                                                }
                                                                                                                                tb.reconstructFormattingElements();
                                                                                                                                tb.insert((StartTag) startTag);
                                                                                                                                break;
                                                                                                                            }
                                                                                                                            tb.reconstructFormattingElements();
                                                                                                                            tb.insert((StartTag) startTag);
                                                                                                                            tb.framesetOk(false);
                                                                                                                            HtmlTreeBuilderState state = tb.state();
                                                                                                                            if (!state.equals(InTable) && !state.equals(InCaption) && !state.equals(InTableBody) && !state.equals(InRow) && !state.equals(InCell)) {
                                                                                                                                tb.transition(InSelect);
                                                                                                                                break;
                                                                                                                            }
                                                                                                                            tb.transition(InSelectInTable);
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        HtmlTreeBuilderState.handleRawtext(startTag, tb);
                                                                                                                        break;
                                                                                                                    }
                                                                                                                    tb.framesetOk(false);
                                                                                                                    HtmlTreeBuilderState.handleRawtext(startTag, tb);
                                                                                                                    break;
                                                                                                                }
                                                                                                                if (tb.inButtonScope("p")) {
                                                                                                                    tb.process(new EndTag("p"));
                                                                                                                }
                                                                                                                tb.reconstructFormattingElements();
                                                                                                                tb.framesetOk(false);
                                                                                                                HtmlTreeBuilderState.handleRawtext(startTag, tb);
                                                                                                                break;
                                                                                                            }
                                                                                                            tb.insert((StartTag) startTag);
                                                                                                            tb.tokeniser.transition(TokeniserState.Rcdata);
                                                                                                            tb.markInsertionMode();
                                                                                                            tb.framesetOk(false);
                                                                                                            tb.transition(Text);
                                                                                                            break;
                                                                                                        }
                                                                                                        tb.error(this);
                                                                                                        if (tb.getFormElement() == null) {
                                                                                                            String prompt;
                                                                                                            tb.tokeniser.acknowledgeSelfClosingFlag();
                                                                                                            tb.process(new StartTag("form"));
                                                                                                            if (startTag.attributes.hasKey("action")) {
                                                                                                                tb.getFormElement().attr("action", startTag.attributes.get("action"));
                                                                                                            }
                                                                                                            tb.process(new StartTag("hr"));
                                                                                                            tb.process(new StartTag(PlusShare.KEY_CALL_TO_ACTION_LABEL));
                                                                                                            if (startTag.attributes.hasKey("prompt")) {
                                                                                                                prompt = startTag.attributes.get("prompt");
                                                                                                            } else {
                                                                                                                prompt = "This is a searchable index. Enter search keywords: ";
                                                                                                            }
                                                                                                            tb.process(new Character(prompt));
                                                                                                            Attributes inputAttribs = new Attributes();
                                                                                                            i$ = startTag.attributes.iterator();
                                                                                                            while (i$.hasNext()) {
                                                                                                                Attribute attr = (Attribute) i$.next();
                                                                                                                if (!StringUtil.in(attr.getKey(), Constants.InBodyStartInputAttribs)) {
                                                                                                                    inputAttribs.put(attr);
                                                                                                                }
                                                                                                            }
                                                                                                            inputAttribs.put("name", "isindex");
                                                                                                            tb.process(new StartTag("input", inputAttribs));
                                                                                                            tb.process(new EndTag(PlusShare.KEY_CALL_TO_ACTION_LABEL));
                                                                                                            tb.process(new StartTag("hr"));
                                                                                                            tb.process(new EndTag("form"));
                                                                                                            break;
                                                                                                        }
                                                                                                        return false;
                                                                                                    }
                                                                                                    startTag.name("img");
                                                                                                    return tb.process(startTag);
                                                                                                }
                                                                                                if (tb.inButtonScope("p")) {
                                                                                                    tb.process(new EndTag("p"));
                                                                                                }
                                                                                                tb.insertEmpty(startTag);
                                                                                                tb.framesetOk(false);
                                                                                                break;
                                                                                            }
                                                                                            tb.insertEmpty(startTag);
                                                                                            break;
                                                                                        }
                                                                                        tb.reconstructFormattingElements();
                                                                                        if (!tb.insertEmpty(startTag).attr("type").equalsIgnoreCase("hidden")) {
                                                                                            tb.framesetOk(false);
                                                                                            break;
                                                                                        }
                                                                                    }
                                                                                    tb.reconstructFormattingElements();
                                                                                    tb.insertEmpty(startTag);
                                                                                    tb.framesetOk(false);
                                                                                    break;
                                                                                }
                                                                                if (tb.getDocument().quirksMode() != QuirksMode.quirks) {
                                                                                    if (tb.inButtonScope("p")) {
                                                                                        tb.process(new EndTag("p"));
                                                                                    }
                                                                                }
                                                                                tb.insert((StartTag) startTag);
                                                                                tb.framesetOk(false);
                                                                                tb.transition(InTable);
                                                                                break;
                                                                            }
                                                                            tb.reconstructFormattingElements();
                                                                            tb.insert((StartTag) startTag);
                                                                            tb.insertMarkerToFormattingElements();
                                                                            tb.framesetOk(false);
                                                                            break;
                                                                        }
                                                                        tb.reconstructFormattingElements();
                                                                        if (tb.inScope("nobr")) {
                                                                            tb.error(this);
                                                                            tb.process(new EndTag("nobr"));
                                                                            tb.reconstructFormattingElements();
                                                                        }
                                                                        tb.pushActiveFormattingElements(tb.insert((StartTag) startTag));
                                                                        break;
                                                                    }
                                                                    tb.reconstructFormattingElements();
                                                                    tb.pushActiveFormattingElements(tb.insert((StartTag) startTag));
                                                                    break;
                                                                }
                                                                if (tb.getActiveFormattingElement("a") != null) {
                                                                    tb.error(this);
                                                                    tb.process(new EndTag("a"));
                                                                    Element remainingA = tb.getFromStack("a");
                                                                    if (remainingA != null) {
                                                                        tb.removeFromActiveFormattingElements(remainingA);
                                                                        tb.removeFromStack(remainingA);
                                                                    }
                                                                }
                                                                tb.reconstructFormattingElements();
                                                                tb.pushActiveFormattingElements(tb.insert((StartTag) startTag));
                                                                break;
                                                            }
                                                            if (!tb.inButtonScope("button")) {
                                                                tb.reconstructFormattingElements();
                                                                tb.insert((StartTag) startTag);
                                                                tb.framesetOk(false);
                                                                break;
                                                            }
                                                            tb.error(this);
                                                            tb.process(new EndTag("button"));
                                                            tb.process(startTag);
                                                            break;
                                                        }
                                                        if (tb.inButtonScope("p")) {
                                                            tb.process(new EndTag("p"));
                                                        }
                                                        tb.insert((StartTag) startTag);
                                                        tb.tokeniser.transition(TokeniserState.PLAINTEXT);
                                                        break;
                                                    }
                                                    tb.framesetOk(false);
                                                    stack = tb.getStack();
                                                    i = stack.size() - 1;
                                                    while (i > 0) {
                                                        el = (Element) stack.get(i);
                                                        if (StringUtil.in(el.nodeName(), Constants.DdDt)) {
                                                            tb.process(new EndTag(el.nodeName()));
                                                        } else if (!tb.isSpecial(el) || StringUtil.in(el.nodeName(), Constants.InBodyStartLiBreakers)) {
                                                            i--;
                                                        }
                                                        if (tb.inButtonScope("p")) {
                                                            tb.process(new EndTag("p"));
                                                        }
                                                        tb.insert((StartTag) startTag);
                                                        break;
                                                    }
                                                    if (tb.inButtonScope("p")) {
                                                        tb.process(new EndTag("p"));
                                                    }
                                                    tb.insert((StartTag) startTag);
                                                } else {
                                                    tb.framesetOk(false);
                                                    stack = tb.getStack();
                                                    i = stack.size() - 1;
                                                    while (i > 0) {
                                                        el = (Element) stack.get(i);
                                                        if (el.nodeName().equals("li")) {
                                                            tb.process(new EndTag("li"));
                                                        } else if (!tb.isSpecial(el) || StringUtil.in(el.nodeName(), Constants.InBodyStartLiBreakers)) {
                                                            i--;
                                                        }
                                                        if (tb.inButtonScope("p")) {
                                                            tb.process(new EndTag("p"));
                                                        }
                                                        tb.insert((StartTag) startTag);
                                                        break;
                                                    }
                                                    if (tb.inButtonScope("p")) {
                                                        tb.process(new EndTag("p"));
                                                    }
                                                    tb.insert((StartTag) startTag);
                                                }
                                            } else {
                                                if (tb.inButtonScope("p")) {
                                                    tb.process(new EndTag("p"));
                                                }
                                                tb.insert((StartTag) startTag);
                                                tb.framesetOk(false);
                                                break;
                                            }
                                        }
                                        if (tb.inButtonScope("p")) {
                                            tb.process(new EndTag("p"));
                                        }
                                        if (StringUtil.in(tb.currentElement().nodeName(), Constants.Headings)) {
                                            tb.error(this);
                                            tb.pop();
                                        }
                                        tb.insert((StartTag) startTag);
                                        break;
                                    }
                                    if (tb.inButtonScope("p")) {
                                        tb.process(new EndTag("p"));
                                    }
                                    tb.insert((StartTag) startTag);
                                    break;
                                }
                                tb.error(this);
                                stack = tb.getStack();
                                if (stack.size() != 1 && (stack.size() <= 2 || ((Element) stack.get(1)).nodeName().equals("body"))) {
                                    if (tb.framesetOk()) {
                                        Element second = (Element) stack.get(1);
                                        if (second.parent() != null) {
                                            second.remove();
                                        }
                                        while (stack.size() > 1) {
                                            stack.removeLast();
                                        }
                                        tb.insert((StartTag) startTag);
                                        tb.transition(InFrameset);
                                        break;
                                    }
                                    return false;
                                }
                                return false;
                            }
                            tb.error(this);
                            stack = tb.getStack();
                            if (stack.size() != 1 && (stack.size() <= 2 || ((Element) stack.get(1)).nodeName().equals("body"))) {
                                tb.framesetOk(false);
                                Element body = (Element) stack.get(1);
                                i$ = startTag.getAttributes().iterator();
                                while (i$.hasNext()) {
                                    attribute = (Attribute) i$.next();
                                    if (!body.hasAttr(attribute.getKey())) {
                                        body.attributes().put(attribute);
                                    }
                                }
                                break;
                            }
                            return false;
                        }
                        return tb.process(t, InHead);
                    }
                    tb.error(this);
                    Element html = (Element) tb.getStack().getFirst();
                    i$ = startTag.getAttributes().iterator();
                    while (i$.hasNext()) {
                        attribute = (Attribute) i$.next();
                        if (!html.hasAttr(attribute.getKey())) {
                            html.attributes().put(attribute);
                        }
                    }
                    break;
                    break;
                case Std.STD_CLASS /*4*/:
                    EndTag endTag = t.asEndTag();
                    name = endTag.name();
                    if (name.equals("body")) {
                        if (tb.inScope("body")) {
                            tb.transition(AfterBody);
                            break;
                        }
                        tb.error(this);
                        return false;
                    }
                    if (name.equals("html")) {
                        if (tb.process(new EndTag("body"))) {
                            return tb.process(endTag);
                        }
                    }
                    if (StringUtil.in(name, Constants.InBodyEndClosers)) {
                        if (tb.inScope(name)) {
                            tb.generateImpliedEndTags();
                            if (!tb.currentElement().nodeName().equals(name)) {
                                tb.error(this);
                            }
                            tb.popStackToClose(name);
                            break;
                        }
                        tb.error(this);
                        return false;
                    }
                    if (name.equals("form")) {
                        Element currentForm = tb.getFormElement();
                        tb.setFormElement(null);
                        if (currentForm != null && tb.inScope(name)) {
                            tb.generateImpliedEndTags();
                            if (!tb.currentElement().nodeName().equals(name)) {
                                tb.error(this);
                            }
                            tb.removeFromStack(currentForm);
                            break;
                        }
                        tb.error(this);
                        return false;
                    }
                    if (name.equals("p")) {
                        if (tb.inButtonScope(name)) {
                            tb.generateImpliedEndTags(name);
                            if (!tb.currentElement().nodeName().equals(name)) {
                                tb.error(this);
                            }
                            tb.popStackToClose(name);
                            break;
                        }
                        tb.error(this);
                        tb.process(new StartTag(name));
                        return tb.process(endTag);
                    }
                    if (name.equals("li")) {
                        if (tb.inListItemScope(name)) {
                            tb.generateImpliedEndTags(name);
                            if (!tb.currentElement().nodeName().equals(name)) {
                                tb.error(this);
                            }
                            tb.popStackToClose(name);
                            break;
                        }
                        tb.error(this);
                        return false;
                    }
                    if (StringUtil.in(name, Constants.DdDt)) {
                        if (tb.inScope(name)) {
                            tb.generateImpliedEndTags(name);
                            if (!tb.currentElement().nodeName().equals(name)) {
                                tb.error(this);
                            }
                            tb.popStackToClose(name);
                            break;
                        }
                        tb.error(this);
                        return false;
                    }
                    if (StringUtil.in(name, Constants.Headings)) {
                        if (tb.inScope(Constants.Headings)) {
                            tb.generateImpliedEndTags(name);
                            if (!tb.currentElement().nodeName().equals(name)) {
                                tb.error(this);
                            }
                            tb.popStackToClose(Constants.Headings);
                            break;
                        }
                        tb.error(this);
                        return false;
                    }
                    if (name.equals("sarcasm")) {
                        return anyOtherEndTag(t, tb);
                    }
                    if (StringUtil.in(name, Constants.InBodyEndAdoptionFormatters)) {
                        i = 0;
                        while (i < 8) {
                            Element formatEl = tb.getActiveFormattingElement(name);
                            if (formatEl == null) {
                                return anyOtherEndTag(t, tb);
                            }
                            if (tb.onStack(formatEl)) {
                                if (tb.inScope(formatEl.nodeName())) {
                                    if (tb.currentElement() != formatEl) {
                                        tb.error(this);
                                    }
                                    Node furthestBlock = null;
                                    Element commonAncestor = null;
                                    boolean seenFormattingElement = false;
                                    stack = tb.getStack();
                                    int stackSize = stack.size();
                                    int si = 0;
                                    while (si < stackSize && si < 64) {
                                        Node el2 = (Element) stack.get(si);
                                        if (el2 == formatEl) {
                                            commonAncestor = (Element) stack.get(si - 1);
                                            seenFormattingElement = true;
                                        } else if (seenFormattingElement && tb.isSpecial(el2)) {
                                            furthestBlock = el2;
                                        }
                                        si++;
                                    }
                                    if (furthestBlock == null) {
                                        tb.popStackToClose(formatEl.nodeName());
                                        tb.removeFromActiveFormattingElements(formatEl);
                                        return true;
                                    }
                                    Element adopter;
                                    Element node = furthestBlock;
                                    Node lastNode = furthestBlock;
                                    for (int j = 0; j < 3; j++) {
                                        if (tb.onStack(node)) {
                                            node = tb.aboveOnStack(node);
                                        }
                                        if (!tb.isInActiveFormattingElements(node)) {
                                            tb.removeFromStack(node);
                                        } else if (node == formatEl) {
                                            if (StringUtil.in(commonAncestor.nodeName(), Constants.InBodyEndTableFosters)) {
                                                if (lastNode.parent() != null) {
                                                    lastNode.remove();
                                                }
                                                commonAncestor.appendChild(lastNode);
                                            } else {
                                                if (lastNode.parent() != null) {
                                                    lastNode.remove();
                                                }
                                                tb.insertInFosterParent(lastNode);
                                            }
                                            adopter = new Element(formatEl.tag(), tb.getBaseUri());
                                            adopter.attributes().addAll(formatEl.attributes());
                                            for (Node childNode : (Node[]) furthestBlock.childNodes().toArray(new Node[furthestBlock.childNodeSize()])) {
                                                adopter.appendChild(childNode);
                                            }
                                            furthestBlock.appendChild(adopter);
                                            tb.removeFromActiveFormattingElements(formatEl);
                                            tb.removeFromStack(formatEl);
                                            tb.insertOnStackAfter(furthestBlock, adopter);
                                            i++;
                                        } else {
                                            Element element = new Element(Tag.valueOf(node.nodeName()), tb.getBaseUri());
                                            tb.replaceActiveFormattingElement(node, element);
                                            tb.replaceOnStack(node, element);
                                            Node node2 = element;
                                            if (lastNode == furthestBlock) {
                                            }
                                            if (lastNode.parent() != null) {
                                                lastNode.remove();
                                            }
                                            node2.appendChild(lastNode);
                                            lastNode = node2;
                                        }
                                    }
                                    if (StringUtil.in(commonAncestor.nodeName(), Constants.InBodyEndTableFosters)) {
                                        if (lastNode.parent() != null) {
                                            lastNode.remove();
                                        }
                                        commonAncestor.appendChild(lastNode);
                                    } else {
                                        if (lastNode.parent() != null) {
                                            lastNode.remove();
                                        }
                                        tb.insertInFosterParent(lastNode);
                                    }
                                    adopter = new Element(formatEl.tag(), tb.getBaseUri());
                                    adopter.attributes().addAll(formatEl.attributes());
                                    while (i$ < len$) {
                                        adopter.appendChild(childNode);
                                    }
                                    furthestBlock.appendChild(adopter);
                                    tb.removeFromActiveFormattingElements(formatEl);
                                    tb.removeFromStack(formatEl);
                                    tb.insertOnStackAfter(furthestBlock, adopter);
                                    i++;
                                } else {
                                    tb.error(this);
                                    return false;
                                }
                            }
                            tb.error(this);
                            tb.removeFromActiveFormattingElements(formatEl);
                            return true;
                        }
                        break;
                    }
                    if (StringUtil.in(name, Constants.InBodyStartApplets)) {
                        if (!tb.inScope("name")) {
                            if (tb.inScope(name)) {
                                tb.generateImpliedEndTags();
                                if (!tb.currentElement().nodeName().equals(name)) {
                                    tb.error(this);
                                }
                                tb.popStackToClose(name);
                                tb.clearFormattingElementsToLastMarker();
                                break;
                            }
                            tb.error(this);
                            return false;
                        }
                    }
                    if (!name.equals("br")) {
                        return anyOtherEndTag(t, tb);
                    }
                    tb.error(this);
                    tb.process(new StartTag("br"));
                    return false;
                    break;
                case Std.STD_JAVA_TYPE /*5*/:
                    Character c = t.asCharacter();
                    if (!c.getData().equals(HtmlTreeBuilderState.nullString)) {
                        if (!HtmlTreeBuilderState.isWhitespace(c)) {
                            tb.reconstructFormattingElements();
                            tb.insert(c);
                            tb.framesetOk(false);
                            break;
                        }
                        tb.reconstructFormattingElements();
                        tb.insert(c);
                        break;
                    }
                    tb.error(this);
                    return false;
            }
            return true;
        }

        boolean anyOtherEndTag(Token t, HtmlTreeBuilder tb) {
            String name = t.asEndTag().name();
            Iterator<Element> it = tb.getStack().descendingIterator();
            while (it.hasNext()) {
                Element node = (Element) it.next();
                if (node.nodeName().equals(name)) {
                    tb.generateImpliedEndTags(name);
                    if (!name.equals(tb.currentElement().nodeName())) {
                        tb.error(this);
                    }
                    tb.popStackToClose(name);
                    return true;
                } else if (tb.isSpecial(node)) {
                    tb.error(this);
                    return false;
                }
            }
            return true;
        }
    },
    Text {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isCharacter()) {
                tb.insert(t.asCharacter());
            } else if (t.isEOF()) {
                tb.error(this);
                tb.pop();
                tb.transition(tb.originalState());
                return tb.process(t);
            } else if (t.isEndTag()) {
                tb.pop();
                tb.transition(tb.originalState());
            }
            return true;
        }
    },
    InTable {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isCharacter()) {
                tb.newPendingTableCharacters();
                tb.markInsertionMode();
                tb.transition(InTableText);
                return tb.process(t);
            } else if (t.isComment()) {
                tb.insert(t.asComment());
                return true;
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag()) {
                StartTag startTag = t.asStartTag();
                name = startTag.name();
                if (name.equals("caption")) {
                    tb.clearStackToTableContext();
                    tb.insertMarkerToFormattingElements();
                    tb.insert(startTag);
                    tb.transition(InCaption);
                    return true;
                } else if (name.equals("colgroup")) {
                    tb.clearStackToTableContext();
                    tb.insert(startTag);
                    tb.transition(InColumnGroup);
                    return true;
                } else if (name.equals("col")) {
                    tb.process(new StartTag("colgroup"));
                    return tb.process(t);
                } else {
                    if (StringUtil.in(name, "tbody", "tfoot", "thead")) {
                        tb.clearStackToTableContext();
                        tb.insert(startTag);
                        tb.transition(InTableBody);
                        return true;
                    }
                    if (StringUtil.in(name, "td", "th", "tr")) {
                        tb.process(new StartTag("tbody"));
                        return tb.process(t);
                    } else if (name.equals("table")) {
                        tb.error(this);
                        if (tb.process(new EndTag("table"))) {
                            return tb.process(t);
                        }
                        return true;
                    } else {
                        if (StringUtil.in(name, "style", "script")) {
                            return tb.process(t, InHead);
                        }
                        if (name.equals("input")) {
                            if (!startTag.attributes.get("type").equalsIgnoreCase("hidden")) {
                                return anythingElse(t, tb);
                            }
                            tb.insertEmpty(startTag);
                            return true;
                        } else if (!name.equals("form")) {
                            return anythingElse(t, tb);
                        } else {
                            tb.error(this);
                            if (tb.getFormElement() != null) {
                                return false;
                            }
                            tb.insertForm(startTag, false);
                            return true;
                        }
                    }
                }
            } else if (t.isEndTag()) {
                name = t.asEndTag().name();
                if (!name.equals("table")) {
                    if (!StringUtil.in(name, "body", "caption", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                        return anythingElse(t, tb);
                    }
                    tb.error(this);
                    return false;
                } else if (tb.inTableScope(name)) {
                    tb.popStackToClose("table");
                    tb.resetInsertionMode();
                    return true;
                } else {
                    tb.error(this);
                    return false;
                }
            } else if (!t.isEOF()) {
                return anythingElse(t, tb);
            } else {
                if (!tb.currentElement().nodeName().equals("html")) {
                    return true;
                }
                tb.error(this);
                return true;
            }
        }

        boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.error(this);
            if (!StringUtil.in(tb.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                return tb.process(t, InBody);
            }
            tb.setFosterInserts(true);
            boolean processed = tb.process(t, InBody);
            tb.setFosterInserts(false);
            return processed;
        }
    },
    InTableText {
        boolean process(Token t, HtmlTreeBuilder tb) {
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[t.type.ordinal()]) {
                case Std.STD_JAVA_TYPE /*5*/:
                    Character c = t.asCharacter();
                    if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
                        tb.error(this);
                        return false;
                    }
                    tb.getPendingTableCharacters().add(c);
                    return true;
                default:
                    if (tb.getPendingTableCharacters().size() > 0) {
                        for (Character character : tb.getPendingTableCharacters()) {
                            if (HtmlTreeBuilderState.isWhitespace(character)) {
                                tb.insert(character);
                            } else {
                                tb.error(this);
                                if (StringUtil.in(tb.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                                    tb.setFosterInserts(true);
                                    tb.process(character, InBody);
                                    tb.setFosterInserts(false);
                                } else {
                                    tb.process(character, InBody);
                                }
                            }
                        }
                        tb.newPendingTableCharacters();
                    }
                    tb.transition(tb.originalState());
                    return tb.process(t);
            }
        }
    },
    InCaption {
        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        boolean process(org.jsoup.parser.Token r13, org.jsoup.parser.HtmlTreeBuilder r14) {
            /*
            r12 = this;
            r11 = 4;
            r10 = 3;
            r9 = 2;
            r4 = 1;
            r3 = 0;
            r5 = r13.isEndTag();
            if (r5 == 0) goto L_0x0052;
        L_0x000b:
            r5 = r13.asEndTag();
            r5 = r5.name();
            r6 = "caption";
            r5 = r5.equals(r6);
            if (r5 == 0) goto L_0x0052;
        L_0x001b:
            r0 = r13.asEndTag();
            r1 = r0.name();
            r5 = r14.inTableScope(r1);
            if (r5 != 0) goto L_0x002d;
        L_0x0029:
            r14.error(r12);
        L_0x002c:
            return r3;
        L_0x002d:
            r14.generateImpliedEndTags();
            r3 = r14.currentElement();
            r3 = r3.nodeName();
            r5 = "caption";
            r3 = r3.equals(r5);
            if (r3 != 0) goto L_0x0043;
        L_0x0040:
            r14.error(r12);
        L_0x0043:
            r3 = "caption";
            r14.popStackToClose(r3);
            r14.clearFormattingElementsToLastMarker();
            r3 = InTable;
            r14.transition(r3);
        L_0x0050:
            r3 = r4;
            goto L_0x002c;
        L_0x0052:
            r5 = r13.isStartTag();
            if (r5 == 0) goto L_0x0093;
        L_0x0058:
            r5 = r13.asStartTag();
            r5 = r5.name();
            r6 = 9;
            r6 = new java.lang.String[r6];
            r7 = "caption";
            r6[r3] = r7;
            r7 = "col";
            r6[r4] = r7;
            r7 = "colgroup";
            r6[r9] = r7;
            r7 = "tbody";
            r6[r10] = r7;
            r7 = "td";
            r6[r11] = r7;
            r7 = 5;
            r8 = "tfoot";
            r6[r7] = r8;
            r7 = 6;
            r8 = "th";
            r6[r7] = r8;
            r7 = 7;
            r8 = "thead";
            r6[r7] = r8;
            r7 = 8;
            r8 = "tr";
            r6[r7] = r8;
            r5 = org.jsoup.helper.StringUtil.in(r5, r6);
            if (r5 != 0) goto L_0x00a9;
        L_0x0093:
            r5 = r13.isEndTag();
            if (r5 == 0) goto L_0x00bf;
        L_0x0099:
            r5 = r13.asEndTag();
            r5 = r5.name();
            r6 = "table";
            r5 = r5.equals(r6);
            if (r5 == 0) goto L_0x00bf;
        L_0x00a9:
            r14.error(r12);
            r3 = new org.jsoup.parser.Token$EndTag;
            r5 = "caption";
            r3.<init>(r5);
            r2 = r14.process(r3);
            if (r2 == 0) goto L_0x0050;
        L_0x00b9:
            r3 = r14.process(r13);
            goto L_0x002c;
        L_0x00bf:
            r5 = r13.isEndTag();
            if (r5 == 0) goto L_0x010b;
        L_0x00c5:
            r5 = r13.asEndTag();
            r5 = r5.name();
            r6 = 10;
            r6 = new java.lang.String[r6];
            r7 = "body";
            r6[r3] = r7;
            r7 = "col";
            r6[r4] = r7;
            r4 = "colgroup";
            r6[r9] = r4;
            r4 = "html";
            r6[r10] = r4;
            r4 = "tbody";
            r6[r11] = r4;
            r4 = 5;
            r7 = "td";
            r6[r4] = r7;
            r4 = 6;
            r7 = "tfoot";
            r6[r4] = r7;
            r4 = 7;
            r7 = "th";
            r6[r4] = r7;
            r4 = 8;
            r7 = "thead";
            r6[r4] = r7;
            r4 = 9;
            r7 = "tr";
            r6[r4] = r7;
            r4 = org.jsoup.helper.StringUtil.in(r5, r6);
            if (r4 == 0) goto L_0x010b;
        L_0x0106:
            r14.error(r12);
            goto L_0x002c;
        L_0x010b:
            r3 = InBody;
            r3 = r14.process(r13, r3);
            goto L_0x002c;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.HtmlTreeBuilderState.11.process(org.jsoup.parser.Token, org.jsoup.parser.HtmlTreeBuilder):boolean");
        }
    },
    InColumnGroup {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
                return true;
            }
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[t.type.ordinal()]) {
                case Std.STD_FILE /*1*/:
                    tb.insert(t.asComment());
                    return true;
                case Std.STD_URL /*2*/:
                    tb.error(this);
                    return true;
                case Std.STD_URI /*3*/:
                    StartTag startTag = t.asStartTag();
                    String name = startTag.name();
                    if (name.equals("html")) {
                        return tb.process(t, InBody);
                    }
                    if (!name.equals("col")) {
                        return anythingElse(t, tb);
                    }
                    tb.insertEmpty(startTag);
                    return true;
                case Std.STD_CLASS /*4*/:
                    if (!t.asEndTag().name().equals("colgroup")) {
                        return anythingElse(t, tb);
                    }
                    if (tb.currentElement().nodeName().equals("html")) {
                        tb.error(this);
                        return false;
                    }
                    tb.pop();
                    tb.transition(InTable);
                    return true;
                case Std.STD_CURRENCY /*6*/:
                    if (tb.currentElement().nodeName().equals("html")) {
                        return true;
                    }
                    return anythingElse(t, tb);
                default:
                    return anythingElse(t, tb);
            }
        }

        private boolean anythingElse(Token t, TreeBuilder tb) {
            if (tb.process(new EndTag("colgroup"))) {
                return tb.process(t);
            }
            return true;
        }
    },
    InTableBody {
        boolean process(Token t, HtmlTreeBuilder tb) {
            String name;
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[t.type.ordinal()]) {
                case Std.STD_URI /*3*/:
                    StartTag startTag = t.asStartTag();
                    name = startTag.name();
                    if (name.equals("tr")) {
                        tb.clearStackToTableBodyContext();
                        tb.insert(startTag);
                        tb.transition(InRow);
                        break;
                    }
                    if (StringUtil.in(name, "th", "td")) {
                        tb.error(this);
                        tb.process(new StartTag("tr"));
                        return tb.process(startTag);
                    }
                    if (StringUtil.in(name, "caption", "col", "colgroup", "tbody", "tfoot", "thead")) {
                        return exitTableBody(t, tb);
                    }
                    return anythingElse(t, tb);
                case Std.STD_CLASS /*4*/:
                    name = t.asEndTag().name();
                    if (StringUtil.in(name, "tbody", "tfoot", "thead")) {
                        if (tb.inTableScope(name)) {
                            tb.clearStackToTableBodyContext();
                            tb.pop();
                            tb.transition(InTable);
                            break;
                        }
                        tb.error(this);
                        return false;
                    } else if (name.equals("table")) {
                        return exitTableBody(t, tb);
                    } else {
                        if (!StringUtil.in(name, "body", "caption", "col", "colgroup", "html", "td", "th", "tr")) {
                            return anythingElse(t, tb);
                        }
                        tb.error(this);
                        return false;
                    }
                default:
                    return anythingElse(t, tb);
            }
            return true;
        }

        private boolean exitTableBody(Token t, HtmlTreeBuilder tb) {
            if (tb.inTableScope("tbody") || tb.inTableScope("thead") || tb.inScope("tfoot")) {
                tb.clearStackToTableBodyContext();
                tb.process(new EndTag(tb.currentElement().nodeName()));
                return tb.process(t);
            }
            tb.error(this);
            return false;
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            return tb.process(t, InTable);
        }
    },
    InRow {
        boolean process(Token t, HtmlTreeBuilder tb) {
            String name;
            if (t.isStartTag()) {
                StartTag startTag = t.asStartTag();
                name = startTag.name();
                if (StringUtil.in(name, "th", "td")) {
                    tb.clearStackToTableRowContext();
                    tb.insert(startTag);
                    tb.transition(InCell);
                    tb.insertMarkerToFormattingElements();
                } else {
                    if (StringUtil.in(name, "caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr")) {
                        return handleMissingTr(t, tb);
                    }
                    return anythingElse(t, tb);
                }
            } else if (!t.isEndTag()) {
                return anythingElse(t, tb);
            } else {
                name = t.asEndTag().name();
                if (name.equals("tr")) {
                    if (tb.inTableScope(name)) {
                        tb.clearStackToTableRowContext();
                        tb.pop();
                        tb.transition(InTableBody);
                    } else {
                        tb.error(this);
                        return false;
                    }
                } else if (name.equals("table")) {
                    return handleMissingTr(t, tb);
                } else {
                    if (!StringUtil.in(name, "tbody", "tfoot", "thead")) {
                        if (!StringUtil.in(name, "body", "caption", "col", "colgroup", "html", "td", "th")) {
                            return anythingElse(t, tb);
                        }
                        tb.error(this);
                        return false;
                    } else if (tb.inTableScope(name)) {
                        tb.process(new EndTag("tr"));
                        return tb.process(t);
                    } else {
                        tb.error(this);
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            return tb.process(t, InTable);
        }

        private boolean handleMissingTr(Token t, TreeBuilder tb) {
            if (tb.process(new EndTag("tr"))) {
                return tb.process(t);
            }
            return false;
        }
    },
    InCell {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isEndTag()) {
                String name = t.asEndTag().name();
                if (!StringUtil.in(name, "td", "th")) {
                    if (StringUtil.in(name, "body", "caption", "col", "colgroup", "html")) {
                        tb.error(this);
                        return false;
                    }
                    if (!StringUtil.in(name, "table", "tbody", "tfoot", "thead", "tr")) {
                        return anythingElse(t, tb);
                    }
                    if (tb.inTableScope(name)) {
                        closeCell(tb);
                        return tb.process(t);
                    }
                    tb.error(this);
                    return false;
                } else if (tb.inTableScope(name)) {
                    tb.generateImpliedEndTags();
                    if (!tb.currentElement().nodeName().equals(name)) {
                        tb.error(this);
                    }
                    tb.popStackToClose(name);
                    tb.clearFormattingElementsToLastMarker();
                    tb.transition(InRow);
                    return true;
                } else {
                    tb.error(this);
                    tb.transition(InRow);
                    return false;
                }
            }
            if (t.isStartTag()) {
                if (StringUtil.in(t.asStartTag().name(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                    if (tb.inTableScope("td") || tb.inTableScope("th")) {
                        closeCell(tb);
                        return tb.process(t);
                    }
                    tb.error(this);
                    return false;
                }
            }
            return anythingElse(t, tb);
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            return tb.process(t, InBody);
        }

        private void closeCell(HtmlTreeBuilder tb) {
            if (tb.inTableScope("td")) {
                tb.process(new EndTag("td"));
            } else {
                tb.process(new EndTag("th"));
            }
        }
    },
    InSelect {
        boolean process(Token t, HtmlTreeBuilder tb) {
            String name;
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[t.type.ordinal()]) {
                case Std.STD_FILE /*1*/:
                    tb.insert(t.asComment());
                    break;
                case Std.STD_URL /*2*/:
                    tb.error(this);
                    return false;
                case Std.STD_URI /*3*/:
                    StartTag start = t.asStartTag();
                    name = start.name();
                    if (name.equals("html")) {
                        return tb.process(start, InBody);
                    }
                    if (name.equals("option")) {
                        tb.process(new EndTag("option"));
                        tb.insert(start);
                        break;
                    } else if (name.equals("optgroup")) {
                        if (tb.currentElement().nodeName().equals("option")) {
                            tb.process(new EndTag("option"));
                        } else if (tb.currentElement().nodeName().equals("optgroup")) {
                            tb.process(new EndTag("optgroup"));
                        }
                        tb.insert(start);
                        break;
                    } else if (name.equals("select")) {
                        tb.error(this);
                        return tb.process(new EndTag("select"));
                    } else {
                        if (StringUtil.in(name, "input", "keygen", "textarea")) {
                            tb.error(this);
                            if (!tb.inSelectScope("select")) {
                                return false;
                            }
                            tb.process(new EndTag("select"));
                            return tb.process(start);
                        } else if (name.equals("script")) {
                            return tb.process(t, InHead);
                        } else {
                            return anythingElse(t, tb);
                        }
                    }
                case Std.STD_CLASS /*4*/:
                    name = t.asEndTag().name();
                    if (name.equals("optgroup")) {
                        if (tb.currentElement().nodeName().equals("option") && tb.aboveOnStack(tb.currentElement()) != null && tb.aboveOnStack(tb.currentElement()).nodeName().equals("optgroup")) {
                            tb.process(new EndTag("option"));
                        }
                        if (!tb.currentElement().nodeName().equals("optgroup")) {
                            tb.error(this);
                            break;
                        }
                        tb.pop();
                        break;
                    } else if (name.equals("option")) {
                        if (!tb.currentElement().nodeName().equals("option")) {
                            tb.error(this);
                            break;
                        }
                        tb.pop();
                        break;
                    } else if (name.equals("select")) {
                        if (tb.inSelectScope(name)) {
                            tb.popStackToClose(name);
                            tb.resetInsertionMode();
                            break;
                        }
                        tb.error(this);
                        return false;
                    } else {
                        return anythingElse(t, tb);
                    }
                case Std.STD_JAVA_TYPE /*5*/:
                    Character c = t.asCharacter();
                    if (!c.getData().equals(HtmlTreeBuilderState.nullString)) {
                        tb.insert(c);
                        break;
                    }
                    tb.error(this);
                    return false;
                case Std.STD_CURRENCY /*6*/:
                    if (!tb.currentElement().nodeName().equals("html")) {
                        tb.error(this);
                        break;
                    }
                    break;
                default:
                    return anythingElse(t, tb);
            }
            return true;
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.error(this);
            return false;
        }
    },
    InSelectInTable {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isStartTag()) {
                if (StringUtil.in(t.asStartTag().name(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                    tb.error(this);
                    tb.process(new EndTag("select"));
                    return tb.process(t);
                }
            }
            if (t.isEndTag()) {
                if (StringUtil.in(t.asEndTag().name(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                    tb.error(this);
                    if (!tb.inTableScope(t.asEndTag().name())) {
                        return false;
                    }
                    tb.process(new EndTag("select"));
                    return tb.process(t);
                }
            }
            return tb.process(t, InSelect);
        }
    },
    AfterBody {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                return tb.process(t, InBody);
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag() && t.asStartTag().name().equals("html")) {
                return tb.process(t, InBody);
            } else {
                if (t.isEndTag() && t.asEndTag().name().equals("html")) {
                    if (tb.isFragmentParsing()) {
                        tb.error(this);
                        return false;
                    }
                    tb.transition(AfterAfterBody);
                } else if (!t.isEOF()) {
                    tb.error(this);
                    tb.transition(InBody);
                    return tb.process(t);
                }
            }
            return true;
        }
    },
    InFrameset {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
            } else if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag()) {
                StartTag start = t.asStartTag();
                String name = start.name();
                if (name.equals("html")) {
                    return tb.process(start, InBody);
                }
                if (name.equals("frameset")) {
                    tb.insert(start);
                } else if (name.equals("frame")) {
                    tb.insertEmpty(start);
                } else if (name.equals("noframes")) {
                    return tb.process(start, InHead);
                } else {
                    tb.error(this);
                    return false;
                }
            } else if (t.isEndTag() && t.asEndTag().name().equals("frameset")) {
                if (tb.currentElement().nodeName().equals("html")) {
                    tb.error(this);
                    return false;
                }
                tb.pop();
                if (!(tb.isFragmentParsing() || tb.currentElement().nodeName().equals("frameset"))) {
                    tb.transition(AfterFrameset);
                }
            } else if (!t.isEOF()) {
                tb.error(this);
                return false;
            } else if (!tb.currentElement().nodeName().equals("html")) {
                tb.error(this);
                return true;
            }
            return true;
        }
    },
    AfterFrameset {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
            } else if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag() && t.asStartTag().name().equals("html")) {
                return tb.process(t, InBody);
            } else {
                if (t.isEndTag() && t.asEndTag().name().equals("html")) {
                    tb.transition(AfterAfterFrameset);
                } else if (t.isStartTag() && t.asStartTag().name().equals("noframes")) {
                    return tb.process(t, InHead);
                } else {
                    if (!t.isEOF()) {
                        tb.error(this);
                        return false;
                    }
                }
            }
            return true;
        }
    },
    AfterAfterBody {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype() || HtmlTreeBuilderState.isWhitespace(t) || (t.isStartTag() && t.asStartTag().name().equals("html"))) {
                return tb.process(t, InBody);
            } else {
                if (!t.isEOF()) {
                    tb.error(this);
                    tb.transition(InBody);
                    return tb.process(t);
                }
            }
            return true;
        }
    },
    AfterAfterFrameset {
        boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype() || HtmlTreeBuilderState.isWhitespace(t) || (t.isStartTag() && t.asStartTag().name().equals("html"))) {
                return tb.process(t, InBody);
            } else {
                if (!t.isEOF()) {
                    if (t.isStartTag() && t.asStartTag().name().equals("noframes")) {
                        return tb.process(t, InHead);
                    }
                    tb.error(this);
                    return false;
                }
            }
            return true;
        }
    },
    ForeignContent {
        boolean process(Token t, HtmlTreeBuilder tb) {
            return true;
        }
    };
    
    private static String nullString;

    /* renamed from: org.jsoup.parser.HtmlTreeBuilderState.24 */
    static /* synthetic */ class AnonymousClass24 {
        static final /* synthetic */ int[] $SwitchMap$org$jsoup$parser$Token$TokenType;

        static {
            $SwitchMap$org$jsoup$parser$Token$TokenType = new int[TokenType.values().length];
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[TokenType.Comment.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[TokenType.Doctype.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[TokenType.StartTag.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[TokenType.EndTag.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[TokenType.Character.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[TokenType.EOF.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

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

        private Constants() {
        }

        static {
            InBodyStartToHead = new String[]{"base", "basefont", "bgsound", "command", "link", "meta", "noframes", "script", "style", PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE};
            InBodyStartPClosers = new String[]{"address", "article", "aside", "blockquote", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "menu", "nav", "ol", "p", "section", "summary", "ul"};
            Headings = new String[]{"h1", "h2", "h3", "h4", "h5", "h6"};
            InBodyStartPreListing = new String[]{"pre", "listing"};
            InBodyStartLiBreakers = new String[]{"address", "div", "p"};
            DdDt = new String[]{"dd", "dt"};
            Formatters = new String[]{"b", "big", "code", "em", "font", "i", DanbooruImageFragment.BREADCRUMB_NAME, "small", "strike", "strong", "tt", "u"};
            InBodyStartApplets = new String[]{"applet", "marquee", "object"};
            InBodyStartEmptyFormatters = new String[]{"area", "br", "embed", "img", "keygen", "wbr"};
            InBodyStartMedia = new String[]{"param", "source", "track"};
            InBodyStartInputAttribs = new String[]{"name", "action", "prompt"};
            InBodyStartOptions = new String[]{"optgroup", "option"};
            InBodyStartRuby = new String[]{"rp", "rt"};
            InBodyStartDrop = new String[]{"caption", "col", "colgroup", "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr"};
            InBodyEndClosers = new String[]{"address", "article", "aside", "blockquote", "button", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "listing", "menu", "nav", "ol", "pre", "section", "summary", "ul"};
            InBodyEndAdoptionFormatters = new String[]{"a", "b", "big", "code", "em", "font", "i", "nobr", DanbooruImageFragment.BREADCRUMB_NAME, "small", "strike", "strong", "tt", "u"};
            InBodyEndTableFosters = new String[]{"table", "tbody", "tfoot", "thead", "tr"};
        }
    }

    abstract boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder);

    static {
        nullString = String.valueOf('\u0000');
    }

    private static boolean isWhitespace(Token t) {
        if (!t.isCharacter()) {
            return false;
        }
        String data = t.asCharacter().getData();
        for (int i = 0; i < data.length(); i++) {
            if (!StringUtil.isWhitespace(data.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static void handleRcData(StartTag startTag, HtmlTreeBuilder tb) {
        tb.insert(startTag);
        tb.tokeniser.transition(TokeniserState.Rcdata);
        tb.markInsertionMode();
        tb.transition(Text);
    }

    private static void handleRawtext(StartTag startTag, HtmlTreeBuilder tb) {
        tb.insert(startTag);
        tb.tokeniser.transition(TokeniserState.Rawtext);
        tb.markInsertionMode();
        tb.transition(Text);
    }
}
