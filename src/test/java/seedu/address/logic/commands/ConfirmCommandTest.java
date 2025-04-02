package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ConfirmCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute_confirmDelete_success() throws CommandException {
        model.addPerson(ALICE);
        model.setPendingDeletion(ALICE);

        ConfirmCommand confirmCommand = new ConfirmCommand();
        CommandResult result = confirmCommand.execute(model);

        assertEquals(String.format(ConfirmCommand.MESSAGE_DELETE_SUCCESS, seedu.address.logic.Messages.format(ALICE)),
                result.getFeedbackToUser());
        assertEquals(false, model.hasPerson(ALICE));
        assertEquals(false, model.isDeletePending());
    }

    @Test
    public void execute_confirmClear_success() throws CommandException {
        model.addPerson(ALICE);
        model.setPendingClear();

        ConfirmCommand confirmCommand = new ConfirmCommand();
        CommandResult result = confirmCommand.execute(model);

        assertEquals(ConfirmCommand.MESSAGE_CLEAR_SUCCESS, result.getFeedbackToUser());
        assertEquals(0, model.getAddressBook().getPersonList().size());
        assertEquals(false, model.isClearPending());
    }

    @Test
    public void execute_noPendingOperation_throwsCommandException() {
        ConfirmCommand confirmCommand = new ConfirmCommand();
        assertThrows(CommandException.class, () -> confirmCommand.execute(model));
    }

    @Test
    public void execute_bothDeleteAndClearPending_throwsCommandException() {
        model.addPerson(ALICE);
        model.setPendingClear();
        model.setPendingDeletion(ALICE);

        ConfirmCommand confirmCommand = new ConfirmCommand();

        CommandException exception = assertThrows(CommandException.class, () -> confirmCommand.execute(model));
        assertEquals("Too many pending operations, try again.", exception.getMessage());

        assertFalse(model.isDeletePending());
        assertFalse(model.isClearPending());
    }
}
