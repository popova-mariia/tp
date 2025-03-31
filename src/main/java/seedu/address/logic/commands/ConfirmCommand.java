package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Confirms either a deletion or clear operation, depending on what is pending.
 */
public class ConfirmCommand extends Command {
    public static final String COMMAND_WORD = "y";
    public static final String MESSAGE_DELETE_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_CLEAR_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_NOTHING_TO_CLEAR = "Address book empty, nothing to clear!";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.isDeletePending()) {
            Person person = model.getPendingDeletion();
            model.deletePerson(person);
            model.clearPendingDeletion();
            return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS, Messages.format(person)));
        } else if (model.isClearPending() && model.hasPeopleToClear()) {
            model.setAddressBook(new seedu.address.model.AddressBook());
            model.clearPendingClear();
            return new CommandResult(MESSAGE_CLEAR_SUCCESS);
        } else if (model.isClearPending() && !model.hasPeopleToClear()) {
            model.clearPendingClear();
            return new CommandResult(MESSAGE_CLEAR_SUCCESS);
        } else {
            throw new CommandException("No pending operation to confirm.");
        }
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof ConfirmCommand;
    }
}
