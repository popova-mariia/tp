package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
/**
 * Confirms the deletion process of a pending person deletion.
 */
public class ConfirmDeletionCommand extends Command {
    public static final String COMMAND_WORD = "y";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person person = model.getPendingDeletion();
        if (person == null) {
            throw new CommandException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
        model.deletePerson(person);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(person)));
    }
}
