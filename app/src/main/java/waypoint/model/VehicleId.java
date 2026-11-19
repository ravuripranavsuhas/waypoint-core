package waypoint.model;

import java.util.Objects;

/** Distinct id type so it cannot be swapped with another id at a call site. */
public record VehicleId(String value) {
    public VehicleId {
        value = Objects.requireNonNull(value, "value");
        if (value.isBlank()) {
            throw new IllegalArgumentException("value must not be blank");
        }
    }
}
