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
     *  두 정수의 최대 공약수를 재귀적으로 구하는 방법을 알아보자.
     */


}

