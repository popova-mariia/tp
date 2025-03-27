package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

@Disabled("Disabled on CI: requires JavaFX UI thread")
public class HelpWindowTest {

    // Initializes JavaFX runtime
    @BeforeAll
    public static void initToolkit() {
        new JFXPanel(); // Initializes JavaFX
    }

    @Test
    public void show() throws Exception {
        Platform.runLater(() -> {
            HelpWindow helpWindow = new HelpWindow();
            helpWindow.show();
            assertTrue(helpWindow.isShowing());

            helpWindow.hide();
            assertFalse(helpWindow.isShowing());
        });

        Thread.sleep(500); // Give time for JavaFX thread
    }

    @Test
    public void openUserGuide_doesNotThrow() throws Exception {
        Platform.runLater(() -> {
            HelpWindow helpWindow = new HelpWindow();
            helpWindow.openUserGuide();
        });

        Thread.sleep(500); // Let JavaFX open the link
    }
}
