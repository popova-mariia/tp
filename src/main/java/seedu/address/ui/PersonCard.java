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
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label gender;
    @FXML
    private HBox appointmentDate;
    @FXML
    private Label medicine;
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

        // Set name and appointment date as highlight-able text
        setHighlightedName(name, person.getName().fullName);
        setHighlightedDate(appointmentDate, person.getAppointmentDate().value);

        // Remaining non-highlight-able fields
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        gender.setText(person.getGender().gender);
        medicine.setText(person.getMedicine().value);

        person.getConditionTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    conditions.getChildren().add(tagLabel);
                });

        person.getDetailTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    details.getChildren().add(tagLabel);
                });
    }

    private void setHighlightedName(HBox fieldBox, String fullText) {
        fieldBox.getChildren().clear();

        String[] words = fullText.split("(?<=\\s)|(?=\\s)");
        for (String word : words) {
            Label label = new Label(word);
            if (containsKeyword(word)) {
                label.getStyleClass().add("highlighted-label");
            } else {
                label.getStyleClass().add("unmatched-text");
            }
            fieldBox.getChildren().add(label);
        }
    }

    private void setHighlightedDate(HBox fieldBox, String fullText) {
        fieldBox.getChildren().clear();
        Label label = new Label(fullText);
        if (containsKeyword(fullText)) {
            label.getStyleClass().add("highlighted-label");
        } else {
            label.getStyleClass().add("unmatched-text");
        }
        fieldBox.getChildren().add(label);
    }

    private boolean containsKeyword(String text) {
        return keywords.stream().anyMatch(kw -> text.toLowerCase().contains(kw.toLowerCase()));
    }
}
