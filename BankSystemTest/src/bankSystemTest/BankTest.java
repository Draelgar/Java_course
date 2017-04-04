package bankSystemTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bankSystem.Bank;

/** A class for testing the Bank class.
 * @author Gustaf Peter Hultgren 
 * @version 1.0 **/
public class BankTest {
	private Bank mBank;
	
	@Before
	public void setup() {
		mBank = new Bank();
		// Add some customers.
		mBank.addCustomer("001", "001");
		mBank.addCustomer("002", "001");
		mBank.addCustomer("003", "001");
		
		// Create additional bank accounts.
		mBank.addBankAccount("001", "002");
		mBank.addBankAccount("001", "003");		
		mBank.addBankAccount("002", "002");
		mBank.addBankAccount("002", "003");		
		mBank.addBankAccount("003", "002");
		mBank.addBankAccount("003", "003");
		
		// Insert 500kr to all 002 bank accounts.
		mBank.insert("001", "002", 500.0);
		mBank.insert("002", "002", 500.0);
		mBank.insert("003", "002", 500.0);
		
		// Insert 500kr to all 003 bank accounts.
		mBank.insert("001", "003", 1000.0);
		mBank.insert("002", "003", 1000.0);
		mBank.insert("003", "003", 1000.0);
	}
	
	@After
	public void tearDown() {
		mBank = null;
	}
	
	@Test
	public void insert500() {
		assertTrue("Insert 500 to existing bank account.", mBank.insert("001", "001", 500.0));
		assertFalse("Insert money to non-existant bank account.", mBank.insert("001", "004", 500.0));
		
		double expected = 500.0;
		
		assertEquals("Check the resulting bank account balance.", expected, mBank.balance("001", "001"), 0.005);
	}
	
	@Test
	public void insert0() {
		assertTrue("Insert 0 to existing bank account.", mBank.insert("001", "001", 0.0));
		double expected = 0.0;
		
		assertEquals("Check the resulting bank account balance.", expected, mBank.balance("001", "001"), 0.005);
	}
	
	@Test
	public void insertNegative500() {
		assertTrue("Insert -500 to existing bank account.", mBank.insert("001", "001", -500.0));
		double expected = 0.0;
		
		assertEquals("Check the resulting bank account balance.", expected, mBank.balance("001", "001"), 0.005);
	}
	
	@Test
	public void withdraw500() {
		assertTrue("Withdraw 500 from existing bank account.", mBank.withdraw("001", "002", 500.0));
		double expected = 0.0;
		
		assertEquals("Check the resulting bank account balance.", expected, mBank.balance("001", "002"), 0.005);
	}
	
	@Test
	public void withdraw1000() {
		assertFalse("Withdraw 1000 from existing bank account.", mBank.withdraw("001", "002", 1000.0));
		double expected = 500.0;
		
		assertEquals("Check the resulting bank account balance.", expected, mBank.balance("001", "002"), 0.005);
	}
	
	@Test
	public void withdrawNegative500() {
		assertFalse("Withdraw -500 from existing bank account.", mBank.withdraw("001", "002", -500.0));
		double expected = 500.0;
		
		assertEquals("Check the resulting bank account balance.", expected, mBank.balance("001", "002"), 0.005);
	}
	
	@Test
	public void withdrawFromLockedAccount() {
		assertTrue(mBank.lock("002", "003"));
		assertFalse("Withdraw 500 from locked account.", mBank.withdraw("002", "003", 500.0));
		assertTrue(mBank.unlock("002", "003"));
		assertTrue("Withdraw 500 from locked account.", mBank.withdraw("002", "003", 500.0));
	}
	
	@Test
	public void transferBetweenTwoAccountsOnSameCustomer() {
		assertTrue("Transfer between accounts.", mBank.transfer("001", "003", "001", "001", 500.0));
		double expected = 500.0;
		
		assertEquals("Check source balance.", expected, mBank.balance("001", "003"), 0.005);
		assertEquals("Check source balance.", expected, mBank.balance("001", "001"), 0.005);
	}
	
	@Test
	public void transferBetweenTwoCustomers() {
		assertTrue("Transfer betweein customers.", mBank.transfer("003", "003", "001", "001", 500.0));
		double expected = 500.0;
		
		assertEquals("Check source balance.", expected, mBank.balance("003", "003"), 0.005);
		assertEquals("Check source balance.", expected, mBank.balance("001", "001"), 0.005);
	}
}
