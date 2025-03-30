
package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.AppointmentDate;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String address;
    private final String gender;
    private final String appointmentDate;
    private final String remark;
    private final List<JsonAdaptedTag> conditionTags = new ArrayList<>();
    private final List<JsonAdaptedTag> detailTags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("address") String address, @JsonProperty("gender") String gender,
                             @JsonProperty("appointment date") String appointmentDate,
                             @JsonProperty("remark") String remark,
                             @JsonProperty("conditions") List<JsonAdaptedTag> conditionTags,
                             @JsonProperty("details") List<JsonAdaptedTag> detailTags) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.appointmentDate = appointmentDate;
        this.remark = remark;
        if (conditionTags != null) {
            this.conditionTags.addAll(conditionTags);
        }
        if (detailTags != null) {
            this.detailTags.addAll(detailTags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        gender = source.getGender().gender;
        appointmentDate = source.getAppointmentDate().value;
        remark = source.getRemark().value;
        conditionTags.addAll(source.getConditionTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));

        detailTags.addAll(source.getDetailTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final Set<Tag> modelConditionTags = new HashSet<>();
        for (JsonAdaptedTag tag : conditionTags) {
            Tag modelTag = tag.toModelType();
            if (modelTag.getTagType() != Tag.TagType.CONDITION) {
                throw new IllegalValueException("Expected tag of type CONDITION but got: " + modelTag.getTagType());
            }
            modelConditionTags.add(modelTag);
        }

        final Set<Tag> modelDetailTags = new HashSet<>();
        for (JsonAdaptedTag tag : detailTags) {
            Tag modelTag = tag.toModelType();
            if (modelTag.getTagType() != Tag.TagType.DETAIL) {
                throw new IllegalValueException("Expected tag of type DETAIL but got: " + modelTag.getTagType());
            }
            modelDetailTags.add(modelTag);
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        if (!AppointmentDate.isValidAppointmentDate(appointmentDate)) {
            throw new IllegalValueException(AppointmentDate.MESSAGE_CONSTRAINTS);
        }
        final AppointmentDate modelAppointmentDate = new AppointmentDate(appointmentDate);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);

        return new Person(modelName, modelPhone, modelAddress, modelGender, modelAppointmentDate, modelRemark,
                modelConditionTags, modelDetailTags);

    }

}
