package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class AbortDeletionCommand extends Command {
    public static final String COMMAND_WORD = "n";

    public static final String MESSAGE_DELETE_PERSON_INTERRUPT = "Aborting deletion of Person: %1$s";

    public CommandResult execute(Model model) throws CommandException {
        Person person = model.getPendingDeletion();
        if (person == null) {
            throw new CommandException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_INTERRUPT, Messages.format(person)));
    }
}
