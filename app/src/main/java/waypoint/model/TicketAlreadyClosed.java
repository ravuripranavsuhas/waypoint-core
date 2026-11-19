package waypoint.model;

public final class TicketAlreadyClosed extends IllegalStateException {
    public TicketAlreadyClosed() {
        super("Ticket is already closed");
    }
}
