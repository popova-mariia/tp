package seedu.address.ui;

import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
    private final List<String> keywords;

    @FXML
    private Label id;
    @FXML
    private HBox cardPane;
    @FXML
    private HBox name;
    @FXML
    private HBox phone;
    @FXML
    private HBox address;
    @FXML
    private HBox remark;
    @FXML
    private Label gender;
    @FXML
    private Label appointmentDate;
    @FXML
    private FlowPane conditions;
    @FXML
    private FlowPane details;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex, List<String> keywords) {
        super(FXML);
        this.person = person;
        this.keywords = keywords;

        id.setText(displayedIndex + ". ");

        setHighlightedText(name, person.getName().fullName);
        setHighlightedText(phone, person.getPhone().value);
        setHighlightedText(address, person.getAddress().value);
        setHighlightedText(remark, person.getRemark().value);

        // Set gender and appointment date (no highlighting needed)
        gender.setText(person.getGender().gender);
        appointmentDate.setText(person.getAppointmentDate().value);

        // Clear and add highlighted condition tags
        conditions.getChildren().clear();
        person.getConditionTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    if (containsKeyword(tag.tagName)) {
                        tagLabel.getStyleClass().add("highlighted-label");
                    }
                    conditions.getChildren().add(tagLabel);
                });

        // Clear and add highlighted detail tags
        details.getChildren().clear();
        person.getDetailTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    if (containsKeyword(tag.tagName)) {
                        tagLabel.getStyleClass().add("highlighted-label");
                    }
                    details.getChildren().add(tagLabel);
                });
    }

    private void setHighlightedText(HBox fieldBox, String fullText) {
        fieldBox.getChildren().clear();
        String lower = fullText.toLowerCase();

        int i = 0;
        while (i < fullText.length()) {
            boolean matched = false;
            for (String keyword : keywords) {
                String kw = keyword.toLowerCase();
                if (lower.startsWith(kw, i)) {
                    Label matchLabel = new Label(fullText.substring(i, i + kw.length()));
                    matchLabel.getStyleClass().add("highlighted-label");
                    fieldBox.getChildren().add(matchLabel);
                    i += kw.length();
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                Label normalLabel = new Label(String.valueOf(fullText.charAt(i)));
                normalLabel.getStyleClass().add("unmatched-text");
                fieldBox.getChildren().add(normalLabel);
                i++;
            }
        }
    }

    private boolean containsKeyword(String text) {
        return keywords.stream().anyMatch(kw -> text.toLowerCase().contains(kw.toLowerCase()));
    }


}
