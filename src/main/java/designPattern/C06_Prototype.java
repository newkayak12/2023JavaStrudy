package designPattern;

import org.junit.jupiter.api.Test;

import javax.lang.model.type.PrimitiveType;
import java.rmi.MarshalException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2023-04-07
 * Project 2023JavaStudy
 */
public class C06_Prototype {
    /**
     *  어떤 클래스의 인스턴스를 만들고자 할 때 우리는 new 라는 Java의 키워드를 사용해서 클래스 이름을 지정하고 인스턴스를 생성한다.
     *
     *                          new Something();
     *
     *  이처럼 new를 사용해서 인스턴스를 만들 때는 클래스 이름을 반드시 지정해야만 한다. 그러나 클래스 이름을 지정하지 않고 인스턴스를 사용하고 싶을 때도
     *  있을 것이다. 다음과 같은 경우에는 클래스로부터 인스턴스를 만드는 대신 인스턴스를 복사해서 새 인스턴스를 만든다.
     *
     *
     *  1) 종류가 너무 많아 클래스로 정리할 수 없는 경우
     *  첫 번째는 취급할 오브젝트 종류가 너무 많아서, 하나하나 다른 클래스로 만들면 소스 파일을 많이 작성해야 하는 경우이다.
     *
     *  2) 클래스로부터 인스턴스 생성이 어려운 경우
     *  두 번째는 생성하고 싶은 인스턴스가 복잡한 과정을 거쳐 만들어지는 것으로, 크랠스로부터 만들기가 매우 어려운 경우이다. 예를 들어, 그래픽
     *  에디터 등으로 사용자가 마우스로 그린 도형을 나타내는 인스턴스가 있다고 해보자. 이렇게 사용자 조작으로 만들어지는 인스턴스는 프로그래밍해서 만들기
     *  어렵다. 사용자 조작으로 만들어진 인스턴스와 같은 것을 다시 만들고 싶다면 지금 만든 인스턴스를 일단 저장해두고 만들고 싶을 때 복사한다.
     *
     *  3) 프레임워크와 생성하는 인스턴스를 분리하고 싶을 경우
     *  세 번째는 인스턴스를 생성하는 프레임워크를 특정 클래스에 의존하지 않게 하고 싶은 경우이다. 이러한 겨웅에는 클래스 이름을 지정해서 인스턴스를
     *  만드는 것이 아니라, 미리 원형이 될 인스턴스를 등록하고, 등록된 인스턴스를 복사해서 인스턴스를 생성한다.
     *
     *  인스턴스로부터 다른 인스턴스를 생성하는 것은 복사기로 문서를 복사하는 것과 유사하다. 원본서류를 어떻게 만들었는지 모르더라도 복사기에 넣으면
     *  같은 서류를 몇 장이든 만들 수 있다.
     */

    interface Product extends Cloneable {
        void use( String text );
        Product createCopy();
    }
    class Manager {
        //Product로 타입을 선언해서 결합도 낮춤
        private Map<String, Product> showcase = new HashMap<>();
        public void register( String name, Product prototype ) {
            showcase.put(name, prototype);
        }
        public Product create( String prototypeName ) {
            Product p = showcase.get(prototypeName);
            return p.createCopy();
        }
    }
    public class MessageBox implements Product {
        private char decorate;

        public MessageBox(char decorate) {
            this.decorate = decorate;
        }


        @Override
        public void use( String text ) {
            int decolen = 1 + text.length() + 1;
            for ( int i = 0; i < decolen; i++ ) {
                System.out.print( (char) decorate );
            }
            System.out.println();
            System.out.println( (char) decorate + text +  (char) decorate );
            for ( int i = 0; i < decolen; i++ ) {
                System.out.print( (char) decorate );
            }
            System.out.println();
        }

        @Override
        public Product createCopy() {
            Product clonee = null;
            try {
                clonee = (Product) clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return clonee;
        }
    }
    public class UnderLinePen implements Product {
        private char decorate;

        public UnderLinePen(char decorate) {
            this.decorate = decorate;
        }


        @Override
        public void use( String text ) {
            int decolen = 1 + text.length() + 1;
            System.out.println(text);
            for ( int i = 0; i < decolen; i++ ) {
                System.out.print( (char) decorate );
            }
            System.out.println();
        }

        @Override
        public Product createCopy() {
            Product clonee = null;
            try {
                clonee = (Product) clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return clonee;
        }
    }

    @Test
    void prototypeTest () {
        Manager manager = new Manager();
        UnderLinePen underLinePen = new UnderLinePen( '-' );
        MessageBox asteriskBox = new MessageBox( '*' );
        MessageBox slashBox = new MessageBox( '/' );

        manager.register("StrongMessage", underLinePen);
        manager.register("warningBox", asteriskBox);
        manager.register("slashBox", slashBox);

        Product product1 = manager.create("StrongMessage");
        product1.use("HELLO, WORLD");

        Product product2 = manager.create("warningBox");
        product2.use("HELLO, WORLD");

        Product product3 = manager.create("slashBox");
        product3.use("HELLO, WORLD");
    }

    /**
     * prototype : 인스턴스를 복사하여 새로운 인스턴스를 만들기 위해서 메소드를 결정한다. (위 예시에서는 Product 인터페이스)
     * concretePrototype: 인스턴스를 복사해서 새로운 인스턴스를 만드는 메소드를 구현 (위 예시에서는 MessageBox, UnserLinePen)
     * client : 인스턴스를 복사하는 메소드를 이용해서 새로운 인스턴스를 만듦 (위 예시에서는 Manager)
     *
     *
     *
     *          왜 클래스에서 인스턴스를 만들면 안되는가?
     *  인스턴스를 만들면 new SomeClass(); 하면 된다. 왜 굳이 프로토타입 패턴이 필요한가?
     *
     *  1) 종류가 너무 많아 클래스로 정리하기 어려운 경우
     *
     *      - '-'를 사용해서 문자열에 밑줄 긋기
     *      - '*'를 사용해서 문자열에 테두리 그리기
     *      - '/'를 사용해서 문자열에 테두리 그리기
     *
     *  위와 같은 세 개의 원형이 있다. 만약 이런 원형들을 모두 개별 클래스로 만들어버리면? 소스를 관리하기 어렵다.
     *
     *  2) 클래스로부터 인스턴스 생성이 어려운 경우
     *  3) 프레임워크가 생성하는 인스턴스를 분리하고 싶은 경우
     *  예제는 인스턴스를 복제하는 부분이 framework 패키지(interface) 안에 두고 있다. Manager는 create 메소드에 클래스 이름 대신 문자열로
     *  인스턴스 생성을 위한 이름을 부여한다. new ~();라는 형식에 대한 속박으로부터 프레임워크를 분리했다고 할 수 있다.
     *
     *
     *          클래스 이름?
     *  클래스 이름이 소스 코드 안에 있으면 해당 클래스와의 결합도가 생긴다. 여기서 속박이라고 한 표현은 소스파일 없이도 class 파일일 때도 재사용이 가능하냐는 관점에서
     *  바라본 것에 대한 결과이다.
     *
     *
     */
}
