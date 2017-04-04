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
	
	/** Check weather this account is locked or not. **/
	public boolean isLocked() {
		return mLocked;
	}
	
	/** Insert money into this account- **/
	public boolean insert(double money) {
		if(money >= 0.0) {
			mBalance += money;
			mHistory.add(new TransactionInfo(money, mBalance));
			return true;
		}
		
		return false;
	}
	
	/** Withdraw money from this account. 
	 * @return True if the account had the money, else false. **/
	public boolean withdraw(double money) {
		if(!mLocked) {
			if(money >= 0.0) {
				double withdrawal = money;
				if(mBalance >= withdrawal) {
					mBalance -= withdrawal;
					mHistory.add(new TransactionInfo(-money, mBalance));
					return true;
				}
			}
		}
		
		return false;
	}
	
	/** Get the transaction history for this account. **/
	public ArrayList<TransactionInfo> history() {
		return mHistory;
	}
}
