package designPattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2023-04-07
 * Project 2023JavaStudy
 */

//C08_AbstractFactory
// 관련 부품을 조합하여 제품을 만든다.

/**
 * AbstractFactory에서는 추상적인 부품을 조합하여 추상적인 제품을 만든다. 추상적이라는 말은 구체적으로 어떻게 구현되어 있는 생각하지 않고
 * 인터페이스에만 주목하는 상태를 뜻한다.
 * 다시 돌아와서 요컨데, 부품의 구체적인 구현에는 주목하지 않고 인터페이스에 집중한다. 그리고 그 인터페이스만 사용해서 부품을 조립하고 제품으로 완성하는 것이다.
 *
 */

//factory
abstract class Item {
    protected String caption;
    public Item ( String caption ){
        this.caption = caption;
    }
    public abstract String makeHTML();
}
abstract class Link extends Item {
    protected String url;
    public Link(String caption, String url) {
        super(caption);
        this.url = url;
    }
} //HTML 링크를 나타내는 클래스
abstract class Tray extends Item {
    protected List<Item> tray = new ArrayList<>();

    public Tray( String caption ) {
        super(caption);
    }
    public void add ( Item item ) {
        tray.add(item
        );
    }
} //Link나 Tray를 모은 클래스
abstract class Page {
    protected String title;
    protected String author;
    protected List<Item> content = new ArrayList<>();

    public Page( String title, String author ) {
        this.title = title;
        this.author = author;
    }

    public void add( Item item ){
        content.add(item);
    }

    public void output ( String fileName ){
        try {
            Files.writeString(
                    Path.of(fileName),
                    makeHTML(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );
            System.out.println("WRITTEN");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract String makeHTML();
} //HTML 페이지를 나타내는 클래스
abstract class Factory {
    public static Factory getFactory(String classname) {
        Factory factory = null;
        try {
            factory = (Factory) Class.forName(classname).getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return factory;
    }
    abstract Link createLink( String caption, String url );
    abstract Tray createTray( String caption );
    abstract Page createPage( String title, String author );
} //추상적인 공장을 나타내는 클래스로 (Link, Tray, Page를 만듦)


public class C08_AbstractFactory {
    @ParameterizedTest
    @ValueSource(strings = {"fileName", "ListFactory"})
    void abstractFactoryTest(String fileName, String className) {

        Factory factory = Factory.getFactory(className);

        Link blog1 = factory.createLink("BLOG 1", "https://example.com/blog1");
        Link blog2 = factory.createLink("BLOG 1", "https://example.com/blog2");
        Link blog3 = factory.createLink("BLOG 1", "https://example.com/blog3");

        Tray blogTray = factory.createTray("Blog Site");
        blogTray.add(blog1);
        blogTray.add(blog2);
        blogTray.add(blog3);


        Link news1 = factory.createLink("BLOG 1", "https://example.com/news1");
        Link news2 = factory.createLink("BLOG 1", "https://example.com/news2");

        Tray newsTray = factory.createTray("Blog Site");
        newsTray.add(news1);
        newsTray.add(news2);

        Page page = factory.createPage("Blog and News", "Younjin.com");
        page.add(blogTray);
        page.add(newsTray);


        page.output(fileName);
    }
}

class ListFactory extends Factory {

    @Override
    Link createLink(String caption, String url) {
        return new ListLink(caption, url);
    }

    @Override
    Tray createTray(String caption) {
        return new ListTray(caption);
    }

    @Override
    Page createPage(String title, String author) {
        return new ListPage(title, author);
    }
}
class ListLink extends Link {
public ListLink(String caption, String url) {
    super(caption, url);
}
    @Override
    public String makeHTML() {
        return "<li><a href=\""+url+"\">"+caption+"</a></li>\n";
    }
}
class ListTray extends Tray {
    public ListTray( String caption ){
        super(caption);
    }

    @Override
    public String makeHTML() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<li>");
        stringBuilder.append(caption);
        stringBuilder.append("<ul>");
        for ( Item item : tray ){
            stringBuilder.append(item.makeHTML());
        }
        stringBuilder.append("</ul>");
        stringBuilder.append("</li>");
        return stringBuilder.toString();
    }

}
class ListPage extends Page {
    public ListPage(String title, String author){
        super(title, author);
    }

    @Override
    String makeHTML() {
         StringBuilder stringBuilder = new StringBuilder("<!DOCTYPE html> <html> <head> <title> "+title+"</title></head><body><h1>"+title+"</h1><ul>");
         for ( Item item : content )  stringBuilder.append(item.makeHTML());
         stringBuilder.append("</ul><hr><address>"+author+"</address></body></html>");
        return stringBuilder.toString();
    }


}


class DivFactory extends Factory {

    @Override
    Link createLink(String caption, String url) {
        return new DivLink(caption, url);
    }

    @Override
    Tray createTray(String caption) {
        return new DivTray(caption);
    }

    @Override
    Page createPage(String title, String author) {
        return new DivPage(title, author);
    }
}
class DivLink extends Link {
    public DivLink(String caption, String url) {
        super(caption, url);
    }
    @Override
    public String makeHTML() {
        return "<div><a href=\""+url+"\">"+caption+"</a></div>\n";
    }
}
class DivTray extends Tray {
    public DivTray( String caption ){
        super(caption);
    }

    @Override
    public String makeHTML() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<p><b>");
        stringBuilder.append(caption);
        stringBuilder.append("</b></p><div class=\"TRAY\"> ");
        for ( Item item : tray ){
            stringBuilder.append(item.makeHTML());
        }
        stringBuilder.append( "</div>");
        return stringBuilder.toString();
    }

}
class DivPage extends Page {
    public DivPage(String title, String author){
        super(title, author);
    }

    @Override
    String makeHTML() {
        StringBuilder stringBuilder = new StringBuilder("<!DOCTYPE html> <html> <head> <title> "+title+"</title></head><body><h1>"+title+"</h1><ul>");
        for ( Item item : content )  stringBuilder.append(item.makeHTML());
        stringBuilder.append("</ul><hr><address>"+author+"</address></body></html>");
        return stringBuilder.toString();
    }


}

/**
 * AbstractProduct : 추상적인 부품이나 제품의 인터페이스를 결정한다 .
 * AbstractFactory : 인스턴스를 만들기 위한 인터페이스를 결정한다.
 * ConcreteProduct : AbstractProduct를 구현
 * ConcreteFactory : AbstractFactory를 구현
 *
 *
 *      AbstractFactory에서 factory를 추가하는 것은 간단하다.
 * 에를 들어 다시 새로운 구체적인 공장을 추가한다고 해보자. 해야할 일은 Factory, Link, Tray, Page의 서브클래싱을 하고 각각 추상 메소드를 구현하는 일이다.
 * 즉, factory 패키지의 클래스가 가진 추상적인 부분을 구체화 하는 일이다.
 * 이때 아무리 추가해도 abstractFactory를 건들 일은 없다.
 *
 *      부품을 새로 추가하는 것은 어렵다.
 * 만약 새롭게 부품을 추가하면 이미 존재하는 abstractFactory를 모두 새로운 부품에 대응하도록 수정해야 한다.
 *
 */



