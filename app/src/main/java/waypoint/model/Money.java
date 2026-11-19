package waypoint.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/** VALUE OBJECT. Exact money represented in minor currency units. */
public final class Money implements Comparable<Money> {
    public static final Currency INR = Currency.getInstance("INR");
    public static final Currency USD = Currency.getInstance("USD");

    private final long minorUnits;
    private final Currency currency;

    private Money(long minorUnits, Currency currency) {
        this.minorUnits = minorUnits;
        this.currency = Objects.requireNonNull(currency, "currency");
    }

    public static Money ofMinor(long amount, Currency currency) {
        return new Money(amount, currency);
    }

    public static Money rupees(long amount) {
        return ofMinor(Math.multiplyExact(amount, 100), INR);
    }

    public long minorUnits() {
        return minorUnits;
    }

    public Currency currency() {
        return currency;
    }

    public Money plus(Money other) {
        requireSameCurrency(other);
        return new Money(Math.addExact(minorUnits, other.minorUnits), currency);
    }

    public Money minus(Money other) {
        requireSameCurrency(other);
        return new Money(Math.subtractExact(minorUnits, other.minorUnits), currency);
    }

    public Money times(int multiplier) {
        return new Money(Math.multiplyExact(minorUnits, multiplier), currency);
    }

    public boolean isNegative() {
        return minorUnits < 0;
    }

    public List<Money> allocate(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("count must be positive");
        }
        long base = minorUnits / count;
        long remainder = minorUnits % count;
        List<Money> shares = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            long share = base;
            if (i < Math.abs(remainder)) {
                share += Long.signum(remainder);
            }
            shares.add(new Money(share, currency));
        }
        return List.copyOf(shares);
    }

    @Override
    public int compareTo(Money other) {
        requireSameCurrency(other);
        return Long.compare(minorUnits, other.minorUnits);
    }

    private void requireSameCurrency(Money other) {
        Objects.requireNonNull(other, "other");
        if (!currency.equals(other.currency)) {
            throw new CurrencyMismatch(currency, other.currency);
        }
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof Money money
                && minorUnits == money.minorUnits
                && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minorUnits, currency);
    }

    @Override
    public String toString() {
        return currency.getCurrencyCode() + " "
                + BigDecimal.valueOf(minorUnits, currency.getDefaultFractionDigits()).toPlainString();
    }
}