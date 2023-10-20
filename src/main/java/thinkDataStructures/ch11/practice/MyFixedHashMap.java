/**
 *
 */
package thinkDataStructures.ch11.practice;

import thinkDataStructures.ch07_09.practice.ch09.MyLinearMap;

import java.util.Map;

/**
 * Implementation of a HashMap using a collection of MyLinearMap and
 * resizing when there are too many entries.
 *
 * @author downey
 * @param <K>
 * @param <V>
 *
 */
public class MyFixedHashMap<K, V> extends MyHashMap<K, V> implements Map<K, V> {

	private int size = 0;

	@Override
	public void clear() {
		super.clear();
		size = 0;
	}

	@Override
	public V put(K key, V value) {
		MyLinearMap<K, V> map = chooseMap(key);
		size -= map.size();
		V oldValue = map.put(key, value);
		size += map.size();

		if (size() > maps.size() * FACTOR) {
			size = 0;
			rehash();
		}
		return oldValue;
	}

	@Override
	public V remove(Object key) {
		MyLinearMap<K, V> map = chooseMap(key);
		size -= map.size();
		V oldValue = map.remove(key);
		size += map.size();
		return oldValue;
	}

	@Override
	public int size() {
		return size;
	}
	//맵을 순회하던 것을 put에 count 해서 상수시간으로 최소화

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyFixedHashMap<String, Integer>();
		for (int i=0; i<10; i++) {
			map.put(new Integer(i).toString(), i);
		}
		Integer value = map.get("3");
		System.out.println(value);
	}
}
