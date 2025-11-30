package org.po2025.animals;

public abstract class Animal
{
    public String name;
    public int legs;

    public abstract String getDescription();
    public void makeSound()
    {
        System.out.println("Making sound");
    }
}
