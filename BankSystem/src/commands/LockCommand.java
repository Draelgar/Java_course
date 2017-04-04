package commands;

import bankSystem.CustomerAccount;
import bankSystem.UserInterface;

/** This class represents a call to lock or unlock a specific account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class LockCommand implements Command{
	private String mBankAccountName;
	private UserInterface mUi = null;
	private boolean mLock;
	
	/** Create a new command to lock/unlock an account. **/
	public LockCommand(UserInterface ui, String bankAccountName, boolean lock) {
		mBankAccountName = bankAccountName;
		mUi = ui;
		mLock = lock;
	}
	
	@Override
	public void execute(CustomerAccount ca) {
		if(mLock) {
			if(ca.lockBankAccount(mBankAccountName))
				mUi.print("The account has been locked.\n");
			else
				mUi.print("The account doesn't exist!\n");
		}
		else {
			if(ca.unlockBankAccount(mBankAccountName))
				mUi.print("The account has been unlocked.\n");
			else
				mUi.print("The account doesn't exist!\n");
		}
	}
}
