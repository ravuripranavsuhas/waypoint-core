package waypoint.model;

import java.util.Currency;

public final class CurrencyMismatch extends IllegalArgumentException {
    public CurrencyMismatch(Currency first, Currency second) {
        super("Cannot combine " + first.getCurrencyCode() + " and " + second.getCurrencyCode());
    }
}
