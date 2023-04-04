import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

/**
 * Created on 2023-04-04
 * Project 2023JavaStudy
 */
public class Chapter_06_Sort_part2 {
    void print(int[] a){
        System.out.println(Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(", ")));;
    }
    void swap(int[] array, int fromIndex, int toIndex){
        int temp = array[toIndex];
        array[toIndex] = array[fromIndex];
        array[fromIndex] = temp;
    }
    /**
     *      병합 정렬
     * 병합 정렬은 배열의 앞부분과 뒷 부분으로 나누어 각각 정렬한 다음 병합하는 작업을 반복하여 정렬을 수행하는 알고리즘이다.
     *
     *      정렬을 마친 배열의 병합
     * 먼저 정렬을 마친 두 배열의 병합을 살펴보자 각 배열에서 선택한 요소의 값을 비교하여 작은 값의 요소를 꺼내 새로운 배열에 넣는 작업을 반복하ㅕㅇ
     * 정렬을 마치는 배열을 만든다.
     *
     *      [2, 4, 6, 8, 11, 13]           [1, 2, 3, 4, 9, 16, 21]
     *
     *          [1, 2, 2, 3, 4, 4, 6, 8, 9, 11, 13, 16, 21]
     *
     *  배열 a,b,c,를 스캔할 때 선택한 요소의 인덱스는 pa, pb, pc이다. 이 인덱스를 저장한 변수를 커서라고 부르자. 첫 요소를 선택하므로
     *  커서를 모두 0으로 초기화 한다.
     *
     *  [1] 배열 a, b를 비교하여 작은 값은 c에 저장한다. 그리고 빼낸 배열(b)과 저장한 배열(c)의 커서를 하나씩 옮기고 동작을 하지 않은 배열(a)의
     *  커서는 그대로 둔다.
     *
     *  이 과정을 반복한다. a, b 둘 중의 커서가 하나라도 끝에 다다르면 이 작업을 종료한다.
     *
     *  [2] while을 돌리는데 현재 상황은 b의 요소를 c로 병합시킨 이후, 배열 a는 아직 잔존 요소들이 남은 상황을 전제로한다.
     *  a 배열의 커서를 하나씩 올리면서 c로 옮긴다.
     *
     *  * 그냥 남은 배열을 복사할 배열로 옮긴다고 생각하면 된다.
     *
     */

    @Test
    void merge(){
        int[] a = {2,4,6,8,11,13};
        int[] b = {1,2,3,4,9,16,21};
        int[] c = new int[a.length + b.length];


        int pa = 0;
        int pb = 0;
        int pc = 0;

        while( pa < a.length && pb < b.length){
            c[pc++] = (a[pa] <= b[pb]) ? a[pa++] : b[pb++];
        }

        while(pa < a.length){
            c[pc++] = a[pa++];
        }
        while(pb < b.length){
            c[pc++] = b[pb++];
        }
        print(c);
    }
    /**
     *      병합 정렬
     * 정렬을 마친 배열의 병합을 응용하여 분할 정복법에 따라 정렬하는 알고리즘을 병합 정렬이라고 한다.
     *
     *      [ 5, 6, 4, 8, 3, 7, 9, 0, 1, 5, 2, 3 ]
     *
     * 배열을 앞 부분과 뒷 부분으로 나눈다.
     *
     *   [ 5, 6, 4, 8, 3, 7]  [9, 0, 1, 5, 2, 3 ]
     *
     * 이 배열을 정렬하고 병합한다.
     *
     * 라는 아이디어를 가지고 있다. 물론 이 과정을 나눈 덩어리에도 프랙탈처럼 반복하면 더 효율적일 것이다.
     *
     *
     *      병합 정렬 알고리즘
     * 병합 정렬 알고리즘의 순서를 정리하면 아래와 같다.
     *
     * 배열의 요소 개수가 2개 이상인 경우
     * 1. 배열의 앞 부분을 병합 정렬로 정렬
     * 2. 배열의 뒷 부분을 병합 정렬로 정렬
     * 3. 배열의 앞 부분과 뒷 부분을 병합
     */
    void basicMergeSort(int[] array, int[] buff, int left, int right){ //원본 배열,  작업용 버퍼?, 그루핑한 배열 시작 ~ 끝 인덱스

        // 배열 세 개와 배열 포인터 세 개로 순차적으로 결과 배열에 어떤 값을 쏟느냐

        if( left < right ) {
            int mainBufferIndex;
            int center = ( left + right ) / 2;  // 그루핑 기점 인덱스
            int leftBufferIndex = 0;
            int rightBufferIndex = 0;
            int resultIndex = left;

            basicMergeSort(array, buff, left, center); // 그루핑 left
            basicMergeSort(array, buff, center + 1, right);// 그루핑 right

            for ( mainBufferIndex = left; mainBufferIndex <= center; mainBufferIndex++ ){ //왼쪽 그루핑 버퍼로 옮겨 담기
                buff[leftBufferIndex++] = array[mainBufferIndex]; //버퍼에는 왼쪽 그루핑이 담겨 있음음
           }
            //temporaryIndex는 왼쪽 그룹에 버퍼에 쏟아 부으면서 오른쪽 인덱스 시작으로 가 있음
            while ( mainBufferIndex <= right && rightBufferIndex < leftBufferIndex){ //왼쪽 그루핑 데이터를 담은 버퍼와 오른쪽 그루핑 간 정렬
                array[resultIndex++] = (buff[rightBufferIndex] <= array[mainBufferIndex]) ? buff[rightBufferIndex++] : array[mainBufferIndex++];
                //실질 정렬
            }
            while (rightBufferIndex < leftBufferIndex){
                array[resultIndex++] = buff[rightBufferIndex++]; //작업 결과물을 담은 버퍼 배열 원본으로 옮겨 담기
                //정렬 후 잔존 요소 플러시
            }

        }
    }
    @Test
    void mergeSortTest(){
        int[] array = {5, 6, 4, 8, 3, 7, 9, 0, 1, 5, 2, 3 };
        int[] buff = new int[array.length];
        basicMergeSort(array, buff, 0, array.length - 1);
        print(array);
    }
    /**
     * 시간 복잡도는 O(n)이고 데이터 요소 개수가 n개일 때, 병합 정렬의 단계는 log n 만큼 필요하므로 전체 시간 복잡도는 O(n log n )이다.
     *
     *      Arrays.sort로  퀵 정렬과 병합정렬하기
     *
     *  이전 binarySearch와 같이 Arrays에 sort() 역시 존재한다.
     *
     *
     *      기본 자료형 배열의 정렬(퀵)
     * 이 sort 메소드는 기본 자료형의 배열을 정렬한다. sort 가 사용하는 알고리즘은 퀵 정렬을 개선한 것으로 안정적이지 않다. 즉, 배열에 같은 값이
     * 존재하는 경우 같은 값 사이의 순서가 뒤바뀔 수 있다.
     *
     */

    @Test
    void ArraysSortTest(){
        int[] array = { 22, 5, 11, 32, 120, 68, 70 };
        Arrays.sort(array);
        print(array);
    }
    /**
     *  클래스 객체 배열의 정렬(병합 정렬)
     *
     *  [A] 자연 정렬이 필요한 배열 : 요소의 대소 관계를 비교하여 정렬한다.
     *
     *      void sort(Object[] a);
     *      void sort(Object[] a, int fromIndex, int toIndex);
     *
     *  [B] 자연 정렬이 필요하지 않은 배열 : 요소의 대소 관계를 비교할 때 Comparator c를 사용해서 정렬
     *
     *    <T> void  sort(T [], Comparator<? super T> c);
     *    <T> void  sort(T [], int fromIndex, int toIndex, Comparator<? super T> c);
     */
    @Test
    void ArraysSortTest2(){
        GregorianCalendar[] calendars = {
                new GregorianCalendar(2017, Calendar.NOVEMBER, 1),
                new GregorianCalendar(1963, Calendar.OCTOBER, 18),
                new GregorianCalendar(1985, Calendar.APRIL, 5),
        };
        Arrays.sort(calendars);
        System.out.println(Arrays.stream(calendars)
                                 .map(gregorianCalendar
                                         -> String.format("%d-%d-%d",
                                         gregorianCalendar.get(Calendar.YEAR),
                                         gregorianCalendar.get(Calendar.MONTH),
                                         gregorianCalendar.get(Calendar.DATE))).collect(Collectors.joining(", ")));


        class PhyscData {
            String name;
            int height;
            double vision;
            PhyscData ( String name, int height, double vision ) {
                this.name = name;
                this.height = height;
                this.vision = vision;
            }

            @Override
            public String toString() {
                return "PhyscData{" +
                        "name='" + name + '\'' +
                        ", height=" + height +
                        ", vision=" + vision +
                        '}';
            }
        }

        PhyscData[] physcDatas = {
            new PhyscData("A", 162, 0.3),
            new PhyscData("B", 173, 0.7),
            new PhyscData("C", 175, 2.0),
            new PhyscData("D", 171, 1.5),
            new PhyscData("E", 168, 0.4),
            new PhyscData("F", 174, 0.4),
            new PhyscData("G", 169, 1.2)
        };
        Arrays.sort(physcDatas, (d1, d2) -> d1.height - d2.height);
        System.out.println(Arrays.stream(physcDatas).map(PhyscData::toString).collect(Collectors.joining(", ")));
    }

    /**
     *      힙 정렬
     * 선택 정렬을 응요한 알고리즘인 힙 정렬은 힙의 특성을 이용하여 정렬을 수행한다.
     *
     *      힙이란?
     * 힙 정렬(heap sort)는 힙(heap)을 이용해서 정렬하는 알고리즘이다. 힙은 '부모의 값이 자식의 값보다 항상 크다'는 조건을 만족하는 완전 이진트리이다.
     * 이떄 부모의 값이 자식보다 항상 작아도 힙이라고 한다(부모 - 자식 관계만 일정하면 된다.)
     *
     *  > 힙 트리 : 여러 개의 값 중에서 가장 크거나 작은 값을 빠르게 찾기 위해서 만든 이진트리이다. 짧게 힙(Heap)이라고 줄여서 부르기도 한다.
     *  >          영 단어 힙(heap)은 무엇인가를 차곡차곡 쌓아올린 더미라는 뜻을 가지고 있다. 힙은 항상 완전 이진 트리의 형태를 띠어야 하고
     *  >          부모의 값은 항상 자식들의 값보다 크거나(MaxHeap), 작아야(MinHeap)하는 규칙이 있다.
     *
     *  >  이진 트리 (Binary Tree): 각가의 노드가 최대 두 개의 자식 노드를 가지는 트리 자료 구조이다.
     *  >  포화 이진 트리 (Full Binary Tree) : 이진트리이면서 서브 트리까지 모두 빈 곳 없이 꽉한 트리를 말한다. 노드의 개수는 2^(높이) - 1이다.(root는 하나)
     *  >  완전 이진 트리 (Complete Binary Tree) : 높이가 h일 때 h - 1 까지는 포화이진 트리이고, h 레벨부터는 노드가 왼쪽부터 순서대로 채워진 이진 트리이다. 노드의 개수는 2^(높이 - 1) - 1 <= n <= 2^(높이) -1 이다.
     *
     *  >> 즉 힙트리는 완전 이진 트리이며 root가 해당 트리의 최소값이거나 최대값이어야만 한다.
     *
     *
     *                      10
     *               9             5
     *          8        3    2        4
     *      6      7  1
     *
     *
     *  이런 구조이고 배열로 변경하면 [10, 9, 5, 8, 3, 2, 4, 6, 7, 1]이 된다.
     *  이 과정을 거쳐 힙으로 요소를 배열에 저장하면 부모 - 자식 간의 인덱스 간 아래와 같은 관계가 성립된다.
     *
     *      1. parentIndex = array[(index - 1) / 2] eg. ((3 - 1) / 2) == 1
     *      2. leftChild = array[(index * 2) + 1] eg. ( 1 * 2 ) + 1 == 3
     *      3. rightChild = array[(index * 2) + 2] eg. ( 1 * 2 ) + 2 == 4
     *
     *
     *
     *              힙 정렬
     *  힙 정렬은 '가장 큰 값이 루트에 위치' 하는 특징을 이용하는 정렬 알고리즘이다. 힙에서 가장 큰 값인 루트를 꺼내는 작업을 반복하고 그 값을 늘어
     *  놓으면 배열은 정렬을 마치게 된다. 즉, 힙 정렬은 선택 정렬을 응용한 알고리즘이다. 힙에서 가장 큰 값인 루트를 꺼내고 남은 요소에서 다시 가장
     *  큰 값을 구해야한다. 예를 들어 힙으로 구성된 10개의 요소에서 가장 큰 값을 없애고 나머지 9개의 요소 중에서 가장 큰 값을 루트로 정해야 한다.
     *  따라서 나머지 9개의 요소로 만든 트리도 힙의 형태를 유지할 수 있도록 재구성해야 한다.
     *
     *  ** 선택 정렬은 가장 큰(작은) 값을 선택해서 정렬하는 알고리즘이다.
     *
     *      1. 힙에서 루트를 꺼낸다. 그런 다음 비어 있는 루트로 힙의 마지막 요소(가장 작은 요소)를 옮긴다.
     *      2. 이때 루트 이외에는 힙 정렬 상태를 유지하고 있다. 따라서 루트 값만 힙 정렬에 맞게 조정하면 힙 정렬 상태가 유지된다.
     *      3. 무조건 이동시키는 것은 아니다. 자기보다 큰 값을 가지는 자식 요소와 자리를 바꿔서 아래쪽으로 내려가는 작업을 반복했을 때 자식의 값이 작거나 잎에 다다르면 작업이 종료된다.
     *
     *
     *              힙 정렬 알고리즘 살펴보기
     *
     *  > 1. 변수 i 의 값을 n - 1로 초기화
     *  > 2. array[0]과 a[i]를 바꾼다.
     *  > 3. a[0], a[1], ..., a[i - 1]을 힙으로 만든다.
     *  > 4. i의 값을 1씩 줄여서 0이 되면 끝이 난다. 그렇지 않으면 2로 돌아간다.
     *
     *
     *              배열을 힙으로 만들기
     *
     *
     *                      *
     *              4               *
     *       8           5
     *     7   6       3
     *
     *     4부터 트리를 타고 내려가면 힙이 아니다.
     *     8, 5를 루트로 생각하면 힙이다.
     *
     *     앞에서는 루트를 없앤 다음 마지막 요소를 루트로 옮기고 루트를 옮긴 요소를 알맞은 위치로 옮기면서 힙을 만들었다. 여기에서도 이 방법으로
     *     루트 4를 알맞은 위치로 옮기면 부분트리 (4 ~ 3)를 힙으로 만들 수 있다.
     *
     *
     *     이 방법을 이용하면 아랫부분의 작은 부분 트리부터 시작해서 올라가는 (bottom-up)으로 전체 배열을 힙으로 만들 수 있다.
     */
    void downHeap(int[] array, int leftIndex, int rightIndex){ //배열에서 root ~ root의 오른쪽 자식 인덱스?
        int beforeRoot = array[leftIndex]; //기존의 root 위치
        int parentIndex; //부모 노드
        int childIndex; //변경 대상 자식 노드

        for ( parentIndex = leftIndex; parentIndex < (rightIndex + 1) / 2; parentIndex = childIndex){

            int leftNodeIndex = parentIndex * 2 + 1; //부모 기준 왼쪽 자식 노드 인덱스
            int rightNodeIndex = leftNodeIndex + 1; //부모 기준 오른쪽 자신 노드 인덱스

            childIndex = (rightNodeIndex <= rightIndex  //배열 범위에 오른쪽 자식 노드가 포함되어 있고
                            && array[rightNodeIndex] > array[leftNodeIndex])// 자식 노드 중에서 큰 값이 (루트 노드는 어떤 자식 노드보다 커야하므로)
                            ? rightNodeIndex : leftNodeIndex;

            if( beforeRoot >= array[childIndex] ) break; //애초에 부모 노드가 자식 노드 중 가장 큰 경우라면 힙상태임
            array[parentIndex] = array[childIndex]; //힙이 아닌 상태므로 가장 큰 자식 노드를 부모 노드로 교체
       }
        //for의 조건식에서 부모 인덱스에 자식 인덱스를 넣음
        array[parentIndex] = beforeRoot;
    }
    void drawHeap(int[] array){
        int index = 0;
        int count = 0;

        while(true){
            if ( (array.length - (int) Math.pow(2, count++)) < 0 ) break;
        }

        for(int j = 0; j < count; j++){
            int threeEnd = (int) Math.pow(2, j);
            int staticIndex = index;
            for(; index < staticIndex + threeEnd ; index++){
                System.out.printf("%"+ (count - j) +"s", index > (array.length - 1) ? "" : array[index]+"");
            }
            System.out.println();
        }
        System.out.println("\n\n");


        /**
         *                      0                           0     1  0
         *              1               2                   1     2  1 ~ 2
         *         3      4         5        6              2     4  3 ~ 6
         *      7   8  9   10    11   12   13   14          3     8
         */

    }


    @Test
    void makeHeap(){
        int[] array = {1, 10, 5, 8, 9, 2, 4, 6, 7, 3};
        System.out.println((array.length - 1) / 2); //이러면 마지막 노드에서 부터 시작 이 노드를 루트로 놓는 힙을 찾을거고 리소스 낭비
        for(int i = ( array.length - 1); i >= 0; i--){
            downHeap(array, i, array.length - 1);
            drawHeap(array);
        }
        print(array);
/**
 *              10
 *           9      5
 *       8     3  2   4
 *     6  7   1
 */
    }
    @Test
    void heapSortTest(){
        int[] array = {1, 10, 5, 8, 9, 2, 4, 6, 7, 3};
        //부모는 (index - 1) / 2
        //자식은 (index * 2) + 1(2)

        for(int i = ( array.length - 1 ) / 2; i >= 0; i--){  //제일 마지막 노드의 부모부터 힙으로 만들기 시작하기
            downHeap(array, i, array.length - 1);
        }
        //모두 힙으로 만들어 놓은 상태

        for ( int i = (array.length - 1) ; i > 0; i--){//전체 배열의 뒤에서부터 하나씩
            swap(array, 0, i);
            downHeap(array, 0, i - 1); //루트 노드가 틀어졌기 때문에 부분적으로 힙상태 깨진 것이 아님
        }
        print(array);
    }
    /**
     *
     *          도수 정렬
     *  도수 정렬은 요소의 대소 관계를 판단하지 않고 빠르게 정렬할 수 있는 알고리즘이다.
     */
}
