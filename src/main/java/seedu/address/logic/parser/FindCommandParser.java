package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AppointmentDate;
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

        String keyword = trimmedArgs.substring(2).trim();

        if (trimmedArgs.startsWith("-n ")) {
            return new FindCommand(new NameContainsKeywordsPredicate(List.of(keyword)));
        } else if (trimmedArgs.startsWith("-d ")) {
            try {
                ParserUtil.parseAppointmentDate(keyword);
            } catch (ParseException e) {
                if (e.getMessage().equals(AppointmentDate.MESSAGE_CONSTRAINTS)) {
                    throw new ParseException(AppointmentDate.MESSAGE_CONSTRAINTS);
                }
                if (e.getMessage().equals(AppointmentDate.INVALID_DATE)) {
                    throw new ParseException(AppointmentDate.INVALID_DATE);
                }
            }
            return new FindCommand(new AppointmentDateContainsKeywordsPredicate(List.of(keyword)));
        } else {
            throw new ParseException("Please specify a valid prefix: '-n ' for name, '-d ' for appointment date.");
        }
    }
}
