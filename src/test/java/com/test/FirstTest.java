package com.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

public class FirstTest {

	@Test
	public void trueTest() {
		assertTrue("Passed test", true);
	}

	@Ignore
	@Test
	public void falseTest() {
		assertFalse("Failed test", true);
	}

	@Test
	public void emptyTest() {
		System.out.println("Empty test");
	}

	@Ignore
	@Test
	public void disabledTest() {
		System.out.println("Ignored test");
	}
}
