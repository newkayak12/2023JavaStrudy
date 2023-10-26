package algorithm.practice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
public class Sort {
    /**
     *  정렬
     *
     * - 내부 정렬 :  정렬한 모든 데이터를 하나의 배열에 저장할 수 있는 경우에 사용하는 알고리즘
     * - 외부 정렬 :  정렬할 데이터가 너무 많아서 하나의 배열에 저장할 수 없는 경우에 사용하는 알고리즘
     *
     *
     *  버블 정렬 : 이웃한 두 요소의 대소 관계를 비교하여 교환을 반복
     */
    int[] target = {};

    @BeforeEach
    public void before () {
        Random random = new Random();
        target = IntStream
                .rangeClosed(0, 9).map(elem -> random.nextInt(50))
                .toArray();

        log.info("BEFORE :: {}", target);
    }

    private void print( String name, int[] array){
        log.info("{} :: {}", name, array);
    }

    private void swap (int[] ref, int idx1, int idx2) {
        int t = ref[idx1];
        ref[idx1] = ref[idx2];
        ref[idx2] = t;
    }

    @Test
    public void bubbleSort () {
        int[] array = Arrays.copyOf(target, target.length);

        outer: for ( int i =  array.length - 1; i > 0; i -- ) {
           inner: for ( int j = 0; j < i; j ++ ){
                if( array[j] > array[j + 1] ) swap(array,j , j + 1);
            }
        }

        print("bubbleSort", array);
    }
}
