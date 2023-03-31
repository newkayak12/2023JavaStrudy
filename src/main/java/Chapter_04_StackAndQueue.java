import org.junit.jupiter.api.Test;

/**
 * Created on 2023-03-30
 * Project 2023JavaStudy
 */
public class Chapter_04_StackAndQueue {
    /**
     *    -  Stack
     *
     * 스택은 데이터를 일시적으로 저장하기 위한 자료구조로, 가장 나중에 넣은 데이터를 가장 먼저 꺼낸다. 이러한 입출력 순서를 LIFO라고 한다.
     * 스택에 데이터를 넣는 과정을 푸시(push), 스택에서 데이터를 꺼내는 작업을 팝(pop)이라고 한다. 스택에서 푸시와 팝을 하는 위치를 top이라고 하고
     * 가장 아랫부분을 bottom 이라고 한다.
     *
     * Java에서 메소드를 호출하고 실행할 때 프로그램 내부에서는 스택을 사용한다.(callStack)
     *
     * >> Intstack.class
     *
     *    - Queue
     *
     * 큐 역시 일시적으로 데이터를 쌓아 놓는 자료구조이다. 하지만 가장 먼저 넣은 데이터를 먼저 꺼내는 선입선출인 점이 스택과 다르다.
     * 큐에 데이터를 넣는 작업을 `enqueue` 데이터를 꺼내는 작업을 `dequeue`라고 한다. 또한 데이터를 꺼내는 쪽을 front라고 하고
     * 데이터를 넣는 쪽을 rear라고 한다.
     *
     *
     */

    @Test
    void queue() throws Exception {
        IntQueue queue = new IntQueue(4);
        queue.enqueue(3);
        queue.enqueue(5);
        queue.dequeue();
        queue.enqueue(99);
        queue.enqueue(5);
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
    }
    /**
     *     - 링 버퍼로 큐 만들기
     * 배열의 요소를 앞쪽으로 옮기지 않는 자료구조를 링 버퍼(ring buffer)라고 한다. 링 버퍼는 배열의 처음과 끝이 연결되어 있다.
     * 논리적으로 맨 처음 요소는 front, 맨 마지막 요소는 rear가 된다.
     *
     * 링 버퍼는 '오래된 데이터를 버리는' 용도로 사용할 수 있다.
     */
}


class IntStack {
    int max; // 스택의 용량(스택에 쌓을 수 있는 최대 데이터 수)를 나타내는 필드이다.
    int ptr; // 스택 포인터는 스택에 쌓여 있는 데이터 수를 나타내는 필드이다.
    int [] stack;

    public class EmptyIntStackException extends RuntimeException {
        public EmptyIntStackException(){};
    }
    public class OverflowIntStackException extends RuntimeException {
        public OverflowIntStackException(){};
    }

    public IntStack(int capacity) {
        this.ptr = 0;
        this.max = capacity;
        try {
            stack = new int[max];
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }
    public int push(int x) throws OverflowIntStackException {
        if(ptr >= max) throw new OverflowIntStackException();
        return stack[ptr++] = x;
    }
    public int pop() throws EmptyIntStackException {
        if(ptr <= 0) throw new EmptyIntStackException();
        return stack[--ptr];
    }
    public int peek() throws EmptyIntStackException {
        if(ptr <= 0) throw new EmptyIntStackException();
        return stack[ptr-1];
    }
    public int indexOf(int target){
        if(ptr <= 0) throw new EmptyIntStackException();
        for(int i = ptr; i >= 0; i--){
            if(stack[i]== target) return i;
        }
        return -1;
    }

    public void clear() {
        this.stack = new int[this.max];
    }
    public int capacity() {
        return this.stack.length;
    }
    public int size() {
        return this.ptr;
    }
    public boolean isEmpty() {
        if(this.ptr == 0) return true;
        return false;
    }
    public boolean isFull() {
        if(this.ptr == this.stack.length - 1) return true;
        return false;
    }
    public String dump() {
        if(this.ptr == 0) return "EMPTY";
        return this.stack.toString();
    }
}
class IntQueue {
    private int max; //큐 용량
    private int num; //현재 데이터 수
    private int[] que;

    public IntQueue(int capacity){
        this.num = -1;
        this.max = capacity;
        this.que = new int[this.max];
    }

    public int enqueue(int value) throws Exception {
        if(num >= max) throw new Exception();
        this.que[++num] = value;
        return value;
    }
    public int dequeue() throws Exception {
        if(this.num < 0) throw new Exception();
        int result = this.que[num];
        this.que[num--] = 0;
        System.out.println(result);
        return result;
    }

}
class IntRingBuffer {
    private int max;
    private int front;
    private int rear;
    private int num;
    private int[] buffer;

    public IntRingBuffer (int capacity) {

        this.max = capacity;
        this.buffer = new int[max];
        this.front = 0;
        this.rear = 0;
        this.num = 0;
    }
    public int enqueue(int x) throws Exception {
        if(num >= max) throw new Exception();
        buffer[rear++] = x;
        num++;
        if(rear == max) rear = 0;
        return x;
    }
    public int dequeue() throws Exception {
        if(num <= 0) throw new Exception();
        int x = buffer[front++];
        num --;
        if(front == max) front = 0;
        return x;
    }


}
