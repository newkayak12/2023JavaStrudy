package designPattern;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.Objects;

public class C21_Proxy {
    /**
     * Proxy란 대리인이라는 의미이다. 객체지향에서는 본인도, 대리인도 오브젝트가 된다. 바빠서 그 일을 할 수 없는 본인 객체르 대신해서 대리인 객체가
     * 일을 처리하게 된다. 혹은 본인이 나설 때가 되기 전까지 대리인을 내세우다. 정말 본인이 필요할 때 인스턴스를 생성한다.
     */


    interface Printable {
        public void setPrinterName(String name);
        public String getPrinterName();
        public void print(String text);
    }

    class Printer implements Printable {
        private String name;

        public Printer() {
            heavyJob("Printer initialized");
        }

        public Printer(String name) {
            this.name = name;
            heavyJob("Printer instance ("+name+") initializing");
        }

        private void heavyJob(String message) {
            System.out.println(message);
            for ( int i = 0; i < 5; i++ ){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public void setPrinterName(String name) {
            this.name = name;
        }

        @Override
        public String getPrinterName() {
            return name;
        }

        @Override
        public void print(String text) {
            System.out.println("------" + name + "------");
            System.out.println(text);
        }
    }

    /**
     * 아래 프록시는 Printable을 구현한다. 내부에  real을 알고 있다. real 이 있으면 본인에 대해서도 그 이름을 설정한다. 그러나 real이 없다면(null)
     * 그냥 자신의 name 필드에 이름을 설정한다.
     *
     * print는 proxy가 처리할 수 없으므로 realize를 호출해서 본체에 위임한다. 이렇게 정말로 본체가 필요하지 않다면 대리인이 처리하고 만다.
     * Printer 클래스는 PrinterProxy의 존재를 모른다.
     */
    class PrinterProxy implements Printable {
        private String name;
        private Printer real;

        public PrinterProxy() {
            this.name = "NO NAME";
            this.real = null;
        }

        public PrinterProxy(String name) {
            this.name = name;
            this.real = null;
        }

        @Override
        public synchronized void setPrinterName(String name) {
            if(Objects.nonNull(real) ) {
                real.setPrinterName(name);
            }
            this.name = name;
        }

        @Override
        public String getPrinterName() {
            return name;
        }

        @Override
        public void print(String text) {
            realize();
            real.print(text);
        }

        private synchronized void realize() {
            if ( Objects.isNull(real) ) real = new Printer(name);
        }
    }

    @Test
    void proxyTest() {
        Printable printable = new PrinterProxy("Alice");
        System.out.println("이름은 "+ printable.getPrinterName() + "이다.");

        printable.setPrinterName("BOB");
        System.out.println("이름은 "+ printable.getPrinterName() + "이다.");
        printable.print("HELLO, WORLD");
    }
    /**
     * Subject: Proxy와 RealSubject를 동일시 하기 위한 인터페이스를 정의한다. Subject 덕분에 Client는 Proxy, RealSubject 간의 차이를 생각할
     * 필요가 없다.
     *
     * Proxy: Client의 요청을 최대한 처리한다. 만약 혼자 처리할 수 없으면 RealSubject에 처리를 위임한다.
     *
     * RealSubject : 대리인이 감당하지 못하는 일을 처리하는 본체다.
     *
     *
     *
     *
     *          Proxy로 속도 올리기
     *  Proxy는 대리로 가능한 일을 최대로 처리한다. 만약 초기화 시간이 오래 걸리는 대규모 시스템을 생각해보자. 시작 시점에 이용하지 않는 기능까지
     *  모두 초기화 해야한다면 애플리케이션 시작이 길어진다. 따라서 '정말로 필요할 때' 초기화해서 오래 걸리는 작업을 분산할 수 있다.
     *
     *
     *          Proxy와 Real?
     *   Proxy, Real로 나누면 역시 부품화로 개별적 수정을 할 수 있다.
     *
     *
     *          투과적
     *  PrinterProxy와 Printer는 Printable을 구현한다. Main은 실제 호출하는 곳이 PrinterProxy이든 Printer이든 별 상관을 하지 ㅇ낳는다.
     *  이런 경우 PrinterProxy를 투과적이라고 할 수 있다.
     *
     *
     *          HTTP PROXY
     *  HTTP에서 프록시는 server - client 사이에 웹 페이지를 캐싱하는 소프트웨어이다. 이것도 Proxy 패턴을 적용해서 생각할 수 있다.
     *
     *
     *          다양한 Proxy
     *  VirtualProxy : 이번 장에서 실제 인스턴스가 필요한 시점에서 생성 및 초기화를 한다.
     *  RemoteProxy : RealSubject가 네트워크 저편에 있음에도 마치 자기 옆에 있는 것처럼 메소드를 호출할 수 있다. (Java RMI : Remote Method Invocation: 원격 메소드 호출) 등이 여기에
     *                해당
     *  AccessProxy: RealySubject 역의 기능에 대해서 접근 제한을 설정한다. 지정된 사용자라면 메소드 호출을 허가하지만 나머지가 오류가 되도록 처리하는 프록시이다.
     *
     *
     */
}