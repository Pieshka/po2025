package org.po2025.animals;

public class Snake extends Animal
{
    public Snake()
    {
        name = "Snake";
        legs = 0;
    }

    public String getDescription()
    {
        return "This is a Snake with name " + name + " and " + legs + " legs.";
    }

    @Override
    public void makeSound()
    {
        System.out.println("Snake sound");
    }
}
