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
     *
     */
}
