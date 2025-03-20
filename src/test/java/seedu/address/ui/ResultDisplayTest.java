package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextArea;

public class ResultDisplayTest {

    /**
     * Initializes the JavaFX toolkit before any tests run.
     * Creating a new JFXPanel forces the JavaFX runtime to initialize.
     */
    @BeforeAll
    public static void initToolkit() {
        new JFXPanel();
    }

    /**
     * Shuts down the JavaFX platform after all tests have completed.
     */
    @AfterAll
    public static void tearDown() {
        Platform.exit();
    }

    /**
     * Tests that the setFeedbackToUser method properly updates the TextArea.
     */
    @Test
    public void testSetFeedbackToUser() throws Exception {
        ResultDisplay resultDisplay = new ResultDisplay();
        String testFeedback = "Test feedback message";

        // Run the update on the JavaFX Application Thread.
        Platform.runLater(() -> resultDisplay.setFeedbackToUser(testFeedback));

        // Allow time for the Platform.runLater task to execute.
        Thread.sleep(100);

        // Use reflection to access the private TextArea field named "resultDisplay".
        Field textAreaField = ResultDisplay.class.getDeclaredField("resultDisplay");
        textAreaField.setAccessible(true);
        TextArea textArea = (TextArea) textAreaField.get(resultDisplay);

        // Assert that the text in the TextArea matches the expected feedback.
        assertEquals(testFeedback, textArea.getText());
    }
}
