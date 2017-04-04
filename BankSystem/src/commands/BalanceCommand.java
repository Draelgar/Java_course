package commands;

import bankSystem.CustomerAccount;
import bankSystem.UserInterface;

/** This class represents a call to get the balance of a specific account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class BalanceCommand implements Command{
	private String mBankAccountName;
	private UserInterface mUi = null;
	
	/** Create a new command. **/
	public BalanceCommand(UserInterface ui, String bankAccountName) {
		mBankAccountName = bankAccountName;
		mUi = ui;
	}
	
	@Override
	public void execute(CustomerAccount ca) {
		double balance = ca.getBankAccountBalance(mBankAccountName);
		if(balance == Double.NEGATIVE_INFINITY)
			mUi.print("The account does not exist!\n\n");
		else
			mUi.print("\"" + mBankAccountName + "\"" + " : " + balance + "kr\n\n");
	}

}
