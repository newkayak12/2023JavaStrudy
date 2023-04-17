package designPattern;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created on 2023-04-07
 * Project 2023JavaStudy
 */
public class C07_Builder {
    /**
     * Builder은 구조를 가진 인스턴스를 만들어 가는데 사용한다.
     */

    public abstract class Builder {
        abstract Builder makeTitle( String title );
        abstract Builder makeString( String string );
        abstract Builder makeItems( String... items );
        abstract void close();
    }

    public class Director {
        private Builder builder;

        public Director( Builder builder ){
            this.builder = builder;
        }

        public void construct() {
            builder.makeTitle("Greeting")
                   .makeString("일반적인 인사")
                   .makeItems("How are you?", "Hello.", "Hi.")
                   .makeString("시간대별 인사")
                   .makeItems("Good morning.", "Good afternoon.", "Good evening.")
                   .close();
        }
    }
    public class TextBuilder extends  Builder {
        private StringBuilder builder = new StringBuilder();

        @Override
        Builder makeTitle(String title) {
            builder.append("====================\n");
            builder.append("[");
            builder.append(title);
            builder.append("]\n\n");
            return this;
        }

        @Override
        Builder makeString(String string) {
            builder.append("■");
            builder.append(string);
            builder.append("\n\n");
            return this;
        }

        @Override
        Builder makeItems(String... items) {
            for( String piece: items ){
                builder.append(" ▶ ");
                builder.append(piece);
                builder.append("\n");
            }
            builder.append("\n");
            return this;
        }

        @Override
        void close() {
            builder.append("====================");
        }

        public String getTextResult() {
            return builder.toString();
        }
    }
    public class HtmlBuilder extends  Builder {
        private StringBuilder stringBuilder = new StringBuilder();
        private String fileName = "index.html";

        @Override
        Builder makeTitle(String title) {
            fileName = title+".html";
            stringBuilder.append("<!DOCTYPE html>" +
                                 " <meta charset=\"UTF-8\">"+
                                 "  <html>" +
                                 "   <head>" +
                                 "      <title> " +
                                 "          "+ title +
                                 "      </title>" +
                                 "   </head>" +
                                 "   <body>" +
                                 "      <h1>"+
                                 "         "+title+
                                 "      </h1>" +
                                 "");

            return this;
        }

        @Override
        Builder makeString(String string) {
            stringBuilder.append("<p>"+string+"</p>");
            return this;
        }

        @Override
        Builder makeItems(String... items) {
            stringBuilder.append("<ul>");
            for ( String piece : items) {
                stringBuilder.append("<li>"+ piece +"</li>");
            }
            stringBuilder.append("</ul>\n");
            return this;
        }

        @Override
        void close() {
            stringBuilder.append("</body>");
            stringBuilder.append("</html>");

            try {
                Writer writer = new FileWriter(fileName);
                writer.write(stringBuilder.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getHtmlResult() {
            return fileName;
        }
    }
    @ParameterizedTest
    @ValueSource(strings = {"html", "text"})
    void BuilderTest(String type) {
        if(type.equals("text")){
            TextBuilder textBuilder = new TextBuilder();
            Director director = new Director(textBuilder);
            director.construct();
            String result = textBuilder.getTextResult();
            System.out.println(result);
        } else {
            HtmlBuilder htmlBuilder = new HtmlBuilder();
            Director director = new Director(htmlBuilder);
            director.construct();
            String fileName = htmlBuilder.getHtmlResult();
            System.out.println(fileName);
        }

    }

    /**
     * Builder 역할 : 인스턴스를 생성하기 위한 인터페이스를 결정한다. Builder에 인스턴스가 각 부분을 만드는 메소드가 준비된다.
     * ConcreteBuilder: Builder의 인터페이스를 구현하는 클래스이다. 실제 인스턴스 생성으로 호출되는 메소드가 여기에 정의된다. (예제에서 TextBuilder, HtmlBuilder)
     * Director: Builder 인터페이스를 사용하여 인스턴스를 생성한다. ConcreteBuilder 역이 무엇이든 잘 작동하도록 Builder의 메소드만 사용한다.
     *
     *
     *
     *
     *          의존성
     * Builder를 사용하면서 파알할 수 있는 것이지만 Director 는 자신이 실제로 이용하고 있는 Builder가 무엇인지 모른다. 이는 Builder 가
     * 추상 클래스로 이뤄져 있기 떄문이다. 결과적으로 '모른다'는 점은 서로 간섭 할 수 없다는 점이고 이는 의존 서잉 낮다는 것으ㅏㄹ 의미한다ㅣ.
     * 이는 언제든지 '교체 가능성'을 둘 수 있다는 것이다.
     *
     *
     *          의존성 주입( Dependency Injection)
     * Director는 Builder는 알고 있지만 이를 서브클래싱한 TextBuilder나 HTMLBuilder에 대해서는 모른다. 이는 Director가  TextBuilder나 HTMLBuilder
     * 에 의존하지 않고 있음을 의미한다.
     *
     * 하지만 Director가 동작하려면 builder의 구체적인 인스턴스가 필요하다. 그래서 생성자의 인수로 전달받는다. Director 클래스의 소스 코드에
     * TextBuilder, HTMLBuilder라고 쓰여져 있지 않지만, TextBuilder나 HTMLBuilder의 인스턴스에서 의존해서 동작하게 된다.
     *
     * '소스 코드에는 쓰여져 있지 않지만, 실제로는 이 인스턴스를 이용해서 동작한다. 이렇게 인스턴스를 건네는 방법을 일반적으로 '의존성 주입'
     * 이라고 한다.
     * 이렇게 하면 클래스 간 결합도를 낮추고 프로그램의 재상용성을 높여주는 유용한 ㅇ방법이다.
     *
     */
}
