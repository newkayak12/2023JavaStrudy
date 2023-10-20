/**
 * 
 */
package thinkDataStructures.ch03.practice;

import java.util.ArrayList;
import org.junit.Before;
import thinkDataStructures.ch02.practice.MyArrayListTest;


/**
 * @author downey
 *
 */
public class MyLinkedListTest extends MyArrayListTest {

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);

		mylist = new com.allendowney.thinkdast.MyLinkedList<Integer>();
		mylist.addAll(list);
	}
}
