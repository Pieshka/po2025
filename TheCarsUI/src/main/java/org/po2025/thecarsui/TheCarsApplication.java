package org.po2025.thecarsui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

public class TheCarsApplication extends Application
{
    private static final Logger logger = LogManager.getLogger();
    private MainViewController controller;

    @Override
    public void start(Stage stage) throws IOException
    {
        logger.info("Loading FXML main view...");
        FXMLLoader fxmlLoader = new FXMLLoader(TheCarsApplication.class.getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();

        logger.info("Loading Vehicle Manager...");
        VehicleManager manager = new VehicleManager();
        controller = fxmlLoader.getController();
        controller.setVehicleManager(manager);
        controller.startSimulation();

        logger.info("Showing FXML window...");
        stage.setScene(new Scene(root));
        stage.setTitle("The Cars");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("car.png"))));
        stage.show();
    }

    @Override
    public void stop()
    {
        logger.info("Stopping application...");
        controller.stopSimulation();
    }
}
