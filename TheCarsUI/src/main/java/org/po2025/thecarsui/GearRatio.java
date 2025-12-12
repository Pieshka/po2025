package org.po2025.thecarsui;

import javafx.beans.property.*;

public class GearRatio
{
    private final IntegerProperty gear;
    private final FloatProperty ratio;

    public GearRatio(int gear, float ratio)
    {
        this.gear = new SimpleIntegerProperty(gear);
        this.ratio = new SimpleFloatProperty(ratio);
    }

    public int getGear() { return gear.get(); }
    public void setGear(int value) { gear.set(value); }
    public IntegerProperty gearProperty() { return gear; }

    public float getRatio() { return ratio.get(); }
    public void setRatio(float value) { ratio.set(value); }
    public FloatProperty ratioProperty() { return ratio; }
}
