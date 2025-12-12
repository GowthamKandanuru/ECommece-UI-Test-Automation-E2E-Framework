package SeleniumSessions;

import java.util.HashMap;

public class LongestSubString {

    public static void main(String[] args) {

        String s = "acddghr";

        int maxLength = 0 ;
        int start = 0 ;
        int end = 0 ;
        HashMap<Character,Integer> hs = new HashMap<>();
        hs.put(s.charAt(0), 1);

        for(int i = 1 ; i < s.length();i++)
        {
            if(hs.containsKey(s.charAt(i)))
            {
                start++;
                end++;
            }else {
                hs.put(s.charAt(i), 1);
                end++;
            }
        }
        System.out.println(end - start +1);
    }
}
