package org.example.samochodgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Samochod.*;

import java.util.ArrayList;
import java.util.List;

public class HelloController {

    public TextField modelTextField;

    public TextField nrRejestracyjnyTextField;

    public TextField wagaTextField;

    public TextField predkoscTextField;

    public Button startButton;
    public void onStartButton()
    {
        currentSamochod.wlacz();
    }

    public Button stopButton;
    public void onStopButton()
    {
        currentSamochod.wylacz();
    }

    public TextField skrzyniaNazwaTextField;

    public TextField skrzyniaCenaTextField;

    public TextField skrzyniaWagaTextField;

    public TextField skrzyniaBiegTextField;

    public Button moreBiegButton;
    public void onMoreBiegButton()
    {
        currentSamochod.Skrzynia.zwiekszBieg();
    }

    public Button lessBiegButton;
    public void onLessBiegButton()
    {
        currentSamochod.Skrzynia.zmniejszBieg();
    }

    public TextField silnikNazwaTextField;

    public TextField silnikCenaTextField;

    public TextField silnikWagaTextField;

    public TextField silnikObrotyTextField;

    public Button moreGazButton;
    public void onMoreGazButton()
    {
        currentSamochod.Silnik.zwiekszObroty();
    }

    public Button lessGazButton;
    public void onLessGazButton()
    {
        currentSamochod.Silnik.zmniejszObroty();
    }

    public TextField sprzegloNameTextField;

    public TextField sprzegloCenaTextField;

    public TextField sprzegloWagaTextField;

    public TextField sprzegloStanTextField;

    public Button pressSprzegloButton;
    public void onPressSprzegloButton()
    {
        currentSamochod.Skrzynia.Sprzeglo.wcisnij();
    }

    public Button unpressSprzegloButton;
    public void onUnpressSprzegloButton()
    {
        currentSamochod.Skrzynia.Sprzeglo.zwolnij();
    }

    public ComboBox samochodComboBox;
    ObservableList<Samochod> samochodList = FXCollections.observableList(new ArrayList<Samochod>());
    Samochod currentSamochod;
    public void onSamochodComboBoxChange() {
        currentSamochod = (Samochod) samochodComboBox.getValue();
        modelTextField.setText(currentSamochod.getModel());
    }

    public Button addNewCarButton;
    public void onAddNewCarButton()
    {
        samochodList.add(new Samochod("Samochód","12345", 300f));
        samochodComboBox.setItems(samochodList);
    }

    public Button removeCarButton;
    public void onRemoveCarButton()
    {
        samochodList.remove(currentSamochod);
    }
}
