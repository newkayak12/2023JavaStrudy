package designPattern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayDeque;
import java.util.Deque;


/**
 * 명령을 클래스로 표현
 *
 * 보통 클래스는 자기 자신이나 다른 클래스의 메소드를 호출해서 일을 처리한다. 메소드를 호출한 결과는 객체 상태에 반영되지만
 * 일한 이력은 어디에도 남지 않는다. 이럴 떄, 명령을 표현하는 클래스가 있으면 편리하다. 명령을 나타내는 클래스의 인스턴스라는
 * 하나의 '객체'로 표현할 수 있다. 이력을 관리하고 싶을 때는 해당 인스턴스의 집합을 관리하면 된다. 명령의 집합을 저장해 두면 같은 명령을
 * 재실행할 수도 있고, 여러 명령을 모아서 새로운 명령으로 재이용할 수도 있을 것이다.
 *
 * 디자인 패턴에서는 이러한 '명령'에 Command 패턴이라는 이름을 붙였다. Command는 Event라고 불리는 경우도 있다.
 */

interface Command {
    public void execute();
}
class MacroCommand implements Command {
    private Deque<Command> commands = new ArrayDeque<>(); //Double End Que
    @Override
    public void execute() {
        for ( Command cmd : commands ) {
            cmd.execute();
        }
    }

    public void append( Command cmd ) throws IllegalAccessException {
        if ( cmd == this ) {
            throw new IllegalAccessException("Infinite loop acued by append");
        }
        commands.push(cmd);
    }

    public void undo() {
        if( !commands.isEmpty() ) {
            commands.pop();
        }
    }

    public void clear() {
        commands.clear();
    }
}

interface Drawable {
    public void draw( int x, int y );
}
class DrawCommand implements Command {
    private Drawable drawable;
    private Point position;

    public DrawCommand(Drawable drawable, Point position) {
        this.drawable = drawable;
        this.position = position;
    }

    @Override
    public void execute() {
        drawable.draw(position.x, position.y);
    }
}

class DrawCanvas extends Canvas implements Drawable {
        private Color color = Color.RED;
        private int radius = 6;

        private MacroCommand history;

        public DrawCanvas(int width, int height, MacroCommand history) {
            setSize(width, height);
            setBackground(Color.WHITE);
            this.history = history;
        }

        @Override
        public void paint(Graphics g) {
            history.execute();
        }

        @Override
        public void draw(int x, int y) {
            Graphics graphics = getGraphics();
            graphics.setColor(color);
            graphics.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        }

    }

public class C22_Command extends JFrame implements MouseMotionListener, WindowListener {
    private MacroCommand history = new MacroCommand();
    private DrawCanvas canvas = new DrawCanvas(400, 400, history);
    private JButton clearButton = new JButton("clear");

    public C22_Command(String title) throws HeadlessException {
        super(title);

        this.addWindowListener(this);
        canvas.addMouseMotionListener(this);
        clearButton.addActionListener(e -> {
            history.clear();
            canvas.repaint();
        });

        Box buttonBox = new Box(BoxLayout.X_AXIS);
        buttonBox.add(clearButton);

        Box mainBox = new Box(BoxLayout.Y_AXIS);
        mainBox.add(buttonBox);
        mainBox.add(canvas);
        getContentPane().add(mainBox);

        pack();

        setVisible(true);
    }


    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Command command = new DrawCommand(canvas, e.getPoint());
        try {
            history.append(command);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        command.execute();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public static void main(String[] args) {
        new C22_Command("COMMAND_PATTERN");
    }
}


/**
 *
 * Command : 명령의 인터페이스를 정의한다.
 * ConcereteCommand : Command의 인터페이스를 구현한다.
 * Receiver :  Command가 명령을 실행할 때 대상이 된다. 명령의 수신자로 부를 수 있다.
 * Client : ConcreteCommand를 생성하고 Recevier를 그 때 할당한다. (예제에서는 Main)
 * Invoker : 명령 실행을 시작한다. Command에 정의된 인터페이스를 호출한다. Main, DrawCanvas가 이 역할을 맡았다.
 *
 *
 *
 *          명령이 가져야하는 정보?
 *
 * '명령'이 어느 정도의 정보를 갖게 할지는 목적에 따라 달라진다.
 */
