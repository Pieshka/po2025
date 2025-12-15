package org.po2025.thecarsui;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.po2025.thecars.Vehicle;
import org.po2025.thecars.VehicleObserver;
import org.po2025.thecars.VehicleStartupException;
import org.po2025.thecars.VehicleUpdater;

import java.io.IOException;
import java.util.*;
import java.awt.geom.Point2D;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainViewController implements VehicleObserver
{
    private static final Logger logger = LogManager.getLogger();
    private ScheduledExecutorService executor;

    private VehicleManager vehicleManager;
    private List<Control> allControls;
    private List<Control> allButtons;
    private final Map<Vehicle, ImageView> vehicleImages = new HashMap<>();
    private static final double METERS_TO_PIXELS = 50;

    /* Changes current Vehicle Manager and binds it with the combo box */
    public void setVehicleManager(VehicleManager manager)
    {
        // Set new manager
        this.vehicleManager = manager;

        // Bind manager's list to combobox
        this.currentVehicleCB.setItems(vehicleManager.getVehicles());

        // Setup activity of controls based on combobox selection
        BooleanBinding noVehicles = Bindings.isEmpty(vehicleManager.getVehicles());
        allControls.forEach(c -> c.disableProperty().bind(noVehicles));
        allButtons.forEach(c -> c.disableProperty().bind(noVehicles));
        noVehicles.addListener((obs, wasEmpty, isNowEmpty) -> {
            if (isNowEmpty) {
                clearControls();
            }
        });

        // Setup refreshing of controls based on combobox selection
        this.currentVehicleCB.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    if (oldV != null)
                    {
                        vehicleImages.get(oldV).setOpacity(0.5); // Make old selection more transparent
                    }

                    if (newV != null) {
                        vehicleImages.get(newV).setOpacity(1); // Make current selection visible
                        refreshControls(newV);
                    }
                }
        );
    }

    /* Starts the simulation */
    public void startSimulation()
    {
        logger.info("Starting multi-threaded simulation...");

        VehicleUpdater vehicleUpdater = new VehicleUpdater(vehicleManager.getVehicles());

        executor = Executors.newSingleThreadScheduledExecutor(r ->
            new Thread(r, "VehicleUpdater")
        );

        /* Run 60 updates per second */
        executor.scheduleAtFixedRate(
                vehicleUpdater,
                0,
                16,
                TimeUnit.MILLISECONDS
        );

        logger.info("Simulation started");
    }

    /* Gracefully stops the simulation */
    public void stopSimulation()
    {
        if (executor != null) {
            executor.shutdownNow();
            logger.info("Simulation stopped");
        }
    }

    /* Vehicle Observer Methods */
    @Override
    public void positionChanged(Vehicle vehicle, Point2D newPosition, double angle)
    {
        logger.debug("Vehicle {}, changed its position to: {}, {}",
                vehicle.toString(), newPosition.getX(), newPosition.getY());

        Platform.runLater(() -> {
            ImageView img = vehicleImages.get(vehicle);
            if (img != null) {
                Bounds paneBounds = MainVehiclePane.getLayoutBounds();

                double px = newPosition.getX() * METERS_TO_PIXELS;
                double py = newPosition.getY() * METERS_TO_PIXELS;

                // We need to clamp, because our Vehicle may escape beyond Pane bounds
                px = Math.max(0, Math.min(px, paneBounds.getWidth() - img.getFitWidth()));
                py = Math.max(0, Math.min(py, paneBounds.getHeight() - img.getFitHeight()));

                img.setLayoutX(px);
                img.setLayoutY(py);

                // Angle normalization to <-90,90> degs
                double newAngle = (angle > 90) ? angle - 180 :
                        (angle < -90) ? angle + 180 : angle;

                // Flip the car sprite if going backwards
                if (angle > 90 || angle < -90) {
                    img.setScaleX(-1);
                } else {
                    img.setScaleX(1);
                }
                img.setRotate(img.getRotate() * 0.8 + newAngle * 0.2); // Interpolation
            }

            // Little bit hacked, but it's the easiest way to implement this
            // We are just utilizing our second thread to update all the information
            refreshDynamicControls(vehicle);
        });
    }

    private void refreshDynamicControls(Vehicle v)
    {
        vehicleCurVelocityTF.setText(String.valueOf(v.GetCurrentVelocity()));
        engineCurRotTF.setText(String.valueOf(v.GetEngine().GetCurrentRotationSpeed()));
        gearboxCurGearTF.setText(String.valueOf(v.GetGearbox().GetCurrentGear()));
        clutchStateTF.setText(v.GetGearbox().GetClutch().IsPressed() ? "Pressed" : "Depressed");

        logger.debug("Refreshed all dynamic controls");
    }

    /* Refreshes all controls based on current vehicle selection */
    private void refreshControls(Vehicle v)
    {
        // Vehicle information
        vehicleModelTF.setText(v.GetModel());
        vehicleLicensePlateTF.setText(v.GetLicensePlateNumber());
        vehicleWeightTF.setText(v.GetWeight().toString());
        vehicleCurVelocityTF.setText(String.valueOf(v.GetCurrentVelocity())); // Also updates dynamically
        vehicleMaxSpeedTF.setText(String.valueOf(v.GetMaxSpeed()));
        vehicleWheelDiaTF.setText(String.valueOf(v.GetWheelDiameter()));

        // Gearbox information
        gearboxNameTF.setText(v.GetGearbox().GetName());
        gearboxPriceTF.setText(v.GetGearbox().GetPrice().toString());
        gearboxWeightTF.setText(v.GetGearbox().GetWeight().toString());
        gearboxCurGearTF.setText(String.valueOf(v.GetGearbox().GetCurrentGear())); // Also updates dynamically

        // Engine information
        engineNameTF.setText(v.GetEngine().GetName());
        enginePriceTF.setText(v.GetEngine().GetPrice().toString());
        engineWeightTF.setText(v.GetEngine().GetWeight().toString());
        engineCurRotTF.setText(String.valueOf(v.GetEngine().GetCurrentRotationSpeed())); // Also updates dynamically


        // Clutch information
        clutchNameTF.setText(v.GetGearbox().GetClutch().GetName());
        clutchPriceTF.setText(v.GetGearbox().GetClutch().GetPrice().toString());
        clutchWeightTF.setText(v.GetGearbox().GetClutch().GetWeight().toString());
        clutchStateTF.setText(v.GetGearbox().GetClutch().IsPressed() ? "Pressed" : "Depressed"); // Also updates dynamically

        logger.debug("Refreshed all controls");
    }

    /* FXML initialization function */
    @FXML
    private void initialize()
    {
        allControls = List.of(
                vehicleModelTF, vehicleLicensePlateTF, vehicleWeightTF,
                vehicleCurVelocityTF, vehicleMaxSpeedTF, vehicleWheelDiaTF,
                gearboxNameTF, gearboxPriceTF, gearboxWeightTF, gearboxCurGearTF,
                engineNameTF, enginePriceTF, engineWeightTF, engineCurRotTF,
                clutchNameTF, clutchPriceTF, clutchWeightTF, clutchStateTF
        );

        allButtons = List.of(
                vehicleStartBtn, vehicleStopBtn,
                gearboxIncGearBtn, gearboxDecGearBtn,
                engineIncRotBtn, engineDecRotBtn,
                clutchDepressBtn, clutchPressBtn
        );

        logger.info("FXML main view initialized");
    }

    /* Clears all controls */
    private void clearControls()
    {
        allControls.forEach(tf -> {
            if (tf instanceof TextInputControl tic) tic.clear();
        });
    }

    @FXML
    private TextField vehicleModelTF;

    @FXML
    private TextField vehicleLicensePlateTF;

    @FXML
    private TextField vehicleWeightTF;

    @FXML
    private TextField vehicleCurVelocityTF;

    @FXML
    private TextField vehicleMaxSpeedTF;

    @FXML
    private TextField vehicleWheelDiaTF;

    @FXML
    private Button vehicleStartBtn;

    @FXML
    private void onVehicleStartBtn(javafx.event.ActionEvent event)
    {
        Vehicle v = currentVehicleCB.getSelectionModel().getSelectedItem();
        if (v != null)
        {
            try
            {
                v.Run();
                refreshDynamicControls(v);
                logger.info("Vehicle {} started!", v);
            }
            catch (VehicleStartupException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Vehicle startup");
                alert.setContentText("You attempted to run an already started vehicle!");
                alert.showAndWait();
                logger.fatal("VehicleStartupException when running Vehicle {} {}: {}", v.GetModel(), v.GetLicensePlateNumber(), String.valueOf(e));
            }
        }
    }

    @FXML
    private Button vehicleStopBtn;

    @FXML
    private void onVehicleStopBtn(javafx.event.ActionEvent event)
    {
        Vehicle v = currentVehicleCB.getSelectionModel().getSelectedItem();
        if (v != null)
        {
            try
            {
                v.Stop();
                refreshDynamicControls(v);
                logger.info("Vehicle {} stopped!", v);
            }
            catch (VehicleStartupException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Vehicle startup");
                alert.setContentText("You attempted to stop an already stopped vehicle!");
                alert.showAndWait();
                logger.fatal("VehicleStartupException when stopping Vehicle {} {}: {}", v.GetModel(), v.GetLicensePlateNumber(), String.valueOf(e));
            }
        }
    }

    @FXML
    private TextField gearboxNameTF;

    @FXML
    private TextField gearboxPriceTF;

    @FXML
    private TextField gearboxWeightTF;

    @FXML
    private TextField gearboxCurGearTF;

    @FXML
    private Button gearboxIncGearBtn;

    @FXML
    private void onGearboxIncGearBtn(javafx.event.ActionEvent event)
    {
        Vehicle v = currentVehicleCB.getSelectionModel().getSelectedItem();
        if (v != null)
        {
            v.GetGearbox().IncreaseGear();
            refreshDynamicControls(v);
            logger.info("Vehicle {} increased gear!", v);
        }
    }

    @FXML
    private Button gearboxDecGearBtn;

    @FXML
    private void onGearboxDecGearBtn(javafx.event.ActionEvent event)
    {
        Vehicle v = currentVehicleCB.getSelectionModel().getSelectedItem();
        if (v != null)
        {
            v.GetGearbox().DecreaseGear();
            refreshDynamicControls(v);
            logger.info("Vehicle {} decreased gear!", v);
        }
    }

    @FXML
    private TextField engineNameTF;

    @FXML
    private TextField enginePriceTF;

    @FXML
    private TextField engineWeightTF;

    @FXML
    private TextField engineCurRotTF;

    @FXML
    public Button engineIncRotBtn;

    @FXML
    private void onEngineIncRotBtn(javafx.event.ActionEvent event)
    {
        Vehicle v = currentVehicleCB.getSelectionModel().getSelectedItem();
        if (v != null)
        {
            v.GetEngine().IncreaseRotationSpeed();
            refreshDynamicControls(v);
            logger.info("Vehicle {} increased RPMs!", v);
        }
    }

    @FXML
    private Button engineDecRotBtn;

    @FXML
    private void onEngineDecRotBtn(javafx.event.ActionEvent event)
    {
        Vehicle v = currentVehicleCB.getSelectionModel().getSelectedItem();
        if (v != null)
        {
            v.GetEngine().DecreaseRotationSpeed();
            refreshDynamicControls(v);
            logger.info("Vehicle {} decreased RPMs!", v);
        }
    }

    @FXML
    private TextField clutchNameTF;

    @FXML
    private TextField clutchPriceTF;

    @FXML
    private TextField clutchWeightTF;

    @FXML
    private TextField clutchStateTF;

    @FXML
    private Button clutchPressBtn;

    @FXML
    private void onClutchPressBtn(javafx.event.ActionEvent event)
    {
        Vehicle v = currentVehicleCB.getSelectionModel().getSelectedItem();
        if (v != null)
        {
            v.GetGearbox().GetClutch().Press();
            refreshDynamicControls(v);
            logger.info("Vehicle {} clutch pressed!", v);
        }
    }

    @FXML
    private Button clutchDepressBtn;

    @FXML
    private void onClutchDepressBtn(javafx.event.ActionEvent event)
    {
        Vehicle v = currentVehicleCB.getSelectionModel().getSelectedItem();
        if (v != null)
        {
            v.GetGearbox().GetClutch().Release();
            refreshDynamicControls(v);
            logger.info("Vehicle {} clutch released!", v);
        }
    }

    @FXML
    private ComboBox<Vehicle> currentVehicleCB;

    @FXML
    private Button addNewVehicleBtn;

    @FXML
    private void onAddNewVehicleBtn(javafx.event.ActionEvent event)
    {
        try {
            logger.info("Loading New Vehicle View...");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("new-vehicle-view.fxml"));
            Parent root = loader.load();

            logger.info("New Vehicle View loaded");

            NewVehicleViewController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add new vehicle");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Get new vehicle
            Vehicle newVehicle = controller.getResult();
            if (newVehicle == null)
                return;

            // Create image for this vehicle
            logger.info("Loading default vehicle image...");
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("car.png")));
            ImageView imgV = new ImageView(img);

            logger.info("Default vehicle image loaded");

            // Setup random color
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setHue(Math.random() * 2 - 1);
            imgV.setEffect(colorAdjust);

            // Set image size
            imgV.setPreserveRatio(true);
            double maxWidth = 50;
            double maxHeight = 30;
            double scale = Math.min(maxWidth / img.getWidth(), maxHeight / img.getHeight());
            imgV.setFitWidth(img.getWidth() * scale);
            imgV.setFitHeight(img.getHeight() * scale);
            imgV.setOpacity(0.5);

            // Set image position
            imgV.setLayoutX(0);
            imgV.setLayoutY(0);

            // Add image to pane
            MainVehiclePane.getChildren().add(imgV);

            // Add vehicle and image to map
            vehicleImages.put(newVehicle, imgV);

            // Subscribe ourselves to the vehicle
            newVehicle.AddObserver(this);

            // Add our vehicle to our Manager
            vehicleManager.addVehicle(newVehicle);

            // Select new vehicle
            currentVehicleCB.getSelectionModel().select(newVehicle);

            logger.info("Added new vehicle: {}!", newVehicle);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Vehicle add");
            alert.setContentText("An error occurred while adding Vehicle! Check logs for more information");
            alert.showAndWait();
            logger.fatal("IOException when adding new Vehicle: {}", String.valueOf(e));
        }
    }

    @FXML
    private Button removeCurrentVehicleBtn;

    @FXML
    private void onRemoveCurrentVehicleBtn(javafx.event.ActionEvent event)
    {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Do you really want to remove this vehicle?");
        confirm.setContentText("This operation is irreversible.");

        Optional<ButtonType> result = confirm.showAndWait();
        if(result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        Vehicle v = currentVehicleCB.getSelectionModel().getSelectedItem();
        if (v != null) {
            vehicleManager.getVehicles().remove(v);
            currentVehicleCB.getSelectionModel().clearSelection();
            ImageView img = vehicleImages.get(v);
            img.setImage(null);
            MainVehiclePane.getChildren().remove(img);
            vehicleImages.remove(v);
            img = null;

            logger.info("Removed existing vehicle: {}!", v);
        }
    }

    @FXML
    public Pane MainVehiclePane;

    @FXML
    private void onMainVehiclePane(javafx.scene.input.MouseEvent event)
    {
        Vehicle v = currentVehicleCB.getSelectionModel().getSelectedItem();
        if (v == null) return;

        ImageView img = vehicleImages.get(v);
        if (img == null) return;

        // Get image size
        double imgWidth = img.getFitWidth();
        double imgHeight = img.getFitHeight();

        // Get pane size
        double paneWidth = MainVehiclePane.getWidth();
        double paneHeight = MainVehiclePane.getHeight();

        // Get mouse click coords
        double clickX = event.getX();
        double clickY = event.getY();

        // Clamp pixel coords
        double clampedX = Math.max(0, Math.min(clickX, paneWidth - imgWidth / 2));
        double clampedY = Math.max(0, Math.min(clickY, paneHeight - imgHeight / 2));

        // Calculate target coords
        float targetX = (float)(clampedX / METERS_TO_PIXELS);
        float targetY = (float)(clampedY / METERS_TO_PIXELS);

        v.GoTo(targetX, targetY);
        
        logger.info("Vehicle: {} is now moving to: {}, {}", v, targetX, targetY);
    }
}
