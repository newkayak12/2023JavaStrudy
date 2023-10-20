# 10 Hashing

## 10.1 해싱
```java
public class MyBetterMap<K, V> implements Map<K, V> {
	protected List<MyLinearMap<K, V>> maps;

	public MyBetterMap() { 	makeMaps(2); }

	protected void makeMaps(int k) {
		maps = new ArrayList<MyLinearMap<K, V>>(k);
		for (int i=0; i<k; i++) {
			maps.add(new MyLinearMap<K, V>());
		}
	}
}
```
일단 제공된 예시 코드이다. `maps`는 일전에 구현한 `MyLinearMap`의 컬렉션이다. `makeMaps`는 내장된 맵을 생성하고 생성된 맵을
ArrayList에 저장한다. 

이 클래스를 끌고 온 이유는 어느 내장 맵에 투입할지를 결정하는 방법을 알아보기 위한 것이다. 새로운 키를 추가하면 맵 중 하나를 고르고, 같은 키가 있으면
그 키를 어떤 맵에 넣었는지를 기억해야한다. 

1. 하위 맵을 무작위로 결정하고 각 키를 어느 맵에 넣었는지 무차별적으로 확인하는 방법이다. -> 이 방법은 비효율적이다. 
2. 해시 함수`hash function`을 사용하는 방법이다. 이 함수는 `Object` 객체를 인자로 받아 해시 코드라는 정수를 반환한다. 

2번 방법을 위해서는 같은 Object 객체에 대해서는 같은 값을 반환해야만 한다.

```java
protected MyLinearMap<K, V> chooseMap(Object key) {
    int index = key==null ? 0 : Math.abs(key.hashCode()) % maps.size();
    return maps.get(index);
}
```
자바에서 Object는 hashCode를 제공해서 해시 함수를 계산한다. 위 메소드를 보면 key가 null이면 0인 하위 맵을 선택한다.
null이 아니면 hashCode 메소드로 정수를 얻고 Math.abs 메소드를 호출해서 절대값을 만든다. 그리고 `%`로 결과가 
(0 ~ map.size() - 1) 사이에 있음을 보장한다.

```java
@Override
public V put(K key, V value) {
    MyLinearMap<K, V> map = chooseMap(key);
    return map.put(key, value);
}

@Override
public V get(Object key) {
    MyLinearMap<K, V> map = chooseMap(key);
    return map.get(key);
}
```

chooseMap을 호출해서 하위 맵을 찾고 하위 맵의 메소드를 호출한다. 
n개의 엔트리를 k개의 하위 맵으로 나누면 맵당 엔트리는 평균 n/k개가 된다.  키를 조회할 때 해시 코드 계산이 필요한데,
여기가 시간 할당이 필요한 부분이다. 

위 예시(MyBetterMap.java)가 k개로 나눠서 다뤘기 때문에 k배 빠르므로 검색도 k배 빠르다고 짐작할 수 있겠다. 하지만
실행 시간은 전체 엔트리 수에 비례하므로 결론적으로는 아직 선형이다. 

## 10.2 해싱에 대한 이해

해시 함수의 가장 주요한 요구사항은 같은 객체는 매번 같은 해시 코드를 만들어야 한다는 것이다. 만약 불변 객체(immutable object)
라면 쉽지만, 가변 객체(mutable object)라면 생각해봐야 한다.

일단 equals, hashCode를 오버라이드 한다. 

> equals는 기본적으로 비교하는 객체 간 동일한지 검사하기 위해서
> 사용한다. 기본적으로 equals는 객체 참조하는 것이 동일한지 확인하는 것이며, 동일성 (identity)를 비교한다.
> 즉, 메모리 주소가 같을 경우 동일한 객체가 된다.
> hashCode는 runtime에 객체의 유일한 integer값을 반환한다. Object에는 heap 메모리 주소를 반환하도록 되어 있다.(고 한다.)
> 즉, 동일한 객체는 동일한 메모리 주소를 갖는다는 것을 의미하므로, 동일한 객체는 동일한 해시코드를 가져야 한다.

두 메소드는 일치해야 한다. 두 객체가 같다면, equals가 true라면 hashCode 역시 같아야 한다. 그러나 두 객체의 해시코드가 같다고 꼭 그 둘이 같은 객체일 필요는 없다. 

예시 코드를 보자. 
```java
public class SillyString {
    private final String innerString;

    public SillyString(String innerString) {
        this.innerString = innerString;
    }

    public String toString() {
        return innerString;
    }

    @Override
    public boolean equals(Object other) {
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        int total = 0;
        for (int i = 0; i < innerString.length(); i++) {
            total += innerString.charAt(i);
        }
        System.out.println(total);
        return total;
    }
}
```
위의 경우는 잘 동작한다. 그러나 좋은 성능을 보장하지는 않는다. ac, bb의 유니코드 합계가 같다. 글자에 포함되는 순서만 다르더라도 같은 값을 반환한다.
같은 해시 값을 갖는 여러 값이 생기면, 결국 같은 하위 맵으로 몰리게 된다. 어떤 하위 맵에 다른 맵보다 많은 엔트리가 생기면
k개의 하위 맵으로 인한 성능 향상은 k보다 줄어들게 된다. 
그래서 해시 함수의 목표 중 하나는 균등해야 한다는 것이다. 

## 10.3 해싱과 변형
위의 예시는 `final String`이었기 때문에 객체 변경이 불가하고 결론적으로 항상 같은 해시 코드를 갖게 된다. 아래의 클래스는
가변성을 띤다. 
```java
public class SillyArray {
    private final char[] array;

    public SillyArray(char[] array) {
        this.array = array;
    }

    public String toString() {
        return Arrays.toString(array);
    }

    public void setChar(int i, char c) {
        this.array[i] = c;
    }

    @Override
    public boolean equals(Object other) {
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        int total = 0;
        for (int i = 0; i < array.length; i++) {
            total += array[i];
        }
        System.out.println(total);
        return total;
    }
}
```
`setChar`로 계속 변경할 수 있다. 변형이 일어나면 해시 코드가 달라서 잘못된 값을 조회할 수 있다. 이러면 키가 맵에 있어도 찾을 수 없다. 
이 경우 맵에 포함된 동안 변하지 않을 수 있다고 보장하거나 해당 변화가 해시코드에 영향을 미치지 않을 수 있도록 조정하면 된다. 
