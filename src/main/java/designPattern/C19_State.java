package designPattern;

import javax.naming.Context;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * State 패턴은 상태를 클래스로 표현한다. state란 상태를 의미한다.
 *
 * state를 사용하지 않는 의사코드
 *
 *  경비 시스템 클래스 {
 *
 *      금고 사용 시 호출되는 메소드() {
 *          if ( 주간 ) {
 *              경비 센터에 사용 기록
 *          } else if ( 야간 ) {
 *              경비 센터에 비상 상황 통보
 *          }
 *      }
 *
 *      비상벨 사용 시 호출되는 메소드() {
 *          경비 센터에 비상벨 통보
 *      }
 *
 *      일반 통화 시 호출되는 메소드() {
 *          if ( 주간 ) {
 *              경비 센터 호출
 *          } else if ( 야간 ) {
 *              경비 센터 자동 응답기 호출
 *          }
 *      }
 *  }
 *
 *
 *  State 패턴 사용 의사 코드
 *
 *  [주간] 상태를 표현하는 클래스 {
 *      금고 사용 시 호출되는 메소드 () {
 *          경비 센터에 사용 기록
 *      }
 *
 *      비상벨 사용 시 호출되는 메소드() {
 *          경비 센터에 비상벨 통보
 *      }
 *
 *      일반 통화 시 호출되는 메소드() {
 *          경비 센터 호출
 *      }
 *  }
 *
 *  [야간] 상태를 표현하는 클래스 {
 *   금고 사용 시 호출되는 메소드 () {
 *       경비 센터에 사용 기록
 *   }
 *
 *   비상벨 사용 시 호출되는 메소드() {
 *       경비 센터에 비상벨 통보
 *   }
 *
 *   일반 통화 시 호출되는 메소드() {
 *       경비 센터 호출
 *   }
 *  }
 *
 *  상태가 클래스로 표현되어 있으므로, 각 메소드는 if 문이 등장하지 않는다.
 *
 */

interface State {
    public void doClock(Contexts context, int hour);
    public void doUse(Contexts context);
    public void doAlarm(Contexts context);
    public void doPhone(Contexts context);
}
interface Contexts {
    public void setClock(int hour);
    public void changeState(State state);
    public void callSecurityCenter(String msg);
    public void recordLog(String msg);
}

class DayState implements State {
    private static DayState singleton = new DayState();
    public DayState() {}
    public static State getInstance() { return singleton;}

    @Override
    public void doClock(Contexts context, int hour) {
        if( hour < 9 || 17 <= hour ){
            context.changeState(NightState.getInstance());
        }
    }

    @Override
    public void doUse(Contexts context) {
        context.recordLog("금고 사용 (주간)");
    }

    @Override
    public void doAlarm(Contexts context) {
        context.callSecurityCenter("비상벨 (주간)");
    }

    @Override
    public void doPhone(Contexts context) {
        context.callSecurityCenter("일반 통화 (주간)");
    }

    @Override
    public String toString() {
        return "[주간]";
    }
}
class NightState implements State {
    private static NightState singleton = new NightState();

    public NightState() {}
    public static State getInstance() {return singleton;}

    @Override
    public void doClock(Contexts context, int hour) {
        if ( 9 <= hour && hour < 17 ){
            context.changeState(DayState.getInstance());
        }
    }

    @Override
    public void doUse(Contexts context) {
        context.callSecurityCenter("비상 : 야간 금고 사용!");
    }

    @Override
    public void doAlarm(Contexts context) {
        context.callSecurityCenter("비상벨 (야간)");
    }

    @Override
    public void doPhone(Contexts context) {
        context.recordLog("야간 통화 녹음");
    }

    @Override
    public String toString() {
        return "[야간]";
    }
}
class SafeFrame extends Frame implements ActionListener, Contexts {
    private TextField textClock = new TextField(60);
    private TextArea textScreen = new TextArea(10, 60);
    private Button buttonUse = new Button("금고 사용");
    private Button buttonAlarm = new Button("비상벨");
    private Button buttonPhone = new Button("일반 통화");
    private Button buttonExit = new Button("종료");

    private State state = DayState.getInstance();

    public SafeFrame(String title) throws HeadlessException {
        super(title);

        setBackground(Color.lightGray);
        setLayout(new BorderLayout());

        add(textClock, BorderLayout.NORTH);
        textClock.setEditable(false);

        add(textScreen, BorderLayout.CENTER);
        textScreen.setEditable(false);

        Panel panel = new Panel();
        panel.add(buttonUse);
        panel.add(buttonAlarm);
        panel.add(buttonPhone);
        panel.add(buttonExit);

        add(panel, BorderLayout.SOUTH);

        pack();
        setVisible(true);

        buttonUse.addActionListener(this);
        buttonAlarm.addActionListener(this);
        buttonPhone.addActionListener(this);
        buttonExit.addActionListener(this);
    }

    @Override
    public void setClock(int hour) {
        String clockString = String.format("현재 시간은 %02d:00", hour);
        System.out.println(clockString);
        textClock.setText(clockString);
        state.doClock(this, hour);
    }

    @Override
    public void changeState(State state) {
        System.out.println(this.state + "에서 "+ state + "으로 상태가 변화했다.");
        this.state = state;
    }

    @Override
    public void callSecurityCenter(String msg) {
        textScreen.append("call!"+msg+"\n");
    }

    @Override
    public void recordLog(String msg) {
        textScreen.append("RECORD..."+msg+"\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.toString());
        if(e.getSource().equals(buttonUse)){
            state.doUse(this);
        } else if(e.getSource().equals(buttonAlarm)){
            state.doAlarm(this);
        } else if(e.getSource().equals(buttonPhone)){
            state.doPhone(this);
        } else if(e.getSource().equals(buttonExit)){
            System.exit(0);
        } else {
            System.out.println("?");
        }
    }
}

public class C19_State {
    public static void main(String[] args) {
        SafeFrame frame = new SafeFrame("State Sample");
        while ( true ) {
            for ( int hour = 0; hour < 24; hour ++ ){
                frame.setClock(hour);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

/**
 * State : 상태, 상태마다 다르게 동작하는 인터페이스를 정의한다. 이 인터페이스는 상태에 의존한 동작을 하는 메소드 모음이 된다.
 * ConcreteState : State에서 정의된 인터페이스를 구체적으로 구현한다.
 * Context : 현재 상태를 나타내는 ConcreteState를 가진다. 또한, State 패턴 이용자에게 필요한 인터페이스를 정의한다.
 *
 *          분할해서 통치하라
 * 복잡하고 규모가 큰 프로그램을 다룰 경우에 사용하는 방침이다. 크고 복잡한 문제를 그래도 해결하려 해서는 안된다. 그 문제를 작은 문제로 나누고 하나씩 처리해나가면 된다.
 * ConcreteState의 경우도 다룰 수 있는 가장 작은 단위로 나눈 경우이다.
 *
 *          상태에 의존한 처리
 * setClock 메소드는 state에 아래와 같이 위임한다.
 *
 *  state.doClock(this, hour);
 *
 *  즉, 시간 설정을 '현재 상태에 의존하는 처리'로 다루는 것이다. state 인터페이스에 선언된 메소드는 모두 '상태에 의존하는 처리'이고, '상태에 따라 동작이 달라지는 처리'이다.
 *  State 패턴에서는 '상태에 의존하는 처리'를 프로그램으로 어떻게 표현하고 있을까?
 *
 *      - 추상 메소드로 선언하고 인터페이스로 한다.
 *      - 구상 메소드로 구현하고 개별 클래스로 한다.
 *
 *  이것이 State '상태에 의존하는 처리'를 표현하는 방법이다.
 *
 *
 *          상태 전환은 누가 관리하는가?
 *  상태를 클래스로 표현해서 상태에 의존하는 동작을 각각의 ConcreteState 역에 분담하는 것은 매우 좋은 방법이다.
 *  State 패턴을 사용할 떄는 상태 전환을 누가 관리하는지 주의하자.
 *
 *  위 예제는 SafeFrame이다. 그러나 실제로 호출하는 것은 ConcreteState가 한다.
 *  이러한 방법은 다른 상태로 전환하는 것이 언제인가라는 정보가 한 클래스 내에 있다는 점이다. DayState 전환 시점을 알고 싶으면 DayState를 읽으면 된다.
 *
 *  단점은 ConcreteState가 서로를 알고 있다는 점이다. 이는 의존 관계 형성을 의미한다.
 *
 *
 *
 *
 *
 */
