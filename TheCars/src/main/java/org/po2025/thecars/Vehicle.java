package org.po2025.thecars;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Vehicle
{
    private String model;
    private String licensePlateNumber;
    private float maxSpeed;
    private float wheelDiameter;
    private Point2D actualPosition = new Point2D.Float(0.0f, 0.0f);
    private Point2D newPosition = new Point2D.Float(0.0f, 0.0f);
    private float currentVelocity = 0.0f;
    private Engine engine;
    private Gearbox gearbox;

    private final List<VehicleObserver> observers = new ArrayList<>();

    public Vehicle(Builder builder)
    {
        this.model = builder.model;
        this.licensePlateNumber = builder.licensePlateNumber;
        this.maxSpeed = builder.maxSpeed;
        this.wheelDiameter = builder.wheelDiameter;
        this.engine = builder.engine;
        this.gearbox = builder.gearbox;
    }

    public String GetModel() { return model; }
    public String GetLicensePlateNumber() { return licensePlateNumber; }
    public float GetMaxSpeed() { return maxSpeed; }
    public float GetWheelDiameter() { return wheelDiameter; }
    public Point2D GetActualPosition() { return actualPosition; }
    public Engine GetEngine() { return engine; }
    public Gearbox GetGearbox() { return gearbox; }

    public BigDecimal GetWeight()
    {
        return gearbox.GetWeight().add(engine.GetWeight());
    }

    public boolean IsRunning()
    {
        return engine.GetCurrentRotationSpeed() == 0;
    }

    public void GoTo(float x, float y)
    {
        newPosition = new Point2D.Float(x,y);
    }

    public float GetCurrentVelocity()
    {
        if(gearbox.GetClutch().IsPressed() || gearbox.GetTotalRatio() <= 0)
            return this.currentVelocity;

        float engineRPM = engine.GetCurrentRotationSpeed();
        float wheelRPM = engineRPM / gearbox.GetTotalRatio();
        float wheelCircumference = (float) (2 * Math.PI * wheelDiameter);
        this.currentVelocity = (wheelCircumference * wheelRPM) / 60.0f;
        if(this.currentVelocity > this.maxSpeed) this.currentVelocity = this.maxSpeed;

        return this.currentVelocity;
    }

    public void AddObserver(VehicleObserver observer)
    {
        observers.add(observer);
    }

    public void RemoveObserver(VehicleObserver observer)
    {
        observers.remove(observer);
    }

    public void UpdateCurrentPosition(double deltaTime)
    {
        double dxTotal = newPosition.getX() - actualPosition.getX();
        double dyTotal = newPosition.getY() - actualPosition.getY();
        double distance = Math.hypot(dxTotal, dyTotal);

        if(distance < 0.001)
        {
            actualPosition.setLocation(newPosition);
            this.currentVelocity = 0; // We have no more velocity
            return; // We are at the destination
        }

        double move = GetCurrentVelocity() * deltaTime;
        double ratio = Math.min(1.0, move/distance);
        double dx = dxTotal * ratio;
        double dy = dyTotal * ratio;
        double angle = Math.toDegrees(Math.atan2(dy,dx));

        actualPosition.setLocation(actualPosition.getX() + dx, actualPosition.getY() + dy);

        for (VehicleObserver observer : observers)
        {
            observer.positionChanged(this, actualPosition, angle);
        }
    }

    public void Run()
    {
        gearbox.SetLowestGear();
        gearbox.IncreaseGear();
        engine.Run();
    }

    public void Stop()
    {
        engine.Stop();
        gearbox.SetLowestGear();
        this.currentVelocity = 0; // Let's assume we are pressing the brake on stop
    }

    @Override
    public String toString()
    {
        return GetModel() + " " + GetLicensePlateNumber();
    }

    public static class Builder
    {
        private String model = "Generic Vehicle";
        private String licensePlateNumber = "A1234";
        private float maxSpeed = 100; // m/s
        private float wheelDiameter = 0.1f; // m
        private Engine engine;
        private Gearbox gearbox;

        public Builder setModel(String model)
        {
            this.model = model;
            return this;
        }

        public Builder setLicensePlateNumber(String plateNumber)
        {
            this.licensePlateNumber = plateNumber;
            return this;
        }

        public Builder setMaxSpeed(float speed)
        {
            this.maxSpeed = speed;
            return this;
        }

        public Builder setWheelDiameter(float circumference)
        {
            this.wheelDiameter = circumference;
            return this;
        }

        public Builder setEngine(Engine engine)
        {
            this.engine = engine;
            return this;
        }

        public Builder setGearbox(Gearbox gearbox)
        {
            this.gearbox = gearbox;
            return this;
        }

        public Vehicle build()
        {
            return new Vehicle(this);
        }
    }
}
