package commands;

import bankSystem.CustomerAccount;
import bankSystem.UserInterface;

/** This class represents a call to insert a sum of money to a specific account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class InsertCommand implements Command{
	private UserInterface mUi = null;
	private String mBankAccountName;
	private double mSum;
	
	public InsertCommand(UserInterface ui, String bankAccountName, double sum) {
		mUi = ui;
		mBankAccountName = bankAccountName;
		mSum = sum;
	}
	
	@Override
	public void execute(CustomerAccount ca) {
		if(ca.insertToBankAccount(mBankAccountName, mSum)){
			mUi.print("Inserted " + mSum + "kr to account \"" + mBankAccountName + "\"\n"
					+ "The total balance is now " + ca.getBankAccountBalance(mBankAccountName)
					+ "kr\n");
		}
		else{
			mUi.print("The bank account does not exist!\n");
		}	
	}

}
