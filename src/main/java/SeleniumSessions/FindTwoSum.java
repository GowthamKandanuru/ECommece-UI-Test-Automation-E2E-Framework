package SeleniumSessions;

public class FindTwoSum {
    public static void main(String[] args) {

        int arr[] = {2,23,5,4,4,9,67,32,678};
        int target = 99;
        int sum = 0;
        boolean flag = false;
        int i,j=0;
        for(i = 0 ; i < arr.length ; i++)
        {
            for(j =0 ; j < arr.length ; j++)
            {
                if(i != j)
                {
                  sum =  arr[i] + arr[j];
                  if(sum == target)
                  {
                      flag = true;
                      break;
                  }
                }
            }
            if(flag)
            {
                break;
            }
        }
        if(flag) {
            System.out.println(i + " " + j);
        }else {
            System.out.println("No pairs");
        }
    }
}
