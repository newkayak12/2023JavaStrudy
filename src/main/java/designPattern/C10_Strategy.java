package designPattern;

import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * Created on 2023-04-10
 * Project 2023JavaStudy
 */
public class C10_Strategy {
    /**
     * 모든 프로그램은 문제를 해결하고자 만들어지며, 문제를 풀기 위한 특정 알고리즘으로 구현된다. Strategy 패턴에서는 구현한 알고리즘을 모조리
     * 교환할 수 있다. 스위치를 전환하듯 알고리즘을 바꿔서 같은 문제를 다른 방법으로 해결하기 쉽게 만들어주는 패턴이 Strategy이다.\
     *
     *
     */

    enum Hand {
        ROCK("바위", 0),
        SCISSORS("가위", 1),
        PAPER("보", 2);

        private String name;
        private int handValue;


        Hand(String name, int handValue) {
            this.name = name;
            this.handValue = handValue;
        }

        static Hand getHand(int handValue) {
            return Hand.values()[handValue];
        }
        public boolean isStrongerThan( Hand hand ){
            return fight(hand) == 1;
        }

        public boolean isWeakerThan( Hand hand ){
            return fight(hand) == -1;
        }

        private int fight( Hand hand ){
            if ( this == hand ) return 0;
            else if ( ((this.handValue + 1) % 3) == hand.handValue ) {
                return 1;
            } else {
                return -1;
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }
    interface Strategy {
        Hand nextHand();
        void study(boolean win);
    }

    /**
     * WinningStrategyClass
     * strategy 인터페이스를 구현하는 클래스 중 하나이다. Strategy를 구현한다는 것은 nextHand, study를 구현하는 것이다.
     */
    class WinningStrategy implements Strategy {
        private Random random;
        private boolean won = false;
        private Hand prevHand;

        public WinningStrategy( int seed ){
            random = new Random(seed);
        }

        @Override
        public Hand nextHand() {
            if(!won) {
                prevHand = Hand.getHand(random.nextInt(3));
            }
            return prevHand;
        }

        @Override
        public void study(boolean win) {
            won = win;
        }
    }
    /**
     * ProbStrategy 클래스
     * 다음 손을 항상 난수로 결정하는데, 과거의 기기고 진 이력을 활용해서 각가의 손을 낼 확률을 바꾸는 것이다.
     * history 필드는 과거의 승패를 반영한 확률 계산을 위한 표로 되어 있다.
     *
     *  history [직전에 낸 손][이번에 낼 손]
     *
     *  eg)
     *      history[0][0] :  바위, 바위를 내가 냈을 때 과거의 승수
     *      history[0][1] :  바위, 가위를 내가 냈을 때 과거의 승수
     *      history[0][2] :  바위, 보를 내가 냈을 때 과거의 승수
     *
     *  이 승수를 모두 더하고 승수의 비율과 난수의 값을 기준으로 다음에 낼 것을 결정한다.
     */
    public class ProbStrategy implements Strategy {
        private Random random;
        private int prevHandValue = 0;
        private int currentHandValue = 0;
        private int[][] history = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };

        public ProbStrategy(int seed) {
            random = new Random(seed);
        }

        @Override
        public Hand nextHand() {
            int bet = random.nextInt(getSum(currentHandValue));
            int handValue = 0;

            if ( bet < history[currentHandValue][0] ){
                handValue = 0;
            } else if ( bet < history[currentHandValue][0] + history[currentHandValue][1] ){
                handValue = 1;
            } else {
                handValue = 2;
            }

            prevHandValue = currentHandValue;
            currentHandValue = handValue;
            return Hand.getHand(handValue);
        }

        private int getSum( int handvalue ) {
            int sum = 0;
            for ( int i = 0; i < 3; i++ ) {
                sum += history[handvalue][i];
            }
            return sum;
        }

        @Override
        public void study(boolean win) {
            if ( win ){
                history[prevHandValue][currentHandValue] ++;
            } else {
                history[prevHandValue][(currentHandValue + 1) % 3]++;
                history[prevHandValue][(currentHandValue + 2) % 3]++;
            }
        }
    }

    public class RandomStrategy implements Strategy {
        private Random random;
        public RandomStrategy (int seed){
            random = new Random(seed);
        }
        @Override
        public Hand nextHand() {
            return Hand.getHand(random.nextInt(3));
        }

        @Override
        public void study(boolean win) {

        }
    }


    /**
     * Player 클래스는 이름과 전략으로 인스턴스를 만든다.
     * nextHand는 다음 손을 얻는 메소드인데, 실제로 다음 손을 결정하는 것은 '전략'이다.
     * nextHand 메소드의 반환 값이 그대로 Player의 nextHand 메소드의 반환 값이 된다. nextHand는 Strategy에 전략을 맡긴다.
     * 즉, 위임하고 있다.
     */
    public class Player {
        private String name;
        private Strategy strategy;
        private int winCount;
        private int loseCount;
        private int gameCount;

        public Player(String name, Strategy strategy) {
            this.name = name;
            this.strategy = strategy;
        }

        public Hand nextHand() {
            return strategy.nextHand();
        }

        public void win() {
            strategy.study(true);
            winCount++;
            gameCount++;
        }
        public void lose() {
            strategy.study(false);
            loseCount++;
            gameCount++;
        }
        public void even(){
            gameCount++;
        }

        @Override
        public String toString() {
            return "["+name+": "+gameCount+" games, "+winCount+" win, "+loseCount+" lose, ]";
        }
    }


    @Test
    void strategyTest(){
        Player player1 = new Player("KIM", new WinningStrategy(315));
        Player player2 = new Player("LEE", new RandomStrategy(12));

        for ( int i = 0; i < 10000; i++ ){
            Hand nextHand1 = player1.nextHand();
            Hand nextHand2 = player2.nextHand();

            if( nextHand1.isStrongerThan(nextHand2)) {
                System.out.println("WINNER :: "+player1);
                player1.win();
                player2.lose();
            } else if (nextHand2.isStrongerThan(nextHand1)) {
                System.out.println("WINNER :: "+player2);
                player1.lose();
                player2.win();
            } else {
                System.out.println("DRAW!");
                player1.even();
                player2.even();
            }
        }

        System.out.println("TOTAL RESULT");
        System.out.println(player1);
        System.out.println(player2);
    }

    /**
     * Strategy : 전략을 이용하기 위한 인터페이스를 결정한다.
     * ConcreteStrategy: Strategy를 구현한다. 여기에서 구체적인 전략(작전, 방안, 방법, 알고리즘)을 실제로 프로그래밍한다.
     * Context : Strategy를 이용한다. ConcreteStrategy의 인스턴스를 가지고 있다가 필요에 따라 이용한다.
     *
     *
     *
     *      Strategy를? 굳이?
     * 보통 메소드 안에 알고리즘을 구현해 버리기 쉽지만, Strategy는 의도적으로 알고리즘을 분리한다. 이러한 작업은
     * 알고리즘을 개량해서 더 빠르게 만들고 싶을 때 Strategy를 구현한 ConcreteStrategy를 수정하면 된다.
     * 게다 위임이라는 약한 결합을 사용하므로 알고리즘을 용이하게 전환할 수도 있다.
     *
     *
     *      실행 중 교체?
     * 실행 중 필요에 따라 교체할 수도 있다. 혹은 같은 결과를 내는 두 알고리즘을 사용해서 상호 검산도 가능하다.
     *
     *
     *      상속의 동일시와 위임의 동일시?
     * 상속: 하위 클래스는 상위클래스로 동일시 할 수 있다.
     * 위임: 자신과 위임할 곳을 동일시, 위임을 사용해서 인터페이스가 투과적으로 되어있을 때는 자신과 위임할 곳을 동일시 할 수 있다.
     */
}
