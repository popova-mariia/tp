package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class AppointmentDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppointmentDate(null));
    }

    @Test
    public void constructor_invalidFormat_throwsIllegalArgumentException() {
        String invalidDate1 = "2025/03/23"; // wrong format
        String invalidDate2 = "2025-13-01"; // invalid month
        String invalidDate3 = "2025-12-01 25:00"; // invalid hour
        assertThrows(IllegalArgumentException.class, () -> new AppointmentDate(invalidDate1));
        assertThrows(IllegalArgumentException.class, () -> new AppointmentDate(invalidDate2));
        assertThrows(IllegalArgumentException.class, () -> new AppointmentDate(invalidDate3));
    }

    @Test
    public void isValidAppointmentDate() {
        // null input
        assertThrows(NullPointerException.class, () -> AppointmentDate.isValidAppointmentDate(null));

        // invalid inputs
        assertFalse(AppointmentDate.isValidAppointmentDate("23-03-2025"));
        assertFalse(AppointmentDate.isValidAppointmentDate("2025-03-23T14:00"));
        assertFalse(AppointmentDate.isValidAppointmentDate("2025-03-23 14"));

        // valid date only
        assertTrue(AppointmentDate.isValidAppointmentDate("2025-03-23"));

        // valid date with time
        assertTrue(AppointmentDate.isValidAppointmentDate("2025-03-23 14:00"));
    }

    @Test
    public void equals() {
        AppointmentDate appt1 = new AppointmentDate("2025-03-23 14:00");
        AppointmentDate appt2 = new AppointmentDate("2025-03-23 14:00");
        AppointmentDate appt3 = new AppointmentDate("2025-03-23");

        // same values -> returns true
        assertTrue(appt1.equals(appt2));

        // same object -> returns true
        assertTrue(appt1.equals(appt1));

        // null -> returns false
        assertFalse(appt1.equals(null));

        // different types -> returns false
        assertFalse(appt1.equals("2025-03-23 14:00"));

        // different values -> returns false
        assertFalse(appt1.equals(appt3));
    }

    @Test
    public void constructor_emptyString_success() {
        AppointmentDate appt = new AppointmentDate("");
        assertEquals("", appt.value);
    }

    @Test
    public void compareTo_bothEmpty_returnsZero() {
        AppointmentDate appt1 = new AppointmentDate("");
        AppointmentDate appt2 = new AppointmentDate("");
        assertEquals(0, appt1.compareTo(appt2));
    }

    @Test
    public void compareTo_emptyVsValid_returnsPositive() {
        AppointmentDate empty = new AppointmentDate("");
        AppointmentDate valid = new AppointmentDate("2025-03-23 14:00");
        assertTrue(empty.compareTo(valid) > 0);
    }

    @Test
    public void compareTo_validVsEmpty_returnsNegative() {
        AppointmentDate empty = new AppointmentDate("");
        AppointmentDate valid = new AppointmentDate("2025-03-23 14:00");
        assertTrue(valid.compareTo(empty) < 0);
    }

    @Test
    public void compareTo_dateVsDateTime_correctOrder() {
        AppointmentDate dateOnly = new AppointmentDate("2025-03-23");
        AppointmentDate dateTime = new AppointmentDate("2025-03-23 14:00");
        assertTrue(dateOnly.compareTo(dateTime) < 0);
    }

    @Test
    public void normaliseDate_leadingZeros_success() {
        AppointmentDate appt = new AppointmentDate("2025-03-03 09:05");
        assertEquals("2025-03-03 09:05", appt.toString());
    }

    @Test
    public void parseToLocalDateTime_validDateOnly_parsesCorrectly() {
        String input = "2025-04-01";
        LocalDateTime expected = LocalDateTime.of(2025, 4, 1, 0, 0);
        LocalDateTime actual = AppointmentDate.parseToLocalDateTime(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parseToLocalDateTime_validDateTime_parsesCorrectly() {
        String input = "2025-04-01 13:45";
        LocalDateTime expected = LocalDateTime.of(2025, 4, 1, 13, 45);
        LocalDateTime actual = AppointmentDate.parseToLocalDateTime(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parseToLocalDateTime_invalidDate_throwsException() {
        String input = "2025-13-01";
        assertThrows(Exception.class, () -> AppointmentDate.parseToLocalDateTime(input));
    }

    @Test
    public void parseToLocalDateTime_invalidTime_throwsException() {
        String input = "2025-04-01 25:00";
        assertThrows(Exception.class, () -> AppointmentDate.parseToLocalDateTime(input));
    }

}
