package designPattern;

import org.junit.jupiter.api.Test;

/**
 * Created on 2023-04-06
 * Project 2023JavaStudy
 */
public class C02_Adapter {
    /**
     *  필요한 형태로 변환한 후 이용하는 경우가 자주 있다.
     *  "이미 제공된 것" 과 "필요한 것"의 차이를 매우는 디자인 패턴이 Adapter이다.
     *
     *  Adapter 패턴은 Wrapper 패턴이라고 불리기도 한다. 래퍼는 '감싸는 것'을 의미한다.
     *  무엇인가를 포장해서 다른 용도로 사용할 수 있도록 변환해 주는 것이 Wrapper이자 Adapter이다.
     *
     *
     *  Adapter에는 두 가지 종류가 있다.
     *
     *  1. 클래스에 의한 Adapter 패턴(상속)
     *  2. 인스턴스에 의한 Adapter 패턴(위임)
     *
     */

    /** Case 1: Use Inheritance */
    class Banner {
        private String text;
        public Banner(String text) {
            this.text = text;
        }

        public void showWithParenthesis () {
            System.out.printf("(%s)", text);
        }
        public void showWithAsterisk () {
            System.out.printf("*%s*", text);
        }
    }


    interface Print1 {
        void printWeak();
        void printStrong();
    }
    class PrintBanner extends Banner implements Print1 { //Adapter
        public PrintBanner(String text) {
            super(text);
        }

        @Override
        public void printWeak() {
            showWithParenthesis();
        }

        @Override
        public void printStrong() {
            showWithAsterisk();
        }

    }

    @Test
    void classAdapterPatternTest(){
        PrintBanner p = new PrintBanner("HELLO!");
        p.printStrong();
        p.printWeak();
    }

    /** Case 2: Use Instance */
    //주의점 -> Print는 인터페이스가 아니라 클래스
    //즉, Banner클래스를 이용해서 Print 클래스와 같은 메소드를 갖는 클래스를 실형하려는 것이다.

    public abstract class Print2 {
        public abstract void printWeak();
        public abstract void printStrong();
    }
    public class PrintBanner2 extends Print2 {
        private Banner banner;

        public PrintBanner2( String text ){
            this.banner = new Banner(text);
        }

        @Override
        public void printWeak() {
            banner.showWithParenthesis();
        }

        @Override
        public void printStrong() {
            banner.showWithAsterisk();
        }
    }

    @Test
    void instanceAdapterPatternTest() {
        Print2 print2 = new PrintBanner2("HELLO!");
        print2.printStrong();
        print2.printWeak();
    }

    /**
     *
     * Target : 필요한 메소드를 결정한다. (위의 예시에서는 Print1 인터페이스, Print2 추상 클래스)
     * Client : Target의 메소드를 사용 (Main class)
     * Adaptee: 적응 대상자이다. (위의 예시에서는 Banner 이다.)
     * Adapter: 적응을 도와는 역할이다. Adaptee를 건들지 않으면서 Target의 조건을 만족시켜주는 역할을 한다. (위의 예시에서 PrintBanner1은 상속, PrintBanner2는 위임)
     *
     *
     *
     *      언제 사용할까?
     * 이미 존재하는 레거시 코드를 건들지 않으면서 확장할 때 사용한다.
     * 핵심적인 아이디어는 레거시 코드를 수정하지 않으면서 확장하고 싶을 때이다. 혹은 레거시 코드와 호환성에 문제가 될 경우이다.
     * Adapter는 신, 구를 공존시키고 유지보수에 편의성을 높여준다.(레거시 코드가 충분히 검증됐다고 할 때 문제가 생긴다면 Adapter에서 생겼음을 확정할 수 있다.)
     * 물론 Target, Adaptee가 너무 결이 다르면 사용하기 어렵다.
     *
     *      상속 vs. 위임
     * 상속보다는 위임하는 편이 문제가 적다. 그 이ㅠㅇ는 사우이 클래스의 내부 동작으로 모르면 상속을 효과적으로 사용하기 어렵기 때문이다.
     *
     *
     */



}
