package SeleniumSessions;

public class subStringExample {

    public static void main(String[] args) {

        String txt = "GeeksForGeeks";
        String pat = "For";
        if(txt.indexOf(pat) != -1)
        {
            System.out.println(txt.indexOf(pat));
        }else {
            System.out.println(txt.indexOf(pat));
        }
        int n = txt.length();
        int m = pat.length();
        int i;
        boolean flag = false;
        // Iterate through txt
        for (i = 0; i <= n - m; i++) {

            // Check for substring match
            int j;
            for (j = 0; j < m; j++) {

                // Mismatch found
                if (txt.charAt(i+j) != pat.charAt(j)) {
                    break;
                }
            }

            // If we completed the inner loop, we found a match
            if (j == m) {

                flag = true;
                break;
            }
        }
        if(flag) {
            System.out.println(i);
        }else {
            System.out.println("-1");
        }
    }
}
