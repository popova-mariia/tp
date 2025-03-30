package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(PREFIX_GENDER + person.getGender().gender + " ");
        sb.append(PREFIX_APPT_DATE + person.getAppointmentDate().value + " ");
        sb.append(PREFIX_MEDICINE + person.getMedicine().value + " ");
        person.getConditionTags().stream().forEach(
            s -> sb.append(PREFIX_CONDITION + s.tagName + " ")
        );
        person.getDetailTags().stream().forEach(
                s -> sb.append(PREFIX_DETAILS + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_GENDER).append(gender.gender).append(" "));
        descriptor.getAppointmentDate().ifPresent(appointmentDate -> sb.append(PREFIX_APPT_DATE)
                .append(appointmentDate.value).append(" "));
        if (descriptor.getConditionTags().isPresent()) {
            Set<Tag> tags = descriptor.getConditionTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_CONDITION);
            } else {
                tags.forEach(s -> sb.append(PREFIX_CONDITION).append(s.tagName).append(" "));
            }
        }
        if (descriptor.getDetailTags().isPresent()) {
            Set<Tag> tags = descriptor.getDetailTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_DETAILS);
            } else {
                tags.forEach(s -> sb.append(PREFIX_DETAILS).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
