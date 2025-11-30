package org.po2025.thecarsui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TheCarsController
{
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick()
    {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
