package samochod;

public class Sprzeglo extends Komponent
{
    private boolean stan_sprzegla;

    public Sprzeglo(String nazwa)
    {
        super(nazwa);
        this.stan_sprzegla = false;
    }

    public Sprzeglo(String nazwa, float waga, float cena)
    {
        super(nazwa, waga, cena);
        this.stan_sprzegla = false;
    }

    public boolean getStan_sprzegla()
    {
        return stan_sprzegla;
    }

    public boolean wcisnij()
    {
        if(stan_sprzegla) return false;
        stan_sprzegla = true;
        return true;
    }

    public boolean zwolnij()
    {
        if(!stan_sprzegla) return false;
        stan_sprzegla = false;
        return true;
    }
}
