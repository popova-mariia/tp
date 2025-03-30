package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    private final String tagName;
    private final String tagType;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName} and {@code tagType}.
     */
    @JsonCreator
    public JsonAdaptedTag(@JsonProperty("tagName") String tagName,
                          @JsonProperty("tagType") String tagType) {
        this.tagName = tagName;
        this.tagType = tagType;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        tagName = source.tagName;
        this.tagType = source.getTagType().name();
    }

    public String getTagName() {
        return tagName;
    }

    public String getTagType() {
        return tagType;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (tagName == null || tagType == null) {
            throw new IllegalValueException("Missing tag name or tag type.");
        }
        try {
            Tag.TagType type = Tag.TagType.valueOf(tagType.toUpperCase());

            if (Tag.isEmptyTagName(tagName)) {
                throw new IllegalValueException(type.emptyInputMessage);
            }

            if (!Tag.isValidTagName(tagName)) {
                throw new IllegalValueException(type.constraintMessage);
            }

            return new Tag(tagName, type);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalValueException("Invalid or missing tag type: " + tagType);
        }
    }

}
