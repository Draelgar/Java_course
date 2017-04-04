package commands;

import bankSystem.Bank;
import bankSystem.UserInterface;

/** This class represents a call to lock or unlock a specific account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class LockCommand implements Command{
	private String mBankAccountName;
	private UserInterface mUi = null;
	private boolean mLock;
	private String mCustomer;
	
	/** Create a new command to lock/unlock an account. **/
	public LockCommand(UserInterface ui, String customer, String bankAccountName, boolean lock) {
		mBankAccountName = bankAccountName;
		mUi = ui;
		mLock = lock;
		mCustomer = customer;
	}
	
	@Override
	public void execute(Bank bank) {
		if(mLock) {
			if(bank.lock(mCustomer, mBankAccountName))
				mUi.print("The account has been locked.\n");
			else
				mUi.print("The account doesn't exist!\n");
		}
		else {
			if(bank.unlock(mCustomer, mBankAccountName))
				mUi.print("The account has been unlocked.\n");
			else
				mUi.print("The account doesn't exist!\n");
		}
	}
}
