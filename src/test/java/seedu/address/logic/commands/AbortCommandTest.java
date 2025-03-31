package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
