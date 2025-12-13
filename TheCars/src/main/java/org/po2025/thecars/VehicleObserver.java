package org.po2025.thecars;

import java.awt.geom.Point2D;

public interface VehicleObserver
{
    void positionChanged(Vehicle vehicle, Point2D newPosition, double angle);
}
