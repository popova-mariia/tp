package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Gender {
    public static final String MESSAGE_CONSTRAINTS =
            "Gender should either be 'Male' or 'Female', and it should not be blank";

    /**
     * The gender must be either "M", "F", "male", or "female", case-insensitive.
     */
    public static final String VALIDATION_REGEX = "(?i)^(M|F|male|female)$";
    public final String gender;

    /**
     * Constructs a {@code Gender}.
     *
     * @param gen A valid gender.
     */
    public Gender(String gen) {
        requireNonNull(gen);
        checkArgument(isValidGender(gen), MESSAGE_CONSTRAINTS);
        this.gender = normaliseGender(gen);
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Normalizes the gender input to "male" or "female".
     */
    private static String normaliseGender(String gender) {
        String normalisedGender = gender.toLowerCase();
        if (normalisedGender.equals("m")) {
            return "male";
        } else if (normalisedGender.equals("f")) {
            return "female";
        }
        return normalisedGender;
    }

    @Override
    public String toString() {
        return gender;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Gender)) {
            return false;
        }

        Gender otherGender = (Gender) other;
        return gender.equals(otherGender.gender);
    }

    @Override
    public int hashCode() {
        return gender.hashCode();
    }
}
