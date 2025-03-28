package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Gender gender;

    // Data fields
    private final Address address;
    private final AppointmentDate appointmentDate;
    private final Remark remark;
    private final Set<Tag> conditionTags = new HashSet<>();
    private final Set<Tag> detailTags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Address address, Gender gender, AppointmentDate appointmentDate,
                  Remark remark, Set<Tag> conditionTags, Set<Tag> detailTags) {
        requireAllNonNull(name, phone, address, gender, appointmentDate, conditionTags, detailTags);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.appointmentDate = appointmentDate;
        this.remark = remark;
        this.conditionTags.addAll(conditionTags);
        this.detailTags.addAll(detailTags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Gender getGender() {
        return gender;
    }

    public Address getAddress() {
        return address;
    }

    public AppointmentDate getAppointmentDate() {
        return appointmentDate;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable condition set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getConditionTags() {
        return Collections.unmodifiableSet(conditionTags);
    }

    /**
     * Returns an immutable detail set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getDetailTags() {
        return Collections.unmodifiableSet(detailTags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && address.equals(otherPerson.address)
                && gender.equals(otherPerson.gender)
                && appointmentDate.equals(otherPerson.appointmentDate)
                && conditionTags.equals(otherPerson.conditionTags)
                && detailTags.equals(otherPerson.detailTags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, gender, appointmentDate, conditionTags, detailTags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("address", address)
                .add("gender", gender)
                .add("appointment date", appointmentDate)
                .add("conditionTags", conditionTags)
                .add("detailTags", detailTags)
                .toString();
    }

}
