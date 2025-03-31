package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CONFIRMATION, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_setsClearPending() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        CommandResult result = new ClearCommand().execute(model);

        assertEquals(ClearCommand.MESSAGE_CONFIRMATION, result.getFeedbackToUser());
        assertTrue(model.isClearPending());
        assertEquals(getTypicalAddressBook(), model.getAddressBook()); // nothing actually cleared yet
    }

    @Test
    public void execute_nonEmptyAddressBook_setsClearPendingWhenDeleteAlreadyPending() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        CommandResult result = new ClearCommand().execute(model);

        assertEquals(ClearCommand.MESSAGE_CONFIRMATION, result.getFeedbackToUser());
        assertTrue(model.isClearPending());
        assertEquals(getTypicalAddressBook(), model.getAddressBook()); // nothing actually cleared yet
    }


}
