package waypoint.model;

import java.util.Objects;

/** ENTITY. Identity = id. Invariant: a spot cannot be assigned while occupied. */
public final class Spot {
    private final SpotId id;
    private VehicleId occupant;

    public Spot(SpotId id) {
        this.id = Objects.requireNonNull(id, "id");
    }

    public SpotId id() {
        return id;
    }

    public boolean tryAssign(VehicleId vehicleId) {
        Objects.requireNonNull(vehicleId, "vehicleId");
        if (occupant != null) {
            return false;
        }
        occupant = vehicleId;
        return true;
    }

    public void release(VehicleId vehicleId) {
        if (Objects.equals(occupant, vehicleId)) {
            occupant = null;
        }
    }

    public boolean isFree() {
        return occupant == null;
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof Spot spot && id.equals(spot.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}