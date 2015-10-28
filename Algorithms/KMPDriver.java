import java.util.Scanner;

public class KMPDriver
{
   public static void main(String[] args)
   {
      Scanner scan = new Scanner(System.in);
      System.out.println("Enter a search string followed by a string to search for (or -1 to stop)");
      String str = "";
      while(!str.equals("-1"))
      {
         str = scan.nextLine();
         String pat = scan.nextLine();
         int n = str.length();
      
         int[] kmpTable = KMP(pat);
      
         int m = kmpSearch(str, pat, kmpTable);
         System.out.println(m);
      }
   }
      
   public static int kmpSearch(String str, String pat, int[] table)
   {
      int i = 0;
         
      for(int j = 0; j<str.length(); j++)
      {
         if(pat.charAt(i)==str.charAt(j))
         {
            if(i==pat.length()-1)
               return j-i;
            i++;
         }
         else
         {
            while(table[i] > -1 && pat.charAt(i)!=str.charAt(j))
            {
               i = table[i];
            }
            if(pat.charAt(i)==str.charAt(j))
               i++;
         }
      }
      return -1;
   }
   public static int[] KMP(String str)
   {
      int n = str.length();
      char[] chars = str.toCharArray();
      int[] table = new int[n+1];
      
      table[0] = -1;
      int j = 0;
      int i = 2;
      while(i<=n)
      {
         if(chars[i-1]==chars[j])
         {
            j++;
            table[i] = j;
            i++;  
         }
         else if(j>0)
            j = table[j];
         else
         {
            table[i] = 0;
            i++;
         }
      }
      return table;
   }
}
