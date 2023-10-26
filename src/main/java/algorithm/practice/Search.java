package algorithm.practice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
public class Search {
    /**
     * 배열 검색
     * 1. 선형 탐색
     * 2. 보초법
     * 3. 이진 탐색
     */

    int[] target = {};
    int targetNumber = 0;

    @BeforeEach
    public void before () {
        Random random = new Random();
        target = IntStream
                .rangeClosed(0, 9).map(elem -> random.nextInt(50))
                .toArray();

        targetNumber = random.nextInt(50);
        log.info("BEFORE :: {}", target);
    }
    private void print( String name, int index){
        log.info("{} :: [target {} / index {}]", name,  targetNumber, index);
    }


    @Test
//    @RepeatedTest(value = 10)
    public void sequentialSearch() {
//        무작위로 늘어놓은 데이터 모임에서 검색을 수행
        int targetNumber = new Random().nextInt(10);
        int index = 0;

        search: for (; index < target.length; index++) {
            if ( target[index] == targetNumber ) break search;
            else if( target[index] != targetNumber && index == target.length - 1 ) {
                index = -1;
                break search;
            }
        }

        print("sequentialSearch", index);
    }

    @Test
//    @RepeatedTest(value = 50)
    public void sentinelSearch() {
//       찾고자하는 값을 배열의 맨 뒤에 놓고 해당 값을 찾으면 배열의 끝이라고 판단한다.
//       종료 조건 중 찾고자하는 값과 맞지 않고, 배열의 끝일 때 종료하는 조건을 없앨 수 있다.


        int[] array = new int[target.length + 1];
        System.arraycopy(target, 0, array, 0, target.length );
        array[target.length ] = targetNumber;

        int index = 0;

        while ( true ) {
            if( array[index] == targetNumber ) {
                break;
            }

            index ++;
        }

        print("sequentialSearch", index == array.length - 1 ? -1 : index);
    }

    @Test
    public void binarySearch() {
//        이미 배열이 정렬됐을 경우를 가정
//        중간 값을 확인해서 찾고자하는 값이 그 값보다 큰 수 인지, 작은 수 인지로
//        탐색할 구간을 줄여나간다.

//        Arrays.binarySearch() :: 이진 검색

        int[] array = Arrays.copyOf(target, target.length);
        Arrays.sort(array);

        log.warn("{} {}", array, targetNumber);
        int startIdx = 0;
        int midIdx = 0;
        int endIdx = array.length - 1;
        int result = 0;

        do {
            midIdx = Math.floorDiv(startIdx + endIdx, 2);
            int pick = array[midIdx];

            if( pick == targetNumber ) {
                result = midIdx;
                break;
            }
            else if ( pick < targetNumber ) startIdx = midIdx + 1;
            else endIdx = midIdx - 1;

        } while (startIdx <= endIdx);

        print("binarySearch", midIdx);
    }
}
