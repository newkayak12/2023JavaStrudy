package designPattern;

import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * Created on 2023-04-12
 * Project 2023JavaStudy
 */
public class C14_ChainOfResponsibility {
    /**
     * 책임을 떠넘긴다는 말은 부정적인 의미가 강하지만, 정말 '떠넘기기'가 유용한 경우가 있다. 어떤 요청이 있을 때, 그 요청을 처리할 객체를
     * 고정적으로 결정할 수 없는 경우이다. 이때 여러 객체를 사슬처럼 연쇄적으로 묶고, 객체 사슬을 차례대로 돌면서 원하는 객체를 결정하는 방법을
     * 생각할 수 있다.
     *
     * 이러한 패턴을 Chain Of Responsibility라고 한다. 이 패턴을 사용하면 요청하는 쪽과 처리하는 쪽의 결합을 약하게 할 수 있어서
     * 각각 부품으로 독립시킬 수 있다.
     *
     * 예를 들어 어떤 요청이 들어왔다. 만약 처리할 수 있으면 처리하고 없으면 떠넘긴다. 이러한 연속적인 관계를 보이는 패턴이 책임사슬 패턴이다.
     *
     */

    class Trouble {
        private int number;
        public Trouble(int number){ this.number = number; }

        public int getNumber() {
            return number;
        }

        @Override
        public String toString() {
            return "[Trouble : " +number +']';
        }
    }
    abstract class Support {
        private String name;
        private Support next;

        public Support(String name) {
            this.name = name;
            this.next = null;
        }
        public Support setNext(Support next){
            this.next = next;
            return next;
        }

        public void support( Trouble trouble ){
            if( resolve(trouble)) {
                done(trouble);
            } else if (Objects.nonNull(next)){
                next.support(trouble);
            } else {
                fail(trouble);
            }
        }

        @Override
        public String toString() {
            return "[" + name + ']';
        }

        protected abstract boolean resolve(Trouble trouble);
        protected void done(Trouble trouble){
            System.out.println(trouble+" is resolved by "+ this);
        };
        protected void fail(Trouble trouble){
            System.out.println(trouble+" cannot be resolved.");
        }
    }
    public class NoSupport extends Support {
        public NoSupport( String name ) {
            super(name);
        }

        @Override
        protected boolean resolve(Trouble trouble) {
            return false;
        }
    }
    class LimitSupport extends Support {
        private  int limit;

        public LimitSupport (String name, int limit) {
            super(name);
            this.limit = limit;
        }

        @Override
        protected boolean resolve(Trouble trouble) {
            if(trouble.getNumber() < limit) return true;
            return false;
        }
    }
    class OddSupport extends Support {

        public OddSupport(String name) {
            super(name);
        }

        @Override
        protected boolean resolve(Trouble trouble) {
            if(trouble.getNumber() % 2 == 1) return true;
            return false;
        }
    }
    class SpecialSupport extends Support {
        private int number;
        public SpecialSupport(String name, int number){
            super(name);
            this.number = number;
        }

        @Override
        protected boolean resolve(Trouble trouble) {
            if(trouble.getNumber() == number) return true;
            return false;
        }
    }

    @Test
    void chainOfResponsibility() {
        Support alice = new NoSupport("Alice");
        Support bob = new LimitSupport("Bob", 100);
        Support charlie = new SpecialSupport("Charlie", 429);
        Support diana = new LimitSupport("Diana", 200);
        Support elmo = new OddSupport("Elmo");
        Support fred = new LimitSupport("Fred", 300);

           alice.setNext(bob)
                .setNext(charlie)
                .setNext(diana)
                .setNext(elmo)
                .setNext(fred);

           for ( int i = 0; i < 500; i += 33){
               alice.support(new Trouble(i));
           }
    }
    /**
     * Handler : 요구를 처리하는 인터페이스를 정의한다 처리할 '다음 사람'을 준비해두고 스스로 처리할 수 없는 요구면 떠넘긴다.
     * ConcreteHandler: 요구를 구체적으로 처리한다. (위 예시 NoSupport, LimitSupport, OddSupport, SpecialSupport)
     *
     *
     *
     *      요구하는 사람과 요구를 처리하는 사람을 유연하게 연결한다
     * Chain of Responsibility는 요구하는 사람과 처리하는 사람을 느슨하게 연결하는 데 있다.
     * 만약 이 패턴을 사용하지 않으면 '이 요구는 이 사람이 처리해야 한다'는 정보를 누군가 중앙집권적으로 가지고 있어야 한다.
     * 요구자가 그런 정보까지 갖고 있어야한다? 부품으로서의 독립성이 훼손된다.
     *
     *
     *      동적으로 사슬의 형태를 바꾼다.
     * 위 예시는 순서가 고정되어 있다. ConcreteHandler 객체의 관계가 동적으로 변화하는 상황도 생각할 수 있다.
     * Chain of Responsibility 패턴처럼 위임을 통해 처리를 떠넘기면 상황에 따라 재편할 수 있다.
     *
     *
     *      자기 일에 집중 할 수 있다.
     * 떠넘기기를  역전시키면 자기가 할 수 있는 일에 충실하다. 라는 의미이다.
     *
     *
     *      떠넘기기의 부작용?
     * Chain of Responsibility 패턴을 사용해 떠넘기면서 적절한 처리를 할 상대를 찾는 방식은 확실히 유연성이 높을지 모르지만 처리가 지연되지는 않을까?
     * 물론 그렇다. 하지만 이는 무엇을 우선으로하는 트레이드오프 문제이다. 만약 요구 순서, 처리 순서가 고정이라면 Chain of Responsibility를 쓰지 않는 것이 좋을 수 있다.
     *
     */
}
