package designPattern;

import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * Created on 2023-04-07
 * Project 2023JavaStudy
 */
public class C05_Singleton {
    /**
     * 프로그램을 실행하면 보통 많은 인스턴스가 생성된다. 하지만 '클래스의 인스턴스를 딱 하나만 만들고 싶을 때'가 있다. 바로 시스템 안에
     * 1개만 존재한다는 것을 프로그램으로 표현하고 싶을 때이다. 물론 단 한 번만 new 연산자로 만들면 할 수 있는 일이지만
     *
     *          1. 지정한 클래스의 인스턴스가 반드시 1개만 존재한다는 것을 보증하고 싶을 때
     *          2. 인스턴스가 하나만 존재한다는 것을 프로그램 상에서 표현하고 싶을 때
     *
     *  이렇게 인스턴스가 하나만 존재하는 것을 보증하는 패턴을 Singleton 이라고 한다. Singleton은 요소가 하나 뿐인 집합을 의미한다.
     *
     *
     *          Singleton
     *   Singlton 클래스에서는 인스턴스를 하나만 만들 수 있는데 Singleton 은 static 필드로 정의 되고 Singleton 클래스의 인스턴스에서 초기ㅗ하
     *   된다. 초기화는 Singleton  클래스를 로드할 때 한 번만 실행된다.
     *
     *   Singleton 클래스의 생성자는 private로 되어 있다. 이는 Singleton 클래스 외부에서 생성자 호출을 금지하기 위해서 이다.
     *   만약 아래와 같은 코드가 클래스 외부에 있어도 컴파일 에러를 낸다.(생성자가 private)
     *
     *              new Singleton();
     *
     *    보통 new로 만들지만 단 하나의 인스턴스만 만들어야 하기 때문에 임의적으로 new를 호출하는 것을 막기 위한 조치이다. 또한 getInstance가
     *    Singleton 인스턴스를 얻는 메소드로 제공된다. 일종의 static Factory Method이다.
     *
     */

    @Test
    void singletonTest () {
        Singleton firstInstance = Singleton.getInstance();
        Singleton secondInstance = Singleton.getInstance();

        System.out.println(firstInstance.equals(secondInstance));

        if (firstInstance == secondInstance) System.out.println("same");
        else System.out.println("different");
    }
}
class Singleton {
    private static Singleton singleton = new Singleton();  //private static
    private static Singleton pattern2 = null;

    private Singleton() {
        System.out.println("INITIALIZED");
    }
    public static Singleton getInstance( ) {
        return singleton;
    }
    public static synchronized Singleton getInstance2( ) {
        if(Objects.isNull(pattern2)) {
            pattern2 = new Singleton();
        }
        return pattern2;
    }
}
/**
 *          왜 싱글톤을 사용하는가?
 *   인스턴스 수를 1개라고 전제를 두고 코드를 짠다던지, 어떤 경우에서 같은 인스턴스를 사용해야한다던지 하는 경우에 주로 사용한다.
 *
 *
 *          enum
 *   enum의 요소는 상수로서 인스턴스의 유일성을 보증받는다. 예를 들어 java.time.Month.APRIL은 달력의 4월을 나타내는 인스턴스이자.
 *   유일한 인스턴스이다. 그러므로 요소를 하나만 가지는 enum을 가지고 아래와 같이 구현할 수 있다.
 */
enum EnumSingleton {
    INSTANCE;
    public void hello () {
        System.out.println("HELLO");
    }
};
/**
 *   사용은 EnumSingleton.INSTANCE.hello()
 *   와 같이 할 수 있다.
 */

