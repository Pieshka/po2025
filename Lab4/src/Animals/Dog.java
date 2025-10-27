package Animals;

public class Dog extends Animal
{
    public Dog()
    {
        name = "Dog";
        legs = 4;
    }

    public String getDescription()
    {
        return "This is a Dog with name " + name + " and " + legs + " legs.";
    }

    public void makeSound()
    {
        System.out.println("Dog sound");
    }
}
