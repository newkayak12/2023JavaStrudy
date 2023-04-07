package designPattern;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created on 2023-04-07
 * Project 2023JavaStudy
 */
public class C04_FactoryMethod {
    /**
     * Factory는 공장이다. 인스턴스를 생성하는 공장을 TemplateMethod 패턴으로 구성한 것이 FactoryMethod이다.
     *
     * Factory Method 패턴에서 인스턴스 생성 방법을 상위 클래스에서 결정하되, 구체적인 클래스 이름까지는 결정하지 않는다. 구체적은 살은
     * 모두 하위 클래스에서 붙인다. 이로써 인스턴스 생성을 위한 뼈대와 실제 인스턴스를 생성하는 클래스를 나눠서 생각할 수 있게 된다.
     *
     */

    // framework;
    public abstract class Product {
        public abstract void use();
    }
    public abstract class Factory {
        public final Product create( String owner ) {
            Product product = createProduct(owner);
            registerProduct(product);
            return product;
        }

        protected abstract Product createProduct( String owner );
        protected abstract void registerProduct( Product product );
    }


    // usage;
    public class IDCard extends Product {
        private String owner;

        IDCard( String owner ) {
            System.out.printf("Create %s's Card\n", owner);

            this.owner = owner;
        }

        @Override
        public void use() {
            System.out.printf("use %s\n", this);
        }

        @Override
        public String toString() {
            return "IDCard[" +owner +"]";
        }

        public String getOwner() {
            return owner;
        }
    }
    public class IDCardFactory extends Factory {

        @Override
        protected Product createProduct(String owner) {
            return new IDCard(owner);
        }

        @Override
        protected void registerProduct(Product product) {
            System.out.println("Registered");
        }
    }


    @Test
    void factoryMethodTest () {
        Factory factory = new IDCardFactory();
        Product card1 = factory.create("YOUNJIN KIM");
        Product card2 = factory.create("HEUNGMIN SON");
        Product card3 = factory.create("KANGIN LEE");

        card1.use();
        card2.use();
        card3.use();
    }
    /**
     * Product : 프레임워크 쪽이다. 이 패턴으로 생성되는 인스턴스가 가져야할 인터페이스를 결정하는 추상클래스이다. 구체적인 서브클래싱은 ConcreteProduct에서 결정한다.
     * Creator : Product를 생성하는 추상클래스이다. 구체적인 내용은 ConcreteCreator가 결정한다. Creator는 실제로 생성할 ConcreteProduct에 대해서 아는 바가 없다.
     *          Creator가 아는 것은 Product와 인스턴스 생성 메소드를 호출하면 Product가 생성된다 정도이다. 결과적으로 new를 사용해서 실제 인스턴스를 만드는 모양새가 아닌
     *          인스턴스를 생성하는 메소드를 호출함으로써 구체적인 클래스 이름에 의한 속박에서 상위 클래스를 자유롭게 한다.
     *
     * ConcreteProduct : 구체적으로 살을 붙이는 역할이다.
     * ConcreteCreator : 역시 구체적으로 살을 붙이는 쪽이다.
     *
     *
     *
     *          프레임워크와 구체적인 사용
     *  위의 예시에서 (여기서는 한 파일에서 진행했지만, )  framework 패키지에는 만들고자하는 대상을 import 하지 않았다. 즉, 만들고자 하는 구체적인 대상이
     *  무엇인지 알필요가 없다는 뜻이다. 이런 경우 framework 패키지는 '만드는 대상에 의존하지 않는다'고 표현한다.
     *
     *
     *          인스턴스 생성 - 메소드 구현
     *  위 예시에서 createProduct 메소드를 기술하는 방법은 두가지로 볼 수 있다.
     *
     *      1. 추상 메소드로 기술
     *  추상 메소드로 기술하면, 하위 클래스에서는 반드시 이 메소드를 구현해야만 한다. 구현되지 않으면 컴파일시 검출된다.
     *
     *         abstract class Factory {
     *             public abstract Product createProduct( String name );
     *         }
     *
     *      2. 디폴트 구현을 준비해두기
     *  디폴트 구현을 준비해두면 하위 클래스에서 구현하지 않은 경우에 디폴트 구현이 사용된다.
     *
     *       class Factory {
     *           public Product createProduct( String name ) {
     *               return new Product( name );
     *           }
     *       }
     *
     *
     *
     *          패턴 이용과 의사소통
     *  TemplateMethod도, FactoryMethod도 실제로 하는 일에 비해서는 복잡해 보인다. 그 이유는 하나의 클래스만 읽어서는 정확하게 어떻게
     *  동작하는지 가늠하기 어렵기 때문이다. 상위 클래스에서 동작의 뼈대를 알고, 거기에서 사용되는 추상 메소드가 무엇인지 확인하고,
     *  다시 그 추상 메소드를 구현한 클래스를 찾아봐야한다.
     *
     *  일반적으로 디자인 패턴을 사용해서 어떤 클래스 군을 설계할 경우, 그 클래스 군을 보수하는 사람에게 설계자가 의도한 디자인 패턴이 무엇인지
     *  잘 전달할 필요가 있다. 그렇지 않으면 설계자의 처음 의도에서 벗어난 수정이 가해질 수 있기 때문이다.
     *
     *
     *          static FactoryMethod
     *  인스턴스 생성을 위한 클래스 메소드(static 메소드) 전반을 FactoryMethod라고 부르는 경우가 있다. 이는 GoF의 FactoryMethod과는
     *  다르지만, Java에서 인스턴스를 생성할 때 매우 자주 사용되는 기법이다. Java Api레퍼런스에서도 인스턴스 생성을 위한 클래스 메소드를
     *  static Factory Method로 표현하기도 한다.
     *
     *  static Factory Method로는 create, newInstance, getInstace 등의 이름이 자주 사용되지만, 그 밖의 이름이 사용될 때도 있다.
     *
     *          > SecureRandom random = SecureRandom.getInstance("NativePRNG")
     *          > List.of(1,2,3,4);
     *          > Arrays.asList("alice", "bolob",
     *          > String.valueOf();
     *          > Instant.now()
     *
     *
     *
     */


}
