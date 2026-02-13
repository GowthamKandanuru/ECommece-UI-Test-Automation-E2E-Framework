package SeleniumSessions;

public interface Vehicle {

    public static int x =9;

    public int y = 0;

    static int z = 0;

    public int area();
    private static void operate()
    {
        System.out.println("Interface completed");
    }
    default void areaPath(String name)
    {
        operate();
    }
   // public int perimeter();
}
