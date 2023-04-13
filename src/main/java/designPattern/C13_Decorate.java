package designPattern;

import org.junit.jupiter.api.Test;

public class C13_Decorate {
    /**
     * 객체에 점점 장식을 더해 가는 디자인 패턴을 Decorator라고 한다.
     *
     *  Display
     */
    public abstract class Display {
        public abstract int getColumns();
        public abstract int getRows();
        public abstract String getRowText( int row );

        public void show() {
            for (int i = 0; i < getRows(); i++ ){
                System.out.println(getRowText(i));
            }
        }

    }
    public class StringDisplay extends Display {
        private String text;
        public StringDisplay(String text) {
            this.text = text;
        }

        @Override
        public int getColumns() {
            return text.length();
        }

        @Override
        public int getRows() {
            return 1;
        }

        @Override
        public String getRowText(int row) {
            if( row != 0 ) throw new IndexOutOfBoundsException();
            return text;
        }
    }
    public abstract class Border extends Display {
        protected Display display;
        protected  Border(Display display){ this.display = display; }
    }
    public class SideBorder extends Border {
        private char borderChar;

        public SideBorder(Display display, char borderChar) {
            super(display);
            this.borderChar = borderChar;
        }


        @Override
        public int getColumns() {
            return 1 + display.getColumns() + 1;
        }

        @Override
        public int getRows() {
            return display.getRows();
        }

        @Override
        public String getRowText(int row) {
            return borderChar + display.getRowText(row) + borderChar;
        }
    }
    public class FullBorder extends Border {
        public FullBorder( Display display ){
            super(display);
        }

        @Override
        public int getColumns() {
            return 1 + display.getColumns() + 1;
        }

        @Override
        public int getRows() {
            return 1 + display.getRows() + 1;
        }

        @Override
        public String getRowText(int row) {
            if ( row == 0 ){
                return "+"+makeLine('-', display.getColumns())+"+";
            } else if (row == display.getRows() + 1) {
                return "+"+makeLine('-', display.getColumns())+"+";
            } else {
                return "|" + display.getRowText(row - 1)+"|";
            }
        }


        private String makeLine( char ch, int count ) {
            StringBuilder line = new StringBuilder();
            for( int i = 0; i < count; i++ ){
                line.append(ch);
            }
            return line.toString();
        }
    }

    @Test
    void decorateTest() {
        Display b1 = new StringDisplay("HELLO WORLD");
        Display b2 = new SideBorder(b1, '#');
        Display b3 = new FullBorder(b2);

        b1.show();
        b2.show();
        b3.show();

        Display b4 = new SideBorder(
                        new FullBorder(
                            new SideBorder(
                                new FullBorder(
                                    new StringDisplay("HELLO WORLD")
                                ),
                                    '*'
                            )
                        ),
                '/'
                    );

        b4.show();
    }

    /**
     * Component: 기능을 추가할 때 핵심이 되는 부분, (위 예시에서는 Display)
     * ConcreteComponent : 인터페이스를 구현하는 역할 (위 예시에서 StringDisplay)
     * Decorator: Component와 동일한 인터페이스를 가지며, 장식할 대상이 되는 Component도 가지고 있다.(장식 대상을 아는 것과 같다.) (위 예시에서 Border)
     * ConcreteDecorator: SideBorder, FullBorder가 이 역할을 맡았다.
     *
     *
     *      투과적 인터페이스??
     * Decorator에서는 장식 틀과 내용물을 동일시한다. 예제에서 장식틀인 Border 클래스가 내용을 나타내는 Display 클래스의 하위 클래스로 되어 있는 부분에서 그 동일시가
     * 표현되어 있다. 즉, Border 클래스는 내용을 나타내는 Display 클래스와 같은 인터페이스를 가진다.
     * 장식 틀을 사용해서 내용물을 감싸도 인터페이스는 전혀 가려지지 않는다. (getColumns, getRows, getRowText, show 등) 은 가려지지 않고 다른 클래스에서
     * 볼 수 있다. 이런 경우 인터페이스가 투과적이라고 한다.
     *
     * 인터페이스가 투과적이므로 Decorator에서는 Composite 과 유사한 재귀적 구조가 등장한다. 둘 다 재귀를 사용하지만 목적이 다르다.
     * Decorator 패턴은 바깥 테두리를 겹쳐 기능을 추가해 나가는 것이 주된 목적이다.
     *
     *
     *      내용물을 바꾸지 않고 기능을 추가할 수 있다.
     * Decorator 패턴에서 장식틀과 내용물이 공통의 인터페이스를 가진다. 인터페이스는 공통이지만 감쌀수록 기능이 추가된다. Display를 SideBorder로 감싸면
     * 좌우에 새로운 장식 문자를 표시할 수 있고 FullBorder로 감싸면 장식틀을 붙일 수 있다.
     * 이때 감싸지는 쪽을 수정할 필요는 없다. 내용물을 변경하지 않고 기능을 추가할 수 있다.  Decorator는 위임을 사용한다.
     *
     *
     *       동적으로 기능을 추가할 수 있다.
     * Decorator에 사용되는 위임은 클래스 사이를 동적으로 결합한다.
     *
     *
     *      JAVA에서의 예시
     *
     * Reader reader = new FileReader("data.txt");
     * Reader reader = new BufferedReader ( new FileReader("data.txt") );
     * Reader reader = new LineNumberReader ( new BufferedReader ( new FileReader("data.txt") );
     * Reader reader = new LineNumberReader (  new FileReader("data.txt") ;
     * Reader reader = new LineNumberReader ( new BufferedReader ( new InputStream( socket.getInputStream() ) );
     *
     *
     */

}
