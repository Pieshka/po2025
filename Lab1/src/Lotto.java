import java.util.HashSet;
import java.util.Random;

public class Lotto
{
    public static void main(String[] args)
    {
        HashSet<Integer> set = new HashSet<>();
        Random rand = new Random();
        while(set.size() < 6)
        {
            set.add(rand.nextInt(1,50));
        }
        System.out.println(set);
    }
}
