package algorithm;

import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created on 2023-04-06
 * Project 2023JavaStudy
 */
public class Chapter_11_Hash {
    /**
     *          해시법
     *
     *  해시법은 검색과 더불어 데이터의 추가와 삭제도 효육적으로 수행할 수 있는 방법이다.
     *
     *          정렬된 배열에 새로운 값 추가하기
     *
     *  [ 5, 6, 14, 20, 29, 34, 37, 51, 69, 75, - , - , - ]
     *
     *                          ↓
     *
     *  [ 5, 6, 14, 20, 29, 34, 35, 37, 51, 69, 75, - , - ]
     *
     * 위와 같이 추가하는 과정은 아래와 같다.
     *
     *  1. 삽입할 위치가 a[5], a[6]임을 이진 검색으로 조사
     *  2. 추가한 이후 하나씩 뒤로 밀어내기기
     *
     * 이 방식은 시간 복잡도 O(n)이며 적지 않다.
     *
     *
     *          해시법
     *  해시법은(hashing)은 데이터를 저장할 떄 위치(인덱스)를 간단한 연산으로 구하는 것으로, 검색뿐만 아니라 추가, 삭제도 효율적으로 수행할 수 있다.
     *  아래의 예시처럼  배열의 키 값을 배열의 요소수로 나눈 나머지로 정리하면 아래와 같다. 이렇게 정리한 값을 해시 값이라고 한다.
     *
     *            Key             5   6   14  20  29  34  37  51  69  75
     *   key % array.length       5   6    1  7   3   8   11  12  4   10
     *
     *
     *  해시 값이 인덱스가 되도록 원래 키 값을 저장한 배열이 아래의 해시 테이블이다.
     *    0   1  2   3   4  5  6   7   8  9  10  11  12
     *  [ -, 14, -, 29, 69, 5, 6, 20, 34, -, 75, 37, 51 ]
     *
     *  그러면 35를 추가하는 경우를 생각해보자. 35를 13으로 나눈 나머지는 9이므로 배열의 9쨰 인덱스에 35를 저장한다. 이전에 중간에 삽입하면 모두 옮겼던 것과 다르게
     *  새로운 값을 추가해도 다른 배열 요소를 옮기지 않아도 된다.이렇게 키를 가지고 해시 값을 만드는 과정을 해시 함수(hashFunction)이라고 한다.
     *  위의 예시와 같이 '나머지를 구하는 연산 또는 이런 나머지 연산을 다시 응요한 연산'을 사용한다. 그리고 해시 테이블의 각 요소를 버킷이라고 한다.
     *
     *
     *
     *          충돌(Collision)
     *  이어서 배열에 새로운 값을 18을 추가헤보자. 18을 13으로 나눈 나머지는 5이므로 a[5]에 저장하면 된다. 그러나 이미 버킷(a[5])는 채워져 있다.
     *  이와 같이 키 값과 해시 값의 대응 관계가 반드시 1:1이라는 보증은 없다.
     *  이렇게 저장할 버킷이 중복되는 현상을 출돌이라고 한다.
     *
     *
     *          충돌에 대한 대처
     *   충돌이 발생할 경우 아래 두 가지 방법으로 대처할 수 있다.
     *
     *          1. 체인법: 같은 해시 값을 갖는 요소를 연결 리스트로 관리한다.
     *          2. 오픈 주소법: 빈 버킷을 찾을 때까지 해시를 반복한다.
     *
     *
     *          체인법
     *   체인법은 같은 해시 값을 갖는 데이터를 쇠사슬(chain) 모양으로 연결 리스트에서 연결하는 방법으로, 오픈 해시법이라고도 한다.
     *
     *      같은 해시 값을 갖는 데이터 저장하기
     *   체인법은 같은 해시 값을 갖는 데이터를 연결 리스트에 의해 사슬 모양으로 연결한다. 배열의 각 버킷(해시 테이블)에 저장하는 값은 그 인덱스를
     *   해시 값으로 하는 연결리스트의 첫 번째 노드에 대한 참조이다.
     *
     *    [ -    -                    - - - - ]
     *        14   29  69  5  6  20
     *                 17        33
     *
     *    위와 같이 저장되고, 각 버킷에 저장하는 값은 같은 해시 값을 갖는 노드를 연결한 리스트' 의 첫 번째 참조가 된다.
     *
     *
     *          버킷용 클래스 Node<K,V>
     *    개별 버킷을 나타낸 클래스이다. 세 가지 필드가 있다. key, data, next
     */

    @Test
    void ChainHashTest() {
        class Node<K,V> {
            private K key;
            private V data;
            private Node<K,V> next;

            Node( K key, V data, Node<K,V> next ) {
                this.key = key;
                this.data = data;
                this.next = next;
            }

            K getKey() {
                return this.key;
            }
            V getValue() {
                return this.data;
            }
            public int hashCode(){
                return key.hashCode();
            }
        }
        /**
         * 제네릭 클래스인 Node<K,V>가 전달받는 매개변수의 자료형은 K, V이다.
         */

        class ChainHash<K,V> {
            private int size;
            private Node<K,V>[] table;

            ChainHash( int capacity ){
                try {
                    table = new Node[capacity];
                    this.size = capacity;
                } catch ( OutOfMemoryError e ){
                    this.size = 0;
                }
            }

            public int hashValue( Object key ){
                return key.hashCode() % size;
            }
            public V search( K key ) {
                int hash = hashValue(key);
                Node<K,V> selectedNode = table[hash];

                while( Objects.nonNull(selectedNode) ){
                    if( selectedNode.key.equals(key) ) return selectedNode.getValue(); //해시에 맞는 첫 번째 참조를 찾고
                    selectedNode = selectedNode.next; //만약 일치하지 않는다면 다음 노드 탐색
               }

                return null;
            }
            public int add ( K key, V data ){
                int hash = hashValue(key);
                Node<K, V> selectedNode = table[hash];


                while ( Objects.nonNull(selectedNode) ) {
                    if( selectedNode.getValue().equals(key) ) return 1;  //키 값이 아예 동일하면 이미 있는 값임
                    selectedNode = selectedNode.next; //다음 노드 확인 -> 중복 키 값 있는지 여부를 확인하기 위해서
                }

                Node<K,V> temp = new Node<>(key, data, table[hash]); //중복 노드가 없다면 리스트 맨 앞위치에 노드를 삽입하고
                table[hash] = temp; //버킷에 기억
                return 0;
            }

            public int remove ( K key ){
                int hash = hashValue(key);
                Node<K, V> selectedNode = table[hash];
                Node<K, V> previousNode = null;

                while( Objects.nonNull(selectedNode) ){//버킷에 노드가 있으면

                    if( selectedNode.getKey().equals(key) ){ //키 체크 같으면

                        if( Objects.isNull(previousNode) ){ //이전 노드 있나 살펴봄 없으면 루트
                            table[hash] = selectedNode.next; // 루트노드 다음 노드를 해시 테이블에 넣어서 루트 노드 없앰
                        } else {
                            previousNode.next = selectedNode.next;// 그게 아니면 이전 노드의 다음으로 본인 기준 다음 노드 삽입
                        }
                        return 0;

                    }

                    previousNode = selectedNode;
                    selectedNode = selectedNode.next;
                }
                return 1;
            }

            public void dump() {
                for ( int i = 0; i < size; i++ ){
                    Node<K, V> selectedNode = table[i];
                    System.out.printf("%02d ", i);
                    while( Objects.nonNull(selectedNode) ){
                        System.out.printf("→ %s (%s)", selectedNode.getKey(), selectedNode.getValue());
                        selectedNode = selectedNode.next;
                    }
                    System.out.println();
                }
            }
        }
    }

    /**
     *      오픈 주소법
     *
     * 또 다른 해시법인 오픈 주소법( open addressing )은 충돌이 발생헀을 때 재해시(rehashing)를 수행하여 비어있는 버킷을 찾아내는 방법으로,
     * 닫힌 해시법( closed hashing)이라고도 한다.
     *
     *
     *      요소 삽입
     *     0  1  2  3  4  5  6  7  8 9 10 11 12
     *  [  - 14  - 29  -  5  6  - 34 - 75 37 51 ]
     *                    18 (충돌)
     *
     *  이 경우 빈 버킷이 나올 때까지 해시해서 빈 버킷에 집어 넣는다.
     *  이렇게 재해시는 하면 (18 + 1) % 13의 결과값 6을 받을 수 있고 다시 한 번 재해시 해서 ( 19 + 1 ) % 13의 결과 값 7을 받고 빈 버킷에 안착시킬 수 있다.
     *  오픈 주소법은 빈 버킷을 만날 때까지 재해시(rehashing)을 여러 번 반복하므로 선형 탐사법(linear probing)이라고도 한다.
     *
     *    0  1  2  3  4  5  6  7  8 9 10 11 12
     * [  - 14  - 29  -  5  6 18 34 - 75 37 51 ]
     *
     *
     *
     *          요소 삭제
     *  인덱스가 5인 값을 삭제하는 과정을 예시로 요소 삭제에 대해서 살펴보자. 인덱스가 5인 버킷을 단순히 제거하면 될 것 같지만 그렇지만은 않다.
     *  왜냐하면 같은 해시 값을 갖는 18을 검색할 때 '해시 값이 5인 데이터는 존재하지 않는다.'라고 생각하여 검색에 실패하기 때문이다.
     *
     *  (해싱한 값인 인덱스 5를 지웠을 때 18을 검색하면 해싱했을 경우 5가 삭제됐기 때문에 18이 존재하지 않아 검색이 실패한다는 의미로 보인다.)
     *
     *  그래서 각 버스킷에 대해서 아래의 속성을 부여한다.
     *
     *      1. 데이터 저장 속성 값
     *      2. 비어있음 속성 값 : 같은 해시 값의 데이터가 존재하지 않는다.
     *      3. 삭제 마침 속성 값 : 같은 해시 값의 데이터가 다른 버킷에 저장되어 있다.
     *
     *
     *         요소 검색
     *
     *       0  1  2  3  4  5  6  7  8 9 10 11 12
     *    [  - 14  - 29  -  *  6 18 34 - 75 37 51 ]
     *  17(hash : 4)을 검색했을 때 비어있음 속성 값이므로 검색 실패이다. 18(hash: 5)의 경우 해시 값이 5인 버킷을 보면 삭제마침(*) 이다.
     *  따라서 재해시를 해서 6인 버킷으로 간다. 여기에는 값이 6이 저장되어 있으므로 재해시를 수행해서 7인 버킷을 검색해서 리턴한다.
     */
    enum Status {OCCUPIED, EMPTY, DELETED};
    @Test
    void openHashTest(){
        class OpenHash<K, V> {
            class Bucket<K, V> {
               private K key;
               private V data;
               private Status status;

               Bucket() {
                   status = Status.EMPTY;
               }
               void set(K key, V data, Status status) {
                   this.key = key;
                   this.data = data;
                   this.status = status;
               }

               void setStatus(Status status){
                   this.status = status;
               }

               K getKey(){return this.key;}
               V getData(){return this.data;}
                public int hashCode() {
                    return this.key.hashCode();
                }
            }

            private int size;
            private Bucket<K, V>[] table;

            public OpenHash(int size) {
                try {
                    table = new Bucket[size];
                    for (int i = 0; i < size; i++){
                        table[i] = new Bucket<>();
                    }
                    this.size = size;
                } catch (  OutOfMemoryError e ) {
                    this.size = 0;
                }
            }
            public int hashValue(Object key) {
                return key.hashCode() % size;
            }
            public int rehashValue(int hash) {
                return ( hash + 1 ) % size;
            }
            private Bucket<K, V> searchNode(K key) {
                int hash = hashValue(key); //검색할 데이터의 해시 값
                Bucket<K, V> selectedBucket = table[hash]; //선택한 버킷

                for ( int i = 0; selectedBucket.status != Status.EMPTY && i < size; i++) {
                    if( selectedBucket.status == Status.OCCUPIED && selectedBucket.getKey().equals(key) ) return selectedBucket;

                    hash = rehashValue(hash);
                    selectedBucket = table[hash];
                }
                return null;
            }

            public V search( K key ) {
                Bucket<K, V> selectedBucket  = searchNode(key);
                if( Objects.nonNull(selectedBucket) ) return selectedBucket.getData();
                else return null;
            }

            public int add ( K key, V data ) {
                if( Objects.nonNull(search( key )) ) return 1; //이미 있는 값

                int hash = hashValue( key );
                Bucket<K, V> selectedBucket = table[hash];

                for ( int i = 0; i < size; i ++ ){
                    if( selectedBucket.status == Status.EMPTY || selectedBucket.status == Status.DELETED ) {
                        selectedBucket.set( key, data, Status.OCCUPIED );
                        return 0;
                    }

                    hash = rehashValue( hash );
                    selectedBucket = table[hash];
                }

                return 2; //FULL!
            }

             public int remove( K key ) {
                Bucket<K, V> selectedBucket = searchNode( key );
                if( Objects.isNull( selectedBucket ) ) return 1;

                selectedBucket.setStatus( Status.DELETED );
                return 0;
             }

             public void dump () {
                for ( int i = 0; i < size; i++ ) {
                    System.out.printf("%02d ", i);
                    switch ( table[i].status ) {
                        case OCCUPIED:
                            System.out.printf( "%s (%s) \n", table[i].getKey(), table[i].getData() );
                            break;
                        case EMPTY:
                            System.out.println("NOT REGISTERED");
                            break;
                        case DELETED:
                            System.out.println("DELETED");
                            break;
                    }
                }
             }


        }
    }

}

