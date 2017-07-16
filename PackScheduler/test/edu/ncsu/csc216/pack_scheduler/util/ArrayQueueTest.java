package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the ArrayQueue class methods
 * @author Samantha Ryan
 *
 */
public class ArrayQueueTest {
	
	private ArrayQueue<String> queue;
	private String s1 = "Test string 1";
	private String s2 = "Test string 2";
	private String s3 = "Test string 3";
	private String s4 = "Test string 4";
	private String s5 = "Test string 5";
	private String s6 = "Test string 6";
	private int initCapacity = 5;

	/**
	 * Sets up the ArrayQueue object for testing 
	 * @throws Exception if ArrayQueue test object cannot be created
	 */
	@Before
	public void setUp() throws Exception {
		queue = new ArrayQueue<String>(initCapacity);
	}

	/**
	 * Tests the enqueue method
	 */
	@Test
	public void testEnqueue() {
		assertEquals(queue.size(), 0);
		try {
			queue.enqueue(s1);
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Tests the dequeue method
	 */
	@Test
	public void testDequeue() {
		assertEquals(queue.size(), 0);
		try {
			queue.dequeue();
			fail();
		} catch (NoSuchElementException e) {
			assertEquals(queue.size(), 0);
		}
		queue.enqueue(s1);
		queue.enqueue(s2);
		queue.enqueue(s3);
		queue.enqueue(s4);
		queue.enqueue(s5);
		assertEquals(queue.dequeue(), s1);
		assertEquals(queue.dequeue(), s2);
		
	}

	/**
	 * Tests the isEmpty method
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(queue.isEmpty());
		queue.enqueue(s1);
		assertFalse(queue.isEmpty());
	}

	/**
	 * Tests the size method
	 */
	@Test
	public void testSize() {
		assertEquals(queue.size(), 0);
		queue.enqueue(s1);
		queue.enqueue(s2);
		queue.enqueue(s3);
		assertEquals(queue.size(), 3);
	}

	/**
	 * Tests the setCapacity method
	 */
	@Test
	public void testSetCapacity() {
		queue.enqueue(s1);
		queue.enqueue(s2);
		queue.enqueue(s3);
		queue.enqueue(s4);
		queue.enqueue(s5);
		
		try {
			queue.enqueue(s6);
			
		} catch (IllegalArgumentException e) {
			assertEquals(queue.size(), 5);
		}
		
		queue.setCapacity(6);
		
		try {
			queue.enqueue(s6);
		} catch (Exception e) {
			//DO SOMETHING
		}
	}

}
