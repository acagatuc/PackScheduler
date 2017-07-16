package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the ArrayList Class
 * @author Samantha Ryan
 *
 */
public class ArrayListTest {

	private ArrayList<Object> a;
	String test0 = null;
	String test1 = "This is a test!";
	String test2 = "Hello, World!";
	String test3 = "Let's push things back a bit";
	String test4 = "The quick brown fox";
	String test5 = "Jumped over the lazy dog";	
	String test6 = "Twinkle twinkle";
	String test7 = "Little bat";
	String test8 = "How I wonder";
	String test9 = "Where you're at";
	String test10 = "Up above the world so high";
	String test11 = "Like a teacup in the sky";
	
	/**
	 * Sets up the ArayList object a for testing
	 * @throws Exception if test ArrayList is unable to be constructed
	 */
	@Before
	public void setUp() throws Exception {
		a = new ArrayList<Object>();
	}

	/**
	 * Tests the constructor method of ArrayList
	 */
	@Test
	public void testArrayList() {
		ArrayList<Object> a1 = new ArrayList<Object>();
		assertEquals(0, a1.size());
	}
	

	
	/**
	 * Tests adding elements to the list
	 */
	@Test
	public void testAdd() {
		
		
		try {
			a.add(0, test0);
		} catch (NullPointerException e) {
			assertEquals(0, a.size());
		}
		
		try {
			a.add(-1, test4);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, a.size());
		}
		
		try {
			a.add(9, test4);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, a.size());
		}
		
		a.add(0, test1);
		assertEquals(1, a.size());
		assertEquals("This is a test!", a.get(0));
		 
		 
		a.add(1, test2);
		assertEquals(2, a.size());
		assertEquals("Hello, World!", a.get(1)); 
		
		a.add(0, test3);
		assertEquals(3, a.size());
		assertEquals("Let's push things back a bit", a.get(0));
		assertEquals("This is a test!", a.get(1));
		assertEquals("Hello, World!", a.get(2));
		
		a.add(3, test4);
		a.add(4, test5);
		a.add(5, test6);
		a.add(6, test7);
		a.add(7, test8);
		a.add(8, test9);
		a.add(9, test10);
		a.add(10, test11);
		assertEquals(11, a.size());
		
		
		try {
			a.add(3, test1);
		} catch (IllegalArgumentException e) {
			assertEquals(11, a.size());
		}
		 
	}

	/**
	 * This is hiding the testSet()
	 */
	@Test
	public void testSet() {
	

		
		a.add(0, test1); 
		a.add(1, test2);
		a.add(2, test3);
		a.add(3, test4); 
		a.add(4, test5); 
		
		try {
			a.set(-1, test6);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(5, a.size());
		}
		
		try {
			a.set(5, test6);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(5, a.size());
		}
		
		try {
			a.set(2,  test0);
		} catch (NullPointerException e) {
			assertEquals("Let's push things back a bit", a.get(2));
		}
		
		a.set(2,  test6);
		assertEquals("Twinkle twinkle", a.get(2));
		
	}
	
	/**
	 * tests the remove() method
	 */
	@Test
	public void testRemove() {

		
		a.add(0, test1);
		a.add(1, test2); 
		a.add(2, test3);
		a.add(3, test4); 
		a.add(4, test5); 

		try {
			a.remove(-1);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(5, a.size());
		}
		
		try {
			a.remove(6);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(5, a.size());
		}
		
		a.remove(4);
		 
		assertEquals(4, a.size());
		
		a.remove(0);
		assertEquals("Hello, World!", a.get(0));
		assertEquals(3, a.size());
		
		a.remove(1);
		assertEquals("Hello, World!", a.get(0));
		assertEquals("The quick brown fox", a.get(1));
	}
	
	/**
	 * Tests the get(int) method 
	 */
	@Test
	public void testGet() {

		
		a.add(0, test1);
		a.add(1, test2);
		a.add(2, test3);
		a.add(3, test4); 
		a.add(4, test5); 
		
		try {
			a.get(-1);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(5, a.size());
		}
		
		try {
			a.get(5);
		} catch (IndexOutOfBoundsException e) {
			assertEquals(5, a.size());
		}
		
		assertEquals("This is a test!", a.get(0));
		assertEquals("Hello, World!", a.get(1));
		assertEquals("Let's push things back a bit", a.get(2));
		assertEquals("The quick brown fox", a.get(3));
		assertEquals("Jumped over the lazy dog", a.get(4));
	}

}
