package org.po2025;

import java.util.ArrayList;
import java.util.Random;

public class Lotto
{
    public static void main(String[] args)
    {
        if(args.length < 6)
        {
            System.out.println("Usage: java Lotto <number> <number> <number> <number> <number> <number>");
            System.exit(1);
        }

        for(int i = 0; i < args.length; i++)
        {
            if(!(1 <= Integer.parseInt(args[i]) && Integer.parseInt(args[i]) <= 49))
            {
                System.out.println("Liczba na pozycji " + (i+1) + " nie jest z przedziału 1-49!");
                System.exit(1);
            }
        }

        ArrayList<Integer> userNumbers = new ArrayList<Integer>();
        for(String str : args) userNumbers.add(Integer.parseInt(str));

        ArrayList<Integer> numbers = new ArrayList<>();
        Random rand = new Random();
        while(numbers.size() < 6)
        {
            int nextNumber = rand.nextInt(1,50);
            if(!numbers.contains(nextNumber))
                numbers.add(nextNumber);
        }

        int countUserHits = 0;
        for(int i = 0; i < numbers.size(); i++)
            if(userNumbers.contains(numbers.get(i)))
                countUserHits++;

        System.out.println("Twoje typy: " + userNumbers);
        System.out.println("Wylosowane liczby: " + numbers);
        System.out.println("Liczba trafień: " + countUserHits);
    }
}
