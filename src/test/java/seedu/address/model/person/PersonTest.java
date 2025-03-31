package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPT_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION_DEMENTIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAIL_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyListWithDetailTags_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getDetailTags().remove(0));
    }
    @Test
    public void asObservableList_modifyListWithConditionTags_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getConditionTags().remove(0));
    }
    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same phone, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withAddress(VALID_ADDRESS_BOB)
                .withGender(VALID_GENDER_BOB).withDetails(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different phone, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same phone but different case name -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // same phone with name having trailing spaces -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name, different phone number -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // same name, different phone -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different address, different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different gender, different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withGender(VALID_GENDER_BOB).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different appointment date, different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withAppointmentDate(VALID_APPT_DATE_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different conditions tag, different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withConditions(VALID_CONDITION_DEMENTIA)
                .withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different details tag, different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withDetails(VALID_DETAIL_MORNING)
                .withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", address=" + ALICE.getAddress() + ", gender=" + ALICE.getGender()
                + ", appointment date=" + ALICE.getAppointmentDate() + ", conditionTags=" + ALICE.getConditionTags()
                + ", detailTags=" + ALICE.getDetailTags() + ", medicine=" + ALICE.getMedicine() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
