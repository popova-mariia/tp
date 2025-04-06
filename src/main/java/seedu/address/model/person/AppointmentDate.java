package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents an Appointment's date and optional time.
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointmentDate(String)}
 */
public class AppointmentDate implements Comparable<AppointmentDate> {
    public static final String MESSAGE_CONSTRAINTS =
            "Date should be in the format yyyy-MM-dd or yyyy-MM-dd HH:mm";
    public static final String INVALID_DATE = "Date given does not exist. Please give a valid date"
            + "\ne.g. 2020-02-02 [12:00]";

    // Matches "yyyy-MM-dd" or "yyyy-MM-dd HH:mm" formats
    public static final String VALIDATION_REGEX =
            "^\\d{4}-\\d{2}-\\d{2}( \\d{2}:\\d{2})?$";

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    public final String value;

    /**
     * Constructs an {@code AppointmentDate}.
     *
     * @param input A valid date string, with or without time, or null (optional).
     * @throws IllegalArgumentException if the given input string does not match the expected format.
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
     * Returns true if a given string is a valid date or date-time format.
     *
     * @param test The string to be tested.
     * @return True if the string matches the valid date format; false otherwise.
     */
    public static boolean isValidFormatAppointmentDate(String test) {
        if (test.isEmpty()) {
            return true;
        }
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the given string is a valid appointment date.
     * This checks if the date string is properly formatted and represents a valid date.
     * The string can also contain time in the format "HH:mm".
     *
     * @param test The string to be validated.
     * @return True if the string represents a valid date or date-time; false otherwise.
     */
    public static boolean isValidAppointmentDate(String test) {
        try {
            if (test.contains(" ")) {
                LocalDate.parse(test, DATE_TIME_FORMAT);
            } else {
                LocalDate.parse(test, DATE_FORMAT);
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Normalizes the date/time string into a consistent format.
     * If the input includes a time, it combines the date and time into one string in the format
     * "yyyy-MM-dd HH:mm". If the input only contains a date, it returns it in "yyyy-MM-dd" format.
     *
     * @param input The date/time string to normalize.
     * @return The normalized date/time string.
     * @throws IllegalArgumentException if the input string is not in the expected format.
     */
    private static String normaliseDate(String input) {
        try {
            if (input.trim().contains(" ")) {
                LocalDateTime dateTime = LocalDateTime.parse(input, DATE_TIME_FORMAT);
                return dateTime.format(DATE_TIME_FORMAT);
            } else {
                LocalDate date = LocalDate.parse(input, DATE_FORMAT);
                return date.format(DATE_FORMAT);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns the string representation of the appointment date.
     *
     * @return The appointment date as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this AppointmentDate to another object for equality.
     * Two AppointmentDate objects are considered equal if their value fields are equal.
     *
     * @param other The object to compare with.
     * @return True if the objects are equal; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppointmentDate)) {
            return false;
        }

        AppointmentDate otherDate = (AppointmentDate) other;

        return this.value.equals(otherDate.value);
    }

    /**
     * Returns the hash code of this AppointmentDate.
     * The hash code is based on the value field.
     *
     * @return The hash code of this AppointmentDate.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(AppointmentDate other) {
        if (this.value.isEmpty() && other.value.isEmpty()) {
            return 0;
        }
        if (this.value.isEmpty()) {
            return 1; // Empty dates go to the end
        }
        if (other.value.isEmpty()) {
            return -1;
        }

        // Try parsing to LocalDateTime for comparison
        LocalDateTime thisDateTime = parseToLocalDateTime(this.value);
        LocalDateTime otherDateTime = parseToLocalDateTime(other.value);

        return thisDateTime.compareTo(otherDateTime);
    }

    protected static LocalDateTime parseToLocalDateTime(String value) {
        if (value.contains(" ")) {
            return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else {
            return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        }
    }
}
