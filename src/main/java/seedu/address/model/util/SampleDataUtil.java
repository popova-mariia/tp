
package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Remark EMPTY_REMARK = new Remark("");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Gender("Male"), null, EMPTY_REMARK, getTagSet(Tag.TagType.CONDITION, "Dementia"),
                    getTagSet(Tag.TagType.DETAIL, "lives alone")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Gender("Female"),
                    new AppointmentDate("2025-01-10"), EMPTY_REMARK, getTagSet(Tag.TagType.CONDITION, "Arthiritis"),
                    getTagSet(Tag.TagType.DETAIL, "")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Gender("Female"),
                    new AppointmentDate("2025-02-01 11:30"), EMPTY_REMARK,
                    getTagSet(Tag.TagType.CONDITION, "high risk"), getTagSet(Tag.TagType.DETAIL, "")),
            new Person(new Name("David Li"), new Phone("91031282"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Gender("Male"),
                    new AppointmentDate("2025-01-11"), EMPTY_REMARK,
                    getTagSet(Tag.TagType.CONDITION, ""), getTagSet(Tag.TagType.DETAIL, "no lift")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Gender("Male"),
                    new AppointmentDate("2025-02-10"), EMPTY_REMARK,
                    getTagSet(Tag.TagType.CONDITION, "Scoliosis"), getTagSet(Tag.TagType.DETAIL, "bedridden")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Gender("Male"), null, EMPTY_REMARK,
                    getTagSet(Tag.TagType.CONDITION, "High BP"), getTagSet(Tag.TagType.DETAIL, ""))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given of the specified {@code TagType}.
     */
    public static Set<Tag> getTagSet(Tag.TagType type, String... strings) {
        return Arrays.stream(strings)
                .map(tagName -> new Tag(tagName, type))
                .collect(Collectors.toSet());
    }

    public static ReadOnlyAddressBook getSampleAddressBookWithValidPersons() {
        AddressBook sampleAb = new AddressBook();
        Person[] validPersons = new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Gender("Male"), new AppointmentDate("2025-01-10"),
                    new Remark("Prefers morning visits"),
                    getTagSet(Tag.TagType.CONDITION, "Dementia"),
                    getTagSet(Tag.TagType.DETAIL, "Lives alone")),

            new Person(new Name("Bernice Yu"), new Phone("99272758"),
                    new Address("Blk 13 Lorong 3 Serangoon Gardens, #07-18"),
                    new Gender("Female"), new AppointmentDate("2025-02-15"),
                    new Remark("Has a helper"),
                    getTagSet(Tag.TagType.CONDITION, "Arthritis"),
                    getTagSet(Tag.TagType.DETAIL, "Needs walking aid")),

            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Gender("Female"), new AppointmentDate("2025-03-20"),
                    new Remark("Allergic to penicillin"),
                    getTagSet(Tag.TagType.CONDITION, "High cholesterol"),
                    getTagSet(Tag.TagType.DETAIL, "Lives with family")),

            new Person(new Name("David Li"), new Phone("91031282"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Gender("Male"), new AppointmentDate("2025-04-12"),
                    new Remark("Loves gardening"),
                    getTagSet(Tag.TagType.CONDITION, "Hypertension"),
                    getTagSet(Tag.TagType.DETAIL, "No lift access")),

            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Gender("Male"), new AppointmentDate("2025-05-05"),
                    new Remark("Bedridden"),
                    getTagSet(Tag.TagType.CONDITION, "Scoliosis"),
                    getTagSet(Tag.TagType.DETAIL, "Requires daily assistance"))
        };

        for (Person p : validPersons) {
            sampleAb.addPerson(p);
        }
        return sampleAb;
    }
}
