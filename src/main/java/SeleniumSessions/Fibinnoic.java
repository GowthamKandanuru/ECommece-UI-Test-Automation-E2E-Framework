package SeleniumSessions;

public class Fibinnoic {

    public static void main(String[] args) {
        int n = 10;
        int first = 0;
        int second = 1;
for (int i = 0 ; i <=n ; i++)
{
    System.out.print(first+", ");
    int next = first + second;
    first = second;
    second = next;
}
    }

    // 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89


}
