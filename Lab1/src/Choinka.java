public class Choinka
{
	public static void main(String[] args)
	{
		if(args.length < 1) return;
		int star_count = Integer.parseInt(args[0]);
		for(int i = 1; i <= star_count; i++)
		{
			for(int j = star_count-i; j > 0; j--)
			{
				System.out.print(' ');
			}
			for(int j = 0; j < (i-1)*2 + 1; j++)
			{
				System.out.print('*');
			}
			System.out.println();
		}
	}
}