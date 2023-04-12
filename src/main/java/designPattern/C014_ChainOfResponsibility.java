package designPattern;

import java.util.Objects;

/**
 * Created on 2023-04-12
 * Project 2023JavaStudy
 */
public class C014_ChainOfResponsibility {
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
            if(trouble.getNumber() % 2 == 1) return true
            return false;
        }
    }
}
