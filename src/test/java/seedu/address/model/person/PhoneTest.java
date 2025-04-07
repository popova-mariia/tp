package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 digits
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("1234567890123456")); // 16 digits (too long)

        // valid phone numbers
        assertTrue(Phone.isValidPhone("123")); // exactly 3 digits (min)
        assertTrue(Phone.isValidPhone("12345678")); // 8 digits
        assertTrue(Phone.isValidPhone("123456789012345")); // exactly 15 digits (max)
    }


    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("995")));
    }

    @Test
    public void isValidPhone_minLengthEdgeCase_returnsTrue() {
        assertTrue(Phone.isValidPhone("123")); // 3 digits
    }

    @Test
    public void isValidPhone_maxLengthEdgeCase_returnsTrue() {
        assertTrue(Phone.isValidPhone("123456789012345")); // 15 digits
    }

    @Test
    public void isValidPhone_belowMinLength_returnsFalse() {
        assertFalse(Phone.isValidPhone("12")); // 2 digits
    }

    @Test
    public void isValidPhone_aboveMaxLength_returnsFalse() {
        assertFalse(Phone.isValidPhone("1234567890123456")); // 16 digits
    }


}
