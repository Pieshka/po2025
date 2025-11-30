package org.po2025.animals;

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

    @Override
    public void makeSound()
    {
        System.out.println("Parrot sound");
    }
}
