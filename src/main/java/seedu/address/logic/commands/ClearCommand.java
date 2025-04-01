package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Requests user confirmation before clearing the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_CONFIRMATION = "Are you sure you want to clear the address book?\n"
            + "Type `y` to proceed or `n` to abort.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.isDeletePending()) {
            model.clearPendingDeletion();
        }
        if (!model.hasPeopleToClear()) {
            return new CommandResult(ConfirmCommand.MESSAGE_NOTHING_TO_CLEAR);
        }
        model.setPendingClear();
        return new CommandResult(MESSAGE_CONFIRMATION, CommandResult.DisplayType.WARNING);
    }
}
