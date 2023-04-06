package algorithm;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created on 2023-04-04
 * Project 2023JavaStudy
 */
public class Chapter_07_Set {
    void print(int[] a){
        System.out.println(Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(", ")));;
    }
    /**
     *      집합
     *  집합이란 명확한 조건을 만족하는 자료의 모임을 의미한다. 즉, 집합도 자료구조로 표현할 수 있다.
     *
     *      집합과 요소
     *  집합(set)이란 객관적으로 범위를 규정한 '어떤 것'의 모임이며, 그 집합 안에서 각각 "어떤 것"을 요소라고 부른다.
     *
     *      X = {1, 5} or {5, 1}
     *
     *  집합에는 순서가 없다. 또한 집합에 포함되는 요소는 서로 달라야 한다.(중복이 없어야한다.) 또한 집합은 집합을 요소로 가질 수 있다.
     *
     *      a가 집합 X의 요소이면 'a는 X에 포함된다.' 또는 'a는 X에 들어 있다.' 또는 'a는 X에 속한다.'라고 표현할 수 있다. ( X ∋ a or a ∈ X)
     *
     *  두 집합이 같다면 'X와 Y는 서로 같다'고 표현한다.
     *
     *  정수의 집합처럼 요소의 개수가 무한한 집합을 무한집합이라고 하고 이와 달리 요소의 개수가 유한한 집합을 유한 집합이라고 한다.
     *  집합은 꼭 포함되는 요소의 수가 2개 이상이어야만 하는 것은 아니다. 하나일 수도 있고 없을 수도 있다.
     *
     *
     *      부분집합과 진부분집합
     *   부분 집합(subset)은 A = {1, 3}, B = {1, 3, 5}와 같이 집합 A의 모든 요소가 집합 B의 요소이면 A는 B의 부분 집합이고 A는 B에 포함된다고 표현한다. (A ⊂ B or B ⊃ A)
     *   또한 서로 같아도 서로가 서로의 부분집합이라고 볼 수 있다.
     *
     *   진부분집합은 집합 A의 모든 요소가 집합 B의 요소이면서 집합 A와 집합 B가 같지 않을 경우 'A는 B의 진부분집합(proper subset)이라고 한다.
     *
     *
     *      합집합
     *  두 개 이상의 집합의 모든 요소를 가지되, 중복이 없는 결과 값을 합집합이라고 한다.
     *
     *      교집합
     *  두 개 이상의 집합의 공통된 요소만을 모아놓은 집합을 교집합이라고 한다.
     *
     *      차집합
     *  두 개 이상의 집합 사이에서 한 집합의 요소 중 다른 집합과의 교집합을 제외한 나머지를 차집합이라고 한다.
     *
     *
     *
     *              배열로 집합 만들기
     *  모든 요소가 같은 자료형으로 구성된 집합은 배열로 표현할 수 있다. 그런데 배열을 사용하여 집합을 표현하려면 집합의 요소 개수와 배열의 요소 개수는 항상
     *  같아야 한다. 즉, 집합의 상태를 표현할 데이터가 필요하다. 따라서 다음과 같이 집합을 표현하는 배열과 이 별의 정보를 담을 클래스를 함께 사용해야 한다.
     */
    class IntSet {
        int max;  //집합의 최대 개수
        int num = 0;  //집합의 요소 개수
        int[] set; //집합 본체
        public IntSet(int capacity) {
            this.max = capacity;
            this.set = new int[this.max];
        }
        public int capacity() {
            return this.max;
        }
        public int size() {
            return this.num;
        }
        public int indexOf(int value){
            for (int index = 0; index < num; index++){
                if(this.set[index] == value){
                    return index;
                }
            }
            return -1;
        }
        public boolean contains(int value){
            return this.indexOf(value) >= 0;
        }
        public boolean add(int value){
            if(num >= max || contains(value)) return false;
            this.set[this.num++] = value;
            return true;
        }
        public boolean remove(int value){
            int idx;

            if(num <= 0 || (idx = indexOf(value)) < 0){
                return false;
            } else {
                set[idx] = set[--this.num];
                return true;
            }
        }
        public void copyTo(IntSet copyTarget){
            int amount = (copyTarget.max < copyTarget.num) ? copyTarget.max : copyTarget.num;
            for (int i = 0; i < amount; i++){
                copyTarget.set[i] = this.set[i];
            }
        }
        public void copyFrom(IntSet original){
            int amount = (original.num > this.max) ? this.max : original.num;

            for (int i = 0; i < amount; i++){
                this.set[i] = original.set[i];
                this.num = amount;
            }
        }
        public boolean isFull(){
            return this.num == this.max;
        }
        public boolean isEmpty(){
            return this.num == 0;
        }
        public void clear(){
            this.num = 0;
            this.set = new int[this.max];
        }

        public boolean equalTo(IntSet compareTarget) {
            if(this.num != compareTarget.num) return false;

            for (int i = 0; i < this.num; i++){
                int j = 0;
                for (; j < compareTarget.num; j++){
                    if(this.set[j] == compareTarget.set[j]) break;
                }
                if ( j == compareTarget.num) return false; //다 뒤졌는데 없으면 없는거
            }

            return true;
        }

        public void unionOf(IntSet firstSet, IntSet secondSet){
            copyFrom(firstSet);
            for (int i = 0; i < secondSet.num; i++){
                add(secondSet.set[i]);
            }
        }

        public String toString(){
            StringBuilder toStringBuilder = new StringBuilder("{ ");
            for ( int i = 0; i < this.num; i++ ){
                toStringBuilder.append(this.set[i]);
                if( i != this.num - 1 ) toStringBuilder.append(", ");
            }
            toStringBuilder.append(" }");
            return toStringBuilder.toString();
        }

        public boolean add(IntSet others) {
            if(others.isEmpty()) return false;
            for( int i = 0; i < others.num; i++){
                if(!this.contains(others.set[i])) add(others.set[i]);
            }
            return true;
        }



    }


    @Test
    void setTest() {
        IntSet set = new IntSet(4);
        set.add(1);
        set.add(1);
        set.add(2);
        set.add(4);
        set.add(6);
        set.remove(1);
        set.clear();
        System.out.println(set.isEmpty());
        System.out.println(set.toString());
    }


}
