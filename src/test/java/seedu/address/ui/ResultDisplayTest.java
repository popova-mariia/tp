package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class ResultDisplayTest {

    /**
     * Initializes the JavaFX toolkit before any tests run.
     */
    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    /**
     * Tests that the setFeedbackToUser method properly updates the TextArea.
     */
    @Test
    public void testSetFeedbackToUser() throws Exception {
        ResultDisplay resultDisplay = new ResultDisplay();
        String testFeedback = "Test feedback message";

        Platform.runLater(() -> resultDisplay.setFeedbackToUser(testFeedback));
        Thread.sleep(100);

        Field textAreaField = ResultDisplay.class.getDeclaredField("resultDisplay");
        textAreaField.setAccessible(true);
        TextArea textArea = (TextArea) textAreaField.get(resultDisplay);

        assertEquals(testFeedback, textArea.getText());
    }
}
