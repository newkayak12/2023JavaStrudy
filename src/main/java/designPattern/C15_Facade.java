package designPattern;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Properties;


/**
     * 프로그램은 비대해진다. 많은 클래스가 얽히고 연관되어 복잡해진다. 클래스를 사용하는 경우에는 클래스 간의 관계를 올바르게 이해해고,
     * 올바른 순서로 메소드를 호출할 필요가 있다.
     *
     * 큰 프로그램을 이용해서 처리하려면 관련된 많은 클래스를 적절하게 제어해야만 한다. 그렇지만 처리하기 위한 '창구'를 준비해 두는 것이 좋다.
     * 그렇게 하면, 많든 클래스를 개별적으로 제어하지 않아도 '창구'에 요청하기만 하면 일이 끝나기 때문이다.
     *
     * Facade 패턴은 façade를 어원으로 하는 단어로, '건물의 정면'이라는 뜻이다. Facade 패턴은 복잡하게 얽혀서 너저분한 세부 내용을 정리하여
     * 높은 수준의 인터페이스를 제공한다. Facade 역은 시스템 외부에 간단한 인터페이스를 보여준다. 또한, 시스템 내부의 각 클래스의
     * 역할과 의존 관계를 고려하여 올바른 순서로 클래스를 사용한다.
     *
     */

class Database {
    private Database() {}

    public static Properties getProperties(String dbName) throws IOException {
        String filename = dbName + ".txt";
        Properties prop = new Properties();
        prop.load(new FileReader(filename));
        return prop;
    }
}
class HtmlWriter {
    private Writer writer;
    public HtmlWriter(Writer writer){
        this.writer = writer;
    }

    public void title(String title) throws IOException {
        writer.write("<!DOCTYPE html>");
        writer.write("<html>");
        writer.write("<head>");
        writer.write("<title>" + title + "</title>");
        writer.write("</head>");
        writer.write("<body>");
        writer.write("<h1>" + title + "</h1>");
    }
    public void paragraph(String message) throws IOException {
        writer.write("<p>"+message+"</p>");
    }
    public void link(String href, String caption) throws IOException {
        paragraph("<a href=\""+href+"\">"+caption+"</a>");
    }
    public void mailto(String mailAddr, String userName) throws IOException {
        link("mailto:"+mailAddr, userName);
    }
    public void close() throws IOException {
        writer.write("</body>");
        writer.write("</html>");
        writer.close();
    }
}
class PageMaker {
    private PageMaker() {}

    public static void makeWelcomePage(String mailAddr, String fileName) {
        try {
            Properties mailProp = Database.getProperties("maildata");
            String userName = mailProp.getProperty(mailAddr);
            HtmlWriter writer = new HtmlWriter(new FileWriter(fileName));
            writer.title(userName+"'s web page");
            writer.paragraph("Welcome to "+ userName + "'s web page!");
            writer.paragraph("Nice to meet you!");
            writer.mailto(mailAddr, userName);
            writer.close();
            System.out.println(fileName + "is created for "+mailAddr + "("+userName+")");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
public class C15_Facade {
    @Test
    void facadeTest() {
        PageMaker.makeWelcomePage("hyuki@example.com", "welcome.html");
    }
}

/**
 *Facade: Facade는 단순한 인터페이스를 외부에 제공한다.(위 예시 PageMaker)
 * 그 밖의 다른 부품들은 자신의 일에 집중하고 Facade에 신경쓰지 않는다.
 *
 *
 *
 *          Facade?
 * Facade는 복잡한 것을 단순하게 보여준다. '복잡한 것'이란 내부에서 동작하는 많은 클래스 간의 관계나 사용법을 의미한다.
 * 여기서 핵심은 인터페이스 수를 줄이는 것이다. 클래스, 메소드가 많으면 필연적으로 어떤 것을 사용해야하는지, 호출 순서는 어떻게 가져가야하는지 헷깔리기 시작한다.
 * 그러므로 인터페이스가 적은 Facade를 고려하는 것이 좋다. 또한 인터페이스 수가 적다는 것은 외부와 결합이 느슨하다고 볼 수도 있다.
 *
 * 클래스를 설계할 때는 어떤 메소드를 public으로 할지 생각해야 한다. 너무 많은 메소드를 public으로 선언하면 클래스 내부를 수정하기 어렵다. 필드를 너무 public으로 선언하면
 * 마음대로 변경할 수 있기 때문에 에상을 벗어난 동작을 보일 수도 있다.
 *
 *
 *      재귀적인 Facade
 * 확장하면 Facade 집합을 모아 새로운 Facade를 도입하는 등의 Facade를 재귀적으로 적용할 수도 있다.
 *
 *
 *
 */
