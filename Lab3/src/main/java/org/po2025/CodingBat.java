package org.po2025;

public class CodingBat
{
    /* Warmup 1 */
    public static boolean sleepIn(boolean weekday, boolean vacation)
    {
        return !(weekday && !vacation);
    }

    public static boolean monkeyTrouble(boolean aSmile, boolean bSmile)
    {
        return aSmile == bSmile;
    }

    public static int sumDouble(int a, int b)
    {
        return a == b ? 2*(a+b) : a+b;
    }

    /* Array 2 */
    public static int countEvens(int[] nums) {
        int i = 0;
        for(int j=0; j<nums.length; j++)
            if(nums[j]%2==0) i++;
        return i;
    }

    public static int bigDiff(int[] nums) {
        int max = nums[0];
        for(int i = 1; i < nums.length; i++)
            max = Math.max(max,nums[i]);
        int min = nums[0];
        for(int i = 1; i < nums.length; i++)
            min = Math.min(min,nums[i]);

        return max-min;
    }

    /* String 1 */
    public static String helloName(String name) {
        return "Hello "+name+"!";
    }

    /* Main */
    public static void main(String[] args)
    {
        System.out.println("sleepIn(true,true): " + sleepIn(true, true));
        System.out.println("sleepIn(false,true): " + sleepIn(false, true));
        System.out.println("sleepIn(true,false): " + sleepIn(true, false));
        System.out.println("sleepIn(false,false): " + sleepIn(false, false));
        System.out.println();

        System.out.println("monkeyTrouble(true,true): " + monkeyTrouble(true, true));
        System.out.println("monkeyTrouble(false,true): " + monkeyTrouble(false, true));
        System.out.println("monkeyTrouble(true,false): " + monkeyTrouble(true, false));
        System.out.println("monkeyTrouble(false,false): " + monkeyTrouble(false, false));
        System.out.println();

        System.out.println("sumDouble(2,2): " + sumDouble(2, 2));
        System.out.println("sumDouble(1,2): " +  sumDouble(1, 2));
        System.out.println();

        System.out.println("countEvens([1,2,5,6,7,9,4,8]): " + countEvens(new int[]{1, 2, 5, 6, 7, 9, 4, 8}));
        System.out.println();

        System.out.println("bigDiff([1,2,5,6,7,9,4,8]): " + bigDiff(new int[]{1, 2, 5, 6, 7, 9, 4, 8}));
        System.out.println();

        System.out.println("helloName(\"Test\"): " + helloName("Test"));
        System.out.println("helloName(\"X\"): " + helloName("X"));
        System.out.println();
    }
}
