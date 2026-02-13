package SeleniumSessions;

public class Audi implements Vehicle , AutoMobile{
    @Override
    public int area() {
        System.out.println("Audi");
        return y + x ;
    }

    @Override
    public int area(int length) {
        return 0;
    }

   /* @Override
    public void areaPath() {
       System.out.println("areapath");
    }*/

   /* @Override
    public int perimeter() {
        return 0;
    }*/
}
