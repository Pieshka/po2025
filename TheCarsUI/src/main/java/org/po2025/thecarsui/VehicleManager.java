package org.po2025.thecarsui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.po2025.thecars.Vehicle;

public class VehicleManager
{
    private final ObservableList<Vehicle> vehicles = FXCollections.observableArrayList();

    public ObservableList<Vehicle> getVehicles()
    {
        return vehicles;
    }

    public void addVehicle(Vehicle vehicle)
    {
        vehicles.add(vehicle);
    }

    public void removeVehicles(Vehicle vehicle)
    {
        vehicles.remove(vehicle);
    }
}
