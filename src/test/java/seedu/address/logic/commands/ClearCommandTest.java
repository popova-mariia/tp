package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_nonEmptyAddressBook_setsClearPending() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        CommandResult result = new ClearCommand().execute(model);

        assertEquals(ClearCommand.MESSAGE_CONFIRMATION, result.getFeedbackToUser());
        assertTrue(model.isClearPending());
        assertEquals(getTypicalAddressBook(), model.getAddressBook()); // nothing actually cleared yet
    }

    @Test
    public void execute_nonEmptyAddressBook_setsClearPendingWhenDeleteAlreadyPending() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        deleteCommand.execute(model);

        assertTrue(model.isDeletePending());

        ClearCommand clearCommand = new ClearCommand();
        CommandResult result = clearCommand.execute(model); // this should clear the pending delete

        assertEquals(ClearCommand.MESSAGE_CONFIRMATION, result.getFeedbackToUser());
        assertTrue(model.isClearPending());
        assertFalse(model.isDeletePending());
    }
}
