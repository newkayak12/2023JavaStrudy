package designPattern;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 객체 지향 프로그램으로 실행 취소를 구현하려면 인스턴스가 가진 정보를 저장해 둬야 한다. 단, 저장해 두는 것만으로는 안되고 저장해 둔 정보로 인스턴스를
 * 원래 상태로 복원할 수 있어야만 한다. 인스턴스를 복원하기 위해서는 인스턴스 내부 정보에 자유롭게 접근할 수 있어야 한다. 그러나, 부주의 하게 접근을
 * 허락하면, 해당 클래스의 내부 구조에 의존하는 코드가 생겨서 클래스 수정에 문제가 생길 수 있다.
 * 이를 캡슐화 파괴라고 한다. 인스턴스의 상태를 나타내는 역할을 도입해서 캡술화의 파괴에 빠지지 않고 저장과 복원을 하는 것이 Memento이다.
 *
 * Memento를 이용하면
 *
 *  1. 실행 취소,
 *  2. 다시 실행
 *  3. 작업 이력 작성
 *  4. 현재 상태 저장
 *
 *
 *  아래 예제 동작 예시
 *  - 게임 주인공이 주사위를 던져나온 수가 다음 상태를 결정
 *  - 좋은 수가 나오면 주인공의 돈이 증가
 *  - 나쁜 수가 나오면 돈이 줄어듦
 *  - 특히 좋은수는 주인공이 과일을 받음
 *  - 돈이 없어지면 종료
 */

class Memento {
    private int money;
    private List<String> fruits;

    //narrow interface
    public int getMoney() {
        return money;
    }


    //생성자 wide interface
    public Memento(int money) {
        this.money = money;
        this.fruits = new ArrayList<>();
    }

    //wide interface
    void addFruit(String fruit) {
        this.fruits.add(fruit);
    }

    //wide interface
    public List<String> getFruits() {
        return fruits;
    }
}
class Gamer {
    private int money;
    private List<String> fruits = new ArrayList<>();
    private Random random = new Random();
    private static String[] fruitsName = {"사과", "포도", "바나나", "오렌지"};

    public Gamer(int money) {
        this.money = money;
    }
    public int getMoney() {
        return money;
    }
    void bet() {
        int dice = random.nextInt(6) + 1;
        switch (dice){
            case 1:
                money += 100;
                System.out.println("EARN");
                break;
            case 2:
                money /= 2;
                System.out.println("LOSE HALF");
                break;
            case 6:
                String f = getFruit();
                System.out.println("과일 (" + f + ")를 받음");
                fruits.add(f);
                break;
            default:
                System.out.println("NO ACTION");
                break;
        }
    }

    public Memento createMemento() {
        Memento m = new Memento(money);
        for ( String f : fruits ){
            if( f.startsWith("맛있는 ")){
                m.addFruit(f);
            }
        }

        return m;
    }
    public void restoreMemento(Memento memento){
        this.money = memento.getMoney();
        this.fruits = memento.getFruits();
    }

    @Override
    public String toString() {
        return "[money=" + money +
                ", fruits=" + fruits +']';
    }

    private String getFruit() {
        String f = fruitsName[random.nextInt(fruitsName.length)];
        if( random.nextBoolean() ){
            return "맛있는 "+f;
        } else {
            return f;
        }
    }
}

public class C18_Memento {

    @Test
    void mementoTest() throws InterruptedException {
        Gamer gamer = new Gamer(100);
        Memento memento = gamer.createMemento();

        for ( int i = 0; i < 100; i++ ){
            System.out.println("---- "+ i);
            System.out.println("Status : " + gamer);

            gamer.bet();

            System.out.println("MONEY : "+ gamer.getMoney());

            if( gamer.getMoney() > memento.getMoney() ){
                System.out.println("SAVE!");
                memento = gamer.createMemento();
            } else if ( gamer.getMoney() < memento.getMoney() / 2 ){
                System.out.println("RESTORE");
                gamer.restoreMemento(memento);

                Thread.sleep(1000);
                System.out.println();
            }
        }
    }

}
/**
 * Originator : 자신의 현재 상태를 저장하고 싶을 때 Memento를 만들고 이전 Memento를 넘겨 받으면 Mement를 만든 시점의 상태로 되돌리는 처리를 한다.
 * Memento : Originator의 내부 정보를 정리한다. Memento는 Originator의 내부 정보를 가지고 있지만, 그 정보를 누구에게나 공개하는 것은 아니다.
 *
 *      > Memento는 두 가지의 인터페이스를 갖고 있다.
 *
 *          - wide interface 넓은 인터페이스
 *              Memento가 제공하는 '넓은 인터페이스'는 오브젝트의 상태를 되돌리는 데 필요한 정보를 모두 얻을 수 있는 메소드 집합이다. 넓은 인터페이스
 *              는 Memento의 내부 상태를 드러내기 때문에, 이 인터페이스를 사용할 수 있는 것은 Originator 뿐이다.
 *          - narrow interface 좁은 인터페이스
 *              Memento가 제공하는 '좁은 인터페이스'는 외부 Caretaker에 보여주는 것이다. 좁은 인터페이스로 할 수 있는 일에는 한계가 있어 내부 상태가
 *              외부에 공개되는 것 방지한다.
 *
 *       이 두 종류의 인터페이스를 구분해서 사용해서 객체의 캡슐화 파괴를 막을 수 있다.
 * Caretaker : 현재 Originator의 상태를 저장하고 싶을 때 Originator에 요청한다. Originator역은 요청을 받으면 Memento를 만들어 Caretaker에
 *            넘겨 주고, Caretaker는 미래에 대비하여 그 Memento를 저장해 둔다. ( 위 예시에서는 Caretaker)
 *
 *            하지만 Caretaker는 Memento가 가진 두 가지 인터페이스 중 '좁은 인터페이스'만 사용할 수 있으므로, Memento의 내부 정보에 접근할 수 없다.
 *            단지, 만들어 준 Memento를 한 덩어리의 블랙 박스로 통째로 보관해 두기만 한다.
 *
 *
 *
 *          Memento를 몇 개 가질까?
 *  Memento의 인스턴스를 여러 개 갖게 하면 인스턴스의 다양한 시점에서의 상태를 저장해 둘 수 있다.
 *
 *          Memento의 유효 기한은?
 *  메모리에만 Memento를 보관해 둘 경우 큰 문제가 없지만 Memento를 파일로 영속적으로 저장한다면 유효 기한이 문제가 된다.
 *  특정 시점의 Memento를 파일로 저장해 놓더라도 그 후 호환이 안되면 문제가 생기기 때문이다(버전이 올라간다거나 하는 문제로)
 *
 *          Caretaker 역할과 Originator 역할을 나누는 이유?
 *  undo가 필요하면 originator 역에 만들면 안될까? Caretaker는 어느 시점에 스냅샷을 찍을지 결정하고 언제 실행 취소를 할지 결정하고, Memento를
 *  저장한다. Originator는 Memento를 만드는 일과 주어진 Memento를 자신의 상태를 되돌리는 일을 한다. 이처럼 둘은 역할을 분담하고 있다.
 *  이렇게 역할을 분담하면 아래와 같이 수정하고 싶을 때에도 Originator를 변경할 필요가 없다.
 *
 *      - 여러 단계의 실행 취소를 할 수 있게 변경하고 싶다.
 *      - 실행 취소뿐만 아니라, 현재 상태를 파일에 저장하고 싶다.
 *
 */