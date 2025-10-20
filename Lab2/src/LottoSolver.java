import java.util.ArrayList;
import java.util.Random;

public class LottoSolver
{
    static ArrayList<Integer> getRandomNumbers()
    {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random rand = new Random();
        while(numbers.size() < 6)
        {
            int nextRandom = rand.nextInt(1,49);
            if (!numbers.contains(nextRandom))
                numbers.add(nextRandom);
        }

        return numbers;
    }

    public static void main(String[] args)
    {
        ArrayList<Integer> numbers = getRandomNumbers();

        int i;
        long timeBegin = System.currentTimeMillis();
        for(i = 0; !numbers.containsAll(getRandomNumbers()); i++);
        long timeEnd = System.currentTimeMillis();

        System.out.println("Time taken: " + (timeEnd - timeBegin) + " ms");
        System.out.println("Total loops: " + i);
    }
}
