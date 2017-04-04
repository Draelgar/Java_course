package commands;

import bankSystem.CustomerAccount;
import bankSystem.UserInterface;

/** This class represents a call to transfer a sum of money between two accounts.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class TransferCommand implements Command{
	private UserInterface mUi = null;
	private String mBankAccountName;
	private String mTargetBankAccountName;
	private double mSum;
	
	public TransferCommand(UserInterface ui, String bankAccountName, String targetBankAccountName, double sum) {
		mUi = ui;
		mBankAccountName = bankAccountName;
		mTargetBankAccountName = targetBankAccountName;
		mSum = sum;
	}
	
	@Override
	public void execute(CustomerAccount ca) {
		if(ca.transfer(mBankAccountName, mTargetBankAccountName, mSum)){
			mUi.print("Transfered " + mSum + "kr from account \"" + mBankAccountName
					+ "\" to account \"" + mTargetBankAccountName + "\"\n"
					+ "The total balance is now " + ca.getBankAccountBalance(mBankAccountName)
					+ "kr\n");
		}
		else{
			mUi.print("Transaction denied!\n");
		}
	}

}
