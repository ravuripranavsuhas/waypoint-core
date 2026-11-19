package waypoint.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class RegistrationTest {
    @Test
    void trimsAndNormalizesRegistration() {
        assertEquals("MH12AB1234", new Registration(" mh12ab1234 ").value());
    }

    @Test
    void acceptsOneOrTwoSeriesLetters() {
        assertEquals("MH12A1234", new Registration("mh12a1234").value());
    }

    @Test
    void rejectsInvalidRegistration() {
        assertThrows(IllegalArgumentException.class, () -> new Registration("MH12ABC1234"));
    }

    @Test
    void rejectsNull() {
        assertThrows(NullPointerException.class, () -> new Registration(null));
    }
}
