package CollectionPractice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Demo2ko {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(4,5,6,7,8,9);

        //for filter in stream
        Predicate<Integer> pre= new Predicate<Integer>(){
            public boolean test(Integer i) {
                if (i%2==0)
                    return true;
                else
                    return false;
            }
        };
        //Predicate<Integer> pre=  i->  i%2==0; this is lambda expression for filter
        //for map
        Function<Integer, Integer> fun = new Function<Integer, Integer>() {
            public Integer apply(Integer i) {
                return i*2;
            }
        };
        //Function<Integer,Integer> fun=  i -> i*2; this is lambda expression for map


        int result = nums.stream()
                .filter(pre)
                .map(fun)
                .reduce(0,(a,b)->a+b);
        System.out.println(result);


//        int sum=0;
//        for (int n: nums){
//            if(n%2==0){
//                n=n*2;
//                sum+=n;
//            }
//        }
//        System.out.println(sum);
    }
}
