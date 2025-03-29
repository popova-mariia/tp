package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPT_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPT_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPT_DATE_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CHARLIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
                .withPhone("94351253").withRemark("She likes aardvarks.").withGender("female")
                .withAppointmentDate("2025-10-10").withDetails("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withRemark("He can't take beer!")
                .withPhone("98765432").withGender("male")
                .withAppointmentDate("2025-01-10 10:30").withDetails("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withAddress("wall street").withGender("female")
            .withAppointmentDate("2025-11-10 11:30").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withAddress("10th street")
                .withGender("male").withDetails("friends").withAppointmentDate("2025-12-05 09:00").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withAddress("michegan ave").withGender("female").withDetails("friends").withAppointmentDate("2025-01-11").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withAddress("little tokyo").withGender("female").withAppointmentDate("2025-02-09")
            .withDetails("friends").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withAddress("4th street").withGender("male").withAppointmentDate("2025-09-12")
            .withDetails("friends").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withAddress("little india").withGender("male").withAppointmentDate("2025-10-10").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withAddress("chicago ave").withGender("female").withAppointmentDate("2025-10-10").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withAddress(VALID_ADDRESS_AMY).withGender(VALID_GENDER_AMY)
                .withAppointmentDate(VALID_APPT_DATE_AMY).withDetails(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withAddress(VALID_ADDRESS_BOB).withGender(VALID_GENDER_BOB)
                .withAppointmentDate(VALID_APPT_DATE_BOB).withDetails(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    public static final Person CHARLIE = new PersonBuilder().withName(VALID_NAME_CHARLIE).withPhone(VALID_PHONE_CHARLIE)
            .withAddress(VALID_ADDRESS_CHARLIE).withGender(VALID_GENDER_CHARLIE)
            .withAppointmentDate(VALID_APPT_DATE_CHARLIE).withDetails().build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
