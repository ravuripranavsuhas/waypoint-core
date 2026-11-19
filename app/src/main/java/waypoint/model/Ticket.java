package waypoint.model;

import java.time.Instant;
import java.util.Objects;

/**
 * ENTITY. A ticket is open until it is closed once with a non-negative fare.
 */
public final class Ticket {
    private final TicketId id;
    private final SpotId spotId;
    private final Instant entry;
    private Instant exit;
    private Money fare;

    private Ticket(TicketId id, SpotId spotId, Instant entry) {
        this.id = Objects.requireNonNull(id, "id");
        this.spotId = Objects.requireNonNull(spotId, "spotId");
        this.entry = Objects.requireNonNull(entry, "entry");
    }

    public static Ticket open(TicketId id, SpotId spotId, Instant entry) {
        return new Ticket(id, spotId, entry);
    }

    public TicketId id() {
        return id;
    }

    public SpotId spotId() {
        return spotId;
    }

    public Instant entry() {
        return entry;
    }

    public Instant exit() {
        return exit;
    }

    public Money fare() {
        return fare;
    }

    public boolean isOpen() {
        return exit == null;
    }

    public Money close(Instant at, FarePolicy policy) {
        if (!isOpen()) {
            throw new TicketAlreadyClosed();
        }
        Objects.requireNonNull(at, "at");
        Objects.requireNonNull(policy, "policy");
        if (at.isBefore(entry)) {
            throw new IllegalArgumentException("exit must not be before entry");
        }
        Money calculatedFare = Objects.requireNonNull(policy.fareFor(entry, at, spotId), "fare");
        if (calculatedFare.isNegative()) {
            throw new IllegalArgumentException("fare must not be negative");
        }
        exit = at;
        fare = calculatedFare;
        return calculatedFare;
    }
}