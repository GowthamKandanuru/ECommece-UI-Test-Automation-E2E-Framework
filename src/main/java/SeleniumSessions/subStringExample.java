package SeleniumSessions;

public class subStringExample {

    public static void main(String[] args) {

        String txt = "GeeksForGeeks";
        String pat = "For";
        int n = txt.length();
        int m = pat.length();

        // Iterate through txt
        for (int i = 0; i <= n - m; i++) {

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

                // Return starting index
                System.out.println(i);
            }
        }

        // No match found
        System.out.println(-1);
    }
}
