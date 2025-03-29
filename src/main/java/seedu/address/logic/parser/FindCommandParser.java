package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AppointmentDateContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.UpcomingAppointmentPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.equalsIgnoreCase("upcoming")) {
            return new FindCommand(new UpcomingAppointmentPredicate());
        }

        String[] split = trimmedArgs.substring(2).trim().split("\\s+");

        if (trimmedArgs.startsWith("-n ")) {
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(split)));
        } else if (trimmedArgs.startsWith("-d ")) {
            return new FindCommand(new AppointmentDateContainsKeywordsPredicate(Arrays.asList(split)));
        } else {
            throw new ParseException("Please specify a valid prefix: '-n ' for name, '-d ' for appointment date.");
        }
    }

}
