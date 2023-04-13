package designPattern;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class C17_Observer {
    /**
     * Observer란 관찰자이다. Observer에서는 관찰 대상의 상태가 변화하면 관찰자에게 알린다.
     *
     *
     *  (!) Java.util.Observer와는 다른 인터페이스를 만들어볼 것이다.
     */

    public interface Observer {
        public void update(NumberGenerator generator);
    }
    public abstract class NumberGenerator {
        private List<Observer> observers = new ArrayList<>();

        public void addObserver(Observer observer){
            observers.add(observer);
        }
        public void deleteObserver(Observer observer){
            observers.remove(observer);
        }

        public void notifyObservers() {
            for(Observer observer : observers) {
                observer.update(this);
            }
        }


        public abstract int getNumber();
        public abstract void execute();
    }

    public class RandomNumberGenerator extends NumberGenerator {
        private Random random = new Random();
        private int number;

        @Override
        public int getNumber() {
            return number;
        }

        @Override
        public void execute() {
            for ( int i = 0; i < 20; i++ ){
                number = random.nextInt(50);
                notifyObservers();
            }
        }
    }
    public class DigitObserver implements Observer {

        @Override
        public void update(NumberGenerator generator) {
            System.out.println("DigitObserver: " + generator.getNumber());
            try {
                Thread.sleep(100);
            } catch ( InterruptedException e ){

            }
        }
    }
    public class GraphObserver implements Observer {

        @Override
        public void update(NumberGenerator generator) {
            System.out.println("GraphObserver:");
            int count = generator.getNumber();
            for ( int i = 0; i < count; i++ ) {
                System.out.print("*");
            }
            System.out.println("");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    void ObserverTest() {
        NumberGenerator generator = new RandomNumberGenerator();
        Observer observer1 = new DigitObserver();
        Observer observer2 = new GraphObserver();

        generator.addObserver(observer1);
        generator.addObserver(observer2);
        generator.execute();
    }
    /**
     * Subject : 관찰 대상역
     * ConcreteSubject : 구체적 관찰 대상
     * Observer : Subject로부터 상태 변화를 전달받음
     * ConcreteObserver : 구체적 Observer
     */
}
