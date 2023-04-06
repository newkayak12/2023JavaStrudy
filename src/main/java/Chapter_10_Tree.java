import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Objects;

/**
 * Created on 2023-04-05
 * Project 2023JavaStudy
 */
public class Chapter_10_Tree {
    /**
     *          트리
     * 트리는 데이터 사이의 계층 관계를 나타내는 자료구조이다.
     *
     *
     *                                    Root -----------------------------Lv 0
     *                        1           leaf           2 -----------------Lv 1
     *                   3       leaf       --------------------------------Lv 2
     *            leaf leaf leaf            --------------------------------Lv 3
     *
     *
     *  - 루트 : 트리의 가장 윗부분을 루트라고 한다. 하나의 루트에는 하나의 루트가 있다.
     *  - 리프 : 트리의 가장 아랫부분에 위치하는 노드를 리프(leaf)라고 한다. 이때 '가장 아래에 위치한다.'는 말은 물리적으로 맨 아래가 아닌 더 이상
     *         가지가 뻗어나가지 않는다는 의미이다.
     *  - 안쪽 노드 : 루트를 포함하여 리프를 제외한 노드를 안쪽 노드라고 한다.
     *  - 자식 : 어떤 노드로부터 가지로 연결된 아래쪽 노드를 자식이라고 한다. 자식을 여러 개 가질 수 있다.
     *  - 부모 : 어떤 노드에서 위로 연결된 노드를 부모라고 한다. 노드는 1개의 부모를 가진다. 루트만은 예외이다.
     *  - 형제 : 같은 부모를 가지는 노드를 형제라고 한다.
     *  - 조상 : 어떤 노드에서 가지로 연결된 위쪽 노드 모두를 조상이라고 한다.
     *  - 자손 :  어떤 노드에서 가지로 연결된 아래쪽 노드 자손을 모두 자손이라고 한다.
     *  - 레벨 : 루트로부터 얼마나 떨어져 있는가를 레벨이라고 한다. 루트 (레벨 0) 부터 아래로 갈수록 레벨이 1씩 올라간다.
     *  - 차수 : 노드가 갖는 자식의 수를 차수(degree)라고 한다.
     *  - 높이 : 루트로부터 가장 멀리 떨어진 리프까지의 (최대 레벨)을 높이라고 한다.
     *  - 서브 트리 : 트리 안에서 다시 어떤 노드를 루트로 정하고 그 자손으로 이뤄진 트리를 서브트리라고 한다.
     *  - 널 트리 : 노드, 가지가 없는 트리를 널 트리(null tree) 라고 한다.
     *  - 순서 트리와 무순서 트리 : 형제 노드의 순서가 있는지 없는지에 따라 트리를 두 종류로 분류한다. 형제 노드의 순서를 따지면 순서트리(orderedTree), 따지지 않으면 무순서트리(unorderedTree)라고 한다.
     *
     *
     *
     *     (예제 트리)
     *
     *                               A
     *                    B                      C
     *             D           E             F       G
     *          H           I     J       K     L
     *
     *
     *          너비 우선 탐색(breadth-first Search)
     * 너비우선 탐색은 낮은레벨에서 시작해서 왼쪽 -> 오른쪽 방향으로 한 레벨의 검색이 끝나면 다음 레벨로 내려가는 탐색 방법을 의미한다.
     *
     *          순서 : A -> B -> C -> D -> E -> F -> G -> H -> I -> J -> K -> L
     *
     *          깊이 우선 탐색 (depth-first Search)
     * 깊이 우선 탐색은 리프까지 내려가면서 검색하는 것을 우선순위로 하는 탐색 방법이다. 리프에 도달해서 더 이상 검색을 진행할 곳이 없으면
     * 부모로 돌아간다.
     *
     *          순서  A -> B -> D -> H -> I -> E -> J -> C -> F -> K -> L -> G (예시)
     *
     *           깊이 우선 탐색에서 순회 방식
     *
     *  1. 전위 순회 (Preorder)
     *      노드 -> 왼쪽 자식 -> 오른쪽 자식 순으로 순회하는 방법이다. 위 예시 트리를 전위 순회하면 * A -> B -> D -> H -> I -> E -> J -> C -> F -> K -> L -> G가 된다.
     *
     *  2. 중위 순회 (Inorder)
     *      왼쪽자식 -> 노드 -> 오른쪽 자식 순으로 순회하는 방식이다. 위 예시 트리를 중위 순회 하면 * H -> D -> B -> I -> E -> J -> A -> K -> F -> L -> C -> G가 된다.
     *
     *  3. 후휘 순회(Postorder)
     *      왼쪽자식 -> 오른쪽 자식 -> 노드 순으로 순회하는 방식이다. 위 예시 트리를 후휘 순회 하면 * H -> D -> I -> J -> E -> B -> K -> L -> F -> G ->C -> A가 된다.
     *
     *
     *
     *
     *
     *              이진트리와 이진검색
     *   노드가 왼쪽 자식과 오른쪽 자식을 갖는 트리를 이진 트리(binary Tree)라고 한다. 이때 각 노드의 자식은 2명 이하로 유지해야 한다.
     *   또한 이전 트리의 특징은 왼쪽자식, 오른쪽 자식을 구분한다는 것이다.
     *
     *              완전 이진 트리
     *   루트부터 노드가 채워져 있으면서 같은 레벨에서는 왼쪽에서 오른쪽으로 노드가 채워져 있는 이진트리를 완전 이진 트리(complete binary tree)라고 한다.
     *
     *          1. 마지막 레벨을 제외한 레벨은 노드를 가득 채운다.
     *          2. 마지막 레벨은 왼쪽부터 오른쪽 방향으로 노드를 채우되 반드시 끝까지 채울 필요는 없다.
     *
     *
     *            예시)
     *                           0
     *                   1             2
     *              3       4       5      6
     *           7    8   9  10  11
     *
     *    높이가 k 인 완전 이진트리가 가질 수 있는 노드의 최대 개수는 2^(k+1) - 1 개이다. 위 예시는 높이가 3이므로 노드 최대 개수는 15이다.
     *
     *
     *
     *              이진 검색 트리
     *    이진 검색 트리( binary search tree )는 이진 트리가 아래의 조건을 만족하면 된다.
     *
     *    1. 어떤 노드 N을 기준으로 왼쪽 서브 트리 노드의 모든 키 값은 노드 N의 키 값보다 작아야 한다.
     *    2. 오른쪽 서브 트리 노드의 키 값은 노드 N의 키 값보다 커야 한다.
     *    3. 같은 키 값을 갖는 노드는 존재하지 않는다.
     *
     *
     *    예시)
     *                         11
     *
     *             5                     15
     *
     *        4         7           13          18
     *
     *    1        6        9    12    14
     *
     *  위 트리를 보면 왼쪽 서브 트리는 루트보다 작고 오른쪽 서브 트리는 루트보다 크다. 이때 이진 검색 트리를 중위 순회(Inorder)하면 아래와
     *  같이 키 값의 오름차순 노드를 얻을 수 있다.
     *
     *    1 -> 4 -> 5 -> 6 -> 7 -> 9 -> 11 -> 12 -> 13 -> 14 -> 15 -> 18
     *
     *  이와 같이 이진 검색 트리는 중위 순회를 하면 키 값의 오름차순으로 노드를 얻을 수 있다는 점과 구조가 단순하다는 점, 이진 검색과 비슷한 방식으로
     *  검색이 가능하다는 점, 노드의 삽입이 쉽다는 점 등의 특징이 있어 폭넓게 사용한다.
     *
     *
     *
     *              이진 검색 트리 만들기
     *
     *       > key : 키 값
     *       > data : 데이터
     *       > left : 왼쪽 자식 노드
     *       > right: 오른쪽 자식 노드
     *
     *
     *      class Node<K,V> {
     *          K key;
     *          V data;
     *          Node<K,V> left;
     *          Node<K,V> right;
     *      }
     *
     */

    class BinTree<K,V>{
        class Node<K,V> {
            private K key;
            private V data;
            private Node<K,V> left;
            private Node<K,V> right;

            Node(K key, V data, Node<K,V> left, Node<K,V> right){
                this.key = key;
                this.data = data;
                this.left = left;
                this.right = right;
            }

            K getKey() {return key;}
            V getValue() {return data;}
            void print(){
                System.out.println(data);
            }
        }

        private Node<K,V> root;
        private Comparator<? super K> comparator = null;
        BinTree() {
            root = null; //루트에 대한 참조가 없는 이진 검색 트리를 생성하는 생성자자
       }// 이 생성자로 생성한 이진 검색트리에서는 노드 키 값의 대소 관계를 판단할 때 자연 순서에 따라 수행한다.
        // 따라서 키를 나타내는 K의 type이 Comparable을 구현하고 있는 경우가 알맞다.


        BinTree(Comparator< ? super K > c){
            this();
            comparator = c;
        }// 인수로 비교자를 전달 받는 생성자이다. 이 생성자로 생성한 이진 검색트리는 노드의 대소 관계를 판단할 때 전달받은 비교자를 받아서 이를 판단 근거로 구분한다.




        /**
         * 이진 검색 트리에서 원하는 것을 찾으려면 루트부터 시작해서 현재 선택한 노드의 키 값과 목표하는 값을 비교하면서 왼쪽, 오른쪽으로 검색을 진행하면 된다.
         *
         *      1. 루트부터 선택하여 검색을 진행한다. 여기서 선택한 노드를 p라고 하자.
         *      2. p가 null 이면 검색에 실패한다.
         *      3. 검색하는 값 key와 선택한 노드의 p의 값을 비교하여
         *              >  값이 같으면 검색에 성공
         *              >  key가 작으면 선택한 노드에 왼쪽 자식 노드를 대입한다. (왼쪽으로 검색 진행)
         *              >  key가 크면 선택한 노드에 오른쪽 자식 노드를 대입한다. (오른쪽으로 검색 진행)
         *      4. 2번 과정으로 돌아감
         */
        int compare(K key1, K key2){
            if(Objects.isNull(comparator)) return ((Comparable<K>)key1).compareTo(key2);
            else return comparator.compare(key1, key2);
        }

        V search(K key){
            Node<K,V> selectedNode = root;
            while(true){
                if(Objects.isNull(selectedNode)){
                    return null;
                }
                int condition = compare(key, selectedNode.getKey());
                if( condition == 0 ){
                    return selectedNode.getValue();
                } else if ( condition < 0 ) {
                    selectedNode = selectedNode.left;
                } else {
                    selectedNode = selectedNode.right;
                }
            }
        }

        /**
         *  노드를 삽입하는 add 메소드
         *  노드를 삽입할 때 주의해야할 점은 노드를 삽입한 다음에 트리의 형태가 이진 검색 트리의 조건을 유지해야한다는 점이다.
         *  따라서 노드를 삽입할 때는 알맞는 위치에 삽입해야 한다. 이 때 삽입할 노드의 키와 같은 값을 가지는 노드가 있다면
         *  노드를 삽입할 수 없다.
         *
         *         > 1. 루트를 선택한다 여기서 선택하는 노드를 node로 한다.ㅣ
         *         > 2. 삽입할 키 key와 선택 노드 node의 키 값을 비교한다. 값이 같다면 삽입에 실패한다.
         *              > 값이 같지 않은 경우 key 값이 삽입할 값보다 작으면
         *                  - 왼쪽 자식 노드가 없으면 노드를 삽입
         *                  - 왼쪽 자식 노드가 있으면 선택한 노드를 왼쪽 자식 노드로 옮김(포커싱)
         *              > key가 삽입할 값보다 크면
         *                  - 오른쪽 자식노드가 없는 경우에는 노드를 삽입한다.
         *                  - 오른쪽 자식 노드가 있으면 선택한 노드를 오른쪽 자식노드로 옮긴다.(포커싱)
         *          > 3. 2로 되돌아간다.
        */

        private void addNode(Node<K,V> node, K key, V data){
            int condition = compare(key, node.getKey());

            if(condition == 0) return;
            else if (condition < 0){
                if(node.left == null){
                    node.left = new Node<K, V>(key, data, null, null);
                } else {
                    addNode(node.left, key, data);
                }
            } else {
                if(node.right == null){
                    node.right = new Node<K, V>(key, data, null, null);
                } else {
                    addNode(node.right, key, data);
                }
            }
        }

        void add (K key, V data) {
            if( Objects.isNull( root ) ){
                root = new Node<K,V>(key, data, null, null);
            } else {
                addNode(root, key, data);
            }
        }

        /**
         * 노드를 삭제하는 remove 메소드
         * 노드를 삭제하는 과정은 삽입보다 복잡하다. 노드를 삭제할 떄는 세 가지 서로 다른 상황이 놓일 수 있기 때문이다.
         *
         *  1. 자식 노드가 없는 경우 : 부모 노드에서 해당 위치 노드를 null로 업데이트
         *  2. 자식 노드가 1개인 노드를 삭제하는 경우 : 부모 노드의 해당 위치 노드를 삭제하고자 하는 노드의 자식 노드로 재할당
         *  3. 자식 노드가 2 개인 노드를 삭제하는 경우 :
         *          > 1. 삭제할 노드의 오니쪽 서브 트리에서 키 값이 가장 큰 노드를 검색한다.
         *          > 2. 검색한 노드를 삭제 위치로 옮긴다.(검색한 노드의 데이터를 삭제 대상 노드 위치로 복사)
         *          > 3. 옮긴 노드를 삭제한다.
         *                  - 옮긴 노드에 자식이 없으면 : 자식 노드가 없는 노드의 삭제 순서를 따라간다.
         *                  - 옮긴 노드에 자식이 1개 있으면 : 자식 노드가 1개 있는 노드의 삭제 순서를 따라간다.
         */

        boolean remove(K key){
            Node<K, V> selectedNode = root;
            Node<K, V> parents = null;
            boolean isLeftChild = true;

            while( true ){
                if( Objects.isNull(selectedNode) ) return false; //진행할 노드가 더 이상 없다면, 찾을 수 없음
                int condition = compare(key, selectedNode.getKey());//선택한 노드와 삭제하고자 하는 노드의 기 값 비교
                if(condition == 0) break; //찾음
                else {
                    parents = selectedNode;

                    if( condition < 0 ){ //키가 작다면 왼쪽 자식으로
                        isLeftChild = true;
                        selectedNode = selectedNode.left;
                    } else { //키가 크면 오른쪽 자식으로
                        isLeftChild = false;
                        selectedNode = selectedNode.right;
                    }
                }
            }
//SelectedNode에 삭제할 값이 들어 있는 상태


            if ( Objects.isNull( selectedNode.left ) ) { //왼쪽 자식 없음

                if ( selectedNode == root ) root = selectedNode.right; //삭제하고자 하는 노드라 루트 노드이면
                else if ( isLeftChild ) parents.left = selectedNode.right; //선택된 노드가 왼쪽 자식이면 부모의 왼쪽 자식의 내 오른쪽 자식을 대입
                else parents.right = selectedNode.right; //선택된 노드가 오른쪽 자식이면 부모의 오른쪽 자식에 내 오른쪽 자식을 대입

            } else if ( Objects.isNull( selectedNode.right ) ) { //으론쪽 자식이 없음

                if( selectedNode == root ) root = selectedNode.left; //삭제하고자 하는 노드가 루트라면
                else if ( isLeftChild ) parents.left = selectedNode.left; //선택된 노드가 왼쪽 자식이라면
                else parents.right = selectedNode.left;//선택된 노드로 오른쪽 자식이라면

            } else { //왼쪽 자식도 있고 오른쪽 자식도 있는 경우
                /**
                 *
                 *  1. 삭제할 노드의 오니쪽 서브 트리에서 키 값이 가장 큰 노드를 검색한다.
                 *  2. 검색한 노드를 삭제 위치로 옮긴다.(검색한 노드의 데이터를 삭제 대상 노드 위치로 복사)
                 *  3. 옮긴 노드를 삭제한다.
                 */

                parents = selectedNode;    //선택된 노드를 부모로 옮기고
                Node<K,V> left = selectedNode.left; //left는 내 자식을 옮기고
                isLeftChild = true;

                while ( Objects.nonNull(left.right) ) {// 내 왼쪽 자식보다 큰 값이 있으면
                    parents = left; //선택된 노드를 왼쪽 자식으로 바꾸고
                    left = left.right;//왼쪽 자식을 왼쪽의 오른쪽 자식으로 바꾸고
                    isLeftChild = false;
                }

                selectedNode.key = left.key;
                selectedNode.data = left.data;

                if( isLeftChild )  parents.left = left.left;
                else parents.right = left.left;
            }

            return true;
        }

        /**
         * 모든 노드를 출력하는 print(메소드)
         * 모든 노드의 키 값을 오름차순으로 출력하는 메소드이다. 오름차순으로 출력하기 위해서는 중위 순회 방법으로 트리를 검색해야한다.
         *
         */

        private void printSubtree(Node node){
            if ( Objects.nonNull(node) ){
                printSubtree(node.left);
                System.out.println(node.key + " : " + node.data);
                printSubtree(node.right);
            }
        }
        void print() {
            printSubtree(root);
        }
        /**
         * print 메소드는 root 를 매개변수로 해서 printSubTree 메소드를 호출한다.
         */
    }




}
