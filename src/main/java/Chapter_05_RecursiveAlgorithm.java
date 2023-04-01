import org.junit.jupiter.api.Test;

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
     *
     */


}

