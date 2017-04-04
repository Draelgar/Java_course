package bankSystemTest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bankSystem.BankAccount;
import bankSystem.TransactionInfo;

/** This class handles tests for bank account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class BankAccountTest {
	// Static @BeforeClass & @AfterClass
	// @Test(expected=Exception.class)
	// @Test(timeout=100)
	private BankAccount mAccount = null;
	
	@Before
	public void prepare() {
		mAccount = new BankAccount();
	}
	
	@After
	public void cleanup() {
		mAccount = null;
	}
	
	@Test
	public void create() {		
		// When
		double expected = 0.0;
		
		// Then		
		assertEquals("No transaction",
				expected, mAccount.balance(), 0.005);
	}
	
	@Test
	public void insert500() {
		// When
		double expected = 500.0;
		mAccount.insert(500.0);
		
		// Then	
		assertEquals("Insert 500.",
				expected, mAccount.balance(), 0.005);
	}
	
	@Test
	public void insert1000() {		
		// When
		double expected = 1000.0;
		mAccount.insert(1000.0);
		
		// Then		
		assertEquals("Insert 1000.",
				expected, mAccount.balance(), 0.005);
	}
	
	@Test
	public void insertNegative1000() {
		double expected = 0.0;
		
		assertFalse("Insert -1000.", mAccount.insert(-1000.0));
		assertEquals("Insert -1000 balance.", expected, mAccount.balance(), 0.005);
	}
	
	@Test
	public void withdraw500() {		
		// When
		boolean expected = false;
		
		// Then	
		assertEquals("Withdraw 500.",
				expected, mAccount.withdraw(500.0));
	}
	
	@Test
	public void withdraw1000() {		
		// Then
		assertFalse("Withdraw 1000.",
				mAccount.withdraw(1000.0));
	}
	
	@Test
	public void withdrawNegative1000() {
		double expected = 0.0;
		
		assertFalse("Withdraw -1000", mAccount.withdraw(-1000.0));
		assertEquals("Withdraw -1000 balance", expected, mAccount.balance(), 0.005);
	}

	@Test
	public void insert1000withdraw500() {		
		// When
		double expected = 500.0;
		mAccount.insert(1000.0);
		assertTrue("Withdraw 500 from filled account.", mAccount.withdraw(500.0));
		
		// Then	
		assertEquals("Insert 1000 and Withdraw 500.",
				expected, mAccount.balance(), 0.005);
	}
	
	@Test
	public void insert1000withdraw500Locked() {		
		// When
		double expected = 1000.0;
		mAccount.lock();
		
		assertTrue("Insert 1000.", mAccount.insert(1000.0));
		assertFalse("Withdraw 500 from locked account.", mAccount.withdraw(500.0));

		assertEquals("Insert 1000 and Withdraw 500.",
				expected, mAccount.balance(), 0.005);
	}
	
	@Test
	public void transactionHistory() {
		double expectedChange[] = {500.0, 500.0, 500.0, 500.0, 500.0};
		double expectedResult[] = {500.0, 1000.0, 1500.0, 2000.0, 2500.0};
		int expected = 5;
		
		mAccount.insert(500.0);
		mAccount.insert(500.0);
		mAccount.insert(500.0);
		mAccount.insert(500.0);
		mAccount.insert(500.0);
		
		ArrayList<TransactionInfo> history = mAccount.history();
		
		assertEquals("Check transaction history.", expected, history.size());
		
		for(int i = 0; i < history.size(); i++) {
			assertEquals("Check transaction history change #" + i,
					expectedChange[i], history.get(i).transaction, 0.005);
			assertEquals("Check transaction history change #" + i,
					expectedResult[i], history.get(i).balance, 0.005);
		}
	}
}
