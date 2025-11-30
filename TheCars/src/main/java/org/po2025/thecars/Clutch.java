package org.po2025.thecars;

import java.math.BigDecimal;

public class Clutch extends Component
{
    private boolean isPressed = false;

    public Clutch(Builder builder)
    {
        this.name = builder.name;
        this.weight = builder.weight;
        this.price = builder.price;
    }

    public boolean IsPressed() { return isPressed; }

    public void Press()
    {
        isPressed = true;
    }

    public void Release()
    {
        isPressed = false;
    }

    public static class Builder
    {
        private String name = "Generic Clutch";
        private BigDecimal weight = BigDecimal.ONE;
        private BigDecimal price = BigDecimal.ZERO;

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

        public Clutch build()
        {
            return new Clutch(this);
        }
    }
}
