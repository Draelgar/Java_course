package bankSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** This class represents a bank.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class Bank {
	Map<String, CustomerAccount> mCustomerAccounts;
	
	/** Create a new bank. **/
	public Bank() {
		mCustomerAccounts = new HashMap<String, CustomerAccount>();
	}
	
	/** Add a new customer to the bank.
	 * @param customerNumber -The identification number for the customer.
	 * @return True if the account was created, false if the account already existed. **/
	public boolean addCustomer(String customerNumber, String bankAccountName) {
		if(!mCustomerAccounts.containsKey(customerNumber)) {
			mCustomerAccounts.put(customerNumber, new CustomerAccount(bankAccountName));
			return true;
		}
		
		return false;
	}
	
	/** Remove a customer from the bank.
	 * @param customerNumber -The identification number for the customer.
	 * @return True if the account existed and was removed, false if it didn't exist. **/
	public boolean removeCustomer(String customerNumber) {
		if(mCustomerAccounts.containsKey(customerNumber)) {
			mCustomerAccounts.remove(customerNumber);
			return true;
		}
		
		return false;
	}
	
	/** Get the names of all accounts belonging to this customer.
	 * @param customerNumber
	 * @return An array list containing the names in string format. **/
	public ArrayList<String> getAccounts(String customerNumber) {
		if(mCustomerAccounts.containsKey(customerNumber)) {
			return mCustomerAccounts.get(customerNumber).getBankAccounts();
		}
		
		return null;
	}
	
	/** Add a bank account to the specified customer.
	 * @param customerNumber -The ID number for the customer.
	 * @param bankAccountName -The ID name for the bank account.
	 * @return True if successful, false if the bank name was taken or the customer didn't exist. **/
	public boolean addBankAccount(String customerNumber, String bankAccountName) {
		if(mCustomerAccounts.containsKey(customerNumber)) {
			return mCustomerAccounts.get(customerNumber).addBankAccount(bankAccountName);
		}
		
		return false;
	}
	
	/** Insert the specified sum to the selected bank account.
	 * @param customerNumber -The ID number of the customer.
	 * @param bankAccountName -The name of the bank account.
	 * @param sum -The sum to insert.
	 * @return True if successful, false if the bank account or the customer didn't exist. **/
	public boolean insert(String customerNumber, String bankAccountName, double sum) {
		if(mCustomerAccounts.containsKey(customerNumber)) {
			return mCustomerAccounts.get(customerNumber).insertToBankAccount(bankAccountName, sum);
		}
		
		return false;
	}
	
	/** Withdraw the specified sum of money from the selected account.
	* @param customerNumber -The ID number of the customer.
	 * @param bankAccountName -The name of the bank account.
	 * @param sum -The sum to withdraw.
	 * @return True if successful, false if the bank account or the customer didn't exist. **/
	public boolean withdraw(String customerNumber, String bankAccountName, double sum) {
		if(mCustomerAccounts.containsKey(customerNumber)) {
			return mCustomerAccounts.get(customerNumber).withdrawFromBankAccount(bankAccountName, sum);
		}
		
		return false;
	}
	
	/** Get the balance for the selected account.
	 * @param customerNumber -The ID number for the customer.
	 * @param bankAccountName -The name of the bank account.
	 * @return The current balance for the bank account, negative infinity if the bank 
	 * account did not exist or NaN if the customer did not exist.
	 */
	public double balance(String customerNumber, String bankAccountName) {
		if(mCustomerAccounts.containsKey(customerNumber)) {
			return mCustomerAccounts.get(customerNumber).getBankAccountBalance(bankAccountName);
		}
		
		return Double.NaN;
	}
	
	/** Lock the selected bank account.
	 * @param customerNumber -The ID number for the customer.
	 * @param bankAccountName -The name of the bank account.
	 * @return True if successful, false if the bank account or the customer didn't exist. **/
	public boolean lock(String customerNumber, String bankAccountName) {
		if(mCustomerAccounts.containsKey(customerNumber)) {
			return mCustomerAccounts.get(customerNumber).lockBankAccount(bankAccountName);
		}
		
		return false;
	}
	
	/** Unlock the selected bank account.
	 * @param customerNumber -The ID number for the customer.
	 * @param bankAccountName -The name of the bank account.
	 * @return True if successful, false if the bank account or the customer didn't exist. **/
	public boolean unlock(String customerNumber, String bankAccountName) {
		if(mCustomerAccounts.containsKey(customerNumber)) {
			return mCustomerAccounts.get(customerNumber).unlockBankAccount(bankAccountName);
		}
		
		return false;
	}
	
	/** Transfer money between two accounts. The accounts may or may not be owned by the same customer.
	 * @param customerNumber	-The ID for the source customer.
	 * @param bankAccountName 	-The ID for the source account.
	 * @param targetCustomerNumber	-The ID for the target customer.
	 * @param targetBankAccountName	-The ID for the target account.
	 * @param sum	-The sum to transfer.
	 * @return True if successful, false if the bank account or the customer didn't exist or the source didn't have enough money. **/
	public boolean transfer(String customerNumber, String bankAccountName, String targetCustomerNumber, String targetBankAccountName, double sum) {
		if(mCustomerAccounts.containsKey(customerNumber) && 
				mCustomerAccounts.containsKey(targetCustomerNumber)) {
			if(customerNumber.equals(targetCustomerNumber))
				return mCustomerAccounts.get(customerNumber).transfer(bankAccountName, targetBankAccountName, sum);
			else {
				if(mCustomerAccounts.get(customerNumber).withdrawFromBankAccount(bankAccountName, sum)) {
					return mCustomerAccounts.get(targetCustomerNumber).insertToBankAccount(targetBankAccountName, sum);
				}
			}
		}
		
		return false;
	}
	
	/** Get the balance for each of the customer's bank accounts.
	 * @param customerNumber -The ID number for the customer.
	 * @return A map mapping the name of the bank account with the balance for that account. Or null.
	 */
	public Map<String, Double> getAllBalance(String customerNumber) {
		if(mCustomerAccounts.containsKey(customerNumber)) {
			return mCustomerAccounts.get(customerNumber).getAllBankAccountBalance();
		}	
		return null;
	}
	
	/** Get the transaction history for the selected account.
	 * @param customerNumber -The ID number for the customer.
	 * @param bankAccountName -The name of the bank account.
	 * @return A list containing the transaction history for the bank account. **/
	public ArrayList<TransactionInfo> history(String customerNumber, String bankAccountName) {
		if(mCustomerAccounts.containsKey(customerNumber)) {
			return mCustomerAccounts.get(customerNumber).getBankAccountHistory(bankAccountName);
		}
		return null;
	}
	
	/** Get the selected customer account. **/
	public CustomerAccount customer(String customerNumber) {
		return mCustomerAccounts.get(customerNumber);
	}
}
