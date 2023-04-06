package algorithm;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;

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

    @Test
    void linkedListTest(){
        class LinkedList<E> {
            class Node<E> {
                private E data;
                private Node<E> next;

                Node(E data, Node<E> next){
                    this.data = data;
                    this.next = next;
                }
            }

            private Node<E> head;
            private Node<E> current;

            public LinkedList() {
                this.head = null;
                this.current = null;
            }

            public boolean isEmpty(){
                return Objects.isNull(this.head);
            }
            public boolean isHead(){
                return this.head == this.current;
            }
            public boolean isTail(){
                return Objects.isNull(this.current.next);
            }
        }
    }

    /**
     *
     *      검색
     *  search 메소드는 어떤 조건을 만족하는 노드를 검색한다. 검색에 사용하는 알고리즘은 선형 검색이고 검색한 노드를 만날 때까지 머리 노드부터 스캔한다.
     *  노드의 스캔은 아래의 조건 중 하나만 성립하면 종료된다.
     *
     *     조건 1. 검색 조건을 만족하는 노드를 찾지 못하고 꼬리 노드를 지나가기 직전인 경우
     *     조건 2. 검색 조건을 만족하는 노드를 찾은 경우
     *
     */
    @Test
    void linkedListSearch(){
        class LinkedList<E> {
            class Node<E> {
                private E data;
                private Node<E> next;

                Node(E data, Node<E> next){
                    this.data = data;
                    this.next = next;
                }
            }

            private Node<E> head;
            private Node<E> current;

            public LinkedList() {
                this.head = null;
                this.current = null;
            }

            public boolean isEmpty(){
                return Objects.isNull(this.head);
            }
            public boolean isHead(){
                return this.head == this.current;
            }
            public boolean isTail(){
                return Objects.isNull(this.current.next);
            }
            public E search(E obj, Comparator<? super E> c) {
                Node<E> ptr = head;

                while (ptr != null){
                    if(c.compare(obj, ptr.data) == 0){ //비교할 때 같은 경우...
                        this.current = ptr;
                        return ptr.data;
                    }

                    ptr = ptr.next; //순회
                }
                return null;
            }
            public void addFirst(E obj){
                Node firstNode = new Node<E>(obj, this.head);
                this.head = firstNode;
                this.current = firstNode;
            }
            public void addLast(E obj){
                if(Objects.isNull(head)) this.addFirst(obj);
                else {
                    Node lastNode = new Node<E>(obj, null);
                    Node tail = head;
                    while (Objects.nonNull(tail.next)){
                        tail = tail.next;
                    }
                    tail.next = lastNode;

                }
            }
            public void removeFirst(){
                if(Objects.nonNull(head)){
                    this.head = this.head.next;
                    this.current = this.head;
                }
            }
            public void removeLast(){

                Node tail = head;
                Node previousNode = head;
                while(Objects.nonNull(tail.next)){
                    previousNode = tail;
                    tail = tail.next;
                }
                previousNode.next = null;
                current = previousNode;

            }
        }
    }


    /**
     *
     *
     *          커서로 연결 리스트 만들기
     *  이번에는 각 노드를 배열 안의 요소에 저장하고 그 요소를 이용해서 연결리스트를 구현하는 방법을 알아보자.
     *  위의 연결 리스트는 '노드의 삽입, 삭제를 데이터 이동 없이 수행한다'는 특징이 있었지만 삽입, 삭제를 할 때 노드용 객체를 위한 메모리 영역을
     *  만들고 해제하는 과정이 필요했다. 메모리 영역을 만들고 해제하는 비용은 결코 무시할 정도가 아니다. 이때 프로그램 실행 중에
     *  데이터 수가 크게 바뀌지 ㅇ낳고 데이터 수의 최댓값을 미리 알 수 있다고 하면 배열을 사용해서 효율적으로 연결 리스트를 구현할 수 있다.
     *
     *
     *  배열의 커서에 해당하는 값은 다음 노드에 대한 포인터가 아닌 다음 노드가 들어있는 요소의 인덱스 값이다. 여기서 포인터 역할을 하는
     *  인덱스를 커서(cursor)라고 한다.
     */

    @Test
    void cursorBaseLinkedListTest(){
        class ArrayLinkedList<E> {
            class Node<E> {
                private E data;
                private int next; //리스트의 뒤쪽 포인터
                private int dnext;  //free 리스트의 뒤쪽 포인터(free 리스트 == 빈칸)

                /**
                 * free list 는 동적 메모리 할당을 위해서 계획적으로 사용된 데이터 구조, 빈칸 목록은 메모리의 할당되지 않은 영역들을 연결리스트로 연결시켜서 운용함
                 *
                 *
                 */

                void set(E data, int next) {
                    this.data = data;
                    this.next = next;
                }
            }

            private Node<E>[] n; //리스트
            private int size;  //리스트 용량
            private int max; //사용중인 꼬리 record
            private int head; //머리 노드
            private int current; //선택 노드
            private int deleted; //free 리스트의 머리 노드(죽은 노드)
            private static final int NULL = -1; //다음노드가 없거나 리스트 가득참

            public ArrayLinkedList(int capacity){
                this.head = NULL;
                this.current = NULL;
                this.max = NULL;
                this.deleted = NULL;

                try {
                    this.n = new Node[capacity];
                    for ( int i = 0; i < capacity; i++ ) {
                        this.n[i] = new Node<E>();
                    }
                    this.size = capacity;
                } catch ( OutOfMemoryError e ) {
                    size = 0;
                }
            }

            private int getInsertIndex() {
                if( this.deleted == NULL ) {//삭제할 레코드 없음
                    if(max < size)  return ++max;
                    else return NULL;
                } else {
                    int rec = deleted;
                    deleted = this.n[rec].dnext;
                    return rec;
                }
           }

           private void deleteIndex(int idx) {
                if ( this.deleted == NULL ) { //삭제할 레코드 없음
                    deleted = idx;
                    this.n[idx].dnext = NULL;
                } else {
                    int rec = deleted;
                    deleted = idx;
                    n[idx].dnext = rec;
                }
           }

           public E search(E obj, Comparator< ? super E> c){
                int ptr = head;
                while(ptr != NULL){
                    if(c.compare(obj, n[ptr].data) == 0 ){
                        this.current = ptr;
                        return n[ptr].data;
                    }
                    ptr = n[ptr].next;
                }
                return null;
           }

           public void addFirst( E obj ){
                int ptr = head;
                int rec = getInsertIndex();
                if( rec != NULL ){
                    head = current = rec;
                    n[head].set(obj, ptr);
                }
           }

           public void addLast ( E obj ){
                if(head == NULL) addFirst(obj);
                else {
                    int ptr = head;
                    while(n[ptr].next != NULL){
                        ptr = n[ptr].next;
                    }
                    int rec = getInsertIndex();
                    if( rec != NULL ){
                        n[ptr].next = current = rec;
                        n[rec].set(obj, NULL);
                    }
                }
           }

           public void removeFirst(){
                if(head != NULL){
                    int ptr = n[head].next;
                    deleteIndex(head);
                    head = current = ptr;
                }
           }
           public void removeLast() {
                if(head != NULL){
                    if(n[head].next == NULL) removeFirst();
                    else {
                        int ptr = head;
                        int pre = head;

                        while( n[ptr].next != NULL ){
                            pre = ptr;
                            ptr = n[ptr].next;
                        }

                        n[pre].next = NULL;
                        deleteIndex(ptr);
                        this.current = pre;
                    }
                }
           }

           public void remove( int p ){
                if(head != NULL) {
                    if( p == head) removeFirst();
                    else {
                        int ptr = head;
                        while( n[ptr].next != p ){
                            ptr = n[ptr].next;
                            if(ptr == NULL) return;
                        }
                        n[ptr].next = NULL;
                        deleteIndex(p);
                        n[ptr].next = n[p].next;
                        this.current = ptr;
                    }
                }
           }

           public void removeCurrentNode(){
                remove(current);
           }

           public void clear() {
                while ( head != NULL ){
                    removeFirst();
                }
                current = NULL;
           }

           public  boolean next(){
                if(current == NULL || n[current].next == NULL) return false;
                current = n[current].next;
                return true;
           }

           public void printCurrentNode(){
                if(current == NULL) System.out.println("NO ELEMENT");
                else System.out.println(n[current].data);
           }

           public void dump(){
                int ptr = head;
                while( ptr != NULL ){
                    System.out.println(n[ptr].data);
                    ptr = n[ptr].next;
                }
           }
        }
    }


    /**
     *                      프리 리스트
     * 위 예시에서 삭제한 여러 레코드를 관리하기 위해서 사용하는 것이 그 순서를 넣어두는 연결 리스트인 프리리스트이다. 데이터 자체의 순서를 나타내는
     * 연결리스트와 프리 리스트과 결합되어 있으므로 Node<E>와 연결 리스트 클래스 ArrayLinkedList<E>에는 포인터 버전이 없는 필드가 추가됐다.
     *
     *      > dnext : 프리 리스트의 다음 노드를 가리키는 커서
     *      > deleted: 프리 리스트의 머리 노드를 가리키는 커서
     *      > max : 배열의 가장 꼬리쪽에 있는 노드의 레코드 번호를 의미
     *
     *
     *
     *
     *                      원형 이중 연결 리스트
     *      원형 리스트
     * 연결 리스트의 꼬리 노드가 머리 노드를 가리키면 원형 연결리스트라고 한다. 원형 리스트는 고리 모양으로 나열된 데이터를 저장할 때 알맞는 자료구조이다.
     * 원형 리스트와 연결 리스트의 차이점은 꼬리 노드의 다음 노드를 가리키는 포인터가 null이 아닌 머리 노드의 포인터 값이라는 점이다.
     * 원형 리스트는 연결 리스트에서 사용했던 것과 같은 자료형을 사용할 수 있다.
     *
     *      이중 연결 리스트
     * 연결 리스트의 가장 큰 단점은 뒤로 순회는 쉽지만 역순회는 할 수 없다는 점이다. 이러한 단점한 자료구조가 이중 연결 리스트(doubly linked list)이다.
     * 각 노드에 앞, 뒤 포인터를 모두 지니고 있다.
     *
     *                          class Node<E> {
     *                              E data;
     *                              Node<E> prev;
     *                              Node<E> next;
     *                          }
     *
     * 와 같은 구조를 사용한다.
     *
     *      원형 이중 연결 리스트
     * 앞의 두 가지를 모두 가지고 있는 것이다.
     */
    @Test
    void dblLinkedListTest(){
        class CircularDoublyLinkedList<E>{
            class Node<E> {
                private E data;
                private Node<E> prev;
                private Node<E> next;
                Node(){
                    data = null;
                    prev = next = this;
                }
                Node(E obj, Node<E> prev, Node<E> next){
                    data = obj;
                    this.prev = prev;
                    this.next = next;
                }
            }

            private Node<E> head;
            private Node<E> current;


            public CircularDoublyLinkedList() {
                head = current = new Node<E>();
            }

            public boolean isEmpty() {
                return head.next == head;
            }

            public E search(E obj, Comparator< ? super E > c){
                Node<E> ptr = head.next;

                while(ptr != head){
                    if( c.compare(obj, ptr.data) == 0) {
                        current = ptr;
                        return ptr.data;
                    }

                    ptr = ptr.next;
                }
                return null;
            }
        }
    }
    /**
     *          노드 클래스 Node<E>
     * 노드 클래스 Node<E>에는 앞 페이지에서 설명한 세 필드 data, prev, next 외에 2 개의 생성자가 더 있다
     *
     *      1. Node() : 데이터가 없고 앞 뒤 포인터가 모두 본인인 빈 생성자이다.
     *      2. Node(E obj, Node<E> prev, Node<E> next) : prev, next 가 있는 노드 생성자
     *
     *
     *
     *      리스트가 비어있는가를 조사하는 isEmpty
     * 리스트가 비어있는가를 조사할 떄 주의할 점은 head.next가 비어있는가를 조사하면된다. 원형이중연결리스트는 head.next가 리스트의 시작점이 된다.
     * head는 더미에 가깝다.
     *
     *      search 메소드
     * 역시 같은 이슈다. 원형 이중 연결리스트는 head가 더미이기 때문에 head를 포함해서 찾아봐야 의미가 없다. 따라서 head.next에서 찾기 시작하자.
     * 또한 주의할 점은 목적노드를 찾지 못하고 스캔이 한 바퀴 돌아서 다시 머리로 돌아왔을 때 while을 끝내야 한다.
     *
     */

}

