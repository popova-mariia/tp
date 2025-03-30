package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("-n ");
    public static final Prefix PREFIX_PHONE = new Prefix("-p ");
    public static final Prefix PREFIX_ADDRESS = new Prefix("-a ");
    public static final Prefix PREFIX_GENDER = new Prefix("-g ");
    public static final Prefix PREFIX_CONDITION = new Prefix("-c ");
    public static final Prefix PREFIX_DETAILS = new Prefix("-det ");
    public static final Prefix PREFIX_APPT_DATE = new Prefix("-d ");

    // reminder to remove below prefixes if not necessary
    public static final Prefix PREFIX_REMARK = new Prefix("r/");

}
