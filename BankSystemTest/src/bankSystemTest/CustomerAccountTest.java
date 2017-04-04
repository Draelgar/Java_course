package bankSystemTest;

import static org.junit.Assert.*;

import org.junit.Test;

import bankSystem.CustomerAccount;

/** This class handles tests for customer account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class CustomerAccountTest {

	@Test
	public void transferNonExistantAccount() {
		// Given
		CustomerAccount ca = new CustomerAccount("5332-4,5346735252635-4");
		
		// When
		boolean expected = false;
		
		//Then
		assertEquals("Attempt to transfer to a non-existing account.",
				expected, ca.transfer("5332-4,5346735252635-4", "5332-4,53464384535-4", 500.0));
	}
	
	@Test
	public void transferFromEmptyAccount() {
		// Given
		CustomerAccount ca = new CustomerAccount("5332-4,5346735252635-4");
		ca.addBankAccount("5332-4,534646575435-4");
		
		// When
		boolean expected = false;
		
		// Then
		assertEquals("Attempt to transfer a sum of money from an empty account.",
				expected, ca.transfer("5332-4,5346735252635-4", "5332-4,534646575435-4", 500.0));
	}
	
	@Test
	public void transferFromFilledAccount() {
		// Given
		CustomerAccount ca = new CustomerAccount("5332-4,5346735252635-4");
		ca.addBankAccount("5332-4,534646575435-4");
		ca.insertToBankAccount("5332-4,5346735252635-4", 1000.0);
		
		// When
		boolean expected = true;
		
		// Then
		assertEquals("Attempt to transfer a sum of money from an account.",
				expected, ca.transfer("5332-4,5346735252635-4", "5332-4,534646575435-4", 500.0));
	}

}
