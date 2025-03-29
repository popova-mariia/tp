package seedu.address.model.util;

import static seedu.address.testutil.Assert.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_invalidSample_throwsNullPointerException() {
        assertThrows(NullPointerException.class, SampleDataUtil::getSamplePersons);
    }

    @Test
    public void getSampleAddressBook_invalidSample_throwsNullPointerException() {
        assertThrows(NullPointerException.class, SampleDataUtil::getSampleAddressBook);
    }

    @Test
    public void getTagSet_invalidTag_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                SampleDataUtil.getTagSet(Tag.TagType.DETAIL, "")); // "" is an invalid tag name
    }

    @Test
    public void getTagSet_validTags_success() {
        Set<Tag> tags = SampleDataUtil.getTagSet(Tag.TagType.DETAIL, "friendly", "elderly");
        assert tags.size() == 2;

        Set<Tag> conditionTags = SampleDataUtil.getTagSet(Tag.TagType.CONDITION, "diabetic");
        assert conditionTags.size() == 1;

        Set<Tag> noTags = SampleDataUtil.getTagSet(Tag.TagType.CONDITION); // no strings
        assert noTags.isEmpty();
    }
}
