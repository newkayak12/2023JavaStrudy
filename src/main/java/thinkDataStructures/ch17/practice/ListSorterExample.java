package thinkDataStructures.ch17.practice;

import lombok.extern.slf4j.Slf4j;
import org.junit.rules.Stopwatch;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class ListSorterExample {
    public static void main(String[] args) {
        ListSorter<Integer> listSorter = new ListSorter<>();
        List<Integer> array = IntStream.rangeClosed(0, 10)
                .boxed()
                .collect(Collectors.toList());

        Collections.shuffle(array, new Random());

        log.warn("BEFORE : {}", array);

        Long start = System.nanoTime();
        List<Integer> insertion = listSorter.insertionSort(array, ((e1, e2) -> e1 - e2));
        Long end = System.nanoTime();
        log.warn("InsertionSort : {} [{}ns]", insertion, (end - start));

        start = System.nanoTime();
        List<Integer> merge = listSorter.mergeSort(array, ((e1, e2) -> e1 - e2));
        end = System.nanoTime();
        log.warn("MergeSort : {} [{}ns]", merge, (end - start));

//        start = System.nanoTime();
//        List<Integer> mergeSolution = listSorter.mergeSortSolution(array, ((e1, e2) -> e1 - e2));
//        end = System.nanoTime();
//        log.warn("MergeSort Solution : {} [{}ns]", mergeSolution, (end - start));

        start = System.nanoTime();
        List<Integer> bubble = listSorter.bubbleSort(array, ((e1, e2) -> e1 - e2));
        end = System.nanoTime();
        log.warn("BubbleSort : {} [{}ns]", bubble, (end - start));


    }
}
