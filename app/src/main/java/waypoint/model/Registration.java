package waypoint.model;

import java.util.Locale;
import java.util.Objects;

/** VALUE OBJECT. A registration is stored in normalized uppercase form. */
public record Registration(String value) {
    // Format: two letters, two digits, one or two letters, four digits. e.g. MH12AB1234
    public Registration {
        value = Objects.requireNonNull(value, "value").trim().toUpperCase(Locale.ROOT);
        if (!value.matches("[A-Z]{2}\\d{2}[A-Z]{1,2}\\d{4}")) {
            throw new IllegalArgumentException("Invalid vehicle registration: " + value);
        }
    }
}