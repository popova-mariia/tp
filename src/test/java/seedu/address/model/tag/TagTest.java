package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null, Tag.TagType.DETAIL));
    }
    @Test
    public void constructor_nulltagtype_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag("a", null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName, Tag.TagType.DETAIL));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    //Positive tests: apostrophe is allowed
    @Test
    public void constructor_validConditionTagWithApostrophe_success() {
        assertDoesNotThrow(() -> new Tag("Alzheimer's", Tag.TagType.CONDITION));
    }

    @Test
    public void constructor_validDetailTagWithApostrophe_success() {
        assertDoesNotThrow(() -> new Tag("lives in son's house", Tag.TagType.DETAIL));
    }

    @Test
    public void constructor_bothValidTagsWithApostrophe_success() {
        assertDoesNotThrow(() -> {
            new Tag("Alzheimer's", Tag.TagType.CONDITION);
            new Tag("lives in son's house", Tag.TagType.DETAIL);
        });
    }

    //negative tests: apostrophe coupled with other disallowed symbols
    @Test
    public void constructor_invalidSymbolInConditionTag_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tag("Alzheimer's!", Tag.TagType.CONDITION));
    }

    @Test
    public void constructor_invalidSymbolInDetailTag_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tag("lives @ son's house", Tag.TagType.DETAIL));
    }
}
