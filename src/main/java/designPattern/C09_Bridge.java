package designPattern;

import org.junit.jupiter.api.Test;

/**
 * Created on 2023-04-10
 * Project 2023JavaStudy
 */
public class C09_Bridge {
    /**
     * 구현부에서 추상층을 분리하여 각자 독립적으로 변형이 가능하고 확장이 가능하도록 합니다. 즉 기능과 구현에 대해서 두 개를 별도의 클래스로 구현을 합니다.
     *
     * 이걸로 알고 있는데...
     */


    /**
     * Bridge 패넡이 다리 역할을 하는 장소는 '기능의 클래스 계층'과 '구현의 클래스 계층'이다.
     *
     *  > 기능을 추가하고 싶다면
     *  어떤 클래스 Something이 있다고 해보자. Something에 새로운 기능을 추가하고 싶을 때(구체적으로는 새로운 메소드를 추가하고 싶을 때),
     *  우리는 Something의 하위 클래스(자식, 파생, 확장 클래스로) somethingGood 클래스를 만든다.
     *
     *          Something
     *             ㄴ SomethingGood
     *
     *  이 계층은 기능을 추가하기 위해서 만들어졌다.
     *      - 상위 클래스는 기본적인 기능을 가지고 있다.
     *      - 하위 클래스에서 새로운 기능을 추가한다.
     *  이 클래스의 계층을 '기능의 클래스 계층'이라고 부르자.
     *
     *  이제, SomethingGood 클래스에 새로운 기능을 추가한다고 해보자. 이 경우 SomethingGood 클래스의 하위 클래스로 SomethingBetter를 만든다.
     *  이로써 기능의 클래스 계층이 한층 더 깊어졌다.
     *
     *
     *          Something
     *             ㄴ SomethingGood
     *                  ㄴSomethingBetter
     *
     *  새로운 기능을 추가하고 싶을 때, 클래스 계층 안에서 자신의 목적과 가까운 클래스를 찾아 그 하위 클래스를 만들고, 원하는 기능을 추가한 새로운 클래스를
     *  만든다. 이것이 클래스 계층이다.
     *
     *
     *    > 새로운 '구현'을 추가하고 싶다면
     *
     *  Template Method 패턴에서 우리는 추상 클래스의 역할에 대해서 배웠다. 추상 클래스는 일련의 메소드를 추상 메소드로 선언하고 인터페이스를 규정한다.
     *  그리고 하위 클래스 쪽에서 그 추상 메소드를 실제로 구현한다. 상위 클래스는 추상 메소드로 인터페이스를 규정하는 역할을 하고, 하위 클래스는
     *  추상 메소드를 구현하는 역할을 한다. 이러한 상위 클래스와 하위 클래스의 역할 분담을 통해 부품으로서 가치가 높은 클래스를 만들 수 있다.
     *
     *  여기에도 클래스 계층이 등장한다. 예를 들어 상위 클래스 AbstractClass의 추상 메소드를 구현한 하위 클래스를 ConcreteClass라고 하면 아래와 같은
     *  작은 클래스 계층이 만들어진다.
     *
     *            AbstractClass
     *              ㄴ ConcreteClass
     *  하지만 여기에서 사용되는 클래스 계층은 기능을 추가하기 위해 사용되는 것은 아니며, 새로우 ㄴ메소드를 늘리기 위해서 클래스 계층을 만든 것도
     *  아니다. 여기서는 아래와 같은 역할 분담을 위해서 클래스 계층이 사용된다.
     *
     *      - 상위 클래스는 추상 메소드로 인터페이스를 규정한다.
     *      - 하위 클래스는 구상 메소드로 그 인터페이스를 구현한다.
     *
     *  이 클래스 계층을 '구현의 클래스 계층'으로 부르자.
     *
     *  여기서 AbstarctClass의 다른 구현을 만들려고 한다. 이때 하위 클래스 AnotherConcreteClass라고 하면 구현의 클래스 계층은 또 변화한다.
     *
     *          AbstractClass
     *              ㄴ ConcreteClass
     *              ㄴ AnotherConcreteClass
     *
     *  새로운 구현을 만들기 위해서는 AbstractClass의 하위 클래스를 만들고 추상 메소드를 구현하게 된다.
     *
     *  > 클래스 계층의 혼재와 클래스 계층의 분리
     *  우리가 하위 클래스를 만들고자 할 때는 자신의 의도를 알아야한다 "나는 기능을 추가하려고 하는가? 아니면 구현하려고 하는가?"
     *  클래스 계층이 하나면 기능의 클래스 계층과 구현의 클래스 계층이 하나의 계층 구조 안에 혼재하게 된다. 이런 상태는 클래스 계층을 복잡하게
     *  만들어 예측을 어렵게 할 우려가 있다. 하위 클래스로 만들고자 할 때 클래스 계층 어디에 만들면 좋을지 망설여지기 때문이다.
     *
     *  그래서 '기능의 클래스 계층', '구현의 클래스 계층'을 두 개의 독립된 클래스 계층으로 나눈다. 단순하게 분리만 하면 흩어지기 때문에
     *  서로 간의 다리를 놓을 필요가 있다.
     */

     // * 기능의 클래스 계층 : Diplay -> 무엇인가를 표시하는 것, 기능의 클래스 계층 최상위에 있는 클래스이다.
    public class Display {
        private DisplayImpl impl; //구현을 나타내는 인스턴스

        public Display(DisplayImpl impl){ this.impl = impl; }

        public void open () {
            impl.rawOpen();
        }
        public void print() {
            impl.rawPrint();
        }
        public void close() {
            impl.rawClose();
        }

        public final void display() {
            open();
            print();
            close();
        }
    }

    // * 기능의 클래스 계층: CountDisplay -> Display 클래스에 기능을 추가한 것이 CountDisplay클래스이다.
    public class CountDisplay extends Display {
        public CountDisplay(DisplayImpl impl){
            super(impl);
        }

        public void multiDisplay(int times){
            open();
            for( int i = 0; i < times; i++ ){
                print();
            }
            close();
        }
    }


    // 구현의 클래스 계층
    public abstract class DisplayImpl {
        abstract void rawOpen();
        abstract void rawPrint();
        abstract void rawClose();
    }

    //구현의 클래스 계층 StringDisplayImpl -> 문자열을 표시하는 클래스다. 다만 그냥 표시하는 것이 아닌 DisplayImpl의 하위 클래스이다.
    public class StringDisplayImpl extends DisplayImpl {
        private String text;
        private int width;

        public StringDisplayImpl( String text ){
            this.text = text;
            this.width = this.text.length();
        }

        @Override
        void rawOpen() {
            printLine();
        }

        @Override
        void rawPrint() {
            System.out.println("|"+text+"|");
        }

        @Override
        void rawClose() {
            printLine();
        }

        private void printLine() {
            System.out.print("+");
            for ( int i = 0; i < width; i++ ) {
                System.out.print("-");
            }
            System.out.println("+");
        }
    }


    @Test
    void bridgeTest() {
        Display d1 = new Display(new StringDisplayImpl("Hello Java"));
        Display d2 = new CountDisplay(new StringDisplayImpl("Hello Swift"));
        CountDisplay d3 = new CountDisplay(new StringDisplayImpl("HELLO Python"));

        d1.display();
        d2.display();
        d3.display();
        d3.multiDisplay(5);
    }

    /**
     * Abstraction : 기능의 클래스 계층의 최상위 클래스 (위 예시에서 Display)
     * RefinedAbstraction : Abstract에 기능을 추가, (위 예시에서 CountDisplay)
     * ConcreteImplementor: 구체적으로 인터페이스를 구현
     *
     *
     *
     *          분리하면 확장에 용이
     * Bridge는 '기능의 클래스', '구현의 클래스'로 분리되어 있다. 이 두 개의 클래스를 분리하면 각각의 클래스 계층을 독립적으로 확장할 수 있다.
     *
     * 기능을 추가하고 싶으면 기능의 클래스 계층에 추가한다. 이때 구현의 클래스 계층은 전혀 수정할 필요가 없다. 계다가 새로 추가한 기능은
     * '모든 구현'에서 이용할 수 있다.
     *
     *
     *          상속은 강한 결합, 위임은 약할 결합
     *  상속은 클래스를 확장하는 간편한 방법이지만 클래스 간 연결성을 강화한다. 이러한 관계는 소스를 다시 작성하지 않는 한 바꿀 수 없다.
     */
}
