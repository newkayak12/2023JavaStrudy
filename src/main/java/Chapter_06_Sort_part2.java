import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created on 2023-04-04
 * Project 2023JavaStudy
 */
public class Chapter_06_Sort_part2 {
    void print(int[] a){
        System.out.println(Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(", ")));;
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
            int resultIndx = left;

            basicMergeSort(array, buff, left, center); // 그루핑 left
            basicMergeSort(array, buff, center + 1, right);// 그루핑 right

            for ( mainBufferIndex = left; mainBufferIndex <= center; mainBufferIndex++ ){ //왼쪽 그루핑 버퍼로 옮겨 담기
                buff[leftBufferIndex++] = array[mainBufferIndex]; //버퍼에는 왼쪽 그루핑이 담겨 있음음
           }
            //temporaryIndex는 왼쪽 그룹에 버퍼에 쏟아 부으면서 오른쪽 인덱스 시작으로 가 있음
            while ( mainBufferIndex <= right && rightBufferIndex < leftBufferIndex){ //왼쪽 그루핑 데이터를 담은 버퍼와 오른쪽 그루핑 간 정렬
                array[resultIndx++] = (buff[rightBufferIndex] <= array[mainBufferIndex]) ? buff[rightBufferIndex++] : array[mainBufferIndex++];
                //실질 정렬
            }
            while (rightBufferIndex < leftBufferIndex){
                array[resultIndx++] = buff[rightBufferIndex++]; //작업 결과물을 담은 버퍼 배열 원본으로 옮겨 담기
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

}
