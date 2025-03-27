package seedu.address.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    public static final String QUICK_START_MESSAGE = String.join("\n",
            "Quick Start:",
            "Try these example commands:",
            "   • add -n John Doe -p 98765432 -a 123 Clementi Rd -g male -d 2025-04-01",
            "   • list",
            "   • edit 1 -p 91234567",
            "   • delete 1",
            "   • exit"
    );

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Label quickStartMessage;

    @FXML
    private Hyperlink helpLink;

    public HelpWindow(Stage root) {
        super(FXML, root);
        quickStartMessage.setText(QUICK_START_MESSAGE);
    }

    public HelpWindow() {
        this(new Stage());
    }

    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

    public void hide() {
        getRoot().hide();
    }

    public void focus() {
        getRoot().requestFocus();
    }

    @FXML
    private void openUserGuide() {
        try {
            Desktop.getDesktop().browse(new URI(USERGUIDE_URL));
        } catch (Exception e) {
            e.printStackTrace(); // Optional: add alert dialog here
        }
    }
}
