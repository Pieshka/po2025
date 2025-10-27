package Animals;

public class Parrot extends Animal
{
    public Parrot()
    {
        name = "Parrot";
        legs = 2;
    }

    public String getDescription()
    {
        return "This is a Parrot with name " + name + " and " + legs + " legs.";
    }

    public void makeSound()
    {
        System.out.println("Parrot sound");
    }
}
