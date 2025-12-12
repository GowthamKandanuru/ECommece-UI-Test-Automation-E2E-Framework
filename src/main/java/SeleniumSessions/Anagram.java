package SeleniumSessions;

import java.util.HashMap;
import java.util.Map;

public class Anagram {

    public static void main(String[] args) {
String s= "..i.like.this.program.very.much.";
        String s2 = s.replaceAll("\\.", " ").trim();
        String[] s1 = s2.split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i = s1.length-1;i >=0;i--)
        {
            sb.append(s1[i]);
            if(i != 0)
            {
                sb.append(".");
            }
        }
        System.out.println(sb.toString());
       /* String s1 = "gaaeeks";
        String s2 = "keajegsa";
        Map<Character,Integer> mp = new HashMap<>();
        Map<Character,Integer> mp1 = new HashMap<>();
        for(int i = 0 ; i < s1.length(); i++)
        {
            if(mp.containsKey(s1.charAt(i))) {
                mp.put(s1.charAt(i), mp.get(s1.charAt(i)) + 1);
            }else{
                mp.put(s1.charAt(i), 1);
            }
        }
        System.out.println(mp);
        for(int i=0;i < s2.length();i++)
        {
            if(mp1.containsKey(s2.charAt(i))) {
                mp1.put(s2.charAt(i), mp1.get(s2.charAt(i)) + 1);
            }else{
                mp1.put(s2.charAt(i), 1);
            }
        }
        System.out.println(mp1);
        if(mp.equals(mp1))
        {
            System.out.println("It is Anagram");
        }else {
            System.out.println("It is not an Anagram");
        }*/
    }
}
