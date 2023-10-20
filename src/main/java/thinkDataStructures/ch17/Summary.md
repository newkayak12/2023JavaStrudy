# 17. 정렬

정렬 알고리즘을 배워야 하는 이유
1. 대다수 응요 프로그램에서 잘 돌아가는 범용 알고리즘이 있지만, 기수정렬(radix sort)과 제한된 힙 정렬(bounded heap sort)는 알아둬야 한다.
2. 정렬 알고리즘의 하나인 병렬 정렬은 분할정복법, 선형 로그라는 증가 차수를 익힐 수 있게 해준다.
3. 면접 때문에... (진짜 책에 이렇게 써있네..ㅋ<sub>ㅋ</sub><sup>ㅋ</sup>)

## 17.1 삽입 정렬
### 정의
삽입 정렬(揷入整列, insertion sort)은 자료 배열의 모든 요소를 앞에서부터 차례대로 이미 정렬된 배열 부분과 비교하여, 자신의 위치를 찾아 삽입함으로써 정렬을 완성하는 알고리즘이다.
```java
public List<T> insertionSort(List<T> refList, Comparator<T> comparator) {
    List<T> list = refList.stream().collect(Collectors.toList());

    outer: for ( int i = 1; i < list.size(); i ++ ){
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
```
중첩된 반복문이 있다. 따라서 실행시간은 이차로 추측할 수 있다. 반복문을 보면 outer도 선형, inner도 선형이다.
총 1 ~ (n - 1)의 수열의 합이 되고, ((n * (n - 1)) / 2)가 되어 최대 차수는 n<sup>2</sup>가 된다.
기본적으로 이차지만, 아래와 같은 특징이 있다.

1. 요소가 이미 정렬되어 있거나 거의 정렬되어 있으면 삽입 정렬은 선형이다. 특히 각 요소가 있어야 하는 자리 기준 k 이하의 위치에
있다면 내부 반복문은 k 번 이하로 동작하게 되고 전체 실행 시간은 O(kn)이 된다.
2. 구현이 단순하므로 오버헤드가 작다. 즉, 실행 시간은 an<sup>2</sup>이지만 최대 차수의 계수인 a는 아마도 작을 것이다.

## 17.2 병합 정렬
하나의 리스트를 두 개의 균등한 크기로 분할하고 분할된 부분 리스트를 정렬한 다음, 두 개의 정렬된 부분 리스트를 합하여 전체가 정렬된 리스트가 되게 하는 방법이다
합병 정렬은 다음의 단계들로 이루어진다.
1. 분할(Divide): 입력 배열을 같은 크기의 2개의 부분 배열로 분할한다.
2. 정복(Conquer): 부분 배열을 정렬한다. 부분 배열의 크기가 충분히 작지 않으면 순환 호출 을 이용하여 다시 분할 정복 방법을 적용한다.
3. 결합(Combine): 정렬된 부분 배열들을 하나의 배열에 합병한다.


