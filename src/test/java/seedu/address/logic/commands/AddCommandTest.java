package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different name -> returns true
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPendingClear() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isClearPending() {
            return false;
        }

        @Override
        public void clearPendingClear() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isDeletePending() {
            return false;
        }

        @Override
        public void setPendingDeletion(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getPendingDeletion() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearPendingDeletion() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPeopleToClear() {
            throw new AssertionError("This method should not be called.");
        }
        public void sortPersonList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    @Test
    public void execute_personLimitReached_throwsCommandException() {
        ModelStubPersonLimit modelStub = new ModelStubPersonLimit();
        Person person = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(person);

        assertThrows(CommandException.class,
                "Cannot add more persons. Maximum person limit has been reached.", () -> addCommand.execute(modelStub));
    }

    /**
     * A Model stub that always throws a PersonLimitReachedException when adding a person.
     */
    private class ModelStubPersonLimit extends ModelStub {
        @Override
        public boolean hasPerson(Person person) {
            return false; // pretend person does not already exist
        }

        @Override
        public void addPerson(Person person) {
            throw new seedu.address.model.person.exceptions.PersonLimitReachedException();
        }
    }

    @Test
    public void execute_upTo30Persons_success() throws Exception {
        Model model = new ModelManager();

        for (int i = 0; i < 30; i++) {
            Person person = new PersonBuilder()
                    .withName("Person " + i)
                    .withPhone("900000" + String.format("%02d", i))
                    .withAddress("123 Street " + i)
                    .withGender("female")
                    .withAppointmentDate("2025-12-12 14:00")
                    .withMedicine("Panadol")
                    .build();

            AddCommand addCommand = new AddCommand(person);
            CommandResult result = addCommand.execute(model);

            assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(person)),
                    result.getFeedbackToUser());
            assertTrue(model.hasPerson(person));
        }
    }

    @Test
    public void execute_moreThan30Persons_throwsCommandException() throws Exception {
        Model model = new ModelManager();

        for (int i = 0; i < 30; i++) {
            Person person = new PersonBuilder()
                    .withName("Person " + i)
                    .withPhone("900000" + String.format("%02d", i))
                    .withAddress("123 Street " + i)
                    .withGender("female")
                    .withAppointmentDate("2025-12-12 14:00")
                    .withMedicine("Panadol")
                    .build();

            model.addPerson(person);
        }

        Person overflow = new PersonBuilder()
                .withName("Overflow")
                .withPhone("99999999")
                .build();

        AddCommand addCommand = new AddCommand(overflow);

        assertThrows(CommandException.class,
                "Cannot add more persons. Maximum person limit has been reached.", ()
                        -> addCommand.execute(model));
    }
}
