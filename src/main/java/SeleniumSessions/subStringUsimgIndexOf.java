package SeleniumSessions;

public class subStringUsimgIndexOf {

    public static void main(String[] args) {

        String txt = "GeeksForGeeks";
        String pat = "For";
        int position = 0 ;

        position = txt.indexOf(pat);

        if(position == -1)
        {
            System.out.println(-1);
        }else {
            System.out.println(position);
        }
    }
}
