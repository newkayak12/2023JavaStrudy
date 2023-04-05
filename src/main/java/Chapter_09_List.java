import org.junit.jupiter.api.Test;

/**
 * Created on 2023-04-05
 * Project 2023JavaStudy
 */
public class Chapter_09_List {
    /**
     *          리스트
     * 리스트는 데이터를 순서대로 나열한 자료구조다.
     *
     *
     *          선형 리스트란?
     * 리스트는 데이터를 순서대로 나열해 놓은 자료구조이다. 가장 단순한 구조를 이루고 있는 리스트를 선형 리스트 또는 연결 리스트라고 한다.
     * 리스트의 데이터는 노드(node) 또는 요소(element)라고 한다. 각각의 노드는 데이터와 다음 노드를 가리키는 포인터를 가지고 있다.
     * 처음과 끝에 있는 노드는 특별히 head node, tail node라고 부른다. 또한 하나의 노드에 대해 바로 앞에 있는 노드를 predecessor node,
     * 바로 뒤에 있는 다음 노드를 successor node라고 한다.
     *
     *
     *          배열로 선형 리스트 만들기
     * 전화번호부를 선형리스트로 저장하기 위해서  간단한 배열로 구현해보자.
     */
    @Test
    void basicListTest(){
        class Person {
            int memNo;
            String name;
            String phoneNo;
            public Person(int memNo, String name, String phoneNo){
                this.memNo = memNo;
                this.name = name;
                this.phoneNo = phoneNo;
            }
        }

        Person [] data = {
                new Person(12, "John", "999-999-1234"),
                new Person(33, "Paul", "999-999-1235"),
                new Person(57, "Mike", "999-999-1236"),
                new Person(69, "Rita", "999-999-1237"),
                new Person(41, "Alan", "999-999-1238"),
                new Person(0, "",""),
                new Person(0, "","")
        };

    //이러한 구조에서 데이터를 중간에 삽입하거나, 삭제하면 데이터를 밀고 당기는 등의 동작을 수반하기 때문에 효율이 좋지 않다.
    //또한 초기 할당된 크기만큼 데이터를 적재할 수 있으므로, 쌓이는 데이터의 크기를 미리 알아야 한다.

    }

    /**
     *
     *      포인터로 연결 리스트 만들기
     * 연결 리스트에 데이터를 삽입할 때 노드용 객체를 만들고, 삭제할 때 노드용 객체를 없애면 위의 문제를 해결할 수 있다.
     * 이런 노드를 구현하는 것이 아래의 예시이다. 데이터용 필드인 data와 별도로 자기 자신과 같은 클래스형의 인스턴스를 참조하기 위한 참조용
     * 필드 next를 가진다. 일반적으로 이런 클래스 구조를 자기 참조(self-referential)형이라고 한다.
     *
     *      class Node<E> {
     *          E data;
     *          Node<E> next;
     *      }
     */
}
