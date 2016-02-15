/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;

abstract class Token {
    TokenType type;

    private Token() {
    }

    Character asCharacter() {
        return (Character)this;
    }

    Comment asComment() {
        return (Comment)this;
    }

    Doctype asDoctype() {
        return (Doctype)this;
    }

    EndTag asEndTag() {
        return (EndTag)this;
    }

    StartTag asStartTag() {
        return (StartTag)this;
    }

    boolean isCharacter() {
        if (this.type == TokenType.Character) {
            return true;
        }
        return false;
    }

    boolean isComment() {
        if (this.type == TokenType.Comment) {
            return true;
        }
        return false;
    }

    boolean isDoctype() {
        if (this.type == TokenType.Doctype) {
            return true;
        }
        return false;
    }

    boolean isEOF() {
        if (this.type == TokenType.EOF) {
            return true;
        }
        return false;
    }

    boolean isEndTag() {
        if (this.type == TokenType.EndTag) {
            return true;
        }
        return false;
    }

    boolean isStartTag() {
        if (this.type == TokenType.StartTag) {
            return true;
        }
        return false;
    }

    String tokenType() {
        return this.getClass().getSimpleName();
    }

    static class Character
    extends Token {
        private final String data;

        Character(String string2) {
            super();
            this.type = TokenType.Character;
            this.data = string2;
        }

        String getData() {
            return this.data;
        }

        public String toString() {
            return this.getData();
        }
    }

    static class Comment
    extends Token {
        boolean bogus = false;
        final StringBuilder data = new StringBuilder();

        Comment() {
            super();
            this.type = TokenType.Comment;
        }

        String getData() {
            return this.data.toString();
        }

        public String toString() {
            return "<!--" + this.getData() + "-->";
        }
    }

    static class Doctype
    extends Token {
        boolean forceQuirks = false;
        final StringBuilder name = new StringBuilder();
        final StringBuilder publicIdentifier = new StringBuilder();
        final StringBuilder systemIdentifier = new StringBuilder();

        Doctype() {
            super();
            this.type = TokenType.Doctype;
        }

        String getName() {
            return this.name.toString();
        }

        String getPublicIdentifier() {
            return this.publicIdentifier.toString();
        }

        public String getSystemIdentifier() {
            return this.systemIdentifier.toString();
        }

        public boolean isForceQuirks() {
            return this.forceQuirks;
        }
    }

    static class EOF
    extends Token {
        EOF() {
            super();
            this.type = TokenType.EOF;
        }
    }

    static class EndTag
    extends Tag {
        EndTag() {
            this.type = TokenType.EndTag;
        }

        EndTag(String string2) {
            this();
            this.tagName = string2;
        }

        public String toString() {
            return "</" + this.name() + ">";
        }
    }

    static class StartTag
    extends Tag {
        StartTag() {
            this.attributes = new Attributes();
            this.type = TokenType.StartTag;
        }

        StartTag(String string2) {
            this();
            this.tagName = string2;
        }

        StartTag(String string2, Attributes attributes) {
            this();
            this.tagName = string2;
            this.attributes = attributes;
        }

        public String toString() {
            if (this.attributes != null && this.attributes.size() > 0) {
                return "<" + this.name() + " " + this.attributes.toString() + ">";
            }
            return "<" + this.name() + ">";
        }
    }

    static abstract class Tag
    extends Token {
        Attributes attributes;
        private String pendingAttributeName;
        private StringBuilder pendingAttributeValue;
        boolean selfClosing = false;
        protected String tagName;

        Tag() {
            super();
        }

        private final void ensureAttributeValue() {
            if (this.pendingAttributeValue == null) {
                this.pendingAttributeValue = new StringBuilder();
            }
        }

        void appendAttributeName(char c2) {
            this.appendAttributeName(String.valueOf(c2));
        }

        /*
         * Enabled aggressive block sorting
         */
        void appendAttributeName(String string2) {
            if (this.pendingAttributeName != null) {
                string2 = this.pendingAttributeName.concat(string2);
            }
            this.pendingAttributeName = string2;
        }

        void appendAttributeValue(char c2) {
            this.ensureAttributeValue();
            this.pendingAttributeValue.append(c2);
        }

        void appendAttributeValue(String string2) {
            this.ensureAttributeValue();
            this.pendingAttributeValue.append(string2);
        }

        void appendAttributeValue(char[] arrc) {
            this.ensureAttributeValue();
            this.pendingAttributeValue.append(arrc);
        }

        void appendTagName(char c2) {
            this.appendTagName(String.valueOf(c2));
        }

        /*
         * Enabled aggressive block sorting
         */
        void appendTagName(String string2) {
            if (this.tagName != null) {
                string2 = this.tagName.concat(string2);
            }
            this.tagName = string2;
        }

        void finaliseTag() {
            if (this.pendingAttributeName != null) {
                this.newAttribute();
            }
        }

        Attributes getAttributes() {
            return this.attributes;
        }

        boolean isSelfClosing() {
            return this.selfClosing;
        }

        /*
         * Enabled aggressive block sorting
         */
        String name() {
            boolean bl2 = this.tagName.length() == 0;
            Validate.isFalse(bl2);
            return this.tagName;
        }

        Tag name(String string2) {
            this.tagName = string2;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        void newAttribute() {
            if (this.attributes == null) {
                this.attributes = new Attributes();
            }
            if (this.pendingAttributeName != null) {
                Attribute attribute = this.pendingAttributeValue == null ? new Attribute(this.pendingAttributeName, "") : new Attribute(this.pendingAttributeName, this.pendingAttributeValue.toString());
                this.attributes.put(attribute);
            }
            this.pendingAttributeName = null;
            if (this.pendingAttributeValue != null) {
                this.pendingAttributeValue.delete(0, this.pendingAttributeValue.length());
            }
        }
    }

    static enum TokenType {
        Doctype,
        StartTag,
        EndTag,
        Comment,
        Character,
        EOF;
        

        private TokenType() {
        }
    }

}

