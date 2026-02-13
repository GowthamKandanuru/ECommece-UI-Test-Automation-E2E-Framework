package SeleniumSessions;

public interface AutoMobile {

    public int area(int length);

    private void operate()
    {
        System.out.println("Interface completed");
    }

    default void areaPath()
    {
        operate();
    }
}
