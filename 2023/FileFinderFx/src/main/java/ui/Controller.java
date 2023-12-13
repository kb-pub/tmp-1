package ui;

import filefinder.FileFinderTask;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextArea area;
    @FXML
    private TextField regexpTf;
    @FXML
    private TextField dirPathTf;
    @FXML
    private Button startBtn;
    @FXML
    private Button stopBtn;

    private final BooleanProperty taskExecuting = new SimpleBooleanProperty(false);

    private FileFinderTask task;

    @FXML
    public void initialize() {
        regexpTf.disableProperty().bind(taskExecuting);
        dirPathTf.disableProperty().bind(taskExecuting);
        startBtn.disableProperty().bind(taskExecuting);
        stopBtn.disableProperty().bind(Bindings.not(taskExecuting));
    }

    @FXML
    public void startBtnAction() {
        task = new FileFinderTask(
                dirPathTf.getText(),
                regexpTf.getText(),
                8,
                msg -> Platform.runLater(() -> area.appendText(msg + "\n")));
        taskExecuting.set(true);
        new Thread(() -> {
            try {
                task.run();
            } finally {
                Platform.runLater(() -> taskExecuting.set(false));
            }
        }).start();
    }

    @FXML
    public void stopBtnAction() {
        if (task != null) {
            task.cancel();
        }
    }
}