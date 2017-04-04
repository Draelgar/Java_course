package commands;

import bankSystem.CustomerAccount;
import bankSystem.UserInterface;

/** This class represents a call to withdraw a sum of money from a specific account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class WithdrawCommand implements Command{
	private UserInterface mUi = null;
	private String mBankAccountName;
	private double mSum;
	
	public WithdrawCommand(UserInterface ui, String bankAccountName, double sum) {
		mUi = ui;
		mBankAccountName = bankAccountName;
		mSum = sum;
	}
	
	@Override
	public void execute(CustomerAccount ca) {
		if(ca.withdrawFromBankAccount(mBankAccountName, mSum)){
			mUi.print("Withdrew " + mSum + "kr from account \"" + mBankAccountName + "\"\n"
					+ "The total balance is now " + ca.getBankAccountBalance(mBankAccountName)
					+ "kr\n");
		}
		else{
			mUi.print("Withdrawal denied!\n");
		}
	}

}
