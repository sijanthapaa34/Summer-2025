package CollectionPractice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Demo2StreamAPI {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(4,5,6,7,8,9);

      //  Consumer <Integer> con  = o -> System.out.println(o);//combining this
      //  nums.forEach(con);//and this make below expression

    //print each item in very short
        //nums.forEach(n -> System.out.println(n));

 //       Stream<Integer> s1 = nums.stream();
//        Stream<Integer> s2 = s1.filter(num -> num % 2 == 0);
//        Stream<Integer> s3 = s2.map(num -> num * 2);
//        int result = s3.reduce(0, (a,b) -> a+b);

        int result = nums.stream()
                .filter(num -> num % 2 == 0)
                .map(num ->num*2)
                .reduce(0,(a,b)->a+b)
                ;
        System.out.println(result);


//        int sum=0;
//        for (int n: nums){
//            if(n%2==0){
//                n=n*2;
//                sum+=n;
//            }
//        }
//        System.out.println(sum);

        List <Integer> nums2 = Arrays.asList(4,5,6,7,8,9);

        Stream <Integer> nums2stream = nums2.stream();


    }
}
