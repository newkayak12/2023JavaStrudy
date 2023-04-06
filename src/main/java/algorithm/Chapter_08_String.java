package algorithm;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created on 2023-04-04
 * Project 2023JavaStudy
 */
public class Chapter_08_String {
    /**
     *              문자열 검색
     * 문자열 검색이란 어떤 문자열 안에 다른 문자열이 들어 있는지 조사하고 들어 있으면 그 위치를 찾아내는 것이다.
     * 예를 들어서 "STRING", "KING"에서 "IN"을 검색하면 문자열 검색에 성공하지만 "QUEEN"에서 "IN"을 검색하면 실패한다.
     *
     *              1. 브루트 포스
     *
     *  "ABABCDEFGHA"에서 "ABC"를 부르트 포스로 검색해보자. 인덱스 한 칸씩을 이동하면 각 인덱스에 A, B, C가 있는지 하나라도 없으면 false 있으면 true를 반환하는
     *  무차별 대입법이다.
     *
     *
     */
    @Test
    void bruteForceTest(){
        int textCursor = 0;
        int patternCursor = 0;

        String text = "ABC이지스DEF";
        String pattern = "이지스";

        while (textCursor != text.length() && patternCursor != pattern.length()){//커서가 끝까지 가면 종료
            if(text.charAt(textCursor) == pattern.charAt(patternCursor) ){ //글자당 하나씩 비교해서 같은지
                textCursor ++;
                patternCursor ++;
            } else {
                textCursor = textCursor - patternCursor + 1;
                patternCursor = 0;// 패턴 커서 초기화와 함께 텍스트 커서도 돌려 놓음
            }
        }
        if(pattern.length()==patternCursor) System.out.println(textCursor - patternCursor); //패턴의 길이와 커서가 같으면
        else System.out.println(-1); //아니면면
   }

   @Test
    void bruteForceVisualizeTest(){
        int textCursor = 0;
        int patternCursor = 0;

        String text = "ABABCDEFGHA";
        String pattern = "ABC";
        int count = 0;

        while(textCursor != text.length() && patternCursor != pattern.length()){
            System.out.print(count % pattern.length() == 0 ? "\n"+count / pattern.length()+" " : "  ");
            System.out.println(text);
            if(text.charAt(textCursor) == pattern.charAt(patternCursor)){
                System.out.println(textCursor == 0 ? String.format("  %s", "+") : String.format("  %"+(1 + textCursor)+"s", "+"));
                textCursor ++;
                patternCursor ++;
            } else {
                System.out.println(textCursor == 0 ? String.format("  %s", "|") : String.format("  %"+(1 + textCursor)+"s", "|"));
                textCursor = textCursor - patternCursor + 1;
                patternCursor = 0;
            }
            System.out.println( count == 0 ? String.format("  %s", pattern) : String.format("  %"+(3 + (int) Math.floor(count/(pattern.length())))+"s", pattern));
            count ++;
        }

       System.out.println("비교 총 : "+ count);
   }

   @Test
    void bruteForceLast(){
        String text = "ABABCDEFGHA";
        String pattern = "ABC";

        int textCursor = text.length() - 1;
        int patternCursor = pattern.length() - 1;

        while(textCursor >= 0 && patternCursor >= 0){
            System.out.println(text.charAt(textCursor) + ":"+ pattern.charAt(patternCursor));
            if(text.charAt(textCursor) == pattern.charAt(patternCursor)){
                textCursor --;
                patternCursor --;
            } else {
                textCursor =  textCursor - patternCursor + 1;
                patternCursor = pattern.length() - 1;
            }
        }

       System.out.println(textCursor);
       if(patternCursor == -1) System.out.println(text.length() - 1  - textCursor + pattern.length() - 1); //패턴의 길이와 커서가 같으면
       else System.out.println(-1); //아니면면
   }
    /**
     *          2. KMP법
     *  KMP는 다른 문자를 만나면 패턴을 1칸씩 옮긴 다음 다시 패턴의 처음부터 검사하는 브루트포스와는 다르게 중간 검사 결과를 효율적으로 사용하는 알고리즘이다.
     *
     *  부르트 포스는 다른 문자를 만나면 패턴에서 문자를 검사했던 위치 결과를 버리고 다음 텍스트의 위치로 1칸 이동한 다음 패턴의 첫 번쨰 문자부터 검사한다.
     *  하지만 KMP는 검사했던 위치 결과를 버리지 않고 이를 활용하는 알고리즘이다.
     *
     *      Z A B C A B X A C C A D E F
     *      A B C A B D
     *
     * 위 예시에서 패턴과 텍스트는 첫 시작부터 맞지 않는다. 그러면 패턴을 한 칸 이동 시킨다. 이때 패턴을 처음부터 순서대로 검사하면
     *
     *      Z A B C A B X A C C A D E F
     *        A B C A B D
     *
     * 패턴의 마지막 문자가 D이기 때문에 텍스트의 X와 일치하지 않는다.
     *
     *
     * 여기서 패턴의 시작이 AB인점, 텍스트 X의 전 글자가 AB인 점을 이용한다. 이 부분은 '이미 검사를 마친 부분'이므로 텍스트의 'X' 다음
     * 문자부터 패턴의 "CABD"가 일치하는지 검사하면 된다. 그래서 아래와 같이
     *
     *      Z A B C A B X A C C A D E F
     *              A B C A B D
     *
     *  한 번에 3 칸을 이동시키고 3번째 문자인 'C'부터 검사를 시작하면 된다.
     *
     *  이와 같이 KMP는 텍스트와 패턴의 겹치는 부분을 찾아서 검사를 다시 시작할 위치를 구한다. 이런 방법으로 패턴을 최소 횟수로 옮겨 알고리즘의
     *  효율을 높입니다.
     *
     *  하지만 몇 번째 문자부터 다시 검색을 시작할지 패턴을 이동시킬 때마다 다시 계산해야한다면 큰 효율을 기대할 수 없습니다.
     *  그래서 '몇 번쨰 문자부터 다시 검색할지'에 대한 값을 미리 '표'로 만들어 해결한다.
     *
     *  표를 작성할 떄는 패턴 안에서 중복되는 문자의 나열을 먼저 찾아야 한다. 이 과정에서 KMP 법을 사용한다.
     *
     *      A B C A B D                                  A B C A B D
     *        A B C A B D                                - 0 0 1 2 0
     *
     *  각 텍스트 당, 패턴 매칭을 할 때 패턴 검사를 어디부터 시작해야하는 가에 대한 인덱스를 기록해 놓은 표를 만든다.
     *  맨 처음 A는 첫 번째라 논외고, 텍스트 B는 패턴 A ( index 0 ) 부터 시작해야하므로 0이다. 쭉 나아가서 두 번째 A는 패턴 A와 일치하므로
     *  패턴의 B( index 1 ) 부터 검사하면 된다. 그 뒤의 B는 A, B는 패턴과 텍스트가 일치하므로 패턴의 C( index 2 ) 부터 검사하면 된다는 의미가 된다.
     *
     *
     */


    @Test
    void kmpTest(){
        String text = "ABCABDAA";
        String pattern = "ABCABD";

        int textCursor = 1; // 스킵테이블에 0번, 텍스트의 0번 인덱스는 패턴의 처음부터 매칭해야 하므로 필요 없음 따라서 1부터 시작
        int patternCursor = 0; //패턴의 인덱스
        int[] skipTable = new int[pattern.length() + 1]; // pattern.length()가 맞는가?  text.length()가 아니라?

        //Skip 테이블 만들기
        skipTable[textCursor] = 0;
        while( textCursor != pattern.length() ) {
            System.out.println(textCursor +" : "+ patternCursor);

            if(text.charAt(textCursor) == pattern.charAt(patternCursor)){ //텍스트 글자 == 패턴 글자
                skipTable[++textCursor] = ++patternCursor; //텍스트 커서에 패턴 몇 번째부터 시작하면 되는지 저장
            } else if ( patternCursor == 0 ) { //패턴 커서가 0 (패턴 처음부터이면)
                skipTable[++textCursor] = patternCursor; //테이블에 패턴 시작 위치(0)을 대입하고 넘어감
            } else { //패턴 커서가 0이 아니면
                patternCursor = skipTable[patternCursor]; //패턴 커서를 0으로 바꾼다. (int 배열 기본 초기값 = 0 )
            }
        }
        System.out.println(Arrays.stream(skipTable).mapToObj(String::valueOf).collect(Collectors.joining(", ")));

        //검색
        textCursor = patternCursor = 0;
        //초기화
        while (textCursor != text.length() && patternCursor != pattern.length()) {
            if(text.charAt(textCursor) == pattern.charAt(patternCursor)) {
                textCursor ++ ;
                patternCursor ++ ;
            } else if (patternCursor == 0){
                textCursor ++ ;
            } else {
                patternCursor = skipTable[patternCursor];
            }
        }

        if( patternCursor == text.length()) {
            System.out.println(textCursor - patternCursor);
        } else {
            System.out.println( -1 );
        }
    }

    /**
     *          Boyer-Moore 법
     * Boyer-Moore법은 프루트 포스를 개선한 KMP 보다 효율적이기 때문에 실 검색에서 더 많이 쓰는 알고리즘이다.
     *
     *
     *      A B C X D E Z C A B A C A B A C
     *      A B A C           [a]
     *        A B A C         [b]
     *          A B A C       [c]
     *            A B A C     [d]
     *
     *  맨 처음 패턴 글자와 텍스트의 첫 번쨰 글자를 겹치고 패턴의 마지막 글자와 텍스트의 같은 인덱스의 글자를 검사한다. 만일 일치하지 않으면
     *  패턴을 하나씩 밀어서 검사를 한다.
     *
     *  위의 예시와 같이 '텍스트 안에 패턴이 들어있지 않은 문자를 찾으면 해당 위치까지 문자는 건너뛸 수 있다'.
     *
     *      A B C X D E Z C A B A C A B A C
     *              A B A C
     *
     *  건너뛴 상태에서 마지막 글자를 검사하면 C로 일치한다. 그러면 한 칸 앞당겨 패턴과 텍스트 일치여부를 본다.
     *  만일 불일치라면 패턴을 한 칸씩 밀어서 검사를 한다.
     *
     *
     *
     *  만약 패턴을 모두 밀어도 일치하지 않으면 패턴을 한꺼번에 옮긴다. <패턴에 없는 글자 인덱스 한 칸 뒤로 보내는 것으로 보인다.>
     *
     *       A B C X D E Z C A B A C A B A C
     *                     A B A C
     *
     *   옮긴 다음 검사를 하면 A, C이므로 민다. 일치하지 않으면 밀고 일치하면 앞의 인덱스를 검사하는 식으로 반복한다.
     *   만일 결과적으로 모든 인덱스와 일치하게 되면 검색이 성공한다.
     *
     *   그런데 Boyer-Moore 알고리즘도 각각의 문자를 만났을 때 패턴을 옮길 크기를 저장할 표(건너뛰기 표)를 만들어야 한다.
     *
     *
     *      >   패턴에 들어 있지 않은 문자를 만난 경우
     *      > 1. 패턴을 옮길 크기는 n이다. (패턴의 크기)
     *      >   패턴에 들어있는 문자를 만난 경우
     *      > 1. 마지막에 나오는 위치의 인덱스가 k이면 패턴을 옮길 크기는 (n - k  - 1)이다.
     *      > 2. 같은 ㅁ눈자가 패턴 안에 중복해서 들어 있지 않다면, 패턴을 옮길 크기는 n이다.
     *
     *   이 건너뛰기 표는 패턴에 존재할 수 있는 문자의 옮길 크기를 계산하고 저장해야 하기 때문에 건너뛰기 표의 요소 개수는 Character.MAX_VALUE + 1 이다.
     */
    @Test
    void boyerMooreTest(){
        String text = "ABABCDEFGHA";
        String pattern = "ABC";
        int resultIndex = -1;
        int textCursor;
        int patternCursor;
        int textLength = text.length();
        int patternLength = pattern.length();

        int[] skip = new int[Character.MAX_VALUE + 1]; //초기화

        //createSkipTable
        /**
         * Key : char
         * Value: index of pattern char
         */
        for( textCursor = 0; textCursor <= Character.MAX_VALUE; textCursor++){
            skip[textCursor] = patternLength; //전체 다 패턴 길이로 들어감 (패턴 인덱스 안에 없음)
        }
        for( textCursor = 0; textCursor < patternLength - 1; textCursor++){
            skip[pattern.charAt(textCursor)]  = patternLength -textCursor - 1; //해당 글자가 패턴 인덱스 몇 번에 존재하는가?
        }

//        IntStream.rangeClosed(0, 10).forEach(intValue -> {
//            System.out.println((char)skip[intValue] + " : " + skip[intValue]);
//        });


        //Search
        outer: while ( textCursor < textLength ){
            patternCursor = patternLength - 1; //패턴 검색은 늘 패턴 마지막부터

            inner: while( text.charAt( textCursor ) == pattern.charAt( patternCursor ) ){ //일치 여부
                if( patternCursor == 0 ){ //패턴 인덱스 모두 순회했는데 같은 경우
                    resultIndex = textCursor;
                    break outer;
                }
                patternCursor --; //하나씩 줄여나감
                textCursor --; //하나씩 줄여가면서 해당 위치 글자와 같은지 체크

            }

            textCursor += ( (skip[text.charAt(textCursor)]  > patternLength - patternCursor) ? skip[text.charAt(textCursor)] : patternLength - patternCursor);
            // 검색 단어가 패턴 안에 있는가? && 있더라도 순서가 다른가?                                   //완전 이동                         //부분 이동
        }

        System.out.println(resultIndex);
    }

}
