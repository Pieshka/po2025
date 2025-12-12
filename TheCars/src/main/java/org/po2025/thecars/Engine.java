package org.po2025.thecars;

import java.math.BigDecimal;

public class Engine extends Component
{
    public static final float MINIMUM_ROTATION_SPEED = 1000.0f;

    private final float maximumRotationSpeed; /* In RPMs */
    private float currentRotationSpeed = 0;

    public Engine(Builder builder)
    {
        this.name = builder.name;
        this.weight = builder.weight;
        this.price = builder.price;
        this.maximumRotationSpeed = builder.maximumRotationSpeed;
    }

    public float GetMaximumRotationSpeed() { return maximumRotationSpeed; }
    public float GetCurrentRotationSpeed() { return currentRotationSpeed; }

    public void Run()
    {
        if(this.currentRotationSpeed > 0) return;
        this.currentRotationSpeed = MINIMUM_ROTATION_SPEED;
    }

    public void Stop()
    {
        this.currentRotationSpeed = 0;
    }

    public void IncreaseRotationSpeed()
    {
        if(this.currentRotationSpeed + 1 > maximumRotationSpeed) return;
        this.currentRotationSpeed = this.currentRotationSpeed + 100;
    }

    public void DecreaseRotationSpeed()
    {
        if (this.currentRotationSpeed - 1 < 0) return;
        this.currentRotationSpeed = this.currentRotationSpeed - 100;
    }

    public static class Builder
    {
        private String name = "Generic Engine";
        private BigDecimal weight = BigDecimal.ONE;
        private BigDecimal price = BigDecimal.ZERO;
        private float maximumRotationSpeed = MINIMUM_ROTATION_SPEED;

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
        public Builder setMaximumRotationSpeed(float maximumRotationSpeed)
        {
            this.maximumRotationSpeed = Math.max(maximumRotationSpeed, MINIMUM_ROTATION_SPEED);
            return this;
        }

        public Engine build()
        {
            return new Engine(this);
        }
    }
}
