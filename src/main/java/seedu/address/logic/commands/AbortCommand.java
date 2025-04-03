package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Aborts either a deletion or clear operation, depending on what is pending.
 */
public class AbortCommand extends Command {
    public static final String COMMAND_WORD = "n";
    public static final String MESSAGE_DELETE_ABORT = "Aborted deletion of Person: %1$s";
    public static final String MESSAGE_CLEAR_ABORT = "Aborted clear operation.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.isDeletePending() && model.isClearPending()) {
            model.clearPendingClear();
            model.clearPendingDeletion();
            throw new CommandException("Too many pending operations.");
        }
        if (model.isDeletePending()) {
            Person person = model.getPendingDeletion();
            model.clearPendingDeletion();
            return new CommandResult(String.format(MESSAGE_DELETE_ABORT, Messages.format(person)));
        } else if (model.isClearPending()) {
            model.clearPendingClear();
            return new CommandResult(MESSAGE_CLEAR_ABORT);
        } else {
            throw new CommandException("No pending operation to abort.");
        }
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbortCommand;
    }
}
