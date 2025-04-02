package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class AbortCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute_abortClear_success() throws CommandException {
        // Set clear pending instead of deletion pending.
        model.setPendingClear();

        AbortCommand abortCommand = new AbortCommand();
        CommandResult result = abortCommand.execute(model);

        assertEquals(AbortCommand.MESSAGE_CLEAR_ABORT, result.getFeedbackToUser());
        assertFalse(model.isClearPending());
    }

    @Test
    public void execute_noPendingOperation_throwsCommandException() {
        AbortCommand abortCommand = new AbortCommand();
        assertThrows(CommandException.class, () -> abortCommand.execute(model));
    }

    @Test
    public void execute_abortDelete_success() throws CommandException {
        model.addPerson(ALICE);
        model.setPendingDeletion(ALICE);

        AbortCommand abortCommand = new AbortCommand();
        CommandResult commandResult = abortCommand.execute(model);

        assertEquals(String.format(AbortCommand.MESSAGE_DELETE_ABORT, Messages.format(ALICE)),
                commandResult.getFeedbackToUser());
        assertFalse(model.isDeletePending());
    }

    @Test
    public void execute_bothDeleteAndClearPending_throwsCommandException() {
        model.addPerson(ALICE);
        model.setPendingClear();
        model.setPendingDeletion(ALICE);

        AbortCommand abortCommand = new AbortCommand();
        CommandException exception = assertThrows(CommandException.class, () -> abortCommand.execute(model));
        assertEquals("Too many pending operations.", exception.getMessage());

        assertFalse(model.isDeletePending());
        assertFalse(model.isClearPending());
    }
}
