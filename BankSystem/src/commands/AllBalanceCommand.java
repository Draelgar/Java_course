package commands;

import java.util.Map;
import java.util.Map.Entry;

import bankSystem.Bank;
import ui.UserInterface;

/** This class represents a call to get the balance of all accounts.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class AllBalanceCommand implements Command {
	private UserInterface mUi = null;
	private String mCustomer;
	
	public AllBalanceCommand(UserInterface ui, String customer) {
		mUi = ui;
		mCustomer = customer;
	}
	
	@Override
	public void execute(Bank bank) {
		Map<String, Double> map = bank.getAllBalance(mCustomer);
		
		String string = "Account Balances:\n";
		java.util.Iterator<Entry<String, Double>> it = map.entrySet().iterator();
		
		while(it.hasNext()) {
			Entry<String, Double> line = it.next();
			string += "\t\u25CF \"" + mCustomer + "," + line.getKey() + "\": " + line.getValue() + "kr\n";
		}
		
		string += "\n";
		
		mUi.print(string);
	}

}
