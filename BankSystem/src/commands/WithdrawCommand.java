package commands;

import bankSystem.Bank;
import ui.UserInterface;

/** This class represents a call to withdraw a sum of money from a specific account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class WithdrawCommand implements Command{
	private UserInterface mUi = null;
	private String mBankAccountName;
	private double mSum;
	private String mCustomer;
	
	public WithdrawCommand(UserInterface ui, String customer, String bankAccountName, double sum) {
		mUi = ui;
		mBankAccountName = bankAccountName;
		mSum = sum;
		mCustomer = customer;
	}
	
	@Override
	public void execute(Bank bank) {
		if(bank.withdraw(mCustomer, mBankAccountName, mSum)){
			mUi.print("Withdrew " + mSum + "kr from account \"" + mBankAccountName + "\"\n"
					+ "The total balance is now " + bank.balance(mCustomer, mBankAccountName)
					+ "kr\n");
		}
		else{
			mUi.print("Withdrawal denied!\n");
		}
	}

}
