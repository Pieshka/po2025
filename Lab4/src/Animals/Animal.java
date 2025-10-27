package Animals;

public abstract class Animal
{
    public String name;
    public int legs;

    public abstract String getDescription();
    public void makeSound()
    {
        System.out.println("Making Sound");
    };
}
