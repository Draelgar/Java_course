package commands;

import java.util.ArrayList;

import bankSystem.CustomerAccount;
import bankSystem.TransactionInfo;
import bankSystem.UserInterface;

/** This class represents a call to get the transaction history of a specific account.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class HistoryCommand implements Command {
	private String mBankAccountName;
	private UserInterface mUi = null;
	
	/** Create a new command to get the transaction history. **/
	public HistoryCommand(UserInterface ui, String bankAccountName) {
		mBankAccountName = bankAccountName;
		mUi = ui;
	}
	
	@Override
	public void execute(CustomerAccount ca) {
		ArrayList<TransactionInfo> history = ca.getBankAccountHistory(mBankAccountName);
		String string = "";
		
		if(history != null) {
			for(TransactionInfo info : history) {
				string += "Change: " + info.transaction + "kr, Balance: " + info.balance + "kr\n";
			}
			mUi.print(string);
		}
		else
			mUi.print("The account does not exist!\n");
	}

}
