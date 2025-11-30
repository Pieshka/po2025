package org.po2025;

import org.po2025.zadania.Zoo;

public class Main
{
    public static void main(String[] args)
    {
        Zoo zoo = new Zoo();
        int legs = zoo.legSum();

        System.out.println("Legs: " + legs);
    }
}
