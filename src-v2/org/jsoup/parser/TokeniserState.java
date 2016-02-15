/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

import org.jsoup.parser.CharacterReader;
import org.jsoup.parser.Token;
import org.jsoup.parser.Tokeniser;

enum TokeniserState {
    Data{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.current()) {
                default: {
                    tokeniser.emit(characterReader.consumeToAny('&', '<', '\u0000'));
                    return;
                }
                case '&': {
                    tokeniser.advanceTransition(CharacterReferenceInData);
                    return;
                }
                case '<': {
                    tokeniser.advanceTransition(TagOpen);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.emit(characterReader.consume());
                    return;
                }
                case '\uffff': 
            }
            tokeniser.emit(new Token.EOF());
        }
    }
    ,
    CharacterReferenceInData{

        /*
         * Enabled aggressive block sorting
         */
        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char[] arrc = tokeniser.consumeCharacterReference(null, false);
            if (arrc == null) {
                tokeniser.emit('&');
            } else {
                tokeniser.emit(arrc);
            }
            tokeniser.transition(Data);
        }
    }
    ,
    Rcdata{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.current()) {
                default: {
                    tokeniser.emit(characterReader.consumeToAny('&', '<', '\u0000'));
                    return;
                }
                case '&': {
                    tokeniser.advanceTransition(CharacterReferenceInRcdata);
                    return;
                }
                case '<': {
                    tokeniser.advanceTransition(RcdataLessthanSign);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    characterReader.advance();
                    tokeniser.emit('\ufffd');
                    return;
                }
                case '\uffff': 
            }
            tokeniser.emit(new Token.EOF());
        }
    }
    ,
    CharacterReferenceInRcdata{

        /*
         * Enabled aggressive block sorting
         */
        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char[] arrc = tokeniser.consumeCharacterReference(null, false);
            if (arrc == null) {
                tokeniser.emit('&');
            } else {
                tokeniser.emit(arrc);
            }
            tokeniser.transition(Rcdata);
        }
    }
    ,
    Rawtext{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.current()) {
                default: {
                    tokeniser.emit(characterReader.consumeToAny('<', '\u0000'));
                    return;
                }
                case '<': {
                    tokeniser.advanceTransition(RawtextLessthanSign);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    characterReader.advance();
                    tokeniser.emit('\ufffd');
                    return;
                }
                case '\uffff': 
            }
            tokeniser.emit(new Token.EOF());
        }
    }
    ,
    ScriptData{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.current()) {
                default: {
                    tokeniser.emit(characterReader.consumeToAny('<', '\u0000'));
                    return;
                }
                case '<': {
                    tokeniser.advanceTransition(ScriptDataLessthanSign);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    characterReader.advance();
                    tokeniser.emit('\ufffd');
                    return;
                }
                case '\uffff': 
            }
            tokeniser.emit(new Token.EOF());
        }
    }
    ,
    PLAINTEXT{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.current()) {
                default: {
                    tokeniser.emit(characterReader.consumeTo('\u0000'));
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    characterReader.advance();
                    tokeniser.emit('\ufffd');
                    return;
                }
                case '\uffff': 
            }
            tokeniser.emit(new Token.EOF());
        }
    }
    ,
    TagOpen{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.current()) {
                default: {
                    if (!characterReader.matchesLetter()) break;
                    tokeniser.createTagPending(true);
                    tokeniser.transition(TagName);
                    return;
                }
                case '!': {
                    tokeniser.advanceTransition(MarkupDeclarationOpen);
                    return;
                }
                case '/': {
                    tokeniser.advanceTransition(EndTagOpen);
                    return;
                }
                case '?': {
                    tokeniser.advanceTransition(BogusComment);
                    return;
                }
            }
            tokeniser.error(this);
            tokeniser.emit('<');
            tokeniser.transition(Data);
        }
    }
    ,
    EndTagOpen{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.emit("</");
                tokeniser.transition(Data);
                return;
            }
            if (characterReader.matchesLetter()) {
                tokeniser.createTagPending(false);
                tokeniser.transition(TagName);
                return;
            }
            if (characterReader.matches('>')) {
                tokeniser.error(this);
                tokeniser.advanceTransition(Data);
                return;
            }
            tokeniser.error(this);
            tokeniser.advanceTransition(BogusComment);
        }
    }
    ,
    TagName{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            String string2 = characterReader.consumeToAny('\t', '\n', '\r', '\f', ' ', '/', '>', '\u0000').toLowerCase();
            tokeniser.tagPending.appendTagName(string2);
            switch (characterReader.consume()) {
                default: {
                    return;
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    tokeniser.transition(BeforeAttributeName);
                    return;
                }
                case '/': {
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                }
                case '>': {
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\u0000': {
                    tokeniser.tagPending.appendTagName(replacementStr);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.transition(Data);
        }
    }
    ,
    RcdataLessthanSign{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('/')) {
                tokeniser.createTempBuffer();
                tokeniser.advanceTransition(RCDATAEndTagOpen);
                return;
            }
            if (characterReader.matchesLetter() && !characterReader.containsIgnoreCase("</" + tokeniser.appropriateEndTagName())) {
                tokeniser.tagPending = new Token.EndTag(tokeniser.appropriateEndTagName());
                tokeniser.emitTagPending();
                characterReader.unconsume();
                tokeniser.transition(Data);
                return;
            }
            tokeniser.emit("<");
            tokeniser.transition(Rcdata);
        }
    }
    ,
    RCDATAEndTagOpen{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesLetter()) {
                tokeniser.createTagPending(false);
                tokeniser.tagPending.appendTagName(Character.toLowerCase(characterReader.current()));
                tokeniser.dataBuffer.append(Character.toLowerCase(characterReader.current()));
                tokeniser.advanceTransition(RCDATAEndTagName);
                return;
            }
            tokeniser.emit("</");
            tokeniser.transition(Rcdata);
        }
    }
    ,
    RCDATAEndTagName{

        private void anythingElse(Tokeniser tokeniser, CharacterReader characterReader) {
            tokeniser.emit("</" + tokeniser.dataBuffer.toString());
            characterReader.unconsume();
            tokeniser.transition(Rcdata);
        }

        @Override
        void read(Tokeniser tokeniser, CharacterReader object) {
            if (object.matchesLetter()) {
                object = object.consumeLetterSequence();
                tokeniser.tagPending.appendTagName(object.toLowerCase());
                tokeniser.dataBuffer.append((String)object);
                return;
            }
            switch (object.consume()) {
                default: {
                    this.anythingElse(tokeniser, (CharacterReader)object);
                    return;
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    if (tokeniser.isAppropriateEndTagToken()) {
                        tokeniser.transition(BeforeAttributeName);
                        return;
                    }
                    this.anythingElse(tokeniser, (CharacterReader)object);
                    return;
                }
                case '/': {
                    if (tokeniser.isAppropriateEndTagToken()) {
                        tokeniser.transition(SelfClosingStartTag);
                        return;
                    }
                    this.anythingElse(tokeniser, (CharacterReader)object);
                    return;
                }
                case '>': 
            }
            if (tokeniser.isAppropriateEndTagToken()) {
                tokeniser.emitTagPending();
                tokeniser.transition(Data);
                return;
            }
            this.anythingElse(tokeniser, (CharacterReader)object);
        }
    }
    ,
    RawtextLessthanSign{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('/')) {
                tokeniser.createTempBuffer();
                tokeniser.advanceTransition(RawtextEndTagOpen);
                return;
            }
            tokeniser.emit('<');
            tokeniser.transition(Rawtext);
        }
    }
    ,
    RawtextEndTagOpen{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesLetter()) {
                tokeniser.createTagPending(false);
                tokeniser.transition(RawtextEndTagName);
                return;
            }
            tokeniser.emit("</");
            tokeniser.transition(Rawtext);
        }
    }
    ,
    RawtextEndTagName{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataEndTag(tokeniser, characterReader, Rawtext);
        }
    }
    ,
    ScriptDataLessthanSign{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                default: {
                    tokeniser.emit("<");
                    characterReader.unconsume();
                    tokeniser.transition(ScriptData);
                    return;
                }
                case '/': {
                    tokeniser.createTempBuffer();
                    tokeniser.transition(ScriptDataEndTagOpen);
                    return;
                }
                case '!': 
            }
            tokeniser.emit("<!");
            tokeniser.transition(ScriptDataEscapeStart);
        }
    }
    ,
    ScriptDataEndTagOpen{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesLetter()) {
                tokeniser.createTagPending(false);
                tokeniser.transition(ScriptDataEndTagName);
                return;
            }
            tokeniser.emit("</");
            tokeniser.transition(ScriptData);
        }
    }
    ,
    ScriptDataEndTagName{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataEndTag(tokeniser, characterReader, ScriptData);
        }
    }
    ,
    ScriptDataEscapeStart{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('-')) {
                tokeniser.emit('-');
                tokeniser.advanceTransition(ScriptDataEscapeStartDash);
                return;
            }
            tokeniser.transition(ScriptData);
        }
    }
    ,
    ScriptDataEscapeStartDash{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('-')) {
                tokeniser.emit('-');
                tokeniser.advanceTransition(ScriptDataEscapedDashDash);
                return;
            }
            tokeniser.transition(ScriptData);
        }
    }
    ,
    ScriptDataEscaped{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
                return;
            }
            switch (characterReader.current()) {
                default: {
                    tokeniser.emit(characterReader.consumeToAny('-', '<', '\u0000'));
                    return;
                }
                case '-': {
                    tokeniser.emit('-');
                    tokeniser.advanceTransition(ScriptDataEscapedDash);
                    return;
                }
                case '<': {
                    tokeniser.advanceTransition(ScriptDataEscapedLessthanSign);
                    return;
                }
                case '\u0000': 
            }
            tokeniser.error(this);
            characterReader.advance();
            tokeniser.emit('\ufffd');
        }
    }
    ,
    ScriptDataEscapedDash{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
                return;
            }
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.emit(c2);
                    tokeniser.transition(ScriptDataEscaped);
                    return;
                }
                case '-': {
                    tokeniser.emit(c2);
                    tokeniser.transition(ScriptDataEscapedDashDash);
                    return;
                }
                case '<': {
                    tokeniser.transition(ScriptDataEscapedLessthanSign);
                    return;
                }
                case '\u0000': 
            }
            tokeniser.error(this);
            tokeniser.emit('\ufffd');
            tokeniser.transition(ScriptDataEscaped);
        }
    }
    ,
    ScriptDataEscapedDashDash{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.transition(Data);
                return;
            }
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.emit(c2);
                    tokeniser.transition(ScriptDataEscaped);
                    return;
                }
                case '-': {
                    tokeniser.emit(c2);
                    return;
                }
                case '<': {
                    tokeniser.transition(ScriptDataEscapedLessthanSign);
                    return;
                }
                case '>': {
                    tokeniser.emit(c2);
                    tokeniser.transition(ScriptData);
                    return;
                }
                case '\u0000': 
            }
            tokeniser.error(this);
            tokeniser.emit('\ufffd');
            tokeniser.transition(ScriptDataEscaped);
        }
    }
    ,
    ScriptDataEscapedLessthanSign{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesLetter()) {
                tokeniser.createTempBuffer();
                tokeniser.dataBuffer.append(Character.toLowerCase(characterReader.current()));
                tokeniser.emit("<" + characterReader.current());
                tokeniser.advanceTransition(ScriptDataDoubleEscapeStart);
                return;
            }
            if (characterReader.matches('/')) {
                tokeniser.createTempBuffer();
                tokeniser.advanceTransition(ScriptDataEscapedEndTagOpen);
                return;
            }
            tokeniser.emit('<');
            tokeniser.transition(ScriptDataEscaped);
        }
    }
    ,
    ScriptDataEscapedEndTagOpen{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesLetter()) {
                tokeniser.createTagPending(false);
                tokeniser.tagPending.appendTagName(Character.toLowerCase(characterReader.current()));
                tokeniser.dataBuffer.append(characterReader.current());
                tokeniser.advanceTransition(ScriptDataEscapedEndTagName);
                return;
            }
            tokeniser.emit("</");
            tokeniser.transition(ScriptDataEscaped);
        }
    }
    ,
    ScriptDataEscapedEndTagName{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataEndTag(tokeniser, characterReader, ScriptDataEscaped);
        }
    }
    ,
    ScriptDataDoubleEscapeStart{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataDoubleEscapeTag(tokeniser, characterReader, ScriptDataDoubleEscaped, ScriptDataEscaped);
        }
    }
    ,
    ScriptDataDoubleEscaped{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.current();
            switch (c2) {
                default: {
                    tokeniser.emit(characterReader.consumeToAny('-', '<', '\u0000'));
                    return;
                }
                case '-': {
                    tokeniser.emit(c2);
                    tokeniser.advanceTransition(ScriptDataDoubleEscapedDash);
                    return;
                }
                case '<': {
                    tokeniser.emit(c2);
                    tokeniser.advanceTransition(ScriptDataDoubleEscapedLessthanSign);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    characterReader.advance();
                    tokeniser.emit('\ufffd');
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.transition(Data);
        }
    }
    ,
    ScriptDataDoubleEscapedDash{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.emit(c2);
                    tokeniser.transition(ScriptDataDoubleEscaped);
                    return;
                }
                case '-': {
                    tokeniser.emit(c2);
                    tokeniser.transition(ScriptDataDoubleEscapedDashDash);
                    return;
                }
                case '<': {
                    tokeniser.emit(c2);
                    tokeniser.transition(ScriptDataDoubleEscapedLessthanSign);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.emit('\ufffd');
                    tokeniser.transition(ScriptDataDoubleEscaped);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.transition(Data);
        }
    }
    ,
    ScriptDataDoubleEscapedDashDash{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.emit(c2);
                    tokeniser.transition(ScriptDataDoubleEscaped);
                    return;
                }
                case '-': {
                    tokeniser.emit(c2);
                    return;
                }
                case '<': {
                    tokeniser.emit(c2);
                    tokeniser.transition(ScriptDataDoubleEscapedLessthanSign);
                    return;
                }
                case '>': {
                    tokeniser.emit(c2);
                    tokeniser.transition(ScriptData);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.emit('\ufffd');
                    tokeniser.transition(ScriptDataDoubleEscaped);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.transition(Data);
        }
    }
    ,
    ScriptDataDoubleEscapedLessthanSign{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matches('/')) {
                tokeniser.emit('/');
                tokeniser.createTempBuffer();
                tokeniser.advanceTransition(ScriptDataDoubleEscapeEnd);
                return;
            }
            tokeniser.transition(ScriptDataDoubleEscaped);
        }
    }
    ,
    ScriptDataDoubleEscapeEnd{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            TokeniserState.handleDataDoubleEscapeTag(tokeniser, characterReader, ScriptDataEscaped, ScriptDataDoubleEscaped);
        }
    }
    ,
    BeforeAttributeName{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.tagPending.newAttribute();
                    characterReader.unconsume();
                    tokeniser.transition(AttributeName);
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    return;
                }
                case '/': {
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                }
                case '>': {
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.tagPending.newAttribute();
                    characterReader.unconsume();
                    tokeniser.transition(AttributeName);
                    return;
                }
                case '\uffff': {
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                }
                case '\"': 
                case '\'': 
                case '<': 
                case '=': 
            }
            tokeniser.error(this);
            tokeniser.tagPending.newAttribute();
            tokeniser.tagPending.appendAttributeName(c2);
            tokeniser.transition(AttributeName);
        }
    }
    ,
    AttributeName{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            String string2 = characterReader.consumeToAny('\t', '\n', '\r', '\f', ' ', '/', '=', '>', '\u0000', '\"', '\'', '<');
            tokeniser.tagPending.appendAttributeName(string2.toLowerCase());
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    return;
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    tokeniser.transition(AfterAttributeName);
                    return;
                }
                case '/': {
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                }
                case '=': {
                    tokeniser.transition(BeforeAttributeValue);
                    return;
                }
                case '>': {
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeName('\ufffd');
                    return;
                }
                case '\uffff': {
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                }
                case '\"': 
                case '\'': 
                case '<': 
            }
            tokeniser.error(this);
            tokeniser.tagPending.appendAttributeName(c2);
        }
    }
    ,
    AfterAttributeName{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.tagPending.newAttribute();
                    characterReader.unconsume();
                    tokeniser.transition(AttributeName);
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    return;
                }
                case '/': {
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                }
                case '=': {
                    tokeniser.transition(BeforeAttributeValue);
                    return;
                }
                case '>': {
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeName('\ufffd');
                    tokeniser.transition(AttributeName);
                    return;
                }
                case '\uffff': {
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                }
                case '\"': 
                case '\'': 
                case '<': 
            }
            tokeniser.error(this);
            tokeniser.tagPending.newAttribute();
            tokeniser.tagPending.appendAttributeName(c2);
            tokeniser.transition(AttributeName);
        }
    }
    ,
    BeforeAttributeValue{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    characterReader.unconsume();
                    tokeniser.transition(AttributeValue_unquoted);
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    return;
                }
                case '\"': {
                    tokeniser.transition(AttributeValue_doubleQuoted);
                    return;
                }
                case '&': {
                    characterReader.unconsume();
                    tokeniser.transition(AttributeValue_unquoted);
                    return;
                }
                case '\'': {
                    tokeniser.transition(AttributeValue_singleQuoted);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeValue('\ufffd');
                    tokeniser.transition(AttributeValue_unquoted);
                    return;
                }
                case '\uffff': {
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                }
                case '>': {
                    tokeniser.error(this);
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                }
                case '<': 
                case '=': 
                case '`': 
            }
            tokeniser.error(this);
            tokeniser.tagPending.appendAttributeValue(c2);
            tokeniser.transition(AttributeValue_unquoted);
        }
    }
    ,
    AttributeValue_doubleQuoted{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            String string2 = characterReader.consumeToAny('\"', '&', '\u0000');
            if (string2.length() > 0) {
                tokeniser.tagPending.appendAttributeValue(string2);
            }
            switch (characterReader.consume()) {
                default: {
                    return;
                }
                case '\"': {
                    tokeniser.transition(AfterAttributeValue_quoted);
                    return;
                }
                case '&': {
                    characterReader = (CharacterReader)tokeniser.consumeCharacterReference(Character.valueOf('\"'), true);
                    if (characterReader != null) {
                        tokeniser.tagPending.appendAttributeValue((char[])characterReader);
                        return;
                    }
                    tokeniser.tagPending.appendAttributeValue('&');
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeValue('\ufffd');
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.transition(Data);
        }
    }
    ,
    AttributeValue_singleQuoted{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            String string2 = characterReader.consumeToAny('\'', '&', '\u0000');
            if (string2.length() > 0) {
                tokeniser.tagPending.appendAttributeValue(string2);
            }
            switch (characterReader.consume()) {
                default: {
                    return;
                }
                case '\'': {
                    tokeniser.transition(AfterAttributeValue_quoted);
                    return;
                }
                case '&': {
                    characterReader = (CharacterReader)tokeniser.consumeCharacterReference(Character.valueOf('\''), true);
                    if (characterReader != null) {
                        tokeniser.tagPending.appendAttributeValue((char[])characterReader);
                        return;
                    }
                    tokeniser.tagPending.appendAttributeValue('&');
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeValue('\ufffd');
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.transition(Data);
        }
    }
    ,
    AttributeValue_unquoted{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            String string2 = characterReader.consumeToAny('\t', '\n', '\r', '\f', ' ', '&', '>', '\u0000', '\"', '\'', '<', '=', '`');
            if (string2.length() > 0) {
                tokeniser.tagPending.appendAttributeValue(string2);
            }
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    return;
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    tokeniser.transition(BeforeAttributeName);
                    return;
                }
                case '&': {
                    characterReader = (CharacterReader)tokeniser.consumeCharacterReference(Character.valueOf('>'), true);
                    if (characterReader != null) {
                        tokeniser.tagPending.appendAttributeValue((char[])characterReader);
                        return;
                    }
                    tokeniser.tagPending.appendAttributeValue('&');
                    return;
                }
                case '>': {
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.tagPending.appendAttributeValue('\ufffd');
                    return;
                }
                case '\uffff': {
                    tokeniser.eofError(this);
                    tokeniser.transition(Data);
                    return;
                }
                case '\"': 
                case '\'': 
                case '<': 
                case '=': 
                case '`': 
            }
            tokeniser.error(this);
            tokeniser.tagPending.appendAttributeValue(c2);
        }
    }
    ,
    AfterAttributeValue_quoted{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                default: {
                    tokeniser.error(this);
                    characterReader.unconsume();
                    tokeniser.transition(BeforeAttributeName);
                    return;
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    tokeniser.transition(BeforeAttributeName);
                    return;
                }
                case '/': {
                    tokeniser.transition(SelfClosingStartTag);
                    return;
                }
                case '>': {
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.transition(Data);
        }
    }
    ,
    SelfClosingStartTag{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                default: {
                    tokeniser.error(this);
                    tokeniser.transition(BeforeAttributeName);
                    return;
                }
                case '>': {
                    tokeniser.tagPending.selfClosing = true;
                    tokeniser.emitTagPending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.transition(Data);
        }
    }
    ,
    BogusComment{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            characterReader.unconsume();
            Token.Comment comment = new Token.Comment();
            comment.bogus = true;
            comment.data.append(characterReader.consumeTo('>'));
            tokeniser.emit(comment);
            tokeniser.advanceTransition(Data);
        }
    }
    ,
    MarkupDeclarationOpen{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchConsume("--")) {
                tokeniser.createCommentPending();
                tokeniser.transition(CommentStart);
                return;
            }
            if (characterReader.matchConsumeIgnoreCase("DOCTYPE")) {
                tokeniser.transition(Doctype);
                return;
            }
            if (characterReader.matchConsume("[CDATA[")) {
                tokeniser.transition(CdataSection);
                return;
            }
            tokeniser.error(this);
            tokeniser.advanceTransition(BogusComment);
        }
    }
    ,
    CommentStart{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.commentPending.data.append(c2);
                    tokeniser.transition(Comment);
                    return;
                }
                case '-': {
                    tokeniser.transition(CommentStartDash);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.commentPending.data.append('\ufffd');
                    tokeniser.transition(Comment);
                    return;
                }
                case '>': {
                    tokeniser.error(this);
                    tokeniser.emitCommentPending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.emitCommentPending();
            tokeniser.transition(Data);
        }
    }
    ,
    CommentStartDash{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.commentPending.data.append(c2);
                    tokeniser.transition(Comment);
                    return;
                }
                case '-': {
                    tokeniser.transition(CommentStartDash);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.commentPending.data.append('\ufffd');
                    tokeniser.transition(Comment);
                    return;
                }
                case '>': {
                    tokeniser.error(this);
                    tokeniser.emitCommentPending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.emitCommentPending();
            tokeniser.transition(Data);
        }
    }
    ,
    Comment{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.current()) {
                default: {
                    tokeniser.commentPending.data.append(characterReader.consumeToAny('-', '\u0000'));
                    return;
                }
                case '-': {
                    tokeniser.advanceTransition(CommentEndDash);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    characterReader.advance();
                    tokeniser.commentPending.data.append('\ufffd');
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.emitCommentPending();
            tokeniser.transition(Data);
        }
    }
    ,
    CommentEndDash{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.commentPending.data.append('-').append(c2);
                    tokeniser.transition(Comment);
                    return;
                }
                case '-': {
                    tokeniser.transition(CommentEnd);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.commentPending.data.append('-').append('\ufffd');
                    tokeniser.transition(Comment);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.emitCommentPending();
            tokeniser.transition(Data);
        }
    }
    ,
    CommentEnd{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.error(this);
                    tokeniser.commentPending.data.append("--").append(c2);
                    tokeniser.transition(Comment);
                    return;
                }
                case '>': {
                    tokeniser.emitCommentPending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.commentPending.data.append("--").append('\ufffd');
                    tokeniser.transition(Comment);
                    return;
                }
                case '!': {
                    tokeniser.error(this);
                    tokeniser.transition(CommentEndBang);
                    return;
                }
                case '-': {
                    tokeniser.error(this);
                    tokeniser.commentPending.data.append('-');
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.emitCommentPending();
            tokeniser.transition(Data);
        }
    }
    ,
    CommentEndBang{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.commentPending.data.append("--!").append(c2);
                    tokeniser.transition(Comment);
                    return;
                }
                case '-': {
                    tokeniser.commentPending.data.append("--!");
                    tokeniser.transition(CommentEndDash);
                    return;
                }
                case '>': {
                    tokeniser.emitCommentPending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.commentPending.data.append("--!").append('\ufffd');
                    tokeniser.transition(Comment);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.emitCommentPending();
            tokeniser.transition(Data);
        }
    }
    ,
    Doctype{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                default: {
                    tokeniser.error(this);
                    tokeniser.transition(BeforeDoctypeName);
                    return;
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    tokeniser.transition(BeforeDoctypeName);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.createDoctypePending();
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    BeforeDoctypeName{

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.matchesLetter()) {
                tokeniser.createDoctypePending();
                tokeniser.transition(DoctypeName);
                do {
                    return;
                    break;
                } while (true);
            }
            char c2 = characterReader.consume();
            switch (c2) {
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    return;
                }
                default: {
                    tokeniser.createDoctypePending();
                    tokeniser.doctypePending.name.append(c2);
                    tokeniser.transition(DoctypeName);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.name.append('\ufffd');
                    tokeniser.transition(DoctypeName);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.createDoctypePending();
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    DoctypeName{

        @Override
        void read(Tokeniser tokeniser, CharacterReader object) {
            if (object.matchesLetter()) {
                object = object.consumeLetterSequence();
                tokeniser.doctypePending.name.append(object.toLowerCase());
                return;
            }
            char c2 = object.consume();
            switch (c2) {
                default: {
                    tokeniser.doctypePending.name.append(c2);
                    return;
                }
                case '>': {
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    tokeniser.transition(AfterDoctypeName);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.name.append('\ufffd');
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    AfterDoctypeName{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            if (characterReader.isEmpty()) {
                tokeniser.eofError(this);
                tokeniser.doctypePending.forceQuirks = true;
                tokeniser.emitDoctypePending();
                tokeniser.transition(Data);
                return;
            }
            if (characterReader.matchesAny('\t', '\n', '\r', '\f', ' ')) {
                characterReader.advance();
                return;
            }
            if (characterReader.matches('>')) {
                tokeniser.emitDoctypePending();
                tokeniser.advanceTransition(Data);
                return;
            }
            if (characterReader.matchConsumeIgnoreCase("PUBLIC")) {
                tokeniser.transition(AfterDoctypePublicKeyword);
                return;
            }
            if (characterReader.matchConsumeIgnoreCase("SYSTEM")) {
                tokeniser.transition(AfterDoctypeSystemKeyword);
                return;
            }
            tokeniser.error(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.advanceTransition(BogusDoctype);
        }
    }
    ,
    AfterDoctypePublicKeyword{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                default: {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.transition(BogusDoctype);
                    return;
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    tokeniser.transition(BeforeDoctypePublicIdentifier);
                    return;
                }
                case '\"': {
                    tokeniser.error(this);
                    tokeniser.transition(DoctypePublicIdentifier_doubleQuoted);
                    return;
                }
                case '\'': {
                    tokeniser.error(this);
                    tokeniser.transition(DoctypePublicIdentifier_singleQuoted);
                    return;
                }
                case '>': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    BeforeDoctypePublicIdentifier{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                default: {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.transition(BogusDoctype);
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    return;
                }
                case '\"': {
                    tokeniser.transition(DoctypePublicIdentifier_doubleQuoted);
                    return;
                }
                case '\'': {
                    tokeniser.transition(DoctypePublicIdentifier_singleQuoted);
                    return;
                }
                case '>': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    DoctypePublicIdentifier_doubleQuoted{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.doctypePending.publicIdentifier.append(c2);
                    return;
                }
                case '\"': {
                    tokeniser.transition(AfterDoctypePublicIdentifier);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.publicIdentifier.append('\ufffd');
                    return;
                }
                case '>': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    DoctypePublicIdentifier_singleQuoted{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.doctypePending.publicIdentifier.append(c2);
                    return;
                }
                case '\'': {
                    tokeniser.transition(AfterDoctypePublicIdentifier);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.publicIdentifier.append('\ufffd');
                    return;
                }
                case '>': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    AfterDoctypePublicIdentifier{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                default: {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.transition(BogusDoctype);
                    return;
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    tokeniser.transition(BetweenDoctypePublicAndSystemIdentifiers);
                    return;
                }
                case '>': {
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\"': {
                    tokeniser.error(this);
                    tokeniser.transition(DoctypeSystemIdentifier_doubleQuoted);
                    return;
                }
                case '\'': {
                    tokeniser.error(this);
                    tokeniser.transition(DoctypeSystemIdentifier_singleQuoted);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    BetweenDoctypePublicAndSystemIdentifiers{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                default: {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.transition(BogusDoctype);
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    return;
                }
                case '>': {
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\"': {
                    tokeniser.error(this);
                    tokeniser.transition(DoctypeSystemIdentifier_doubleQuoted);
                    return;
                }
                case '\'': {
                    tokeniser.error(this);
                    tokeniser.transition(DoctypeSystemIdentifier_singleQuoted);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    AfterDoctypeSystemKeyword{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                default: {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    return;
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    tokeniser.transition(BeforeDoctypeSystemIdentifier);
                    return;
                }
                case '>': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\"': {
                    tokeniser.error(this);
                    tokeniser.transition(DoctypeSystemIdentifier_doubleQuoted);
                    return;
                }
                case '\'': {
                    tokeniser.error(this);
                    tokeniser.transition(DoctypeSystemIdentifier_singleQuoted);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    BeforeDoctypeSystemIdentifier{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                default: {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.transition(BogusDoctype);
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    return;
                }
                case '\"': {
                    tokeniser.transition(DoctypeSystemIdentifier_doubleQuoted);
                    return;
                }
                case '\'': {
                    tokeniser.transition(DoctypeSystemIdentifier_singleQuoted);
                    return;
                }
                case '>': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    DoctypeSystemIdentifier_doubleQuoted{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.doctypePending.systemIdentifier.append(c2);
                    return;
                }
                case '\"': {
                    tokeniser.transition(AfterDoctypeSystemIdentifier);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.systemIdentifier.append('\ufffd');
                    return;
                }
                case '>': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    DoctypeSystemIdentifier_singleQuoted{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            char c2 = characterReader.consume();
            switch (c2) {
                default: {
                    tokeniser.doctypePending.systemIdentifier.append(c2);
                    return;
                }
                case '\'': {
                    tokeniser.transition(AfterDoctypeSystemIdentifier);
                    return;
                }
                case '\u0000': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.systemIdentifier.append('\ufffd');
                    return;
                }
                case '>': {
                    tokeniser.error(this);
                    tokeniser.doctypePending.forceQuirks = true;
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    AfterDoctypeSystemIdentifier{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                default: {
                    tokeniser.error(this);
                    tokeniser.transition(BogusDoctype);
                }
                case '\t': 
                case '\n': 
                case '\f': 
                case '\r': 
                case ' ': {
                    return;
                }
                case '>': {
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.eofError(this);
            tokeniser.doctypePending.forceQuirks = true;
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    BogusDoctype{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            switch (characterReader.consume()) {
                default: {
                    return;
                }
                case '>': {
                    tokeniser.emitDoctypePending();
                    tokeniser.transition(Data);
                    return;
                }
                case '\uffff': 
            }
            tokeniser.emitDoctypePending();
            tokeniser.transition(Data);
        }
    }
    ,
    CdataSection{

        @Override
        void read(Tokeniser tokeniser, CharacterReader characterReader) {
            tokeniser.emit(characterReader.consumeTo("]]>"));
            characterReader.matchConsume("]]>");
            tokeniser.transition(Data);
        }
    };
    
    private static final char eof = '\uffff';
    private static final char nullChar = '\u0000';
    private static final char replacementChar = '\ufffd';
    private static final String replacementStr;

    static {
        replacementStr = String.valueOf('\ufffd');
    }

    private TokeniserState() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final void handleDataDoubleEscapeTag(Tokeniser tokeniser, CharacterReader object, TokeniserState tokeniserState, TokeniserState tokeniserState2) {
        if (object.matchesLetter()) {
            object = object.consumeLetterSequence();
            tokeniser.dataBuffer.append(object.toLowerCase());
            tokeniser.emit((String)object);
            return;
        }
        char c2 = object.consume();
        switch (c2) {
            default: {
                object.unconsume();
                tokeniser.transition(tokeniserState2);
                return;
            }
            case '\t': 
            case '\n': 
            case '\f': 
            case '\r': 
            case ' ': 
            case '/': 
            case '>': 
        }
        if (tokeniser.dataBuffer.toString().equals("script")) {
            tokeniser.transition(tokeniserState);
        } else {
            tokeniser.transition(tokeniserState2);
        }
        tokeniser.emit(c2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static final void handleDataEndTag(Tokeniser var0, CharacterReader var1_1, TokeniserState var2_2) {
        if (var1_1.matchesLetter()) {
            var1_1 = var1_1.consumeLetterSequence();
            var0.tagPending.appendTagName(var1_1.toLowerCase());
            var0.dataBuffer.append((String)var1_1);
            return;
        }
        var4_3 = false;
        if (!var0.isAppropriateEndTagToken() || var1_1.isEmpty()) ** GOTO lbl24
        var3_4 = var1_1.consume();
        switch (var3_4) {
            default: {
                var0.dataBuffer.append(var3_4);
                var4_3 = true;
                ** GOTO lbl25
            }
            case '\t': 
            case '\n': 
            case '\f': 
            case '\r': 
            case ' ': {
                var0.transition(TokeniserState.BeforeAttributeName);
                ** GOTO lbl25
            }
            case '/': {
                var0.transition(TokeniserState.SelfClosingStartTag);
                ** GOTO lbl25
            }
            case '>': 
        }
        var0.emitTagPending();
        var0.transition(TokeniserState.Data);
        ** GOTO lbl25
lbl24: // 1 sources:
        var4_3 = true;
lbl25: // 5 sources:
        if (var4_3 == false) return;
        var0.emit("</" + var0.dataBuffer.toString());
        var0.transition(var2_2);
    }

    abstract void read(Tokeniser var1, CharacterReader var2);

}

