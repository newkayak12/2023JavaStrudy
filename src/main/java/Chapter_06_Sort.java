import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created on 2023-04-03
 * Project 2023JavaStudy
 */
public class Chapter_06_Sort {
    /**
     *      정렬이란?
     * 정렬은 대소 관계에 따라 데이터 집합을 일정한 순서로 줄지어 늘어서도록 바꾸는 작업을 말한다.
     * 이 알고리즘을 이용해서 데이터를 정렬하면 겁색을 더 쉽게 하 ㄹ수 있다.
     *
     *      정렬 알고리즘의 안정성
     * 정렬 알고리즘은 안정된 알고리즘과 그렇지 않은 알고리즘으로 나눌 수 있다. 안정된 정렬이란 같은 값의 키를 가진 요소의 순서가 정렬 전후에도 유지되는 것을 의미한다.
     * 반대로 안정되지 않은 알고리즘은 같은 값의 키를 가진 요소의 순서가 정렬 전후에 순서가 유지 될 것이라는 보장이 없는 경우를 의미한다.
     *
     *      내부 정렬과 외부 정렬
     * 정렬 알고리즘도 하나의 배열에서 작업할 수 있는 경우에는 내부 정렬을 사용하고, 하나의 배열에서 작업할 수 없는 경우에는 외부 정렬을 사용한다.
     *
     *      1. 내부 정렬 : 정렬한 모든 데이터를 하나의 배열에 저장할 수 있는 경우에 사용하는 알고리즘
     *      2. 외부 정렬:  정렬할 데이터가 너무 많아서 하나의 배열에 저장할 수 없는 경우에 사용하는 알고리즘
     *
     *
     *
     *       버블 정렬
     *  : 버블 정렬은 이웃한 두 요소의 대소 관계를 비교하여 교환을 반복한다.
     *
     * (배열을 오름차순 정렬한다고 할 때)
     * 배열의 끝에서 마지막 값, 이전 값의 대소를 비교하고 정렬이 필요하다면 변경한다. 이러한 과정을 첫 번째 요소에 도달할 때까지 진행한다.
     * 이 과정을 마치면 가장 작은 요소가 배열의 맨 앞으로 간다. 이러한 일련의 과a정(비교, 교환 작업)을 패스(pass)라고 한다. (n-1 번 시행하면 된다.)
     *
     * 이후 정렬이 확정된 요소를 제외하고 맨 뒤에서부터 같은 작업을 다시 시작한다. (이미 확정된 요소가 있으므로 n - 2번 시행하면 된다.)
     */

    @Test
    void bubbleSorting(){
        int[] array1 = {22, 5, 11, 32, 120, 68, 70};
        bubbleSort(array1);
        print(array1);

        int[] array2 = {22, 5, 11, 32, 120, 68, 70};
        bubbleSort2(array2);
        print(array2);

    }
    void print(int[] a){
        System.out.println(Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(", ")));;
    }
    void swap(int[] a, int idx1, int idx2){
        int t = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = t;
    }
    int swapCount(int[] a, int idx1, int idx2){
        int t = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = t;
        return 1;
    }
    void bubbleSort(int[] a){
        int count = 0;
        int n = a.length;
        for( int i = 0; i < n - 1; i++){
            for(int j = n - 1; j > i; j--){
                if(a[j-1] > a[j]){
                    count += swapCount(a, j-1, j);
                }
            }
        }
        System.out.println("SWAP :: "+ count);
    }
    void bubbleSort2(int[] a){
        int count = 0;
        for (int i = 0; i < a.length; i++){
            for( int j = i + 1; j < a.length; j++){
                if(a[j - 1] > a[j]){
                    count += swapCount(a, j-1, j);
                }
            }
        }
        System.out.println("SWAP :: "+ count);
    }

    /**
     * 알고리즘 개선(1)
     *
     * [1, 3, 4, 6, 7, 8, 9]
     * 는 9에서부터 정렬해서오며, 3번째 패스가 끝난 상태이다.  문제는 4 번째 패스에서 발생한다. 여기부터는 한 번의 교환도 이뤄지지 않는다.
     * 이미 배열이 정렬을 마친상태라면 그 이후의 패스를 굳이 진행할 이유가 없어진다.
     *
     * 만약 정렬이 된 상태를 확인하고 이후의 과정을 없앤다면 쓸 데 없는 리소스 낭비를 줄일 수 있다.
     */

    void bubbleSort3(int[] a){
        for (int i = 0; i < a.length; i++){
            int count = 0; // 매 패스 때마다 초기화
            for( int j = i + 1; j < a.length; j++){
                if(a[j - 1] > a[j]){
                    count += swapCount(a, j-1, j); // 요소를 탐색하면서 스와핑이 일어나면 카운트를 올린다.
                }
            }
            if(count == 0) break; //스와핑이 한 번도 없다면 더 이상 버블 정렬을 이행할 필요성이 없어진다.
        }
    }
    @Test
    void bubbleSort3(){
        int[] array1 = {22, 5, 11, 32, 120, 68, 70};
        bubbleSort3(array1);
        print(array1);
    }
    /**
     *
     * 알고리즘 개선(2)
     *
     * 버블 정렬 패스 중에서 비교, 교환을 하다가 어떤 시점 이후로 교환이 수행되지 않는다고 하면 앞쪽의 요소는 이미 정렬을 마친 상태라고 생각해도
     * 좋다. 그러면 첫 번째 패스에서는 전체를 다 순회할 수 있어도 다음 패스부터는 이미 정렬된 부분을 순회하지 않도록 해서 리소스 낭비를 줄일 수 있다.
     */
    void bubbleSort4(int[] a) {
        int n = a.length;
        int k = 0;

        while(k < (n - 1)){
            int last = n - 1;

            for(int j = n - 1; j > k; j--){
                if(a[j-1] > a[j]){
                    swap(a, j-1, j);
                    last = j;
                }
            }

            k = last;
        }
    }
    @Test
    void bubbleSort4(){
        int[] array1 = {22, 5, 11, 32, 120, 68, 70};
        bubbleSort4(array1);
        print(array1);
    }
    @Test
    void biDirectionBubbleSort(){
        int[] a = {9,1,2,3,4,5,6,7,8};
        int n = a.length;
        for( int i = 0; i < n - 1; i++){
            int count = 0;

            if(i%2 != 0) {
                for (int j = i + 1; j < a.length; j++) {
                    if (a[j - 1] > a[j]) {
                        count += swapCount(a, j - 1, j); // 요소를 탐색하면서 스와핑이 일어나면 카운트를 올린다.
                    }
                }
            } else {
                for (int j = n - 1; j > i; j--) {
                    if (a[j - 1] > a[j]) {
                        count += swapCount(a, j - 1, j);
                    }
                }
            }

            print(a);
            if(count == 0) break;
        }


    }


    /**
     * 단순 선택 정렬
     * 단순 선택 정렬은 가장 작은 요소부터 선택해서 알맞은 위치로 옮겨서 순서대로 정렬하는 알고리즘이다.
     * 개념은 일정 값을 선택하고 아직 정렬하지 않은 부분의 첫 번째 요소를 교환한다. 선택한 정렬 값은 더 이상 정렬 대상에 두지 않는다.
     * 이러한 정렬은 모든 요소를 순회하면 종료된다.
     */

    void selectSort1(int[] a){
        for ( int i = 0; i < a.length - 1; i++){
            int min = i; //일단 초기화

            for ( int j = i + 1; j < a.length; j++){ //비교하면서 최저 값을 가진 인덱스 추출
                if(a[j] < a[min]){
                    min = j;
                }
            }


            swap(a, i, min);
        }
    }
    @Test
    void selectSort(){
        int[] array = {3,4,2,8,1};
        selectSort1(array);
        print(array );
    }

    /**
     * 단순 삽입 정렬
     * 단순 삽입 정렬은 선택한 요소를 그보다 더 앞쪽에 알맞는 위치에 '삽입하는' 작업을 반복하여 정렬하는 알고리즘이다. 단순 선택 정렬과 비슷해보일 수 있지만,
     * 단순 선택 정렬은 값이 가장 작은 요소를 선택해서 알맞는 위치로 옮긴다는 점이 다르다.
     *
     * 두 번째 인덱스부터 시작해서 선택해서 비교한 후 앞 쪽에 삽입하고 한 칸씩 밀어준다.
     */
    @Test
    void sortTest1(){
        int[] array = {22, 5, 11, 32, 120, 68, 70};
        insertSort1(array);
        print(array);
    }
    void insertSort1(int[] a){
        for ( int i = 1; i < a.length; i++){
            int j;
            int tmp = a[i];
            for ( j = i; j > 0 && a[j - 1] > tmp; j--){
                a[j] = a[j - 1];
            }
            a[j] = tmp;
        }
    }
    /**
     * 이렇게 구현한 단순 삽입 정렬 알고리즘은 떨어져있는 요소들이 뒤바뀌지 않아 안정적이다.
     * * 단순 삽입 정렬은 셔틀 정렬(Shuttle sort)라고도 한다.
     */

    void printIdx(int sortedIndex, int minimumIndex, int totalIndex){
        String[] tag = new String[totalIndex];
        for(int i = 0; i < tag.length; i++){
            if(i == sortedIndex && sortedIndex == minimumIndex) tag[i] = "*+";
            else if(i == sortedIndex) tag[i] = "*";
            else if(i == minimumIndex) tag[i] = "+";
            else tag[i] = " ";
        }

        System.out.println(Arrays.stream(tag).collect(Collectors.joining("  ")));;
    }
    void printValue(int[] array){
        System.out.println(Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.joining("  ")));;
    }

    @Test
    void selectSortGraph(){

        int[] targetArray = {54,2,1,68,6,39,40};
        for ( int i = 0; i < targetArray.length; i++){
            int minimunIndex = i;
            for( int j = i + 1; j < targetArray.length; j++ ){
                if(targetArray[minimunIndex] > targetArray[j]){
                    minimunIndex = j;
                }
            }

            printIdx(i, minimunIndex, targetArray.length);
            printValue(targetArray);
            swap(targetArray, i, minimunIndex);

        }

        print(targetArray);
    }

    @Test
    void insertSort() {
        int[] array = {0, 6,4,1,7,3,9,8};
        for (int i = 1; i < array.length; i++){
            int j = i;
            int temporaryValue = array[i];
            for (; j > 0 && array[j - 1] > temporaryValue; j --){
                array[j] = array[j - 1];
            }
            array[j] = temporaryValue;
        }
        print(array);
    }
    @Test
    void insertSortCen(){
        int[] array = {0, 6,4,1,7,3,9,8};

        for (int i = 2; i < array.length; i++) {
            print(array);
            int tmp = array[0] = array[i]; //보초를 둠
            int j = i;

//            j > 0 보초를 둠으로써 이 조건이 없어짐
            for ( ; array[j - 1] > tmp; j--){
                array[j] = array[j - 1];
            }

            array[j] = tmp;
        }
        print(array);
    }

    @Test
    void binaryInsertSort(){
        int[] array = {6,4,1,7,3,9,8};
        for ( int i  = 1; i < array.length; i++){
            int temporary = array[i];
            int j = i;
            int binarySearchIndex = 0;
            int pl = 0;
            int pr = i - 1;
            int pc = 0;
            do {
                pc = (pl + pr) / 2;

                if(array[pc] == temporary) {
                    binarySearchIndex = pc;
                    break;
                }
                if(array[pc] < temporary) {
                    pl = pc + 1;
                    binarySearchIndex = pl;
                    //배열의 앞으로 갈수록 작은 값이다.
                    //이진 검색을 하는 이유는 쓸 데 없이 제일 작은 값부터 검사하고 결과에 부합하는 정렬하는 과정을 줄이기 위해서이다.
                    //pl이 조정 되면 될 수록 삽입하려는 값쪽으로 삽입 정렬 범위를 줄일 수 있다.
                    //이렇게 삽입정렬하고자 하는 범위를 줄여버리면
                    //맨 아래에서 for 문을 돌릴 범위가 줄어든다.
                }
                if(array[pc] > temporary) pr = pc - 1;
            } while (pl <= pr);
            System.out.println(pl);


            for(; j > binarySearchIndex; j--){
                array[j] = array[j - 1];
            }
            array[j] = temporary;
        }

        print(array);
    }

    /**
     *
     * 단순 정렬의 시간 복잡도
     * 버블/ 선택/ 삽입 정렬의 시간 복잡도는 모두 O(n^2)이다.
     *
     *
     *
     *      셸정렬
     *
     * 셸정렬은 단순 삽입 정렬의 장점은 살리고 단점은 보완하여 더 빠르게 정렬하는 알고리즘이다.
     * 삽입 정렬은 정렬에 가까워질수록 정렬 속도가 빨라진다. 그러나 단점으로 배열을 밀어내는 과정이 꽤나 오래 걸린다는 것이다.
     * 만약 인덱스 6번의 값을 0번의 값으로 삽입한다고 했을 때 그 사이의 값은 모두 밀어내야한다.
     *
     * 셸 정렬은 이러한 장점은 극대화하고 단점은 보완한 정렬 알고리즘이다. 단점이 무엇이었는가? 정렬하는 인덱스 사이가 멀수록 배열 밀어내기에
     * 리소스를 낭비한다. 장점은 정렬의 끝에 다다를수록 빨라진다는 것이다.
     * 셸정렬은 정렬할 배열의 요소를 그룹으로 나눠서 각 그룹별로 단순 삽입 정렬을 수행하고, 그 그룹을 합치면서 정렬을 반복하여 요소의 이동 횟수를 줄인다.
     * [8, 1, 4, 2, 7, 6, 3, 5] : 시작
     *
     *  *           *
     * [8, 1, 4, 2, 7, 6, 3, 5] : 4-정렬 예시 : 8 ~ 7과 같이 밀어낼 사이를 정해서 정렬을 시작한다. (그룹 {8,7}, {1,6}, {4, 3}, {2,5})
     *
     * [7, 1, 3, 2, 8, 6, 4, 2] : 4-정렬 완료
     *
     *  *     *     *     *
     * [7, 1, 3, 2, 8, 6, 4, 2] : 2-정렬 예시 : 2칸만큼 떨어진 요소를 모아 두 그룹으로 나눠서 2정렬을 한다. (그룹 {7,3,8,4}, {1,2,6,5})
     *
     * 마지막으로 1-정렬을 하면 최종 정렬을 마치게 된다.
     *
     * 정렬되지 않은 배열에 단순 삽입 정렬을 하는 것이 아니라 4-정렬, 2-정렬로 조금이라도 정렬이 된 상태에 가까운 배열을 만들어 놓은 다음에
     * 마지막으로 단순 삽입 정렬을 해서 마무리를 한다. 이렇게 여러 개의 그룹으로 나누어 정렬하는 이유는 단순 삽입 정렬의 장점은 살리고 단점은
     * 보완하기 위해서이다.
     *
     */

    @Test
    void shellSort(){
        int[] array = {8, 1, 4, 2, 7, 6, 3, 5};

        for( int h = array.length / 2; h > 0; h /= 2){ //4, 2, 1 term
            for (int i = h; i< array.length; i++){ //4 ~ 8, 2 ~ 8, 1 ~ 8   -> 기존 selectSort
                int j;
                int tmp = array[i];

                                   // && 순서 바꿔야하는 경우;
                for(j = i - h; j >= 0 && array[j] > tmp; j -= h){ //바꾸는 코드 (떨어진 거리만큼 -= h)
                    //0+4 = 0 / 1+4 = 1 //
                    array[j + h] = array[j];
                    //print(array);
                } // 배열 밀어내기

                array[j + h] = tmp;
                System.out.println(i+ " : "+h+"_ FOR" + ">> " +(i-h+h) +"/"+ (i-h));
            }
        }
        print(array);
    }
    /**
     *
     * 증분값(h 값) 선택
     *
     * h(위에서는 4,2,1)를 어떤 수열로 감소시키면 효율적으로 정렬할 수 있을까? 이는 배열을 그룹으로 나누는 과정과도 연결된다.
     * 그룹을 잘못 나누면 차시를 거듭하면서 같은 요소들로 이뤄진 그룹만 정렬하고 있을 수 있다. 이러면 결과적으로 그냥 삽입 정렬 알고리즘을
     * 실행한 것과 같아질 수 있습니다. 이런 문제를 해결하기 위해서는 h값이 서로 배수가 되지 않도록 유의해야 한다.
     *
     * (3x + 1)이라는 수열을 만족하는 값으로 진행하면 된다. 또한 h의 초기 값이 너무 크면 효과가 없기 때문에 요소의 수 n개를 9로 나눈 값을
     * 넘지 않도록 해야한다.
     */

    @Test
    void shellSort2(){
        int[] array = {8, 1, 4, 2, 7, 6, 3, 5};
        int h;
        for (h = 1; h < (array.length / 9); h = 3 * h + 1);
        System.out.println("H : " + h);


            for(; h > 0; h /= 3){
                for ( int i = h; i < array.length; i++){
                    int j;
                    int tmp = array[i];
                    for( j = (i - h); j >= 0 && array[j]> tmp; j -= h){
                        array[j + h] = array[j];
                    }
                    array[j + h] = tmp;
                }
            }
        print(array);
    }
    /**
     * 위 예시는 h를 구한다는 점이 다르다.
     * 셸 정렬 시간복잡도는 O(n^1.25)이므로, 기존 시간 복잡도인 O(n^2)에 비해 빠르다. 그러나 떨어져 있는 요소를 교환하므로 안정적이지 않다.
     *
     *
     *
     *      퀵 정렬
     * 퀵 정렬은 가장 빠른 정렬 알고리즘 중 하나이다.
     * 일단 전체에 피벗을 정한다. 퀵 정렬은 각 그룹에 대해 피벗 설정과 그룹 나눔을 반복하며 모든 그룹의 요소가 1개가 되면 정렬을 마친다.
     *
     *
     *      배열을 두 그룹으로 나누기
     * 먼저 배열을 두 그룹으로 나누는 순서를 알아보자
     *
     */
}
