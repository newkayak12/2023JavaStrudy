## 1.1 리스트
자바에서는 ArrayList, LinkedList를 구현한다 이들의 장단점/ 차이점에 대해서 알아보자

## 1.3 List interface
List interface를 구현한다는 것은 add, get, remove 등 20개의 메소드를 포함한 특정 메소드 집합을 제공해야함을 의미한다.
이러한 공통으로 구현해야하만 하는 (강제하는) 부분 덕분에 ArrayList, LinkedList는 상호교환할 수도 있다.

```java
import java.util.LinkedList;

public class ListClientExample {
    private List list;

    public ListClientExample() {
        list = new LinkedList<>();
    }
    
    private List getList() {
        return list;
    }
    
    
    public static void main( String [] args ) {
        ListClientExample lce = new ListClientExample();
        List list = lce.getList();
        System.out.println(list);
    }
}
```
위 예시에서 `new LinkedList()` 부분을 `new ArrayList()`로 바꿔도 큰 문제가 되지 않는다. 이러한 스타일을 인터페이스 프로그래밍이라고 한다.
