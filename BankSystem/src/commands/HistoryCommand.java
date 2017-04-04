package commands;

import java.util.ArrayList;

import bankSystem.Bank;
import bankSystem.TransactionInfo;
import bankSystem.UserInterface;

/** This class represents a call to get the transaction history of a specific account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class HistoryCommand implements Command {
	private String mBankAccountName;
	private UserInterface mUi = null;
	private String mCustomer;
	
	/** Create a new command to get the transaction history. **/
	public HistoryCommand(UserInterface ui, String customer, String bankAccountName) {
		mBankAccountName = bankAccountName;
		mUi = ui;
		mCustomer = customer;
	}
	
	@Override
	public void execute(Bank bank) {
		ArrayList<TransactionInfo> history = bank.history(mCustomer, mBankAccountName);
		String string = "";
		
		if(history != null) {
			for(TransactionInfo info : history) {
				string += "Change: " + info.transaction + "kr, Balance: " + info.balance + "kr\n";
			}
			mUi.print(string);
		}
		else
			mUi.print("Account " + mBankAccountName + " does not exist for customer " 
					+ mCustomer + "!\n");
	}

}
