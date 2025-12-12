package org.po2025.thecarsui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.po2025.thecars.Clutch;
import org.po2025.thecars.Engine;
import org.po2025.thecars.Gearbox;
import org.po2025.thecars.Vehicle;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NewVehicleViewController
{
    private static final Logger log = Logger.getLogger("NewVehicleViewController");
    private Vehicle result = null;

    public Vehicle getResult() { return result; }

    /* Build new float text formatter */
    private <T> TextFormatter<T> GetNewFloatTextFormatter()
    {
        return new TextFormatter<>(c -> {
            String newText = c.getControlNewText();
            if (newText.isEmpty()) return c;
            try {
                Float.parseFloat(newText);
                return c;
            } catch (NumberFormatException e) {
                return null; // Rollback
            }
        });
    }

    /* FXML controls and functions */
    @FXML
    private void initialize()
    {
        // Prepopulate table data
        gRatiosTV.getItems().addAll(
          new GearRatio(1, Gearbox.GENERIC_GEAR_RATIOS.get(1)),
          new GearRatio(2, Gearbox.GENERIC_GEAR_RATIOS.get(2)),
          new GearRatio(3, Gearbox.GENERIC_GEAR_RATIOS.get(3)),
          new GearRatio(4, Gearbox.GENERIC_GEAR_RATIOS.get(4)),
          new GearRatio(5, Gearbox.GENERIC_GEAR_RATIOS.get(5)),
          new GearRatio(6, Gearbox.GENERIC_GEAR_RATIOS.get(6))
        );

        // Set Gear column as view-only
        gGearColumn.setCellValueFactory(cellData -> cellData.getValue().gearProperty().asObject());
        gGearColumn.setEditable(false);

        // Prepare Ratio column
        gRatioColumn.setCellValueFactory(cellData -> cellData.getValue().ratioProperty().asObject());
        gRatioColumn.setCellFactory(col -> new TableCell<>() {
            private final TextField tf = new TextField();

            {
                /* Float-only Text Formatter */
                tf.setTextFormatter(GetNewFloatTextFormatter());

                // Commit action on Enter
                tf.setOnAction(evt -> commitEdit(parseFloat(tf.getText())));

                // Commit action on FocusLost
                tf.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if(!isNowFocused) commitEdit(parseFloat(tf.getText()));
                });
            }

            // Show textfield in place of row
            @Override
            protected void updateItem(Float item, boolean empty)
            {
                super.updateItem(item, empty);
                if(empty)
                    setGraphic(null);
                else
                {
                    tf.setText(item.toString());
                    setGraphic(tf);
                }
            }

            private float parseFloat(String text) {
                try {
                    return Float.parseFloat(text);
                } catch (NumberFormatException e) {
                    return getItem(); // Leave old value if incorrect
                }
            }
        });

        gRatiosTV.setEditable(true);

        // Apply text formatter to all floats
        vMaxSpeedTF.setTextFormatter(GetNewFloatTextFormatter());
        vWheelDiameterTF.setTextFormatter(GetNewFloatTextFormatter());
        gPriceTF.setTextFormatter(GetNewFloatTextFormatter());
        gWeightTF.setTextFormatter(GetNewFloatTextFormatter());
        gFinalDriveTF.setTextFormatter(GetNewFloatTextFormatter());
        ePriceTF.setTextFormatter(GetNewFloatTextFormatter());
        eWeightTF.setTextFormatter(GetNewFloatTextFormatter());
        eMaxSpeedTF.setTextFormatter(GetNewFloatTextFormatter());
        cPriceTF.setTextFormatter(GetNewFloatTextFormatter());
        cWeightTF.setTextFormatter(GetNewFloatTextFormatter());

        // Set default values
        Random rnd = new Random();

        vMaxSpeedTF.setText("100");
        vWheelDiameterTF.setText("0.1");
        gPriceTF.setText(String.valueOf(Math.round(rnd.nextFloat(1,1000))));
        gWeightTF.setText(String.valueOf(Math.round(rnd.nextFloat(1,1000))));
        ePriceTF.setText(String.valueOf(Math.round(rnd.nextFloat(1,1000))));
        eWeightTF.setText(String.valueOf(Math.round(rnd.nextFloat(1,1000))));
        cPriceTF.setText(String.valueOf(Math.round(rnd.nextFloat(1,1000))));
        cWeightTF.setText(String.valueOf(Math.round(rnd.nextFloat(1,1000))));
        gFinalDriveTF.setText(String.valueOf(Gearbox.GENERIC_FINAL_DRIVE));
        eMaxSpeedTF.setText(String.valueOf(Engine.MINIMUM_ROTATION_SPEED));
    }

    @FXML
    public TextField vModelTF;

    @FXML
    public TextField vLicensePlateNumberTF;

    @FXML
    public TextField vMaxSpeedTF;

    @FXML
    public TextField vWheelDiameterTF;

    @FXML
    public TextField gNameTF;

    @FXML
    public TextField gPriceTF;

    @FXML
    public TextField gWeightTF;

    @FXML
    public TextField gFinalDriveTF;

    @FXML
    public TableView<GearRatio> gRatiosTV;

    @FXML
    public TableColumn<GearRatio, Integer> gGearColumn;

    @FXML
    public TableColumn<GearRatio, Float> gRatioColumn;

    @FXML
    public TextField eNameTF;

    @FXML
    public TextField ePriceTF;

    @FXML
    public TextField eWeightTF;

    @FXML
    public TextField eMaxSpeedTF;

    @FXML
    public TextField cNameTF;

    @FXML
    public TextField cPriceTF;

    @FXML
    public TextField cWeightTF;

    @FXML
    public Button okBtn;

    @FXML
    private void onOkBtn()
    {
        // I don't like this
        if(vModelTF.getText().isEmpty() || vLicensePlateNumberTF.getText().isEmpty() || vMaxSpeedTF.getText().isEmpty() ||
        vWheelDiameterTF.getText().isEmpty() || gNameTF.getText().isEmpty() || gPriceTF.getText().isEmpty() ||
        gWeightTF.getText().isEmpty() || gFinalDriveTF.getText().isEmpty() || eNameTF.getText().isEmpty() ||
        ePriceTF.getText().isEmpty() || eMaxSpeedTF.getText().isEmpty() || cNameTF.getText().isEmpty() || cPriceTF.getText().isEmpty() ||
        cWeightTF.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not all fields are populated");
            alert.setContentText("Please populate all available fields.");
            alert.showAndWait();
            return;
        }

        try
        {
            Clutch clutch = new Clutch.Builder()
                    .setName(cNameTF.getText())
                    .setPrice(BigDecimal.valueOf(Float.parseFloat(cPriceTF.getText())))
                    .setWeight(BigDecimal.valueOf(Float.parseFloat(cWeightTF.getText())))
                    .build();

            Engine engine = new Engine.Builder()
                    .setName(eNameTF.getText())
                    .setPrice(BigDecimal.valueOf(Float.parseFloat(ePriceTF.getText())))
                    .setWeight(BigDecimal.valueOf(Float.parseFloat(eWeightTF.getText())))
                    .setMaximumRotationSpeed(Float.parseFloat(eMaxSpeedTF.getText()))
                    .build();

            Map<Integer, Float> gearRatios = gRatiosTV.getItems().stream().collect(
                    Collectors.toMap(GearRatio::getGear, GearRatio::getRatio)
            ); gearRatios.put(0, 0.0f);

            Gearbox gearbox = new Gearbox.Builder()
                    .setClutch(clutch)
                    .setName(gNameTF.getText())
                    .setPrice(BigDecimal.valueOf(Float.parseFloat(gPriceTF.getText())))
                    .setWeight(BigDecimal.valueOf(Float.parseFloat(gWeightTF.getText())))
                    .setFinalDrive(Float.parseFloat(gFinalDriveTF.getText()))
                    .setGearRatios(gearRatios)
                    .build();

            result = new Vehicle.Builder()
                    .setModel(vModelTF.getText())
                    .setLicensePlateNumber(vLicensePlateNumberTF.getText())
                    .setWheelDiameter(Float.parseFloat(vWheelDiameterTF.getText()))
                    .setMaxSpeed(Float.parseFloat(vMaxSpeedTF.getText()))
                    .setGearbox(gearbox)
                    .setEngine(engine)
                    .build();

            ((Stage) okBtn.getScene().getWindow()).close();
        }
         catch (NumberFormatException e)
         {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error!");
             alert.setHeaderText("Vehicle add");
             alert.setContentText("An error occurred while adding Vehicle! Check logs for more information");
             log.log(Level.SEVERE, "NumberFormatException when adding new Vehicle: " + e);
         }
    }

    @FXML
    public Button cancelBtn;

    @FXML
    private void onCancelBtn(ActionEvent event)
    {
        result = null;
        ((Stage) cancelBtn.getScene().getWindow()).close();
    }
}
