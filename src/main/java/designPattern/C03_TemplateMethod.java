package designPattern;

import org.junit.jupiter.api.Test;

/**
 * Created on 2023-04-06
 * Project 2023JavaStudy
 */
public class C03_TemplateMethod {
    /**
     *  TemplateMethod는 템플릿 기능을 가진 패턴이다. 상위 클래스 쪽에 템플릿이 될 메소드가 정의되어 있고, 그 메소드 정의에 추상 메소드가
     *  사용된다. 따라서 상위 클래스의 코드만 봐서는 최종적으로 어떻게 처리될지 알 수 없다. 상위 클래스로 알 수 있는 것은 추상 메소드를 호출하는
     *  방법뿐이다.
     *
     *  추상 메소드를 실제로 구현하는 것은 하위 클래스이다. 하위 클래스에서 메소드를 구현하면 구체적인 처리 방식이 정해진다. 다른 하위 클래스에서
     *  구현을 다르게 하면, 처리도 달라진다. 그러나, 어느 하위 클래스에서 어떻게 구현되더라도 처리의 큰 흐름은 상위 클래스에서 구성한 대로 된다.
     *
     *  이처럼 상위 클래스에서 처리의 뼈대를 결정하고 하위 클래스에서 그 구체적인 내용을 결정하는 디자인 패턴을 TemplateMethod라고 한다.
     */

    abstract class AbstractDisplay {
        //하위에 구현을 맡기는 추상 메소드
        abstract void open();
        abstract void print();
        abstract void close();

        public void display() { //구현된 메소드(템플릿)
            open();
            for ( int i = 0; i < 5; i++ ) {
                print();
            }
            close();
        };
    }

    class CarDisplay extends AbstractDisplay {
        private char aChar;
        public CarDisplay(char aChar) {
            this.aChar = aChar;
        }

        @Override
        void open() {
            System.out.print("<<");
        }

        @Override
        void print() {
            System.out.print(aChar);
        }

        @Override
        void close() {
            System.out.println(">>");
        }
    }
    class StringDisplay extends AbstractDisplay {
        private String text;
        private int width;

        public StringDisplay(String text){
            this.text = text;
            this.width = text.length();
        }

        @Override
        void open() {
            printLine();
        }

        @Override
        void print() {
            System.out.println("|"+text+"|");
        }

        @Override
        void close() {
            printLine();
        }

        private void printLine() {
            System.out.print("+");
            for ( int i = 0; i < width; i++){
                System.out.print("-");
            }
            System.out.println("+");
        }
    }

    @Test
    void templateMethodTest () {
        AbstractDisplay carDisplay = new CarDisplay('T');
        AbstractDisplay stringDisplay = new StringDisplay("HELLO");

        carDisplay.display();
        stringDisplay.display();
    }
    /**
     * AbstractClass : 템플릿 메소드를 구현하며, 그 템플릿 메소드에서 사용할 추상 메소드를 선언한다.
     * ConcreteClass :  AbstractClass에 정의된 추상 메소드를 구체적으로 구현한다. 여기에서 구현하는 메소드는 Abstract Class의 템플릿
     * 메소드에서 호출된다.
     *
     *
     *      템플릿 메소드를 사용하는 이유는?
     *  TemplateMethod는 상위 클래스의 템플릿 메소드에 알고리즘이 기술되어 있으므로, 하위 클래스에서는 알고리즘을 일일이 기술할 필요가 없어진다.
     *  또한 오류 발생시 TemplateMethod 패턴을 적용했다면 템플릿메소드만 수정하면 모든 부분에서의 오류를 수정할 수 있다.
     *
     *
     *      TemplateMethod, 상위 클래스와 하위 클래스와의 연관성
     *  TemplateMethod 패턴에서는 상위 클래스와 하위 클래스가 긴밀하게 연계되어 있다. 그러므로, 상위 클래스에서 선언된 추상 메소드를
     *  실제로 하위 클래스에서 구현할 때는 그 메소드가 어떤 타이밍에 호출되는지 이해해야만 한다.
     *
     *
     *      하위 클래스, 상위 클래스를 동일시 한다.
     *   하위 클래스를 상위 클래스로 받기 때문에 (AbstractDisplay display = new StringDisplay();) instanceof 등으로 하위 클래스의
     *   종류를 특정하지 않아도 프로그램이 동작하게 만들수 있다. 상위 클래스형 변수에 하위 클래스의 인스턴스 중 어떤 것을 대입해도 동작할 수 있게
     *   하는 원칙을 "The Liskov Substitution Principle(LSP)"라고 한다.
     *
     *
     *       관점의 전환.
     *   우리가 클래스 계층에 대해서 배울 때 주로 하위 클래스의 입장에서만 생각한다.
     *
     *      1. 상위 클래스에서 정의된 메소드를 하위 클래스에서 사용할 수 있다.
     *      2. 하위 클래스에서 약간의 메소드를 기술하는 것만으로 새로운 기능을 추가할 수 있다.
     *      3. 하위 클래스에서 메소드를 오버라이드하면 동작을 변경할 수 있다.
     *
     *    관점을 바꿔서 상위 클래스 입장에서 생각하면
     *
     *      1. 하위 클래스에서 그 메소드를 구현하기를 기다린다.
     *      2. 하위 클래스에 해당 메소드 구현을 강요한다.
     *
     *    이렇게 보면 하위 클래스는 상위 클래스의 추상 메소드를 구현할 책임이 생긴다. 이를 하위 클래스의 책임(subclass Responsibility)라고 한다.
     *
     *
     *
     *         추상 클래스의 의의
     *    추상 클래스는 인스턴스를 만들 수 없다. 추상 메소드에는 메소드 본체가 기술되지 않는다. 즉, 구체화 되어있지도 않다. 그 자체를 구체화 할 수도 없다.
     *    그러나 메소드를 정하고 그 메소드를 이용한 템플릿을 정해 흐름을 정하는 등의 '틀'을 제시할 수 있다. 실제 처리는 하위 클래스가 서브클래싱을
     *    해야만 하지만 추상 클래스 단계에서 처리 흐름을 형성하는 것이 중요하다.
     *
     *
     */
}
