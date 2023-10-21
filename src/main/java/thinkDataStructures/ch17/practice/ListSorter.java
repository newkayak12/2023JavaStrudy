package thinkDataStructures.ch17.practice;

import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListSorter<T> {
    public List<T> insertionSort(List<T> refList, Comparator<T> comparator) {
        List<T> list = new ArrayList<>(refList);

        for ( int i = 1; i < list.size(); i ++ ){
            T ref = list.get(i);
            int j = i;

            inner: for ( ; j > 0; j -- ) {
                T target = list.get(j - 1);

                if( comparator.compare(ref, target) >= 0) break inner;

                list.set(j, target);
            }

            list.set(j, ref);
        }

        return list;
    }
    public List<T> mergeSort(List<T> refList,  Comparator<T> comparator){
        Integer size = refList.size();
        if( size < 2 ) return refList;
        else {
            Integer splitPoint = Math.floorDiv(size, 2);
            List<T> left  = mergeSort(refList.subList(0, splitPoint), comparator);
            List<T> right  = mergeSort(refList.subList(splitPoint, size), comparator);


            List<T> result = new ArrayList<>();
            merge(left, right, result, comparator);
            return result;
        }
    }
    private List<T> merge (List<T> left, List<T> right, List<T> result, Comparator<T> comparator) {
        if( left.isEmpty() && right.isEmpty() ) return result;
        if( left.isEmpty() ) {
            result.addAll(right);
            return result;
        }
        if( right.isEmpty() ) {
            result.addAll(left);
            return result;
        }


        List<T> leftCopy =  new ArrayList<>(left);
        List<T> rightCopy = new ArrayList<>(right);
        T leftElement = leftCopy.get(0);
        T rightElement = rightCopy.get(0);
        int compare = comparator.compare(leftElement, rightElement);
        if( compare > 0 ) {
            result.add(rightElement);
            rightCopy.remove(rightElement);
        } else if ( compare < 0){
            result.add(leftElement);
            leftCopy.remove(leftElement);
        } else {
            result.add(rightElement);
            result.add(leftElement);
            leftCopy.remove(leftElement);
            rightCopy.remove(rightElement);
        }


        merge(leftCopy, rightCopy, result, comparator);
        return result;
    }


    public List<T> mergeSortSolution(List<T> list, Comparator<T> comparator) {
        int size = list.size();
        if (size <= 1) {
            return list;
        }
        // make two lists with half the elements each.
        List<T> first = mergeSortSolution(new LinkedList<T>(list.subList(0, size/2)), comparator);
        List<T> second = mergeSortSolution(new LinkedList<T>(list.subList(size/2, size)), comparator);

        return mergeSolution(first, second, comparator);
    }
    private List<T> mergeSolution(List<T> first, List<T> second, Comparator<T> comparator) {
        // NOTE: using LinkedList is important because we need to
        // remove from the beginning in constant time
        List<T> result = new LinkedList<T>();
        int total = first.size() + second.size();
        for (int i=0; i<total; i++) {
            List<T> winner = pickWinner(first, second, comparator);
            result.add(winner.remove(0));
        }
        return result;
    }
    private List<T> pickWinner(List<T> first, List<T> second, Comparator<T> comparator) {
        if (first.size() == 0) {
            return second;
        }
        if (second.size() == 0) {
            return first;
        }
        int res = comparator.compare(first.get(0), second.get(0));
        if (res < 0) {
            return first;
        }
        if (res > 0) {
            return second;
        }
        return first;
    }

    public List<T> bubbleSort(List<T> refList, Comparator<T> comparator) {
        List<T> copy = new ArrayList<>(refList);
        outer: for ( int i = 0; i < copy.size(); i ++) {
            inner: for (int j = 1; j< copy.size() - i; j ++) {
                    if( comparator.compare(copy.get(j - 1), copy.get(j)) > 0) {
                        T element = copy.get(j - 1);
                        copy.set(j - 1, copy.get(j));
                        copy.set(j, element);
                    }
            }
        }
        return copy;
    }



}
