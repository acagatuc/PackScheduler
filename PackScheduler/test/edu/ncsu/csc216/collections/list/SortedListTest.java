package edu.ncsu.csc216.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testing suite for SortedList and it's methods
 * @author Samantha Ryan
 *
 */
public class SortedListTest {

	/**
	 * Tests that the SortedList Object is constructed correctly
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));

		list.add("apples");
		list.add("bananas");
		list.add("carrots");
		list.add("dates");
		list.add("eggplants");
		list.add("figs");
		list.add("grapefruit");
		list.add("honeydew");
		list.add("iceberg lettuce");
		list.add("java bean");

		// Test list with full capacity (10 elements)
		assertEquals(10, list.size());

		assertEquals("apples", list.get(0));
		assertEquals("bananas", list.get(1));
		assertEquals("carrots", list.get(2));
		assertEquals("dates", list.get(3));
		assertEquals("eggplants", list.get(4));
		assertEquals("figs", list.get(5));
		assertEquals("grapefruit", list.get(6));
		assertEquals("honeydew", list.get(7));
		assertEquals("iceberg lettuce", list.get(8));
		assertEquals("java bean", list.get(9));

		// Test list with expanded capacity (11 elements)
		list.add("kiwi");
		assertEquals(11, list.size());
		assertEquals("kiwi", list.get(10));

	}

	/**
	 * Test adding elements to the list
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();

		// Add initial element
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		// Add element at the start of the list
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));

		try {
			list.add(null);
		} catch (NullPointerException e) {
			assertEquals(2, list.size());
		}

		try {
			list.add("apple");
		} catch (IllegalArgumentException e) {
			assertEquals(2, list.size());
		}
	}

	/**
	 * Tests get(index) method's error and boundary list
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();

		// Since get() is used throughout the tests to check the
		// contents of the list, we don't need to test main flow functionality
		// here. Instead this test method should focus on the error
		// and boundary cases.

		// Test getting an element from an empty list
		try {
			list.get(0);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}

		list.add("apple");
		list.add("banana");
		list.add("carrot");

		// Test getting an element at an index < 0
		try {
			list.get(-1);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, list.size());
			assertEquals("apple", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("carrot", list.get(2));
		}

		// Test getting an element at size
		try {
			list.get(list.size());
		} catch (IndexOutOfBoundsException e) {
			assertEquals(3, list.size());
			assertEquals("apple", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("carrot", list.get(2));
		}

	}

	/**
	 * Tests that remove() removes the appropriate element and moves the
	 * remaining elements into the correct position
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();

		// Test removing from an empty list
		try {
			list.remove(0);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}

		list.add("apple");
		list.add("banana");
		list.add("carrot");
		list.add("date");
		list.add("eggplant");

		// Test removing an element at an index < 0

		try {
			list.remove(-2);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(5, list.size());
			assertEquals("apple", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("carrot", list.get(2));
			assertEquals("date", list.get(3));
			assertEquals("eggplant", list.get(4));
		}

		// Test removing an element at size
		try {
			list.remove(5);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(5, list.size());
			assertEquals("apple", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("carrot", list.get(2));
			assertEquals("date", list.get(3));
			assertEquals("eggplant", list.get(4));
		}

		// Test removing a middle element
		try {
			list.remove(2);
			assertEquals("apple", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("date", list.get(2));
			assertEquals("eggplant", list.get(3));
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		// Test removing the last element
		try {
			list.remove(3);
			assertEquals(3, list.size());
			assertEquals("apple", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("date", list.get(2));
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		// Test removing the first element
		try {
			list.remove(0);
			assertEquals("banana", list.get(0));
			assertEquals("date", list.get(1));
			assertEquals(2, list.size());
		} catch (IndexOutOfBoundsException e) {
			fail();
		}

		// Test removing the last element
		try {
			list.remove(1);
			assertEquals(1, list.size());
			assertEquals("banana", list.get(0));
		} catch (IndexOutOfBoundsException e) {
			fail();
		}
	}

	/**
	 * Test that indexOf(element) method returns the correct index of the
	 * passed element
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();

		// Test indexOf on an empty list
		assertEquals(-1, list.indexOf("apple"));

		list.add("apple");
		list.add("banana");
		list.add("carrot");
		list.add("date");
		list.add("eggplant");

		// Test various calls to indexOf for elements in the list
		// and not in the list

		assertEquals(0, list.indexOf("apple"));
		assertEquals(-1, list.indexOf("corn"));
		assertEquals(4, list.indexOf("eggplant"));

		// Test checking the index of null
		try {
			list.indexOf(null);
		} catch (NullPointerException e) {
			assertEquals(5, list.size());
		}

	}

	/**
	 * Test that the method clears when .clear() is called
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		list.add("apple");
		list.add("banana");
		list.add("carrot");
		list.add("date");
		list.add("eggplant");

		assertEquals(5, list.size());

		list.clear();

		assertEquals(0, list.size());
	}

	/**
	 * Tests that an empty sorted list is empty, and that a non-empty list is
	 * not empty
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();

		// Test that the list starts empty
		assertTrue(list.isEmpty());

		list.add("apple");
		list.add("banana");
		list.add("carrot");
		list.add("date");
		list.add("eggplant");

		// Check that the list is no longer empty

		assertFalse(list.isEmpty());
	}

	/**
	 * Tests that .contains() returns true if the SortedList contains an
	 * element, and false if it does not
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();

		// Test the empty list case
		assertFalse(list.contains("apple"));

		list.add("apple");
		list.add("banana");
		list.add("carrot");
		list.add("date");
		list.add("eggplant");

		// Test some true and false cases

		assertTrue(list.contains("apple"));
		assertTrue(list.contains("carrot"));
		assertTrue(list.contains("eggplant"));
		assertFalse(list.contains("cat"));
		assertFalse(list.contains("dog"));
		assertFalse(list.contains("turtle"));

	}

	/**
	 * Tests whether two compared SortedLists are equal, returning true if they
	 * are, and false if they are not
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		list1.add("apple");
		list1.add("banana");
		list1.add("carrot");
		list1.add("date");
		list1.add("eggplant");

		list2.add("apple");
		list2.add("banana");
		list2.add("carrot");
		list2.add("date");
		list2.add("eggplant");

		list3.add("cat");
		list3.add("dog");
		list3.add("turtle");
		list3.add("fish");
		list3.add("gerbil");

		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));
		assertFalse(list1.equals(list3));
		assertFalse(list3.equals(list2));

		// TODO Test for equality and non-equality
	}

	/**
	 * Tests that the hashCode of identical elements is equal, and the hashCodes
	 * of different elements are different
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		list1.add("apple");
		list1.add("banana");
		list1.add("carrot");
		list1.add("date");
		list1.add("eggplant");

		list2.add("apple");
		list2.add("banana");
		list2.add("carrot");
		list2.add("date");
		list2.add("eggplant");

		list3.add("cat");
		list3.add("dog");
		list3.add("turtle");
		list3.add("fish");
		list3.add("gerbil");

		// Test for the same and different hashCodes

		assertEquals(list1.hashCode(), list2.hashCode());
		assertNotEquals(list1.hashCode(), list3.hashCode());
		assertNotEquals(list3.hashCode(), list2.hashCode());
	}

}
