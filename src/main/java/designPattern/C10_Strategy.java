package designPattern;

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



}
