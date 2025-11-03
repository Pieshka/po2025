package samochod;

public class SkrzyniaBiegow extends Komponent
{
    private int aktualny_bieg;
    private int max_bieg;
    private int aktualne_przelozenie;

    public Sprzeglo Sprzeglo;

    SkrzyniaBiegow(String name, int maxBieg)
    {
        super(name);
        this.Sprzeglo = new Sprzeglo("Sprzęgło", 15, 500);
        this.setMaxBieg(maxBieg);
        aktualny_bieg = 1;
        aktualne_przelozenie = 0;
    }

    SkrzyniaBiegow(String name, float waga, float cena, int maxBieg)
    {
        super(name, waga, cena);
        this.Sprzeglo = new Sprzeglo("Sprzęgło", 15, 500);
        this.setMaxBieg(maxBieg);
        aktualny_bieg = 1;
        aktualne_przelozenie = 0;
    }

    public int getMaxBieg()
    {
        return max_bieg;
    }

    public int getAktualnyBieg()
    {
        return aktualny_bieg;
    }

    public int getAktualnePrzelozenie()
    {
        return aktualne_przelozenie;
    }

    public boolean zwiekszBieg()
    {
        if(aktualny_bieg+1 > max_bieg) return false;
        if(!Sprzeglo.wcisnij()) return false;
        aktualny_bieg++;
        if(!Sprzeglo.zwolnij()){aktualny_bieg--; return false;}
        return true;
    }

    public boolean zmniejszBieg()
    {
        if(aktualny_bieg-1 < 1) return false;
        if(!Sprzeglo.wcisnij()) return false;
        aktualny_bieg--;
        if(!Sprzeglo.zwolnij()){aktualny_bieg++; return false;}
        return true;
    }

    public float getWaga()
    {
        return super.getWaga() + Sprzeglo.getWaga();
    }

    private void setMaxBieg(int newBieg)
    {
        max_bieg = newBieg > 0 ? newBieg : 1;
    }
}
