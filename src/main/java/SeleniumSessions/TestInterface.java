package SeleniumSessions;

public class TestInterface {

    public static void main(String[] args) {

        Vehicle b = new Audi();
        int x = b.area();
        System.out.println(x);
        System.out.println(Bmw.y);
    }
}
