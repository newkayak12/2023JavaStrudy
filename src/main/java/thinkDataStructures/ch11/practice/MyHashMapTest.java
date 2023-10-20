/**
 * 
 */
package thinkDataStructures.ch11.practice;

import org.junit.Before;
import thinkDataStructures.ch07_09.practice.ch09.MyLinearMapTest;

/**
 * @author downey
 *
 */
public class MyHashMapTest extends MyLinearMapTest {

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		map = new MyHashMap<String, Integer>();
		map.put("One", 1);
		map.put("Two", 2);
		map.put("Three", 3);
		map.put(null, 0);
	}
}
