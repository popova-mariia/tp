package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an Appointment's date and optional time.
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointmentDate(String)}
 */
public class AppointmentDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Date should be in the format yyyy-MM-dd or yyyy-MM-dd HH:mm";

    // Matches "yyyy-MM-dd" or "yyyy-MM-dd HH:mm" formats
    public static final String VALIDATION_REGEX =
            "^\\d{4}-\\d{2}-\\d{2}( \\d{2}:\\d{2})?$";

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public final String value;

    /**
     * Constructs an {@code AppointmentDate}.
     *
     * @param input A valid date string, with or without time, or null (optional).
     */
    public AppointmentDate(String input) {
        requireNonNull(input);

        if (input.isEmpty()) {
            this.value = "";
            return;
        }

        checkArgument(isValidAppointmentDate(input), MESSAGE_CONSTRAINTS);
        this.value = normaliseDate(input);
    }

    /**
     * Returns true if a given string is a valid date or date-time.
     */
    public static boolean isValidAppointmentDate(String test) {
        if (test.isEmpty()) {
            return true;
        }
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Normalizes the date/time string into a consistent format.
     */
    private static String normaliseDate(String input) {
        try {
            if (input.trim().contains(" ")) {
                LocalDate datePart = LocalDate.parse(input.substring(0, 10), DATE_FORMAT);
                LocalTime timePart = LocalTime.parse(input.substring(11),
                        DateTimeFormatter.ofPattern("HH:mm"));
                return datePart.format(DATE_FORMAT) + " " + timePart.format(DateTimeFormatter.ofPattern("HH:mm"));
            } else {
                LocalDate date = LocalDate.parse(input, DATE_FORMAT);
                return date.format(DATE_FORMAT);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppointmentDate)) {
            return false;
        }

        AppointmentDate otherDate = (AppointmentDate) other;

        return this.value.isEmpty() && otherDate.value.isEmpty() || this.value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
