package algorithm.practice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
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
     *  선택 정렬 : 가장 작은 요소부터 선택해서 알맞는 위치로 옮겨서 순서대로 정렬하는 알고리즘
     *  삽입 정렬 : 선택한 요소를 그보다 더 앞쪽에 알맞는 위치에 삽입하는 작업을 반복해서 정렬하는 알고리즘
     *  쉘  정렬 : 어느 정도 정렬된 상태에서만 빠른 것에서 착안된 개선된 삽입 정렬, 삽입 정렬이 이웃된 요소 간 정렬을 통해서 제자리를 찾아가는 것을 최적화 함
     *
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

    private int swap (int[] ref, int idx1, int idx2) {
        int t = ref[idx1];
        ref[idx1] = ref[idx2];
        ref[idx2] = t;

        return 1;
    }

    @Test
    public void bubbleSort () {
        int[] array = Arrays.copyOf(target, target.length);
        /**
         * 이웃한 요소를 비교해서 정렬한다.
         * 1차 정렬이 끝나면 가장 맨 뒤 값은 가장 큰 값이 된다. 그리고 맨 뒤는 정렬할 필요가 없어진다.
         * 이런 과정을 반복한다.
         *
         * origin : [1, 38, 6, 30, 31, 35, 1, 38, 16, 33]
         * first  : [1, 6, 30, 31, 35, 1, 38, 16, 33, || 38] -> 맨 뒤는 끝
         */

        outer: for ( int i =  array.length - 1; i > 0; i -- ) {

           int hasSwapped = 0;

           inner: for ( int j = 0; j < i; j ++ ){
                if( array[j] > array[j + 1] ) hasSwapped += swap(array,j , j + 1);
            }

            if( hasSwapped == 0) break outer; //한 번이라도 정렬하지 않으면 이미 정렬된 상태다.
                                              //따라서  더 이상 작업을 할 필요성이 없어짐
        }

        print("bubbleSort", array);
    }

    @Test
    public void selectionSort () {
        int[] array = Arrays.copyOf(target, target.length);

        outer : for ( int i = 0; i < array.length - 1; i ++ ) {
            //가장 작은 값을 맨 앞으로 둔다.
            //index가 하나씩 올라가므로, 이전에 정렬한 요소는 정렬 대상에 두지 않는다.

            int minimum = i;

            inner : for ( int j = i + 1; j < array.length; j ++ ) {
                if( array[j] < array[minimum]) minimum = j;
            }

            swap(array, i, minimum);
        }

        print("selectionSort", array);
    }

    @Test
    public void insertionSort () {
        int[] array = Arrays.copyOf(target, target.length);

        outer: for ( int i = 1; i < array.length; i ++ ) {
            int j;
            int tmp = array[i];

            // array [j - 1] > tmp; :: 이미 정렬된 것은 안건듦
            inner: for ( j = i; j > 0 && array [j - 1] > tmp; j -- ) {
                array[j] = array[j - 1];
            }

            array[j] = tmp;
        }

        print("insertionSort", array);
    }

    @Test
    public void shellSort () {
        int [] array = Arrays.copyOf(target, target.length);

        shell: for ( int h = array.length / 2; h > 0; h /= 2 ) {
            outer: for ( int i = h;  i < array.length; i ++ ) {
                int j;
                int tmp = array[i];

                inner: for ( j = i - h; j >= 0 && array [j] > tmp; j -= h ) {
                    array[j + h] = array[j];
                }

                array[j + h] = tmp;
            }
        }

        print("shellSort", array);
    }

    @Test
    public void quickSort () {
        int[] array = Arrays.copyOf(target, target.length);
        quickSortAction(array, 0, array.length - 1);

        print("quickSort", array);

    }

    /**
     * 퀵 정렬의 내부 루프는 대부분의 컴퓨터 아키텍처에서 효율적으로 작동하도록 설계되어 있고(그 이유는 메모리 참조가 지역화되어 있기 때문에 CPU 캐시의 히트율이 높아지기 때문이다.),
     * 대부분의 실질적인 데이터를 정렬할 때 제곱 시간이 걸릴 확률이 거의 없도록 알고리즘을 설계하는 것이 가능하다.
     */
    private int[] quickSortAction( int[] array, int left, int right) {
        int pivotLeft = left;
        int pivotRight = right;
        int x = array[Math.floorDiv(pivotLeft + pivotRight, 2)];

        do {
            while( array[pivotLeft] < x ) pivotLeft ++;
            while( array[pivotRight] > x ) pivotRight --;

            if( pivotLeft <= pivotRight ) swap(array, pivotLeft++, pivotRight--);
        } while (pivotLeft <= pivotRight);


        if( left < pivotRight ) quickSortAction(array, left, pivotRight);
        if( pivotLeft < right ) quickSortAction(array, pivotLeft, right);

        return array;
    }
}
