package bankSystemTest;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bankSystem.CustomerAccount;

/** This class handles tests for customer account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class CustomerAccountTest {
	private CustomerAccount mCustomerAccount = null;
	private String mKeys[] = {"5332-4,5346735252635-4", "5332-4,534646575435-4"};
	
	@Before
	public void prepare() {
		mCustomerAccount = new CustomerAccount(mKeys[0]);
		mCustomerAccount.addBankAccount(mKeys[1]);
	}
	
	@After
	public void cleanup() {
		mCustomerAccount = null;
	}
	
	@Test
	public void transferToNonExistantAccount() {		
		// When
		boolean expected = false;
		
		//Then
		assertEquals("Attempt to transfer to a non-existing account.",
				expected, mCustomerAccount.transfer(mKeys[0], "5332-4,53464384535-4", 500.0));
	}
	
	@Test
	public void transferFromEmptyAccount() {		
		// When
		boolean expected = false;
		
		// Then
		assertEquals("Attempt to transfer a sum of money from an empty account.",
				expected, mCustomerAccount.transfer(mKeys[0], mKeys[1], 500.0));
	}
	
	@Test
	public void transferFromFilledAccount() {
		// Given
		mCustomerAccount.insertToBankAccount(mKeys[0], 1000.0);
		
		// When
		boolean expected = true;
		
		// Then
		assertEquals("Attempt to transfer a sum of money from an account.",
				expected, mCustomerAccount.transfer(mKeys[0], mKeys[1], 500.0));
	}
	
	@Test
	public void insertToBankAccount() {
		double expected = 1000.0;
		assertTrue("Insert 1000 to bank account.", mCustomerAccount.insertToBankAccount(mKeys[0], 1000.0));
		assertEquals("Check account balance.", expected, mCustomerAccount.getBankAccountBalance(mKeys[0]), 0.005);
	}
	
	@Test
	public void withdrawFromBankAccount() {
		double expected = 500.0;
		assertTrue("Insert 1000 to bank account.", mCustomerAccount.insertToBankAccount(mKeys[0], 1000.0));
		assertTrue("Withdraw 500 from bank account.", mCustomerAccount.withdrawFromBankAccount(mKeys[0], 500.0));
		assertEquals("Check account balance.", expected, mCustomerAccount.getBankAccountBalance(mKeys[0]), 0.005);
	}
	
	@Test
	public void getAllAcountBalances() {
		double expected[] = {1000.0, 435.0};
		mCustomerAccount.insertToBankAccount(mKeys[0], 1000.0);
		mCustomerAccount.insertToBankAccount(mKeys[1], 435.0);
		
		Map<String, Double> accountBalances = mCustomerAccount.getAllBankAccountBalance();
		assertEquals("Check map size.", 2, accountBalances.size());
		assertTrue("Check for the first key", accountBalances.containsKey(mKeys[0]));
		assertTrue("Check for the second key", accountBalances.containsKey(mKeys[1]));
		assertEquals("Check value for the first key.", expected[0], accountBalances.get(mKeys[0]), 0.005);
		assertEquals("Check value for the second key.", expected[1], accountBalances.get(mKeys[1]), 0.005);
	}

}
