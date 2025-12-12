package org.po2025.thecarsui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TheCarsApplication extends Application
{
    MainViewController controller;

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(TheCarsApplication.class.getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();

        VehicleManager manager = new VehicleManager();
        controller = fxmlLoader.getController();
        controller.setVehicleManager(manager);
        controller.startSimulation();

        stage.setScene(new Scene(root));
        stage.setTitle("The Cars");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car.png"))));
        stage.show();
    }

    @Override
    public void stop()
    {
        controller.stopSimulation();
    }
}
