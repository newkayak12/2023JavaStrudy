import org.junit.jupiter.api.Test;

import java.util.Scanner;

/**
 * Created on 2023-03-24
 * Project 2023JavaStrudy
 */
public class Chapter_01_Algorithm {
    Scanner scanner = new Scanner(System.in);

    @Test
    public void  threeNumberMaxNumber() {
      // 세 정수 값을 입력 받고 max
        Integer a = 0;
        Integer b = 0;
        Integer c = 0;
        Integer max = 0;
        System.out.println("세 정수 최대값 구하기");
        System.out.println("A : 412");
        a = 412;

        System.out.println("B : 123");
        b = 123;

        System.out.println("C : 867");
        c = 867;

        max = b;
        if( a > b) max = a;
        if( max < c) max = c;

        System.out.println("MAX : "+max );
    }

    @Test
    public void threeNumberMiddleNumber() {
        Integer a = 1492;
        Integer b = 1239;
        Integer c = 6980;
        Integer max = a;
        Integer center = a;
        Integer other1 = 0;
        Integer other2 = 0;
        if(a > b) max = a;
        if(max < c) max = c;


        if(a == max) {
            other1 = b;
            other2 = c;
        }
        if(b == max ){
            other1 = a;
            other2 = c;
        }
        if(c == max){
            other1 = b;
            other2 = a;
        }

        center = other1;
        if(other1 < other2) center = other2;

        System.out.println("Center : " + center);
    }


}
