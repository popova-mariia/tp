package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.AppointmentDateContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = "Correct format: " + COMMAND_WORD
            + " -n <name>, -d <appointment date>, or upcoming\n"
            + "Examples:\n"
            + COMMAND_WORD + " -n Alice\n"
            + COMMAND_WORD + " -d 2025-04-01\n"
            + COMMAND_WORD + " upcoming";

    private final Predicate<Person> predicate;

    /**
     * Constructor for FindCommand.
     * @param predicate
     */
    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.isClearPending()) {
            model.clearPendingClear();
        }
        if (model.isDeletePending()) {
            model.clearPendingDeletion();
        }
        model.updateFilteredPersonList(predicate);
        int numPatients = model.getFilteredPersonList().size();
        if (numPatients != 0) {
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, numPatients));
        } else {
            return new CommandResult(
                    Messages.MESSAGE_NO_SUCH_PERSONS
            );
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

    /**
     * Getter for keywords
     * @return keywords
     */
    public List<String> getKeywords() {
        if (predicate instanceof NameContainsKeywordsPredicate) {
            NameContainsKeywordsPredicate predicateName = (NameContainsKeywordsPredicate) predicate;
            return predicateName.getKeywords();
        } else if (predicate instanceof AppointmentDateContainsKeywordsPredicate) {
            AppointmentDateContainsKeywordsPredicate datePredicate =
                    (AppointmentDateContainsKeywordsPredicate) predicate;
            return datePredicate.getKeywords();
        }
        return Collections.emptyList();
    }
}
