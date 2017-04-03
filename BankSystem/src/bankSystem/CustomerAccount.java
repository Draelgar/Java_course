package bankSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/** This class represents a bank customer's account and includes 1 or more bank accounts.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class CustomerAccount {
	private Map<String, BankAccount> mBankAccounts;
	
	/** Create a new customer account.
	 * @param bankAccountName -The name used to identify the individual bank accounts. **/
	public CustomerAccount(String bankAccountName) {
		mBankAccounts = new HashMap<String, BankAccount>();
		mBankAccounts.put(bankAccountName, new BankAccount());
	}
	
	/** Get the balance for a specific bank account. 
	 * @return The current balance, or negative infinity if the account does not exist.**/
	public double getBankAccountBalance(String bankAccountName) {
		BankAccount account = mBankAccounts.get(bankAccountName);
		if(account != null)
			return account.balance();
		
		return Double.NEGATIVE_INFINITY;
	}
	
	/** Get the transaction history for a specific bank account. 
	 * @return The current history, or null if the account does not exist.**/
	public ArrayList<TransactionInfo> getBankAccountHistory(String bankAccountName) {
		BankAccount account = mBankAccounts.get(bankAccountName);
		if(account != null)
			return account.history();
		
		return null;
	}
	
	/** Add a new bank account to the list.
	 * @param bankAccountName -The name used to identify the individual bank accounts. 
	 * @return True if the operation was successful, false if the bank account name was 
	 * 		already taken. **/
	public boolean addBankAccount(String bankAccountName) {
		if(!mBankAccounts.containsKey(bankAccountName)) {
			mBankAccounts.put(bankAccountName, new BankAccount());
			return true;
		}
		
		return false;
	}
	
	/** Lock the selected bank acount from withdrawals.
	 * @param bankAccountName -The name for the account.
	 * @return True if the account exists, else false. **/
	public boolean lockBankAccount(String bankAccountName) {
		BankAccount account = mBankAccounts.get(bankAccountName);
		if(account != null) {
			account.lock();
			return true;
		}
		
		return false;
	}
	
	/** Unlock a locked bank account, Does nothing if the account wasn't locked.
	 * @param bankAccountName -The name used for identifying the account.
	 * @return True if the account exists, else false. **/
	public boolean unlockBankAccount(String bankAccountName) {
		BankAccount account = mBankAccounts.get(bankAccountName);
		if(account != null) {
			account.unlock();
			return true;
		}
		
		return false;
	}
	
	/** Add a sum of money to selected bank account.
	 * @param bankAccountName -The name of the selected account.
	 * @param money -The sum of money to add.
	 * @return True if successful, false if the account did not exist. **/
	public boolean insertToBankAccount(String bankAccountName, double money) {
		BankAccount account = mBankAccounts.get(bankAccountName);
		if(account != null) {
			account.insert(money);
			return true;
		}
		
		return false;
	}
	
	/** Withdraw a sum of money from the selected bank account.
	 * @param bankAccountName
	 * @param money -A positive sum to withdraw.
	 * @return True if the account exists and its balance is high enough. **/
	public boolean withdrawFromBankAccount(String bankAccountName, double money) {
		BankAccount account = mBankAccounts.get(bankAccountName);
		if(account != null) {
			return account.withdraw(money);
		}
		
		return false;
	}

	/** Transfer a sum of money between two bank accounts.
	 * @param source -The name of the bank account source.
	 * @param destination -The name of the bank account destination.
	 * @param money -The sum of money to transfer.
	 * @return True if the operation succeeded, else false. **/
	public boolean transfer(String source, String destination, double money) {
		BankAccount sender = mBankAccounts.get(source);
		if(sender != null) {
			BankAccount receiver = mBankAccounts.get(destination);
			if(receiver != null)
				if(sender.withdraw(money)) {
					receiver.insert(money);
					return true;
				}
		}
		
		return false;
	}
	
	/** Get the transaction history for all accounts. **/
	public Map<String, ArrayList<TransactionInfo>> getAllBankAccountHistory() {
		Map<String, ArrayList<TransactionInfo>> history = 
				new HashMap<String, ArrayList<TransactionInfo>>(); 
		
		Iterator<Entry<String, BankAccount>> it = mBankAccounts.entrySet().iterator();
		
		while (it.hasNext()) {
			Entry<String, BankAccount> element = it.next();
			history.put(element.getKey(), element.getValue().history());
		}
		
		return history;
	}
	
	/** Get the balance for all bank accounts. **/
	public Map<String, Double> getAllBankAccountBalance() {
		Map<String, Double> balance = 
				new HashMap<String, Double>(); 
		
		Iterator<Entry<String, BankAccount>> it = mBankAccounts.entrySet().iterator();
		
		while (it.hasNext()) {
			Entry<String, BankAccount> element = it.next();
			balance.put(element.getKey(), element.getValue().balance());
		}
		
		return balance;
	}
}
