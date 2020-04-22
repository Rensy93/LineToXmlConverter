package org.flomen;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class PrimaryController {

    public Label completedLabel;
    public TextField saveFileLocation;
    public TextField selectFileLocation;
    public Button selectFileBtn;
    public Button saveFileLocationBtn;

    public void onClickEventConvertButton(MouseEvent mouseEvent) throws Exception {
        boolean isComplete = false;
        String selectLocation = selectFileLocation.getText();
        String saveLocation = saveFileLocation.getText();
        isComplete = FileConverter.fileConverter(selectLocation, saveLocation);

        updateCompletionLabel(isComplete);

    }

    public void onClickEventSelectButton(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(App.primaryStage);
        if (file != null) {
            selectFileLocation.setText(file.getAbsolutePath());
        }
    }

    public void onClickEventSaveLocationButton(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Xml", "*.xml"));
        File file = fileChooser.showSaveDialog(App.primaryStage);
        if (file != null) {
            saveFileLocation.setText(file.getAbsolutePath());
        }
    }

    public void updateCompletionLabel(boolean isCompleted) {
        completedLabel.setVisible(true);
        if (isCompleted) {
            completedLabel.setText("Completed!");
            selectFileLocation.setText("");
            saveFileLocation.setText("");
        } else {
            completedLabel.setText("failed!");
        }

    }
}
