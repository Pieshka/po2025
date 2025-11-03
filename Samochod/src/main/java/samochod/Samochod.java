package samochod;

public class Samochod
{
    private boolean stan_wlaczenia;
    private String nr_rejestracyjny;
    private String model;
    private float max_predkosc;

    private Pozycja aktualna_pozycja;

    public SkrzyniaBiegow Skrzynia;
    public Silnik Silnik;

    public Samochod(String model, String nr_rejestracyjny, float max_predkosc)
    {
        this.model = model;
        this.nr_rejestracyjny = nr_rejestracyjny;
        this.max_predkosc = max_predkosc;
        stan_wlaczenia = false;
        aktualna_pozycja = new Pozycja(0,0);
        Skrzynia = new SkrzyniaBiegow("Skrzynia Biegów", 100, 1000, 7);
        Silnik = new Silnik("Silnik", 100, 1000, 5000);
    }

    public float getWaga()
    {
        return Skrzynia.getWaga() + Silnik.getWaga();
    }

    public float getPredkosc()
    {
        // Boilerplate
        return 0;
    }

    public Pozycja getPozycja()
    {
        return aktualna_pozycja;
    }

    public String getNrRejestracyjny()
    {
        return nr_rejestracyjny;
    }

    public String getModel()
    {
        return model;
    }

    public void jedzDo(Pozycja cel){}

    public boolean wlacz()
    {
        if(stan_wlaczenia) return false;
        if(!Skrzynia.Sprzeglo.wcisnij()) return false;
        if(!Silnik.uruchom()) return false;
        if(!Skrzynia.Sprzeglo.zwolnij()) return false;

        stan_wlaczenia = true;
        return true;
    }

    public boolean wylacz()
    {
        if(!stan_wlaczenia) return false;
        if(!Skrzynia.Sprzeglo.wcisnij()) return false;
        if(!Silnik.zatrzymaj()) return false;
        if(!Skrzynia.Sprzeglo.zwolnij()) return false;

        stan_wlaczenia = false;
        return true;
    }
}
