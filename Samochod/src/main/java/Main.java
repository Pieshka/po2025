import samochod.Samochod;

void main()
{
    boolean ret;
    Samochod samochod = new Samochod("Ursus C360", "KR1248",240);

    ret = samochod.wlacz();
    System.out.println("Uruchomienie samochodu: " + ret);

    ret = samochod.Skrzynia.zmniejszBieg();
    System.out.println("Redukcja biegu: " + ret + ", aktualny: " + samochod.Skrzynia.getAktualnyBieg());

    ret = samochod.Skrzynia.zwiekszBieg();
    System.out.println("Wbicie biegu: " + ret + ", aktualny: " + samochod.Skrzynia.getAktualnyBieg());

    ret = samochod.Skrzynia.zwiekszBieg();
    System.out.println("Wbicie biegu: " + ret + ", aktualny: " + samochod.Skrzynia.getAktualnyBieg());

    ret = samochod.Skrzynia.Sprzeglo.wcisnij();
    System.out.println("Wciśnięcie sprzęgła: " + ret + ", aktualne: " + samochod.Skrzynia.Sprzeglo.getStan_sprzegla());

    System.out.println("\nStan samochodu:\n");
    System.out.println("Model samochodu: " + samochod.getModel());
    System.out.println("Nr rejestracyjny: " + samochod.getNrRejestracyjny());
    System.out.println("Aktualna pozycja: " + samochod.getPozycja());
    System.out.println("Aktualny bieg: " + samochod.Skrzynia.getAktualnyBieg());
    System.out.println("Stan sprzęgła: " + samochod.Skrzynia.Sprzeglo.getStan_sprzegla());
    System.out.println("Obroty silnika: " + samochod.Silnik.getObroty());
}