package calculatorTest;

import static org.junit.Assert.*;

import org.junit.Test;

import calculator.Calculator;

public class CalculatorTest {

	@Test
	public void addGiven1And1Gives2() {
		// Given
		Calculator c = new Calculator();
		
		// When
		double expected = 2.0;
		
		// Then
		assertEquals("Addition of 1 and 1 should be 2",
				expected, c.add(1.0,  1.0), 0.005);
	}
	
	@Test
	public void addGiven1And2Gives3() {
		// Given
		Calculator c = new Calculator();
		
		// When
		double expected = 3.0;
		
		// Then
		assertEquals("Addition of 1 and 2 should be 3",
				expected, c.add(1.0,  2.0), 0.005);
	}
	
	@Test
	public void subtractGiven1And1Gives0() {
		// Given
		Calculator c = new Calculator();
		
		// When
		double expected = 0.0;
		
		// Then
		assertEquals("Subtraction of 1 and 1 should be 2",
				expected, c.sub(1.0,  1.0), 0.005);
	}
	
	@Test
	public void subtractGiven2And1Gives1() {
		// Given
		Calculator c = new Calculator();
		
		// When
		double expected = 1.0;
		
		// Then
		assertEquals("Subtraction of 1 and 2 should be 3",
				expected, c.sub(2.0,  1.0), 0.005);
	}
	
	@Test
	public void multiplyGiven1And1Gives1() {
		// Given
		Calculator c = new Calculator();
		
		// When
		double expected = 1.0;
		
		// Then
		assertEquals("Multiplication of 1 and 1 should be 1",
				expected, c.mult(1.0,  1.0), 0.005);
	}
	
	@Test
	public void multiplyGiven1And2Gives2() {
		// Given
		Calculator c = new Calculator();
		
		// When
		double expected = 2.0;
		
		// Then
		assertEquals("Multiplication of 1 and 2 should be 2",
				expected, c.mult(1.0,  2.0), 0.005);
	}
	
	@Test
	public void multiplyGiven1And2Gives3() {
		// Given
		Calculator c = new Calculator();
		
		// When
		double expected = 6.0;
		
		// Then
		assertEquals("Multiplication of 2 and 3 should be 6",
				expected, c.mult(2.0,  3.0), 0.005);
	}
	
	@Test
	public void divideGiven6By2Gives3() {
		// Given
		Calculator c = new Calculator();
		
		// When
		double expected = 3.0;
		
		// Then
		assertEquals("Division of 6 by 2 should be 3",
				expected, c.div(6.0,  2.0), 0.005);
	}
	
	@Test
	public void divideGiven3By2Gives1_5() {
		// Given
		Calculator c = new Calculator();
		
		// When
		double expected = 1.5;
		
		// Then
		assertEquals("Division of 3 by 2 should be 1.5",
				expected, c.div(3.0,  2.0), 0.005);
	}
	
	@Test
	public void divideGiven2By1Gives2() {
		// Given
		Calculator c = new Calculator();
		
		// When
		double expected = 2.0;
		
		// Then
		assertEquals("Division of 2 by 1 should be 2.0",
				expected, c.div(2.0,  1.0), 0.005);
	}
}
