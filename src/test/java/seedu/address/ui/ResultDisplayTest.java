package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.TextArea;



public class ResultDisplayTest {

    private ResultDisplay resultDisplay;

    @BeforeAll
    public static void initJavaFX() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await(); // Ensures JavaFX starts before tests run
    }

    @BeforeEach
    public void setUp() {
        resultDisplay = new ResultDisplay();
    }

    @Test
    public void setFeedbackToUser_warningTrue_appliesWarningStyle() {
        Platform.runLater(() -> {
            String feedback = "Warning message";
            resultDisplay.setFeedbackToUser(feedback, true);

            TextArea textArea = resultDisplay.getResultDisplay();
            assertEquals(feedback, textArea.getText());
            assertTrue(textArea.getStyleClass().contains("warning-text"));
        });
    }

    @Test
    public void setFeedbackToUser_warningFalse_removesWarningStyle() {
        Platform.runLater(() -> {
            String feedback = "Normal message";
            resultDisplay.setFeedbackToUser(feedback, false);

            TextArea textArea = resultDisplay.getResultDisplay();
            assertEquals(feedback, textArea.getText());
            assertFalse(textArea.getStyleClass().contains("warning-text"));
        });
    }
}
