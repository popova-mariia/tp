package seedu.address.model.person.exceptions;

/**
 * Signals that the maximum limit for the list of persons to be added to the addressbook has been reached and
 * cannot be exceeded.
 */
public class PersonLimitReachedException extends RuntimeException {

    public PersonLimitReachedException() {
        super("Cannot add more persons. Maximum person limit has been reached.");
    }
}
