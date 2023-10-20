package thinkDataStructures.ch02.practice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
public class SelectSort {

    /**
     * 최소 값을 탐색한다.
     * 이미 정렬한 최소 값을 제외한 인덱스의 맨 앞으로 이동시킨다.
     */
    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    private int seekLowestIndex( int[] array, int start) {
        int lowestIndex = start;

        for ( int i = start; i < array.length; i++ ) {
            if( array[i] < array[lowestIndex]) lowestIndex = i;
        }

        return lowestIndex;
    }
    private void selectionSort( int[] array ) {
        for( int i = 0; i < array.length; i ++ ){
            int j = seekLowestIndex(array, i);
            this.swap(array, i, j);
        }
    }
    @Test
    public void selectSort(){
        Random random = new Random(10);
        int[] array = IntStream.generate(() -> random.nextInt(10)).limit(10).toArray();

        log.info("BEFORE : {}", array);
        this.selectionSort(array);
        log.info("AFTER : {}", array);

    }
}
