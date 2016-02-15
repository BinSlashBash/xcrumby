/*
 * Decompiled with CFR 0_110.
 */
package org.jsoup.select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.parser.TokenQueue;
import org.jsoup.select.CombiningEvaluator;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;
import org.jsoup.select.StructuralEvaluator;

class QueryParser {
    private static final String[] AttributeEvals;
    private static final Pattern NTH_AB;
    private static final Pattern NTH_B;
    private static final String[] combinators;
    private List<Evaluator> evals = new ArrayList<Evaluator>();
    private String query;
    private TokenQueue tq;

    static {
        combinators = new String[]{",", ">", "+", "~", " "};
        AttributeEvals = new String[]{"=", "!=", "^=", "$=", "*=", "~="};
        NTH_AB = Pattern.compile("((\\+|-)?(\\d+)?)n(\\s*(\\+|-)?\\s*\\d+)?", 2);
        NTH_B = Pattern.compile("(\\+|-)?(\\d+)");
    }

    private QueryParser(String string2) {
        this.query = string2;
        this.tq = new TokenQueue(string2);
    }

    private void allElements() {
        this.evals.add(new Evaluator.AllElements());
    }

    private void byAttribute() {
        TokenQueue tokenQueue = new TokenQueue(this.tq.chompBalanced('[', ']'));
        String string2 = tokenQueue.consumeToAny(AttributeEvals);
        Validate.notEmpty(string2);
        tokenQueue.consumeWhitespace();
        if (tokenQueue.isEmpty()) {
            if (string2.startsWith("^")) {
                this.evals.add(new Evaluator.AttributeStarting(string2.substring(1)));
                return;
            }
            this.evals.add(new Evaluator.Attribute(string2));
            return;
        }
        if (tokenQueue.matchChomp("=")) {
            this.evals.add(new Evaluator.AttributeWithValue(string2, tokenQueue.remainder()));
            return;
        }
        if (tokenQueue.matchChomp("!=")) {
            this.evals.add(new Evaluator.AttributeWithValueNot(string2, tokenQueue.remainder()));
            return;
        }
        if (tokenQueue.matchChomp("^=")) {
            this.evals.add(new Evaluator.AttributeWithValueStarting(string2, tokenQueue.remainder()));
            return;
        }
        if (tokenQueue.matchChomp("$=")) {
            this.evals.add(new Evaluator.AttributeWithValueEnding(string2, tokenQueue.remainder()));
            return;
        }
        if (tokenQueue.matchChomp("*=")) {
            this.evals.add(new Evaluator.AttributeWithValueContaining(string2, tokenQueue.remainder()));
            return;
        }
        if (tokenQueue.matchChomp("~=")) {
            this.evals.add(new Evaluator.AttributeWithValueMatching(string2, Pattern.compile(tokenQueue.remainder())));
            return;
        }
        throw new Selector.SelectorParseException("Could not parse attribute query '%s': unexpected token at '%s'", this.query, tokenQueue.remainder());
    }

    private void byClass() {
        String string2 = this.tq.consumeCssIdentifier();
        Validate.notEmpty(string2);
        this.evals.add(new Evaluator.Class(string2.trim().toLowerCase()));
    }

    private void byId() {
        String string2 = this.tq.consumeCssIdentifier();
        Validate.notEmpty(string2);
        this.evals.add(new Evaluator.Id(string2));
    }

    private void byTag() {
        String string2 = this.tq.consumeElementSelector();
        Validate.notEmpty(string2);
        String string3 = string2;
        if (string2.contains("|")) {
            string3 = string2.replace("|", ":");
        }
        this.evals.add(new Evaluator.Tag(string3.trim().toLowerCase()));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void combinator(char c2) {
        void var4_11;
        void var5_24;
        void var4_9;
        Evaluator evaluator;
        boolean bl2;
        this.tq.consumeWhitespace();
        Evaluator evaluator2 = QueryParser.parse(this.consumeSubQuery());
        boolean bl3 = false;
        if (this.evals.size() == 1) {
            Evaluator evaluator3;
            evaluator = evaluator3 = this.evals.get(0);
            Evaluator evaluator4 = evaluator3;
            bl2 = bl3;
            Evaluator evaluator5 = evaluator;
            if (evaluator instanceof CombiningEvaluator.Or) {
                Evaluator evaluator6 = evaluator3;
                bl2 = bl3;
                Evaluator evaluator7 = evaluator;
                if (c2 != ',') {
                    Evaluator evaluator8 = ((CombiningEvaluator.Or)evaluator3).rightMostEvaluator();
                    bl2 = true;
                    Evaluator evaluator9 = evaluator;
                }
            }
        } else {
            CombiningEvaluator.And and;
            CombiningEvaluator.And and2 = and = new CombiningEvaluator.And(this.evals);
            bl2 = bl3;
        }
        this.evals.clear();
        if (c2 == '>') {
            CombiningEvaluator.And and = new CombiningEvaluator.And(evaluator2, new StructuralEvaluator.ImmediateParent((Evaluator)var4_9));
        } else if (c2 == ' ') {
            CombiningEvaluator.And and = new CombiningEvaluator.And(evaluator2, new StructuralEvaluator.Parent((Evaluator)var4_9));
        } else if (c2 == '+') {
            CombiningEvaluator.And and = new CombiningEvaluator.And(evaluator2, new StructuralEvaluator.ImmediatePreviousSibling((Evaluator)var4_9));
        } else if (c2 == '~') {
            CombiningEvaluator.And and = new CombiningEvaluator.And(evaluator2, new StructuralEvaluator.PreviousSibling((Evaluator)var4_9));
        } else {
            if (c2 != ',') {
                throw new Selector.SelectorParseException("Unknown combinator: " + c2, new Object[0]);
            }
            if (var4_9 instanceof CombiningEvaluator.Or) {
                CombiningEvaluator.Or or = (CombiningEvaluator.Or)var4_9;
                or.add(evaluator2);
            } else {
                evaluator = new CombiningEvaluator.Or();
                evaluator.add((Evaluator)var4_9);
                evaluator.add(evaluator2);
                Evaluator evaluator10 = evaluator;
            }
        }
        if (bl2) {
            void var5_23;
            ((CombiningEvaluator.Or)var5_23).replaceRightMostEvaluator((Evaluator)var4_11);
        } else {
            void var5_26 = var4_11;
        }
        this.evals.add((Evaluator)var5_24);
    }

    private int consumeIndex() {
        String string2 = this.tq.chompTo(")").trim();
        Validate.isTrue(StringUtil.isNumeric(string2), "Index must be numeric");
        return Integer.parseInt(string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String consumeSubQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        while (!this.tq.isEmpty()) {
            if (this.tq.matches("(")) {
                stringBuilder.append("(").append(this.tq.chompBalanced('(', ')')).append(")");
                continue;
            }
            if (this.tq.matches("[")) {
                stringBuilder.append("[").append(this.tq.chompBalanced('[', ']')).append("]");
                continue;
            }
            if (this.tq.matchesAny(combinators)) {
                return stringBuilder.toString();
            }
            stringBuilder.append(this.tq.consume());
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void contains(boolean bl2) {
        TokenQueue tokenQueue = this.tq;
        String string2 = bl2 ? ":containsOwn" : ":contains";
        tokenQueue.consume(string2);
        string2 = TokenQueue.unescape(this.tq.chompBalanced('(', ')'));
        Validate.notEmpty(string2, ":contains(text) query must not be empty");
        if (bl2) {
            this.evals.add(new Evaluator.ContainsOwnText(string2));
            return;
        }
        this.evals.add(new Evaluator.ContainsText(string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void cssNthChild(boolean bl2, boolean bl3) {
        int n2 = 0;
        int n3 = 1;
        String string2 = this.tq.chompTo(")").trim().toLowerCase();
        Matcher matcher = NTH_AB.matcher(string2);
        Matcher matcher2 = NTH_B.matcher(string2);
        if ("odd".equals(string2)) {
            n3 = 2;
            n2 = 1;
        } else if ("even".equals(string2)) {
            n3 = 2;
            n2 = 0;
        } else if (matcher.matches()) {
            if (matcher.group(3) != null) {
                n3 = Integer.parseInt(matcher.group(1).replaceFirst("^\\+", ""));
            }
            if (matcher.group(4) != null) {
                n2 = Integer.parseInt(matcher.group(4).replaceFirst("^\\+", ""));
            }
        } else {
            if (!matcher2.matches()) {
                throw new Selector.SelectorParseException("Could not parse nth-index '%s': unexpected format", string2);
            }
            n3 = 0;
            n2 = Integer.parseInt(matcher2.group().replaceFirst("^\\+", ""));
        }
        if (bl3) {
            if (bl2) {
                this.evals.add(new Evaluator.IsNthLastOfType(n3, n2));
                return;
            }
            this.evals.add(new Evaluator.IsNthOfType(n3, n2));
            return;
        }
        if (bl2) {
            this.evals.add(new Evaluator.IsNthLastChild(n3, n2));
            return;
        }
        this.evals.add(new Evaluator.IsNthChild(n3, n2));
    }

    private void findElements() {
        if (this.tq.matchChomp("#")) {
            this.byId();
            return;
        }
        if (this.tq.matchChomp(".")) {
            this.byClass();
            return;
        }
        if (this.tq.matchesWord()) {
            this.byTag();
            return;
        }
        if (this.tq.matches("[")) {
            this.byAttribute();
            return;
        }
        if (this.tq.matchChomp("*")) {
            this.allElements();
            return;
        }
        if (this.tq.matchChomp(":lt(")) {
            this.indexLessThan();
            return;
        }
        if (this.tq.matchChomp(":gt(")) {
            this.indexGreaterThan();
            return;
        }
        if (this.tq.matchChomp(":eq(")) {
            this.indexEquals();
            return;
        }
        if (this.tq.matches(":has(")) {
            this.has();
            return;
        }
        if (this.tq.matches(":contains(")) {
            this.contains(false);
            return;
        }
        if (this.tq.matches(":containsOwn(")) {
            this.contains(true);
            return;
        }
        if (this.tq.matches(":matches(")) {
            this.matches(false);
            return;
        }
        if (this.tq.matches(":matchesOwn(")) {
            this.matches(true);
            return;
        }
        if (this.tq.matches(":not(")) {
            this.not();
            return;
        }
        if (this.tq.matchChomp(":nth-child(")) {
            this.cssNthChild(false, false);
            return;
        }
        if (this.tq.matchChomp(":nth-last-child(")) {
            this.cssNthChild(true, false);
            return;
        }
        if (this.tq.matchChomp(":nth-of-type(")) {
            this.cssNthChild(false, true);
            return;
        }
        if (this.tq.matchChomp(":nth-last-of-type(")) {
            this.cssNthChild(true, true);
            return;
        }
        if (this.tq.matchChomp(":first-child")) {
            this.evals.add(new Evaluator.IsFirstChild());
            return;
        }
        if (this.tq.matchChomp(":last-child")) {
            this.evals.add(new Evaluator.IsLastChild());
            return;
        }
        if (this.tq.matchChomp(":first-of-type")) {
            this.evals.add(new Evaluator.IsFirstOfType());
            return;
        }
        if (this.tq.matchChomp(":last-of-type")) {
            this.evals.add(new Evaluator.IsLastOfType());
            return;
        }
        if (this.tq.matchChomp(":only-child")) {
            this.evals.add(new Evaluator.IsOnlyChild());
            return;
        }
        if (this.tq.matchChomp(":only-of-type")) {
            this.evals.add(new Evaluator.IsOnlyOfType());
            return;
        }
        if (this.tq.matchChomp(":empty")) {
            this.evals.add(new Evaluator.IsEmpty());
            return;
        }
        if (this.tq.matchChomp(":root")) {
            this.evals.add(new Evaluator.IsRoot());
            return;
        }
        throw new Selector.SelectorParseException("Could not parse query '%s': unexpected token at '%s'", this.query, this.tq.remainder());
    }

    private void has() {
        this.tq.consume(":has");
        String string2 = this.tq.chompBalanced('(', ')');
        Validate.notEmpty(string2, ":has(el) subselect must not be empty");
        this.evals.add(new StructuralEvaluator.Has(QueryParser.parse(string2)));
    }

    private void indexEquals() {
        this.evals.add(new Evaluator.IndexEquals(this.consumeIndex()));
    }

    private void indexGreaterThan() {
        this.evals.add(new Evaluator.IndexGreaterThan(this.consumeIndex()));
    }

    private void indexLessThan() {
        this.evals.add(new Evaluator.IndexLessThan(this.consumeIndex()));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void matches(boolean bl2) {
        TokenQueue tokenQueue = this.tq;
        String string2 = bl2 ? ":matchesOwn" : ":matches";
        tokenQueue.consume(string2);
        string2 = this.tq.chompBalanced('(', ')');
        Validate.notEmpty(string2, ":matches(regex) query must not be empty");
        if (bl2) {
            this.evals.add(new Evaluator.MatchesOwn(Pattern.compile(string2)));
            return;
        }
        this.evals.add(new Evaluator.Matches(Pattern.compile(string2)));
    }

    private void not() {
        this.tq.consume(":not");
        String string2 = this.tq.chompBalanced('(', ')');
        Validate.notEmpty(string2, ":not(selector) subselect must not be empty");
        this.evals.add(new StructuralEvaluator.Not(QueryParser.parse(string2)));
    }

    public static Evaluator parse(String string2) {
        return new QueryParser(string2).parse();
    }

    /*
     * Enabled aggressive block sorting
     */
    Evaluator parse() {
        this.tq.consumeWhitespace();
        if (this.tq.matchesAny(combinators)) {
            this.evals.add(new StructuralEvaluator.Root());
            this.combinator(this.tq.consume());
        } else {
            this.findElements();
        }
        while (!this.tq.isEmpty()) {
            boolean bl2 = this.tq.consumeWhitespace();
            if (this.tq.matchesAny(combinators)) {
                this.combinator(this.tq.consume());
                continue;
            }
            if (bl2) {
                this.combinator(' ');
                continue;
            }
            this.findElements();
        }
        if (this.evals.size() == 1) {
            return this.evals.get(0);
        }
        return new CombiningEvaluator.And(this.evals);
    }
}

