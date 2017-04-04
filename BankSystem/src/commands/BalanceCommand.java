package commands;

import bankSystem.Bank;
import ui.UserInterface;

/** This class represents a call to get the balance of a specific account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class BalanceCommand implements Command{
	private String mBankAccountName;
	private UserInterface mUi = null;
	String mCustomer;
	
	/** Create a new command. **/
	public BalanceCommand(UserInterface ui, String customer, String bankAccountName) {
		mBankAccountName = bankAccountName;
		mUi = ui;
		mCustomer = customer;
	}
	
	@Override
	public void execute(Bank bank) {
		double balance = bank.balance(mCustomer, mBankAccountName);
		if(balance == Double.NEGATIVE_INFINITY)
			mUi.print("The account does not exist!\n\n");
		else
			mUi.print("\"" + mBankAccountName + "\"" + " : " + balance + "kr\n\n");
	}

}
