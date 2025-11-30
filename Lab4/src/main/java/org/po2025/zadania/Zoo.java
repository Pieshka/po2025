package org.po2025.zadania;

import org.po2025.animals.*;

import java.util.Random;

public class Zoo
{
    Animal[] animals = new Animal[5];

    public Zoo()
    {
        Random rand = new Random();
        for(int i = 0; i < animals.length; i++)
        {
            int r = rand.nextInt(0,3);
            if(r == 0)
                animals[i] = new Dog();
            else if(r == 1)
                animals[i] = new Snake();
            else
                animals[i] = new Parrot();

            System.out.println(animals[i].getDescription());
            animals[i].makeSound();
        }
    }

    public int legSum()
    {
        int sum = 0;
        for(Animal a : animals) sum += a.legs;

        return sum;
    }
}
