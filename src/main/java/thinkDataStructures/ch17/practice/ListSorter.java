package thinkDataStructures.ch17.practice;

import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListSorter<T> {
    public List<T> insertionSort(List<T> refList, Comparator<T> comparator) {
        List<T> list = refList.stream().collect(Collectors.toList());

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

        if( refList.size() < 2 ) return refList;
        else {
            List<T> left  = mergeSort(refList.subList(0, Math.floorDiv(refList.size(), 2)), comparator);
            System.out.println("LEFT : "+left);
            List<T> right  = mergeSort(refList.subList(Math.floorDiv(refList.size(), 2), refList.size()), comparator);
            System.out.println("RIGHT : "+right);


            List<T> result = new ArrayList<>();

//            result.addAll(left);
//            result.addAll(right);
//            result.sort(comparator);

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


        List<T> leftCopy = left.stream().collect(Collectors.toList());
        List<T> rightCopy = right.stream().collect(Collectors.toList());
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



}
