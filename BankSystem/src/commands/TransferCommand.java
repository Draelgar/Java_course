package commands;

import bankSystem.Bank;
import bankSystem.UserInterface;

/** This class represents a call to transfer a sum of money between two accounts.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class TransferCommand implements Command{
	private UserInterface mUi = null;
	private String mBankAccountName;
	private String mTargetBankAccountName;
	private String mCustomer;
	private double mSum;
	
	public TransferCommand(UserInterface ui, String customer, String bankAccountName, String targetBankAccountName, double sum) {
		mUi = ui;
		mBankAccountName = bankAccountName;
		mTargetBankAccountName = targetBankAccountName;
		mSum = sum;
		mCustomer = customer;
	}
	
	@Override
	public void execute(Bank bank) {
		if(bank.transfer(mCustomer, mBankAccountName, mCustomer, mTargetBankAccountName, mSum)){
			mUi.print("Transfered " + mSum + "kr from account \"" + mBankAccountName
					+ "\" to account \"" + mTargetBankAccountName + "\"\n"
					+ "The total balance for " + mBankAccountName + " is now " + bank.balance(mCustomer, mBankAccountName)
					+ "kr\n");
		}
		else{
			mUi.print("Transaction denied!\n");
		}
	}

}
