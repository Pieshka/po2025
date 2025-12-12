package org.po2025.thecars;

import java.util.List;

public class VehicleUpdater implements Runnable
{
    private final List<Vehicle> vehicles;
    private long lastTime = System.nanoTime();

    public VehicleUpdater(List<Vehicle> vehicles)
    {
        this.vehicles = vehicles;
    }

    @Override
    public void run()
    {
        long now = System.nanoTime();
        double deltaTime = (now - lastTime) / 1e9; // Seconds
        lastTime = now;

        for (Vehicle vehicle : vehicles)
        {
            vehicle.UpdateCurrentPosition(deltaTime);
        }
    }
}
