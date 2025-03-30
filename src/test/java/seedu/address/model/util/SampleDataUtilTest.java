
package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.AppointmentDate;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_invalidSample_throwsNullPointerException() {
        assertThrows(NullPointerException.class, SampleDataUtil::getSamplePersons);
    }

    @Test
    public void getSampleAddressBook_invalidSample_throwsNullPointerException() {
        assertThrows(NullPointerException.class, SampleDataUtil::getSampleAddressBook);
    }

    @Test
    public void getTagSet_invalidTag_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                SampleDataUtil.getTagSet(Tag.TagType.DETAIL, ""));
    }

    @Test
    public void getTagSet_validTags_success() {
        Set<Tag> tags = SampleDataUtil.getTagSet(Tag.TagType.DETAIL, "friendly", "elderly");
        assert tags.size() == 2;

        Set<Tag> conditionTags = SampleDataUtil.getTagSet(Tag.TagType.CONDITION, "diabetic");
        assert conditionTags.size() == 1;

        Set<Tag> noTags = SampleDataUtil.getTagSet(Tag.TagType.CONDITION); // no strings
        assert noTags.isEmpty();
    }
    @Test
    public void getSampleAddressBook_allValidData_successfullyAddsAll() {
        Person[] validSamplePersons = new Person[] {
            new Person(new Name("Test A"), new Phone("12345678"), new Address("123 Street"),
                    new Gender("Male"), new AppointmentDate("2025-01-01"), new Remark(""),
                    SampleDataUtil.getTagSet(Tag.TagType.CONDITION, "Healthy"),
                    SampleDataUtil.getTagSet(Tag.TagType.DETAIL, "Independent")),

            new Person(new Name("Test B"), new Phone("87654321"), new Address("321 Street"),
                    new Gender("Female"), new AppointmentDate("2025-02-01"), new Remark(""),
                    SampleDataUtil.getTagSet(Tag.TagType.CONDITION, "Diabetic"),
                    SampleDataUtil.getTagSet(Tag.TagType.DETAIL, "Wheelchair"))
        };

        AddressBook ab = new AddressBook();
        for (Person p : validSamplePersons) {
            ab.addPerson(p);
        }

        assertEquals(2, ab.getPersonList().size());
    }
    @Test
    public void getSampleAddressBook_validSample_success() {
        ReadOnlyAddressBook sampleAddressBook = SampleDataUtil.getSampleAddressBookWithValidPersons();

        assertNotNull(sampleAddressBook);
        assertEquals(5, sampleAddressBook.getPersonList().size());

        Person expectedFirstPerson = new Person(
                new Name("Alex Yeoh"),
                new Phone("87438807"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                new Gender("Male"),
                new AppointmentDate("2025-01-10"),
                new Remark("Prefers morning visits"),
                SampleDataUtil.getTagSet(Tag.TagType.CONDITION, "Dementia"),
                SampleDataUtil.getTagSet(Tag.TagType.DETAIL, "Lives alone")
        );

        assertEquals(expectedFirstPerson, sampleAddressBook.getPersonList().get(0));
    }
}
