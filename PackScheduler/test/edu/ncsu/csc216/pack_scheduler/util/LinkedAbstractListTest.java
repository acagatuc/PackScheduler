package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * Test cases for the LinkedAbstractList class
 * @author Samantha Ryan
 *
 */
public class LinkedAbstractListTest {

	private LinkedAbstractList<String> l;
	private static final int CAP = 5;
	
	//Test Strings// 
	private String test1 = "The quick brown fox";
	private String test2 = "Jumped over the lazy dog";
	private String test3 = "When Mr. Bilbo Baggins of Bag End announced that he would shortly be ";
	private String test4 = "celebrating his eleventy-first birthday with a party of special magnificence, ";
	private String test5 = "there was much talk and excitment in Hobbiton.";

	private String test6 = "Bilbo was very rich and very peculiar, and had been the wonder of the Shire ";
	private String test7 = "for sixty years, ever since his remarkable disappearance and unexpected return.";
	private String test8 = "They're taking the hobbits to Isengard!";

	private String test9 = null;

	/**
	 * Sets up a LinkedAbstractList object for the purposes of testing
	 */
	@Before
	public void setup() {
		l = new LinkedAbstractList<String>(CAP);
	}
	 
	/**
	 * Tests the size() method for the LinkedAbstractList class
	 */
	@Test
	public void testSize() {
		assertEquals(0, l.size());
	}

	/**
	 * Tests the constructor method of the LinkedAbstractList class
	 */
	@Test
	public void testLinkedAbstractList() {
		LinkedAbstractList<String> ll = new LinkedAbstractList<String>(CAP);
		assertEquals(0, ll.size());

	}

	/**
	 * Tests the setCapacity() method of the LinkedAbstractList
	 */
	@Test
	public void testSetCapacity() {
		l.add(0, test1);
		l.add(1, test2);
		l.add(2, test3);
		l.add(3, test4);
		l.add(4, test5);
		
		try {
			l.add(5, test6);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(5, l.size());
		}
		
		try {
			l.setCapacity(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(5, l.size());
		}
		
		try {
			l.setCapacity(3);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(5, l.size());
		}
		l.setCapacity(6);
		l.add(5, test6);
		assertEquals(6, l.size());

	}

	/**
	 * Tests the get() method of the LinkedAbstractList class
	 */
	@Test
	public void testGetInt() {
		l.add(0, test1);
		l.add(1, test2);
		l.add(2, test3);
		l.add(3, test4);
		l.add(4, test5);

		assertEquals(test1, l.get(0));
		assertEquals(test2, l.get(1));
		assertEquals(test3, l.get(2));
		assertEquals(test4, l.get(3));
		assertEquals(test5, l.get(4));

	}

	/**
	 * Tests the set() method of the LinkedAbstractList class
	 */
	@Test
	public void testSetIntE() {
		l.add(0, test1);
		l.add(1, test2);
		l.add(2, test3);
		l.add(3, test4);
		l.add(4, test5);

		l.set(0, test6);
		assertEquals(test6, l.get(0));
		assertEquals(test2, l.get(1));
		assertEquals(test3, l.get(2));
		assertEquals(test4, l.get(3));
		assertEquals(test5, l.get(4));
		assertEquals(5, l.size());

		l.set(2, test7);
		assertEquals(test7, l.get(2));

		assertEquals(5, l.size());

		l.set(4, test8);
		assertEquals(test8, l.get(4));
		assertEquals(test7, l.get(2));
		assertEquals(test6, l.get(0));
		assertEquals(test2, l.get(1));
		assertEquals(test4, l.get(3));

		assertEquals(5, l.size());

	}

	/**
	 * Tests the add() method of the LinkedAbstractList class
	 */
	@Test
	public void testAddIntE() {
		l.add(0, test1);
		l.add(1, test2);
		assertEquals(2, l.size());
		assertEquals(test1, l.get(0));

		try {
			l.add(5, test3);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(2, l.size());
		}

		try {
			l.add(2, test1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(2, l.size());
		}

		// Test adding a null element
		try {
			l.add(2, test9);
			fail(); 
		} catch (NullPointerException e) {
			assertEquals(2, l.size());
		}
		// Test going beyond the capacity
		l.add(2, test3);
		assertEquals(3, l.size());
		l.add(3, test4); 
		assertEquals(4, l.size());
		l.add(4, test5); 

		try {
			l.add(5, test6);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(5, l.size());
		}

	}

	
	/**
	 * Tests the remove() method of the LinkedAbstractList class
	 */
	@Test
	public void testRemoveInt() {
		l.add(0, test1);
		l.add(1, test2);
		l.add(2, test3);
		l.add(3, test4);
		l.add(4, test5);

		l.remove(2);
		assertEquals(test4, l.get(2));

		l.remove(0);
		assertEquals(test2, l.get(0));

		l.remove(2);
		assertEquals(2, l.size());
	}
	

		
		
	

}
