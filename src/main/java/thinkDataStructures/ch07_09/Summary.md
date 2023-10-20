# 09. Map 인터페이스

자바에서 Map 구현체 중 하나는 해시 테이블에 기반을 두고, 다른 하나는 트리 기반을 두고 있다. 이를 구현하고 성능을 분석해보자.

```java
private Entry findEntry(Object target) {
    seek: for (Entry piece : entries) {
        if( equals( target, piece.getKey() ) )  return piece;
    }
    return null;
}
```
equal은 target의 크기에 영향을 받긴하지만 엔트리 수와는 관계 없다. `equal`은 상수 시간이다.
엔트리 순회는 처음에 찾을 수도 있지만 보통 검색을 진행할 개수는 엔트리 개수에 비례한다.
따라서 선형이다.

```java
@Override
public V put(K key, V value) {
    Entry entry = this.findEntry(key);
    if(Objects.nonNull(entry)) entries.remove(entry);
    entries.add(new Entry(key, value));
    return value;
}

@Override
public V get(Object key) {
    Entry entry = this.findEntry(key);
    if(Objects.nonNull(entry)) return entry.getValue();
    return null;
}

@Override
public V remove(Object key) {
    Entry entry = this.findEntry(key);
    if(Objects.nonNull(entry)) {
    entries.remove(entry);
    return entry.getValue();
    };
    return null;
}
```
put, get, remove 모두 findEntry 후에는 모두 상수 시간이다. 위 구현에서 entries는 ArrayList이기 때문에 맨 끝에 추가하는 것은 상수 시간이다.
put, get은 상수 시간이다. remove는 조금 복잡한데, `entries.remove`가 ArrayList 어디에 있을지 모른다. 결론적으로는 ArrayList의 remove 자체도
선형이기 때문에 두 개의 선형은 선형 연산이 된다. 

엔트리 개수가 작으면 이 구현은 양호하지만, 다른 모든 핵심 메소드가 상수 시간인 Map도 있다.

1. Entry를 하나의 커다란 List 대신 다수의 작은 리스트로 쪼갠다. 각 키에 대해서 해시 코드를 이용해서 어떤 리스트를 사용할지 정한다.
2. 다수의 작은 리스트를 사용하는 것이 더 빠르지만, 증가 차수를 개선하지 못한다. 핵심 연산은 선형이다. 이 경우 리스트 개수를 늘리고 리스트당 요소 개수를 줄이면
상수 시간 맵이 된다. 
