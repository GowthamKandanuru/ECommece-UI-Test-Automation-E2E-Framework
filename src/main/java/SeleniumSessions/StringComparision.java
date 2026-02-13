package SeleniumSessions;

public class StringComparision {

    public static void main(String[] args) {

        String s = "aabccccccaaa";
        StringBuilder sb = new StringBuilder();
        int i,j;

         for(i = 0 ; i < s.length() ; i++)
         {
            int counter = 1 ;
             for(j = i+1 ; j < s.length(); j++)
             {
                 if(s.charAt(j) == s.charAt(i))
                 {
                     counter++;
                 }else
                 {
                     sb.append(s.charAt(j-1));
                     sb.append(counter);
                     i = j - 1;
                     break;
                 }
             }
         }
         System.out.println(sb.toString());
    }
}
