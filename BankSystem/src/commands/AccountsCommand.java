package commands;

import java.util.Map;
import java.util.Map.Entry;

import bankSystem.Bank;
import bankSystem.UserInterface;

/** This class represents a call to get the names of the accounts.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class AccountsCommand implements Command {
	private UserInterface mUi = null;
	private String mCustomer;
	
	public AccountsCommand(UserInterface ui, String customer) {
		mUi = ui;
		mCustomer = customer;
	}
	
	@Override
	public void execute(Bank bank) {
		Map<String, Double> map = bank.getAllBalance(mCustomer);
		
		String string = "Accounts:\n";
		java.util.Iterator<Entry<String, Double>> it = map.entrySet().iterator();
		
		while(it.hasNext()) {
			Entry<String, Double> line = it.next();
			string += "\t\u25CF \"" + mCustomer + "," + line.getKey() + "\"\n";
		}
		
		string += "\n";
		
		mUi.print(string);
	}
	
}
