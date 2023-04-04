import org.junit.jupiter.api.Test;

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

        while (textCursor != text.length() && patternCursor != pattern.length()){
            if(text.charAt(textCursor) == pattern.charAt(patternCursor) ){
                textCursor ++;
                patternCursor ++;
            } else {
                textCursor = textCursor - patternCursor + 1;
                patternCursor = 0;
            }
        }
        if(pattern.length()==patternCursor) System.out.println(textCursor - patternCursor);
        else System.out.println(-1);
    }
}
