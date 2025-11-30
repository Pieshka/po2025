package org.po2025;

public class Choinka
{
    public static void main(String[] args)
    {
        if(args.length < 1) return;
        int starCount = Integer.parseInt(args[0]);
        for(int i = 1; i <= starCount; i++)
        {
            for(int j = starCount-i; j > 0; j--)
            {
                System.out.print(" ");
            }

            for(int j = 0; j < (i-1)*2 + 1; j++)
            {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
