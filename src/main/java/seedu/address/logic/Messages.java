package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The patient index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d patients listed!";
    public static final String MESSAGE_NO_SUCH_PERSONS = "No such patients in the list!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_UNCLEAR_DELETE_CONFIRMATION =
            "A deletion is pending. Please type 'y' to confirm deletion or 'n' to abort deletion.";
    public static final String MESSAGE_UNCLEAR_CLEAR_CONFIRMATION =
            "A clear is pending. Please type 'y' to confirm clear or 'n' to abort clear.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Gender: ")
                .append(person.getGender())
                .append("; Appointment Date: ")
                .append(person.getAppointmentDate())
                .append("; Medicine: ")
                .append(person.getMedicine())
                .append("; Condition Tags: ");
        person.getConditionTags().forEach(builder::append);
        builder.append("; Detail Tags: ");
        person.getDetailTags().forEach(builder::append);
        return builder.toString();
    }

}
