package bankSystem;

import java.util.ArrayList;

/** This class handles a bank account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class BankAccount {
	private double mBalance;
	private boolean mLocked;
	private ArrayList<TransactionInfo> mHistory;
	
	/** Create a new empty bank account. **/
	public BankAccount() {
		mBalance = 0.0f;
		mLocked = false;
		mHistory = new ArrayList<TransactionInfo>();
	}
	
	/** Get the current account balance. **/
	public double balance() {
		return mBalance;
	}
	
	/** Lock this account from withdrawals. **/
	public void lock() {
		mLocked = true;
	}
	
	/** Open this account up for withdrawals. **/
	public void unlock() {
		mLocked = false;
	}
	
	/** Insert money into this account- **/
	public void insert(double money) {
		mBalance += Math.abs(money);
		mHistory.add(new TransactionInfo(money, mBalance));
	}
	
	/** Withdraw money from this account. 
	 * @return True if the account had the money, else false. **/
	public boolean withdraw(double money) {
		if(!mLocked) {
			double withdrawal = Math.abs(money);
			if(mBalance > withdrawal) {
				mBalance -= withdrawal;
				mHistory.add(new TransactionInfo(-money, mBalance));
				return true;
			}
		}
		
		return false;
	}
	
	/** Get the transaction history for this account. **/
	ArrayList<TransactionInfo> history() {
		return mHistory;
	}
}
