package designPattern;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 디자인 패턴의 목적 중 하나는 클래스의 재사용성을 높이는 것이다. 재사용성이란 한 번 작성한 클래스를 거의 수정하지 않고 몇 번이고 사용할 수 있도록 하는
 * 것이다.
 * Interpreter는 프로그램이 해결하려는 문제를 간단한 '미니 언어'로 표현한다. 구체적인 문제를 미니 언어로 작성된 '미니 프로그램'으로 나타내는 것이다.
 * 미니 프로그램은 그 자체로는 동작하지 않기 때문에 Java언어로 통역하는 역할을 하는 프로그램을 만들어둔다. 통영 프로그램은 미니 언어를 이해하고 해석하여
 * 프로그램을 실행한다. 이 통역 프로그램 자체를 인터프리터라고 부른다.
 *
 * 해결해야할 문제가 변경되면 Java언어로 작성한 프로그램을 바꾸는 것이 아니라, 미니 프로그램 쪽 코드를 변경해서 대처한다.
 *
 *
 *      > 미니 언어
 *  Interpreter 패턴 예제 프로그램을 설명하기 전 '미니 언어'를 알아보자. 무선으로 자동차를 조종한다고 가정하자
 *      - go
 *      - left
 *      - right
 *
 *      - repeat
 *
 *
 */

class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }
}
class Context {
    private String[] tokens;
    private String lastToken;
    private int index;

    public Context( String text ) {
        this.tokens = text.split("\\s+");
        this.index = 0;
        nextToken();
    }

    public String nextToken() {
        if ( index < tokens.length ) {
            lastToken = tokens[index++];
        } else {
            lastToken = null;
        }
        return lastToken;
    }

    public String currentToken() {
        return lastToken;
    }

    public void skipToken( String token ) throws ParseException {
        if( currentToken() == null ) {
            throw  new ParseException("ERROR : "+token+" is expected, but no more token is found");
        } else if ( !token.equals(currentToken()) ) {
            throw  new ParseException("Error : " + token + " is expected but "+ currentToken() + " is found");
        }

        nextToken();
    }

    public int currentNumber() throws  ParseException {
        if ( currentToken() == null ) {
            throw new ParseException("Error : No more token");
        }

        int number = 0;
        try {
            number = Integer.parseInt(currentToken());
        } catch ( NumberFormatException e ) {
            throw new ParseException("ERROR : " + e);
        }

        return number;
    }
}

/**
 * Node는 구문 해석을 하는 것에 대한 것을 정의해놓았다.
 */
abstract class Node {
    public abstract void parse( Context context ) throws ParseException;
}
// ProgramNode ->

class ProgramNode extends  Node {
    private Node commandListNode;


    @Override
    public void parse(Context context) throws ParseException {
        context.skipToken("program");
        commandListNode = new CommandListNode();
        commandListNode.parse(context);
    }
}
class CommandListNode extends Node {
    private List<Node> list = new ArrayList<>();

    @Override
    public void parse(Context context) throws ParseException {
        while ( true ) {
            if ( context.currentToken() == null ) {
                throw new ParseException("Error :  Missing end");
            } else if ( context.currentToken().equals("end") ) {
                context.skipToken("end");
                break;
            } else {
                Node commandNode = new CommandNode();
                commandNode.parse(context);
                list.add(commandNode);
            }
        }
    }

    @Override
    public String toString() {
        return  list.toString();
    }
}
class CommandNode extends Node {
    private Node node;
    @Override
    public void parse(Context context) throws ParseException {
        if ( context.currentToken().equals("repeat") ) {
            node = new RepeatCommandNode();
            node.parse(context);
        } else {
            node = new PrimitiveCommandNode();
            node.parse(context);
        }
    }

    @Override
    public String toString() {
        return node.toString();
    }
}
class RepeatCommandNode extends Node {
    private int number;
    private Node commandListNode;

    @Override
    public void parse(Context context) throws ParseException {
        context.skipToken("repeat");
        number = context.currentNumber();
        context.nextToken();
        commandListNode = new CommandListNode();
        commandListNode.parse(context);
    }

    @Override
    public String toString() {
        return "[" +
                "number=" + number +
                " commandListNode=" + commandListNode +
                ']';
    }
}
class PrimitiveCommandNode extends Node {
    private String name;

    @Override
    public void parse(Context context) throws ParseException {
        name = context.currentToken();
        if ( name == null ) {
            throw new ParseException("Error : Missing <primitive command>");
        } else if ( !name.equals("go") && !name.equals("right") && !name.equals("left") ) {
            throw new ParseException("error unknown <primitive command> " + name);
        }
        context.skipToken(name);
    }

    @Override
    public String toString() {
        return name;
    }
}


public class C23_Interpreter {
    public static void main(String[] args) {
        try {
            for ( String text : Files.readAllLines(Path.of("program.txt"))) {
                System.out.println("Text = " + text);
                Node node = new ProgramNode();
                node.parse(new Context(text));
                System.out.println("node = " + node);
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
/**
 * AbstractExpression : 구문 트리의 노드에 공통 인터페이스를 선언한다. (Node)
 * TerminalExpression : BNF의 터미널 익스프레션에 대응
 * NonterminalExpression : BNF의 논 터미널 익스프레션에 대응
 * Context : 인터프리터가 구문 해석을 하기 위한 정보를 제공
 * Client : 구문 트리를 조립하기 위해서 TerminalExpression와 NoneterminalExpression을 호출
 *
 *
 *
 *          어떤 미니 언어가 있나?
 *  - 정규표현
 *  - 검색
 *  - 배치 처리 언어
 */
