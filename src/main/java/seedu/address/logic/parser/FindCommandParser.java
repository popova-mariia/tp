package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.stream.Stream;

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
        requireNonNull(args);
        String trimmedArgs = args.trim();

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_APPT_DATE);

        if (trimmedArgs.equalsIgnoreCase("upcoming")) {
            return new FindCommand(new UpcomingAppointmentPredicate());
        }

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME)
                || arePrefixesPresent(argMultimap, PREFIX_APPT_DATE))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
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

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
