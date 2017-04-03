package bankSystemTest;

import static org.junit.Assert.*;

import org.junit.Test;

import bankSystem.BankAccount;

/** This class handles tests for bank account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class BankAccountTest {
	@Test
	public void create() {
		// Given
		BankAccount account = new BankAccount();
		
		// When
		double expected = 0.0;
		
		// Then		
		assertEquals("No transaction",
				expected, account.balance(), 0.005);
	}
	
	@Test
	public void insert500() {
		// Given
		BankAccount account = new BankAccount();
		
		// When
		double expected = 500.0;
		account.insert(500.0);
		
		// Then	
		assertEquals("Insert 500.",
				expected, account.balance(), 0.005);
	}
	
	@Test
	public void insert1000() {
		// Given
		BankAccount account = new BankAccount();
		
		// When
		double expected = 1000.0;
		account.insert(1000.0);
		
		// Then		
		assertEquals("Insert 1000.",
				expected, account.balance(), 0.005);
	}
	
	@Test
	public void insertMinus1000() {
		// Given
		BankAccount account = new BankAccount();
		
		// When
		double expected = 1000.0;
		account.insert(-1000.0);
		
		// Then	
		assertEquals("Insert -1000.",
				expected, account.balance(), 0.005);
	}
	
	@Test
	public void withdraw500() {
		// Given
		BankAccount account = new BankAccount();
		
		// When
		boolean expected = false;
		
		// Then	
		assertEquals("Withdraw 500.",
				expected, account.withdraw(500.0));
	}
	
	@Test
	public void withdraw1000() {
		// Given
		BankAccount account = new BankAccount();
		
		// When
		boolean expected = false;
		
		// Then
		assertEquals("Withdraw 1000.",
				expected, account.withdraw(1000.0));
	}

	@Test
	public void insert1000withdraw500() {
		// Given
		BankAccount account = new BankAccount();
		
		// When
		double expected = 500.0;
		account.insert(1000.0);
		account.withdraw(500.0);
		
		// Then	
		assertEquals("Insert 1000 and Withdraw 500.",
				expected, account.balance(), 0.005);
	}
	
	@Test
	public void insert1000withdraw500Locked() {
		// Given
		BankAccount account = new BankAccount();
		
		// When
		double expected = 1000.0;
		account.lock();
		account.insert(1000.0);
		account.withdraw(500.0);
		
		// Then	
		assertEquals("Insert 1000 and Withdraw 500.",
				expected, account.balance(), 0.005);
	}
}
