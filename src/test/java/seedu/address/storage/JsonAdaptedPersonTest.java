package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.AppointmentDate;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Medicine;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class JsonAdaptedPersonTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_GENDER = "not sure";
    private static final String INVALID_APPT_DATE = "2025/10/10";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_APPT_DATE = BENSON.getAppointmentDate().toString();
    private static final String VALID_MEDICINE = BENSON.getMedicine().toString();

    private static final List<JsonAdaptedTag> VALID_DETAIL_TAGS = BENSON.getDetailTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private static final List<JsonAdaptedTag> VALID_CONDITION_TAGS = BENSON.getConditionTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_ADDRESS,
                VALID_GENDER, VALID_APPT_DATE, VALID_MEDICINE, new ArrayList<>(), new ArrayList<>());
        assertThrows(IllegalValueException.class, Name.MESSAGE_CONSTRAINTS, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_ADDRESS,
                VALID_GENDER, VALID_APPT_DATE, VALID_MEDICINE, new ArrayList<>(), new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()), person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_ADDRESS,
                VALID_GENDER, VALID_APPT_DATE, VALID_MEDICINE, new ArrayList<>(), new ArrayList<>());
        assertThrows(IllegalValueException.class, Phone.MESSAGE_CONSTRAINTS, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_ADDRESS,
                VALID_GENDER, VALID_APPT_DATE, VALID_MEDICINE, new ArrayList<>(), new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()), person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_ADDRESS,
                VALID_GENDER, VALID_APPT_DATE, VALID_MEDICINE, new ArrayList<>(), new ArrayList<>());
        assertThrows(IllegalValueException.class, Address.MESSAGE_CONSTRAINTS, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null,
                VALID_GENDER, VALID_APPT_DATE, VALID_MEDICINE, new ArrayList<>(), new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()), person::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_ADDRESS,
                INVALID_GENDER, VALID_APPT_DATE, VALID_MEDICINE, new ArrayList<>(), new ArrayList<>());
        assertThrows(IllegalValueException.class, Gender.MESSAGE_CONSTRAINTS, person::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_ADDRESS,
                null, VALID_APPT_DATE, VALID_MEDICINE, new ArrayList<>(), new ArrayList<>());
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()), person::toModelType);
    }

    @Test
    public void toModelType_invalidAppointmentDate_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_ADDRESS,
                VALID_GENDER, INVALID_APPT_DATE, VALID_MEDICINE, new ArrayList<>(), new ArrayList<>());
        assertThrows(IllegalValueException.class, AppointmentDate.MESSAGE_CONSTRAINTS, person::toModelType);
    }

    @Test
    public void toModelType_invalidDetailTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidDetailTags = new ArrayList<>();
        // simulate invalid tag name coming from JSON
        invalidDetailTags.add(new JsonAdaptedTag("#friend", "DETAIL"));
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_GENDER,
                VALID_APPT_DATE, VALID_MEDICINE, new ArrayList<>(), invalidDetailTags);

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidConditionTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidConditionTags = new ArrayList<>();
        // simulate invalid tag name coming from JSON
        invalidConditionTags.add(new JsonAdaptedTag("#friend", "CONDITION"));
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_GENDER,
                VALID_APPT_DATE, VALID_MEDICINE, invalidConditionTags, new ArrayList<>());

        assertThrows(IllegalValueException.class, person::toModelType);
    }
    @Test
    public void toModelType_wrongConditionTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> wrongConditionTags = new ArrayList<>();
        wrongConditionTags.add(new JsonAdaptedTag("vegetarian", "DETAIL"));

        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME,
                VALID_PHONE,
                VALID_ADDRESS,
                VALID_GENDER,
                VALID_APPT_DATE,
                VALID_MEDICINE,
                wrongConditionTags,
                new ArrayList<>()
        );

        assertThrows(IllegalValueException.class, person::toModelType);

    }

    @Test
    public void toModelType_wrongDetailTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> wrongDetailTags = new ArrayList<>();
        wrongDetailTags.add(new JsonAdaptedTag("diabetic", "CONDITION"));

        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME,
                VALID_PHONE,
                VALID_ADDRESS,
                VALID_GENDER,
                VALID_APPT_DATE,
                VALID_MEDICINE,
                new ArrayList<>(),
                wrongDetailTags
        );

        assertThrows(IllegalValueException.class, person::toModelType);

    }
    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME,
                VALID_PHONE,
                VALID_ADDRESS,
                VALID_GENDER,
                VALID_APPT_DATE,
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
        assertThrows(IllegalValueException.class,
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Medicine.class.getSimpleName()),
                person::toModelType);
    }

    @Test
    public void toModelType_nullTagName_throwsIllegalValueException() {
        JsonAdaptedTag tag = new JsonAdaptedTag(null, "CONDITION");
        assertThrows(IllegalValueException.class, tag::toModelType);
    }

    @Test
    public void toModelType_nullTagType_throwsIllegalValueException() {
        JsonAdaptedTag tag = new JsonAdaptedTag("dementia", null);
        assertThrows(IllegalValueException.class, tag::toModelType);
    }

    @Test
    public void toModelType_emptyTagName_throwsIllegalValueException() {
        JsonAdaptedTag tag = new JsonAdaptedTag("   ", "DETAIL");
        assertThrows(IllegalValueException.class, tag::toModelType);
    }

}

