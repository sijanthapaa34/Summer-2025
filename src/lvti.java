import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class lvti {
    public static void main(String[] args) {
        Integer [] s = new Integer[]{1,2,3,4,5};
        var t = new String[] {"sijan","ram"};
        var nums = Arrays.stream(s).distinct();


        List<int[]> list = new ArrayList<>();
        list.add(new int[]{1,2,3,4,5});

        Stream <Integer>liststream = list.stream()
                .flatMapToInt(Arrays::stream)//convert each int[] to IntStream
               .filter(n -> n%2 !=0)
                .map(n-> n*2).boxed();// Convert IntStream back to Stream<Integer> (optional, if you need Stream<Integer>)

        //Stream<Integer> lists =
                Arrays.stream(s)
                .filter(n -> n%2 != 0)
                .map(n-> n*2)
                        .forEach(System.out::println);
        //liststream.forEach(n -> System.out.println(n));
//lists.forEach(System.out::println);

    }
}
