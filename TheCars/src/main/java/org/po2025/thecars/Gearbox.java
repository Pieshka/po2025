package org.po2025.thecars;

import java.math.BigDecimal;
import java.util.HashMap;

public class Gearbox extends Component
{
    private static HashMap<Integer, Float> GENERIC_GEAR_RATIOS = new HashMap<>();
    static {
        GENERIC_GEAR_RATIOS.put(0, 0.0f);
        GENERIC_GEAR_RATIOS.put(1, 3.78f);
        GENERIC_GEAR_RATIOS.put(2, 2.06f);
        GENERIC_GEAR_RATIOS.put(3, 1.34f);
        GENERIC_GEAR_RATIOS.put(4, 1.00f);
        GENERIC_GEAR_RATIOS.put(5, 0.85f);
        GENERIC_GEAR_RATIOS.put(6, 0.67f);
    }

    private int currentGear = 0;
    private float finalDrive;
    private HashMap<Integer, Float> gearRatios;
    private Clutch clutch;

    public Gearbox(Builder builder)
    {
        this.name = builder.name;
        this.weight = builder.weight;
        this.price = builder.price;
        this.finalDrive = builder.finalDrive;
        this.gearRatios = builder.gearRatios;
        this.clutch = builder.clutch;
    }

    public int GetCurrentGear() { return currentGear; }
    public float GetCurrentGearRatio()
    {
        return gearRatios.get(currentGear);
    }
    public float GetTotalRatio()
    {
        if(this.currentGear == 0 || this.GetCurrentGearRatio() == 0) return 0.0f;
        return this.GetCurrentGearRatio() * this.finalDrive;
    }

    public Clutch GetClutch()
    {
        return clutch;
    }

    @Override
    public BigDecimal GetWeight()
    {
        return super.GetWeight().add(clutch.GetWeight());
    }

    public void SetLowestGear()
    {
        this.currentGear = 0;
    }

    public void IncreaseGear()
    {
        if(!this.gearRatios.containsKey(this.currentGear+1)) return;
        this.currentGear++;
    }

    public void DecreaseGear()
    {
        if(!this.gearRatios.containsKey(this.currentGear-1)) return;
        this.currentGear--;
    }

    public static class Builder
    {
        private String name = "Generic Gearbox";
        private BigDecimal weight = BigDecimal.ONE;
        private BigDecimal price =  BigDecimal.ZERO;
        private float finalDrive = 4.1f;
        private HashMap<Integer, Float> gearRatios = GENERIC_GEAR_RATIOS;
        private Clutch clutch;

        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }

        public Builder setWeight(BigDecimal weight)
        {
            this.weight = weight;
            return this;
        }

        public Builder setPrice(BigDecimal price)
        {
            this.price = price;
            return this;
        }

        public Builder setFinalDrive(float finalDrive)
        {
            this.finalDrive = finalDrive;
            return this;
        }

        public Builder setGearRatios(HashMap<Integer, Float> gearRatios)
        {
            this.gearRatios = gearRatios;
            return this;
        }

        public Builder setClutch(Clutch clutch)
        {
            this.clutch = clutch;
            return this;
        }

        public Gearbox build()
        {
            return new Gearbox(this);
        }
    }
}
