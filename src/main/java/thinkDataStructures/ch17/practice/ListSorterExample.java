package thinkDataStructures.ch17.practice;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class ListSorterExample {
    public static void main(String[] args) {
        ListSorter<Integer> listSorter = new ListSorter<>();
        List<Integer> array = IntStream.rangeClosed(0, 30)
                .boxed()
                .collect(Collectors.toList());

        Collections.shuffle(array, new Random());

        log.warn("BEFORE : {}", array);

        List<Integer> insertion = listSorter.insertionSort(array, ((e1, e2) -> e1 - e2));
        log.warn("InsertionSort : {}", insertion);


        List<Integer> merge = listSorter.mergeSort(array, ((e1, e2) -> e1 - e2));
        log.warn("MergeSort : {}", merge);


    }
}
