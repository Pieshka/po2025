package org.po2025.thecars;

import java.math.BigDecimal;

public abstract class Component
{
    protected String name;
    protected BigDecimal weight;
    protected BigDecimal price;

    public String GetName() { return name; }
    public BigDecimal GetWeight() { return weight; }
    public BigDecimal GetPrice() { return price; }

}
