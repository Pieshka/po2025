package org.po2025.thecars;

import java.util.List;

public class VehicleUpdater implements Runnable
{
    private final List<Vehicle> vehicles;
    private final double deltaTime;

    public VehicleUpdater(List<Vehicle> vehicles, double deltaTime)
    {
        this.vehicles = vehicles;
        this.deltaTime = deltaTime;
    }

    @Override
    public void run()
    {
        for (Vehicle vehicle : vehicles)
        {
            vehicle.UpdateCurrentPosition(deltaTime);
        }
    }
}
