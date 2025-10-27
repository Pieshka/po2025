import Animals.Dog;
import Zadania.Zoo;

public class Main
{
    public static void main(String[] args)
    {
        Zoo zoo = new Zoo();
        int legs = zoo.legSum();
        System.out.println("Legs: " + legs);
    }
}
