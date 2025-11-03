package samochod;

public abstract class Komponent
{
    private String nazwa;
    private float waga;
    private float cena;

    public Komponent(String nazwa)
    {
        this.setNazwa(nazwa);
        this.setWaga(1);
        this.setCena(1);
    }

    public Komponent(String nazwa, float waga, float cena)
    {
        this.setNazwa(nazwa);
        this.setWaga(waga);
        this.setCena(cena);
    }

    public String getNazwa()
    {
        return nazwa;
    }

    public float getWaga()
    {
        return waga;
    }

    public float getCena()
    {
        return cena;
    }

    public void setNazwa(String newNazwa)
    {
        nazwa = newNazwa;
    }

    // In kg
    public void setWaga(float newWaga)
    {
        waga = newWaga > 0 ?  newWaga : 0;
    }

    // In ZŁ
    public void setCena(float newCena)
    {
        cena = newCena >= 0 ?  newCena : 0;
    }
}
