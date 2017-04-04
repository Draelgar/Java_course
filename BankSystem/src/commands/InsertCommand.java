package commands;

import bankSystem.Bank;
import ui.UserInterface;

/** This class represents a call to insert a sum of money to a specific account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class InsertCommand implements Command{
	private UserInterface mUi = null;
	private String mBankAccountName;
	private double mSum;
	private String mCustomer;
	
	public InsertCommand(UserInterface ui, String customer, String bankAccountName, double sum) {
		mUi = ui;
		mBankAccountName = bankAccountName;
		mSum = sum;
		mCustomer = customer;
	}
	
	@Override
	public void execute(Bank bank) {
		if(bank.insert(mCustomer, mBankAccountName, mSum)){
			mUi.print("Inserted " + mSum + "kr to account \"" + mBankAccountName + "\"\n"
					+ "The total balance is now " + bank.balance(mCustomer, mBankAccountName)
					+ "kr\n");
		}
		else{
			mUi.print("The bank account does not exist!\n");
		}	
	}

}
