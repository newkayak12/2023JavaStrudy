package algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Chapter_03_Search {
    /**
     *  1.  배열 검색
     *
     *  1-1. 선형 검색 : 무작위로 늘어놓은 데이터 모임에서 검색을 수행한다.
     *  1-2. 이진 검색 : 일정한 규칙으로 늘어놓은 데이터 모임에서 아주 빠른 검색을 수행한다.
     *  1-3. 해시법 : 추가, 삭제가 자주 일어나는 데이터 모임에서 아주 빠른 검색을 수행한다.
     *    1-3-1. 체인점 : 같은 해시 값의 데이터를 선형 리스트로 연결하는 방법
     *    1-3-2. 오픈 주소법 : 데이터를 위한 해시 값이 충돌할 때 재해시하는 방법
     *
     *
     *  데이터 집합이 있을 때 검색에 사용할 알고리즘은 계산 시간이 짧은 것을 선택하면 된다. 그러나 데이터 집합에 대한 검색뿐 아니라 데이터의 추가,
     *  삭제 등을 자주하는 경우라면 검색 이외의 작업에 소요되는 비용을 종합적으로 평가하여 알고리즘을 선택해야 한다.
     *  예를 들어 데이터 추가를 자주 하는 경우에는 검색이 빠르더라도 데이터의 추가 비용이 많이 들어가는 알고리즘은 피하는 것이 좋다.
     *
     *
     *
     *
     *      1-1. 선형 검색
     *
     * 요소가 직선 모양으로 늘어선 배열에서의 검색은 원하는 키 값을 갖는 요소를 만날 때까지 맨 앞부터 순서대로 요소로를 검색하면 되는데, 이를 선형 검색(linear search)
     * 또는 순차 검색(sequential search) 이라는 알고리즘이다.
     *
     * 이 경우의 탐색은 조건을 두 가지 갖게 된다.
     *
     *      조건 1) 검색할 값을 발견하지 못하고 배열의 끝을 지나간 경우
     *      조건 2) 검색할 값과 같은 요소를 발견한 경우
     *
     */

    @Test
    void seqSearch(){
        int key = 55;
        int i = 0;
        int[] target = {22,8,55,32,120,55,70};
        int idx = 0;


//        while (true) {
//            if( i >= target.length - 1 ) {
//                idx = -1;
//                break;
//            }
//            if(target[i] == key) {
//                idx = i;
//                break;
//            }
//            i++;
//        }
        for ( ; i < target.length; i++){
            if(target[i] == key){
                break;
            } else if (target[i] != key && i == target.length -1){
                i = -1;
                break;
            }
        }

        idx = i;

        System.out.println(idx);
    }
    /**
     * SeqSearch는 배열의 처음부터 끝까지 순회하며 지정된 key와 같은 요소를 검색하면 인덱스를 반환한다. 만약 key와 같은 요소가 여러 개라면
     * 처음으로 발견한 이후 종료된다.
     *
     *
     *      1-2. 보초법
     *
     * 선형 검색은 반복할 때마다 종료조건 - 1) 검색할 값을 발생하지 못하고 배열의 끝을 지나간 경우, 2) 검색할 값과 같은 요소를 발견할 경우-을 체크한다.
     * 이 비용은 무시하기 어렵다. 이 비용을 반으로 줄이는 보초법(sentinel method)이다.
     *
     * 보초법은 검색하기 전에 검색하고자 하는 키 값을 맨 끝 요소에 저장한다. 이때 저장하는 값을 보초라고 한다.
     *
     *      > a를 검색하기 위해 배열의 맨 끝에 a를 저장한다.
     *      > b를 검색하기 위해 배열의 맨 끝에 b를 저장한다.
     * 그러면 원하는 값이 원래 데이터에 존재하지 않아도 보초를 검색하면 종료 조건 2)가 성립한다. 이렇게하면 1)번의 종료 조건을 고려할 필요가 없어진다.
     * 보초는 반복문에서 종료 판단 횟수를 2회에서 1회로 줄인다.
     */

    @Test
    void searchSen(){
        int[] target = {22, 8, 55, 32, 120, 55, 70, 0};
        int key = 120;
        target[target.length - 1] = key;

        int i = 0;
        while (true) {
            if(target[i] == key) break;

            i++;
        }

        System.out.println(i == target.length - 1 ? -1 : i);
    }

    /**
     * [1] 배열의 맨 끝에 보초를 대입
     * [2] 배열을 순서대로 검사
     * [3] 조건이 완료되면 찾은 인덱스가 맨 끝인지 아닌지 판별
     *
     *
     *      1-3. 이진 검색
     *
     * 이 알고리즘을 적용하는 전제 조건은 데이터가 키 값으로 이미 정렬되어있다는 것을 전제로 한다. 이진 검색은 선형 검색보다 조금 더 빠르게 검색할 수 있다는
     * 장점이 있다.
     *
     *
     *      > 이진 검색
     * 이진 검색(binarySearch)은 요소가 오름차순 또는 내림차순으로 정렬된 배열에서 검색하는 알고리즘이다.
     *
     * 예시) [5, 7, 15, 28, 29, 31, 39, 58, 68, 70, 95]
     *
     *  만약 39를 찾으려한다고 한다. 중간 값 31을 기준으로 왼쪽으로는 모두 배제해도 된다. 그 다음 남은 영역 중 중간 값 68을 잡는다.
     *  이번에는 오른쪽을 배제하면 된다.
     *
     *  이러한 방법은 업/다운 게임과 굉장히 유사하다. 이제 수식으로 생각해보자
     *
     *  인덱스의 시작을 pl, 맨 끝을 pr 중앙을 pc로 지정하자. 검색을 시작할 때는 pl은 0, pr은 n - 1, pc (n-1)/2으로 초기화한다.
     *  결과적으로 pc 인덱스의 값과 찾고자하는 값이 같으면 성공이다.
     *
     *      case 1. a[pc] < key
     *          a[pl] ~ a[pc]는 제외하면 된다. 검색 범위는 a[pc] 이후인 a[pc + 1] ~ a[pr]로 좁히면된다.
     *          그런 다음 pl을 pc + 1로 업데이트 한다.
     *
     *      case 2. a[pc] > key
     *          a[pc] ~ a[pr]은 key보다 크므로 제외한다. 이 경우 a[pr]을 a[pc - 1]로 업데이트 한다.
     *
     */


    @Test
    void binarySearch(){
        int[] array = {15, 27, 39, 77, 92, 108, 121};
        int key = 39;
        int pl = 0;
        int pr = array.length - 1;
        int result = 0;

        do {
            int pc = (pl + pr) / 2;
            if( array[pc] == key ) {
                result = pc;
                break;
            } else if( array[pc] < key ){
                pl = pc + 1;
            } else {
                pr = pc - 1;
            }
        } while(pl <= pr);

        System.out.println(result);
    }


    /**
     *
     * 복잡도
     *
     * 알고리즘의 성능을 객관적으로 평가하는 기준을 복잡도(complexity)라고 한다.
     *
     *  > 1. 시간 복잡도(time complexity) : 실행에 필요한 시간을 평가한 것
     *  > 2. 공간 복잡도(space complexity) : 기억 영역과 파일 공간이 얼마나 필요한 가를 평가한 것
     *
     *
     *  시간 복잡도를 표기할 떄는 'O (Order)'를 사용한다. 선형 검색의 예로 살펴보면 평균 실행 횟수는 n / 2이다. 이처럼 n 에 비례하는
     *  횟수만큼 실행하는 경우 복잡도를 O(n)으로 표기한다. 여기서 O(n/2)로 표기하지 않는 이유는 n이 무한이 커지면 별 차이가 없어지기 때문이다.
     *
     *  그런데 n이 점점 커지면 O(n)에 필요한 계산은 n에 비례해서 점점 길어진다. 일반적으로 O(f(n))과  O(g(n))의 복잡도를 계산하는 방법은
     *
     *          O(f(n)) + O(g(n)) = O(max(f(n), g(n))
     *
     *  이 된다.
     *
     *  2개 이상의 복잡도로 구성된 알고리즘의 전체 복잡도는 차원이 더 높은 쪽의 복잡도를 우선시 한다. 위의 예시처럼 두 개가 아닌 세 개가 되더라도
     *  마찬가지이다. 정리하면 전체 복잡도는 차원이 가장 높은 복잡도를 따라간다. 그러므로 선형 검색 알고리즘의 복잡도는
     *
     *          O(1) + O(4) + .... + O(n)  = O(max(1,4,....., n)= O(n)이 된다.
     *
     */
    @Test
    void sequentialSearch(){
        int[] target = {6,4,3,2,1,9,8, 0};
        int key = 3;
        int result = 0;
        target[target.length - 1] = key;


        System.out.println(String.format("%2s | %s", "", IntStream.rangeClosed(0, target.length - 2).mapToObj(String::valueOf).collect(Collectors.joining("  "))));
        System.out.println("---------------------------");
        for(int i = 0; i < target.length; i++){
            String layout = "%2s |"+ (i == 0? "" : "%"+i*3+"s")+" %s";
            System.out.println(String.format(layout, "", i==0? "*":"", "*"));
            System.out.println(String.format("%2s | %s", i, Arrays.stream(target).limit(target.length - 1).mapToObj(String::valueOf).collect(Collectors.joining("  "))));
            if(target[i] == key) {
                result = i;
                break;
            }
        }
        System.out.println("\n::"+key+" 는(은) x["+result+"]에 있다.");
    }
    /**
     * 이진 검색의 시간 복잡도
     *
     *  int[] array = {15, 27, 39, 77, 92, 108, 121};
     *         int key = 39;
     *         int result = 0;
     *
     *
     *         int pl = 0;  [1]
     *         int pr = array.length - 1; [2]
     *
     *         do {
     *             int pc = (pl + pr) / 2; [3]
     *             if( array[pc] == key ) { [4]
     *                 result = pc; [5]
     *                 break;
     *             } else if( array[pc] < key ){ [6]
     *                 pl = pc + 1; [7]
     *             } else {
     *                 pr = pc - 1; [8]
     *             }
     *         } while(pl <= pr);[9]
     *
     *
     * 이진 검색의 복잡도는 순서대로 O(1), O(1), O(long n), O(long n), O(1), O(long n), O(long n), O(long n), O(long n), O(1)
     * 이기 때문에 결과적으로 O(long n)가 된다.
     */

    @Test
    void binarySearch2(){
        int count = 0;
        int[] target ={1,2,3,5,6,8,9};
        String[] ui = {"", "", "", "", "", "", ""};
        int key = 6;
        int pl = 0;
        int pr = target.length - 1;
        int pc = (pl + pr) / 2;

        String result = "찾는 값이 없다.";

        ui[pl] = "<- ";
        ui[pc] = " * ";
        ui[pr] = " ->";

        String header = IntStream.rangeClosed(0, target.length -1 ).mapToObj(String::valueOf).collect(Collectors.joining("  "));
        System.out.println(String.format("%2s | %s", "", header));
        System.out.println("---------------------------");
//        System.out.println(String.format("%2s | %s", "", Arrays.stream(ui).collect(Collectors.joining("  "))));
//        System.out.println(String.format("%2s |  %s", count, Arrays.stream(target).mapToObj(String::valueOf).collect(Collectors.joining("  "))));
        do {
            if(target[pc] == key){
                result = String.format("%d는 %d 번째에 있다.", key, pc + 1);
                break;
            } else if ( target[pc] > key){
                String[] newUi = {"", "", "", "", "", "", ""};
                ui = newUi;
                ui[pl] = "<- ";
                ui[pc] = " * ";
                ui[pr] = " ->";
                pr = pc - 1;
                pc = (pl + pr) / 2;
            } else if( target[pc] < key){
                String[] newUi = {"", "", "", "", "", "", ""};
                ui = newUi;
                ui[pl] = "<- ";
                ui[pc] = " * ";
                ui[pr] = " ->";
                pl = pc + 1;
                pc = (pl + pr) / 2;
            }
            System.out.println(String.format("%2s | %s", "", Arrays.stream(ui).collect(Collectors.joining("  "))));
            System.out.println(String.format("%2s |  %s", count, Arrays.stream(target).mapToObj(String::valueOf).collect(Collectors.joining("  "))));
            count ++;
        } while( pl <= pr);
        System.out.println(result);
    }

    @Test
    void binarySearchX() {
        int[] array = {1,3,5,7,7,7,7,8,8,9,9};
        int key = 7;
        int pl = 0;
        int pr = array.length - 1;
        int pc = (pl + pr) / 2;
        int result = 0;

        do {
            if( array[pc] == key ){
                int temp = 0;
                for(int i = pc; i >= 0; i--) {
                    if(array[i - 1] == key){
                       temp = (i - 1);
                       break;
                    }
                }
                result = temp;
                break;
            } else if ( array[pc] < key) {
                pl = pc + 1;
                pc = (pl + pr) / 2;
            } else if( array[pc] > key) {
                pr = pc - 1;
                pc = (pl + pr) / 2;
            }

        } while (pl <= pr);

        System.out.println(result);
    }

    /**
     * Arrays.binarySearch에 의한 이진 검색
     *
     */

    @Test
    void test() {
        int[] array = {1,2,7,6,52,9, 54};
        System.out.println(Arrays.binarySearch(Arrays.stream(array).sorted().toArray(), 54));
    }
    /**
     * 위와 같이 java.util.Arrays에서 binarySearch 메소드를 지원한다.
     * 검색에 성공한 경우 key와 일치하는 요소의 인덱스를 반환한다. 일치하는 요소가 여러 개 있다면 무작위 인덱스를 반환한다. 맨 앞이나 어떤 특정한 인덱스를 반환하는 것이 아니다.
     * 검색에 실패한 경우 삽입 포인트를 X라고 했을 때, -X-1로 반환한다.
     *
     *
     *
     * 객체의 배열에서 검색하기
     * 객체의 배열에서도 검색이 가능하다.
     */

    @Test
    void test2() {
        String[] x = {"Apple", "Bear", "Car", "Demon","Egg", "Flower", "Glory", "High", "Intellij", "Joy", "King", "Lol"};
        Arrays.binarySearch(x, "Intellij");
    }


}

