package org.hamcrest.number;

import java.math.BigDecimal;
import java.math.MathContext;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class BigDecimalCloseTo extends TypeSafeMatcher<BigDecimal> {
    private final BigDecimal delta;
    private final BigDecimal value;

    public BigDecimalCloseTo(BigDecimal value, BigDecimal error) {
        this.delta = error;
        this.value = value;
    }

    public boolean matchesSafely(BigDecimal item) {
        return actualDelta(item).compareTo(BigDecimal.ZERO) <= 0;
    }

    public void describeMismatchSafely(BigDecimal item, Description mismatchDescription) {
        mismatchDescription.appendValue(item).appendText(" differed by ").appendValue(actualDelta(item));
    }

    public void describeTo(Description description) {
        description.appendText("a numeric value within ").appendValue(this.delta).appendText(" of ").appendValue(this.value);
    }

    private BigDecimal actualDelta(BigDecimal item) {
        return item.subtract(this.value, MathContext.DECIMAL128).abs().subtract(this.delta, MathContext.DECIMAL128).stripTrailingZeros();
    }

    @Factory
    public static Matcher<BigDecimal> closeTo(BigDecimal operand, BigDecimal error) {
        return new BigDecimalCloseTo(operand, error);
    }
}
