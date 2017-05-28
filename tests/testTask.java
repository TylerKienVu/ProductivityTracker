package tests;


import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.*;

import common.Task;

public class testTask {
	private Task validTask;

	@Before
	public void constructObject() {
		int[] validFrequency = {1,0,1,0,1,0,1};
		validTask = new Task("Study","Study for interview",validFrequency);
		assertNotNull(validTask);
	}
	@Test(expected=InvalidParameterException.class)
	public void testBadFrequencySize(){
		int[] badSizeFrequency = {1,0,1,0,1,1,1,0};
		@SuppressWarnings("unused")
		Task badSizeTask = new Task("Gym","Go to the gym",badSizeFrequency);
	}
	@Test(expected=InvalidParameterException.class)
	public void testBadFrequencyValue(){
		int[] badValueFrequency = {0,1,0,2,1,1,1};
		@SuppressWarnings("unused")
		Task badValueTask = new Task("Homework","Do the homework",badValueFrequency);
	}
	@Test
	public void testGetName(){
		assertEquals(validTask.getName(),"Study");
	}
	@Test
	public void testGetDescription(){
		assertEquals("Study for interview",validTask.getDescription());
	}
	@Test
	public void testGetFrequency(){
		int[] frequency = {1,0,1,0,1,0,1};
		assertArrayEquals(frequency,validTask.getFrequency());
	}
	@Test
	public void testSetName(){
		validTask.setName("Test");
		assertEquals("Test",validTask.getName());
	}
	@Test
	public void testSetDescription(){
		validTask.setDescription("This is a test");
		assertEquals("This is a test",validTask.getDescription());
	}
	@Test
	public void testSetFrequency(){
		int[] test = {0,0,0,0,0,0,0};
		validTask.setFrequency(test);
		assertArrayEquals(test,validTask.getFrequency());
	}
	@Test(expected = InvalidParameterException.class)
	public void testSetBadSizeFrequency(){
		int[] test = {0,0,0,0,0,0,0,0,0};
		validTask.setFrequency(test);
	}
	@Test(expected = InvalidParameterException.class)
	public void testSetBadValueFrequency(){
		int[] test = {1,1,1,1,1,1,2};
		validTask.setFrequency(test);
	}
}
