/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.ParseError;
import org.jsoup.parser.ParseErrorList;
import org.jsoup.parser.Token;
import org.jsoup.parser.TokeniserState;

class Tokeniser {
    static final char replacementChar = '\ufffd';
    private StringBuilder charBuffer = new StringBuilder();
    Token.Comment commentPending;
    StringBuilder dataBuffer;
    Token.Doctype doctypePending;
    private Token emitPending;
    private ParseErrorList errors;
    private boolean isEmitPending = false;
    private Token.StartTag lastStartTag;
    private CharacterReader reader;
    private boolean selfClosingFlagAcknowledged = true;
    private TokeniserState state = TokeniserState.Data;
    Token.Tag tagPending;

    Tokeniser(CharacterReader characterReader, ParseErrorList parseErrorList) {
        this.reader = characterReader;
        this.errors = parseErrorList;
    }

    private void characterReferenceError(String string2) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Invalid character reference: %s", string2));
        }
    }

    private void error(String string2) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), string2));
        }
    }

    void acknowledgeSelfClosingFlag() {
        this.selfClosingFlagAcknowledged = true;
    }

    void advanceTransition(TokeniserState tokeniserState) {
        this.reader.advance();
        this.state = tokeniserState;
    }

    String appropriateEndTagName() {
        return this.lastStartTag.tagName;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    char[] consumeCharacterReference(Character object, boolean bl2) {
        int n2;
        int n3;
        if (this.reader.isEmpty()) {
            return null;
        }
        if (object != null) {
            if (object.charValue() == this.reader.current()) return null;
        }
        if (this.reader.matchesAny('\t', '\n', '\r', '\f', ' ', '<', '&')) return null;
        this.reader.mark();
        if (this.reader.matchConsume("#")) {
            bl2 = this.reader.matchConsumeIgnoreCase("X");
            object = bl2 ? this.reader.consumeHexSequence() : this.reader.consumeDigitSequence();
            if (object.length() == 0) {
                this.characterReferenceError("numeric reference with no numerals");
                this.reader.rewindToMark();
                return null;
            }
            if (!this.reader.matchConsume(";")) {
                this.characterReferenceError("missing semicolon");
            }
            n2 = -1;
            n3 = bl2 ? 16 : 10;
            n3 = Integer.valueOf((String)object, n3);
        } else {
            object = this.reader.consumeLetterThenDigitSequence();
            boolean bl3 = this.reader.matches(';');
            boolean bl4 = Entities.isBaseNamedEntity((String)object) || Entities.isNamedEntity((String)object) && bl3;
            if (!bl4) {
                this.reader.rewindToMark();
                if (!bl3) return null;
                this.characterReferenceError(String.format("invalid named referenece '%s'", object));
                return null;
            }
            if (bl2 && (this.reader.matchesLetter() || this.reader.matchesDigit() || this.reader.matchesAny('=', '-', '_'))) {
                this.reader.rewindToMark();
                return null;
            }
            if (this.reader.matchConsume(";")) return new char[]{Entities.getCharacterByName((String)object).charValue()};
            this.characterReferenceError("missing semicolon");
            return new char[]{Entities.getCharacterByName((String)object).charValue()};
            catch (NumberFormatException numberFormatException) {
                n3 = n2;
            }
        }
        if (n3 != -1 && (n3 < 55296 || n3 > 57343)) {
            if (n3 <= 1114111) return Character.toChars(n3);
        }
        this.characterReferenceError("character outside of valid range");
        return new char[]{'\ufffd'};
    }

    void createCommentPending() {
        this.commentPending = new Token.Comment();
    }

    void createDoctypePending() {
        this.doctypePending = new Token.Doctype();
    }

    /*
     * Enabled aggressive block sorting
     */
    Token.Tag createTagPending(boolean bl2) {
        void var2_3;
        if (bl2) {
            Token.StartTag startTag = new Token.StartTag();
        } else {
            Token.EndTag endTag = new Token.EndTag();
        }
        this.tagPending = var2_3;
        return this.tagPending;
    }

    void createTempBuffer() {
        this.dataBuffer = new StringBuilder();
    }

    boolean currentNodeInHtmlNS() {
        return true;
    }

    void emit(char c2) {
        this.charBuffer.append(c2);
    }

    void emit(String string2) {
        this.charBuffer.append(string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    void emit(Token token) {
        Validate.isFalse(this.isEmitPending, "There is an unread token pending!");
        this.emitPending = token;
        this.isEmitPending = true;
        if (token.type == Token.TokenType.StartTag) {
            this.lastStartTag = token = (Token.StartTag)token;
            if (!token.selfClosing) return;
            {
                this.selfClosingFlagAcknowledged = false;
                return;
            }
        } else {
            if (token.type != Token.TokenType.EndTag || ((Token.EndTag)token).attributes == null) return;
            {
                this.error("Attributes incorrectly present on end tag");
                return;
            }
        }
    }

    void emit(char[] arrc) {
        this.charBuffer.append(arrc);
    }

    void emitCommentPending() {
        this.emit(this.commentPending);
    }

    void emitDoctypePending() {
        this.emit(this.doctypePending);
    }

    void emitTagPending() {
        this.tagPending.finaliseTag();
        this.emit(this.tagPending);
    }

    void eofError(TokeniserState tokeniserState) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Unexpectedly reached end of file (EOF) in input state [%s]", new Object[]{tokeniserState}));
        }
    }

    void error(TokeniserState tokeniserState) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Unexpected character '%s' in input state [%s]", new Object[]{Character.valueOf(this.reader.current()), tokeniserState}));
        }
    }

    TokeniserState getState() {
        return this.state;
    }

    boolean isAppropriateEndTagToken() {
        if (this.lastStartTag == null) {
            return false;
        }
        return this.tagPending.tagName.equals(this.lastStartTag.tagName);
    }

    Token read() {
        if (!this.selfClosingFlagAcknowledged) {
            this.error("Self closing flag not acknowledged");
            this.selfClosingFlagAcknowledged = true;
        }
        while (!this.isEmitPending) {
            this.state.read(this, this.reader);
        }
        if (this.charBuffer.length() > 0) {
            String string2 = this.charBuffer.toString();
            this.charBuffer.delete(0, this.charBuffer.length());
            return new Token.Character(string2);
        }
        this.isEmitPending = false;
        return this.emitPending;
    }

    void transition(TokeniserState tokeniserState) {
        this.state = tokeniserState;
    }

    String unescapeEntities(boolean bl2) {
        StringBuilder stringBuilder = new StringBuilder();
        while (!this.reader.isEmpty()) {
            stringBuilder.append(this.reader.consumeTo('&'));
            if (!this.reader.matches('&')) continue;
            this.reader.consume();
            char[] arrc = this.consumeCharacterReference(null, bl2);
            if (arrc == null || arrc.length == 0) {
                stringBuilder.append('&');
                continue;
            }
            stringBuilder.append(arrc);
        }
        return stringBuilder.toString();
    }
}

