package samochod;

public class Silnik extends Komponent
{
    private float max_obroty;
    private float cur_obroty;

    public Silnik(String nazwa, float maxObroty)
    {
        super(nazwa);
        this.setMaxObroty(maxObroty);
        this.setCurObroty(0);
    }

    public Silnik(String nazwa, float waga, float cena, float maxObroty)
    {
        super(nazwa, waga, cena);
        this.setMaxObroty(maxObroty);
        this.setCurObroty(0);
    }

    public boolean uruchom()
    {
        if(cur_obroty > 0) return false;
        cur_obroty = 1;
        return true;
    }

    public boolean zatrzymaj()
    {
        if(cur_obroty == 0) return false;
        cur_obroty = 0;
        return true;
    }

    public boolean zwiekszObroty()
    {
        if(cur_obroty+1 > max_obroty) return false;
        cur_obroty++;
        return true;
    }

    public boolean zmniejszObroty()
    {
        if(cur_obroty-1 < 0) return false;
        cur_obroty--;
        return true;
    }

    public float getMaxObroty()
    {
        return max_obroty;
    }

    public float getObroty()
    {
        return cur_obroty;
    }

    private void setMaxObroty(float maxObroty)
    {
        max_obroty = maxObroty >= 0 ? maxObroty : 0;
        cur_obroty = Math.min(cur_obroty, max_obroty);
    }

    private void setCurObroty(float curObroty)
    {
        cur_obroty = curObroty >= 0 ? curObroty : 0;
    }

}
