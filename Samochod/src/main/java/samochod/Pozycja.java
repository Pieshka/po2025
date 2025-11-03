package samochod;

public class Pozycja
{
    public double x;
    public double y;

    public Pozycja(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
