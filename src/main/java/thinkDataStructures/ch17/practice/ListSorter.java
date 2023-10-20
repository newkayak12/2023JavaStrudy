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

            result.addAll(left);
            result.addAll(right);
            result.sort(comparator);

            return result;
        }


//
//        if( copyList.size() <= 3 ){
//            copyList.sort(comparator);
//            result = copyList;
//        }

//        if( !left.isEmpty() || !right.isEmpty() ){
//            if(left.isEmpty()) result = right;
//            if(right.isEmpty()) result = left;
//            if(!left.isEmpty() && !right.isEmpty()) {
//                T leftFirst = left.stream().findFirst().get();
//                T rightFirst = right.stream().findFirst().get();
//
//                int compareResult = comparator.compare(leftFirst, rightFirst);
//                if( compareResult > 0 ) {
//                    result = right;
//                    result.addAll(left);
//                } else if( compareResult == 0) {
//
//                } else {
//                    result = left;
//                    result.addAll(right);
//                }
//            }
//
//        }

    }



}
