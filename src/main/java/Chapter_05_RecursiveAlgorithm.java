import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Created on 2023-03-31
 * Project 2023JavaStudy
 */
public class Chapter_05_RecursiveAlgorithm {
    /**
     *      재귀란?
     * 어떤 사건이 자기 자신을 포함하고 다시 자기 자신을 사용하여 정의될 때 재귀적이라고 한다.
     *
     *      팩토리얼 구하기
     */
    public Long factorial(Long value){
        if(value > 0) return   value * factorial(value - 1);
         else  return 1L;
    }
    @Test
    void factorialTest(){
        System.out.println(factorial(20L));
    }
    /**
     *      직접 재귀와 간접 재귀
     *  재귀 안에서 본인을 호출하면 직접 재귀, 다른 메소드를 통해서 자신을 호출하면 간접재귀라고 한다.
     *
     *
     *      유클리드 호제법
     *  두 정수의 최대 공약수를 재귀적으로 구하는 방법을 알아보자. 두 정수를 직사각형의 두 변의 길이라고 생각하면 두 정수의 최대공약수를 구하는 문제는 아래와 같이 바꿀 수 있다.
     *
     *  >  직사각형을 정사각형으로 완전히 채운다. 이렇게 만들 수 있는 정사각형의 가장 긴 변의 길이를 구해라.
     *
     *  [1] 22 x 8 크기의 직사각형에서 짧은 변(8)을 한 변으로 하는 정사각형으로 분할한다. 이렇게 하면 8 X 8 두 개, 8 X 6 하나가 남는다.
     *  [2] 남은 8 X 6 크기의 직사각형으로 다시 같은 과정으로 6 X 6 크기의 정사각형이 1개, 6 X 2 크기의 직사각형 1개 남는다.
     *  [3] 다시 남은 6 X 2 크기의 직사각형으로 같은 과정을 수행한다. 2 X 2 정사각형 3개로 나눌 수 있다. 여기서 얻은 2가 최대공약수이다.
     */
    public int gcd(int x, int y){
        if(y == 0) return x;
        else return gcd(y, x % y);
    }
    @Test
    void EuclidGCD(){
        System.out.println(gcd(288, 144));
    }

    @Test
    void factorialWithoutRecursive(){
        int target = 10;
        int result = 1;
        do {
            if( target >= 1) result *= (target--);
            else {
                result *= 1;
                target--;
            }
        } while (target >= 0);

        System.out.println(result);
    }
    @Test
    void euclidGCDWithoutRecursive(){
        int x = 144;
        int y = 12;

        do {
            if(y == 0) {
                System.out.println(x);
                break;
            } else {
                int temp = y;
                y = x % y;
                x = temp;
            }
        } while( true );
    }
    @Test
    void euclidArray(){
        int[] array = {12,144,288};
        int[] results = new int[array.length - 1];
        for ( int i = 1; i < array.length; i++){
            int x = array[i - 1];
            int y = array[i];
            results[i - 1] = gcd(x, y);
        }

        Arrays.stream(results).sorted().toArray();
        System.out.println(results[0]);
    }


    public void recursiveExample(int n){
        if ( n > 0 ){
            recursiveExample( n - 1 );
            System.out.println(n);
            recursiveExample( n - 2 );
        }
    }
    /**
     *      재귀 알고리즘 분석
     * 재귀 알고리즘을 분석하는 방법으로는 하향식 분석과 상향식 분석이 있다.
     *
     *      하향식 분석
     * 매개변수 n으로 4를 전달하면 위 예시 메소드는 아래와 같은 동작을 한다.
     *
     *
     *                                             recur(3)  4 recur(2)
     *                   recur(2) 3 recur(1)                                       recur(1) 2 recur(0)
     *    recur(1) 2 recur(0)         recur(0) 1 recur(-1)         recur(0) 1 recur(-1)              END
     *
     *                                                      ....
     *
     *
     *  이렇게 위에서 아래로 내려오면서 계단식으로 조사하는 방법을 하향식 분석이라고 한다.
     *
     *
     *
     *      상향식 분석
     *  위에서 아래로 진행하는 하향식 분석과는 대조적으로 아래서 위로 쌓아 올리는 방법이 상향식 분석이다.
     *
     *  [1] :  recur(0) 1 recur(-1)
     *  [2] :  recur(1) 2 recur(0)
     *  [3] :  recur(2) 3 recur(1)
     *  [4] :  recur(3) 4 recur(2)
     *
     *
     *
     *      재귀 알고리즘의 비재귀적 표현
     *
     *  1. 꼬리 재귀의 제거
     *  위 예시의 recur( n - 2 )는 인자로 n - 2 를 전달하여 recur를 실행시킨다는 의미이다. 이는 아래와 같이 변경할 수 있다.
     *
     *  void recur( int n ) {
     *      while ( n > 0 ) {
     *          recur( n - 1 );
     *          System.out.println(n);
     *          n = n - 2;
     *      }
     *  }
     *
     *  이렇게 하면 메소드 끝에서 실행하는 꼬리 재귀를 제거할 수 있다.
     *
     *
     *  2. 재귀의 제거
     *  꼬리 재귀와는 다르게 앞에서 호출한 재귀는 제거하기 쉽지 않다. 왜냐하면 System.out.println(n) 전에 recur( n - 1 )을 해야하기 때문이다.
     *  꼬리 재귀를 응용하면
     *   [1] n을 n - 1 로 바꾸고 메소드의 시작으로 돌아감
     *   [2] n을 잠시 저장
     *   [3] 저장한 n을 꺼내서 나머지 처리
     *
     *   여기서 잠시 저장하는 부분에 스택을 사용하면 될 것 같다.
     */

    @Test
    void removeRecursive(){
        int n = 5;
        IntStack stack = new IntStack( n );
        while ( true ) {
            if( n > 0 ){
                stack.push(n); // 5, 4, 3, 2, 1
                n = n - 1;
                continue;
            }

            if( !stack.isEmpty() ){
                n = stack.pop();
                System.out.println(n);
                n = n - 2;
                continue;
            }
            break;
        }
    }
    /**
     * 하노이의 탑
     *
     * 하노이의 탑은 작은 원반이 위에, 큰 원반이 아래에 위치하도록 원반을 3 개의 기둥 사이에 옮기는 문제이다. 모든 원반은 규칙에 맞춰 첫 번째 기둥에 쌓여 있다.
     * 이 상태에서 모든 원반을 세 번째 기둥으로 최소의 횟수로 옮기면 된다. 원반은 하나씩 옮길 수 있고 큰 원반을 작은 원반 위에 쌓을 수 없다.
     */

    @Test
    void hanoi() {
        int no = 3;
        int x = 1;
        int y = 3;

        move(no, x, y);
    }
    public void move(int no, int x, int y){
        if ( no > 1 ) move(no - 1, x,  6 - x - y);
        System.out.println(String.format("원반 [%d]를 %d 기둥에서 %d 기둥으로 옮김", no, x, y));
        if ( no > 1 ) move( no - 1, 6 - x - y, y);
    }

    /**
     * move(3, 1, 3)
     *      ᄂ move(2, 1, 2)
     *          ᄂ move(1, 1, 3)
     *                  ᄂ sysout : 1를 1에서 3으로
     *          ᄂ sysout : 2를 1에서 2로
     *          ᄂ move(1, 3, 2)
     *                  ᄂ sysout : 1를 3에서 2로
     *      ᄂ sysout : 3을 1에서 3으로
     *      ᄂ move (2, 2, 3)
     *          ᄂ move(1, 2, 1)
     *                   ᄂ sysout : 1를 2에서 1에서
     *          ᄂ sysout : 2를 2에서 3으로
     *          ᄂ move(1, 1, 3)
     *                  ᄂ sysout : 1를 1에서 3으로
     *
     */

    void example(int n){
        if ( n > 0 ){
            example(n - 1);
            example(n - 2);
            System.out.println(n);
        }
    }

    /**
     * ex3
     *  ex 2
     *      ex 1
     *          ex 0
     *          ex -1
     *          sout 1
     *      ex 0
     *      sout 2
     *  ex 1
     *      ex 0
     *      ex -1
     *      sout 1
     *  sout 3
     *
     */

    @Test
    void recur3Exam() throws InterruptedException {
        example(3); // 1 2 1 3
    }
    @Test
    void recur3Do() {
        int n = 3;
        int n1 = n;
        int n2 = n;
        IntStack s1 = new IntStack(n);
        IntStack s2 = new IntStack(n);
        while (true){
            if ( n1 > 0){
                n1 -= 1;
                s1.push(n1);
                continue;
            }
            if( n2 > 0){
                n2 -= 2;
                s2.push(n2);
                continue;
            }

            if(!s1.isEmpty()){
                int a = s1.pop();
                if (a > 0 ) System.out.println(a);
                continue;
            }
            if(!s2.isEmpty()){
                int a = s2.pop();
                if (a > 0) System.out.println(a);
                continue;
            }

            System.out.println(n);
            break;
        }
    }

    @Test
    void hanoiChar() throws InterruptedException {
        int number = 3;
        int from = 1;
        int to = 3;
        move2(number, from, to);

        System.out.println("NO RECURSIVE");

        move3(number, from, to);

    }
    public void move2(int num, int from, int to){
//        String fromLocation = from == 1? "A 기둥" : from == 2? "B 기둥" : "C 기둥";
//        String toLocation = to == 1? "A 기둥" : to == 2? "B 기둥" : "C 기둥";
        if(num > 1) move2(num - 1, from, 6 - from - to);
        System.out.println(String.format("%d을 %s에서 %s으로 옮김", num, from, to));
        if(num > 1) move2(num - 1, 6 - from - to, to);
    }

     void move3(int no, int x, int y) {
//        int[] xstk = new int[100];
//        int[] ystk = new int[100];
//        int[] sstk = new int[100];		// 스택
//        int ptr = 0; 						// 스택포인터
//        int sw = 0;
//
//        while (true) {
//            if (sw == 0 && no > 1) {
//                xstk[ptr] = x;						// x의 값을 푸시
//                ystk[ptr] = y;						// y의 값을 푸시
//                sstk[ptr] = sw;						// sw의 값을 푸시
//                ptr++;
//                no = no - 1;
//                y = 6  - x - y;
//                continue;
//            }
//
//            System.out.printf("원반[%d]을 %d 기둥에서 %d 기둥으로 이동\n", no, x, y);
//
//            if (sw == 1 && no > 1) {
//                xstk[ptr] = x;						// x의 값을 푸시
//                ystk[ptr] = y;						// y의 값을 푸시
//                sstk[ptr] = sw;						// sw의 값을 푸시
//                ptr++;
//                no = no - 1;
//                x = 6  - x - y;
//                if (++sw == 2) sw = 0;
//                continue;
//            }
//
//
//            do {
//                if (ptr-- == 0) 					// 스택이 비어 있으면
//                    return;
//                x  = xstk[ptr]; 					// 값을 저장하고 있는 x를 팝
//                y  = ystk[ptr]; 					// 값을 저장하고 있는 y을 팝
//                sw = sstk[ptr] + 1;				// 값을 저장하고 있는 sw을 팝
//                no++;
//            } while (sw == 2);
//        }
    }

    /**
     * 8퀸 문제
     *
     */


}

