package org.po2025;

import java.util.ArrayList;
import java.util.Random;

public class LottoSolver
{
    public static ArrayList<Integer> getRandomNumbers()
    {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random rand = new Random();
        while(numbers.size() < 6)
        {
            int nextRandom = rand.nextInt(1,50);
            if (!numbers.contains(nextRandom))
                numbers.add(nextRandom);
        }

        return numbers;
    }

    public static void main(String[] args)
    {
        ArrayList<Integer> numbers = getRandomNumbers();
        int i;
        long startTime = System.nanoTime();
        for(i=0;!numbers.containsAll(getRandomNumbers());i++);
        long endTime = System.nanoTime();

        System.out.println("Time taken: " + (endTime - startTime)/1000000 + " ms");
        System.out.println("Total loops: " + i);
    }
}
