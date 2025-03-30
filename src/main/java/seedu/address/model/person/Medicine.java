package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Medicine in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidMedicine(String)}
 */
public class Medicine {

    /**
     * Error message for invalid medicine input.
     */
    public static final String MESSAGE_CONSTRAINTS =
            "Medicine can only take alphanumeric values, and it should not be blank";

    /**
     * The first character of the medicine must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    /**
     * The medicine name stored in this object.
     */
    public final String value;

    /**
     * Constructs a {@code Medicine}.
     *
     * @param medicine A valid medicine name.
     */
    public Medicine(String medicine) {
        requireNonNull(medicine);
        if (medicine.isEmpty()) {
            this.value = "";
            return;
        }
        checkArgument(isValidMedicine(medicine), MESSAGE_CONSTRAINTS);
        value = medicine;
    }

    /**
     * Returns true if a given string is a valid medicine name.
     *
     * @param test The string to validate.
     * @return True if the given string matches the validation regex, false otherwise.
     */
    public static boolean isValidMedicine(String test) {
        return test.matches(VALIDATION_REGEX);
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

        // instanceof handles nulls
        if (!(other instanceof Medicine)) {
            return false;
        }

        Medicine otherMedicine = (Medicine) other;
        return value.equals(otherMedicine.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
