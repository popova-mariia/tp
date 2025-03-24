package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric and may contain spaces";
    public static final String VALIDATION_REGEX = "[\\p{Alnum} ]+";

    /**
     * Represents the type of a tag.
     * A tag can either be a CONDITION tag or a DETAIL tag.
     * - CONDITION: describes a medical condition or status-related attribute (e.g., "dementia").
     * - DETAIL: provides additional information or context (e.g., "wheelchair access").
     */
    public enum TagType {
        CONDITION,
        DETAIL
    }
    public final String tagName;
    private final TagType tagType;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName, TagType tagType) {
        requireNonNull(tagName);
        requireNonNull(tagType);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
        this.tagType = tagType;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the type of this tag.
     *
     * @return the tag type, either {@code TagType.CONDITION} or {@code TagType.DETAIL}.
     */
    public TagType getTagType() {
        return tagType;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName) && tagType == otherTag.tagType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName, tagType);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        String prefix = tagType == TagType.CONDITION ? "[C:" : "[D:";
        return prefix + tagName + "]";
    }

}
